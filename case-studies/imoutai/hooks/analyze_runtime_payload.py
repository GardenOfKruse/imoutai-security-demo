#!/usr/bin/env python3
"""Offline structural triage for app-owned runtime payload files."""

from __future__ import annotations

import argparse
import hashlib
import json
import math
import zipfile
from collections import Counter
from pathlib import Path


MARKERS = {
    "dex": b"dex\n",
    "cdex": b"cdex",
    "vdex": b"vdex",
    "elf": b"\x7fELF",
    "zip_local": b"PK\x03\x04",
    "zip_central": b"PK\x01\x02",
    "zip_end": b"PK\x05\x06",
    "dexdata": b"dexdata",
    "fdex": b"fdex",
}
KEYWORDS = (
    b"com/moutai/mall",
    b"login",
    b"signature",
    b"classes.dve",
    b"KEY_RES_ENC",
    b"InMemoryDexClassLoader",
)


def offsets(data: bytes, needle: bytes, limit: int = 64) -> list[int]:
    result: list[int] = []
    start = 0
    while len(result) < limit:
        pos = data.find(needle, start)
        if pos < 0:
            break
        result.append(pos)
        start = pos + 1
    return result


def entropy(data: bytes) -> float:
    if not data:
        return 0.0
    counts = Counter(data)
    size = len(data)
    return -sum((n / size) * math.log2(n / size) for n in counts.values())


def triage(path: Path) -> dict:
    data = path.read_bytes()
    item = {
        "path": str(path),
        "size": len(data),
        "sha256": hashlib.sha256(data).hexdigest(),
        "head_hex": data[:64].hex(),
        "entropy_bits_per_byte": round(entropy(data), 6),
        "zero_bytes": data.count(0),
        "unique_byte_values": len(set(data)),
        "markers": {name: offsets(data, marker) for name, marker in MARKERS.items()},
        "keywords": {
            word.decode("ascii"): offsets(data.lower(), word.lower())
            for word in KEYWORDS
        },
    }
    try:
        with zipfile.ZipFile(path) as archive:
            item["zip"] = {
                "valid": True,
                "entry_count": len(archive.infolist()),
                "first_entries": archive.namelist()[:16],
            }
    except (OSError, ValueError, zipfile.BadZipFile) as exc:
        item["zip"] = {"valid": False, "error": type(exc).__name__}
    return item


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--out", type=Path, required=True)
    parser.add_argument("files", type=Path, nargs="+")
    args = parser.parse_args()
    report = {"files": [triage(path) for path in args.files]}
    args.out.parent.mkdir(parents=True, exist_ok=True)
    args.out.write_text(json.dumps(report, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    for item in report["files"]:
        print(json.dumps({
            "path": item["path"],
            "size": item["size"],
            "sha256": item["sha256"],
            "entropy": item["entropy_bits_per_byte"],
            "markers": {k: v[:8] for k, v in item["markers"].items()},
            "zip": item["zip"],
        }, ensure_ascii=False))
    print(f"OUT={args.out}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
