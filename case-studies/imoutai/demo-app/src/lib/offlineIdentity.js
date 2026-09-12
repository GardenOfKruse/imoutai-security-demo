// offlineIdentity.js — 脱离真机的算法研究 fixture
//
// 证据边界：
// - vcode MD5 的拼接公式已由运行态样本 15/15 验证，复用 signature.js。
// - deviceKey 的 native 来源、clips_* / MT-Device-ID 的派生算法、MT-R 算法仍未验证。
// 因此本文件生成的是可重复的 synthetic 值，只允许进入本地 Mock 流程，不能当作真机档案。

import { buildVcodeSign, md5, VERIFIED_DEVICE_KEY } from './signature.js'

export const RESEARCH_PROFILE_TYPE = 'offline-algorithm-research'
export const OBSERVED_DEVICE_TUPLE = 'android;31;Redmi;lime'

const RISK_FACTOR_PRIORITY = ['android_id', 'drmid', 'mac', 'imei', 'serial']
const INVALID_RISK_FACTORS = new Set([
  '', 'null', '00000000', '000000000000000000', '111111', '111111111111111111',
  '123456', '12345678', '0123456789ABCDEF', 'NA', 'NP',
])
const INVALID_MAC_PREFIXES = [
  '00:11:22:33:', '11:22:33:44', 'aa:bb:cc:dd', '00:00:00:00:00',
  '02:00:00:00:00:00', '6a:aa:6a:aa:6a:6a', '00:02:00:00:00:00',
  '00:00:00:80:00:00', '10:00:00:00:00:12', 'f2:0f:f0:02:f0:22',
  '32:12:31:23:32:32', '66:00:44:40:06:66', 'c0:00:00:00:00:d0',
  '04:00:00:50:54:04', 'NA', 'NP',
]

function isValidRiskFactor(value) {
  const text = String(value || '').trim()
  if (!text || INVALID_RISK_FACTORS.has(text.toLowerCase())) return false
  if (new Set(text.toLowerCase().split('')).size < 3) return false
  return !text.split('').every((char, index, chars) => index === 0 || char.charCodeAt(0) - chars[index - 1].charCodeAt(0) === 1)
}

function isValidMac(value) {
  const text = String(value || '').trim()
  if (!/^([0-9a-f]{2}:){5}[0-9a-f]{2}$/i.test(text)) return false
  if (new Set(text.toLowerCase().split(':')).size <= 2) return false
  return !INVALID_MAC_PREFIXES.some((prefix) => text.toLowerCase().startsWith(prefix.toLowerCase()))
}

function uuidV3FromText(value) {
  const bytes = (md5(value).match(/.{2}/g) || []).map((pair) => Number.parseInt(pair, 16))
  bytes[6] = (bytes[6] & 0x0f) | 0x30
  bytes[8] = (bytes[8] & 0x3f) | 0x80
  const hex = bytes.map((byte) => byte.toString(16).padStart(2, '0')).join('')
  return hex.slice(0, 8) + '-' + hex.slice(8, 12) + '-' + hex.slice(12, 16) + '-' + hex.slice(16, 20) + '-' + hex.slice(20)
}

/**
 * RiskStub aa.java 的离线等价 Mock：
 * Android > 26 的因子优先级为 android_id -> drmid -> mac -> imei -> serial，
 * 命中有效因子后使用 UUID.nameUUIDFromBytes(factor.getBytes())（UUID v3）。
 * 该结果只代表 RiskStub udid，不代表业务 deviceKey / clips_*。
 */
export function deriveRiskStubUdid(factors = {}) {
  const selected = RISK_FACTOR_PRIORITY.find((name) => {
    const value = factors[name]
    return name === 'mac' ? isValidMac(value) : isValidRiskFactor(value)
  })
  return selected ? uuidV3FromText(String(factors[selected]).trim()) : null
}

function syntheticClipsToken(material, slot) {
  const digest = md5(`clips-fixture|${slot}|${material}`)
  return `clips_${btoa(`research|${slot}|${digest}`).replace(/=/g, '').slice(0, 44)}`
}

/**
 * 生成稳定的本地研究档案。
 * signingDeviceKey 使用已确认的 32-hex 样本作为公式 fixture，不表示当前浏览器就是该设备。
 */
export function deriveOfflineResearchProfile(seed = 'training-fixture-001') {
  const material = `${seed}|${OBSERVED_DEVICE_TUPLE}`
  const syntheticDeviceKey = md5(`device-key-fixture|${material}`)
  const riskStubFactors = {
    android_id: 'android-id-' + md5('risk-factor|' + seed).slice(0, 12),
    drmid: '',
    mac: '',
    imei: '',
    serial: '',
  }
  return {
    profileType: RESEARCH_PROFILE_TYPE,
    verifiedCapture: false,
    fixtureSeed: seed,
    observedDeviceTuple: OBSERVED_DEVICE_TUPLE,
    signingDeviceKey: VERIFIED_DEVICE_KEY,
    syntheticDeviceKey,
    syntheticDeviceId: syntheticClipsToken(material, 'device-id'),
    riskStubFactors,
    riskStubUdid: deriveRiskStubUdid(riskStubFactors),
    syntheticClipsTokens: [
      syntheticClipsToken(material, 'a'),
      syntheticClipsToken(material, 'b'),
      syntheticClipsToken(material, 'c'),
    ],
    unverified: [
      'native deviceKey 生成来源',
      'clips_* / MT-Device-ID 派生算法',
      'MT-R 生成算法',
    ],
  }
}

export function buildOfflineResearchTrace(profile, mobile = '10086', timestamp = '1789140362120') {
  const sign = buildVcodeSign(profile.signingDeviceKey, mobile, timestamp)
  return {
    profileType: profile.profileType,
    input: { deviceKey: profile.signingDeviceKey, mobile, timestamp },
    confirmed: {
      formula: 'MD5(deviceKey + mobile + timestamp)',
      plain: sign.plain,
      md5: sign.md5,
    },
    synthetic: {
      deviceId: profile.syntheticDeviceId,
      clipsTokens: profile.syntheticClipsTokens,
    },
    riskStub: {
      factors: profile.riskStubFactors,
      priority: 'android_id → drmid → mac → imei → serial',
      algorithm: 'UUID.nameUUIDFromBytes(selectedFactor.getBytes()) / UUID v3',
      udid: profile.riskStubUdid,
      status: '已确认算法，可离线精确复现；不等于业务 deviceKey',
    },
    unverified: profile.unverified,
  }
}
