import { useEffect, useMemo, useRef, useState } from 'react'

const CHARS = 'ABCDEFGHJKLMNPQRSTUVWXYZ23456789'
const WORDS = ['强', '安', '全', '酒', '香', '茅', '台']
const TYPE_ORDER = ['text', 'slider', 'click']
const pick = (arr, n) => Array.from({ length: n }, () => arr[Math.floor(Math.random() * arr.length)].valueOf())
const pickStr = (n) => Array.from({ length: n }, () => CHARS[Math.floor(Math.random() * CHARS.length)]).join('')

/* ---------------- 类型 1：文字/字母数字验证码（OCR 自动识别演示） ---------------- */
function TextCaptcha({ onSolved }) {
  const code = useMemo(() => pickStr(4), [])
  const [progress, setProgress] = useState(0)
  const [phase, setPhase] = useState('showing')
  const canvasRef = useRef(null)

  useEffect(() => {
    const c = canvasRef.current, ctx = c.getContext('2d')
    const { width: w, height: h } = c
    ctx.fillStyle = '#0e1628'; ctx.fillRect(0, 0, w, h)
    for (let i = 0; i < 6; i++) {
      ctx.strokeStyle = `rgba(99,140,255,${0.15 + Math.random() * 0.25})`
      ctx.beginPath(); ctx.moveTo(Math.random() * w, Math.random() * h); ctx.lineTo(Math.random() * w, Math.random() * h); ctx.stroke()
    }
    for (let i = 0; i < 40; i++) {
      ctx.fillStyle = `rgba(148,163,184,${Math.random() * 0.5})`
      ctx.fillRect(Math.random() * w, Math.random() * h, 2, 2)
    }
    code.split('').forEach((ch, i) => {
      ctx.save(); ctx.translate(28 + i * 36, h / 2 + 10); ctx.rotate((Math.random() - 0.5) * 0.5)
      ctx.font = `bold ${26 + Math.random() * 6}px monospace`
      ctx.fillStyle = ['#93c5fd', '#6ee7b7', '#fbbf24', '#f0abfc'][i % 4]
      ctx.fillText(ch, -10, 0); ctx.restore()
    })
  }, [code])

  useEffect(() => {
    const t = setInterval(() => setProgress((p) => {
      const next = p + 3.3
      if (next >= 100) { clearInterval(t); setPhase('ocr'); setTimeout(() => { setPhase('done'); onSolved(code) }, 600) }
      return Math.min(100, next)
    }), 100)
    return () => clearInterval(t)
  }, [code])

  return (
    <div>
      <div className="cap-label">🅰️ 类型：字符/文字验证码（canvas 扭曲 + 噪点）</div>
      <div className="captcha-img-wrap">
        <canvas ref={canvasRef} width={190} height={64} className="captcha-img" />
        {phase === 'ocr' && <div className="captcha-ocr">OCR 识别中…</div>}
        {phase === 'done' && <div className="captcha-solved">识别结果：{code}</div>}
      </div>
      <div className="ocr-bar"><div style={{ width: progress + '%' }} /></div>
      <div className="ocr-status">🤖 攻击脚本：截图 → OCR 引擎 → 回填（模拟自动识别）</div>
    </div>
  )
}

/* ---------------- 类型 2：滑块拼图验证码（缺口检测 + 轨迹仿真） ---------------- */
function SliderCaptcha({ onSolved }) {
  const [x, setX] = useState(0)
  const [phase, setPhase] = useState('showing')
  const gap = 62
  const W = 260

  useEffect(() => {
    let t = 0
    const timer = setInterval(() => {
      t += 0.03
      const ease = 1 - Math.pow(1 - Math.min(1, t), 3)
      const jitter = Math.sin(t * 40) * 1.2 * (1 - Math.min(1, t))
      setX(Math.min(gap, ease * gap + jitter))
      if (t >= 1) {
        clearInterval(timer); setX(gap); setPhase('verify')
        setTimeout(() => { setPhase('done'); onSolved() }, 600)
      }
    }, 30)
    return () => clearInterval(timer)
  }, [])

  return (
    <div>
      <div className="cap-label">🧩 类型：滑块拼图验证码（缺口检测 + 轨迹仿真）</div>
      <div className="slider-bg">
        <div className="slider-gap" style={{ left: gap + 'px' }} />
        <div className="slider-piece" style={{ left: x + 'px' }} />
      </div>
      <div className="slider-track">
        <div className="slider-handle" style={{ left: (x / W) * 100 + '%' }}>
          <span>{phase === 'done' ? '✓' : '»'}</span>
        </div>
      </div>
      <div className="ocr-status">
        {phase === 'showing' && '🤖 攻击脚本：边缘检测定位缺口 px → 生成带抖动的拟人拖动轨迹…'}
        {phase === 'verify' && '🤖 拖动到位，提交校验…'}
        {phase === 'done' && '✅ 滑块校验通过（自动）'}
      </div>
    </div>
  )
}

