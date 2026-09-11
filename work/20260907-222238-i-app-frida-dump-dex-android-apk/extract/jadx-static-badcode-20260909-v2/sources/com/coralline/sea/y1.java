package com.coralline.sea;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class y1 {
    public static final Object d = new Object();
    public static final y1 e = new y1();
    public static Set<String> f = new a();
    public static String g = null;
    public static String h = null;
    public static JSONObject i = new JSONObject();
    public final int a = 4;
    public JSONObject b = new JSONObject();
    public long c;

    public class a extends HashSet<String> {
        public a() {
            add(e2.d);
            add(e2.c);
        }
    }

    public y1() throws Throwable {
        try {
            n3 n3VarA = n3.a();
            String packageResourcePath = c7.c;
            Context context = n3.a().a;
            packageResourcePath = context != null ? context.getPackageResourcePath() : packageResourcePath;
            String strC = c4.c(packageResourcePath);
            strC = TextUtils.isEmpty(strC) ? new JSONObject(i6.a(packageResourcePath)).optString("signMd5") : strC;
            this.c = 0L;
            this.b.put("udid", n3.a().f());
            this.b.put("version_client", n3.a().z);
            this.b.put("version", n3VarA.v + "-" + n3VarA.i + "-" + n3VarA.h);
            this.b.put("agent_id", n3VarA.k);
            this.b.put("self_md5", strC);
            this.b.put("start_id", ja.o());
            this.b.put("msg_id", 0);
            this.b.put("protol_type", i2.b);
            this.b.put("platform", a0.b);
            if (ja.s()) {
                this.b.put("platform", "HarmonyOS");
                this.b.put("harmony_version", ja.a(context));
            }
        } catch (Exception e2) {
        }
    }

    public static synchronized y1 a() {
        synchronized (d) {
            y1 y1Var = e;
            if (y1Var != null) {
                return y1Var;
            }
            return new y1();
        }
    }

    public static JSONObject a(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = new JSONObject(str2);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                jSONObject2.put(next, (next.equals("extra") && jSONObject2.has("extra")) ? a(jSONObject, jSONObject2) : jSONObject.get(next));
            }
            return jSONObject2;
        } catch (Exception e2) {
            return null;
        }
    }

    public static JSONObject a(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("extra");
        JSONObject jSONObjectOptJSONObject2 = jSONObject2.optJSONObject("extra");
        if (jSONObjectOptJSONObject == null || jSONObjectOptJSONObject2 == null) {
            return jSONObjectOptJSONObject == null ? jSONObjectOptJSONObject2 : jSONObjectOptJSONObject;
        }
        JSONObject jSONObject3 = new JSONObject(jSONObjectOptJSONObject.toString());
        Iterator<String> itKeys = jSONObject2.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            jSONObject3.put(next, jSONObject2.get(next));
        }
        return jSONObject3;
    }

    public static synchronized void a(Object obj) {
        try {
            JSONObject jSONObject = (JSONObject) obj;
            g = jSONObject.getString("token");
            h = jSONObject.getString("uuid");
        } catch (Exception e2) {
        }
    }

    public static synchronized void a(JSONObject jSONObject) {
        i = jSONObject;
    }

    public static String b(String str) {
        return a().a(str).toString();
    }

    public static void c(String str) {
        try {
            a().b.put("udid", str);
            a().b.toString();
        } catch (Exception e2) {
        }
    }

    public synchronized JSONObject a(String str) {
        JSONObject jSONObject;
        JSONObject jSONObjectA;
        JSONObject jSONObjectB;
        Context context = n3.a().a;
        jSONObject = null;
        try {
            JSONObject jSONObject2 = new JSONObject(this.b.toString());
            try {
                long j = this.c + 1;
                this.c = j;
                jSONObject2.put("msg_id", j);
                jSONObject2.put("client_time", String.valueOf(System.currentTimeMillis()));
                if (n3.T.d && !TextUtils.equals(str, "token")) {
                    jSONObject2.put("client_token", b4.b().c());
                }
                jSONObject2.put("protol_type", str);
                if (n3.T.C) {
                    String str2 = g;
                    if (str2 != null) {
                        jSONObject2.put("token", str2);
                    }
                    String str3 = h;
                    if (str3 != null) {
                        jSONObject2.put("uuid", str3);
                    }
                }
                if (!f.contains(str) && !n3.T.g) {
                    ja.a(jSONObject2, "ip_lan", k6.a().b());
                    ja.a(jSONObject2, "net_type", l6.b(context));
                }
                boolean zB = t1.b("userdata");
                if (zB && (jSONObjectB = ga.a().b()) != null) {
                    Iterator<String> itKeys = jSONObjectB.keys();
                    while (itKeys.hasNext()) {
                        String next = itKeys.next();
                        jSONObject2.put(next, jSONObjectB.get(next));
                    }
                }
                if (t1.b(p3.b) && (jSONObjectA = q3.b().a()) != null && jSONObjectA.length() > 0) {
                    Iterator<String> itKeys2 = jSONObjectA.keys();
                    while (itKeys2.hasNext()) {
                        String next2 = itKeys2.next();
                        jSONObject2.put(next2, jSONObjectA.get(next2));
                    }
                }
                n3 n3Var = n3.T;
                if (!n3Var.g && context != null && !jSONObject2.has("extra")) {
                    JSONObject jSONObjectA2 = q5.a();
                    JSONObject jSONObjectA3 = a9.a(ga.d, (JSONObject) null);
                    Objects.toString(jSONObjectA3);
                    if (jSONObjectA3 != null) {
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("location", jSONObjectA3);
                        jSONObject2.put("extra", jSONObject3);
                    } else if (jSONObjectA2 != null) {
                        JSONObject jSONObject4 = new JSONObject();
                        jSONObject4.put("location", jSONObjectA2);
                        jSONObject2.put("extra", jSONObject4);
                    }
                }
                JSONObject jSONObjectA4 = a9.a("userdata_pre", (JSONObject) null);
                if (jSONObjectA4 != null && zB) {
                    jSONObject2.put("userdata", jSONObjectA4);
                }
                if (i == null) {
                    i = new JSONObject();
                }
                jSONObject2.put("userscenariodata", i);
                if (!n3Var.C && !"unique_equipment".equals(str)) {
                    jSONObject2.put("token", ja.p());
                }
            } catch (JSONException e2) {
            }
            jSONObject = jSONObject2;
        } catch (JSONException e3) {
        }
        return jSONObject;
    }
}
