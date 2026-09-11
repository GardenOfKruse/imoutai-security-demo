package com.coralline.sea;

import android.os.Process;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class z5 extends t6 {
    public static z5 a;

    public z5() {
        super("modify");
    }

    public static void a(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("subtype", str2);
            jSONObject.put("file", str);
            jSONObject.put("detail", new JSONArray().put(str));
            synchronized (z5.class) {
                a.push(e2.b, "modify", jSONObject.toString());
            }
        } catch (JSONException e) {
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        synchronized (z5.class) {
            if (a == null) {
                a = this;
            }
        }
        i6.a(Process.myPid());
    }
}
