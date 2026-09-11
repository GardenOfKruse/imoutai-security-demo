package com.coralline.sea;

import org.json.JSONArray;

/* JADX INFO: loaded from: assets/RiskStub.dex */
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
