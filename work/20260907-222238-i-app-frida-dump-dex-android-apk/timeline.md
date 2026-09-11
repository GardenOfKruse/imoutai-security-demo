# Timeline (append-only)

## 2026-09-07T22:22:38.7999766+08:00 | lead | init
- action: case-init
- command_or_ref: skills/scripts/case-init.ps1
- result_summary: case directory created; scope pending auth
- artifacts: [scope.md, workitems.md]
- evidence_ids: []
- decision_delta: [case_initialized]
- carry_forward_refs: [scope.md]
- next: fill scope auth + in_scope; set ready_for_act

## 2026-09-11T08:48:00+08:00 | lead | loader-native-boundary-static
- action: offline static loader/native boundary triage
- command_or_ref: IDA 9.3 via case-studies/imoutai/hooks/ida_entries.py and ida_disasm_range.py; byte comparison of APK classes.dex and runtime classes.jar
- result_summary: runtime classes.jar is byte-identical to APK classes.dex; libCryptoSeed JNI names resolve to 0x1058/0x132c in non-executable zero-filled area, while executable 0x3090-0x3e3c contains /proc, Bangcle, package-name and DexHelper/memory-transform checks
- artifacts: [evidence/new-direction-loader-native-boundary-20260911.md]
- evidence_ids: [E-loader-native-boundary-20260911]
- decision_delta: [deprioritize-CryptoSeed-as-login-signature, prioritize-loader-boundary]
- carry_forward_refs: [scope.md, evidence/mp34-annotation-device-block-20260910.md]
- next: obtain original authorized rooted device for dynamic address/call verification; keep offline

## 2026-09-11T09:00:00+08:00 | lead | glm-handoff-snapshot
- action: write GLM handoff snapshot
- command_or_ref: handoff-to-glm-20260911.md
- result_summary: recorded verified progress, failed paths, unresolved questions, and evidence references
- artifacts: [handoff-to-glm-20260911.md]
- evidence_ids: [E-loader-native-boundary-20260911, E-mp34-reflection-20260910, E-mp34-device-block-20260910]
- decision_delta: [handoff-state-frozen-for-next-agent]
- carry_forward_refs: [scope.md, notes/steps-log.md, workitems.md]
- next: GLM to choose next technical route from the recorded evidence
