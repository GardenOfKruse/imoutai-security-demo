import { useEffect, useMemo, useState } from 'react'
import { windowStatus } from '../lib/realApi.js'
import { deriveDeviceKey } from '../lib/deviceKey.js'
import { md5 } from '../lib/signature.js'

/**
 * 默认 HeaderMap 档案自动生成：
 * - deviceKey/MT-Device-ID：由当前环境指纹派生（模拟 native 设备绑定）
 * - 字段名采用公开协议已知的头名骨架（现场如拿到测试设备真实抓包，直接粘贴覆盖即可校准）
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
      note: '本 _meta 块仅存在于客户端档案，不会进入任何真实请求；请求头已按真实 App 形态生成，不含任何模拟标记',
    },
  }
}

/** 实弹模式授权门：三确认 + 窗口校验 + HeaderMap 档案（自动生成/可粘贴覆盖） */
export default function ModeGate({ onConfirm }) {
  const initial = useMemo(() => JSON.stringify(generateDefaultProfile(), null, 2), [])
  const [c1, setC1] = useState(false)
  const [c2, setC2] = useState(false)
  const [c3, setC3] = useState(false)
  const [profileText, setProfileText] = useState(initial)
  const [err, setErr] = useState('')
  const ws = windowStatus()

  const regenerate = () => setProfileText(JSON.stringify(generateDefaultProfile(), null, 2))
  const loadReal = async () => {
    try {
      const r = await fetch('./real-headermap.json')
      if (!r.ok) throw new Error('HTTP ' + r.status)
      const j = await r.json()
      setProfileText(JSON.stringify(j, null, 2))
      setErr('')
    } catch (e) { setErr('真实档案加载失败：' + e.message + '（确认 real-headermap.json 随包部署）') }
  }

  const confirm = () => {
    if (ws.level === 'peak') { setErr(ws.label); return }
    if (!(c1 && c2 && c3)) { setErr('三项确认未勾选完整'); return }
    let profile = null
    try {
      profile = JSON.parse(profileText)
      if (!profile.headers || typeof profile.headers !== 'object') throw new Error('缺少 headers 字段')
      if (!profile.deviceKey) throw new Error('缺少 deviceKey 字段')
    } catch (e) {
      setErr('档案解析失败：' + e.message + '（可点击"重新生成默认档案"恢复）')
      return
    }
    onConfirm(profile)
  }

  return (
    <div className="card stepcard">
      <h2>🔴 实弹模式 · 授权门</h2>
      <p className="hint">实弹模式会向生产系统发送<b>真实请求</b>（真实登录、真实下单，止步支付）。仅限本次授权测试的单轮 runbook 执行时使用；日常演示请用「演示模式」。</p>

      <div className={'gate-window' + (ws.level === 'peak' ? ' bad' : ws.level === 'buffer' ? ' warn' : '')}>{ws.label}<span className="gate-window-sub">允许窗口：20:00–次日01:00 / 07:00–18:00 · 高峰禁发：06:00–06:15（系统申购高峰）· 缓冲时段需对接人知情</span></div>

      <div className="gchecks">
        <label><input type="checkbox" checked={c1} onChange={(e) => setC1(e.target.checked)} />
          确认当前为<b>客户授权的本次单轮测试执行</b>（仅此一轮，不重跑）</label>
        <label><input type="checkbox" checked={c2} onChange={(e) => setC2(e.target.checked)} />
          确认使用<b>客户提供的测试账号</b>，验证码由测试手机人工接收；下单<b>止步支付页</b></label>
        <label><input type="checkbox" checked={c3} onChange={(e) => setC3(e.target.checked)} />
          确认客户对接人<b>已知情并在场</b>，知晓本次真实下单演示</label>
      </div>

      <div className="frow col">
        <label className="flabel">
          HeaderMap 档案（已自动生成默认档案，每次进入实弹模式都会重新生成；如现场有测试设备真实抓包 JSON 可直接粘贴覆盖）
        </label>
        <textarea className="ipt area" rows={10} value={profileText} onChange={(e) => setProfileText(e.target.value)} />
        <div style={{ display: 'flex', gap: 10 }}>
          <button className="btn ghost" style={{ alignSelf: 'flex-start' }} onClick={regenerate}>↺ 重新生成默认档案</button>
          <button className="btn primary" style={{ alignSelf: 'flex-start' }} onClick={loadReal}>📂 加载真实档案（mitm 抓包）</button>
        </div>
      </div>

      <div className="note">📌 档案说明：请求头按<b>真实 App 形态</b>生成，不含任何模拟标记（实弹全真实原则，见 AGENTS.md）；
        <code>deviceKey / MT-Device-ID</code> 由本机环境指纹派生，<code>MT-Token</code> 留空（登录后由服务端下发并自动注入）。
        下方 <code>_meta</code> 块仅存在客户端，绝不随请求发出。</div>

      {err && <div className="errmsg">{err}</div>}
      <button className="btn danger wide" onClick={confirm}>🔓 解锁实弹模式</button>
    </div>
  )
}
