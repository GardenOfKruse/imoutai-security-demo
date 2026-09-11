package com.coralline.sea;

import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class j4 extends x6 {
    public static final String f = "HttpProxyChecker";
    public static final String g = "httpproxy";
    public boolean b;
    public int c;
    public String d;
    public String e;

    public j4() {
        super(g, 15);
        this.b = true;
        this.c = 15;
        this.d = c7.c;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        try {
            JSONObject jSONObjectA = z1.a(g);
            if (jSONObjectA.length() > 0) {
                this.b = jSONObjectA.optBoolean(t1.b, true);
                this.c = jSONObjectA.optInt("period", 15);
                if (this.b) {
                    JSONObject jSONObjectA2 = h4.d().a();
                    if (f5.a(jSONObjectA2)) {
                        return;
                    }
                    String string = jSONObjectA2.toString();
                    if (string.equals(this.e)) {
                        return;
                    }
                    this.e = string;
                    push(e2.b, g, string);
                }
            }
        } catch (Exception e) {
        }
    }
}
