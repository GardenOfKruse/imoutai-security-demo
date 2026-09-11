#!/usr/bin/env python3
"""Extract structurally valid embedded DEX images from a local binary."""

from __future__ import annotations

import argparse
import hashlib
import json
import struct
from pathlib import Path


MAGIC = b"dex\n"


def candidate(data: bytes, offset: int) -> tuple[int, dict] | None:
    if offset + 0x70 > len(data):
        return None
    header = data[offset : offset + 0x70]
    file_size = struct.unpack_from("<I", header, 0x20)[0]
    header_size = struct.unpack_from("<I", header, 0x24)[0]
    endian = struct.unpack_from("<I", header, 0x28)[0]
    map_off = struct.unpack_from("<I", header, 0x34)[0]
    string_count = struct.unpack_from("<I", header, 0x38)[0]
    string_off = struct.unpack_from("<I", header, 0x3C)[0]
    if header_size != 0x70 or endian != 0x12345678:
        return None
    if file_size < header_size or offset + file_size > len(data):
        return None
    if not 0x70 <= map_off < file_size:
        return None
    if not string_count or not 0x70 <= string_off < file_size:
        return None
    body = data[offset : offset + file_size]
    return file_size, {
        "map_off": map_off,
        "string_ids_size": string_count,
        "string_ids_off": string_off,
        "sha256": hashlib.sha256(body).hexdigest(),
    }


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("input", type=Path)
    parser.add_argument("--out", type=Path, required=True)
    args = parser.parse_args()
    data = args.input.read_bytes()
    args.out.mkdir(parents=True, exist_ok=True)
    records = []
    seen = set()
    position = 0
    while True:
        position = data.find(MAGIC, position)
        if position < 0:
            break
        result = candidate(data, position)
        position += 1
        if result is None:
            continue
        size, info = result
        if info["sha256"] in seen:
            continue
        seen.add(info["sha256"])
        name = f"embedded_{len(records):03d}_{size}.dex"
        (args.out / name).write_bytes(data[position - 1 : position - 1 + size])
        records.append({"file": name, "offset": hex(position - 1), "size": size, **info})
    manifest = {"input": str(args.input), "input_size": len(data), "count": len(records), "dexes": records}
    (args.out / "manifest.json").write_text(
        json.dumps(manifest, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
    )
    print(json.dumps(manifest, ensure_ascii=False, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
