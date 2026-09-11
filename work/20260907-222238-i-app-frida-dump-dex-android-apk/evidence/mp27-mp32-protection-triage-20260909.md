# mp27–mp32 protection/crash triage

日期：2026-09-09  
案例：i-Moutai v1.9.12，`com.moutai.mall`  
设备：`82e459fc0920`  
范围：本地离线、无真实请求；仅记录 GLM3.5 mp26 后的诊断性运行。

## 结论摘要

- mp27–mp31 均完成了 `libDexHelper.so` 的 `dlopen`、进入 `JNI_OnLoad`，并观察到同一 native 崩溃位置；均未捕获业务 DEX。
- mp28 的异常观察明确：故障 PC 位于 `libDexHelper` 映射内，而被读取的 `x0` 地址位于匿名 `PROT_NONE` 区域。
- mp31 在 `sub_1E33C` 实际入口观察到带 MTE 标签的 JNIEnv 指针；因此当前证据不支持把崩溃简单归因为“入口 JNIEnv 无效”。
- mp29 的 `JavaVM->GetEnv` 观察点没有命中；这只能说明该观察点未捕获到调用，不能推出 `GetEnv` 没有发生。
- mp32 加入 `mmap/mprotect PROT_NONE` 观察后，运行时序和注册数量发生变化，最终未形成同等条件下的稳定成功或稳定崩溃，作为补充诊断，不作为成功证据。

## 分轮事实

### mp27：异常观察器

产物：`evidence/E9-mp27-exc-console.log`、`extract/obs-mp27-exc/`。

异常观察器记录 `access-violation`，PC 为 `libDexHelper` 映射内的相对偏移 `+0x23974`；进程约 2.2 秒退出，Frida 会话随后销毁。该轮没有 DEX 捕获。

### mp28：地址范围补充

产物：`evidence/E10-mp28-exc-ranges-console.log`、`extract/obs-mp28-exc-ranges/`。

异常记录为：

```text
pc=0x79c403b974
x0=0x79c8997cb0
pcRange=0x79c4018000..rwx
faultRange=0x79c893e000..protection=---
x0Range=0x79c893e000..protection=---
```

故障相对偏移仍为 `+0x23974`。这证明该次非法读取的目标地址处于不可访问匿名区域；没有证明该区域由应用检测逻辑创建。

### mp29：GetEnv 观察

产物：`evidence/E11-mp29-getenv-console.log`、`extract/obs-mp29-getenv/`。

JavaVM `GetEnv` 观察器已安装但没有输出命中；`JNI_OnLoad` 仍完成 7 批、20 个 native 方法注册，随后在相同相对偏移发生保护错误并退出。该观察器未命中不改变对 JNIEnv 来源的判断。

### mp30：错误基址的入口观察

产物：`evidence/E12-mp30-stageentry-console.log`、`extract/obs-mp30-stageentry/`。

初始 `sub_1E33C` 观察点使用了与本次实际加载基址不匹配的地址，因此没有命中实际入口；同轮仍在 `+0x23974` 发生保护错误。该观察点结果不用于证明函数未执行。

### mp31：实际入口观察

产物：`evidence/E13-mp31-actual-entry-console.log`、`extract/obs-mp31-actual-entry/`。

由本轮实际 `JNI_OnLoad` 地址推导出的 `sub_1E33C` 入口命中：

```text
actual sub_1E33C enter env=0xb400007a35428400 envRange=none
```

`envRange=none` 是因为该值带 MTE 标签，范围查询接口未解析该带标签地址；不应解释为“没有内存范围”。随后仍完成第 7 批 native 注册，并在 `libDexHelper+0x23974` 发生同类保护错误。

### mp32：PROT_NONE 创建观察

产物：`evidence/E14-mp32-protection-console.log`、`extract/obs-mp32-protection/`。

观察到多次 `mmap`/`mprotect` 创建或设置 `PROT_NONE` 的调用，其中若干 caller 位于系统/runtime 映射（例如 `0x7aea509...`）。该轮在约 1.8 秒退出，Frida 返回无明确 Crash 对象，只记录到 6 批 native 注册；相较 mp27–mp31 行为发生时序变化，因此不能作为稳定复现或成功证据。

## 静态映射

已有 IDA 文本将运行时 `libDexHelper+0x23974` 映射为 `sub_1E33C+0x5638`，对应指令 `LDR X21,[X0]`；调用点位于 `JNI_OnLoad+0x1e80` 的 `BL sub_1E33C`。完整导出见 `evidence/ida-crash-functions-mp26-20260909.txt`。

## 当前边界

这些轮次确认了加载链路在当前改写组合下可以越过此前的 `FindClass`/ART abort 并完成 native 注册，但仍未证明：稳定 Frida 注入、梆梆/壳自检绕过、异常/反调试绕过、业务 DEX 捕获或登录签名恢复。没有执行真实请求，也没有使用凭据、token 或 cookie。

