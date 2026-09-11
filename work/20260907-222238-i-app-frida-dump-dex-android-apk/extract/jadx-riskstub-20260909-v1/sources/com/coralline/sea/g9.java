package com.coralline.sea;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class g9 extends t6 {
    public static final String a = "startup";
    public static final String b = "start_info";
    public static final String c = "start";
    public static final String d = "emulator";
    public static final String e = "multi_open";
    public static final String f = "new_root";
    public static final String g = "devinfo";
    public static final String h = "xposed";
    public static final String i = "tweak_me";
    public static final String j = "wifi";
    public static final String k = "apkinfo_self";
    public static final String l = "cloud_phone";
    public static final String m = "rom_file";
    public static final String n = "custom_rom";
    public static boolean o = false;
    public static boolean p = false;

    public g9() {
        super(a);
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        JSONObject jSONObjectA;
        JSONObject jSONObjectD;
        JSONObject jSONObjectA2;
        Thread.currentThread().getName();
        Thread.currentThread().getId();
        boolean z = n3.a().g;
        JSONArray jSONArray = new JSONArray();
        boolean z2 = n3.T.M;
        if (n3.T.M) {
            v6.b();
        }
        JSONObject jSONObject = new JSONObject();
        if (!j6.d()) {
            try {
                new Date(System.currentTimeMillis()).toString();
                JSONObject jSONObjectA3 = z1.a(this.checkerName);
                Context context = n3.T.a;
                x4 x4VarC = a5.a().c();
                if (!z && t1.b("emulator") && (jSONObjectA2 = i3.a()) != null && jSONObjectA2.length() > 0) {
                    jSONObjectA2.put("protol_type", "emulator");
                    jSONArray.put(jSONObjectA2);
                }
                System.currentTimeMillis();
                if (!z && t1.b(e) && x4VarC.c(context)) {
                    JSONObject jSONObjectA4 = x4VarC.a(context);
                    jSONObjectA4.put("protol_type", e);
                    jSONArray.put(jSONObjectA4);
                }
                System.currentTimeMillis();
                if (!z && t1.b(f) && (jSONObjectD = x4VarC.d(context)) != null && jSONObjectD.length() > 0 && jSONObjectD.optBoolean("is_root")) {
                    p = true;
                    JSONObject jSONObject2 = new JSONObject(jSONObjectD.toString());
                    jSONObject2.put("protol_type", f);
                    jSONArray.put(jSONObject2);
                }
                System.currentTimeMillis();
                if (!z) {
                    JSONObject jSONObjectA5 = x2.a(0);
                    jSONObjectA5.put("protol_type", g);
                    jSONArray.put(jSONObjectA5);
                    System.currentTimeMillis();
                    if (t1.b(p8.g)) {
                        JSONObject jSONObjectB = w7.B();
                        if (jSONObjectB.length() > 0) {
                            jSONObjectB.put("protol_type", h);
                            jSONArray.put(jSONObjectB);
                        }
                    }
                    System.currentTimeMillis();
                    JSONObject jSONObjectF = l6.f();
                    if (jSONObjectF != null && jSONObjectF.length() > 0) {
                        JSONObject jSONObject3 = new JSONObject(jSONObjectF.toString());
                        jSONObject3.remove("type");
                        jSONObject3.put("protol_type", "wifi");
                        jSONArray.put(jSONObject3);
                    }
                    System.currentTimeMillis();
                    JSONObject jSONObjectA6 = t.a().a(context.getPackageName(), context);
                    if (jSONObjectA6 != null && jSONObjectA6.length() > 0) {
                        jSONObjectA6.put("protol_type", k);
                        jSONArray.put(jSONObjectA6);
                    }
                    System.currentTimeMillis();
                    if (t1.b(l) && (jSONObjectA = t.a().a(context, jSONObjectA3.optJSONArray(l))) != null && jSONObjectA.length() > 0) {
                        jSONObjectA.put("protol_type", l);
                        jSONArray.put(jSONObjectA);
                    }
                    System.currentTimeMillis();
                    if (t1.b(n)) {
                        JSONObject jSONObjectA7 = y2.a().a(jSONObjectA3.optJSONArray(m));
                        String[] strArrA = e7.a(context, a0.b);
                        String str = strArrA[1];
                        String str2 = strArrA[0];
                        String str3 = strArrA[2];
                        if (jSONObjectA7 != null && jSONObjectA7.length() > 0) {
                            JSONObject jSONObject4 = new JSONObject();
                            jSONObject4.put(m, jSONObjectA7);
                            jSONObject4.put("protol_type", n);
                            jSONObject4.put("device", Build.MODEL);
                            jSONObject4.put("build_tags", Build.TAGS);
                            jSONObject4.put("rom_issuer", str);
                            jSONObject4.put("platform_cert_md5", str2);
                            jSONObject4.put("rom_subject", str3);
                            jSONArray.put(jSONObject4);
                        }
                        System.currentTimeMillis();
                    }
                }
            } catch (Exception e2) {
            }
        }
        JSONObject jSONObjectE = x5.c().e();
        try {
            jSONObjectE.put("protol_type", c);
            jSONArray.put(jSONObjectE);
        } catch (Exception e3) {
        }
        jSONArray.toString();
        int i2 = 0;
        while (true) {
            if (i2 >= jSONArray.length()) {
                break;
            }
            if (TextUtils.equals(jSONArray.optJSONObject(i2).optString("protol_type", c7.c), g) && TextUtils.isEmpty(jSONArray.optJSONObject(i2).optString("model", c7.c))) {
                JSONObject jSONObjectA8 = x2.a(0);
                try {
                    jSONObjectA8.put("protol_type", g);
                    jSONArray.remove(i2);
                    jSONArray.put(jSONObjectA8);
                    break;
                } catch (Exception e4) {
                }
            } else {
                i2++;
            }
        }
        jSONArray.toString();
        if (j2.b("suspendSendStartInfo")) {
            o = false;
            return;
        }
        try {
            jSONObject.put("data", jSONArray);
            push(e2.b, b, jSONObject.toString());
            o = true;
        } catch (JSONException e5) {
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void flush() {
        super.flush();
        start();
        check();
    }
}
