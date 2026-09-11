package com.coralline.sea;

import android.app.usage.UsageStats;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import com.coralline.sea.r9;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class e7 {
    public static boolean a = true;
    public static HashMap<String, HashMap<String, String>> b = new HashMap<>();
    public static Field c = null;
    public static a0 d = null;
    public static boolean e = false;

    public static String a(String str) {
        ApplicationInfo applicationInfo;
        CharSequence charSequenceLoadLabel;
        try {
            PackageManager packageManager = n3.a().a.getPackageManager();
            PackageInfo packageInfoA = v6.a(n3.T.a, str, 0);
            return (packageInfoA == null || (applicationInfo = packageInfoA.applicationInfo) == null || (charSequenceLoadLabel = applicationInfo.loadLabel(packageManager)) == null) ? c7.c : charSequenceLoadLabel.toString().trim();
        } catch (Exception e2) {
            return c7.c;
        }
    }

    public static HashMap<String, String> a(Context context, String str, HashMap<String, r9.b> map) {
        HashMap<String, String> map2 = new HashMap<>();
        if (context != null && str != null) {
            try {
                if (str.length() == 0 || !b(str)) {
                    return map2;
                }
                String str2 = a(context, str)[0];
                map2.put("package_name", str);
                map2.put("cert_md5", str2);
                map2.put("app_name", a(str));
                if (map == null) {
                    map2.put("pname", c7.c);
                    map2.put("uname", c7.c);
                    return map2;
                }
                r9.b bVar = map.get(str);
                if (bVar == null) {
                    return null;
                }
                map2.put("pname", bVar.f);
                map2.put("uname", bVar.a);
                return map2;
            } catch (Exception e2) {
            }
        }
        return map2;
    }

    public static JSONArray a() {
        HashMap<String, r9.b> mapA = r9.a().a(Boolean.FALSE);
        if (mapA == null) {
            return null;
        }
        return a(mapA.keySet(), mapA);
    }

    public static JSONArray a(Context context) {
        HashSet hashSet = new HashSet();
        List<UsageStats> listC = ja.c(context);
        if (listC == null || listC.isEmpty()) {
            return null;
        }
        if (d == null) {
            d = new a0(n3.a().a);
        }
        for (int i = 0; i < listC.size(); i++) {
            String packageName = listC.get(i).getPackageName();
            if (!d.a(packageName) && !packageName.startsWith("com.android.") && !packageName.startsWith("com.mi.") && !packageName.startsWith("com.huawei.")) {
                hashSet.add(packageName);
            }
        }
        return a(hashSet, (HashMap<String, r9.b>) null);
    }

    public static JSONArray a(Set<String> set, HashMap<String, r9.b> map) {
        JSONArray jSONArray = new JSONArray();
        try {
            Set<String> setKeySet = b.keySet();
            setKeySet.toString();
            set.toString();
            HashSet<String> hashSet = new HashSet(set);
            hashSet.removeAll(setKeySet);
            HashSet hashSet2 = new HashSet(setKeySet);
            hashSet2.removeAll(set);
            for (String str : hashSet) {
                HashMap<String, String> mapA = a(n3.a().a, str, map);
                if (mapA != null && !mapA.isEmpty()) {
                    b.put(str, mapA);
                    JSONObject jSONObject = new JSONObject(mapA);
                    jSONObject.put(g9.a, true);
                    jSONArray.put(jSONObject);
                }
            }
            b.keySet().removeAll(hashSet2);
            return jSONArray;
        } catch (Exception e2) {
            e2.printStackTrace();
            return jSONArray;
        }
    }

    public static String[] a(Context context, String str) {
        PackageInfo packageInfoB = b(context, str);
        if (packageInfoB == null) {
            return new String[]{c7.c, c7.c};
        }
        String str2 = c7.c;
        String str3 = c7.c;
        String string = c7.c;
        Signature[] signatureArr = null;
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                signatureArr = (Signature[]) q7.a(q7.a(packageInfoB).c("signingInfo").c()).b("getApkContentsSigners").c();
            } catch (Exception e2) {
            }
        }
        if (signatureArr == null) {
            signatureArr = packageInfoB.signatures;
        }
        if (signatureArr != null) {
            try {
                X509Certificate x509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(signatureArr[0].toByteArray()));
                String strB = c4.b(x509Certificate.getEncoded());
                try {
                    String string2 = x509Certificate.getIssuerX500Principal().toString();
                    try {
                        string = x509Certificate.getSubjectX500Principal().toString();
                    } catch (Exception e3) {
                    }
                    str3 = string2;
                } catch (Exception e4) {
                }
                str2 = strB;
            } catch (Exception e5) {
            }
        }
        return new String[]{str2, str3, string};
    }

    public static PackageInfo b(Context context, String str) {
        PackageInfo packageInfoA;
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                packageInfoA = v6.a(context, str, ((Integer) q7.b((Class<?>) PackageManager.class).c("GET_SIGNING_CERTIFICATES").c()).intValue());
            } catch (Exception e2) {
                packageInfoA = null;
            }
        } else {
            packageInfoA = null;
        }
        if (packageInfoA == null) {
            packageInfoA = u.a(str, 64);
        }
        return packageInfoA == null ? v6.a(context, str, 64) : packageInfoA;
    }

    public static JSONArray b() {
        try {
            if (a && !b.isEmpty()) {
                a = false;
            }
            return b(n3.a().a);
        } catch (Exception e2) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x002a A[Catch: all -> 0x0021, Exception -> 0x0032, TRY_ENTER, TRY_LEAVE, TryCatch #4 {Exception -> 0x0032, all -> 0x0021, blocks: (B:9:0x0016, B:10:0x0018, B:12:0x001c, B:19:0x002a), top: B:36:0x0016 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0043 A[PHI: r4
  0x0043: PHI (r4v6 org.json.JSONArray) = (r4v7 org.json.JSONArray), (r4v9 org.json.JSONArray) binds: [B:33:0x0041, B:24:0x0034] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static JSONArray b(Context context) throws Throwable {
        JSONArray jSONArrayA;
        JSONArray jSONArrayA2;
        int i = Build.VERSION.SDK_INT;
        try {
        } catch (Exception e2) {
            jSONArrayA = null;
        } catch (Throwable th) {
            th = th;
            jSONArrayA = null;
        }
        if (n3.a().P >= 23) {
            jSONArrayA = a(context);
            if (jSONArrayA == null && i == 23) {
                try {
                    e = false;
                } catch (Exception e3) {
                    if (jSONArrayA != null) {
                    }
                    return jSONArrayA;
                } catch (Throwable th2) {
                    th = th2;
                    if (jSONArrayA != null) {
                        jSONArrayA.toString();
                    }
                    throw th;
                }
            }
            if (!e) {
                jSONArrayA2 = a();
            }
            if (jSONArrayA != null) {
                if (jSONArrayA.length() == 0) {
                    jSONArrayA = null;
                }
            }
            if (jSONArrayA != null) {
                jSONArrayA.toString();
            }
            return jSONArrayA;
        }
        jSONArrayA2 = a();
        jSONArrayA = jSONArrayA2;
        if (jSONArrayA != null) {
        }
        if (jSONArrayA != null) {
        }
        return jSONArrayA;
    }

    public static boolean b(String str) {
        ApplicationInfo applicationInfo;
        try {
            PackageManager packageManager = n3.a().a.getPackageManager();
            if (packageManager != null && (applicationInfo = packageManager.getApplicationInfo(str, 0)) != null) {
                if ((applicationInfo.flags & 1) == 0) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException e2) {
        }
        return false;
    }
}
