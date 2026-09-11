# i-Moutai v1.9.12 低端模型执行交接单

> **纠偏通知（2026-09-09）**：本文件是历史执行计划，其中旧阶段 D / `t4e` 路线已出现红色界面和约 2.1 秒后的 `libart` abort。它不再作为默认执行方案。交接时以 [`glm-correction-handoff-20260909.md`](glm-correction-handoff-20260909.md) 的事实记录为准，由接管模型自行判断。

更新时间：2026-09-08  
目标：继续分析 `com.moutai.mall` v10912 的登录签名链路；只做本地、离线、可复核的安全分析。

## 0. 当前结论（先看这一段）

| 项目 | 当前状态 | 证据口径 |
|---|---|---|
| Frida 通道 | **部分成功** | 能连接目标进程、执行脚本、做只读 native 抓取；不是完整注入成功 |
| `libDexHelper.so` 加载 | **部分成功** | `Runtime.nativeLoad` 改写为绝对路径后，`android_dlopen_ext` 返回 `ok` |
| 梆梆/壳加载自检绕过 | **未成功证实** | JNI_OnLoad 仍在 ART 路径触发 abort |
| 异常/反调试检测绕过 | **未成功证实** | `/proc/self/maps` 过滤能生效，但 abort 仍发生，不能归因于单一检测 |
| 业务 DEX 提取 | **未完成** | 标准 carve=0；magic-less 结构约束候选=0 |
| 登录签名恢复 | **尚未开始有效验证** | 没有业务 DEX，也没有可用的脱敏基线 |

不要把下面三件事混为一谈：

1. Frida 能连上，不等于壳初始化成功。
2. `dlopen ret=ok`，不等于 JNI_OnLoad、自检和业务 DEX 解密成功。
3. 内存中出现 `Lcom/moutai/mall` 字符串，不等于已经拿到可反编译的 DEX。

## 1. 固定范围和执行规则

- 设备固定为 `82e459fc0920`，所有 ADB 操作必须带 `-s 82e459fc0920`。
- 案例目录：
  `E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk`
- Hook 目录：
  `E:\code\逆向\android\case-studies\imoutai\hooks`
- 只做本地静态分析、只读内存抓取、运行时观察和离线摘要比较。
- 不输入账号、手机号、短信、验证码；不保存 token/cookie；不访问真实业务服务器；不构造或重放请求。
- 不冻结父进程，不执行 `kill -STOP`，不手工调用 `JNI_OnLoad`，不直接调用壳的 JNI 初始化函数。
- 每个成功步骤追加 `notes\steps-log.md`；失败单独追加下一个 `F` 编号。证据不足时写 `UNVERIFIED`。
- `scope.md` 不修改。

## 2. 已有证据索引

优先阅读这些文件，不要重复已经完成的 T1/T2 全量扫描：

- `evidence\E3-mp21-console.log`：手工加载路径无效，boot classloader/FindClass 失败。
- `evidence\E5-mp23-console.log`：绝对路径 + app `PathClassLoader` 后 `android_dlopen_ext` 成功，但 JNI_OnLoad abort。
- `evidence\maps-analysis-28343.txt`：完整 maps 统计和未抓取区域排序。
- `evidence\magicless-dex-scan-4999.json`：业务类名命中，但满足 DEX 结构约束的候选为 0。
- `evidence\ida-xrefs-libdexhelper.txt`：`libDexHelper.so` 的关键静态交叉引用。
- `evidence\elf-dynsym-libdexhelper-raw.json`：原始 ELF 有 235 个动态符号、226 个未定义导入。
- `evidence\ida-triage-libdexhelper-raw.json`：IDA 头部、段和字符串初筛结果。
- `notes\steps-log.md`：完整时间线，当前最新记录为 S4-8/F18。

当前已有的脚本/产物：

