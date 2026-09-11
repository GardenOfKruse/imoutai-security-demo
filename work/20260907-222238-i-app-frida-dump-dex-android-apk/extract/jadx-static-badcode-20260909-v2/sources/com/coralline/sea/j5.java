package com.coralline.sea;

import com.coralline.sea.h5;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class j5 {
    public static volatile j5 a;

    public class a implements l4 {
        public String a = c7.c;

        public a() {
        }

        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) throws Throwable {
            c(s1Var);
        }

        public final void c(s1 s1Var) throws Throwable {
            try {
                boolean z = n3.a().E;
                boolean z2 = r1.n;
                JSONObject jSONObject = new JSONObject(s1Var.d());
                if (!n3.T.E && r1.n && jSONObject.has(t1.b)) {
                    JSONArray jSONArray = jSONObject.getJSONArray(t1.b);
                    jSONArray.toString();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        com.coralline.sea.checkers.a.c().a(jSONArray.get(i).toString());
                    }
                }
                if (jSONObject.has("instruction_v493")) {
                    Objects.toString(jSONObject.getJSONObject("instruction_v493"));
                    this.a = ja.a(jSONObject, this.a);
                }
                if (jSONObject.has("invalid_udid")) {
                    aa.f().g();
                }
                h5.b(jSONObject);
                b9.a(jSONObject);
                if (jSONObject.has(t1.b)) {
                    JSONArray jSONArray2 = jSONObject.getJSONArray(t1.b);
                    for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                        com.coralline.sea.checkers.a.c().a(jSONArray2.get(i2).toString());
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public static j5 a() {
        if (a == null) {
            synchronized (j5.class) {
                if (a == null) {
                    a = new j5();
                }
            }
        }
        return a;
    }

    public void b() {
        try {
            j1.c(new h5.b(), e2.d);
            y9.a(new s1(h5.a().toString(), y1.b(e2.d), e2.d, e2.d, false), 2000L);
        } catch (Exception e) {
        }
    }
}
