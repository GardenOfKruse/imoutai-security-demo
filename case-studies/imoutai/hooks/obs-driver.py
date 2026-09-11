"""
obs-driver.py — 观测会话驱动（mp22+）
spawn 注入 dump-dex-hook 编译产物；nativeLoad 实参改写（壳按名加载 libDexHelper 失败的修正）；
不 pre-probe dlopen、不 dump native 映像；收集:
  - dex 捕获（C hooks + live scan） -> <out>/captured/
  - RegisterNatives 记录           -> <out>/regnat.jsonl
  - 事件日志                       -> <out>/events.log
  - 会话结束类清单                 -> <out>/loaded-classes-<pid>.txt
  - :rs 隔离进程出现时机           -> 控制台 + events.log

用法:
  python obs-driver.py --js dump-dex-hook-compiled.js --out <dir> [--wait 25]
"""
import argparse
import hashlib
import json
import os
import re
import subprocess
import threading
import time

import frida

HOST = "127.0.0.1:8899"
PKG = "com.moutai.mall"
ADB = r"E:\Android\platform-tools\adb.exe"
SERIAL = "82e459fc0920"


def adb(*args, timeout=10):
    return subprocess.run([ADB, "-s", SERIAL, *args], capture_output=True,
                          text=True, encoding="utf-8", errors="replace", timeout=timeout)


def exports_of(script):
    try:
        return script.exports_sync
    except AttributeError:
        return script.exports


