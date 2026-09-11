package com.coralline.sea;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class a6 {
    public static final int e = 1;
    public static final int f = 2;
    public static final int g = 3;
    public static final int h = 4;
    public static final int i = 5;
    public static final int j = 0;
    public static a6 k;
    public Handler c;
    public ArrayList<s1> a = new ArrayList<>();
    public boolean d = true;
    public Set<s1> b = new LinkedHashSet();

    public class a implements Handler.Callback {
        public a() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            try {
                switch (message.what) {
                    case 1:
                        a6.this.c();
                        break;
                    case 2:
                        a6.this.d();
                        break;
                    case 3:
                        a6.this.b((s1) message.obj);
                        break;
                    case 4:
                        a6.this.a((s1) message.obj);
                        break;
                    case 5:
                        a6.this.b();
                        break;
                }
                return true;
            } catch (Exception e) {
                return true;
            }
        }
    }

    public class b implements l4 {
        public b() {
        }

        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) {
            if (!n3.a().E) {
                String str = s1Var.d;
            } else if (!s1Var.e) {
                return;
            }
            Handler handler = a6.this.c;
            handler.sendMessage(Message.obtain(handler, 3, s1Var));
        }
    }

    public class c implements Observer {
        public c() {
        }

        @Override // java.util.Observer
        public void update(Observable observable, Object obj) {
            if (n3.a().E && obj == n8.e) {
                a6.this.c.sendEmptyMessage(2);
            }
        }
    }

    public a6() {
        HandlerThread handlerThread = new HandlerThread("messageHandler");
        handlerThread.start();
        this.c = new Handler(handlerThread.getLooper(), new a());
        j1.c(new b(), null);
        if (n3.a().m) {
            n8.c().addObserver(new c());
        }
    }

    public static synchronized a6 e() {
        if (k == null) {
            k = new a6();
        }
        return k;
    }

    public void a() {
        this.c.sendEmptyMessage(5);
    }

    public final void a(s1 s1Var) {
        boolean z = s1Var.e;
        s1Var.g();
        if (s1Var.e && !s1Var.g()) {
            this.b.add(s1Var);
            this.b.size();
            if (!this.c.hasMessages(1) || this.b.size() < 10) {
                this.c.removeMessages(1);
                this.c.sendEmptyMessageDelayed(1, 0L);
            }
        }
        this.a.add(s1Var);
        if (n3.a().E) {
            this.c.sendEmptyMessage(2);
        }
    }

    public final void b() {
        this.b.clear();
        this.a.clear();
        this.c.removeMessages(4);
        this.c.removeMessages(1);
        this.c.removeMessages(2);
        r1.e().c();
    }

    public final void b(s1 s1Var) {
        try {
            String str = s1Var.d;
            this.b.remove(s1Var);
            if (!n3.a().E || s1Var.g()) {
                r1.e().a(s1Var);
            }
        } catch (Exception e2) {
        }
    }

    public final void c() {
        if (this.b.isEmpty()) {
            return;
        }
        for (s1 s1Var : this.b) {
            String str = s1Var.d;
            s1Var.g();
            if (!s1Var.g() && r1.e().b(s1Var)) {
                s1Var.c(true);
                this.b.remove(s1Var);
            }
        }
    }

    public void c(s1 s1Var) {
        Handler handler = this.c;
        handler.sendMessage(Message.obtain(handler, 4, s1Var));
    }

    public final void d() {
        Iterator<s1> it = this.a.iterator();
        while (it.hasNext()) {
            n8.c().b(it.next());
        }
        this.a.clear();
    }
}
