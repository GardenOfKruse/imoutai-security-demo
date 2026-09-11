# APK static triage evidence — 2026-09-09

## Evidence

- Input APK: `sample/imoutai-1.9.12.apk`
- SHA-256: `02D69D153820695BCB40DF48B49FD98C88951D8C9898FDA980AA60D4EBD38C8E`
- File size: `64,558,811` bytes.
- ZIP inventory confirmed:
  - `classes.dex`: `23,505,324` bytes.
  - `assets/RiskStub.dex`: `610,152` bytes.
  - `lib/arm64-v8a/libDexHelper.so`: `1,234,026` bytes.
  - `lib/arm64-v8a/libDexHelper-x86.so`: `1,397,453` bytes.
  - `lib/arm64-v8a/libdexvmp.so`: `520,368` bytes.
  - `lib/arm64-v8a/libhaotiansec.so`: `1,253,632` bytes.
  - `assets/meta-data/manifest.mf`: `49,479` bytes.
- Command used:

  ```powershell
  E:\code\逆向\tools\jadx\bin\jadx.bat --show-bad-code --no-res -d extract\jadx-static-badcode-20260909-v2 sample\imoutai-1.9.12.apk
  ```

- JADX exit code: `0`.
- Output: `329` Java files. Under `com/moutai/mall`, only `IsoService.java` and generated `R.java` were emitted. No business login implementation was emitted.
- Manifest evidence in `jadx-out/resources/AndroidManifest.xml`:
  - application is `com.secneo.apkwrapper.AW` at line 135;
  - `LoginActivity` is declared at line 210;
  - `MainActivity` is declared at line 238;
  - `IsoService` is declared at line 998.
- Wrapper evidence in `extract/jadx-static-badcode-20260909-v2/sources/com/secneo/apkwrapper/H.java`:
  - lines 164–180: `System.loadLibrary(...)` is attempted first;
  - lines 230–277: fallback extracts a library entry from the APK into `<dataDir>/.cache/libDexHelper-x86.so` and selects architecture-specific entries;
  - lines 360 onward: the wrapper exposes multiple native methods, including `is(int)`.

## Finding

The APK statically exposes the Bangcle wrapper, native libraries, resource/manifest class names, and risk SDK code, but not the business login implementation in the JADX-readable class set. The wrapper has a load/fallback extraction path; therefore the earlier nativeLoad rewrite changed a meaningful part of the normal loader lifecycle. This is a static loader finding, not proof of the exact red-screen trigger and not proof of a recovered login signature.

## Path

- Source APK: `sample/imoutai-1.9.12.apk`
- Decompiled output: `extract/jadx-static-badcode-20260909-v2/`
- Relevant source: `extract/jadx-static-badcode-20260909-v2/sources/com/secneo/apkwrapper/H.java`
- Manifest: `jadx-out/resources/AndroidManifest.xml`
- Prior dynamic evidence: `evidence/E3-mp21-console.log`, `evidence/E4-mp22-console.log`, `evidence/E5-mp23-console.log`
