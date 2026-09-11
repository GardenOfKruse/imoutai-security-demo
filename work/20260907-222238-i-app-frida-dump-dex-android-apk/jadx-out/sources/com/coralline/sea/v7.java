package com.coralline.sea;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class v7 {
    public static String a(String str) {
        try {
            PackageManager packageManager = n3.a().a.getPackageManager();
            return packageManager.getApplicationInfo(str, 0).loadLabel(packageManager).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject a() {
        return n3.a().g ? b() : z1.a(p8.g);
    }

    public static String b(String str) {
        try {
            return n3.a().a.getPackageManager().getApplicationInfo(str, 0).sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            return i(str) ? d(str) : "app not install";
        }
    }

    public static JSONObject b() {
        JSONArray jSONArrayOptJSONArray;
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        try {
            jSONObject.put("risk_app", jSONArray);
            JSONArray jSONArrayOptJSONArray2 = o6.f().a("risk_app").optJSONArray("risk_pkgname");
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < jSONArrayOptJSONArray2.length(); i++) {
                String strOptString = jSONArrayOptJSONArray2.optString(i);
                if (!TextUtils.isEmpty(strOptString)) {
                    arrayList.add(strOptString);
                }
            }
            if (arrayList.size() > 0) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    jSONArray.put(new JSONObject().put("package", (String) it.next()));
                }
            }
            JSONObject jSONObjectA = z1.a(p8.g);
            if (jSONObjectA != null && (jSONArrayOptJSONArray = jSONObjectA.optJSONArray("risk_app")) != null && jSONArrayOptJSONArray.length() > 0) {
                for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                    JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i2);
                    if (!jSONArray.toString().contains(jSONObject2.getString("package"))) {
                        jSONArray.put(jSONObject2);
                    }
                }
            }
        } catch (Exception e) {
        }
        return jSONObject;
    }

    public static long c(@Nullable String str) {
        if (str == null) {
            return -1L;
        }
        try {
            return n3.a().a.getPackageManager().getPackageInfo(str, 0).firstInstallTime;
        } catch (Throwable th) {
            return -2L;
        }
    }

    public static JSONArray c() {
        JSONArray jSONArray = new JSONArray();
        try {
            JSONObject jSONObjectA = a();
            if (jSONObjectA != null) {
                JSONArray jSONArray2 = jSONObjectA.getJSONArray("risk_app");
                for (int i = 0; i < jSONArray2.length(); i++) {
                    JSONObject jSONObject = jSONArray2.getJSONObject(i);
                    String strA = a(jSONObject.getString("package"));
                    String string = jSONObject.getString("package");
                    JSONObject jSONObject2 = new JSONObject();
                    if ((strA != null || h(string)) && !jSONArray.toString().contains(string)) {
                        if (strA == null) {
                            strA = c7.c;
                        }
                        jSONObject2.put("name", jSONObject.optString("name", strA));
                        jSONObject2.put("package", string);
                        jSONObject2.put("app_md5", jSONObject.optString("md5"));
                        jSONArray.put(jSONObject2);
                    }
                }
            }
            jSONArray.toString(4);
            return jSONArray;
        } catch (Exception e) {
            return jSONArray;
        }
    }

    public static String d(String str) {
        StringBuilder sb;
        String str2;
        if (new File("/data/app/" + str.trim() + "-1.apk").exists()) {
            sb = new StringBuilder("/data/app/");
            sb.append(str.trim());
            str2 = "-1.apk";
        } else {
            if (!new File("/data/app/" + str.trim() + "-2.apk").exists()) {
                return "app is hide";
            }
            sb = new StringBuilder("/data/app/");
            sb.append(str.trim());
            str2 = "-2.apk";
        }
        sb.append(str2);
        return sb.toString();
    }

    @SuppressLint({"PackageManagerGetSignatures"})
    public static String e(String str) {
        try {
            PackageInfo packageInfoA = v6.a(n3.a().a, str, 64);
            if (packageInfoA == null) {
                return i(str) ? "app is hide don't can get md5" : "package not install";
            }
            Signature[] signatureArr = null;
            if (Build.VERSION.SDK_INT >= 28) {
                try {
                    signatureArr = (Signature[]) q7.a(q7.a(packageInfoA).c("signingInfo").c()).b("getApkContentsSigners").c();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (signatureArr == null) {
                signatureArr = packageInfoA.signatures;
            }
            Signature signature = signatureArr[0];
            if (signature == null) {
                return i(str) ? "app is hide don't can get md5" : "package not install";
            }
            MessageDigest.getInstance("MD5").update(signature.toByteArray());
            return c4.b(signature.toByteArray());
        } catch (Exception e2) {
            return "MD5 key failure";
        }
    }

    public static boolean f(String str) {
        PackageInfo packageInfoA;
        try {
            packageInfoA = v6.a(n3.a().a, str, 0);
        } catch (Exception e) {
            packageInfoA = null;
        }
        return packageInfoA != null;
    }

    public static boolean g(String str) {
        return f(str) || j(str) || h(str);
    }

    public static boolean h(String str) {
        return t.a().a(true).contains(str);
    }

    public static boolean i(String str) {
        return h(str) || j(str);
    }

    public static boolean j(String str) {
        if (str == null) {
            return false;
        }
        File file = new File("/data/data/" + str.trim());
        if (file.exists() && file.isDirectory()) {
            return true;
        }
        File file2 = new File("/data/app/" + str.trim() + "-1");
        File file3 = new File("/data/app/" + str.trim() + "-2");
        File file4 = new File("/data/app/" + str.trim() + "-1.apk");
        StringBuilder sb = new StringBuilder("/data/app/");
        sb.append(str.trim());
        sb.append("-2.apk");
        return file2.isDirectory() || file3.isDirectory() || file4.exists() || new File(sb.toString()).exists();
    }
}
