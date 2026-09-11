package com.coralline.sea;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class q5 extends c7 {
    public static boolean i = false;
    public static JSONObject j = c7.f;
    public static Location k;
    public static Location l;
    public static List<CellInfo> m;

    static {
        Location location = c7.g;
        k = location;
        l = location;
        m = c7.h;
    }

    public static Location a(Context context, String str) {
        return a(context, str, true);
    }

    public static synchronized Location a(Context context, String str, boolean z) {
        if ("gps".equals(str)) {
            if (c7.c(d2.g)) {
                Location locationA = w3.b().a(context, str);
                k = locationA;
                return locationA;
            }
            if (z) {
                return k;
            }
        } else if ("network".equals(str)) {
            if (c7.c(d2.g)) {
                Location locationA2 = w3.b().a(context, str);
                l = locationA2;
                return locationA2;
            }
            if (z) {
                return l;
            }
        }
        return c7.g;
    }

    public static List<CellInfo> a(TelephonyManager telephonyManager) {
        return a(telephonyManager, true);
    }

    @SuppressLint({"MissingPermission"})
    public static synchronized List<CellInfo> a(TelephonyManager telephonyManager, boolean z) {
        if (c7.b(d2.y) && ja.e("android.permission.ACCESS_FINE_LOCATION")) {
            List<CellInfo> allCellInfo = telephonyManager.getAllCellInfo();
            m = allCellInfo;
            return allCellInfo;
        }
        if (z) {
            return m;
        }
        return c7.h;
    }

    public static JSONObject a() {
        return a(true);
    }

    public static synchronized JSONObject a(boolean z) {
        boolean zB = c7.b(d2.i);
        boolean zB2 = c7.b(d2.h);
        if (zB && zB2) {
            JSONObject jSONObjectA = w3.b().a();
            j = jSONObjectA;
            return c7.a(jSONObjectA);
        }
        if (z) {
            return c7.a(j);
        }
        return new JSONObject();
    }

    public static boolean a(Context context) {
        return a(context, true);
    }

    public static synchronized boolean a(Context context, boolean z) {
        boolean zB = c7.b(d2.p);
        boolean zB2 = c7.b(d2.q);
        boolean zB3 = c7.b(d2.r);
        boolean zB4 = c7.b(d2.s);
        if (zB && zB2 && zB3 && zB4) {
            boolean zC = a5.a().b().c(context);
            i = zC;
            return zC;
        }
        if (!z) {
            return false;
        }
        return i;
    }
}
