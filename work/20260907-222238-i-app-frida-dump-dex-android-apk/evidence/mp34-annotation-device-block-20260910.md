# mp34 annotation reflection: device/Frida block (2026-09-10)

## Summary

This note records the interrupted mp34 annotation-reflection rerun. The annotation-enabled Frida bundle exists and passes JavaScript syntax validation, but the originally scoped device is not currently visible over ADB. The currently visible devices have the same target package/version installed, but none exposes `su` or a Frida server, so the dynamic annotation run was not executed.

No DEX dump, RegisterNatives event, Retrofit annotation payload, login request body, signature input, credential, token, cookie, or network request was produced in this rerun.

## Evidence

- Compiled bundle present: `android/case-studies/imoutai/hooks/dump-dex-hook-compiled-mp34-reflect-annotations-20260910.js`
- Syntax validation: `node --check` completed without error.
- Bundle contains the intended reflection annotation collection code:
  - `MP34_REFLECTION_DUMP`
  - `MP34_MINIMAL`
  - `getDeclaredAnnotations`
  - `getParameterAnnotations`
- Original scoped device `82e459fc0920`: not listed after ADB server restart.
- ADB devices currently listed:
  - `70a18048`, model `23013RK75C`
  - `e7200212`, model `23013RK75C`
  - `eb46d389`, model `23013RK75C`
- All three current devices have `com.moutai.mall` installed at `versionCode=10912`, `versionName=1.9.12`.
- All three current devices returned `/system/bin/sh: su: inaccessible or not found`.
- `/data/local/tmp/frida-server-17.8.2-android-arm64` was absent on the checked devices.
- `frida-ps -H 127.0.0.1:8899 -a` failed because no remote Frida server was reachable.

## Finding

The annotation-reflection dynamic experiment is blocked by current device/runtime capability, not by a newly observed app-side detection event. There is no evidence from this rerun that the app detected Frida, displayed the red screen, crashed, or passed/failed BangBang reinforcement.

## UNVERIFIED

- Retrofit method annotations and parameter annotations for `com.moutai.mall.api.f`.
- Whether the mp34 delayed attach path still works on the original rooted device.
- Whether the earlier early-attach SIGSEGV is caused by reinforcement detection, ART loader state, or our instrumentation footprint.
- Any claim that BangBang reinforcement, abnormal-exception detection, or login signature protection has been bypassed.

## Next consumer note

Use `evidence/mp34-reflection-results-20260910.md` and `extract/obs-mp34-reflect-20260910/reflection.jsonl` as the latest successful dynamic evidence. Treat this file only as an environment-blocking record for the annotation rerun.
