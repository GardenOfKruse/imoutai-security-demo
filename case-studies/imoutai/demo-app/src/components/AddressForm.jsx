import { useState } from 'react'

const FIELDS = [
  { k: 'name', label: '收货人' },
  { k: 'phone', label: '联系电话' },
  { k: 'region', label: '省市区' },
  { k: 'detail', label: '详细地址' },
]

/** Step 4：填写收货地址 */
export default function AddressForm({ clean, address, setAddress, onNext }) {
  const [form, setForm] = useState(address || { name: '演示用户', phone: '138****1234', region: '贵州省 贵阳市 观山湖区', detail: '××街道××号（演示数据）' })
  const [err, setErr] = useState('')
  const set = (k) => (e) => setForm((f) => ({ ...f, [k]: e.target.value }))
  const next = () => {
    if (Object.values(form).some((v) => !v.trim())) { setErr('请完整填写地址信息'); return }
    setErr(''); setAddress(form); onNext()
  }
  return (
    <div className="card stepcard">
      <h2>🏠 填写收货地址</h2>
      {!clean && <p className="hint">已预填演示数据，可直接下一步。</p>}      <div className="form">
        {FIELDS.map((f) => (
          <div className="frow" key={f.k}>
            <label className="flabel">{f.label}</label>
            <input className="ipt" value={form[f.k]} onChange={set(f.k)} />
          </div>
        ))}
        {err && <div className="errmsg">{err}</div>}
        <button className="btn primary wide" onClick={next}>确认地址，去支付 →</button>
      </div>
    </div>
  )
}
