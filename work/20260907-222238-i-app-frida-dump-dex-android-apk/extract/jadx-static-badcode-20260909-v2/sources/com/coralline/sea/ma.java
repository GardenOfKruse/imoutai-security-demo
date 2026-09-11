package com.coralline.sea;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class ma extends t6 {
    public static String a = "virtual_env";

    public ma() {
        super(a);
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        try {
            JSONArray jSONArrayA = la.a();
            if (f5.a((Object) jSONArrayA)) {
                return;
            }
            push(e2.b, a, new JSONObject().put("data", jSONArrayA).toString());
        } catch (JSONException e) {
        }
    }
}
