# i 茅台逆向安全测试：GLM 接管快照（2026-09-11）

## 一句话结论

当前已经完成 APK wrapper、运行时抽取物和 native loader 边界的离线取证；此前有 Frida 延迟附加成功记录，但没有完成可验证的业务 DEX 导出，也没有证明绕过加固检测或异常检测。

## 已完成

1. **案例与范围**
   - 目标：i 茅台 v1.9.12，包名 `com.moutai.mall`，versionCode `10912`。
   - 继续遵守现有 `scope.md`：内部授权、offline；未构造、发送或重放网络请求，未处理凭据、Token 或 OTP。

2. **Frida / 动态观察**
   - 旧实验中，延迟附加曾取得业务类和方法声明：209 个唯一类、1,937 条方法声明、0 次反射失败。
   - 已观察到 `LoginActivity.login`、`authLogin`、`getVerifyRequest`、`api.f.E0`、`api.f.b1`、`api.a.intercept` 等声明。
   - 该结果没有提供方法体、登录签名算法或签名输入输出。
   - 近期复测未执行成功：原授权设备 `82e459fc0920` 不在线；当前可见设备没有 root/Frida server。因此近期没有新的动态结论。

3. **DEX / wrapper 静态取证**
   - `extract/runtime-app-files-25103-20260909-raw/classes.jar` 实际是 DEX 文件，且与 APK `classes.dex` 逐字节相同，不能视为已解密业务 DEX。
   - wrapper 链已经确认：`AW/AP → H.a(ApplicationInfo) → DexHelper → native loader/fallback → MTApp`。
   - JADX 对 `H.b(ApplicationInfo)` 仍有 314 条指令未恢复，属于未完成静态缺口。

4. **native 静态取证**
   - IDA 已分析 `libCryptoSeed.so` 的入口和可执行段。
   - 名称为 `CryptoUtil_getSeed` / `CryptoUtil_getPrivateKey` 的地址落在非执行零填充区，不能据此认定它们是有效的登录签名实现。
   - 真正可执行区域出现 `/proc/self/maps`、`/proc/self/cmdline`、Bangcle 标记、包名检查、`DexHelper` 和内存页变换逻辑。
   - 当前更可靠的归类是“加载/自检/内存变换边界”；与登录签名的关系仍为 `UNVERIFIED`。

## 已确认的失败或不应直接重试路径

- Frida-dexdump spawn 分支、冻结进程、手工 `dlopen + JNI_OnLoad`。
- 仅按名称 hook `nativeLoad`、MIUI forcedark 分支。
- JNIEnv 表补丁、pthread 入口替换、base.vdex 快照。
- 将运行时 `classes.jar` 当作明文业务 DEX。
- 将 `libCryptoSeed.so` 的 JNI 字符串名称直接当作登录签名证据。

这些路径已有记录；重试前需要出现新的输入条件或新的证据，否则只能重复既有失败。

## 尚未完成 / 不应宣称已完成

- 没有可验证的明文业务 DEX。
- 没有确认完整 Frida 注入链在当前设备上可用。
- 没有确认红色界面确实由哪一项检测触发。
- 没有证明绕过了加固检测。
- 没有证明绕过了异常检测。
- 没有拿到登录请求签名的真实调用者、参数、密钥来源、返回值或最终 digest。
- 没有进行网络请求构造、发送或重放。

## 现有关键证据文件

- `evidence/new-direction-loader-native-boundary-20260911.md`
- `evidence/mp34-reflection-results-20260910.md`
- `evidence/mp34-annotation-device-block-20260910.md`
- `notes/steps-log.md`
- `timeline.md`
- `workitems.md`

## 接管边界

GLM 接管时应以本文件、`scope.md` 和上述 Evidence 文件为准。当前阶段的客观状态是：**离线 loader/native 边界已完成初步裁定，动态验证和登录签名归因仍未完成**。后续技术路线由接管模型根据这些证据自行判断。

