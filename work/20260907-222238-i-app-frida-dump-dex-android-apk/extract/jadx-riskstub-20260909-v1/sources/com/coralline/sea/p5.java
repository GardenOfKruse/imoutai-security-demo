package com.coralline.sea;

import android.location.LocationManager;
import android.location.LocationProvider;
import androidx.core.app.ActivityCompat;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class p5 {
    public static LocationProvider a(LocationManager locationManager, String str) {
        if (ActivityCompat.checkSelfPermission(n3.a().a, "android.permission.ACCESS_FINE_LOCATION") == 0 && ActivityCompat.checkSelfPermission(n3.T.a, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            return locationManager.getProvider(str);
        }
        return null;
    }
}
