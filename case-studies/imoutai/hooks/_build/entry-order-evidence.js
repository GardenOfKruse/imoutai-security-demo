// entry-order-evidence.js — compose/验证码/submit 只读证据采集
//
// 目的：回到授权测试设备后，用同一订单窗口确认调用顺序、字段形状和响应关联。
// 约束：不改参数、不改返回值、不点击验证码、不重放请求；不输出 Header/ Cookie/ token/JSON 值。
import Java from 'frida-java-bridge';

function sendEvent(data) { send({ type: 'order-evidence', data: data }); }

function safe(fn, tag) {
    try { return fn(); }
    catch (e) {
        sendEvent({ kind: 'error', tag: String(tag || ''), message: String(e) });
        return undefined;
    }
}

function javaClassName(obj) {
    return safe(function () { return String(obj.getClass().getName()); }, 'class') || '';
}

function shape(value, depth) {
    if (value === null) return 'null';
    if (Array.isArray(value)) return depth > 1 ? 'array' : { type: 'array', item: value.length ? shape(value[0], depth + 1) : 'empty' };
    if (typeof value !== 'object') return typeof value;
    if (depth > 1) return 'object';
    const out = {};
    Object.keys(value).sort().forEach(function (key) { out[key] = shape(value[key], depth + 1); });
    return out;
}

function bodySummary(request) {
    return safe(function () {
        const body = request.body();
        if (!body) return { present: false };
        const Buffer = Java.use('okio.Buffer');
        const buf = Buffer.$new();
        body.writeTo(buf);
        const text = String(buf.readUtf8());
        const out = { present: true, bytes: text.length, className: javaClassName(body) };
        try {
            const parsed = JSON.parse(text);
            out.jsonShape = shape(parsed, 0);
        } catch (e) {
            out.jsonShape = 'non-json';
        }
        return out;
    }, 'request-body') || { present: 'unknown' };
}

function responseSummary(response) {
    return safe(function () {
        const out = { code: Number(response.code()) };
        try {
            const peek = response.peekBody(1024 * 1024);
            const text = String(peek.string());
            out.bytes = text.length;
            try {
                const parsed = JSON.parse(text);
                out.jsonShape = shape(parsed, 0);
                const flat = JSON.stringify(parsed);
                out.hasTransactionId = flat.indexOf('transactionId') !== -1;
                out.hasOrderId = flat.indexOf('orderId') !== -1;
            } catch (e) { out.jsonShape = 'non-json'; }
        } catch (e) { out.body = 'peek-failed'; }
        return out;
    }, 'response') || { code: 'unknown' };
}

function pathOf(url) {
    const text = String(url || '');
    const q = text.indexOf('?');
    const noQuery = q >= 0 ? text.slice(0, q) : text;
    const marker = noQuery.indexOf('/xhr/');
    return marker >= 0 ? noQuery.slice(marker) : '(non-xhr)';
}

function classify(path) {
    if (path.indexOf('/trade/order/standard/compose/v2') >= 0) return 'compose';
    if (path.indexOf('/trade/order/standard/submit/v2') >= 0) return 'submit';
    if (/captcha|verifycode|verify-code/i.test(path)) return 'captcha-network';
    return null;
}

function enumerateCaptchaSdk() {
    safe(function () {
        const names = Java.enumerateLoadedClassesSync()
            .filter(function (name) { return name.indexOf('com.netease.nis.captcha') === 0; })
            .sort();
        const classes = names.map(function (name) {
            return safe(function () {
                const cls = Java.use(name).class;
                const methods = [];
                const declared = cls.getDeclaredMethods();
                for (let i = 0; i < declared.length; i++) methods.push(String(declared[i]));
                return { name: name, methods: methods.sort() };
            }, 'captcha-class') || { name: name, methods: [] };
        });
        sendEvent({ kind: 'captcha-sdk-inventory', classCount: classes.length, classes: classes });
    }, 'captcha-inventory');
}

function hookWebViewTelemetry() {
    safe(function () {
        const WebView = Java.use('android.webkit.WebView');
        ['loadUrl', 'loadDataWithBaseURL', 'evaluateJavascript'].forEach(function (methodName) {
            if (!WebView[methodName]) return;
            WebView[methodName].overloads.forEach(function (ov) {
                ov.implementation = function () {
                    const cls = javaClassName(this);
                    if (cls.indexOf('CaptchaWebView') >= 0) {
                        const lengths = [];
                        for (let i = 0; i < arguments.length; i++) lengths.push(String(arguments[i] || '').length);
                        sendEvent({ kind: 'captcha-webview-call', method: methodName, className: cls, argLengths: lengths });
                    }
                    return ov.apply(this, arguments);
                };
            });
        });
    }, 'webview-hooks');
}

function install() {
    Java.perform(function () {
        safe(function () {
            const a = Java.use('com.moutai.mall.api.a');
            a.intercept.implementation = function (chain) {
                const request = chain.request();
                const path = pathOf(request.url());
                const kind = classify(path);
                if (kind) {
                    sendEvent({
                        kind: 'http-request',
                        phase: kind,
                        method: String(request.method()),
                        path: path,
                        headerNames: safe(function () {
                            const hs = request.headers();
                            const names = [];
                            for (let i = 0; i < hs.size(); i++) names.push(String(hs.name(i)));
                            return names.sort();
                        }, 'headers') || [],
                        body: bodySummary(request),
                    });
                }
                const response = this.intercept(chain);
                if (kind) sendEvent({ kind: 'http-response', phase: kind, path: path, response: responseSummary(response) });
                return response;
            };
        }, 'api-intercept');
        enumerateCaptchaSdk();
        hookWebViewTelemetry();
        sendEvent({ kind: 'installed', readOnly: true, targetPhases: ['compose', 'captcha-network', 'submit'] });
    });
}

setImmediate(install);

rpc.exports = {
    getStats: function () { return { ok: true, agent: 'order-evidence', readOnly: true }; },
};
