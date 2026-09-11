package com.coralline.sea;

import android.content.Context;
import android.os.Build;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class j {
    public static final String a = "AndroidCompat";

    public static int a(Context context, String str) {
        if (str != null) {
            return (Build.VERSION.SDK_INT < 23 || context.getApplicationInfo().targetSdkVersion < 23) ? context.getPackageManager().checkPermission(str, context.getPackageName()) : context.checkSelfPermission(str);
        }
        throw new IllegalArgumentException("permission is null");
    }
}
