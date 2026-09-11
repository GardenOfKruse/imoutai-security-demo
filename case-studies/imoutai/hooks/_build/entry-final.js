// entry-final.js — ★ 最终版综合 hook agent（2026-09-12 整合定稿）
//
// 整合自今晚全部验证成功的能力（每一项都有对应证据），不含任何失败路径：
//   R1 纯反射注解 dump（自动执行）……………… 证据：S5-29，220 类 0 失败
//   R2 请求模型构造器捕获（自动）……………… 证据：S6-1 三元组落网
//   R3 MessageDigest 输入捕获（采样窗口内）…… 证据：S6-1 md5 拼接串现形
//   R4 CryptoUtil 全方法观测（采样窗口内）…… 证据：S6-1 调用链归因
//   R5 头部常量读取 a$b/a（RPC）………………… 证据：S5-30 clips_ 值
//   R6 CryptoUtil 字段/方法探测（RPC）………… 证据：findings F4 RSA 私钥
//   R7 拦截器观测 api.a.intercept（默认关闭，RPC 开启）→ 供 M1.6 抓头，平时不开
//
// 明确不包含（历史失败路径，勿再合入——详见 notes/steps-log.md F1-F34）：
//   spawn 注入 / dex 内存扫描 / B2 nativeLoad 改写 / B3 ForceDark / maps 过滤 /
//   手工 JNI_OnLoad / Process.setExceptionHandler / 进程冻结
//
// 窗口纪律：挂钩后 App 存活 ~20-45s（RiskStub 检测 ArtMethod 篡改加速 Everisk 倒计时），
// 一切采集动作在 T+25s 内完成；进程死亡由 watch_capture.py 守护自动跟随重启重挂。
import Java from 'frida-java-bridge';

function log(msg) { send({ type: 'log', msg: String(msg) }); }
function safe(fn, tag) {
    try { return fn(); }
    catch (e) { log('safe[' + (tag || '') + ']: ' + e); return undefined; }
}

let capturing = false;      // md5/CryptoUtil 捕获窗口开关
let captured = [];
let interceptOn = false;

// ---------- 字节/参数工具（annot7 修复版） ----------
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
        return String(s).split('\n').slice(0, 12).join(' | ');
    }, 'stack');
}

