package com.coralline.sea;

import android.os.Build;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class v5 {
    public static void a() {
        if (Build.VERSION.SDK_INT >= 28) {
            q7.b(n3.a().a);
            ja.b();
        }
    }

    public static boolean b() {
        x9.a(r5.a);
        n3 n3VarA = n3.a();
        if (n3VarA == null || !n3VarA.i()) {
            x9.a(r5.d);
            return false;
        }
        a();
        if (j6.c()) {
            x9.a(r5.k);
            com.coralline.sea.checkers.a.c().a(false);
            return true;
        }
        if (n3VarA.g) {
            com.coralline.sea.checkers.a.c().a(true);
            return true;
        }
        if (n3VarA.c) {
            com.coralline.sea.checkers.a.c().a(true);
            return true;
        }
        com.coralline.sea.checkers.a.c().a(true);
        return true;
    }
}
