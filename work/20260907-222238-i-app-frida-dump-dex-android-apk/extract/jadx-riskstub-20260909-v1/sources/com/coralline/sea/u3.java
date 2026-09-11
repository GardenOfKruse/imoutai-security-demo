package com.coralline.sea;

import android.text.TextUtils;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class u3 {
    public static final String a = "FlowControlShareUtil";
    public static final String b = "flow_control";
    public static final String c = "location";
    public static final String d = "range";
    public static final String e = "total";
    public static final String f = "open";
    public static final String g = "msg_frequency";

    public static void a() {
        JSONObject jSONObjectA = a9.a(a9.d, (JSONObject) null);
        if (jSONObjectA == null) {
            return;
        }
        try {
            jSONObjectA.getString("ratio");
            int i = jSONObjectA.getInt("count");
            if (i > 0) {
                jSONObjectA.remove("count");
                jSONObjectA.put("count", i - 1);
                a9.b(a9.d, jSONObjectA.toString());
            } else {
                a9.b(a9.d);
            }
        } catch (JSONException | Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void a(String str, int i) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("ratio", str);
            jSONObject.put("count", i);
            a9.b(a9.d, jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public static boolean a(int i, int i2) {
        return new Random().nextInt(i2) < i;
    }

    public static void b() {
        a9.b(a9.d);
    }

    public static String c() {
        try {
            String strB = ja.b(n3.T.a, n3.a().t);
            try {
                return n3.T.s ? v1.a(strB) : strB;
            } catch (Exception e2) {
                return strB;
            }
        } catch (Exception e3) {
            return null;
        }
    }

    public static boolean d() {
        JSONObject jSONObjectA = a9.a(a9.d, (JSONObject) null);
        if (jSONObjectA == null) {
            return false;
        }
        try {
            String string = jSONObjectA.getString("ratio");
            jSONObjectA.getInt("count");
            if (!TextUtils.isEmpty(string)) {
                if (new Random().nextFloat() > Float.parseFloat(string)) {
                    return true;
                }
            }
        } catch (JSONException | Exception e2) {
            e2.printStackTrace();
        }
        return false;
    }
}
