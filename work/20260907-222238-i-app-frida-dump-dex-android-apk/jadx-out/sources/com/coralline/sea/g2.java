package com.coralline.sea;

import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class g2 extends x6 {
    public static final String c = "credential";
    public boolean b;

    public g2() {
        super(c, 60);
        this.b = false;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        try {
            JSONObject jSONObjectA = h2.b().a();
            if (f5.a(jSONObjectA) || this.b) {
                return;
            }
            push(e2.b, c, jSONObjectA.toString());
            this.b = true;
        } catch (Exception e) {
        }
    }
}
