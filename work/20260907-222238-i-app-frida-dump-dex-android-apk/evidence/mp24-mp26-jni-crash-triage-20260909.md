# mp24–mp26 JNI 加载链路与崩溃取证

日期：2026-09-09  
样本：i-Moutai v1.9.12 / `com.moutai.mall`  
设备：`82e459fc0920`  
范围：本地离线动态观察；未输入凭据，未访问真实服务，未构造或重放请求。

## mp24：JNIEnv 表窗口修复未命中

- v6 bundle 已加载并建立 Frida 会话。
- `JNI_OnLoad` 进入后仍发生 ART abort；该版本的 JNIEnv 表槽位修复回调没有触发。
- 这只证明壳没有通过该 JNIEnv 表槽位调用到被观测的 `FindClass`，不能据此判断没有调用 `FindClass`。
- 证据：`evidence/E6-mp24-console.log`。

## mp25：libart FindClass 入口观察

- 根据已有 crash 栈，在本设备 `libart.so` 中按相对偏移挂载 `FindClass` 入口观察。
- 该观察点命中并暴露了 `FindClass(com/secneo/apkwrapper/H)`、`FindClass(.../AW)` 返回空与错误 ClassLoader 参数之间的关联。
- 证据：`evidence/E7-mp25-console.log`。

## mp26：caller ClassLoader 修正后的结果

mp26 使用了当前 `dump-dex-hook.js` 中的 v8 loader 处理：

- `nativeLoad` 的库参数改写为应用私有绝对路径。
- loader 参数改为 nativeLoad 第三参数 caller 类 `H` 的 ClassLoader，并以 `H` 可加载作为校验。
- 日志确认 `android_dlopen_ext(libDexHelper.so) ret=ok`。
- `JNI_OnLoad` 进入后，`RegisterNatives` 共观察到 7 批、20 个 native 方法，包含：
  - `bs`、`is`、`us`
  - `hn`、`pn`、`d`
  - `gha`、`ghc`、`gah`、`sha`、`he`、`gv`
  - `bli`、`bla`、`blr`、`blq`、`blc`、`bls`、`blv`
  - `sn`
- 此后进程 PID `12502` 在约 2.1 秒时因 `SIGSEGV`、`SEGV_ACCERR` 退出；没有捕获到 DEX，Frida 脚本随进程销毁。
- 证据：`evidence/E8-mp26-console.log`、`extract/obs-mp26/events.log`、`extract/obs-mp26/regnat.jsonl`。

## mp26 tombstone 静态映射

从设备 crash buffer/dropbox 中读取到 PID `12502` 的记录：

- 信号：`SIGSEGV`，代码 `SEGV_ACCERR`。
- 运行时匿名代码段基址：`0x79c3e19000`。
- `pc=0x79c3e3c974` 对应该段相对偏移 `0x23974`。
- `lr=0x79c3e3c974`，调用栈中的上一帧相对偏移为 `0x16704`；更上一层为 Frida 生成的匿名桥接段。
- 使用已有 IDA 数据库 `extract/libDexHelper-stage-20260909.i64` 映射结果：
  - `0x23974` 位于 `sub_1E33C`（`0x1e33c–0x26fbc`）内，函数内偏移 `0x5638`，指令为 `LDR X21, [X0]`。
  - `0x16704` 位于 `JNI_OnLoad`（`0x14884–0x16dd0`）内，函数内偏移 `0x1e80`，指令为 `BL sub_1E33C`。
  - `0x180c` 在当前重建阶段 IDA 数据库中没有对应函数定义。
- 完整映射与反编译文本：`evidence/ida-crash-functions-mp26-20260909.txt`。

## 事实边界

- mp26 已证明：库加载返回成功、`JNI_OnLoad` 走到了 20 个 native 注册完成之后。
- mp26 未证明：壳自检已绕过、异常检测已绕过、业务 DEX 已解密捕获、登录签名已恢复。
- 当前 SIGSEGV 的直接静态位置已定位到 `sub_1E33C` 的一次内存读取；现有证据尚不能把非法访问归因于某一个 Frida hook、ClassLoader 返回值、壳自身状态或重建阶段差异。
- 本文只记录 mp24–mp26 已发生的动作和证据，不指定后续处理路线。
