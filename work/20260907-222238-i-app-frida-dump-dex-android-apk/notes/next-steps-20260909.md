# 方向裁定与推进计划（2026-09-09，接管 Codex 后）

> 取代 `next-steps-20260908-2250.md` 的路线部分；环境事实节仍有效。

## 一、本轮新证据（详见 steps-log S5-1..S5-6）

| 检验 | 结果 |
|---|---|
| 文件系统全量复查（含 cache/files） | 无业务 dex；base.vdex 21KB 装不下 classes.dex |
| maps 全量审计（app 区域/dalvik-classes.dex/deleted/data 映射） | app 无 dex 载体映射 |
| 大区域完整性（region 1GB / free 512MB / zygote） | 全部完整读取，0 短读，快照可信 |
| dex 魔数 / cdex / endian_tag+header_size / 斜杠描述符 四重扫描 | 全部 0 命中 |
| inotify 启动期全程监听 | 无 dex/jar 文件创建删除 |
| APK md5 / 新构件 | 设备==PC；新发现 libdexjni.so（未加载）、libDexHelper-x86.so |
| A5（Codex 的 FindClass 观察） | 实现有误：JNI_OnLoad arg0=JavaVM* 被当 JNIEnv*，改写的是 VM 表 slot6，从未生效 |

**裁定**：外部只读扫描路线收口（dex 正文不持久存在于可读内存）；`Lcom/moutai/mall` 仅存在于 ART 运行时元数据。

## 二、唯一主攻：进程内 v6（mp23 差最后一步）

mp23 已达成：nativeLoad 实参改绝对路径 + loader 换 app PathClassLoader → `dlopen(libDexHelper.so)=ok`。
剩余阻塞：壳 `JNI_OnLoad` 内某 JNI 调用失败产生 pending exception → 后续 `FindClass` 入口命中 ART CHECK `No pending exception expected` → abort。

v6 修法（对全局 JNIEnv 函数表 slot 6 打补丁，窗口化激活）：
1. 脚本加载期：`Java.vm.getEnv()` → 取全局 JNINativeInterface 表 → slot6(FindClass) 替换为修复回调；
2. 模块观察器在 libDexHelper `JNI_OnLoad` onEnter/onLeave 置/清 `findclass_gate`；
3. 回调逻辑（仅 gate 窗口内生效）：
   - 调原 FindClass **前**先 `ExceptionClear`（slot17）——直接拆除 abort 引信；
   - 原调用返回 NULL → 再 ExceptionClear → 类名斜杠转点分 → 经 app PathClassLoader `loadClass` 代解析 → 返回 `cls.$handle`（局部引用，JNI_OnLoad 帧内有效）；
4. 预期连锁：JNI_OnLoad 走通 → RegisterNatives（E1 观测）→ 壳初始化完成 → InMemoryDexClassLoader/DexFile 钩子在解密瞬间捕获明文 dex（绕过"读后回加密"）→ `com.moutai.mall` 业务类 > 0。

验收标准（全部满足才算过）：注入进程存活 >30s、无 abort、`loaded app classes > 0`、dex_captured > 0 或 listDexes 命中带魔数候选。
失败则：F21 记录，fallback 依次试 (a) 保留 gate 到整个 Application.onCreate 结束；(b) 同法修补 slot30(GetObjectClass)/其他失败面；(c) 判定壳自检非 FindClass 单点，转 libDexHelper 运行时映像（mp19）IDA 定位 JNI_OnLoad 内自检点（`ida_disasm_range.py`/`elf_reloc_report.py` 已就位）。

## 三、拿到业务 dex 后（不变）
jadx → okhttp Interceptor / login 入口 / sign|signature|hmac|md5|secret 工具类 → VMP 空壳则转网络层+native 原语双向 hook → 本人账号脱敏基准 → findings/login-signature.md + reproduce_sign.py 离线逐字节 PASS。

## 四、纪律
- 不冻结父进程、不手工调 JNI_OnLoad、不发真实请求、不存凭据；
- 成功步骤记 steps-log，失败编号顺延（下一号 F21）；
- 证据不足写 UNVERIFIED。
