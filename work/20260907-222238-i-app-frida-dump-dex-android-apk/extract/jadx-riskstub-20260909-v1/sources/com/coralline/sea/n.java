package com.coralline.sea;

import android.app.Activity;
import android.os.Build;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class n {
    public static n e;
    public List<a> a;
    public Timer b;
    public l3 c = new l3();
    public String d;

    public class a extends TimerTask {
        public Activity a;
        public boolean b = true;

        /* JADX INFO: renamed from: com.coralline.sea.n$a$a, reason: collision with other inner class name */
        public class RunnableC0006a implements Runnable {
            public RunnableC0006a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (a.this.b) {
                    boolean z = w6.c;
                    boolean z2 = w6.d;
                    if (w6.c && w6.d) {
                        n.this.c.c();
                        a aVar = a.this;
                        n.this.a.remove(aVar);
                    }
                }
            }
        }

        public a(Activity activity) {
            this.a = activity;
        }

        public void a(boolean z) {
            this.b = z;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            this.a.runOnUiThread(new RunnableC0006a());
        }
    }

    public n() {
        this.d = c7.c;
        this.b = null;
        this.a = null;
        this.a = new ArrayList();
        this.b = new Timer();
        this.d = c7.c;
    }

    public static n a() {
        if (e == null) {
            e = new n();
        }
        return e;
    }

    public void a(Activity activity) {
        if (Build.VERSION.SDK_INT < 33) {
            a aVar = new a(activity);
            this.a.add(aVar);
            this.b.schedule(aVar, 1000L);
        } else if (w6.c && w6.d) {
            b(activity);
        }
    }

    public void b() {
        if (this.a.size() > 0) {
            this.a.get(r0.size() - 1).a(false);
            this.a.remove(r0.size() - 1);
        }
    }

    public final void b(Activity activity) {
        Toast toastMakeText = Toast.makeText(activity, w6.e, 1);
        toastMakeText.setGravity(w6.g, 0, 0);
        toastMakeText.show();
    }
}
