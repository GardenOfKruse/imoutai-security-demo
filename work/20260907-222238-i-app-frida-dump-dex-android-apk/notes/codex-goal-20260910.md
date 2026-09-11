# Codex Mission Prompt — i-Moutai v1.9.12 Login-Signature Recovery (Handoff 2026-09-10)

You are continuing an authorized white-hat Android reverse-engineering case. Your predecessor
sessions (GLM/Codex, mp1..mp33) have pushed the analysis to a single well-defined blocker.
Read this document fully, then `notes/steps-log.md` (S5-23 is the newest entry) before acting.

## 1. Mission (unchanged)

Fully recover the LOGIN-REQUEST SIGNATURE mechanism of i-Moutai v1.9.12
(`com.moutai.mall`, versionCode 10912; Bangcle SecShell + libdexvmp + Everisk/haotian SDK)
and produce an offline, byte-exact reproduction.

Success = ALL of:
1. Login endpoint URL, HTTP method, signed-headers list.
2. Parameter table (name, source, format/encoding, sample — sanitized).
3. Algorithm: field list + concatenation order + separator, primitive
   (MD5/HMAC-SHA256/AES/RSA...), key source (static/native-derived/server-issued),
   timestamp/nonce usage.
4. Offline verification: recompute digest from a sanitized baseline capture → byte-exact PASS.
5. Defensive assessment (weaknesses + developer recommendations).

## 2. Hard constraints (violation = task failure)

- Authorized local research only. Device `82e459fc0920` (own), own installed copy.
  THREE adb devices are online — ALWAYS `adb -s 82e459fc0920`.
- NEVER send crafted/replayed requests to any real server. The ONLY permitted network touch is
  capturing your own test-account login once (own traffic), sanitized before disk.
- Never type or store credentials/phone numbers/SMS codes/tokens/cookies.
- Never `kill -STOP` the app's main process (shell watchdog kills it — F15).
  Read-only `dd /proc/PID/mem` without freezing is proven safe.
- Workspace artifacts + reproducible commands are the only truth. Re-verify anything you doubt.
- Append every success to `notes/steps-log.md` with command + artifact path.
  Number failures F21, F22, ... Write "UNVERIFIED" where evidence is missing.
  Never fabricate algorithms, PASS results, or "bypass complete" claims.

## 3. Environment (do not re-probe)

- frida-server renamed `fs` @ `/data/local/tmp/fs`, port 8899, v17.11.0 == PC frida.
  `adb -s 82e459fc0920 forward tcp:8899 tcp:8899` → `frida -H 127.0.0.1:8899`.
- Frida 17: no built-in `Java` (bundle frida-java-bridge), RPC keys camelCase,
  `Memory.readByteArray` gone (use `ptr.readByteArray()`).
- Build (run in `E:\code\逆向\android\case-studies\imoutai\hooks`):
  `node _build\node_modules\esbuild\bin\esbuild _build\entry.js --bundle --platform=neutral --format=iife "--alias:frida-java-bridge=<abs>\_build\node_modules\frida-java-bridge\index.js" "--alias:buffer=<abs>\_build\buffer-shim.js" --outfile=dump-dex-hook-compiled.js`
- Driver: `python obs-driver.py --js <bundle> --out <dir> [--wait 35]` (spawns, rewrites
  nativeLoad, launches SplashActivity, polls :rs, dumps stats/classes; saves dex captures).
- jadx: `E:\code\逆向\tools\jadx\bin\jadx.bat`. Case root:
  `E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk`.
- `dump-dex-hook.js` is at mp33 state (mp32 Codex code + A5 table patch gated OFF by
  `globalThis.A5_TABLE_PATCH`). Do NOT destructively trim it — create a new variant file
  (e.g. `dump-dex-hook-mp34.js` + compiled `dump-dex-hook-compiled-mp34.js`).

## 4. Verified causal chain (all evidence on disk)

1. Under spawn injection the shell's `System.loadLibrary("DexHelper")` by-name lookup fails
   → `com.secneo.apkwrapper.H.is(int)` UnsatisfiedLinkError → zero business classes
   (dropbox data_app_crash PID 23708). FIXED since mp23/mp26 by hook B2: rewrite
   `Runtime.nativeLoad` arg0 to the absolute lib path AND arg1 to the caller class's own
   ClassLoader (`H` — verified to loadClass com.secneo.apkwrapper.H; the earlier
   "first PathClassLoader" heuristic picked a shell `DexPathList["."]` loader and broke
   FindClass — v8 lesson).
