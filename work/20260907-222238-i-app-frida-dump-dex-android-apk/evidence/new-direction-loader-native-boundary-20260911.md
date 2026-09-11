# 新方向：加载器边界与辅助 native 角色裁定（2026-09-11）

## 目的与范围

在原有 Frida 动态分支受设备环境阻塞、可见业务 DEX 不完整的前提下，对 APK 内运行时 `classes.jar`、wrapper 入口和 `libCryptoSeed.so` 做离线只读静态关联。未调用 JNI，未修改 APK/设备文件，未访问网络，也未读取或写入凭据。

## Evidence

### 1. 运行时缓存不是解密业务 DEX

- APK 内 `classes.dex`：23,505,324 字节，SHA-256 `66598e881fd7860a8091e61aafc45f3cc127ac67626da11f2edd32121542839f`。
- `extract/runtime-app-files-25103-20260909-raw/classes.jar`：同为 23,505,324 字节，SHA-256 相同。
- 逐字节比较：`byte_equal=True`。
- 该文件起始为 `dex\n037\0`，不是 ZIP/JAR；因此它只是 APK 原始 `classes.dex` 的副本，不是已解密业务 DEX。

### 2. 可见 wrapper 明确把业务入口交给 native loader

- `H.java:28-34` 固定了真实 Application `com.moutai.mall.MTApp`、组件工厂和 `DexHelper` 库名。
- `H.java:158-169` 通过 `System.loadLibrary(c() ? g : ARM_LIBRARY)` 加载 `DexHelper`，失败时转入 `H.b(ApplicationInfo)` fallback。
- `AP.java:137-145` 的 `instantiateClassLoader` 调用 `H.a(applicationInfo)`；`AP.java:108-112` 选择真实 Application 类名；`AP.java:92-95` 在 Activity 创建后调用 `H.callBS`。
- `AW.java:125-133` 在 `attachBaseContext` 期间再次进入 `H.a(H.sAppInfo)`，随后尝试实例化 `MTApp`。
- `IsoService.java:30-35` 通过 `H.cis()` 触发另一个 native 服务入口。

### 3. `libCryptoSeed.so` 的符号与可执行代码不一致

- IDA headless `ida_entries.py` 识别到 4 个入口：
  - `0x1058`：`Java_com_netease_libs_yxsecurity_encrypt_CryptoUtil_getSeed`
  - `0x132c`：`Java_com_netease_libs_yxsecurity_encrypt_CryptoUtil_getPrivateKey`
  - `0x1004`：`start`
  - `0x3098`：`.init_proc`
- IDA 段布局：`0x0-0x3090` 为只读段，`0x3090-0x3e3c` 为可执行段，`0x3e40-0x3e60` 为 extern。
- 对 `0x1000-0x1500` 的反汇编显示 `0x1058` 与 `0x132c` 位于零填充/数据区域，不在可执行段；不能把两个符号名直接当成可调用的密钥返回实现。
- 对真正可执行段 `0x3098-0x3d00` 的反汇编显示：
  - `0x3388-0x33b0` 使用 `/proc/self/maps`；
  - `0x345c-0x3480` 使用 `/proc/self/cmdline`；
  - `0x3770-0x37b0` 分段比较 `__b_a_n_g_`、`c_l_e__check1234567_`；
  - `0x37d4-0x37f0` 比较 `__p_k_g_n_a_m_e__`，不匹配时进入 `sub_3454`；
  - `0x35e8-0x3660` 使用 syscall 包装执行页粒度内存申请/写入/缓存刷新；
  - `0x374c` 出现 `DexHelper` 字符串。
- 因此这份 `libCryptoSeed.so` 的可执行部分更符合加载、自检和内存变换片段；现有证据不能把它归因于 i 茅台登录请求签名。

## Finding

- **高置信度**：当前业务入口的静态可见链路是 `AW/AP → H.a(ApplicationInfo) → DexHelper → native loader/fallback`；运行时 `classes.jar` 没有提供第二份明文业务 DEX。
- **高置信度**：`libCryptoSeed.so` 中的 `/proc`、Bangcle 标记、包名检查和内存变换是独立的加载/自检线索；两个 `CryptoUtil` JNI 名称不足以证明其参与登录签名。
- **中置信度**：之前出现的红色界面/卡住现象更应先归入 loader/native 自检或初始化失败边界，不能直接判定为“Frida 已绕过”或“异常检测已绕过”。
- **UNVERIFIED**：两个 JNI 导出的运行时实际地址、调用者、参数/返回值；业务请求签名的算法、密钥来源和最终 digest；`H.b(ApplicationInfo)` 被省略的 314 条指令的完整语义。

## Path

- APK：`sample/imoutai-1.9.12.apk`
- 运行时缓存：`extract/runtime-app-files-25103-20260909-raw/classes.jar`
- wrapper 源码：`jadx-out/sources/com/secneo/apkwrapper/H.java`、`AP.java`、`AW.java`
- native 输入：`extract/lib-all-static-20260909/arm64-v8a/libCryptoSeed.so`
- 使用脚本：`E:\code\逆向\android\case-studies\imoutai\hooks\ida_entries.py`、`ida_disasm_range.py`
- 临时静态输出：`%TEMP%\imoutai-ida-crypto-20260911\ida-entries-libCryptoSeed.txt`、`ida-disasm-libCryptoSeed-1000-1500.txt`、`ida-disasm-libCryptoSeed-3098-3d00.txt`

## 当前状态

- Frida：此前已有 delayed attach 成功证据；本轮未重新注入。
- DEX：未获得可验证的明文业务 DEX；新核验进一步排除了 runtime `classes.jar` 作为解密产物。
- 加固/异常检测：没有完成绕过证明；本轮只完成离线 loader/native 边界裁定。
- 网络：保持 offline，未发送、构造或重放请求。
