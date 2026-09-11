# 本地历史材料中的登录签名调用链阴性核验（2026-09-10）

## 范围

只读取当前案例的 JADX 输出、既有证据和旧版本地项目源文件，搜索登录入口、请求层、签名工具和 `CryptoSeed` 调用点。没有执行旧脚本、没有输入凭据、没有构造或发送请求。

## 直接观察

- 当前 `jadx-out/sources/com/moutai/mall/` 只生成 `IsoService.java` 与 `R.java`；`IsoService` 仅包含隔离服务与 `H.cis(...)` 调用，没有登录请求或签名实现。
- 当前可见源码中：`authLogin`、`phoneCode`、`sendCode`、`LoginActivity`、`okhttp3.Interceptor`、`addInterceptor`、`getPrivateKey`、`CryptoUtil`、`HmacSHA` 均未命中。
- 当前可见源码虽有 `MessageDigest` 与 `Signature` 命中，但命中主要位于 `com.coralline.sea` 风险/完整性代码；既有证据已将其区分为风险遥测或 APK 签名校验，未形成业务请求签名链。
- 旧项目 `E:\code\py\reverse-engineering\frida\imoutai` 的 Java/Kotlin/smali/JS 搜索没有命中 `authLogin`、`phoneCode`、`sendCode`、`LoginActivity`、`addInterceptor`、`getPrivateKey`、`CryptoUtil` 或 `signature`。

## 裁定

- **Evidence**：现有可读业务源码范围只有 `IsoService`/资源类；目标登录入口、请求层和 `CryptoSeed` 调用者均未在本地历史材料中出现。
- **Finding**：已排除“从现有可见源码或旧项目直接恢复登录签名”的简单路线。
- **Inference**：如果签名实现确实存在，候选位置只能是当前尚未取得的运行时隐藏代码、native/生成代码或未提供的内部可调试构建；这不是存在性证明。
- **UNVERIFIED**：签名算法、输入字段顺序、密钥来源、调用时序，以及是否存在登录请求签名。

## 停止条件

继续在同一批可见源码中扩大关键词列表不会产生更强证据；后续路线必须取得新的可验证输入（例如脱敏离线基准或新的本地代码/快照），否则不得生成签名复现脚本或宣称恢复成功。
