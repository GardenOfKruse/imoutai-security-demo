# H loader fallback 静态证据（2026-09-09）

## 目的

在不启动目标、不注入、不发起网络请求的前提下，复核原始 APK 中 `com.secneo.apkwrapper.H` 的 native loader fallback，确认 `libDexHelper` 的选择、落地与 native 入口。该记录只描述 JADX fallback 反编译得到的可复核行为，不把壳加载链等同于业务 DEX 或登录签名恢复。

## 输入与方法

- 输入 APK：`sample/imoutai-1.9.12.apk`
- 反编译产物：`extract/jadx-h-fallback-20260909/H.java`
- 方法：JADX `--decompilation-mode fallback --show-bad-code` 单类离线反编译；随后按源码行号回读关键分支。
- 网络边界：未发起请求、未构造或重放请求，未读取凭据、token、cookie。

## 直接证据

1. `H.g` 被初始化为 `DexHelper-x86`，`ARM_LIBRARY` 为 `DexHelper`（`H.java:63-68`）。
2. `H.b(ApplicationInfo)` 先检查 Android SDK >= 19，并调用 `d()` 获取运行时架构类别；无法识别时直接返回（`H.java:411-429`）。
3. 该方法以 `ApplicationInfo.sourceDir` 作为 ZIP 输入，在 `ApplicationInfo.dataDir/.cache` 下构造目标文件名 `lib` + `H.g` + `.so`，即在当前字段值下为 `libDexHelper-x86.so`（`H.java:430-451`）。
4. 架构为 64 位时优先读取 `lib/arm64-v8a/libDexHelper-x86.so`；未命中时读取 `lib/x86_64/libDexHelper.so`。32 位分支依次尝试 `lib/armeabi-v7a/libDexHelper-x86.so`、`lib/armeabi/libDexHelper-x86.so`，再回退到 `lib/x86/libDexHelper.so`（`H.java:452-528`）。
5. 命中 ZIP entry 后，以 4096 字节缓冲区复制到目标文件；复制完成后调用 native `sl(targetAbsolutePath)`（`H.java:529-570`）。未命中 entry 时返回，不调用 `sl`（`H.java:523-528`）。
6. 类中存在 `H.a(ApplicationInfo)` 的加载入口：先尝试 `System.loadLibrary(c() ? "DexHelper-x86" : "DexHelper")`，异常时再调用上述 fallback（`H.java:286-316`）。

## 这次实验确认了什么

- 原始 stub DEX 中确实包含一个按 ABI 从 APK ZIP 提取 `libDexHelper`、再把绝对路径交给 native 方法的 Java fallback。
- 这为此前运行时观察到 `libDexHelper.so` 参与初始化提供了静态装载链证据。
- 该链路是 native helper 的加载路径，尚未证明它负责解密出业务 DEX，也未证明它负责登录签名。

## 未确认项（保持 UNVERIFIED）

- `sl(String)` 的 native 实现具体做什么，以及是否继续加载/解密其他阶段：**UNVERIFIED**。
- `d()` 的确切架构返回值与设备上实际走过的 ZIP entry：**UNVERIFIED**（本轮没有启动或注入目标）。
- `libDexHelper` 后续是否产生可读取的业务 DEX、是否使用 `InMemoryDexClassLoader`/`DexFile::OpenMemory`：**UNVERIFIED**；静态 IDA 字符串/XREF 只说明存在相关线索。
- 红色界面、加固检测、异常检测与上述 fallback 的因果关系：**UNVERIFIED**。
- 登录接口、签名参数顺序、密钥来源及逐字节复现：**UNVERIFIED**。

## 复核定位

- 生成源码：`extract/jadx-h-fallback-20260909/H.java`
- 关键方法起点：`H.java:411`
- 关键 ABI/ZIP 分支：`H.java:452-528`
- 复制与 native 调用：`H.java:529-570`