- `hooks\dump-dex-hook-compiled-t3.js`
- `hooks\dump-dex-hook-compiled-t4e.js`
- `hooks\dump-native-driver.py`
- `hooks\dump-mem3.sh`
- `hooks\magicless_dex_scan.py`
- `hooks\ida_triage_libdexhelper.py`
- `hooks\ida_dump_xrefs.py`

## 3. 推荐执行顺序

### 阶段 A：先做低成本证据复核

目的：确认低端模型理解的是当前状态，而不是从旧日志重新猜测。

```powershell
$CASE = 'E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk'
Get-Content -LiteralPath "$CASE\scope.md"
Get-Content -LiteralPath "$CASE\notes\steps-log.md" -Tail 140
Get-Content -LiteralPath "$CASE\evidence\magicless-dex-scan-4999.json"
Get-Content -LiteralPath "$CASE\evidence\ida-xrefs-libdexhelper.txt"
```

复核结论必须保持：`UNVERIFIED` 的内容不能升级成“已绕过”。

### 阶段 B：验证两个 VDEX 命中点

旧快照中有两个字符串命中：

- `000.bin.gz` 偏移 `3353026` 附近：`vdexfile`
- `005.bin.gz` 偏移 `4124965` 附近：`vdex...`

只做离线验证，要求同时检查：VDEX 版本字段、header 大小、checksum/校验字段、dex 数量/偏移表，以及偏移表指向的数据是否能被 `jadx` 或现有 DEX 解析器打开。只有“结构完整 + 含 `Lcom/moutai/mall` 类”的结果才算候选；否则记录为文本误命中。

如果两个位置均不是完整容器，不要继续对同一快照反复 carve，转阶段 C。

### 阶段 C：以 native 静态分析定位真正的自检点

当前最有价值的三个静态假设：

- `sub_33F8`：打开 `/proc/self/maps`，解析自身映射和 ELF 段；更像自映射/完整性或诊断路径。
- `sub_3184`：呈现 RC4 类解码结构；需要确认它解的是字符串、DEX 数据还是配置。
- `sub_303C`：从自定义哈希/动态符号表中解析 `JNI_OnLoad`，说明库有自己的符号解析层。

这三个判断都是“待验证假设”，不是漏洞结论。低端模型应按以下顺序缩小范围：

1. 用已有 `ida-xrefs-libdexhelper.txt` 找 `sub_33F8` 的全部 callers，标出进入点和返回值用途。
2. 对 `sub_3184` 向上追踪输入缓冲区来源，向下追踪输出缓冲区去向；只记录长度、地址和调用关系，不打印敏感明文。
3. 对 `sub_303C` 记录被解析的符号名/哈希、调用者和返回地址，确认它是否在 JNI_OnLoad 前后触发。
4. 运行时只观察以下边界：`dlsym`、`dlopen`、`abort`、`RegisterNatives`、`mmap/mprotect`、`open/fopen/fgets/read`。日志只保留模块名、地址、返回值、长度和脱敏后的路径。
5. 如果再次出现 abort，保存一次 backtrace 和 logcat/dropbox；不要扩大 Hook 面，也不要循环重试。

重点是找出“哪个检查导致 JNI_OnLoad 中断”，而不是把所有 abort 都改成返回。粗暴修改会破坏 ART 状态，不能作为绕过证据。

### 阶段 D：运行时 Hook 只做一次短实验

若阶段 C 找到明确的检查边界，再用已有 bundle 做一次短时验证：

```powershell
$HOOK = 'E:\code\逆向\android\case-studies\imoutai\hooks'
$CASE = 'E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk'
python "$HOOK\dump-native-driver.py" `
  --serial 82e459fc0920 `
  --package com.moutai.mall `
  --js "$HOOK\dump-dex-hook-compiled-t4e.js" `
  --out "$CASE\extract\native-memdump\low-model-t4e-check" `
  --wait 5 --chunk 4096 --list-classes
