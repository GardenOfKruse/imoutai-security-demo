// entry-annot4.js — login signature observation agent v2 (mp34-annot4, 2026-09-11)
// 修复：去掉 okio Buffer 同步发现（拖死安装）；只做三类高价值观测：
//   A. api.a.intercept —— 每个 HTTP 请求的最终 headers（签名头就在这里）
//   B. 请求模型构造器 —— GetVerifyCodeRequest(md5,mobile,timestamp) 等入参
//   C. api.a$b / api.a 静态头方法 —— 头部常量值
// 约束：只读观测，不修改任何行为；不构造/不重放请求。
import Java from 'frida-java-bridge';

function log(msg) { send({ type: 'log', msg: String(msg) }); }

function safe(fn, tag) {
    try { return fn(); }
    catch (e) { log('safe[' + (tag || '') + ']: ' + e); return undefined; }
}

function install() {
    Java.perform(function () {
        log('annot4 entered');

        // endpoints
        safe(function () {
            const ep = Java.use('com.moutai.mall.env.Endpoints');
            const vals = ep.values();
            const out = {};
            for (let i = 0; i < vals.length; i++) out[String(vals[i].name())] = safe(function () { return String(vals[i].getApp()); }, 'ep');
            send({ type: 'endpoints', data: out });
        }, 'endpoints');
        log('step1 endpoints done');

        // A. interceptor
        safe(function () {
            const a = Java.use('com.moutai.mall.api.a');
            a.intercept.implementation = function (chain) {
                safe(function () {
                    const req = chain.request();
                    const hs = [];
                    const headers = req.headers();
                    const n = headers.size();
                    for (let i = 0; i < n; i++) hs.push([String(headers.name(i)), String(headers.value(i))]);
                    send({ type: 'request', method: String(req.method()), url: String(req.url()), headers: hs });
                }, 'req');
                return this.intercept(chain);
            };
            log('step2 intercept hooked');
        }, 'intercept');

        // B. model ctors
        ['com.moutai.mall.api.model.GetVerifyCodeRequest',
         'com.moutai.mall.api.model.LoginRequest',
         'com.moutai.mall.api.model.AuthLoginRequest',
         'com.moutai.mall.api.model.BindPhoneRequest'].forEach(function (cn) {
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
        log('step3 ctors hooked');

        // C. header value sources
        safe(function () {
            const ab = Java.use('com.moutai.mall.api.a$b');
            ['a', 'b', 'c', 'd'].forEach(function (mn) {
                safe(function () {
                    ab[mn].overloads.forEach(function (ov) {
                        ov.implementation = function () {
                            const r = ov.apply(this, arguments);
                            log('a$b.' + mn + '() = ' + r);
                            return r;
                        };
                    });
                }, 'ab.' + mn);
            });
        }, 'ab');
        safe(function () {
            const a = Java.use('com.moutai.mall.api.a');
            ['b', 'c', 'd'].forEach(function (mn) {
                safe(function () {
                    a[mn].overload().implementation = function () {
                        const r = this[mn]();
                        log('api.a.' + mn + '() = ' + r);
                        return r;
                    };
                }, 'a.' + mn);
            });
        }, 'a-static');
        log('step4 header sources hooked');

        log('annot4 all hooks installed');
    });
}

setImmediate(install);

rpc.exports = {
    getStats: function () { return { ok: true, agent: 'annot4' }; },
};