2. mp26: with the fix, `dlopen(libDexHelper.so)=ok`, `JNI_OnLoad` runs to completion,
   **7 RegisterNatives batches / 20 native methods** registered —
   `bs(Context,I)Object`, `is(I)Z` (the old killer), `us(Context)V`, `hn(Context,Application)V`,
   `pn()V`, `d(String)String`, `gha/ghc/gah/sha/he/gv`, `bli/bla/blr/blq/blc/bls/blv`, `sn(String)I`.
   Evidence: `evidence/E8-mp26-console.log`, `extract/obs-mp26/regnat.jsonl`.
3. mp27–mp33: ALL die at `libDexHelper+0x23974` = `sub_1E33C+0x5638` (`LDR X21,[X0]`),
   where x0 points into an anonymous **PROT_NONE 16MB (0x1000000) region the shell itself
   mmap'd during JNI_OnLoad** (mp28; mp33 shows the mmaps). mp33 disabled the JNIEnv
   table patch → still crashes (table patch NOT the trigger). pthread-entry replacement
   stats `suppressed=0` (no-op; NOT the trigger).
4. Clean (non-injected) runs pass this point: the app fully unpacks and sits at
   `com.moutai.mall/.module.login.LoginActivity`. BUT: full read-only memory snapshots
   of that clean process (files+maps+inotify+endian-tag scans, ~1.8GB, 0 short reads)
   contain ZERO plaintext business dex (`com/moutai/mall/module` = 0 hits) while ART
   metadata contains `Lcom/moutai/mall` (38x) — business classes run without any
   persistent plaintext dex image (S5-2/3/5/6).
5. classes.dex inside base.apk (device md5 == PC md5, `25066d61...`): a 22,924-byte stub dex
   whose header claims file_size=23.5MB, followed by a 22.4MB tail:
   ~8.4MB plaintext (valid dex string_data pools of androidx/kotlin/HMS libraries;
   only 2 moutai descriptors: VideoViewHolder/TimePickerView) + ~14MB entropy≈8.0 cipher.
   Business dex body is in the cipher zone. (`evidence/classes-tail-boundary-strings-20260909.txt`,
   entropy boundary at 0x2598c→plain, 0xe5598c→cipher.)

## 5. PRIME HYPOTHESIS (test this first)

The shell implements **on-demand decryption via a SIGSEGV fault handler on PROT_NONE
regions**: fault → shell handler decrypts/remaps → execution resumes. This explains fact 4
(no persistent plaintext dex) and fact 3 (the crash IS the first such fault — and under our
instrumentation the fault is fatal instead of handled). Frida's signal handling interposition
and especially Codex's X observer (`Process.setExceptionHandler(...)`) likely break the
fault-decrypt-resume cycle.

Falsifier: a minimal bundle WITHOUT any exception observer still crashes at the same PC.

## 6. Task list (in order)

T1 — mp34 (minimal-footprint run). Create `dump-dex-hook-mp34.js` from the current source
    keeping ONLY: B2 (nativeLoad rewrite + caller-loader swap, v8 logic), B3 (MIUI ForceDark
    bypass), C1/C2/C3 (InMemoryDexClassLoader/DexFile/DexClassLoader capture + send_dex),
    E1 (RegisterNatives observer), E2 (class-count timeline). REMOVE/never install:
    `Process.setExceptionHandler` and ALL X observers, maps filter (`install_maps_filter`),
    A5 table patch (already gated off), A5b libart FindClass inline hook, A2/A3 exit/signal
    hooks, A pthread hook, A4 dlopen observer, live-scan timers.
    Run: `python obs-driver.py --js dump-dex-hook-compiled-mp34.js --out extract/obs-mp34 --wait 35`.
    Acceptance: process alive >30s OR `listLoadedClasses("com.moutai.mall")` >0 OR dex captured.
    Record as S5-24 (success path) or F21 (fail, with exact bundle diff).