```

验收只有两个条件同时满足才算前进：

- 注入进程中 `loaded app classes > 0`；
- 没有 JNI_OnLoad/ART abort，且生成了可复核 dump 或类列表。

如果仍是 `abort + app classes=0`，立即记录失败，停止运行时路线，回到阶段 C；不要再次重复 T3 的 maps 关键词过滤。现有证据已表明：maps 过滤生效，但不足以解决问题。

### 阶段 E：拿到业务 DEX 后再做签名定位

只有在 DEX 通过“可打开 + 含业务类”验收后，才进入 Java/Smali 分析：

1. 用 jadx 定位 OkHttp `Interceptor`、请求构造、`login/sms/sendCode` 入口。
2. 搜索 `sign/signature/hmac/md5/secret/nonce/timestamp`，并记录调用链和字段来源。
3. 若方法是 DexVMP 空壳，转到 OkHttp 请求边界和 `libhaotiansec.so/libCryptoSeed.so` 的 native crypto 边界做两侧观察。
4. 当前无脱敏基线，因此不得生成“签名已恢复”或 `reproduce_sign.py` 的伪实现。

网络基线和真实登录不在本轮低端模型执行范围内。缺少脱敏基线时，最终状态必须写成 `UNVERIFIED`，而不是 PASS/FAIL 猜测。

## 4. 低端模型的停止条件

遇到以下任一情况就停止当前分支并写失败记录：

- 进程被 watchdog 杀死或需要冻结进程才能继续。
- 手工 `dlopen/JNI_OnLoad` 才能复现，或出现 boot classloader 错误。
- 只看到类名字符串，没有 DEX/VDEX 结构证据。
- 日志包含账号、验证码、token、cookie 或未脱敏请求内容。
- 需要访问真实服务器、构造请求或重放流量。
- 需要修改 APK/Manifest 或大范围 patch 以“让它不崩”。

## 5. 交付格式

每次执行结束只交付三项：

1. 新增的证据文件路径。
2. `steps-log.md` 中新增的 S/F 编号和一句结论。
3. 下一步建议，只允许基于新证据提出一个最小动作。

最终签名报告只有在存在真实 DEX/请求边界证据和脱敏基线后才允许创建：

- `findings\login-signature.md`
- `findings\reproduce_sign.py`

## 6. 可直接粘贴给低端模型的冷提示词

你是本地 Android 逆向分析执行代理。继续处理 i-Moutai v1.9.12（`com.moutai.mall`，versionCode 10912）案例。

先读：

1. `notes\low-end-model-execution-plan-20260908.md`
2. `notes\steps-log.md`
3. `scope.md`
4. `evidence\E5-mp23-console.log`
5. `evidence\magicless-dex-scan-4999.json`
6. `evidence\ida-xrefs-libdexhelper.txt`

你的目标不是猜算法，而是用最小、可复核的动作判断下一条证据链：

- 先离线验证两个 VDEX 命中点是否为完整容器；
- 若不是，分析 `libDexHelper.so` 中 `sub_33F8/sub_3184/sub_303C` 的调用关系和数据流；
- 运行时最多做一次 5 秒观察，关注 `dlsym/dlopen/abort/RegisterNatives/mmap/mprotect/open/read`；
- 不冻结进程、不手工调用 JNI_OnLoad、不构造或重放请求、不输入或保存任何账号/验证码/token/cookie；
- 不把 `dlopen ret=ok`、maps redaction 或类名字符串命中当作绕过成功；
- 只有“业务类数 > 0 + 无 ART abort + 可打开的 DEX/VDEX”才算提取成功；
- 每一步把命令、结果、证据路径追加到 `notes\steps-log.md`，失败使用下一个 `F` 编号；
- 如果没有新证据，停止并明确写 `UNVERIFIED`，不要重复旧实验。

输出必须包含：当前判断、执行动作、证据路径、成功/失败编号、唯一下一步。
