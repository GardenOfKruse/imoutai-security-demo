package com.coralline.sea;

import android.os.Handler;
import android.os.HandlerThread;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class e6 {
    public String a;
    public Set<String> b;
    public l1 c;
    public Handler d;

    public class a implements Runnable {

        /* JADX INFO: renamed from: com.coralline.sea.e6$a$a, reason: collision with other inner class name */
        public class C0003a implements s6 {
            public C0003a() {
            }

            @Override // com.coralline.sea.s6
            public void a(q4 q4Var, Method method, Object obj) {
                if (m7.a(e6.this.b, method.getName())) {
                    String strA = m7.a(Thread.currentThread().getStackTrace());
                    boolean zA = m7.a(strA, e6.this.c.d());
                    if (!zA) {
                        q4Var.b();
                        method.getName();
                    }
                    if (zA) {
                        return;
                    }
                    String strA2 = m7.a(strA, e6.this.c.c() > 0 ? e6.this.c.c() : 3);
                    y6 y6VarA = m7.a(e6.this.c.b(), strA2);
                    e6.this.c.a();
                    method.getName();
                    Objects.toString(y6VarA);
                    if (y6VarA == y6.release) {
                        return;
                    }
                    ca caVar = new ca();
                    caVar.a = strA2;
                    caVar.b = strA;
                    caVar.d = method.getName();
                    caVar.f = y6VarA.a;
                    caVar.c = e6.this.a;
                    int i = b.a[y6VarA.ordinal()];
                    if (i == 1) {
                        y8.a(caVar);
                        return;
                    }
                    if (i != 2) {
                        return;
                    }
                    y8.a(caVar);
                    SecurityException securityException = new SecurityException("Permission Denied : have no permission for " + method.getName());
                    securityException.setStackTrace(m7.a);
                    throw securityException;
                }
            }
        }

        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            q4 q4VarA = y8.a().a(e6.this.a);
            if (q4VarA != null) {
                String str = e6.this.a;
                Objects.toString(q4VarA.f());
                b0 b0VarC = q4VarA.c();
                if (b0VarC != null) {
                    b0VarC.a(new C0003a());
                }
            }
        }
    }

    public static /* synthetic */ class b {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[y6.values().length];
            a = iArr;
            try {
                iArr[y6.monitor.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[y6.block.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public e6(String str, Set<String> set) {
        this.a = str;
        this.b = set;
        HandlerThread handlerThread = new HandlerThread("service_monitor_bv");
        handlerThread.start();
        this.d = new Handler(handlerThread.getLooper());
    }

    public l1 a() {
        return this.c;
    }

    public void a(l1 l1Var) {
        this.c = l1Var;
    }

    public void a(String str) {
        this.a = str;
    }

    public void a(Set<String> set) {
        this.b = set;
    }

    public Set<String> b() {
        return this.b;
    }

    public String c() {
        return this.a;
    }

    public void d() {
        this.d.post(new a());
    }
}
