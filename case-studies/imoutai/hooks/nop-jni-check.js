
/**
 * nop-jni-check.js
 *
 * 目的: 把 trace 出的监测点指令 NOP 掉,绕过梆梆检测
 *       配套 stalk-jni-bangcle.js + trace-signal-bangcle.js
 *
 * 用法:
 *   1. stalk-jni-bangcle 跑一遍,记下 (libDexHelper 范围, B 跳转 from 偏移, kind)
 *   2. 改本脚本 PATCHES 数组,填基址 (通常 = 0x73fb65c000,见 mod.base) + 偏移
 *   3. frida -H 127.0.0.1:8888 -f com.moutai.mall -l hooks/nop-jni-check.js
 *
 * PATCHES 格式:
 *   { offset: 0x5a310, kind: 'B' }     <- 把单条 B 指令 NOP 掉
 *   { offset: 0x5a310, kind: 'BR' }    <- 把单条 BR 指令 NOP 掉 (NOP 后下条指令继续)
 *
 * 注意:
 *   - NOP 单条指令会让 PC 滑到下一条,可能引起栈不平衡 / 返回值错
 *   - 推荐: 跳转目标非法的 B 指令可以 NOP (崩点)
 *   - BLR 类不要直接 NOP,改成 putRet() 才对 (见 frida-toolkit §3.6)
 *   - ARM64 NOP 字节序: 1F 20 03 D5 (4 字节)
 */

const SO_NAME = "libDexHelper.so";
const NOP_ARM64 = [0x1f, 0x20, 0x03, 0xd5];  // little-endian

// === 在这里填要 NOP 的偏移 ===
// 从 stalk-jni-bangcle 拿到的 B/JUMP-TO-NULL 输出:
const PATCHES = [
    // 例: { offset: 0x5a310, kind: 'B' },
    // 例: { offset: 0x42b9d0, kind: 'BLR' },
];

function dumpBytes(addr, n) {
    const out = [];
    for (let i = 0; i < n; i++) {
        out.push(Memory.readU8(addr.add(i)).toString(16).padStart(2, "0"));
    }
    return out.join(" ");
}

function nopOne(offset) {
    const mod = Process.findModuleByName(SO_NAME);
    if (!mod) {
        console.log("[-] " + SO_NAME + " 未加载");
        return;
    }
    const target = mod.base.add(offset);
    const before = dumpBytes(target, 4);
    const beforeWord = Memory.readU32(target);

    Memory.protect(target, 4, "rwx");
    Memory.patchCode(target, 4, code => {
        for (let i = 0; i < NOP_ARM64.length; i++) {
            code.add(i).writeU8(NOP_ARM64[i]);
        }
    });
    const after = dumpBytes(target, 4);
    const afterWord = Memory.readU32(target);

    console.log("[+] NOP @ " + mod.name + "!0x" + offset.toString(16) +
        " (" + target + ")");
    console.log("    before: 0x" + beforeWord.toString(16).padStart(8, "0") + "  " + before);
    console.log("    after : 0x" + afterWord.toString(16).padStart(8, "0") + "  " + after);
}

console.log("[*] nop-jni-check 启动, " + PATCHES.length + " 处待 patch");
console.log("[*] 目标: " + SO_NAME);

if (PATCHES.length === 0) {
    console.log("[!] PATCHES 数组为空,先在 stalk-jni-bangcle 里找出 [JUMP-TO-NULL] 偏移,再填本脚本");
} else {
    // 等 libDexHelper 加载完
    const tryPatch = () => {
        if (Process.findModuleByName(SO_NAME)) {
            PATCHES.forEach(p => nopOne(p.offset));
        } else {
            setTimeout(tryPatch, 200);
        }
    };
    tryPatch();
}
