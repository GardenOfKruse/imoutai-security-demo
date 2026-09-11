package com.coralline.sea;

import android.location.LocationManager;
import android.location.LocationProvider;
import androidx.core.app.ActivityCompat;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class p5 {
    public static LocationProvider a(LocationManager locationManager, String str) {
        if (ActivityCompat.checkSelfPermission(n3.a().a, "android.permission.ACCESS_FINE_LOCATION") == 0 && ActivityCompat.checkSelfPermission(n3.T.a, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            return locationManager.getProvider(str);
        }
        return null;
    }
}
