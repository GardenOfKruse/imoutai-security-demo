# Work Items

| ID | title | role | targets | surface | status | evidence | notes |
|----|-------|------|---------|---------|--------|----------|-------|
| WI-001 | Establish scope and auth | lead | case | process | in_progress | | |
| WI-009 | Correlate loader/native boundary with login signature | lead | APK/classes.dex/libCryptoSeed.so | static RE | in_progress | E-loader-native-boundary-20260911 | Runtime classes.jar ruled out as decrypted DEX; CryptoSeed login role remains UNVERIFIED |
| WI-010 | GLM takeover from evidence snapshot | handoff | case artifacts | documentation | ready_for_handoff | E-loader-native-boundary-20260911; E-mp34-reflection-20260910; E-mp34-device-block-20260910 | Current state frozen in handoff-to-glm-20260911.md; no completion claim |

## Coverage
- [ ] Recon/analysis complete for in_scope assets
- [ ] Critical/High candidates triaged (or N/A for pure RE)
- [ ] Validated findings have Evidence (E-*)
- [ ] Path documented (attack/call/solve)
- [ ] Timeline continuous across major phases
- [ ] Report via docs-generator
- [ ] field-journal anonymized

## Refs
- skills/ops/timeline-workitem.md
- skills/ops/evidence-finding-path.md
