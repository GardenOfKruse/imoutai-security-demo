package com.coralline.sea;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class w7 {
    public static final String a = "com.saurik.substrate";
    public static final String b = "de.robv.android.xposed.installer";
    public static final String c = "de.robv.android.xposed.XposedHelpers";
    public static final String d = "de.robv.android.xposed.XposedBridge";
    public static final String e = "io.va.exposed";
    public static final String f = "io.va.exposed64";
    public static final String g = "org.meowcat.edxposed.manager";
    public static final String h = "org.lsposed.manager";
    public static final String i = "me.weishu.exp";
    public static final String j = "com.offsec.nhterm";
    public static final String k = "com.offsec.nethunter";
    public static final String l = "com.offsec.nhvnc";
    public static final String o = "/io.virtualapp.sandvxposed/";
    public static String q = "frida is exist";
    public static final String t = "/proc/net/unix";
    public static final String[] m = {"io.virtualapp.sandvxposed", "io.virtualapp.sandvxposed64"};
    public static final String[] n = {"/storage/emulated/0/Android/data/io.virtualapp.sandvxposed", "/storage/emulated/0/Android/data/io.virtualapp.sandvxposed64"};
    public static final String[] p = {".odex", ".vdex", ".art", ".apk"};
    public static final String[] r = {"tweak/redirect.txt", "tweak/tweakme.cer", "tweak/libtarget.so"};
    public static final String[] s = {"libx.so"};

    public static JSONObject A() {
        JSONObject jSONObject = new JSONObject();
        Context context = n3.a().a;
        boolean zB = b(context, r);
        boolean zA = a(context, s);
        if (zB && zA) {
            try {
                jSONObject.put("name", "TWeakMe");
                jSONObject.put(n5.n, "TWeakMe is exist!");
                if (zB) {
                    jSONObject.put("detail", new JSONArray().put("tweak/redirect.txt,tweak/tweakme.cer,tweak/libtarget.so,libx.so"));
                    return jSONObject;
                }
            } catch (JSONException e2) {
            }
        }
        return jSONObject;
    }

    public static JSONObject B() {
        String str;
        JSONArray jSONArray;
        String str2;
        JSONObject jSONObject = new JSONObject();
        if (v()) {
            try {
                if (v7.f(b)) {
                    String strA = v7.a(b);
                    if (strA == null) {
                        strA = "Xposed Installer";
                    }
                    jSONObject.put("name", strA);
                    jSONObject.put("package", b);
                    jSONObject.put("app_md5", v7.e(b));
                    jSONObject.put("path", a(b));
                    str = "detail";
                    jSONArray = new JSONArray();
                    str2 = "xposed-install";
                } else {
                    jSONObject.put("name", g9.h);
                    jSONObject.put(n5.n, "XposedInstaller is uninstall or hide but Inject still exist");
                    str = "detail";
                    jSONArray = new JSONArray();
                    str2 = "xposed-uninstall";
                }
                jSONObject.put(str, jSONArray.put(str2));
            } catch (JSONException e2) {
            }
        }
        return jSONObject.length() == 0 ? y() : jSONObject;
    }

    public static JSONArray a(String str) {
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(v7.b(str));
        return jSONArray;
    }

    public static boolean a() {
        String strI;
        try {
            if (!new File("/proc/net/unix").exists() || (strI = ja.i("cat /proc/net/unix")) == null) {
                return false;
            }
            int i2 = 0;
            for (String str : strI.split("\n")) {
                if (str.contains("@") && str.split("@")[1].contains("re.frida.server")) {
                    i2++;
                }
            }
            if (i2 > 0) {
                return true;
            }
        } catch (Exception e2) {
        }
        return false;
    }

    public static boolean a(Context context, String[] strArr) {
        String str = context.getApplicationInfo().nativeLibraryDir;
        for (String str2 : strArr) {
            if (!new File(str, str2).exists()) {
                return false;
            }
        }
        return true;
    }

    public static boolean a(AssetManager assetManager, String str, Set<String> set) throws IOException {
        String[] list = assetManager.list(str);
        if (list == null || list.length == 0) {
            return set.isEmpty();
        }
        int length = list.length;
        for (int i2 = 0; i2 < length; i2++) {
            String str2 = list[i2];
            if (!str.isEmpty()) {
                str2 = str + "/" + str2;
            }
            if (set.contains(str2)) {
                set.remove(str2);
                if (set.isEmpty()) {
                    return true;
                }
            } else if (assetManager.list(str2).length > 0 && a(assetManager, str2, set)) {
                return true;
            }
        }
        return set.isEmpty();
    }

    public static boolean a(JSONArray jSONArray) {
        boolean z = false;
        for (String str : d7.c()) {
            if (str.contains("edxp") || str.contains("yahafa") || str.contains("sandhook")) {
                if (str.startsWith("/system/")) {
                    jSONArray.put(str);
                    z = true;
                }
            }
        }
        return z;
    }

    public static boolean b() {
        try {
            ClassLoader.getSystemClassLoader().loadClass(c).newInstance();
            try {
                ClassLoader.getSystemClassLoader().loadClass(d).newInstance();
                return true;
            } catch (ClassNotFoundException e2) {
                return false;
            } catch (IllegalAccessException e3) {
                return true;
            } catch (InstantiationException e4) {
                return true;
            }
        } catch (ClassNotFoundException e5) {
            return false;
        } catch (IllegalAccessException e6) {
            return true;
        } catch (InstantiationException e7) {
            return true;
        }
    }

    public static boolean b(Context context, String[] strArr) {
        HashSet hashSet = new HashSet();
        for (String str : strArr) {
            hashSet.add(str);
        }
        try {
            String[] list = context.getAssets().list("tweak/");
            int length = list.length;
            if (list.length == 0) {
                return false;
            }
            int i2 = 0;
            for (String str2 : list) {
                if (hashSet.contains("tweak/" + str2)) {
                    i2++;
                }
            }
            return i2 == strArr.length;
        } catch (IOException e2) {
            e2.toString();
            return false;
        }
    }

    public static boolean b(String str) {
        for (String str2 : p) {
            if (str.endsWith(str2) && str.contains(o)) {
                return true;
            }
        }
        return false;
    }

    public static boolean b(JSONArray jSONArray) {
        String str;
        String str2;
        File file = new File("/data/local/nhsystem");
        File file2 = new File("/sdcard/nh_files");
        File file3 = new File("/sdcard/nh_install_*.log");
        boolean zExists = file.exists();
        boolean z = file2.exists() && file3.exists();
        boolean z2 = v7.f(j) && v7.f(k) && v7.f(l);
        if (zExists && z) {
            jSONArray.put("/data/local/nhsystem");
            jSONArray.put("/sdcard/nh_files");
            str2 = "/sdcard/nh_install_*.log";
        } else {
            if (zExists && z2) {
                str = "/data/local/nhsystem";
            } else {
                if (!z || !z2) {
                    return false;
                }
                jSONArray.put("/sdcard/nh_files");
                str = "/sdcard/nh_install_*.log";
            }
            jSONArray.put(str);
            jSONArray.put(j);
            jSONArray.put(k);
            str2 = l;
        }
        jSONArray.put(str2);
        return true;
    }

    public static String c(String str) throws IOException {
        StringBuilder sb = new StringBuilder();
        FileInputStream fileInputStream = new FileInputStream(str);
        byte[] bArr = new byte[1024];
        while (true) {
            int i2 = fileInputStream.read(bArr);
            if (i2 <= 0) {
                fileInputStream.close();
                return sb.toString();
            }
            sb.append(new String(bArr, 0, i2));
        }
    }

    public static boolean c() {
        Method declaredMethod;
        try {
            declaredMethod = Class.forName(y8.b).getDeclaredMethod(y8.c, String.class);
            declaredMethod.setAccessible(true);
        } catch (Exception e2) {
        }
        return declaredMethod.invoke(null, "user.xposed.system") != null;
    }

    public static boolean d() {
        String str = System.getenv("CLASSPATH");
        if (str == null || str.length() <= 5) {
            return i6.b("dalvik.vm.dex2oat-flags").equals("--inline-max-code-units=0");
        }
        return true;
    }

    public static JSONObject e() {
        JSONArray jSONArrayM = m();
        JSONObject jSONObject = new JSONObject();
        if (jSONArrayM.length() > 0) {
            try {
                jSONObject.put("name", "Frida");
                jSONObject.put(n5.n, q);
                jSONObject.put("detail", jSONArrayM);
                return jSONObject;
            } catch (Exception e2) {
            }
        }
        return jSONObject;
    }

    public static JSONObject f() {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        try {
            if (b(jSONArray)) {
                jSONObject.put("name_a", v7.a(j));
                jSONObject.put("name_b", v7.a(k));
                jSONObject.put("name_c", v7.a(l));
                jSONObject.put("package_a", j);
                jSONObject.put("package_b", k);
                jSONObject.put("package_c", l);
                jSONObject.put("name", "Kali-Linux");
                jSONObject.put(n5.n, "KaliLinux-install");
                jSONObject.put("feature_files", "/sdcard/nh_files,/sdcard/nh_install_*.log");
                jSONObject.put("path", "/data/local/nhsystem");
                jSONObject.put("detail", jSONArray);
                return jSONObject;
            }
        } catch (Exception e2) {
        }
        return jSONObject;
    }

    public static JSONObject g() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObjectA = a5.a().c().a();
            if (jSONObjectA != null) {
                jSONObjectA.toString();
                jSONObject.put("name", "magisk");
                jSONObject.put(n5.n, "magisk is exist");
                jSONObject.put("detail", jSONObjectA.optJSONArray("detail"));
                return jSONObject;
            }
        } catch (Exception e2) {
        }
        return jSONObject;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00b8 A[Catch: Exception -> 0x00d8, TryCatch #0 {Exception -> 0x00d8, blocks: (B:3:0x0005, B:4:0x0015, B:6:0x001b, B:8:0x0027, B:9:0x002c, B:11:0x0033, B:13:0x0040, B:14:0x0044, B:15:0x0047, B:17:0x004c, B:19:0x0069, B:24:0x00a9, B:25:0x00ac, B:27:0x00b2, B:30:0x00bb, B:29:0x00b8, B:21:0x0091, B:23:0x0097, B:32:0x00c0, B:34:0x00c6), top: B:38:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static JSONArray h() {
        JSONArray jSONArray = new JSONArray();
        try {
            JSONArray jSONArray2 = new JSONArray();
            boolean z = false;
            for (String str : d7.c()) {
                if (b(str)) {
                    jSONArray2.put(str);
                    z = true;
                }
            }
            boolean z2 = z;
            for (String str2 : n) {
                if (new File(str2).exists()) {
                    jSONArray2.put(str2);
                    z2 = true;
                }
            }
            for (String str3 : m) {
                JSONObject jSONObject = new JSONObject();
                Boolean boolValueOf = Boolean.valueOf(v7.f(str3));
                Boolean boolValueOf2 = Boolean.valueOf(v7.i(str3));
                if (boolValueOf.booleanValue()) {
                    jSONArray2.put("appInstall");
                    jSONObject.put("name", v7.a(str3));
                    jSONObject.put("package", str3);
                    jSONObject.put("app_md5", v7.e(str3));
                    jSONObject.put("path", a(str3));
                } else {
                    if (boolValueOf2.booleanValue()) {
                        jSONArray2.put("appPathExist");
                        jSONObject.put("name", str3);
                        jSONObject.put(n5.n, "sandvxposed is exist and hidden");
                    }
                    if (!boolValueOf.booleanValue() || boolValueOf2.booleanValue()) {
                        jSONArray.put(jSONObject);
                    }
                }
                jSONObject.put("detail", jSONArray2);
                if (!boolValueOf.booleanValue()) {
                    jSONArray.put(jSONObject);
                }
            }
            if (z2 && jSONArray.length() == 0) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("name", "sandvxposed");
                jSONObject2.put("detail", jSONArray2);
                return jSONArray;
            }
        } catch (Exception e2) {
        }
        return jSONArray;
    }

    public static JSONObject i() {
        String str;
        JSONArray jSONArray;
        String str2;
        JSONObject jSONObject = new JSONObject();
        try {
            if (t()) {
                if (v7.f(a)) {
                    jSONObject.put("name", v7.a(a));
                    jSONObject.put("package", a);
                    jSONObject.put("app_md5", v7.e(a));
                    jSONObject.put("path", a(a));
                    str = "detail";
                    jSONArray = new JSONArray();
                    str2 = "Substraced-install";
                } else {
                    jSONObject.put("name", "substrate");
                    jSONObject.put(n5.n, "Substraced so is exist but substrate uninstall");
                    str = "detail";
                    jSONArray = new JSONArray();
                    str2 = "Substraced-uninstall";
                }
                jSONObject.put(str, jSONArray.put(str2));
                return jSONObject;
            }
        } catch (Exception e2) {
        }
        return jSONObject;
    }

    public static JSONObject j() {
        Boolean boolValueOf;
        Boolean boolValueOf2;
        String str;
        JSONArray jSONArray;
        String str2;
        JSONObject jSONObject = new JSONObject();
        try {
            boolValueOf = Boolean.valueOf(v7.f(i));
            boolValueOf2 = Boolean.valueOf(v7.i(i));
        } catch (Exception e2) {
        }
        if (!boolValueOf.booleanValue()) {
            if (boolValueOf2.booleanValue()) {
                jSONObject.put("name", "太极");
                jSONObject.put(n5.n, "taichi is exist and hidden");
                str = "detail";
                jSONArray = new JSONArray();
                str2 = "taichi-path";
            }
            return jSONObject;
        }
        jSONObject.put("name", v7.a(i));
        jSONObject.put("package", i);
        jSONObject.put("app_md5", v7.e(i));
        jSONObject.put("path", a(i));
        str = "detail";
        jSONArray = new JSONArray();
        str2 = "taichi-install";
        jSONObject.put(str, jSONArray.put(str2));
        return jSONObject;
    }

    public static JSONObject k() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArrayU = u();
            if (jSONArrayU.length() > 0) {
                if (p()) {
                    String str = e;
                    if (jSONArrayU.getString(0).endsWith("64")) {
                        str = f;
                    }
                    String strA = v7.a(str);
                    if (strA == null) {
                        strA = "VirtualXposed";
                    }
                    jSONObject.put("name", strA);
                    jSONObject.put("package", str);
                    jSONObject.put("app_md5", v7.e(str));
                    jSONObject.put("path", a(str));
                } else {
                    jSONObject.put("name", "virtualXposed");
                    jSONObject.put(n5.n, "virtualXposed is exist");
                }
                jSONObject.put("detail", jSONArrayU);
                return jSONObject;
            }
        } catch (Exception e2) {
        }
        return jSONObject;
    }

    public static JSONArray l() {
        JSONArray jSONArray = new JSONArray();
        boolean zA = a(jSONArray);
        boolean zExists = new File("/sbin/.magisk/modules/riru_edxposed").exists();
        if (v7.f(g) || v7.i(g)) {
            jSONArray.put("apk");
        }
        if (zA) {
            jSONArray.put("lib");
        }
        if (zExists) {
            jSONArray.put("file");
        }
        return jSONArray;
    }

    public static JSONArray m() {
        HashMap map = new HashMap();
        int iE = i6.e();
        if (11 == iE) {
            map.put("thread", Double.valueOf(0.6d));
        }
        if (7 == iE) {
            map.put("gadget", Double.valueOf(0.6d));
        }
        if (n()) {
            map.put("lib", Double.valueOf(0.8d));
        }
        if (a()) {
            map.put("uds", Double.valueOf(0.5d));
        }
        if (1 == iE) {
            map.put("memory", Double.valueOf(0.8d));
        }
        Iterator it = map.keySet().iterator();
        JSONArray jSONArray = new JSONArray();
        while (it.hasNext()) {
            jSONArray.put((String) it.next());
        }
        return jSONArray;
    }

    public static boolean n() {
        String strConcat;
        Boolean bool = Boolean.FALSE;
        for (String str : d7.a()) {
            if (str.contains("frida-agent-32.so") || str.contains("frida-agent-64.so")) {
                bool = Boolean.TRUE;
                q = "maps is contains frida: ".concat(str);
                break;
            }
        }
        File file = new File("/data/local/tmp/re.frida.server/frida-agent-32.so");
        File file2 = new File("/data/local/tmp/re.frida.server/frida-agent-64.so");
        if (file.exists()) {
            strConcat = "/data/local/tmp/re.frida.server/frida-agent-32.so is exit";
        } else {
            if (!file2.exists()) {
                List<String> listM = ja.m("/data/local/tmp");
                if (listM != null && listM.size() > 0) {
                    for (String str2 : listM) {
                        if (str2.contains("frida")) {
                            strConcat = "fileName contains frida: ".concat(str2);
                        }
                    }
                }
                return bool.booleanValue();
            }
            strConcat = "/data/local/tmp/re.frida.server/frida-agent-64.so is exit";
        }
        q = strConcat;
        return true;
    }

    public static boolean o() {
        if (b8.b().contains("frida")) {
            q = "process is contains frida";
            return true;
        }
        String strA = b8.a(true);
        if (strA == null || !strA.contains("frida")) {
            return false;
        }
        q = "process is contains frida";
        return true;
    }

    public static boolean p() {
        return v7.f(e) || v7.f(f);
    }

    public static JSONArray q() {
        JSONArray jSONArray = new JSONArray();
        boolean zExists = new File("/sbin/.magisk/modules/riru_lsposed").exists();
        if (v7.f(h) || v7.i(h)) {
            jSONArray.put("apk");
        }
        if (zExists) {
            jSONArray.put("file");
        }
        return jSONArray;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean r() throws IOException {
        String strC;
        String str;
        String strC2 = null;
        try {
            strC = c("/proc/net/tcp");
        } catch (IOException e2) {
            e = e2;
            strC = null;
        }
        try {
            strC2 = c("/proc/net/tcp6");
        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
        }
        if (strC == null && strC2 == null) {
            return false;
        }
        if (TextUtils.isEmpty(strC) || "null".equalsIgnoreCase(strC)) {
            if (!TextUtils.isEmpty(strC2) && !"null".equalsIgnoreCase(strC2)) {
                for (String str2 : strC2.split("\n")) {
                    if (str2.toLowerCase().contains(":69a2")) {
                        str = "tcp6 netstat is 69a2";
                    }
                }
            }
            return false;
        }
        for (String str3 : strC.split("\n")) {
            if (str3.toLowerCase().contains(":69a2")) {
                str = "tcp netstat is 69a2";
                break;
            }
        }
        if (!TextUtils.isEmpty(strC2)) {
            while (i < r1) {
            }
        }
        return false;
        q = str;
        return true;
    }

    public static boolean s() {
        return new File("/system/lib64/libSubstrateRun.so").exists() || new File("/system/lib64/libsubstrate-dvm.so").exists() || new File("/system/lib64/libSubstrateJNI.so").exists() || new File("/system/lib64/libsubstrate.so").exists() || new File("/system/lib/libsubstrate-dvm.so").exists() || new File("/system/lib/libsubstrate.so").exists() || new File("/system/lib/libSubstrateJNI.so").exists() || new File("/system/lib/libSubstrateRun.so").exists();
    }

    public static boolean t() {
        return s() || v7.f(a);
    }

    public static JSONArray u() {
        Boolean boolValueOf = Boolean.valueOf(v7.f(e));
        Boolean boolValueOf2 = Boolean.valueOf(v7.i(e));
        Boolean boolValueOf3 = Boolean.valueOf(v7.f(f));
        return (boolValueOf.booleanValue() && boolValueOf2.booleanValue()) ? new JSONArray().put("apk").put("dir") : boolValueOf.booleanValue() ? new JSONArray().put("apk") : boolValueOf2.booleanValue() ? new JSONArray().put("dir") : (boolValueOf3.booleanValue() && Boolean.valueOf(v7.i(f)).booleanValue()) ? new JSONArray().put("apk64").put("dir64") : boolValueOf3.booleanValue() ? new JSONArray().put("apk64") : new JSONArray();
    }

    public static boolean v() {
        return c() || b() || w() || v7.f(b) || d();
    }

    public static boolean w() {
        Boolean bool = Boolean.FALSE;
        File file = new File("/system/lib/libxposed_art.so");
        File file2 = new File("/system/lib64/libxposed_art.so");
        if (file.exists() || file2.exists()) {
            bool = Boolean.TRUE;
            break;
        }
        for (String str : d7.a()) {
            if (str.contains("app_process32_xposed.so") || str.contains("libxposed_art.so") || str.contains("XposedBridge.jar")) {
                bool = Boolean.TRUE;
                break;
            }
        }
        return bool.booleanValue();
    }

    public static JSONArray x() {
        JSONArray jSONArray = new JSONArray();
        if (n3.a().g) {
            JSONObject jSONObjectB = B();
            if (jSONObjectB.length() > 0) {
                jSONArray.put(jSONObjectB);
            }
        }
        JSONObject jSONObjectZ = z();
        if (jSONObjectZ.length() > 0) {
            jSONArray.put(jSONObjectZ);
        }
        JSONObject jSONObjectA = A();
        if (jSONObjectA.length() > 0) {
            jSONArray.put(jSONObjectA);
        }
        JSONObject jSONObjectE = e();
        if (jSONObjectE.length() > 0) {
            jSONArray.put(jSONObjectE);
        }
        JSONObject jSONObjectI = i();
        if (jSONObjectI.length() > 0) {
            jSONArray.put(jSONObjectI);
        }
        JSONObject jSONObjectF = f();
        if (jSONObjectF.length() > 0) {
            jSONArray.put(jSONObjectF);
        }
        JSONObject jSONObjectG = g();
        if (jSONObjectG.length() > 0) {
            jSONArray.put(jSONObjectG);
        }
        JSONObject jSONObjectK = k();
        if (jSONObjectK.length() > 0) {
            jSONArray.put(jSONObjectK);
        }
        JSONObject jSONObjectJ = j();
        if (jSONObjectJ.length() > 0) {
            jSONArray.put(jSONObjectJ);
        }
        JSONArray jSONArrayH = h();
        if (jSONArrayH.length() > 0) {
            for (int i2 = 0; i2 < jSONArrayH.length(); i2++) {
                jSONArray.put(jSONArrayH.optJSONObject(i2));
            }
        }
        jSONArray.toString();
        return jSONArray;
    }

    public static JSONObject y() {
        String str;
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArrayL = l();
        String strCex = com.coralline.sea.a.b.cex();
        boolean z = !TextUtils.isEmpty(strCex);
        if (jSONArrayL.length() > 0 || z) {
            boolean zF = v7.f(g);
            try {
                if (z) {
                    jSONObject.put("name", "Edxposed");
                    if (z) {
                        str = "detail";
                    } else {
                        str = "detail";
                        strCex = "Edxposed is exist!";
                    }
                    jSONObject.put(str, strCex);
                } else {
                    if (zF) {
                        String strA = v7.a(g);
                        if (strA == null) {
                            strA = "EdXposed Manager";
                        }
                        jSONObject.put("name", strA);
                        jSONObject.put("package", g);
                        jSONObject.put("app_md5", v7.e(g));
                        jSONObject.put("path", a(g));
                    } else {
                        jSONObject.put("name", "Edxposed");
                        jSONObject.put(n5.n, "Edxposed Manager is hide or uninstall");
                    }
                    jSONObject.put("detail", jSONArrayL);
                }
            } catch (JSONException e2) {
            }
        }
        return jSONObject;
    }

    public static JSONObject z() {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArrayQ = q();
        String strF = i6.f();
        boolean z = (TextUtils.isEmpty(strF) && TextUtils.isEmpty(com.coralline.sea.a.b.vpn())) ? false : true;
        if (jSONArrayQ.length() > 0 || z) {
            boolean zF = v7.f(h);
            try {
                if (z) {
                    String strA = v7.a(h);
                    if (strA == null) {
                        strA = "LSPosed";
                    }
                    jSONObject.put("name", strA);
                    jSONObject.put(n5.n, "LSPosed is exist!");
                    jSONObject.put("detail", new JSONArray().put(strF));
                } else {
                    if (zF) {
                        String strA2 = v7.a(h);
                        if (strA2 == null) {
                            strA2 = "LSPosed";
                        }
                        jSONObject.put("name", strA2);
                        jSONObject.put("package", h);
                        jSONObject.put("app_md5", v7.e(h));
                        jSONObject.put("path", a(h));
                    } else {
                        jSONObject.put("name", "LSPosed");
                        jSONObject.put(n5.n, "LSPosed Manager is hide or uninstall");
                    }
                    jSONObject.put("detail", jSONArrayQ);
                }
            } catch (JSONException e2) {
            }
        }
        return jSONObject;
    }
}
