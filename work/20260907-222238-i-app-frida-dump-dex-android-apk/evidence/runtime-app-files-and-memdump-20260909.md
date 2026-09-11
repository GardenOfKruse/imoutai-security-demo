# 运行时应用文件与双进程离线快照

日期：2026-09-09  
样本：i-Moutai v1.9.12 / `com.moutai.mall`  
设备：`82e459fc0920`  

## 已执行操作

1. 在关闭 Wi-Fi 和移动数据的条件下冷启动应用，等待运行时初始化。
2. 只读确认主进程与隔离进程：主进程 PID `25103`，隔离进程 PID `25135`；前台组件为 `com.moutai.mall/.module.login.LoginActivity`。
3. 通过进程 root 视图只读读取应用私有缓存文件，并使用无损二进制传输保存到本地。
4. 使用 `hooks/dump-mem4.sh` 分别对两个进程做不冻结进程的只读内存快照；抓取后进程仍存活。
5. 对两份快照分别运行标准 DEX carve 和 magic-less DEX 结构扫描。

## 应用私有缓存文件

发现的文件位于应用缓存目录：

- `extract/runtime-app-files-25103-20260909-raw/classes.dve`
  - 大小：24 字节
  - SHA-256：`9c5286f9dd36cc275e4863a88d3f123c3e02ce1370bbbff2294383e7956be2a3`
  - 不含 DEX、CDEX、VDEX、ELF 或 ZIP 标记；离线 ZIP 解析失败。
- `extract/runtime-app-files-25103-20260909-raw/classes.jar`
  - 大小：23,505,324 字节
  - SHA-256：`66598e881fd7860a8091e61aafc45f3cc127ac67626da11f2edd32121542839f`
  - 起始为 DEX 头；无第二个 DEX/CDEX/VDEX/ELF 头；文件整体不是有效 ZIP。

将运行时 `classes.jar` 与 APK 内 `classes.dex` 通过 ZIP 成员读取后比较：大小和 SHA-256 均相同，逐字节比较结果为 `byte_identical=True`。因此该缓存文件是 APK 原始 `classes.dex` 的副本，不是已确认的业务 DEX 解密产物。

原始文件结构扫描结果另存于：
`evidence/runtime-payload-triage-raw-20260909.json`。

## 双进程离线内存快照

### 主进程 PID 25103

- 设备端抓取区域数：273。
- 本地快照目录：`extract/memdump4-25103-1788890310`。
- 本地保存：273 个压缩区域文件及映射/清单文件，共 276 个文件。
- 抓取后进程存活检查通过。
- 标准 DEX carve：`0 unique dex, 0 bytes`。
- magic-less 扫描：`files=273`、`Lcom/moutai/mall=38`、`com/moutai/mall/module=0`、`LoginActivity=23`、`candidates=0`。

### 隔离进程 PID 25135

- 设备端抓取区域数：133。
- 本地快照目录：`extract/memdump4-25135-1788890376`。
- 本地保存：133 个压缩区域文件及映射/清单文件，共 136 个文件。
- 抓取后进程存活检查通过。
- 标准 DEX carve：`0 unique dex, 0 bytes`。
- magic-less 扫描：`files=133`、`Lcom/moutai/mall=0`、`com/moutai/mall/module=0`、`LoginActivity=1`、`candidates=0`。

两份快照的映射清单都包含 `libDexHelper.so` 的多个映射以及 `base.odex/base.vdex` 映射；映射存在本身不等于对应内容已形成可用 DEX/VDEX。原始映射和写出清单分别保存在各快照目录中。

扫描证据：

- `evidence/magicless-dex-scan-memdump4-25103-1788890310.json`
- `evidence/magicless-dex-scan-memdump4-25135-1788890376.json`

## 传输与权限失败记录

- `run-as com.moutai.mall` 返回应用不可调试；第一次读取得到的是权限错误文本，不是样本文件，错误输出已排除。
- 直接使用 Windows `adb exec-out ... cat` 读取二进制时发生换行转换，导致本地 `classes.jar` 被改变；该文件已排除。之后改用 Base64 传输，重新获得上述原始哈希。

## 本轮事实结论

- 冷启动离线状态下，主进程和隔离进程均到达登录 Activity，并能完成不冻结的只读内存抓取。
- 当前两份快照均未产出可验证业务 DEX/VDEX。
- 运行时缓存中的 `classes.jar` 与 APK 原始 `classes.dex` 逐字节一致；`classes.dve` 不是可直接解析的 DEX/VDEX/ZIP。
- 本轮没有输入凭据、没有访问真实业务服务、没有构造或重放请求，也没有修改 APK 或设备文件。
