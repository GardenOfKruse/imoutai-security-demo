# i茅台 v1.9.12 梆梆加固分析 — 思路整理（GLM-5.3）

> Case: `20260907-222238-i-app-frida-dump-dex-android-apk`
> 更新: 2026-09-07 22:40 | 状态: 静态分析完成，动态绕过进行中
> 角色: 网络白客（授权本地样本研究 → 输出防御建议）

---

## 1. 任务与授权边界

| 项 | 内容 |
|---|---|
| 目标 | i茅台 最新版 v1.9.12（versionCode 10912）梆梆加固分析 |
| 任务 | 用 Frida 等技术绕过壳保护，获取运行时动态文件（解密 dex / 自解密 so），据此给开发者防御建议 |
| 授权 | scope.md `auth.status=granted`（offline-sample preset）：自有设备 + 官方渠道安装副本，仅本机分析，不对任何外部服务发起请求 |
| Out of scope | DoS、真实用户钓鱼、无限制数据外传、绕过支付/业务风控进行越权操作 |

## 2. 目标与环境

**样本**: `work/20260907-222238-i-app-frida-dump-dex-android-apk/sample/imoutai-1.9.12.apk`（64.5 MB，从设备 pull）

**设备**: 小米系 `82e459fc0920`，arm64-v8a，Android 12，Magisk root（Kitsune Mask 变体，自带 root 隐藏）

**工具链**:
- frida 17.11.0（PC 与手机端一致）；frida-server **改名 `fs`** 放在 `/data/local/tmp/fs`，监听 **8899** 非默认端口（避开 27042 扫描），PC 侧 `adb forward tcp:8899` + `frida-ps -H 127.0.0.1:8899`
- jadx 1.5.5 @ `E:\code\逆向\tools\jadx\bin\jadx.bat`（用户约定：新工具装 `E:\code\逆向\tools\<名>`）
- adb @ `E:\Android\platform-tools\adb.exe`

## 3. 静态分析结论：三层加固结构

对 APK 解包 + jadx 反编译 + so 字符串侦察，确认 2026-08-30 构建版使用了梆梆"全加密壳 + DexVMP + 风控 SDK"组合：

### 第 1 层：SecShell 全加密壳（主体）
- **证据**：`AndroidManifest.xml` 的 `android:name="com.secneo.apkwrapper.AW"`（梆梆 StubApplication）；jadx 中 `com.moutai` 包下仅剩 `IsoService.java`（壳的隔离进程 Messenger 服务）和 `R.java`
- `classes.dex` 23.5 MB 但全部是壳代码 + 加密业务数据段；**业务 dex 运行时才由 `libDexHelper.so` 解密、InMemoryDexClassLoader 加载** → 静态拿不到任何业务逻辑，必须动态 dump
- 完整性自校验：`assets/meta-data/manifest.mf + rsa.pub + rsa.sig`

### 第 2 层：DexVMP 方法虚拟化
- **证据**：`lib/arm64-v8a/libdexvmp.so`（520 KB）
- 预期：核心方法被抽成 VM 字节码，**即使 dump 出 dex，VMP 方法也拿不回原始 Java**——这是 dump 攻击的天然上界，报告里要如实说明

### 第 3 层：风控/环境检测 SDK 群
| 组件 | 作用 |
|---|---|
| `libbangcle_risk.so` + `assets/RiskStub.dex` + `libRiskStub.so` | 梆梆 Everisk 风控（manifest 里有 provider `com.bangcle.everisk.core.c`；so 内有明文 `KernalSU` 串） |
| `libemulator_check.so` | 网易 deviceid 模拟器检测（JNI: `com_netease_deviceid_jni_EmulatorDetectUtil_detect`） |
| `libhaotiansec.so`（1.25 MB）+ `libhaotian.so` | 阿里安全 haotian |
| `libalive_detected.so`、`libproperty_get.so`、`libtiny_magic.so` | 存活/属性/魔数辅助检测 |
| 壳本体 `libDexHelper.so`（1.23 MB） | 反调试主力：字符串全加密，仅剩 `/proc/self/cmdline`、`/proc/self/maps` 明文（TracerPid / frida 模块扫描） |

## 4. 动态绕过思路（主线）

### Phase 0 隐匿基线 ✅ 已完成
改名 + 非默认端口 + spawn 模式注入（`-f`，避免 attach 的 ptrace 窗口）+ Kitsune Mask 隐藏 root。

