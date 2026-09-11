package com.coralline.sea;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class y7 {

    public static class a {
        public int a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;

        public a(int i, String str, String str2, String str3, String str4) {
            this.a = i;
            this.b = str;
            this.e = str2;
            this.f = str3;
            this.g = str4;
        }

        public String a() {
            return this.f;
        }

        public void a(int i) {
            this.a = i;
        }

        public void a(String str) {
            this.f = str;
        }

        public String b() {
            return this.g;
        }

        public void b(String str) {
            this.g = str;
        }

        public int c() {
            return this.a;
        }

        public void c(String str) {
            this.e = str;
        }

        public String d() {
            return this.e;
        }

        public void d(String str) {
            this.b = str;
        }

        public String e() {
            return this.b;
        }

        public void e(String str) {
            this.c = str;
        }

        public String f() {
            return this.c;
        }

        public void f(String str) {
            this.d = str;
        }

        public String g() {
            return this.d;
        }

        public String toString() {
            return "Info{pid=" + this.a + ", pname='" + this.b + "', reason='" + this.c + "', uname='" + this.d + "', pkg_name='" + this.e + "', app_name='" + this.f + "', md5='" + this.g + "'}";
        }
    }

    public static class b {
        public String a;
        public int b;
        public int c;
        public int d;
        public String e;
        public String f;

        public b(String str) {
            String[] strArrSplit = str.split("\\s+");
            if (strArrSplit.length >= 4) {
                String str2 = strArrSplit[strArrSplit.length - 1];
                String strReplace = (str2.contains(":") ? str2.split(":")[0] : str2).replace("/", "@#@");
                if (strReplace.contains(".")) {
                    String str3 = strArrSplit[0];
                    this.a = str3;
                    this.b = Process.getUidForName(str3);
                    this.c = Integer.parseInt(strArrSplit[1]);
                    this.d = Integer.parseInt(strArrSplit[2]);
                    this.f = strArrSplit[strArrSplit.length - 1];
                    for (String str4 : strReplace.split("@#@")) {
                        if (str4.contains(".")) {
                            this.e = str4;
                            return;
                        }
                    }
                }
            }
        }

        public String toString() {
            return String.format(this.f, this.e, this.a);
        }
    }

    public static ApplicationInfo a(String str) {
        PackageManager packageManager = n3.a().a.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            return packageManager.getApplicationInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static ArrayList<Object> a() {
        ArrayList<Object> arrayList = new ArrayList<>();
        List<b> listA = a(Boolean.TRUE);
        listA.size();
        Context context = n3.a().a;
        for (int i = 0; i < listA.size(); i++) {
            ApplicationInfo applicationInfoA = a(listA.get(i).e);
            if (applicationInfoA != null && (applicationInfoA.flags & 1) == 0) {
                arrayList.add(new a(listA.get(i).c, listA.get(i).a, applicationInfoA.packageName, applicationInfoA.loadLabel(context.getPackageManager()).toString(), v7.e(applicationInfoA.packageName)));
            }
        }
        return arrayList;
    }

    public static List<b> a(Boolean bool) {
        b bVar;
        ArrayList arrayList = new ArrayList();
        String strA = b8.a(false);
        if (strA == null || strA.length() <= 0) {
            return arrayList;
        }
        for (String str : strA.split("\n")) {
            try {
                if (!bool.booleanValue()) {
                    bVar = new b(str);
                    if (bVar.e != null) {
                        arrayList.add(bVar);
                    }
                } else if (str.split("\\s+")[0].equals("root")) {
                    bVar = new b(str);
                    if (bVar.e != null) {
                        arrayList.add(bVar);
                    }
                }
            } catch (Exception e) {
            }
        }
        return arrayList;
    }

    public static JSONArray b() {
        JSONArray jSONArray = new JSONArray();
        try {
            ArrayList<Object> arrayListA = a();
            for (int i = 0; i < arrayListA.size(); i++) {
                a aVar = (a) arrayListA.get(i);
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("pid", aVar.c());
                    jSONObject.put("uname", "root");
                    jSONObject.put("pkg_name", aVar.d());
                    jSONObject.put("app_name", aVar.a());
                    jSONObject.put("md5", aVar.b());
                    jSONArray.put(jSONObject);
                    jSONArray.toString(4);
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
        }
        return jSONArray;
    }
}
