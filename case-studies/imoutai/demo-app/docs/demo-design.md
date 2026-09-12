# i茅台安全培训演示 —— Demo 实现完整思路文档

> 面向：维护/扩展本 demo 的开发者与后续接手者
> 工程位置：`case-studies/imoutai/demo-app/`（Vite 5 + React 18，纯 JS）
> 配套：`docs/narration.md`（现场口述稿）· `README.md`（快速上手）
> 上游依据：`work/20260907-222238-i-app-frida-dump-dex-android-apk/`（运行时取证全部证据链）

---

## 1. 为什么做这个 Demo（问题定义）

向 APP 开发者讲"你的客户端会被逆向、你的签名会被复刻、你的风控会被绕过"，靠 PPT 是没有说服力的。开发者的典型质疑是：

- "你说签名能复刻？复刻给我看。"
- "验证码不是有人机校验吗？"
- "就算你复刻了，我们后台风控难道发现不了？"

所以这个 demo 的设计目标不是"讲解"，而是**可交互的实证**：让开发者亲手走一遍业务流程——登录 → 选购 → 本地验证码 fixture 状态机 → 订单提交 → 支付链接生成——亲眼看到每一步在协议层发生了什么。fixture 只用于状态机演示，不被描述为 OCR 或原生验证码算法还原。

一句话设计纲领：**把运行时取证到的真实能力，包装成开发者可以亲手操作的可视化流程。**

## 2. 设计原则（先于一切实现决策）

| 原则 | 含义 | 落点 |
|---|---|---|
| 双模式隔离 | Mock（零生产交互，随时演示）与 Live（实弹，仅维护窗口外低频执行）物理分离 | `App.jsx` mode 状态机 + `ModeGate` 授权门 |
| 实弹全真实 | 实弹模式的请求**不含任何模拟标记**（UA/头/参数全按真实 App 形态）——核心结论"协议级复刻与正常订单无法区分"依赖于此 | `realApi.js` + `ModeGate` 默认档案生成 |
| 支付红线 | 支付链接只做拼接展示，代码里**不存在** order/pay 调用路径 | `payLinks.js` 顶层注释 + `realApi.js` 故意不实现 |
| 单进程 | 全程单页应用单线程顺序请求，天然满足单进程硬门禁 | 架构 inherent |
| 窗口硬校验 | 实弹请求发起前校验当前时间不在 06:00–06:15 维护窗口 | `realApi.inAllowedWindow()` |
| 预算硬上限 | 请求计数 ≤300，超限拒发；每次请求留脱敏日志 | `realApi.js` 计数器 + `RequestLog` |
| 证据可溯 | demo 里每个"真实还原能力"都能指回 case 证据（reflection.jsonl / reproduce_sign.py 15/15） | 每步 `AttackNotes` + 本文 §4 |

## 3. 真实性来源：demo 的每个环节背后是什么取证证据

这个 demo 不是"编一个像 App 的东西"——每个核心环节都对应一条已完成的运行时取证链：

| Demo 环节 | 真实依据 | 证据 |
|---|---|---|
| 短信验证码签名 `md5(deviceKey+mobile+timestamp)` | MessageDigest hook 捕获原始输入字节，公式破解 | `findings/login-signature.md` F1；`reproduce_sign.py` 15/15 PASS |
| 接口路径 `/xhr/front/user/register/vcode` 等 | Retrofit 注解运行时反射 dump（220 类 0 失败） | `extract/obs-mp34-annot2-t6-20260911/reflection.jsonl` |
| 请求模型字段（mobile/vCode/ydLogId/ydToken…） | 类字段反射（Moshi 序列化，字段名即 JSON key） | 同上 |
| HeaderMap 签名头机制 | okhttp 拦截器 `api.a.intercept` 定位 + clips_* 设备头值采样 | findings F3 |
| 设备绑定字段形态（32-hex deviceKey、clips_*） | 运行态采样：32-hex deviceKey 跨会话稳定；三枚 clips_* 跨会话稳定 | findings F1/F3/F4；派生源仍未验证 |
| 支付渠道与订单接口清单 | api.f 117 方法含 order/compose/submit 全族 | Swagger 文档（gen_openapi.py 生成） |
| 验证码三形态（字符/点选/滑块） | 真实环境存在多种验证码形态（M2.x 逆向分析进行中） | goals-and-progress G2 |

