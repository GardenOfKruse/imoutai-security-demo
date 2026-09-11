package com.coralline.sea;

import android.content.Context;
import android.text.TextUtils;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class z9 {
    public static final String a = "UDID";
    public static final String b = "tmp_d2";
    public static final String c = "tmp_d5";
    public static final String d = "tmp_x1";
    public static final String e = "OoO";
    public static final String f = "null_h";

    public static String a() {
        return a(n3.a().a, e, c7.c);
    }

    public static synchronized String a(Context context, String str, String str2) {
        try {
            String string = context.getSharedPreferences("tmp_d2", 0).getString(str, str2);
            if (!TextUtils.isEmpty(string)) {
                return v1.a(string, i6.r(), i6.q());
            }
        } catch (Exception e2) {
        }
        return c7.c;
    }

    public static void a(String str) {
        b(n3.a().a, e, str);
    }

    public static JSONObject b() {
        String strA = a(n3.a().a, "null_h", null);
        if (strA != null) {
            try {
                return new JSONObject(strA);
            } catch (Exception e2) {
            }
        }
        return new JSONObject();
    }

    public static synchronized void b(Context context, String str, String str2) {
        try {
            context.getSharedPreferences("tmp_d2", 0).edit().putString(str, v1.b(str2, i6.r(), i6.q())).apply();
        } catch (Exception e2) {
        }
    }

    public static void b(String str) {
        b(n3.a().a, d, str);
    }

    public static String c() {
        return a(n3.a().a, d, c7.c);
    }

    public static void c(String str) {
        b(n3.a().a, c, str);
    }
}
