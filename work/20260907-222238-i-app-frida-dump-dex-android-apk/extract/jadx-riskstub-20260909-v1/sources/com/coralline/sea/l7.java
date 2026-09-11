package com.coralline.sea;

import android.text.TextUtils;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class l7 extends g0 implements o4 {
    public static final String d = "flow-control";
    public static final String e = "ratio";
    public static final String f = "count";
    public static final String g = "loader_flow_ctrol";
    public static JSONObject h = null;
    public static boolean i = false;
    public static boolean j = false;
    public static volatile l7 k;
    public l4 c;

    public class a implements l4 {
        public a() {
        }

        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) {
            if (s1Var != null) {
                l7.this.a(s1Var);
            }
        }
    }

    public l7() {
        a aVar = new a();
        this.c = aVar;
        j1.c(aVar, null);
    }

    public static l7 j() {
        if (k == null) {
            synchronized (l7.class) {
                if (k == null) {
                    k = new l7();
                }
            }
        }
        return k;
    }

    @Override // com.coralline.sea.o4
    public String a() {
        return null;
    }

    @Override // com.coralline.sea.g0
    public void a(s1 s1Var) {
        String str = s1Var.d;
        try {
            String strD = s1Var.d();
            JSONObject jSONObject = strD != null ? new JSONObject(strD) : new JSONObject();
            if (jSONObject.has(d) && jSONObject.getJSONObject(d).length() > 0) {
                JSONObject jSONObject2 = jSONObject.getJSONObject(d);
                String string = jSONObject2.getString("ratio");
                int i2 = jSONObject2.getInt("count");
                if (!TextUtils.isEmpty(string)) {
                    float f2 = Float.parseFloat(string);
                    new Random().nextFloat();
                    if (0.0f == f2) {
                        u3.a(string, i2);
                        l9.b().a(false);
                        x9.a(r5.h);
                    } else {
                        u3.a(string, i2);
                        l9.b().a(true);
                    }
                }
            } else if (jSONObject.has("ratio")) {
                String string2 = jSONObject.getString("ratio");
                int i3 = jSONObject.has("count") ? jSONObject.getInt("count") : -2;
                if (!TextUtils.isEmpty(string2)) {
                    float f3 = Float.parseFloat(string2);
                    new Random().nextFloat();
                    if (0.0f == f3) {
                        u3.a(string2, i3);
                        l9.b().a(false);
                        x9.a(r5.h);
                    } else {
                        u3.a(string2, i3);
                        l9.b().a(true);
                    }
                }
            }
            if (jSONObject.has("ratio") || jSONObject.has(d)) {
                return;
            }
            a9.b(a9.d);
        } catch (JSONException e2) {
        }
    }

    public final boolean a(float f2) {
        return new Random().nextFloat() >= f2;
    }

    public boolean a(String str) {
        return false;
    }

    @Override // com.coralline.sea.o4
    public boolean c() {
        return h() == null || h() == v3.FULL_START;
    }

    @Override // com.coralline.sea.o4
    public boolean d() {
        return !k();
    }

    @Override // com.coralline.sea.o4
    public String g() {
        return null;
    }

    public boolean i() {
        return true;
    }

    public boolean k() {
        JSONObject jSONObjectA = a9.a(a9.d, (JSONObject) null);
        if (jSONObjectA == null) {
            return false;
        }
        try {
            String string = jSONObjectA.getString("ratio");
            jSONObjectA.getInt("count");
            if (!TextUtils.isEmpty(string)) {
                return a(Float.parseFloat(string));
            }
        } catch (JSONException | Exception e2) {
            e2.printStackTrace();
        }
        return false;
    }

    public void l() {
        if (k() && h() == null) {
            a(v3.ONLY_KEEPALIVE);
        }
    }
}
