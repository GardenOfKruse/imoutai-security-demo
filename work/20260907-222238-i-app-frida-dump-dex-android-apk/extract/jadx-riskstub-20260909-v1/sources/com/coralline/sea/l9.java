package com.coralline.sea;

import android.text.TextUtils;
import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class l9 {
    public static l9 l = null;
    public static final String m = "is_triggered_global_flow_control_limit";
    public static final String n = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public boolean a;
    public int b;
    public int c;
    public int d;
    public int e;
    public s1 j;
    public boolean f = false;
    public boolean g = false;
    public boolean h = true;
    public int i = 0;
    public boolean k = false;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            l9.l.f();
        }
    }

    public l9() {
        List<Integer> listA = h9.b().d().a();
        if (listA == null) {
            this.a = false;
            return;
        }
        this.a = true;
        this.b = listA.get(0).intValue();
        this.c = listA.get(1).intValue();
        this.d = listA.get(2).intValue();
        this.e = listA.get(3).intValue() > 0 ? listA.get(3).intValue() : 3;
    }

    public static String a(int i) {
        StringBuilder sb = new StringBuilder(i);
        SecureRandom secureRandom = new SecureRandom();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(n.charAt(secureRandom.nextInt(62)));
        }
        return sb.toString();
    }

    public static synchronized l9 b() {
        if (l == null) {
            l = new l9();
        }
        return l;
    }

    public void a(boolean z) {
        this.h = z;
    }

    public final boolean a(s1 s1Var, int i) {
        try {
            n4 n4VarA = e2.a().a(s1Var.c);
            boolean zA = false;
            for (String str : n4VarA.a()) {
                JSONObject jSONObjectA = s1Var.a();
                jSONObjectA.put("upload_time", System.currentTimeMillis());
                String strA = n4VarA.a(jSONObjectA.toString());
                if (TextUtils.isEmpty(strA)) {
                    if (i != 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(".");
                        sb.append(i);
                    }
                    s1Var.i();
                    return false;
                }
                s1 s1Var2 = this.j;
                if (s1Var2 != null) {
                    JSONObject jSONObjectA2 = s1Var2.a();
                    String strA2 = n4VarA.a(jSONObjectA2.toString());
                    if (i != 0) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(".");
                        sb2.append(i);
                    }
                    this.j.i();
                    jSONObjectA2.length();
                    jSONObjectA2.toString();
                    if (i4.a().a(str, strA2.getBytes(), this.j)) {
                        zA = true;
                        String strA3 = n4VarA.a(this.j.e());
                        if (strA3 != null) {
                            s1Var.d(strA3);
                            if (i != 0) {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(".");
                                sb3.append(i);
                            }
                            s1Var.i();
                            s1Var.d();
                        }
                        this.j = null;
                    }
                }
                if (!s1Var.d.equals(g9.a)) {
                    if (i != 0) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(".");
                        sb4.append(i);
                    }
                    s1Var.i();
                    strA.length();
                    zA = i4.a().a(str, strA.getBytes(), s1Var);
                    if (zA) {
                        String strA4 = n4VarA.a(s1Var.e());
                        if (strA4 != null) {
                            s1Var.d(strA4);
                            if (i != 0) {
                                StringBuilder sb5 = new StringBuilder();
                                sb5.append(".");
                                sb5.append(i);
                            }
                            s1Var.i();
                            s1Var.d();
                            if (!n3.a().C) {
                                return zA;
                            }
                        }
                        if (!n3.a().C) {
                            new String(s1Var.e());
                            zA = false;
                        }
                    }
                    if (!n3.a().C) {
                        s1Var.i();
                    }
                }
            }
            return zA;
        } catch (Exception e) {
            e.toString();
            return false;
        }
    }

    public boolean c() {
        return this.h;
    }

    public boolean d() {
        return this.g;
    }

    public final void e() throws Exception {
        t7 t7VarA;
        String str;
        y9.g();
        s1 s1VarA = n8.c().a();
        if (s1VarA.d.equals(g9.a) && !this.k) {
            this.j = s1VarA;
            this.k = true;
        }
        if (!h9.b().a().a(s1VarA)) {
            x9.a(r5.g);
            t7VarA = t7.a();
            str = r5.g;
        } else if (this.g) {
            t7VarA = t7.a();
            str = r5.l;
        } else {
            if (this.h) {
                int i = e2.b.equals(s1VarA.c) ? this.e : 3;
                for (int i2 = 0; !a(s1VarA, i2); i2++) {
                    t7.a().a(s1VarA, r5.i);
                    if (!this.a || i2 >= i) {
                        return;
                    }
                    int i3 = (this.c * i2) + this.b;
                    int i4 = this.d;
                    if (i3 <= i4) {
                        i4 = i3;
                    }
                    m6.a(((long) i4) * 1000);
                }
                t7.a().b(s1VarA);
                JSONObject jSONObject = new JSONObject(s1VarA.d());
                if (jSONObject.has(m)) {
                    this.g = jSONObject.optBoolean(m);
                }
                double dA = h9.b().e().a();
                if (dA > 0.0d) {
                    TimeUnit.MILLISECONDS.sleep((long) (dA * 1000.0d));
                    return;
                }
                return;
            }
            t7VarA = t7.a();
            str = r5.m;
        }
        t7VarA.a(s1VarA, str);
    }

    public final void f() {
        while (true) {
            try {
                e();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void g() {
        new Thread(new a(), "Risk-thread-Switch").start();
    }
}
