import fs from 'node:fs'
import path from 'node:path'
import { validateCaptureEvidence } from '../src/lib/captureEvidence.js'

const input = process.argv[2]
if (!input) {
  console.error('用法：node scripts/check-capture-evidence.mjs <authorized-capture.json>')
  process.exitCode = 1
} else {
  try {
    const profile = JSON.parse(fs.readFileSync(input, 'utf8'))
    const status = validateCaptureEvidence(profile)
    // 只输出结构状态；不回显路径、Header、Cookie、token、请求体或响应值。
    const report = {
      file: path.basename(input),
      profileType: typeof profile.profileType === 'string' ? profile.profileType : null,
      verifiedCapture: profile.verifiedCapture === true,
      orderReady: status.orderReady,
      lifecycleComplete: status.lifecycleComplete,
      missing: status.missing,
      checks: status.checks.map(({ id, ok }) => ({ id, ok })),
      structuralOnly: status.structuralOnly,
    }
    console.log(JSON.stringify(report, null, 2))
    process.exitCode = status.orderReady ? 0 : 1
  } catch (error) {
    console.error(`取证 JSON 解析失败：${error.message}`)
    process.exitCode = 1
  }
}
