# Bangcle metadata triage evidence — 2026-09-09

## Evidence

- Input: `extract/assets/meta-data/manifest.mf`
- Size: `49,479` bytes.
- The file contains no newline characters and only ASCII characters from the Base64 alphabet.
- It contains six `=` separators and therefore is not valid as one single Base64 stream; direct whole-file decoding fails.
- Splitting at the padding boundaries yields seven segments. The first segment includes an encoded text prefix referencing `AndroidManifest.xml` and `stamp-cert-sha256`; the following segments decode to short/random-looking binary blocks and a large binary block.
- No DEX magic, VDEX header, or readable `com/moutai/mall` business implementation was obtained from this parsing. No file was modified.

## Finding

`manifest.mf` is structured/encrypted packer metadata rather than a directly readable business DEX or signature source. Its exact field format and key remain unverified. This result does not establish that the business DEX is absent from the device runtime; it only establishes that this local metadata file was not directly decoded by the tested Base64 interpretation.

## Path

- Source: `extract/assets/meta-data/manifest.mf`
- Related static evidence: `evidence/apk-static-triage-20260909.md`
- Related loader source: `extract/jadx-static-badcode-20260909-v2/sources/com/secneo/apkwrapper/H.java`
