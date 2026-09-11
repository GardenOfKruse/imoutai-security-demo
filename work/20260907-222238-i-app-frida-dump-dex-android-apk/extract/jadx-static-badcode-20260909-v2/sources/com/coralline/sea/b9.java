package com.coralline.sea;

import android.text.TextUtils;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class b9 {
    public static void a(String str) {
        if (TextUtils.isEmpty(str) || str.equals(a9.a(c2.c, c7.c))) {
            return;
        }
        a9.b(c2.c, str);
    }

    public static void a(JSONObject jSONObject) {
        try {
            if (jSONObject.optBoolean(c2.a)) {
                a(jSONObject.optString(c2.c));
                b(jSONObject.optString(c2.b));
                c(jSONObject.optString(c2.d));
            }
        } catch (Exception e) {
        }
    }

    public static void b(String str) {
        if (TextUtils.isEmpty(str) || str.equals(a9.a(c2.b, c7.c))) {
            return;
        }
        a9.b(c2.b, str);
    }

    public static void c(String str) {
        if (TextUtils.isEmpty(str) || str.equals(a9.a(c2.d, c7.c))) {
            return;
        }
        a9.b(c2.d, str);
    }
}
