package com.coralline.sea;

import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class q8 {
    public static final String a = "risk_frame";
    public static final String b = "sys_conf";
    public static JSONObject c;

    public static JSONArray a(JSONArray jSONArray, JSONArray jSONArray2) {
        boolean z;
        try {
            JSONArray jSONArray3 = new JSONArray();
            for (int i = 0; i < jSONArray.length(); i++) {
                String string = jSONArray.getString(i);
                int i2 = 0;
                while (true) {
                    if (i2 >= jSONArray2.length()) {
                        z = false;
                        break;
                    }
                    if (string.equals(jSONArray2.getString(i2))) {
                        z = true;
                        break;
                    }
                    i2++;
                }
                if (!z) {
                    jSONArray3.put(jSONArray.get(i));
                }
            }
            if (jSONArray3.length() == 0) {
                return null;
            }
            return jSONArray3;
        } catch (Exception e) {
            return null;
        }
    }

    public static synchronized JSONObject a() {
        try {
            JSONObject jSONObject = new JSONObject();
            if (!n3.a().g || o6.f().c(a)) {
                JSONArray jSONArrayX = w7.x();
                if (jSONArrayX.length() != 0) {
                    jSONObject.put(a, jSONArrayX);
                }
            }
            if (!n3.T.g || o6.f().c("risk_env")) {
                JSONArray jSONArrayB = n9.b();
                if (jSONArrayB.length() > 0) {
                    jSONObject.put(b, jSONArrayB);
                }
            }
            JSONObject jSONObjectA = a(jSONObject);
            if (jSONObjectA != null) {
                if (jSONObjectA.length() != 0) {
                    return jSONObjectA;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static synchronized JSONObject a(JSONObject jSONObject) {
        if (c == null) {
            c = jSONObject;
            return jSONObject;
        }
        try {
            if (jSONObject.has(a) && c.has(a)) {
                JSONArray jSONArrayA = a(jSONObject.getJSONArray(a), c.getJSONArray(a));
                if (jSONArrayA != null) {
                    for (int i = 0; i < jSONArrayA.length(); i++) {
                        c.getJSONArray(a).put(jSONArrayA.get(i));
                    }
                    jSONObject.put(a, jSONArrayA);
                } else {
                    jSONObject.remove(a);
                }
            } else if (jSONObject.has(a)) {
                c.put(a, jSONObject.get(a));
            }
            if (jSONObject.has(b) && c.has(b)) {
                JSONArray jSONArrayA2 = a(jSONObject.getJSONArray(b), c.getJSONArray(b));
                if (jSONArrayA2 != null) {
                    for (int i2 = 0; i2 < jSONArrayA2.length(); i2++) {
                        c.getJSONArray(b).put(jSONArrayA2.get(i2));
                    }
                    jSONObject.put(b, jSONArrayA2);
                } else {
                    jSONObject.remove(b);
                }
            } else if (jSONObject.has(b)) {
                c.put(b, jSONObject.get(b));
            }
            if (jSONObject.length() <= 0) {
                jSONObject = null;
            }
            return jSONObject;
        } catch (Exception e) {
            return null;
        }
    }
}
