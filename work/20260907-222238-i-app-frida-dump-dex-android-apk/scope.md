# Case Scope

## meta
- case_id: 20260907-222238-i-app-frida-dump-dex-android-apk
- created: 2026-09-07T22:22:38.7999766+08:00
- operator: local
- project_root: E:\code\逆向\android
- primary_skill: apk-reverse/SKILL.md
- primary_id: R1
- lead_role: lead
- specialist_roles: []
- hint: i茅台 app 梆梆加固分析 Frida 脱壳 dump dex Android APK 逆向 防御建议
- preset: authorized_target_only

## auth
- status: granted
- basis: client_authorized_engagement
- evidence_of_auth: |
    客户方（目标公司开发团队）出具纸质盖章授权文书，已完成线下备案（操作者声明确认，2026-09-11）。
    授权范围：对本公司系统的风控能力测试，含模拟登录、签名模拟、接口风控压力测试。
    硬性约束（客户指定，等同 scope 条款）：
    1. 仅限夜间时段执行，禁止白天测试；
    2. 仅允许一次执行（single run），禁止多轮；
    3. 并发上限 2 个进程；
    4. 不得影响线上稳定性（含限流/熔断/止损阈值与中止条件，见 runbook）。
- MUST NOT proceed if status != granted

## in_scope
- assets:
  - i茅台 APK（设备 82e459fc0920 本地安装副本，包名待确认 cn.moutai.mall）
  - 梆梆加固壳 so（libDexHelper.so 等）
- surfaces:
  - 本地 APK 静态结构
  - 本地进程内存（dump dex/so）
  - 本地 JNI/frida 动态插桩
- activities:
  - 静态解包分析
  - frida hook / 反反调试
  - 内存 dump 脱壳
  - 防御建议报告撰写

## out_of_scope
- assets: []
- activities: [dos, phishing_real_users, unrestricted_exfil]
- 追加（授权文书仍不覆盖的动作）:
  - **高峰时段（06:00–06:15 系统申购高峰）的任何生产请求**（客户指定：会影响真实用户）
  - 同一目标的第二次及以后的压力测试执行
  - **任何多进程/并发执行（验证阶段单进程硬限制，见 AGENTS.md）**
  - 持续高压/高频请求（超出 runbook 的请求总量与速率上限）
  - 真实支付提交（下单链路测试止步于支付页）

## network_profile
- mode: authorized_target_only
- notes: |
    授权目标仅限：h5.moutai519.com.cn / app.moutai519.com.cn（客户自有系统）。
    允许窗口（客户指定 2026-09-12）：
      - 夜间：20:00 – 次日 01:00
      - 日间：07:00 – 18:00
    高峰时段禁止任何生产请求：06:00 – 06:15（系统申购高峰，2026-09-12 更正，替代此前 01:00–07:00/18:00–20:00 的错误标注）。
    缓冲时段（01:00–06:00、06:15–07:00、18:00–20:00）：非原定窗口，技术上放行但需客户对接人当场知情。
    执行模式：单轮、单进程、速率与总量受 runbook 硬门禁约束（AGENTS.md）。
    其余分析保持本地离线。

## deliverables
- report: true
- field_journal: true
- diagrams: true
- timeline: true

## constraints
- timebox: {}
- stealth: low
- data_handling: anonymize

## signoff
- ready_for_act: true
- checklist:
  - [x] auth.status = granted
  - [x] in_scope.assets non-empty OR offline sample path set
  - [x] network_profile.mode chosen
  - [x] out_of_scope reviewed
  - [x] roles assigned (see skills/ops/role-map.md)

## ops_refs
- skills/ops/scope-contract.md
- skills/ops/evidence-finding-path.md
- skills/ops/role-map.md
- skills/ops/timeline-workitem.md
- skills/ops/IDENTITY.md