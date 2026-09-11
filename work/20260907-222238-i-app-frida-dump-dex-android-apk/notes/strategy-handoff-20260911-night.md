# i茅台登录签名还原 — 阶段思路总结与后续推进手册（2026-09-11 深夜版）

> 面向：接管本任务的低成本执行模型（cheap model）
> Case: `work/20260907-222238-i-app-frida-dump-dex-android-apk`（scope.md 已 granted，offline-sample，勿改）
> 前序交接：`handoff-to-glm-20260911.md`（09-11 上午快照）+ `notes/steps-log.md`（F1-F33 失败清单，禁止重复）
> 本文 = 09-11 晚间新突破后的最新状态。读完直接按 §5 执行，不要重读全部历史。

---

## 1. 一句话状态

**登录接口全貌（URL/参数模型/注解/头部值）已通过纯反射拿到；剩下两件事：① 拿到 vcode 的 `(mobile, timestamp, md5)` 样本对破解 md5 拼接公式；② 观测一次真实请求的完整签名头。** 均不需要脱壳、不需要发任何构造请求。

## 2. 今晚新突破（全部有证据落盘）

| 突破 | 证据路径 |
|---|---|
| 220 个业务类的方法/参数/字段注解全量 dump（0 失败） | `extract/obs-mp34-annot2-t6-20260911/reflection.jsonl`（450KB） |
| 环境：Production base URL = `https://app.moutai519.com.cn` | 同上目录 events.log + `extract/obs-annot5-20260911/samples.json` |
| 头部常量值：3 个 `clips_` base64 token + 设备串 | `extract/obs-annot5-20260911/samples.json` |
| annot5 agent 骨架已建好（本地采样器，能装、能跑） | `hooks/dump-dex-hook-annot5.js` + `hooks/annot5-driver.py` |

关键方法论突破：**绕开脱壳，用"延迟附加 + 纯反射 agent"**。Everisk 熔断窗口内（进程启动后 ~30-48s 死）足够完成反射枚举和本地方法调用。今天上午的 attach 崩溃（`FindClassDef` 空指针）通过**重启手机**解决——那是设备/zygote 状态问题，不是壳检测。

## 3. 已确认事实（勿再重新推导）

### 3.1 登录相关接口（`com.moutai.mall.api.f`，Moshi 序列化，@hj.o=POST @hj.f=GET @hj.j=HeaderMap @hj.a=Body）

| 方法 | 接口 | 请求模型（字段） |
|---|---|---|
| `E0(LoginRequest, Map, cont)` | POST `/xhr/front/user/register/login` | LoginRequest{mobile, vCode, ydLogId, ydToken} |
| `s0(GetVerifyCodeRequest, cont)` | POST `/xhr/front/user/register/vcode` | GetVerifyCodeRequest{**md5**, mobile, timestamp} |
| `b1(AuthLoginRequest, Map, cont)` | POST `/xhr/front/user/register/ctdid/login` | AuthLoginRequest{bizSeq, certPwdData, idCardAuthData} |
| `u0(BindPhoneRequest, Map, cont)` | POST `/xhr/front/user/register/ctdid/bindLogin` | BindPhoneRequest{ctdidToken, mobile, vCode} |
| `y0(cont)` | GET `/xhr/front/user/authConfig` | 无参——LoginActivity `queryAuthArgs()` 会自然调用，**是天然的有机请求观测点** |

其他关键接口：预约 `/xhr/front/mall/reservation/add`(M)、下单 `/xhr/front/trade/order/standard/submit`(V) 等 117 个方法全在 reflection.jsonl 里可查。

### 3.2 头部值来源（`com.moutai.mall.api.a` = okhttp Interceptor，`api.b` 组装 client）

