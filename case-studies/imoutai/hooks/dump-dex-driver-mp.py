"""spawn-gating driver: track com.moutai.mall and its child processes."""
import argparse
import hashlib
import json
import os
import subprocess
import threading
import time

import frida

HOST = "127.0.0.1:8899"
PKG = "com.moutai.mall"
ADB_DEFAULT = r"E:\Android\platform-tools\adb.exe"
SERIAL_DEFAULT = "82e459fc0920"
CHUNK = 1024 * 1024
ACTIVITY_DEFAULT = "com.moutai.mall/.MainActivity"


def exports_of(script):
    try:
        return script.exports_sync
    except AttributeError:
        return script.exports


class Collector:
    def __init__(self, outdir):
        self.outdir = outdir
        self.capdir = os.path.join(outdir, "captured")
        self.fbdir = os.path.join(outdir, "fallback")
        os.makedirs(self.capdir, exist_ok=True)
        os.makedirs(self.fbdir, exist_ok=True)
        self.seen = set()
        self.count = 0
        self.events = []
        self.lock = threading.RLock()

    def save(self, data, src, magic, sub, pid):
        digest = hashlib.md5(data).hexdigest()
        with self.lock:
            if digest in self.seen:
                return False
            self.seen.add(digest)
            name = f"dex_{self.count:03d}_{len(data)}.bin"
            self.count += 1
        with open(os.path.join(sub, name), "wb") as f:
            f.write(data)
        print(f"  [+] pid={pid} {sub.split(os.sep)[-1]}/{name} "
              f"({len(data)}B, magic={magic}, md5={digest[:10]}) src={src}")
        return True


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--wait", type=float, default=35.0)
    ap.add_argument("--js", required=True)
    ap.add_argument("--out", required=True)
    ap.add_argument("--adb", default=ADB_DEFAULT)
    ap.add_argument("--serial", default=SERIAL_DEFAULT)
    ap.add_argument("--activity", default=None,
                    help="启动指定 Activity，例如 com.moutai.mall/.MainActivity")
    ap.add_argument("--dump-native", action="store_true",
                    help="从已注入进程只读采集 libDexHelper.so 内存镜像")
    args = ap.parse_args()
    with open(args.js, "r", encoding="utf-8") as f:
        source = f.read()

    col = Collector(args.out)
    dev = frida.get_device_manager().add_remote_device(HOST)
    sessions = {}
    scripts = {}
    lock = threading.RLock()
    t0 = time.time()

    def attach_one(pid, identifier, resume=False):
        with lock:
            if pid in scripts:
                return
        try:
            session = dev.attach(pid)
            script = session.create_script(source)

            def on_message(m, data, pid=pid, identifier=identifier):
                if m.get("type") == "send":
                    p = m.get("payload") or {}
                    if p.get("type") == "dex" and data:
                        if col.save(bytes(data), p.get("src", "?"),
                                    p.get("magic", False), col.capdir, pid):
                            col.events.append({"kind": "dex", "pid": pid,
                                               "process": identifier,
                                               "src": p.get("src"),
                                               "size": p.get("size"),
                                               "magic": p.get("magic")})
                    else:
                        print(f"  [js-send pid={pid} {identifier}] {p}")
                        col.events.append({"kind": "send", "pid": pid,
                                           "process": identifier, "payload": p})
                elif m.get("type") == "log":
                    print(f"  {m.get('payload')}")
                else:
                    print(f"  [js:{m.get('type')} pid={pid}] {m}")

            def on_detached(reason, *unused, pid=pid, identifier=identifier):
                print(f"[!] detached pid={pid} {identifier} "
                      f"@ {time.time()-t0:.1f}s: {reason}")

            session.on("detached", on_detached)
            script.on("message", on_message)
            script.load()
            with lock:
                sessions[pid] = session
                scripts[pid] = script
            if resume:
                dev.resume(pid)
            print(f"[+] attached pid={pid} {identifier}")
        except Exception as exc:
            print(f"[-] attach pid={pid} {identifier} failed: {exc}")

    def adb_pids():
        try:
            cp = subprocess.run(
                [args.adb, "-s", args.serial, "shell", "pidof", PKG],
                capture_output=True, text=True, timeout=2,
                encoding="utf-8", errors="replace")
            return {int(x) for x in cp.stdout.split() if x.isdigit()}
        except Exception as exc:
            print(f"[-] adb pidof failed: {exc}")
            return set()

    print(f"[*] spawn {PKG} and monitor child processes")
    pid = dev.spawn([PKG])
    attach_one(pid, PKG, resume=True)
    if args.activity:
        subprocess.run(
            [args.adb, "-s", args.serial, "shell", "am", "start", "-n", args.activity],
            capture_output=True, text=True, timeout=8,
            encoding="utf-8", errors="replace")
        print(f"[*] activity requested: {args.activity}")
    end = t0 + args.wait
    while time.time() < end:
        try:
            for child_pid in adb_pids():
                if child_pid != pid:
                    with lock:
                        known = child_pid in scripts
                    if not known:
                        identifier = f"{PKG}:pid-{child_pid}"
                        print(f"[*] child found pid={child_pid} {identifier}")
                        attach_one(child_pid, identifier)
        except Exception as exc:
            print(f"[-] process enumeration failed: {exc}")
        time.sleep(1)

    result = {"stats": {}, "events": col.events, "ts": time.strftime("%F %T")}
    with lock:
        items = list(scripts.items())
    for pid, script in items:
        try:
            result["stats"][str(pid)] = exports_of(script).get_stats()
        except Exception as exc:
            result["stats"][str(pid)] = {"error": str(exc)}
        try:
            dexes = exports_of(script).list_dexes()
            print(f"[*] pid={pid} fallback candidates={len(dexes)}")
            for d in dexes:
                buf = bytearray()
                off = 0
                while off < d["size"]:
                    part = exports_of(script).read_dex(d["base"], d["size"], off, CHUNK)
                    b = bytes(part)
                    if not b:
                        break
                    buf += b
                    off += len(b)
                if len(buf) == d["size"]:
                    col.save(bytes(buf), f"memscan@{d['base']}", True,
                             col.fbdir, pid)
        except Exception as exc:
            print(f"[-] pid={pid} fallback failed: {exc}")
        if args.dump_native:
            try:
                info = exports_of(script).get_native_info()
                if info:
                    natdir = os.path.join(args.out, "native")
                    os.makedirs(natdir, exist_ok=True)
                    path = os.path.join(natdir, f"libDexHelper-runtime-{pid}.so")
                    digest = hashlib.sha256()
                    with open(path, "wb") as out:
                        off = 0
                        while off < int(info["size"]):
                            part_size = min(CHUNK, int(info["size"]) - off)
                            raw = bytes(exports_of(script).read_native(
                                info["base"], int(info["size"]), off, part_size))
                            if len(raw) != part_size:
                                raise RuntimeError(
                                    f"short native read at {off}: {len(raw)} != {part_size}")
                            out.write(raw)
                            digest.update(raw)
                            off += len(raw)
                    result.setdefault("native", {})[str(pid)] = {
                        "path": path, "size": int(info["size"]),
                        "sha256": digest.hexdigest(),
                        "exports": exports_of(script).list_native_exports(),
                    }
                    print(f"[+] pid={pid} native image={path} "
                          f"bytes={info['size']} sha256={digest.hexdigest()}")
                else:
                    print(f"[*] pid={pid} libDexHelper.so not present")
            except Exception as exc:
                result.setdefault("native", {})[str(pid)] = {"error": str(exc)}
                print(f"[-] pid={pid} native dump failed: {exc}")
    result["count"] = col.count
    with open(os.path.join(args.out, "session-stats-mp.json"), "w", encoding="utf-8") as f:
        json.dump(result, f, ensure_ascii=False, indent=2)
    print(f"[+] done -> {args.out} (captured+fallback={col.count}, processes={len(items)})")


if __name__ == "__main__":
    main()
