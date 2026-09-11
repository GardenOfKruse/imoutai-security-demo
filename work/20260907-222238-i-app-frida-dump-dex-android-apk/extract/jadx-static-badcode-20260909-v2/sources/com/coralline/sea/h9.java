package com.coralline.sea;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class h9 {
    public static volatile h9 e;
    public p4 a;
    public u4 b;
    public w4 c;
    public y4 d;

    public static class b implements p4 {
        @Override // com.coralline.sea.p4
        public boolean a(s1 s1Var) {
            return true;
        }
    }

    public static class c implements u4 {
        public int a;
        public int b;
        public Timer c;
        public h6 d;
        public final Set<a> e = new HashSet();

        public class a extends TimerTask {
            public a() {
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                try {
                    c.this.b();
                    synchronized (c.this.e) {
                        c.this.e.remove(this);
                        c.this.e.size();
                    }
                } catch (Throwable th) {
                    synchronized (c.this.e) {
                        c.this.e.remove(this);
                        c.this.e.size();
                        throw th;
                    }
                }
            }
        }

        public c() {
            List<Integer> listA = a();
            a(listA.get(0).intValue(), listA.get(1).intValue());
            this.c = new Timer("coralline-send-data");
            this.d = new h6();
        }

        @Override // com.coralline.sea.u4
        public s1 a(s1 s1Var) {
            if (!b(s1Var) || this.b == 0 || this.a == 0) {
                String str = s1Var.d;
                n8.c().a(s1Var);
                return s1Var;
            }
            c();
            if (a(s1Var.j())) {
                b();
            }
            c(s1Var);
            return this.d;
        }

        public final List<Integer> a() {
            int iOptInt;
            int iOptInt2;
            JSONObject jSONObjectC;
            try {
                jSONObjectC = z1.c("mulit_config");
                iOptInt = jSONObjectC.optInt("msg_size", 0);
            } catch (Exception e) {
                e = e;
                iOptInt = 0;
            }
            try {
                iOptInt2 = jSONObjectC.optInt("wait_time", 0);
            } catch (Exception e2) {
                e = e2;
                e.getMessage();
                iOptInt2 = 0;
            }
            return Arrays.asList(Integer.valueOf(iOptInt), Integer.valueOf(iOptInt2));
        }

        @Override // com.coralline.sea.u4
        public void a(int i, int i2) {
            this.b = i < 1 ? 0 : i * 1024;
            this.a = i2 >= 1 ? i2 * 1000 : 0;
        }

        public final synchronized boolean a(int i) {
            return this.d.j() + i > this.b;
        }

        public final synchronized void b() {
            n8 n8VarC;
            s1 s1Var;
            if (this.d.l() < 1) {
                return;
            }
            if (this.d.l() == 1) {
                String str = this.d.k().get(0).d;
                n8VarC = n8.c();
                s1Var = this.d.k().get(0);
            } else {
                this.d.l();
                n8VarC = n8.c();
                s1Var = this.d;
            }
            n8VarC.a(s1Var);
            this.d = new h6();
        }

        @Override // com.coralline.sea.u4
        public boolean b(s1 s1Var) {
            if (s1Var.d.equals(g9.a) || s1Var.d.equals("startup_all") || s1Var.d.equals("userdata") || s1Var.f() || p3.b.equals(s1Var.d)) {
                return false;
            }
            return s1Var.c.equals(e2.b);
        }

        public final synchronized void c() {
            synchronized (this.e) {
                if (!this.e.isEmpty()) {
                    this.e.size();
                    return;
                }
                a aVar = new a();
                this.c.schedule(aVar, this.a);
                this.e.add(aVar);
                this.e.size();
            }
        }

        public final synchronized void c(s1 s1Var) {
            this.d.b(s1Var);
        }
    }

    public static class d implements w4 {
        public static d a;

        public d() {
        }

        public static synchronized d b() {
            if (a == null) {
                a = new d();
            }
            return a;
        }

        @Override // com.coralline.sea.w4
        public List<Integer> a() {
            List<Integer> listC = c();
            return listC != null ? listC : Arrays.asList(10, 10, 300, 3);
        }

        public final List<Integer> c() {
            try {
                JSONObject jSONObjectC = z1.c("http_time");
                if (jSONObjectC.length() > 0) {
                    return Arrays.asList(Integer.valueOf(jSONObjectC.optInt("wait_time_base")), Integer.valueOf(jSONObjectC.optInt("wait_time_step_value")), Integer.valueOf(jSONObjectC.optInt("wait_time_max")), Integer.valueOf(jSONObjectC.optInt("max_retry_count", 3)));
                }
                return null;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        }
    }

    public static class e implements y4 {
        public static e a;

        public e() {
        }

        public static synchronized e b() {
            if (a == null) {
                a = new e();
            }
            return a;
        }

        @Override // com.coralline.sea.y4
        public double a() {
            return 0.0d;
        }

        @Override // com.coralline.sea.y4
        public void a(double d) {
        }
    }

    public h9() {
        this.a = m2.d();
        this.b = m2.g();
        this.c = m2.h();
        this.d = m2.i();
        if (this.a == null) {
            this.a = new b();
        }
        if (this.b == null) {
            this.b = new c();
        }
        if (this.c == null) {
            this.c = new d();
        }
        if (this.d == null) {
            this.d = new e();
        }
    }

    public static h9 b() {
        if (e == null) {
            synchronized (h9.class) {
                if (e == null) {
                    e = new h9();
                }
            }
        }
        return e;
    }

    public p4 a() {
        return this.a;
    }

    public u4 c() {
        return this.b;
    }

    public w4 d() {
        return this.c;
    }

    public y4 e() {
        return this.d;
    }
}