**这就是 demo 的说服力结构**：开发者看到的一切"攻击能力"，都能回答"你怎么知道的"——答案永远指向一次可复现的运行时取证实验。

## 4. 六步流程逐环节解析

每一步：用户看到什么 → 底层复刻了什么 → 教学击点。

### Step 0 模式选择
- **Mock**：随时可演示，全部本地模拟，请求只进日志。
- **离线算法研究**：不读取 `real-headermap.json`，使用固定 seed 生成 synthetic ID；短信签名公式可复现，设备 ID/Token 仅展示合成输出，随后可进入 Mock 购买流程。
- **Live（实弹）**：真实请求发往生产（真实登录、真实下单止步支付）。进入前过授权门：三确认 + 允许窗口自动校验 + 授权测试设备真实 HeaderMap + 预算重置；默认骨架仅供字段说明，不能解锁。
- 设计决策：实弹门禁做成**代码级强制**（窗口外 `liveRequest` 直接抛错拒绝），而不是靠操作者自觉。

### Step 1 验证码登录（`PhoneLogin.jsx`）
- 用户操作：输手机号 → 发送验证码 → 输码 → 登录。
- 底层复刻：点"发送"瞬间，`signature.buildVcodeSign(deviceKey, mobile, ts)` 用**页面内 1:1 还原的 md5 算法**计算签名——和真实 App 内计算结果逐字节一致（自带 15/15 样本自校验）。
- 展示"协议层实况"：POST body 原文 + 签名明文拼接串。
- 教学击点：**客户端可算 = 攻击者可算**，签名挡不住客户端外的复刻。
- Live 差异：真实发请求；登录响应分别提取 App Token 与 H5 会话 Cookie，分别写入 `MT-Token` 和 `MT-Token-Wap`（登录态保持）。

### Step 2 选购商品（`ProductSelect.jsx`）
- 用户操作：勾选商品（虚构条目）→ 调数量 → 提交订单。
- 底层复刻：接口参数结构来自注解 dump；Live 模式挂载时真实调用 `purchaseInfoV2` 并展示原始响应（脱敏预览）。
- 教学击点：接口元数据一次反射 dump 全部暴露；117 个接口见 Swagger。
- Live 边界：`purchaseInfoV2` 已有真实抓包基准；订单 compose/submit 的真实 body 尚未完成运行时取证，因此 Live 不发送猜测订单请求并停在这里。Mock 模式仍可继续演示后续本地步骤。

### Step 3 提交订单 + 验证码（`CaptchaVerify.jsx`）
- 用户操作：进入验证码 → 手动输入/拖动/点选或使用本地 fixture 快捷完成 → 可"刷新再来一张"循环 → 验证码成功后才提交订单。
- 三类验证码按固定顺序轮换（对应本地演示形态）：
  1. **字符/文字**：canvas fixture 图像 + 输入匹配
  2. **滑块拼图**：fixture 目标位置 + 位置容差校验
  3. **点选图片**：fixture 目标序列 + 顺序校验
- 计数器记录已通过 fixture 的轮数和覆盖类型，便于重复培训演示。
- 教学击点：三种形态共享一个显式状态机（ready/failed/passed/submit），成功前不调用订单提交；这不是三条真实算法路径。
- 边界：本地 fixture 只用于 Mock；实弹流程的真实验证码由操作者现场交互，真实 challenge/回调/刷新协议未验证。

### Step 4 填写地址（`AddressForm.jsx`，可选）
- 完整流程（客户开单时）必经；深夜/关单路径可从支付页"补填地址"回跳，或直接跳过。
- 教学击点：登录即注册 + 接码平台 = 批量养号；业务接口需属主校验防 IDOR。

### Step 5 选择支付（`PaymentSelect.jsx`）
- 渠道卡片（支付宝/微信/银联）。
- 教学击点：支付渠道界面本身没有安全属性——安全在支付参数由谁生成。

### Step 6 生成支付链接（`PayLinkResult.jsx`）
- 本地拼接三种渠道的 scheme/H5 形态（`payLinks.js` 模板，参数为演示值），带复制按钮。
- **红线可视化**：页面顶部红色横幅"绝不真实调用"；`realApi.js` 中不存在 order/pay 调用路径。
- 教学击点：客户端拼参 = 改价/伪造/钓鱼入口；支付参数必须服务端签发。

