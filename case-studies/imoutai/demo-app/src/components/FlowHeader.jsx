import { useEffect, useState } from 'react'

/** 步骤指示器 */
export default function FlowHeader({ steps, current }) {
  return (
    <nav className="flowbar">
      {steps.map((s, i) => (
        <div key={s} className={'fstep' + (i === current ? ' cur' : i < current ? ' past' : '')}>
          <span className="fdot">{i < current ? '✓' : i + 1}</span>
          <span className="ftxt">{s}</span>
          {i < steps.length - 1 && <span className="farrow">→</span>}
        </div>
      ))}
    </nav>
  )
}

/** 攻击者视角 / 开发者注意 侧栏卡片 */
const NOTES = {
  login: {
    title: '🎯 Step 1 · 攻击者视角：签名算法已被 1:1 复刻',
    attacker: '真实 App 中 md5(deviceKey+mobile+timestamp) 由客户端计算。我们已把该算法原样搬进本页 JS（左侧发送验证码时实时计算）——证明：客户端可算 = 攻击者可算，且已用真实抓包样本 15/15 逐字节验证。',
    dev: [
      '签名改为 HMAC-SHA256 + 服务端下发一次性 nonce，密钥不落客户端',
      '时间戳窗口收紧到 ±60s，服务端去重防重放',
      'md5 字段不应由客户端预计算后原样提交（等于没签）',
    ],
  },
  product: {
    title: '🎯 Step 2 · 攻击者视角：接口参数结构已完全暴露',
    attacker: 'Retrofit 注解在运行时可被反射枚举，全部接口 URL/参数模型一次 dump 即得（220 类 0 失败）。下单接口 /xhr/front/trade/order/standard/submit/v2 的字段结构对我们不再有任何秘密。',
    dev: [
      '接口元数据按端最小暴露，非必要字段不进注解',
      '关键写接口（下单/预约）增加服务端风控权重分',
      '响应中不要回传多余结构（攻击者用于逆向建模）',
    ],
  },
  captcha: {
    title: '🎯 Step 3 · 验证码状态机与证据边界',
    attacker: '本地 fixture 展示字符、滑块、点选三类校验的失败/成功/刷新状态，以及验证码通过后才提交订单的时序；它不等同 OCR、目标检测、轨迹仿真或原生算法还原。',
    dev: [
      '高价值写接口（下单）前置强人机校验（行为式验证码）',
      '服务端做行为序列分析（Everisk 遥测已有数据，应在服务端消费）',
      '验证码失败率/耗时上报，异常自动化特征入风控分',
    ],
  },
  address: {
    title: '🎯 Step 4 · 攻击者视角：越权与批量注册',
    attacker: '登录即注册意味着攻击者可用接码平台批量养号；地址等业务数据接口若水平越权校验缺失，可枚举他人信息。批量脚本会复用同一签名模板——这正是服务端指纹识别的抓手。',
    dev: [
      '所有业务接口做资源属主校验（防 IDOR）',
      '同设备/同签名的批量行为入风控分（本演示的请求日志就是特征样本）',
      '接码号段/虚拟运营商号段降权',
    ],
  },
  pay: {
    title: '🎯 Step 5 · 攻击者视角：支付渠道选择只是壳',
    attacker: '支付渠道的"安全性"不在选择界面，而在支付参数由谁生成。若客户端拼接金额/商品并提交，攻击者即可改价；本页下一步将展示链接拼模板的可伪造性。',
    dev: [
      '支付参数（金额/订单号）必须服务端生成并签名，客户端只透传',
      '支付回调服务端验签 + 对账兜底',
      '下单接口与支付接口的签名体系分离',
    ],
  },
  paylink: {
    title: '🎯 Step 6 · 攻击者视角：支付链接拼接 = 钓鱼入口',
    attacker: '支付链接拼接模板一旦泄露（静态逆向即可得），攻击者可构造外观完全一致的支付唤起链接用于钓鱼，或篡改参数重放。因此我们只做拼接逻辑整理，绝不真实调用（红线）。',
    dev: [
      '支付链接由服务端按订单签发生成，客户端不参与拼参',
      'scheme/回调增加来源签名校验，防第三方应用仿冒',
      '对客户端上报的支付参数做服务端全量校验',
    ],
  },
}

export function AttackNotes({ type }) {
  const n = NOTES[type]
  return (
    <div className="attack card">
      <div className="attack-title">{n.title}</div>
      <p className="attack-p">{n.attacker}</p>
      <div className="attack-dev">
        <div className="attack-dev-h">🛡️ 开发者应该注意</div>
        <ul>{n.dev.map((d) => <li key={d}>{d}</li>)}</ul>
      </div>
    </div>
  )
}
