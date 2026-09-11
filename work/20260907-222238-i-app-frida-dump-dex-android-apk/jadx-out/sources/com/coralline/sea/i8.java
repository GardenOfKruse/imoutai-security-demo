package com.coralline.sea;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.WindowManager;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class i8 {
    public static final String b = "ScreenSharingSystemApi";
    public static i8 c = null;
    public static volatile boolean d = false;
    public Consumer a;

    public class a implements Consumer<Integer> {
        public a() {
        }

        @Override // java.util.function.Consumer
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void accept(Integer num) {
            boolean unused = i8.d = num.intValue() == 1;
        }
    }

    public i8() {
        if (Build.VERSION.SDK_INT >= 35) {
            this.a = new a();
        }
    }

    public static i8 a() {
        if (c == null) {
            c = new i8();
        }
        return c;
    }

    public static synchronized boolean b() {
        return d;
    }

    public void a(Activity activity) {
        try {
            d = false;
            if (Build.VERSION.SDK_INT >= 35) {
                WindowManager windowManager = activity.getWindowManager();
                windowManager.getClass().getDeclaredMethod("addScreenRecordingCallback", Executor.class, Consumer.class).invoke(windowManager, activity.getMainExecutor(), this.a);
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

    public void a(Context context) {
        if (context == null) {
            return;
        }
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager == null || Build.VERSION.SDK_INT < 35) {
                return;
            }
            boolean z = false;
            Integer num = (Integer) windowManager.getClass().getDeclaredMethod("addScreenRecordingCallback", Executor.class, Consumer.class).invoke(windowManager, context.getMainExecutor(), this.a);
            if (num != null && num.intValue() == 1) {
                z = true;
            }
            d = z;
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

    public void b(Activity activity) {
        try {
            d = false;
            if (Build.VERSION.SDK_INT >= 35) {
                WindowManager windowManager = activity.getWindowManager();
                activity.getMainExecutor();
                windowManager.getClass().getDeclaredMethod("removeScreenRecordingCallback", Consumer.class).invoke(windowManager, this.a);
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }
}
