package com.coralline.sea;

import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class t1 {
    public static final String a = "CheckerSwitch";
    public static final String b = "switch";
    public static final String c = "filter";
    public static final String d = "on_demand_switch_and_filter_disabled";
    public static Map<String, String> e;

    public enum a {
        BRAND("brand"),
        MODEL("model"),
        OS_VERSION("os_version"),
        VERSION_CLIENT("version_client"),
        APP_VERSION("app_version");

        public final String a;

        a(String str) {
            this.a = str;
        }

        public String a() {
            return this.a;
        }
    }

    @Nullable
    public static String a(@Nullable Map<String, String> map, @Nullable String str) {
        if (map == null || TextUtils.isEmpty(str)) {
            return null;
        }
        return map.get(str);
    }

    @Nullable
    public static String a(@Nullable JSONObject jSONObject, @Nullable String str) {
        if (jSONObject == null || TextUtils.isEmpty(str)) {
            return null;
        }
        String strTrim = jSONObject.optString(str).trim();
        if (strTrim.isEmpty()) {
            return null;
        }
        return strTrim;
    }

    @NonNull
    public static Map<String, String> a() {
        Map<String, String> map = e;
        if (map != null) {
            return map;
        }
        HashMap map2 = new HashMap();
        e = map2;
        map2.put(a.BRAND.a, Build.BRAND);
        e.put(a.MODEL.a, Build.MODEL);
        e.put(a.OS_VERSION.a, Build.VERSION.RELEASE);
        e.put(a.VERSION_CLIENT.a, n3.a().z);
        e.put(a.APP_VERSION.a, ja.f());
        return e;
    }

    @NonNull
    public static JSONArray a(@Nullable String str, @Nullable String str2) {
        return (str == null || str2 == null) ? new JSONArray() : a2.b(k2.d().b(), str, str2) ? a2.a(k2.d().b(), str, str2) : a2.a(z1.c, str, str2);
    }

    public static boolean a(@Nullable String str) {
        return !b(str);
    }

    public static boolean a(@Nullable String str, @Nullable String str2, boolean z) {
        if (str == null || str2 == null) {
            return z;
        }
        return a2.a(a2.b(k2.d().b(), str, str2) ? k2.d().b() : z1.c, str, str2, z);
    }

    public static boolean b(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (n3.a().g && !o6.f().c(str)) {
            return false;
        }
        if (!a(str, b, true)) {
            boolean z = n3.T.g;
            return false;
        }
        JSONArray jSONArrayA = a(str, c);
        if (jSONArrayA.length() == 0) {
            boolean z2 = n3.T.g;
            return true;
        }
        for (int i = 0; i < jSONArrayA.length(); i++) {
            try {
                Map<String, String> mapA = a();
                JSONObject jSONObjectOptJSONObject = jSONArrayA.optJSONObject(i);
                if (a(mapA, jSONObjectOptJSONObject, a.BRAND, a.MODEL, a.OS_VERSION, a.VERSION_CLIENT, a.APP_VERSION)) {
                    boolean z3 = n3.T.g;
                    Objects.toString(jSONObjectOptJSONObject);
                    Objects.toString(mapA);
                    return false;
                }
                continue;
            } catch (Exception e2) {
            }
        }
        boolean z4 = n3.T.g;
        return true;
    }

    public static boolean c(@Nullable String str) {
        return !d(str);
    }

    public static boolean d(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (n3.a().g && !o6.f().c(str)) {
            return false;
        }
        if (a(str, d, false)) {
            boolean z = n3.T.g;
            return true;
        }
        boolean zB = b(str);
        boolean z2 = n3.T.g;
        return zB;
    }

    public static boolean a(@Nullable Map<String, String> map, @Nullable JSONObject jSONObject, @Nullable a... aVarArr) {
        if (jSONObject == null || map == null || aVarArr == null || aVarArr.length == 0) {
            return false;
        }
        boolean z = false;
        for (a aVar : aVarArr) {
            String strA = a(jSONObject, aVar.a);
            if (strA != null) {
                if (!strA.equals(a(map, aVar.a))) {
                    return false;
                }
                z = true;
            }
        }
        return z;
    }
}
