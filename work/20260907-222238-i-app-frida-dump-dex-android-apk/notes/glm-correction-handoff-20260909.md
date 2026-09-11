# i-Moutai v1.9.12 纠偏交接单（交给 GLM）

更新时间：2026-09-09  
用途：向接管模型提供截至目前的本地、离线、可复核实验事实、证据位置和不确定性。

> 重要记录：上一轮加入 maps 过滤、nativeLoad 实参改写、loader 替换、preload/direct-dlopen 的组合分支后，应用出现红色界面并很快退出。该分支的实验结果为失败/未证实，不应被当成绕过成功证据。

## 1. 结论先行

| 问题 | 当前结论 | 证据口径 |
|---|---|---|
| Frida 是否注入成功 | **部分成功** | 能连接/加载脚本，并完成过只读 native 抓取；但当前侵入式分支不能维持应用生命周期 |
| 梆梆/壳加载检测是否绕过 | **没有证实，按未绕过处理** | `android_dlopen_ext(...)=ok` 后约 2.1 秒仍在 `libart.so` 路径 abort |
| 异常/反调试检测是否绕过 | **没有证实，按未绕过处理** | maps 过滤确实产生过 redaction，但 abort 仍发生，不能归因于单一检测 |
| 业务 DEX 是否提取 | **没有** | memdump3 标准 carve=0；magic-less 结构候选=0；两个 VDEX 命中均不是有效头 |
| 登录签名是否恢复 | **尚未进入有效验证** | 没有可反编译业务 DEX，也没有可用的脱敏业务基线 |
| 当前版本红色界面原因 | **高度疑似注入/加载链路回归；具体触发点未定位** | 用户报告旧路径可进入登录界面；`E5` 记录当前分支 2.1 秒后 Trace/BPT，精确检测点仍为 `UNVERIFIED` |

不要把以下三件事混为一谈：

1. Frida channel 能连上，不等于壳初始化成功。
2. `dlopen ret=ok`，不等于 `JNI_OnLoad`、壳自检和业务 DEX 解密成功。
3. 内存中出现 `Lcom/moutai/mall` 字符串，不等于已有可反编译 DEX。

## 2. 范围与硬性边界

- 设备：`82e459fc0920`。
- 案例目录：`E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk`。
- Hook 目录：`E:\code\逆向\android\case-studies\imoutai\hooks`。
- 只做本地静态分析、只读内存抓取、运行时观察和离线摘要比较。
- 不输入账号、手机号、短信、验证码；不保存 token/cookie；不访问真实业务服务；不构造或重放请求。
- 不冻结父进程、不执行 `kill -STOP`、不手工调用 `JNI_OnLoad`、不直接调用壳的 JNI 初始化函数。
- `scope.md` 保持不变；成功步骤写入 `notes\steps-log.md`，失败单独编号；证据不足必须写 `UNVERIFIED`。

## 3. 关键证据索引

### 3.1 当前侵入式分支已经失败

- `evidence\E5-mp23-console.log`：nativeLoad 参数被改成绝对路径，并替换为应用 `PathClassLoader`；`android_dlopen_ext(libDexHelper.so) ret=ok`；随后重复 `tgkill(33)`，`abort caller=libart.so`，约 2.1 秒进程终止，脚本销毁，业务类/DEX 均为 0。
- `evidence\E4-mp22-console.log`：maps redaction 发生过，但库加载因 `libandroid.so` namespace 错误失败，随后应用退出；说明“过滤 + loader 改写”不是稳定成功路径。
- `evidence\E3-mp21-console.log`：手工 direct-dlopen + 手工 JNI_OnLoad 触发 abort；该方法已判定无效。

### 3.2 离线提取尚未成功

