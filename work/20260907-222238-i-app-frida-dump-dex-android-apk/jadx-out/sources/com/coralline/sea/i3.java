package com.coralline.sea;

import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class i3 {
    public static final String a = "emulator";

    public static JSONObject a() {
        try {
            JSONObject jSONObjectQ = new k3(n3.a().a).q();
            if (jSONObjectQ == null) {
                return null;
            }
            if (a(jSONObjectQ.getString("type"))) {
                return null;
            }
            return jSONObjectQ;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean a(String str) {
        try {
            JSONObject jSONObjectA = z1.a("emulator");
            if (jSONObjectA == null) {
                return false;
            }
            JSONArray jSONArray = jSONObjectA.getJSONArray("whitelist");
            for (int i = 0; i < jSONArray.length(); i++) {
                if (jSONArray.getString(i).equalsIgnoreCase(str)) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }
}
