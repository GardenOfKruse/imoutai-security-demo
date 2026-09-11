# i茅台 v1.9.12 登录链路签名还原 — Findings（2026-09-11）

> Case: `20260907-222238-i-app-frida-dump-dex-android-apk` | scope: offline-sample（granted）
> 方法论：**延迟附加 + 纯反射/只读 hook agent**（不脱壳、不构造请求、不重放）
> 结论等级：F1 公式已逐字节验证；F2-F5 有运行时证据；F6 归因进行中

---

## F1. 短信验证码请求的 md5 签名公式（已验证 ⭐）

**Finding**：`GetVerifyCodeRequest.md5 = MD5( deviceKey + mobile + timestamp )`，其中 `deviceKey = 2af72f100c356273d46284f6fd1dfc08`（32 位十六进制，本设备绑定，跨会话稳定）。

**Evidence**：
1. 接口定义（`extract/obs-mp34-annot2-t6-20260911/reflection.jsonl`）：`api.f.s0` → `@hj.o(value=/xhr/front/user/register/vcode)`（POST），请求体 `GetVerifyCodeRequest{md5, mobile, timestamp}`（Moshi 序列化，字段名即 JSON key）。
2. 输入字节捕获（`extract/obs-annot7-20260911/samples.json`）：hook `java.security.MessageDigest.update([B)`，本地调用 `LoginActivity.getVerifyRequest(mobile)` 时捕获到的原始输入，例（mobile=10086）：
   ```
   32af72f100c356273d46284f6fd1dfc08 | 10086 | 1789140362120
   → md5 = 9a654aa5fbf4afe210aadc14f71254a6 = GetVerifyCodeRequest.md5 ✓
   ```
   （注：上例首字符为 `2`，"32af..." 是 deviceKey 首字符 '2' 前无分隔——精确串见 samples.json `update` 记录。）
3. 调用链（digest 栈回溯）：`LoginActivity.getVerifyRequest(LoginActivity.kt)` → `com.netease.libs.yxsecurity.encrypt.CryptoUtil.l` → `CryptoUtil.o` → `MessageDigest.digest`（MD5）。
4. 验证：3 个独立会话 × 5 组合成手机号 = **15/15 逐字节 PASS**（`findings/reproduce_sign.py` 可复核）。

**Path（推导链）**：`api.f.s0` 注解定位接口 → LoginActivity 实例方法 `getVerifyRequest` 为纯本地工厂 → 本地反射调用触发真实构造 → 构造器 hook 拿三元组 → MessageDigest hook 拿明文拼接串 → 离线 md5 复算比对。

**限制**：deviceKey 的**来源**未最终归因（候选：CryptoUtil native 生成 / 服务端 ctdid 下发 / 本地存储）。它对本设备恒定，跨进程重启不变。其他设备上的值可能不同——复用到其他设备前需先读取该设备的 key（同法可采）。

## F2. 登录接口定义（已验证）

| 项 | 值 | Evidence |
|---|---|---|
| Base URL（生产） | `https://app.moutai519.com.cn` | 运行时枚举 `com.moutai.mall.env.Endpoints`（annot4/annot5 events.log） |
| 短信验证码 | POST `/xhr/front/user/register/vcode`，body=`{md5, mobile, timestamp}` | reflection.jsonl |
| 短信登录 | POST `/xhr/front/user/register/login`，body=`LoginRequest{mobile, vCode, ydLogId, ydToken}`，另有 `@hj.j()`=HeaderMap 参数 | reflection.jsonl |
| 一键登录（中移动） | POST `/xhr/front/user/register/ctdid/login`，body=`AuthLoginRequest{bizSeq, certPwdData, idCardAuthData}` | reflection.jsonl |
| 绑定登录 | POST `/xhr/front/user/register/ctdid/bindLogin`，body=`BindPhoneRequest{ctdidToken, mobile, vCode}` | reflection.jsonl |
| 序列化 | Moshi（`com.squareup.moshi`），字段名直出，含自定义 Boolean/Long ByString 适配器 | reflection.jsonl |

`ydLogId/ydToken` 来源于中国移动一键登录 SDK（仅一键登录路径需要）；纯短信登录路径 body 只需 `mobile+vCode`。

## F3. 请求头签名体系（部分观测，值来源已定位）

