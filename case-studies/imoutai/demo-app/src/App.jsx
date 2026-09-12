import { useEffect, useMemo, useState } from 'react'
import FlowHeader, { AttackNotes } from './components/FlowHeader.jsx'
import ModeGate from './components/ModeGate.jsx'
import PhoneLogin from './components/PhoneLogin.jsx'
import ProductSelect from './components/ProductSelect.jsx'
import CaptchaVerify from './components/CaptchaVerify.jsx'
import AddressForm from './components/AddressForm.jsx'
import PaymentSelect from './components/PaymentSelect.jsx'
import PayLinkResult from './components/PayLinkResult.jsx'
import RequestLog from './components/RequestLog.jsx'
import SwaggerPage from './components/SwaggerPage.jsx'
import { deriveDeviceKey, deriveClipsToken } from './lib/deviceKey.js'
import { selfTest, buildVcodeSign, VERIFIED_DEVICE_KEY } from './lib/signature.js'
import { mockSendSmsCode, mockLogin, mockSubmitOrder, recordApi } from './lib/mockApi.js'
import { live, budgetLeft, resetBudget, setProfile, liveStats, windowLabel } from './lib/realApi.js'

export const STEPS = ['验证码登录', '选购商品', '提交 · 验证码', '填写地址', '选择支付', '生成支付链接']

const TOKEN_KEYS = ['token', 'mtToken', 'accessToken', 'authToken', 'access_token', 'mt_token', 'jwt', 'sessionToken']
const TOKEN_ENVELOPES = ['', 'data', 'result', 'payload', 'data.data', 'data.result', 'result.data']

function valueAt(obj, path) {
  return path.split('.').filter(Boolean).reduce((v, key) => v && typeof v === 'object' ? v[key] : undefined, obj)
}

function extractLoginToken(json) {
  if (!json || typeof json !== 'object') return ''
  for (const envelope of TOKEN_ENVELOPES) {
    const value = valueAt(json, envelope)
    for (const key of TOKEN_KEYS) {
      if (typeof value?.[key] === 'string' && value[key].trim()) return value[key].trim()
    }
  }
  return ''
}

function extractCookieToken(setCookie) {
  for (const line of Array.isArray(setCookie) ? setCookie : [setCookie]) {
    const match = String(line || '').match(/(?:^|;\s*)MT-Token-Wap=([^;]+)/i)
    if (match?.[1]) return match[1].trim()
  }
  return ''
}

function extractH5Token(json) {
  const raw = valueAt(json, 'data.cookie') || valueAt(json, 'cookie')
  if (typeof raw !== 'string' || !raw.trim()) return ''
  const match = raw.match(/(?:^|;\s*)MT-Token-Wap=([^;]+)/i)
  return (match?.[1] || raw).trim()
}

function upsertCookie(header, name, value) {
  const values = new Map()
  for (const part of String(header || '').split(';')) {
    const i = part.indexOf('=')
    if (i > 0) values.set(part.slice(0, i).trim(), part.slice(i + 1).trim())
  }
  values.set(name, value)
  return [...values.entries()].map(([k, v]) => `${k}=${v}`).join('; ')
}

function applySessionToken(p, token, h5Token = '') {
  const appHeaders = { ...(p.appHeaders || p.headers), 'MT-Token': token }
  const h5Headers = { ...(p.h5Headers || p.headers) }
  const cookie = h5Headers.Cookie || h5Headers.cookie
  h5Headers.Cookie = upsertCookie(cookie, 'MT-Token-Wap', h5Token)
  delete h5Headers.cookie
  return {
    ...p,
    headers: { ...(p.headers || {}), 'MT-Token': token },
    appHeaders,
    h5Headers,
  }
}

function usableSession(s) {
  return Boolean(s?.token && (s.mode !== 'live' || (s.authenticated === true && s.h5Token && !String(s.token).startsWith('LIVE_'))))
}

