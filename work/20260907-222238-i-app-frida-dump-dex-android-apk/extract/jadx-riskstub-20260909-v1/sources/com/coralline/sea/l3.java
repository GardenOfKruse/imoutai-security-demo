package com.coralline.sea;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class l3 extends Handler {
    public static final String b = "hijack";
    public static final int c = 100;
    public static final int d = 101;
    public t3 a;

    public l3() {
        super(Looper.getMainLooper());
    }

    public void a() {
        b();
        removeMessages(100);
        removeMessages(101);
        sendEmptyMessageDelayed(101, 0L);
    }

    public final void b() {
        t3 t3Var = this.a;
        if (t3Var != null) {
            t3Var.b();
        }
    }

    public void c() {
        removeMessages(100);
        removeMessages(101);
        sendEmptyMessageDelayed(100, 100L);
    }

    public final void d() {
        if (this.a == null) {
            this.a = new t3(l5.a().c);
        }
        this.a.a(w6.e);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (message.what == 100) {
            d();
        } else {
            b();
        }
    }
}
