package com.coralline.sea;

import android.content.ContentProvider;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class g7 {
    public static Class b;
    public static Field c;
    public static Field d;
    public static Field e;
    public static Field f;
    public Object a;

    public g7(Object obj) {
        e();
        if (b.isInstance(obj)) {
            this.a = obj;
            return;
        }
        throw new ClassCastException(obj.getClass().getName() + " Cannot be cast to " + b.getName());
    }

    public static void e() {
        try {
            if (b == null || c == null || d == null || e == null || f == null) {
                Class<?> cls = Class.forName("android.app.ActivityThread$ProviderClientRecord");
                b = cls;
                Field declaredField = cls.getDeclaredField("mNames");
                c = declaredField;
                declaredField.setAccessible(true);
                Field declaredField2 = b.getDeclaredField("mProvider");
                d = declaredField2;
                declaredField2.setAccessible(true);
                Field declaredField3 = b.getDeclaredField("mLocalProvider");
                e = declaredField3;
                declaredField3.setAccessible(true);
                Field declaredField4 = b.getDeclaredField("mHolder");
                f = declaredField4;
                declaredField4.setAccessible(true);
            }
        } catch (Exception e2) {
        }
    }

    public Object a() {
        try {
            return f.get(this.a);
        } catch (IllegalAccessException e2) {
            return null;
        }
    }

    public void a(ContentProvider contentProvider) {
        try {
            e.set(this.a, contentProvider);
        } catch (IllegalAccessException e2) {
        }
    }

    public void a(Object obj) {
        try {
            f.set(this.a, obj);
        } catch (IllegalAccessException e2) {
        }
    }

    public void a(String[] strArr) {
        try {
            c.set(this.a, strArr);
        } catch (IllegalAccessException e2) {
        }
    }

    public ContentProvider b() {
        try {
            return (ContentProvider) e.get(this.a);
        } catch (IllegalAccessException e2) {
            return null;
        }
    }

    public void b(Object obj) {
        try {
            d.set(this.a, obj);
        } catch (IllegalAccessException e2) {
        }
    }

    public String[] c() {
        try {
            return (String[]) c.get(this.a);
        } catch (IllegalAccessException e2) {
            return null;
        }
    }

    public Object d() {
        try {
            return d.get(this.a);
        } catch (IllegalAccessException e2) {
            return null;
        }
    }
}
