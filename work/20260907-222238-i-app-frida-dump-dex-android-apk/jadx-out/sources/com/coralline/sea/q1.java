package com.coralline.sea;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class q1 {
    public static final int d = 0;
    public static final int e = 1;
    public static q1 f;
    public int a;
    public String b = "0";
    public String c = "0";

    public static q1 a(int i, int i2) {
        q1 q1Var = new q1();
        q1Var.a = 1;
        q1Var.b = p1.a(c7.c, i);
        q1Var.c = p1.a(c7.c, i2);
        return q1Var;
    }

    public static q1 d() {
        if (f == null) {
            q1 q1Var = new q1();
            f = q1Var;
            q1Var.a(0);
        }
        return f;
    }

    public String a() {
        return this.c;
    }

    public void a(int i) {
        this.a = i;
    }

    public final void a(String str) {
        this.c = str;
    }

    public String b() {
        return this.b;
    }

    public void b(String str) {
        this.b = str;
    }

    public int c() {
        return this.a;
    }
}
