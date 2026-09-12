import { buildOfflineHeadmap, deriveOfflineResearchProfile, deriveRiskStubClientToken } from '../src/lib/offlineIdentity.js'
import { logStore, mockComposeOrder, mockSubmitOrder } from '../src/lib/mockApi.js'

function assert(condition, message) {
  if (!condition) throw new Error(message)
}

const profileA = deriveOfflineResearchProfile('training-fixture-001')
const profileB = deriveOfflineResearchProfile('training-fixture-001')
const headmapA = buildOfflineHeadmap(profileA)
const headmapB = buildOfflineHeadmap(profileB)

assert(JSON.stringify(headmapA) === JSON.stringify(headmapB), 'offline HeadMap must be deterministic')
assert(headmapA.profileType === 'offline-algorithm-research', 'offline profile type is missing')
assert(headmapA.verifiedCapture === false, 'offline fixture must not be treated as a real capture')
assert(headmapA.headers.Cookie.includes('<offline-placeholder>'), 'offline fixture must contain only placeholder cookies')
assert(deriveRiskStubClientToken('1789140362120') === deriveRiskStubClientToken('1789140362120'), 'RiskStub client_token must be deterministic for fixed time')
assert(deriveRiskStubClientToken('1789140362120')?.length > 20, 'RiskStub client_token fixture is missing')

const items = [{ product: { id: 'fly53', name: 'fixture product', price: 1499 }, qty: 1 }]
const before = logStore.getAll().length
const draft = mockComposeOrder(items)
assert(draft.transactionId && draft.composeBody, 'compose fixture is incomplete')
assert(logStore.getAll().length === before + 1, 'compose must be recorded once')

mockSubmitOrder({
  transactionId: draft.transactionId,
  orderId: draft.order.orderId,
  items: [{ sku: 'fly53', qty: 1 }],
})
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
