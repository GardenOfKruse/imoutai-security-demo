package com.coralline.sea;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import java.util.UUID;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class b4 {
    public static b4 d = new b4();
    public final Context a = n3.a().a.getApplicationContext();
    public String b;
    public String c;

    public b4() {
        System.currentTimeMillis();
        a();
        System.currentTimeMillis();
    }

    public static b4 b() {
        return d;
    }

    public final void a() {
        JSONObject jSONObject = new JSONObject();
        try {
            String strF = n3.a().f();
            String string = Settings.Secure.getString(this.a.getContentResolver(), "android_id");
            String str = Build.MODEL;
            String str2 = Build.BRAND;
            String str3 = Build.SERIAL;
            String strValueOf = String.valueOf(System.currentTimeMillis() / 1000);
            String strSubstring = strValueOf.substring(0, 3);
            String strSubstring2 = strValueOf.substring(3, 5);
            String strSubstring3 = strValueOf.substring(5, 8);
            String strSubstring4 = strValueOf.substring(8);
            String[] strArrSplit = UUID.nameUUIDFromBytes(String.valueOf(System.currentTimeMillis()).getBytes()).toString().split("-");
            String str4 = strArrSplit[0] + c7.c + strSubstring + c7.c + strArrSplit[1] + c7.c + strSubstring2 + c7.c + strArrSplit[2] + c7.c + strSubstring3 + c7.c + strArrSplit[3] + c7.c + strSubstring4 + c7.c + strArrSplit[4];
            this.b = str4;
            jSONObject.put("client_token", str4).put("udid", strF).put("androidId", string).put("model", str).put("brand", str2).put("serial", str3).put("platform", a0.b).put("start_id", ja.o());
            this.c = v1.d(jSONObject.toString());
        } catch (Exception e) {
        }
    }

    public String c() {
        return this.b;
    }

    public String d() {
        return this.c;
    }
}
