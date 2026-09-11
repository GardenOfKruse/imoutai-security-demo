package com.coralline.sea;

import android.content.Context;
import android.view.accessibility.AccessibilityManager;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class e extends x6 {
    public static final String c = "accessibility_service_enabled";
    public static e d;
    public String b;

    public e() {
        super(c, 10);
        this.b = c7.c;
    }

    public static e b() {
        if (d == null) {
            synchronized (e.class) {
                if (d == null) {
                    d = new e();
                }
            }
        }
        return d;
    }

    public int a(Context context) {
        return ((AccessibilityManager) context.getSystemService("accessibility")).isEnabled() ? 1 : 0;
    }

    public JSONObject a() {
        try {
            int iA = a(n3.a().a);
            if (iA == 0) {
                return null;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("is_accessibility_enabled", iA);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", "accessibility");
            jSONObject2.put("detail", jSONObject);
            return jSONObject2;
        } catch (Exception e) {
            e.toString();
            return null;
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        JSONObject jSONObjectA = a();
        if (jSONObjectA == null) {
            return;
        }
        if (this.b.equals(ja.o() + jSONObjectA.toString())) {
            return;
        }
        this.b = ja.o() + jSONObjectA.toString();
        push(e2.b, c, jSONObjectA.toString());
    }
}
