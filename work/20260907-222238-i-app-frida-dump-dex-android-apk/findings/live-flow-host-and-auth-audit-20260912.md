# Live 全流程接口与主机映射审计

> Case: `20260907-222238-i-app-frida-dump-dex-android-apk`  
> 日期：2026-09-12  
> 范围：demo 当前实现、运行时接口注解、已保存的脱敏/历史流量证据。  
> 初始审计未新增请求；后续有效账号验证补充了 1 次验证码、1 次登录和 1 次 H5 查询，未触发订单或支付请求。

## 结论先行

GLM 所说的“App 原路径使用 H5 API”只对嵌入 App 的 H5 WebView 商品购买信息模块成立，不是整个 App 的 API 基址。

| 流程 | 接口 | 当前主机 | 证据与状态 |
|---|---|---|---|
| 发送验证码 | `POST /xhr/front/user/register/vcode` | `app.moutai519.com.cn` | App `Endpoints` 与 `api.f.s0`；Live UI 已实测 HTTP 200/code 2000 |
| 短信登录 | `POST /xhr/front/user/register/login` | `app.moutai519.com.cn` | App `api.f.E0`；异常状态测试账号已实测 HTTP 480/“用户已注销” |
| 商品购买信息 | `POST /xhr/front/mall/item/purchaseInfoV2` | `h5.moutai519.com.cn` | H5 WebView 抓包；已实测 HTTP 200/code 2000；鉴权为 H5 Cookie |
| 订单组合 | `POST /xhr/front/trade/order/standard/compose/v2` | `app.moutai519.com.cn` | App `api.f.e` 注解；没有真实订单运行时请求证据 |
| 订单提交 | `POST /xhr/front/trade/order/standard/submit/v2` | `app.moutai519.com.cn` | App `api.f.T0` 注解；没有真实订单运行时请求证据 |
| 地址、支付链接展示 | 本地 UI 状态 | — | 当前 demo 未发真实地址/支付请求；支付调用永久止步 |

因此，当前 demo 的主机选择应保持：**认证与原生订单接口走 app，嵌入 H5 的 purchaseInfoV2 走 h5；两者登录态字段独立**。

## 本轮统一审计结果

### F1. 登录态向后续请求传播

此前登录成功后只更新了 realApi 模块状态，没有同步 React 中的 profile；并且没有把新令牌同时写入 App 头和 H5 Cookie。现已修复：

- 登录响应抽取到令牌后，同时更新 React profile 与 realApi profile；
- App 侧写入 `MT-Token`；
- H5 侧使用登录响应独立返回的 Cookie 会话写入 `MT-Token-Wap`，不再把 App Token 当作 H5 Token；
- 同时兼容 JSON 令牌/会话 Cookie 和登录响应 `Set-Cookie`；缓存会话保存两类登录态与已验证标志，重启后仍能正确恢复；
- 恢复 localStorage 登录态时走同一套更新函数，不再直接修改旧对象。

这修复了“登录成功但下一步仍携带旧登录态”的代码级问题。缓存恢复实测发现 App Token 直接替换 H5 Cookie 会返回 4011 invalid signature，已改为分别缓存；随后重新登录取得两类会话，并完成了“登录→H5 purchaseInfoV2”及重载后的缓存复用验证。

### F2. Cookie 更新优先级

本地代理此前可能让静态档案里的旧 Cookie 覆盖登录响应下发的动态 Cookie。现改为合并 Cookie，并让响应 `Set-Cookie` 覆盖同名档案值；App 与 H5 请求分别按对应头集合构造。

### F3. purchaseInfoV2 的状态不能被误判为成功

Live 选购页现在会显示 purchaseInfoV2 的 HTTP 非 200 错误，并在没有 HTTP 200 响应时禁用“提交订单”。这避免把 401/480/481 等上游拒绝继续包装成本地订单流程。

### F4. 订单 compose/submit 目前不能宣称已还原

旧 UI 的实弹 submit body 是猜测结构（`orderId/items/addressToken`），与注解得到的 `ComposeOrderRequestWrapper` 和 `SubmitOrderRequestV2Wrapper` 字段集合不一致；UI 也没有先调用 compose 再使用其结果调用 submit。

