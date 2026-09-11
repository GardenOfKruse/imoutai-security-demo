# memdump4 扩大覆盖与离线 DEX 结果

日期：2026-09-09  
样本：i-Moutai v1.9.12 / `com.moutai.mall`  
设备：`82e459fc0920`  
进程：PID `17125`  

## 已执行操作

1. 使用 `hooks/dump-mem4.sh` 对干净启动后的 PID `17125` 做只读内存抓取。
2. 抓取脚本覆盖 `r--p`、`rw-p`、`r--s`、`rw-s`、`r-xp`、`rwxp`，并保留匿名、dalvik、memfd 及目标应用文件映射；未执行 `STOP/CONT`。
3. 设备端输出目录为：
   `/data/local/tmp/memdump4-17125-1788885598`
4. 设备端返回：`captured_regions=257`、`process alive after dump`、`28M`。
5. 设备端目录已拉取到：
   `extract/memdump4-17125-1788885598-local`
   本地共有 260 个文件（257 个压缩区域文件和 3 个元数据文件），总字节数为 `28,331,828`。

## 覆盖记录

- 本次 `maps.txt` 中存在 3 个 `base.apk` 的 `r--s` 映射和 6 个 `libDexHelper.so` 映射。
- `dump-list.txt` 记录了 8 个成功写出非空快照的目标应用文件映射，其中包括 2 个 `base.apk` 的 `r--s` 映射和 `base.odex/base.vdex` 映射；`dump-list.txt` 中没有 `libDexHelper.so` 行。
- 上述“maps 中存在”与“成功写入 dump-list”分开记录，不把映射存在升级为对应内容已成功导出。

## 离线处理结果

- `carve_dex.py` 对本地快照运行退出码为 `0`，结果为：`0 unique dex, 0 bytes`。
- `magicless_dex_scan.py` 输出 `evidence/magicless-dex-scan-17125-memdump4.json`，顶层统计为：
  - `files=257`
  - `Lcom/moutai/mall=38`
  - `com/moutai/mall/module=0`
  - `LoginActivity=23`
  - `candidates=0`
- 字符串命中主要来自 `0000.bin.gz`（`Lcom/moutai/mall=38`、`LoginActivity=22`）及 `0027.bin.gz`（`LoginActivity=1`）；没有形成通过 DEX 头、文件大小、map 等结构约束的候选。

## 运行状态核对

- 抓取后再次执行只读存活检查，`kill -0 17125` 返回码为 `0`。
- 当前前台组件为：`com.moutai.mall/.module.login.LoginActivity`。

## 本轮可直接得出的结论

- 这轮扩大了只读快照的映射筛选范围，并成功保留了进程运行状态。
- 在这份本地快照中，没有得到可验证的业务 DEX 文件，也没有得到通过结构约束的 magic-less DEX 候选。
- 该结果不能单独证明进程内不存在业务 DEX；它只记录本次快照及离线解析没有产出有效 DEX。

## 证据路径

- 脚本：`case-studies/imoutai/hooks/dump-mem4.sh`
- 原始映射：`extract/memdump4-17125-1788885598-local/maps.txt`
- 规范化映射：`extract/memdump4-17125-1788885598-local/maps.norm.txt`
- 成功写出清单：`extract/memdump4-17125-1788885598-local/dump-list.txt`
- 区域快照：`extract/memdump4-17125-1788885598-local/*.bin.gz`
- DEX carve 目录：`extract/memdump4-17125-1788885598-local/carved`
- magic-less 扫描：`evidence/magicless-dex-scan-17125-memdump4.json`
