package com.coralline.sea;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class j3 extends t6 {
    public j3() {
        super("emulator");
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        try {
            JSONObject jSONObjectA = i3.a();
            if (jSONObjectA == null || jSONObjectA.length() <= 0) {
                return;
            }
            jSONObjectA.put("protol_type", "emulator");
            push(e2.b, "emulator", jSONObjectA.toString());
        } catch (JSONException e) {
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void start() {
        super.start();
    }
}
