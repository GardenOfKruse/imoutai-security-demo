package com.coralline.sea;

import android.os.IBinder;
import android.text.TextUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class u8 extends i0 {
    public IBinder c;

    public u8(Object obj, b0 b0Var) {
        super(obj, b0Var);
    }

    public void a(IBinder iBinder) {
        this.c = iBinder;
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) {
        try {
            if (this.c != null && method != null && TextUtils.equals(method.getName(), "asBinder")) {
                return this.c;
            }
            if (a() != null) {
                a().invoke(obj, method, objArr);
            }
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            m7.a(e);
            return null;
        } catch (SecurityException e2) {
            throw e2;
        } catch (Exception e3) {
            return null;
        }
    }
}
