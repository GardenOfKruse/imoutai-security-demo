/**
 * dump-dex-hook.js — v2: Java 层捕获解密 dex + 阻断风控自杀（i茅台 v1.9.12 / com.moutai.mall）
 *
 * 策略依据: Everisk"设备异常"倒计时自杀是 Java 层流程（Handler.postDelayed → System.exit），
 *           native pthread 压制挡不住 → 直接 hook 退出点。
 *           业务 dex 是全内存加载 → hook InMemoryDexClassLoader/DexFile 的字节入口，
 *           在解密瞬间抓取，比内存盲扫精准。
 *
 * 组成:
 *   A. pthread_create 压制（修正版，含统计 rpc: getStats）
 *   B. 阻断 System.exit / Runtime.exit / Runtime.halt / Process.killProcess
 *   C. 捕获 dex 字节:
 *      - InMemoryDexClassLoader.<init>(ByteBuffer / ByteBuffer[], ClassLoader)
 *      - DexFile 静态方法 openInMemoryDexFile(s) / loadDex / openDexFile
 *      - DexClassLoader.<init> 记录 path
 *   D. 兜底: rpc listDexes() 内存魔数扫描（放宽: memfd/app-data 文件段纳入, 上限 1GB）
 *
 * send 消息: {type:'dex', size, src, stack?} + binary payload
 * rpc: getStats() / listDexes() / readDex() / loadedClassesCount()
 */
import Java from 'frida-java-bridge';   // frida 17 移除内置 Java 全局，编译期由 esbuild 打包
'use strict';

function log(s) { console.log('[dexhook] ' + s); }

// mp34: 只保留 nativeLoad/ForceDark、DEX 捕获、RegisterNatives 与类时间线。
// 默认关闭，保持既有观测脚本行为不变；由 obs-driver.py 在加载前显式启用。
var MP34_MINIMAL = globalThis.MP34_MINIMAL === true;
var MP34_REFLECTION_DUMP = globalThis.MP34_REFLECTION_DUMP === true;
if (MP34_MINIMAL) log('mp34 minimal mode enabled');

var SHELL_SOS = ['libDexHelper.so', 'libdexvmp.so', 'libSecShell', 'libbangcle_risk.so', 'libRiskStub.so',
                 'libhaotiansec.so', 'libhaotian.so', 'libemulator_check.so',
                 'libalive_detected.so', 'libtiny_magic.so', 'libproperty_get.so'];
var stats = { pthread_total: 0, pthread_suppressed: 0, modules: {}, dex_captured: 0, exit_blocked: 0, native_signal_blocked: 0, regnat_count: 0, jni_onload_observed: 0, jni_findclass_calls: 0, jni_findclass_null: 0 };
var native_redirect_path = null;
var app_loader = null;
var maps_filter_enabled = false;
var maps_filter_fds = {};
var maps_filter_streams = {};
var attach_jni_onload_observer = null;
var getenv_observer_installed = false;
var stage_entry_observer_installed = false;
var stage_entry_observer_address = null;

// 仅记录 native 异常上下文，不修改异常处理结果；用于定位 JNI_OnLoad 后的 SIGSEGV。
if (!MP34_MINIMAL) (function install_exception_observer() {
    try {
        Process.setExceptionHandler(function (details) {
            try {
                var ctx = details.context || {};
                var pc = ctx.pc || ctx.rip || ctx.eip || 'unknown';
                var x0 = ctx.x0 || ctx.rcx || ctx.r0 || 'unknown';
                var pcRange = null;
                var faultRange = null;
                var x0Range = null;
                try {
                    pcRange = Process.findRangeByAddress(ptr(pc));
                } catch (e) { }
                try {
                    var fault = details.memory && details.memory.address ? details.memory.address : details.address;
                    faultRange = Process.findRangeByAddress(ptr(fault));
                } catch (e) { }
                try { x0Range = Process.findRangeByAddress(ptr(x0)); } catch (e) { }
                function rangeSummary(r) {
                    return r ? { base: r.base.toString(), size: r.size, protection: r.protection,
                        file: r.file ? r.file.path : null } : null;
                }
                log('X: native exception type=' + details.type +
                    ' address=' + details.address + ' pc=' + pc + ' x0=' + x0 +
                    ' memory=' + JSON.stringify(details.memory || null) +
                    ' pcRange=' + JSON.stringify(rangeSummary(pcRange)) +
                    ' faultRange=' + JSON.stringify(rangeSummary(faultRange)) +
                    ' x0Range=' + JSON.stringify(rangeSummary(x0Range)));
                try {
                    log('X: backtrace\n' + Thread.backtrace(ctx, Backtracer.ACCURATE)
                        .slice(0, 12).map(describe_native_address).join('\n'));
                } catch (e) { }
            } catch (e) { log('X: exception observer error=' + e); }
            return false;
        });
        log('X: native exception observer installed');
    } catch (e) { log('X: native exception observer unavailable=' + e); }
})();

// 只观察 PROT_NONE 映射/保护变化，确认 JNI_OnLoad 后的 fault 区由谁建立；不改写返回值。
if (!MP34_MINIMAL) (function install_protection_observer() {
    try {
        var mmap = Module.findGlobalExportByName('mmap');
        if (mmap) {
            Interceptor.attach(mmap, {
                onEnter: function (args) {
                    this.watch = ((args[2].toInt32() & 7) === 0);
                    if (this.watch) {
                        this.prot = args[2].toInt32();
                        this.len = args[1];
                        this.caller = this.returnAddress;
                        log('X: mmap PROT_NONE len=' + this.len + ' caller=' + this.caller);
                    }
                },
                onLeave: function (retval) {
                    if (this.watch) log('X: mmap PROT_NONE ret=' + retval);
                }
            });
            log('X: mmap protection observer installed');
        }
        var mprotect = Module.findGlobalExportByName('mprotect');
        if (mprotect) {
            Interceptor.attach(mprotect, {
                onEnter: function (args) {
                    this.watch = ((args[2].toInt32() & 7) === 0);
                    if (this.watch) {
                        log('X: mprotect PROT_NONE addr=' + args[0] + ' len=' + args[1] +
                            ' caller=' + this.returnAddress);
                    }
                },
                onLeave: function (retval) {
                    if (this.watch) log('X: mprotect PROT_NONE ret=' + retval);
                }
            });
            log('X: mprotect protection observer installed');
        }
    } catch (e) { log('X: protection observer unavailable=' + e); }
})();

function is_maps_path(path) {
    return !!path && /^\/proc\/(self|[0-9]+)\/maps$/.test(path);
}

function describe_native_address(address) {
    try {
        var p = ptr(address);
        var m = Process.findModuleByAddress(p);
        if (m) return m.name + '+' + p.sub(m.base);
        return 'unmapped@' + p;
    } catch (e) { return 'unavailable'; }
}

