package com.coralline.sea;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class s extends c7 {
    public static final String i = "ApkList";
    public static ConcurrentHashMap<String, String> j = new ConcurrentHashMap<>(20, 0.6f);
    public static List<ApplicationInfo> k = null;
    public static List<PackageInfo> l = null;
    public static String m = c7.c;
    public static String n = c7.c;
    public static long o = 0;
    public static long p = 0;

    public static String a(boolean z) {
        return a(z, true);
    }

    public static synchronized String a(boolean z, boolean z2) {
        if (z) {
            return c(z2);
        }
        return b(z2);
    }

    public static List<ApplicationInfo> a(Context context, int i2) {
        return a(context, i2, true);
    }

    public static synchronized List<ApplicationInfo> a(Context context, int i2, boolean z) {
        if (!c7.b(d2.H)) {
            if (!z) {
                return null;
            }
            return k;
        }
        PackageManager packageManager = context.getPackageManager();
        if (ja.b(context)) {
            k = packageManager.getInstalledApplications(i2);
        }
        return k;
    }

    public static String b(boolean z) throws Throwable {
        String str;
        if (!c7.b(d2.I)) {
            return z ? n : c7.c;
        }
        if (j.get("pmlist") == null) {
            if (p > 0) {
                if ((System.currentTimeMillis() / 1000) - p < new SecureRandom().nextInt(10) + 70) {
                    return n;
                }
            }
            String strI = ja.i("pm list package");
            n = strI;
            j.put("pmlist", strI);
            p = System.currentTimeMillis() / 1000;
            if (n == null) {
                str = c7.c;
            }
            return n;
        }
        str = j.get("pmlist");
        n = str;
        return n;
    }

    public static List<PackageInfo> b(Context context, int i2) {
        return b(context, i2, true);
    }

    public static synchronized List<PackageInfo> b(Context context, int i2, boolean z) {
        if (!c7.b(d2.G)) {
            if (!z) {
                return null;
            }
            return l;
        }
        PackageManager packageManager = context.getPackageManager();
        if (ja.b(context)) {
            List<PackageInfo> installedPackages = packageManager.getInstalledPackages(i2);
            l = installedPackages;
            Objects.toString(installedPackages);
        }
        return l;
    }

    public static String c(boolean z) throws Throwable {
        String str;
        if (!c7.b(d2.I)) {
            return z ? m : c7.c;
        }
        if (j.get("pmlist") == null) {
            if (o > 0) {
                if (!TextUtils.isEmpty(m)) {
                    return m;
                }
                if ((System.currentTimeMillis() / 1000) - o < new SecureRandom().nextInt(10) + 70) {
                    return m;
                }
            }
            String strI = ja.i("pm list package -3");
            m = strI;
            j.put("pmlist", strI);
            o = System.currentTimeMillis() / 1000;
            if (m == null) {
                str = c7.c;
            }
            return m;
        }
        str = j.get("pmlist");
        m = str;
        return m;
    }
}