- 头部注入点：okhttp 拦截器 `com.moutai.mall.api.a.intercept`（`api.b.j(Context, Endpoints, api.a)` 注册；Retrofit client 组装）。
- 头部值常量（运行时读取，本设备）：
  - `api.a$b.a()` = `clips_fxkuFiISc0R9T3lJLRksTnZAeRh7GigYLRwpTylILU4=`
  - `api.a.b()/a$b.b()` = `clips_OlU6TmFRag5rCXwbNAQ/Tz1SKlN8THcecBp/HGhHdw==`
  - `api.a.c()/a$b.c()` = `clips_ehwpSC0fLBggRnJAdxYgFiAYLxl9Si5PfEl/TC0afkw=`
  - `api.a$d()/a$b.d()` = `android;31;Redmi;lime`（platform;sdk;brand;model）
  - `clips_*` 三值跨会话相同（设备绑定）。`clips_` 前缀为 base64，明文形态未解（疑与 ctdid/设备指纹相关）。
- **Header 名→值映射未拿到**（intercept hook 已装好，但窗口内没有请求飞过；复跑时等 `queryAuthArgs()` 的有机请求即可，见 §复跑指引）。

## F4. CryptoUtil（native 桥）能力（已验证存在）

- `com.netease.libs.yxsecurity.encrypt.CryptoUtil`：方法 `j,a,b,c,d,e,f,g,h,i,k,l,m,n,o,getSeed,getPrivateKey`；`j()` 为 native（返回单例）。
- `getPrivateKey()` 返回 PKCS#8 RSA 私钥（`MIIEvQIBADANBgkqhkiG9w0BAQEFAASC...`，base64，运行时从 native 取出）——**私钥由 native 层派生/存储**，Java 层无明文常量。用途未验证（候选：请求头 RSA 签名 / ctdid 协议）。
- 对应 native 库 `libCryptoSeed.so`（导出 `CryptoUtil_getSeed`/`CryptoUtil_getPrivateKey`；注意 09-11 上午 evidence 曾裁定其可执行段为"加载/自检边界"，JNI 名与实现的对应关系当时标 UNVERIFIED——本次运行时已证实 `getPrivateKey` 确实产出 RSA 私钥，修正该裁定的一半）。

## F5. 运行环境约束（对复现作业重要）

- Everisk 风控对 root 环境有 ~30-48s 熔断自杀（进程级），与是否注入无关 → 所有采集须在 T+4s 附加、~25s 内完成。
- attach 后若崩 `art::OatDexFile::FindClassDef+52`（空指针）→ 重启手机解决（09-11 实测）。
- frida-server 改名 `fs` @ 8899；frida 17 agent 需 esbuild 打包 frida-java-bridge（`hooks/_build/` 流水线）。
- Splash 可能卡隐私弹窗；直接 `su -c am start .module.login.LoginActivity` 拉起即可获得活实例。

## F6. 未完成事项（后续接手）

1. deviceKey 来源归因（CryptoUtil 字段 `a` 是 byte[]、`c/d` 是 char[]，dump 其内容可定性）。
2. intercept 头部名→值映射（Step-C，agent 就绪 `dump-dex-hook-annot8.js`，跑法见 strategy-handoff §5）。
3. 登录接口 E0 的 HeaderMap 实际内容（需一次真实登录动作，机主本人操作，采集端只读）。
4. RSA 私钥用途验证（是否用于请求头签名）。

## 防御视角注记（给开发者的核心输入）

1. **vcode 签名形同虚设**：`md5(deviceKey+mobile+timestamp)` 的三个输入对攻击者全部可得（deviceKey 设备绑定但可被同机读取/抓包重放），且无盐、无 HMAC、无 nonce 防重放——应改为 HMAC-SHA256(server-issued token) 并加时间窗+nonce 服务端校验。
2. **签名实现集中在一个 Java 工厂方法**（`getVerifyRequest`），反射调用即可批量构造合法请求对象（本次 15 样本零网络请求取得）——签名构造应下沉 native 或至少拆散调用链。
3. **RSA 私钥运行时可取**：`getPrivateKey()` 无任何调用方校验，任何注入态都能导出——私钥应每会话服务端协商，或至少增加运行时调用环境校验。
4. **熔断自杀是唯一挡住本次全量提取的机制**：~30-48s 窗口显著抬高成本，但延迟附加可完全在窗口内完成反射采集——建议风控信号（root/frida/注入检测）上报服务端而非客户端自杀，避免被"快进快出"策略绕过。
