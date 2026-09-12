import { useEffect, useMemo, useRef, useState } from 'react'

const CHARS = 'ABCDEFGHJKLMNPQRSTUVWXYZ23456789'
const WORDS = ['强', '安', '全', '酒', '香', '茅', '台']
const TYPE_ORDER = ['text', 'slider', 'click']
const TYPE_NAME = { text: '字符/文字验证码', slider: '滑块拼图验证码', click: '点选图片验证码' }

function fixtureText(round) {
  return Array.from({ length: 4 }, (_, i) => CHARS[(round * 7 + i * 11 + 3) % CHARS.length]).join('')
}

function ResultBadge({ phase, message }) {
  return <div className={`captcha-state ${phase}`} role="status">{message}</div>
}

/* 本地 fixture：可重复校验，不代表 OCR，也不还原原生识别算法。 */
function TextCaptcha({ round, onResult }) {
  const code = useMemo(() => fixtureText(round), [round])
  const [input, setInput] = useState('')
  const [phase, setPhase] = useState('ready')
  const canvasRef = useRef(null)

  useEffect(() => {
    const canvas = canvasRef.current
    if (!canvas) return undefined
    const ctx = canvas.getContext('2d')
    const { width: w, height: h } = canvas
    ctx.fillStyle = '#0e1628'
    ctx.fillRect(0, 0, w, h)
    for (let i = 0; i < 6; i++) {
      ctx.strokeStyle = `rgba(99,140,255,${0.15 + ((round + i) % 4) * 0.06})`
      ctx.beginPath()
      ctx.moveTo((round * 17 + i * 29) % w, (round * 11 + i * 13) % h)
      ctx.lineTo((round * 23 + i * 41) % w, (round * 7 + i * 19) % h)
      ctx.stroke()
    }
    for (let i = 0; i < 40; i++) {
      ctx.fillStyle = `rgba(148,163,184,${0.12 + ((round + i) % 4) * 0.08})`
      ctx.fillRect((round * 13 + i * 31) % w, (round * 5 + i * 17) % h, 2, 2)
    }
    code.split('').forEach((ch, i) => {
      ctx.save()
      ctx.translate(28 + i * 36, h / 2 + 10)
      ctx.rotate((((round + i) % 5) - 2) * 0.06)
      ctx.font = `bold ${26 + ((round + i) % 3)}px monospace`
      ctx.fillStyle = ['#93c5fd', '#6ee7b7', '#fbbf24', '#f0abfc'][i % 4]
      ctx.fillText(ch, -10, 0)
      ctx.restore()
    })
  }, [code, round])

  const verify = (value) => {
    if (phase === 'passed') return
    const ok = String(value).trim().toUpperCase() === code
    setPhase(ok ? 'passed' : 'failed')
    onResult({ ok, reason: ok ? '本地 fixture 字符匹配' : '字符与本地 fixture 不匹配' })
  }

  return (
    <div>
      <div className="cap-label">🅰️ 类型：字符/文字验证码（本地 fixture）</div>
      <div className="captcha-img-wrap">
        <canvas ref={canvasRef} width="190" height="64" className="captcha-img" />
        <div className="captcha-fixture">本地参考答案：<code>{code}</code></div>
      </div>
      <div className="captcha-controls">
        <input value={input} maxLength={4} onChange={(e) => setInput(e.target.value)} placeholder="输入 4 位字符" />
        <button className="btn ghost" onClick={() => verify(input)}>校验</button>
        <button className="btn ghost" onClick={() => { setInput(code); verify(code) }}>使用 fixture 完成</button>
      </div>
      <ResultBadge phase={phase} message={phase === 'passed' ? '✅ 字符校验成功（本地 fixture）' : phase === 'failed' ? '❌ 字符校验失败，可修改后重试' : '等待输入或使用本地 fixture'} />
    </div>
  )
}

