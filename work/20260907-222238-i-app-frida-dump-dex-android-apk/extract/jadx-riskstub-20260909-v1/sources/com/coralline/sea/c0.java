package com.coralline.sea;

import android.content.Context;
import android.os.Handler;
import com.coralline.sea.checkers.Checker;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class c0 extends x6 {
    public static final String d = "auto_click";
    public static volatile c0 f;
    public String b;
    public String c;
    public static final Context e = n3.a().a;
    public static boolean g = false;

    public class a implements Runnable {
        public final /* synthetic */ Checker a;

        public a(Checker checker) {
            this.a = checker;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.check();
        }
    }

    public c0() {
        super("auto_click", 60);
        this.b = c7.c;
        this.c = c7.c;
    }

    public static c0 a() {
        if (f == null) {
            synchronized (c0.class) {
                if (f == null) {
                    f = new c0();
                }
            }
        }
        return f;
    }

    public static boolean b() {
        return k2.d().b("auto_click");
    }

    public static void c() {
        Checker checkerB;
        Handler handlerD;
        com.coralline.sea.checkers.a aVarB = com.coralline.sea.checkers.a.b();
        if (aVarB == null || (checkerB = aVarB.b("auto_click")) == null || (handlerD = aVarB.d()) == null) {
            return;
        }
        handlerD.post(new a(checkerB));
    }

    @Override // com.coralline.sea.checkers.Checker
    public synchronized void check() {
        JSONObject jSONObjectA;
        try {
            jSONObjectA = d0.b().a();
        } catch (Exception e2) {
            e2.toString();
        }
        if (jSONObjectA == null) {
            return;
        }
        if (this.b.equals(ja.o() + c7.c)) {
            return;
        }
        this.b = ja.o() + c7.c;
        g = true;
        push(e2.b, "auto_click", jSONObjectA.toString());
    }
}
