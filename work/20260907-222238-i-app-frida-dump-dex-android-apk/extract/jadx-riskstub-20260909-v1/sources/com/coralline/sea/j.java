package com.coralline.sea;

import android.content.Context;
import android.os.Build;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class j {
    public static final String a = "AndroidCompat";

    public static int a(Context context, String str) {
        if (str != null) {
            return (Build.VERSION.SDK_INT < 23 || context.getApplicationInfo().targetSdkVersion < 23) ? context.getPackageManager().checkPermission(str, context.getPackageName()) : context.checkSelfPermission(str);
        }
        throw new IllegalArgumentException("permission is null");
    }
}
