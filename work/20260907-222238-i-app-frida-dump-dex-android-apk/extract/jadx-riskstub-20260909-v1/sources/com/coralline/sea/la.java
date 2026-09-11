package com.coralline.sea;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.util.Pair;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipFile;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class la {
    public static final String a = "me.weishu.exposed.ExposedApplication";
    public static String b = "virtual_env";

    public static List<String> a(Context context) {
        ArrayList<String> stringArrayList;
        try {
            Bundle bundleCall = context.getContentResolver().call(Uri.parse("content://me.weishu.exposed.CP/"), "apps", (String) null, (Bundle) null);
            return (bundleCall == null || (stringArrayList = bundleCall.getStringArrayList("apps")) == null) ? Collections.emptyList() : stringArrayList;
        } catch (Throwable th) {
            return Collections.emptyList();
        }
    }

    public static JSONArray a() {
        Context context = n3.a().a;
        JSONArray jSONArray = new JSONArray();
        try {
            JSONObject jSONObjectC = c(context);
            JSONObject jSONObjectB = b(context);
            JSONObject jSONObjectE = e(context);
            if (jSONObjectC.length() > 0) {
                jSONArray.put(jSONObjectC);
            }
            if (jSONObjectB.length() > 0) {
                jSONArray.put(jSONObjectB);
            }
            if (jSONObjectE.length() > 0) {
                jSONArray.put(jSONObjectE);
                return jSONArray;
            }
        } catch (Exception e) {
        }
        return jSONArray;
    }

    public static boolean a(String str) {
        try {
            return new ZipFile(str).getEntry("assets/xposed_init") != null;
        } catch (IOException e) {
            return false;
        }
    }

    public static JSONObject b(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            Pair<Boolean, Boolean> pairD = d();
            JSONArray jSONArray = new JSONArray();
            if (((Boolean) pairD.first).booleanValue()) {
                jSONArray.put("class");
            }
            if (((Boolean) pairD.second).booleanValue()) {
                jSONArray.put("maps");
            }
            if (((Boolean) pairD.second).booleanValue() || ((Boolean) pairD.first).booleanValue()) {
                jSONObject.put("moudle", d(context));
                jSONObject.put("detail", jSONArray);
                jSONObject.put("type", "taichi");
                return jSONObject;
            }
        } catch (Exception e) {
        }
        return jSONObject;
    }

    public static boolean b() throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        boolean z = false;
        try {
            try {
                try {
                    bufferedReader = new BufferedReader(new FileReader(new File("/proc/" + Process.myPid() + "/maps")));
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line != null) {
                                if (!TextUtils.isEmpty(line) && line.contains("/data/app/me.weishu.exp")) {
                                    String[] strArrSplit = line.split("\\s+");
                                    if (strArrSplit.length >= 6) {
                                        String str = strArrSplit[5];
                                        if (str.startsWith("/data/app/me.weishu.exp") && (str.endsWith(".so") || str.endsWith(".dex") || str.endsWith(".vdex") || str.endsWith(".odex"))) {
                                            break;
                                        }
                                    }
                                }
                            } else {
                                break;
                            }
                        } catch (Exception e) {
                            e = e;
                            bufferedReader2 = bufferedReader;
                            e.printStackTrace();
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                                return z;
                            }
                            return z;
                        } catch (Throwable th) {
                            th = th;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e2) {
                                }
                            }
                            throw th;
                        }
                    }
                    z = true;
                    bufferedReader.close();
                    bufferedReader.close();
                    return z;
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = bufferedReader2;
            }
        } catch (IOException e4) {
        }
    }

    public static JSONObject c(Context context) {
        PackageInfo packageInfoB;
        JSONObject jSONObject = new JSONObject();
        try {
            if (a5.a().c().c(context) && (packageInfoB = a5.a().c().b(context)) != null && packageInfoB.packageName.startsWith(w7.e)) {
                jSONObject.put("moudle", d(context));
                jSONObject.put("detail", new JSONArray().put(packageInfoB.applicationInfo.sourceDir));
                jSONObject.put("type", "virtual_xposed");
                return jSONObject;
            }
        } catch (Exception e) {
        }
        return jSONObject;
    }

    public static boolean c() {
        try {
            Class.forName(a);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static Pair<Boolean, Boolean> d() {
        return new Pair<>(Boolean.valueOf(c()), Boolean.valueOf(b()));
    }

    public static JSONArray d(Context context) {
        JSONArray jSONArray = new JSONArray();
        List<PackageInfo> listA = v6.a(64);
        if (listA != null) {
            for (PackageInfo packageInfo : listA) {
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                if (applicationInfo != null && !TextUtils.isEmpty(applicationInfo.sourceDir) && a(packageInfo.applicationInfo.sourceDir)) {
                    jSONArray.put(t.a().a(packageInfo.packageName, context));
                }
            }
        }
        return jSONArray;
    }

    public static JSONObject e(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObjectA = t.a().a("com.yiqiang.advancedsetting", context);
            JSONObject jSONObjectA2 = t.a().a("com.excelliance.transfile", context);
            int length = jSONObjectA.length();
            int length2 = jSONObjectA2.length();
            if (length > 0 && length2 > 0) {
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(jSONObjectA);
                jSONArray.put(jSONObjectA2);
                jSONObject.put("moudle", jSONArray);
                jSONObject.put("detail", new JSONArray().put("虚拟大师"));
                jSONObject.put("type", "虚拟大师");
                return jSONObject;
            }
        } catch (Exception e) {
        }
        return jSONObject;
    }
}
