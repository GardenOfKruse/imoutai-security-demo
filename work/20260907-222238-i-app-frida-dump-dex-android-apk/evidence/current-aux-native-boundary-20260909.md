# 当前版本辅助 native 构件边界（2026-09-09）

## 目的与范围

对当前 APK 中此前未形成业务调用链的 `libCryptoSeed.so` 与 `libdexjni.so` 做静态角色边界核验，并把它们与当前 `classes.dex` 的可解析语义区/尾随区分开。只读取 APK/解包文件和既有静态快照，不调用 JNI、不传入参数、不访问网络。

## `libCryptoSeed.so`

- arm64 文件大小：12,432 字节。
- ASCII 导出/字符串表包含：`Java_com_netease_libs_yxsecurity_encrypt_CryptoUtil_getSeed`、`Java_com_netease_libs_yxsecurity_encrypt_CryptoUtil_getPrivateKey`、`libCryptoSeed.so`。
- 现有动态符号报表对该库的符号边界标记为截断，不能把报告中的计数当作完整导出真值；上述 JNI 名称来自直接字节字符串观察。
- 既有干净进程 maps 证据显示该库被映射，但本轮没有建立其 JNI 函数被调用的证据。

## `libdexjni.so`

- arm64 文件大小：1,985,237 字节。
- ELF 动态报表解析到 26 个 dynsym、27 个重定位，未截断。
- 导入能力包含 `open`、`read`、`mmap`、`dlopen`、`dlsym`、`abort`；未观察到 Android `AAssetManager`、zlib、ptrace、socket 或 `CryptoUtil` 相关直接导入。
- 字节字符串中出现 `JNI_OnLoad`，但没有由此推导业务类、签名算法或调用顺序。

## 与当前 `classes.dex` 语义边界对照

对 APK 内 `classes.dex`，以既有结构核验得到的语义区终点 `0x598c` 为边界：

- `CryptoSeed`、`CryptoUtil`、`getPrivateKey`、`getSeed`：语义区 0，尾随区 0；当前可见 DEX 没有这些字节串。
- `com/netease`：语义区 0，尾随区 2。
- `token`：语义区 0，尾随区 103。
- `signature`：语义区 1，尾随区 37；语义区单个命中不能单独证明登录请求签名。

## 裁定

- **Evidence**：`libCryptoSeed` 的 JNI 导出名可确认；`libdexjni` 的文件/映射/动态加载导入面可确认；当前可见 DEX 没有对应 CryptoUtil 方法名。
- **Finding**：现有静态证据未形成“可见业务类 → CryptoSeed/libdexjni → 登录请求签名”的连续链路。
- **UNVERIFIED**：`getSeed/getPrivateKey` 是否参与登录签名；`libdexjni` 是否参与隐藏 DEX 处理；尾随区相关字符串的真实归属；运行时调用参数、密钥内容和输出 digest。
