# `libDexHelper` 跨版本关键词与静态引用核验（2026-09-09）

## 目的与边界

核对旧版 helper 产物、当前 APK 内原始 helper 和当前隐藏阶段重建产物中的少量登录/签名相关字节串，判断是否存在可直接迁移的静态锚点。只做本地字节计数、字符串边界和 AArch64 PC-relative/指针引用扫描；没有执行、注入、解密或输出原始业务值。

## 关键词计数

| 产物 | `login` | `signature` | `token` | `account` | `password` | `DexFile` | `InMemoryDexClassLoader` |
|---|---:|---:|---:|---:|---:|---:|---:|
| 旧版 `libDexHelper_fixed.so` | 0 | 0 | 0 | 0 | 0 | 0 | 0 |
| 旧版运行时样本 | 0 | 0 | 0 | 0 | 0 | 0 | 0 |
| 当前 APK `libDexHelper.so` | 0 | 0 | 0 | 0 | 0 | 0 | 0 |
| 当前隐藏阶段重建 ELF | 0 | 1 | 4 | 0 | 0 | 39 | 1 |

关键词计数是字节事实，不代表语义或调用关系。尤其重建 ELF 不是已证明与设备运行时逐字节等价的原始库。

## 重建 ELF 的最小引用核验

- `signature` 位于文件偏移 `0xfa688` 的 12 字节 ASCII 字符串范围内；未发现指向该位置的对齐 32/64 位指针，也未发现扫描到的 AArch64 `ADRP+ADD` 或 literal-load 直接命中该位置。
- 四个 `token` 命中位于 `0xf71a0`、`0xf71a7`、`0xf71b6`、`0xf71c5`；每个位置各发现两个指向该字节位置的对齐指针，位于 `0x3a40/0x117ee0`、`0x3a58/0x117ee8`、`0x3a70/0x117ef0`、`0x3a88/0x117ef8`。这只能证明存在数据表样式引用，未证明其被业务登录路径读取。
- `InMemoryDexClassLoader` 位于 `0xef2bb` 的 36 字节字符串范围内；本轮直接 PC-relative 扫描没有命中。
- 两处 `http` 字符串分别位于 `0xf25b8`（78 字节范围）和 `0x11f528`（180 字节范围）；本轮没有直接 PC-relative/对齐指针命中。

## 裁定

- **Evidence**：旧版和当前原始 helper 没有直观登录/签名关键词；重建阶段存在少量相关字节，但它们没有形成直接代码引用链。
- **Finding**：关键词和数据表命中不足以定位登录入口、签名输入、密钥来源或输出格式。
- **UNVERIFIED**：重建 ELF 中相关字节的真实语义；是否存在通过寄存器拼接、间接表、加密字符串或运行时解密形成的引用；与 `LoginActivity`/请求签名的关系。

## 证据输入

- 旧版：`E:/code/py/reverse-engineering/frida/imoutai/libs/libDexHelper_fixed.so`、`libDexHelper.so_0x7a34a2b000_0x15d000.so`。
- 当前：`extract/lib/arm64-v8a/libDexHelper.so`、`extract/libDexHelper-stage-decrypted-20260909.elf`。
- 相关 IDA 静态文本：`evidence/ida-stage-focus-20260909.txt`、`evidence/ida-libdexhelper-selected-full-20260909.txt`。
