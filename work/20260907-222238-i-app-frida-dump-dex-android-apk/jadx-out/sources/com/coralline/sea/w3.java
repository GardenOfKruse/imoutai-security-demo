package com.coralline.sea;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import java.util.LinkedList;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class w3 {
    public static w3 a = null;
    public static final int b = 120000;

    public static synchronized w3 b() {
        if (a == null) {
            a = new w3();
        }
        return a;
    }

    public Location a(Context context, String str) {
        if (!b(context, str)) {
            return null;
        }
        try {
            return ((LocationManager) context.getSystemService("location")).getLastKnownLocation(str);
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject a() {
        try {
            Location locationA = a(n3.a().a);
            if (locationA == null) {
                if (b(n3.T.a, "gps") || b(n3.T.a, "network")) {
                    return null;
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("longitude", -1.1d);
                jSONObject.put("latitude", -1.1d);
                return jSONObject;
            }
            double longitude = locationA.getLongitude();
            double latitude = locationA.getLatitude();
            if (longitude == 0.0d && latitude == 0.0d) {
                return null;
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("longitude", locationA.getLongitude());
            jSONObject2.put("latitude", locationA.getLatitude());
            return jSONObject2;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean a(Location location, Location location2) {
        if (location2 == null || location == null) {
            return true;
        }
        long time = location.getTime() - location2.getTime();
        boolean z = time > 120000;
        boolean z2 = time < -120000;
        boolean z3 = time > 0;
        if (z) {
            return true;
        }
        if (z2) {
            return false;
        }
        int accuracy = (int) (location.getAccuracy() - location2.getAccuracy());
        boolean z4 = accuracy > 0;
        boolean z5 = accuracy < 0;
        boolean z6 = accuracy > 200;
        boolean zA = a(location.getProvider(), location2.getProvider());
        if (z5) {
            return true;
        }
        if (!z3 || z4) {
            return z3 && !z6 && zA;
        }
        return true;
    }

    public final boolean a(String str) {
        if ("gps".equals(str)) {
            return ja.e("android.permission.ACCESS_FINE_LOCATION");
        }
        if ("network".equals(str)) {
            return ja.e("android.permission.ACCESS_FINE_LOCATION") || ja.e("android.permission.ACCESS_COARSE_LOCATION");
        }
        return false;
    }

    public final boolean a(String str, String str2) {
        return str == null ? str2 == null : str.equals(str2);
    }

    public boolean b(Context context, String str) {
        if (ja.t() && context.getApplicationInfo().targetSdkVersion > 28 && !j2.a()) {
            return a(str) && ja.e("android.permission.ACCESS_BACKGROUND_LOCATION");
        }
        return a(str);
    }

    public Location a(Context context) {
        Object obj;
        try {
            LinkedList linkedList = new LinkedList();
            Location locationA = q5.a(context, "network");
            if (locationA != null) {
                linkedList.add(locationA);
            }
            Location locationA2 = q5.a(context, "gps", true);
            if (locationA2 != null) {
                linkedList.add(locationA2);
            }
            int size = linkedList.size();
            if (size == 0) {
                return null;
            }
            if (size == 1) {
                obj = linkedList.get(0);
            } else {
                if (size != 2) {
                    return null;
                }
                obj = a((Location) linkedList.get(0), (Location) linkedList.get(1)) ? linkedList.get(0) : linkedList.get(1);
            }
            return (Location) obj;
        } catch (SecurityException e) {
            e.getMessage();
            return null;
        }
    }
}