- `api.a.intercept(okhttp3.w$a)` — 所有请求的最终 headers 注入点（**签名头观测钩子已验证可装**）
- `api.a$d()`/`a$b.d()` = `android;31;Redmi;lime`（platform;sdk;brand;model 明文）
- `api.a$b.a()` = `clips_fxkuFiISc0R9T3lJLRksTnZAeRh7GigYLRwpTylILU4=`（base64，疑似设备指纹 token，native 生成）
- `api.a.b()`/`a$b.b()` = `clips_OlU6TmFRag5rCXwbNAQ/Tz1SKlN8THcecBp/HGhHdw==`
- `api.a.c()`/`a$b.c()` = `clips_ehwpSC0fLBggRnJAdxYgFiAYLxl9Si5PfEl/TC0afkw=`
- `clips_*` 值在两次会话中相同（设备绑定、非每请求变化）。**header 名→值的映射还没拿到**（要等 intercept 观测一次请求）。

### 3.3 LoginActivity 关键方法（实例方法，需活实例）

- `getVerifyRequest(String mobile): GetVerifyCodeRequest` — **纯本地工厂**，构造含 md5 的请求对象，不发网络 → 本地调用采样是合法且安全的核心手段
- `fetchVerifyCode(String)` / `login(Editable,Editable,String,String)` / `authLogin(...)` / `queryAuthArgs()` / `requestWClient(AuthArgsInfo)`

### 3.4 运行环境与窗口规律

- 设备 `82e459fc0920`（lime/Redmi, Android 12, arm64, Magisk root）
- frida-server 改名 `fs` @ `/data/local/tmp/fs`，端口 8899；`adb forward tcp:8899 tcp:8899` 后 `frida -H 127.0.0.1:8899`
- **熔断**：进程 ~30-48s 内被 Everisk 杀死（与是否注入无关）→ 一切动作在 T+4s 附加、T+28s 前完成
- attach 时若崩 `art::OatDexFile::FindClassDef` 空指针 → 重启手机 → 重启 fs → 重试（今晚验证过的恢复路径）
- Splash 可能长时间停留（隐私弹窗/网络等待）→ **不能假设 LoginActivity 自动出现**，要主动验证或直接拉起

## 4. 当前阻塞（接手时先看这里）

1. `sampleVcode` 返回 no instance：两次跑 driver 时堆上都没有 LoginActivity——因为 annot5-driver 硬编码先 force-stop 再启动 **SplashActivity**，而 Splash 卡住没走到 Login。
2. `a$a.invoke()` 不能静态调（是懒汉单例的实例方法）——已知，改用 `Java.choose` 或忽略（价值低）。
3. intercept 装上了但窗口内没有请求飞过（app 停在 Splash，无网络行为）。

## 5. 后续推进路线（按序执行，每步带验收）

### Step-A：让 LoginActivity 上屏并采样 vcode 三元组 ⭐ 最高优先

1. `adb -s 82e459fc0920 shell "su -c 'am start -n com.moutai.mall/.module.login.LoginActivity'"`
2. `sleep 5` 后用 `dumpsys activity top | grep -i login` **确认** Login 在顶层（不要跳过确认）
3. 若被弹窗挡住：`adb shell uiautomator dump /sdcard/ui.xml && adb pull` 看按钮文本，用 `input tap x y` 点掉（同意/继续）
4. 给 `annot5-driver.py` 加 `--activity` 参数（现在硬编码 Splash），或直接把 ACTIVITY 改成 LoginActivity
5. 跑采样（settle 4-5s，给 choose 留时间）：
   ```bash
   cd E:/code/逆向/android/case-studies/imoutai/hooks
   python -u annot5-driver.py --js dump-dex-hook-annot5.js \
     --out <case>/extract/obs-annot5c-<date> --settle 5
   ```
   **验收**：`samples.json` 里 `vcode` 数组 ≥3 条 `(mobile, timestamp, md5)`。

### Step-B：离线破解 vcode md5 公式（不需要设备）

