# 旧版分层载荷结构核验（2026-09-09）

## 目的

核对旧版本地项目中曾用于 `1.9.1` 的 `baiduprotect*.i.dex` / `baiduprotect*.jar`，判断它们是否可以直接作为当前 `1.9.12` 业务 DEX 或登录签名实现的离线输入。该核验只读取本地文件，不启动旧版应用，不访问网络，不修改原始文件。

## 输入边界

- 旧版项目：`E:/code/py/reverse-engineering/frida/imoutai/source/src/main`
- 旧版清单：`AndroidManifest.xml` 标记 `versionName=1.9.1`、`versionCode=191`。
- 旧版 Java 可见装载线索：`com.sagittarius.v6.StubApplication` 调用 `A.n1(...)`，`AppInfo.LIBNAME` 为 `baiduprotect`；`t.java` 只直接展示 native 库加载、架构/路径检查和偏好文件处理，未提供业务登录实现。
- 当前案例：i-Moutai `1.9.12`，两者版本关系及载荷复用关系均未证实。

## 直接观察

### `.i.dex` 外层文件

旧版 assets 中的六个 `.i.dex` 都是可正常读取的 ZIP，均只有一个条目 `classes.dex`，CRC 读取成功。外层文件大小如下：

| 文件 | 外层大小 |
|---|---:|
| `baiduprotect1.i.dex` | 10,780 |
| `baiduprotect2.i.dex` | 8,899 |
| `baiduprotect3.i.dex` | 9,080 |
| `baiduprotect4.i.dex` | 1,825 |
| `baiduprotect5.i.dex` | 265 |
| `baiduprotect6.i.dex` | 900 |

将唯一 ZIP 条目提取到案例 `extract/old-baiduprotect-dex-20260909/` 后，六个条目均以 `dex\n035` 开头，且 DEX 头部字段共同表现为：

- `file_size` 与物理文件大小一致；
- `header_size=112`、`endian_tag=0x12345678`、`map_off=0x74`；
- `string_ids_size=0`、`type_ids_size=0`、`proto_ids_size=0`、`field_ids_size=0`、`method_ids_size=0`、`class_defs_size=0`；
- `data_size=44`、`data_off=0x70`，`class_defs_off=0`。

内容分布也高度一致：每个提取条目只有 50–51 个非零字节，最后一个非零字节都在 `0x98` 附近，`0x99` 到文件末尾是长零区；整文件熵仅约 `0.000115–0.001622 bits/byte`（`baiduprotect5` 因文件较短为 `0.203125`）。这不是“被 JADX 误识别但仍有大量代码”的表现，而是 DEX 形状的零填充占位文件。

因此它们具备 DEX magic 和表面上自洽的文件长度，但没有任何类、方法或字符串索引。对 `baiduprotect1.i.classes.dex` 运行 JADX 得到 `ERROR - Load failed! No classes for decompile!`。这里的“DEX”应记录为**零填充的伪/占位头**，不能称为可直接反编译的业务 DEX。

提取后条目 SHA-256：

| 文件 | 大小 | SHA-256 |
|---|---:|---|
| `baiduprotect1.i.classes.dex` | 10,803,324 | `b215317f4b4e6e6a8891834f9afed5087c454b0ea4dfc3109fc444130d26e07d` |
| `baiduprotect2.i.classes.dex` | 8,860,912 | `23f335270808d9a5a346981a2bcd4f2ea70c604bbd6dd7d388c9d27d10bbe0ce` |
| `baiduprotect3.i.classes.dex` | 9,051,724 | `618a5438a63a8f4fe1dad10a58ec97312e22857a74569c03fd7a10d2c7b4b374` |
| `baiduprotect4.i.classes.dex` | 1,589,472 | `95dac59beef91e8efec3b702a34679077d1583a363a0f6173b9fe13d8fa7d779` |
| `baiduprotect5.i.classes.dex` | 3,068 | `6d5bcb7a0073c4ec30156935d837059d695bbd85c02d0eee26a4e59207b8fa23` |
| `baiduprotect6.i.classes.dex` | 637,260 | `d6164e5baf9ff9e18587eddd3f2de6fe5654adc9aada49df486d6f109f346325` |

### `.jar` 配套文件

`baiduprotect1.jar` 至 `baiduprotect6.jar` 以及 `baiduprotect1.d.jar` 均不是标准 ZIP/JAR：读取 ZIP 目录失败，文件头不是 ZIP magic；整文件熵约为 `7.877–7.992 bits/byte`。该现象与加密或压缩后的二进制资源相容，但仅凭熵不能判定算法、密钥或明文内容。

六个主 `.jar` 的非零字节比例均为 `0.9970` 以上、字节取值覆盖 256 个可能值；与对应 `.i.dex` 的长零区形成明显对比。这个对比支持“`.i.dex` 保存解密后 DEX 的长度/形状，`.jar` 保存待处理资源”的结构性推断，但没有证明 native 读取顺序、压缩格式或解密算法。

仅记录尺寸和 SHA-256，不落盘原始字符串：

