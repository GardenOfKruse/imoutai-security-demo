/**
 * dump-dex-mem.js — 梆梆 SecShell 业务 dex 内存 dump（i茅台 v1.9.12 / com.moutai.mall）
 *
 * 配套驱动: dump-dex-driver.py（spawn + rpc 拉取）
 *
 * 1. resume 前注入: pthread_create 整表压制
 *    - 修正 bypass-bangcle.js 的 bug: `indexOf("libDexHelper" !== -1)` 优先级错误恒为 false，
 *      壳线程从未被真正压制；本版对 entry 所在模块名做正确的字符串匹配
 * 2. app 初始化稳定后由 PC 端经 rpc 触发: list_dexes() -> read_dex() 逐块拉取
 *
 * rpc.exports:
 *   ping()                                   -> "pong"
 *   list_dexes()                             -> [{base,size,protection,map_off}]
 *   read_dex(base_str, size, offset, chunk)  -> ArrayBuffer
 *
 * 合法性: 仅本地授权样本分析（scope.md offline-sample），dump 内容不回传任何远端
 */
'use strict';

var SHELL_SOS = ['libDexHelper.so', 'libdexvmp.so', 'libSecShell', 'libbangcle_risk.so', 'libRiskStub.so'];

function log(s) { console.log('[dump-dex-mem] ' + s); }

// ---------- Phase 1: 压制壳的反调试/看门狗线程 ----------
(function () {
    var pthread_create_addr = Module.findGlobalExportByName('pthread_create');
    if (!pthread_create_addr) { log('!! pthread_create not found'); return; }
    var real_pthread_create = new NativeFunction(pthread_create_addr, 'int',
        ['pointer', 'pointer', 'pointer', 'pointer']);
    var replacement = new NativeCallback(function (tid, attr, start, arg) {
        try {
            var mod = Process.findModuleByAddress(start);
            if (mod !== null) {
                for (var i = 0; i < SHELL_SOS.length; i++) {
                    if (mod.name.indexOf(SHELL_SOS[i]) !== -1) {
                        log('suppress thread: ' + mod.name + ' entry=+' + start.sub(mod.base).toString(16));
                        return 0;
                    }
                }
            }
        } catch (e) { }
        return real_pthread_create(tid, attr, start, arg);
    }, 'int', ['pointer', 'pointer', 'pointer', 'pointer']);
    Interceptor.replace(pthread_create_addr, replacement);
    log('pthread_create 压制已装 (shell so: ' + SHELL_SOS.join(', ') + ')');
})();

// dlopen 观测（仅壳相关 so，控制日志量）
(function () {
    var dlopen = Module.findGlobalExportByName('android_dlopen_ext');
    if (dlopen) {
        Interceptor.attach(dlopen, {
            onEnter: function (args) {
                try { this.path = args[0].readCString(); } catch (e) { this.path = null; }
            },
            onLeave: function () {
                if (this.path && (this.path.indexOf('DexHelper') !== -1 ||
                                  this.path.indexOf('dexvmp') !== -1)) {
                    log('dlopen: ' + this.path);
                }
            }
        });
    }
})();

// ---------- Phase 2: 匿名内存 dex 扫描 ----------
function build_ranges() {
    var ranges = [];
    var prots = ['r--', 'rw-'];
    for (var p = 0; p < prots.length; p++) {
        var rs = Process.enumerateRanges({ protection: prots[p], coalesce: true });
        for (var i = 0; i < rs.length; i++) {
            var r = rs[i];
            if (r.file) continue;                       // 文件映射跳过（壳解密产物是匿名段）
            if (r.size > 256 * 1024 * 1024) continue;   // 超大段跳过
            if (r.size < 0x80) continue;
            ranges.push(r);
        }
    }
    return ranges;
}

function in_ranges(ranges, start, end) {
    for (var i = 0; i < ranges.length; i++) {
        var rb = ranges[i].base;
        var re = rb.add(ranges[i].size);
        if (start.compare(rb) >= 0 && end.compare(re) <= 0) return true;
    }
    return false;
}

function scan_dexes() {
    var ranges = build_ranges();
    log('anonymous scan ranges: ' + ranges.length);
    var found = {};
    var order = [];
    for (var i = 0; i < ranges.length; i++) {
        var r = ranges[i];
        var matches;
        try {
            matches = Memory.scanSync(r.base, r.size, '64 65 78 0a'); // "dex\n"
        } catch (e) { continue; }
        for (var m = 0; m < matches.length; m++) {
            try {
                var base = matches[m].address;
                var size = base.add(0x20).readU32();       // header.file_size
                var header_size = base.add(0x24).readU32();
                var endian = base.add(0x28).readU32();     // ENDIAN_CONSTANT
                var map_off = base.add(0x34).readU32();    // header.map_off
                if (header_size !== 0x70) continue;
                if (endian !== 0x12345678) continue;
                if (size < 0x70 || size > 200 * 1024 * 1024) continue;
                if (map_off < 0x70 || map_off >= size) continue;
                var end = base.add(size);
                if (!in_ranges(ranges, base, end)) continue; // 整个 dex 必须落在可读段内
                var key = base.toString() + '_' + size;
                if (found[key]) continue;
                found[key] = {
                    base: base.toString(),
                    size: size,
                    protection: r.protection,
                    map_off: map_off
                };
                order.push(found[key]);
            } catch (e) { }
        }
    }
    return order;
}

// 注意: frida rpc 的 JS 端键名必须 camelCase（Python 端以 snake_case 调用自动映射）
rpc.exports = {
    ping: function () { return 'pong'; },
    listDexes: function () {
        var dexes = scan_dexes();
        log('dex candidates: ' + dexes.length);
        return dexes;
    },
    readDex: function (baseStr, size, offset, chunk) {
        var basePtr = ptr(baseStr);
        var readPtr = basePtr.add(offset);
        var thisSize = Math.min(chunk, size - offset);
        return Memory.readByteArray(readPtr, thisSize);
    }
};

log('script loaded, rpc ready');
