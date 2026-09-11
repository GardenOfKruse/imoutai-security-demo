# 当前版本 native 内嵌 ZIP/DEX 普查（2026-09-09）

## 目的与范围

对当前案例 `extract/` 下已解包的所有 `.so` 做与旧版 `libbaiduprotect.so` 相同的静态扫描：查找 ZIP local-header 命中，并从每个命中位置尝试独立解析 ZIP 目录。只读取本地文件，不执行库、不加载 APK、不解密资源。

## 结果

- 扫描对象：当前 `extract/` 下所有 `.so`，包含 `lib/arm64-v8a`、`lib2`、`lib-all-static-20260909` 及既有运行时导出文件。
- 可独立解析的 ZIP 尾部：**0**。
- 因此没有得到当前 native 库内可直接列出的 `classes.dex` 条目，也没有新的 DEX 文件产物。
- 该结果不否定当前 `libDexHelper` 运行时按需构造/解密数据，也不覆盖 APK `classes.dex` 尾随区或进程内短命缓冲区；它只排除了“当前解包 `.so` 里存在旧版同类完整 ZIP 尾部”这一简单假设。

## 与旧版的对照

旧版 `libbaiduprotect.so` 在文件偏移 `0xcc2c8` 存在可独立解析的单条目 ZIP，内层为 156 字节空表 DEX；当前解包 `.so` 未复现该结构。旧版资源/库不能据此直接替换当前构件。

## 结论口径

- **Evidence**：当前解包 `.so` 的 ZIP 尾部独立解析候选为 0。
- **Finding**：旧版 native 内嵌 stub ZIP 的结构未在当前 `.so` 静态文件中重现。
- **UNVERIFIED**：当前版本的运行时解密缓冲区、隐藏 DEX 生成位置、`libdexjni`/`libCryptoSeed` 与业务加载或签名的关系。
