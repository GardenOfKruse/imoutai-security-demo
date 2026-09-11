// entry-annot8.js — key 归因 + 有机请求观测 (mp34-annot8, 2026-09-11)
// 在 annot7 基础上新增：
//   1. CryptoUtil 静态字段 dump + 无参方法探测（getSeed/getPrivateKey/j...）→ 归因设备 key 来源
//   2. api.a.intercept 常驻 hook（annot5 已有），驱动 post-wait 期收 authConfig 有机请求
// 只读观测。
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

function install() {
    Java.perform(function () {
        log('annot8 entered');

        // CryptoUtil 捕获 hook（同 annot7）
        safe(function () {
            const CU = Java.use('com.netease.libs.yxsecurity.encrypt.CryptoUtil');
            const names = CU.class.getDeclaredMethods().map(function (m) { return String(m.getName()); });
            names.forEach(function (mn) {
                safe(function () {
                    CU[mn].overloads.forEach(function (ov) {
                        ov.implementation = function () {
                            const r = ov.apply(this, arguments);
                            if (capturing) {
                                captured.push({
                                    kind: 'crypto.' + mn, args: argDump(arguments),
                                    ret: safe(function () { return String(r); }, 'ret') || '(bytes?)'
                                });
                            }
                            return r;
                        };
                    });
                }, 'cu.' + mn);
            });
        }, 'cryptoutil');

        // MessageDigest 捕获（同 annot7）
        safe(function () {
            const MD = Java.use('java.security.MessageDigest');
            MD.update.overloads.forEach(function (ov) {
                ov.implementation = function () {
                    if (capturing) captured.push({ kind: 'update', args: argDump(arguments) });
                    return ov.apply(this, arguments);
                };
            });
            MD.digest.overloads.forEach(function (ov) {
                ov.implementation = function () {
                    const r = ov.apply(this, arguments);
                    if (capturing) captured.push({ kind: 'digest', out: tryBytes(r) });
                    return r;
                };
            });
        }, 'md');

        // 拦截器常驻（收有机请求）
        safe(function () {
            const a = Java.use('com.moutai.mall.api.a');
            a.intercept.implementation = function (chain) {
                safe(function () {
                    const req = chain.request();
                    const hs = [];
                    const headers = req.headers();
                    for (let i = 0, n = headers.size(); i < n; i++) hs.push([String(headers.name(i)), String(headers.value(i))]);
                    send({ type: 'request', method: String(req.method()), url: String(req.url()), headers: hs });
                }, 'req');
                return this.intercept(chain);
            };
        }, 'intercept');
        log('hooks installed');
    });
}

setImmediate(install);

rpc.exports = {
    // 归因：CryptoUtil 字段与方法探测
    probeCrypto: function () {
        const out = {};
        Java.perform(function () {
            safe(function () {
                const CU = Java.use('com.netease.libs.yxsecurity.encrypt.CryptoUtil');
                const fs = CU.class.getDeclaredFields();
                for (let i = 0; i < fs.length; i++) {
                    (function (f) {
                        f.setAccessible(true);
                        const isStatic = (f.getModifiers() & 8) !== 0;
                        const name = 'field:' + f.getName();
                        if (isStatic) {
                            out[name] = safe(function () { return String(f.get(null)); }, name);
                        } else {
                            out[name] = '(instance:' + f.getType() + ')';
                        }
                    })(fs[i]);
                }
                // 无参方法探测
                ['j', 'getSeed', 'getPrivateKey', 'a', 'b', 'd', 'e', 'f', 'g', 'h', 'i', 'k', 'm', 'n'].forEach(function (mn) {
                    safe(function () {
                        const ov = CU[mn].overloads.filter(function (o) { return o.argumentTypes.length === 0; })[0];
                        if (!ov) { out['fn:' + mn] = '(needs args)'; return; }
                        const inst = safe(function () { return CU.j(); }, 'j-inst');
                        let r;
                        if (String(ov.returnType.className).indexOf('CryptoUtil') !== -1) {
                            r = ov.apply(inst || CU, []);
                        } else {
                            r = ov.apply(inst || CU, []);
                        }
                        const rs = safe(function () { return String(r); }, 'r');
                        out['fn:' + mn + '()'] = rs !== undefined ? rs.substring(0, 200) : '(bytes/null)';
                    }, 'probe:' + mn);
                });
            }, 'probe');
        });
        send({ type: 'crypto-probe', data: out });
        return out;
    },
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
    getStats: function () { return { agent: 'annot8' }; },
};
