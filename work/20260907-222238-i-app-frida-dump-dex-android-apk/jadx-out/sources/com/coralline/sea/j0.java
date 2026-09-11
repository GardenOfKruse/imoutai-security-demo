package com.coralline.sea;

import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public abstract class j0 extends h0 {
    public String j;

    public j0(f4 f4Var) {
        super(f4Var);
    }

    @Override // com.coralline.sea.h0, com.coralline.sea.q4
    public void a(JSONObject jSONObject) {
        super.a(jSONObject);
        if (jSONObject != null) {
            try {
                this.j = jSONObject.optString("service_interface");
            } catch (Exception e) {
            }
        }
    }

    public String k() {
        return this.j;
    }
}
