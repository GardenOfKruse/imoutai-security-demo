# i茅台 v1.9.12 逆向任务交接摘要

> 交接对象：GLM-5.3
> 交接日期：2026-09-08
> Case：`20260907-222238-i-app-frida-dump-dex-android-apk`
> 目标：继续恢复登录接口签名；当前仍处于动态加载边界阶段，不能宣称签名已还原。

## 结论先行

| 项目 | 当前结论 | 证据强度 |
|---|---|---|
| Frida 注入 | spawn 注入成功；Java/native hook 可安装并命中 | 已证实 |
| 加固绕过 | 仅完成 MIUI ForceDark 兼容性入口短路；SecShell/DexVMP/Everisk 整体未绕过 | 已证实为部分完成 |
| 异常检测 | Java `killProcess`/`System.exit` 可观测并部分阻断；native `raise/tgkill` 仍出现 | 整体未证实 |
| 业务 DEX | `InMemoryDexClassLoader`/`DexFile` hook 已装，但 `dex_captured=0`；运行时 app 类数量为 0 | 未完成 |
| native | 绝对路径 `dlopen(libDexHelper.so)` 成功；运行时映像和导出表已完整落盘 | 已证实 |
| 登录签名 | 尚未拿到接口、参数、算法、真实脱敏基准或逐字节 digest | 未开始到可验证阶段 |

## 已完成步骤

### 1. 基线与静态结构

- 样本：`sample/imoutai-1.9.12.apk`，包名 `com.moutai.mall`，versionCode `10912`。
- 静态结论：三层结构为梆梆 SecShell 全加密壳、`libdexvmp.so` 方法虚拟化、Everisk/haotian 风控组件。
- `jadx-out` 的业务包基本缺失；`classes.dex` 主要是壳代码，业务逻辑预期运行时解密加载。
- Manifest 确认 `IsoService`：`android:process=":rs"`、`android:isolatedProcess="true"`，但实验期间没有实际出现可注入的 `:rs` 进程。

### 2. Frida 与 Java/Dex 边界

已验证的 hook/行为：

- `pthread_create` 观察及壳模块线程入口替换；实验统计中 `pthread_suppressed=0`，没有证据表明壳线程被有效压制。
- `System.exit`、`Runtime.exit/halt`、`Process.killProcess` 观测/部分阻断。
- `Runtime.nativeLoad` 结果诊断：库名 `libDexHelper.so` 返回 `dlopen failed: library not found`；MIUI 缺失库 `libforcedarkimpl.so` 被精确短路。
- `MiuiForceDarkConfigManagerImpl.nativeInit/nativeSetConfig` 两个 overload 均安装并命中。
- `InMemoryDexClassLoader`、`DexFile.openInMemoryDexFiles/loadDex/openDexFile`、`DexClassLoader` hook 已安装；`openDexFile` 命中过 `org.apache.http.legacy.jar` 与 `base.apk`，不是业务 DEX。
- 多轮内存扫描只得到两个约 284 字节的伪阳性 DEX 魔数，未得到业务 DEX。

### 3. native 映像

mp19 是当前最可靠的 native 读取样本：

- 绝对路径 `dlopen` 成功，模块大小 `1495040` 字节。
- 9 个可读区间均以 `4096` 字节分块读取完成。
- 映像：`extract/native-memdump/mp19/libDexHelper-runtime-18231.so`
- 映像 SHA-256：`D4459996C37A14480B1DE1A8097B38F3CC1D186B0E478C2CE9E4143A39D15544`
- 原始库：`extract/lib/arm64-v8a/libDexHelper.so`
- 原始库 SHA-256：`3854EDA3D11B1C308EB0FF32594BF4E418EFE950DF6175909E33E15F5E405CAA`
- 导出表：`extract/native-memdump/mp19/libDexHelper-exports-18231.txt`，目前仅见 `JNI_OnLoad`。
- 映像前 64 字节与原始 ELF 头一致；运行时文件包含加载后的段/BSS 范围，不能简单按文件哈希判断“脱壳成功”。

### 4. 启动入口与类视图

mp20 使用 Manifest 启动入口 `com.moutai.mall/.module.splash.SplashActivity`，系统确认该 Activity resumed；但采集到的 `com.moutai.mall` 运行时类数量为 `0`。这说明当前进程在真实业务类加载前就进入异常/自杀链，或业务逻辑位于未覆盖的隔离/自定义加载边界。

## 失败与未完成尝试

