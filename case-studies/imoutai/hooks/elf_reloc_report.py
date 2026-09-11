"""Emit a compact, read-only ELF64 relocation/import report."""
from __future__ import annotations

import argparse
import json
import struct
from typing import Any


PT_LOAD = 1
PT_DYNAMIC = 2
DT_NULL = 0
DT_STRTAB = 5
DT_SYMTAB = 6
DT_STRSZ = 10
DT_SYMENT = 11
DT_HASH = 4
DT_RELA = 7
DT_RELASZ = 8
DT_JMPREL = 23
DT_PLTRELSZ = 2


def vaddr_to_offset(loads: list[tuple[int, int, int]], value: int) -> int | None:
    for file_offset, virtual_address, file_size in loads:
        if virtual_address <= value < virtual_address + file_size:
            return file_offset + value - virtual_address
    return None


def c_string(raw: bytes, offset: int) -> str:
    if offset < 0 or offset >= len(raw):
        return ""
    end = raw.find(b"\0", offset)
    if end < 0:
        end = len(raw)
    return raw[offset:end].decode("utf-8", "replace")


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("path")
    parser.add_argument("--out", required=True)
    args = parser.parse_args()

    raw = open(args.path, "rb").read()
    if raw[:4] != b"\x7fELF" or raw[4:6] != b"\x02\x01":
        raise SystemExit("not ELF64 little-endian")
    header = struct.unpack_from("<16sHHIQQQIHHHHHH", raw, 0)
    phoff, phentsize, phnum = header[5], header[9], header[10]
    loads: list[tuple[int, int, int]] = []
    dynamic: tuple[int, int] | None = None
    for index in range(phnum):
        item = struct.unpack_from("<IIQQQQQQ", raw, phoff + index * phentsize)
        p_type, _, p_offset, p_vaddr, _, p_filesz, _, _ = item
        if p_type == PT_LOAD:
            loads.append((p_offset, p_vaddr, p_filesz))
        elif p_type == PT_DYNAMIC:
            dynamic = (p_offset, p_filesz)

    tags: dict[int, int] = {}
    if dynamic:
        start, size = dynamic
        for offset in range(start, start + size, 16):
            tag, value = struct.unpack_from("<QQ", raw, offset)
            if tag == DT_NULL:
                break
            tags[tag] = value

    str_offset = vaddr_to_offset(loads, tags.get(DT_STRTAB, 0))
    sym_offset = vaddr_to_offset(loads, tags.get(DT_SYMTAB, 0))
    if str_offset is None or sym_offset is None:
        raise SystemExit("dynamic string/symbol table not file-backed")

    symbol_count_reported = 0
    hash_offset = vaddr_to_offset(loads, tags.get(DT_HASH, 0))
    if hash_offset is not None:
        _, symbol_count_reported = struct.unpack_from("<II", raw, hash_offset)
    syment = tags.get(DT_SYMENT, 24)
    if syment <= 0:
        raise SystemExit("invalid dynamic symbol entry size")
    available_symbol_count = max(0, (len(raw) - sym_offset) // syment)
    symbol_count = min(symbol_count_reported, available_symbol_count)
    symbols: list[dict[str, Any]] = []
    for index in range(symbol_count):
        offset = sym_offset + index * syment
        st_name, info, other, shndx, value, size = struct.unpack_from(
            "<IBBHQQ", raw, offset
        )
        symbols.append(
            {
                "index": index,
                "name": c_string(raw, str_offset + st_name),
                "value": hex(value),
                "size": size,
                "info": info,
                "other": other,
                "shndx": shndx,
                "undefined": shndx == 0,
            }
        )

    def relocations(tag_address: int, tag_size: int, label: str) -> list[dict[str, Any]]:
        address = tags.get(tag_address)
        size = tags.get(tag_size, 0)
        offset = vaddr_to_offset(loads, address or 0)
        if address is None or offset is None:
            return []
        result = []
        for index in range(size // 24):
            r_offset, r_info, addend = struct.unpack_from("<QQq", raw, offset + index * 24)
            symbol_index = r_info >> 32
            result.append(
                {
                    "table": label,
                    "index": index,
                    "offset": hex(r_offset),
                    "type": r_info & 0xFFFFFFFF,
                    "symbol_index": symbol_index,
                    "symbol": symbols[symbol_index]["name"] if symbol_index < len(symbols) else "",
                    "addend": addend,
                }
            )
        return result

    relocs = relocations(DT_JMPREL, DT_PLTRELSZ, "jmprel")
    relocs.extend(relocations(DT_RELA, DT_RELASZ, "rela"))
    result = {
        "path": args.path,
        "dynamic_tags": {str(key): hex(value) for key, value in tags.items()},
        "dynsym_count_reported": symbol_count_reported,
        "dynsym_count": symbol_count,
        "dynsym_truncated_to_file": symbol_count < symbol_count_reported,
        "undefined_symbols": [item for item in symbols if item["undefined"]],
        "relocations": relocs,
    }
    with open(args.out, "w", encoding="utf-8") as stream:
        json.dump(result, stream, ensure_ascii=False, indent=2)
    print(json.dumps({"dynsym_count": symbol_count, "relocations": len(relocs)}, ensure_ascii=False))
    print("[+] wrote " + args.out)


if __name__ == "__main__":
    main()
