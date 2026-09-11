package com.coralline.sea;

import java.io.File;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class f2 extends t6 {
    public static final String b = "everisk_jcrash.txt";
    public static final String c = "everisk_anrcrash.txt";
    public static final String d = "everisk_ccrash.dmp";
    public static final String e = "NETWORK_WIFI";
    public static final String f = "NETWORK_4G";
    public l4 a;

    public class a implements l4 {
        public a() {
        }

        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) {
            try {
                if (new File(z3.b().a).exists()) {
                    new File(z3.j.a).delete();
                }
                if (new File(z3.j.c).exists()) {
                    new File(z3.j.c).delete();
                }
                if (new File(z3.j.b).exists()) {
                    new File(z3.j.b).delete();
                }
            } catch (Exception e) {
            }
        }
    }

    public f2() {
        super(x9.f);
        this.a = new a();
    }

    public final boolean a() {
        String strB = l6.b(n3.a().a);
        return ("NETWORK_NO".equals(strB) || "NETWORK_2G".equals(strB) || "NETWORK_3G".equals(strB)) ? false : true;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
    }

    @Override // com.coralline.sea.checkers.Checker
    public void start() {
        i6.f(n3.a().r, d);
        if (!n3.T.q) {
            g5.a();
        }
        JSONObject jSONObjectA = z1.a(x9.f);
        int iOptInt = jSONObjectA.optInt("anr_time", 5);
        new com.coralline.sea.a(iOptInt * 1000).a(new m3()).a(jSONObjectA.optInt("anr_thread_count", 10)).start();
    }
}
