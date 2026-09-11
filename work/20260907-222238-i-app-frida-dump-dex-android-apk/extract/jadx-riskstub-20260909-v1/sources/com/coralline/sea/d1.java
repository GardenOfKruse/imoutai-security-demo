package com.coralline.sea;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class d1 implements y4 {
    public static d1 b;
    public double a = 0.0d;

    public static d1 b() {
        if (b == null) {
            b = new d1();
        }
        return b;
    }

    @Override // com.coralline.sea.y4
    public double a() {
        return this.a;
    }

    @Override // com.coralline.sea.y4
    public void a(double d) {
        this.a = d;
    }
}
