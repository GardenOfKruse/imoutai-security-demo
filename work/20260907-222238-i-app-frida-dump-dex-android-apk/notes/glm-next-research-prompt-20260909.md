# GLM 新接管提示词：证据中性、独立裁定版

## 最新增量（2026-09-10，优先读取）

再增量：mp34 注解反射版 bundle 已生成并通过语法检查：`hooks/dump-dex-hook-compiled-mp34-reflect-annotations-20260910.js`。该 bundle 具备注解读取逻辑，但本轮未执行动态采样：原 scope 设备 `82e459fc0920` 当前不在 ADB 列表；当前在线的 `70a18048`、`e7200212`、`eb46d389` 虽均安装 `com.moutai.mall` `versionCode=10912` / `versionName=1.9.12`，但均无 `su`，且无可用 Frida server。因此没有产生 Retrofit 注解、DEX、RegisterNatives、登录请求或签名输入新证据。读取 `evidence/mp34-annotation-device-block-20260910.md`，并把它只解释为环境阻塞，不要解释成 App 检测、红屏、崩溃或绕过结果。

端点不可达已解决。mp34 延迟附加 + 反射在 PID 17672 成功保存 209 个唯一业务类、1,937 条方法声明、0 条反射失败，结束另存 253 类清单。立即读取 `evidence/mp34-reflection-results-20260910.md` 和 `extract/obs-mp34-reflect-20260910/reflection.jsonl`；文末旧端点阻塞记录只代表历史。

声明已定位到 `LoginActivity.login/authLogin/getVerifyRequest`、`api.f.E0(LoginRequest, Map, Continuation)`、`api.f.b1(AuthLoginRequest, Map, Continuation)` 和 `api.a.intercept`。它们是元数据锚点，尚不是调用图或算法证据。方法体、请求签名输入及逐字节复现均未取得。延迟附加成功不证明早期 SIGSEGV 按需解密假设，也不证明检测整体绕过。最后 application-requested 为驱动主动 detach，不能解释为应用崩溃。路线仍由接管者独立判断。

## 使用原则

这份材料不指定下一条技术路线。它只汇总多轮 Codex/GLM 已产生的证据、失败边界和验收标准；接管模型必须先独立评估证据，再选择、组合、回滚或放弃候选路线。任何候选方向都要先用最小、可证伪的实验验证，不得因为本文件列出了某个线索就把它当成根因或主线。

尤其不要预设“尾随载荷一定是业务 DEX”，也不要预设“继续 Frida 绕检测一定能成功”。尾随区域、干净进程取证、native 静态分析、运行时边界观察、测试构建/本地 mock 等都只是候选方向；模型可以提出完全不同的路线。

## 可直接交给 GLM 的提示词

