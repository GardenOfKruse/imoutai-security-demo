#!/usr/bin/env python3
"""Read one app-owned file through run-as and save it as a local evidence artifact."""

import argparse
import base64
import hashlib
import json
import os
import subprocess
from pathlib import Path


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--adb", required=True)
    parser.add_argument("--serial", required=True)
    parser.add_argument("--remote", required=True)
    parser.add_argument("--out", required=True)
    parser.add_argument("--package", default="com.moutai.mall")
    parser.add_argument(
        "--proc-root-pid",
        type=int,
        help="read through /proc/<pid>/root using the root shell instead of run-as",
    )
    args = parser.parse_args()

    out_path = Path(args.out)
    if out_path.exists():
        raise SystemExit(f"refusing to overwrite existing file: {out_path}")
    out_path.parent.mkdir(parents=True, exist_ok=True)

    if args.proc_root_pid is not None:
        if not args.remote.startswith("/"):
            raise SystemExit("--remote must be an absolute path")
        proc_path = f"/proc/{args.proc_root_pid}/root{args.remote}"
        cmd = [
            args.adb,
            "-s",
            args.serial,
            "exec-out",
            "su",
            "-c",
            f"base64 {proc_path}",
        ]
    else:
        cmd = [
            args.adb,
            "-s",
            args.serial,
            "exec-out",
            "run-as",
            args.package,
            "cat",
            args.remote,
        ]
    proc = subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    raw_output = proc.stdout.read()
    if args.proc_root_pid is not None:
        try:
            data = base64.b64decode(raw_output, validate=False)
        except (ValueError, base64.binascii.Error) as exc:
            raise SystemExit(f"base64 decode failed: {exc}") from exc
    else:
        data = raw_output

    digest = hashlib.sha256()
    size = 0
    with out_path.open("wb") as handle:
        handle.write(data)
        digest.update(data)
        size = len(data)
    stderr = proc.stderr.read().decode("utf-8", errors="replace")
    rc = proc.wait()
    if rc != 0:
        try:
            out_path.unlink()
        except FileNotFoundError:
            pass
        raise SystemExit(f"adb/run-as failed rc={rc}: {stderr.strip()}")

    record = {
        "package": args.package,
        "remote": args.remote,
        "local": str(out_path),
        "size": size,
        "sha256": digest.hexdigest(),
        "command": cmd,
    }
    print(json.dumps(record, ensure_ascii=False, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