- `evidence\magicless-dex-scan-4999.json`：业务字符串有命中，但满足 DEX 结构约束的候选为 `0`。
- `evidence\memdump4-coverage-and-carve-20260909.md`：干净进程 PID 17125 只读抓取 257 个区域，抓取后进程存活并停留在 `LoginActivity`；扩大映射筛选后，标准 DEX carve 为 `0 unique dex, 0 bytes`，magic-less 结构候选为 `0`。
- `evidence\vdex-sites-28343.json`：`vdexfile` 和 `vdex` 字符串命中均不满足 VDEX 版本/数量约束，有效 VDEX 候选为 `0`。
- `extract\memdump3-4999\208.bin.gz`：对应 `base.vdex` 映射解压后为 24,576 字节全零，无 VDEX/DEX magic；只能说明该快照不可用，不能据此推断原文件内容。

### 3.3 native 静态线索只能作为定位入口

- `evidence\elf-relocs-libdexhelper-raw.json`：原始 `libDexHelper.so` 有 235 个动态符号、27 个重定位，包含 `open/read/pread/mmap/mprotect/fopen/fgets/strcmp/strstr/dlopen/dlsym/abort` 等导入。
- `evidence\ida-disasm-3000-4100.txt`：`0x33F8` 起的函数读取 `/proc/self/maps`，解析映射范围并使用 ELF/`pread`/`mmap`/`mprotect`；同时调用 RC4-like 变换函数 `0x3184` 和自定义 `JNI_OnLoad` 查找函数 `0x303C`。
- 上述函数的确切自检条件、触发分支和 abort 因果链仍未证实。不要把 `0x33F8` 直接命名为“已确认的 JNI_OnLoad”或“已确认的 Frida 检测函数”。

### 3.4 干净进程 late attach 的新增事实

- 对干净 PID `17125` 做过一次只读 late attach 类审计；连接建立后，审计 RPC 返回前脚本被销毁，会话以 `process-terminated` 分离，随后 PID 存活检查失败。
- 本次没有得到类枚举结果，也没有生成 `class-audit.json` 或 `app-classes.txt`。
- 这只证明该次 attach 期间进程终止；没有确认终止由哪一项检测触发。详见 `evidence/attach-class-audit-17125-20260909.md` 和原始 `extract/attach-class-audit-17125/events.log`。

### 3.5 IDA 服务环境事实

- 已确认本机 IDA 路径存在，并按确定性启动脚本尝试启动 IDA MCP supervisor。
- supervisor 因缺少 `ida_pro_mcp.idalib_supervisor` 模块失败，端口 `13337` 未监听，未通过该服务继续打开样本。
- 这是工具环境失败，不是 APK、壳或检测逻辑结论。详见 `evidence/ida-mcp-start-failure-20260909.md`。

### 3.6 原始 `classes.dex` 载荷结构复核

- 对 APK 内原始 `classes.dex` 做了独立的头部、语义区、尾随载荷和魔数扫描；头部声明文件大小为 23,505,324 字节，但按可解析 DEX 结构得到的语义区终点为 22,924 字节，之后仍有 23,482,400 字节尾随载荷。
- 尾随载荷 SHA-256 为 `ad1fcccdee158fb3fc288c08af63f2c6f5e83240a06dc5089019620793a6eef8`；全文件除起始 DEX 外没有第二个 `dex\n`、`cdex`、`vdex` 或 ELF 魔数。
- 全文件出现 `dexdata0`、多个 `fdex` 和一个 `PK\x03\x04` 命中；该 ZIP 局部头不能独立解析为 ZIP，未发现中央目录或 ZIP 结束记录。
- 尾随载荷中有 `com/moutai/mall`、`login`、`signature` 等字节串，但这次扫描没有把它们提升为可反编译 DEX 或业务调用链证据。详见 `evidence/classes-dex-payload-20260909.json`。

### 3.7 `libDexHelper` 隐藏 native 阶段重建与静态标注

