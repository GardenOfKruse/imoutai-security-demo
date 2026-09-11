"""Attach to a live process and record a read-only Java class audit."""

import argparse
import json
import os
import subprocess
import time

import frida


HOST = "127.0.0.1:8899"
ADB = r"E:\Android\platform-tools\adb.exe"
SERIAL = "82e459fc0920"


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--pid", type=int, required=True)
    parser.add_argument("--js", required=True)
    parser.add_argument("--out", required=True)
    parser.add_argument("--wait", type=float, default=3.0)
    args = parser.parse_args()

    os.makedirs(args.out, exist_ok=True)
    events_path = os.path.join(args.out, "events.log")
    audit_path = os.path.join(args.out, "class-audit.json")
    events = open(events_path, "w", encoding="utf-8")

    def record(line):
        print(line)
        events.write(line + "\n")
        events.flush()

    device = frida.get_device_manager().add_remote_device(HOST)
    session = device.attach(args.pid)
    with open(args.js, "r", encoding="utf-8") as fh:
        script = session.create_script(fh.read())

    audit_payload = None

    def on_message(message, data):
        nonlocal audit_payload
        if message.get("type") == "send":
            payload = message.get("payload") or {}
            if payload.get("type") == "class_audit":
                audit_payload = payload.get("result")
                record(
                    "[+] class audit total={} app={} loaders={}".format(
                        audit_payload.get("total_classes"),
                        len(audit_payload.get("app_classes") or []),
                        len(audit_payload.get("loaders") or []),
                    )
                )
            else:
                record("[agent-send] {}".format(payload))
        elif message.get("type") == "error":
            record("[agent-error] {}".format(message.get("stack", message)))
        else:
            record("[agent] {}".format(message))

    script.on("message", on_message)
    session.on("detached", lambda reason, *rest: record(
        "[!] session detached reason={} {}".format(reason, rest)
    ))
    script.load()
    record("[*] attached pid={} (read-only audit)".format(args.pid))
    try:
        script.exports_sync.audit()
    except Exception as exc:
        record("[!] audit rpc failed: {}".format(exc))
    time.sleep(args.wait)

    if audit_payload is not None:
        with open(audit_path, "w", encoding="utf-8") as fh:
            json.dump(audit_payload, fh, ensure_ascii=False, indent=2)
            fh.write("\n")
        classes_path = os.path.join(args.out, "app-classes.txt")
        with open(classes_path, "w", encoding="utf-8") as fh:
            fh.write("\n".join(audit_payload.get("app_classes") or []))
            fh.write("\n")
        record("[+] audit={} classes={}".format(audit_path, classes_path))

    alive = subprocess.run(
        [ADB, "-s", SERIAL, "shell", "su", "-c", "kill -0 {}".format(args.pid)],
        capture_output=True,
        text=True,
        encoding="utf-8",
        errors="replace",
        timeout=8,
    )
    record("[*] pid_alive_after_attach={} exit={}".format(alive.returncode == 0, alive.returncode))
    try:
        session.detach()
    except Exception:
        pass
    events.close()


if __name__ == "__main__":
    main()
