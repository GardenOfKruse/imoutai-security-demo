package com.coralline.sea;

import android.text.TextUtils;
import com.coralline.sea.s1;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class o6 {
    public static o6 a;
    public static JSONObject b;
    public static JSONObject c = new JSONObject();
    public static JSONObject d = null;
    public static final String e;
    public static final String f;
    public static final String g;

    public static final class a {
        public static final Map<String, String> a;
        public static final Map<String, String> b;
        public static final Map<String, String> c;

        static {
            HashMap map = new HashMap();
            a = map;
            HashMap map2 = new HashMap();
            b = map2;
            HashMap map3 = new HashMap();
            c = map3;
            map.put("1000", "checker");
            map.put("1001", g9.f);
            map.put("1002", q8.a);
            map.put("1005", q8.b);
            map.put("1006", "emulator");
            map.put("1008", g9.e);
            map.put("1010", "inject");
            map.put("1011", "debug");
            map.put("1013", k4.c);
            map.put("1016", "modify");
            map.put("1017", j4.g);
            map.put("1018", na.i);
            map3.put(g9.f, "系统环境存在root风险");
            map3.put(q8.a, "系统环境存在框架攻击软件");
            map3.put(q8.b, "系统环境敏感配置开关已经打开");
            map3.put("emulator", "请勿使用模拟器!");
            map3.put(g9.e, "请勿多开应用!");
            map3.put("inject", "请勿使用注入攻击!");
            map3.put("debug", "请勿使用调试行为!");
            map3.put(k4.c, "请勿使用https劫持!");
            map3.put("modify", "请勿进行内存篡改!");
            map3.put(j4.g, "请勿使用https代理!");
            map3.put(na.i, "请勿使用vpn代理!");
            map2.put("2000", c7.c);
            map2.put("2001", "msg");
            map2.put("2002", "quit");
            map2.put("2003", "msgquit");
        }

        public static String a(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            Map<String, String> map = b;
            if (map.containsKey(str)) {
                return map.get(str);
            }
            return null;
        }

        public static String b(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            Map<String, String> map = a;
            if (map.containsKey(str)) {
                return map.get(str);
            }
            return null;
        }

        public static String c(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            Map<String, String> map = c;
            if (map.containsKey(str)) {
                return map.get(str);
            }
            return null;
        }
    }

    static {
        String str;
        String str2;
        String str3;
        if (n3.a().e) {
            str = "instruction" + n3.T.f + ".json";
        } else {
            str = "instruction.json";
        }
        e = str;
        if (n3.T.e) {
            str2 = "privacyStrategy" + n3.T.f + ".json";
        } else {
            str2 = "privacyStrategy.json";
        }
        f = str2;
        if (n3.T.e) {
            str3 = "defaultv1" + n3.T.f;
        } else {
            str3 = "defaultv1";
        }
        g = str3;
    }

    public o6() {
        d();
        b();
        c();
    }

    public static String a() {
        return f;
    }

    public static void b() {
        try {
            String strB = ja.b(n3.a().a, g);
            if (TextUtils.isEmpty(strB)) {
                return;
            }
            d = new JSONObject(v1.b(strB)).optJSONObject("checker");
        } catch (Exception e2) {
        }
    }

    public static void c() {
        try {
            String strB = ja.b(n3.a().a, e);
            if (TextUtils.isEmpty(strB)) {
                return;
            }
            JSONObject jSONObject = new JSONObject(strB).getJSONObject("1000");
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                String strB2 = a.b(next);
                if (!TextUtils.isEmpty(strB2)) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(next).getJSONObject("instruction");
                    jSONObject2.put("source", strB2);
                    jSONObject2.put("action", a.a(jSONObject2.getString("action")));
                    jSONObject2.put("title", jSONObject2.optString("title").length() == 0 ? "warning" : jSONObject2.getString("title"));
                    jSONObject2.put(s1.a.a, jSONObject2.optString(s1.a.a).length() == 0 ? a.c(strB2) : jSONObject2.getString(s1.a.a));
                    c.put(strB2, new JSONObject().put("instruction_v493", jSONObject2));
                }
            }
        } catch (Exception e2) {
        }
        c.toString();
    }

    public static void d() {
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(v1.a(n3.a().o, i6.r(), i6.q())).optJSONObject(x9.p).optJSONObject(t1.b);
            b = jSONObjectOptJSONObject;
            if (jSONObjectOptJSONObject.optBoolean("risk_env", false)) {
                b.put(q8.b, true);
            }
            JSONObject jSONObject = b;
            jSONObject.put(g9.f, jSONObject.optBoolean("root", false));
            if (b.optBoolean(q8.b, false) || b.optBoolean(q8.a, false)) {
                b.put(p8.g, true);
            }
        } catch (Exception e2) {
        }
    }

    public static synchronized o6 f() {
        if (a == null) {
            a = new o6();
        }
        return a;
    }

    public synchronized JSONObject a(String str) {
        try {
            JSONObject jSONObject = d;
            if (jSONObject != null && jSONObject.length() > 0) {
                return d.optJSONObject(str);
            }
        } catch (Exception e2) {
        }
        return null;
    }

    public synchronized JSONObject b(String str) {
        try {
            JSONObject jSONObject = c;
            if (jSONObject != null && jSONObject.length() > 0) {
                return c.optJSONObject(str);
            }
        } catch (Exception e2) {
        }
        return null;
    }

    public boolean c(String str) {
        try {
            JSONObject jSONObject = b;
            if (jSONObject != null) {
                return jSONObject.optBoolean(str, true);
            }
            return false;
        } catch (Exception e2) {
            return false;
        }
    }

    public boolean d(String str) {
        return n3.a().y.containsKey(str);
    }

    public void e() {
        try {
            String strB = ja.b(n3.a().a, f);
            if (TextUtils.isEmpty(strB)) {
                return;
            }
            b7.a(new JSONObject(strB), b7.b());
        } catch (Exception e2) {
        }
    }
}
