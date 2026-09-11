package com.coralline.sea;

import android.text.TextUtils;
import java.util.Objects;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class k2 {
    public static final String a = "CustomConfig";
    public static final String b;
    public static k2 c;
    public static JSONObject d;

    static {
        String str;
        if (n3.a().e) {
            str = "risk_stub_custom_config" + n3.T.f + ".json";
        } else {
            str = "risk_stub_custom_config.json";
        }
        b = str;
        c = null;
        d = new JSONObject();
    }

    public k2() {
        c();
    }

    public static boolean a() {
        try {
            n3.a().a.getAssets().open(b).close();
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public static void c() {
        try {
            if (a()) {
                String strB = ja.b(n3.a().a, b);
                if (TextUtils.isEmpty(strB)) {
                    return;
                }
                JSONObject jSONObject = new JSONObject(strB);
                d = jSONObject;
                Objects.toString(jSONObject);
            }
        } catch (Throwable th) {
            th.getMessage();
        }
    }

    public static synchronized k2 d() {
        if (c == null) {
            c = new k2();
        }
        return c;
    }

    public Integer a(String str) {
        JSONObject jSONObjectOptJSONObject;
        JSONObject jSONObjectOptJSONObject2;
        if (str != null) {
            try {
                JSONObject jSONObject = d;
                if (jSONObject == null || (jSONObjectOptJSONObject = jSONObject.optJSONObject("checker")) == null || (jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject(str)) == null) {
                    return null;
                }
                return Integer.valueOf(Integer.parseInt(jSONObjectOptJSONObject2.optString("period")));
            } catch (Exception e) {
            }
        }
        return null;
    }

    public JSONObject b() {
        return d;
    }

    public boolean b(String str) {
        JSONObject jSONObjectOptJSONObject;
        JSONObject jSONObjectOptJSONObject2;
        if (str != null) {
            try {
                JSONObject jSONObject = d;
                if (jSONObject == null || (jSONObjectOptJSONObject = jSONObject.optJSONObject("checker")) == null || (jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject(str)) == null) {
                    return false;
                }
                return jSONObjectOptJSONObject2.optBoolean("debug", false);
            } catch (Throwable th) {
            }
        }
        return false;
    }

    public boolean e() {
        JSONObject jSONObjectOptJSONObject;
        JSONObject jSONObject = d;
        if (jSONObject == null || (jSONObjectOptJSONObject = jSONObject.optJSONObject(z1.f)) == null) {
            return false;
        }
        return jSONObjectOptJSONObject.optBoolean("ccb_query_route_disabled", false);
    }
}
