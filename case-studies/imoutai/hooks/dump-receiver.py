"""
dump-receiver.py
PC 端拉 libDexHelper.so 内存, 写成 so 文件

依赖: frida-tools (项目内 .venv 已装)
  .venv/Scripts/python.exe hooks/dump-receiver.py

流程:
  1. 启 frida spawn + dump-libdexhelper.js (一个终端, 挂着别关)
  2. 本脚本连接同一会话, 通过 rpc.exports.get_info / read_chunk 拉数据
  3. 写 outputs/libDexHelper_<pid>_<timestamp>.so
"""
import os
import time
import frida

HOST = "127.0.0.1:8888"
PKG = "com.moutai.mall"
SCRIPT_PATH = os.path.join(os.path.dirname(__file__), "dump-libdexhelper.js")
CHUNK = 1024 * 1024  # 1MB

OUT_DIR = os.path.abspath(os.path.join(
    os.path.dirname(__file__), "..", "..", "..", "..", "outputs"
))
os.makedirs(OUT_DIR, exist_ok=True)


def main():
    host, port = HOST.split(":")
    device = frida.get_device(host, int(port))
    print(f"[*] 已连接 {HOST}")

    # attach 已运行的 frida (不自己 spawn, 跟手动跑的 frida CLI 共用)
    # 找 com.moutai.mall 的会话
    sessions = [
        s for s in device.enumerate_sessions()
        if s.pid and PKG in (s.name or "")
    ]
    if not sessions:
        print(f"[-] 找不到 {PKG} 的 frida 会话, 请先跑:")
        print(f"    frida -H {HOST} -f {PKG} -l hooks/dump-libdexhelper.js")
        return
    session = sessions[0]
    print(f"[*] 接管 session pid={session.pid}")

    with open(SCRIPT_PATH, "r", encoding="utf-8") as f:
        script = session.create_script(f.read())
    script.load()
    time.sleep(1.0)  # 等 hook 装好

    info = script.exports.get_info()
    if not info:
        print("[-] libDexHelper.so 还没加载, 退出")
        return
    print(f"[*] libDexHelper.so @ {info['base']} size={info['size']} path={info['path']}")

    size = info["size"]
    num_chunks = (size + CHUNK - 1) // CHUNK
    print(f"[*] 开始拉 {num_chunks} 个 chunk (chunk={CHUNK})")

    out_name = f"libDexHelper_{session.pid}_{int(time.time())}.so"
    out_path = os.path.join(OUT_DIR, out_name)

    with open(out_path, "wb") as out:
        for i in range(num_chunks):
            offset = i * CHUNK
            this_size = min(CHUNK, size - offset)
            buf = script.exports.read_chunk(offset, this_size)
            out.write(bytes(buf))
            print(f"[+] chunk {i + 1}/{num_chunks} ({this_size} bytes)")

    print(f"[+] 已保存: {out_path}")
    print(f"[+] 大小: {os.path.getsize(out_path)} bytes")


if __name__ == "__main__":
    main()
