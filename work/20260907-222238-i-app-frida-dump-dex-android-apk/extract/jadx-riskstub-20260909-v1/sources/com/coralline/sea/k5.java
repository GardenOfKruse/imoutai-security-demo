package com.coralline.sea;

import android.text.TextUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class k5 extends t6 {
    public static final int c = 604800000;
    public static final String d = "license_sync_time_key";
    public static k5 e;
    public volatile boolean a;
    public l4 b;

    public class a implements l4 {
        public a() {
        }

        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) {
            try {
                JSONObject jSONObject = new JSONObject(s1Var.d());
                if (jSONObject.has(x9.h)) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(x9.h);
                    if (jSONObject2.length() < 1) {
                        return;
                    }
                    k5 k5Var = k5.this;
                    k5Var.a = k5Var.a(jSONObject2.optString("end_time"));
                    k5.this.a(jSONObject2);
                }
                b9.b(jSONObject.optString(c2.b));
            } catch (Exception e) {
            }
        }
    }

    public k5() {
        super(x9.h);
        this.a = false;
        this.b = new a();
    }

    public static synchronized k5 a() {
        if (e == null) {
            e = new k5();
        }
        return e;
    }

    public void a(JSONObject jSONObject) {
        a9.b(a9.b, jSONObject);
        a9.b(d, String.valueOf(System.currentTimeMillis()));
    }

    public final void a(boolean z) {
        try {
            j1.c(this.b, this.checkerName);
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(x9.h);
            jSONObject.put("type", jSONArray);
            jSONObject.put(c2.a, true);
            s1 s1Var = new s1(jSONObject.toString(), y1.b(e2.c), this.checkerName, e2.c, false);
            if (z) {
                y9.b(s1Var, -1L);
            } else {
                y9.b(s1Var);
            }
        } catch (Exception e2) {
        }
    }

    public final boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Etc/GMT+0"));
        return simpleDateFormat.parse(str).getTime() > simpleDateFormat.parse(simpleDateFormat.format(new Date())).getTime();
    }

    public boolean b() {
        JSONObject jSONObjectA = a9.a(a9.b, (JSONObject) null);
        if (jSONObjectA != null && jSONObjectA.length() > 0) {
            jSONObjectA.toString();
            if (a(jSONObjectA.optString("end_time"))) {
                return true;
            }
        }
        a(true);
        return this.a;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        if (System.currentTimeMillis() - a9.a(d, 0L) >= 604800000) {
            a(false);
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void flush() {
        super.flush();
        a9.b(d, 0L);
        check();
    }
}
