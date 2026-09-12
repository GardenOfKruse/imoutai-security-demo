// live-server.mjs — 实弹模式的本地原生代理服务（零依赖，单进程）
//
// 设计目标：浏览器 UI 只与本服务交互；真实请求由本服务用 Node 原生 https 客户端发出——
//   1. 零浏览器指纹：不发 Origin/Referer/Sec-Fetch-*/sec-ch-ua，UA 按真实 App 形态
//   2. cookie jar：自动保存/回传 set-cookie（acw_tc 等，真实 App 亦有 cookie 行为）
//   3. 头卫生：剔除空值头（如空 MT-Token）、按 profile 构造 MT-* 签名头
//   4. 证据留痕：每次真实请求落 JSONL（logs/live-requests.jsonl，脱敏）
//   5. 硬门禁（服务端复刻）：高峰 06:00–06:15 拒发、请求预算 ≤300
//
// 同时静态托管 dist/（npm run build 产物）→ 单进程即是"窗口程序本体"：
//   npm run build && npm start   →  http://localhost:8787

import http from 'node:http'
import https from 'node:https'
import fs from 'node:fs'
import path from 'node:path'
import { fileURLToPath } from 'node:url'
import { decodeResponseBody } from './response-codec.mjs'

const __dirname = path.dirname(fileURLToPath(import.meta.url))
const DIST = path.join(__dirname, '..', 'dist')
const LOG_DIR = path.join(__dirname, '..', '..', '..', 'work', '20260907-222238-i-app-frida-dump-dex-android-apk', 'evidence')
const PORT = process.env.PORT || 8787

const TARGETS = {
  app: 'app.moutai519.com.cn',
  h5: 'h5.moutai519.com.cn',
}
const ALLOWED_APIS = {
  app: new Set([
    '/xhr/front/user/register/vcode',
    '/xhr/front/user/register/login',
    '/xhr/front/trade/order/standard/compose/v2',
    '/xhr/front/trade/order/standard/submit/v2',
  ]),
  h5: new Set(['/xhr/front/mall/item/purchaseInfoV2']),
}
const BUDGET = { maxRequests: 300 }
const MIN_INTERVAL_MS = 2000

// ---------- 服务端硬门禁（与 realApi.js 同规则） ----------
let count = 0
let lastSentAt = 0

function windowStatus(d = new Date()) {
  const m = d.getHours() * 60 + d.getMinutes()
  if (m >= 6 * 60 && m < 6 * 60 + 15) {
    return { ok: false, level: 'peak', label: '⛔ 高峰时段（06:00–06:15 系统申购高峰）：禁止任何生产请求' }
  }
  return { ok: true, level: 'allowed', label: '✅ 客户允许时段（除 06:00–06:15 维护窗口外）' }
}

// ---------- cookie jar（跨请求保存 set-cookie） ----------
const jar = new Map()

function saveCookies(resHeaders) {
  const sc = resHeaders['set-cookie']
  if (!sc) return
  for (const line of Array.isArray(sc) ? sc : [sc]) {
    const [pair] = line.split(';')
    const eq = pair.indexOf('=')
    if (eq > 0) jar.set(pair.slice(0, eq).trim(), pair.slice(eq + 1).trim())
  }
}

function cookieHeader(profileCookie = '') {
  const merged = new Map()
  for (const part of String(profileCookie || '').split(';')) {
    const i = part.indexOf('=')
    if (i > 0) merged.set(part.slice(0, i).trim(), part.slice(i + 1).trim())
  }
  // 登录响应的动态 Set-Cookie 必须覆盖静态抓包档案中的旧值。
  for (const [k, v] of jar.entries()) merged.set(k, v)
  return [...merged.entries()].map(([k, v]) => `${k}=${v}`).join('; ')
}

// ---------- 头构造：按真实 App 形态，剔除浏览器痕迹与空值 ----------
function buildHeaders(profile, bodyStr, hasBody, hostKey = 'app') {
  // 全真实原则：profile 头按真实抓包原样转发（含 Origin/Referer/Sec-Fetch/空 csrf）。
  // 仅剔除传输层自动管理的头；空值头保留 x-csrf-token（真实请求中存在且为空）。
  const h = {}
  const sourceHeaders = hostKey === 'h5' ? (profile?.h5Headers || profile?.headers) : (profile?.appHeaders || profile?.headers)
  for (const [k, v] of Object.entries(sourceHeaders || {})) {
    if (v === null || v === undefined) continue
    if (v === '' && !/csrf/i.test(k)) continue
    if (/^(connection|content-length|host)$/i.test(k)) continue
    h[k] = String(v)
  }
  if (!h['User-Agent']) h['User-Agent'] = 'MT/android 12;screen/1080*2400;app/1.9.12;h5/1.9.12;'
  h['Accept'] = h['Accept'] || 'application/json'
  h['Connection'] = h['Connection'] || 'keep-alive'
  if (hasBody) h['Content-Type'] = h['Content-Type'] || 'application/json'
  const ck = cookieHeader(h['Cookie'] || h.cookie)
  if (ck) { h['Cookie'] = ck; delete h.cookie }
  return h
}