- 根据已有局部反汇编记录的变换行为，对原始 `libDexHelper.so` 的隐藏区 `[0x8000, 0x1281ed)` 做了单字节 `0x16` 异或重建，并用文件偏移 `0x1281ed` 的 16 字节 trailer key 对前 64 字节做 RC4-like 解密。
- 生成的重建文件为 `extract/libDexHelper-stage-decrypted-20260909.elf`，大小 1,180,141 字节；自定义 ELF 校验确认其为 AArch64、ET_DYN，共 8 个 program header，program header 的文件范围均在输出文件内。该结果是“结构有效的重建产物”，不是已证明可直接运行的完整原始文件。
- 对该重建产物生成了动态符号和重定位报告：`JNI_OnLoad` 动态符号位于 `0x14884`，动态符号 235 个，导入 231 个，重定位 2,054 个。导入面包含 `ptrace`、`kill`、`abort`、`/proc`/`dlopen`/`dlsym`、`mmap`/`mprotect`、socket 和 zlib 等能力；这些只是导入事实，不等于已确认某个检测分支。
- 使用本地 IDA 生成数据库 `extract/libDexHelper-stage-20260909.i64`，并输出 `evidence/ida-triage-libdexhelper-stage-20260909.json`、`evidence/ida-stage-focus-20260909.txt`。静态引用落在 `Anonymous-DexFile`/ART `DexFile::OpenMemory`、`dalvik/system/InMemoryDexClassLoader`、`DexPathList`、`classes.dve`、`KEY_RES_ENC`、`stamp-cert-sha256` 和 `/proc/self/maps` 等位置；当前仍没有从这些位置得到业务登录签名实现或逐字节签名结果。

### 3.8 运行时缓存文件与双进程离线快照

- 离线冷启动后，主进程为 PID `25103`，隔离进程为 PID `25135`，前台组件为 `com.moutai.mall/.module.login.LoginActivity`。
- 通过进程 root 视图只读取得应用缓存中的 `classes.dve`（24 字节）和 `classes.jar`（23,505,324 字节）。使用无损传输重取后，`classes.jar` 与 APK 内原始 `classes.dex` 大小、SHA-256 和逐字节内容完全一致；它是原始 `classes.dex` 副本，不是已确认的业务 DEX 解密产物。
- `classes.dve` 未匹配 DEX/CDEX/VDEX/ELF/ZIP 标记。原始文件结构报告为 `evidence/runtime-payload-triage-raw-20260909.json`。
- 对主进程抓取 273 个区域、对隔离进程抓取 133 个区域；两次均为不冻结进程的只读抓取，抓取后进程存活。
- 两份快照的标准 DEX carve 均为 `0 unique dex, 0 bytes`；magic-less 结构候选均为 `0`。主进程命中 `Lcom/moutai/mall=38`、`LoginActivity=23`，隔离进程命中 `Lcom/moutai/mall=0`、`LoginActivity=1`，均未形成可验证 DEX。
- 原始快照与扫描证据：`extract/memdump4-25103-1788890310`、`extract/memdump4-25135-1788890376`、`evidence/magicless-dex-scan-memdump4-25103-1788890310.json`、`evidence/magicless-dex-scan-memdump4-25135-1788890376.json`。完整事实记录见 `evidence/runtime-app-files-and-memdump-20260909.md`。

### 3.9 RiskStub/边界检测的静态与运行时事实

