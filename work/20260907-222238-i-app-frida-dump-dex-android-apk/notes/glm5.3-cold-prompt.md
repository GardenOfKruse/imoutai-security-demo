# GLM-5.3 冷启动交接提示词

你接管一个已经进行过多轮实验的 Android 逆向 case。不要从零设计方案，也不要依据上一位 Agent 的口头结论猜测成功；以工作区文件、运行时产物和可复现命令为唯一事实来源。

## 任务

工作目录：`E:\code\逆向\android\work`

Case：`20260907-222238-i-app-frida-dump-dex-android-apk`

目标样本：i茅台 v1.9.12，包名 `com.moutai.mall`，versionCode `10912`。

终极目标：在本地授权设备和离线边界内，完整还原登录接口请求签名机制，并产出可离线逐字节验证的实现。只有拿到真实、脱敏的登录请求基准并通过 digest 比对，才能宣称算法还原完成。

## 先读这些文件

1. `notes/steps-log.md`：成功步骤、失败尝试、可复现命令。
2. `notes/handoff-glm5.3.md`：当前交接摘要和未验证改动。
3. `glm5.3-思路整理.md`：原始技术路线。
4. `scope.md`：授权和 offline 网络边界；不要修改。
5. `case-studies/imoutai/hooks/` 下现有 hook、driver 和编译 bundle。

## 已确认的底线

- Frida spawn 注入成功，Java/native hook 可安装并命中。
- 已确认的局部处理：MIUI ForceDark 缺失库及两个 native 兼容性入口；这不等于 SecShell、DexVMP、Everisk 整体绕过。
- `Process.killProcess`/`System.exit` 可被观测并部分阻断；仍观察到 native `raise/tgkill`，不得宣称异常检测整体绕过。
- `DexFile.openDexFile` 等边界已命中，但业务 DEX 没有捕获；运行时 `com.moutai.mall` 类数量曾为 0。
- 绝对路径 `dlopen(libDexHelper.so)` 成功；mp19 已完整读取 9 个可读区间并生成运行时 ELF 与导出表，导出表目前仅见 `JNI_OnLoad`。
- `IsoService` 为 `:rs` + `isolatedProcess=true`，实验中没有稳定出现可注入的隔离进程。
- 最近写入的 `setNativeRedirect` / `load_native_with_jni` 代码尚未编译和验证；mp19/mp20 是回退基线。

## 约束

- 只操作授权本地设备和本地样本；不向任何真实服务器发送构造或重放请求。
- 不输入或保存账号、密码、手机号、验证码、token、Cookie；如出现本机自有流量，落盘前脱敏。
- 不做撞库、验证码绕过、风控刷接口或业务越权。
- 签名验证只能读取脱敏基准并在本地计算 digest。
- 不删除、覆盖或重建既有样本和产物；不执行 git commit/push/reset。
- 每通过一个步骤就追加 `notes/steps-log.md`，写清通过标准、命令、产物和证据；失败尝试单独记录为 F 编号。
- 不能把“脚本装载”“库映射”“局部兼容性短路”表述成“整体绕过”。证据不足就写未证实。

## 立即执行顺序

1. 检查当前工作区和设备状态；确认 `scope.md` 未改变。
2. 先对最近未验证代码执行 Python 语法检查和 Frida bundle 构建；失败则修复最小问题，成功也要单独记录。
3. 以 mp19/mp20 的已验证 bundle 为回退点，单独测试 `setNativeRedirect`：只针对 `libDexHelper.so`，记录 `dlopen`、`JNI_OnLoad` 返回值、异常和进程状态。
4. 若业务类仍为 0，转向更早的 native/JNI/ART 边界：`JNI_OnLoad`、`RegisterNatives`、`android_dlopen_ext`、自定义 ClassLoader，以及 `:rs` 进程真实创建时机。优先观测，不要盲目 NOP 或扩大线程压制。
5. 一旦捕获到业务 DEX，先验证 DEX 完整性并用 jadx 搜索网络层、登录入口、签名原语；再挂请求构造前的脱敏边界。
6. 只有具备脱敏登录请求基准后，才创建/完善：
   - `findings/login-signature.md`
   - `findings/reproduce_sign.py`
7. 最终审计必须逐项回答：URL/method/headers、参数来源与编码、字段顺序与原语、密钥来源、timestamp/nonce、离线 digest 是否逐字节一致、防御建议。

## 汇报格式

每轮先给一句当前结论，再列本轮新证据、修改文件、验证命令、失败编号和下一步。不要重复已经写入交接文档的背景，也不要在没有证据时生成签名算法、PASS 结果或最终报告。
