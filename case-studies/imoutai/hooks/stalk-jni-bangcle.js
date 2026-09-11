
/**
 * stalk-jni-bangcle.js
 *
 * 目的: JNI 入口 hook + Stalker 指令级 trace,定位 libDexHelper 里
 *       "监测点" JNI 调用的具体代码段,直到崩点(B 指令跳到非法地址)
 *
 * 用法 (PC 端):
 *   frida -H 127.0.0.1:8888 -f com.moutai.mall -l hooks/stalk-jni-bangcle.js
 *
 * 配合:
 *   - trace-signal-bangcle.js (看崩点 PC)
 *   - dump-libdexhelper.js (dump so 给 IDA)
 *   - trace-jni-calls-bangcle.js (高层 JNI 调用记录)
 *
 * 工作流:
 *   1. JNI 入口 (FindClass / RegisterNatives / GetMethodID / GetStaticMethodID)
 *      被 libDexHelper 触发时,挂上当前线程的 Stalker
 *   2. transform 内过滤到 libDexHelper!0xXXX 范围,只打这个 so 的指令
 *   3. ARM64 B/BL/BR 跳转单独打,记 (from offset, target)
 *   4. 看到 BR Xn 且 target 是 0x0 附近,标 [JUMP-TO-NULL]
 *   5. onLeave unfollow,Stalker.garbageCollect
 *
 * 注意:
 *   - Stalker 性能开销大,单 JNI 函数级,不要长期挂
 *   - 不要跟 pthread_create / clone hook 同时开,本脚本不挂那两个
 *   - ARM64 B 指令立即数解码: op 000101 xx (xx=00 B, 01 BL)
 *   - ARM64 BR 指令: 1101011 0 0001 1111 000000 Rn 00000 (0xD61F0000 | Rn)
 */

const SO_NAME = "libDexHelper.so";
const TRACE_JNI = [
    "FindClass",
    "RegisterNatives",
    "GetStaticMethodID",
    "GetMethodID"
];

// ARM64 指令掩码
const B_IMM_MASK  = 0xFC000000;
const B_IMM_OP    = 0x14000000;  // B <imm>
const BL_IMM_OP   = 0x94000000;  // BL <imm>
const BR_OP       = 0xD61F0000;  // BR Xn (Rn 0..30)
const BR_XZR_OP   = 0xD61F03C0;  // BR XZR = ret 0
const BLR_OP      = 0xD63F0000;  // BLR Xn
const RET_OP      = 0xD65F03C0;  // RET

function isBranchToNearNull(target) {
    // 0x0..0x1000 都算"空指针",梆梆的常见崩点
    if (target.compare(0) < 0) return false;
    return target.compare(0x1000) < 0;
}

function decodeBranch(instBytes, pc) {
    // instBytes 是 4 字节 ARM64 指令
    const u32 = (instBytes[3] << 24) | (instBytes[2] << 16) | (instBytes[1] << 8) | instBytes[0];
    const op = u32 & B_IMM_MASK;

    if (op === B_IMM_OP || op === BL_IMM_OP) {
        // B/BL imm26 符号扩展,左移 2
        let imm26 = u32 & 0x03FFFFFF;
        if (imm26 & 0x02000000) imm26 |= 0xFC000000;  // sign extend
        const signed = (imm26 << 6) >> 6;  // 算术右移
        const target = pc.add(signed * 4);
        return { kind: op === B_IMM_OP ? "B" : "BL", target: target };
    }
    if ((u32 & 0xFFFFFC1F) === BR_OP) {
        const rn = (u32 >> 5) & 0x1F;
        return { kind: "BR", rn: rn, target: null };
    }
    if (u32 === BR_XZR_OP) return { kind: "BR-XZR", target: ptr(0) };
    if ((u32 & 0xFFFFFC1F) === BLR_OP) {
        const rn = (u32 >> 5) & 0x1F;
        return { kind: "BLR", rn: rn, target: null };
    }
    if (u32 === RET_OP) return { kind: "RET", target: null };
    return null;
}

function describePc(addr, mod) {
    if (mod) {
        const off = addr.sub(mod.base);
        return mod.name + "!0x" + off.toString(16);
    }
    return addr.toString();
}

function startStalker(jniName, mod) {
    const tid = Process.getCurrentThreadId();
    let blocks = 0;
    let branches = 0;
    let nullJumps = 0;

    console.log("\n[stalk] " + jniName + " 进入, libDexHelper 范围 0x" +
        mod.base.toString(16) + "-0x" + mod.base.add(mod.size).toString(16));

    Stalker.follow(tid, {
        transform(iterator) {
            let inst = iterator.next();
            const startAddr = inst.address;

            do {
                const modAt = Process.findModuleByAddress(inst.address);
                if (modAt && modAt.name === SO_NAME) {
                    const off = inst.address.sub(modAt.base);
                    // ARM64 指令是 4 字节,直接读
                    const bytes = inst.bytes || Memory.readU8 ? null : null;
                    let branchInfo = null;
                    if (inst.bytes && inst.bytes.length === 4) {
                        branchInfo = decodeBranch(inst.bytes, inst.address);
                    }

                    if (branchInfo) {
                        branches++;
                        // BR 还要再看上下文寄存器值,Stalker 提供不了,只能看
                        // B 立即数跳转
                        if (branchInfo.target && isBranchToNearNull(branchInfo.target)) {
                            nullJumps++;
                            console.log("[JUMP-TO-NULL] " + describePc(inst.address, modAt) +
                                "  " + branchInfo.kind + " -> 0x" +
                                branchInfo.target.toString(16));
                        } else {
                            console.log("  [BR] 0x" + off.toString(16) + "  " + branchInfo.kind +
                                (branchInfo.target ? " -> " + describePc(branchInfo.target, Process.findModuleByAddress(branchInfo.target)) :
                                    " X" + branchInfo.rn));
                        }
                    } else {
                        // 普通指令, 限速 (前 50 条打, 后面同地址范围内只数)
                        if (blocks < 200) {
                            console.log("  0x" + off.toString(16) + "  " + inst);
                            blocks++;
                        }
                    }
                }
            } while ((inst = iterator.next()) !== null);
        }
    });
}

function stopStalker() {
    const tid = Process.getCurrentThreadId();
    Stalker.unfollow(tid);
    Stalker.garbageCollect();
    console.log("[stalk] unfollow + gc");
}

function hookJni(name) {
    const addr = Module.findGlobalExportByName(name);
    if (!addr) {
        console.log("[-] 未找到 " + name);
        return;
    }
    Interceptor.attach(addr, {
        onEnter(args) {
            const mod = Process.findModuleByAddress(this.returnAddress);
            if (!mod || mod.name !== SO_NAME) return;
            this.stalk = true;
            this.callerMod = mod;
        },
        onLeave(retval) {
            if (!this.stalk) return;
            // Stalker 只在调用方是 libDexHelper 时开
            startStalker(name, this.callerMod);
            // 不主动停,让 hook 链上某条指令出 BR 后再停
            // 用 setTimeout 兜底,5 秒后强制停
            setTimeout(stopStalker, 5000);
        }
    });
}

console.log("[*] stalk-jni-bangcle 启动");
console.log("[*] 只在 JNI 入口由 libDexHelper 触发时挂 Stalker,5s 兜底");
TRACE_JNI.forEach(hookJni);
