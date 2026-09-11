package com.coralline.sea;

import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class f9 extends t6 {
    public f9() {
        super("startup_all");
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() throws Throwable {
        JSONObject jSONObjectC = w5.b().c();
        if (jSONObjectC == null || jSONObjectC.length() == 0) {
            return;
        }
        push(e2.b, "start_all", jSONObjectC.toString());
    }
}
