# RiskStub LoginChecker 静态证据复核 — 2026-09-09

## Evidence

本轮只读取已抽取并离线 JADX 反编译的 `RiskStub-a.dex`，没有修改 APK、设备文件或运行时进程。

| 文件 | 位置 | 直接观察 |
|---|---:|---|
| `s5.java` | 120–142 | `a(Object)` 仅在对象为 `Activity`、存在 `Window.Callback` 时包装窗口回调为 `i1` |
| `i1.java` | 56–83 | `dispatchTouchEvent` 记录触摸坐标、size、pressure 和时间戳，最多保留 16 条；随后仍调用原回调 |
| `s5.java` | 144–151 | `b(Object)` 接收字符串并写入静态账号字段，同时将触发标志置为 true |
| `s5.java` | 154–158 | `check()` 仅在触发标志为 true 且尚未执行时启动一次后台线程 |
| `s5.java` | 21–101 | 后台线程组装 `scene=login`、`protol_type=hxb_login`、账号、触摸事件和多类传感器事件，然后调用 `push(e2.b, "hxb_login", ...)` |
| `s5.java` | 181–191 | `initialize()` 注册 Activity 生命周期回调并初始化传感器采集器 |
| `q6.java` | 41–53, 68–83 | 结果分发可通过反射回调 `onResult`；另有独立的 `AlertActivity` 启动封装，但本轮未建立它与 `LoginChecker` 的直接调用链 |

## Finding

`RiskStub` 中确实存在一个面向登录场景的行为/传感器采集器，并带有 `hxb_login` 结果通道。该路径更像登录风险遥测或检测结果上报链，不能仅凭类名和字符串认定为业务登录签名算法。

## UNVERIFIED

- 未证实 `LoginChecker` 在本次红色界面复现中被触发。
- 未证实 `hxb_login` 是否实际离开进程；当前案例的 `network_profile=offline`，本轮没有放行或重试网络。
- 未证实 `q6.AlertActivity` 与红色界面存在调用关系；现有静态片段不足以把它归因为红屏。
- 未得到登录请求的 URL、参数序列、密钥来源或 byte-exact digest。

## Path

- `extract/jadx-riskstub-20260909-v1/sources/com/coralline/sea/s5.java`
- `extract/jadx-riskstub-20260909-v1/sources/com/coralline/sea/i1.java`
- `extract/jadx-riskstub-20260909-v1/sources/com/coralline/sea/q6.java`