function install_getenv_observer(vm) {
    if (getenv_observer_installed || !vm) return;
    try {
        var table = ptr(vm).readPointer();
        var getEnv = table.add(Process.pointerSize * 7).readPointer(); // JNIInvokeInterface::GetEnv
        Interceptor.attach(getEnv, {
            onEnter: function (args) { this.out = args[1]; },
            onLeave: function (retval) {
                var env = null;
                var range = null;
                try { env = this.out.readPointer(); } catch (e) { }
                try { if (env) range = Process.findRangeByAddress(env); } catch (e) { }
                log('X: GetEnv ret=' + retval + ' env=' + env +
                    ' envRange=' + (range ? JSON.stringify({ base: range.base.toString(), size: range.size,
                        protection: range.protection, file: range.file ? range.file.path : null }) : 'none'));
            }
        });
        getenv_observer_installed = true;
        log('X: JavaVM GetEnv observer installed @' + getEnv);
    } catch (e) { log('X: GetEnv observer unavailable=' + e); }
}

function redact_maps_buffer(buf, length) {
    if (!buf || length <= 0) return 0;
    var targets = ['frida-agent', 'libfrida', 'gum-js-loop', 'gmain', 'frida', 'agent'];
    var changed = 0;
    for (var t = 0; t < targets.length; t++) {
        var needle = targets[t];
        for (var i = 0; i + needle.length <= length; i++) {
            var ok = true;
            for (var j = 0; j < needle.length; j++) {
                var c = buf.add(i + j).readU8();
                var lo = c >= 65 && c <= 90 ? c + 32 : c;
                if (lo !== needle.charCodeAt(j)) { ok = false; break; }
            }
            if (!ok) continue;
            for (var k = 0; k < needle.length; k++) buf.add(i + k).writeU8(0x78);
            changed++;
            i += needle.length - 1;
        }
    }
    if (changed) log('T3: maps redactions=' + changed);
    return changed;
}

function install_maps_filter() {
    if (maps_filter_enabled) return true;
    var openat = Module.findGlobalExportByName('openat');
    var fopen = Module.findGlobalExportByName('fopen');
    var read = Module.findGlobalExportByName('read');
    var fgets = Module.findGlobalExportByName('fgets');
    var fread = Module.findGlobalExportByName('fread');
    var fileno = Module.findGlobalExportByName('fileno');
    if (!openat || !read) { log('T3: maps filter unavailable (openat/read missing)'); return false; }
    maps_filter_enabled = true;
    Interceptor.attach(openat, {
        onEnter: function (args) { try { this.path = args[1].readCString(); } catch (e) { this.path = null; } },
        onLeave: function (retval) {
            if (!retval.isNull() && is_maps_path(this.path)) {
                maps_filter_fds[retval.toInt32()] = true;
                log('T3: tracked openat maps fd=' + retval.toInt32());
            }
        }
    });
    Interceptor.attach(read, {
        onEnter: function (args) {
            this.fd = args[0].toInt32();
            this.buf = args[1];
        },
        onLeave: function (retval) {
            if (maps_filter_fds[this.fd] && retval.toInt32() > 0) redact_maps_buffer(this.buf, retval.toInt32());
        }
    });
    if (fopen && fileno) {
        var filenoFn = new NativeFunction(fileno, 'int', ['pointer']);
        Interceptor.attach(fopen, {
            onEnter: function (args) { try { this.path = args[0].readCString(); } catch (e) { this.path = null; } },
            onLeave: function (retval) {
                if (!retval.isNull() && is_maps_path(this.path)) {
                    var fd = filenoFn(retval);
                    maps_filter_streams[retval.toString()] = true;
                    maps_filter_fds[fd] = true;
                    log('T3: tracked fopen maps fd=' + fd);
                }
            }
        });
    }
    if (fgets) {
        Interceptor.attach(fgets, {
            onEnter: function (args) { this.buf = args[0]; this.stream = args[2].toString(); },
            onLeave: function (retval) {
                if (!retval.isNull() && maps_filter_streams[this.stream]) redact_maps_buffer(this.buf, 4096);
            }
        });
    }
    if (fread) {
        Interceptor.attach(fread, {
            onEnter: function (args) { this.buf = args[0]; this.size = args[1].toInt32(); this.nmemb = args[2].toInt32(); this.stream = args[3].toString(); },
            onLeave: function (retval) {
                if (maps_filter_streams[this.stream]) redact_maps_buffer(this.buf, retval.toInt32() * this.size);
            }
        });
    }
    log('T3: /proc/*/maps openat/read/fopen filter installed');
    return true;
}

// 定位 app 的 PathClassLoader（能加载壳 wrapper 类的那个）：
// 壳用自定义 ClassLoader 加载解密 dex，其类调用 System.loadLibrary 时
// ART 找不到对应 namespace，回退 boot 命名空间导致依赖解析失败（mp22 实测）。
// 把 nativeLoad 的 loader 参数换回 app PathClassLoader 即可进入 classloader-namespace。
function find_app_loader() {
    if (app_loader !== null) return app_loader;
    var probed = 0;
    try {
        var loaders = Java.enumerateClassLoadersSync();
        for (var i = 0; i < loaders.length && !app_loader; i++) {
            var l = loaders[i];
            try {
                var cn = l.getClass().getName();
                if (cn.indexOf('ClassLoader') === -1) continue;
                probed++;
                l.loadClass('com.secneo.apkwrapper.H');   // 必须真正能加载, 不接受失败兜底
                app_loader = l;
            } catch (e) { }
        }
    } catch (e) { }
    if (app_loader) {
        fc_loader_wrapper = app_loader;
        log('B2: app loader(verified) = ' + app_loader.getClass().getName() + ' probes=' + probed);
    } else {
        log('B2: no verified app loader (probed=' + probed + ')');
    }
    return app_loader;
}

// ---------- A. native 线程入口压制 ----------
// 不替换 pthread_create 本身：该做法会改变 ART/壳的线程创建语义并诱发早期崩溃。
// 保留线程创建，只把壳模块线程入口改成空函数，避免破坏 pthread 内部状态。
if (!MP34_MINIMAL) (function () {
    var pthread_create_addr = Module.findGlobalExportByName('pthread_create');
    if (!pthread_create_addr) { log('!! pthread_create not found'); return; }
    var inert_thread = new NativeCallback(function (arg) { return ptr(0); }, 'pointer', ['pointer']);
    Interceptor.attach(pthread_create_addr, {
        onEnter: function (args) {
            stats.pthread_total++;
            try {
                var start = args[2];
                var mod = Process.findModuleByAddress(start);
                if (mod !== null) {
                    var nm = mod.name;
                    if (!stats.modules[nm]) stats.modules[nm] = 0;
                    stats.modules[nm]++;
                    for (var i = 0; i < SHELL_SOS.length; i++) {
                        if (nm.indexOf(SHELL_SOS[i]) !== -1) {
                            stats.pthread_suppressed++;
                            args[2] = inert_thread;
                            log('A: shell thread entry replaced: ' + nm);
                            break;
                        }
                    }
                }
            } catch (e) { log('A: pthread inspect fail: ' + e); }
        }
    });
    log('A: pthread_create 观察 + 壳线程入口压制已装');
})();

