package com.coralline.sea;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class g8 extends x6 {
    public static final String f = "ScreenSharingChecker";
    public static final String g = "screen_sharing";
    public static g8 h = null;
    public static boolean i = true;
    public static boolean j = false;
    public JSONArray b;
    public int c;
    public boolean d;
    public JSONObject e;

    public g8() {
        super("screen_sharing", 10);
        this.b = new JSONArray();
        this.c = 0;
        this.d = false;
    }

    public static g8 a() {
        if (h == null) {
            h = new g8();
        }
        return h;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        JSONArray jSONArray;
        JSONException e;
        if (i) {
            JSONObject jSONObjectA = f8.c().a();
            boolean z = j;
            if (jSONObjectA == null || z) {
                return;
            }
            try {
                jSONArray = jSONObjectA.getJSONArray("detail");
                try {
                    if (jSONArray.length() > 0) {
                        for (int length = jSONArray.length() - 1; length >= 0; length--) {
                            JSONObject jSONObject = jSONArray.getJSONObject(length);
                            if (isInWhitelist(jSONObject.optString("package"), jSONObject.optString("app_name"), "screen_sharing")) {
                                jSONArray.remove(length);
                            }
                        }
                    }
                    if (jSONArray.length() > 0) {
                        jSONObjectA.put("detail", jSONArray);
                    }
                } catch (JSONException e2) {
                    e = e2;
                    e.getMessage();
                }
            } catch (JSONException e3) {
                jSONArray = null;
                e = e3;
            }
            if (jSONArray == null || jSONArray.length() == 0) {
                return;
            }
            push(e2.b, "screen_sharing", jSONObjectA.toString());
            j = true;
        }
    }
}
