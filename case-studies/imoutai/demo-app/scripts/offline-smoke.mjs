import fs from 'node:fs'
import { buildOfflineHeadmap, deriveOfflineResearchProfile, deriveRiskStubClientToken } from '../src/lib/offlineIdentity.js'
import { isVerifiedCaptureProfile, validateCaptureEvidence } from '../src/lib/captureEvidence.js'
import { logStore, mockComposeOrder, mockSubmitOrder } from '../src/lib/mockApi.js'

function assert(condition, message) {
  if (!condition) throw new Error(message)
}

const profileA = deriveOfflineResearchProfile('training-fixture-001')
const profileB = deriveOfflineResearchProfile('training-fixture-001')
const headmapA = buildOfflineHeadmap(profileA)
const headmapB = buildOfflineHeadmap(profileB)
const checkedInHeadmap = JSON.parse(fs.readFileSync(new URL('../public/offline-headermap.json', import.meta.url), 'utf8'))
const fallbackHeadmap = buildOfflineHeadmap(deriveOfflineResearchProfile('training-fixture-001', {
  androidId: 'NA',
  drmid: 'drmid-fixture-123',
}))

assert(JSON.stringify(headmapA) === JSON.stringify(headmapB), 'offline HeadMap must be deterministic')
assert(JSON.stringify(headmapA) === JSON.stringify(checkedInHeadmap), 'checked-in offline HeadMap is stale')
assert(headmapA.profileType === 'offline-algorithm-research', 'offline profile type is missing')
assert(headmapA.verifiedCapture === false, 'offline fixture must not be treated as a real capture')
assert(headmapA.headers.Cookie.includes('<offline-placeholder>'), 'offline fixture must contain only placeholder cookies')
assert(headmapA.riskStub.clientToken === '761d432f17845289134f2403b5ce62334ac6f4ca4f', 'RiskStub client_token fixture changed unexpectedly')
assert(fallbackHeadmap.riskStub.selectedFactor === 'drmid', 'RiskStub factor fallback must follow the selected input')
assert(fallbackHeadmap.riskStub.udid !== headmapA.riskStub.udid, 'changing the selected factor must change the RiskStub udid')
assert(deriveRiskStubClientToken('1789140362120') === deriveRiskStubClientToken('1789140362120'), 'RiskStub client_token must be deterministic for fixed time')
assert(deriveRiskStubClientToken('1789140362120')?.length > 20, 'RiskStub client_token fixture is missing')
const evidenceStatus = validateCaptureEvidence({ ...headmapA, orderEvidence: {} })
assert(evidenceStatus.orderReady === false, 'offline HeadMap must not appear order-ready')
assert(evidenceStatus.structuralOnly === true, 'capture evidence check must remain structural-only')
assert(evidenceStatus.missing.includes('compose/v2 请求体'), 'missing compose evidence must be reported')
assert(isVerifiedCaptureProfile({ ...headmapA, verifiedCapture: false }) === false, 'offline HeadMap must fail the Live profile gate')
assert(isVerifiedCaptureProfile({ ...headmapA, profileType: 'redacted-template', verifiedCapture: true }) === false, 'redacted template must fail the Live profile gate')
assert(isVerifiedCaptureProfile({ ...headmapA, profileType: 'authorized-runtime-capture', verifiedCapture: true }) === true, 'explicit real capture marker must pass the structural profile gate')
assert(isVerifiedCaptureProfile({ ...headmapA, profileType: 'generated-skeleton', verifiedCapture: true }) === false, 'generated skeleton must fail the server-side profile gate')

const items = [{ product: { id: 'fly53', name: 'fixture product', price: 1499 }, qty: 1 }]
const before = logStore.getAll().length
const draft = mockComposeOrder(items)
assert(draft.transactionId && draft.composeBody && draft.submitBody, 'compose fixture is incomplete')
assert(Object.keys(draft.composeBody).sort().join(',') === 'actParam,addressInfo,deliverMethod,itemList,selfPickUpSite,shopSelfPickUpInventoryInfo', 'compose field skeleton changed')
assert(Object.keys(draft.submitBody).sort().join(',') === 'actParam,addressInfo,deliverMethod,instantDeliveryInfo,invoiceSubmitDTO,itemList,payChannel,selectedCoupon,selfPickUpSite,shopSelfPickUpInventoryInfo,source,sourceId,transactionId', 'submit field skeleton changed')
assert(logStore.getAll().length === before + 1, 'compose must be recorded once')

mockSubmitOrder(draft.submitBody, draft.order.orderId)
const entries = logStore.getAll().slice(before)
assert(entries.length === 2, 'compose and submit must be the only two mock order events')
assert(entries[0].api.endsWith('/compose/v2'), 'compose must precede submit')
assert(entries[1].api.endsWith('/submit/v2'), 'submit event is missing')

console.log(JSON.stringify({
  deterministicHeadmap: true,
  liveUnlock: false,
  orderSequence: entries.map((entry) => entry.api),
  network: 'none',
}, null, 2))
