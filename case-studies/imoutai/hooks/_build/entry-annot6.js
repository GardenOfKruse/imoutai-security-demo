// entry-annot6.js — md5 input capture agent (mp34-annot6, 2026-09-11)
// 在 sampleVcode 前后开启捕获窗口，hook java.security.MessageDigest 的
// update/digest 全部 overload，抓 getVerifyRequest 构造期间喂入 md5 的原始字节。
// 只读观测；不做网络操作。
import Java from 'frida-java-bridge';

function log(msg) { send({ type: 'log', msg: String(msg) }); }

function safe(fn, tag) {
    try { return fn(); }
    catch (e) { log('safe[' + (tag || '') + ']: ' + e); return undefined; }
}

let capturing = false;
let captured = [];

function toHex(jarr) {
    let out = '';
    const n = jarr.length;
    for (let i = 0; i < n; i++) {
        const v = jarr[i] & 0xff;
        out += (v < 16 ? '0' : '') + v.toString(16);
    }
    return out;
}

function tryBytes(arg) {
    try {
        if (arg === null || arg === undefined) return null;
        const t = arg.$className || '';
        if (t === '[B') return toHex(arg);
        return null;
    } catch (e) { return null; }
}

function shortStack() {
    return safe(function () {
        const s = Java.use('android.util.Log').getStackTraceString(Java.use('java.lang.Exception').$new());
        return String(s).split('\n').slice(0, 12).join(' | ');
    }, 'stack');
}

function install() {
    Java.perform(function () {
        log('annot6 entered');

        // MessageDigest 捕获
        safe(function () {
            const MD = Java.use('java.security.MessageDigest');
            MD.getInstance.overload('java.lang.String').implementation = function (alg) {
                if (capturing) captured.push({ kind: 'getInstance', alg: String(alg) });
                return this.getInstance(alg);
            };
            MD.update.overloads.forEach(function (ov) {
                ov.implementation = function () {
                    if (capturing) {
                        const rec = { kind: 'update', sig: ov.argumentTypes.map(function (t) { return t.className; }).join(',') };
                        for (let i = 0; i < arguments.length; i++) {
                            const b = tryBytes(arguments[i]);
                            if (b !== null) rec.hex = b;
                        }
                        if (rec.hex && rec.hex.length <= 256) rec.stack = shortStack();
                        captured.push(rec);
                    }
                    return ov.apply(this, arguments);
                };
            });
            MD.digest.overloads.forEach(function (ov) {
                ov.implementation = function () {
                    const r = ov.apply(this, arguments);
                    if (capturing) {
                        const rec = { kind: 'digest', sig: ov.argumentTypes.map(function (t) { return t.className; }).join(',') };
                        for (let i = 0; i < arguments.length; i++) {
                            const b = tryBytes(arguments[i]);
                            if (b !== null) rec.input_hex = b;
                        }
                        rec.out_hex = safe(function () { return toHex(r); }, 'out');
                        if (!rec.input_hex) rec.stack = shortStack();
                        captured.push(rec);
                    }
                    return r;
                };
            });
        }, 'md-hook');
        log('md hooks installed');

        // 构造器 hook（三元组来源）
        ['com.moutai.mall.api.model.GetVerifyCodeRequest'].forEach(function (cn) {
            safe(function () {
                const w = Java.use(cn);
                w.$init.overloads.forEach(function (ov) {
                    if (ov.argumentTypes.length === 0) return;
                    ov.implementation = function () {
                        const args = [];
                        for (let i = 0; i < arguments.length; i++) args.push(String(arguments[i]));
                        send({ type: 'ctor', cls: cn, args: args });
                        return ov.apply(this, arguments);
                    };
                });
            }, 'ctor');
        });
        log('annot6 installed');
    });
}

setImmediate(install);

rpc.exports = {
    sampleVcode: function (mobile) {
        let result = null;
        Java.perform(function () {
            Java.choose('com.moutai.mall.module.login.LoginActivity', {
                onMatch: function (inst) {
                    safe(function () {
                        captured = [];
                        capturing = true;
                        const reqObj = inst.getVerifyRequest(Java.use('java.lang.String').$new(String(mobile)));
                        capturing = false;
                        const triple = {};
                        ['mobile', 'timestamp', 'md5'].forEach(function (n) {
                            triple[n] = safe(function () { return String(reqObj[n].value); }, 'f:' + n);
                        });
                        triple._input_mobile = String(mobile);
                        triple._digests = captured.slice();
                        result = triple;
                        send({ type: 'vcode-sample', data: triple });
                    }, 'sample');
                },
                onComplete: function () { }
            });
        });
        return result;
    },
    getStats: function () { return { agent: 'annot6' }; },
};