### 贯穿：请求日志（`RequestLog.jsx`）
- 每步协议交互实时落一条：时间/method/接口/脱敏 body/响应预览（live）/取证注解。
- 这是给开发者的"协议教材"——也是实弹"后台查无异常"结论的留痕载体。

### 侧栏：设备信息 + AttackNotes
- 设备信息卡：deviceKey、clips_token、MT-Token、网关——随模式切换说明来源；离线研究模式的 ID 明确标记为 synthetic。
- AttackNotes：每步的"攻击者视角 + 开发者应该注意"双段卡片，现场讲解的提词器。

## 5. 技术架构

```
src/
  App.jsx                # 状态机：mode(select/mock/gate/live/swagger) × step(0..5)
                         # + api 适配器（组件不感知 mock/live）
  lib/
    signature.js         # 还原的 md5 签名算法（自校验内置）★ 全 demo 最核心文件
    deviceKey.js         # 设备指纹可复刻性演示（环境指纹 → 32-hex）
    payLinks.js          # 支付链接拼接模板（🚫 仅展示）
    mockApi.js           # Mock 网络层 + logStore 事件流
    realApi.js           # 实弹网络层：窗口/预算/档案三重硬门禁 + 真实 fetch
  components/
    ModeGate.jsx         # 实弹授权门（三确认+档案自动生成+窗口校验）
    FlowHeader.jsx       # 步骤条 + AttackNotes 卡片
    PhoneLogin.jsx       # Step1 …（六步组件）
    RequestLog.jsx       # 底部协议日志（订阅 logStore）
    SwaggerPage.jsx      # API 全景文档页（swagger-ui-dist 本地打包，离线可用）
```

**关键架构决策：协议适配器（adapter pattern）**

步骤组件只调用 `api.sendSms / api.login / api.purchaseInfo / api.submitOrder`，不关心底层是 mock 还是 live。App 按 mode 装配适配器：

- mock 实现：本地函数 + `recordApi` 落日志
- live 实现：`realApi.live.*`（真实 fetch，窗口/预算门禁内）

好处：① 演示模式与实弹模式的 UI 流程完全一致（培训时无模式切换割裂感）；② 新增步骤只写一次组件；③ 门禁逻辑集中一处，不可绕过。

**为什么实弹也要走同样的 UI 流程**：演示的说服力来自"攻击者的操作和正常用户一模一样"。如果实弹是个黑盒脚本，开发者感受不到"和你日常操作无异"这一点。

**HeaderMap 档案的边界**：实弹授权门仍显示默认字段骨架，但该内容是 synthetic 结构说明，不能解锁 Live；只有测试设备真实取证档案才标记 `verifiedCapture=true`。无真机演示应进入离线算法研究台，不再依赖 `real-headermap.json`。

## 6. 支付红线的技术实现

"不真实调用支付"不是口头承诺，是结构：

1. `realApi.js` 的 live 对象里**没有** order/pay 方法（故意缺失）；
2. `payLinks.js` 只返回字符串模板，无任何 fetch/跳转代码；
3. `PayLinkResult` 的"复制"仅写剪贴板文本，不触发 scheme；
4. 页面红色横幅 + AGENTS.md「💳 支付红线」章节双重声明（保护线上账务）。

## 7. 两条演示动线

| 动线 | 流程 | 适用 |
|---|---|---|
| 🌙 深夜彩排/系统关单 | 登录 → 选商品 → 验证码循环自动过 → **直达支付链接**（下单失败属预期，日志留痕） | 排练、非窗口时段 |
| 🎤 现场正式演示（客户开单） | 登录 → 选商品 → 真实下单 → 验证码 → 地址 → 支付 → 链接生成 → **运维查单查无异常** | 维护窗口外的授权低频 runbook 执行 |

现场最强一击（口述稿 `docs/narration.md`）："请运维现在查这笔订单——它在协议层和正常用户没有任何区别。攻击者可以在你们毫不知情的情况下完成这一切。"

## 8. 与 case 证据链的关系

