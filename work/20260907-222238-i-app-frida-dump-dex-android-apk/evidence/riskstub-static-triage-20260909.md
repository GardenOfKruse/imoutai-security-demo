# RiskStub 静态与运行时事实记录

日期：2026-09-09  
样本：i-Moutai v1.9.12 / `com.moutai.mall`  
设备：`82e459fc0920`  
范围：仅本地离线分析，不访问业务服务，不输入凭据，不构造或重放请求。

## 输入与抽取结果

从运行中的应用私有目录只读抽取了以下文件：

- `extract/runtime-code-files-25103-20260909-raw/RiskStub-a.dex`
  - 大小：610,152 字节
  - SHA-256：`85124109b6dd4f42a0f3fc4a639099ef63a35c8fe9c2da86f4c93c3ea8104183`
- `extract/runtime-code-files-25103-20260909-raw/RiskStub-l.dex`
  - 与 `RiskStub-a.dex` 大小和 SHA-256 相同
- `extract/runtime-code-files-25103-20260909-raw/RiskStub-a.vdex`
  - 大小：10,460 字节
- `extract/runtime-code-files-25103-20260909-raw/libRiskStub-a.so`
  - 大小：1,188,624 字节
- `extract/runtime-code-files-25103-20260909-raw/libRiskStub-l.so`
  - 与 `libRiskStub-a.so` 大小和 SHA-256 相同

`RiskStub-a.dex` 使用 JADX 离线反编译，命令为：

```text
E:\code\逆向\tools\jadx\bin\jadx.bat --show-bad-code --no-res -d extract\jadx-riskstub-20260909-v1 extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex
```

输出目录为 `extract/jadx-riskstub-20260909-v1`，生成 320 个 Java 文件。包名统计显示主体位于 `com.coralline.sea`，另有 `com.bangcle.everisk.Agent` 等类；未发现 `com.moutai.mall` 业务包。

## 静态可见的检测能力

以下内容是反编译源代码中直接可见的行为，不是对运行时分支是否执行的结论。

### Frida、进程与文件名检查

`extract/jadx-riskstub-20260909-v1/sources/com/coralline/sea/w7.java`：

- `w7.n()` 遍历 maps 字符串，检查 `frida-agent-32.so` 和 `frida-agent-64.so`。
- 同一函数检查 `/data/local/tmp/re.frida.server/frida-agent-32.so`、`/data/local/tmp/re.frida.server/frida-agent-64.so`，并枚举 `/data/local/tmp` 中包含 `frida` 的文件名。
- `w7.o()` 检查进程相关字符串是否包含 `frida`。
- `w7.m()` 将 `thread`、`gadget`、`lib`、`uds`、`memory` 等指标收集为结果数组。

对应源代码行：`w7.java:506-575`。

### Hook、Dex 注入和加载相关检查

`extract/jadx-riskstub-20260909-v1/sources/com/coralline/sea/c5.java` 中可见：

- 可按配置反射检查 Java 方法和 native 方法。
- 对 system/user library 的函数地址和参数做比对。
- 结果类型包括 `chook`、`java_hook`、`dex_inject`，同时组织 `dlopen`、`inject` 和 `statcktrace` 字段。

对应源代码行：`c5.java:108-158`、`c5.java:161-227`、`c5.java:229-288`。

### 调试器、端口和 ptrace 相关检查

- `k8.a()` 直接调用 `Debug.isDebuggerConnected()`，见 `k8.java:25-27`。
- `v2.a()` 检查调试器连接，并检查本地端口 `23946`、`27042`、`27043` 是否被占用；在特定配置下还记录 `ptrace`，见 `v2.java:17-45`。
- `u2.check()` 将 debug/ptrace 结果组织为风险事件，见 `u2.java:31-47`。
- `com.bangcle.everisk.Agent.init()` 进入 `q6.a(context, str)`，见 `Agent.java:9-12`。

## 运行时日志观察

本地 logcat 中观察到：

- `D ========bb=======: is init everisk: true`。
- 随后出现应用私有 `.RiskStub/.a/` 下 `RiskStub.dex` 与 `libRiskStub.so` 的执行许可/加载相关记录，并进入 Everisk `core.c` 活动日志。
- 干净离线启动时，主进程 PID `25103` 和隔离进程 PID `25135` 存活，前台组件为 `com.moutai.mall/.module.login.LoginActivity`。
- 早期侵入式注入崩溃记录中出现 `/memfd:frida-agent-64.so (deleted)`、`Fatal signal 6`，以及 ART abort 信息：`No pending exception expected: java.lang.ClassNotFoundException: com.secneo.apkwrapper.H`。

这些日志证明 RiskStub 初始化和某些历史注入崩溃发生过；过滤后的日志没有证明某个具体检测函数触发了当前红色界面，也没有证明任何检测已被绕过。

## 与壳阶段静态导出的关联事实

对 `extract/libDexHelper-stage-decrypted-20260909.elf` 做了完整 IDA 函数导出：

- `evidence/ida-libdexhelper-selected-full-20260909.txt`：`sub_3A178` 等关注函数的完整导出。
- `evidence/ida-libdexhelper-helper-functions-20260909.txt`：`sub_3E544`、`sub_3E114`、`sub_3EF28` 等辅助函数的完整导出。

导出中可见：

- `sub_3A178` 读写 `classes.dve`，文件长度为 24 字节；调用 `sub_3E544`/`sub_3E114`，静态形态与 MD5 压缩/收尾代码一致。
- 该函数随后读取 Android asset，并将返回缓冲区按重复字符串 `KEY_RES_ENC` 异或后交给后续处理。
- 同一调用链还涉及 `.cache/assets`、`assets/baoef` 和 `libandroidfw.so` 的 asset manager 相关处理。

以上是静态调用片段和字符串事实；尚未得到完整的业务 DEX、登录签名实现或逐字节签名结果。`classes.dve` 是缓存/指纹记录的解释目前仍为 `UNVERIFIED`。

## 嵌入 DEX 复核

使用 `case-studies/imoutai/hooks/extract_embedded_dex.py` 对重建 native 阶段按 DEX 头、大小、endian 和结构边界抽取：

- 输入：`extract/libDexHelper-stage-decrypted-20260909.elf`
- 输出：`extract/embedded-stage-dex-20260909-v1/manifest.json`
- 两个命中位置内容去重后得到 1 个唯一 DEX，284 字节，偏移 `0x11c090`。
- JADX 输出：`extract/jadx-embedded-stage-dex-20260909-v1/sources/defpackage/Empty.java`，内容仅为 `defpackage.Empty` 空类。

该嵌入 DEX 不是已确认的业务 DEX。

## 本报告的边界结论

- 已确认：RiskStub/Coralline 代码具备 Frida、Hook、Dex 注入、调试器和 ptrace 等检测能力；运行时日志确认 Everisk 初始化；壳阶段存在 `classes.dve`、asset 处理和相关 native 调用。
- 未确认：当前红色界面的精确触发点；Frida 稳定注入成功；梆梆/壳自检绕过；异常/反调试绕过；业务 DEX 提取；登录签名恢复。
- 本报告不指定后续路线，由接管者根据原始证据自行判断。
