package com.coralline.sea;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class a1 extends w0 {
    public f1 a;
    public s0 b;

    public a1() {
        String[] strArrN = i6.n();
        this.a = new f1(strArrN[0], strArrN[1]);
        this.b = new s0(i6.m());
    }

    @Override // com.coralline.sea.w0
    public String a(String str) throws Exception {
        return this.b.a(str, s0.f);
    }

    @Override // com.coralline.sea.w0
    public String b(String str) throws Exception {
        return this.a.a(str, s0.f);
    }
}
