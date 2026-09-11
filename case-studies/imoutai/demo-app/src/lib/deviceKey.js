// deviceKey.js — 设备指纹的"可复刻性"演示
//
// 真实 App 的 deviceKey 由 native 层派生（libCryptoSeed/CryptoUtil，32-hex，设备绑定）。
// 但关键教学点：无论密钥藏在多深的 native，只要它能出现在"运行中的客户端"，
// 攻击者就能把它读出来并复制到任何环境。本页用浏览器指纹模拟同样的"设备绑定"效果。

function fnv1a(str) {
  let h = 0x811c9dc5
  for (let i = 0; i < str.length; i++) {
    h ^= str.charCodeAt(i)
    h = (h + ((h << 1) + (h << 4) + (h << 7) + (h << 8) + (h << 24))) >>> 0
  }
  return h.toString(16).padStart(8, '0')
}

/** 模拟 native 设备指纹派生：把浏览器环境特征压成 32-hex（演示用，非真实算法） */
export function deriveDeviceKey() {
  const seeds = [
    navigator.userAgent,
    navigator.language,
    screen.width + 'x' + screen.height,
    screen.colorDepth,
    new Date().getTimezoneOffset(),
    navigator.hardwareConcurrency || 0,
  ].join('|')
  return fnv1a(seeds) + fnv1a(seeds.split('').reverse().join('')) + fnv1a(seeds + 'a') + fnv1a(seeds + 'b')
}

/** 模拟 App 的 clips_* 设备 token（真实值为 base64，此处同样派生自环境指纹） */
export function deriveClipsToken(salt) {
  const k = deriveDeviceKey()
  return btoa('clips_' + k + ':' + salt).replace(/=/g, '').slice(0, 44)
}
