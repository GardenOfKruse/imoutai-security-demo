package com.coralline.sea;

import java.lang.reflect.InvocationHandler;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public abstract class i0 implements InvocationHandler {
    public Object a;
    public b0 b;

    public i0(Object obj, b0 b0Var) {
        this.a = obj;
        this.b = b0Var;
    }

    public b0 a() {
        return this.b;
    }

    public void a(b0 b0Var) {
        this.b = b0Var;
    }

    public void a(Object obj) {
        this.a = obj;
    }

    public Object b() {
        return this.a;
    }
}
