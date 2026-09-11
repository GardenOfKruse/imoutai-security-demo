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
import java.io.IOException;
import java.io.InputStreamReader;
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
    */
    public static String a(String str) throws Throwable {
        Process processStart;
        InputStreamReader inputStreamReader;
        InputStreamReader inputStreamReader2 = null;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(str.split(" "));
            processBuilder.redirectErrorStream(false);
            try {
                processStart = processBuilder.start();
                try {
                    inputStreamReader = new InputStreamReader(processStart.getInputStream());
                    try {
                        StringBuilder sb = new StringBuilder();
                        char[] cArr = new char[1024];
                        while (true) {
                            int i = inputStreamReader.read(cArr, 0, 1024);
                            if (i == -1) {
                                break;
                            }
                            sb.append(cArr, 0, i);
                        }
                        processStart.waitFor();
                        if (processStart.exitValue() != 0) {
                            processStart.destroy();
                            try {
                                inputStreamReader.close();
                                return null;
                            } catch (IOException e) {
                                return null;
                            }
                        }
                        String string = sb.toString();
                        processStart.destroy();
                        try {
                            inputStreamReader.close();
                            return string;
                        } catch (IOException e2) {
                            return string;
                        }
                    } catch (Exception e3) {
                        if (processStart != null) {
                            processStart.destroy();
                        }
                        if (inputStreamReader != null) {
                            try {
                                inputStreamReader.close();
                                return null;
                            } catch (IOException e4) {
                                return null;
                            }
                        }
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        inputStreamReader2 = inputStreamReader;
                        if (processStart != null) {
                            processStart.destroy();
                        }
                        if (inputStreamReader2 != null) {
                            try {
                                inputStreamReader2.close();
                            } catch (IOException e5) {
                            }
                        }
                        throw th;
                    }
                } catch (Exception e6) {
                    inputStreamReader = null;
                    if (processStart != null) {
                    }
                    if (inputStreamReader != null) {
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e7) {
                return null;
            }
        } catch (Exception e8) {
            processStart = null;
        } catch (Throwable th3) {
            th = th3;
            processStart = null;
        }
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
