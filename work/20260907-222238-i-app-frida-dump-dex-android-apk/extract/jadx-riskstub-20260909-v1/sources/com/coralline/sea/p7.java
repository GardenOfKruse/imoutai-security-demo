package com.coralline.sea;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class p7 implements v4 {
    public static ConcurrentHashMap<String, LocationProvider> c = new ConcurrentHashMap<>(20, 0.6f);
    public Handler a = new Handler(Looper.getMainLooper());
    public boolean b = false;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            p7.this.b = true;
        }
    }

    @Override // com.coralline.sea.v4
    public boolean a(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo.getType() == 0) {
                    return activeNetworkInfo.getSubtype() == 4;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    @Override // com.coralline.sea.v4
    public boolean b(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(m1.j);
            if (telephonyManager != null) {
                return telephonyManager.getPhoneType() == 2;
            }
        } catch (Exception e) {
        }
        return false;
    }

    @Override // com.coralline.sea.v4
    public boolean c(Context context) {
        boolean z;
        if (context == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT <= 22) {
            return Settings.Secure.getInt(context.getContentResolver(), "mock_location", 0) != 0;
        }
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            if (!this.b) {
                this.a.postDelayed(new a(), 110000L);
            }
            LocationProvider locationProvider = (c.get("location_provider") == null || this.b) ? null : c.get("location_provider");
            if (locationProvider != null) {
                locationManager.addTestProvider(locationProvider.getName(), locationProvider.requiresNetwork(), locationProvider.requiresSatellite(), locationProvider.requiresCell(), locationProvider.hasMonetaryCost(), locationProvider.supportsAltitude(), locationProvider.supportsSpeed(), locationProvider.supportsBearing(), locationProvider.getPowerRequirement(), locationProvider.getAccuracy());
            } else {
                locationManager.addTestProvider("gps", true, true, false, false, true, true, true, 3, 1);
            }
            locationManager.setTestProviderEnabled("gps", true);
            locationManager.setTestProviderStatus("gps", 2, null, System.currentTimeMillis());
            try {
                locationManager.removeTestProvider("gps");
            } catch (Exception e) {
            }
            z = true;
        } catch (Exception e2) {
            z = false;
        }
        if (!n3.a().g && !z) {
            String[] strArr = {"gps", "network"};
            for (int i = 0; i < 2; i++) {
                try {
                    Location locationA = q5.a(context, strArr[i]);
                    if (locationA != null && locationA.isFromMockProvider()) {
                        return true;
                    }
                } catch (Exception e3) {
                }
            }
        }
        return z;
    }
}