### Phase 1 反反调试压制（spawn 时注入，赶在壳初始化前）
1. **hook `android_dlopen_ext`**：感知 `libDexHelper.so` 加载时机（`bypass-bangcle.js` 已实现）
2. **整表 replace `pthread_create`**：来自 libDexHelper 的线程一律不实际创建、直接返回 0 → 压掉反调试/看门狗线程（上轮已验证思路，见 `case-studies/imoutai/hooks/bypass-bangcle.js`）
   - ⚠️ 风险：无差别压制可能连业务线程一起杀 → 只过滤 `module.name` 命中壳 so 的
   - ⚠️ 若壳校验 pthread_create 返回值/自检线程存活，改用"创建后立即改线程入口为死循环"的软压制
3. **信号陷阱**：梆梆用 SIGTRAP/SIGSEGV 陷阱反调试（`trace-signal-bangcle.js` 定位点），必要时 hook `sigaction`/`pthread_kill` 过滤来自壳的信号注册
4. **兜底**：若仍被检测（典型表现：启动即退、JNI 调用跳空指针），走 `stalk-jni-bangcle.js` → `nop-jni-check.js` 流程：Stalker 定位 JUMP-TO-NULL 检测点 → 按基址+偏移 NOP

### Phase 2 dump 动态文件（三路并进）
| 途径 | 对象 | 方法 | 状态 |
|---|---|---|---|
| A | 解密后的业务 dex | 内存扫 `dex\n035` 魔数（frida-dexdump 思路），枚举 `rw-`/`r--` ranges，dump + 修 header（checksum/signature/map_off），多 dex 全收 | 待做 |
| B | 自解密后的 `libDexHelper.so` | `dump-libdexhelper.js`（rpc `read_chunk` 二进制通道）+ PC 端 `dump-receiver.py`，落盘后给 IDA | 上轮已产出模板（`libDexHelper.so_0x73fb65c000_0x16d000.so`、`libDexHelper_fixed.so`），v1.9.12 需重跑 |
| C | 运行时类视图 | `Java.enumerateLoadedClasses` + 各 ClassLoader 枚举，交叉验证 A 的完整性，顺带定位壳检测类 | 待做 |

**dump 时机**：.dex 在 `AW.attachBaseContext` 解密完成、真实 Application 初始化后（日志观察 `realApplication.onCreate`）；.so 在 dlopen onLeave + 若干秒延迟（等自解密完成）。

### Phase 3 验证与产出
- dump 出的 dex 过一遍 jadx，确认业务类（`com.moutai.mall.*`）恢复率；VMP 方法缺失情况单独记录
- 按 `Evidence→Finding→Path` 写报告（`work/<case>/` 下 append evidence），防御建议单独成文

## 5. 复用上轮资产（`case-studies/imoutai/hooks/`）

- `bypass-bangcle.js` — pthread_create 整表压制（Phase 1 主体）
- `dump-libdexhelper.js` + `dump-receiver.py` — so dump 管道（Phase 2-B）
- `stalk-jni-bangcle.js` / `trace-{signal,clone,jni-calls,jni-load}-bangcle.js` — 检测点定位（Phase 1 兜底输入）
- `nop-jni-check.js` — 按偏移 NOP（填 PATCHES 后启用）
- 修正版 so：`libDexHelper_fixed.so`（上轮产物，用于对照新版本差异）

## 6. 防御建议框架（最终报告提纲）

1. **全加密壳的物理上界**：一次性解密进内存 = dump 即得明文；壳只提高成本不改变结果 → 关键资产防护应下沉到服务端（接口签名、设备指纹、风控信号聚合），客户端做"对抗成本递增"而非"绝对保密"
2. **检测线程可被整表 replace**：pthread_create 级压制能瘫痪全部看门狗 → 建议：检测逻辑内联进主流程 + 对 `pthread_create`/`sigaction` 的 GOT/PLT 与前几条指令做完整性校验 + 多信号交叉验证（单点信号不触发）
3. **DexVMP 方向正确**：dump 只能拿壳外代码，VMP 方法不可还原 → 建议扩大覆盖面至核心业务方法（下单、token 生成）
4. **完整性自校验信任根在客户端**：`rsa.sig` 校验可被 hook 短路 → 运行时完整性应与服务端二次校验联动
5. **环境检测应聚合并置服务端**：模拟器/root/frida 单点信号客户端可逐个绕过；客户端只上报原始信号，判定放服务端
