package com.coralline.sea;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import com.coralline.sea.m5;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class o9 {
    public static final String a = "hijack";

    public class a extends HashMap<String, ArrayList<String>> {

        /* JADX INFO: renamed from: com.coralline.sea.o9$a$a, reason: collision with other inner class name */
        public class C0007a extends ArrayList<String> {
            public C0007a() {
                add("FLYME 6.1.0.0A");
                add("FLYME 5.1.3.0M");
                add("FLYME 8.0.5.0A");
            }
        }

        public class b extends ArrayList<String> {
            public b() {
                add("N5117.A.24");
            }
        }

        public class c extends ArrayList<String> {
            public c() {
                add("C199V100R001C92B253");
                add("TRT-TL10AC01B201");
            }
        }

        public a() {
            put("MEIZU", new C0007a());
            put("OPPO", new b());
            put("HUAWEI", new c());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0074 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String a(java.lang.String r8) throws java.lang.Throwable {
        /*
            r0 = 0
            java.lang.String r1 = " "
            java.lang.String[] r8 = r8.split(r1)     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L6a
            java.lang.ProcessBuilder r1 = new java.lang.ProcessBuilder     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L6a
            r1.<init>(r8)     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L6a
            r8 = 0
            r1.redirectErrorStream(r8)     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L6a
            java.lang.Process r1 = r1.start()     // Catch: java.io.IOException -> L59 java.lang.Throwable -> L5b java.lang.Exception -> L6a
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L55 java.lang.Exception -> L57
            java.io.InputStream r3 = r1.getInputStream()     // Catch: java.lang.Throwable -> L55 java.lang.Exception -> L57
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L55 java.lang.Exception -> L57
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L53
            r3.<init>()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L53
            r4 = 1024(0x400, float:1.435E-42)
            char[] r5 = new char[r4]     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L53
        L26:
            int r6 = r2.read(r5, r8, r4)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L53
            r7 = -1
            if (r6 == r7) goto L31
            r3.append(r5, r8, r6)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L53
            goto L26
        L31:
            r1.waitFor()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L53
            int r8 = r1.exitValue()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L53
            if (r8 == 0) goto L43
            r1.destroy()
            r2.close()     // Catch: java.io.IOException -> L41
            return r0
        L41:
            r8 = move-exception
            return r0
        L43:
            java.lang.String r8 = r3.toString()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L53
            r1.destroy()
            r2.close()     // Catch: java.io.IOException -> L4e
            return r8
        L4e:
            r0 = move-exception
            return r8
        L50:
            r8 = move-exception
            r0 = r2
            goto L5d
        L53:
            r8 = move-exception
            goto L6d
        L55:
            r8 = move-exception
            goto L5d
        L57:
            r8 = move-exception
            goto L6c
        L59:
            r8 = move-exception
            return r0
        L5b:
            r8 = move-exception
            r1 = r0
        L5d:
            if (r1 == 0) goto L62
            r1.destroy()
        L62:
            if (r0 == 0) goto L69
            r0.close()     // Catch: java.io.IOException -> L68
            goto L69
        L68:
            r0 = move-exception
        L69:
            throw r8
        L6a:
            r8 = move-exception
            r1 = r0
        L6c:
            r2 = r0
        L6d:
            if (r1 == 0) goto L72
            r1.destroy()
        L72:
            if (r2 == 0) goto L79
            r2.close()     // Catch: java.io.IOException -> L78
            return r0
        L78:
            r8 = move-exception
        L79:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.o9.a(java.lang.String):java.lang.String");
    }

    public static boolean a() {
        a aVar = new a();
        String str = Build.MANUFACTURER;
        String str2 = Build.DISPLAY;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String upperCase = str.toUpperCase();
        if (aVar.containsKey(upperCase) && !TextUtils.isEmpty(str2)) {
            return aVar.get(upperCase).contains(str2.toUpperCase());
        }
        return false;
    }

    public static boolean a(Activity activity) {
        try {
            return !activity.isFinishing();
        } catch (Exception e) {
            return true;
        }
    }

    @TargetApi(m5.b.u)
    public static boolean a(Context context) {
        int i = Build.VERSION.SDK_INT;
        return i >= 23 ? Settings.canDrawOverlays(context) : i >= 19 ? c(context) : b(context);
    }

    public static String b() {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getDeclaredMethod("get", String.class).invoke(cls, "ro.build.version.emui");
        } catch (Throwable th) {
            th.printStackTrace();
            return c7.c;
        }
    }

    public static boolean b(Context context) {
        return !"Xiaomi".equalsIgnoreCase(Build.BRAND) || (context.getApplicationInfo().flags & 134217728) == 134217728;
    }

    public static int c() {
        if (Build.VERSION.SDK_INT < 23) {
            return 0;
        }
        try {
            return Build.VERSION.PREVIEW_SDK_INT;
        } catch (Throwable th) {
            return 0;
        }
    }

    @TargetApi(m5.b.q)
    public static boolean c(Context context) {
        AppOpsManager appOpsManager;
        Class<?> cls;
        Class<?> cls2;
        try {
            appOpsManager = (AppOpsManager) context.getSystemService("appops");
            cls = appOpsManager.getClass();
            cls2 = Integer.TYPE;
        } catch (Exception e) {
        }
        return ((Integer) cls.getDeclaredMethod("checkOp", cls2, cls2, String.class).invoke(appOpsManager, 24, Integer.valueOf(Binder.getCallingUid()), context.getPackageName())).intValue() == 0;
    }

    public static CharSequence d(Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager());
    }

    public static boolean d() {
        int i = Build.VERSION.SDK_INT;
        return (i == 28 && c() > 0) || i >= 29;
    }

    public static void e() {
        if (w6.b.equals("true")) {
            try {
                if (a()) {
                    return;
                }
                String strA = a(new String(Base64.decode("Z2V0ZW5mb3JjZQ==", 0)));
                String str = new String(Base64.decode("UGVybWlzc2l2ZQ==", 0));
                if (strA == null || !str.equalsIgnoreCase(strA.trim())) {
                    return;
                }
                String str2 = Build.MANUFACTURER;
                String str3 = Build.DISPLAY;
                System.exit(0);
            } catch (Throwable th) {
                th.getMessage();
            }
        }
    }
}
