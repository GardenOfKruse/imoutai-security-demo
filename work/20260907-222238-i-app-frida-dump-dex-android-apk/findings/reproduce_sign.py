#!/usr/bin/env python
"""
reproduce_sign.py — i茅台 v1.9.12 短信验证码签名离线复现验证（2026-09-11）

Finding（findings/login-signature.md F1）:
    GetVerifyCodeRequest.md5 = MD5( deviceKey + mobile + timestamp )
    deviceKey 为设备绑定 32-hex 常量（本设备 = 2af72f100c356273d46284f6fd1dfc08）

用法:
    python reproduce_sign.py                 # 用随附样本跑离线比对
    python reproduce_sign.py <samples.json>  # 用指定 annot 采样产物比对
    python reproduce_sign.py --key <hex> --mobile <m> --ts <ms>   # 计算单个签名

数据来源: extract/obs-annot{5c,6b,7}-20260911/samples.json（本地反射采样，未发任何网络请求）
"""
import hashlib
import json
import os
import sys

DEVICE_KEY = "2af72f100c356273d46284f6fd1dfc08"
CASE = os.path.join(os.path.dirname(__file__), "..", "extract")
DEFAULT_SAMPLE_DIRS = [
    "obs-annot5c-20260911",
    "obs-annot6b-20260911",
    "obs-annot7-20260911",
]


def sign(device_key: str, mobile: str, ts: str) -> str:
    return hashlib.md5((device_key + mobile + ts).encode()).hexdigest()


def load_samples(path: str):
    d = json.load(open(path, encoding="utf-8"))
    out = []
    for s in d.get("vcode", []):
        if "_digests" in s:
            for rec in s["_digests"]:
                if rec.get("kind") == "update":
                    for p in rec.get("args", []):
                        if "," in p:
                            bs = bytes(int(x) & 0xff for x in p.split(","))
                            if bs.decode("latin1", "replace").startswith(s["md5"][:0] or "2af72f"):
                                out.append({
                                    "mobile": s["mobile"], "timestamp": s["timestamp"],
                                    "md5": s["md5"], "raw_input": bs.decode("latin1"),
                                })
    return out or [
        {"mobile": s["mobile"], "timestamp": s["timestamp"], "md5": s["md5"]}
        for s in d.get("vcode", [])
    ]


def main():
    if "--key" in sys.argv:
        i = sys.argv.index("--key")
        key = sys.argv[i + 1]
        mob = sys.argv[sys.argv.index("--mobile") + 1]
        ts = sys.argv[sys.argv.index("--ts") + 1]
        print(sign(key, mob, ts))
        return

    path = sys.argv[1] if len(sys.argv) > 1 else None
    samples = []
    if path:
        samples = load_samples(path)
    else:
        for d in DEFAULT_SAMPLE_DIRS:
            p = os.path.join(CASE, d, "samples.json")
            if os.path.exists(p):
                samples += load_samples(p)
    if not samples:
        print("no samples found")
        return

    # 设备 key 自动恢复：raw_input 里 = mobile 前面的部分
    key_votes = {}
    for s in samples:
        raw = s.get("raw_input")
        if raw and raw.endswith(s["mobile"] + s["timestamp"]):
            key_votes[raw[: len(raw) - len(s["mobile"]) - len(s["timestamp"])]] = key_votes.get(
                raw[: len(raw) - len(s["mobile"]) - len(s["timestamp"])], 0) + 1
    key = max(key_votes, key=key_votes.get) if key_votes else DEVICE_KEY
    print(f"[*] deviceKey = {key} (votes: {key_votes or 'default'})")

    ok = 0
    for s in samples:
        got = sign(key, s["mobile"], s["timestamp"])
        passed = got == s["md5"]
        ok += passed
        print(f"  {'PASS' if passed else 'FAIL'}  mobile={s['mobile']} ts={s['timestamp']} "
              f"md5={s['md5'][:16]}... got={got[:16]}...")
    print(f"--- {ok}/{len(samples)} PASS  formula: MD5(deviceKey + mobile + timestamp)")


if __name__ == "__main__":
    main()
