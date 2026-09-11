package com.coralline.sea;

import com.coralline.sea.m5;
import java.util.Iterator;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class da {
    public static synchronized void a(s1 s1Var) {
        if (s1Var.f()) {
            return;
        }
        if (!s1Var.c.equals(e2.c) && !s1Var.c.equals(e2.d)) {
            if (s1Var instanceof h6) {
                Iterator<s1> it = ((h6) s1Var).k().iterator();
                while (it.hasNext()) {
                    b(it.next());
                }
            } else {
                b(s1Var);
            }
        }
    }

    public static void b(s1 s1Var) {
        if (n3.a().q) {
            q6.g.a(s1Var.a(true));
        } else if (n3.T.g) {
            n6.a().c(s1Var);
        } else {
            m5.a().a(s1Var.a(true), m5.b.C0005b.b);
        }
    }
}
