"""Minimal read-only ELF64 dynamic symbol parser for local evidence."""
import argparse
import json
import struct

PT_LOAD = 1
PT_DYNAMIC = 2
DT_NULL = 0
DT_STRTAB = 5
DT_SYMTAB = 6
DT_STRSZ = 10
DT_SYMENT = 11
DT_HASH = 4


def vaddr_to_off(loads, va):
    for p_offset, p_vaddr, p_filesz in loads:
        if p_vaddr <= va < p_vaddr + p_filesz:
            return p_offset + (va - p_vaddr)
    return None


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("path")
    ap.add_argument("--out", required=True)
    args = ap.parse_args()
    raw = open(args.path, "rb").read()
    if raw[:4] != b"\x7fELF" or raw[4] != 2 or raw[5] != 1:
        raise SystemExit("not ELF64 little-endian")
    _, _, _, _, _, phoff, _, _, _, _, phentsize, phnum, *_ = struct.unpack_from("<16sHHIQQQIHHHHHH", raw, 0)
    loads = []
    dynamic = None
    for i in range(phnum):
        p = struct.unpack_from("<IIQQQQQQ", raw, phoff + i * phentsize)
        p_type, _, p_offset, p_vaddr, _, p_filesz, _, _ = p
        if p_type == PT_LOAD:
            loads.append((p_offset, p_vaddr, p_filesz))
        elif p_type == PT_DYNAMIC:
            dynamic = (p_offset, p_filesz)
    tags = {}
    if dynamic:
        for off in range(dynamic[0], dynamic[0] + dynamic[1], 16):
            tag, val = struct.unpack_from("<QQ", raw, off)
            if tag == DT_NULL:
                break
            tags[tag] = val
    str_off = vaddr_to_off(loads, tags.get(DT_STRTAB, 0))
    sym_off = vaddr_to_off(loads, tags.get(DT_SYMTAB, 0))
    count = 0
    if DT_HASH in tags:
        hash_off = vaddr_to_off(loads, tags[DT_HASH])
        if hash_off is not None:
            _, count = struct.unpack_from("<II", raw, hash_off)
    syment = tags.get(DT_SYMENT, 24)
    symbols = []
    all_symbols = []
    if str_off is not None and sym_off is not None and count:
        for i in range(count):
            off = sym_off + i * syment
            st_name, info, other, shndx, value, size = struct.unpack_from("<IBBHQQ", raw, off)
            end = raw.find(b"\0", str_off + st_name)
            name = raw[str_off + st_name:end].decode("utf-8", "replace") if end >= 0 else ""
            all_symbols.append({"name": name, "value": hex(value), "size": size,
                                "info": info, "shndx": shndx, "undefined": shndx == 0})
            if name and ("JNI" in name or "dex" in name.lower() or "load" in name.lower() or "register" in name.lower()):
                symbols.append({"name": name, "value": hex(value), "size": size, "info": info, "shndx": shndx})
    result = {"path": args.path, "dynamic_tags": {str(k): hex(v) for k, v in tags.items()},
              "dynsym_count": count, "undefined_count": sum(x["undefined"] for x in all_symbols),
              "symbols_of_interest": symbols, "all_symbols": all_symbols}
    with open(args.out, "w", encoding="utf-8") as f:
        json.dump(result, f, ensure_ascii=False, indent=2)
    print(json.dumps({"dynsym_count": count, "symbols_of_interest": symbols}, ensure_ascii=False))


if __name__ == "__main__":
    main()