对样本逐一测试假设（Python `hashlib`）：
- 拼接顺序：`mobile+timestamp` / `timestamp+mobile`
- 分隔符：无、`_`、`-`、`|`
- 大小写：md5 输出 lower（模型字段风格）与 upper
- 盐：先无盐；失败后用已知候选（"2XEG61"、"moutai"、"MT"、包名、版本号）——**只试字符串常量，不要编造**
- 注意 timestamp 是 String 字段：先确认是毫秒还是秒（长度 13 vs 10）
**验收**：全部样本公式复现一致，写入 `findings/login-signature.md`。

### Step-C：观测一次真实请求的完整 headers

- LoginActivity 上屏后会自然调 `queryAuthArgs()` → GET `/xhr/front/user/authConfig`（无参、无需凭据）→ 必过 `api.a.intercept`
- 在 annot5 基础上加：装好 intercept 后**多等 15-20s**（driver 加 wait），期间不要 detach
- **验收**：events.log 出现 `type:request`，记录全部 header 名→值；对照 clips_ 值确定 header 名（预期形如 `MT-*` 风格，以实测为准）

### Step-D：HeaderMap 内容与 E0 入参观测（可选加深）

- hook `api.f.E0/b1/u0`（Kotlin suspend，Frida 可 hook，末参是 Continuation）：`implementation` 里 dump LoginRequest 字段 + Map 内容后**原样放行**——只在 app 自身发起时触发，不要主动调用
- 若需要登录动作样本：由机主本人用自己的测试账号在屏幕上操作，采集端只读（scope 允许，凭据不落盘）

### Step-E：收尾交付

- `findings/login-signature.md`：接口定义表 + 参数表 + md5 公式 + 证据链（Evidence→Finding→Path）
- `findings/reproduce_sign.py`：输入样本三元组 → 重算 md5 → PASS/FAIL
- 防御建议（并入 `glm5.3-思路整理.md` §6）：vcode md5 静态盐/无盐的弱点、clips_ token 的设备绑定强度评估、HeaderMap 签名头集中注入的单点被 hook 风险、熔断时长的防护意义等
- 更新 `notes/steps-log.md`（新成功步骤 S 前缀，新失败 F34+）

## 6. 执行守则（cheap model 必读）

1. **禁止重复 F1-F33**（`notes/steps-log.md`）：尤其 spawn 注入脱壳、冻结快照、手工 JNI_OnLoad、maps 过滤、frida-dexdump——全部证伪。
2. **禁止**：发构造/重放请求、撞验证码、输真实凭据、改 scope.md、改设备系统配置（重启 fs/手机除外）。
3. **允许**：本地反射调用纯工厂方法（getVerifyRequest）、hook 后原样放行的只读观测、本人账号手动操作。
4. attach 崩溃恢复路径：重启手机 → `su -c 'nohup /data/local/tmp/fs -l 0.0.0.0:8899 &'` → `adb forward` → 重试（最多 2 次，再失败就停，记录后交回）。
5. 每个实验独立输出目录（driver 强制非空即拒），证据文件不覆盖。
6. 时间盒：单次会话 10 分钟内没新证据就停，写记录，不要循环重试。

## 7. 资产索引

- **Agent 构建流水线**：`hooks/_build/entry-annot{2,3,4,5}.js` → esbuild（见 `hooks/_build/` 命令历史：`node node_modules/esbuild/bin/esbuild entry-X.js --bundle --platform=neutral --format=iife --alias:frida-java-bridge=...index.js --alias:buffer=...buffer-shim.js --outfile=../dump-dex-hook-X.js`）
- **驱动**：`hooks/annot5-driver.py`（本次新增）、`hooks/obs-driver.py`（通用，支持 --attach-running/--attach-existing）
- **数据**：`extract/obs-mp34-annot2-t6-20260911/reflection.jsonl`（注解全景）、`extract/obs-annot5-20260911/samples.json`（头部值）
- **背景**：`glm5.3-思路整理.md`（总体）、`handoff-to-glm-20260911.md`（上午快照）、`login-signature-目标提示词.md`（目标定义）
