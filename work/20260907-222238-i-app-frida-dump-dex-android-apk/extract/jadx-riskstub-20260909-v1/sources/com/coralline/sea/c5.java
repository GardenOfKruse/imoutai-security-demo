package com.coralline.sea;

import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class c5 {
    public static String a = "inject";
    public static HashSet<String> g;
    public static HashSet<String> h;
    public static HashSet<String> i;
    public static HashSet<String> b = new HashSet<>();
    public static HashSet<String> c = new HashSet<>();
    public static HashSet<String> d = new HashSet<>();
    public static HashSet<String> e = new HashSet<>();
    public static boolean f = false;
    public static HashMap<String, Long> j = new HashMap<>();
    public static HashMap<String, Long> k = new HashMap<>();

    public static d7 a(List<d7> list, String str) {
        for (d7 d7Var : list) {
            if (d7Var.c.endsWith(str)) {
                return d7Var;
            }
        }
        return null;
    }

    public static String a() {
        String str = c7.c;
        try {
            throw new Exception("Excetption");
        } catch (Exception e2) {
            for (StackTraceElement stackTraceElement : e2.getStackTrace()) {
                String className = stackTraceElement.getClassName();
                if (!className.startsWith("com.coralline")) {
                    str = str + str + className + "->" + stackTraceElement.getMethodName() + "\n";
                }
            }
            return str;
        }
    }

    public static JSONArray a(Set<String> set) {
        JSONArray jSONArray = new JSONArray();
        HashSet<String> hashSet = new HashSet();
        try {
            if (z1.c != null && z1.c.has("checker")) {
                JSONObject jSONObject = z1.c.getJSONObject("checker");
                JSONObject jSONObject2 = jSONObject.has("inject") ? jSONObject.getJSONObject("inject") : new JSONObject();
                JSONArray jSONArray2 = jSONObject2.has("dex_match_pattern") ? jSONObject2.getJSONArray("dex_match_pattern") : new JSONArray();
                JSONArray jSONArray3 = jSONObject2.has("dex_filter_pattern") ? jSONObject2.getJSONArray("dex_filter_pattern") : new JSONArray();
                if (jSONArray2.length() == 0 || jSONArray3.length() == 0) {
                    return jSONArray;
                }
                for (String str : set) {
                    if (!str.contains(n3.a().a.getPackageName()) && !str.toLowerCase().contains("webview") && (str.endsWith(".dex") || str.endsWith(".odex"))) {
                        boolean z = false;
                        for (int i2 = 0; i2 < jSONArray3.length(); i2++) {
                            if (Pattern.compile(new String(Base64.decode((String) jSONArray3.get(i2), 0))).matcher(str).matches()) {
                                z = true;
                            }
                        }
                        if (!z) {
                            hashSet.add(str);
                        }
                    }
                }
                for (String str2 : hashSet) {
                    for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                        Matcher matcher = Pattern.compile(new String(Base64.decode((String) jSONArray2.get(i3), 0))).matcher(str2);
                        if (matcher.find()) {
                            String strGroup = matcher.group(1);
                            if (!e.contains(strGroup)) {
                                jSONArray.put(strGroup);
                                e.add(strGroup);
                            }
                        }
                    }
                }
            }
        } catch (JSONException e2) {
        }
        return jSONArray;
    }

    public static JSONArray a(JSONObject jSONObject) {
        int i2;
        JSONArray jSONArray = new JSONArray();
        String property = System.getProperty("java.vm.version");
        boolean z = !TextUtils.isEmpty(property) && Integer.parseInt(property.substring(0, property.indexOf(".")).replaceAll("[\\D]", c7.c)) >= 2;
        if (jSONObject.has("dex") || jSONObject.has("face_check")) {
            try {
                JSONArray jSONArrayA = f5.a(jSONObject.optJSONArray("dex"), jSONObject.optJSONArray("face_check"));
                for (int i3 = 0; i3 < jSONArrayA.length(); i3++) {
                    try {
                        JSONObject jSONObject2 = jSONArrayA.getJSONObject(i3);
                        if (jSONObject2 != null) {
                            int iOptInt = jSONObject2.optInt("min_ver");
                            jSONObject2.optInt("max_ver");
                            String strOptString = jSONObject2.optString("class_name");
                            String strOptString2 = jSONObject2.optString("func_name");
                            String strOptString3 = jSONObject2.optString("func_sigs");
                            boolean zOptBoolean = jSONObject2.optBoolean("is_native", false);
                            if (!TextUtils.isEmpty(strOptString) && !TextUtils.isEmpty(strOptString2) && !TextUtils.isEmpty(strOptString3) && (i2 = Build.VERSION.SDK_INT) >= iOptInt) {
                                Class<?> cls = Class.forName(strOptString.replace("/", "."));
                                if (!Modifier.isAbstract(cls.getModifiers())) {
                                    String strA = i6.a(z, i2, strOptString, strOptString2, strOptString3, zOptBoolean, cls.getDeclaredMethod(strOptString2, e(strOptString3)));
                                    if (!TextUtils.isEmpty(strA)) {
                                        String[] strArrSplit = strA.split("/");
                                        int i4 = Integer.parseInt(strArrSplit[0]);
                                        String str = strArrSplit[1];
                                        if (i4 > 0) {
                                            String str2 = strOptString + "|" + strOptString2 + "|" + strOptString3;
                                            if (!d.contains(str2)) {
                                                d.add(str2);
                                                JSONObject jSONObject3 = new JSONObject();
                                                jSONObject3.put("class_name", strOptString);
                                                jSONObject3.put("func_name", strOptString2);
                                                jSONObject3.put("func_sigs", strOptString3);
                                                jSONObject3.put("hook_type", i4);
                                                jSONObject3.put("func_flag", str);
                                                jSONArray.put(jSONObject3);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e2) {
                        e2.toString();
                    }
                }
            } catch (Exception e3) {
            }
        }
        return jSONArray;
    }

    public static JSONArray a(JSONObject jSONObject, List<d7> list) {
        JSONObject jSONObjectA;
        JSONArray jSONArray = new JSONArray();
        try {
            if (jSONObject.has("hook")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("hook");
                if (jSONObject2.has("system")) {
                    JSONArray jSONArray2 = jSONObject2.getJSONArray("system");
                    jSONArray2.toString();
                    for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                        JSONObject jSONObject3 = jSONArray2.getJSONObject(i2);
                        String string = jSONObject3.getString("lib_name");
                        String string2 = jSONObject3.getString("func_name");
                        JSONObject jSONObjectB = b(a(list, string), string, string2);
                        if (jSONObjectB != null) {
                            jSONArray.put(jSONObjectB);
                        }
                        if (jSONObject.has("user_libs")) {
                            JSONArray jSONArray3 = jSONObject.getJSONArray("user_libs");
                            for (int i3 = 0; jSONArray3 != null && i3 < jSONArray3.length(); i3++) {
                                String string3 = jSONArray3.getString(i3);
                                d7 d7VarA = a(list, string3);
                                if (d7VarA != null && (jSONObjectA = a(d7VarA, string3, string2)) != null) {
                                    jSONArray.put(jSONObjectA);
                                }
                            }
                        }
                    }
                }
            }
        } catch (JSONException e2) {
        }
        return jSONArray;
    }

    public static JSONObject a(d7 d7Var, String str, String str2) {
        long jA;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || d7Var == null || a(str, str2)) {
            return null;
        }
        long jA2 = i6.a(d7Var.a, new String[]{str2});
        Long.toHexString(jA2);
        if (j.containsKey(str2)) {
            jA = j.get(str2).longValue();
        } else {
            jA = i6.a(d7Var.c, str2);
            j.put(str2, Long.valueOf(jA));
        }
        Long.toHexString(Long.parseLong(d7Var.a.replace("0x", c7.c), 16) + jA);
        if (jA != 0 && jA2 != Long.parseLong(d7Var.a.replace("0x", c7.c), 16) + jA) {
            String str3 = d7Var.c + "|" + str2 + "|" + jA2;
            if (!c.contains(str3)) {
                c.add(str3);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("so_path", d7Var.c);
                    jSONObject.put("func_name", str2);
                    jSONObject.put("cur_param", "0x" + Long.toHexString(jA2).toLowerCase());
                    jSONObject.put("old_param", "0x" + Long.toHexString(Long.parseLong(d7Var.a.replace("0x", c7.c), 16) + jA).toLowerCase());
                    return jSONObject;
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return null;
    }

    public static JSONObject a(String str, HashSet<String> hashSet, HashSet<String> hashSet2) {
        g = hashSet;
        h = hashSet2;
        ArrayList<d7> arrayListD = d7.d();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObjectA = z1.a(str);
        if (jSONObjectA == null) {
            arrayListD.clear();
            return jSONObject;
        }
        try {
            JSONArray jSONArrayB = b(jSONObjectA, arrayListD);
            JSONArray jSONArrayB2 = b();
            JSONArray jSONArrayA = a(d7.a());
            JSONArray jSONArrayA2 = a(jSONObjectA);
            JSONArray jSONArrayA3 = a(jSONObjectA, arrayListD);
            String strA = a();
            JSONArray jSONArray = new JSONArray();
            if (jSONArrayA3.length() > 0) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("type", "chook");
                jSONObject2.put("hook_function", jSONArrayA3);
                jSONObject2.put("detail", new JSONArray().put("chook"));
                jSONArray.put(jSONObject2);
            }
            if (jSONArrayA2.length() > 0) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("type", "java_hook");
                jSONObject3.put("hook_function", jSONArrayA2);
                jSONObject3.put("detail", new JSONArray().put("java_hook"));
                jSONArray.put(jSONObject3);
            }
            if (jSONArrayA.length() > 0) {
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("type", "dex_inject");
                jSONObject4.put("list", jSONArrayA);
                jSONObject4.put("detail", new JSONArray().put("dex_inject"));
                jSONArray.put(jSONObject4);
            }
            if (jSONArrayB.length() > 0) {
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put("detail", jSONArrayB);
                jSONObject.put("dlopen", jSONObject5);
            }
            if (jSONArrayB2.length() > 0) {
                JSONObject jSONObject6 = new JSONObject();
                jSONObject6.put("detail", jSONArrayB2);
                jSONObject.put("inject", jSONObject6);
            }
            if (jSONArray.length() > 0) {
                jSONObject.put("hook", jSONArray);
            }
            if (strA.length() > 0) {
                jSONObject.put("statcktrace", strA);
                return jSONObject;
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    public static void a(File file) {
        File[] fileArrListFiles;
        for (File file2 : file.listFiles()) {
            if (file2.isDirectory() && (fileArrListFiles = file2.listFiles()) != null) {
                for (File file3 : fileArrListFiles) {
                    if (file3.getName().endsWith(".so")) {
                        i.add(file3.getName());
                    }
                }
            }
        }
    }

    public static boolean a(String str) {
        Iterator<String> it = i.iterator();
        while (it.hasNext()) {
            if (str.endsWith(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean a(String str, String str2) {
        try {
            if (!f) {
                for (String str3 : h) {
                    if (str3.contains("DexHelper") || str3.contains("SdkHelper_") || str3.contains("SdkAppGuard_") || str3.contains("AppGuard")) {
                        f = true;
                        break;
                    }
                }
                if (!f && c()) {
                    f = true;
                }
            }
            if (f) {
                String[][] strArr = {new String[]{"libc.so", "read"}, new String[]{"libc.so", "open"}};
                for (int i2 = 0; i2 < 2; i2++) {
                    String[] strArr2 = strArr[i2];
                    if (str.contains(strArr2[0]) && strArr2[1].equals(str2)) {
                        return true;
                    }
                }
            }
        } catch (Exception e2) {
        }
        return false;
    }

    public static boolean a(String str, JSONObject jSONObject) {
        try {
            if (jSONObject.has("user_libs")) {
                JSONArray jSONArray = jSONObject.getJSONArray("user_libs");
                int i2 = 0;
                while (jSONArray != null) {
                    if (i2 >= jSONArray.length()) {
                        break;
                    }
                    if (str.endsWith(jSONArray.getString(i2))) {
                        return true;
                    }
                    i2++;
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return false;
    }

    public static JSONArray b() {
        JSONArray jSONArray = new JSONArray();
        Iterator<String> it = d5.b().a().iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next());
        }
        return jSONArray;
    }

    public static JSONArray b(JSONObject jSONObject, List<d7> list) {
        JSONArray jSONArray = new JSONArray();
        try {
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("blacklist");
            if (jSONArrayOptJSONArray != null) {
                for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                    for (d7 d7Var : list) {
                        if (d7Var.c.endsWith(jSONArrayOptJSONArray.getString(i2)) && !b.contains(d7Var.c)) {
                            b.add(d7Var.c);
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("so_path", d7Var.c);
                            jSONObject2.put("type", "dlopen");
                            jSONArray.put(jSONObject2);
                        }
                    }
                }
            }
            for (d7 d7Var2 : list) {
                if (d7Var2.c.endsWith(".so") && !c(d7Var2.c) && !b(d7Var2.c) && !b(d7Var2.c, jSONObject) && !f(d7Var2.c) && !a(d7Var2.c) && !a(d7Var2.c, jSONObject) && !b.contains(d7Var2.c)) {
                    b.add(d7Var2.c);
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("so_path", d7Var2.c);
                    jSONObject3.put("type", "dlopen");
                    jSONArray.put(jSONObject3);
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONArray;
    }

    public static JSONObject b(d7 d7Var, String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || d7Var == null || a(str, str2)) {
            return null;
        }
        long jA = i6.a(Build.VERSION.SDK_INT, str, str2);
        if (jA != 0) {
            String str3 = d7Var.c + "|" + str2 + "|" + jA;
            if (!c.contains(str3)) {
                c.add(str3);
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("so_path", d7Var.c);
                    jSONObject.put("func_name", str2);
                    return jSONObject;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean b(String str) {
        String[] strArr = {"libRiskStub" + n3.a().f + ".so", "libLoaderRiskStub.so"};
        for (int i2 = 0; i2 < 2; i2++) {
            try {
                if (str.endsWith(strArr[i2])) {
                    return true;
                }
            } catch (Exception e2) {
            }
        }
        return false;
    }

    public static boolean b(String str, JSONObject jSONObject) {
        try {
            if (jSONObject.has("whitelist")) {
                JSONArray jSONArray = jSONObject.getJSONArray("whitelist");
                int i2 = 0;
                while (jSONArray != null) {
                    if (i2 >= jSONArray.length()) {
                        break;
                    }
                    if (str.endsWith(jSONArray.getString(i2))) {
                        return true;
                    }
                    i2++;
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return false;
    }

    public static boolean c() {
        String[] strArr = {"com.secneo.apkwrapper.AW", "com.secneo.apkwrapper.H", "com.secneo.sdk.Helper", "com.secneo.sdk.DexInstall"};
        for (int i2 = 0; i2 < 4; i2++) {
            try {
                Class.forName(strArr[i2]);
                return true;
            } catch (Exception e2) {
            }
        }
        return false;
    }

    public static boolean c(String str) {
        return str.startsWith("/system/") || str.startsWith("/dev/") || str.startsWith("/data/dalvik-cache/") || str.startsWith("/cache/dalvik-cache/") || str.startsWith("/vendor/") || str.startsWith("[") || str.startsWith("/apex/") || str.startsWith("/system_ext/") || str.endsWith("libwebviewchromium.so");
    }

    public static int d(String str) {
        int iIndexOf;
        int i2 = 0;
        while (!TextUtils.isEmpty(str)) {
            char cCharAt = str.charAt(0);
            if (cCharAt == 'L') {
                iIndexOf = str.indexOf(59) + 1;
            } else if (cCharAt == '[') {
                iIndexOf = 0;
                while (str.charAt(iIndexOf) == '[') {
                    iIndexOf++;
                }
            } else {
                str = str.substring(1);
                i2++;
            }
            str = str.substring(iIndexOf);
            i2++;
        }
        return i2;
    }

    public static boolean d() {
        return true;
    }

    public static void e() {
        try {
            i = new HashSet<>();
            String packageResourcePath = n3.a().a.getPackageResourcePath();
            File file = new File(packageResourcePath);
            if (file.exists()) {
                File file2 = new File(file.getParent() + "/lib");
                if (file2.exists()) {
                    a(file2);
                } else {
                    i(packageResourcePath);
                }
            }
        } catch (Exception e2) {
            e2.toString();
        }
    }

    public static Class<?>[] e(String str) throws ClassNotFoundException {
        return h(str.substring(1, str.indexOf(41)));
    }

    public static boolean f(String str) {
        return (str.contains(File.separator) ? g : h).contains(str);
    }

    public static Class<?> g(String str) {
        char cCharAt = str.charAt(0);
        if (cCharAt == 'F') {
            return Float.TYPE;
        }
        if (cCharAt == 'S') {
            return Short.TYPE;
        }
        if (cCharAt == 'V') {
            return Void.TYPE;
        }
        if (cCharAt == 'Z') {
            return Boolean.TYPE;
        }
        switch (cCharAt) {
            case 'B':
                return Byte.TYPE;
            case 'C':
                return Character.TYPE;
            case 'D':
                return Double.TYPE;
            default:
                switch (cCharAt) {
                    case 'I':
                        return Integer.TYPE;
                    case 'J':
                        return Long.TYPE;
                    default:
                        throw new IllegalArgumentException("Unknown descriptor type: " + str.charAt(0));
                }
        }
    }

    public static Class<?>[] h(String str) throws ClassNotFoundException {
        Class<?>[] clsArr = new Class[d(str)];
        int i2 = 0;
        while (!TextUtils.isEmpty(str)) {
            char cCharAt = str.charAt(0);
            if (cCharAt == 'L') {
                int iIndexOf = str.indexOf(59);
                clsArr[i2] = Class.forName(str.substring(1, iIndexOf).replace(t4.b, '.'));
                str = str.substring(iIndexOf + 1);
                i2++;
            } else if (cCharAt == '[') {
                int i3 = 0;
                while (str.charAt(i3) == '[') {
                    i3++;
                }
                Class<?> clsG = g(str.substring(i3));
                for (int i4 = 0; i4 < i3; i4++) {
                    clsG = Array.newInstance(clsG, 0).getClass();
                }
                clsArr[i2] = clsG;
                str = str.substring(i3);
                i2++;
            } else {
                clsArr[i2] = g(str);
                str = str.substring(1);
                i2++;
            }
        }
        return clsArr;
    }

    public static void i(String str) throws IOException {
        if (str != null && str.endsWith(".apk")) {
            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(str)));
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry == null) {
                    break;
                }
                String name = nextEntry.getName();
                if (name.endsWith(".so")) {
                    i.add(name.substring(name.lastIndexOf(File.separatorChar) + 1));
                }
            }
        }
        Objects.toString(i);
    }
}
