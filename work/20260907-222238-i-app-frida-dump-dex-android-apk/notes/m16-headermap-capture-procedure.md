# M1.6 真实 HeaderMap 抓取 — 下次插线 3 分钟操作手册

> 目的：抓测试设备发起真实请求时的完整 MT-* 头 → 喂入 demo 实弹模式档案 → 请求与真实设备完全对齐
> 前置：设备插 USB + root + `/data/local/tmp/fs` 已起（8899）+ adb forward tcp:8899
> 失败教训（2026-09-12 obs-headermap2/3）：① 熔断窗口 ~30s，必须抢时间；② attach 到了旧 PID（先 force-stop 再核对 pidof 与 attach PID 一致）；③ UI 自动化输入要在 hook 安装完成（日志出现 "all hooks installed"）之后立即做

## 步骤（PowerShell / Git Bash）

```bash
# 1. 全新启动到登录页
adb -s 82e459fc0920 shell "am force-stop com.moutai.mall; sleep 1; su -c 'am start -n com.moutai.mall/.module.login.LoginActivity'"
sleep 7
adb -s 82e459fc0920 shell pidof com.moutai.mall   # 记下 PID（应只有一个主进程）

# 2. 后台起抓取会话（新目录！driver 拒绝非空目录）
cd E:/code/逆向/android/case-studies/imoutai/hooks
python -u obs-driver.py --js dump-dex-hook-annot4.js \
  --out <case>/extract/obs-headermapN-$(date +%H%M) --wait 40 --attach-running &

# 3. 等 "all hooks installed" 出现（约 2s），立刻 UI 操作：
#    ★★★ 必须先勾选协议复选框（130,827）——不勾它，获取验证码不发任何请求（2026-09-12 实测教训）
export MSYS_NO_PATHCONV=1
adb -s 82e459fc0920 shell "input tap 130 827"        # ★ 勾选"我已阅读并同意"复选框
adb -s 82e459fc0920 shell "input tap 485 447"        # 手机号输入框
adb -s 82e459fc0920 shell "input text 13800001234"
adb -s 82e459fc0920 shell "input tap 862 641"        # 获取验证码 → 触发 check/phoneSegment 真实请求
sleep 6
```

## ⚠️ 反制升级注意（2026-09-12 深夜实测）

多次崩溃重启后，壳进入反制升级态：**挂钩后数秒即冻结（ANR）而非倒计时退出**，UI 自动化来不及完成。
应对：
- 先 `adb reboot` 恢复设备常态，重启后 fs 重新拉起再尝试（首请求窗口更宽）
- 一条复合命令内完成全部 tap（不要分多轮）
- 若仍冻结：改用人工手指操作（操作者本人），AI 只负责起会话——人手比 input 序列快
- 单日多次崩溃会加剧反制，建议换一天再试

## 4. 提取（events.log → 档案 JSON）

events.log 中 `[agent-send] {'type': 'request', ...}` 条目即含真实请求的全部 headers。
整理为：
```json
{ "deviceKey": "<MT-Device-ID 值>",
  "headers": { "MT-Device-ID": "…", "MT-APP-Version": "…", "MT-Token": "…", "MT-R": "…",
               "User-Agent": "…", "Content-Type": "application/json", "…": "…" } }
```
粘贴进 demo 实弹授权门（或存 `demo-app/docs/real-headermap.json` 备用）。

## 5. 顺带做（同会话内）

- 若触发了真实验证码下发：**不要输真实收到的验证码做登录**（避免消耗）；仅抓头即可
- 一并记录 a$b.a/b/c 的 clips_* 值与 MT-Device-ID 是否一致（设备绑定关系佐证）
- 抓完 force-stop，结束

## 已知注意

- 熔断 ~30-48s：所有动作在 T+25s 前完成
- attach 崩 FindClassDef → 重启手机重试
- tap 坐标基于 1080×2400 分辨率登录页（2026-09-12 实测有效）
