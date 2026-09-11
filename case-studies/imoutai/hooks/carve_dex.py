"""
carve_dex.py — 从内存快照（/proc/PID/mem 匿名段 dump）里离线切割 dex

用法:
  python carve_dex.py <memdump_dir> [--out <dir>]
  输入: dump-list.txt + NNN.gz（gzip 自动解压）

校验: dex\\n 魔数 + header_size==0x70 + endian_tag + map_off 边界 + file_size；
      adler32 checksum 仅作标记（VMP/壳改动可致不匹配，不作硬过滤）。
输出: <out>/dex_XXX_<size>.dex + manifest.json
"""
import argparse
import hashlib
import json
import os
import struct
import zlib

MAGIC = b"dex\n"


def validate(hay: bytes, off: int, end: int):
    """在 off 处尝试解析 dex header，返回 (size, info) 或 None"""
    if off + 0x70 > end:
        return None
    hdr = hay[off:off + 0x70]
    size = struct.unpack_from("<I", hdr, 0x20)[0]
    header_size = struct.unpack_from("<I", hdr, 0x24)[0]
    endian = struct.unpack_from("<I", hdr, 0x28)[0]
    map_off = struct.unpack_from("<I", hdr, 0x34)[0]
    string_ids_size = struct.unpack_from("<I", hdr, 0x38)[0]
    string_ids_off = struct.unpack_from("<I", hdr, 0x3C)[0]
    if header_size != 0x70 or endian != 0x12345678:
        return None
    if size < 0x70 or size > 200 * 1024 * 1024:
        return None
    if off + size > end:
        return None
    if map_off < 0x70 or map_off >= size:
        return None
    if string_ids_off and (string_ids_off < 0x70 or string_ids_off >= size):
        return None
    if string_ids_size == 0:
        return None
    body = hay[off:off + size]
    sum_stored = struct.unpack_from("<I", hdr, 0x08)[0]
    sum_calc = zlib.adler32(body[12:]) & 0xFFFFFFFF
    return size, {
        "map_off": map_off,
        "string_ids": string_ids_size,
        "checksum_ok": sum_stored == sum_calc,
    }


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("memdump_dir")
    ap.add_argument("--out", default=None)
    args = ap.parse_args()
    out = args.out or os.path.join(args.memdump_dir, "carved")
    os.makedirs(out, exist_ok=True)

    dlist = os.path.join(args.memdump_dir, "dump-list.txt")
    regions = {}
    if os.path.exists(dlist):
        for line in open(dlist, encoding="utf-8", errors="replace"):
            parts = line.split()
            if len(parts) >= 4:
                regions[parts[0]] = " ".join(parts[1:])

    seen_md5 = {}
    manifest = []
    idx = 0
    total_dex_bytes = 0
    for name in sorted(os.listdir(args.memdump_dir)):
        if not (name.endswith(".gz") or name.endswith(".bin")):
            continue
        path = os.path.join(args.memdump_dir, name)
        raw = open(path, "rb").read()
        if name.endswith(".gz"):
            try:
                import gzip
                raw = gzip.decompress(raw)
            except Exception as e:
                print(f"[!] {name}: gzip fail {e}")
                continue
        hits = 0
        pos = 0
        while True:
            off = raw.find(MAGIC, pos)
            if off < 0:
                break
            pos = off + 1
            r = validate(raw, off, len(raw))
            if not r:
                continue
            size, info = r
            body = raw[off:off + size]
            md5 = hashlib.md5(body).hexdigest()
            hits += 1
            if md5 in seen_md5:
                print(f"  [=] {name}+0x{off:x}: dup of {seen_md5[md5]}")
                continue
            seen_md5[md5] = f"{name}+0x{off:x}"
            fn = f"dex_{idx:03d}_{size}.dex"
            with open(os.path.join(out, fn), "wb") as f:
                f.write(body)
            total_dex_bytes += size
            rec = {"file": fn, "src": name, "offset": hex(off), "size": size,
                   "md5": md5, **info, "region": regions.get(name.split(".")[0], "?")}
            manifest.append(rec)
            print(f"  [+] {fn}  from {name}+0x{off:x}  strings={info['string_ids']} "
                  f"chksum_ok={info['checksum_ok']}")
            idx += 1
        if hits:
            print(f"[{name}] {hits} dex found ({len(raw)} bytes)")

    with open(os.path.join(out, "manifest.json"), "w", encoding="utf-8") as f:
        json.dump({"count": len(manifest), "total_bytes": total_dex_bytes,
                   "dexes": manifest}, f, ensure_ascii=False, indent=2)
    print(f"[+] done: {len(manifest)} unique dex, {total_dex_bytes} bytes -> {out}")


if __name__ == "__main__":
    main()
