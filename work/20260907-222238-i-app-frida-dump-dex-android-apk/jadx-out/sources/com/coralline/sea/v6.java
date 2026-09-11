package com.coralline.sea;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class v6 {
    public static final String a = "PackageInfos";
    public static int b;
    public static PackageInfo c;
    public static ConcurrentHashMap<String, PackageInfo> d = new ConcurrentHashMap<>(20, 0.6f);
    public static List<PackageInfo> e = null;

    public static int a() {
        if (Build.VERSION.SDK_INT < 28) {
            return 64;
        }
        try {
            return ((Integer) q7.b((Class<?>) PackageManager.class).c("GET_SIGNING_CERTIFICATES").c()).intValue();
        } catch (Throwable th) {
            th.toString();
            return 64;
        }
    }

    public static PackageInfo a(Context context, String str, int i) {
        if (str.equals(c7.c)) {
            return null;
        }
        return d.get(str);
    }

    public static List<PackageInfo> a(int i) {
        return new ArrayList(d.values());
    }

    public static void a(String str) {
        PackageInfo packageInfoA;
        String str2;
        if (TextUtils.isEmpty(str) || (packageInfoA = u.a(str, 64)) == null || (str2 = packageInfoA.packageName) == null) {
            return;
        }
        d.put(str2, packageInfoA);
    }

    public static void b() {
        String str;
        Context context = n3.a().a;
        int iA = a();
        if (e == null) {
            e = s.b(context, iA);
            o.a(context);
        }
        List<PackageInfo> list = e;
        if (list != null) {
            for (PackageInfo packageInfo : list) {
                if (packageInfo != null && (str = packageInfo.packageName) != null) {
                    d.put(str, packageInfo);
                }
            }
            if (d.size() > 0) {
                b = 1;
            }
        }
    }

    public static void b(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        d.remove(str);
    }

    public static boolean b(Context context, String str, int i) {
        return d.get(str) != null;
    }
}
