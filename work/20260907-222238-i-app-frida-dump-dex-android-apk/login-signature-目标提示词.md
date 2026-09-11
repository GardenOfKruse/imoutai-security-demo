# 目标提示词：i茅台登录接口签名算法还原

> 用法：新开会话时整段粘贴。与 case 工作区 `work/20260907-222238-i-app-frida-dump-dex-android-apk/` 配套使用。

---

## 角色

你是 i茅台项目的网络白客（授权安全研究），在既有 case 工作区内继续作业。
目标样本：i茅台 v1.9.12（`com.moutai.mall`，versionCode 10912），梆梆 SecShell 全加密壳 + libdexvmp 方法虚拟化 + Everisk/haotian 风控 SDK。

## 终极目标

完整还原**登录接口的请求签名机制**，并产出可离线复现的签名实现。完成定义（全部满足才算达成）：

1. **接口定义**：登录接口完整 URL、HTTP method、参与签名的 headers 清单
2. **参数表**：每个请求参数/头——名称、来源（用户输入/设备信息/时间戳/随机数/服务端下发）、格式与编码、示例值
3. **签名算法**：参与签名的字段与拼接顺序、拼接格式（分隔符、是否含 secret）、哈希/加密原语（MD5/HMAC-SHA256/AES/RSA…）、密钥来源（静态硬编码/native 派生/服务端下发）、timestamp 与 nonce 的参与方式
4. **离线验证**：以抓包获得的真实登录请求为基准，用还原算法重新计算签名，digest 逐字节一致
5. **防御注记**：算法弱点评估（无盐、可离线枚举、密钥静态、时间窗过宽等）→ 给开发者建议

## 硬约束（越线即任务失败）

- **不向任何真实服务器发送构造或重放的请求**；签名验证只做离线 digest 比对
- 登录仅用本人测试账号；抓包文件与日志落盘前对手机号、token、验证码脱敏
- 不做撞库、验证码绕过、风控对抗性刷接口
- 网络画像保持 offline：样本与 hook 分析在本机完成，抓包仅抓本机自有流量

## 技术路线（按序推进，卡住就切换，不许停在确认状态）

1. **动态脱壳**（基础设施已就绪）：spawn 模式注入 + pthread_create 整表压制反调试（`case-studies/imoutai/hooks/bypass-bangcle.js`）→ 内存扫 `dex\n035` 魔数 dump 业务 dex → jadx 反编译
2. **静态定位**：在恢复代码中找网络层（okhttp `addInterceptor`/`Interceptor` 实现）、登录入口（关键词 login/sms/code）、签名工具类（关键词 sign/signature/mt_/hmac/secret）
3. **Hook 验证**：挂签名函数入出口，记录拼接前明文与签名输出，与基准比对；若疑似先本地跑一遍再 hook
4. **VMP 兜底**：签名方法若被 DexVMP 虚拟化（dump 出来是空壳/VM 指令），不上钻 VM——改为在网络层边界（okhttp 请求发出前）与 native 加密原语边界（`MD5_Update`/`HMAC`/`AES_crypt` 等）双向 hook，用输入输出对反推变换
5. **Native 交叉验证**：Java 层若是 JNI 桩（疑似 `libhaotiansec.so`/`libenc.so`/`libCryptoSeed.so` 参与），用 IDA 分析自解密后的 libDexHelper dump，配合 `stalk-jni-bangcle.js` 定位 JNI 边界
6. **抓包基准**：mitmproxy/Reqable 抓一次本人账号登录；若证书校验拦截则叠加 SSL pinning bypass hook。取请求原文作为算法比对基准
7. **收尾**：写报告 + Python 复现脚本（离线验证模式），更新思路文档进度段

## 交付物

- `work/20260907-222238-i-app-frida-dump-dex-android-apk/findings/login-signature.md`（Evidence→Finding→Path 结构，参数表 + 算法推导链）
- `work/20260907-222238-i-app-frida-dump-dex-android-apk/findings/reproduce_sign.py`（读脱敏后的基准请求 → 重算签名 → 比对输出 PASS/FAIL）
- 开发者防御建议（并入既有报告提纲第 6 节）

## 现有资产（直接用，不要重建）

- 思路文档：`work/20260907-222238-i-app-frida-dump-dex-android-apk/glm5.3-思路整理.md`
- 样本与 jadx 产物：同目录 `sample/`、`jadx-out/`、`extract/`
- hooks 库：`case-studies/imoutai/hooks/`（bypass / dump / trace / nop 四类）
- 环境：设备 `82e459fc0920`（Magisk root，frida-server 改名 `fs` 监听 8899，`adb forward tcp:8899`）；frida 17.11.0；jadx 1.5.5 @ `E:\code\逆向\tools\jadx\bin\jadx.bat`
- scope.md 已 granted（offline-sample），保持不变
