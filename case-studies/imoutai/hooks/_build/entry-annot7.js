// entry-annot7.js — CryptoUtil 签名输入捕获 (mp34-annot7, 2026-09-11)
// 在 annot6 基础上：
//   1. 修复 byte[] 捕获（length+index 读取，替代 $className 判断）
//   2. 直接 hook com.netease.libs.yxsecurity.encrypt.CryptoUtil 的 l/o 方法，打印全部入参
// 目标：拿到 vcode md5 的明文拼接串（含盐）。只读观测。
import Java from 'frida-java-bridge';

function log(msg) { send({ type: 'log', msg: String(msg) }); }

function safe(fn, tag) {
    try { return fn(); }
    catch (e) { log('safe[' + (tag || '') + ']: ' + e); return undefined; }
}

let capturing = false;
let captured = [];

function tryBytes(arg) {
    try {
        if (arg === null || arg === undefined) return null;
        let len;
        try { len = arg.length; } catch (e) { return null; }
        if (typeof len !== 'number' || len < 0 || len > 4096) return null;
        let out = '';
        for (let i = 0; i < len; i++) {
            const v = arg[i] & 0xff;
            out += (v < 16 ? '0' : '') + v.toString(16);
        }
        return out;
    } catch (e) { return null; }
}

function argDump(args) {
    const parts = [];
    for (let i = 0; i < args.length; i++) {
        const a = args[i];
        if (a === null || a === undefined) { parts.push('null'); continue; }
        const s = safe(function () { return String(a); }, 'str');
        if (s !== undefined && s.length < 300) { parts.push(s); continue; }
        const b = tryBytes(a);
        parts.push(b !== null ? ('bytes:' + b) : '(obj)');
    }
    return parts;
}

function shortStack() {
    return safe(function () {
        const s = Java.use('android.util.Log').getStackTraceString(Java.use('java.lang.Exception').$new());
        return String(s).split('\n').slice(0, 14).join(' | ');
    }, 'stack');
}

function install() {
    Java.perform(function () {
        log('annot7 entered');

        // 1. CryptoUtil 全量方法观测（捕获期打印入参/出参）
        safe(function () {
            const CU = Java.use('com.netease.libs.yxsecurity.encrypt.CryptoUtil');
            const cls = CU.class;
            const names = cls.getDeclaredMethods().map(function (m) { return String(m.getName()); });
            log('CryptoUtil methods: ' + names.join(','));
            names.forEach(function (mn) {
                safe(function () {
                    CU[mn].overloads.forEach(function (ov) {
                        ov.implementation = function () {
                            const r = ov.apply(this, arguments);
                            if (capturing) {
                                const rec = { kind: 'crypto.' + mn, args: argDump(arguments) };
                                rec.ret = safe(function () { return String(r); }, 'ret');
                                if (rec.ret === undefined) rec.ret = '(bytes?)';
                                rec.stack = shortStack();
                                captured.push(rec);
                            }
                            return r;
                        };
                    });
                }, 'cu.' + mn);
            });
        }, 'cryptoutil');

        // 2. MessageDigest 捕获
        safe(function () {
            const MD = Java.use('java.security.MessageDigest');
            MD.update.overloads.forEach(function (ov) {
                ov.implementation = function () {
                    if (capturing) {
                        const rec = { kind: 'update' };
                        const parts = argDump(arguments);
                        rec.args = parts;
                        captured.push(rec);
                    }
                    return ov.apply(this, arguments);
                };
            });
            MD.digest.overloads.forEach(function (ov) {
                ov.implementation = function () {
                    const r = ov.apply(this, arguments);
                    if (capturing) {
                        const rec = { kind: 'digest', args: argDump(arguments) };
                        rec.out = safe(function () { return toHexBuf(r); }, 'out');
                        captured.push(rec);
                    }
                    return r;
                };
            });
            function toHexBuf(r) {
                try { return tryBytes(r); } catch (e) { return String(r); }
            }
        }, 'md');
        log('md+crypto hooks installed');

        // 3. 构造器（三元组）
        safe(function () {
            const w = Java.use('com.moutai.mall.api.model.GetVerifyCodeRequest');
            w.$init.overloads.forEach(function (ov) {
                if (ov.argumentTypes.length === 0) return;
                ov.implementation = function () {
                    const args = [];
                    for (let i = 0; i < arguments.length; i++) args.push(String(arguments[i]));
                    send({ type: 'ctor', cls: 'GetVerifyCodeRequest', args: args });
                    return ov.apply(this, arguments);
                };
            });
        }, 'ctor');
        log('annot7 installed');
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
    getStats: function () { return { agent: 'annot7' }; },
};