/* 本地 fixture：滑块位置可手动校验，不代表缺口检测或轨迹仿真。 */
function SliderCaptcha({ round, onResult }) {
  const W = 260
  const pieceWidth = 34
  const gap = 48 + (round % 4) * 34
  const [x, setX] = useState(0)
  const [phase, setPhase] = useState('ready')

  const verify = (value = x) => {
    if (phase === 'passed') return
    const ok = Math.abs(Number(value) - gap) <= 3
    setPhase(ok ? 'passed' : 'failed')
    onResult({ ok, reason: ok ? '本地 fixture 位置匹配' : `位置偏差过大：目标 ${gap}px` })
  }

  return (
    <div>
      <div className="cap-label">🧩 类型：滑块拼图验证码（本地位置 fixture）</div>
      <div className="slider-bg">
        <div className="slider-gap" style={{ left: gap + 'px' }} />
        <div className="slider-piece" style={{ left: x + 'px' }} />
      </div>
      <div className="slider-track">
        <div className="slider-handle" style={{ left: (x / (W - pieceWidth)) * 100 + '%' }}><span>{phase === 'passed' ? '✓' : '»'}</span></div>
      </div>
      <input className="captcha-range" type="range" min="0" max={W - pieceWidth} value={x} onChange={(e) => { setX(Number(e.target.value)); setPhase('ready') }} aria-label="滑块位置" />
      <div className="captcha-controls">
        <button className="btn ghost" onClick={() => verify()}>校验当前位置</button>
        <button className="btn ghost" onClick={() => { setX(gap); verify(gap) }}>使用 fixture 完成</button>
      </div>
      <ResultBadge phase={phase} message={phase === 'passed' ? '✅ 滑块校验成功（本地 fixture）' : phase === 'failed' ? '❌ 滑块校验失败，可调整后重试' : `拖动滑块，目标位置约 ${gap}px`} />
    </div>
  )
}

/* 本地 fixture：显式点击顺序，不代表目标检测或自动点击算法。 */
function ClickCaptcha({ round, onResult }) {
  const items = useMemo(() => {
    const target = [0, 1, 2].map((offset) => WORDS[(round + offset) % WORDS.length])
    const distractors = [WORDS[(round + 4) % WORDS.length], WORDS[(round + 5) % WORDS.length]]
    return [...target, ...distractors].map((label, i) => ({
      label,
      target: i < target.length,
      x: 14 + (i % 3) * 92 + ((round + i) % 2) * 10,
      y: 22 + Math.floor(i / 3) * 54,
      rot: (((round + i) % 5) - 2) * 0.08,
    }))
  }, [round])
  const [selected, setSelected] = useState([])
  const [phase, setPhase] = useState('ready')
  const target = items.filter((item) => item.target)

  const fail = (reason) => {
    setPhase('failed')
    onResult({ ok: false, reason })
  }

  const clickItem = (index) => {
    if (phase === 'passed') return
    const expected = target[selected.length]?.label
    if (items[index].label !== expected) {
      fail(`顺序错误：第 ${selected.length + 1} 个应点击“${expected}”`)
      return
    }
    const next = [...selected, index]
    setSelected(next)
    if (next.length === target.length) {
      setPhase('passed')
      onResult({ ok: true, reason: '本地 fixture 顺序匹配' })
    }
  }

  const useFixture = () => {
    const indexes = target.map((item) => items.indexOf(item))
    setSelected(indexes)
    setPhase('passed')
    onResult({ ok: true, reason: '本地 fixture 顺序匹配' })
  }

  return (
    <div>
      <div className="cap-label">🖱️ 类型：点选图片验证码（本地顺序 fixture）</div>
      <div className="click-img">
        <div className="click-noise" />
        {items.map((item, i) => (
          <button key={i} className="click-word" style={{ left: item.x + 'px', top: item.y + 'px', transform: `rotate(${item.rot}rad)` }} onClick={() => clickItem(i)}>{item.label}</button>
        ))}
        {selected.map((index, i) => (
          <span key={'m' + i} className="click-marker" style={{ left: items[index].x - 8 + 'px', top: items[index].y - 8 + 'px' }}>{i + 1}</span>
        ))}
      </div>
      <div className="click-target">请依次点击：<b>{target.map((item) => item.label).join(' → ')}</b></div>
      <div className="captcha-controls">
        <button className="btn ghost" onClick={() => { setSelected([]); setPhase('ready') }}>清空重试</button>
        <button className="btn ghost" onClick={useFixture}>使用 fixture 完成</button>
      </div>
      <ResultBadge phase={phase} message={phase === 'passed' ? '✅ 点选校验成功（本地 fixture）' : phase === 'failed' ? '❌ 点选顺序失败，可清空后重试' : '等待按顺序点选'} />
    </div>
  )
}

