# Live 订单链路就绪矩阵（2026-09-12）

## 结论

当前 Demo 已经可以安全演示 Mock 全流程，并能在授权档案存在时复现 App 登录与 H5 purchaseInfoV2 的请求边界；但还不能把“订单真实下单已打通”作为事实。

阻断点不是 HeaderMap 文件路径，而是以下三类运行态证据尚未取得：

1. compose/v2 的真实请求 body、响应和服务端生成的交易字段；
2. CaptchaWebView 的真实 challenge、校验回调、通过结果和刷新协议；
3. submit/v2 使用的完整 body，以及验证码通过结果如何进入该 body 或 HeaderMap。

## 当前状态

| 链路 | 已确认 | 未确认 | Demo 处理 |
|---|---|---|---|
| 短信签名 | MD5(deviceKey + mobile + timestamp)，15/15 逐字节通过 | deviceKey 的 native/服务端来源 | 离线可复现，Live 仅使用授权档案 |
| App 登录 | App 域接口、模型和 HeaderMap 注入位置 | 其他账号/设备的可迁移性 | Live 保留真实请求入口 |
| H5 商品信息 | purchaseInfoV2 的域名、body 形态和成功样本 | 当前凭据有效期、后续下单关联字段 | 成功响应才允许进入选购 |
| 订单组合 | compose/v2、ComposeOrderRequestWrapper 字段集合 | 真实 body、响应字段和交易状态 | Live 不发送猜测 body |
| 订单验证码 | com.netease.nis.captcha.CaptchaWebView | challenge、回调、刷新和服务端校验 | Mock 只演示状态机，不冒充原生算法 |
| 订单提交 | submit/v2、SubmitOrderRequestV2Wrapper 字段集合 | 完整真实 body、响应和幂等关系 | 无真实 submitBody 时硬阻断 |

| 支付 | 支付模型和静态拼接线索 | 无需验证真实支付调用 | 永久禁止真实调用 |
| 设备标识 | RiskStub udid 算法可离线复现 | 业务 deviceKey、clips_* 派生链 | 分层 Mock，不混用 |

### 静态覆盖边界补充

对 `jadx-out` 源码/资源、APK 容器目录和 APK 二进制字符串做了交叉核对：当前可见证据包括 `yd_dialog_captcha*.xml` 中的 `CaptchaWebView` 和 `yd_captcha_*` 资源名；没有发现 `com.netease.nis.captcha` SDK 类体、challenge URL/参数或回调方法名。该“未发现”不能证明 SDK 未被拆分/动态加载，但足以说明当前静态材料不能推出订单验证码协议，仍需运行态记录 WebView 初始化、回调与刷新事件。

## 回机后的最小取证动作

只需要补一轮低频、单进程、授权测试账号的运行态证据，不需要重新做全量逆向：

1. 在同一订单草稿窗口记录 api.f.e（compose）请求/响应；
2. 记录订单验证码 WebView 的初始化参数、回调方法名和刷新事件，只保存脱敏字段名与长度；
3. 在验证码通过后记录 api.f.T0（submit）的请求/响应；
4. 关联三者的 transactionId、订单草稿标识和验证码通过结果；
5. 将真实值保留在本地，报告只提交字段结构、调用顺序和证据哈希。

若第 1～4 步没有完整响应，不得把字段模型拼成“可用请求”；应继续标记为 UNVERIFIED。

## 完成标准

只有同时满足以下条件，才可把 F 组状态改为“真实下单已验证”：

- compose 请求与 App 运行态逐字段一致；
- compose 响应中的交易字段进入 submit 请求；
- 订单验证码通过结果与 submit 调用存在同一请求窗口关联；
- submit 返回明确的订单创建结果；
- 支付接口没有被调用；
- 全程有脱敏日志、请求预算和运维取消记录。

在此之前，培训演示应使用 Mock 订单，并明确展示“真实订单写入仍未验证”。