/* ---------------- 类型 3：点选图片验证码（目标检测 + 顺序点击） ---------------- */
function ClickCaptcha({ onSolved }) {
  const words = useMemo(() => pick(WORDS, 3), [])
  const pos = useMemo(() => words.map((_, i) => ({
    x: 14 + i * 78 + Math.random() * 30, y: 24 + Math.random() * 60, rot: (Math.random() - 0.5) * 0.6,
  })), [words])
  const [hits, setHits] = useState([])
  const [phase, setPhase] = useState('showing')

  useEffect(() => {
    const timers = words.map((_, i) => setTimeout(() => {
      setHits((h) => [...h, pos[i]])
      if (i === words.length - 1) {
        setPhase('verify')
        setTimeout(() => { setPhase('done'); onSolved() }, 600)
      }
    }, 900 + i * 1000))
    return () => timers.forEach(clearTimeout)
  }, [])

  return (
    <div>
      <div className="cap-label">🖱️ 类型：点选图片验证码（语序点选 · 目标检测演示）</div>
      <div className="click-img">
        <div className="click-noise" />
        {words.map((w, i) => (
          <span key={i} className="click-word"
            style={{ left: pos[i].x + 'px', top: pos[i].y + 'px', transform: `rotate(${pos[i].rot}rad)` }}>{w}</span>
        ))}
        {hits.map((h, i) => (
          <span key={'m' + i} className="click-marker" style={{ left: h.x - 8 + 'px', top: h.y - 8 + 'px' }}>{i + 1}</span>
        ))}
      </div>
      <div className="click-target">请依次点击：<b>{words.join(' → ')}</b></div>
      <div className="ocr-status">
        {phase === 'showing' && '🤖 攻击脚本：目标检测定位文字坐标 → 按语序生成点击序列…'}
        {phase === 'verify' && '🤖 点击序列提交校验…'}
        {phase === 'done' && '✅ 点选校验通过（自动）'}
      </div>
    </div>
  )
}

/* ---------------- 容器：随机呈现一种验证码 ---------------- */
export default function CaptchaVerify({ clean, order, isLive, onPass }) {
  const [round, setRound] = useState(0)
  const [solvedCount, setSolvedCount] = useState(0)
  const [solvedTypes, setSolvedTypes] = useState([])
  const [solved, setSolved] = useState(false)
  const TYPE_NAME = { text: '字符/文字验证码', slider: '滑块拼图验证码', click: '点选图片验证码' }
  const type = useMemo(() => TYPE_ORDER[round % TYPE_ORDER.length], [round])
  const refresh = () => { setSolved(false); setRound((r) => r + 1) }
  const markSolved = () => {
    setSolved(true)
    setSolvedCount((n) => n + 1)
    setSolvedTypes((items) => items.includes(type) ? items : [...items, type])
  }

  return (
    <div className="card stepcard">
      <h2>🔐 提交订单 · 风控验证码（{TYPE_NAME[type]}）</h2>
      <p className="hint">
        订单 <code>{order?.orderId}</code> · 金额 <b>¥{order?.amount}</b>。
        {!clean && (isLive
          ? '🔴 实弹说明：真实流程弹出哪种验证码就由操作者按界面现场完成（验证码机制的逆向分析结论见 findings M2.x，实弹流程不内置自动化）。'
          : '真实环境存在字符/点选/滑块等多种形态——本页演示攻击者对三类验证码的自动化通过过程（模拟）。')}
      </p>

      <div className="captcha-box">
        {type === 'text' && <TextCaptcha key={round} onSolved={markSolved} />}
        {type === 'slider' && <SliderCaptcha key={round} onSolved={markSolved} />}
        {type === 'click' && <ClickCaptcha key={round} onSolved={markSolved} />}
      </div>

      {!clean && <div className="cap-refresh">
        <span className="cap-count">🧪 本地 Mock：已自动通过 <b>{solvedCount}</b> 张，覆盖 <b>{solvedTypes.length}/3</b> 种（按顺序刷新可完整演示三种形态）</span>
        <button className="btn ghost" onClick={refresh}>↻ 刷新验证码，再来一张（Mock）</button>
      </div>}

      {!clean && (
      <div className="note" style={{ marginTop: 12 }}>
        📌 本地 Mock 演示：三种验证码形态按顺序轮换，对应三条自动化路径——OCR（字符）、缺口检测+轨迹仿真（滑块）、目标检测+语序点击（点选）。
        共同弱点：<b>校验只发生在"这一次交互"上，缺少行为基线与服务端聚合判定</b>。
      </div>)}

      <button className="btn primary wide" disabled={!solved} onClick={onPass}>
        {solved ? (clean ? '验证通过，继续 →' : `验证码已自动通过 ×${solvedCount}，继续填写地址 →`) : '验证码自动处理中…'}
      </button>
    </div>
  )
}