T2 — only if T1 still crashes at/near +0x23974: single-variable elimination, one run each:
    (a) drop B2 loader-swap (path-rewrite only); (b) drop E1; (c) drop B3. Note any change in
    crash PC/behavior. Goal: identify the minimal perturbation that breaks the fault cycle.

T3 — if process survives/decrypts: dex bytes may exist transiently. Immediately call rpc
    `listDexes()` + `readDex()` (driver already has fallback scan; consider re-enabling
    live-scan in THIS variant only after first class appears). Save captures to
    `extract/obs-mp34/captured/`. Then jadx, verify `com.moutai.mall` classes recovered.

T4 — static parallel (independent of T1-T3):
    a) Tail parser: enumerate ALL valid string pools beyond 0x8c1707..0x9b0158; test whether
       the plaintext zone is a dex body missing only its header — scan for u32 arrays whose
       values equal known string-pool offsets (string_ids table), then type_ids/proto_ids;
       try header reconstruction if ids tables are intact. Target: a loadable library dex as
       methodology proof, then apply to business fragments.
    b) IDA on `extract/native-memdump/mp19/libDexHelper-runtime-18231.so` (sha256
       D4459996...): full analysis of `sub_1E33C` (stage entry) — what does +0x5638 expect in
       X0; who installs the SIGSEGV handler (sigaction/sigaltstack xrefs); where the 16MB
       PROT_NONE regions feed. Scripts exist: `ida_dump_crash_full.py`, `ida_disasm_range.py`,
       `elf_reloc_report.py`.

T5 — with business dex recovered: jadx full; locate okhttp `Interceptor` implementations,
    login entry (`login/sms/sendCode`), signature utils (`sign|signature|hmac|md5|secret|MT-`).
    DexVMP-hollowed methods → pivot to okhttp boundary + native crypto primitives
    (libhaotiansec.so / libCryptoSeed.so; note libCryptoSeed exports
    `CryptoUtil.getSeed/getPrivateKey` JNI names — S5-21/22 found no visible-dex callers,
    so callers are in hidden code).
T6 — capture ONE baseline of your own test-account login (mitmproxy/Reqable). Sanitize
    phone/token/codes before saving. SSL pinning bypass only via the T1-style minimal
    process if needed.
T7 — write `findings/login-signature.md` (Evidence→Finding→Path, param table, algorithm
    chain) + `findings/reproduce_sign.py` (reads sanitized baseline → recomputes → prints
    byte-exact PASS/FAIL). Defensive notes for report section 6. Keep steps-log current.

## 7. Watchdog-safe relaunch procedure

```
adb -s 82e459fc0920 shell "pidof com.moutai.mall"     # pick PID with libDexHelper in /proc/PID/maps
adb -s 82e459fc0920 shell "monkey -p com.moutai.mall -c android.intent.category.LAUNCHER 1; sleep 12"
adb -s 82e459fc0920 shell "dumpsys activity activities | grep mResumedActivity"  # wait LoginActivity
adb -s 82e459fc0920 shell "su -c 'sh /data/local/tmp/memdump2.sh <PID>'"         # read-only, NO freeze
adb -s 82e459fc0920 pull /data/local/tmp/memdump2 <local>
```

## 8. Known-dead paths — do NOT retry

frida-dexdump spawn (F3); freezing the process (F15); manual dlopen+JNI_OnLoad (F14);
by-name nativeLoad without rewrite; MIUI forcedark red herrings (F6/F7); t3/t4e combined
branch (red-screen regression, deprecated); JNIEnv table patch (proven non-trigger, gated
off); pthread-entry replacement (no-op here); base.vdex snapshot (21KB, F19); scanning clean
process memory for plaintext dex (S5-6 closed).

## 9. Key file index

- Hooks: `E:\code\逆向\android\case-studies\imoutai\hooks\` (dump-dex-hook.js @mp33 state,
  obs-driver.py, dump-mem2.sh + carve_dex.py, baseline `dump-dex-hook-mp20-baseline.js`)
- Case: `work/20260907-222238-i-app-frida-dump-dex-android-apk\` — notes/steps-log.md,
  notes/next-steps-20260909.md, evidence/E1..E15*, extract/memdump2-28343,
  extract/memdump3-4999, extract/native-memdump/mp19, extract/obs-mp26..mp33
- Goal docs superseded: codex-handoff-20260908.md, next-steps-20260908-2250.md (history only)
