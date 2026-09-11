package com.coralline.sea;

import android.os.SystemClock;
import android.view.MotionEvent;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class m {
    public static final int d = 3;
    public static final float e = 1.0f;
    public static final float f = 0.001f;
    public float a = -1.0f;
    public int b = 0;
    public long c = 0;

    public final void a() {
        this.b = 0;
        this.a = -1.0f;
    }

    public boolean a(MotionEvent motionEvent) {
        float size = motionEvent.getSize();
        float pressure = motionEvent.getPressure();
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (jElapsedRealtime - this.c < 50) {
            return true;
        }
        this.c = jElapsedRealtime;
        if (Float.compare(pressure, 1.0f) != 0) {
            a();
            return false;
        }
        if (Math.abs(size - this.a) < 0.001f) {
            int i = this.b + 1;
            this.b = i;
            if (i >= 3) {
                a();
                return true;
            }
        } else {
            a();
        }
        this.a = size;
        return false;
    }
}
