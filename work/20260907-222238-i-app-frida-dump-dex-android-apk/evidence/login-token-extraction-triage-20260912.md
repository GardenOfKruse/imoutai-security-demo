# 登录响应与 Token 提取链路排查（2026-09-12）

## 结论

昨晚 GLM 的额度错误已在原始 CLI 日志中确认：18:33 左右出现 `exceed quota limit`，18:36 左右出现并发限制和再次额度不足。额度耗尽解释了为什么没有继续验证，但不是当前登录态失败的唯一原因。

现有本地证据显示：验证码/登录请求至少走到了 HTTP 200；登录响应在本地服务入口被当作 UTF-8 文本处理，`json` 解析结果为空，Token 没有形成可复用的登录态。当前不能把“HTTP 200”称为“登录完成”。

## Evidence → Finding → Path

### E1：落盘登录响应

- `demo-app/docs/login-resp-raw.json` 存在，响应状态为 200；响应正文长度为 464 个字符，`json=null`，`setCookie` 数量为 0。
- 正文经过再次 UTF-8 编码后保留 `0x1f`，并出现替换字符，符合 gzip 二进制被错误当作字符串保存的特征；正文不能直接 `JSON.parse`。
- 文件包含请求头和 Cookie 字段，仍按本地敏感证据处理；本报告不复制其值。

### E2：服务端响应处理

- `case-studies/imoutai/demo-app/server/live-server.mjs:108-114` 允许请求头声明 gzip，并把响应 Buffer 直接 `toString('utf8')`。
- `case-studies/imoutai/demo-app/server/live-server.mjs:145-146` 只对已经转换后的字符串调用 `JSON.parse`；没有根据 `content-encoding` 做 `gunzip`/`inflate`。
- 结果：压缩响应在 token 解析前已经丢失原始字节，前端拿到的是不可解析文本。

### E3：独立 Hook 脚本

- `case-studies/imoutai/hooks/login_and_capture.js:3,15-16` 确实引入 `zlib` 并尝试 `gunzipSync`，说明“解压”方向曾被考虑。
- 但 `case-studies/imoutai/hooks/login_and_capture.js:17-20` 只保存正文前缀和 Cookie，没有 `JSON.parse`、Token 字段定位、登录态写入或后续请求验证。
- 该脚本包含真实登录请求构造；本次排查没有执行它。

### E4：前端状态链路

- `case-studies/imoutai/demo-app/src/App.jsx:54-60` 只尝试少数固定路径读取 token；找不到 token 时返回 `LIVE_HTTP_200`，找到时返回的是 `LIVE_` 加前 24 个字符的展示串，而不是原 token。
- `case-studies/imoutai/demo-app/src/components/PhoneLogin.jsx:28-33` 以 HTTP 200 作为 `authed` 条件，即使 token 为空也会继续登录流程。
- `case-studies/imoutai/demo-app/src/App.jsx:180-186` 将上述展示串或 `LIVE_HTTP_200` 写入 `localStorage`；后续 `startLive` 会把缓存值作为 `MT-Token` 使用，导致重载后的登录态必然不可用。

## 当前判定

1. **GLM 额度问题：已确认。** 原始 CLI 日志中有 3 次额度/并发失败记录，最后一次发生在 2026-09-11 18:36:34 左右。
2. **验证码/登录传输层：有 HTTP 200 证据。** 但这不足以证明业务登录成功。
3. **gzip 解压：在当前 `live-server` 链路未完成。** 响应在 Buffer→UTF-8 转换处已经被破坏。
4. **Token 提取：未完成。** 当前代码既没有可靠的服务端解压解析，也没有可靠的前端登录态判定和持久化。
5. **真实 token 字段路径：未确认。** 现有落盘文件正文已损坏，不能从中安全恢复字段名；没有重发请求就不能进一步确认上游 JSON schema。
6. **本次动作：仅本地静态检查和脱敏结构检查；未重发验证码、未重试登录、未访问生产接口、未修改业务代码。**

## 相关原始证据

- GLM CLI 日志：`C:\Users\76327\.zcode\cli\log\zcode-2026-09-12.jsonl`；SHA-256：`F181CB42796A08417ACC7D41A28E19C28CAB749ED9A7499BB34ACF4EDED68426`。
- 用户提供的 `.zcode-session` 路径当前未找到；以上结论来自 CLI JSONL 日志和项目内已落盘文件。
