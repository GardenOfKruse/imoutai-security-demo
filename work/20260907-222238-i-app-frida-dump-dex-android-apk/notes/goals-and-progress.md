# 目标与里程碑总档（最终报告主线）

> 用途：面向最终《风控能力评估报告》的目标-里程碑总视图。
> 规则（见 AGENTS.md §进度记录规则）：**每完成一个小目标/小突破，立即更新本文档对应状态 + 追加 steps-log.md**。
> 状态图例：✅ 已验证 | 🔄 进行中 | ⬜ 未开始 | 🚫 红线禁止

最后更新：2026-09-12

---

## G1. 账户链路接口全景整理（注册 / 短信 / 登录 / 实名认证）

| 里程碑 | 内容 | 状态 | 证据 |
|---|---|---|---|
| M1.1 | 短信验证码接口：`POST /xhr/front/user/register/vcode`，body=`{md5, mobile, timestamp}`，md5 公式已破解 | ✅ | findings F1、reproduce_sign.py 15/15 |
| M1.2 | 登录接口：`POST /xhr/front/user/register/login`，body=`{mobile, vCode, ydLogId, ydToken}` + HeaderMap | ✅（接口定义） | reflection.jsonl |
| M1.3 | 一键登录/绑定：`/register/ctdid/login`（AuthLoginRequest{bizSeq,certPwdData,idCardAuthData}）、`/register/ctdid/bindLogin` | ✅（接口定义） | reflection.jsonl |
| M1.4 | 注册语义确认：SMS 登录即隐式注册（`register/cancel` 为注销），需运行时确认 | 🔄 | api.f.y 待 hook |
| M1.5 | 实名认证接口组：`realNameAuth`（实名）、`realPersonAuth`/`realPersonAuthV2`（人脸核身）、`getRealNameAuthInfo` | ✅（接口定义）/ ⬜ 请求模型字段待运行时补全 | reflection.jsonl |
| M1.6 | HeaderMap 完整内容（签名头名→值映射） | ⬜ | annot8 agent 已就绪，待有机请求 |

## G2. 下单后弹窗验证码机制

| 里程碑 | 内容 | 状态 | 证据 |
|---|---|---|---|
| M2.1 | 识别验证码承载组件：APK 资源确认 `com.netease.nis.captcha.CaptchaWebView`；具体 challenge 形态仍未确认 | 🔄（组件已确认） | `jadx-out/resources/res/layout/yd_dialog_captcha*.xml`；S6-36 |
| M2.2 | 验证码校验协议还原（参数、签名、服务端校验点） | ⬜ | — |
| M2.3 | "通过逻辑"分析报告（机制 + 防御建议；不产出绕过工具） | ⬜ | — |

## G3. 已验证步骤的自动化固化（避免重复验证）

| 里程碑 | 内容 | 状态 | 证据 |
|---|---|---|---|
| M3.1 | vcode 签名构造器（离线复现，15/15 PASS） | ✅ | findings/reproduce_sign.py |
| M3.2 | 登录态请求模板（HeaderMap 构造 + App/H5 双会话注入，仅授权窗口内可用） | ✅（有效账号登录→H5 purchaseInfo 及缓存重载均 200） | findings/live-flow-host-and-auth-audit-20260912.md；steps-log S6-32 |
| M3.3 | 测试用例自动执行器（内置单进程/窗口校验/计数/中止条件） | 🔄 | demo-app realApi.js（窗口/预算硬门禁已内置） |
| M3.4 | 已通过用例回归清单（一键重跑已 PASS 用例，窗口内） | ⬜ | — |
| M3.5 | 实弹模式（授权门 + 真实下单止步支付）进入 demo | 🔄（已验证到 purchaseInfo；订单写接口未接通） | demo-app ModeGate.jsx + realApi.js；steps-log S6-27~S6-32 |

## G4. 支付链接拼接算法（🚫 只做静态还原，禁止真实调用）

| 里程碑 | 内容 | 状态 | 证据 |
|---|---|---|---|
| M4.1 | 支付方式枚举（PayMethod model 已定位）与支付渠道清单 | 🔄 | reflection.jsonl |
| M4.2 | 各渠道支付链接/scheme 拼接模板还原（JS/App 静态分析） | ⬜ | — |
| M4.3 | 用户安全支付建议（拼接逻辑弱点 → 防钓鱼/防篡改建议） | ⬜ | — |

