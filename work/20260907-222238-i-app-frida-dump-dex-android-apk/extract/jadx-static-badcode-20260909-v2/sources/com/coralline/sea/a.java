package com.coralline.sea;

import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class a extends Thread {
    public static final int m = 5000;
    public static final g n = new C0000a();
    public static final f o = new b();
    public static final h p = new c();
    public g a;
    public f b;
    public h c;
    public final Handler d;
    public final int e;
    public String f;
    public boolean g;
    public int h;
    public boolean i;
    public volatile long j;
    public volatile boolean k;
    public final Runnable l;

    /* JADX INFO: renamed from: com.coralline.sea.a$a, reason: collision with other inner class name */
    public class C0000a implements g {
        @Override // com.coralline.sea.a.g
        public void a(String str) {
        }
    }

    public class b implements f {
        @Override // com.coralline.sea.a.f
        public long a(long j) {
            return 0L;
        }
    }

    public class c implements h {
        @Override // com.coralline.sea.a.h
        public void a(InterruptedException interruptedException) {
        }
    }

    public class d implements Runnable {
        public d() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a.this.j = 0L;
            a.this.k = false;
        }
    }

    public class e implements Comparator<Thread> {
        public final /* synthetic */ Thread a;

        public e(Thread thread) {
            this.a = thread;
        }

        @Override // java.util.Comparator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(Thread thread, Thread thread2) {
            if (thread == thread2) {
                return 0;
            }
            Thread thread3 = this.a;
            if (thread == thread3) {
                return 1;
            }
            if (thread2 == thread3) {
                return -1;
            }
            return thread2.getName().compareTo(thread.getName());
        }
    }

    public interface f {
        long a(long j);
    }

    public interface g {
        void a(String str);
    }

    public interface h {
        void a(InterruptedException interruptedException);
    }

    public a() {
        this(m);
    }

    public a(int i) {
        this.a = n;
        this.b = o;
        this.c = p;
        this.d = new Handler(Looper.getMainLooper());
        this.f = c7.c;
        this.g = false;
        this.h = Integer.MAX_VALUE;
        this.i = false;
        this.j = 0L;
        this.k = false;
        this.l = new d();
        this.e = i;
    }

    public int a() {
        return this.e;
    }

    public a a(int i) {
        this.h = i;
        return this;
    }

    public a a(f fVar) {
        if (fVar == null) {
            fVar = o;
        }
        this.b = fVar;
        return this;
    }

    public a a(g gVar) {
        if (gVar == null) {
            gVar = n;
        }
        this.a = gVar;
        return this;
    }

    public a a(h hVar) {
        if (hVar == null) {
            hVar = p;
        }
        this.c = hVar;
        return this;
    }

    public a a(String str) {
        if (str == null) {
            str = c7.c;
        }
        this.f = str;
        return this;
    }

    public a a(boolean z) {
        this.i = z;
        return this;
    }

    public String a(long j) {
        return "Application Not Responding, " + j + " ms." + x9.a(Looper.getMainLooper().getThread());
    }

    public String a(long j, String str, boolean z) {
        Thread thread = Looper.getMainLooper().getThread();
        TreeMap treeMap = new TreeMap(new e(thread));
        int i = 0;
        for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
            int i2 = i + 1;
            if (i < this.h && entry.getKey() != thread && entry.getKey().getName().startsWith(str) && (z || entry.getValue().length > 0)) {
                treeMap.put(entry.getKey(), entry.getValue());
            }
            i = i2;
        }
        StringBuilder sb = new StringBuilder("Application Not Responding: At least ");
        sb.append(j);
        sb.append(" ms.\n");
        sb.append(x9.a(thread));
        for (Map.Entry entry2 : treeMap.entrySet()) {
            sb.append("\n");
            sb.append(x9.a((Thread) entry2.getKey()));
        }
        return sb.toString();
    }

    public a b() {
        this.f = null;
        return this;
    }

    public a b(boolean z) {
        this.g = z;
        return this;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        setName("anr_monitor");
        long jA = this.e;
        while (!isInterrupted()) {
            boolean z = this.j == 0;
            this.j += jA;
            if (z) {
                this.d.post(this.l);
            }
            try {
                Thread.sleep(jA);
                if (this.j != 0 && !this.k) {
                    if (this.i || (!Debug.isDebuggerConnected() && !Debug.waitingForDebugger())) {
                        jA = this.b.a(this.j);
                        if (jA <= 0) {
                            try {
                                this.a.a(this.f != null ? a(this.j, this.f, this.g) : a(this.j));
                            } catch (Exception e2) {
                            }
                            jA = this.e;
                        }
                    }
                    this.k = true;
                }
            } catch (InterruptedException e3) {
                this.c.a(e3);
                return;
            }
        }
    }
}
