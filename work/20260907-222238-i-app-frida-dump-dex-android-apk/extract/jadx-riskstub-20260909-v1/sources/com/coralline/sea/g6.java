package com.coralline.sea;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class g6 extends t6 {
    public g6() {
        super(g9.e);
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        Context context = n3.a().a;
        try {
            x4 x4VarC = a5.a().c();
            if (x4VarC.c(context)) {
                JSONObject jSONObjectA = x4VarC.a(context);
                jSONObjectA.put("protol_type", g9.e);
                push(e2.b, g9.e, jSONObjectA.toString());
            }
        } catch (JSONException e) {
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void start() {
        super.start();
    }
}
