// deviceKey.js — 设备指纹的"可复刻性"演示
//
// 运行态观察到真实 App 使用 32-hex、跨会话稳定的 deviceKey；CryptoUtil/native 是候选来源，
// 但具体派生链尚未完成归因。本文件只做浏览器环境的 synthetic 演示，不能当作 native 算法。

function fnv1a(str) {
  let h = 0x811c9dc5
  for (let i = 0; i < str.length; i++) {
    h ^= str.charCodeAt(i)
    h = (h + ((h << 1) + (h << 4) + (h << 7) + (h << 8) + (h << 24))) >>> 0
  }
  return h.toString(16).padStart(8, '0')
}

/** 把浏览器环境特征压成 32-hex（演示用，非真实算法） */
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
