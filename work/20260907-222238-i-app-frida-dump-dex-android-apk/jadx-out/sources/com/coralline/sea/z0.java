package com.coralline.sea;

import java.net.URLEncoder;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class z0 {
    public static z0 b = new z0();
    public w0 a;

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public z0() {
        this.a = n3.a().A ? new y0() : new a1();
    }

    public static z0 a() {
        return b;
    }

    public String a(String str) throws Exception {
        return this.a.a(str);
    }

    public String b(String str) throws Exception {
        return URLEncoder.encode(this.a.b(str), s0.f);
    }
}
