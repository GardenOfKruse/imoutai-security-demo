package com.coralline.sea;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class b0 implements InvocationHandler {
    public q4 a;
    public List<s6> b = new ArrayList();

    public b0(q4 q4Var) {
        this.a = q4Var;
    }

    public void a() {
        this.b.clear();
    }

    public void a(s6 s6Var) {
        if (s6Var != null) {
            this.b.add(s6Var);
        }
    }

    public final void a(Method method, Object[] objArr) {
        List<s6> list;
        try {
            String name = method.getName();
            method.getName();
            if (!m7.a(this.a.e(), name) || (list = this.b) == null || list.isEmpty()) {
                return;
            }
            for (s6 s6Var : this.b) {
                if (s6Var != null) {
                    s6Var.a(this.a, method, objArr);
                }
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
        }
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) {
        if (this.a == null) {
            return null;
        }
        a(method, objArr);
        return null;
    }
}
