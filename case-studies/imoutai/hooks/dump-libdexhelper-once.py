"""Attach to the already-running test process and dump libDexHelper read-only."""

import hashlib
import os
import time

import frida


HOST = "127.0.0.1:8899"
PKG = "com.moutai.mall"
SERIAL = "82e459fc0920"
HOOK = os.path.join(os.path.dirname(__file__), "dump-libdexhelper.js")
OUT_DIR = os.path.abspath(
    os.path.join(
        os.path.dirname(__file__),
        "..",
        "..",
        "..",
        "work",
        "20260907-222238-i-app-frida-dump-dex-android-apk",
        "extract",
        "native-memdump",
    )
)
CHUNK = 1024 * 1024


def on_message(message, data):
    if message.get("type") == "error":
        print("[-] agent error:", message.get("stack", message))


def main():
    device = frida.get_device_manager().add_remote_device(HOST)
    processes = [p for p in device.enumerate_processes() if p.name == PKG]
    if not processes:
        raise SystemExit("[-] target process is not running")
    process = processes[0]
    print(f"[*] attach pid={process.pid} serial={SERIAL}")

    session = device.attach(process.pid)
    try:
        with open(HOOK, "r", encoding="utf-8") as fh:
            script = session.create_script(fh.read())
        script.on("message", on_message)
        script.load()
        time.sleep(0.5)
        info = script.exports_sync.get_info()
        if not info:
            raise SystemExit("[-] libDexHelper.so is not loaded in target")

        os.makedirs(OUT_DIR, exist_ok=True)
        out_path = os.path.join(OUT_DIR, f"libDexHelper-runtime-{process.pid}.so")
        size = int(info["size"])
        digest = hashlib.sha256()
        with open(out_path, "wb") as out:
            for offset in range(0, size, CHUNK):
                part_size = min(CHUNK, size - offset)
                data = script.exports_sync.read_chunk(offset, part_size)
                if data is None:
                    raise RuntimeError(f"read failed at offset {offset}")
                raw = bytes(data)
                if len(raw) != part_size:
                    raise RuntimeError(
                        f"short read at offset {offset}: {len(raw)} != {part_size}"
                    )
                out.write(raw)
                digest.update(raw)
                print(f"[+] chunk offset={offset} size={part_size}")
        print(f"[+] runtime image: {out_path}")
        print(f"[+] bytes={size} sha256={digest.hexdigest()}")
    finally:
        session.detach()


if __name__ == "__main__":
    main()