```text
你接手的是公司内部开发团队与安全团队授权的 Android 安全测试案例：i-Moutai v1.9.12，包名 com.moutai.mall，versionCode 10912，授权设备 82e459fc0920。该设备是白名单测试设备；在当前案例 scope 内的本地静态分析、只读文件/内存取证、可逆实验和运行时观察均视为已授权，不要反复询问同一项授权。

但请严格服从案例文件的边界：当前 scope.md 的 network_profile=offline。不得因“内部授权”自行访问真实业务服务、构造或重放请求、使用或保存账号/手机号/验证码/token/cookie，也不得把网络重试当作默认权限。只有当后续消息明确给出一个 allowlisted 的本地 mock 或公司 staging 端点、明确的脱敏测试数据和一次范围扩展确认时，才可在该端点做最小化验证；范围未改变时不要重复弹出同一确认。不得修改 scope.md 来扩大范围。

最终目标仍是：在离线证据充分的前提下恢复 LOGIN-REQUEST SIGNATURE，并产出 byte-exact 的本地复现；没有逐字节证据就写 UNVERIFIED，不得伪造 PASS。

先读取并以当前文件为准：
1. 案例根目录 scope.md（只读）；
2. notes/steps-log.md；
3. notes/glm-correction-handoff-20260909.md；
4. evidence/classes-dex-payload-20260909.json；
5. evidence/ida-libdexhelper-selected-full-20260909.txt；
6. evidence/ida-libdexhelper-helper-functions-20260909.txt；
7. evidence/mp27-mp32-protection-triage-20260909.md；
8. evidence/native-string-triage-20260909.md；
9. extract/libDexHelper-stage-decrypted-20260909.elf 的校验报告与对应 IDA 文本。
10. 本提示词末尾“接管后的新增事实”所引用的增量证据：`evidence/old-baiduprotect-layered-dex-20260909.md`、`evidence/libdexvmp-cross-version-diff-20260909.md`、`evidence/libdexhelper-cross-version-keyword-xref-20260909.md`、`evidence/current-native-embedded-payload-scan-20260909.md`、`evidence/current-aux-native-boundary-20260909.md`。
11. 辅助 native 与可见 DEX 调用点的增量阴性核验：`evidence/current-aux-callsite-negative-20260909.md`。
12. 当前 APK 资产角色分类核验：`evidence/current-asset-role-classification-20260910.md`。
13. 本地历史材料中的登录签名调用链阴性核验：`evidence/local-login-chain-negative-20260910.md`。

已确认事实（不要升级成成功结论）：
- Frida 通道、脚本加载和部分只读 native 操作曾成功；当前侵入式分支不能维持生命周期。
- mp26–mp31 能加载 libDexHelper、进入 JNI_OnLoad，并观察到 7 批共 20 个 RegisterNatives；随后在同一相对偏移 +0x23974 发生 protection failure。
- mp28 的 fault/x0 地址落在匿名 PROT_NONE 区域；mp31 在实际 sub_1E33C 入口看到带 MTE 标签的 JNIEnv 指针。原因不能简单写成“入口 JNIEnv 无效”。
- 干净进程可以到 LoginActivity；多轮不冻结只读快照未得到合法业务 DEX。RiskStub/Coralline 的 Frida、Hook、Dex 注入、debugger、ptrace 检查已在静态代码中确认，但未证明某个具体分支就是红屏原因。
- APK 的 classes.dex 文件总长 23,505,324 字节；可解析的初始 DEX 语义区终点约为 22,924 字节，之后有 23,482,400 字节尾随区域。证据文件记录了尾随区域 SHA-256、`dexdata0`、多个 `fdex`、一个无法独立解析的 PK zip-local 头，以及业务类名、login、signature 等字节串；全文件没有第二个合法 dex/cdex/vdex/ELF 魔数。
- `libDexHelper` 的隐藏阶段已经生成一个结构有效的 AArch64 ET_DYN 重建产物，但尚未证明它可直接运行或等同于完整业务代码。静态线索包含 `classes.dve`、`KEY_RES_ENC`、assets、`Anonymous-DexFile`/`DexFile::OpenMemory`、`InMemoryDexClassLoader`、ELF/pread/mmap/mprotect；新发现的 `libdexjni.so`、`libdexvmp.so`、`libenc.so`、`libCryptoSeed.so` 等必须分别核对，不能凭名称猜功能。

请独立完成路线裁定：
1. 从现有证据提出至少两个互相不同的候选解释/路线；也可以否定所有已列线索并提出新路线。对每条写出支持证据、反证、最小验证实验、停止条件和预期产物。
2. 先选择信息增益最高且风险最低的一项最小实验；实验结果若不支持假设，立即记录并换路，不得连续叠加未经验证的 hook、loader 改写、内存过滤或解密猜测。
3. 候选路线可以包括但不限于：APK/尾随载荷结构分析、干净进程的只读取证、各 native 构件静态/差分分析、运行时加载/网络边界观察、纯函数离线 harness、或由内部开发团队提供的可调试测试构建。上述只是例子，不是执行顺序，也不是正确性暗示。
4. 每个成功步骤追加 `notes/steps-log.md`，写明可复现命令和产物；失败从当前日志的下一个 F 编号开始单独记录。不要覆盖既有证据。
5. 所有结论分为 Evidence、Finding、Inference、UNVERIFIED。只有直接证据支持时，才可以声称业务 DEX、壳检查、异常检测或签名机制已恢复/绕过。
6. 最终交付仍需满足原目标：若能恢复业务 DEX，再定位登录入口、HTTP 边界、签名输入和 native crypto；若不能，提交已排除假设、证据缺口和下一项最小实验，不伪造算法或 PASS。
7. 只有存在脱敏基准时才生成 `findings/login-signature.md` 和 `findings/reproduce_sign.py`；复现脚本只能读脱敏数据并在本地做逐字节 digest 比对，禁止发送请求。

工作纪律：独立审查前任结论；区分 Evidence、Finding、Inference、UNVERIFIED；不使用真实服务和凭据；不把 Frida 连接、dlopen=ok、native 注册或字符串命中写成壳绕过、异常检测绕过或签名恢复成功。
```

## 为什么不预设路线

- 进程内路线的失败点已重复出现，但这只能排除当前若干组合，不能证明所有运行时路线都错误。
- `classes.dex` 尾随区、native 隐藏阶段、RiskStub 检测和干净进程行为都是真实线索，但每一项仍可能是诱饵、旁路构件或与登录签名无关。
- 因此交接材料只提供可复核事实和反证要求，把路线选择留给接管模型；模型必须用实验结果而不是提示词措辞决定下一步。

## 接管后的新增事实（仍不指定路线）

