#!/usr/bin/env python3
'''Offline reconstruction of the hidden ELF stage used by libDexHelper.'''
from __future__ import annotations

import argparse
import hashlib
import json
import struct
from pathlib import Path
from typing import Any

ENCRYPTED_START = 0x8000
TRAILER_OFFSET = 0x1281ED
TRAILER_SIZE = 20
KEY_SIZE = 16
ELF_HEADER = struct.Struct("<16sHHIQQQIHHHHHH")
PROGRAM_HEADER = struct.Struct("<IIQQQQQQ")


def sha256(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()


def rc4_crypt(data: bytes, key: bytes, stream_skip: int = 0) -> bytes:
    if len(key) != KEY_SIZE:
        raise ValueError("unexpected RC4 key length")
    state = list(range(256))
    j = 0
    for i in range(256):
        j = (j + state[i] + key[i % len(key)]) & 0xFF
        state[i], state[j] = state[j], state[i]

    i = 0
    j = 0

    def next_byte() -> int:
        nonlocal i, j
        i = (i + 1) & 0xFF
        j = (j + state[i]) & 0xFF
        state[i], state[j] = state[j], state[i]
        return state[(state[i] + state[j]) & 0xFF]

    for _ in range(stream_skip):
        next_byte()
    return bytes(byte ^ next_byte() for byte in data)


def parse_elf(data: bytes) -> dict[str, Any]:
    result: dict[str, Any] = {
        "actual_size": len(data),
        "sha256": sha256(data),
        "magic": data[:4].hex(),
        "is_elf64_le": False,
    }
    if len(data) < ELF_HEADER.size:
        result["error"] = "short ELF header"
        return result
    fields = ELF_HEADER.unpack_from(data, 0)
    ident, e_type, e_machine, e_version, e_entry, e_phoff, e_shoff, e_flags, e_ehsize, e_phentsize, e_phnum, e_shentsize, e_shnum, e_shstrndx = fields
    if ident[:4] != b"\x7fELF" or ident[4] != 2 or ident[5] != 1:
        result["error"] = "not ELF64 little-endian"
        return result
    result["is_elf64_le"] = True
    result["header"] = {
        "type": e_type,
        "machine": e_machine,
        "version": e_version,
        "entry": e_entry,
        "phoff": e_phoff,
        "shoff": e_shoff,
        "flags": e_flags,
        "ehsize": e_ehsize,
        "phentsize": e_phentsize,
        "phnum": e_phnum,
        "shentsize": e_shentsize,
        "shnum": e_shnum,
        "shstrndx": e_shstrndx,
    }
    programs: list[dict[str, Any]] = []
    phdr_end = e_phoff + e_phentsize * e_phnum
    result["program_headers_within_output"] = phdr_end <= len(data)
    if e_phentsize < PROGRAM_HEADER.size or phdr_end > len(data):
        result["error"] = "program header table is outside output"
        return result
    for index in range(e_phnum):
        offset = e_phoff + index * e_phentsize
        p_type, p_flags, p_offset, p_vaddr, p_paddr, p_filesz, p_memsz, p_align = PROGRAM_HEADER.unpack_from(data, offset)
        programs.append(
            {
                "index": index,
                "type": p_type,
                "flags": p_flags,
                "offset": p_offset,
                "vaddr": p_vaddr,
                "filesz": p_filesz,
                "memsz": p_memsz,
                "align": p_align,
                "file_range_within_output": p_offset + p_filesz <= len(data),
            }
        )
    result["program_headers"] = programs
    result["all_file_ranges_within_output"] = all(item["file_range_within_output"] for item in programs)
    return result


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--input", type=Path, required=True)
    parser.add_argument("--output", type=Path, required=True)
    parser.add_argument("--report", type=Path, required=True)
    args = parser.parse_args()

    source = args.input.read_bytes()
    if TRAILER_OFFSET + TRAILER_SIZE > len(source):
        raise ValueError("input is shorter than expected trailer")
    trailer = source[TRAILER_OFFSET : TRAILER_OFFSET + TRAILER_SIZE]
    key = trailer[:KEY_SIZE]
    encrypted = source[ENCRYPTED_START:TRAILER_OFFSET]
    xor_mask = struct.unpack_from("<I", trailer, KEY_SIZE)[0]
    if xor_mask > 0xFF:
        raise ValueError(f"unexpected XOR mask: {xor_mask:#x}")
    rc4_header = rc4_crypt(encrypted[:64], key)
    decrypted_buffer = bytearray(byte ^ xor_mask for byte in encrypted)
    decrypted_buffer[: len(rc4_header)] = rc4_header
    decrypted = bytes(decrypted_buffer)
    validation = parse_elf(decrypted)

    report: dict[str, Any] = {
        "input": str(args.input),
        "input_size": len(source),
        "input_sha256": sha256(source),
        "encrypted_range": {
            "start": ENCRYPTED_START,
            "end_exclusive": TRAILER_OFFSET,
            "size": len(encrypted),
            "sha256": sha256(encrypted),
        },
        "trailer": {
            "offset": TRAILER_OFFSET,
            "size": TRAILER_SIZE,
            "key_length": len(key),
            "key_sha256": sha256(key),
            "xor_mask": xor_mask,
            "trailing_dword_hex": trailer[16:20].hex(),
        },
        "reconstruction": {
            "body_transform": "single-byte XOR",
            "body_xor_mask": xor_mask,
            "rc4_header_size": len(rc4_header),
            "rc4_header_sha256": sha256(rc4_header),
        },
        "decrypted_stage": validation,
        "output": str(args.output),
    }
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_bytes(decrypted)
    args.report.parent.mkdir(parents=True, exist_ok=True)
    args.report.write_text(json.dumps(report, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
    print(json.dumps({
        "output": str(args.output),
        "report": str(args.report),
        "decrypted_size": len(decrypted),
        "decrypted_sha256": sha256(decrypted),
        "magic": validation["magic"],
        "is_elf64_le": validation["is_elf64_le"],
        "program_headers_within_output": validation.get("program_headers_within_output"),
        "all_file_ranges_within_output": validation.get("all_file_ranges_within_output"),
    }, ensure_ascii=False))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
