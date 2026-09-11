package com.coralline.sea;

import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class w9 {
    public static final String a = "token";
    public static w9 b = new w9();

    public static w9 a() {
        return b;
    }

    public void b() {
        try {
            String strC = b4.b().c();
            String strD = b4.d.d();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("client_token", strC);
            y9.b(new s1(jSONObject.toString(), y1.b("token"), "token", e2.d, false));
            jSONObject.put("client_token", "A" + strC).put("encrypt_token", strD);
            da.a(new s1(jSONObject.toString(), y1.b("token"), "token", e2.b, false));
        } catch (Exception e) {
        }
    }
}
