import { useMemo, useState } from 'react'
import { buildPayLink } from '../lib/payLinks.js'

/** Step 6：生成支付链接（🚫 仅拼接展示，禁止真实调用） */
export default function PayLinkResult({ clean, order, address }) {
  const [chId, setChId] = useState('alipay')
  const link = useMemo(() => buildPayLink(chId, order || { orderId: 'MO-DEMO', amount: '0.00', subject: '演示' }), [chId, order])
  const [copied, setCopied] = useState(false)

  const copy = async () => {
    try { await navigator.clipboard.writeText(link.scheme); setCopied(true); setTimeout(() => setCopied(false), 1500) } catch { /* 演示环境忽略 */ }
  }

  return (
    <div className="card stepcard">
      <h2>🔗 支付链接生成（拼接算法还原结果）</h2>
      <p className="hint">订单 <code>{order?.orderId}</code> · ¥{order?.amount} · 收货人 <code>{address?.name || '—'}</code></p>

      <div className="warnbox">{clean
        ? <>🚫 支付链接为本地拼接展示，绝不真实调用（止步支付）。</>
        : <>🚫 <b>红线声明：</b>以下链接为<b>本地拼接的协议形态展示</b>，参数均为演示值。<b>绝不真实调用</b>（不打开、不请求、不唤起）——防止破坏线上账务信息。此处展示的唯一目的：让开发者看到"客户端拼参"的风险面。</>}</div>

      <div className="chlist small">
        {['alipay', 'wechat', 'unionpay'].map((id) => (
          <div key={id} className={'chitem' + (chId === id ? ' sel' : '')} onClick={() => setChId(id)}>
            <span>{id === 'alipay' ? '🔵 支付宝' : id === 'wechat' ? '🟢 微信支付' : '🔴 银联'}</span>
          </div>
        ))}
      </div>

      <div className="wire">
        {!clean && <div className="wire-h">拼接结果（scheme 唤起形态）</div>}
        <div className="wire-line mono">{link.scheme}</div>
        {!clean && <div className="wire-h">H5 网关形态</div>}
        <div className="wire-line mono">{link.h5}</div>
        {!clean && <div className="wire-warn">📌 {link.note}</div>}
      </div>

      {!clean && <div className="btnrow">
        <button className="btn ghost" onClick={copy}>{copied ? '✓ 已复制' : '复制 scheme（仅文本）'}</button>
      </div>}

      {!clean && <div className="sugg">
        <div className="attack-dev-h">🛡️ 用户安全支付建议（本节产出目标）</div>
        <ul>
          <li>支付参数（金额/商品/订单号）必须由服务端签名下发，客户端只透传——防止改价与伪造订单</li>
          <li>支付回调必须服务端验签 + 异步对账，杜绝"回调伪造确认"</li>
          <li>scheme/深层链接增加来源签名与时效，防钓鱼应用仿冒拼接</li>
          <li>对用户：只在官方 App 内完成支付，警惕外部跳转的同构支付链接</li>
        </ul>
      </div>}
    </div>
  )
}
