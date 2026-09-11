package com.coralline.sea;

import org.json.JSONArray;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class s3 {
    public static JSONArray a(String[] strArr) {
        JSONArray jSONArray = new JSONArray();
        try {
            for (String str : strArr) {
                if (a(str)) {
                    jSONArray.put(str);
                }
            }
        } catch (Throwable th) {
        }
        return jSONArray;
    }

    public static boolean a(String str) {
        try {
            return i6.f(str);
        } catch (Throwable th) {
            return false;
        }
    }
}
