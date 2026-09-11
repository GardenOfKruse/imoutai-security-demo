# ★ 最终版 Hook — 总说明（唯一现行版本）

> **现行版本就三个文件，其余 hooks 目录里的一切 JS 均为历史版本，不再使用。**
> 定稿：2026-09-12 深夜。整合自今晚全部验证成功的能力，每项能力对应可查证据。

---

## 1. 现行文件（就这三个 + 两个驱动）

| 文件 | 角色 |
|---|---|
| `hooks/dump-dex-hook-final.js` | ★ 最终 hook 产物（esbuild 打包，`node --check` 通过） |
| `hooks/_build/entry-final.js` | 源码（改这里，然后重新 esbuild） |
| `hooks/watch_capture.py` | ★ 守护：跟随 App 重启自动重挂 hook（单会话 600s） |
| `hooks/obs-driver.py` | 通用驱动（attach-running / attach-existing / 日志落盘） |
| `hooks/annot5-driver.py` | 采样驱动（自动调 sampleStaticHeaders + sampleVcode + probeCrypto） |

历史版本（annot2~8、dump-dex-hook.js 主线、mpXX 系列等）**仅作证据留存**，其已验证能力全部已并入 final-hook，无需回看。

## 2. 这个最终版能拿到什么（全部真实战果）

| # | 能力 | 怎么触发 | 拿到过什么（证据） |
|---|---|---|---|
| R1 | 纯反射注解 dump | attach 后自动 | 220 类方法/注解/字段，105 接口、42 模型（S5-29，0 失败） |
| R2 | 请求模型构造器捕获 | 自动（构造即报） | GetVerifyCodeRequest{md5,mobile,timestamp} 三元组（S6-1） |
| R3 | MessageDigest 输入捕获 | sampleVcode 窗口内 | **md5 拼接串现形 → 公式破解 15/15**（S6-1 ★） |
| R4 | CryptoUtil 全方法观测 | sampleVcode 窗口内 | 调用链归因 CryptoUtil.l→o→MD5（S6-1） |
| R5 | 头部常量读取 | RPC sampleStaticHeaders | clips_* 三 token + 设备串（S5-30） |
| R6 | CryptoUtil 字段/方法探测 | RPC probeCrypto | RSA 私钥可导出（findings F4） |
| R7 | 拦截器头观测 | RPC enableIntercept（默认关） | M1.6 抓真实 HeaderMap 用 |

**签名公式（已破解）**：`GetVerifyCodeRequest.md5 = MD5(deviceKey + mobile + timestamp)`，
deviceKey 设备绑定 32-hex。复现脚本：`work/.../findings/reproduce_sign.py`。

## 3. 标准操作（两条命令 + 一个 RPC）

```bash
# 1) 前置（设备插线后）
adb -s 82e459fc0920 shell "su -c 'nohup /data/local/tmp/fs -l 0.0.0.0:8899 >/dev/null 2>&1 &'"
adb -s 82e459fc0920 forward tcp:8899 tcp:8899
adb -s 82e459fc0920 shell "am force-stop com.moutai.mall; su -c 'am start -n com.moutai.mall/.module.login.LoginActivity'"

# 2) 启动守护（App 重启自动重挂，单会话 600s）
cd E:/code/逆向/android/case-studies/imoutai/hooks
python -u watch_capture.py > /tmp/watch.log 2>&1 &

# 3) 采样（全自动，out 目录必须全新）
python -u annot5-driver.py --js dump-dex-hook-final.js \
  --out <case>/extract/obs-final-<时间戳> --no-restart
```

RPC 手动调用（Python 侧）：`sample_static_headers()` / `sample_vcode(mobile)` / `probe_crypto()` / `enable_intercept()` / `get_stats()`。

## 4. 窗口纪律（为什么动作要快）

挂钩后 App 存活 **~20-45s**（RiskStub 检测 ArtMethod 篡改 → 加速 Everisk 倒计时；无 hook 时 App 可活 80s+，实验 A/B 实测）。
- 所有 RPC 采样在 attach 后 25s 内完成
- App 死了不用管：守护自动跟随重启重挂（已验证）
- 屏幕保持常亮：`adb shell svc power stayon usb true`

## 5. 明确废弃的路径（不要复活）

| 废弃项 | 原因 |
|---|---|
| spawn 模式注入 / frida-dexdump | 壳按名加载失败 + 加载期自检 abort（F1/F3） |
| 内存扫 dex / 冻结快照 | dex 按需解密读后回密；壳看门狗杀冻结进程（S5-6/F15） |
| B2 nativeLoad 改写 / 手工 JNI_OnLoad / maps 过滤 | 壳加载期自检 abort（mp21-33） |
| Process.setExceptionHandler / JNIEnv 表补丁 | 破坏 SIGSEGV 按需解密循环（mp33/F27） |
| annot4 的常开 intercept hook | 加速死亡且窗口内无自然请求——final 版改为默认关闭 RPC 开启 |

## 6. 修改与重建

```bash
cd hooks/_build
node node_modules/esbuild/bin/esbuild entry-final.js --bundle --platform=neutral --format=iife \
  "--alias:frida-java-bridge=E:/code/逆向/android/case-studies/imoutai/hooks/_build/node_modules/frida-java-bridge/index.js" \
  "--alias:buffer=E:/code/逆向/android/case-studies/imoutai/hooks/_build/buffer-shim.js" \
  --outfile=../dump-dex-hook-final.js
node --check ../dump-dex-hook-final.js
```

改完必须：`node --check` + `py_compile` 驱动 + 真机冒烟一次（S6-15 教训：补丁静默失败，构建过 ≠ 运行对）。
