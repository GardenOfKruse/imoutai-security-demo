
/**
 * trace-jni-load-bangcle.js
 *
 * 目的: 看 libDexHelper.so 的 JNI_OnLoad 跑什么,是不是同步检测点
 *       配合 Interceptor.attach + 打印调用栈 + 返回值
 *
 * 用法:
 *   frida -H 127.0.0.1:8888 -f com.moutai.mall -l hooks/trace-jni-load-bangcle.js
 *
 * 输出:
 *   [JNI_OnLoad] libDexHelper.so @ 0x...
 *     #0 libDexHelper.so!0xXXXX
 *     #1 link64!0xXXXX           <- 加载者
 *     ...
 *   [JNI_OnLoad] leave ret=0
 *
 * 下一步:
 *   如果 JNI_OnLoad 里看到了可疑函数,再 Stalker trace 它
 *   如果 JNI_OnLoad 返回 0 正常,但后面还是崩 -> 检测在 .init_array
 */

const SO_NAME = "libDexHelper.so";

function hook_jni_onload() {
    const so = Process.findModuleByName(SO_NAME);
    if (!so) {
        // libDexHelper 可能还没加载,等 dlopen
        console.log("[*] libDexHelper.so 未加载,等 dlopen");
        return;
    }
    do_hook(so);
}

function do_hook(so) {
    // 1. JNI_OnLoad 是标准导出符号
    const jni_onload_addr = so.findExportByName("JNI_OnLoad");
    if (!jni_onload_addr) {
        console.log("[-] libDexHelper.so 未导出 JNI_OnLoad (可能被 strip)");
        console.log("[*] 尝试在 .init_array 段里找");
        // 退化: hook .init_array 区间所有函数
        return;
    }
    console.log("[*] JNI_OnLoad @ " + jni_onload_addr);

    Interceptor.attach(jni_onload_addr, {
        onEnter: function (args) {
            this.t0 = Date.now();
            console.log("\n[JNI_OnLoad] " + SO_NAME + " enter @ " +
                jni_onload_addr.sub(so.base).toString(16));
            console.log("    javaVM: " + args[0]);
            console.log("    reserved: " + args[1]);
            // backtrace: 看看是谁调上来的
            const bt = Thread.backtrace(this.context, Backtracer.ACCURATE);
            console.log("    backtrace (" + bt.length + "):");
            bt.forEach(function (addr, i) {
                const m = Process.findModuleByAddress(addr);
                if (m) {
                    const off = addr.sub(m.base);
                    console.log("      #" + i + " " + m.name + "!0x" + off.toString(16));
                } else {
                    console.log("      #" + i + " " + addr);
                }
            });
        },
        onLeave: function (retval) {
            const dt = Date.now() - this.t0;
            console.log("[JNI_OnLoad] leave ret=0x" + retval.toString(16) +
                " (耗时 " + dt + "ms)");
        }
    });
}

// 方式 1: libDexHelper 已加载(本进程已起)
hook_jni_onload();

// 方式 2: libDexHelper 还没加载,等 dlopen
const dlopen_addr = Module.findGlobalExportByName("android_dlopen_ext");
if (dlopen_addr) {
    Interceptor.attach(dlopen_addr, {
        onEnter: function (args) {
            try {
                const p = args[0].readCString();
                if (p && p.indexOf(SO_NAME) !== -1) {
                    this.match = true;
                }
            } catch (e) { }
        },
        onLeave: function () {
            if (this.match) {
                console.log("[+] " + SO_NAME + " 已加载,挂 JNI_OnLoad");
                // 延迟 0,等 module 注册完
                const so = Process.findModuleByName(SO_NAME);
                if (so) do_hook(so);
            }
        }
    });
}
