// entry-annot2.js — minimal read-only annotation dump agent (mp34-annot2, 2026-09-11)
// 设计约束（来自交接 F23/S5-25 教训）：
//   1. 不装任何 hook —— 纯反射读取，最小化被壳扫到的窗口
//   2. 目标类优先（login/auth/api/sign/token/intercept），关键类先 dump 先 send
//   3. 每个类独立 send，进程随时可能死，落袋为安
//   4. 类字段注解一并抓（LoginRequest 的 @SerializedName → 请求体参数名）
import Java from 'frida-java-bridge';

const PKG_PREFIX = 'com.moutai.mall';

function priority(name) {
    const n = name.toLowerCase();
    if (n.indexOf('loginrequest') !== -1 || n.indexOf('authlogin') !== -1) return 0;
    if (n.indexOf('login') !== -1) return 1;
    if (name.indexOf(PKG_PREFIX + '.api.') === 0) return 2;
    if (n.indexOf('sign') !== -1 || n.indexOf('token') !== -1) return 3;
    if (n.indexOf('intercept') !== -1 || n.indexOf('okhttp') !== -1) return 4;
    if (n.indexOf('request') !== -1 || n.indexOf('param') !== -1) return 5;
    return 9;
}

function annStrings(arr) {
    const out = [];
    for (let i = 0; i < arr.length; i++) out.push(String(arr[i]));
    return out;
}

function dumpClass(cls) {
    const methods = [];
    const declared = cls.getDeclaredMethods();
    for (let j = 0; j < declared.length; j++) {
        const m = declared[j];
        const item = { signature: String(m), annotations: [], parameterAnnotations: [] };
        try { item.annotations = annStrings(m.getDeclaredAnnotations()); }
        catch (e) { item.annotations.push('(ann failed: ' + e + ')'); }
        try {
            const pa = m.getParameterAnnotations();
            for (let pi = 0; pi < pa.length; pi++) {
                const one = [];
                for (let pj = 0; pj < pa[pi].length; pj++) one.push(String(pa[pi][pj]));
                item.parameterAnnotations.push(one);
            }
        } catch (e) { item.parameterAnnotations.push(['(paramann failed: ' + e + ')']); }
        methods.push(item);
    }
    const fields = [];
    try {
        const fs = cls.getDeclaredFields();
        for (let j = 0; j < fs.length; j++) {
            const f = fs[j];
            const item = { name: String(f.getName()), type: String(f.getType()), annotations: [] };
            try { item.annotations = annStrings(f.getDeclaredAnnotations()); }
            catch (e) { item.annotations.push('(ann failed: ' + e + ')'); }
            fields.push(item);
        }
    } catch (e) {
        fields.push({ name: '(fields failed: ' + e + ')', type: '', annotations: [] });
    }
    return { methods, fields };
}

const stats = { classes_dumped: 0, classes_failed: 0, total_seen: 0, started_at: null };

function enumerateAndDump() {
    const names = Java.enumerateLoadedClassesSync()
        .filter(function (n) { return n.indexOf(PKG_PREFIX) === 0; });
    stats.total_seen = names.length;
    names.sort(function (a, b) {
        const pa = priority(a), pb = priority(b);
        if (pa !== pb) return pa - pb;
        return a < b ? -1 : (a > b ? 1 : 0);
    });
    for (let i = 0; i < names.length; i++) {
        try {
            const cls = Java.use(names[i]).class;
            const payload = dumpClass(cls);
            send({ type: 'reflection', className: names[i], methods: payload.methods, fields: payload.fields });
            stats.classes_dumped++;
        } catch (e) {
            stats.classes_failed++;
            send({ type: 'reflection', className: names[i], methods: ['(class reflection failed: ' + e + ')'], fields: [] });
        }
    }
    send({ type: 'log', msg: 'annot2 pass done dumped=' + stats.classes_dumped + ' failed=' + stats.classes_failed + ' seen=' + stats.total_seen });
}

rpc.exports = {
    getStats: function () { return stats; },
    redump: function () {
        Java.perform(function () { enumerateAndDump(); });
        return true;
    }
};

setImmediate(function () {
    stats.started_at = Date.now();
    Java.perform(function () {
        send({ type: 'log', msg: 'annot2 agent entered Java.perform' });
        enumerateAndDump();
    });
});
