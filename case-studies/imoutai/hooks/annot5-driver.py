"""
annot5-driver.py — mp34-annot5 采样驱动（2026-09-11）
启动 app → 等待登录页 → 附加 annot5 agent → RPC 本地采样：
  1. sampleStaticHeaders() 读取 api.a 静态头值
  2. sampleVcode(mobile) 对活实例本地调用 getVerifyRequest（纯构造，不发送）
输出 <out>/samples.json + events.log
"""
import argparse
import json
import os
import subprocess
import time

import frida

HOST = "127.0.0.1:8899"
PKG = "com.moutai.mall"
ADB = r"E:\Android\platform-tools\adb.exe"
SERIAL = "82e459fc0920"
ACTIVITY = "com.moutai.mall/.module.splash.SplashActivity"

# 合成手机号：非真实号码，仅用于本地 md5 关系采样
SYNTH_MOBILE = ["13000000001", "13000000002", "15888888888", "19912345678", "10086"]


def adb(*args, timeout=10):
    return subprocess.run([ADB, "-s", SERIAL, *args], capture_output=True, text=True,
                          encoding="utf-8", errors="replace", timeout=timeout)


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--js", required=True)
    ap.add_argument("--out", required=True)
    ap.add_argument("--settle", type=float, default=6.0, help="启动后等待秒数再附加")
    ap.add_argument("--activity", default=ACTIVITY, help="要启动的组件（默认 SplashActivity）")
    ap.add_argument("--no-restart", action="store_true", help="不重启 app，附加到现有进程")
    ap.add_argument("--post-wait", type=float, default=0, help="采样后保持会话秒数（观察有机请求）")
    args = ap.parse_args()

    if os.path.isdir(args.out) and os.listdir(args.out):
        raise SystemExit("--out must be new or empty")
    os.makedirs(args.out, exist_ok=True)
    events = open(os.path.join(args.out, "events.log"), "w", encoding="utf-8")

    def rec(line):
        print(line, flush=True)
        events.write(line + "\n")
        events.flush()

    with open(args.js, "r", encoding="utf-8-sig") as fh:
        source = fh.read()
    if "import" in source.split("\n", 3)[0] or source.lstrip().startswith("import"):
        raise SystemExit("refusing raw ES module; use the bundled IIFE")

    if not args.no_restart:
        adb("shell", "am", "force-stop", PKG)
        time.sleep(1)
        adb("shell", "am", "start", "-n", args.activity)
        rec(f"[*] started {args.activity}")
        time.sleep(args.settle)
    pid_out = adb("shell", "pidof", PKG).stdout.split()
    if not pid_out:
        raise SystemExit("no pid; app died during settle")
    pid = int(pid_out[0])
    rec(f"[*] attaching pid={pid}")

    dev = frida.get_device_manager().add_remote_device(HOST)
    session = dev.attach(pid)
    script = session.create_script(source)
    msgs = []

    def on_message(m, data):
        msgs.append(m)
        p = m.get("payload") or {}
        if m.get("type") == "send":
            rec("[agent-send] " + json.dumps(p, ensure_ascii=False)[:800])
        elif m.get("type") == "error":
            rec("[agent-error] " + str(m.get("description")))

    script.on("message", on_message)
    script.set_log_handler(lambda lvl, msg: rec(f"[agent:{lvl}] {msg}"))
    script.load()
    rec("[*] script loaded; waiting 2s for install")
    time.sleep(2)

    ex = script.exports_sync

    results = {"headers": None, "vcode": []}
    try:
        cp = ex.probe_crypto()
        results["crypto_probe"] = cp
        rec("[+] crypto probe: " + json.dumps(cp, ensure_ascii=False)[:1500])
    except Exception as e:
        rec(f"[!] probeCrypto failed: {e}")
    try:
        results["headers"] = ex.sample_static_headers()
        rec("[+] headers: " + json.dumps(results["headers"], ensure_ascii=False))
    except Exception as e:
        rec(f"[!] sampleStaticHeaders failed: {e}")

    for mob in SYNTH_MOBILE:
        try:
            t = ex.sample_vcode(mob)
            if t:
                results["vcode"].append(t)
                rec(f"[+] vcode {mob}: " + json.dumps(t, ensure_ascii=False))
            else:
                rec(f"[-] vcode {mob}: no LoginActivity instance on heap")
        except Exception as e:
            rec(f"[!] sampleVcode({mob}) failed: {e}")
        time.sleep(0.4)

    with open(os.path.join(args.out, "samples.json"), "w", encoding="utf-8") as f:
        json.dump(results, f, ensure_ascii=False, indent=2)
    rec(f"[*] saved samples.json vcode={len(results['vcode'])}")

    if args.post_wait > 0:
        rec(f"[*] post-wait {args.post_wait}s for organic requests...")
        time.sleep(args.post_wait)

    try:
        session.detach()
    except Exception:
        pass
    events.close()


if __name__ == "__main__":
    main()
