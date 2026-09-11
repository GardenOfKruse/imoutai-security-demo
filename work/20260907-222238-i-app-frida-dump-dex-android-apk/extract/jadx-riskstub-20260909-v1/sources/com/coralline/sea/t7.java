package com.coralline.sea;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class t7 {
    public static t7 a;

    public static synchronized t7 a() {
        if (a == null) {
            a = new t7();
        }
        return a;
    }

    public void a(s1 s1Var) {
        s1Var.c((String) null);
        j1.b().a(s1Var, true, true);
    }

    public void a(s1 s1Var, String str) {
        s1Var.c(str);
        j1.b().a(s1Var, false, false);
    }

    public void b() {
        j1.b().a(m8.e);
    }

    public void b(s1 s1Var) {
        s1Var.c((String) null);
        j1.b().a(s1Var, true, false);
    }

    public void b(s1 s1Var, String str) {
        s1Var.c(str);
        j1.b().a(s1Var, false, true);
    }
}
