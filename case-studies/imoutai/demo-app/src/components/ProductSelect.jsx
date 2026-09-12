import { useEffect, useState } from 'react'
import { PRODUCTS } from '../lib/mockApi.js'

/** Step 2：选购商品 + 数量（实弹模式下额外拉取真实 purchaseInfoV2） */
export default function ProductSelect({ clean, cart, setCart, api, isLive, onSubmit }) {
  const [liveResp, setLiveResp] = useState(null)
  const [liveErr, setLiveErr] = useState('')
  useEffect(() => {
    if (!isLive) return
    api.purchaseInfo({}).then((r) => {
      setLiveResp(r)
      if (r.status !== 200) setLiveErr(`purchaseInfoV2 返回 HTTP ${r.status}`)
    }).catch((e) => setLiveErr(String(e.message || e)))
  }, [isLive, api])
  const [err, setErr] = useState('')
  const [serverMsg, setServerMsg] = useState(null)
  const toggle = (p) => {
    setErr('')
    setCart((c) => (c.find((x) => x.product.id === p.id) ? c.filter((x) => x.product.id !== p.id) : [...c, { product: p, qty: 1 }]))
  }
  const setQty = (id, d) =>
    setCart((c) => c.map((x) => (x.product.id === id ? { ...x, qty: Math.max(1, Math.min(6, x.qty + d)) } : x)))
  const total = cart.reduce((s, x) => s + x.product.price * x.qty, 0)

  const submit = async () => {
    if (!cart.length) { setErr('请先勾选商品'); return }
    let order = null
    try {
      const r = await api.submitOrder(cart)
      if (r.resp) setServerMsg({ status: r.resp.status, msg: r.resp.json?.message || '', simulated: r.resp.simulated })
      order = r.order
    } catch (e) {
      setServerMsg({ status: 'ERR', msg: String(e.message || e), simulated: !isLive })
      // 实弹订单 body 尚未有真实抓包基准时必须停在这里，不能用演示订单掩盖未验证请求。
      if (isLive) return
      order = null
    }
    if (!order) {
      const total2 = cart.reduce((s, x) => s + x.product.price * x.qty, 0)
      order = { orderId: 'MO' + Date.now(), amount: (total2 / 100).toFixed(2), subject: cart.map((x) => x.product.name).join(' / ') }
    }
    onSubmit(order)
  }

  return (
    <div className="card stepcard">
      <h2>🛒 选购商品</h2>
      <div className="plist">
        {PRODUCTS.map((p) => {
          const inCart = cart.find((x) => x.product.id === p.id)
          return (
            <div key={p.id} className={'pitem' + (inCart ? ' sel' : '')} onClick={() => toggle(p)}>
              <div className="pcheck">{inCart ? '✓' : ''}</div>
              <div className="pbody">
                <div className="pname">{p.name} <span className="ptag">{p.tag}</span></div>
                <div className="pmeta">{p.stock}</div>
              </div>
              <div className="pprice">¥{p.price}</div>
              {inCart && (
                <div className="qty" onClick={(e) => e.stopPropagation()}>
                  <button onClick={() => setQty(p.id, -1)}>−</button>
                  <span>{inCart.qty}</span>
                  <button onClick={() => setQty(p.id, +1)}>＋</button>
                </div>
              )}
            </div>
          )
        })}
      </div>
      {isLive && !clean && (
        <div className="wire">
          <div className="wire-h">🔴 实弹：purchaseInfoV2 真实响应（脱敏预览）</div>
          {liveErr && <div className="wire-warn">请求异常：{liveErr}</div>}
          <div className="wire-line mono">{liveResp ? JSON.stringify(liveResp).slice(0, 400) : '请求中…'}</div>
        </div>
      )}
      {serverMsg && (
        <div className={'servermsg' + (serverMsg.simulated ? ' sim' : serverMsg.status === 200 ? ' ok' : ' bad')}>
          服务端响应：HTTP {serverMsg.status}
          {serverMsg.msg ? ` · ${serverMsg.msg}` : ''}
          {serverMsg.simulated && '（非演示时段 · 未发送真实请求）'}
        </div>
      )}
      <div className="cartbar">
        <span>合计：<b>¥{(total / 100).toFixed(2)}</b></span>
        <button className="btn primary" disabled={isLive && (!liveResp || liveResp.status !== 200)} onClick={submit}>提交订单</button>
      </div>
      {err && <div className="errmsg">{err}</div>}
    </div>
  )
}
