package com.coralline.sea;

/* JADX INFO: loaded from: assets/RiskStub.dex */
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
