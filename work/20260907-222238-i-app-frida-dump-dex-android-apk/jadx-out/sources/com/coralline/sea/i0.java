package com.coralline.sea;

import java.lang.reflect.InvocationHandler;

/* JADX INFO: loaded from: assets/RiskStub.dex */
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
