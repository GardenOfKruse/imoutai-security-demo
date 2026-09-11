package com.coralline.sea;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Debug;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class k8 {

    public static class b {
        public static final k8 a = new k8();
    }

    public k8() {
    }

    public static final k8 b() {
        return b.a;
    }

    public boolean a() {
        return Debug.isDebuggerConnected();
    }

    public boolean a(Context context) {
        return (context.getApplicationInfo().flags & 2) != 0;
    }

    public boolean a(String str, int i) throws UnknownHostException {
        try {
            new ServerSocket(i).close();
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    public boolean b(Context context) {
        Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        return intentRegisterReceiver != null && intentRegisterReceiver.getIntExtra("plugged", -1) == 2;
    }
}
