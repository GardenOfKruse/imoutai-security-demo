package com.coralline.sea;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import java.util.Objects;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class m6 {
    public static final String a = "NetworkUtil";
    public static ConnectivityManager.NetworkCallback b;
    public static final Object c = new Object();

    public class a extends ConnectivityManager.NetworkCallback {
        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(@NonNull Network network) {
            try {
                super.onAvailable(network);
                Objects.toString(network);
                synchronized (m6.c) {
                    m6.c.notifyAll();
                }
            } catch (Throwable th) {
            }
        }
    }

    public static void a(long j) {
        try {
            System.currentTimeMillis();
            Object obj = c;
            synchronized (obj) {
                obj.wait(j);
            }
            System.currentTimeMillis();
        } catch (Throwable th) {
        }
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static void a(@Nullable Context context) {
        if (context != null && b == null && Build.VERSION.SDK_INT >= 21) {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                NetworkRequest networkRequestBuild = new NetworkRequest.Builder().addCapability(12).addTransportType(1).addTransportType(0).build();
                a aVar = new a();
                b = aVar;
                connectivityManager.registerNetworkCallback(networkRequestBuild, aVar);
            } catch (Throwable th) {
            }
        }
    }
}
