
/**
 * trace-signal-bangcle.js
 *
 * 目的: 定位"跳垃圾地址"的具体指令位置 + 是哪个函数跳的
 *       用 Process.setExceptionHandler 拦截 SIGSEGV/SIGBUS/SIGILL
 *       不杀线程, 不杀 pthread_create, 保留原行为让守门员起来
 *       return false 让 app 正常崩
 *
 * 用法:
 *   frida -H 127.0.0.1:8888 -f com.moutai.mall -l hooks/trace-signal-bangcle.js
 *
 * 预期输出:
 *   [CRASH] SIGSEGV
 *     pc = libDexHelper.so!0x5a312   <-- 跳的指令位置, 跳完前一条
 *     bt:
 *       #0 libDexHelper.so!0x5a312   <-- 跳的指令
 *       #1 libDexHelper.so!0x42b9d0   <-- 守门员函数
 *       #2 libutils.so!0x12c50       <-- 线程池入口
 *     x0=0x...  x1=0x...  ... x7=0x...
 *     (有 ASCII 特判: 8 字节像字符串就单独打)
 *
 * 下一步: 拿 PC + 偏移去 IDA 看汇编, 找 B/BL 跳转源
 */

function reg_i64(ctx, n) {
    // arm64: x0-x7
    return ctx[n];
}

function maybeAscii(ptr) {
    try {
        // NativePointer 转字符串, 截前 8 字节
        const s = Memory.readUtf8String(ptr, 8);
        if (!s) return null;
        // 全是 ASCII 可打印字符 (0x20-0x7e) 才算
        let ok = true;
        for (let i = 0; i < s.length; i++) {
            const c = s.charCodeAt(i);
            if (c < 0x20 || c > 0x7e) { ok = false; break; }
        }
        return ok ? s : null;
    } catch (e) {
        return null;
    }
}

function describe_pc(addr) {
    const m = Process.findModuleByAddress(addr);
    if (m) {
        const off = addr.sub(m.base);
        return m.name + "!0x" + off.toString(16);
    }
    return addr.toString();
}

function describe_ptr(p) {
    if (p.isNull()) return "0x0";
    const m = Process.findModuleByAddress(p);
    if (m) {
        return p + " (" + m.name + "+0x" + p.sub(m.base).toString(16) + ")";
    }
    return p.toString();
}

Process.setExceptionHandler(function (details) {
    console.log("\n[CRASH] " + details.type);
    console.log("    pc  = " + describe_pc(details.context.pc));
    console.log("    sp  = " + describe_ptr(details.context.sp));
    console.log("    lr  = " + describe_pc(details.context.lr));
    console.log("    fp  = " + describe_pc(details.context.fp));

    // backtrace
    const bt = Thread.backtrace(details.context, Backtracer.ACCURATE);
    console.log("    bt (" + bt.length + "):");
    bt.forEach(function (addr, i) {
        console.log("      #" + i + " " + describe_pc(addr));
    });

    // x0-x7
    const ctx = details.context;
    const regs = ["x0", "x1", "x2", "x3", "x4", "x5", "x6", "x7"];
    let line = "    regs:";
    for (let i = 0; i < regs.length; i++) {
        const v = reg_i64(ctx, regs[i]);
        line += " " + regs[i] + "=" + v;
    }
    console.log(line);

    // ASCII 特判: x0/x1/x2 如果是 8 字节 ASCII, 单独打
    for (let i = 0; i < 3; i++) {
        const v = reg_i64(ctx, regs[i]);
        if (v.isNull()) continue;
        const s = maybeAscii(v);
        if (s) {
            console.log("    " + regs[i] + " ascii: \"" + s + "\"");
        }
    }

    // 不处理, 让 app 正常崩
    return false;
});

console.log("[*] trace-signal-bangcle 启动 (signal handler 已注册, 只读不杀)");
