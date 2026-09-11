package com.coralline.sea;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class k7 extends Instrumentation {
    public static final String d = "hijack";
    public static k7 e;
    public boolean a = false;
    public Instrumentation b;
    public int c;

    public k7(Instrumentation instrumentation) {
        Class<?> cls;
        int i;
        this.c = 0;
        this.b = instrumentation;
        try {
            cls = Class.forName("com.alipay.mobile.quinox.activity.QuinoxInstrumentation");
        } catch (ClassNotFoundException e2) {
            cls = null;
        }
        if (this.b.getClass() == Instrumentation.class) {
            i = 1;
        } else if (this.b.getClass() != cls) {
            return;
        } else {
            i = 2;
        }
        this.c = i;
    }

    public static k7 a() {
        return e;
    }

    public static void a(Activity activity) {
        a(activity, f());
    }

    public static void a(boolean z) {
        k7 k7Var = e;
        if (k7Var != null) {
            k7Var.b(z);
        }
    }

    public static boolean a(Activity activity, k7 k7Var) {
        if (k7Var == null) {
            return false;
        }
        try {
            if (((Instrumentation) r7.a((Object) activity).c("mInstrumentation").c()) instanceof k7) {
                return true;
            }
            r7.a((Object) activity).a("mInstrumentation", k7Var);
            return true;
        } catch (Exception e2) {
            e2.getMessage();
            return false;
        }
    }

    public static boolean c() {
        return !d();
    }

    public static boolean d() {
        k7 k7Var = e;
        if (k7Var != null) {
            return k7Var.b();
        }
        return false;
    }

    public static void e() {
        f();
    }

    public static k7 f() {
        try {
            Object objC = r7.j("android.app.ActivityThread").b("currentActivityThread").c();
            Instrumentation instrumentation = (Instrumentation) r7.a(objC).c("mInstrumentation").c();
            instrumentation.getClass().toString();
            if (instrumentation instanceof k7) {
                return (k7) instrumentation;
            }
            k7 k7Var = new k7(instrumentation);
            e = k7Var;
            r7.a(objC).a("mInstrumentation", k7Var);
            return k7Var;
        } catch (Exception e2) {
            e2.getMessage();
            return null;
        }
    }

    public Instrumentation.ActivityResult a(Context context, IBinder iBinder, IBinder iBinder2, Activity activity, Intent intent, int i, Bundle bundle, UserHandle userHandle) {
        try {
            return (Instrumentation.ActivityResult) r7.a(this.b).a("execStartActivity", context, iBinder, iBinder2, activity, intent, Integer.valueOf(i), bundle, userHandle).c();
        } catch (Throwable th) {
            return null;
        }
    }

    public Instrumentation.ActivityResult a(Context context, IBinder iBinder, IBinder iBinder2, String str, Intent intent, int i, Bundle bundle) {
        if (str != null) {
            try {
                if (("@android:requestPermissions:".equals(str) || str.startsWith("@android:requestPermissions:android:fragment:")) && intent != null && "android.content.pm.action.REQUEST_PERMISSIONS".equals(intent.getAction())) {
                    this.a = true;
                }
            } catch (Throwable th) {
                th.printStackTrace();
                return null;
            }
        }
        return (Instrumentation.ActivityResult) r7.a(this.b).a("execStartActivity", context, iBinder, iBinder2, str, intent, Integer.valueOf(i), bundle).c();
    }

    public void b(boolean z) {
        this.a = z;
    }

    public boolean b() {
        return this.a;
    }

    @Override // android.app.Instrumentation
    public void callActivityOnCreate(Activity activity, Bundle bundle) {
        if (g()) {
            super.callActivityOnCreate(activity, bundle);
        } else {
            this.b.callActivityOnCreate(activity, bundle);
        }
    }

    @Override // android.app.Instrumentation
    public void callActivityOnDestroy(Activity activity) {
        try {
            if (g()) {
                super.callActivityOnDestroy(activity);
            } else {
                this.b.callActivityOnDestroy(activity);
            }
        } catch (Throwable th) {
        }
    }

    @Override // android.app.Instrumentation
    public void callActivityOnNewIntent(Activity activity, Intent intent) {
        if (g()) {
            super.callActivityOnNewIntent(activity, intent);
        } else {
            this.b.callActivityOnNewIntent(activity, intent);
        }
    }

    @Override // android.app.Instrumentation
    public void callActivityOnPause(Activity activity) {
        if (g()) {
            super.callActivityOnPause(activity);
        } else {
            this.b.callActivityOnPause(activity);
        }
    }

    @Override // android.app.Instrumentation
    public void callActivityOnPostCreate(Activity activity, Bundle bundle) {
        if (g()) {
            super.callActivityOnPostCreate(activity, bundle);
        } else {
            this.b.callActivityOnPostCreate(activity, bundle);
        }
    }

    @Override // android.app.Instrumentation
    public void callActivityOnRestart(Activity activity) {
        if (g()) {
            super.callActivityOnRestart(activity);
        } else {
            this.b.callActivityOnRestart(activity);
        }
    }

    @Override // android.app.Instrumentation
    public void callActivityOnRestoreInstanceState(Activity activity, Bundle bundle) {
        if (g()) {
            super.callActivityOnRestoreInstanceState(activity, bundle);
        } else {
            this.b.callActivityOnRestoreInstanceState(activity, bundle);
        }
    }

    @Override // android.app.Instrumentation
    public void callActivityOnResume(Activity activity) {
        if (g()) {
            super.callActivityOnResume(activity);
        } else {
            this.b.callActivityOnResume(activity);
        }
    }

    @Override // android.app.Instrumentation
    public void callActivityOnSaveInstanceState(Activity activity, Bundle bundle) {
        if (g()) {
            super.callActivityOnSaveInstanceState(activity, bundle);
        } else {
            this.b.callActivityOnSaveInstanceState(activity, bundle);
        }
    }

    @Override // android.app.Instrumentation
    public void callActivityOnStart(Activity activity) {
        if (g()) {
            super.callActivityOnStart(activity);
        } else {
            this.b.callActivityOnStart(activity);
        }
    }

    @Override // android.app.Instrumentation
    public void callActivityOnStop(Activity activity) {
        if (g()) {
            super.callActivityOnStop(activity);
        } else {
            this.b.callActivityOnStop(activity);
        }
    }

    @Override // android.app.Instrumentation
    public void callActivityOnUserLeaving(Activity activity) {
        if (g()) {
            super.callActivityOnUserLeaving(activity);
        } else {
            this.b.callActivityOnUserLeaving(activity);
        }
    }

    @Override // android.app.Instrumentation
    public void callApplicationOnCreate(Application application) {
        if (g()) {
            super.callApplicationOnCreate(application);
        } else {
            this.b.callApplicationOnCreate(application);
        }
    }

    public final boolean g() {
        return this.c == 1;
    }

    @Override // android.app.Instrumentation
    public boolean invokeContextMenuAction(Activity activity, int i, int i2) {
        return g() ? super.invokeContextMenuAction(activity, i, i2) : this.b.invokeContextMenuAction(activity, i, i2);
    }

    @Override // android.app.Instrumentation
    public Activity newActivity(ClassLoader classLoader, String str, Intent intent) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return this.b.newActivity(classLoader, str, intent);
    }

    @Override // android.app.Instrumentation
    public Application newApplication(ClassLoader classLoader, String str, Context context) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return g() ? super.newApplication(classLoader, str, context) : this.b.newApplication(classLoader, str, context);
    }

    @Override // android.app.Instrumentation
    public boolean onException(Object obj, Throwable th) {
        return g() ? super.onException(obj, th) : this.b.onException(obj, th);
    }
}