- 从主进程应用私有目录只读抽取了 `RiskStub-a.dex`、`RiskStub-l.dex`、`RiskStub-a.vdex` 和两份 `libRiskStub.so`；两份 DEX 内容相同，两份 SO 内容相同。
- `RiskStub-a.dex` 经 JADX 离线反编译得到 320 个 Java 文件，主体包为 `com.coralline.sea`，未发现 `com.moutai.mall` 业务包；独立事实报告为 `evidence/riskstub-static-triage-20260909.md`。
- `w7.java:506-575` 静态包含对 `frida-agent-32.so`/`frida-agent-64.so`、`/data/local/tmp/re.frida.server/`、`/data/local/tmp` 文件名和进程字符串的检查，并收集 `thread`、`gadget`、`lib`、`uds`、`memory` 指标。
- `c5.java:108-288` 静态包含 Java/native 函数、system/user library、Hook 和 Dex 注入结果组织，字段/类型包括 `chook`、`java_hook`、`dex_inject`、`dlopen`、`inject` 等。
- `k8.java`、`v2.java`、`u2.java` 静态包含 `Debug.isDebuggerConnected()`、本地端口 `23946/27042/27043`、ptrace/debug 结果检查。
- logcat 显示 `is init everisk: true` 以及 `.RiskStub/.a/` 下组件的运行时初始化/加载相关记录；干净离线进程仍到达 `LoginActivity`。
- 早期侵入式注入崩溃记录包含 `/memfd:frida-agent-64.so (deleted)`、`Fatal signal 6` 和 `No pending exception expected: java.lang.ClassNotFoundException: com.secneo.apkwrapper.H`；现有日志没有把它们绑定到单一检测函数，也没有证明当前红色界面的精确触发点。

### 3.10 native 完整静态导出与嵌入 DEX 复核

- 对 `extract/libDexHelper-stage-decrypted-20260909.elf` 完整导出了 `sub_3A178`、`sub_12F00`、`sub_10220`、`sub_1E33C` 及其相关辅助函数；产物为 `evidence/ida-libdexhelper-selected-full-20260909.txt` 和 `evidence/ida-libdexhelper-helper-functions-20260909.txt`。
- 静态导出显示 `sub_3A178` 读写 24 字节 `classes.dve`，调用形态与 MD5 压缩/收尾函数一致；随后读取 asset 并使用重复字符串 `KEY_RES_ENC` 对缓冲区进行异或，同时涉及 `.cache/assets`、`assets/baoef` 和 `libandroidfw.so`。
- 上述 `classes.dve` 的缓存/指纹解释仍为 `UNVERIFIED`；没有从该调用片段得到业务 DEX、登录签名算法或逐字节 digest。
- 对重建 native 阶段按 DEX 头和结构边界抽取，两个命中去重后只得到一个 284 字节 DEX；JADX 结果仅为 `defpackage.Empty` 空类，不是业务 DEX。证据为 `extract/embedded-stage-dex-20260909-v1/manifest.json`、`extract/jadx-embedded-stage-dex-20260909-v1/sources/defpackage/Empty.java`。

### 3.11 GLM3.5 mp24–mp26 接管结果

- mp24 的 JNIEnv 表槽位修复没有命中，`JNI_OnLoad` 仍以 ART abort 结束；证据为 `evidence/E6-mp24-console.log`。
- mp25 将观察点移到本设备 `libart.so` 的 `FindClass` 入口，确认此前的 ClassLoader 参数选择会使 `com/secneo/apkwrapper/H` 与 `AW` 查找返回空；证据为 `evidence/E7-mp25-console.log`。
- mp26 改用 nativeLoad 第三参数 caller 类 `H` 的 ClassLoader，并确认 `libDexHelper.so` 的 `dlopen` 返回成功。
- mp26 的 `JNI_OnLoad` 完成 7 批、20 个 native 方法注册；随后 PID `12502` 以 `SIGSEGV/SEGV_ACCERR` 退出，未捕获到 DEX。
- tombstone 将运行时相对偏移 `0x23974` 映射到重建阶段 `sub_1E33C+0x5638` 的 `LDR X21,[X0]`，调用点位于 `JNI_OnLoad+0x1e80` 的 `BL sub_1E33C`。
- 完整证据：`evidence/mp24-mp26-jni-crash-triage-20260909.md`、`evidence/ida-crash-functions-mp26-20260909.txt`、`evidence/E8-mp26-console.log`、`extract/obs-mp26/regnat.jsonl`。
- 该结果证明加载链路已越过 `FindClass` abort 并完成 native 注册，但不证明 Frida 稳定注入、壳检测绕过、异常检测绕过或业务 DEX 提取成功。

### 3.12 GLM3.5 mp27–mp32 诊断结果

