import { useEffect, useState } from 'react'
import { PRODUCTS } from '../lib/mockApi.js'

/** Step 2：选购商品 + 数量（实弹模式下额外拉取真实 purchaseInfoV2） */
export default function ProductSelect({ clean, cart, setCart, api, isLive, onSubmit }) {
  const [liveResp, setLiveResp] = useState(null)
  const [liveErr, setLiveErr] = useState('')
  const [composing, setComposing] = useState(false)
  useEffect(() => {
    if (!isLive) return
    api.purchaseInfo({}).then((r) => {
      setLiveResp(r)
      if (r.status !== 200) setLiveErr(`purchaseInfoV2 返回 HTTP ${r.status}`)
    }).catch((e) => setLiveErr(String(e.message || e)))
  }, [isLive, api])
  const [err, setErr] = useState('')
  const toggle = (p) => {
    setErr('')
    setCart((c) => (c.find((x) => x.product.id === p.id) ? c.filter((x) => x.product.id !== p.id) : [...c, { product: p, qty: 1 }]))
  }
  const setQty = (id, d) =>
    setCart((c) => c.map((x) => (x.product.id === id ? { ...x, qty: Math.max(1, Math.min(6, x.qty + d)) } : x)))
  const total = cart.reduce((s, x) => s + x.product.price * x.qty, 0)

  const submit = async () => {
    if (!cart.length) { setErr('请先勾选商品'); return }
    const total2 = cart.reduce((s, x) => s + x.product.price * x.qty, 0)
    setErr('')
    setComposing(true)
    try {
      if (isLive) {
        // 真实 compose body 尚未有运行态证据；只建立 UI 草稿，不发送猜测请求。
        onSubmit({ orderId: 'LIVE_PENDING', amount: (total2 / 100).toFixed(2), subject: cart.map((x) => x.product.name).join(' / ') }, cart, null)
        return
      }
      const composeResult = await api.composeOrder(cart)
      onSubmit(composeResult.order, cart, composeResult)
    } catch (e) {
      setErr(String(e.message || e))
    } finally {
      setComposing(false)
    }
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
      <div className="cartbar">
        <span>合计：<b>¥{(total / 100).toFixed(2)}</b></span>
        <button className="btn primary" disabled={composing || (isLive && (!liveResp || liveResp.status !== 200))} onClick={submit}>{composing ? '准备订单草稿…' : '进入验证码'}</button>
      </div>
      {err && <div className="errmsg">{err}</div>}
    </div>
  )
}
