// signature.js — 真实还原的客户端签名算法（教学演示核心）
//
// 来源：i茅台 v1.9.12 运行态取证（见 findings/login-signature.md）
//   GetVerifyCodeRequest.md5 = MD5( deviceKey + mobile + timestamp )
// 这里 1:1 复刻客户端逻辑——向开发者证明：凡是客户端可算的签名，攻击者都能在
// 任意环境（本页 = 浏览器 JS）原样重放。防御必须假设签名算法已泄露。

/** 经典 MD5（公有领域实现，字符串入参返回小写 hex） */
export function md5(str) {
  return hex(md51(unescape(encodeURIComponent(str))))
}

function md51(s) {
  const n = s.length
  const state = [1732584193, -271733879, -1732584194, 271733878]
  let i
  for (i = 64; i <= s.length; i += 64) md5cycle(state, md5blk(s.substring(i - 64, i)))
  s = s.substring(i - 64)
  const tail = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
  for (i = 0; i < s.length; i++) tail[i >> 2] |= s.charCodeAt(i) << ((i % 4) << 3)
  tail[i >> 2] |= 0x80 << ((i % 4) << 3)
  if (i > 55) {
    md5cycle(state, tail)
    for (i = 0; i < 16; i++) tail[i] = 0
  }
  tail[14] = n * 8
  md5cycle(state, tail)
  return state
}

function md5blk(s) {
  const blks = []
  for (let i = 0; i < 64; i += 4)
    blks[i >> 2] = s.charCodeAt(i) + (s.charCodeAt(i + 1) << 8) + (s.charCodeAt(i + 2) << 16) + (s.charCodeAt(i + 3) << 24)
  return blks
}

function md5cycle(x, k) {
  let [a, b, c, d] = x
  a = ff(a, b, c, d, k[0], 7, -680876936); d = ff(d, a, b, c, k[1], 12, -389564586)
  c = ff(c, d, a, b, k[2], 17, 606105819); b = ff(b, c, d, a, k[3], 22, -1044525330)
  a = ff(a, b, c, d, k[4], 7, -176418897); d = ff(d, a, b, c, k[5], 12, 1200080426)
  c = ff(c, d, a, b, k[6], 17, -1473231341); b = ff(b, c, d, a, k[7], 22, -45705983)
  a = ff(a, b, c, d, k[8], 7, 1770035416); d = ff(d, a, b, c, k[9], 12, -1958414417)
  c = ff(c, d, a, b, k[10], 17, -42063); b = ff(b, c, d, a, k[11], 22, -1990404162)
  a = ff(a, b, c, d, k[12], 7, 1804603682); d = ff(d, a, b, c, k[13], 12, -40341101)
  c = ff(c, d, a, b, k[14], 17, -1502002290); b = ff(b, c, d, a, k[15], 22, 1236535329)
  a = gg(a, b, c, d, k[1], 5, -165796510); d = gg(d, a, b, c, k[6], 9, -1069501632)
  c = gg(c, d, a, b, k[11], 14, 643717713); b = gg(b, c, d, a, k[0], 20, -373897302)
  a = gg(a, b, c, d, k[5], 5, -701558691); d = gg(d, a, b, c, k[10], 9, 38016083)
  c = gg(c, d, a, b, k[15], 14, -660478335); b = gg(b, c, d, a, k[4], 20, -405537848)
  a = gg(a, b, c, d, k[9], 5, 568446438); d = gg(d, a, b, c, k[14], 9, -1019803690)
  c = gg(c, d, a, b, k[3], 14, -187363961); b = gg(b, c, d, a, k[8], 20, 1163531501)
  a = gg(a, b, c, d, k[13], 5, -1444681467); d = gg(d, a, b, c, k[2], 9, -51403784)
  c = gg(c, d, a, b, k[7], 14, 1735328473); b = gg(b, c, d, a, k[12], 20, -1926607734)
  a = hh(a, b, c, d, k[5], 4, -378558); d = hh(d, a, b, c, k[8], 11, -2022574463)
  c = hh(c, d, a, b, k[11], 16, 1839030562); b = hh(b, c, d, a, k[14], 23, -35309556)
  a = hh(a, b, c, d, k[1], 4, -1530992060); d = hh(d, a, b, c, k[4], 11, 1272893353)
  c = hh(c, d, a, b, k[7], 16, -155497632); b = hh(b, c, d, a, k[10], 23, -1094730640)
  a = hh(a, b, c, d, k[13], 4, 681279174); d = hh(d, a, b, c, k[0], 11, -358537222)
  c = hh(c, d, a, b, k[3], 16, -722521979); b = hh(b, c, d, a, k[6], 23, 76029189)
  a = hh(a, b, c, d, k[9], 4, -640364487); d = hh(d, a, b, c, k[12], 11, -421815835)
  c = hh(c, d, a, b, k[15], 16, 530742520); b = hh(b, c, d, a, k[2], 23, -995338651)
  a = ii(a, b, c, d, k[0], 6, -198630844); d = ii(d, a, b, c, k[7], 10, 1126891415)
  c = ii(c, d, a, b, k[14], 15, -1416354905); b = ii(b, c, d, a, k[5], 21, -57434055)
  a = ii(a, b, c, d, k[12], 6, 1700485571); d = ii(d, a, b, c, k[3], 10, -1894986606)
  c = ii(c, d, a, b, k[10], 15, -1051523); b = ii(b, c, d, a, k[1], 21, -2054922799)
  a = ii(a, b, c, d, k[8], 6, 1873313359); d = ii(d, a, b, c, k[15], 10, -30611744)
  c = ii(c, d, a, b, k[6], 15, -1560198380); b = ii(b, c, d, a, k[13], 21, 1309151649)
  a = ii(a, b, c, d, k[4], 6, -145523070); d = ii(d, a, b, c, k[11], 10, -1120210379)
  c = ii(c, d, a, b, k[2], 15, 718787259); b = ii(b, c, d, a, k[9], 21, -343485551)
  x[0] = add32(a, x[0]); x[1] = add32(b, x[1]); x[2] = add32(c, x[2]); x[3] = add32(d, x[3])
}

