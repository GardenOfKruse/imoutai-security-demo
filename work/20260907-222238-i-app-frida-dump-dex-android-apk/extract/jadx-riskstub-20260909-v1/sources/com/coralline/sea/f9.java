package com.coralline.sea;

import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class f9 extends t6 {
    public f9() {
        super("startup_all");
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() throws Throwable {
        JSONObject jSONObjectC = w5.b().c();
        if (jSONObjectC == null || jSONObjectC.length() == 0) {
            return;
        }
        push(e2.b, "start_all", jSONObjectC.toString());
    }
}
