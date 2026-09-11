# Native string/import triage evidence — 2026-09-09

## Evidence

Read-only ASCII printable-string screening was run over the arm64 native libraries in the APK. The screen used keyword groups for signing, hashing, encryption, login, token/nonce, loading and `/proc` inspection. Selected hits were:

| Library | Selected hits | Interpretation status |
|---|---|---|
| `libCryptoSeed.so` | `Java_com_netease_libs_yxsecurity_encrypt_CryptoUtil_getPrivateKey`, `...getSeed` | Export/string evidence only; caller and role in this app are unverified |
| `librand.so` | `aesEncode`, `aesDecode`, `getMd5S` | Generic crypto capability; no login call chain |
| `libuptsmaddon.so` | HMAC, Nonce, SHA-1/SHA-256/SHA-512, AES-related symbols | Generic SDK capability; no app-specific login call chain |
| `libDexHelper.so` | `/proc/self/maps`, `dlopen`, `libDexHelper.so` | Loader/self-inspection clues; no login/signature API hit in the selected strings |
| `libdexvmp.so` | `Signature.toByteArray`, `sha1`, `/proc/self/maps` | Packer/signature-check clues; not proof of request signing |
| `libbangcle_risk.so` | `/proc/self/maps`, `AES`, `PBKDF2`, `HmacSHA1`, `BusinessURL`, `ASign` | Risk/packer strings; not tied to a login request |

The selected original APK library files were copied read-only to `extract/lib-all-static-20260909/arm64-v8a/` for repeatable local analysis. `elf_reloc_report.py` was then run against the selected files. Its output JSON files are `evidence/elf-relocs-*-20260909.json`.

## Finding

The APK contains several third-party or security-related native crypto capabilities, but the static scan does not establish which one, if any, computes the i-Moutai login-request signature. No endpoint, parameter order, key source, or app-specific caller was recovered from this scan. All such attribution remains `UNVERIFIED`.

The first report run exposed a parser boundary problem on libraries whose dynamic hash metadata did not fit the parser's `DT_HASH` assumption. The parser was corrected to bound symbol iteration to file-backed bytes and to record `dynsym_count_reported`, `dynsym_count`, and `dynsym_truncated_to_file`. The corrected reports complete successfully, but reports marked `dynsym_truncated_to_file=true` are inventory aids only, not complete symbol inventories.

## Path

- APK: `sample/imoutai-1.9.12.apk`
- Selected native copies: `extract/lib-all-static-20260909/arm64-v8a/`
- Scanner source: `case-studies/imoutai/hooks/elf_reloc_report.py`
- Reports: `evidence/elf-relocs-*-20260909.json`
- Dynamic comparison logs: `evidence/E3-mp21-console.log`, `evidence/E4-mp22-console.log`, `evidence/E5-mp23-console.log`
