import { useMemo, useRef, useState } from 'react'
import { windowStatus } from '../lib/realApi.js'
import { deriveDeviceKey } from '../lib/deviceKey.js'
import { md5 } from '../lib/signature.js'
import { isVerifiedCaptureProfile, validateCaptureEvidence } from '../lib/captureEvidence.js'

/**
 * Live 默认文本只用于解释档案结构，不能解锁 Live：
 * - 浏览器环境派生值是 synthetic fixture，不是 native deviceKey 算法
 * - 字段名采用公开协议已知的头名骨架，缺少真实取证字段时必须拒绝发送
 */
function generateDefaultProfile() {
  const dk = deriveDeviceKey()
  const ts = String(Date.now())
  return {
    deviceKey: dk,
    headers: {
      'MT-Device-ID': dk,
      'MT-APP-Version': '1.9.12',
      'MT-Token': '',
      'MT-R': md5(dk + ts).slice(0, 32),
      'MT-Latn': '26.647661',
      'MT-Lng': '106.630153',
      'User-Agent': 'MT/android 12;screen/1080*2400;app/1.9.12;h5/1.9.12;',
      'Content-Type': 'application/json',
    },
    _meta: {
      generated: new Date().toLocaleString('zh-CN', { hour12: false }),
      note: '本档案是 Live 结构说明用 synthetic fixture，不含真实设备证明，不能解锁 Live；_meta 不会进入请求',
    },
    profileType: 'generated-skeleton',
    verifiedCapture: false,
  }
}