// ---------- A5(v6). 全局 JNIEnv 表 FindClass 失败修复（窗口化） ----------
// mp23 实测: 壳 JNI_OnLoad 内前置 JNI 调用失败产生 pending exception，后续 FindClass
// 入口命中 ART CHECK("No pending exception expected") 直接 abort。
// 修法: ART 所有 JNIEnv 共享同一张 JNINativeInterface 表 → 脚本加载期改写 slot6(FindClass):
//   仅在 libDexHelper 的 JNI_OnLoad 执行窗口(gate)内:
//     a) 调原 FindClass 前 ExceptionClear(slot17) —— 拆 abort 引信
//     b) 原调用返回 NULL → ExceptionClear → 斜杠类名转点分 → app PathClassLoader.loadClass
//        代解析 → 返回局部引用（JNI_OnLoad 帧内有效）
// 旧版教训(F1x): JNI_OnLoad 的 arg0 是 JavaVM* 不是 JNIEnv*，不能对 arg0 克隆改写。
var findclass_gate = false;
var findclass_in_fallback = false;
var fc_orig = null;
var fc_loader_wrapper = null;
var stats_fc = { calls: 0, nulls: 0, fixed: 0, cleared: 0 };

function env_clear_exc(e) {
    var fn = e.readPointer().add(Process.pointerSize * 17).readPointer(); // slot17 ExceptionClear
    new NativeFunction(fn, 'void', ['pointer'])(e);
}