export default function CaptchaVerify({ clean, order, isLive, onPass, onComplete, onBack }) {
  const [round, setRound] = useState(0)
  const [solvedCount, setSolvedCount] = useState(0)
  const [solvedTypes, setSolvedTypes] = useState([])
  const [captchaState, setCaptchaState] = useState('ready')
  const [message, setMessage] = useState('等待本地验证码校验')
  const [submitState, setSubmitState] = useState('idle')
  const alive = useRef(true)
  const handledRound = useRef(null)
  const generation = useRef(0)
  const type = useMemo(() => TYPE_ORDER[round % TYPE_ORDER.length], [round])

  useEffect(() => () => { alive.current = false }, [])

  const refresh = () => {
    generation.current += 1
    setRound((r) => r + 1)
    setCaptchaState('ready')
    setSubmitState('idle')
    setMessage('已刷新，等待新一轮本地 fixture 校验')
  }

  const handleResult = async ({ ok, reason }) => {
    setCaptchaState(ok ? 'passed' : 'failed')
    setMessage(reason)
    if (!ok) return
    if (handledRound.current === round) return
    handledRound.current = round
    const currentGeneration = generation.current
    setSolvedCount((n) => n + 1)
    setSolvedTypes((items) => items.includes(type) ? items : [...items, type])
    setSubmitState('submitting')
    try {
      await onPass({ type, round, source: 'local-fixture' })
      if (alive.current && generation.current === currentGeneration) {
        setSubmitState('submitted')
        setMessage('验证码已通过，订单提交已完成；支付接口未调用')
        onComplete?.()
      }
    } catch (error) {
      if (alive.current && generation.current === currentGeneration) {
        setSubmitState('failed')
        setMessage(`验证码已通过，但订单提交失败：${String(error?.message || error)}`)
      }
    }
  }

  if (isLive) {
    return (
      <div className="card stepcard">
        <h2>🔐 提交订单 · 原生风控验证码</h2>
        <p className="hint">订单 <code>{order?.orderId || '待服务端返回'}</code> · 金额 <b>¥{order?.amount}</b></p>
        <div className="note">
          Live 仅保留“验证码通过后调用订单提交、止步支付”的调用边界。原生证据目前只确认 <code>com.netease.nis.captcha.CaptchaWebView</code>；真实 challenge、校验回调和刷新协议均未验证，因此这里不生成 Mock challenge，也不把本地 fixture 当作 Live 验证结果。
        </div>
        {onBack && <button className="btn ghost" onClick={onBack}>← 返回商品选择</button>}
      </div>
    )
  }

  return (
    <div className="card stepcard">
      <h2>🔐 提交订单 · 本地风控验证码（{TYPE_NAME[type]}）</h2>
      <p className="hint">
        订单 <code>{order?.orderId}</code> · 金额 <b>¥{order?.amount}</b>。本地 fixture 按“字符 → 滑块 → 点选”轮换；这里演示的是状态机和可重复校验，不是 OCR、目标检测、轨迹仿真或原生算法还原。
      </p>

      <div className="captcha-box">
        {type === 'text' && <TextCaptcha key={round} round={round} onResult={handleResult} />}
        {type === 'slider' && <SliderCaptcha key={round} round={round} onResult={handleResult} />}
        {type === 'click' && <ClickCaptcha key={round} round={round} onResult={handleResult} />}
      </div>

      <ResultBadge phase={submitState === 'failed' ? 'failed' : submitState === 'submitted' ? 'passed' : captchaState} message={submitState === 'submitting' ? '验证码已通过，正在提交本地 Mock 订单…' : message} />
      {!clean && <div className="cap-refresh">
        <span className="cap-count">🧪 本地 fixture：已通过 <b>{solvedCount}</b> 张，覆盖 <b>{solvedTypes.length}/3</b> 种</span>
        <button className="btn ghost" onClick={refresh}>↻ 刷新验证码</button>
      </div>}
      {!clean && <div className="note" style={{ marginTop: 12 }}>
        📌 每一轮都有明确的本地参考答案、失败回写和刷新边界；成功后才调用订单提交 Mock。Live 的真实提交仍要求已验证的订单 body，支付接口永远不在此链路。
      </div>}
      <button className="btn primary wide" disabled>
        {submitState === 'submitting' ? '验证码通过，订单提交中…' : '先完成本地验证码校验'}
      </button>
    </div>
  )
}
