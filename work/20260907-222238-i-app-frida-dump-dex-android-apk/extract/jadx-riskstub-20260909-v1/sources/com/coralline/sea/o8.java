package com.coralline.sea;

import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class o8 {
    public static final String c = "collection";
    public static final String d = "SensitiveCollection";
    public static o8 e = null;
    public static JSONObject f = null;
    public static boolean g = false;
    public Boolean a;
    public l4 b = new a();

    public class a implements l4 {
        public a() {
        }

        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) {
            try {
                JSONObject jSONObject = new JSONObject(s1Var.d());
                if (jSONObject.has("udid_support_blacklist")) {
                    o8.g = jSONObject.optBoolean("udid_support_blacklist");
                }
                if (jSONObject.has("sensitive_info_collect_switch")) {
                    JSONObject unused = o8.f = jSONObject.optJSONObject("sensitive_info_collect_switch");
                    b7.c(o8.f);
                    o8.this.a = Boolean.TRUE;
                }
                a9.b(o8.c, jSONObject);
                b9.c(jSONObject.optString(c2.d));
            } catch (Exception e) {
            }
        }
    }

    public static synchronized o8 b() {
        if (e == null) {
            e = new o8();
        }
        return e;
    }

    public Boolean c() {
        return this.a;
    }

    public void d() {
        try {
            j1.c(this.b, c);
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(c);
            jSONObject.put("type", jSONArray);
            jSONObject.put(c2.a, true);
            y9.a(new s1(jSONObject.toString(), y1.b(e2.c), c, e2.c, false), 2000L);
        } catch (Exception e2) {
        }
    }

    public final void e() {
        try {
            this.a = Boolean.FALSE;
            b7.a((JSONObject) null, b7.b());
        } catch (Exception e2) {
        }
    }
}
