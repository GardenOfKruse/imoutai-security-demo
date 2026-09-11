
/**
 * trace-jni-calls-bangcle.js
 *
 * 目的: 看梆梆解 dex 后 libart 跑 JVM 初始化时, 哪个 JNI 调用返回 null
 *       崩点是 libart 0x5aabe0 + x0=0x0 (null deref), 溯源哪个 JNI 返回 null
 *
 * 用法:
 *   frida -H 127.0.0.1:8888 -f com.moutai.mall -l hooks/trace-jni-calls-bangcle.js
 *
 * 输出:
 *   [JNI] FindClass("com/moutai/mall/...")
 *         -> ret=0x0  <-- 这里返回 null 就会崩
 *   [JNI] RegisterNatives clazz=0x... nMethods=3
 *
 * 下一步: 看哪个 JNI 返回 null, 那个 JNI 调用前面的栈, 找梆梆逻辑
 */

const FUNCS = [
    "FindClass",
    "RegisterNatives",
    "GetMethodID",
    "GetStaticMethodID",
    "GetFieldID",
    "GetStaticFieldID",
    "GetStringUTFChars",
    "NewStringUTF",
    "NewGlobalRef",
    "ExceptionCheck",
    "ExceptionOccurred",
    "ExceptionDescribe"
];

function describe_jstring(p) {
    if (p.isNull()) return "null";
    try {
        return Memory.readUtf8String(p);
    } catch (e) {
        return "(read err)";
    }
}

function hook_jni(name) {
    const addr = Module.findGlobalExportByName(name);
    if (!addr) {
        console.log("[-] 未找到 " + name);
        return;
    }
    Interceptor.attach(addr, {
        onEnter: function (args) {
            this.name = name;
            // 记录参数
            if (name === "FindClass") {
                this.clsname = describe_jstring(args[1]);
            } else if (name === "GetMethodID" || name === "GetStaticMethodID" || name === "GetFieldID" || name === "GetStaticFieldID") {
                this.clsname = "<clazz>";
                this.mname = describe_jstring(args[2]);
                this.sig = describe_jstring(args[3]);
            } else if (name === "RegisterNatives") {
                this.nMethods = args[2].toInt32();
            }
        },
        onLeave: function (retval) {
            const isNull = retval.isNull();
            const tag = isNull ? "  -> NULL!" : "";
            let line = "[JNI] " + name;

            if (name === "FindClass") {
                line += "(\"" + this.clsname + "\")" + tag;
            } else if (name === "GetMethodID" || name === "GetStaticMethodID" || name === "GetFieldID" || name === "GetStaticFieldID") {
                line += "(" + this.mname + ", " + this.sig + ")" + tag;
            } else if (name === "RegisterNatives") {
                line += "(nMethods=" + this.nMethods + ")" + tag;
            } else {
                line += " ret=0x" + retval.toString(16) + tag;
            }
            console.log(line);

            // 特别标记 null 返回
            if (isNull) {
                const bt = Thread.backtrace(this.context, Backtracer.ACCURATE);
                console.log("    null ret bt (" + bt.length + "):");
                bt.forEach(function (addr, i) {
                    const m = Process.findModuleByAddress(addr);
                    if (m) {
                        const off = addr.sub(m.base);
                        console.log("      #" + i + " " + m.name + "!0x" + off.toString(16));
                    } else {
                        console.log("      #" + i + " " + addr);
                    }
                });
            }
        }
    });
}

console.log("[*] trace-jni-calls-bangcle 启动 (hook libart JNI 函数, 只读不杀)");

// 等 libart 加载完再 hook (一般 zygote 起来就有了, 但保险起见)
function try_hook_all() {
    const art = Process.findModuleByName("libart.so");
    if (!art) {
        console.log("[*] libart.so 未加载, 等");
        return false;
    }
    FUNCS.forEach(hook_jni);
    console.log("[*] 已 hook " + FUNCS.length + " 个 JNI 函数 @ libart " + art.base);
    return true;
}

if (!try_hook_all()) {
    // 等 dlopen
    const dlopen = Module.findGlobalExportByName("android_dlopen_ext");
    if (dlopen) {
        Interceptor.attach(dlopen, {
            onLeave: function () {
                if (try_hook_all()) {
                    // 找到后这个 hook 没必要留着, 但留着也行, try_hook_all 内部幂等
                }
            }
        });
    }
}
