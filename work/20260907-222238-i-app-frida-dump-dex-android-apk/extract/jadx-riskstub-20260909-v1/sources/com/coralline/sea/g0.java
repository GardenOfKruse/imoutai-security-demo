package com.coralline.sea;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public abstract class g0 implements o4 {
    public v3 a = null;
    public boolean b = true;

    public void a(s1 s1Var) {
    }

    @Override // com.coralline.sea.o4
    public void a(v3 v3Var) {
        this.a = v3Var;
    }

    @Override // com.coralline.sea.o4
    public final void a(boolean z) {
        this.b = z;
    }

    @Override // com.coralline.sea.o4
    public boolean b() {
        return h() == v3.STOP_RUNNING;
    }

    @Override // com.coralline.sea.o4
    public final boolean e() {
        return this.b;
    }

    @Override // com.coralline.sea.o4
    public boolean f() {
        return h() == v3.ONLY_KEEPALIVE;
    }

    @Override // com.coralline.sea.o4
    public v3 h() {
        return this.a;
    }
}
