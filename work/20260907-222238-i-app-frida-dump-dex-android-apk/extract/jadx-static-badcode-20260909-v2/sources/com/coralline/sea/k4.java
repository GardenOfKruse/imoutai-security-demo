package com.coralline.sea;

import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class k4 extends x6 {
    public static final String c = "https";
    public int b;

    public k4() {
        super(c, 86400);
        this.b = 0;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        try {
            JSONObject jSONObjectA = z1.a(c);
            if (jSONObjectA == null || !jSONObjectA.has("url")) {
                return;
            }
            JSONArray jSONArray = jSONObjectA.getJSONArray("url");
            if (this.b == 0) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    jSONArray.getString(i);
                    JSONObject jSONObjectA2 = h4.d().a(jSONArray.getString(i));
                    if (!f5.a(jSONObjectA2)) {
                        this.b++;
                    }
                    if (this.b > 0) {
                        push(e2.b, c, jSONObjectA2.toString());
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}
