package com.coralline.sea;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import com.coralline.sea.k8;
import java.net.UnknownHostException;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class n9 {
    public static int a(String str, String str2) {
        int length = 0;
        int i = 0;
        while (true) {
            int iIndexOf = str.indexOf(str2, length);
            if (iIndexOf == -1) {
                return i;
            }
            length = iIndexOf + str2.length();
            i++;
        }
    }

    public static boolean a() throws Throwable {
        String strI = ja.i("getprop service.adb.tcp.port");
        if (strI == null || strI.length() <= 1) {
            strI = "5037";
        }
        StringBuilder sb = new StringBuilder();
        for (char c : strI.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }
        try {
            return k8.b.a.a("127.0.0.1", Integer.parseInt(sb.toString().trim()));
        } catch (UnknownHostException e) {
            return false;
        }
    }

    public static JSONArray b() {
        JSONArray jSONArray = new JSONArray();
        try {
            JSONObject jSONObject = new JSONObject();
            boolean zA = q5.a(n3.a().a);
            boolean zC = c();
            boolean zA2 = a();
            if (zA || zC || zA2) {
                jSONObject.put("mock_location", zA);
                jSONObject.put("usb_debug", zC);
                jSONObject.put("tcp_adb", zA2);
                jSONObject.put("detail", new JSONArray().put(q8.b));
                jSONArray.put(jSONObject);
                return jSONArray;
            }
        } catch (Exception e) {
        }
        return jSONArray;
    }

    public static boolean c() {
        Bundle bundleCall = n3.a().a.getContentResolver().call(Uri.parse("content://settings/global"), "GET_global", "adb_enabled", (Bundle) null);
        String string = bundleCall != null ? bundleCall.getString("value") : null;
        if (!TextUtils.isEmpty(string)) {
            return "1".equals(string);
        }
        if (Build.VERSION.SDK_INT >= 17) {
            if (Settings.Secure.getInt(n3.T.a.getContentResolver(), "adb_enabled", 0) == 0) {
                return false;
            }
        } else if (Settings.Secure.getInt(n3.T.a.getContentResolver(), "adb_enabled", 0) == 0) {
            return false;
        }
        return true;
    }
}
