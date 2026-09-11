# 可见代码边界复核（2026-09-09）

## 复核目的

确认目前可离线直接读取的 APK/反编译代码是否包含业务登录实现，避免把 manifest 中的类名、RiskStub 的安全遥测或签名校验代码误归因到登录请求签名。

## 直接观察

### APK ZIP 条目

对 `sample/imoutai-1.9.12.apk` 做只读 ZIP 条目枚举，DEX-like 文件只有：

- `assets/RiskStub.dex`
- `classes.dex`

没有列出第二个 `.dex`、`.cdex`、`.dve`、`.vdex` 或 `.odex` 条目。APK 中另有 `libdexvmp.so`、`libdexjni.so` 和两个 `libDexHelper` ABI 变体，但它们是 native ELF，不是可直接反编译为 Java 业务实现的 DEX。

### JADX 输出树

在 `extract/jadx-static-badcode-20260909-v2/sources/com/moutai/mall/` 下当前只有：

- `R.java`
- `IsoService.java`

没有生成 `LoginActivity.java` 或其他 `com.moutai.mall.module.login` 业务实现。manifest/字符串池中仍能看到 `com.moutai.mall.module.login.LoginActivity` 等名称，但名称存在不等于对应方法体已在当前可见 DEX 中恢复。

### RiskStub 与签名关键词的边界

- `com.coralline.sea.s5` 的 `hxb_login` 路径组装登录场景、账号、触摸/传感器事件并交给 `push`；这是风险遥测链。
- `com.coralline.sea.a0/e7/v7` 等代码读取或比较 APK/证书 `Signature`，属于应用签名/完整性检查，不是登录接口的业务签名算法。
- 当前静态输出没有形成“LoginActivity → 请求构造 → 参数规范化 → 签名函数 → 发送”的连续调用链。

## 结论

可见静态代码边界已确认，但业务登录实现仍不在可直接读取的 DEX 中；它可能位于运行时加载的隐藏阶段、另一个未落地的内存对象，或未提供的内部可调试构建。这里的“可能”均为推断，不能作为已证实事实。

因此：

- “APK 中存在 LoginActivity 字符串”：**CONFIRMED**。
- “当前离线反编译已得到 LoginActivity 方法体”：**NOT CONFIRMED**。
- “已定位登录签名算法/密钥/参数顺序”：**UNVERIFIED**。
- “RiskStub 的 Signature 代码就是登录签名”：**UNVERIFIED，且静态语义不支持该归因**。

## 复核输入

- APK：`sample/imoutai-1.9.12.apk`
- JADX 输出目录：`extract/jadx-static-badcode-20260909-v2/`
- RiskStub 静态证据：`evidence/riskstub-loginchecker-static-20260909.md`
- ZIP 枚举：2026-09-09 离线只读枚举，未修改 APK。

