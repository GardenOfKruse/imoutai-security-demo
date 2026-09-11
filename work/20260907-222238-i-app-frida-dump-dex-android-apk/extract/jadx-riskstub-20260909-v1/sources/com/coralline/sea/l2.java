package com.coralline.sea;

import com.coralline.sea.h9;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class l2 {
    public static l2 a;
    public static o4 b;
    public static n4 c;
    public static p4 d;
    public static w4 e;
    public static y4 f;
    public static u4 g;

    public class a extends h9.c {
        @Override // com.coralline.sea.h9.c, com.coralline.sea.u4
        public boolean b(s1 s1Var) {
            return super.b(s1Var) && !s1Var.d.equals("mobile_pool");
        }
    }

    public static synchronized l2 g() {
        if (a == null) {
            a = new l2();
            h();
        }
        return a;
    }

    public static void h() {
        a aVar;
        try {
            if (n3.a().c) {
                c = v0.b();
                b = l7.j();
                d = c1.a();
                e = h9.d.b();
                f = h9.e.b();
                aVar = new a();
            } else {
                aVar = null;
                c = null;
                b = l7.j();
                d = null;
            }
            g = aVar;
        } catch (Exception e2) {
        }
    }

    public n4 a() {
        return c;
    }

    public o4 b() {
        return b;
    }

    public p4 c() {
        return d;
    }

    public y4 d() {
        return f;
    }

    public u4 e() {
        return g;
    }

    public w4 f() {
        return e;
    }
}
