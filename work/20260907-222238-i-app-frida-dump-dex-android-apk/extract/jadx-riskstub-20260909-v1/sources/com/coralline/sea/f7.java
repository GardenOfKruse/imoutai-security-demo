package com.coralline.sea;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
@Deprecated
public class f7 extends t6 {
    public static final String b = "prop_info";
    public l4 a;

    public class a implements l4 {
        public a() {
        }

        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) {
            a9.b(f7.b, 1L);
        }
    }

    public f7() {
        super(b);
        this.a = new a();
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        JSONObject jSONObjectA;
        j1.c(this.a, b);
        if (0 != a9.a(b, 0L) || (jSONObjectA = x2.a()) == null || jSONObjectA.length() <= 0) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("data", jSONObjectA);
            jSONObject.put("prop_md5", ja.n(jSONObjectA.toString()));
            push(e2.e, b, jSONObject.toString());
        } catch (JSONException e) {
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void flush() {
        super.flush();
        check();
    }
}