- 旧版 `1.9.1` 项目中的 `baiduprotect1.i.dex` 至 `baiduprotect6.i.dex` 均为单条目 ZIP；解包后的 `classes.dex` 虽有 `dex\\n035` 头，但所有类/方法/字符串索引均为 0，JADX 报告 `No classes for decompile`。配套 `.jar` 不是标准 ZIP/JAR，熵约 `7.877–7.992 bits/byte`。详见 `evidence/old-baiduprotect-layered-dex-20260909.md`。
- 进一步的内容分布核验显示，六个 `.i.dex` 提取条目只有 50–51 个非零字节，`0x99` 后是长零区；对应 `.jar` 非零比例均超过 `0.997`、字节取值覆盖 256 个值。该配对只支持“DEX 形状占位头 + native 处理的高熵资源”的结构推断，不证明 `.jar` 格式、解密算法或与当前版本复用。
- 旧版与当前版同名 arm64 `libdexvmp.so` 都是 AArch64 ET_DYN、入口数值均为 `0x8e20`，但文件长度、动态元数据和绝大多数字节不同，不能直接按旧版偏移迁移。详见 `evidence/libdexvmp-cross-version-diff-20260909.md`。
- 旧版 `libbaiduprotect.so` 的动态导入包含 Android asset 读取、zlib 解压、文件/映射、动态加载和 inotify；当前隐藏阶段重建 ELF 也有 inflate/CRC 与运行时保护相关导入，但构件同源和调用顺序未证实。详见 `evidence/old-baiduprotect-layered-dex-20260909.md` 与 `evidence/elf-relocs-old-libbai-protect-20260909.json`。
- 旧版 `libbaiduprotect.so` 字符串表还在两个偏移范围命中 `classes.dex` 子串；这只是静态锚点，不能代替函数级调用链或证明其结果是业务 DEX。
- 旧版 `libbaiduprotect.so` 在尾部还包含一个可独立解析的单条目 ZIP，内层 156 字节 `classes.dex` 仍是零类/零方法空表头；这确认了 loader/stub 资源存在，但不是业务 DEX。
- 对当前案例 `extract/` 下所有 `.so` 做同口径扫描，没有发现可独立解析的 ZIP 尾部；旧版 native 内嵌 stub ZIP 未在当前静态 `.so` 中复现。该阴性结果只排除简单静态替换，不否定运行时短命缓冲区。
- 当前 `libCryptoSeed.so` 的直接字符串表包含 `CryptoUtil.getSeed/getPrivateKey` JNI 导出名，`libdexjni.so` 主要包含文件/映射/动态加载导入；但当前 `classes.dex` 可解析语义区没有对应类/方法名，`com/netease` 与 `token` 命中仅在尾随区。两库与登录签名的关系仍是 `UNVERIFIED`。详见 `evidence/current-aux-native-boundary-20260909.md`。
- 进一步的 APK 全条目与本地材料搜索没有形成“可见业务类 → `CryptoSeed` → 登录请求签名”的静态调用点；`signature` 在语义区仅有单个命中，不能据此归因。详见 `evidence/current-aux-callsite-negative-20260909.md`。
- 当前 APK 资产盘点显示 `info.y` 更接近风险/环境检测规则；`defaultv0/v1` 解码后不是 DEX/ELF/ZIP；APK 中没有 `assets/baoef` 条目。资产文件名、熵或检测字符串都不足以证明业务载荷或登录签名关系。详见 `evidence/current-asset-role-classification-20260910.md`。
- 当前可见 JADX 输出与旧项目本地材料均未形成登录入口、请求层、签名工具或 `CryptoSeed` 调用链；可见 `Signature`/`MessageDigest` 命中主要属于风险/完整性代码。已排除从现有可见源码直接恢复登录签名的简单路线。详见 `evidence/local-login-chain-negative-20260910.md`。
- 旧版 `libDexHelper` 固定/运行时样本、当前 APK 内原始 helper 没有直观 `login`/`signature`/`token` 字节串；当前隐藏阶段重建 ELF 的少量相关字符串没有直接 PC-relative 调用引用，`token` 只有数据表样式指针。详见 `evidence/libdexhelper-cross-version-keyword-xref-20260909.md`。
- 上述新增事实只用于更新证据集，不暗示旧载荷复用、重建 ELF 等价、业务 DEX 存在、检测绕过或登录签名恢复成功。
- mp34 最小足迹实验未进入目标进程：脚本/驱动改动已完成，ADB 设备在线，但 `127.0.0.1:8899` 无可用 Frida server；驱动在 `dev.spawn` 处报 `frida.ServerNotRunningError`。因此没有 hook、DEX、RegisterNatives 或类加载结果，mp34 的运行时假设仍为 `UNVERIFIED`。详见 `evidence/mp34-minimal-20260910.md`。
