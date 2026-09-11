// entry-annot3.js — login request observation agent (mp34-annot3, 2026-09-11)
// 只读观测：api.a.intercept（最终签名请求）、api.a$b/api.a 头部值来源、
// GetVerifyCodeRequest/LoginRequest 构造器入参。不改任何返回值/行为。
import Java from 'frida-java-bridge';

function log(msg) { send({ type: 'log', msg: String(msg) }); }

function safe(fn, fallback) {
    try { return fn(); } catch (e) { return fallback === undefined ? ('(err: ' + e + ')') : fallback; }
}

function dumpEndpoints() {
    Java.perform(function () {
        const out = {};
        safe(function () {
            const ep = Java.use('com.moutai.mall.env.Endpoints');
            const vals = ep.values();
            for (let i = 0; i < vals.length; i++) {
                const v = vals[i];
                out[String(v.name())] = safe(function () { return String(v.getApp()); });
            }
        });
        send({ type: 'endpoints', data: out });
    });
}

function findOkioBuffer() {
    const names = Java.enumerateLoadedClassesSync().filter(function (n) { return n.indexOf('okio.') === 0; });
    for (let i = 0; i < names.length; i++) {
        try {
            const c = Java.use(names[i]).class;
            const ms = c.getDeclaredMethods();
            let hasUtf8 = false, hasWrite = false;
            for (let j = 0; j < ms.length; j++) {
                const mn = String(ms[j].getName());
                const mod = ms[j].getModifiers();
                if (mn === 'readUtf8' && (mod & 8) === 0) hasUtf8 = true;
                if (mn === 'write' && (mod & 8) === 0) hasWrite = true;
            }
            if (hasUtf8 && hasWrite) {
                const inst = Java.use(names[i]).$new();  // Buffer 有无参构造
                return names[i];
            }
        } catch (e) { /* 需要构造参数的类跳过 */ }
    }
    return null;
}

function install() {
    Java.perform(function () {
        log('annot3 entered');
        dumpEndpoints();

        // 1. 请求模型构造器入参
        const modelCtors = [
            'com.moutai.mall.api.model.GetVerifyCodeRequest',
            'com.moutai.mall.api.model.LoginRequest',
            'com.moutai.mall.api.model.AuthLoginRequest',
            'com.moutai.mall.api.model.BindPhoneRequest',
        ];
        modelCtors.forEach(function (cn) {
            safe(function () {
                const cls = Java.use(cn).class;
                const ctors = cls.getDeclaredConstructors();
                for (let i = 0; i < ctors.length; i++) {
                    (function (ctor) {
                        const k = cls.getDeclaredFields().length;
                        const wrapper = Java.use(cn);
                        wrapper.$init.overloads.forEach(function (ov) {
                            if (ov.argumentTypes.length === 0) return;  // 合成构造器跳过
                            ov.implementation = function () {
                                const args = [];
                                for (let a = 0; a < arguments.length; a++) args.push(String(arguments[a]));
                                send({ type: 'ctor', cls: cn, args: args });
                                return ov.apply(this, arguments);
                            };
                        });
                    })(ctors[i]);
                }
            });
        });

        // 2. 头部值来源
        safe(function () {
            const ab = Java.use('com.moutai.mall.api.a$b');
            ['a', 'b', 'c', 'd'].forEach(function (mn) {
                safe(function () {
                    ab[mn].implementation = function () {
                        const r = this[mn]();
                        log('a$b.' + mn + '() = ' + r);
                        return r;
                    };
                });
            });
        });
        safe(function () {
            const a = Java.use('com.moutai.mall.api.a');
            ['b', 'c', 'd'].forEach(function (mn) {
                safe(function () {
                    a[mn].overload().implementation = function () {
                        const r = this[mn]();
                        log('api.a.' + mn + '() = ' + r);
                        return r;
                    };
                });
            });
        });

        // 3. 拦截器：最终请求观测
        safe(function () {
            const bufClsName = findOkioBuffer();
            log('okio buffer class = ' + bufClsName);
            const a = Java.use('com.moutai.mall.api.a');
            a.intercept.implementation = function (chain) {
                try {
                    const req = chain.request();
                    const hs = [];
                    const headers = req.headers();
                    const n = headers.size();
                    for (let i = 0; i < n; i++) hs.push([String(headers.name(i)), String(headers.value(i))]);
                    let bodyStr = null;
                    const body = req.body();
                    if (body !== null && bufClsName) {
                        bodyStr = safe(function () {
                            const buf = Java.use(bufClsName).$new();
                            body.writeTo(buf);
                            const s = String(buf.readUtf8());
                            buf.close();
                            return s;
                        });
                    }
                    send({ type: 'request', method: String(req.method()), url: String(req.url()), headers: hs, body: bodyStr });
                } catch (e) {
                    log('intercept obs err: ' + e);
                }
                return this.intercept(chain);
            };
            log('api.a.intercept hooked');
        });

        log('annot3 hooks installed');
    });
}

setImmediate(install);

rpc.exports = {
    getStats: function () { return { ok: true }; },
};
