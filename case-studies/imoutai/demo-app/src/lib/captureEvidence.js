// captureEvidence.js — 真实取证包的结构检查（只读，不是授权器）
//
// 这里检查“证据是否齐全”，不验证 token 真伪、不生成请求体，也不代表可以绕过
// 服务端风控。verifiedCapture 仍必须来自操作者选择的授权取证档案；本模块不能把
// 本地 JSON 自行升级为真实档案。

const SYNTHETIC_PROFILE_TYPES = new Set([
  'generated-skeleton',
  'redacted-template',
  'offline-algorithm-research',
])

function objectLike(value) {
  return Boolean(value && typeof value === 'object' && !Array.isArray(value))
}

function nonEmptyObject(value) {
  return objectLike(value) && Object.keys(value).length > 0
}

function hasValue(value) {
  return value !== undefined && value !== null && String(value).trim() !== ''
}

function check(id, label, ok) {
  return { id, label, ok: Boolean(ok) }
}

/**
 * 检查 HeaderMap + 订单运行态证据的结构完整度。
 * 返回值只适合 UI/离线 smoke test 使用，不能作为真实授权判断。
 */
export function validateCaptureEvidence(profile) {
  const p = objectLike(profile) ? profile : {}
  const order = objectLike(p.orderEvidence) ? p.orderEvidence : {}
  const compose = objectLike(order.compose) ? order.compose : {}
  const captcha = objectLike(order.captcha) ? order.captcha : {}
  const submit = objectLike(order.submit) ? order.submit : {}
  const composeResponse = objectLike(compose.response) ? compose.response : {}
  const submitResponse = objectLike(submit.response) ? submit.response : {}
  const transactionId = compose.transactionId || composeResponse.transactionId || composeResponse.data?.transactionId
  const checks = [
    check('profile', '授权取证档案标记', p.verifiedCapture === true && !SYNTHETIC_PROFILE_TYPES.has(p.profileType)),
    check('appHeaders', 'App HeaderMap', nonEmptyObject(p.appHeaders || p.headers)),
    check('composeBody', 'compose/v2 请求体', nonEmptyObject(compose.requestBody)),
    check('composeResponse', 'compose/v2 响应', nonEmptyObject(compose.response)),
    check('transactionId', 'compose 交易字段', hasValue(transactionId)),
    check('captchaChallenge', '验证码 challenge 事件', captcha.challengeObserved === true),
    check('captchaCallback', '验证码校验回调', captcha.callbackObserved === true),
    check('captchaPassed', '验证码通过结果', captcha.passedObserved === true),
    check('captchaRefresh', '验证码刷新事件', captcha.refreshObserved === true),
    check('captchaTransaction', '验证码与交易关联', hasValue(captcha.transactionId) && String(captcha.transactionId) === String(transactionId)),
    check('submitBody', 'submit/v2 请求体', nonEmptyObject(submit.requestBody)),
    check('submitResponse', 'submit/v2 响应', nonEmptyObject(submit.response)),
    check('orderCreated', '订单创建结果', submit.orderCreated === true || submitResponse.orderCreated === true),
  ]
  const missing = checks.filter((item) => !item.ok).map((item) => item.label)
  const orderReady = checks
    .filter((item) => !['captchaRefresh'].includes(item.id))
    .every((item) => item.ok)
  return {
    orderReady,
    lifecycleComplete: orderReady && checks.find((item) => item.id === 'captchaRefresh')?.ok === true,
    missing,
    checks,
    structuralOnly: true,
  }
}