function redact(obj) {
  try {
    return JSON.stringify(obj).replace(
      /("(?:token|Token|md5|sign|password|vCode|mt_r|mt_k|Authorization)"\s*:\s*")([^"]{6})[^"]*(")/g,
      '$1$2***$3')
  } catch { return String(obj) }
}

function redactText(value) {
  return String(value ?? '')
    .replace(/(\"(?:token|Token|md5|sign|password|vCode|mt_r|mt_k|Authorization)\"\s*:\s*\")([^\"]*)(\")/gi, '$1<redacted>$3')
    .replace(/(MT-Token(?:-Wap)?=)[^;,\s\"]+/gi, '$1<redacted>')
}

function redactHeaders(headers) {
  const out = {}
  for (const [key, value] of Object.entries(headers || {})) {
    out[key] = /(authorization|cookie|token|password|secret|md5|sign|device[-_]?id|clips|bs-dvid|_d_u)/i.test(key)
      ? '<redacted>'
      : String(value)
  }
  return out
}

function appendEvidence(entry) {
  try {
    fs.mkdirSync(LOG_DIR, { recursive: true })
    fs.appendFileSync(path.join(LOG_DIR, 'live-requests.jsonl'),
    JSON.stringify({ ...entry, reqHeaders: entry.reqHeaders, respBody: redactText(entry.respBody).slice(0, 2000) }) + '\n')
  } catch { /* 留痕失败不阻塞演示 */ }
}

// ---------- 真实请求（Node 原生 https，零浏览器指纹） ----------
function realRequest({ host: hostKey, api, method = 'POST', body = {}, profile }) {
  return new Promise((resolve, reject) => {
    const hostname = TARGETS[hostKey]
    if (!hostname) return reject(new Error('unknown host key: ' + hostKey))
    const bodyStr = method === 'GET' ? undefined : JSON.stringify(body ?? {})
    const headers = buildHeaders(profile, bodyStr, !!bodyStr, hostKey)
    if (bodyStr) headers['Content-Length'] = Buffer.byteLength(bodyStr)

    const req = https.request({
      hostname, port: 443, path: api, method, headers, gzip: true,
      timeout: 15000,
    }, (res) => {
      saveCookies(res.headers)
      let chunks = []
      res.on('data', (c) => { chunks.push(c) })
      res.on('end', () => {
        try {
          const text = decodeResponseBody(Buffer.concat(chunks), res.headers['content-encoding'])
          resolve({ status: res.statusCode, headers: res.headers, text, setCookie: res.headers['set-cookie'] || [] })
        } catch (e) {
          reject(new Error(`响应解压失败：${e.message || e}`))
        }
      })
    })
    req.on('timeout', () => req.destroy(new Error('timeout 15s')))
    req.on('error', reject)
    if (bodyStr) req.write(bodyStr)
    req.end()
  })
}

// ---------- API：POST /api/live ----------
async function handleLive(req, res, raw) {
  const ws = windowStatus()
  if (ws.level === 'peak') return json(res, 451, { error: ws.label })
  if (count >= BUDGET.maxRequests) return json(res, 429, { error: `请求预算耗尽（≤${BUDGET.maxRequests}）` })
  const now = Date.now()
  if (lastSentAt && now - lastSentAt < MIN_INTERVAL_MS) {
    return json(res, 429, { error: `请求间隔不足，需至少 ${MIN_INTERVAL_MS / 1000} 秒` })
  }

  let payload
  try { payload = JSON.parse(raw) } catch { return json(res, 400, { error: 'bad json' }) }
  const { host, api, method = 'POST', body = {}, profile } = payload
  if (!profile?.headers && !profile?.appHeaders) return json(res, 400, { error: '缺少 profile.headers（授权门生成）' })
  if (profile.verifiedCapture !== true || profile.profileType === 'redacted-template' || profile.profileType === 'offline-algorithm-research') {
    return json(res, 403, { error: '仅允许授权测试设备真实取证档案；占位或离线研究档案已阻断' })
  }
  if (!api || !api.startsWith('/')) return json(res, 400, { error: 'bad api path' })
  if (!ALLOWED_APIS[host]?.has(api)) return json(res, 403, { error: 'API 不在当前演示授权白名单内' })
  if (/(^|\/)pay(\/|$)/i.test(api) || /(^|\/)order\/pay(\/|$)/i.test(api)) {
    return json(res, 403, { error: '支付接口永久禁止调用' })
  }

  count++
  lastSentAt = now
  const t0 = Date.now()
  let out
  try {
    out = await realRequest({ host, api, method, body, profile })
  } catch (e) {
    out = { status: 0, headers: {}, text: String(e.message || e) }
  }
  const ms = Date.now() - t0

  let jsonBody = null
  try { jsonBody = JSON.parse(out.text) } catch { /* 保留原文 */ }

  appendEvidence({
    ts: new Date().toISOString(), windowLevel: ws.level, host: TARGETS[host], api, method,
    reqHeaders: out.headers ? redactHeaders(headersSentShim(payload, out)) : null,
    reqBody: redact(body), status: out.status, ms,
    respHeaders: {
      server: out.headers?.server, via: out.headers?.via,
      'content-type': out.headers?.['content-type'],
      'content-encoding': out.headers?.['content-encoding'],
    },
    respBody: redactText(out.text),
  })

  return json(res, 200, {
    status: out.status, ms,
    text: String(out.text).slice(0, 8000),
    json: jsonBody,
    setCookie: out.setCookie || [],
    reqHeaders: redactHeaders(headersSentShim(payload, out)),
    respHeaders: {
      server: out.headers?.server, via: out.headers?.via,
      'content-type': out.headers?.['content-type'],
      'content-encoding': out.headers?.['content-encoding'],
      'x-site-cache-status': out.headers?.['x-site-cache-status'],
    },
    budget: { used: count, left: BUDGET.maxRequests - count },
    window: ws.label,
  })
}

// 构造"实际发送的头"视图（教学/取证展示）
function headersSentShim(payload, out) {
  const h = buildHeaders(payload.profile, '{}', payload.method !== 'GET', payload.host)
  return { ...h, Host: TARGETS[payload.host] }
}

function json(res, code, obj) {
  const s = JSON.stringify(obj)
  res.writeHead(code, { 'Content-Type': 'application/json; charset=utf-8', 'Access-Control-Allow-Origin': '*' })
  res.end(s)
}

// ---------- 静态托管 dist/ ----------
const MIME = { '.html': 'text/html', '.js': 'text/javascript', '.css': 'text/css', '.json': 'application/json', '.svg': 'image/svg+xml', '.png': 'image/png', '.ico': 'image/x-icon' }

function serveStatic(req, res) {
  let p = req.url.split('?')[0]
  if (p === '/' || p === '') p = '/index.html'
  const file = path.join(DIST, p)
  if (!file.startsWith(DIST)) return json(res, 403, { error: 'forbidden' })
  fs.readFile(file, (err, data) => {
    if (err) {
      fs.readFile(path.join(DIST, 'index.html'), (e2, d2) => {
        if (e2) return json(res, 404, { error: 'dist 未构建：先 npm run build' })
        res.writeHead(200, { 'Content-Type': 'text/html', 'Cache-Control': 'no-store, must-revalidate' }); res.end(d2)
      })
      return
    }
    res.writeHead(200, {
      'Content-Type': MIME[path.extname(file)] || 'application/octet-stream',
      'Cache-Control': 'no-store, must-revalidate',   // 演示现场绝不吃缓存旧 bundle
    })
    res.end(data)
  })
}

// ---------- 服务入口 ----------
const server = http.createServer((req, res) => {
  if (req.method === 'OPTIONS') {
    res.writeHead(204, {
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'POST, GET, OPTIONS',
      'Access-Control-Allow-Headers': 'Content-Type',
    })
    return res.end()
  }
  if (req.url === '/api/live' && req.method === 'POST') {
    let raw = ''
    req.on('data', (c) => { raw += c })
    req.on('end', () => handleLive(req, res, raw).catch((e) => json(res, 500, { error: String(e) })))
    return
  }
  if (req.url === '/api/status') {
    return json(res, 200, { budget: { used: count, left: BUDGET.maxRequests - count }, window: windowStatus().label, cookies: [...jar.keys()] })
  }
  serveStatic(req, res)
})

server.listen(PORT, () => {
  console.log(`[live-server] http://localhost:${PORT}  (dist 静态 + /api/live 实弹代理)`)
  console.log(`[live-server] ${windowStatus().label}`)
})
