package com.coralline.sea;

import android.os.IBinder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class i7 extends i0 {
    public i7(Object obj, b0 b0Var) {
        super(obj, b0Var);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0044 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0046  */
    @Override // java.lang.reflect.InvocationHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        if ("asBinder".equals(method.getName())) {
            IBinder iBinder = (IBinder) method.invoke(b(), objArr);
            iBinder.isBinderAlive();
            return iBinder;
        }
        try {
            if (a() != null) {
                a().invoke(obj, method, objArr);
            }
            if (b() != null) {
                obj = b();
            }
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            m7.a(e);
            if (b() != null) {
                return null;
            }
            return method.invoke(b(), objArr);
        } catch (SecurityException e2) {
            throw e2;
        } catch (Exception e3) {
            if (b() != null) {
            }
        }
    }
}
