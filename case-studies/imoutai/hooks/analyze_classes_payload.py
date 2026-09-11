#!/usr/bin/env python3
'''Offline structural report for DEX entries embedded in the sample APK.'''
from __future__ import annotations

import argparse
import hashlib
import io
import json
import math
import re
import struct
import zipfile
import zlib
from collections import Counter
from pathlib import Path
from typing import Any

DEX_HEADER_SIZE = 112
DEX_FIELDS = (
    "file_size",
    "header_size",
    "endian_tag",
    "link_size",
    "link_off",
    "map_off",
    "string_ids_size",
    "string_ids_off",
    "type_ids_size",
    "type_ids_off",
    "proto_ids_size",
    "proto_ids_off",
    "field_ids_size",
    "field_ids_off",
    "method_ids_size",
    "method_ids_off",
    "class_defs_size",
    "class_defs_off",
    "data_size",
    "data_off",
)
KEYWORDS = (
    b"com/moutai/mall",
    b"Lcom/moutai/mall",
    b"login",
    b"signature",
    b"sign",
    b"hmac",
    b"sha",
    b"md5",
    b"DexHelper",
    b"libdexvmp",
)
MARKERS = (
    ("fdex", b"fdex"),
    ("dexdata", b"dexdata"),
    ("zip_local", b"PK\x03\x04"),
    ("zip_central", b"PK\x01\x02"),
    ("zip_end", b"PK\x05\x06"),
    ("AndroidManifest.xml", b"AndroidManifest.xml"),
    ("classes.dex", b"classes.dex"),
    ("resources.arsc", b"resources.arsc"),
    ("META-INF", b"META-INF"),
    ("lib/arm64", b"lib/arm64"),
)


