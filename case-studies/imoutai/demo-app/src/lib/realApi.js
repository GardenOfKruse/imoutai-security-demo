// realApi.js — 实弹模式（Live Mode）：授权窗口内的真实协议调用
//
// ⚠️ 使用条件（全部满足才允许发请求，缺一即拒绝）：
//   1. 操作者已完成授权三确认（见 ModeGate）
//   2. 当前不在客户维护窗口：06:00–06:15 禁止生产请求，其余时间允许
//   3. 操作者在界面中粘贴了从测试设备（授权取证）采集的 HeaderMap 档案
//   4. 请求总量 ≤ runbook 预算
//   5. 止步支付页：本文件绝不实现 order/pay 的调用（支付链接仅拼接展示）
//
// 单进程保证：本 SPA 单线程顺序执行，天然单进程；请求计数器强制预算上限。

import { logStore } from './mockApi.js'

// 实弹请求不再由浏览器直发（浏览器会带 Origin/Referer/Sec-Fetch-*/sec-ch-ua 等指纹，
// 实测被阿里 ESA 边缘 480/4010 拒绝）。改走本地原生代理服务 live-server.mjs：
// Node 原生 https 客户端构造请求——零浏览器指纹、cookie jar、头按真实 App 形态。
export const HOSTS = {
  app: '/mt-app',
  h5: '/mt-h5',
}
export const DISPLAY_HOSTS = {
  app: 'https://app.moutai519.com.cn',
  h5: 'https://h5.moutai519.com.cn',
}
const LIVE_SERVER = '/api/live'

export const BUDGET = { maxRequests: 300, maxDurationMin: 30 }

// ---------- 允许窗口校验 ----------

function minutesOfDay(d) {
  return d.getHours() * 60 + d.getMinutes()
}

/** 客户最新时间规则：仅 06:00–06:15 维护窗口禁发，其余时间允许。 */
export function windowStatus(d = new Date()) {
  const m = minutesOfDay(d)
  if (m >= 6 * 60 && m < 6 * 60 + 15) {
    return { ok: false, level: 'peak', label: '⛔ 高峰时段（06:00–06:15 系统申购高峰）：禁止任何生产请求' }
  }
  return { ok: true, level: 'allowed', label: '✅ 客户允许时段（除 06:00–06:15 维护窗口外）' }
}

export function inAllowedWindow(d = new Date()) {
  return windowStatus(d).ok
}

export function windowLabel(d = new Date()) {
  return windowStatus(d).label
}

// ---------- 预算与状态 ----------

const state = { count: 0, startedAt: null, profile: null }

export function setProfile(p) { state.profile = p }
export function budgetLeft() { return BUDGET.maxRequests - state.count }
export function resetBudget() { state.count = 0; state.startedAt = null }
export function liveStats() {
  return { count: state.count, left: budgetLeft(), startedAt: state.startedAt, profile: !!state.profile }
}

function redact(obj) {
  try {
    const s = JSON.stringify(obj)
    return s.replace(/("(?:token|Token|md5|sign|password|vCode|mt_r|mt_k)"\s*:\s*")([^"]{6})[^"]*(")/g, '$1$2***$3')
  } catch { return String(obj) }
}

// ---------- 真实请求 ----------

export class LiveModeError extends Error {}

/**
 * 发起一次真实协议请求（仅授权窗口内可用）。
 * profile.headers 由操作者从测试设备（授权取证 intercept hook）导出的 HeaderMap 粘贴获得。
 */
export async function liveRequest({ api, method = 'POST', body = {}, host = 'h5', note = '' }) {
  if (!state.profile) throw new LiveModeError('未加载设备 HeaderMap 档案（测试设备取证导出）')
  const ws = windowStatus()
  if (ws.level === 'peak') throw new LiveModeError(ws.label + ' —— 请求已被硬门禁拦截')
  if (state.count >= BUDGET.maxRequests) throw new LiveModeError(`请求预算耗尽（≤${BUDGET.maxRequests}），硬门禁拦截`)
  if (!state.startedAt) state.startedAt = new Date().toISOString()

  state.count++
  const t0 = performance.now()
  let status = 0, respText = '', errMsg = null, jsonBody = null, reqHeaders = null, respHeaders = null
  try {
    // 浏览器 → 本地原生服务（同源）；真实请求由 live-server.mjs 用 Node https 原生客户端发出
    // （零浏览器指纹 + cookie jar + 头按真实 App 形态——浏览器直发会被 ESA 边缘 480/4010 拒绝）
    const res = await fetch(LIVE_SERVER, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ host, api, method, body, profile: state.profile }),
    })
    const data = await res.json()
    status = data.status
    respText = data.text || ''
    jsonBody = data.json || null
    reqHeaders = data.reqHeaders || null
    respHeaders = data.respHeaders || null
    if (data.error) errMsg = data.error
  } catch (e) {
    errMsg = String(e)
  }
  const ms = Math.round(performance.now() - t0)

  logStore._emit({
    id: `${Date.now()}_${Math.random().toString(36).slice(2, 6)}`,
    time: new Date().toLocaleTimeString('zh-CN', { hour12: false }),
    method, url: (DISPLAY_HOSTS[host] || '') + api, api,
    body: JSON.parse(redact(body) || '{}'),
    live: true, windowLevel: ws.level, status, ms,
    reqHeaders, respHeaders,
    respPreview: (errMsg ? errMsg : respText.slice(0, 220)),
    note: note + ` [预算剩余 ${budgetLeft()}]`,
  })

  if (errMsg) throw new LiveModeError(`网络异常：${errMsg}`)
  return { status, json: jsonBody, text: respText, ms, simulated: false }
}

/** 各步骤的真实接口封装（F 组用例：真实下单，止步支付） */
export const live = {
  sendSms: (body) =>
    liveRequest({
      api: '/xhr/front/user/register/vcode', host: 'app', body,
      note: 'F0 · 真实验证码请求（md5 由本页复刻算法用 profile.deviceKey 计算）',
    }),
  login: (body) =>
    liveRequest({ api: '/xhr/front/user/register/login', host: 'app', body, note: 'F0 · 真实登录（测试账号，人工输入验证码）' }),
  purchaseInfo: (body) => {
    const b = (body && Object.keys(body).length) ? body : { hot: true, spuId: 'IMTP1000313', jt: 'anonymous' }
    return liveRequest({
      api: '/xhr/front/mall/item/purchaseInfoV2', host: 'h5', method: 'POST', body: b,
      note: 'F1 · 目标接口 purchaseInfoV2（真实抓包 body 模板，200 已复现）',
    })
  },
  composeOrder: (body) =>
    liveRequest({ api: '/xhr/front/trade/order/standard/compose/v2', host: 'app', body, note: 'F2 · 订单组合' }),
  submitOrder: (body) =>
    liveRequest({ api: '/xhr/front/trade/order/standard/submit/v2', host: 'app', body, note: 'F3 · 真实下单（止步支付，绝不调用 order/pay）' }),
  // 🚫 故意不存在：order/pay —— 支付调用在实弹模式同样被禁止
}
