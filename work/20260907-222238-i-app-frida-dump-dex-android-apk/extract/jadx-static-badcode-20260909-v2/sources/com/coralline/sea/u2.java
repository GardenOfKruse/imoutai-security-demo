package com.coralline.sea;

import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class u2 extends x6 {
    public boolean b;
    public boolean c;
    public boolean d;

    public u2() {
        super("debug", 5);
        this.c = true;
        this.d = false;
        this.b = false;
    }

    public final void a(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            Object objN = i6.N();
            if (objN instanceof String) {
                jSONObject2 = new JSONObject((String) objN);
            }
            jSONObject.put("ptrace_detail", jSONObject2);
        } catch (Exception e) {
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        if (this.b || !v2.b().a()) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", v2.d.a);
            if (!n3.a().g && "ptrace".equals(v2.d.a)) {
                a(jSONObject);
            }
            jSONObject.put("detail", new JSONArray().put(v2.d.b));
            push(e2.b, "debug", jSONObject.toString());
        } catch (Exception e) {
        }
        this.b = true;
    }
}
