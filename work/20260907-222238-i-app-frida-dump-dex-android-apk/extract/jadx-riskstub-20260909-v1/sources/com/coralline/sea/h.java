package com.coralline.sea;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import java.util.LinkedList;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class h implements Application.ActivityLifecycleCallbacks {
    public static boolean c = false;
    public static long d;
    public Context a;
    public LinkedList<JSONObject> b;

    public h(Context context, LinkedList<JSONObject> linkedList) {
        this.a = context;
        this.b = linkedList;
    }

    public static long a() {
        return d;
    }

    public static boolean b() {
        return c;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        c = false;
        d = System.currentTimeMillis();
        Window window = activity.getWindow();
        if (window.getCallback().getClass().equals(i1.class)) {
            return;
        }
        window.setCallback(new i1(activity, window.getCallback(), this.b));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        c = true;
        d = System.currentTimeMillis();
    }
}
