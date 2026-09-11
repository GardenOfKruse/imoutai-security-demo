"""Offline analysis of a /proc/<pid>/maps snapshot against dump-mem2.sh rules."""
from __future__ import annotations

import argparse
from pathlib import Path


def parse(path: Path):
    for line_no, line in enumerate(path.read_text(errors="replace").splitlines(), 1):
        parts = line.split(None, 5)
        if len(parts) < 5:
            continue
        span, prot, offset, dev, inode = parts[:5]
        label = parts[5].strip() if len(parts) == 6 else ""
        start, end = (int(x, 16) for x in span.split("-", 1))
        yield {
            "line": line_no,
            "start": start,
            "end": end,
            "size": end - start,
            "prot": prot,
            "offset": offset,
            "dev": dev,
            "inode": inode,
            "label": label,
        }


def would_v2_capture(row):
    if row["prot"] not in {"r--p", "rw-p"}:
        return False
    first = row["label"].split(None, 1)[0] if row["label"] else ""
    return (
        (not first or first.lower().startswith("[anon") or first.startswith("[")
         or first.startswith("/memfd:"))
        and 65536 <= row["size"] <= 1024 * 1024 * 1024
    )


def rank(row):
    label = row["label"]
    if "com.moutai.mall" in label:
        return 100
    if "base.apk" in label:
        return 95
    if label.startswith("/memfd:"):
        return 90
    if label.startswith("[anon:dalvik"):
        return 85
    if label in {"[heap]", "[stack]"}:
        return 70
    if "vdex" in label or "oat" in label:
        return 30
    return 0


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("maps", type=Path)
    ap.add_argument("--min-size", type=int, default=4096)
    ap.add_argument("--all-missed", action="store_true")
    ap.add_argument("--out", type=Path, default=None)
    args = ap.parse_args()
    rows = list(parse(args.maps))
    missed = [r for r in rows if not would_v2_capture(r)]
    candidates = [r for r in missed if r["size"] >= args.min_size and rank(r) > 0]
    output = []
    output.append(f"total_regions={len(rows)} v2_would_capture={len(rows)-len(missed)} missed={len(missed)}")
    output.append("rank size_mib prot range label")
    for r in sorted(candidates, key=lambda x: (-rank(x), -x["size"], x["start"])):
        output.append(f"{rank(r):4d} {r['size']/1048576:8.2f} {r['prot']:4s} "
                      f"{r['start']:x}-{r['end']:x} {r['label'] or '<anonymous>'}")
    if args.all_missed:
        output.append("--- every missed region (stable line-numbered form) ---")
        for r in missed:
            output.append(f"line={r['line']} size={r['size']} prot={r['prot']} "
                          f"range={r['start']:x}-{r['end']:x} label={r['label'] or '<anonymous>'}")
    text = "\n".join(output) + "\n"
    if args.out:
        args.out.parent.mkdir(parents=True, exist_ok=True)
        args.out.write_text(text, encoding="utf-8")
    print(text, end="")


if __name__ == "__main__":
    main()
