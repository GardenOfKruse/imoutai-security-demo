# purchaseInfoV2 接口安全评估 — 真实抓包还原（Phase 0/1 实证）

> Case: `20260907-222238-i-app-frida-dump-dex-android-apk` | 2026-09-12
> 目标：`https://h5.moutai519.com.cn/xhr/front/mall/item/purchaseInfoV2`
> 方法：mitmproxy 被动抓包（root 系统证书 + USB 反向代理，App 零注入）+ 独立客户端复现
> ⚠️ 本文含接口凭据的使用规则：活凭据仅存于 `demo-app/docs/real-headermap.json`（本地），报告与对外材料一律脱敏

---

## 1. 接口规范（从真实流量完整还原）

```
POST https://h5.moutai519.com.cn/xhr/front/mall/item/purchaseInfoV2
Content-Type: application/json
Cookie: MT-Token-Wap=<JWT，HS256，30 天有效>
x-csrf-token: （存在但为空）
X-Requested-With: XMLHttpRequest
MT-APP-Version: 1.9.12
User-Agent: Mozilla/5.0 (Linux; Android 12; M2010J19SC …) — H5 webview 内宿主 UA
Referer/Origin: https://h5.moutai519.com.cn

body: {"hot":true,"spuId":"IMTP1000313","jt":"anonymous"}
```

**核心发现：该接口没有 MT-R/MD5 类应用层签名**。鉴权与"签名"就是 Cookie 里的
`MT-Token-Wap`（JWT：`{"iss":"mt","userId":1203157454,"deviceId":"clips_fxku…","exp":+30d}`）。
JWT 内嵌 deviceId = App 端 `MT-Device-ID` = `clips_*` 设备标识（三处一致，设备绑定链闭环）。

## 2. 响应结构（真实业务数据）

```json
{"code":2000,"data":{"itemId":"IMTP1000313","purchaseInfoMap":{"1001017":{
  "purchaseInfo":{"skuId":"741","inventory":0,"forbiddenBuyDesc":"09:00投放",
  "limitCount":6,"startTimeList":[1789174800000,1789175100000,…每5分钟一批]}}}}}
```

- 投放节奏：09:00 起每 5 分钟一个批次（`startTimeList` 共 12 批）
- 限购：`limitCount:6`；当前 `inventory:0`（未到投放时间）

## 3. 拒绝路径矩阵（实测，demo 可复现）

| 场景 | 结果 | 说明 |
|---|---|---|
| 浏览器直发（带全部浏览器指纹） | HTTP 480 · code 4010 获取验证码失败 | ESA 边缘识别浏览器特征（Origin/Sec-Fetch/sec-ch-ua/Chrome UA） |
| Node 原生客户端 + 真实头（剥离 Origin 等） | HTTP 401 · code 4011 retrieve token failed | 业务网关 ianus-token-auth 因请求头不完整拒绝 |
| Node 原生客户端 + **完整真实头**（mitm 抓包原样） | **HTTP 200 · code 2000** | ✅ 与真实 App 请求无差异，**后台无法区分** |
| 任意客户端 + 空 body | HTTP 481 · code 4810 | 参数校验正常 |

（均在 `evidence/live-requests.jsonl` / 会话日志留痕，可复现）

## 4. 风控能力判定（该接口）

| 维度 | 判定 | 依据 |
|---|---|---|
| 应用层签名 | **无**（仅 JWT Cookie） | 请求头全量比对 |
| 设备绑定 | JWT 内嵌 deviceId（clips_） | JWT payload 解码 |
| 传输防护 | TLS + CDN（ESA），无固定 IP | 响应头 server/via |
| 环境检测 | 有代理检测（5s 内 /bangcle/bbprbdata/upload 上报） | 用户实测 + 抓包 |
| **但**：持有有效 JWT 的自动化请求**与正常用户无法区分** | | 200 复现实证 |

## 5. 给开发者的建议（按优先级）

1. H5 关键接口（purchaseInfoV2/下单）增加服务端校验的短时效行为签名（当前仅 Cookie）
2. JWT 收紧有效期（当前 30 天）+ 高危操作（下单）要求二次校验
3. 代理/证书环境检测信号**上报服务端聚合判定**（当前客户端检测后并不拦截已登录会话的查询）
4. `startTimeList` 全量下发 = 投放时刻表暴露 → 改为临近投放才下发或做模糊化
