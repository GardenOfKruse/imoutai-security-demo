/**
 * attach-class-audit.js — late-attach, read-only Java class / ClassLoader audit.
 * No hooks, no return-value changes, no native calls, no network observation.
 */
import Java from 'frida-java-bridge';

'use strict';

function emit(payload) {
    send(payload);
}

function audit_loaded_classes() {
    var result = { total_classes: 0, app_classes: [], loaders: [], error: null };
    var run = function () {
        try {
            var all = Java.enumerateLoadedClassesSync();
            result.total_classes = all.length;
            result.app_classes = all.filter(function (name) {
                return name.indexOf('com.moutai.mall') === 0;
            }).sort();
            Java.enumerateClassLoadersSync().forEach(function (loader) {
                try {
                    result.loaders.push({
                        class_name: String(loader.getClass().getName()),
                        text: String(loader)
                    });
                } catch (e) {
                    result.loaders.push({ class_name: '<unreadable>', text: '<unreadable>' });
                }
            });
        } catch (e) {
            result.error = String(e);
        }
    };
    try {
        if (Java.performNow) Java.performNow(run);
        else Java.perform(run);
    } catch (e) {
        result.error = String(e);
    }
    emit({ type: 'class_audit', result: result });
    return result;
}

rpc.exports = {
    audit: function () {
        return audit_loaded_classes();
    }
};
