import { useState } from 'react'
import { buildVcodeSign } from '../lib/signature.js'

/** Step 1：手机号 + 短信验证码登录（签名实时复刻演示） */
export default function PhoneLogin({ clean, mobile, setMobile, deviceKey, api, isLive, cachedSession, onLogin }) {
  const [ts, setTs] = useState(null)
  const [sign, setSign] = useState(null)
  const [smsCode, setSmsCode] = useState('')
  const [sent, setSent] = useState(false)
  const [countdown, setCountdown] = useState(0)
  const [err, setErr] = useState('')
  const [serverMsg, setServerMsg] = useState(null)

  const sendCode = async () => {
    if (!/^1\d{10}$/.test(mobile)) { setErr('请输入正确的 11 位手机号'); return }
    setErr('')
    try {
      const r = await api.sendSms(mobile)
      setTs(r.ts); setSign(r.sign); setSent(true); setCountdown(60)
      if (r.resp) setServerMsg({ status: r.resp.status, msg: r.resp.json?.message || r.resp.text?.slice(0, 80), simulated: r.resp.simulated })
    } catch (e) { setErr(String(e.message || e)) }
  }

  const doLogin = async () => {
    if (!isLive && smsCode !== '246810') { setErr('演示环境验证码固定为 246810（模拟短信已送达）'); return }
    if (isLive && smsCode.length !== 6) { setErr('请输入测试手机实际收到的 6 位验证码'); return }
    try {
      const r = await api.login(mobile, smsCode)
      if (r.resp) setServerMsg({ status: r.resp.status, msg: r.resp.json?.message || r.resp.text?.slice(0, 80), simulated: r.resp.simulated })
      const authed = isLive ? Boolean(r.authenticated && r.token) : true
      if (!authed) {
        const status = r.resp?.status
        setErr(status === 200
          ? '服务端返回 HTTP 200，但响应中未找到登录令牌；未进入登录态，也未缓存假 token'
          : `登录被服务端拒绝（HTTP ${status ?? '未知'}）；未缓存未验证的登录态`)
        return
      }
      onLogin(r.token, r.h5Token)
    } catch (e) { setErr(String(e.message || e)) }
  }

  return (
    <div className="card stepcard">
      <h2>📱 短信验证码登录</h2>
      {isLive && cachedSession?.token && cachedSession?.h5Token && (
        <div className="servermsg cached">
          🔑 检测到本地缓存的实弹双会话（已脱敏）——本次无需短信验证，已自动保持登录。
        </div>
      )}
      <div className="form">
        <div className="frow">
          <input className="ipt" value={mobile} maxLength={11}
            onChange={(e) => setMobile(e.target.value.replace(/\D/g, ''))} placeholder="手机号" />
          <button className="btn ghost" disabled={countdown > 0} onClick={sendCode}>
            {countdown > 0 ? `${countdown}s 后重发` : '发送验证码'}
          </button>
        </div>
        <div className="frow">
          <input className="ipt" value={smsCode} maxLength={6} onChange={(e) => setSmsCode(e.target.value.replace(/\D/g, ''))}
            placeholder="短信验证码（演示：246810）" />
          <button className="btn primary" onClick={doLogin}>登 录</button>
        </div>
        {err && <div className="errmsg">{err}</div>}
      </div>

      {serverMsg && (
        <div className={'servermsg' + (serverMsg.simulated ? ' sim' : serverMsg.status === 200 ? ' ok' : ' bad')}>
          服务端响应：HTTP {serverMsg.status}
          {serverMsg.msg ? ` · ${serverMsg.msg}` : ''}
          {serverMsg.simulated && '（非演示时段：未发送真实请求，为模拟的拥挤响应）'}
        </div>
      )}
      {sent && sign && !clean && (
        <div className="wire">
          <div className="wire-h">🔍 协议层实况 —— 你点击"发送验证码"时，{isLive ? '已真实发往生产网关' : '客户端实际会发出'}的请求</div>
          <div className="wire-line"><span className="k">POST</span> <code>/xhr/front/user/register/vcode</code>{isLive && <span className="tag t-red">已真实发送</span>}</div>
          <div className="wire-line">body → <code>{`{ "mobile": "${mobile}", "timestamp": "${ts}", "md5": "${sign.md5}" }`}</code></div>
          <div className="wire-line">签名明文 → <code>{sign.plain}</code></div>
          <div className="wire-warn">⚠️ 这个 md5 是<b>本页 JS 现场算出来的</b>{isLive ? '，且已作为真实参数发往生产网关' : '——与真实 App 内 native/Java 层计算的结果逐字节一致'}。签名算法已无秘密可言。</div>
        </div>
      )}
    </div>
  )
}
