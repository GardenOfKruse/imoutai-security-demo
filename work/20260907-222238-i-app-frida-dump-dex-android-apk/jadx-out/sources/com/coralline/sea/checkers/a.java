package com.coralline.sea.checkers;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.coralline.sea.a7;
import com.coralline.sea.a8;
import com.coralline.sea.aa;
import com.coralline.sea.b5;
import com.coralline.sea.b6;
import com.coralline.sea.c0;
import com.coralline.sea.d;
import com.coralline.sea.d6;
import com.coralline.sea.e;
import com.coralline.sea.ea;
import com.coralline.sea.f2;
import com.coralline.sea.f9;
import com.coralline.sea.g2;
import com.coralline.sea.g4;
import com.coralline.sea.g6;
import com.coralline.sea.g8;
import com.coralline.sea.g9;
import com.coralline.sea.h1;
import com.coralline.sea.h5;
import com.coralline.sea.j2;
import com.coralline.sea.j3;
import com.coralline.sea.j4;
import com.coralline.sea.j5;
import com.coralline.sea.j6;
import com.coralline.sea.ja;
import com.coralline.sea.k2;
import com.coralline.sea.k4;
import com.coralline.sea.l0;
import com.coralline.sea.l2;
import com.coralline.sea.l9;
import com.coralline.sea.m5;
import com.coralline.sea.ma;
import com.coralline.sea.n3;
import com.coralline.sea.na;
import com.coralline.sea.o;
import com.coralline.sea.o8;
import com.coralline.sea.p3;
import com.coralline.sea.p8;
import com.coralline.sea.p9;
import com.coralline.sea.q1;
import com.coralline.sea.s5;
import com.coralline.sea.t1;
import com.coralline.sea.u2;
import com.coralline.sea.u5;
import com.coralline.sea.w6;
import com.coralline.sea.x7;
import com.coralline.sea.x9;
import com.coralline.sea.y3;
import com.coralline.sea.y9;
import com.coralline.sea.z1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class a {
    public static a n = null;
    public static boolean o = false;
    public Handler c;
    public CountDownLatch e;
    public CountDownLatch f;
    public int g = 0;
    public final Class<?>[] h = {g9.class, g8.class, u2.class, b5.class, j4.class, p8.class, ea.class, na.class, h1.class};
    public final Class<?>[] i = {a8.class, j3.class, g6.class};
    public final Class<?>[] j = {f2.class, o.class, w6.class, g2.class, ma.class, k4.class, h5.class, d6.class, g4.class, a7.class, com.coralline.sea.b.class, y3.class, c0.class, e.class, d.class, u5.class, l0.class, b6.class, p3.class};
    public HashSet<String> k = new HashSet<>();
    public List<String> l = new ArrayList();
    public final String m = "allInit";
    public LinkedHashMap<String, Checker> a = new LinkedHashMap<>();
    public Map<String, q1> b = new HashMap();
    public ScheduledExecutorService d = Executors.newScheduledThreadPool(2, new ThreadFactoryC0002a());

    /* JADX INFO: renamed from: com.coralline.sea.checkers.a$a, reason: collision with other inner class name */
    public class ThreadFactoryC0002a implements ThreadFactory {
        public final AtomicInteger a = new AtomicInteger(1);

        public ThreadFactoryC0002a() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("Risk-CheckerEngine-" + this.a.getAndIncrement());
            return thread;
        }
    }

    public class b implements Runnable {
        public final /* synthetic */ Checker a;

        public b(Checker checker) {
            this.a = checker;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                String str = this.a.checkerName;
                System.currentTimeMillis();
                this.a.start();
                this.a.check();
                a.this.a(this.a.checkerName, 1);
                String str2 = this.a.checkerName;
                System.currentTimeMillis();
                if (!n3.a().g && (this.a instanceof g9) && g9.o) {
                    a.this.e.countDown();
                }
                Checker checker = this.a;
                if ((checker instanceof o) && ((o) checker).a) {
                    a.this.f.countDown();
                }
            } catch (Throwable th) {
                a.this.a(this.a.checkerName, -1);
            }
        }
    }

    public class c implements Runnable {
        public int a = 0;
        public final /* synthetic */ Checker b;

        public c(Checker checker) {
            this.b = checker;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (!n3.a().g && !l2.g().b().f()) {
                    a.this.e.await();
                }
                int i = this.a + 1;
                this.a = i;
                if (j2.a(this.b, i)) {
                    this.b.checkNoTask();
                    return;
                }
                System.currentTimeMillis();
                if (a.this.f != null && (this.b instanceof g8)) {
                    a.this.f.await(5L, TimeUnit.SECONDS);
                }
                Checker checker = this.b;
                String str = checker.checkerName;
                checker.check();
                a.this.a(this.b.checkerName, 1);
                String str2 = this.b.checkerName;
                System.currentTimeMillis();
            } catch (Throwable th) {
                a.this.a(this.b.checkerName, -1);
            }
        }
    }

    public a() {
        HandlerThread handlerThread = new HandlerThread("Risk-thread-Tasks");
        handlerThread.start();
        this.c = new Handler(handlerThread.getLooper());
        this.e = new CountDownLatch(1);
    }

    @Nullable
    public static a b() {
        return n;
    }

    public static synchronized a c() {
        if (n == null) {
            n = new a();
        }
        return n;
    }

    public final Checker a(Checker checker, q1 q1Var) {
        String str = checker.checkerName;
        try {
            checker.initialize();
        } catch (Throwable th) {
            checker.getName();
            th.getMessage();
        }
        this.a.put(checker.getName(), checker);
        this.b.put(checker.getName(), q1Var);
        c(checker.getName());
        return checker;
    }

    public final Checker a(Class<?> cls) {
        try {
            Checker checker = (Checker) cls.newInstance();
            String lowerCase = checker.getName().toLowerCase();
            if (!t1.b(lowerCase)) {
                x9.a("-211#" + ja.n(lowerCase));
                return null;
            }
            x9.a("211#" + ja.n(lowerCase));
            if (checker instanceof o) {
                this.f = new CountDownLatch(1);
            }
            return a(checker, checker.getDefaultCheckerConfig());
        } catch (Exception e) {
            return null;
        }
    }

    public void a(int i) {
        this.g = i;
    }

    public void a(String str) {
        for (String str2 : this.a.keySet()) {
            if (str2.equals(str)) {
                this.a.get(str2).flush();
            }
        }
    }

    public void a(String str, int i) {
        if (this.k.contains(str)) {
            return;
        }
        this.k.add(str);
        m5.a().a(new Pair(str, Integer.valueOf(i)), m5.b.C0005b.f);
    }

    public void a(boolean z) {
        try {
            if (z) {
                if (!n3.a().g) {
                    o8.b().d();
                    Checker checkerA = a(z1.class);
                    if (checkerA != null) {
                        checkerA.start();
                        checkerA.check();
                        if (!z1.c()) {
                            z1.k();
                        }
                        j5.a().b();
                    }
                    if (!l9.b().d() && y9.e() && l9.b().c()) {
                        if (o8.g) {
                            aa.f().c();
                        } else {
                            aa.f().h();
                        }
                    }
                }
                p9.a();
                e();
            } else if (j6.c()) {
                a(g9.class);
                a(f2.class);
            } else if (!n3.a().g) {
                a(new h5(), q1.a(5, 5));
            }
            g();
            x7.a(n3.a().a, "RiskPM.dex");
        } catch (Exception e) {
        }
    }

    public final void a(Class<?>[]... clsArr) {
        for (Class<?>[] clsArr2 : clsArr) {
            for (Class<?> cls : clsArr2) {
                a(cls);
            }
        }
    }

    public boolean a() {
        HashSet hashSet = new HashSet(this.l);
        return hashSet.size() > 0 && hashSet.size() == this.k.size();
    }

    public Checker b(String str) {
        return this.a.get(str);
    }

    public final void b(Checker checker, q1 q1Var) {
        Integer numA;
        try {
            int i = n3.a().p;
            int i2 = Integer.parseInt(q1Var.a());
            if (i < 0) {
                i = i2;
            }
            int iIntValue = Integer.parseInt(q1Var.b());
            if (n3.T.g && (numA = k2.d().a(checker.checkerName)) != null) {
                iIntValue = numA.intValue();
            }
            String str = checker.checkerName;
            if (iIntValue > 0) {
                StringBuilder sb = new StringBuilder("period ");
                sb.append(iIntValue);
                sb.append(" ");
            }
            if (q1Var.c() == 0) {
                this.c.postDelayed(new b(checker), ((long) i) * 1000);
            } else if (1 == q1Var.c()) {
                if (!checker.checkerName.equals(z1.k)) {
                    try {
                        checker.start();
                    } catch (Throwable th) {
                    }
                }
                this.d.scheduleWithFixedDelay(new c(checker), i, iIntValue, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
        }
    }

    public void c(String str) {
        if (!str.equals("allInit")) {
            this.l.add(str);
        } else {
            this.l.toString();
            m5.a().a(this.l, m5.b.C0005b.g);
        }
    }

    public Handler d() {
        return this.c;
    }

    public final void e() {
        a(n3.a().g ? new Class[][]{this.h, this.i} : new Class[][]{this.h, this.j});
        if (n3.T.d) {
            a(s5.class);
            a(f9.class);
        }
        c("allInit");
    }

    public int f() {
        m5.a().a(Integer.valueOf(this.g), m5.b.C0005b.i);
        return this.g;
    }

    public void g() {
        Objects.toString(this.a.keySet());
        if (j2.b("suspendTriggerJobs")) {
            return;
        }
        for (Checker checker : this.a.values()) {
            b(checker, this.b.get(checker.getName()));
            if (TextUtils.equals(checker.checkerName, g9.a)) {
                o = true;
            }
        }
    }
}