- mp27–mp31 使用独立诊断 bundle 观察异常、地址范围、GetEnv 和实际 `sub_1E33C` 入口；均在 `libDexHelper+0x23974` 重复发生 protection failure，未捕获业务 DEX。
- mp28 的异常范围记录显示 PC 位于 `libDexHelper` 的 `rwx` 映射，而 `x0`/fault 地址位于匿名 `PROT_NONE` 映射；这只记录内存事实，不把创建者归因到某个检测分支。
- mp31 由本轮实际 `JNI_OnLoad` 地址推导入口并命中 `sub_1E33C`，记录到带 MTE 标签的 JNIEnv 指针 `0xb400007a35428400`；因此“崩溃发生在函数入口因为 JNIEnv 无效”没有得到支持。
- mp29 的 GetEnv 观察器未命中；mp30 使用了错误的函数基址，两个结果都不作为额外因果结论。
- mp32 的 `mmap/mprotect PROT_NONE` 观察改变了时序，仅完成 6 批注册且无同等 Crash 记录，标记为非稳定诊断轮次。
- 事实证据：`evidence/mp27-mp32-protection-triage-20260909.md`、`evidence/E9-mp27-exc-console.log` 至 `evidence/E14-mp32-protection-console.log`、`extract/obs-mp27-exc/` 至 `extract/obs-mp32-protection/`。

## 4. 我实际做过的事情

以下是事实记录，不是建议的执行顺序：