| 编号 | 尝试 | 结果与当前判断 |
|---|---|---|
| F1 | 自研内存 DEX 魔数扫描 | 只得到 284B 假阳性；旧 RPC/API 与范围过滤问题已修正，但业务 DEX 仍未出现 |
| F2 | 旧版 `bypass-bangcle.js` | `indexOf` 运算符优先级错误，实际没有压制线程 |
| F3 | `frida-dexdump -H -f` | spawn 后挂起，未产生有效产物，不再作为主路径 |
| F4 | 无注入 root `/proc/PID/mem` 快照 | 进程在风控倒计时自杀，快照不完整，carve 无 DEX |
| F5 | 干净启动后事后读内存 | 仍自杀，说明触发条件不是单纯 Frida 注入 |
| F6 | 只处理 `libforcedarkimpl.so` | 后续 `nativeInit` 仍抛 `UnsatisfiedLinkError` |
| F7 | ForceDark 两个 native overload + Java 退出点 | 兼容性错误消失，但无业务 DEX，整体风控未绕过 |
| F8 | 初版 spawn-gating/进程枚举 driver | 同步阻塞；改用 ADB PID 轮询 |
| F9 | Node 平台 esbuild bundle | agent 无 Node `require("buffer")`；改为 neutral bundle + buffer shim |
| F10 | 旧 PID attach 读取 native | attach 超时，改为 spawn 会话 |
| F11 | spawn 内按库名读取 `libDexHelper` | `Runtime.nativeLoad` 找不到库名，模块不可枚举 |
| F12 | 进程内 DEX 扫描与 native 分块读取并行 | 扫描占用 Frida JS 线程，回读不自然结束；增加 `setLiveScan(false)` |
| F13 | 仅针对 `libDexHelper.so` 的绝对路径 native 重定向 + 手动 JNI_OnLoad | 代码已写入但发生在 mp20 之后，尚未重新编译/验证；不得当作成功 |

## 可复现命令

以下命令中的 `<AUTHORIZED_SERIAL>` 使用授权设备序列号替换；命令只做本地注入、读取和落盘，不发起业务请求。

### 重新编译与检查

```powershell
& 'E:\Programs\Python\python.exe' -m py_compile `
  'E:\code\逆向\android\case-studies\imoutai\hooks\dump-native-driver.py'
& 'E:\packages\nvm4w\nodejs\node.exe' `
  'E:\code\逆向\android\case-studies\imoutai\hooks\_build\node_modules\esbuild\bin\esbuild' `
  'E:\code\逆向\android\case-studies\imoutai\hooks\_build\entry.js' `
  --bundle --platform=neutral --format=iife `
  '--alias:frida-java-bridge=E:\code\逆向\android\case-studies\imoutai\hooks\_build\node_modules\frida-java-bridge\index.js' `
  '--alias:buffer=E:\code\逆向\android\case-studies\imoutai\hooks\_build\buffer-shim.js' `
  --outfile='E:\code\逆向\android\case-studies\imoutai\hooks\dump-dex-hook-compiled.js'
```

### 已验证的 native-only 导出（mp19 逻辑）

```powershell
& 'E:\Android\platform-tools\adb.exe' -s <AUTHORIZED_SERIAL> shell am force-stop com.moutai.mall
& 'E:\Programs\Python\python.exe' -u `
  'E:\code\逆向\android\case-studies\imoutai\hooks\dump-native-driver.py' `
  --js 'E:\code\逆向\android\case-studies\imoutai\hooks\dump-dex-hook-compiled.js' `
  --out 'E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\native-memdump\mp19' `
  --wait 1 --chunk 4096 --no-activity
```

### 已验证的启动入口/类视图（mp20 逻辑）

```powershell
& 'E:\Programs\Python\python.exe' -u `
  'E:\code\逆向\android\case-studies\imoutai\hooks\dump-native-driver.py' `
  --js 'E:\code\逆向\android\case-studies\imoutai\hooks\dump-dex-hook-compiled.js' `
  --out 'E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\native-memdump\mp20' `
  --wait 6 --chunk 4096 --list-classes `
  --activity 'com.moutai.mall/.module.splash.SplashActivity'
```

## 当前代码状态

- 主 hook：`case-studies/imoutai/hooks/dump-dex-hook.js`
- 已编译 bundle：`case-studies/imoutai/hooks/dump-dex-hook-compiled.js`
- native 驱动：`case-studies/imoutai/hooks/dump-native-driver.py`
- 最近未验证改动：`setLiveScan`、`listLoadedClasses`、`setNativeRedirect`、`load_native_with_jni`。
- 注意：mp19/mp20 使用的是重定向代码写入前的已验证 bundle；GLM 若验证最近改动，必须先重新编译，并保留 mp19/mp20 产物作为回退基线。

## 终极交付物核对

以下均未达到完成标准：

1. 登录 URL、HTTP method、签名 headers：无证据。
2. 登录参数来源、编码、示例：无脱敏登录请求基准。
3. 签名字段顺序、密钥来源、算法原语：未定位。
4. 离线逐字节 digest：无算法与基准，禁止生成伪 PASS。
5. `findings/login-signature.md`、`findings/reproduce_sign.py`：尚未创建为有效成果。

继续推进时，优先顺序是：

1. 先编译并单独验证 `setNativeRedirect`，记录 `JNI_OnLoad` 返回值和异常；失败时回退 mp19/mp20 bundle。
2. 在 `JNI_OnLoad`、`RegisterNatives`、`android_dlopen_ext`、`ClassLoader`/ART 类链接边界增加脱敏观测，重点覆盖 `:rs` 服务进程的真实出现时机。
3. 只有捕获到业务类或本机自有、已脱敏的登录请求后，才进入网络边界和签名算法推导。
4. 签名验证严格离线；不要构造或重放任何真实服务请求。

## 相关文件

- 主步骤日志：`notes/steps-log.md`
- 思路文档：`glm5.3-思路整理.md`
- 授权范围：`scope.md`（保持不变）
- native 证据：`extract/native-memdump/mp19/`
