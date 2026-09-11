package com.coralline.sea;

import android.os.Build;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public final class m0 {
    public static final String a = "BootstrapClass";
    public static Object b;
    public static Method c;

    static {
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                Method declaredMethod = Class.class.getDeclaredMethod("forName", String.class);
                Method declaredMethod2 = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
                Class cls = (Class) declaredMethod.invoke(null, "dalvik.system.VMRuntime");
                Method method = (Method) declaredMethod2.invoke(cls, "getRuntime", null);
                c = (Method) declaredMethod2.invoke(cls, "setHiddenApiExemptions", new Class[]{String[].class});
                b = method.invoke(null, new Object[0]);
            } catch (Throwable th) {
                th.toString();
            }
        }
    }

    public static boolean a() {
        return a("L");
    }

    public static boolean a(String str) {
        return a(str);
    }

    public static boolean a(String... strArr) {
        Method method;
        Object obj = b;
        if (obj == null || (method = c) == null) {
            return false;
        }
        try {
            method.invoke(obj, strArr);
            return true;
        } catch (Throwable th) {
            th.toString();
            return false;
        }
    }
}
