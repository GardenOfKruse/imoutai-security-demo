package com.coralline.sea;

import android.content.Context;
import android.telephony.TelephonyManager;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class c6 {
    public JSONObject a = null;
    public boolean b = false;

    public static boolean a(JSONObject jSONObject, JSONObject jSONObject2) {
        boolean z;
        if (jSONObject2 == null) {
            return false;
        }
        if (jSONObject == null) {
            return true;
        }
        try {
            String[] strArr = {"mcc", "mnc", "lac", "cid"};
            z = false;
            for (int i = 0; i < 4; i++) {
                try {
                    String str = strArr[i];
                    String string = c7.c;
                    String string2 = c7.c;
                    if (jSONObject.has(str)) {
                        string = jSONObject.getString(str);
                    }
                    if (jSONObject2.has(str)) {
                        string2 = jSONObject2.getString(str);
                    }
                    if (!string.equals(string2)) {
                        z = true;
                    }
                    if (z) {
                        return z;
                    }
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                    return z;
                }
            }
        } catch (Exception e2) {
            e = e2;
            z = false;
        }
        return z;
    }

    public final boolean a() {
        return ja.r("android.permission.ACCESS_COARSE_LOCATION") == 0 || ja.r("android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    public JSONObject b() {
        JSONObject jSONObjectB;
        String str;
        String str2;
        try {
            Context context = n3.a().a;
            JSONObject jSONObjectA = l6.a(context);
            if (jSONObjectA == null || jSONObjectA.length() == 0) {
                return null;
            }
            String strA = c8.a();
            if ("UNKNOWN".equals(strA)) {
                return null;
            }
            jSONObjectA.put("cell_type", strA);
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(m1.j);
            int iIntValue = k1.a().c(telephonyManager).intValue();
            if (iIntValue > 0) {
                jSONObjectA.put("neighbor_cell_num", iIntValue);
            }
            this.b = a();
            int phoneType = telephonyManager.getPhoneType();
            if (phoneType != 1) {
                if (phoneType != 2) {
                    telephonyManager.getPhoneType();
                } else if (this.b && (jSONObjectB = k1.a().a(telephonyManager)) != null) {
                    str = "cid";
                    str2 = "cid";
                    jSONObjectA.put(str, jSONObjectB.getInt(str2));
                }
            } else if (this.b && (jSONObjectB = k1.a().b(telephonyManager)) != null) {
                jSONObjectA.put("cid", jSONObjectB.getInt("cid"));
                str = "lac";
                str2 = "lac";
                jSONObjectA.put(str, jSONObjectB.getInt(str2));
            }
            if (!a(this.a, jSONObjectA)) {
                return null;
            }
            this.a = jSONObjectA;
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(jSONObjectA);
            jSONObject.put("type", "mobile");
            jSONObject.put("cell_info", jSONArray);
            return jSONObject;
        } catch (Exception e) {
            return null;
        }
    }
}