def prepare_source(path, minimal=False):
    with open(path, "r", encoding="utf-8-sig") as fh:
        source = fh.read()
    if re.search(r"^\s*import\s+", source, re.MULTILINE):
        raise ValueError("--js requires the bundled script; bundle Java bridge before attaching")
    if minimal:
        # Applies to the existing IIFE bundles. No strict-mode marker is needed.
        if "globalThis.MP34_MINIMAL" not in source:
            raise ValueError("--mp34 requires a bundle with the runtime MP34_MINIMAL flag")
        source = "globalThis.MP34_MINIMAL = true;\n" + source
    return source


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--js", required=True)
    ap.add_argument("--out", required=True)
    ap.add_argument("--wait", type=float, default=25.0)
    ap.add_argument("--activity", default="com.moutai.mall/.module.splash.SplashActivity")
    ap.add_argument("--no-activity", action="store_true")
    ap.add_argument("--mp34", action="store_true",
                    help="enable the minimal mp34 observer mode")
    ap.add_argument("--attach-existing", action="store_true",
                    help="start the activity normally, then attach to its pid")
    ap.add_argument("--attach-running", action="store_true",
                    help="attach to the already-running package without restarting it")
    ap.add_argument("--poll-classes", action="store_true",
                    help="save periodic loaded-class snapshots while attached")
    args = ap.parse_args()

    if args.attach_existing and args.attach_running:
        ap.error("choose only one attach mode")
    # Fail before device operations or evidence creation.
    source = prepare_source(args.js, args.mp34)
    if os.path.isdir(args.out) and os.listdir(args.out):
        ap.error("--out must be new or empty; choose a unique directory to preserve evidence")
    os.makedirs(os.path.join(args.out, "captured"), exist_ok=True)
    regnat_path = os.path.join(args.out, "regnat.jsonl")
    events_path = os.path.join(args.out, "events.log")
    events = open(events_path, "w", encoding="utf-8")
    seen_md5 = set()
    regnat_n = 0

    def rec(line):
        print(line, flush=True)
        try:
            events.write(line + "\n")
            events.flush()
        except ValueError:
            pass  # 会话收尾时 events 已关闭

    dev = frida.get_device_manager().add_remote_device(HOST)
    if args.attach_running:
        pids = adb("shell", "pidof", PKG).stdout.split()
        if not pids:
            raise RuntimeError(f"no running pid for {PKG}")
        pid = int(pids[0])
        rec(f"[*] attaching already-running pid={pid}")
    elif args.attach_existing:
        if not args.no_activity:
            adb("shell", "am", "force-stop", PKG)
            adb("shell", "am", "start", "-n", args.activity)
            rec(f"[*] normal start before attach {args.activity}")
        pid = None
        deadline = time.time() + 10.0
        while time.time() < deadline:
            pids = adb("shell", "pidof", PKG).stdout.split()
            if pids:
                pid = int(pids[0])
                break
            time.sleep(0.25)
        if pid is None:
            raise RuntimeError(f"unable to find running pid for {PKG}")
        rec(f"[*] attaching existing pid={pid}")
    else:
        pid = dev.spawn([PKG])
    session = dev.attach(pid)
    script = session.create_script(source)

    t0 = time.time()
    detached = threading.Event()

    def on_detached(reason, *details):
        detached.set()
        rec(f"[!] session detached @ {time.time()-t0:.1f}s reason={reason} {details}")

    session.on("detached", on_detached)
    script.on("destroyed", lambda: rec(f"[!] script destroyed @ {time.time()-t0:.1f}s"))
    script.set_log_handler(lambda level, message: rec(f"[agent:{level}] {message}"))

    def on_message(m, data):
        nonlocal regnat_n
        t = m.get("type")
        if t == "send":
            p = m.get("payload") or {}
            kind = p.get("type")
            if kind == "dex" and data:
                b = bytes(data)
                md5 = hashlib.md5(b).hexdigest()
                if md5 not in seen_md5:
                    seen_md5.add(md5)
                    fn = os.path.join(args.out, "captured",
                                      f"dex_{len(seen_md5):03d}_{len(b)}.bin")
                    with open(fn, "wb") as f:
                        f.write(b)
                    rec(f"[+] DEX captured size={len(b)} magic={p.get('magic')} "
                        f"src={p.get('src')} md5={md5[:12]} -> {os.path.basename(fn)}")
            elif kind == "regnat":
                regnat_n += 1
                with open(regnat_path, "a", encoding="utf-8") as f:
                    f.write(json.dumps(p, ensure_ascii=False) + "\n")
            elif kind == "dcl":
                rec(f"[*] DexClassLoader path={p.get('dexPath')}")
            elif kind == "reflection":
                with open(os.path.join(args.out, "reflection.jsonl"), "a", encoding="utf-8") as f:
                    f.write(json.dumps(p, ensure_ascii=False) + "\n")
            else:
                rec(f"[agent-send] {p}")
        elif t == "log":
            rec(f"[agent] {m.get('payload')}")
        elif t == "error":
            rec(f"[agent-error] {m.get('description')}\n{m.get('stack','')}")

    script.on("message", on_message)
    script.load()

    def rpc(name, *a):
        try:
            return getattr(exports_of(script), name)(*a)
        except Exception as e:
            rec(f"[!] rpc {name} failed: {e}")
            return None

    rpc("set_live_scan", False)
    pkg_apk = adb("shell", "pm", "path", PKG).stdout.splitlines()[0]
    apk_path = pkg_apk.strip().removeprefix("package:")
    native_path = apk_path.rsplit("/base.apk", 1)[0] + "/lib/arm64/libDexHelper.so"
    rpc("set_native_redirect", native_path)
    rec(f"[*] nativeLoad rewrite target: {native_path}")

    if not args.attach_existing and not args.attach_running:
        dev.resume(pid)
        rec(f"[*] resumed pid={pid}")
    if not args.no_activity and not args.attach_existing and not args.attach_running:
        adb("shell", "am", "start", "-n", args.activity)
        rec(f"[*] am start {args.activity}")

    end = t0 + args.wait
    known_pids = {pid}
    rs_seen = False
    next_class_poll = t0
    class_sample_n = 0
    while time.time() < end and not detached.is_set():
        if detached.wait(min(1.0, max(0, end - time.time()))):
            break
        out = adb("shell", "pidof", PKG).stdout.split()
        rs = adb("shell", "pidof", f"{PKG}:rs").stdout.split()
        for p in out + rs:
            if p and int(p) not in known_pids:
                known_pids.add(int(p))
                tag = " (:rs)" if p in rs else ""
                rec(f"[+] NEW process pid={p}{tag} @ {time.time()-t0:.1f}s")
                if p in rs:
                    rs_seen = True
        # pidof may return shell-owned helper processes as well; require our attached PID.
        if str(pid) not in out:
            rec(f"[!] main pid={pid} GONE @ {time.time()-t0:.1f}s")
            break
        if args.poll_classes and time.time() >= next_class_poll:
            classes_now = rpc("list_loaded_classes", "com.moutai.mall")
            if classes_now is not None:
                class_sample_n += 1
                cp = os.path.join(args.out, f"loaded-classes-snapshot-{class_sample_n:02d}.txt")
                with open(cp, "w", encoding="utf-8") as f:
                    f.write("\n".join(sorted(classes_now)) + "\n")
                rec(f"[*] app class snapshot={len(classes_now)} -> {cp}")
            next_class_poll = time.time() + 2.0

    st = None if detached.is_set() else rpc("get_stats")
    rec(f"[*] stats: {json.dumps(st, ensure_ascii=False)}")
    classes = None if detached.is_set() else rpc("list_loaded_classes", "com.moutai.mall")
    if classes is not None:
        cp = os.path.join(args.out, f"loaded-classes-{pid}.txt")
        with open(cp, "w", encoding="utf-8") as f:
            f.write("\n".join(sorted(classes)) + "\n")
        rec(f"[*] app classes captured={len(classes)} -> {cp}")
    try:
        session.detach()
    except Exception:
        pass
    rec(f"[*] done rs_seen={rs_seen} dex={len(seen_md5)} regnat_msgs={regnat_n}")
    events.close()


if __name__ == "__main__":
    main()
