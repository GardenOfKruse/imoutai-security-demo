package com.coralline.sea;

import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class o7 extends c7 {
    public static ConcurrentHashMap<String, String> i = new ConcurrentHashMap<>(20, 0.6f);
    public static String j = c7.c;
    public static String k = c7.c;
    public static JSONArray l;
    public static JSONArray m;

    static {
        JSONArray jSONArray = c7.e;
        l = jSONArray;
        m = jSONArray;
    }

    public static String a() {
        return a(true);
    }

    public static synchronized String a(boolean z) {
        if (!c7.b(d2.o)) {
            if (!z) {
                return c7.c;
            }
            return j;
        }
        if (i.get("imei") != null) {
            j = i.get("imei");
        } else {
            String strA = z4.c().a();
            j = strA;
            i.put("imei", strA);
        }
        return j;
    }

    public static synchronized JSONArray a(String str, boolean z) {
        if ("imei".equals(str)) {
            if (c7.b(d2.j)) {
                JSONArray jSONArrayA = z4.c().a("imei");
                l = jSONArrayA;
                return c7.a(jSONArrayA);
            }
            if (z) {
                return c7.a(l);
            }
            return new JSONArray();
        }
        if (c7.b(d2.k)) {
            JSONArray jSONArrayA2 = z4.c().a("imsi");
            m = jSONArrayA2;
            return c7.a(jSONArrayA2);
        }
        if (z) {
            return c7.a(m);
        }
        return new JSONArray();
    }

    public static String b() {
        return b(true);
    }

    public static synchronized String b(boolean z) {
        if (!c7.b(d2.k)) {
            if (!z) {
                return c7.c;
            }
            return k;
        }
        if (i.get("imsi") != null) {
            k = i.get("imsi");
        } else {
            String strB = z4.c().b();
            k = strB;
            i.put("imsi", strB);
        }
        return k;
    }

    public static JSONArray d(String str) {
        return a(str, true);
    }
}
