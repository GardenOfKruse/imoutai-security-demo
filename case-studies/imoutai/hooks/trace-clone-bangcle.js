
/**
 * trace-clone-bangcle.js
 *
 * 目的: 守门员线程函数到底在哪？hook clone + args[3]+96 拿真线程函数
 *       配合 ImModule + arm64,只 trace,什么都不杀
 *
 * 用法:
 *   frida -H 127.0.0.0:8888 -f com.moutai.mall -l hooks/trace-clone-bangcle.js
 *
 * 输出 (看样本):
 *   [clone] libDexHelper.so 0x42b9d0
 *   [clone] libutils.so    0x12c50
 *   [clone] libart.so      0x67d728
 *   ...
 *
 * 下一步:
 *   把这堆偏移给 NOP 脚本 (17.x putRet) 干掉守门员线程函数
 *
 * 注意:
 *   - 1.10+ 删了 --no-pause,spawn 后默认就是不暂停
 *   - 不能同时 hook pthread_create + clone,梆梆检测 libc 的 pthread_create
 *   - ARM64: args[3]+96,ARM32: args[3]+48
 *   - args[3] 是 __pthread_start 结构体, +96 才是真线程函数指针
 */

const SO_FILTER = ["libDexHelper.so", "libutils.so", "libart.so", "libc.so"];

function get_export(name) {
    if (Module.findGlobalExportByName) return Module.findGlobalExportByName(name);
    if (Module.getGlobalExportByName) return Module.getGlobalExportByName(name);
    if (Module.findExportByName) return Module.findExportByName(null, name);
    return null;
}

function hook_clone() {
    const clone_addr = get_export("clone");
    if (!clone_addr) {
        console.log("[-] 未找到 clone 导出");
        return;
    }
    console.log("[*] clone @ " + clone_addr);

    Interceptor.attach(clone_addr, {
        onEnter: function (args) {
            try {
                const struct_ptr = args[3];
                if (struct_ptr.isNull()) return;

                // ARM64: +96 拿真线程函数; ARM32: +48
                const start_routine = struct_ptr.add(96).readPointer();
                const mod = Process.findModuleByAddress(start_routine);
                if (!mod) return;

                const hit = SO_FILTER.some(function (n) {
                    return mod.name.indexOf(n) !== -1;
                });
                if (!hit) return;

                const offset = start_routine.sub(mod.base);
                console.log("[clone] " + mod.name + " 0x" + offset.toString(16));
            } catch (e) {
                console.log("[-] clone hook err: " + e);
            }
        }
    });
}

console.log("[*] trace-clone-bangcle 启动 (只 trace, 不杀线程)");
hook_clone();
