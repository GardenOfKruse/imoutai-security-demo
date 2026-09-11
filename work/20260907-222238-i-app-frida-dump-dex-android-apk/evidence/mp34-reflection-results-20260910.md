# mp34：延迟附加与业务方法元数据

## 当前结论

Frida 已能附加并读取业务类元数据。最后一次反射会话 PID 17672 取得 209 个唯一类、1,937 条方法声明、0 条反射失败记录；结束时保存 253 个业务类名，统计 RPC 成功返回。DEX 捕获和 RegisterNatives 观测均为 0。

这证明当前构建在某个延迟附加窗口允许 Java 元数据观察。未证明加固检测、异常检测已整体绕过，也未恢复方法体、登录签名算法或逐字节基准。

## 可复核产物

- `extract/obs-mp34-reflect-20260910/reflection.jsonl`：209 行，224,283 字节。SHA-256 `2541D803DB45BFB4750D3420878B7D1AD3731D698EB35E0D1E533369891E1808`。
- `extract/obs-mp34-reflect-20260910/loaded-classes-17672.txt`：253 个类名。SHA-256 `8C588C6B22E45C5420EFFA2B2244C1D0361CBC6A75FA215A47CD047B6824CE5A`。
- `extract/obs-mp34-reflect-20260910/events.log`：统计、类清单落盘与主动结束会话记录。
- `extract/obs-mp34-lateattach-20260910/loaded-classes-snapshot-01.txt`：之前一轮的 209 个类名快照。

反射文件仅包含类名和方法声明，没有调用参数值、字段值或业务方法返回值。类清单与反射清单在不同时间采样，253 和 209 不应当作为矛盾或同一时点覆盖率。

## 已确认方法声明

下表是声明和类型关系，不是经过证明的调用图。

| 构件 | 声明或关联类型 | 证据限度 |
|---|---|---|
| `com.moutai.mall.module.login.LoginActivity` | `login(Editable, Editable, String, String)`、`authLogin(String, String, String)` | 私有方法存在；实现与执行分支未知 |
| 同上 | `getVerifyRequest(String): GetVerifyCodeRequest`、`fetchVerifyCode(String)`、`access$getService$p(...): api.f` | 请求构造入口与服务类型可定位；未调用 |
| `com.moutai.mall.api.f` | `E0(LoginRequest, Map, kotlin.coroutines.d): Object` | 登录模型对应的方法；Map 是否为 HeaderMap 尚未核验 |
| 同上 | `b1(AuthLoginRequest, Map, kotlin.coroutines.d): Object` | 认证登录模型对应的方法；HTTP 路径与参数注解未知 |
| 同上 | `s0(GetVerifyCodeRequest, kotlin.coroutines.d): Object` | 验证码请求模型对应的方法；未发送请求 |
| `com.moutai.mall.api.a` | `intercept(okhttp3.w$a): okhttp3.d0` | 网络拦截器候选；不能仅凭签名认定它生成请求签名 |
| `com.moutai.mall.api.b` | `g(): api.f`、`h(): okhttp3.z`、`i(): retrofit2.u` | 服务、HTTP 客户端、Retrofit 构造相关类型；实例值未知 |
| `LoginRequest` | getters：`Mobile`、`VCode`、`YdLogId`、`YdToken` | 属性访问器存在；无字段值、序列化注解或字节顺序 |
| `AuthLoginRequest` | getters：`BizSeq`、`CertPwdData`、`IdCardAuthData` | 同上 |
| `GetVerifyCodeRequest` | getters：`Md5`、`Mobile`、`Timestamp` | Md5 属性名不证明算法、输入顺序或它等同于登录签名 |
| `com.moutai.mall.util.sntp.b` | `intercept(okhttp3.w$a): okhttp3.d0` | 第二个网络拦截器声明；行为未知 |

209 类中的声明未出现 `native` 修饰符。该采样仅覆盖 `com.moutai.mall*`，不能排除第三方包或其他未采样构件中的 native 实现。

## 实验历史及纠正

1. 最初本地转发缺失，Frida server 不可达。设备已有 root，但首次 server 以 shell 身份启动：spawn 返回 InvocationTargetException，attach 返回 PermissionDeniedError。改为 root server 并建立 `8899 -> 27042` 转发后可附加。这些错误不属于目标 hook 检测证据。
2. 最初错误地将带 `import Java from 'frida-java-bridge'` 的源码直接交给 `create_script`。错误原因是未打包模块依赖，和全局标志位在 import 前后的位置无关。此前“移到 import 后即可修复”的解释已被后续失败否定。
3. 已生成独立 esbuild IIFE 产物。依赖按导入文件的目录解析；单纯切换 cwd 不能解决父目录源码对 `_build/node_modules` 的定位，最终使用 bridge 路径 alias。mp34/反射标志使用编译期 define 启用。
4. 早期 attach PID 9973：hook 安装后约 1.3 秒进程终止。设备 `tombstone_05` 对应 `2026-09-10 10:33:31.898221380+0800`、PID 9973：SIGSEGV / SEGV_MAPERR，fault `0xa8`，x0=0；栈顶 `art::OatDexFile::FindClassDef+52`，经过 `VMClassLoader_findLoadedClass` 和 `AP.instantiateApplication+756`。这是与旧 `libDexHelper+0x23974` 不同的故障；不能写成已经证明越过旧故障或已证明触发检测。
5. 正常启动的对照只证明有限时间内存活；Frida server 当时仍在运行，所以不是完整无 Frida 环境。后续日志包含 PID 14333 的广播投递失败与系统杀进程，但 PID 与此前 20 秒采样的 12649 不同，不能合并为单一进程生命周期或推导固定退出时限。
6. 启动约 2 秒后附加 PID 14788：控制台观察到 224 个业务类，约 19.5 秒 process-terminated，无 crash 对象；原因未证实。后来同目录被重用，原 events.log 被覆盖；该条目前只能引用本会话工具输出，不能谎称原日志仍完整存在。
7. PID 15921：209 类快照已保存，随后进程终止。`--wait 12` 实际超过 12 秒，因为同步 RPC 可延长会话，不能据此称为严格超时保证。
8. PID 17672：反射 209 类、结束清单 253 类；stats 成功返回，约 16.1 秒日志为 application-requested。驱动源码在结束处主动 `session.detach()`，这是会话正常清理路径，不是应用主动退出/崩溃的证据。detach 后的长期存活与界面状态未核验。

早期驱动未配置 Frida Python 的 log handler，部分 `[dexhook]` 日志只在控制台出现，没有进入 events.log。原始缺失内容没有补造。后续驱动已增加 log handler、新输出目录检查、源码预检查及 detached 状态处理；这些修改未重新进行设备验证。

## 离线推断边界

本轮把可分析入口推进到真实业务方法声明，但尚无方法体、HTTP 注解、签名输入字节或预期 digest。SIGSEGV 按需解密仍是假设；延迟附加与早期附加同时改变时机，不能单独归因于移除 X 观察器。LoginActivity 出现在类清单不代表登录界面已显示。

目标仍未完成。后续可依据上述入口补足调用关系和离线基准；不能调用验证码发送或登录方法来代替静态/元数据核验。
