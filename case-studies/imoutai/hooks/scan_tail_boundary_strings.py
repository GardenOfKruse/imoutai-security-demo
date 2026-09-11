#!/usr/bin/env python3
"""Offline, redacted scan for URL/domain-like strings in classes.dex tail."""
from __future__ import annotations

import argparse
import hashlib
import re
import zipfile
from pathlib import Path


PRINTABLE = re.compile(rb"[ -~]{8,}")
URL = re.compile(rb"https?://[A-Za-z0-9-]+(?:\.[A-Za-z0-9-]+)+(?:[A-Za-z0-9._~:/?#\[\]@!$&'()*+,;=%-]*)", re.I)
DOMAIN = re.compile(rb"(?:[A-Za-z0-9-]+\.)+(?:com|cn|net|io|org|cc|top)(?::[0-9]+)?", re.I)
WORDS = (b"login", b"signature", b"sign", b"hmac", b"nonce", b"token", b"authorization", b"account")


def digest(value: bytes) -> str:
    return hashlib.sha256(value).hexdigest()[:16]


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--apk", type=Path, required=True)
    parser.add_argument("--tail-start", type=lambda value: int(value, 0), default=0x598C)
    args = parser.parse_args()

    with zipfile.ZipFile(args.apk) as archive:
        data = archive.read("classes.dex")
    tail = data[args.tail_start :]
    print(f"tail_start=0x{args.tail_start:x} tail_size={len(tail)}")
    print(f"tail_sha256={hashlib.sha256(tail).hexdigest()}")

    runs = list(PRINTABLE.finditer(tail))
    urls = list(URL.finditer(tail))
    domains = []
    for run in runs:
        domains.extend(DOMAIN.finditer(tail, run.start(), run.end()))
    domains.sort(key=lambda match: match.start())
    print(f"printable_runs_ge8={len(runs)} url_like_count={len(urls)} domain_like_count={len(domains)}")
    print("-- url-like redacted inventory --")
    for match in urls[:128]:
        value = match.group()
        print(f"offset=0x{match.start():x} length={len(value)} sha256_prefix={digest(value)}")
    print("-- domain-like redacted inventory --")
    for match in domains[:256]:
        value = match.group()
        print(f"offset=0x{match.start():x} length={len(value)} sha256_prefix={digest(value)}")
    print("-- keyword offsets --")
    lowered = tail.lower()
    for word in WORDS:
        offsets = []
        start = 0
        while True:
            pos = lowered.find(word, start)
            if pos < 0:
                break
            offsets.append(pos)
            start = pos + 1
        print(f"word={word.decode()} count={len(offsets)} offsets={[hex(x) for x in offsets[:128]]}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
