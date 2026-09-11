"""Short spawn session for a read-only libDexHelper runtime image."""

import argparse
import hashlib
import os
import subprocess
import time

import frida


HOST = "127.0.0.1:8899"
PKG = "com.moutai.mall"
ADB = r"E:\Android\platform-tools\adb.exe"
SERIAL = "82e459fc0920"
CHUNK = 1024 * 1024


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--js", required=True)
    parser.add_argument("--out", required=True)
    parser.add_argument("--wait", type=float, default=8.0)
    parser.add_argument("--chunk", type=int, default=64 * 1024)
    parser.add_argument("--no-activity", action="store_true")
    parser.add_argument("--list-classes", action="store_true")
    parser.add_argument("--hide-maps", action="store_true", help="resume 前过滤 /proc/*/maps 中的 Frida 标识")
    parser.add_argument("--no-direct-dlopen", action="store_true", help="跳过已知会触发壳自检的手工 JNI_OnLoad 路径")
    parser.add_argument("--preload-native", action="store_true", help="暂停态仅 dlopen 壳库并在 ART resume 前挂住 JNI_OnLoad")
    parser.add_argument("--activity", default="com.moutai.mall/.module.splash.SplashActivity")
    args = parser.parse_args()

    device = frida.get_device_manager().add_remote_device(HOST)
    pid = device.spawn([PKG])
    session = device.attach(pid)
    with open(args.js, "r", encoding="utf-8") as fh:
        script = session.create_script(fh.read())

    def on_message(message, data):
        if message.get("type") == "send":
            payload = message.get("payload") or {}
            if payload.get("type") != "dex":
                print(f"[agent] {payload}")
        elif message.get("type") == "log":
            print(f"[agent] {message.get('payload')}")
        elif message.get("type") == "error":
            print(f"[agent-error] {message.get('stack', message)}")

    script.on("message", on_message)
    script.load()
    # 在恢复目标进程前关闭全进程 DEX 扫描，避免扫描先占用 Frida JS 线程。
    try:
        script.exports_sync.set_live_scan(False)
        print("[*] live DEX scan paused before resume")
    except Exception as exc:
        print(f"[!] live scan pre-pause unavailable: {exc}")
    pkg_apk = subprocess.check_output(
        [ADB, "-s", SERIAL, "shell", "pm", "path", PKG],
        text=True, encoding="utf-8", errors="replace").splitlines()[0]
    apk_path = pkg_apk.strip().removeprefix("package:")
    native_path = apk_path.rsplit("/base.apk", 1)[0] + "/lib/arm64/libDexHelper.so"
    try:
        script.exports_sync.set_native_redirect(native_path)
        print(f"[*] native load redirect configured for {native_path}")
    except Exception as exc:
        print(f"[!] native redirect unavailable: {exc}")
    if args.hide_maps:
        try:
            print(f"[*] maps filter configured={script.exports_sync.install_maps_filter()}")
        except Exception as exc:
            print(f"[!] maps filter unavailable: {exc}")
    if args.preload_native:
        try:
            print(f"[*] preload native result={script.exports_sync.preload_native(native_path)}")
        except Exception as exc:
            print(f"[!] preload native unavailable: {exc}")
    device.resume(pid)
    print(f"[*] direct dlopen path={native_path}")
    if args.no_direct_dlopen:
        print("[*] direct dlopen skipped")
    else:
        print(f"[*] direct dlopen result={script.exports_sync.load_native_absolute(native_path)}")
    if not args.no_activity:
        subprocess.run(
             [ADB, "-s", SERIAL, "shell", "am", "start", "-n",
             args.activity],
            capture_output=True, text=True, timeout=8,
            encoding="utf-8", errors="replace")
    time.sleep(args.wait)

    exports = script.exports_sync
    os.makedirs(args.out, exist_ok=True)
    if args.list_classes:
        classes = exports.list_loaded_classes("com.moutai.mall")
        class_path = os.path.join(args.out, f"loaded-classes-{pid}.txt")
        with open(class_path, "w", encoding="utf-8") as fh:
            fh.write("\n".join(sorted(classes)) + "\n")
        print(f"[+] loaded app classes={len(classes)} path={class_path}")
    info = exports.get_native_info()
    if not info:
        raise SystemExit("[-] libDexHelper.so not present in the injected process")
    os.makedirs(args.out, exist_ok=True)
    path = os.path.join(args.out, f"libDexHelper-runtime-{pid}.so")
    digest = hashlib.sha256()
    size = int(info["size"])
    ranges = exports.get_native_ranges()
    print(f"[*] readable native ranges={len(ranges)}")
    with open(path, "wb") as out:
        out.truncate(size)
        base_int = int(info["base"], 16)
        for region in ranges:
            region_offset = int(region["base"], 16) - base_int
            offset = 0
            while offset < int(region["size"]):
                part_size = min(args.chunk, int(region["size"]) - offset)
                raw = bytes(exports.read_native(
                    info["base"], size, region_offset + offset, part_size))
                if len(raw) != part_size:
                    raise RuntimeError(f"short read offset={region_offset + offset}")
                out.seek(region_offset + offset)
                out.write(raw)
                digest.update(raw)
                offset += part_size
            print(f"[+] range base={region['base']} size={region['size']} prot={region['protection']}")
    exports_path = os.path.join(args.out, f"libDexHelper-exports-{pid}.txt")
    with open(exports_path, "w", encoding="utf-8") as out:
        for item in exports.list_native_exports():
            out.write(f"{item['type']} {item['offset']} {item['name']}\n")
    print(f"[+] pid={pid} base={info['base']} size={info['size']}")
    print(f"[+] image={path} sha256={digest.hexdigest()}")
    print(f"[+] exports={exports_path}")
    try:
        exports.set_live_scan(True)
    except Exception:
        pass
    session.detach()


if __name__ == "__main__":
    main()
