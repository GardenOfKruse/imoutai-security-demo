package com.coralline.sea;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import dalvik.system.DexFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class q7 {
    public static Method c = null;
    public static Method d = null;
    public static Method e = null;
    public static Method f = null;
    public static Method g = null;
    public static Method h = null;
    public static Method i = null;
    public static Method j = null;
    public static Method k = null;
    public static Method l = null;
    public static boolean m = false;
    public static final String n = "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TGFuZHJvaWQvdXRpbC9Mb2c7ABFMamF2YS9sYW5nL0NsYXNzOwAUTGphdmEvbGFuZy9DbGFzczwqPjsAEkxqYXZhL2xhbmcvT2JqZWN0OwASTGphdmEvbGFuZy9TdHJpbmc7ABJMamF2YS9sYW5nL1N5c3RlbTsAFUxqYXZhL2xhbmcvVGhyb3dhYmxlOwAaTGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZDsAJkxtZS93ZWlzaHUvZnJlZXJlZmxlY3Rpb24vQnVpbGRDb25maWc7ACVMbWUvd2Vpc2h1L3JlZmxlY3Rpb24vQm9vdHN0cmFwQ2xhc3M7ACFMbWUvd2Vpc2h1L3JlZmxlY3Rpb24vUmVmbGVjdGlvbjsAClJlZmxlY3Rpb24AD1JlZmxlY3Rpb24uamF2YQAHU0RLX0lOVAADVEFHAAFWAAxWRVJTSU9OX0NPREUADFZFUlNJT05fTkFNRQACVkwAAVoAAlpMABJbTGphdmEvbGFuZy9DbGFzczsAE1tMamF2YS9sYW5nL09iamVjdDsAE1tMamF2YS9sYW5nL1N0cmluZzsAB2NvbnRleHQAF2RhbHZpay5zeXN0ZW0uVk1SdW50aW1lAAFlAAZleGVtcHQACWV4ZW1wdEFsbAAHZm9yTmFtZQAPZnJlZS1yZWZsZWN0aW9uABJnZXRBcHBsaWNhdGlvbkluZm8AEWdldERlY2xhcmVkTWV0aG9kAApnZXRSdW50aW1lAAZpbnZva2UAC2xvYWRMaWJyYXJ5ABhtZS53ZWlzaHUuZnJlZXJlZmxlY3Rpb24ABm1ldGhvZAAHbWV0aG9kcwAZcmVmbGVjdCBib290c3RyYXAgZmFpbGVkOgAHcmVsZWFzZQAKc1ZtUnVudGltZQAWc2V0SGlkZGVuQXBpRXhlbXB0aW9ucwAQdGFyZ2V0U2RrVmVyc2lvbgAEdGhpcwAGdW5zZWFsAAx1bnNlYWxOYXRpdmUADnZtUnVudGltZUNsYXNzAAYABw4AFgAHDmr/AwEyCwEVEAMCNQvwBAREBhcBEg8DAzYLARsPqQUCBQMFBBkeAwAvCgAOAAcOACwBOgcOADYBOwcsnRriAQEDAC8KHgBIAAcOAA0ABw4AEwEtBx1yGWtaAAYXOBc8HxcABAEXAQEXBgEXHwYAAQACGQEZARkBGQEZARkGgYAEiAcDAAUACBoBCgEKB4iABKAHAYGABLQJAQnMCQGJAfQJAQnMCgEAAwALGgyBgAT4CgEJkAsBigIAAAAADgAAAAAAAAABAAAAAAAAAAEAAABEAAAAcAAAAAIAAAATAAAAgAEAAAMAAAALAAAAzAEAAAQAAAAMAAAAUAIAAAUAAAAPAAAAsAIAAAYAAAADAAAAKAMAAAEgAAAIAAAAiAMAAAEQAAAHAAAA3AUAAAIgAABEAAAAFgYAAAMgAAAIAAAAAwoAAAUgAAADAAAAZgoAAAAgAAADAAAAeAoAAAAQAAABAAAAyAoAAA==";
    public final Class<?> a;
    public final Object b;

    public class a implements InvocationHandler {
        public final /* synthetic */ boolean a;

        public a(boolean z) {
            this.a = z;
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            String name = method.getName();
            try {
                return new q7(q7.this.a, q7.this.b).a(name, objArr).c();
            } catch (c e) {
                if (this.a) {
                    Map map = (Map) q7.this.b;
                    int length = objArr == null ? 0 : objArr.length;
                    if (length == 0 && name.startsWith("get")) {
                        return map.get(q7.k(name.substring(3)));
                    }
                    if (length == 0 && name.startsWith("is")) {
                        return map.get(q7.k(name.substring(2)));
                    }
                    if (length == 1 && name.startsWith("set")) {
                        map.put(q7.k(name.substring(3)), objArr[0]);
                        return null;
                    }
                }
                throw e;
            }
        }
    }

    public static class b {
    }

    public static class c extends RuntimeException {
        public static final long a = -6213149635297151442L;

        public c() {
        }

        public c(String str) {
            super(str);
        }

        public c(String str, Throwable th) {
            super(str, th);
        }

        public c(Throwable th) {
            super(th);
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                c = Class.class.getDeclaredMethod("getMethod", String.class, Class[].class);
                d = Class.class.getDeclaredMethod("getMethods", new Class[0]);
                e = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
                f = Class.class.getDeclaredMethod("getDeclaredMethods", new Class[0]);
                g = Class.class.getDeclaredMethod("getField", String.class);
                h = Class.class.getDeclaredMethod("getFields", new Class[0]);
                i = Class.class.getDeclaredMethod("getDeclaredField", String.class);
                j = Class.class.getDeclaredMethod("getDeclaredFields", new Class[0]);
                k = Class.class.getDeclaredMethod("getSuperclass", new Class[0]);
                l = AccessibleObject.class.getDeclaredMethod("setAccessible", Boolean.TYPE);
                m = true;
            } catch (Throwable th) {
            }
        }
    }

    public q7(Class<?> cls) {
        this(cls, cls);
    }

    public q7(Class<?> cls, Object obj) {
        this.a = cls;
        this.b = obj;
    }

    public static q7 a(Class cls, Object obj) {
        return new q7(cls, obj);
    }

    public static q7 a(Object obj) {
        return new q7(obj == null ? Object.class : obj.getClass(), obj);
    }

    public static q7 a(Method method, Object obj, Object... objArr) throws c {
        try {
            a(method);
            if (method.getReturnType() != Void.TYPE) {
                return a(method.invoke(obj, objArr));
            }
            method.invoke(obj, objArr);
            return a(obj);
        } catch (Exception e2) {
            throw new c(e2);
        }
    }

    public static File a(Context context) {
        if (context != null) {
            return context.getCodeCacheDir();
        }
        String property = System.getProperty("java.io.tmpdir");
        if (TextUtils.isEmpty(property)) {
            return null;
        }
        File file = new File(property);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public static Class<?> a(String str, ClassLoader classLoader) throws c {
        try {
            return Class.forName(str, true, classLoader);
        } catch (Exception e2) {
            throw new c(e2);
        }
    }

    public static <T extends AccessibleObject> T a(T t) {
        if (t == null) {
            return null;
        }
        if (t instanceof Member) {
            Member member = (Member) t;
            if (Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
                return t;
            }
        }
        if (!t.isAccessible()) {
            if (m) {
                try {
                    l.invoke(t, Boolean.TRUE);
                    return t;
                } catch (Throwable th) {
                    return t;
                }
            }
            t.setAccessible(true);
        }
        return t;
    }

    public static q7 b(Class<?> cls) {
        return new q7(cls, cls);
    }

    public static q7 b(Class<?> cls, Object obj) {
        return new q7(cls, obj);
    }

    public static Object b(Object obj) {
        return obj instanceof q7 ? ((q7) obj).c() : obj;
    }

    public static void b(Context context) {
        c(context);
    }

    public static Class<?>[] b(Object... objArr) {
        if (objArr == null) {
            return new Class[0];
        }
        Class<?>[] clsArr = new Class[objArr.length];
        for (int i2 = 0; i2 < objArr.length; i2++) {
            Object obj = objArr[i2];
            clsArr[i2] = obj == null ? b.class : obj.getClass();
        }
        return clsArr;
    }

    public static Class<?> c(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        return cls.isPrimitive() ? Boolean.TYPE == cls ? Boolean.class : Integer.TYPE == cls ? Integer.class : Long.TYPE == cls ? Long.class : Short.TYPE == cls ? Short.class : Byte.TYPE == cls ? Byte.class : Double.TYPE == cls ? Double.class : Float.TYPE == cls ? Float.class : Character.TYPE == cls ? Character.class : Void.TYPE == cls ? Void.class : cls : cls;
    }

    public static void c(Context context) {
        if (Build.VERSION.SDK_INT >= 28 && !m0.a()) {
            d(context);
        }
    }

    public static boolean d(Context context) {
        byte[] bArrDecode = Base64.decode(n, 2);
        File fileA = a(context);
        if (fileA == null) {
            return false;
        }
        File file = new File(fileA, System.currentTimeMillis() + ".dex");
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                fileOutputStream2.write(bArrDecode);
                file.setReadOnly();
                boolean zBooleanValue = ((Boolean) new DexFile(file).loadClass("me.weishu.reflection.BootstrapClass", null).getDeclaredMethod("exemptAll", new Class[0]).invoke(null, new Object[0])).booleanValue();
                try {
                    fileOutputStream2.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                if (file.exists()) {
                    file.delete();
                }
                return zBooleanValue;
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                try {
                    th.toString();
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (file.exists()) {
                        file.delete();
                    }
                    return false;
                } finally {
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static Class<?> e(String str) throws c {
        try {
            return Class.forName(str);
        } catch (Exception e2) {
            throw new c(e2);
        }
    }

    public static boolean g(String str) {
        Class<?> cls;
        try {
            cls = Class.forName(str);
        } catch (Exception e2) {
            cls = null;
        }
        return cls != null;
    }

    public static q7 j(String str) throws c {
        Class<?> clsE = e(str);
        return new q7(clsE, clsE);
    }

    public static String k(String str) {
        int length = str.length();
        if (length == 0) {
            return c7.c;
        }
        if (length == 1) {
            return str.toLowerCase(Locale.getDefault());
        }
        return str.substring(0, 1).toLowerCase(Locale.getDefault()) + str.substring(1);
    }

    public q7 a() throws c {
        return a(new Object[0]);
    }

    public q7 a(String str, Object obj) throws c {
        try {
            Field fieldD = d(str);
            if ((fieldD.getModifiers() & 16) == 16) {
                if (m) {
                    Field field = (Field) i.invoke(Field.class, "modifiers");
                    l.invoke(field, Boolean.TRUE);
                    ((Method) e.invoke(Field.class, "setInt", new Class[]{Object.class, Integer.TYPE})).invoke(field, fieldD, Integer.valueOf(fieldD.getModifiers() & (-17)));
                } else {
                    Field declaredField = Field.class.getDeclaredField("modifiers");
                    declaredField.setAccessible(true);
                    declaredField.setInt(fieldD, fieldD.getModifiers() & (-17));
                }
            }
            if (m) {
                ((Method) e.invoke(Field.class, "set", new Class[]{Object.class, Object.class})).invoke(fieldD, this.b, b(obj));
                return this;
            }
            fieldD.set(this.b, b(obj));
            return this;
        } catch (Exception e2) {
            throw new c(e2);
        }
    }

    public q7 a(String str, Object... objArr) throws c {
        Class<?>[] clsArrB = b(objArr);
        try {
            return a(a(str, clsArrB), this.b, objArr);
        } catch (NoSuchMethodException e2) {
            try {
                return a(b(str, clsArrB), this.b, objArr);
            } catch (NoSuchMethodException e3) {
                throw new c(e3);
            }
        }
    }

    public q7 a(Object... objArr) throws c {
        Class<?>[] clsArrB = b(objArr);
        try {
            return a(d().getDeclaredConstructor(clsArrB), objArr);
        } catch (NoSuchMethodException e2) {
            for (Constructor<?> constructor : d().getDeclaredConstructors()) {
                if (a(constructor.getParameterTypes(), clsArrB)) {
                    return a(constructor, objArr);
                }
            }
            throw new c(e2);
        }
    }

    public <P> P a(Class<P> cls) {
        return (P) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new a(this.b instanceof Map));
    }

    public final Method a(String str, Class<?>[] clsArr) throws NoSuchMethodException {
        Class<?> clsD = d();
        try {
            return m ? (Method) c.invoke(clsD, str, clsArr) : clsD.getMethod(str, clsArr);
        } catch (Exception e2) {
            do {
                try {
                    return m ? (Method) e.invoke(clsD, str, clsArr) : clsD.getDeclaredMethod(str, clsArr);
                } catch (Exception e3) {
                    if (m) {
                        try {
                            clsD = (Class) k.invoke(clsD, new Object[0]);
                        } catch (Throwable th) {
                        }
                    } else {
                        clsD = clsD.getSuperclass();
                    }
                }
            } while (clsD != null);
            throw new NoSuchMethodException();
        }
    }

    public final boolean a(Method method, String str, Class<?>[] clsArr) {
        return method.getName().equals(str) && a(method.getParameterTypes(), clsArr);
    }

    public final boolean a(Class<?>[] clsArr, Class<?>[] clsArr2) {
        if (clsArr.length != clsArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < clsArr2.length; i2++) {
            if (clsArr2[i2] != b.class && !c(clsArr[i2]).isAssignableFrom(c(clsArr2[i2]))) {
                return false;
            }
        }
        return true;
    }

    public q7 b(String str) throws c {
        return a(str, new Object[0]);
    }

    public final Method b(String str, Class<?>[] clsArr) throws NoSuchMethodException {
        Method[] methods;
        Class<?> clsD = d();
        Method[] declaredMethods = null;
        if (m) {
            try {
                methods = (Method[]) d.invoke(clsD, new Object[0]);
            } catch (Throwable th) {
                methods = null;
            }
        } else {
            methods = clsD.getMethods();
        }
        for (Method method : methods) {
            if (a(method, str, clsArr)) {
                return method;
            }
        }
        if (m) {
            try {
                declaredMethods = (Method[]) f.invoke(clsD, new Object[0]);
            } catch (Throwable th2) {
            }
        } else {
            declaredMethods = clsD.getDeclaredMethods();
        }
        do {
            for (Method method2 : declaredMethods) {
                if (a(method2, str, clsArr)) {
                    return method2;
                }
            }
            if (m) {
                try {
                    clsD = (Class) k.invoke(clsD, new Object[0]);
                } catch (Throwable th3) {
                }
            } else {
                clsD = clsD.getSuperclass();
            }
        } while (clsD != null);
        throw new NoSuchMethodException("No similar method " + str + " with params " + Arrays.toString(clsArr) + " could be found on type " + d() + ".");
    }

    public Map<String, q7> b() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Class<?> clsD = d();
        Field[] declaredFields = null;
        do {
            if (m) {
                try {
                    declaredFields = (Field[]) j.invoke(clsD, new Object[0]);
                } catch (Throwable th) {
                }
            } else {
                declaredFields = clsD.getDeclaredFields();
            }
            for (Field field : declaredFields) {
                if ((this.a != this.b) ^ Modifier.isStatic(field.getModifiers())) {
                    String name = field.getName();
                    if (!linkedHashMap.containsKey(name)) {
                        linkedHashMap.put(name, c(name));
                    }
                }
            }
            if (m) {
                try {
                    clsD = (Class) k.invoke(clsD, new Object[0]);
                } catch (Throwable th2) {
                }
            } else {
                clsD = clsD.getSuperclass();
            }
        } while (clsD != null);
        return linkedHashMap;
    }

    public q7 c(String str) throws c {
        try {
            Field fieldD = d(str);
            return new q7(fieldD.getType(), fieldD.get(this.b));
        } catch (Exception e2) {
            throw new c(e2);
        }
    }

    public <T> T c() {
        return (T) this.b;
    }

    public Class<?> d() {
        return this.a;
    }

    public final Field d(String str) throws c {
        Class<?> clsD = d();
        try {
            return m ? (Field) a((Field) g.invoke(clsD, str)) : (Field) a(clsD.getField(str));
        } catch (Exception e2) {
            do {
                try {
                    return m ? (Field) a((Field) i.invoke(clsD, str)) : (Field) a(clsD.getDeclaredField(str));
                } catch (Exception e3) {
                    if (m) {
                        try {
                            clsD = (Class) k.invoke(clsD, new Object[0]);
                        } catch (Throwable th) {
                        }
                    } else {
                        clsD = clsD.getSuperclass();
                    }
                    if (clsD == null) {
                        throw new c(e2);
                    }
                }
            } while (clsD == null);
            throw new c(e2);
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof q7) {
            return this.b.equals(((q7) obj).c());
        }
        return false;
    }

    public <T> T f(String str) throws c {
        return (T) c(str).c();
    }

    public boolean h(String str) {
        Map<String, q7> mapB = b();
        if (mapB == null || mapB.size() <= 0) {
            return false;
        }
        return mapB.containsKey(str);
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public boolean i(String str) {
        Class<?> clsD = d();
        Method[] declaredMethods = null;
        do {
            if (m) {
                try {
                    declaredMethods = (Method[]) f.invoke(clsD, new Object[0]);
                } catch (Throwable th) {
                }
            } else {
                declaredMethods = clsD.getDeclaredMethods();
            }
            for (Method method : declaredMethods) {
                if (method.getName().equals(str)) {
                    return true;
                }
            }
            if (m) {
                try {
                    clsD = (Class) k.invoke(clsD, new Object[0]);
                } catch (Throwable th2) {
                }
            } else {
                clsD = clsD.getSuperclass();
            }
        } while (clsD != null);
        return false;
    }

    public String toString() {
        return this.b.toString();
    }

    public static q7 a(Constructor<?> constructor, Object... objArr) throws c {
        try {
            return new q7(constructor.getDeclaringClass(), ((Constructor) a(constructor)).newInstance(objArr));
        } catch (Exception e2) {
            throw new c(e2);
        }
    }

    public static q7 b(String str, ClassLoader classLoader) throws c {
        Class<?> clsA = a(str, classLoader);
        return new q7(clsA, clsA);
    }
}