if (!MP34_MINIMAL) (function install_global_findclass_fix() {
    Java.perform(function () {
        try {
            var env = Java.vm.getEnv();
            var table = env.handle.readPointer();
            var slot = table.add(Process.pointerSize * 6);
            var origPtr = slot.readPointer();
            fc_orig = new NativeFunction(origPtr, 'pointer', ['pointer', 'pointer']);
            var fix = new NativeCallback(function (e, namePtr) {
                var name = '';
                try { name = namePtr.readCString(); } catch (err) { }
                if (!findclass_gate || findclass_in_fallback) return fc_orig(e, namePtr);
                stats_fc.calls++;
                try { env_clear_exc(e); stats_fc.cleared++; } catch (err) { }  // 拆 abort 引信
                var ret = fc_orig(e, namePtr);
                if (!ret.isNull()) return ret;
                stats_fc.nulls++;
                try { env_clear_exc(e); } catch (err) { }
                var fixedHandle = null;
                try {
                    findclass_in_fallback = true;
                    Java.performNow(function () {
                        try {
                            if (!fc_loader_wrapper) fc_loader_wrapper = find_app_loader();
                            if (!fc_loader_wrapper) return;
                            var dot = name.replace(/\//g, '.');
                            var cls = fc_loader_wrapper.loadClass.overload('java.lang.String')
                                          .call(fc_loader_wrapper, dot);
                            fixedHandle = cls ? cls.$handle : null;
                        } finally { findclass_in_fallback = false; }
                    });
                } catch (err) {
                    findclass_in_fallback = false;
                    log('A5: loadClass fallback fail(' + name + '): ' + err);
                }
                if (fixedHandle && !fixedHandle.isNull()) {
                    stats_fc.fixed++;
                    log('A5: FindClass(' + name + ') NULL -> FIXED via app loader');
                    return fixedHandle;
                }
                log('A5: FindClass(' + name + ') NULL, fallback unavailable');
                return ret;
            }, 'pointer', ['pointer', 'pointer']);
            // mp33 实验: 表补丁默认禁用 —— 壳可能对 JNIEnv 表做完整性校验，
            // 表补丁疑似 tamper 触发器；保留 A5b 对 libart FindClass 入口的内联钩。
            if (globalThis.A5_TABLE_PATCH === true) {
                Memory.protect(table, Process.pointerSize * 8, 'rw-');
                slot.writePointer(fix);
                log('A5: 全局 JNIEnv FindClass 修复已装 table=' + table + ' orig=' + origPtr);
            } else {
                log('A5: 表补丁禁用(A5_TABLE_PATCH=false), 依赖 A5b 入口内联钩');
            }
        } catch (e) {
            log('A5: install fail: ' + e);
        }
        // maps 过滤（T3 资产）提前自装，降低壳扫 frida 的检测面
        try { install_maps_filter(); } catch (err) { log('A5: maps filter auto-install skip: ' + err); }
    });
})();

// ---------- A5b(v7). libart FindClass 直呼入口拦截 ----------
// mp24 证据: vtable 槽位补丁未拦截壳的 FindClass —— 壳绕过 JNIEnv 表直接按地址调用
// libart 内部符号 art::JNI<false>::FindClass（栈帧 #11/#12 为 libDexHelper 匿名段）。
// 本设备 logcat 栈: pc 0x466254 = FindClass+1012 → 函数入口 = libart.base + 0x465E60。
if (!MP34_MINIMAL) (function () {
    try {
        var libart = Process.findModuleByName('libart.so');
        if (!libart) { log('A5b: libart not found'); return; }
        var entry = libart.base.add('0x465E60');
        var head = entry.readU32();
        var okPrologue = (head === 0xD503233F) ||            // paciasp
                         ((head >>> 24) === 0xA9) ||         // stp x.. , [sp,#-..]!
                         ((head >>> 24) === 0xD1);           // sub sp, #..
        if (!okPrologue) {
            log('A5b: 入口字节不像函数序言(head=0x' + head.toString(16) + ')，跳过挂载');
            return;
        }
        Interceptor.attach(entry, {
            onEnter: function (args) {
                this.e = args[0];
                this.np = args[1];
                if (findclass_gate) {
                    try { env_clear_exc(args[0]); stats_fc.cleared++; } catch (err) { }
                }
            },
            onLeave: function (retval) {
                if (!findclass_gate || !retval.isNull()) return;
                var name = '';
                try { name = this.np.readCString(); } catch (err) { return; }
                stats_fc.nulls++;
                try { env_clear_exc(this.e); } catch (err) { }
                var fixedHandle = null;
                try {
                    findclass_in_fallback = true;
                    Java.performNow(function () {
                        try {
                            if (!fc_loader_wrapper) fc_loader_wrapper = find_app_loader();
                            if (!fc_loader_wrapper) return;
                            var dot = name.replace(/\//g, '.');
                            var cls = fc_loader_wrapper.loadClass.overload('java.lang.String')
                                          .call(fc_loader_wrapper, dot);
                            fixedHandle = cls ? cls.$handle : null;
                        } finally { findclass_in_fallback = false; }
                    });
                } catch (err) {
                    findclass_in_fallback = false;
                    log('A5b: loadClass fallback fail(' + name + '): ' + err);
                }
                if (fixedHandle && !fixedHandle.isNull()) {
                    stats_fc.fixed++;
                    log('A5b: direct FindClass(' + name + ') NULL -> FIXED via app loader');
                    retval.replace(ptr(fixedHandle));
                } else {
                    log('A5b: direct FindClass(' + name + ') NULL, fallback unavailable');
                }
            }
        });
        log('A5b: libart FindClass 直呼入口已挂 @' + entry + ' head=0x' + head.toString(16));
    } catch (e) {
        log('A5b: install fail: ' + e);
    }
})();

// JNI_OnLoad 窗口观测（模块观察器 + dlsym 兜底），onEnter/onLeave 维护 gate
if (!MP34_MINIMAL) (function () {
    var hooked = {};
    function attach_onload(address, source) {
        if (!address || address.isNull()) return;
        var stable = ptr(address.toString());
        var key = 'onload:' + stable.toString();
        if (hooked[key]) return;
        hooked[key] = true;
        stats.jni_onload_observed++;
        try {
            // 模块观察器可能看到重映射前的同名区域；以实际 JNI_OnLoad 地址反推入口。
            var actualStageEntry = stable.sub(0x14884).add(0x1e33c);
            var actualStageKey = actualStageEntry.toString();
            if (stage_entry_observer_address !== actualStageKey) {
                Interceptor.attach(actualStageEntry, {
                    onEnter: function (args) {
                        var env = args[0];
                        var range = null;
                        try { range = Process.findRangeByAddress(env); } catch (e) { }
                        log('X: actual sub_1E33C enter env=' + env + ' envRange=' +
                            (range ? JSON.stringify({ base: range.base.toString(), size: range.size,
                                protection: range.protection, file: range.file ? range.file.path : null }) : 'none'));
                    }
                });
                stage_entry_observer_address = actualStageKey;
                log('X: actual sub_1E33C observer installed @' + actualStageEntry + ' source=' + source);
            }
            Interceptor.attach(stable, {
                onEnter: function (args) {
                    findclass_gate = true;
                    install_getenv_observer(args[0]);
                    log('A5: JNI_OnLoad enter(gate=ON) vm=' + args[0] + ' reserved=' + args[1]);
                },
                onLeave: function (retval) {
                    findclass_gate = false;
                    log('A5: JNI_OnLoad leave(gate=OFF) ret=0x' + retval.toString(16));
                }
            });
            log('A5: JNI_OnLoad observer attached @' + stable + ' source=' + source);
        } catch (e) { log('A5: JNI_OnLoad attach fail: ' + e); }
    }
    attach_jni_onload_observer = attach_onload;
    try {
        Process.attachModuleObserver({
            onAdded: function (module) {
                if (module.name !== 'libDexHelper.so') return;
                try {
                    if (!stage_entry_observer_installed) {
                        var stageEntry = module.base.add(0x1e33c);
                        Interceptor.attach(stageEntry, {
                            onEnter: function (args) {
                                var env = args[0];
                                var range = null;
                                try { range = Process.findRangeByAddress(env); } catch (e) { }
                                log('X: sub_1E33C enter env=' + env + ' envRange=' +
                                    (range ? JSON.stringify({ base: range.base.toString(), size: range.size,
                                        protection: range.protection, file: range.file ? range.file.path : null }) : 'none'));
                            }
                        });
                        stage_entry_observer_installed = true;
                        log('X: sub_1E33C observer installed @' + stageEntry);
                    }
                    var exps = module.enumerateExports();
                    for (var i = 0; i < exps.length; i++) {
                        if (exps[i].name === 'JNI_OnLoad') {
                            log('A5: libDexHelper module observed @' + module.base);
                            attach_onload(exps[i].address, 'module-observer');
                            break;
                        }
                    }
                } catch (e) { log('A5: module export scan fail: ' + e); }
            }
        });
        log('A5: module observer installed');
    } catch (e) { log('A5: module observer unavailable: ' + e); }
    var dlsym = Module.findGlobalExportByName('dlsym');
    if (dlsym) {
        Interceptor.attach(dlsym, {
            onEnter: function (args) {
                try { this.name = args[1].readCString(); } catch (e) { this.name = ''; }
            },
            onLeave: function (retval) {
                if (this.name !== 'JNI_OnLoad' || retval.isNull()) return;
                attach_onload(ptr(retval.toString()), 'dlsym');
            }
        });
        log('A5: dlsym JNI_OnLoad observer installed');
    }
})();

// ---------- A2/A3. native 自杀出口: 观察 + shell 来源定点拦截 ----------
// mp21 证实 retval.replace 挡不住信号（raise/tgkill 已发出）；这里改为 onEnter 阶段
// 直接改写 pc 到 "mov x0,0; ret" 存根，原调用体被整体跳过 —— 信号根本不会发出。
// 仅当调用方模块 ∈ SHELL_SOS 时生效；ART/libbacktrace 的正常调用原样放行。
if (!MP34_MINIMAL) (function () {
    var stub = Memory.alloc(Process.pageSize);
    Memory.protect(stub, Process.pageSize, 'rwx');
    stub.writeU32(0xAA1F03E0);          // mov x0, xzr
    stub.add(4).writeU32(0xD65F03C0);   // ret

    function isShellCaller(retAddr) {
        try {
            var m = Process.findModuleByAddress(retAddr);
            if (!m) return false;
            for (var i = 0; i < SHELL_SOS.length; i++)
                if (m.name.indexOf(SHELL_SOS[i]) !== -1) return true;
        } catch (e) { }
        return false;
    }

    var names = ['abort', '__stack_chk_fail', 'raise', 'kill', 'tgkill', 'pthread_kill', 'exit', '_exit'];
    names.forEach(function (name) {
        var addr = Module.findGlobalExportByName(name);
        if (!addr) return;
        try {
            Interceptor.attach(addr, {
                onEnter: function (args) {
                    var sig = null;
                    try {
                        if (name === 'raise' || name === 'pthread_kill') sig = args[0].toInt32();
                        else if (name === 'kill') sig = args[1].toInt32();
                        else if (name === 'tgkill') sig = args[2].toInt32();
                    } catch (e) { }
                    var caller = Process.findModuleByAddress(this.returnAddress);
                    var callerName = caller ? caller.name : 'unknown';
                    this.block = isShellCaller(this.returnAddress);
                    log('A2: ' + name + (sig === null ? '' : '(' + sig + ')') +
                        ' caller=' + callerName + ' @' + describe_native_address(this.returnAddress) +
                        (this.block ? ' [BLOCKED]' : ''));
                    if (name === 'abort' || name === '__stack_chk_fail') {
                        try {
                            var bt = Thread.backtrace(this.context, Backtracer.ACCURATE)
                                .slice(0, 12).map(describe_native_address).join('\n');
                            log('A2: ' + name + ' backtrace:\n' + bt);
                        } catch (e) { log('A2: backtrace unavailable ' + e); }
                    }
                    if (this.block) {
                        stats.native_signal_blocked++;
                        this.context.pc = stub;   // 跳过原函数体: 信号不发出 / 不退出
                    }
                }
            });
        } catch (e) { log('A2: hook ' + name + ' fail: ' + e); }
    });
    log('A2/A3: native exit/signal 观察 + shell 来源 pc-redirect 拦截已装');
})();

// ---------- A4. android_dlopen_ext 观察（壳 so 加载序列） ----------
if (!MP34_MINIMAL) (function () {
    var dlopen = Module.findGlobalExportByName('android_dlopen_ext');
    if (!dlopen) return;
    Interceptor.attach(dlopen, {
        onEnter: function (args) {
            try { this.path = args[0].readCString(); } catch (e) { this.path = null; }
            var caller = Process.findModuleByAddress(this.returnAddress);
            this.caller = caller ? caller.name : '?';
        },
        onLeave: function (retval) {
            if (this.path) {
                var tail = this.path;
                var slash = tail.lastIndexOf('/');
                if (slash !== -1) tail = tail.substring(slash + 1);
                log('A4: dlopen_ext(' + tail + ') caller=' + this.caller +
                    ' ret=' + (retval.isNull() ? 'NULL' : 'ok'));
            }
        }
    });
    log('A4: android_dlopen_ext 观察已装');
})();

// ---------- C 的字节读取工具 ----------
function jbytearray_to_arraybuffer(jarr) {
    var N = jarr.length;
    var ByteBuffer = Java.use('java.nio.ByteBuffer');
    var bb = ByteBuffer.allocateDirect(N);
    bb.put(jarr);
    var addr = bb.address.value;
    return ptr(addr).readByteArray(N);
}

function jbuffer_to_arraybuffer(buf) {
    // 只读副本，不动原 position
    var dup = buf.duplicate();
    var N = dup.remaining();
    if (N <= 0) return null;
    var addr = dup.address.value;
    if (addr !== null && !addr.isNull() && !ptr(0).equals(ptr(addr))) {
        return ptr(addr).add(dup.position()).readByteArray(N);   // direct buffer
    }
    try {
        return jbytearray_to_arraybuffer(dup.array());           // heap buffer
    } catch (e) {
        // 逐字节兜底（慢，仅小缓冲）
        var out = new ArrayBuffer(N);
        var u8 = new Uint8Array(out);
        for (var i = 0; i < N; i++) u8[i] = dup.get() & 0xff;
        return out;
    }
}

function short_stack() {
    try {
        return Java.use('android.util.Log').getStackTraceString(
            Java.use('java.lang.Exception').$new()).split('\n').slice(0, 25).join('\n');
    } catch (e) { return '(no stack)'; }
}

// 运行日志会落盘；只保留加载参数的类型、长度与文件名尾部，避免把潜在敏感内容写入证据。
function dex_arg_summary(a) {
    try {
        if (a === null || a === undefined) return 'null';
        if (typeof a === 'string') {
            var primitive = a;
            var primitiveSlash = Math.max(primitive.lastIndexOf('/'), primitive.lastIndexOf('\\\\'));
            return 'str[len=' + primitive.length + ',tail=' + primitive.substring(primitiveSlash + 1) + ']';
        }
        var cn = a.$className || '';
        if (cn === 'java.lang.String') {
            var s = String(a);
            var slash = Math.max(s.lastIndexOf('/'), s.lastIndexOf('\\\\'));
            return 'str[len=' + s.length + ',tail=' + s.substring(slash + 1) + ']';
        }
        if (cn === '[B') return 'byte[len=' + a.length + ']';
        if (cn === 'java.nio.ByteBuffer') return 'ByteBuffer';
        if (cn === '[Ljava.nio.ByteBuffer;') return 'ByteBuffer[len=' + a.length + ']';
        if (typeof a === 'number') return 'num=' + a;
        return cn || typeof a;
    } catch (e) {
        return 'unavailable';
    }
}

function send_dex(ab, meta) {
    stats.dex_captured++;
    var u8 = new Uint8Array(ab);
    var magicOk = (u8[0] === 0x64 && u8[1] === 0x65 && u8[2] === 0x78 && u8[3] === 0x0a);
    log('C: 捕获缓冲 #' + stats.dex_captured + ' size=' + ab.byteLength +
        ' magic=' + magicOk + ' src=' + meta.src);
    var payload = { type: 'dex', size: ab.byteLength, magic: magicOk };
    for (var k in meta) payload[k] = meta[k];
    send(payload, ab);
}

// E3: 只读采样已加载业务类的 Java 反射元数据；不调用业务方法。
function dump_loaded_reflection() {
    if (!MP34_REFLECTION_DUMP) return;
    try {
        var names = Java.enumerateLoadedClassesSync()
            .filter(function (n) { return n.indexOf('com.moutai.mall') === 0; })
            .sort();
        var emitted = 0;
        for (var i = 0; i < names.length; i++) {
            var name = names[i];
            var methods = [];
            try {
                var cls = Java.use(name).class;
                var declared = cls.getDeclaredMethods();
                for (var j = 0; j < declared.length; j++) {
                    var method = declared[j];
                    var item = { signature: String(method), annotations: [], parameterAnnotations: [] };
                    try {
                        var anns = method.getDeclaredAnnotations();
                        for (var ai = 0; ai < anns.length; ai++) item.annotations.push(String(anns[ai]));
                    } catch (e1) { item.annotations.push('(annotations failed: ' + e1 + ')'); }
                    try {
                        var pann = method.getParameterAnnotations();
                        for (var pi = 0; pi < pann.length; pi++) {
                            var one = [];
                            for (var pj = 0; pj < pann[pi].length; pj++) one.push(String(pann[pi][pj]));
                            item.parameterAnnotations.push(one);
                        }
                    } catch (e2) { item.parameterAnnotations.push(['(parameter annotations failed: ' + e2 + ')']); }
                    methods.push(item);
                }
            } catch (e) { methods.push('(reflection failed: ' + e + ')'); }
            send({ type: 'reflection', className: name, methods: methods });
            emitted++;
        }
        log('E3: reflection metadata emitted classes=' + emitted);
    } catch (e) { log('E3: reflection dump failed: ' + e); }
}

// ---------- B + C: Java hooks ----------
log('B+C: scheduling Java.performNow/perform, available=' + (typeof Java !== 'undefined' && Java ? Java.available : 'NO-JAVA'));
function install_java_hooks() {
    log('B+C: Java.perform entered');
    // B. 阻断自杀出口
    if (!MP34_MINIMAL) try {
        var System = Java.use('java.lang.System');
        System.exit.overload('int').implementation = function (code) {
            stats.exit_blocked++;
            log('B: System.exit(' + code + ') 已阻断 #' + stats.exit_blocked + '\n' + short_stack());
            return;
        };
    } catch (e) { log('B: System.exit hook fail: ' + e); }
    if (!MP34_MINIMAL) try {
        var Runtime = Java.use('java.lang.Runtime');
        ['exit', 'halt'].forEach(function (m) {
            Runtime[m].overload('int').implementation = function (code) {
                stats.exit_blocked++;
                log('B: Runtime.' + m + '(' + code + ') 已阻断\n' + short_stack());
                return;
            };
        });
    } catch (e) { log('B: Runtime hook fail: ' + e); }
    if (!MP34_MINIMAL) try {
        var Proc = Java.use('android.os.Process');
        Proc.killProcess.overload('int').implementation = function (pid) {
            stats.exit_blocked++;
            log('B: Process.killProcess(' + pid + ') 已阻断\n' + short_stack());
            return;
        };
    } catch (e) { log('B: Process.killProcess hook fail: ' + e); }

    // B2. 记录壳在 instantiateClassLoader 阶段加载的 native 库，便于把
    // LoadNativeLibrary 崩溃与具体 so 对上；默认不修改返回值。
    try {
        var System = Java.use('java.lang.System');
        ['load', 'loadLibrary'].forEach(function (mn) {
            System[mn].overloads.forEach(function (ov) {
                ov.implementation = function () {
                    try { log('B2: System.' + mn + '(' + String(arguments[0]) + ')'); } catch (e) { }
                    return ov.apply(this, arguments);
                };
            });
        });
    } catch (e) { log('B2: System.load hook fail: ' + e); }
    try {
        var Runtime2 = Java.use('java.lang.Runtime');
        ['load', 'loadLibrary', 'nativeLoad'].forEach(function (mn) {
            var ovs;
            try { ovs = Runtime2[mn].overloads; } catch (e) { return; }
            ovs.forEach(function (ov) {
                ov.implementation = function () {
                    try {
                        var a0 = arguments.length ? String(arguments[0]) : '';
                        log('B2: Runtime.' + mn + '(' + a0 + ')');
                        // 该设备的 MIUI ForceDark 辅助库缺失；它与样本无关，
                        // 但会在应用绑定阶段把进程带入 UnsatisfiedLinkError。
                        // 仅对这个精确库名短路 nativeLoad，其他 so 不改动。
                        if (mn === 'nativeLoad' && a0.indexOf('libforcedarkimpl.so') !== -1) {
                            log('B2: bypass missing MIUI libforcedarkimpl.so');
                            return null;
                        }
                        if (mn === 'nativeLoad' && native_redirect_path &&
                            a0.indexOf('libDexHelper.so') !== -1 && a0.indexOf('/') === -1) {
                            // mp21 实测: 壳按库名 System.loadLibrary("DexHelper") 在本设备
                            // namespace 下解析失败；手工 dlopen+JNI_OnLoad 会 abort。
                            // mp23 修法: 改写实参为绝对路径 + loader 换 app PathClassLoader，
                            // 让 ART 在 classloader-namespace 内完成 dlopen/依赖解析/JNI_OnLoad。
                            log('B2: rewrite nativeLoad arg "' + a0 + '" -> ' + native_redirect_path);
                            arguments[0] = native_redirect_path;
                            // v8: 正确 loader = nativeLoad 第三参 caller 类自身的 ClassLoader
                            // (mp25 实测: find_app_loader 误选壳的 DexPathList["."] 加载器,
                            //  导致 JNI_OnLoad 内 FindClass(H/AW) 全部 NULL)
                            var ld = null;
                            try {
                                var callerCls = null;
                                for (var c = arguments.length - 1; c > 0; c--) {
                                    var cc = arguments[c];
                                    if (cc && cc.$className === 'java.lang.Class') { callerCls = cc; break; }
                                }
                                if (callerCls) {
                                    var cl = callerCls.getClassLoader();
                                    if (cl) {
                                        cl.loadClass('com.secneo.apkwrapper.H');  // 校验
                                        ld = cl;
                                        fc_loader_wrapper = cl;
                                        log('B2: caller(H) classloader verified');
                                    }
                                }
                            } catch (e1) { }
                            if (!ld) ld = find_app_loader();
                            if (ld) {
                                for (var k = 1; k < arguments.length; k++) {
                                    var t = ov.argumentTypes[k];
                                    if (t && t.className === 'java.lang.ClassLoader') {
                                        arguments[k] = ld;
                                        log('B2: nativeLoad loader arg[' + k + '] -> app PathClassLoader');
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (e) { }
                    try {
                        var ret = ov.apply(this, arguments);
                        if (mn === 'nativeLoad') {
                            if (ret === null || ret === undefined) {
                                log('B2: Runtime.nativeLoad result=success(null)');
                            } else {
                                var diag = String(ret);
                                var tail = diag.length > 180 ? diag.slice(-180) : diag;
                                log('B2: Runtime.nativeLoad result=error[len=' +
                                    diag.length + ',tail=' + tail + ']');
                            }
                        }
                        return ret;
                    } catch (e) {
                        if (mn === 'nativeLoad') {
                            var kind = e && e.$className ? e.$className : 'Java/unknown';
                            log('B2: Runtime.nativeLoad threw ' + kind);
                        }
                        throw e;
                    }
                };
            });
        });
    } catch (e) { log('B2: Runtime load hook fail: ' + e); }

    // B3. 设备侧 MIUI ForceDark native 绑定点。
    // 仅短路 libforcedarkimpl.so 不够；系统可能继续调用多个 native 方法。
    try {
        var MiuiForceDark = Java.use('android.graphics.MiuiForceDarkConfigManagerImpl');
        var forceDarkMethods = ['nativeInit', 'nativeSetConfig'];
        var forceDarkHooked = 0;
        forceDarkMethods.forEach(function (methodName) {
            try {
                if (!MiuiForceDark[methodName]) return;
                MiuiForceDark[methodName].overloads.forEach(function (ov) {
                ov.implementation = function () {
                        try { log('B3: MiuiForceDarkConfigManagerImpl.' + methodName + ' bypassed'); } catch (e) { }
                        return (ov.returnType && ov.returnType.type === 'void') ? undefined : 0;
                    };
                    forceDarkHooked++;
                });
            } catch (e) {
                log('B3: ' + methodName + ' unavailable: ' + e);
            }
        });
        if (forceDarkHooked) {
            log('B3: MiuiForceDark native hooks installed (' + forceDarkHooked + ' overloads)');
        } else {
            log('B3: no MiuiForceDark native overloads found');
        }
    } catch (e) { log('B3: MiuiForceDarkConfigManagerImpl hook fail: ' + e); }

    // B4. 观测 UncaughtExceptionHandler 安装者（风控/壳常自装 handler 做自杀收尾）
    if (!MP34_MINIMAL) try {
        var ThreadCls = Java.use('java.lang.Thread');
        ThreadCls.setDefaultUncaughtExceptionHandler.overload(
            'java.lang.Thread$UncaughtExceptionHandler').implementation = function (h) {
            try {
                log('B4: setDefaultUncaughtExceptionHandler <- ' +
                    (h === null ? 'null' : h.getClass().getName()));
            } catch (e) { }
            return this.setDefaultUncaughtExceptionHandler(h);
        };
        log('B4: UEH observer installed');
    } catch (e) { log('B4: UEH hook fail: ' + e); }

    // E1. RegisterNatives 观察（JNI 函数表 index 215）——壳注册的 native 方法清单
    // 是后续定位签名原语的直接线索；仅记录名称/签名/函数所在模块，不改动注册结果。
    try {
        var env = Java.vm.getEnv();
        var vtable = env.handle.readPointer();
        var regNat = vtable.add(215 * Process.pointerSize).readPointer();
        Interceptor.attach(regNat, {
            onEnter: function (args) {
                stats.regnat_count++;
                var count = args[3].toInt32();
                var caller = Process.findModuleByAddress(this.returnAddress);
                var recs = [];
                try {
                    for (var i = 0; i < Math.min(count, 64); i++) {
                        var m = args[2].add(i * Process.pointerSize * 3);
                        var nm = m.readPointer().readCString();
                        var sg = m.add(Process.pointerSize).readPointer().readCString();
                        var fn = m.add(Process.pointerSize * 2).readPointer();
                        var mod = Process.findModuleByAddress(fn);
                        recs.push(nm + sg + '@' + (mod ? mod.name + '+' + fn.sub(mod.base).toString(16) : fn.toString()));
                    }
                } catch (e) { recs.push('(read fail: ' + e + ')'); }
                log('E1: RegisterNatives #' + stats.regnat_count + ' count=' + count +
                    ' caller=' + (caller ? caller.name : '?') + ' ' + recs.join(' | '));
                send({ type: 'regnat', seq: stats.regnat_count, count: count,
                       caller: caller ? caller.name : '?', methods: recs });
            }
        });
        log('E1: RegisterNatives 观察已装');
    } catch (e) { log('E1: RegisterNatives hook fail: ' + e); }

    // E2. 类加载时间线：业务类从 0 变非 0 的时刻 = 解密加载成功的时刻
    var lastClassCount = '';
    setInterval(function () {
        Java.perform(function () {
            try {
                var all = Java.enumerateLoadedClassesSync();
                var app = 0;
                for (var i = 0; i < all.length; i++)
                    if (all[i].indexOf('com.moutai.mall') === 0) app++;
                var key = all.length + '/' + app;
                if (key !== lastClassCount) {
                    lastClassCount = key;
                    log('E2: classes total=' + all.length + ' app(com.moutai.mall*)=' + app);
                }
            } catch (e) { }
        });
    }, 3000);
    log('E2: 类加载时间线已装 (3s)');

    // C1. InMemoryDexClassLoader
    try {
        var IMDCL = Java.use('dalvik.system.InMemoryDexClassLoader');
        IMDCL.$init.overloads.forEach(function (ov) {
            ov.implementation = function () {
                var args = Array.prototype.slice.call(arguments);
                for (var i = 0; i < args.length; i++) {
                    var a = args[i];
                    try {
                        if (a && a.$className === 'java.nio.ByteBuffer') {
                            var ab = jbuffer_to_arraybuffer(a);
                            if (ab) send_dex(ab, { src: 'IMDCL.ByteBuffer', ov: ov.signature });
                        } else if (a && a.$className === '[Ljava.nio.ByteBuffer;') {
                            for (var k = 0; k < a.length; k++) {
                                var ab2 = jbuffer_to_arraybuffer(a[k]);
                                if (ab2) send_dex(ab2, { src: 'IMDCL.ByteBuffer[' + k + ']', ov: ov.signature });
                            }
                        }
                    } catch (e) {
                        log('C1: capture fail: ' + e);
                    }
                }
                return ov.apply(this, arguments);
            };
        });
        log('C1: InMemoryDexClassLoader hooks installed (' + IMDCL.$init.overloads.length + ' overloads)');
    } catch (e) { log('C1: IMDCL hook fail: ' + e); }

    // C2. DexFile 静态加载方法（直接按已知方法名挂，避免反射数组迭代兼容问题）
    try {
        var DexFile = Java.use('dalvik.system.DexFile');
        ['openInMemoryDexFile', 'openInMemoryDexFiles', 'loadDex', 'openDexFile'].forEach(function (mn) {
            var ovList;
            try { ovList = DexFile[mn].overloads; } catch (e) { return; }
            ovList.forEach(function (ov) {
                ov.implementation = function () {
                    var args = Array.prototype.slice.call(arguments);
                    var summaries = [];
                    for (var i = 0; i < args.length; i++) {
                        var a = args[i];
                        summaries.push(dex_arg_summary(a));
                        try {
                            if (a && a.$className === 'java.nio.ByteBuffer') {
                                var ab = jbuffer_to_arraybuffer(a);
                                if (ab) send_dex(ab, { src: 'DexFile.' + mn });
                            } else if (a && a.$className === '[B') {
                                var ab2 = jbytearray_to_arraybuffer(a);
                                if (ab2) send_dex(ab2, { src: 'DexFile.' + mn + '.byte[]' });
                            }
                        } catch (e) { }
                    }
                    log('C2: DexFile.' + mn + ' called args=[' + summaries.join(', ') + ']');
                    return ov.apply(this, arguments);
                };
            });
            log('C2: DexFile.' + mn + ' hooked (' + ovList.length + ' overloads)');
        });
    } catch (e) { log('C2: DexFile hook fail: ' + e); }

    // C3. DexClassLoader 记录路径
    try {
        var DCL = Java.use('dalvik.system.DexClassLoader');
        DCL.$init.overloads.forEach(function (ov) {
            ov.implementation = function (dexPath, opt, libPath, parent) {
                log('C3: DexClassLoader path=' + dexPath + ' opt=' + opt);
                send({ type: 'dcl', dexPath: '' + dexPath, optimizedDirectory: '' + opt });
                return ov.call(this, dexPath, opt, libPath, parent);
            };
        });
        log('C3: DexClassLoader hooks installed');
    } catch (e) { log('C3: DCL hook fail: ' + e); }

    if (MP34_REFLECTION_DUMP) {
        setTimeout(function () {
            try { Java.perform(dump_loaded_reflection); }
            catch (e) { log('E3: deferred reflection failed: ' + e); }
        }, 350);
    }
}
try {
    if (Java.performNow) Java.performNow(install_java_hooks);
    else Java.perform(install_java_hooks);
} catch (e) {
    log('B+C: immediate Java hook failed: ' + e);
    try { Java.perform(install_java_hooks); } catch (e2) { log('B+C: deferred Java hook failed: ' + e2); }
}

// ---------- D. 兜底内存扫描 ----------
function build_ranges() {
    var ranges = [];
    var prots = ['r--', 'rw-'];
    for (var p = 0; p < prots.length; p++) {
        var rs = Process.enumerateRanges({ protection: prots[p], coalesce: true });
        for (var i = 0; i < rs.length; i++) {
            var r = rs[i];
            if (r.file) {
                var fp = r.file.path || '';
                if (fp.indexOf('/memfd:') !== 0 &&
                    fp.indexOf('/data/user/0/com.moutai.mall') !== 0 &&
                    fp.indexOf('/data/data/com.moutai.mall') !== 0) continue;  // 只留 memfd + app 数据文件
            }
            if (r.size > 1024 * 1024 * 1024) continue;
            if (r.size < 0x80) continue;
            ranges.push(r);
        }
    }
    return ranges;
}

function in_ranges(ranges, start, end) {
    for (var i = 0; i < ranges.length; i++) {
        if (start.compare(ranges[i].base) >= 0 && end.compare(ranges[i].base.add(ranges[i].size)) <= 0)
            return true;
    }
    return false;
}

function scan_dexes() {
    var ranges = build_ranges();
    log('D: scan ranges=' + ranges.length);
    var found = {}, order = [];
    for (var i = 0; i < ranges.length; i++) {
        var r = ranges[i];
        var matches;
        try { matches = Memory.scanSync(r.base, r.size, '64 65 78 0a'); } catch (e) { continue; }
        for (var m = 0; m < matches.length; m++) {
            try {
                var base = matches[m].address;
                var size = base.add(0x20).readU32();
                var header_size = base.add(0x24).readU32();
                var endian = base.add(0x28).readU32();
                var map_off = base.add(0x34).readU32();
                if (header_size !== 0x70 || endian !== 0x12345678) continue;
                if (size < 0x70 || size > 200 * 1024 * 1024) continue;
                if (map_off < 0x70 || map_off >= size) continue;
                if (!in_ranges(ranges, base, base.add(size))) continue;
                var key = base.toString() + '_' + size;
                if (found[key]) continue;
                found[key] = { base: base.toString(), size: size, protection: r.protection, map_off: map_off };
                order.push(found[key]);
            } catch (e) { }
        }
    }
    return order;
}

// ---------- D2. 进程存活期间的主动扫描 ----------
// 壳可能在 dex 尚未进入稳定映射前触发自毁；把兜底从 driver 退出后的
// 单次 RPC 提前到进程内执行，避免 process-terminated 后无法再读内存。
var live_emitted = {};
var live_scanning = false;
var live_scan_enabled = true;
function live_scan_once() {
    if (!live_scan_enabled) return;
    if (live_scanning) return;
    live_scanning = true;
    try {
        var ds = scan_dexes();
        for (var i = 0; i < ds.length; i++) {
            var d = ds[i];
            var key = d.base + '_' + d.size;
            if (live_emitted[key]) continue;
            try {
                var ab = ptr(d.base).readByteArray(d.size);
                if (ab) {
                    live_emitted[key] = true;
                    send_dex(ab, { src: 'live-memscan@' + d.base, protection: d.protection, map_off: d.map_off });
                }
            } catch (e) { log('D2: live read fail ' + key + ': ' + e); }
        }
    } catch (e) { log('D2: live scan fail: ' + e); }
    live_scanning = false;
}
if (!MP34_MINIMAL) {
    setTimeout(live_scan_once, 350);
    setInterval(live_scan_once, 900);
}

function native_module(name) {
    var mod = Process.findModuleByName(name);
    if (!mod) return null;
    return { name: mod.name, base: mod.base.toString(), size: mod.size };
}

function preload_native_no_jni(path) {
    try {
        var dlopen = Module.findGlobalExportByName('dlopen');
        if (!dlopen) return { handle: null, error: 'dlopen export unavailable' };
        var callDlopen = new NativeFunction(dlopen, 'pointer', ['pointer', 'int']);
        var handle = callDlopen(Memory.allocUtf8String(String(path)), 2); // RTLD_NOW
        if (handle.isNull()) return { handle: '0x0', error: 'dlopen returned NULL' };
        var mod = Process.findModuleByName('libDexHelper.so');
        var exportName = null;
        if (mod && attach_jni_onload_observer) {
            mod.enumerateExports().forEach(function (e) {
                if (e.name === 'JNI_OnLoad') {
                    exportName = e.name;
                    attach_jni_onload_observer(e.address, 'preload');
                }
            });
        }
        return { handle: handle.toString(), module: native_module('libDexHelper.so'), export: exportName };
    } catch (e) { return { handle: null, error: String(e) }; }
}

rpc.exports = {
    getStats: function () { stats.findclass_fix = stats_fc; return stats; },
    setNativeRedirect: function (path) {
        native_redirect_path = path ? String(path) : null;
        return native_redirect_path;
    },
    setLiveScan: function (enabled) {
        live_scan_enabled = !!enabled;
        return live_scan_enabled;
    },
    installMapsFilter: function () {
        return install_maps_filter();
    },
    listDexes: function () { return scan_dexes(); },
    readDex: function (baseStr, size, offset, chunk) {
        var readPtr = ptr(baseStr).add(offset);
        var thisSize = Math.min(chunk, size - offset);
        return readPtr.readByteArray(thisSize);
    },
    loadedClassesCount: function () {
        var n = 0;
        Java.perform(function () {
            Java.enumerateLoadedClassesSync().forEach(function () { n++; });
        });
        return n;
    },
    listLoadedClasses: function (prefix) {
        var out = [];
        var wanted = prefix ? String(prefix) : '';
        Java.perform(function () {
            Java.enumerateLoadedClassesSync().forEach(function (name) {
                if (!wanted || name.indexOf(wanted) === 0) out.push(name);
            });
        });
        return out;
    },
    getNativeInfo: function () {
        return native_module('libDexHelper.so');
    },
    preloadNative: function (path) {
        return preload_native_no_jni(path);
    },
    loadNativeAbsolute: function (path) {
        try {
            var dlopen = Module.findGlobalExportByName('dlopen');
            if (!dlopen) return { handle: null, error: 'dlopen export unavailable' };
            var callDlopen = new NativeFunction(dlopen, 'pointer', ['pointer', 'int']);
            var handle = callDlopen(Memory.allocUtf8String(String(path)), 2); // RTLD_NOW
            var err = null;
            if (handle.isNull()) {
                var dlerror = Module.findGlobalExportByName('dlerror');
                if (dlerror) {
                    var callDlerror = new NativeFunction(dlerror, 'pointer', []);
                    var ep = callDlerror();
                    if (ep && !ep.isNull()) err = ep.readCString();
                }
            }
            var mod = Process.findModuleByName('libDexHelper.so');
            return { handle: handle.toString(), error: err, module: native_module('libDexHelper.so') };
        } catch (e) {
            return { handle: null, error: String(e) };
        }
    },
    getNativeRanges: function () {
        var mod = Process.findModuleByName('libDexHelper.so');
        if (!mod) return [];
        var out = [];
        ['r--', 'r-x', 'rw-', 'rwx'].forEach(function (prot) {
            try {
                Process.enumerateRanges({ protection: prot, coalesce: false }).forEach(function (r) {
                    var end = r.base.add(r.size);
                    var modEnd = mod.base.add(mod.size);
                    if (r.base.compare(mod.base) >= 0 && end.compare(modEnd) <= 0) {
                        out.push({ base: r.base.toString(), size: r.size, protection: prot });
                    }
                });
            } catch (e) { }
        });
        return out;
    },
    readNative: function (baseStr, size, offset, chunk) {
        var mod = Process.findModuleByName('libDexHelper.so');
        if (!mod) return null;
        var requested = Math.min(chunk, size - offset);
        if (requested <= 0) return null;
        return mod.base.add(offset).readByteArray(requested);
    },
    listNativeExports: function () {
        var mod = Process.findModuleByName('libDexHelper.so');
        if (!mod) return [];
        return mod.enumerateExports().map(function (e) {
            return { type: e.type, name: e.name, offset: e.address.sub(mod.base).toString() };
        });
    }
};

log('v5 script loaded, hooks ready');
