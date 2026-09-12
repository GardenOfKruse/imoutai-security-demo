# i茅台 App 安全培训演示（Vite + React）

> 面向 APP 开发者的安全培训交互演示：Mock 模式完整演示「短信登录 → 选购 → 提交订单（本地验证码 fixture 状态机）→ 填地址 → 选支付 → 生成支付链接」；Live 模式已验证真实验证码请求、登录结果解析与 H5 商品信息，订单写接口在真实抓包完成前止步。
> **⚠️ 红线**：支付链接只做拼接展示，禁止真实调用；实弹模式仅在维护窗口之外的低频 runbook 执行。

## 两种模式

| 模式 | 网络行为 | 用途 |
|---|---|---|
| 🟢 演示模式（Mock） | 零生产交互，全部本地模拟 | 日常培训演示，随时可放 |
| 🧪 离线算法研究 | 零网络；生成可重复的 synthetic fixture | 无真机时研究已确认公式，并明确设备 ID 算法边界 |
| 🔴 实弹模式（Live） | **已验证部分走真实环境**：真实验证码/登录与 H5 商品信息请求；订单 compose/submit 未完成真实抓包前不发送，支付永久止步 | 仅限授权 runbook F 组用例的低频执行 |

实弹模式有四重硬门禁（`src/lib/realApi.js`）：授权三确认 → 时段校验（除 **06:00–06:15** 客户维护窗口外均可执行）→ **授权测试设备真实取证 HeaderMap** → 单进程低频与请求预算 ≤300。默认生成内容和离线研究 fixture 都不能解锁 Live，任一不满足即拒绝发请求。

**跨域（CORS）说明**：浏览器直连生产域名会被 CORS 拦截。本 demo 通过 vite 本地代理解决（`/mt-app` → `app.moutai519.com.cn`、`/mt-h5` → `h5.moutai519.com.cn`，dev 与 preview 均已配置），浏览器视角同源。教学点：CORS 只是浏览器内防线，真实攻击者用自定义客户端根本不受它约束——别把 CORS 当安全方案。

**证据边界（2026-09-12）**：短信验证码签名 `MD5(deviceKey + mobile + timestamp)` 已运行态 15/15 验证；native `deviceKey` 来源、`clips_*`/`MT-Device-ID` 派生算法和 `MT-R` 仍未验证。离线研究台只生成稳定的 synthetic 值用于教学，不能声称符合真机，也不代表 OCR、目标检测、轨迹仿真或原生算法还原。

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

真实档案来自 mitmproxy 抓包，运行时只允许使用本地未跟踪的 `public/real-headermap.local.json`；仓库中的 `public/real-headermap.json` 仅是脱敏结构占位文件，不含凭据。
purchaseInfoV2 请求规范与拒绝矩阵详见 `work/.../findings/purchase-info-v2-assessment.md`。
页面 JS 崩溃会直接渲染错误栈在页面上（全局错误钩子），现场排障无需 DevTools。

### 实弹链路架构（为什么必须走原生服务）

浏览器直发会被生产边缘（阿里 ESA）识别拒绝：实测 `Origin/Referer/Sec-Fetch-*/sec-ch-ua` + Chrome UA 等
浏览器指纹导致 480/4010（`{"code":4010,"message":"获取验证码失败"}`）。

解决：`server/live-server.mjs`（零依赖 Node 原生 https 客户端）——
- 浏览器只与本服务交互（`POST /api/live`），真实请求由服务端构造：**零浏览器指纹**、UA 按真实 App 形态、空值头剔除
- 主机按模块分流：验证码/登录及原生 App 订单接口 → `app.moutai519.com.cn`；嵌入 H5 的 `purchaseInfoV2` → `h5.moutai519.com.cn`
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
    deviceKey.js           # 浏览器指纹演示（明确为 synthetic，非 native 算法）
    offlineIdentity.js      # 无真机算法研究 fixture + 证据/未验证边界
    payLinks.js            # 支付链接拼接算法还原（🚫 仅拼接展示，禁止调用）
    mockApi.js             # 演示模式 mock 网络层 + 协议请求日志
    realApi.js             # 实弹模式：授权门禁 + 真实协议调用（止步支付）
  components/
    FlowHeader.jsx         # 步骤指示器 + AttackNotes（攻击者视角/开发者注意）
    PhoneLogin.jsx         # Step1 验证码登录（签名实时复刻演示）
    ProductSelect.jsx      # Step2 选购商品（实弹模式拉真实 purchaseInfoV2）
    CaptchaVerify.jsx      # Step3 提交订单 + 三类验证码本地 fixture 状态机
    AddressForm.jsx        # Step4 填写地址
    PaymentSelect.jsx      # Step5 选择支付渠道
    PayLinkResult.jsx      # Step6 生成支付链接（拼接展示 + 红线声明）
    ModeGate.jsx           # 实弹模式授权门（三确认 + 档案粘贴 + 窗口校验）
    OfflineIdentityLab.jsx  # 无真机研究台（不读取 real-headermap.json）
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
