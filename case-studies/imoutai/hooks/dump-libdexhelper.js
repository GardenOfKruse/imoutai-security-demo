/**
 * dump-libdexhelper.js
 *
 * 目的: dlopen 加载完 libDexHelper.so 立即 dump 内存 (解密态, 给 IDA)
 *       走 rpc.exports, 让 PC 端 Python 拉 binary (不走 send)
 *
 * 用法 (PC 端 Python, 见 dump-receiver.py):
 *   frida -H 127.0.0.1:8888 -f com.moutai.mall -l hooks/dump-libdexhelper.js
 *   (同时) python hooks/dump-receiver.py
 *
 * rpc.exports:
 *   - get_info() -> {name, base, size}
 *   - read_chunk(idx, chunk_size) -> ArrayBuffer (RPC binary, 不走 JSON 序列化)
 */

const SO_NAME = "libDexHelper.so";

rpc.exports = {
    get_info: function () {
        const mod = Process.findModuleByName(SO_NAME);
        if (!mod) return null;
        return {
            name: mod.name,
            base: mod.base.toString(),
            size: mod.size,
            path: mod.path
        };
    },
    read_chunk: function (offset, size) {
        const mod = Process.findModuleByName(SO_NAME);
        if (!mod) return null;
        const ptr = mod.base.add(offset);
        // Frida 17: use NativePointer.readByteArray; do not change page
        // permissions merely to collect a read-only runtime image.
        return ptr.readByteArray(size);  // ArrayBuffer
    }
};

// 注册: 等 dlopen 后启动 console.log 提示
const dlopen = Module.findGlobalExportByName("android_dlopen_ext");
if (dlopen) {
    Interceptor.attach(dlopen, {
        onEnter: function (args) {
            try {
                const p = args[0].readCString();
                if (p && p.indexOf(SO_NAME) !== -1) this.match = true;
            } catch (e) { }
        },
        onLeave: function () {
            if (this.match) {
                const m = Process.findModuleByName(SO_NAME);
                if (m) {
                    console.log("[+] " + SO_NAME + " 已加载 @ " + m.base + " size=" + m.size);
                    console.log("[*] rpc.exports.get_info / read_chunk 可用");
                }
            }
        }
    });
}
console.log("[*] dump-libdexhelper (rpc 模式) 启动");
