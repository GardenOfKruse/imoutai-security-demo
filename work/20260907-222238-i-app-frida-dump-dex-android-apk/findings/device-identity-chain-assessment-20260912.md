# 设备身份链调研（2026-09-12）

## 结论

当前样本中至少存在两条不能混为一谈的身份链：

1. RiskStub.dex 的 udid 链：静态代码已经足够还原，可在离线环境做等价 Mock。
2. 业务登录/订单请求的 deviceKey 与 clips_* / MT-Device-ID 链：运行态已看到输出和跨会话稳定性，但尚未找到它们与 RiskStub.udid 的调用连接，不能把两者当成同一个设备码。

因此，Demo 应同时展示“已确认可复现的 RiskStub UDID 算法”和“业务设备绑定字段仍未验证”的边界。

## A. 已确认：RiskStub UDID 链

### 输入与采集

RiskStub.dex 静态代码读取或整理了以下设备因子：

- Settings.Secure.android_id；
- Build.MODEL、Build.BRAND、Build.SERIAL；
- imei、imsi、MAC 等 Telephony/Wi-Fi 因子；
- 平台、应用版本、包签名和风控 SDK 自身的运行状态字段。

证据：jadx-out/sources/com/coralline/sea/b4.java、ja.java、w2.java。

### 因子选择与 UDID 生成

在 Android 8 以上的分支，aa.b(JSONObject) 按以下顺序选择有效因子：

android_id → drmid → mac → imei → serial

有效性检查会排除空值、常见占位值、重复字符和明显的伪造 MAC。选中的原始因子随后在 aa.a(String) 中执行：

UUID.nameUUIDFromBytes(factor.getBytes())

如果没有有效因子，则使用随机数生成 UUID。生成结果写入 udid，并记录 udid_from。

证据：jadx-out/sources/com/coralline/sea/aa.java 的因子选择、UUID 生成和来源标记逻辑。

### 持久化与服务端覆盖

生成后的 JSON 写入应用私有 SharedPreferences("tmp_d2") 的 null_h 字段，并经过 SDK 自己的编码/加密包装；后续启动优先读取已保存的 udid。若服务端返回 udid_used，SDK 会采用服务端下发的 udid，因此最终值不一定只由本地因子决定。

证据：z9.java、aa.java 的持久化和服务端响应处理逻辑。

### RiskStub client_token：静态算法已确认

`com.coralline.sea.b4.a()` 另行构造时间型 `client_token`，与业务 `deviceKey`、`clips_*` 无直接等同证据。给定固定的毫秒时间 `T`，其结构为：

```text
U = UUID.nameUUIDFromBytes(String.valueOf(T).getBytes()).split("-")
S = String.valueOf(T / 1000)
client_token = U[0] + S[0:3] + U[1] + S[3:5] + U[2] + S[5:8] + U[3] + S[8:] + U[4]
```

其中 `c7.c` 已确认为空字符串。该值随时间变化，不能作为稳定设备码或有效生产会话凭据；Demo 仅使用固定时间做可重复 Mock。

证据：`jadx-out/sources/com/coralline/sea/b4.java`、`c7.java`。

## B. 已观察但未连接：业务设备身份

| 字段 | 当前证据 | 结论 |
|---|---|---|
| deviceKey | 32 位十六进制，跨会话稳定；参与 MD5(deviceKey + mobile + timestamp) | 来源未归因，不能等同于 RiskStub udid |
| clips_* | 三个带 clips_ 前缀的 Base64 形态 Token，跨会话稳定 | 真实派生算法和 header 映射未还原 |
| MT-Device-ID | JWT 的 deviceId 与 App 端某个 clips_* 值对应 | 已确认存在绑定关系，但不能由此推出生成算法 |
| MT-R | 只有字段/占位使用线索 | 算法未验证 |
| Cookie / JWT / MT-Token | 登录或服务端返回 | 不能离线凭空生成有效会话 |

证据：findings/login-signature.md、findings/purchase-info-v2-assessment.md、extract/obs-annot5b-20260911/。

## C. Mock 处理建议

### 可以做成“算法等价 Mock”

- RiskStub udid：允许输入一组脱敏设备因子，按同样的有效性检查和 UUID v3 规则生成；标记为 riskstub-udid-confirmed。
- RiskStub client_token：可以按静态代码展示其“时间戳 + UUID”拼装结构，但它是时间相关的 SDK Token，不能与业务 clips_* 混用。
- 短信签名：继续使用已验证的 MD5(deviceKey + mobile + timestamp)。

### 只能做“格式/状态 Mock”

- deviceKey、clips_*、MT-Device-ID、MT-R：只生成 synthetic 值和证据 trace，不宣称真机兼容，不允许进入 Live。
- Cookie、JWT、MT-Token：只在 Mock 中生成模拟会话，不伪造生产有效凭据。

## D. 继续还原所需证据

下一步应在授权设备上做同一请求窗口的调用关联：

1. 记录 LoginActivity 构造 deviceKey 的调用栈和输入输出；
2. 同时记录 CryptoUtil / libCryptoSeed.so 的调用者关系；
3. 在同一个 api.a.intercept 请求上记录 clips_* 头部实际映射；
4. 对比这些输出是否与 RiskStub.udid、本地 SharedPreferences 或服务端返回的 ctdid 相等。

在这条关联证据出现前，不能把 RiskStub 的可复现 UDID 算法升级为业务设备码算法。
