package com.coralline.sea;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class l5 {
    public static final String g = "hijack";
    public static l5 h = new l5();
    public static AtomicBoolean i = new AtomicBoolean(false);
    public static AtomicBoolean j = new AtomicBoolean(false);
    public static AtomicLong k = new AtomicLong(0);
    public Context c;
    public volatile Stack<Activity> a = new Stack<>();
    public int b = 0;
    public final Set<Activity> d = new HashSet();
    public final AtomicBoolean e = new AtomicBoolean(false);
    public boolean f = true;

    public class a implements Application.ActivityLifecycleCallbacks {
        public a() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
            l5.this.d.size();
            l5.i.get();
            Objects.toString(activity);
            l5.h.a(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            l5.this.d.size();
            l5.i.get();
            Objects.toString(activity);
            l5.h.b(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            if (!l5.this.e.get()) {
                l5.this.e.set(true);
            }
            l5.this.d.size();
            l5.i.get();
            Objects.toString(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            l5.this.d.add(activity);
            if (!l5.i.get()) {
                l5.this.d.size();
                l5.i.get();
                Objects.toString(activity);
            } else {
                l5.i.set(false);
                l5.this.d.size();
                l5.i.get();
                Objects.toString(activity);
                n.a().b();
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            l5.this.d.size();
            l5.i.get();
            Objects.toString(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            l5.this.d.add(activity);
            if (l5.i.get()) {
                l5.i.set(false);
                l5.this.d.size();
                l5.i.get();
                Objects.toString(activity);
                n.a().b();
            } else {
                l5.this.d.size();
                l5.i.get();
                Objects.toString(activity);
            }
            e0.a(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            l5.this.d.remove(activity);
            boolean zC = k7.c();
            if (l5.this.d.isEmpty() && zC && l5.this.e.get()) {
                l5.i.set(true);
                l5.k.set(System.currentTimeMillis());
                l5.this.d.size();
                l5.i.get();
                Objects.toString(activity);
                n.a().a(activity);
            } else {
                l5.this.d.size();
                l5.i.get();
                l5.this.e.get();
                Objects.toString(activity);
            }
            k7.a(false);
        }
    }

    public l5() {
        o9.e();
    }

    public static l5 a() {
        return h;
    }

    public final void a(Activity activity) {
        activity.getLocalClassName();
        this.a.add(activity);
    }

    public void a(Context context) {
        Application application = (Application) context.getApplicationContext();
        this.c = application;
        application.registerActivityLifecycleCallbacks(new a());
        j.set(true);
    }

    public final void b(Activity activity) {
        if (this.a == null || this.a.size() <= 0 || activity == null) {
            return;
        }
        this.a.remove(activity);
    }
}