def sha256(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()


def entropy(data: bytes) -> float:
    if not data:
        return 0.0
    counts = Counter(data)
    size = len(data)
    return -sum((n / size) * math.log2(n / size) for n in counts.values())


def all_offsets(data: bytes, needle: bytes, limit: int = 256) -> list[int]:
    result: list[int] = []
    start = 0
    while len(result) < limit:
        pos = data.find(needle, start)
        if pos < 0:
            break
        result.append(pos)
        start = pos + 1
    return result


def keyword_offsets(data: bytes) -> dict[str, list[int]]:
    lowered = data.lower()
    return {
        keyword.decode("ascii"): all_offsets(lowered, keyword.lower())
        for keyword in KEYWORDS
    }


def marker_report(data: bytes) -> dict[str, Any]:
    result: dict[str, Any] = {}
    for name, marker in MARKERS:
        positions = all_offsets(data, marker, limit=64)
        contexts = []
        for pos in positions[:8]:
            start = max(0, pos - 24)
            end = min(len(data), pos + len(marker) + 40)
            contexts.append({"offset": pos, "context_hex": data[start:end].hex()})
        result[name] = {
            "count_capped": len(positions),
            "offsets": positions,
            "contexts": contexts,
        }
    return result


def embedded_zip_candidates(data: bytes) -> list[dict[str, Any]]:
    result: list[dict[str, Any]] = []
    for pos in all_offsets(data, b"PK\x03\x04", limit=16):
        item: dict[str, Any] = {"offset": pos}
        try:
            with zipfile.ZipFile(io.BytesIO(data[pos:])) as archive:
                names = archive.namelist()
                item["valid"] = True
                item["entry_count"] = len(names)
                item["first_entries"] = names[:16]
        except (OSError, ValueError, zipfile.BadZipFile) as exc:
            item["valid"] = False
            item["error"] = type(exc).__name__
        result.append(item)
    return result


def dex_info(data: bytes) -> dict[str, Any]:
    info: dict[str, Any] = {
        "actual_size": len(data),
        "sha256": sha256(data),
        "magic": data[:8].hex(),
        "is_dex_magic": data[:4] == b"dex\n",
    }
    if len(data) < DEX_HEADER_SIZE:
        info["header_error"] = "shorter than DEX header"
        return info

    values = struct.unpack_from("<8sI20s20I", data, 0)
    fields = dict(zip(DEX_FIELDS, values[3:]))
    fields["endian_tag_hex"] = f"0x{fields['endian_tag']:08x}"
    info["header"] = fields

    semantic_end = fields["data_off"] + fields["data_size"]
    info["semantic_dex_end"] = semantic_end
    info["declared_file_size_matches_actual"] = fields["file_size"] == len(data)
    info["semantic_end_within_actual"] = 0 <= semantic_end <= len(data)
    info["trailing_after_semantic_dex"] = max(0, len(data) - semantic_end)

    info["adler32_stored"] = data[8:12].hex()
    info["adler32_computed"] = f"{zlib.adler32(data[12:]) & 0xffffffff:08x}"
    info["signature_stored"] = data[12:32].hex()
    info["signature_computed"] = hashlib.sha1(data[32:]).hexdigest()
    info["signature_matches"] = data[12:32] == hashlib.sha1(data[32:]).digest()
    info["magic_offsets"] = {
        "dex\\n": all_offsets(data, b"dex\n"),
        "cdex": all_offsets(data, b"cdex"),
        "vdex": all_offsets(data, b"vdex"),
        "ELF": all_offsets(data, b"\x7fELF"),
        "zip_local": all_offsets(data, b"PK\x03\x04"),
    }
    info["keyword_offsets"] = keyword_offsets(data)
    info["marker_report"] = marker_report(data)
    info["embedded_zip_candidates"] = embedded_zip_candidates(data)
    return info


def byte_stats(data: bytes) -> dict[str, Any]:
    counts = Counter(data)
    return {
        "size": len(data),
        "sha256": sha256(data),
        "entropy_bits_per_byte": round(entropy(data), 6),
        "unique_byte_values": len(counts),
        "zero_bytes": counts.get(0, 0),
        "top_byte_counts": [[value, count] for value, count in counts.most_common(8)],
        "prefix_hex": data[:128].hex(),
        "suffix_hex": data[-128:].hex() if data else "",
    }


def block_stats(data: bytes, block_size: int = 1024 * 1024) -> list[dict[str, Any]]:
    result: list[dict[str, Any]] = []
    for start in range(0, len(data), block_size):
        block = data[start : start + block_size]
        result.append(
            {
                "offset": start,
                "size": len(block),
                "sha256": sha256(block),
                "entropy_bits_per_byte": round(entropy(block), 6),
            }
        )
    return result


def analyze_entry(data: bytes) -> dict[str, Any]:
    info = dex_info(data)
    semantic_end = int(info.get("semantic_dex_end", len(data)))
    semantic_end = max(0, min(semantic_end, len(data)))
    tail = data[semantic_end:]
    info["semantic_region_stats"] = byte_stats(data[:semantic_end])
    info["appended_region_stats"] = byte_stats(tail)
    info["appended_region_blocks_1mib"] = block_stats(tail)
    info["appended_region_keyword_offsets_relative"] = keyword_offsets(tail)
    return info


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--apk", type=Path, required=True)
    parser.add_argument("--out", type=Path, required=True)
    args = parser.parse_args()

    entries = ("classes.dex", "assets/RiskStub.dex")
    report: dict[str, Any] = {
        "apk": str(args.apk),
        "apk_sha256": sha256(args.apk.read_bytes()),
        "entries": {},
    }

    with zipfile.ZipFile(args.apk) as archive:
        for name in entries:
            data = archive.read(name)
            item = analyze_entry(data)
            meta = archive.getinfo(name)
            item["zip"] = {
                "compress_type": meta.compress_type,
                "compressed_size": meta.compress_size,
                "uncompressed_size": meta.file_size,
                "crc32": f"{meta.CRC & 0xffffffff:08x}",
            }
            report["entries"][name] = item

    args.out.parent.mkdir(parents=True, exist_ok=True)
    args.out.write_text(
        json.dumps(report, indent=2, ensure_ascii=False) + "\n",
        encoding="utf-8",
    )
    summary = {
        "out": str(args.out),
        "apk_sha256": report["apk_sha256"],
        "entries": {
            name: {
                "actual_size": item["actual_size"],
                "semantic_dex_end": item.get("semantic_dex_end"),
                "appended_after_semantic_dex": item.get("trailing_after_semantic_dex"),
                "appended_sha256": item["appended_region_stats"]["sha256"],
                "appended_entropy": item["appended_region_stats"]["entropy_bits_per_byte"],
            }
            for name, item in report["entries"].items()
        },
    }
    print(json.dumps(summary, ensure_ascii=False))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
