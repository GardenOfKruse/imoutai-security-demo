package com.coralline.sea;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class t8 extends i0 {
    public static final String c = "android.content.pm.IPackageManager";

    public t8(Object obj, b0 b0Var) {
        super(obj, b0Var);
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) {
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
            return null;
        } catch (SecurityException e2) {
            throw e2;
        } catch (Exception e3) {
            return null;
        }
    }
}
