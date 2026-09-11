"""Offline validation of previously reported VDEX string hits.

This is deliberately a targeted validator, not a memory scanner.  It records
the bytes around the two known offsets and checks the VDEX magic/version and
the common Android VDEX header fields.  It never accesses a device or network.
"""
from __future__ import annotations

import argparse
import gzip
import json
import os
import re
import struct
from typing import Any


SITES = (
    ("000.bin.gz", 3_353_026),
    ("005.bin.gz", 4_124_965),
)
VDEX_MAGIC = b"vdex"
PRINTABLE_VERSION = re.compile(rb"^0[0-9]{2}(?:\x00)?$")


def load(path: str) -> bytes:
    if path.endswith(".gz"):
        with gzip.open(path, "rb") as stream:
            return stream.read()
    with open(path, "rb") as stream:
        return stream.read()


def u32(raw: bytes, offset: int) -> int | None:
    if offset < 0 or offset + 4 > len(raw):
        return None
    return struct.unpack_from("<I", raw, offset)[0]


def inspect_site(path: str, offset: int) -> dict[str, Any]:
    raw = load(path)
    window_start = max(0, offset - 64)
    window_end = min(len(raw), offset + 192)
    prefix = raw[offset : offset + 64]
    magic = raw[offset : offset + 4]
    version = raw[offset + 4 : offset + 8]

    fields = {
        "number_of_dex_files": u32(raw, offset + 8),
        "verifier_deps_size": u32(raw, offset + 12),
        "quickening_info_size": u32(raw, offset + 16),
        "dex_section_size": u32(raw, offset + 20),
    }
    count = fields["number_of_dex_files"]
    sizes_ok = all(
        value is not None and value <= len(raw)
        for key, value in fields.items()
        if key != "number_of_dex_files"
    )
    header_like = (
        magic == VDEX_MAGIC
        and bool(PRINTABLE_VERSION.match(version))
        and count is not None
        and 1 <= count <= 64
        and sizes_ok
    )

    return {
        "file": os.path.basename(path),
        "file_bytes": len(raw),
        "reported_offset": offset,
        "reported_offset_hex": hex(offset),
        "magic_hex": magic.hex(),
        "magic_ascii": magic.decode("latin1"),
        "version_hex": version.hex(),
        "version_ascii": version.decode("latin1"),
        "header_fields_le": fields,
        "header_like": header_like,
        "classification": (
            "valid_vdex_header_candidate"
            if header_like
            else "string_hit_not_vdex_header"
        ),
        "window_start": window_start,
        "window_end": window_end,
        "prefix_hex": prefix.hex(),
        "prefix_ascii": "".join(chr(byte) if 32 <= byte < 127 else "." for byte in prefix),
    }


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("memdump_dir")
    parser.add_argument("--out", required=True)
    args = parser.parse_args()

    results = []
    for filename, offset in SITES:
        path = os.path.join(args.memdump_dir, filename)
        if not os.path.exists(path):
            results.append({"file": filename, "reported_offset": offset, "error": "missing"})
            continue
        item = inspect_site(path, offset)
        results.append(item)
        print(
            f"{filename}+0x{offset:x}: {item['classification']} "
            f"version={item['version_ascii']!r} fields={item['header_fields_le']}"
        )

    summary = {
        "input": os.path.abspath(args.memdump_dir),
        "sites": results,
        "valid_vdex_header_candidates": sum(
            item.get("classification") == "valid_vdex_header_candidate" for item in results
        ),
    }
    with open(args.out, "w", encoding="utf-8") as stream:
        json.dump(summary, stream, ensure_ascii=False, indent=2)
    print(f"[+] wrote {args.out}")


if __name__ == "__main__":
    main()