export default function App() {
  // mode: 'select' 未选 | 'mock' 演示模式 | 'gate' 实弹授权门 | 'live' 实弹执行 | 'swagger' API 文档
  // clean=true 时为「纯净版 UI」：隐藏全部说明/日志/侧栏，用于开场真实下单 + 后台查证
  const [mode, setMode] = useState('select')
  const [clean, setClean] = useState(false)
  const [mobile, setMobile] = useState(() => localStorage.getItem('mt_mobile') || '13800001234')
  const [session, setSession] = useState(() => {
    try { return JSON.parse(localStorage.getItem('mt_session')) || null } catch { return null }
  })
  const [step, setStep] = useState(() => (session?.token ? 1 : 0))
  const token = session?.token || null
  const [cart, setCart] = useState([])
  const [order, setOrder] = useState(null)
  const [address, setAddress] = useState(null)
  const [profile, setProfileState] = useState(null)

  const deviceKey = useMemo(() => deriveDeviceKey(), [])
  const clipsToken = useMemo(() => deriveClipsToken('device'), [])
  const signCheck = useMemo(() => selfTest(), [])
  const isLive = mode === 'live'
  const effDeviceKey = isLive ? profile?.deviceKey : deviceKey

  // —— 协议适配器：组件不感知 mock/live，按模式路由 ——
  const api = useMemo(() => ({
    mode,
    async sendSms(mob) {
      const ts = String(Date.now())
      // real-app-profile 的 deviceKey 是 clips_* 头部标识；vcode MD5 使用独立的
      // native 32-hex 签名 key（findings F1），两者不能混用。
      const sign = buildVcodeSign(isLive ? (profile.signingDeviceKey || VERIFIED_DEVICE_KEY) : deviceKey, mob, ts)
      if (isLive) {
        const resp = await live.sendSms({ mobile: mob, timestamp: ts, md5: sign.md5 })
        return { ts, sign, resp }
      }
      mockSendSmsCode(mob)
      return { ts, sign, resp: null }
    },
    async login(mob, code) {
      if (isLive) {
        const resp = await live.login({ mobile: mob, vCode: code, ydLogId: '', ydToken: '' })
        // 尽力捕获服务端下发的 token，自动注入后续请求头（登录态保持）
        const tk = extractLoginToken(resp?.json) || extractCookieToken(resp?.setCookie)
        const h5Token = extractH5Token(resp?.json) || extractCookieToken(resp?.setCookie)
        if (tk) {
          const nextProfile = applySessionToken(profile, tk, h5Token)
          setProfileState(nextProfile)
          setProfile(nextProfile)
        }
        return { token: tk, h5Token, authenticated: Boolean(tk && h5Token), resp }
      }
      mockLogin(mob)
      return { token: 'Token_' + Math.random().toString(36).slice(2, 12).padEnd(12, 'x'), resp: null }
    },
    async purchaseInfo(body) {
      if (isLive) return live.purchaseInfo(body || {})
      return null
    },
    async submitOrder(items) {
      if (isLive) throw new Error('实弹订单提交模板尚未按真实 App 抓包验证，已阻止发送')
      const orderId = 'MO' + Date.now()
      const o = { orderId, amount: (items.reduce((s, x) => s + x.product.price * x.qty, 0) / 100).toFixed(2), subject: items.map((x) => x.product.name).join(' / ') }
      mockSubmitOrder({ orderId, items: items.map((x) => ({ sku: x.product.id, qty: x.qty })) })
      return { order: o, resp: { status: 200, json: { code: 2000, message: '模拟下单成功', data: { orderId } }, simulated: true } }
    },
  }), [mode, profile, deviceKey, address])

  const startLive = (p) => {
    const nextProfile = usableSession(session) && session.mode === 'live' ? applySessionToken(p, session.token, session.h5Token) : p
    setProfileState(nextProfile); setProfile(nextProfile); resetBudget()
    // 恢复缓存的实弹登录态（避免重复短信验证）
    setMode('live'); setStep(usableSession(session) ? 1 : 0)
  }
  const logout = () => {
    localStorage.removeItem('mt_session')
    setSession(null); setStep(0); setOrder(null); setCart([])
  }
  const startClean = (p) => { setProfileState(p); setProfile(p); resetBudget(); setClean(true); setMode('live'); setStep(0) }

  return (
    <div className="app">
      <header className={'hero' + (clean ? ' cleanhero' : '')}>
        <div className="hero-inner">
          {clean ? (
            <h1 className="cleanh1">🛍️ 购买流程演示</h1>
          ) : (
          <h1>i茅台 App 安全培训演示 <span className="ver">v1.1</span>
            {mode !== 'select' && (
              <span className={'modechip ' + (isLive ? 'live' : '')}>{isLive ? '🔴 实弹模式' : '🟢 演示模式'}</span>
            )}
          </h1>
          )}
          {!clean && (<p className="hero-sub">
            客户端签名可复刻性还原 ——
            {isLive
              ? <> <b className="tred">实弹模式：向生产发送真实请求（维护窗口外 · 低频 · 止步支付）</b>，支付链接仅拼接展示<b>绝不调用</b></>
              : <> 全流程本地模拟，<b>不向真实服务发送任何请求</b>，支付链接仅拼接展示（<b>禁止真实调用</b>）</>}
          </p>)}
          {!clean && (
          <div className="hero-badges">
            <span className="badge ok">客户盖章授权</span>
            <span className="badge ok">线下备案</span>
            {isLive ? (
              <>
                <span className="badge warn">{windowLabel()}</span>
                <span className="badge">预算剩余 {budgetLeft()} / 300</span>
              </>
            ) : (
              <span className="badge">本地 Mock · 零生产交互</span>
            )}
            <span className="badge">数据脱敏</span>
            {signCheck.pass && <span className="badge ok">签名实现与真实样本逐字节一致 ✓</span>}
          </div>
          )}
        </div>
      </header>

      {mode === 'select' && (
        <div className="modesel">
          <div className="card modecard mock" onClick={() => setMode('mock')}>
            <h3>🟢 演示模式（Mock）</h3>
            <p>完整走一遍「登录 → 选购 → 验证码自动识别 → 地址 → 支付 → 链接生成」流程，所有请求为本地 Mock，随时可演示，无任何生产交互。</p>
            <span className="btn primary">进入演示模式</span>
          </div>
          <div className="card modecard" onClick={() => { setClean(true); setMode('gate') }}>
            <h3>🎬 纯净版流程（开场真实下单 · 无说明）</h3>
            <p>界面无任何讲解标记，观感与正常购买流程一致。开场用它真实下单 → 请后台查证订单存在且无异常；随后切回「演示模式」逐环节讲解原理。同样受实弹硬门禁约束（维护窗口/低频/预算/止步支付）。</p>
            <span className="btn danger">授权门 → 纯净版下单</span>
          </div>
          <div className="card modecard live" onClick={() => { setClean(false); setMode('gate') }}>
            <h3>🔴 实弹模式（Live · 仅授权执行时）</h3>
            <p>同样的流程，但请求真实发往生产（真实登录、真实下单止步支付）。需要：客户授权三确认 + 非维护窗口 + 测试设备 HeaderMap 档案。这是低频 runbook F 组用例的现场执行。</p>
            <span className="btn danger">通过授权门进入</span>
          </div>
        </div>
      )}

      {mode === 'select' && (
      <div className="swagger-entry card" onClick={() => setMode('swagger')}>
        <div style={{ display: 'flex', alignItems: 'center', gap: 16, justifyContent: 'space-between', flexWrap: 'wrap' }}>
          <div>
            <h3 style={{ marginBottom: 4 }}>📚 API 全景文档（Swagger）—— 从 App 运行时一次性还原</h3>
            <p style={{ color: 'var(--dim)', fontSize: 13.5 }}>
              105 个后端接口 · 42 个请求模型 schema · 按业务域分 tags · 请求体字段全部来自运行时反射取证。
              现场翻开给开发者看：他们的整个后端面，我们一台手机 + 一条 USB 线就拿到了。
            </p>
          </div>
          <span className="btn primary">打开 Swagger 文档 →</span>
        </div>
      </div>
      )}

      {mode === 'gate' && (
        <ModeGate onConfirm={startLive} />
      )}

      {mode === 'swagger' && <SwaggerPage onBack={() => setMode('select')} />}

      {(mode === 'mock' || mode === 'live') && (
        <>
          <FlowHeader steps={STEPS} current={step} clean={clean} />
          <main className={'layout' + (clean ? ' cleanlayout' : '')}>
            <section className="stage">
              {step === 0 && (
                <PhoneLogin clean={clean} mobile={mobile} setMobile={setMobile} deviceKey={effDeviceKey} api={api} isLive={isLive}
                  cachedSession={session}
                  onLogin={(tk, h5Token) => {
                    if (tk) {
                      const s = { token: tk, h5Token, authenticated: true, mode, mobile, ts: Date.now() }
                      setSession(s); localStorage.setItem('mt_session', JSON.stringify(s))
                    }
                    localStorage.setItem('mt_mobile', mobile)
                    setStep(1)
                  }} />
              )}
              {step === 1 && (
                <ProductSelect clean={clean} cart={cart} setCart={setCart} api={api} isLive={isLive}
                  onSubmit={(o) => { setOrder(o); setStep(2) }} />
              )}
              {step === 2 && (
                <CaptchaVerify clean={clean} order={order} isLive={isLive} onPass={() => setStep(3)} />
              )}
              {step === 3 && (
                <AddressForm address={address} setAddress={setAddress} onNext={() => setStep(4)} />
              )}
              {step === 4 && (
                <PaymentSelect clean={clean} clipsToken={clipsToken} isLive={isLive}
                  onBackToAddress={() => setStep(3)} onNext={() => setStep(5)} />
              )}
              {step === 5 && <PayLinkResult clean={clean} order={order} address={address} />}
            </section>

            {!clean && (<aside className="side">
              <div className="devinfo">
                <div className="devinfo-title">🖥️ 设备信息（{isLive ? '实弹：来自测试设备取证档案' : '演示：本机模拟'}）</div>
                <div className="kv"><span>deviceKey</span><code>{isLive ? profile?.deviceKey : deviceKey}</code></div>
                <div className="kv"><span>clips_token</span><code>{isLive ? '(档案 headers 内)…' : clipsToken}</code></div>
                <div className="kv"><span>MT-Token（登录态）</span><code>{token ? String(token).slice(0, 30) + '…' : '未登录'}</code></div>
                <div className="kv"><span>接口网关</span><code>{isLive ? '认证/订单：app · purchaseInfo：h5' : 'app（仅展示）'}</code></div>
                {isLive && <div className="kv"><span>请求预算</span><code>{liveStats().count} 已用 / 剩余 {budgetLeft()}</code></div>}
              </div>
              {step === 0 && <AttackNotes type="login" isLive={isLive} />}
              {step === 1 && <AttackNotes type="product" isLive={isLive} />}
              {step === 2 && <AttackNotes type="captcha" isLive={isLive} />}
              {step === 3 && <AttackNotes type="address" isLive={isLive} />}
              {step === 4 && <AttackNotes type="pay" isLive={isLive} />}
              {step === 5 && <AttackNotes type="paylink" isLive={isLive} />}
            </aside>)}
          </main>
          {!clean && <RequestLog />}
        </>
      )}

      {clean && mode === 'live' && (
        <button className="floatmenu" title="返回模式选择"
          onClick={() => { setMode('select'); setStep(0) }}>≡</button>
      )}
    </div>
  )
}
