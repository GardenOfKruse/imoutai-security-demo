// payLinks.js — 支付链接拼接算法还原（🚫 仅拼接展示，禁止任何真实调用）
//
// 红线（AGENTS.md §支付红线）：支付链接只做拼接算法静态还原；绝对禁止真实调用
// （HTTP / 浏览器打开 / scheme 唤醒 / 参数重放），防止破坏线上账务信息。
// 本文件的产出仅用于向开发者演示"客户端拼接支付参数"的风险。

import { md5 } from './signature.js'

export const CHANNELS = [
  { id: 'alipay', name: '支付宝', icon: '🔵', color: '#1677ff' },
  { id: 'wechat', name: '微信支付', icon: '🟢', color: '#07c160' },
  { id: 'unionpay', name: '银联云闪付', icon: '🔴', color: '#e60012' },
]

/**
 * 各渠道支付链接拼接模板（依据公开协议形态整理，参数为演示值）
 * 真实 App 中这些模板散落在客户端代码里——攻击者可按模板伪造任意内容的支付唤起链接，
 * 用于钓鱼。防御：支付参数必须由服务端签名下发 + 客户端仅透传 + 回调服务端验签。
 */
export function buildPayLink(channelId, order) {
  const { orderId, amount, subject } = order
  const ts = Date.now().toString()
  switch (channelId) {
    case 'alipay': {
      // 形态：alipays://platformapi/startapp?appId=20000003&orderSuffix=...
      const biz = encodeURIComponent(
        JSON.stringify({ out_trade_no: orderId, total_amount: amount, subject })
      )
      const sign = md5(`alipay|${orderId}|${amount}|${ts}`).slice(0, 16)
      return {
        scheme: `alipays://platformapi/startapp?appId=20000003&orderSuffix=${biz}`,
        h5: `https://openapi.alipay.com/gateway.do?out_trade_no=${orderId}&total_amount=${amount}&subject=${encodeURIComponent(subject)}&sign=${sign}`,
        note: 'orderSuffix 内嵌完整交易参数——若客户端可改 amount，即改价攻击入口',
      }
    }
    case 'wechat': {
      // 形态：weixin://wap/pay?prepayid=...&noncestr=...&sign=...
      const nonce = md5(`wx|${orderId}|${ts}`).slice(0, 32)
      const prepay = `wx${ts}${nonce.slice(0, 10)}`
      return {
        scheme: `weixin://wap/pay?prepayid=${prepay}&noncestr=${nonce}&package=WAP`,
        h5: `https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=${prepay}&package=WAP`,
        note: 'prepayid 应由服务端统一下单获得——客户端自行构造即伪造风险',
      }
    }
    case 'unionpay': {
      const tn = md5(`up|${orderId}|${amount}|${ts}`)
      return {
        scheme: `upwallet://mobilepay?tn=${tn}`,
        h5: `https://gateway.95516.com/jiaoyimobile/mobilepay.html?tn=${tn}`,
        note: 'tn（交易流水）应由银联/服务端签发，客户端拼接不具备任何防伪能力',
      }
    }
    default:
      throw new Error('unknown channel: ' + channelId)
  }
}
