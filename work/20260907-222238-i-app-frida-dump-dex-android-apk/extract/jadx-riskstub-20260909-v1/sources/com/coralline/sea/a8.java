package com.coralline.sea;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class a8 extends t6 {
    public a8() {
        super(g9.f);
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        try {
            JSONObject jSONObjectD = a5.a().c().d(n3.a().a);
            if (jSONObjectD == null || jSONObjectD.length() <= 0 || !jSONObjectD.optBoolean("is_root")) {
                return;
            }
            JSONObject jSONObject = new JSONObject(jSONObjectD.toString());
            jSONObject.put("protol_type", g9.f);
            push(e2.b, g9.f, jSONObject.toString());
        } catch (JSONException e) {
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void start() {
        super.start();
    }
}
