package com.coralline.sea;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class c1 implements p4 {
    public static final long c = 0;
    public static final int d = 204800;
    public static c1 e;
    public static long f;
    public boolean a = false;
    public boolean b = true;

    public static synchronized c1 a() {
        if (e == null) {
            e = new c1();
        }
        return e;
    }

    public void a(boolean z) {
        this.b = z;
    }

    @Override // com.coralline.sea.p4
    public boolean a(s1 s1Var) {
        String strB = s1Var.b();
        strB.length();
        if (System.currentTimeMillis() - f >= 0) {
            this.a = true;
        }
        if (m1.j().o() == null || !this.a) {
            return false;
        }
        boolean zH = m1.j().h(strB);
        if (zH || !this.b) {
            return zH;
        }
        this.a = false;
        f = System.currentTimeMillis();
        return zH;
    }
}
