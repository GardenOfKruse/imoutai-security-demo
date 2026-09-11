package com.coralline.sea;

import android.content.Context;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class u5 extends x6 {
    public static final String c = "board_phone";
    public static u5 d = null;
    public static boolean e = false;
    public String[] b;

    public u5() {
        super(c, 10);
        this.b = new String[]{"/data/local/tmp/xiaowei.jar", "/data/local/tmp/xdevice1.jar", "/data/local/tmp/xdevice.jar"};
    }

    public static u5 b() {
        if (d == null) {
            d = new u5();
        }
        return d;
    }

    public File a(String[] strArr) {
        for (String str : strArr) {
            File file = new File(str);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }

    public final JSONObject a() {
        File fileA;
        Context context = n3.a().a;
        if (context == null) {
            return null;
        }
        JSONObject jSONObjectB = k0.b(context);
        if (jSONObjectB.length() > 0) {
            try {
                int i = jSONObjectB.getInt("plugged");
                int i2 = jSONObjectB.getInt("counter");
                if (i == 2 && i2 <= 0 && (fileA = a(this.b)) != null) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("battery_plugged", "2");
                        jSONObject.put("battery_charge_counter", "0");
                        jSONObject.put("control_agent", fileA.getAbsoluteFile());
                        return jSONObject;
                    } catch (Exception e2) {
                        return null;
                    }
                }
            } catch (JSONException e3) {
                throw new RuntimeException(e3);
            }
        }
        return null;
    }

    public boolean a(String str) {
        return new File(str).exists();
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        JSONObject jSONObjectA = a();
        if (jSONObjectA == null || e) {
            return;
        }
        jSONObjectA.toString();
        push(e2.b, c, jSONObjectA.toString());
        e = true;
    }
}
