"""
dump-dex-driver.py — spawn i茅台（com.moutai.mall），等初始化后 rpc 拉取内存中的业务 dex

用法（PC, 需先: adb forward tcp:8899 tcp:8899 且设备端 fs 已运行）:
  python dump-dex-driver.py [--wait 25] [--rounds 3] [--out <dir>]

流程:
  1. spawn 注入 dump-dex-mem.js（含 pthread_create 压制），resume
  2. 分轮扫描（间隔 --wait/rounds 秒），逐 dex 分块拉取，内容 md5 去重
  3. 落盘 work/<case>/extract/dexes/ + manifest.json

合规: offline-sample 授权样本，dump 数据仅本地留存分析
"""
import argparse
import hashlib
import json
import os
import time

import frida

HOST = "127.0.0.1:8899"
PKG = "com.moutai.mall"
HERE = os.path.dirname(os.path.abspath(__file__))
JS = os.path.join(HERE, "dump-dex-mem.js")
DEFAULT_OUT = os.path.normpath(os.path.join(
    HERE, "..", "..", "..",
    "work", "20260907-222238-i-app-frida-dump-dex-android-apk", "extract", "dexes"))
CHUNK = 1024 * 1024  # 1MB


def exports_of(script):
    try:
        return script.exports_sync
    except AttributeError:  # 老版绑定
        return script.exports


def pull_dex(exp, d, out_path):
    size = d["size"]
    with open(out_path, "wb") as f:
        off = 0
        while off < size:
            buf = exp.read_dex(d["base"], size, off, CHUNK)
            if buf is None:
                raise RuntimeError(f"read_dex returned None @ {off}/{size}")
            f.write(bytes(buf))
            off += len(bytes(buf))
    return os.path.getsize(out_path)


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--wait", type=float, default=25.0, help="resume 后首次扫描前等待秒数")
    ap.add_argument("--rounds", type=int, default=3, help="扫描轮数")
    ap.add_argument("--out", default=DEFAULT_OUT)
    args = ap.parse_args()

    os.makedirs(args.out, exist_ok=True)
    with open(JS, "r", encoding="utf-8") as f:
        source = f.read()

    print(f"[*] connect {HOST}")
    dev = frida.get_device_manager().add_remote_device(HOST)
    print(f"[*] spawn {PKG}")
    pid = dev.spawn([PKG])
    session = dev.attach(pid)
    script = session.create_script(source)
    script.on("message", lambda m, data: print(f"  [js] {m.get('description') or m}"))
    script.load()
    dev.resume(pid)
    print(f"[+] resumed pid={pid}, wait {args.wait:.0f}s for app init")

    interval = max(2.0, args.wait / max(1, args.rounds))
    seen_keys = set()
    md5_seen = {}
    manifest = []
    idx = 0

    for rnd in range(1, args.rounds + 1):
        if rnd > 1:
            time.sleep(interval)
        try:
            dexes = exports_of(script).list_dexes()
        except Exception as e:
            print(f"[-] round {rnd}: rpc failed: {e}")
            break
        print(f"[round {rnd}] candidates={len(dexes)}")
        for d in dexes:
            key = f"{d['base']}_{d['size']}"
            if key in seen_keys:
                continue
            seen_keys.add(key)
            tmp = os.path.join(args.out, f"_tmp_{idx:03d}.bin")
            try:
                n = pull_dex(exp := exports_of(script), d, tmp)
            except Exception as e:
                print(f"  [-] pull fail {key}: {e}")
                if os.path.exists(tmp):
                    os.remove(tmp)
                continue
            md5 = hashlib.md5(open(tmp, "rb").read()).hexdigest()
            if md5 in md5_seen:
                os.remove(tmp)
                print(f"  [=] dup of {md5_seen[md5]}, skipped ({n} bytes)")
                continue
            md5_seen[md5] = key
            final = os.path.join(args.out, f"dex_{idx:03d}_{n}.dex")
            os.rename(tmp, final)
            idx += 1
            print(f"  [+] saved {os.path.basename(final)}  base={d['base']} prot={d['protection']} md5={md5[:12]}")
            manifest.append({"file": os.path.basename(final), "base": d["base"],
                             "size": n, "protection": d["protection"], "md5": md5})

    with open(os.path.join(args.out, "manifest.json"), "w", encoding="utf-8") as f:
        json.dump({"pkg": PKG, "ts": time.strftime("%Y-%m-%d %H:%M:%S"),
                   "count": len(manifest), "dexes": manifest}, f, ensure_ascii=False, indent=2)
    print(f"[+] done: {len(manifest)} unique dexes -> {args.out}")


if __name__ == "__main__":
    main()
