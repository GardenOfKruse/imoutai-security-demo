# 当前 APK 资产角色分类核验（2026-09-10）

## 范围

直接读取 `sample/imoutai-1.9.12.apk` 的 ZIP 条目，核验此前 native 静态报告提到的资源名是否真实存在，并按文件头、可解析格式、熵和短字符串摘要做角色分类。不解密未知资源、不调用应用、不访问网络。

## 结果

| 条目 | 大小 | SHA-256 | 结构观察 | 当前裁定 |
|---|---:|---|---|---|
| `assets/info.y` | 10,697 | `c8a14278be0eb90b73e6665efcad0198e19061dbbc972c4d079cd0004ed06c72` | 以 `INFO-` 开头；短字符串包含 `roothide`、`edxposed`、`fridasig`、`ptrace`、`fupk3`、`dumpArtMethod` 等 | 风险/环境检测规则或特征资源；不是已确认的登录签名载荷 |
| `assets/defaultv0` | 40,256 | `6bb4e02898572ee719996851d421ededf2158ad5a79db323c99abc25ea332391` | 全部为 Base64 字符；标准解码后 30,192 字节，非 DEX/ELF/ZIP/VDEX/CDEX，熵约 7.9935 | 独立高熵资源；格式、用途和与业务签名的关系 `UNVERIFIED` |
| `assets/defaultv1` | 300 | `c5ba3606fb4970cbcee3a98e4dc8e70e773f7839779dd0025f1804b43298d331` | 全部为 Base64 字符；标准解码后 224 字节，非 DEX/ELF/ZIP/VDEX/CDEX | 独立小型高熵资源；用途 `UNVERIFIED` |
| `assets/detect.ms` | 679,056 | `72c1eb7d35b6b76d8e0126c80459612bc45eafdd569845815d61e7d067314e0a` | 非标准文件头，高熵；未识别为 DEX/ELF/ZIP | 检测/模型类资源候选；不是已确认业务 DEX |
| `assets/angle.ms` | 125,968 | `9c289a673aef59a9d4728816a384aa942e88bed4c9ade287440237e667174238` | 非标准文件头，高熵；未识别为 DEX/ELF/ZIP | 模型/检测资源候选；用途 `UNVERIFIED` |
| `assets/corner.ms` | 380,592 | `185b3c238df6dd0e495ccb5555041a7ab703771a294a1d706b1aa3f8ce0f7871` | 非标准文件头，高熵；未识别为 DEX/ELF/ZIP | 模型/检测资源候选；用途 `UNVERIFIED` |

APK 中没有名为 `assets/baoef` 的条目；IDA 报告中的该字符串只能作为代码常量，不能当成当前 APK 的实际资源名。

## 相关元数据

- `assets/meta-data/manifest.mf` 为单行、高熵的自定义元数据，大小 49,479 字节；其内容不能直接还原为标准 Java manifest 文本。
- `assets/meta-data/rsa.pub` 与 `rsa.sig` 存在，但本轮没有验证其签名对象，也没有把它们与登录请求签名关联。

## 裁定

- **Evidence**：`info.y` 明确包含多种 Frida/Xposed/root/ART dump 特征字串；`defaultv0/v1` 不是可直接装载的 DEX/ELF/ZIP；此前报告引用的 `assets/baoef` 不在当前 APK 条目中。
- **Finding**：本轮资产盘点未发现一个可直接作为业务 DEX 或登录签名代码输入的静态资产。
- **Inference**：风险检测资源与运行时隐藏载荷可能由同一壳管理，但当前资产分类不足以证明二者同源，更不足以证明签名算法位置。
- **UNVERIFIED**：`defaultv0/v1`、`.ms` 资源的内部格式和用途；`manifest.mf` 的签名对象；任何资源是否参与登录请求签名。

## 停止条件

仅凭资源熵、Base64 外观、文件名或 `info.y` 中的检测字符串继续猜测解密/签名算法，信息增益不足，后续应改由接管模型根据完整证据集选择其他最小实验。