function cmn(q, a, b, x, s, t) { a = add32(add32(a, q), add32(x, t)); return add32((a << s) | (a >>> (32 - s)), b) }
function ff(a, b, c, d, x, s, t) { return cmn((b & c) | (~b & d), a, b, x, s, t) }
function gg(a, b, c, d, x, s, t) { return cmn((b & d) | (c & ~d), a, b, x, s, t) }
function hh(a, b, c, d, x, s, t) { return cmn(b ^ c ^ d, a, b, x, s, t) }
function ii(a, b, c, d, x, s, t) { return cmn(c ^ (b | ~d), a, b, x, s, t) }
function add32(a, b) { return (a + b) & 0xffffffff }
function hex(x) { return x.map(rhex).join('') }
function rhex(n) {
  const chr = '0123456789abcdef'
  let s = ''
  for (let j = 0; j < 4; j++) s += chr[(n >> (j * 8 + 4)) & 0x0f] + chr[(n >> (j * 8)) & 0x0f]
  return s
}

/**
 * 还原短信验证码签名（与真实客户端逐字节一致，见 reproduce_sign.py 15/15 PASS）
 * @returns {{plain: string, md5: string}}
 */
export function buildVcodeSign(deviceKey, mobile, timestamp) {
  const plain = deviceKey + mobile + timestamp
  return { plain, md5: md5(plain) }
}

/** 自校验：签名实现与真实抓取样本必须逐字节一致 */
export function selfTest() {
  const expect = '9a654aa5fbf4afe210aadc14f71254a6'
  const got = buildVcodeSign('2af72f100c356273d46284f6fd1dfc08', '10086', '1789140362120').md5
  return { pass: got === expect, got, expect }
}
