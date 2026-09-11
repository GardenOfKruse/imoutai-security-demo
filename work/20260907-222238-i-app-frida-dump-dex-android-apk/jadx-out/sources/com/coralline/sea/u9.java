package com.coralline.sea;

import android.content.Context;
import dalvik.system.DexFile;
import java.io.File;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class u9 {
    public static Boolean a(Context context, String str, boolean z) {
        Boolean boolValueOf = Boolean.valueOf(z);
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
            return (Boolean) clsLoadClass.getMethod("getBoolean", String.class, Boolean.TYPE).invoke(clsLoadClass, str, Boolean.valueOf(z));
        } catch (Exception e) {
            return boolValueOf;
        }
    }

    public static Integer a(Context context, String str, int i) {
        Integer numValueOf = Integer.valueOf(i);
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
            return (Integer) clsLoadClass.getMethod("getInt", String.class, Integer.TYPE).invoke(clsLoadClass, str, Integer.valueOf(i));
        } catch (Exception e) {
            return numValueOf;
        }
    }

    public static Long a(Context context, String str, long j) {
        Long lValueOf = Long.valueOf(j);
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
            return (Long) clsLoadClass.getMethod("getLong", String.class, Long.TYPE).invoke(clsLoadClass, str, Long.valueOf(j));
        } catch (Exception e) {
            return lValueOf;
        }
    }

    public static String a(Context context, String str) {
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
            return (String) clsLoadClass.getMethod("get", String.class).invoke(clsLoadClass, str);
        } catch (Exception e) {
            return c7.c;
        }
    }

    public static String a(Context context, String str, String str2) {
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
            return (String) clsLoadClass.getMethod("get", String.class, String.class).invoke(clsLoadClass, str, str2);
        } catch (Exception e) {
            return str2;
        }
    }

    public static void b(Context context, String str, String str2) {
        try {
            new DexFile(new File("/system/app/Settings.apk"));
            context.getClassLoader();
            Class<?> cls = Class.forName("android.os.SystemProperties");
            cls.getMethod("set", String.class, String.class).invoke(cls, str, str2);
        } catch (Exception e) {
        }
    }
}
