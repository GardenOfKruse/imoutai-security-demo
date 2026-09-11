package com.coralline.sea;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class w8 implements InvocationHandler {
    public IBinder a;
    public Class<?> b;
    public Class<?> c;
    public InvocationHandler d;

    public static class a implements InvocationHandler {
        public Object a;
        public InvocationHandler b;

        public a(Object obj, Class<?> cls, InvocationHandler invocationHandler) {
            this.b = invocationHandler;
            this.a = obj;
            try {
                if (obj instanceof IBinder) {
                    this.a = Proxy.newProxyInstance(obj.getClass().getClassLoader(), new Class[]{IBinder.class}, new b((IBinder) this.a, (IInterface) obj));
                }
            } catch (Exception e) {
            }
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            InvocationHandler invocationHandler = this.b;
            return invocationHandler != null ? invocationHandler.invoke(this.a, method, objArr) : method.invoke(this.a, objArr);
        }
    }

    public static class b implements InvocationHandler {
        public IBinder a;
        public IInterface b;

        public b(IBinder iBinder, IInterface iInterface) {
            this.a = iBinder;
            this.b = iInterface;
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            try {
                if (objArr.length >= 2) {
                    boolean z = objArr[1] instanceof Parcel;
                }
                return method.invoke(this.a, objArr);
            } catch (Exception e) {
                IInterface iInterface = this.b;
                if (iInterface == null) {
                    return null;
                }
                iInterface.getClass();
                return null;
            }
        }
    }

    public w8(IBinder iBinder, String str, boolean z, InvocationHandler invocationHandler) {
        this.a = iBinder;
        this.d = invocationHandler;
        try {
            this.c = Class.forName(str);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(c7.c);
            sb.append(z ? "$Stub" : c7.c);
            this.b = Class.forName(sb.toString());
        } catch (Exception e) {
        }
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())) {
            try {
                return Proxy.newProxyInstance(obj.getClass().getClassLoader(), new Class[]{this.c}, new a(this.b.getDeclaredMethod("asInterface", IBinder.class).invoke(null, this.a), this.b, this.d));
            } catch (Exception e) {
            }
        }
        return method.invoke(this.a, objArr);
    }
}
