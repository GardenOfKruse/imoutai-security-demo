# M1.7 订单三段证据采集手册

> 目的：在授权测试设备的同一订单窗口，观察 `compose/v2 → CaptchaWebView → submit/v2` 的真实调用顺序和字段形状。
> 约束：只读观察；不修改请求/返回值，不自动点击或绕过验证码，不调用支付接口；events.log 不写 Header、Cookie、token 或 JSON 值。

## 1. 启动观测

使用全新输出目录，避免与 DEX/反射实验混用：

```bash
cd E:/code/逆向/android/case-studies/imoutai/hooks
python -u obs-driver.py --js dump-dex-hook-order-evidence.js \
  --out <case>/extract/order-evidence-<time> --wait 240 --attach-running
```

确认 `events.log` 出现 `order-evidence` 的 `installed` 事件后，再由操作者本人按正常 App 流程操作。若应用重启，停止本次会话并为新 PID 使用新目录，不复用旧日志。

## 2. 最小操作顺序

1. 测试账号人工完成登录；
2. 进入购买页，选择授权测试商品；
3. 点击进入订单确认，等待原生验证码出现；
4. 操作者本人按正常方式完成验证码，观察页面是否刷新/重试；
5. 完成地址确认，等待提交结果；
6. 到达支付页立即停止，绝不调用 `order/pay`。

Hook 输出的安全摘要包括：

- `http-request/http-response`: `compose`、`captcha-network`、`submit` 的阶段、路径、状态码、请求 JSON 字段形状和响应顶层形状；
- `captcha-sdk-inventory`: 当前进程已加载的网易验证码类名及方法签名；
- `captcha-webview-call`: `CaptchaWebView` 的 WebView 调用名和参数长度，不含 URL/脚本内容。

## 3. 判定规则

- 只有同一目录中按 `compose → captcha → submit` 顺序出现，并且 compose 响应存在交易字段、submit 响应出现订单字段，才可进入下一步离线适配；
- 字段形状摘要只能证明生命周期和模型边界，不能直接成为 Live 请求 body；真实值必须留在授权本地取证环境，不能提交 Git；
- 若只有 `CaptchaWebView` 或只有 `captcha-network`，仍标记验证码协议未验证；若 submit 未出现，不能宣称订单创建成功。

## 4. 产物与后处理

- Hook 源码：`case-studies/imoutai/hooks/_build/entry-order-evidence.js`；
- 已编译 bundle：`case-studies/imoutai/hooks/dump-dex-hook-order-evidence.js`；
- 原始事件：只保存在本地 `<case>/extract/order-evidence-<time>/events.log`，不提交；
- 将摘要字段交给 `demo-app/npm run check:capture -- <authorized-capture.json>` 做结构检查。该命令不验证 token，也不生成订单请求。
