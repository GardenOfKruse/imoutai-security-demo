import { useState } from 'react'
import { CHANNELS, buildPayLink } from '../lib/payLinks.js'
import { recordApi } from '../lib/mockApi.js'

/** Step 5：选择支付方式 */
export default function PaymentSelect({ clean, isLive, onBackToAddress, onNext }) {
  const [ch, setCh] = useState(null)
  const choose = (c) => {
    setCh(c)
    recordApi({
      api: '/xhr/front/trade/order/pay',
      note: '支付渠道选择仅是"壳"——真正风险在支付参数由谁拼接（下一步展示）',
      body: { channel: c.id },
    })
  }
  return (
    <div className="card stepcard">
      <h2>💳 选择支付方式</h2>
      {!clean && <p className="hint">选择渠道后演示页将<b>本地拼接</b>该渠道的支付链接形态——仅展示，绝不发起调用。{isLive && '🔴 实弹模式同样不调用支付（止步支付页是硬红线）。'}</p>}
      {!clean && onBackToAddress && (
        <div className="skipaddr">
          🌙 深夜/系统关单演示路径：业务系统关闭时订单走不完，可跳过地址直达支付链接。
          <button className="btn ghost" onClick={onBackToAddress}>补填收货地址（完整流程）</button>
        </div>
      )}
      <div className="chlist">
        {CHANNELS.map((c) => (
          <div key={c.id} className={'chitem' + (ch?.id === c.id ? ' sel' : '')} onClick={() => choose(c)}>
            <span className="chicon" style={{ color: c.color }}>{c.icon}</span>
            <span>{c.name}</span>
            {ch?.id === c.id && <span className="tag t-ok">已选择</span>}
          </div>
        ))}
      </div>
      <button className="btn primary wide" disabled={!ch} onClick={onNext}>生成支付链接 →</button>
    </div>
  )
}