/** 实弹模式授权门：三确认 + 窗口校验 + HeaderMap 档案（自动生成/可粘贴覆盖） */
export default function ModeGate({ onConfirm, onFallbackMock }) {
  const initial = useMemo(() => JSON.stringify(generateDefaultProfile(), null, 2), [])
  const [c1, setC1] = useState(false)
  const [c2, setC2] = useState(false)
  const [c3, setC3] = useState(false)
  const [profileText, setProfileText] = useState(initial)
  const [err, setErr] = useState('')
  const [loadingReal, setLoadingReal] = useState(false)
  const fileRef = useRef(null)
  const ws = windowStatus()
  let evidenceStatus = null
  try {
    evidenceStatus = validateCaptureEvidence(JSON.parse(profileText))
  } catch { evidenceStatus = validateCaptureEvidence(null) }

  const regenerate = () => {
    setErr('')
    setProfileText(JSON.stringify(generateDefaultProfile(), null, 2))
  }
  const loadRealFile = async (event) => {
    const file = event.target.files?.[0]
    event.target.value = ''
    if (!file) return
    setLoadingReal(true)
    setErr('')
    try {
      const h5 = JSON.parse(await file.text())
      // 部署包只携带一份本地真实档案，避免把含 Cookie 的 App 档案再复制一份。
      // GLM 的成功样本表明 App 验证码请求相对 H5 只需补齐 MT-Device-ID 和
      // WebView 的 Accept-* 头；Cookie、Origin、Referer 等沿用同一份抓包档案。
      const appHeaders = {
        ...h5.headers,
        'MT-Device-ID': h5.deviceKey,
        'Accept-Encoding': 'gzip, deflate',
        'Accept-Language': 'zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7',
      }
      setProfileText(JSON.stringify({
        ...h5,
        // App 登录与 H5 业务请求使用各自真实抓包头，避免把 H5 档案误用于验证码接口。
        headers: appHeaders,
        appHeaders,
        h5Headers: h5.headers,
        profileType: 'paired app-domain + h5-webview real profiles',
        // 必须由取证流程显式写入 true；缺失字段不能自动升级为真实档案。
        verifiedCapture: h5.verifiedCapture === true,
        _meta: {
          source: 'operator-selected local authorized capture',
          app: 'derived from the same captured device profile',
          h5: h5._meta,
        },
      }, null, 2))
    } catch (e) {
      setErr('真实档案加载失败：' + e.message + '（请选择授权取证导出的 JSON 文件）')
    } finally { setLoadingReal(false) }
  }

  const confirm = () => {
    if (ws.level === 'peak') { setErr(ws.label); return }
    if (!(c1 && c2 && c3)) { setErr('三项确认未勾选完整'); return }
    let profile = null
    try {
      profile = JSON.parse(profileText)
      if (!profile.headers || typeof profile.headers !== 'object') throw new Error('缺少 headers 字段')
      if (!profile.deviceKey) throw new Error('缺少 deviceKey 字段')
      if (!isVerifiedCaptureProfile(profile)) throw new Error('缺少 verifiedCapture=true 或真实 HeaderMap 标记')
      const appHeaders = profile.appHeaders || profile.headers
      for (const key of ['MT-Device-ID', 'Cookie', 'Origin', 'Referer', 'X-Requested-With']) {
        if (!appHeaders[key]) throw new Error('App 登录档案缺少 ' + key + '（不能使用默认档案或 H5 档案）')
      }
    } catch (e) {
      setErr('档案解析失败：' + e.message + '（可点击"重新生成默认档案"恢复）')
      return
    }
    onConfirm(profile)
  }

  return (
    <div className="card stepcard">
      <h2>🔴 实弹模式 · 授权门</h2>
      <p className="hint">实弹模式会向生产系统发送<b>真实请求</b>（真实登录、真实下单，止步支付）。仅用于授权的低频 runbook 执行；日常演示请用「演示模式」，禁止大并发和高频调用。</p>

      <div className={'gate-window' + (ws.level === 'peak' ? ' bad' : '')}>{ws.label}<span className="gate-window-sub">除 06:00–06:15 客户维护窗口外均可执行 · 维护窗口内禁止生产请求</span></div>

      <div className="gchecks">
        <label><input type="checkbox" checked={c1} onChange={(e) => setC1(e.target.checked)} />
          确认当前为<b>客户授权的低频测试执行</b>（禁止大并发和高频调用）</label>
        <label><input type="checkbox" checked={c2} onChange={(e) => setC2(e.target.checked)} />
          确认使用<b>客户提供的测试账号</b>，验证码由测试手机人工接收；下单<b>止步支付页</b></label>
        <label><input type="checkbox" checked={c3} onChange={(e) => setC3(e.target.checked)} />
          确认客户对接人<b>已知情并在场</b>，知晓本次真实下单演示</label>
      </div>

      <div className="frow col">
        <label className="flabel">
          HeaderMap 档案（默认内容仅为 synthetic 结构 fixture；Live 必须粘贴授权测试设备真实抓包 JSON）
        </label>
        <textarea className="ipt area" rows={10} value={profileText} onChange={(e) => setProfileText(e.target.value)} />
        <div style={{ display: 'flex', gap: 10 }}>
          <button className="btn ghost" style={{ alignSelf: 'flex-start' }} onClick={regenerate}>↺ 重新生成默认档案</button>
          <input ref={fileRef} type="file" accept=".json,application/json" onChange={loadRealFile} style={{ display: 'none' }} />
          <button className="btn primary" style={{ alignSelf: 'flex-start' }} onClick={() => fileRef.current?.click()} disabled={loadingReal}>{loadingReal ? '⏳ 加载中…' : '📂 选择真实档案（App + H5 抓包）'}</button>
        </div>
      </div>

      <div className="note">📌 档案说明：默认值只用于展示字段结构，<b>不代表真机算法或真机身份</b>；
        <code>deviceKey / MT-Device-ID</code> 的 native 派生仍未验证，<code>MT-Token</code> 留空（登录后由服务端下发并自动注入）。
        下方 <code>_meta</code> 块仅存在客户端，绝不随请求发出。</div>

      <div className="note">🧾 订单取证完整度：<b>{evidenceStatus.orderReady ? '已具备结构化下单证据' : '尚未具备真实下单证据'}</b>。
        {evidenceStatus.missing.length > 0 && <span> 缺少：{evidenceStatus.missing.slice(0, 4).join('、')}{evidenceStatus.missing.length > 4 ? ' 等' : ''}。</span>}
        <span>此检查仅为结构提示，不会把 JSON 变成真实授权，也不会解锁或发送请求。</span>
      </div>

      {err && <div className="errmsg">{err}
        {onFallbackMock && <button className="btn ghost" style={{ marginLeft: 10 }} onClick={onFallbackMock}>切换到本地 Mock 演示</button>}
      </div>}
      <button className="btn danger wide" onClick={confirm} disabled={loadingReal}>🔓 解锁实弹模式</button>
    </div>
  )
}
