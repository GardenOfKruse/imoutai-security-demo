package com.coralline.sea;

import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class x2 extends c7 {
    public static JSONObject i;
    public static JSONObject j;

    static {
        JSONObject jSONObject = c7.f;
        i = jSONObject;
        j = jSONObject;
    }

    public static JSONObject a() {
        return a(true);
    }

    public static JSONObject a(int i2) {
        return a(true, i2);
    }

    public static synchronized JSONObject a(boolean z) {
        if (c7.b(d2.F)) {
            JSONObject jSONObjectD = y2.a().d();
            i = jSONObjectD;
            return c7.a(jSONObjectD);
        }
        if (z) {
            return c7.a(i);
        }
        return new JSONObject();
    }

    public static synchronized JSONObject a(boolean z, int i2) {
        if (c7.b(d2.E)) {
            JSONObject jSONObjectA = ja.a(i2);
            j = jSONObjectA;
            return c7.a(jSONObjectA);
        }
        if (z) {
            return c7.a(j);
        }
        return new JSONObject();
    }
}
