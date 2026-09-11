package com.coralline.sea;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class v9 {
    public static final String a = "type";
    public static final String b = "token";
    public static final String c = "province";
    public static final int d = 0;
    public static String e = "token";

    public class a implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
            try {
                JSONObject jSONObjectA = v9.a();
                if (!jSONObjectA.has("token")) {
                    String str = v9.e;
                    return;
                }
                JSONObject jSONObject = new JSONObject();
                String strF = n3.a().f();
                String str2 = n3.T.k;
                jSONObject.put("protol_type", "token_verification");
                jSONObject.put("udid", strF);
                jSONObject.put("type", jSONObjectA.opt("type"));
                jSONObject.put("token", jSONObjectA.opt("token"));
                jSONObject.put(v9.c, jSONObjectA.opt(v9.c));
                jSONObject.put("agent_id", str2);
                jSONObject.put("platform", a0.b);
                String str3 = v9.e;
                jSONObject.toString();
                String strOptString = z1.c("token").optString("dev_mark_url", c7.c);
                n4 n4VarA = e2.a().a(e2.b);
                String strA = n4VarA.a(i4.a().a(strOptString + "/3/2", n4VarA.a(jSONObject.toString()).getBytes(), 30000));
                String str4 = v9.e;
                v9.a(new JSONObject(strA).optString("uaid", c7.c));
            } catch (JSONException e) {
            }
        }
    }

    public static JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", 0);
            TextUtils.isEmpty(z1.c("token").optString("dev_mark_url", c7.c));
            return jSONObject;
        } catch (Exception e2) {
            return jSONObject;
        }
    }

    public static void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        z9.b(str);
    }

    public static void b() {
        new Thread(new a()).start();
    }

    public static void c() {
        if (TextUtils.isEmpty(z9.c())) {
            b();
        }
    }
}
