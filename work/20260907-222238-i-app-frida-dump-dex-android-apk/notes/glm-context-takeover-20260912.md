# GLM 上下文接管快照（2026-09-12）

## 接管结论

原始 GLM session 文件
`E:\code\逆向\android\sess\_be5fbb9d-f4f2-45e7-9d77-d17adf384a7a.zcode-session`
当前不存在；但 CLI 日志中确认该 session ID 为
`sess_be5fbb9d-f4f2-45e7-9d77-d17adf384a7a`。因此本文件是依据 CLI 事件日志和项目落盘证据恢复的接管快照，不是可原样恢复的对话 session。

CLI 日志：`C:\Users\76327\.zcode\cli\log\zcode-2026-09-12.jsonl`

- 日志规模：约 2.8MB、4100 行。
- 记录到 34 轮，工具调用约 237 次。
- 主要模型：GLM-5.3-Flash，后段少量 GLM-5.3。
- 22 轮完成，12 轮取消或失败。
- 最后一轮失败原因：配额超限；此前还出现过模型并发限制。

## GLM 已完成的主要工作

### 1. 登录链路

- 已从反射注解和运行时构造器定位登录、验证码和请求模型。
- 短信验证码请求的 MD5 公式已经逐字节验证，复现脚本为 `findings/reproduce_sign.py`。
- 登录接口定义、请求模型和部分 Header 线索已落盘。
- `deviceKey`、native CryptoUtil 和 RSA 私钥相关运行时证据已存在于本地 findings/证据中；本快照不复制任何真实值。
- Header 名值映射、私钥用途和完整登录签名链仍需按证据等级区分，不能把局部观测扩大为完整协议还原。

### 2. 运行时与 native

- 早期 Frida 分支曾因壳的 loader、自检、异常处理和动态内存机制失败。
- 后续最终 Hook 版本整合了反射注解、构造器、MessageDigest、CryptoUtil、头常量和拦截器观察能力。
- 真机冒烟记录为 PASS：验证码采样、设备派生头值和 CryptoUtil probe 均有成功记录。
- 早期“反制升级/挂钩即死”的表述已被后续对照实验修正：主要现象归入注入窗口、Everisk 弹窗和设备状态影响，不能直接作为检测绕过结论。

### 3. purchaseInfoV2 与真实流量

- 已切换到 mitmproxy + root CA + USB 反向代理的零注入路线。
- 已捕获真实 App 流量并完整还原 `purchaseInfoV2` 请求/响应。
- 已确认该接口的关键鉴权、设备绑定和业务响应结构；真实值只保存在本地采集文件，不复制到本快照。
- 浏览器指纹路径曾得到 480/4010；使用真实设备档案的独立客户端和 demo 管线得到 HTTP 200 / 业务成功响应。
- 无证据表明存在 TLS 指纹拦截；自动生成档案被拒绝与设备绑定/真实档案差异有关。
- Bangcle 遥测端点已在真实流量中出现，属于风控评估证据。

### 4. Demo 与文档

- `case-studies/imoutai/demo-app/` 已完成 React/Vite 双模式演示应用。
- 已包含 Mock/Live 两种模式、登录、验证码演示、订单流程、支付链接静态拼接展示和协议日志视图。
- 已加入原生 Node 代理服务、窗口/高峰/预算门禁、token 缓存和退出登录。
- 已生成 OpenAPI 文档，覆盖接口清单和请求模型 schema。
- 已加入纯净版流程和开发者讲解版流程。
- 相关设计、讲解和评估文档已经落盘；支付仍只允许静态拼接展示，禁止真实支付调用。

## 当前客观状态

| 事项 | 状态 |
|---|---|
| Frida 延迟附加 | 已有历史成功证据 |
| 完整业务 DEX 导出 | 未完成/未证实 |
| 加固检测绕过 | 未证明 |
| 异常检测绕过 | 未证明 |
| 短信验证码 MD5 | 已逐字节验证 |
| purchaseInfoV2 真实复现 | 已有 HTTP 200 实证 |
| M1.6 真实 HeaderMap | 已通过零注入真实抓包路线取得本地证据 |
| 登录完整签名协议 | 部分完成，仍有归因缺口 |
| Demo 应用 | 已完成主要交付 |
| 原始 `.zcode-session` | 缺失，不能原样恢复 |

## 重要本地证据路径

- `findings/login-signature.md`
- `findings/reproduce_sign.py`
- `findings/purchase-info-v2-assessment.md`
- `notes/goals-and-progress.md`
- `notes/m16-headermap-capture-procedure.md`
- `notes/steps-log.md`
- `evidence/mitm-flows-20260912.jsonl`（本地敏感采集，不上传）
- `demo-app/docs/real-headermap.json`（本地敏感档案，不上传）
- `hooks/FINAL-HOOK-README.md`

## GitHub 同步状态

项目根目录为 `E:\code\逆向\android`，远端为
`https://github.com/GardenOfKruse/imoutai-security-demo.git`。

本快照生成时，已同步代码提交；原始流量、真实 Header 和 raw 登录响应仍按项目忽略规则保留在本地，不作为接管文档内容复制。

## 接管边界

后续工作应以本快照、`scope.md`、`goals-and-progress.md` 和 Evidence 文件为准。GLM 日志只用于恢复执行轨迹，不作为比 Evidence 更高等级的结论来源。

