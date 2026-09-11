package com.coralline.sea;

import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class m8 {
    public static final String a = "SendQ";
    public static LinkedList<s1> b = null;
    public static boolean c = false;
    public static m8 d = null;
    public static String e = "startInfo";
    public static l4 f = new a();

    public class a implements l4 {
        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) {
            boolean unused = m8.c = true;
            Iterator<s1> it = m8.b.iterator();
            while (it.hasNext()) {
                n8.c().a(it.next());
            }
        }
    }

    public m8() {
        b = new LinkedList<>();
        j1.c(f, e);
    }

    public static synchronized m8 b() {
        return d;
    }

    public static synchronized m8 c() {
        if (d == null) {
            d = new m8();
        }
        return d;
    }

    public void a(s1 s1Var) {
        if (c) {
            n8.c().a(s1Var);
        } else {
            b.add(s1Var);
        }
    }

    public void b(s1 s1Var) {
        y9.b(s1Var);
    }
}