**红线声明（写入 AGENTS.md）**：支付链接只做拼接算法静态整理；绝对禁止真实调用（HTTP/浏览器/scheme 唤醒/参数重放），防止破坏线上账务。所有涉支付产出必须带此声明。

## G5. H5 演示页（向 APP 开发者演示攻击视角）

| 里程碑 | 内容 | 状态 | 证据 |
|---|---|---|---|
| M5.1 | 单文件演示页（攻击链路可视化 + 交互式签名计算器 + 防御建议） | ✅ | `demo/imoutai-security-demo.html` |
| M5.2 | **Vite+React 交互式演示应用**（双模式：Mock 演示 + 实弹授权门；六步全流程；协议请求日志；口述稿） | ✅ | `case-studies/imoutai/demo-app/`（build PASS + dev server 冒烟 PASS） |
| M5.2.1 | **API 全景 Swagger 文档**（105 接口 + 42 模型 schema + 基础设施节，内嵌 Swagger UI 离线可用） | ✅ | `demo-app/docs/imoutai-openapi.json` + `hooks/gen_openapi.py` |
| M5.3 | 演示页/应用随 G2/G4 进度补全（验证码证据边界、Mock 状态机、支付渠道还原） | 🔄（验证码 Mock 与无真机算法研究入口已补强，原生协议未验证） | `demo-app/src/components/CaptchaVerify.jsx`、`src/components/OfflineIdentityLab.jsx`；S6-36~S6-37 |

## G6. 《风控能力评估报告》终稿

| 里程碑 | 内容 | 状态 | 证据 |
|---|---|---|---|
| M6.1 | Phase 0 离线分析（H5 签名链 + 设备号归因） | ⬜ | 方案 §2 |
| M6.2 | 维护窗口之外的低频 runbook 执行 | ⬜ | 方案 §3（AGENTS.md 硬门禁） |
| M6.3 | 报告终稿（以本文档 + steps-log 为主线） | ⬜ | — |

---

## 突破时间线（随进度追加）

| 日期 | 突破 | 详见 |
|---|---|---|
| 2026-09-11 | 220 类注解全量 dump（延迟附加+纯反射） | steps-log S5-29 |
| 2026-09-11 | 登录接口全景 + Endpoints + clips_ 头部值 | steps-log S5-30 |
| 2026-09-11 | **vcode md5 公式破解（15/15 逐字节）** | steps-log S6-1 |
| 2026-09-11 | CryptoUtil RSA 私钥运行时可取 | findings F4 |
| 2026-09-12 | 授权/窗口/单进程规则体系固化 | steps-log S6-2~4 |
| 2026-09-12 | **Vite+React 双模式演示应用交付**（Mock/实弹，含支付红线与授权门） | steps-log S6-6 |
| 2026-09-12 | **API 全景 Swagger 文档**（105 接口 + 42 模型，运行时取证自动生成） | steps-log S6-8 |
| 2026-09-12 | 登录响应 / Token 提取链路只读排查：HTTP 200 已见，但 gzip 解压、Token 提取和登录态持久化仍有缺口 | steps-log S6-24、evidence/login-token-extraction-triage-20260912.md |
| 2026-09-12 | 修复 UI 请求档案与签名 key；Live UI 短信返回 HTTP 200/code=2000，异常账号登录正确解析 HTTP 480/用户已注销 | steps-log S6-27~S6-29 |
| 2026-09-12 | 客户时间规则更新：仅 06:00–06:15 维护窗口禁发，其余时间允许 | steps-log S6-25 |
| 2026-09-12 | 全流程主机映射与登录态审计；修复 Token/Cookie 传播，阻断未验证订单 body，统一 2 秒间隔与日志脱敏 | steps-log S6-30；findings/live-flow-host-and-auth-audit-20260912.md |
| 2026-09-12 | 有效账号取得 App/H5 双会话；登录后及缓存重载后的 H5 purchaseInfoV2 均 HTTP 200/code=2000 | steps-log S6-32 |
| 2026-09-12 | 验证码证据边界收敛；Mock 三类 fixture 改为可重复失败/成功/刷新状态机，订单提交延后到验证码之后 | steps-log S6-36 |
| 2026-09-12 | 新增无真机算法研究台；合成 ID 与真实 HeaderMap 解耦，Live 层拒绝 synthetic 档案 | steps-log S6-37 |