1. 建立并核对了 `scope.md`、`timeline.md`、`notes/steps-log.md`，确认案例是本地离线样本，设备为 `82e459fc0920`。
2. 使用过多版 Frida 脚本和驱动，包含：早期内存 DEX 扫描、类加载/`RegisterNatives`/`android_dlopen_ext` 观察、maps 读取过滤、`Runtime.nativeLoad` 库名改写、loader 参数改写、direct-dlopen/preload 试验。
3. 早期脚本能建立 Frida 通道并执行 native 只读抓取；其中一次用户反馈可以进入登录界面。现有日志本身没有把该用户反馈对应到唯一脚本哈希，因此不把它写成已复现事实。
4. `mp21` 做过 direct-dlopen + 手工 `JNI_OnLoad`，结果 abort；dropbox 栈显示 boot classloader/`FindClass` 失败。
5. `mp22` 做过 maps 过滤和加载观察，出现 maps redaction，但 `libDexHelper.so` 因 namespace/依赖问题加载失败，业务类为 0。
6. `mp23` 做过 nativeLoad 库名改写和应用 `PathClassLoader` 替换；日志出现 `android_dlopen_ext(...)=ok`，但约 2.1 秒后 `libart.so abort`、Trace/BPT、脚本销毁，业务类为 0。
7. 做过不冻结父进程的 memdump3，只读抓取 273 个区域，进程抓取后仍存活；标准 DEX carve 为 0。
8. 做过 magic-less DEX 结构扫描：`Lcom/moutai/mall` 有字符串命中，但合法 DEX 候选为 0。
9. 做过两个 VDEX 字符串命中点的离线复核：均不满足 VDEX 头约束，有效候选为 0。
10. 对 `libDexHelper.so` 做过 ELF 重定位、导入符号、IDA 局部反汇编和调用线索整理；看到 `/proc/self/maps`、ELF/pread、mmap/mprotect、字符串比较和变换函数，但没有确认具体检测分支。
11. 检查过脚本版本和 SHA256：`mp20-baseline` 为 `CA3914C5D6F9E2ABD6C53D7745DE583BBEE2C24446424440B5254FA4CCAA74CA`；`t3` 为 `CBD7A9BD66A923C3C5D5EC6D6E1154EFABBFE4D246045A9D8B235B844E8B7600`；`t4e` 为 `C2EB73D56339E5890D74A2FC9989CC512C24DEA12BD59A7B36268CCE660F77AA`。
12. 对干净 PID 17125 执行过扩大范围的只读内存抓取：257 个区域、进程存活；本地离线 carve 为 0 DEX，magic-less 结构候选为 0。该事实已记录在 `evidence/memdump4-coverage-and-carve-20260909.md`。
13. 对干净 PID 17125 做过一次只读 late attach 类审计；连接建立后在 RPC 返回前进程终止，未得到类审计输出，原始事件见 `extract/attach-class-audit-17125/events.log`。
14. 按 IDA 技能启动本地 supervisor，但因缺少 `ida_pro_mcp.idalib_supervisor` 模块退出，端口 `13337` 未监听；本轮没有把该工具失败解释为样本结论。
15. 对 APK 内原始 `classes.dex` 做了语义区/尾随载荷/魔数和标记扫描；得到语义区终点 22,924 字节、尾随载荷 23,482,400 字节，并将结果写入 `evidence/classes-dex-payload-20260909.json`。
16. 依据原始 `libDexHelper.so` 的静态变换线索重建了隐藏 native 阶段，生成 `extract/libDexHelper-stage-decrypted-20260909.elf`，并对其 ELF、动态符号和重定位做了离线校验；没有修改 APK 或设备文件。
17. 直接运行本地 IDA 对重建 native 阶段建立数据库并导出关注字符串及交叉引用；得到 `JNI_OnLoad` 地址、ART 内存 DEX 加载相关引用、`classes.dve`/`KEY_RES_ENC` 引用和导入面记录，但没有得到业务 DEX 或登录签名。
18. 离线冷启动后，通过进程 root 视图只读抽取了应用缓存中的 `classes.dve` 和 `classes.jar`；使用无损传输重取并与 APK 内 `classes.dex` 做大小、SHA-256、逐字节比对，确认 `classes.jar` 是原始 `classes.dex` 副本。
19. 对主进程 PID `25103` 和隔离进程 PID `25135` 分别执行了不冻结进程的 `dump-mem4` 只读快照，并离线运行标准 DEX carve 和 magic-less 结构扫描；两份均未得到合法 DEX 候选。
20. 从主进程应用私有目录只读抽取了两套 `RiskStub.dex`、`RiskStub.vdex` 和 `libRiskStub.so`，对 DEX 做了离线 JADX 反编译和包/关键字统计；未发现 `com.moutai.mall` 业务包。
21. 对 RiskStub 反编译结果做了静态事实摘录，记录 Frida、Hook、Dex 注入、调试器、端口和 ptrace 相关检测代码；没有把静态能力写成运行时已触发或已绕过。
22. 读取并筛选本地 logcat，记录 Everisk 初始化、干净进程到达 `LoginActivity` 以及历史注入崩溃栈；没有从日志确认红屏的单一触发点。
23. 对重建 `libDexHelper` 阶段完成关注函数及辅助函数的完整 IDA 文本导出，并按结构约束抽取/反编译嵌入 DEX；得到的唯一 DEX 只有 `defpackage.Empty` 空类。

## 5. 目前能确认和不能确认的事项

### 能确认

- Frida 连接、脚本加载和部分只读 native 操作曾经成功。
- 当前组合分支确实出现了用户所说的红色界面现象；对应日志也显示该分支很快发生 ART/进程异常终止。
- `dlopen ret=ok` 只发生在壳初始化链路的一个中间点，之后仍然 abort。
- 当前没有合法、可反编译、可验证的业务 DEX/VDEX 产物。
- 已经得到一个结构有效的 `libDexHelper` 隐藏 native 阶段重建产物，并完成离线 IDA 静态标注；这不等于壳已绕过或业务代码已恢复。
- 已确认运行时 `classes.jar` 与 APK 内原始 `classes.dex` 逐字节一致；双进程离线快照均未形成可验证业务 DEX/VDEX。

### 不能确认

