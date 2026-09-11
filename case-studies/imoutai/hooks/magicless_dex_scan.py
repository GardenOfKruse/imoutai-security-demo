"""Offline DEX-header scan without requiring the ``dex\n`` magic.

This is intentionally conservative: it only reports candidates whose header
fields are structurally plausible and whose body contains a target class
descriptor. It never talks to a device or a server.
"""
import argparse
import gzip
import json
import os
import struct

HEADER_PATTERN = b"\x70\x00\x00\x00\x78\x56\x34\x12"
TARGETS = (b"Lcom/moutai/mall", b"com/moutai/mall/module", b"LoginActivity")


def validate(raw: bytes, start: int):
    if start < 0 or start + 0x70 > len(raw):
        return None
    size = struct.unpack_from("<I", raw, start + 0x20)[0]
    map_off = struct.unpack_from("<I", raw, start + 0x34)[0]
    string_count = struct.unpack_from("<I", raw, start + 0x38)[0]
    string_off = struct.unpack_from("<I", raw, start + 0x3C)[0]
    type_count = struct.unpack_from("<I", raw, start + 0x40)[0]
    type_off = struct.unpack_from("<I", raw, start + 0x44)[0]
    if not (0x70 <= size <= 200 * 1024 * 1024):
        return None
    if not (0x70 <= map_off < size and string_count and 0x70 <= string_off < size):
        return None
    if type_count == 0 or not (0x70 <= type_off < size):
        return None
    if start + size > len(raw):
        return None
    return {
        "offset": start,
        "size": size,
        "map_off": map_off,
        "string_ids": string_count,
        "string_ids_off": string_off,
        "type_ids": type_count,
        "type_ids_off": type_off,
    }


def scan_file(path: str):
    with gzip.open(path, "rb") if path.endswith(".gz") else open(path, "rb") as f:
        raw = f.read()
    target_hits = {target: [] for target in TARGETS}
    for target in TARGETS:
        pos = 0
        while True:
            pos = raw.find(target, pos)
            if pos < 0:
                break
            target_hits[target].append(pos)
            pos += 1

    candidates = []
    seen = set()
    pos = 0
    while True:
        marker = raw.find(HEADER_PATTERN, pos)
        if marker < 0:
            break
        start = marker - 0x24
        pos = marker + 1
        item = validate(raw, start)
        if not item or start in seen:
            continue
        matched = [target.decode("ascii") for target, hits in target_hits.items()
                   if any(start <= hit < start + item["size"] for hit in hits)]
        if matched:
            item["targets"] = matched
            item["magic"] = raw[start:start + 4].decode("latin1")
            candidates.append(item)
            seen.add(start)
    return {
        "file": os.path.basename(path),
        "bytes": len(raw),
        "target_hits": {k.decode("ascii"): len(v) for k, v in target_hits.items()},
        "candidates": candidates,
    }


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("memdump_dir")
    ap.add_argument("--out", required=True)
    args = ap.parse_args()
    results = []
    for name in sorted(os.listdir(args.memdump_dir)):
        if name.endswith((".gz", ".bin")):
            print(f"[*] scanning {name}", flush=True)
            result = scan_file(os.path.join(args.memdump_dir, name))
            results.append(result)
            print(f"    hits={result['target_hits']} candidates={len(result['candidates'])}", flush=True)
    summary = {
        "files": len(results),
        "target_hits": {target: sum(r["target_hits"].get(target, 0) for r in results)
                        for target in (t.decode("ascii") for t in TARGETS)},
        "candidates": sum(len(r["candidates"]) for r in results),
        "results": results,
    }
    with open(args.out, "w", encoding="utf-8") as f:
        json.dump(summary, f, ensure_ascii=False, indent=2)
    print(f"[+] wrote {args.out}; candidates={summary['candidates']}")


if __name__ == "__main__":
    main()
