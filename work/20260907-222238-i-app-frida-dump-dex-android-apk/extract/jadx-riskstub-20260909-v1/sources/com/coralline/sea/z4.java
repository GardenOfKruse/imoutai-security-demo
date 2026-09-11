package com.coralline.sea;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import org.json.JSONArray;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class z4 {
    public static z4 a = null;
    public static final String b = "imei_imsi";

    public static synchronized z4 c() {
        if (a == null) {
            a = new z4();
        }
        return a;
    }

    public String a() {
        return n3.a().I;
    }

    public JSONArray a(String str) {
        JSONArray jSONArray = new JSONArray();
        Context context = n3.a().a;
        if (ja.r("android.permission.READ_PHONE_STATE") == -1) {
            jSONArray.put("N/P");
            return jSONArray;
        }
        if (context == null || str == null || ja.t()) {
            jSONArray.put(i2.b);
            return jSONArray;
        }
        jSONArray.put(n3.T.I);
        if (str.equals("imsi") && ja.b >= 21 && TextUtils.isEmpty(a9.a("imsi", c7.c))) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(m1.j);
                if (telephonyManager == null) {
                    jSONArray.put(i2.b);
                    return jSONArray;
                }
                String str2 = (String) q7.a(telephonyManager).a(d2.k, 1).c();
                jSONArray.put(str2);
                a9.b("imsi", str2);
            } catch (Exception e) {
            }
        }
        return jSONArray;
    }

    public String b() {
        String subscriberId = i2.b;
        if (ja.r("android.permission.READ_PHONE_STATE") == -1) {
            return "N/P";
        }
        if (ja.t()) {
            return i2.b;
        }
        try {
            Context context = n3.a().a;
            if (context == null) {
                return i2.b;
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(m1.j);
            if (telephonyManager != null) {
                subscriberId = telephonyManager.getSubscriberId();
            }
            return subscriberId == null ? i2.b : subscriberId;
        } catch (SecurityException | Exception e) {
            return i2.b;
        }
    }
}
