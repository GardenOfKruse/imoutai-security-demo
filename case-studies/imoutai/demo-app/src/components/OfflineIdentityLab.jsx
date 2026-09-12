import { useMemo, useState } from 'react'
import { buildOfflineHeadmap, buildOfflineResearchTrace, deriveOfflineResearchProfile, OBSERVED_DEVICE_TUPLE } from '../lib/offlineIdentity.js'

function short(value) {
  const s = String(value || '')
  return s.length > 32 ? `${s.slice(0, 16)}…${s.slice(-10)}` : s
}

export default function OfflineIdentityLab({ onBack, onStartMock }) {
  const [seed, setSeed] = useState('training-fixture-001')
  const [mobile, setMobile] = useState('10086')
  const [timestamp, setTimestamp] = useState('1789140362120')
  const profile = useMemo(() => deriveOfflineResearchProfile(seed), [seed])
  const trace = useMemo(() => buildOfflineResearchTrace(profile, mobile, timestamp), [profile, mobile, timestamp])

  const downloadFixture = () => {
    const payload = buildOfflineHeadmap(profile)
    const blob = new Blob([JSON.stringify(payload, null, 2)], { type: 'application/json' })
    const url = URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = `offline-headermap-${profile.fixtureSeed.replace(/[^a-z0-9_-]/gi, '_')}.json`
    link.click()
    URL.revokeObjectURL(url)
  }

  return (
    <main className="research-layout">
      <section className="card stepcard">
        <div className="btnrow">
          <button className="btn ghost" onClick={onBack}>← 返回模式选择</button>
          <button className="btn ghost" onClick={downloadFixture}>下载离线 HeadMap fixture</button>
          <button className="btn primary" onClick={() => onStartMock(profile)}>进入离线购买演示 →</button>
        </div>
        <h2>🧪 离线算法研究 · 无真机</h2>
        <p className="hint">本页不读取 <code>real-headermap.json</code>，不访问生产接口。它把“已确认公式”“观察到的字段形态”和“尚未验证算法”分栏展示。</p>

        <div className="research-grid">
          <label className="frow col"><span className="flabel">本地 fixture seed</span>
            <input className="ipt" value={seed} onChange={(e) => setSeed(e.target.value || 'training-fixture-001')} /></label>
          <label className="frow col"><span className="flabel">mobile（公式输入）</span>
            <input className="ipt" value={mobile} onChange={(e) => setMobile(e.target.value)} /></label>
          <label className="frow col"><span className="flabel">timestamp（固定可复现）</span>
            <input className="ipt" value={timestamp} onChange={(e) => setTimestamp(e.target.value)} /></label>
        </div>

        <div className="research-section confirmed">
          <h3>✅ 已确认：短信验证码签名</h3>
          <div className="kv"><span>公式</span><code>MD5(deviceKey + mobile + timestamp)</code></div>
          <div className="kv"><span>deviceKey fixture</span><code>{short(trace.input.deviceKey)}</code></div>
          <div className="kv"><span>md5 输出</span><code>{trace.confirmed.md5}</code></div>
          <p className="research-note">这是对已验证客户端公式的离线重放；不等于已经恢复 native deviceKey 的生成算法。</p>
        </div>

        <div className="research-section confirmed">
          <h3>✅ 已确认：RiskStub udid（独立于业务设备码）</h3>
          <div className="kv"><span>因子优先级</span><code>{trace.riskStub.priority}</code></div>
          <div className="kv"><span>命中因子</span><code>android_id = {trace.riskStub.factors.android_id}</code></div>
          <div className="kv"><span>算法输出</span><code>{trace.riskStub.udid}</code></div>
          <div className="kv"><span>client_token（固定时间 fixture）</span><code>{trace.riskStub.clientToken || '请输入有效毫秒时间戳'}</code></div>
          <p className="research-note">JADX 已确认两段 RiskStub 算法：有效设备因子 → UUID v3，以及 b4.java 的时间型 client_token 拼装。二者都可脱离真机复现，但没有证据表明它们就是业务 deviceKey 或 clips_*。</p>
        </div>

        <div className="research-section synthetic">
          <h3>🟡 仅合成：设备 ID / clips_* 演示值</h3>
          <div className="kv"><span>观察到的设备串</span><code>{OBSERVED_DEVICE_TUPLE}</code></div>
          <div className="kv"><span>synthetic deviceKey</span><code>{profile.syntheticDeviceKey}</code></div>
          <div className="kv"><span>设备参数输入</span><code>API {profile.deviceParameters.apiLevel} · {profile.deviceParameters.manufacturer} {profile.deviceParameters.model} · android_id</code></div>
          <div className="kv"><span>synthetic deviceId</span><code>{trace.synthetic.deviceId}</code></div>
          {trace.synthetic.clipsTokens.map((token, index) => <div className="kv" key={token}><span>synthetic clips_{String.fromCharCode(97 + index)}</span><code>{token}</code></div>)}
          <p className="research-note">这些值只用于验证“输入稳定 → 输出稳定”的演示效果；按钮导出的 JSON 也明确标为 offline research，不能声称与真机 ID 相等，也不能解锁 Live。</p>
        </div>

        <div className="research-section unverified">
          <h3>❔ 未验证：禁止自行补协议</h3>
          <ul>{trace.unverified.map((item) => <li key={item}>{item}</li>)}</ul>
          <p className="research-note">下一步只能通过新增 JADX/运行态/抓包证据收敛，不能用合成值替代真机 HeaderMap。</p>
        </div>
      </section>
    </main>
  )
}
