package com.coralline.sea;

import android.content.Context;
import java.security.SecureRandom;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class p8 extends x6 {
    public static final String g = "sensitive_environment";
    public static final String h = "env_check";
    public long b;
    public int c;
    public int d;
    public int e;
    public boolean f;

    public p8() {
        super(g, 10);
        this.b = 0L;
        this.c = k0.b;
        this.d = k0.b;
        this.e = 0;
        this.f = false;
    }

    public final boolean a() {
        if (this.b > 0) {
            if ((System.currentTimeMillis() / 1000) - this.b < ((long) (new SecureRandom().nextInt(5) + 55)) * 20) {
                return false;
            }
        }
        this.b = System.currentTimeMillis() / 1000;
        return true;
    }

    public final boolean a(int i, int i2) {
        int i3 = this.c;
        if (!(i3 == -12312340 && this.d == -12312340) && i3 == i && this.d == i2) {
            return false;
        }
        this.c = i;
        this.d = i2;
        return true;
    }

    public final JSONObject b() {
        Context context;
        if (!a() || (context = n3.a().a) == null) {
            return null;
        }
        JSONObject jSONObjectB = k0.b(context);
        this.e++;
        if (jSONObjectB.length() > 0) {
            return jSONObjectB;
        }
        return null;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        JSONObject jSONObjectA = q8.a();
        if (jSONObjectA != null) {
            push(e2.b, h, jSONObjectA.toString());
        }
    }
}
