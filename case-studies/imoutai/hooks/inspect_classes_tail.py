#!/usr/bin/env python3
"""Read-only structural inspection of the bytes after the parsed classes.dex map."""
from __future__ import annotations

import argparse
import hashlib
import math
import struct
import zipfile
from collections import Counter
from pathlib import Path


def hits(data: bytes, needle: bytes) -> list[int]:
    result: list[int] = []
    pos = 0
    while True:
        pos = data.find(needle, pos)
        if pos < 0:
            return result
        result.append(pos)
        pos += 1


def entropy(data: bytes) -> float:
    counts = Counter(data)
    size = len(data)
    return -sum((n / size) * math.log2(n / size) for n in counts.values()) if size else 0.0


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--apk", type=Path, required=True)
    parser.add_argument("--tail-start", type=lambda value: int(value, 0), default=0x598C)
    parser.add_argument("--block-size", type=int, default=1024 * 1024)
    parser.add_argument("--window", type=int, default=64)
    args = parser.parse_args()

    with zipfile.ZipFile(args.apk) as archive:
        data = archive.read("classes.dex")
    if not 0 <= args.tail_start < len(data):
        raise ValueError("tail start outside classes.dex")
    tail = data[args.tail_start:]
    print(f"classes_size={len(data)} tail_start=0x{args.tail_start:x} tail_size={len(tail)}")
    print(f"tail_sha256={hashlib.sha256(tail).hexdigest()}")

    markers = (
        ("dexdata0", b"dexdata0"),
        ("fdex", b"fdex"),
        ("PKlocal", b"PK\x03\x04"),
        ("classdesc", b"Lcom/moutai/mall"),
        ("signature", b"signature"),
        ("login", b"login"),
    )
    print("-- markers --")
    for name, needle in markers:
        positions = hits(tail, needle)
        print(f"{name} count={len(positions)} offsets={[hex(x) for x in positions[:64]]}")

    print("-- blocks --")
    for offset in range(0, len(tail), args.block_size):
        block = tail[offset : offset + args.block_size]
        print(
            f"rel=0x{offset:x} abs=0x{args.tail_start + offset:x} size={len(block)} "
            f"entropy={entropy(block):.6f} sha256={hashlib.sha256(block).hexdigest()}"
        )

    print("-- marker windows and aligned u32 values --")
    positions = sorted({pos for _, needle in markers for pos in hits(tail, needle)})
    for pos in positions:
        start = max(0, pos - args.window)
        end = min(len(tail), pos + 4 * args.window)
        print(f"pos=0x{pos:x} abs=0x{args.tail_start + pos:x} hex={tail[start:end].hex()}")
        field_start = max(0, pos - 32)
        for field in range(field_start - (field_start % 4), min(len(tail) - 4, pos + 16), 4):
            value = struct.unpack_from("<I", tail, field)[0]
            print(f"  u32 rel=0x{field:x} abs=0x{args.tail_start + field:x} value=0x{value:08x} ({value})")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