// ---------- 安装（attach 后自动执行） ----------
function install() {
    Java.perform(function () {
        log('final-hook entered');

        // R2: 请求模型构造器（GetVerifyCodeRequest / LoginRequest / AuthLoginRequest）
        ['com.moutai.mall.api.model.GetVerifyCodeRequest',
         'com.moutai.mall.api.model.LoginRequest',
         'com.moutai.mall.api.model.AuthLoginRequest'].forEach(function (cn) {
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
            }, 'ctor:' + cn);
        });
        log('R2 ctor hooks installed');

        // R3: MessageDigest 捕获
        safe(function () {
            const MD = Java.use('java.security.MessageDigest');
            MD.getInstance.overload('java.lang.String').implementation = function (alg) {
                if (capturing) captured.push({ kind: 'getInstance', alg: String(alg) });
                return this.getInstance(alg);
            };
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
        log('R3 MessageDigest hooks installed');

        // R4: CryptoUtil 全方法观测（捕获窗口内记录入参/出参/栈）
        safe(function () {
            const CU = Java.use('com.netease.libs.yxsecurity.encrypt.CryptoUtil');
            const names = CU.class.getDeclaredMethods().map(function (m) { return String(m.getName()); });
            log('CryptoUtil methods: ' + names.join(','));
            names.forEach(function (mn) {
                safe(function () {
                    CU[mn].overloads.forEach(function (ov) {
                        ov.implementation = function () {
                            const r = ov.apply(this, arguments);
                            if (capturing) {
                                captured.push({
                                    kind: 'crypto.' + mn, args: argDump(arguments),
                                    ret: safe(function () { return String(r); }, 'ret') || '(bytes?)',
                                    stack: shortStack(),
                                });
                            }
                            return r;
                        };
                    });
                }, 'cu.' + mn);
            });
        }, 'cryptoutil');
        log('R4 CryptoUtil hooks installed');

        // R7（默认关闭）: api.a.intercept 观测 —— 仅 M1.6 抓头时通过 RPC 开启
        safe(function () {
            const a = Java.use('com.moutai.mall.api.a');
            a.intercept.implementation = function (chain) {
                if (interceptOn) {
                    safe(function () {
                        const req = chain.request();
                        const hs = [];
                        const headers = req.headers();
                        for (let i = 0, n = headers.size(); i < n; i++) hs.push([String(headers.name(i)), String(headers.value(i))]);
                        send({ type: 'request', method: String(req.method()), url: String(req.url()), headers: hs });
                    }, 'req');
                }
                return this.intercept(chain);
            };
        }, 'intercept');
        log('R7 intercept observer ready (OFF — rpc enableIntercept 开启)');

        // R1: 纯反射注解 dump（自动一次；目标类优先）
        safe(function () {
            const PKG = 'com.moutai.mall';
            function priority(name) {
                const n = name.toLowerCase();
                if (n.indexOf('loginrequest') !== -1 || n.indexOf('authlogin') !== -1) return 0;
                if (n.indexOf('login') !== -1) return 1;
                if (name.indexOf(PKG + '.api.') === 0) return 2;
                if (n.indexOf('sign') !== -1 || n.indexOf('token') !== -1) return 3;
                if (n.indexOf('intercept') !== -1 || n.indexOf('okhttp') !== -1) return 4;
                if (n.indexOf('request') !== -1 || n.indexOf('param') !== -1) return 5;
                return 9;
            }
            const names = Java.enumerateLoadedClassesSync()
                .filter(function (x) { return x.indexOf(PKG) === 0; });
            names.sort(function (a, b) {
                const pa = priority(a), pb = priority(b);
                if (pa !== pb) return pa - pb;
                return a < b ? -1 : (a > b ? 1 : 0);
            });
            let dumped = 0, failed = 0;
            for (let i = 0; i < names.length; i++) {
                try {
                    const cls = Java.use(names[i]).class;
                    const methods = [];
                    const declared = cls.getDeclaredMethods();
                    for (let j = 0; j < declared.length; j++) {
                        const m = declared[j];
                        const item = { signature: String(m), annotations: [], parameterAnnotations: [] };
                        try { const a = m.getDeclaredAnnotations(); for (let k = 0; k < a.length; k++) item.annotations.push(String(a[k])); }
                        catch (e1) { item.annotations.push('(ann failed: ' + e1 + ')'); }
                        try {
                            const pa = m.getParameterAnnotations();
                            for (let pi = 0; pi < pa.length; pi++) {
                                const one = [];
                                for (let pj = 0; pj < pa[pi].length; pj++) one.push(String(pa[pi][pj]));
                                item.parameterAnnotations.push(one);
                            }
                        } catch (e2) { item.parameterAnnotations.push(['(paramann failed: ' + e2 + ')']); }
                        methods.push(item);
                    }
                    const fields = [];
                    try {
                        const fs = cls.getDeclaredFields();
                        for (let j = 0; j < fs.length; j++) {
                            const f = fs[j];
                            const item = { name: String(f.getName()), type: String(f.getType()), annotations: [] };
                            try { const a = f.getDeclaredAnnotations(); for (let k = 0; k < a.length; k++) item.annotations.push(String(a[k])); }
                            catch (e) { item.annotations.push('(ann failed: ' + e + ')'); }
                            fields.push(item);
                        }
                    } catch (e) { fields.push({ name: '(fields failed: ' + e + ')', type: '', annotations: [] }); }
                    send({ type: 'reflection', className: names[i], methods: methods, fields: fields });
                    dumped++;
                } catch (e) {
                    failed++;
                    send({ type: 'reflection', className: names[i], methods: ['(class reflection failed: ' + e + ')'], fields: [] });
                }
            }
            send({ type: 'log', msg: 'R1 reflection dump done dumped=' + dumped + ' failed=' + failed });
        }, 'R1');
        log('R1 reflection dump finished; final-hook all ready');
    });
}

setImmediate(install);

// ---------- RPC（与 annot5-driver.py 对齐：sample_static_headers / sample_vcode / probe_crypto） ----------
rpc.exports = {
    getStats: function () { return { agent: 'final-hook', capturing: capturing, capturedLen: captured.length }; },

    // R5: 头部常量值（反射读取，无 hook 风险）
    sampleStaticHeaders: function () {
        const out = {};
        Java.perform(function () {
            safe(function () {
                const a = Java.use('com.moutai.mall.api.a');
                ['b', 'c', 'd'].forEach(function (mn) {
                    out['api.a.' + mn] = safe(function () { return String(a[mn]()); }, 'a.' + mn);
                });
            }, 'a-static');
            safe(function () {
                Java.choose('com.moutai.mall.api.a$b', {
                    onMatch: function (inst) {
                        ['a', 'b', 'c', 'd'].forEach(function (mn) {
                            out['a$b.' + mn] = safe(function () { return String(inst[mn]()); }, 'ab.' + mn);
                        });
                    },
                    onComplete: function () { }
                });
            }, 'a$b');
        });
        send({ type: 'headers-sample', data: out });
        return out;
    },

    // R2+R3+R4: 本地采样 vcode 三元组 + md5 原始输入 + CryptoUtil 调用链
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

    // R6: CryptoUtil 字段与无参方法探测（getPrivateKey 等）
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
                        out[name] = isStatic
                            ? safe(function () { return String(f.get(null)); }, name)
                            : '(instance:' + f.getType() + ')';
                    })(fs[i]);
                }
                const inst = safe(function () { return CU.j(); }, 'j');
                ['getPrivateKey'].forEach(function (mn) {
                    out['fn:' + mn] = safe(function () { return String(CU[mn]()).substring(0, 200); }, mn);
                });
            }, 'probe');
        });
        send({ type: 'crypto-probe', data: out });
        return out;
    },

    // R7: 拦截器观测开关（M1.6 抓头时开启；平时关闭减小足迹）
    enableIntercept: function () {
        interceptOn = true;
        log('R7 intercept observation ENABLED');
        return true;
    },
    disableIntercept: function () { interceptOn = false; return true; },
};