```
work/20260907-.../
  findings/login-signature.md     ← 签名公式与接口清单的权威出处
  findings/reproduce_sign.py      ← 签名离线验证（demo signature.js 的 Python 对应物）
  notes/goals-and-progress.md     ← 目标-里程碑总档（G5 = 本 demo）
  notes/steps-log.md              ← 全部实验留痕（S6-6/7/8/9/10 为 demo 相关）
  notes/next-phase-plan-...md     ← runbook（实弹模式的执行依据）
  AGENTS.md                       ← 硬门禁/支付红线/自动化边界（demo 遵守的规则来源）
```

demo 中每个还原能力 → 都能沿此链路指回一次可复现实验。修改 demo 涉及"还原能力"时，先确认证据链支持。

## 9. 构建与运行

```bash
npm install
npm run dev        # http://localhost:5173
npm run build      # 产物 dist/（静态托管即可，内含 Swagger UI 全离线）
```

Swagger 文档再生成（取证数据更新时）：`python hooks/gen_openapi.py` → 覆盖 `src/lib/openapi.json` → rebuild。

## 10. 已知边界与后续方向

- 实弹请求头与真实设备完全对齐依赖 M1.6（测试设备 HeaderMap 现场抓取）——自动生成档案为骨架版，网关强校验时会被拒（该结果亦有演示价值）。
- 无真机时可演示已确认的 vcode MD5 公式和稳定输入/输出关系；native deviceKey 来源、clips_* / MT-Device-ID 派生算法、MT-R 仍未验证，synthetic 输出不能用于 Live。
- 本地验证码 fixture 只证明 Demo 状态机可重复演示；真实验证码虽已确认承载于 `CaptchaWebView`，但 challenge/协议/回调/刷新仍是 G2 未验证项。
- 支付链接模板为公开协议形态整理，实际字段以现场抓包校准（永远只读不调）。
- Swagger 文档随取证批次更新重新生成。


---

## 11. v2 追加（2026-09-12）：实弹双路径 + 真实档案

### 11.1 实弹链路当前形态
```
浏览器 UI ──(同源 /api/live)──▶ live-server.mjs（Node 原生 https）
                                   ├─ 硬门禁：peak 06:00–06:15 拒发 / 预算 ≤300 / 档案必须
                                   ├─ 头构造：profile 原样转发（全真实，含 Cookie/Origin/空 csrf）
                                   ├─ cookie jar + 证据 JSONL（evidence/live-requests.jsonl）
                                   └─ ▶ 生产网关（app/h5.moutai519.com.cn）
```
- 浏览器指纹永不外泄（实测浏览器直发被 ESA 480/4010 拒）
- CORS 由同源代理解决（/mt-app、/mt-h5 备用直连通道保留）

### 11.2 实弹双路径（同一套代码，两种档案）
| 档案 | 结果 | 演示叙事 |
|---|---|---|
| 自动生成档案（本机指纹） | 服务端 480/4010 拒绝 | "被识别为非注册设备——设备绑定防线有效" |
| 📂 真实档案（mitm 抓包，由操作者选择本地 JSON） | **HTTP 200 · code 2000 真实业务数据** | "与真实 App 逐头一致，后台查无异常" |

### 11.3 purchaseInfoV2 已完整还原（目标接口）
- 请求规范/响应结构/拒绝矩阵：`work/.../findings/purchase-info-v2-assessment.md`
- 关键结论：**H5 域无应用层签名，鉴权仅 JWT Cookie（30 天）**；demo 管线已实测 200 复现
- UI 内 `purchaseInfo` 调用自动使用真实 body 模板 `{hot:true,spuId,jt:anonymous}`

### 11.4 App 与 H5 主机边界

- App 原生认证：`app.moutai519.com.cn`（验证码、短信登录）。
- App 原生订单接口：`app.moutai519.com.cn`（compose/submit，当前仅有接口注解和模型字段证据）。
- 嵌入 H5 WebView 商品信息：`h5.moutai519.com.cn`（`purchaseInfoV2`，Cookie 鉴权，真实 HTTP 200 已复现）。
- 因此“App 原路径使用 H5 API”应理解为 App 内嵌 H5 模块的局部路径，不是全局 API 基址。

### 11.5 全局错误可见化
`index.html` 注入 window error/unhandledrejection 钩子——JS 崩溃栈直接渲染在页面上，演示现场排障不用开 DevTools。
