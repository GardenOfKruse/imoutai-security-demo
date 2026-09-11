package com.coralline.sea;

import android.text.TextUtils;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class b7 {
    public static final String a = "PrivacyConfig";
    public static ConcurrentHashMap<String, Long> b = new ConcurrentHashMap<>();
    public static AtomicLong c = new AtomicLong(0);
    public static long d = 100000;
    public static final ConcurrentHashMap<String, Long> e = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, Long> f = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, Long> g = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, Long> h = new ConcurrentHashMap<>();

    public static long a(String str) {
        return a9.a(str, b());
    }

    public static void a() {
        List list = n3.a().F;
        if (list == null || list.size() <= 0) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            a((String) it.next(), 0L);
        }
    }

    public static void a(long j) {
        c.set(j);
    }

    public static void a(String str, long j) {
        if (b.containsKey(str)) {
            return;
        }
        b.put(str, Long.valueOf(j));
        a9.b(str, j);
    }

    public static void a(Map<String, Long> map, JSONObject jSONObject) {
        if (map == null || jSONObject == null) {
            return;
        }
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            try {
                String next = itKeys.next();
                long j = jSONObject.getLong(next);
                map.put(next, Long.valueOf(j));
                if (d2.G.equals(next)) {
                    map.put(d2.H, Long.valueOf(j));
                }
            } catch (Throwable th) {
            }
        }
    }

    public static void a(JSONObject jSONObject) {
        a(e, jSONObject);
        Objects.toString(jSONObject);
    }

    public static void a(JSONObject jSONObject, long j) {
        a();
        if (jSONObject == null) {
            return;
        }
        jSONObject.toString();
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            if (d2.G.equals(next)) {
                a(d2.H, jSONObject.optLong(next, j));
            }
            a9.b(next, jSONObject.optLong(next, j));
        }
    }

    public static long b() {
        return o8.b().c() != null && !o8.b().c().booleanValue() ? d : c.get();
    }

    public static long b(String str) {
        Long l = e.get(str);
        if (l != null) {
            return l.longValue();
        }
        Long l2 = f.get(str);
        if (l2 != null) {
            return l2.longValue();
        }
        Long l3 = g.get(str);
        if (l3 != null) {
            return l3.longValue();
        }
        Long l4 = h.get(str);
        return l4 != null ? l4.longValue() : d;
    }

    public static void b(JSONObject jSONObject) {
        a(h, jSONObject);
        Objects.toString(jSONObject);
    }

    public static long c(String str) {
        return b(str);
    }

    public static String c() {
        if (!n3.a().e) {
            return "online_default_privacy.json";
        }
        return "online_default_privacy" + n3.T.f + ".json";
    }

    public static void c(JSONObject jSONObject) {
        a(f, jSONObject);
        Objects.toString(jSONObject);
    }

    public static String d() {
        return n3.a().g ? o6.a() : c();
    }

    public static void d(JSONObject jSONObject) {
        a(g, jSONObject);
        Objects.toString(jSONObject);
    }

    public static void e() {
        f();
        h();
        g();
    }

    public static void f() {
        n3 n3VarA = n3.a();
        if (n3VarA == null) {
            return;
        }
        try {
            List list = n3VarA.F;
            if (list == null || list.isEmpty()) {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            for (Object obj : list) {
                if (obj != null) {
                    jSONObject.put(obj.toString(), 0);
                }
            }
            a(jSONObject);
        } catch (Throwable th) {
            th.toString();
        }
    }

    public static void g() {
        try {
            String strB = ja.b(n3.a().a, d());
            if (TextUtils.isEmpty(strB)) {
                return;
            }
            b(new JSONObject(strB));
        } catch (Throwable th) {
            th.toString();
        }
    }

    public static void h() {
        JSONObject jSONObjectOptJSONObject;
        try {
            JSONObject jSONObjectA = a9.a(o8.c, (JSONObject) null);
            if (jSONObjectA == null || (jSONObjectOptJSONObject = jSONObjectA.optJSONObject("sensitive_info_collect_switch")) == null) {
                return;
            }
            d(jSONObjectOptJSONObject);
        } catch (Throwable th) {
            th.toString();
        }
    }
}
