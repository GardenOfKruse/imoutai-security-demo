"""
dump-dex-driver2.py — spawn i茅台 + dump-dex-hook.js（v2: 捕获 dex 字节 + 阻断风控自杀）

用法（PC, 先: adb forward tcp:8899 tcp:8899）:
  python dump-dex-driver2.py [--wait 40] [--out <dir>]

产出:
  <out>/captured/dex_XXX_<size>.dex    # hook 捕获的字节缓冲
  <out>/fallback/dex_XXX_<size>.dex    # 兜底内存扫描结果
  <out>/session-stats.json             # pthread 压制统计 / 捕获计数
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
JS = os.path.join(HERE, "dump-dex-hook.js")
DEFAULT_OUT = os.path.normpath(os.path.join(
    HERE, "..", "..", "..",
    "work", "20260907-222238-i-app-frida-dump-dex-android-apk", "extract", "dexes-hook"))
CHUNK = 1024 * 1024


def exports_of(script):
    try:
        return script.exports_sync
    except AttributeError:
        return script.exports


class Collector:
    def __init__(self, outdir):
        self.capdir = os.path.join(outdir, "captured")
        self.fbdir = os.path.join(outdir, "fallback")
        os.makedirs(self.capdir, exist_ok=True)
        os.makedirs(self.fbdir, exist_ok=True)
        self.md5_seen = {}
        self.count = 0
        self.events = []

    def save(self, data: bytes, src: str, magic: bool, sub: str):
        md5 = hashlib.md5(data).hexdigest()
        if md5 in self.md5_seen:
            print(f"  [=] dup ({src}) of {self.md5_seen[md5]}, skip {len(data)}B")
            return None
        self.md5_seen[md5] = src
        name = f"dex_{self.count:03d}_{len(data)}.bin"
        with open(os.path.join(sub, name), "wb") as f:
            f.write(data)
        self.count += 1
        print(f"  [+] saved {sub.split(os.sep)[-1]}/{name} ({len(data)}B, magic={magic}, md5={md5[:10]}) src={src}")
        return name


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--wait", type=float, default=40.0)
    ap.add_argument("--out", default=DEFAULT_OUT)
    ap.add_argument("--js", default=JS, help="frida 脚本（frida 17 需 browserify/esbuild 打包 java-bridge）")
    args = ap.parse_args()

    with open(args.js, "r", encoding="utf-8") as f:
        source = f.read()

    col = Collector(args.out)
    print(f"[*] connect {HOST}")
    dev = frida.get_device_manager().add_remote_device(HOST)
    print(f"[*] spawn {PKG}")
    pid = dev.spawn([PKG])
    session = dev.attach(pid)

    def on_message(m, data):
        t = m.get("type")
        if t == "send":
            p = m.get("payload") or {}
            if p.get("type") == "dex" and data:
                col.save(bytes(data), p.get("src", "?"), p.get("magic", False), col.capdir)
                col.events.append({"kind": "dex", **{k: p.get(k) for k in ("src", "size", "magic")}})
            else:
                print(f"  [js-send] {p}")
                col.events.append({"kind": "send", "payload": p})
        elif t == "log":
            print(f"  {m.get('payload')}")
        else:
            print(f"  [js:{t}] {m}")

    script = session.create_script(source)

    def on_detached(reason, *a):
        print(f"[!] session detached @ {time.time()-t0:.1f}s: reason={reason} {a}")

    def on_destroyed():
        print(f"[!] script destroyed @ {time.time()-t0:.1f}s")

    t0 = time.time()
    session.on("detached", on_detached)
    script.on("destroyed", on_destroyed)
    script.on("message", on_message)
    script.load()
    dev.resume(pid)
    print(f"[+] resumed pid={pid}; hook 捕获窗口 {args.wait:.0f}s（自动阻断 System.exit）")

    end = t0 + args.wait
    while time.time() < end:
        time.sleep(2)
        try:
            dev.get_process(pid)
        except Exception:
            print(f"[!] process {pid} gone @ {time.time()-t0:.1f}s")
            break

    stats = None
    try:
        stats = exports_of(script).get_stats()
        print(f"[*] stats: {json.dumps(stats, ensure_ascii=False)}")
    except Exception as e:
        print(f"[-] getStats failed: {e}")

    # 兜底: 内存魔数扫描 + 拉取
    try:
        dexes = exports_of(script).list_dexes()
        print(f"[*] fallback scan: {len(dexes)} candidates")
        for d in dexes:
            key = f"{d['base']}_{d['size']}"
            if key in col.md5_seen:
                continue
            buf = bytearray()
            off = 0
            try:
                while off < d["size"]:
                    chunk = exports_of(script).read_dex(d["base"], d["size"], off, CHUNK)
                    b = bytes(chunk)
                    buf += b
                    off += len(b)
            except Exception as e:
                print(f"  [-] pull fail {key}: {e}")
                continue
            col.save(bytes(buf), f"memscan@{d['base']}", True, col.fbdir)
    except Exception as e:
        print(f"[-] fallback scan failed: {e}")

    with open(os.path.join(args.out, "session-stats.json"), "w", encoding="utf-8") as f:
        json.dump({"stats": stats, "events": col.events, "ts": time.strftime("%F %T")},
                  f, ensure_ascii=False, indent=2)
    print(f"[+] done -> {args.out} (captured+fallback={col.count})")


if __name__ == "__main__":
    main()
