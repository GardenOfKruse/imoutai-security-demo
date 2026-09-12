import zlib from 'node:zlib'

/**
 * 将上游 HTTP 响应按 Content-Encoding 解码。
 * Node 的 https.request 不会因为传入 gzip 选项而自动解压响应。
 */
export function decodeResponseBody(body, contentEncoding = '') {
  let out = Buffer.isBuffer(body) ? body : Buffer.from(body ?? '')
  const encodings = String(contentEncoding || '')
    .split(',')
    .map((x) => x.trim().toLowerCase())
    .filter(Boolean)

  // 兼容已有样本中缺少 content-encoding 头、但正文仍是 gzip 的情况。
  if (!encodings.length && out[0] === 0x1f && out[1] === 0x8b) encodings.push('gzip')

  for (const encoding of encodings.reverse()) {
    if (encoding === 'gzip' || encoding === 'x-gzip') out = zlib.gunzipSync(out)
    else if (encoding === 'deflate') out = zlib.inflateSync(out)
    else if (encoding === 'br') out = zlib.brotliDecompressSync(out)
    else throw new Error(`unsupported content-encoding: ${encoding}`)
  }
  return out.toString('utf8')
}
