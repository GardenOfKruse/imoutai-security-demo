package com.coralline.sea;

import java.math.BigDecimal;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class g1 {
    public static final g1 c = new g1();
    public boolean a = false;
    public double b = 0.0d;

    public static g1 b() {
        return c;
    }

    public double a(Object obj) {
        if (obj != null) {
            if (this.b == 0.0d) {
                this.b = obj instanceof Integer ? ((Integer) obj).doubleValue() : obj instanceof BigDecimal ? ((BigDecimal) obj).doubleValue() : ((Double) obj).doubleValue();
                return this.b;
            }
        }
        return this.b;
    }

    public int a(int i, double d, double d2, double d3) {
        return (int) ((Math.log(d2 + ((double) i)) / Math.log(d)) + d3);
    }

    public void a(int i, int i2) {
        if (i == i2) {
            this.a = true;
            return;
        }
        a9.b(a9.e, b1.q + c7.c);
    }

    public boolean a() {
        return this.a;
    }

    public void c() {
        try {
            if (a9.a(a9.e, (String) null) != null) {
                a9.b(a9.e);
            }
        } catch (Exception e) {
        }
    }
}
