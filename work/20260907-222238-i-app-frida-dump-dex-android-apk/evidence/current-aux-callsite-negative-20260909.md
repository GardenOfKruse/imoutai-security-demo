# 当前辅助 native 与可见 DEX 调用点阴性核验（2026-09-09）

## 目的与范围

补充核验当前 APK 中 `libCryptoSeed.so` 的 JNI 名称，是否能在 APK 内可见 `classes.dex` 或现有本地源码/快照中找到对应调用点。只读取本地 APK、解包文件和既有静态快照；不调用 JNI、不传入参数、不访问真实或 mock 网络服务。

## 直接观察

- `lib/arm64-v8a/libCryptoSeed.so` 中直接出现：
  - `Java_com_netease_libs_yxsecurity_encrypt_CryptoUtil_getSeed`
  - `Java_com_netease_libs_yxsecurity_encrypt_CryptoUtil_getPrivateKey`
- 当前 APK `classes.dex` 以既有结构核验得到的语义区终点 `0x598c` 进行分界：
  - `CryptoSeed`、`CryptoUtil`、`getPrivateKey`、`getSeed`：总命中 0，语义区 0，尾随区 0。
  - `com/netease`：总命中 2，语义区 0，尾随区 2。
  - `token`：总命中 103，语义区 0，尾随区 103。
  - `signature`：总命中 38，语义区 1，尾随区 37。
- 在当前 APK 其他条目、既有解包目录和旧项目源码中，没有形成“可见业务类直接调用上述两个 JNI 方法”的静态调用点证据。`libCryptoSeed.so` 在既有干净进程 maps 快照中出现过映射，但没有 JNI 调用参数或返回值快照。

## 裁定

- **Evidence**：辅助库的两个 JNI 导出名称真实存在；对应方法名未出现在当前可见 `classes.dex` 语义区；`com/netease`、`token` 的命中集中在尾随区。
- **Finding**：截至本轮，无法从 APK 内可见 DEX 和现有本地材料连接 `CryptoUtil` 到登录入口、请求构造或签名输出。
- **Inference**：该库可能由隐藏代码、其他运行时生成代码或特定分支调用；这只是解释候选，不能当作已确认架构。
- **UNVERIFIED**：`getSeed/getPrivateKey` 是否参与登录签名；尾随区命中是否属于业务载荷；运行时是否曾调用该库及其输入/输出。

## 复核限制

该阴性结果只能排除“调用链完整落在当前可见 DEX 且以明文方法名表达”的简单情形，不能证明库无用，也不能证明登录签名不存在或已恢复。
