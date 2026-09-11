// mockApi.js — 本地 mock 网络层
//
// 🚫 红线：本演示与生产系统零交互。所有"请求"只是本地函数 + 请求日志，
// 用于向开发者展示每个步骤在协议层长什么样（含签名参数）。
// 任何情况下都不应把这里的请求指向真实域名。

export const HOST_APP = 'https://app.moutai519.com.cn'   // 仅用于展示协议形态，绝不请求
export const HOST_H5 = 'https://h5.moutai519.com.cn'     // 仅用于展示协议形态，绝不请求

const listeners = new Set()
const entries = []   // 持久留痕：纯净版隐藏日志 UI 时仍全程记录（证据链要求）
export const logStore = {
  subscribe(fn) { listeners.add(fn); return () => listeners.delete(fn) },
  getAll() { return entries },
  _emit(entry) { entries.push(entry); listeners.forEach((f) => f(entry)) },
}

let seq = 0

/**
 * 记录一次"协议交互"（本地模拟，无网络行为）。
 * entry: { api, method, body, headers, note }
 */
export function recordApi({ api, method = 'POST', body = {}, headers = {}, note = '' }) {
  const entry = {
    id: ++seq,
    time: new Date().toLocaleTimeString('zh-CN', { hour12: false }),
    method,
    url: HOST_APP + api,
    api,
    body,
    headers,
    note,
  }
  logStore._emit(entry)
  return entry
}

/** 模拟短信验证码下发（演示环境直接"收到"固定码，真实系统不存在此行为） */
export function mockSendSmsCode(mobile) {
  return recordApi({
    api: '/xhr/front/user/register/vcode',
    note: '真实接口要求 body.md5 由客户端预计算——签名算法可被完整复刻（见 signature.js）',
    body: { mobile, timestamp: String(Date.now()), md5: '@由 signature.buildVcodeSign 计算@' },
  })
}

export function mockLogin(mobile) {
  return recordApi({
    api: '/xhr/front/user/register/login',
    note: '登录成功下发 MT-Token；HeaderMap 签名头由 okhttp 拦截器集中注入（api.a.intercept）',
    body: { mobile, vCode: '******', ydLogId: '(一键登录路径专用)', ydToken: '(一键登录路径专用)' },
  })
}

export function mockSubmitOrder(order) {
  return recordApi({
    api: '/xhr/front/trade/order/standard/submit/v2',
    note: '下单接口（App 端 V0/T0 方法）；客户端拼接支付参数的风险见 payLinks.js',
    body: order,
  })
}

export const PRODUCTS = [
  { id: 'fly53', name: '飞天茅台 53%vol 500ml', price: 1499, stock: '申购限量', tag: '经典款' },
  { id: 'zhenbao', name: '茅台1935 500ml', price: 1188, stock: '申购限量', tag: '文创系列' },
  { id: 'chunshou', name: '茅台王子酒 己亥猪年', price: 698, stock: '少量现货', tag: '生肖款' },
]