- 红色界面究竟对应 maps 检测、Frida 检测、loader 身份、完整性校验、依赖加载，还是多个因素共同作用。
- `0x33F8` 是否就是实际 `JNI_OnLoad` 主体；目前只能确认它具有 maps/ELF 相关行为线索。
- 是否已经绕过梆梆/壳加载自检。
- 是否已经绕过异常/反调试检测。
- 登录签名算法、输入输出和逐字节 digest。
- 上述重建 native 阶段是否与设备运行时的完整代码逐字节一致，以及其静态引用是否最终连接到业务登录链路。

## 6. 证据文件清单

- 运行日志：`evidence/E1-dexdump-console.log` 至 `evidence/E5-mp23-console.log`。
- 内存扫描：`evidence/magicless-dex-scan-4999.json`、`evidence/magicless-dex-scan-17125-memdump4.json`、`evidence/memdump4-coverage-and-carve-20260909.md`、`evidence/vdex-sites-28343.json`。
- maps 与 native：`evidence/maps-analysis-28343.txt`、`evidence/elf-relocs-libdexhelper-raw.json`、`evidence/ida-disasm-3000-4100.txt`、`evidence/ida-disasm-2a00-303c.txt`。
- 过程记录：`notes/steps-log.md`。
- 静态 APK 补充：`evidence/apk-static-triage-20260909.md`。
- 壳元数据补充：`evidence/bangcle-metadata-triage-20260909.md`。
- native 字符串/导入补充：`evidence/native-string-triage-20260909.md`。
- late attach 审计：`evidence/attach-class-audit-17125-20260909.md`、`extract/attach-class-audit-17125/events.log`。
- IDA 服务环境：`evidence/ida-mcp-start-failure-20260909.md`。
- `classes.dex` 载荷结构：`evidence/classes-dex-payload-20260909.json`。
- 隐藏 native 阶段：`evidence/libdexhelper-stage-decrypt-20260909.json`、`evidence/elf-dynsym-libdexhelper-stage-20260909.json`、`evidence/elf-relocs-libdexhelper-stage-20260909.json`、`evidence/ida-triage-libdexhelper-stage-20260909.json`、`evidence/ida-stage-focus-20260909.txt`、`extract/libDexHelper-stage-decrypted-20260909.elf`、`extract/libDexHelper-stage-20260909.i64`。
- 运行时缓存与双进程快照：`evidence/runtime-app-files-and-memdump-20260909.md`、`evidence/runtime-payload-triage-raw-20260909.json`、`evidence/magicless-dex-scan-memdump4-25103-1788890310.json`、`evidence/magicless-dex-scan-memdump4-25135-1788890376.json`、`extract/runtime-app-files-25103-20260909-raw/`、`extract/memdump4-25103-1788890310/`、`extract/memdump4-25135-1788890376/`。
- RiskStub 与 native 完整导出：`evidence/riskstub-static-triage-20260909.md`、`evidence/ida-libdexhelper-selected-full-20260909.txt`、`evidence/ida-libdexhelper-helper-functions-20260909.txt`、`extract/jadx-riskstub-20260909-v1/`、`extract/runtime-code-files-25103-20260909-raw/`。
- 嵌入 DEX 复核：`extract/embedded-stage-dex-20260909-v1/manifest.json`、`extract/embedded-stage-dex-20260909-v1/embedded_000_284.dex`、`extract/jadx-embedded-stage-dex-20260909-v1/sources/defpackage/Empty.java`。
- 相关脚本：`case-studies/imoutai/hooks/` 下的 `dump-dex-hook-mp20-baseline.js`、`dump-dex-hook-compiled-t3.js`、`dump-dex-hook-compiled-t4e.js`、`dump-mem3.sh`、`dump-mem4.sh`、`magicless_dex_scan.py`、`parse_vdex_sites.py`、`elf_reloc_report.py`、`ida_disasm_range.py`。
- 本轮补充脚本：`case-studies/imoutai/hooks/pull_app_owned_file.py`、`analyze_runtime_payload.py`、`ida_dump_functions_full.py`、`extract_embedded_dex.py`。
- GLM3.5 崩溃映射脚本：`case-studies/imoutai/hooks/ida_dump_crash_full.py`；mp24–mp26 记录：`evidence/mp24-mp26-jni-crash-triage-20260909.md`、`evidence/E6-mp24-console.log`、`evidence/E7-mp25-console.log`、`evidence/E8-mp26-console.log`、`extract/obs-mp26/`。

