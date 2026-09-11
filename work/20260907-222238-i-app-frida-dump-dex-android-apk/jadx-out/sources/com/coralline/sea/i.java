package com.coralline.sea;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.ArrayMap;
import com.coralline.sea.m5;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/* JADX INFO: loaded from: assets/RiskStub.dex */
@TargetApi(m5.b.q)
public class i {
    public static Class a;
    public static Method b;
    public static Method c;
    public static Field d;
    public static Object e;
    public static ArrayMap f;
    public static Class g;
    public static Field h;
    public static Field i;

    public static Method a() {
        if (b == null) {
            c();
        }
        return b;
    }

    public static void a(Object obj) {
        if (obj == null) {
            c();
        }
    }

    public static Object b() {
        if (e == null) {
            c();
        }
        return e;
    }

    public static void c() {
        try {
            if (a == null || b == null || c == null || d == null || e == null || f == null || g == null || h == null || i == null) {
                Class<?> cls = Class.forName("android.app.ActivityThread");
                a = cls;
                Method declaredMethod = cls.getDeclaredMethod("currentActivityThread", new Class[0]);
                c = declaredMethod;
                declaredMethod.setAccessible(true);
                e = c.invoke(null, new Object[0]);
                Method declaredMethod2 = a.getDeclaredMethod("acquireProvider", Context.class, String.class, Integer.TYPE, Boolean.TYPE);
                b = declaredMethod2;
                declaredMethod2.setAccessible(true);
                Field declaredField = a.getDeclaredField("mProviderMap");
                d = declaredField;
                declaredField.setAccessible(true);
                f = (ArrayMap) d.get(e);
                Class<?> cls2 = Class.forName("android.app.ActivityThread$ProviderKey");
                g = cls2;
                h = cls2.getDeclaredField("authority");
                i = g.getDeclaredField("userId");
                h.setAccessible(true);
                i.setAccessible(true);
            }
        } catch (Exception e2) {
        }
    }

    public static void d() {
        if (f == null) {
            c();
        }
        for (Object obj : f.keySet()) {
            g7 g7Var = new g7(f.get(obj));
            try {
                h.get(obj).toString();
                i.get(obj).toString();
                g7Var.c();
                Objects.toString(g7Var.b());
                Objects.toString(g7Var.d());
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static g7 a(String str) {
        try {
            if (g == null) {
                c();
            }
            Constructor constructor = g.getConstructor(String.class, Integer.TYPE);
            constructor.setAccessible(true);
            return new g7(f.get(constructor.newInstance(str, 0)));
        } catch (Exception e2) {
            return null;
        }
    }
}
