package com.coralline.sea;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import com.coralline.sea.z;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class t {
    public static final String a = "ApkListCollector";
    public static t b = null;
    public static final String c = "apk_list";
    public static final String e = "legacy_buf_7e";
    public static final HashMap<String, String> d = new HashMap<>();
    public static final HashSet<String> f = new a();
    public static final HashSet<String> g = new b();

    public class a extends HashSet<String> {
        public a() {
            add("com.android.bluetooth");
            add("com.android.phone");
            add("com.android.settings");
            add("com.android.systemui");
            add("com.android.providers.contacts");
            add("com.android.providers.media");
            add("com.android.providers.settings");
        }
    }

    public class b extends HashSet<String> {
        public b() {
            add("com.huawei.bluetooth");
            add("com.huawei.phoneservice");
            add("com.huawei.desktop.systemui");
            add("com.huawei.contacts");
            add("com.huawei.camera");
        }
    }

    public static synchronized t a() {
        if (b == null) {
            b = new t();
        }
        return b;
    }

    public static void a(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(e, 0);
            Map<String, ?> all = sharedPreferences.getAll();
            if (all.isEmpty()) {
                return;
            }
            for (String str : all.keySet()) {
                String string = sharedPreferences.getString(str, null);
                if (!TextUtils.isEmpty(string)) {
                    d.put(new String(Base64.decode(str.getBytes(StandardCharsets.UTF_8), 2)), new String(Base64.decode(string.getBytes(StandardCharsets.UTF_8), 2)));
                }
            }
        } catch (Exception e2) {
            e2.getMessage();
        }
    }

    public static boolean a(String str) {
        return ja.s() ? g.contains(str) || f.contains(str) : f.contains(str);
    }

    public long a(PackageInfo packageInfo) {
        try {
            return Build.VERSION.SDK_INT >= 9 ? packageInfo.lastUpdateTime : new File(packageInfo.applicationInfo.sourceDir).lastModified();
        } catch (Exception e2) {
            return System.currentTimeMillis();
        }
    }

    public final PackageInfo a(Context context, String str, int i) {
        try {
            return v6.a(context, str, i);
        } catch (Exception e2) {
            return null;
        }
    }

    public z a(Context context, String str, PackageInfo packageInfo) throws Throwable {
        z zVarA;
        long jA = a(packageInfo);
        HashMap<String, String> map = d;
        if (map.containsKey(str) && (zVarA = z.a(map.get(str))) != null && zVarA.e == jA) {
            return zVarA;
        }
        z zVarA2 = a(context, str, packageInfo, jA);
        if (zVarA2 != null) {
            a(context, str, zVarA2);
        }
        return zVarA2;
    }

    public final z a(Context context, String str, PackageInfo packageInfo, long j) throws Throwable {
        JSONObject jSONObjectA;
        try {
            String strA = a(context, str);
            String[] strArrA = e7.a(context, str);
            String strOptString = strArrA[0];
            String str2 = strArrA[1];
            String str3 = packageInfo.applicationInfo.sourceDir;
            String strC = c4.c(str3);
            if ((TextUtils.isEmpty(strC) || TextUtils.isEmpty(strOptString)) && (jSONObjectA = a(context, str, str3)) != null) {
                if (TextUtils.isEmpty(strC)) {
                    strC = jSONObjectA.optString("signMd5");
                }
                if (TextUtils.isEmpty(strOptString)) {
                    strOptString = jSONObjectA.optString("certificateMd5");
                }
            }
            z.b bVar = new z.b();
            bVar.a = strA;
            bVar.b = strOptString;
            bVar.c = str2;
            bVar.d = strC;
            bVar.e = j;
            return new z(bVar);
        } catch (Exception e2) {
            e2.getMessage();
            return null;
        }
    }

    public final String a(Context context, String str) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getApplicationInfo(str, 0).loadLabel(packageManager).toString();
        } catch (Exception e2) {
            e2.getMessage();
            return i2.b;
        }
    }

    public HashSet<String> a(boolean z) {
        HashSet<String> hashSet = new HashSet<>();
        try {
            List<PackageInfo> listA = v6.a(0);
            if (listA != null) {
                Iterator<PackageInfo> it = listA.iterator();
                while (it.hasNext()) {
                    hashSet.add(it.next().packageName);
                }
            }
        } catch (Exception e2) {
        }
        return hashSet;
    }

    public final JSONObject a(Context context, String str, String str2) {
        boolean zA;
        try {
            boolean zB = false;
            if (context.getPackageName().equals(str)) {
                zB = w7.b(context, w7.r);
                zA = w7.a(context, w7.s);
            } else {
                zA = false;
            }
            if (zB && zA) {
                return null;
            }
            return new JSONObject(i6.a(str2));
        } catch (Exception e2) {
            return null;
        }
    }

    public JSONObject a(Context context, JSONArray jSONArray) {
        String strJ;
        JSONArray jSONArray2 = jSONArray;
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray3 = new JSONArray();
        if (jSONArray2 == null || jSONArray.length() < 1) {
            return jSONObject;
        }
        try {
            strJ = ja.j("getprop  init.svc.rc_hippo");
        } catch (JSONException e2) {
        }
        if (!TextUtils.isEmpty(strJ) && !strJ.equals("\n")) {
            jSONArray3.put("init.svc.rc_hippo");
            jSONObject.put("code", 3).put("ratio", 1).put("detail", jSONArray3);
            return jSONObject;
        }
        HashSet<String> hashSetB = b(context, false);
        HashSet<String> hashSet = new HashSet<>();
        if (hashSetB.isEmpty()) {
            hashSet = a(false);
        }
        JSONArray jSONArray4 = jSONArray3;
        int i = 0;
        double d2 = 0.0d;
        int i2 = 0;
        while (i < jSONArray.length()) {
            JSONArray jSONArray5 = new JSONArray();
            JSONObject jSONObject2 = jSONArray2.getJSONObject(i);
            JSONArray jSONArray6 = jSONObject2.getJSONArray("packages");
            int length = jSONArray6.length();
            for (int i3 = 0; i3 < length; i3++) {
                String string = jSONArray6.getString(i3);
                if (v7.f(string) || hashSetB.contains(string) || hashSet.contains(string)) {
                    jSONArray5.put(string);
                }
            }
            if (jSONArray5.length() > 0) {
                double dRound = Math.round((((double) jSONArray5.length()) / ((double) length)) * 10.0d) / 10.0d;
                if (dRound > d2) {
                    d2 = dRound;
                    i2 = jSONObject2.getInt("code");
                    jSONArray4 = jSONArray5;
                }
            }
            i++;
            jSONArray2 = jSONArray;
        }
        if (d2 > 0.0d) {
            jSONObject.put("code", i2).put("ratio", d2).put("detail", jSONArray4);
            return jSONObject;
        }
        return jSONObject;
    }

    public JSONObject a(Context context, boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            List<PackageInfo> listA = v6.a(64);
            if (listA == null) {
                return jSONObject;
            }
            for (PackageInfo packageInfo : listA) {
                String str = packageInfo.packageName;
                if (!z || a(str) || (packageInfo.applicationInfo.flags & 129) == 0) {
                    JSONObject jSONObjectA = a(str, n3.a().a);
                    if (jSONObjectA != null && jSONObjectA.length() > 0) {
                        jSONObject.put(str, jSONObjectA);
                    }
                }
            }
            return jSONObject;
        } catch (Exception e2) {
            return jSONObject;
        }
    }

    public JSONObject a(String str, Context context) {
        PackageInfo packageInfoB;
        long jLastModified;
        String str2;
        JSONObject jSONObject = new JSONObject();
        try {
            if (context.getPackageName().equals(str)) {
                packageInfoB = context.getPackageManager().getPackageInfo(str, Build.VERSION.SDK_INT >= 28 ? 134217728 : 64);
            } else {
                packageInfoB = e7.b(context, str);
            }
        } catch (Exception e2) {
        }
        if (packageInfoB == null) {
            return jSONObject;
        }
        z zVarA = a(context, str, packageInfoB);
        jSONObject.put("pkg_name", str);
        jSONObject.put("app_name", zVarA.a);
        String str3 = packageInfoB.versionName;
        if (str3 != null) {
            jSONObject.put("ver_name", str3);
        } else {
            jSONObject.put("ver_name", c7.c);
        }
        jSONObject.put("ver_code", packageInfoB.versionCode);
        jSONObject.put("md5", zVarA.d);
        jSONObject.put("cert_issure", zVarA.c);
        jSONObject.put("cert_md5", zVarA.b);
        if (ja.b >= 9) {
            jSONObject.put("install_time", packageInfoB.firstInstallTime);
            str2 = "update_time";
            jLastModified = packageInfoB.lastUpdateTime;
        } else {
            jLastModified = new File(packageInfoB.applicationInfo.sourceDir).lastModified();
            jSONObject.put("install_time", jLastModified);
            str2 = "update_time";
        }
        jSONObject.put(str2, jLastModified);
        if (n3.a().d) {
            int i = context.getApplicationInfo().targetSdkVersion;
            jSONObject.put("target_sdk_version", i);
            jSONObject.put("targetSdkVersion", i);
            jSONObject.put("id", Build.ID);
            jSONObject.put("product", Build.PRODUCT);
            jSONObject.put("user", Build.USER);
            try {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                jSONObject.put("baseband", cls.getMethod("get", String.class, String.class).invoke(cls.newInstance(), "gsm.version.baseband", "no message"));
                return jSONObject;
            } catch (Exception e3) {
                System.out.println("Get baseband worong");
                return jSONObject;
            }
        }
        return jSONObject;
    }

    public void a(Context context, String str, z zVar) {
        String strB = zVar.b();
        d.put(str, strB);
        b(context, str, strB);
    }

    public PackageInfo b(Context context, String str) {
        PackageInfo packageInfoA;
        if (!str.equals(context.getPackageName())) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                packageInfoA = a(context, str, ((Integer) q7.b((Class<?>) PackageManager.class).c("GET_SIGNING_CERTIFICATES").c()).intValue());
            } catch (Exception e2) {
                packageInfoA = null;
            }
        } else {
            packageInfoA = null;
        }
        return packageInfoA == null ? a(context, str, 64) : packageInfoA;
    }

    public HashSet<String> b(Context context, boolean z) {
        HashSet<String> hashSet = new HashSet<>();
        try {
            List<PackageInfo> listA = v6.a(64);
            if (listA == null) {
                return hashSet;
            }
            for (PackageInfo packageInfo : listA) {
                if (!z || (packageInfo.applicationInfo.flags & 129) == 0) {
                    hashSet.add(packageInfo.packageName);
                }
            }
            return hashSet;
        } catch (Exception e2) {
            return hashSet;
        }
    }

    public final void b(Context context, String str, String str2) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(e, 0);
            sharedPreferences.edit().putString(Base64.encodeToString(str.getBytes(StandardCharsets.UTF_8), 2), Base64.encodeToString(str2.getBytes(StandardCharsets.UTF_8), 2)).apply();
        } catch (Exception e2) {
            e2.getMessage();
        }
    }
}