### 6.1 静态 APK 补充事实

- 原 APK SHA-256 为 `02D69D153820695BCB40DF48B49FD98C88951D8C9898FDA980AA60D4EBD38C8E`，包含 23,505,324 字节的 `classes.dex`、`RiskStub.dex` 和多份壳/风险 native 库。
- `jadx --show-bad-code --no-res` 返回 0，输出 329 个 Java 文件；`com/moutai/mall` 下只有 `IsoService` 和 `R`，业务登录实现没有出现在可读类集中。
- Manifest 仍列出 `LoginActivity`、`MainActivity` 等业务类名；这只是组件声明，不是业务代码已提取的证据。
- `com.secneo.apkwrapper.H` 的静态代码显示先调用 `System.loadLibrary`，失败后尝试从 APK 解出 `libDexHelper` 到应用缓存目录，再经 native 方法继续；这说明 nativeLoad 直接改写会改变正常加载/回退语义，但没有单独证明红屏的精确触发点。
- `assets/meta-data/manifest.mf` 是无换行的多段自定义/加密元数据；整体 Base64 解码失败，分段解析未得到 DEX 或业务实现，字段格式和密钥均未确认。
- native 字符串筛选发现若干第三方库的 AES/MD5/SHA/HMAC/Nonce 能力，以及 `libCryptoSeed` 的 `getPrivateKey/getSeed` 字符串；当前没有任何目标登录调用者或请求字段链路证据，不能把这些符号当作登录签名实现。

## 7. 给 GLM 的冷提示词

```text
你接手的是一个本地、离线、已授权的 Android 逆向分析案例：i-Moutai v1.9.12，包名 com.moutai.mall，设备 82e459fc0920。

请先读取：
1. case 根目录的 scope.md；
2. notes/steps-log.md；
3. notes/glm-correction-handoff-20260909.md；
4. evidence/E1-dexdump-console.log 至 evidence/E5-mp23-console.log。

这份交接单只提供我已经做过的实验、日志、产物和不确定性，不规定你接下来采用哪条技术路线。请独立判断现有证据，必要时否定我的判断；不要默认沿用旧计划，也不要把“Frida 能连接”“dlopen ret=ok”或字符串命中升级为“已绕过/已提取”。

已知背景：用户反馈旧路径至少能进入登录界面；当前加入 maps 过滤、nativeLoad 改写、loader 替换等组合后出现红色界面，E5 记录约 2.1 秒后 libart abort、脚本销毁、业务类为 0。另已从 libDexHelper 隐藏区重建出结构有效的 AArch64 native 阶段，并完成本地 IDA 静态标注；这不是业务 DEX，也不是绕过成功证据。

请把你的接管结果单独写成事实、证据、推断和待验证项，并自行决定后续是否继续、回滚、换路线或停止。不要修改 scope.md，不访问真实服务，不输入或保存凭据，不构造/重放请求；所有没有直接证据的判断标为 UNVERIFIED。
```

## 8. 交接时的最终口径

目前交给 GLM 的是实验事实包，不是我的后续方案：Frida 通道与只读 native 抓取能力部分可用；当前侵入式加载分支会触发应用异常退出；RiskStub/Coralline 的检测能力已在静态代码中确认，Everisk 初始化已在日志中观察到；已得到一个结构有效的 `libDexHelper` 隐藏 native 阶段重建产物并完成静态标注；壳加载自检、异常检测绕过、业务 DEX 提取、登录签名恢复仍未完成或未证实。后续取舍由 GLM 根据证据自行决定。