| 文件 | 大小 | SHA-256 |
|---|---:|---|
| `baiduprotect1.d.jar` | 125,962 | `2ca6128297b48b0c4db9ab92a2bd2d4969b2f22c455a27ab7ea3a306e16680ba` |
| `baiduprotect1.jar` | 4,060,370 | `9b29b05ac3032e24362ecc031c6ff6435f0337dc1445e02bb8666e16e011f69c` |
| `baiduprotect2.jar` | 3,724,194 | `ed61ebed26bdeea13b8988a7204a41ab09ce542af5ef819237387c72c9fb8434` |
| `baiduprotect3.jar` | 3,538,332 | `bec73371d99112a8d42e0e15e8e3cfeea780c66f7c3ffdcd4cbb513d10be3c03` |
| `baiduprotect4.jar` | 655,624 | `fd5c532fd2fa8af2391eb1d169dde13b484677ed380611461c7d1b5326ee4b52` |
| `baiduprotect5.jar` | 1,739 | `1a10f15b5f98780297dd2b8ac12af121507b4a2c8b330ad5013e1f7acf2fd5c4` |
| `baiduprotect6.jar` | 247,454 | `0b0351ccb256b06a1e020bf1e5a41f700e4cf072996d17f7cb1c324ecad7da02` |

### 旧版 native 能力面

对旧版 arm64 `libbaiduprotect.so` 的 ELF 动态导入做了只读枚举。其导入集合直接包含 `AAssetManager_fromJava`、`AAssetManager_open`、`AAsset_getLength`、`AAsset_read`，同时包含 `inflate`/`uncompress`、`open`/`read`/`pread`、`mmap`/`mprotect`、`dlopen`/`dlsym`、`inotify_init`/`inotify_add_watch` 以及进程/文件操作。该集合支持“旧版 native 层处理 APK asset 并生成/加载运行时内容”的能力判断，但没有给出调用顺序或明文算法。

与当前重建 `libDexHelper` 阶段对比：当前阶段导入 `inflate`/`inflateEnd`/`inflateInit2_`、`crc32`、文件/内存映射、`dlopen`/`dlsym`，并另有 `ptrace`、`kill`、socket/connect 等运行时能力；当前 APK 内原始 `libDexHelper.so` 则只有较小的文件/映射/动态加载导入面。两者只能作为构件能力差异记录，不能据此认定旧 `libbaiduprotect` 与当前 `libDexHelper` 同源。

旧版 `libbaiduprotect.so` 的只读 ASCII 字符串表在文件偏移 `0xcc2e6` 和 `0xcc388` 的字符串范围内各出现一次 `classes.dex` 子串（每处所在字符串范围长度为 13）。结合上面的 `AAssetManager_*` 与 zlib 导入，这构成“旧版 native 至少处理过 asset/DEX 形态数据”的静态锚点；它没有证明具体输入是哪个 `.jar`，也没有证明处理结果是业务 DEX。

同一库从文件偏移 `0xcc2c8` 开始的尾部片段可以独立解析为完整 ZIP：只有一个 `classes.dex` 条目，压缩大小 71 字节、解压大小 156 字节，CRC 读取成功。该条目也是 `dex\\n035` 空表头（`string/type/proto/field/method/class_defs` 均为 0），因此它是可验证的 loader/stub 资源，不是业务 DEX。该片段的尾部 SHA-256 为 `60cbfb544866222eff01d9eec9ef2be7f0ccdc5c88520d3c78bc2ab4d65d915a`；内层 DEX SHA-256 为 `6a67b5f701670ee9ca45a6e38de68cccbe645bbca46679c11d24bcfb7517d1f8`。

## 裁定

- **Evidence**：旧版确实存在六层 ZIP 包装的 `.i.dex` 和高熵非 ZIP `.jar` 配套资源；旧版 Java 装载链把 native `baiduprotect` 作为关键入口。
- **Evidence**：旧版 `libbaiduprotect.so` 的导入集合包含 Android asset 读取和 zlib 解压能力，与“`.i.dex` 占位头 + `.jar` 待处理资源”的结构推断相容。
- **Evidence**：同一旧版库的字符串表含两处 `classes.dex` 子串，位置与导入能力相互印证，但没有形成函数级调用链。
- **Evidence**：旧版库尾部包含一个可独立解析的单条目 ZIP，但其内层仍是 156 字节空表 DEX；这进一步区分了 loader/stub 与业务代码。
- **Finding**：六个 `.i.dex` 的解包结果均为空 DEX 表，JADX 无法得到类；它们不能直接提供业务 Java 代码。
- **Inference**：长零区与高熵 `.jar` 的配对关系与“native 阶段从 `.jar` 处理出 DEX”的模型相容；这是结构推断，不等于已知算法或密钥。
- **UNVERIFIED**：旧版资源是否被当前 `1.9.12` 复用；`.jar` 的格式/解密算法；任一层与 `LoginActivity`、登录请求签名或红色界面的因果关系；Frida/异常检测是否因此触发。

## 复现与限制

本轮使用 ZIP 条目枚举、DEX 头字段读取、JADX 单文件反编译和 SHA-256/熵统计。没有尝试猜测密钥、解密 `.jar`、执行载荷或把旧版文件注入当前进程；因此本轮没有生成 `findings/login-signature.md` 或 `findings/reproduce_sign.py`。
