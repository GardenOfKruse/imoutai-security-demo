// entry-annot5.js — local signature sampling agent (mp34-annot5, 2026-09-11)
// 核心思路：getVerifyRequest(mobile) 是纯本地工厂方法（构造 GetVerifyCodeRequest，不发送）。
// 对活实例调用它 N 次（合成手机号），配合构造器 hook 采样 (mobile, timestamp, md5) 三元组，
// 用于离线破解 md5 拼接方案。同时保留 intercept/a$b 观测。不发任何网络请求。
import Java from 'frida-java-bridge';

function log(msg) { send({ type: 'log', msg: String(msg) }); }

function safe(fn, tag) {
    try { return fn(); }
    catch (e) { log('safe[' + (tag || '') + ']: ' + e); return undefined; }
}

const samples = { vcode: [], headers: [] };

function readFields(obj, names) {
    const out = {};
    names.forEach(function (n) { out[n] = safe(function () { return String(obj[n].value); }, 'f:' + n); });
    return out;
}

function install() {
    Java.perform(function () {
        log('annot5 entered');

        // ctor hook：捕获所有请求模型构造（含 getVerifyRequest 内部构造）
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
        log('ctors hooked');

        // interceptor 观测（若熔断前有请求飞过）
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
        log('intercept hooked');
        log('annot5 installed');
    });
}

rpc.exports = {
    // 对活 LoginActivity 实例本地调用 getVerifyRequest（不发网络）
    sampleVcode: function (mobile) {
        let result = null;
        Java.perform(function () {
            Java.choose('com.moutai.mall.module.login.LoginActivity', {
                onMatch: function (inst) {
                    safe(function () {
                        const reqObj = inst.getVerifyRequest(Java.use('java.lang.String').$new(String(mobile)));
                        const triple = readFields(reqObj, ['mobile', 'timestamp', 'md5']);
                        triple._input_mobile = String(mobile);
                        samples.vcode.push(triple);
                        result = triple;
                        send({ type: 'vcode-sample', data: triple });
                    }, 'sample');
                },
                onComplete: function () { }
            });
        });
        return result;
    },
    // 直接读取静态头方法
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
                const aa = Java.use('com.moutai.mall.api.a$a');
                out['a$a.invoke'] = safe(function () { return String(aa.invoke()); }, 'aa');
            }, 'a$a');
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
        samples.headers.push(out);
        send({ type: 'headers-sample', data: out });
        return out;
    },
    getStats: function () { return { agent: 'annot5', vcode: samples.vcode.length }; },
};

setImmediate(install);
