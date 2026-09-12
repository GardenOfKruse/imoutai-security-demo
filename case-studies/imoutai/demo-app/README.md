# i茅台 App 安全培训演示（Vite + React）

> 面向 APP 开发者的安全培训交互演示：完整还原「短信登录 → 选购 → 提交订单（验证码自动识别）→ 填地址 → 选支付 → 生成支付链接」的攻击者视角流程。
> **⚠️ 红线**：支付链接只做拼接展示，禁止真实调用；实弹模式仅在维护窗口之外的低频 runbook 执行。

## 两种模式

| 模式 | 网络行为 | 用途 |
|---|---|---|
| 🟢 演示模式（Mock） | 零生产交互，全部本地模拟 | 日常培训演示，随时可放 |
| 🔴 实弹模式（Live） | **全真实环境**：真实请求、真实业务数据、真实业务步骤（真实登录、真实下单**止步支付**），请求中不含任何模拟标记 | 仅限授权 runbook F 组用例的低频执行 |

实弹模式有四重硬门禁（`src/lib/realApi.js`）：授权三确认 → 时段校验（除 **06:00–06:15** 客户维护窗口外均可执行）→ 设备 HeaderMap 档案（自动生成，真实 App 形态无模拟标记）→ 单进程低频与请求预算 ≤300。任一不满足即拒绝发请求。

**跨域（CORS）说明**：浏览器直连生产域名会被 CORS 拦截。本 demo 通过 vite 本地代理解决（`/mt-app` → `app.moutai519.com.cn`、`/mt-h5` → `h5.moutai519.com.cn`，dev 与 preview 均已配置），浏览器视角同源。教学点：CORS 只是浏览器内防线，真实攻击者用自定义客户端根本不受它约束——别把 CORS 当安全方案。

**实弹全真实原则（客户明确要求，2026-09-12）**：实弹模式下禁止任何模拟标记/测试痕迹进入真实请求（UA、头、参数全部按真实 App 形态构造）。本次培训的核心结论——"协议级复刻的请求与正常用户订单无法区分、后台无异常可查"——依赖于此。验证码自动识别动画只存在于 Mock 模式；实弹流程中真实验证码由操作者现场交互完成，验证码机制逆向分析结论见 findings（M2.x）。

## 启动

```bash
npm install
npm run dev        # 开发模式：http://localhost:5173（实弹需另起 npm start）
npm run build      # 产线构建（dist/）
npm start          # ★ 实弹/正式演示：单进程原生服务（静态 dist + 实弹代理）→ http://localhost:8787
npm run live       # = npm run build && npm start
```

### 实弹双路径结论（2026-09-12 实测）

| 档案 | 服务端结果 |
|---|---|
| 自动生成（本机指纹） | HTTP 480 · 4010 —— "设备绑定防线有效"叙事 |
| 📂 真实档案（授权门内一键「📂 加载真实档案」） | **HTTP 200 · code 2000 真实业务数据** —— "后台查无异常"叙事 |

真实档案来自 mitmproxy 抓包（`docs/real-headermap.json`，含活凭据 JWT 有效期至 10-12，勿外传）。
purchaseInfoV2 请求规范与拒绝矩阵详见 `work/.../findings/purchase-info-v2-assessment.md`。
页面 JS 崩溃会直接渲染错误栈在页面上（全局错误钩子），现场排障无需 DevTools。

### 实弹链路架构（为什么必须走原生服务）

浏览器直发会被生产边缘（阿里 ESA）识别拒绝：实测 `Origin/Referer/Sec-Fetch-*/sec-ch-ua` + Chrome UA 等
浏览器指纹导致 480/4010（`{"code":4010,"message":"获取验证码失败"}`）。

解决：`server/live-server.mjs`（零依赖 Node 原生 https 客户端）——
- 浏览器只与本服务交互（`POST /api/live`），真实请求由服务端构造：**零浏览器指纹**、UA 按真实 App 形态、空值头剔除
- cookie jar 自动保存回传 `acw_tc/cdn_sec_tc`
- 服务端复刻硬门禁：高峰 06:00–06:15 拒发、预算 ≤300
- 证据落盘：`evidence/live-requests.jsonl`（脱敏）
- UI 抓包视图：请求日志显示 HTTP 状态/耗时/实际发送头/响应头

## 代码结构

```
src/
  App.jsx                  # 流程状态机 + 双模式路由（mock/live 协议适配器）
  styles.css               # 全局样式（深色安全主题）
  lib/
    signature.js           # ★ 真实还原的客户端签名算法 md5(deviceKey+mobile+timestamp)
                           #   与真实抓包样本 15/15 逐字节一致（含自校验）
    deviceKey.js           # 设备指纹"可复刻性"演示（模拟 native 派生）
    payLinks.js            # 支付链接拼接算法还原（🚫 仅拼接展示，禁止调用）
    mockApi.js             # 演示模式 mock 网络层 + 协议请求日志
    realApi.js             # 实弹模式：授权门禁 + 真实协议调用（止步支付）
  components/
    FlowHeader.jsx         # 步骤指示器 + AttackNotes（攻击者视角/开发者注意）
    PhoneLogin.jsx         # Step1 验证码登录（签名实时复刻演示）
    ProductSelect.jsx      # Step2 选购商品（实弹模式拉真实 purchaseInfoV2）
    CaptchaVerify.jsx      # Step3 提交订单 + 三类验证码（文字/点选图片/滑块）自动通过演示
    AddressForm.jsx        # Step4 填写地址
    PaymentSelect.jsx      # Step5 选择支付渠道
    PayLinkResult.jsx      # Step6 生成支付链接（拼接展示 + 红线声明）
    ModeGate.jsx           # 实弹模式授权门（三确认 + 档案粘贴 + 窗口校验）
    RequestLog.jsx         # 底部协议请求日志（每步协议形态实时展示）
docs/
  narration.md             # 演示口述稿（操作者专用，含每步原理与质询应对）
```

## 实弹模式前置条件（缺一不可）

1. 客户盖章授权文书 + 备案（runbook 见 `work/.../notes/next-phase-plan-purchase-info-v2.md`）
2. 测试账号 + 验证码接收手机
3. 测试设备 HeaderMap 档案（由授权取证 intercept hook 导出，JSON 粘贴进授权门）
4. 当前不在 06:00–06:15 维护窗口（页面自动校验）

## 演示后的留痕

- 协议请求日志：时间戳 / 接口 / 脱敏 body / 响应预览 / 预算消耗
- 报告主线：`work/.../notes/goals-and-progress.md` + `notes/steps-log.md`
