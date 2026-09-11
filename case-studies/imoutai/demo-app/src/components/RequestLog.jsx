import { useEffect, useState } from 'react'
import { logStore } from '../lib/mockApi.js'

/** 底部协议请求日志（教学：让开发者看到每步的协议形态） */
export default function RequestLog() {
  const [entries, setEntries] = useState([])
  const [open, setOpen] = useState(true)

  useEffect(() => {
    setEntries(logStore.getAll().slice(-20))
    return logStore.subscribe((e) => setEntries(logStore.getAll().slice(-20)))
  }, [])

  return (
    <section className={'reqlog' + (open ? '' : ' closed')}>
      <div className="reqlog-bar" onClick={() => setOpen(!open)}>
        <span>📡 协议请求日志（本地 Mock · 每一步真实协议形态）</span>
        <span className="reqlog-count">{entries.length} 条 {open ? '▾' : '▸'}</span>
      </div>
      {open && (
        <div className="reqlog-body">
          {entries.length === 0 && <div className="reqlog-empty">尚无请求 —— 开始操作上面的流程后，每一步的协议交互会实时出现在这里</div>}
          {entries.map((e) => (
            <div key={e.id} className="reqitem">
              <div className="reqhead">
                <span className="reqtime">{e.time}</span>
                <span className="reqmethod">{e.method}</span>
                <code className="requrl">{e.api}</code>
              </div>
              <div className="reqbody">{JSON.stringify(e.body)}</div>
              {e.live && (
                <div className="reqbody livehdr">
                  → HTTP {e.status} · {e.ms}ms{e.respHeaders?.server ? ` · server:${e.respHeaders.server}` : ''}
                  {e.reqHeaders && <> · 发送头: {Object.keys(e.reqHeaders).slice(0, 8).join(', ')}</>}
                </div>
              )}
              {e.note && <div className="reqnote">💡 {e.note}</div>}
            </div>
          ))}
        </div>
      )}
    </section>
  )
}
