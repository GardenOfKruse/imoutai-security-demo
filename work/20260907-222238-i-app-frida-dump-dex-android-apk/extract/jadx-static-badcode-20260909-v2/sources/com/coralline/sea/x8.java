package com.coralline.sea;

import android.text.TextUtils;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class x8 extends x6 {
    public static final String i = "service_prority";
    public static CountDownLatch j = new CountDownLatch(1);
    public int b;
    public s1 c;
    public h6 d;
    public int e;
    public int f;
    public int g;
    public int h;

    public x8() {
        super(i, 5);
        this.b = 102400;
        this.e = 10;
        this.f = 0;
        this.g = 24;
        this.h = 0;
    }

    public static synchronized void a(long j2) {
        try {
            j.await(j2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        int i2;
        try {
            this.h++;
            String strB = y8.b();
            if (!TextUtils.isEmpty(strB)) {
                this.f++;
                s1 s1Var = new s1(strB, y1.b(i), i, e2.b, needToPersist());
                this.c = s1Var;
                this.d.b(s1Var);
            }
            this.d.j();
            int i3 = this.b;
            int i4 = this.f;
            int i5 = this.e;
            int i6 = this.h;
            int i7 = this.g;
            if ((this.d.j() > this.b || (i2 = this.f) >= this.e || (this.h >= this.g && i2 >= 1)) && n3.a().E && !r1.e().i) {
                n8.c().a(this.d);
                this.d = new h6();
                this.f = 0;
                this.h = 0;
            }
        } catch (Exception e) {
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void initialize() {
        this.d = new h6();
    }

    @Override // com.coralline.sea.checkers.Checker
    public void start() {
        JSONArray jSONArrayOptJSONArray;
        q4 q4VarA;
        try {
            JSONObject jSONObjectA = z1.a(i);
            if (jSONObjectA != null && jSONObjectA.length() > 0) {
                if (!jSONObjectA.has("hook_entry") || (jSONArrayOptJSONArray = jSONObjectA.optJSONArray("hook_entry")) == null || jSONArrayOptJSONArray.length() == 0) {
                    return;
                }
                int length = jSONArrayOptJSONArray.length();
                for (int i2 = 0; i2 < length; i2++) {
                    JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i2);
                    String strOptString = jSONObjectOptJSONObject.has("hook_type") ? jSONObjectOptJSONObject.optString("hook_type") : null;
                    if (strOptString != null && (q4VarA = e4.a().a(f4.valueOf(strOptString))) != null) {
                        q4VarA.a(jSONObjectOptJSONObject);
                        q4VarA.h();
                    }
                }
                if (jSONObjectA.has("mapping")) {
                    y5.a(jSONObjectA.optJSONObject("mapping"));
                }
            }
        } catch (Exception e) {
        }
        j.countDown();
    }
}