本轮已移除这条猜测实弹发送路径：Live 模式在订单模板没有真实抓包基准前直接停止，并显示原因；Mock 模式仍可完整演示后续 UI。这样不会把错误请求或本地伪造订单误报为真实链路成功。

### F5. 后续本地步骤与支付边界

验证码演示、地址填写、支付方式选择和支付链接拼接属于本地演示状态；真实支付接口没有调用路径。另修复了字符验证码组件缺失 `useRef` 导入导致的运行时崩溃。

### F6. 低频门禁与证据脱敏

客户端和本地代理均保证相邻真实请求至少间隔 2 秒；客户端会串行等待而不是让登录后的自动 purchaseInfo 请求静默失败。代理返回给 UI、写入新增证据的请求头和响应预览均做敏感值脱敏；真实值只在发送请求的内存路径使用。

### F7. 订单写模型与验证码组件静态归因

对本地 APK 反射结果和生成的 OpenAPI 进行只读整理，得到以下可确认字段链：

- `POST /xhr/front/trade/order/standard/compose/v2` 对应 App 方法 `api.f.e`，请求模型为 `ComposeOrderRequestWrapper`，字段为 `actParam`、`addressInfo`、`deliverMethod`、`itemList`、`selfPickUpSite`、`shopSelfPickUpInventoryInfo`。
- `POST /xhr/front/trade/order/standard/submit/v2` 对应 App 方法 `api.f.T0`，请求模型为 `SubmitOrderRequestV2Wrapper`，除组合阶段字段外还包括 `instantDeliveryInfo`、`invoiceSubmitDTO`、`payChannel`、`selectedCoupon`、`source`、`sourceId`、`transactionId` 等字段。
- 两个 App 方法都带动态 HeaderMap 参数；当前证据只证明接口、模型和字段存在，不证明任一订单 body 已在运行时成功发送。
- APK 布局包含 `com.netease.nis.captcha.CaptchaWebView`，说明订单验证码 UI 使用网易验证码 WebView 组件；现有反射结果没有给出订单验证码的真实 challenge、校验回调或刷新协议。
- `CopyInfoVerifyCodeModel(md5,timestamp)` 属于用户资料复制/迁移验证码模型，不能当作订单验证码协议；没有将其误用到订单链路。

因此，当前可以把订单写链路准确建模为“compose/v2 → 风控验证码 → submit/v2”的状态机，但仍不能凭静态字段拼出可发送的真实请求，也不能宣称订单验证码已验证。

### 反射类型补充（静态事实）

从同一份运行时反射结果进一步确认：

- `ComposeOrderRequestWrapper.actParam` 是 `String`，`addressInfo` 是 `ComposeAddressUpload`；
- `SubmitOrderRequestV2Wrapper.actParam` 是 `String`，`addressInfo` 是 `ShipAddressResult`；
- submit 的 `source`、`sourceId`、`transactionId` 是 `String`，`payChannel` 是 `int`；
- `deliverMethod` 两阶段均为 `DeliverMethod`，其暴露 `value` 和 `desc` 访问器。

这只提高字段类型层面的静态还原精度，不提供真实字段值、嵌套对象内容、服务端交易字段或验证码协议；下次取证仍需按阶段分别记录完整 JSON。

## 证据边界

- App 原生基址：`findings/login-signature.md`、`extract/obs-mp34-annot2-t6-20260911/reflection.jsonl`。
- H5 商品链路：`findings/purchase-info-v2-assessment.md`、`evidence/mitm-flows-20260912.jsonl`。
- Live UI 认证实测：`notes/steps-log.md` 的 S6-27～S6-29 及脱敏 `evidence/live-requests.jsonl`。
- OpenAPI 只提供订单接口和模型字段定义，不等价于订单请求已运行成功。

## 当前可确认范围

已确认：App 域验证码请求、App 域异常账号登录结果解析、H5 域 purchaseInfoV2 请求复现、登录态传播修复、订单 compose/submit 模型字段与调用顺序的静态归因、订单猜测请求阻断、支付止步。

未确认：compose/submit 的真实 body 与完整响应、订单验证码的真实 challenge/校验/刷新协议。因此这些项目仍保持未验证，不在培训材料中宣称“完整真实下单已打通”。
