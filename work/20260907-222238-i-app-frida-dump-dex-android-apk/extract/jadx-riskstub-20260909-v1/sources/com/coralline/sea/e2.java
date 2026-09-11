package com.coralline.sea;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class e2 {
    public static final String b = "upload";
    public static final String c = "download";
    public static final String d = "keepalive";
    public static final String e = "data_collection";
    public static e2 f;
    public Map<String, n4> a = new HashMap();

    public static synchronized e2 a() {
        if (f == null) {
            e2 e2Var = new e2();
            f = e2Var;
            e2Var.a(b, l2.g().a() == null ? new ba() : l2.g().a());
            f.a(c, l2.g().a() == null ? new d3() : l2.g().a());
            f.a(d, l2.g().a() == null ? new i5() : l2.g().a());
            f.a(e, l2.g().a() == null ? new n2() : l2.g().a());
        }
        return f;
    }

    public n4 a(String str) {
        return this.a.get(str);
    }

    public void a(String str, n4 n4Var) {
        this.a.put(str, n4Var);
    }
}
