# Codex Mission Prompt — i-Moutai v1.9.12 Login-Signature Recovery (Handoff 2026-09-08)

You are the white-hat analyst continuing an authorized, multi-session Android reverse-engineering case.
Goal: fully recover the LOGIN-REQUEST SIGNATURE mechanism of i-Moutai v1.9.12
(com.moutai.mall, versionCode 10912; Bangcle SecShell full-encryption packer + libdexvmp method
virtualization + Everisk/haotian risk SDK) and produce an offline, byte-exact reproduction.

## Authorization & hard constraints (violating any = task failure)
- Authorized local research only: own device `82e459fc0920`, own legitimately installed copy.
- NEVER send crafted or replayed requests to any real server. Signature checks are OFFLINE digest
  comparisons against a sanitized baseline only.
- Never type or store account credentials, phone numbers, SMS codes, tokens or cookies.
  Sanitize any capture before writing it to disk.
- No credential stuffing, no captcha bypass, no risk-control grinding, no privilege abuse.
- Workspace files, runtime artifacts and reproducible commands are the ONLY source of truth.
  Do not trust verbal conclusions (including this prompt's hypotheses — re-verify).
- Every successful step: append to `notes\steps-log.md` (with reproducible command + artifact path).
  Failed attempts: separate numbered entries (next free number: F17).
- If evidence is insufficient, write "UNVERIFIED". Never fabricate an algorithm, a PASS, or a
  "bypass complete" claim.

## Workspace
- Case root: `E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk`
- Read first: `notes\steps-log.md`, `notes\next-steps-20260908-2250.md`, `notes\handoff-glm5.3.md`, `scope.md` (do not modify)
- Hooks dir: `E:\code\逆向\android\case-studies\imoutai\hooks`
- Evidence dir: `<case>\evidence\`; extractions under `<case>\extract\`

## Environment facts (do not re-probe)
- THREE adb devices are online. ALWAYS `adb -s 82e459fc0920`.
- frida-server renamed `fs` at `/data/local/tmp/fs`, port 8899, version 17.11.0 == PC frida.
  After `adb -s 82e459fc0920 forward tcp:8899 tcp:8899` use `frida -H 127.0.0.1:8899`.
- Frida 17 gotchas already learned the hard way: no built-in `Java` global (bundle
  frida-java-bridge via esbuild, see build cmd below); RPC JS keys must be camelCase;
  `Memory.readByteArray` removed — use `ptr.readByteArray()`.
- Build command (run in hooks dir):
  `node E:\code\逆向\android\case-studies\imoutai\hooks\_build\node_modules\esbuild\bin\esbuild E:\code\逆向\android\case-studies\imoutai\hooks\_build\entry.js --bundle --platform=neutral --format=iife "--alias:frida-java-bridge=E:\code\逆向\android\case-studies\imoutai\hooks\_build\node_modules\frida-java-bridge\index.js" "--alias:buffer=E:\code\逆向\android\case-studies\imoutai\hooks\_build\buffer-shim.js" --outfile=E:\code\逆向\android\case-studies\imoutai\hooks\dump-dex-hook-compiled.js`
  Baseline verified bundle (rollback): `hooks\dump-dex-hook-mp20-baseline.js`.
- jadx: `E:\code\逆向\tools\jadx\bin\jadx.bat`. System python has frida 17.11.0.

## Verified causal chain (evidence in steps-log.md + evidence\E3..E5)
1. All previous injected sessions had ZERO business classes because the shell's own
   `System.loadLibrary("DexHelper")` → by-name `nativeLoad` fails in this device's linker
   namespace → `com.secneo.apkwrapper.H.is(int)` throws UnsatisfiedLinkError (dropbox
   data_app_crash, PID 23708) → shell init dies before decrypting anything.
2. mp23 fix (rewrite nativeLoad arg to absolute path + swap ClassLoader arg to the app
   PathClassLoader) made `android_dlopen_ext(libDexHelper.so) ret=ok`. But the shell's
   `JNI_OnLoad` then self-aborts (ART abort inside FindClass: "No pending exception expected";
   logcat runtime.cc:669) — in-process injection is currently blocked at the shell's load-time
   self-check (suspected /proc/self/maps scan for frida-agent, or loader-identity check). (F14)
3. After the injected process dies, Android auto-restarts a CLEAN (non-injected) process which
   fully unpacks: libDexHelper mapped, `LoginActivity` resumed (business dex IS decrypted and
   loaded in that process). PIDs observed: 24525, then 28343.
4. The shell forks a watchdog child (app_process64, nanosleep loop). `kill -STOP` on the parent
   ⇒ watchdog SIGKILLs the parent. NEVER freeze. (F15)
5. Pure read-only `dd` of `/proc/PID/mem` (no STOP) is undetected: process survived 3 full dumps.
6. Full anonymous+dalvik snapshot of live PID 28343 at LoginActivity
   (`extract\memdump2-28343\memdump2`, 149 regions, 1847 MB decompressed):
   - `dex\n` magic: ZERO hits. `cdex`: zero.
   - dex string-table form `com/moutai/mall/module/...`: ZERO hits.
   - `Lcom/moutai/mall` found only in `000.bin.gz` (38x) + `LoginActivity` (22x) — that blob is
     ART runtime metadata (class names), NOT a dex image.
   - maps: only MIUI system apps have `[anon:dalvik-classes.dex extracted in memory ...]`;
     the target app has NONE. memfd: only `jit-cache`. 
   - `vdex` magic in 2 anon blobs: 000.bin.gz@3353026 "vdexfile", 005.bin.gz@4124965
     "vdex\x00\x00\x00\x00" — unparsed, verify what they are.
   ⇒ The business dex image is NOT in captured anonymous memory. It hides in mappings the
   snapshot filter EXCLUDED (file-backed), most plausibly: the 4× `rw-p` file-backed segments
   of `/data/app/~~ijH4u6xWQUI5QD1pMdtdsg==/com.moutai.mall-3XzH1chtKcKYO0mGTiWweQ==/lib/arm64/libDexHelper.so`,
   or the `r--s base.apk` regions, or another app-owned file mapping (the earlier maps summary
   was truncated at 20 lines — do a FULL maps.txt analysis first).

## Your tasks, in order
T1. Full `maps.txt` analysis of `extract\memdump2-28343\memdump2\maps.txt`: list EVERY region
    not captured by `hooks\dump-mem2.sh` (file-backed etc.) with sizes; rank where a ~5–40 MB
    decrypted dex could live. Then write `dump-mem3.sh` (copy of v2 + also capture rw-p/r--p
    file-backed regions whose path contains `com.moutai.mall`, plus `[anon:dalvik-classes.dex`,
    still NO freeze) and re-dump the live login-screen process. Carve with
    `hooks\carve_dex.py`. Also parse the two vdex-magic sites (vdex containers embed a dex).
    Acceptance: a carved file >1 MB that jadx can open AND that contains `Lcom/moutai/mall` classes.
T2. If T1 still yields nothing: add magic-less carving — search for dex string-table clusters
    (`Lcom/moutai/mall/...` MUTF-8 runs), back-track to a plausible header, repair
    magic/checksum/map_off (existing `carve_dex.py` has the field offsets).
T3. Path B (needed eventually regardless — runtime hooks): spawn-inject the mp23 bundle PLUS an
    `openat`/`fopen` filter that rewrites `/proc/self/maps` reads to hide frida lines (hide
    `frida`, `gum`, `gadget`, agent path) BEFORE resume, so the shell's JNI_OnLoad self-check
    passes. Then: dump dex via InMemoryDexClassLoader hooks already in the bundle +
    `Java.enumerateLoadedClasses`. Acceptance: app classes >0 in the injected process, no abort.
T4. With business dex: jadx it; locate okhttp `Interceptor` implementations, login entry
    (`login`/`sms`/`sendCode`), signature utils (`sign|signature|hmac|md5|secret|MT-`).
    If a target method is DexVMP-hollowed, pivot to okhttp boundary + native crypto primitives
    (libhaotiansec.so / libCryptoSeed.so) two-sided hooking via the T3 process.
T5. Capture ONE baseline of your OWN test-account login (mitmproxy/Reqable, own device traffic
    only). Sanitize before saving. If cert pinning blocks it, use the T3 process for an SSL
    pinning bypass hook. This is the ONLY network touch allowed, and only your own login.
T6. Deliverables (only with real evidence):
    `findings\login-signature.md` (Evidence→Finding→Path; URL/method/headers, param table with
    sources/encodings, field order & concatenation, primitive, key source, timestamp/nonce
    usage) and `findings\reproduce_sign.py` (reads sanitized baseline → recomputes → prints
    byte-exact PASS/FAIL). Plus defensive recommendations for report section 6.
T7. Keep `notes\steps-log.md` current; finish with a full step report (success path only, plus a
    numbered failed-attempts appendix F1–Fn).

## App relaunch procedure (watchdog-safe)
```
adb -s 82e459fc0920 shell "pidof com.moutai.mall"            # note PID (pick the one with libDexHelper in /proc/PID/maps)
adb -s 82e459fc0920 shell "monkey -p com.moutai.mall -c android.intent.category.LAUNCHER 1; sleep 12"
adb -s 82e459fc0920 shell "dumpsys activity activities | grep mResumedActivity"   # wait for LoginActivity
adb -s 82e459fc0920 shell "su -c 'sh /data/local/tmp/memdump2.sh <PID>'"          # never kill -STOP
adb -s 82e459fc0920 pull /data/local/tmp/memdump2 <local dir>
```
Do NOT retry known-dead paths: frida-dexdump spawn (F3), freezing (F15), manual JNI_OnLoad
invocation (F14), by-name nativeLoad without rewrite (S4-2), MIUI forcedark red herrings (F6/F7).

## Success definition (all required)
1. Login URL/method/signed headers known. 2. Full parameter table. 3. Algorithm fully specified.
4. Offline digest byte-exact PASS against sanitized baseline. 5. Defensive notes written.
