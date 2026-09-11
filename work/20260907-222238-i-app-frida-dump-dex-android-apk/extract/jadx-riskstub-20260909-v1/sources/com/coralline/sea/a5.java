package com.coralline.sea;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class a5 {
    public static a5 a;
    public static x4 b;
    public static v4 c;

    public static class b implements InvocationHandler {
        public Object a;

        public Object a(Object obj) {
            this.a = obj;
            return Proxy.newProxyInstance(obj.getClass().getClassLoader(), this.a.getClass().getInterfaces(), this);
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            return method.invoke(this.a, objArr);
        }
    }

    public static class c implements InvocationHandler {
        public Object a;

        public c() {
        }

        public Object a(Object obj) {
            this.a = obj;
            return Proxy.newProxyInstance(obj.getClass().getClassLoader(), this.a.getClass().getInterfaces(), this);
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            return method.invoke(this.a, objArr);
        }
    }

    public static class d implements InvocationHandler {
        public Object a;

        public d() {
        }

        public Object a(Object obj) {
            this.a = obj;
            return Proxy.newProxyInstance(obj.getClass().getClassLoader(), this.a.getClass().getInterfaces(), this);
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            return method.invoke(this.a, objArr);
        }
    }

    public static synchronized a5 a() {
        if (a == null) {
            a = new a5();
            d();
        }
        return a;
    }

    public static Object a(Object obj, Method method, Object[] objArr) throws Throwable {
        return method.invoke(obj, objArr);
    }

    public static void d() {
        b = (x4) new d().a(new u7());
        c = (v4) new c().a(new p7());
    }

    public v4 b() {
        return c;
    }

    public x4 c() {
        return b;
    }
}
