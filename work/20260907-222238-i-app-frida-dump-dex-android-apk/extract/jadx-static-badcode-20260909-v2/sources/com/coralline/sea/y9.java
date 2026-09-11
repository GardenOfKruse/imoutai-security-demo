package com.coralline.sea;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Pair;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class y9 {
    public static y9 d = null;
    public static boolean e = true;
    public static ReentrantLock f = null;
    public static Condition g = null;
    public static final String h = "ratio";
    public static final String i = "count";
    public final Map<Object, Pair<Boolean, CountDownLatch>> a = new ConcurrentHashMap();
    public Handler b = new Handler(Looper.getMainLooper());
    public l4 c = new a();

    public class a implements l4 {
        public a() {
        }

        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
            if (y9.this.a.get(s1Var) == null || ((Boolean) y9.this.a.get(s1Var).first).booleanValue()) {
                return;
            }
            a((Object) s1Var);
        }

        public final void a(Object obj) {
            CountDownLatch countDownLatch = (CountDownLatch) ((Pair) y9.this.a.get(obj)).second;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) {
            a((Object) s1Var);
        }
    }

    public class b implements Runnable {
        public final /* synthetic */ s1 a;

        public b(s1 s1Var) {
            this.a = s1Var;
        }

        @Override // java.lang.Runnable
        public void run() {
            da.a(this.a);
        }
    }

    static {
        ReentrantLock reentrantLock = new ReentrantLock();
        f = reentrantLock;
        g = reentrantLock.newCondition();
    }

    public y9() {
        l9.b().g();
    }

    public static void a() {
        if (n3.a().m) {
            a6.e().a();
        }
        f.lock();
        try {
            e = false;
        } finally {
            f.unlock();
        }
    }

    public static void a(s1 s1Var, long j) throws InterruptedException {
        c().a(s1Var, j, false);
    }

    public static void b() {
        f.lock();
        try {
            e = true;
            g.signalAll();
        } finally {
            f.unlock();
        }
    }

    public static void b(s1 s1Var, long j) throws InterruptedException {
        c().a(s1Var, j, true);
    }

    public static synchronized boolean b(s1 s1Var) {
        return c().a(s1Var);
    }

    public static y9 c() {
        if (d == null) {
            d = new y9();
        }
        return d;
    }

    public static boolean d() {
        String string;
        JSONObject jSONObjectA = a9.a(a9.d, (JSONObject) null);
        if (jSONObjectA == null || TextUtils.isEmpty(jSONObjectA.optString("ratio", c7.c))) {
            return false;
        }
        try {
            string = jSONObjectA.getString("ratio");
            try {
                jSONObjectA.getInt("count");
            } catch (Exception e2) {
            }
        } catch (Exception e3) {
            string = null;
        }
        float f2 = Float.parseFloat(string);
        new Random().nextFloat();
        return f2 == 0.0f;
    }

    public static boolean e() {
        return e;
    }

    public static boolean f() {
        return !n3.a().c;
    }

    public static void g() {
        f.lock();
        while (!e) {
            try {
                g.await();
            } catch (InterruptedException e2) {
            } catch (Throwable th) {
                f.unlock();
                throw th;
            }
        }
        f.unlock();
    }

    public final void a(s1 s1Var, long j, boolean z) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        this.a.put(s1Var, new Pair<>(Boolean.valueOf(z), countDownLatch));
        j1.c(this.c, s1Var.d);
        synchronized (y9.class) {
            a(s1Var);
        }
        boolean zAwait = false;
        try {
            if (j <= 0) {
                countDownLatch.await();
            } else {
                zAwait = countDownLatch.await(j, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e2) {
        } catch (Throwable th) {
            this.a.remove(s1Var);
            j1.d(this.c, s1Var.d);
            throw th;
        }
        this.a.remove(s1Var);
        j1.d(this.c, s1Var.d);
        if (zAwait) {
            while (!j1.b().a()) {
            }
        }
    }

    public final boolean a(s1 s1Var) {
        String str = s1Var.d;
        if (n3.a().m) {
            boolean z = s1Var.e;
        }
        if (n3.T.c) {
            this.b.postDelayed(new b(s1Var), 2000L);
        } else {
            da.a(s1Var);
        }
        boolean zA = true;
        if (n3.T.g) {
            return true;
        }
        if (j6.c() || s1Var.c.equals(e2.c) || s1Var.c.equals(e2.d) || !n3.T.m) {
            zA = n8.c().a(s1Var);
        } else {
            a6.e().c(s1Var);
        }
        if (g9.a.equals(s1Var.d)) {
            t7.a().b();
        }
        return zA;
    }
}
