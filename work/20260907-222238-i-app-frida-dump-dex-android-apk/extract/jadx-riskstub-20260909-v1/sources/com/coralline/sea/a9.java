package com.coralline.sea;

import android.content.SharedPreferences;
import android.util.Base64;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class a9 {
    public static final String a = "K_MSG_DEV";
    public static final String b = "K_LICENSE";
    public static final String c = "K_12345";
    public static final String d = "K_FLOW_CONTROL_V1";
    public static final String e = "K_START_TIMES";
    public static final String f = "EVERISK_START_ID";
    public static final String g = "K_APP_VER";
    public static final String h = "K_7272896";
    public static final String i = "key_magisk_files";
    public static final String j = "userdata_pre";
    public static SharedPreferences k = n3.a().a.getSharedPreferences(n3.T.u, 0);

    public static long a(String str, long j2) {
        String strA = a(str, (String) null);
        if (strA == null) {
            return j2;
        }
        try {
            return Long.parseLong(strA);
        } catch (Exception e2) {
            b(str);
            return j2;
        }
    }

    public static String a(String str, String str2) {
        try {
            synchronized (a9.class) {
                SharedPreferences sharedPreferences = k;
                if (sharedPreferences == null) {
                    return str2;
                }
                String string = sharedPreferences.getString(str, c7.c);
                if (string.length() == 0) {
                    return str2;
                }
                if (n3.a().B) {
                    return v1.a(string, i6.r(), i6.q());
                }
                return v1.a(string);
            }
        } catch (Exception e2) {
            b(str);
            return str2;
        }
    }

    public static JSONObject a(String str, JSONObject jSONObject) {
        String strA = a(str, (String) null);
        if (strA == null) {
            return jSONObject;
        }
        try {
            return new JSONObject(strA);
        } catch (Exception e2) {
            b(str);
            return jSONObject;
        }
    }

    public static boolean a(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        k.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        return true;
    }

    public static boolean a(String str, byte[] bArr) {
        try {
            synchronized (a9.class) {
                if (k == null) {
                    return false;
                }
                k.edit().putString(str, Base64.encodeToString(v1.c(bArr), 2)).apply();
                return true;
            }
        } catch (Exception e2) {
            return false;
        }
    }

    public static byte[] a(String str) {
        try {
            synchronized (a9.class) {
                SharedPreferences sharedPreferences = k;
                if (sharedPreferences == null) {
                    return null;
                }
                String string = sharedPreferences.getString(str, c7.c);
                if (string.length() == 0) {
                    return null;
                }
                return v1.a(Base64.decode(string, 2));
            }
        } catch (Exception e2) {
            b(str);
            return null;
        }
    }

    public static void b(String str) {
        if (n3.a().a != null) {
            k.edit().remove(str).apply();
        }
    }

    public static boolean b(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        k.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        return true;
    }

    public static boolean b(String str, long j2) {
        return b(str, String.valueOf(j2));
    }

    public static boolean b(String str, String str2) {
        try {
            synchronized (a9.class) {
                if (k == null) {
                    return false;
                }
                k.edit().putString(str, n3.a().B ? v1.b(str2, i6.r(), i6.q()) : v1.c(str2)).apply();
                return true;
            }
        } catch (Exception e2) {
            return false;
        }
    }

    public static boolean b(String str, JSONObject jSONObject) {
        return b(str, jSONObject.toString());
    }
}
