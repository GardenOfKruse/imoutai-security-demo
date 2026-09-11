package com.coralline.sea;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class l6 extends c7 {
    public static ConcurrentHashMap<String, WifiInfo> i = new ConcurrentHashMap<>(20, 0.6f);
    public static ConcurrentHashMap<String, String> j = new ConcurrentHashMap<>(20, 0.6f);
    public static String k = c7.c;
    public static String l = c7.c;
    public static WifiInfo m = null;
    public static pa n = new pa();
    public static JSONObject o;
    public static c6 p;
    public static JSONObject q;
    public static String r;
    public static String s;
    public static String t;
    public static NetworkInfo u;
    public static NetworkInfo v;
    public static NetworkInfo w;
    public static JSONObject x;

    static {
        JSONObject jSONObject = c7.f;
        o = jSONObject;
        p = new c6();
        q = jSONObject;
        r = c7.c;
        s = c7.c;
        t = c7.c;
        u = null;
        v = null;
        w = null;
        x = jSONObject;
    }

    public static NetworkInfo a(ConnectivityManager connectivityManager, int i2) {
        return a(connectivityManager, i2, true);
    }

    public static synchronized NetworkInfo a(ConnectivityManager connectivityManager, int i2, boolean z) {
        NetworkInfo networkInfo;
        if (!c7.c(d2.a)) {
            if (z && (networkInfo = w) != null && i2 == networkInfo.getType() && w.isConnected()) {
                return w;
            }
            return null;
        }
        if (ja.r("android.permission.ACCESS_NETWORK_STATE") != 0) {
            return null;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        w = activeNetworkInfo;
        if (activeNetworkInfo != null && i2 == activeNetworkInfo.getType() && w.isConnected()) {
            return w;
        }
        return null;
    }

    public static String a() {
        return a(true);
    }

    public static synchronized String a(boolean z) {
        if (!c7.b(d2.m)) {
            if (!z) {
                return c7.c;
            }
            return s;
        }
        if (j.get("blue_tooth_mac") != null) {
            s = j.get("blue_tooth_mac");
        } else {
            String strA = w2.b().a();
            s = strA;
            j.put("blue_tooth_mac", strA);
        }
        return s;
    }

    public static JSONObject a(Context context) {
        return a(context, true);
    }

    public static synchronized JSONObject a(Context context, boolean z) {
        if (c7.c(d2.v)) {
            JSONObject jSONObjectA = k1.a().a(context);
            x = jSONObjectA;
            return c7.a(jSONObjectA);
        }
        if (z) {
            return c7.a(x);
        }
        return new JSONObject();
    }

    public static String b() {
        return b(true);
    }

    public static String b(Context context) {
        return b(context, true);
    }

    public static synchronized String b(Context context, boolean z) {
        if (c7.b(d2.f)) {
            String strB = k6.a().b(context);
            t = strB;
            return strB;
        }
        if (!z) {
            return c7.c;
        }
        return t;
    }

    public static synchronized String b(boolean z) {
        if (c7.b(d2.l)) {
            String strD = w2.b().d();
            r = strD;
            return strD;
        }
        if (!z) {
            return c7.c;
        }
        return r;
    }

    public static JSONObject c() {
        return c(true);
    }

    public static synchronized JSONObject c(boolean z) {
        boolean zB = c7.b(d2.z);
        boolean zB2 = c7.b(d2.w);
        boolean zB3 = c7.b(d2.x);
        boolean zB4 = c7.b(d2.A);
        if (zB && zB2 && zB3 && zB4) {
            JSONObject jSONObjectB = p.b();
            q = jSONObjectB;
            return c7.a(jSONObjectB);
        }
        if (z) {
            return c7.a(q);
        }
        return new JSONObject();
    }

    public static String d() {
        return d(true);
    }

    public static synchronized String d(boolean z) {
        if (c7.b(d2.d)) {
            String strC = k6.a().c();
            l = strC;
            return strC;
        }
        if (!z) {
            return c7.c;
        }
        return l;
    }

    public static WifiInfo e() {
        return e(true);
    }

    public static synchronized WifiInfo e(boolean z) {
        boolean zB = c7.b(d2.b);
        boolean zB2 = c7.b(d2.e);
        if (!zB || !zB2) {
            if (!z) {
                return null;
            }
            return m;
        }
        if (i.get("wifi_info") != null) {
            m = i.get("wifi_info");
        } else {
            WifiInfo wifiInfoC = oa.b().c();
            m = wifiInfoC;
            if (wifiInfoC != null) {
                i.put("wifi_info", wifiInfoC);
            }
        }
        return m;
    }

    public static JSONObject f() {
        return f(true);
    }

    public static synchronized JSONObject f(boolean z) {
        boolean zB = c7.b(d2.t);
        boolean zB2 = c7.b(d2.u);
        if (zB && zB2) {
            JSONObject jSONObjectA = n.a();
            o = jSONObjectA;
            return c7.a(jSONObjectA);
        }
        JSONObject jSONObjectA2 = a9.a(o8.c, new JSONObject());
        if (jSONObjectA2.has("sensitive_info_collect_switch")) {
            JSONObject jSONObjectOptJSONObject = jSONObjectA2.optJSONObject("sensitive_info_collect_switch");
            long jOptLong = jSONObjectOptJSONObject.optLong(d2.t);
            long jOptLong2 = jSONObjectOptJSONObject.optLong(d2.u);
            if (jOptLong == 0 || jOptLong2 == 0) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("bssid", "N/P");
                    jSONObject.put("ssid", "N/P");
                    jSONObject.put("mac_type", ja.l());
                    jSONObject.put("ip", g(true));
                    JSONObject jSONObject2 = new JSONObject();
                    JSONArray jSONArray = new JSONArray();
                    jSONArray.put(jSONObject);
                    jSONObject2.put("wifi_info", jSONArray);
                    jSONObject2.put("detail", new JSONArray().put("wifi"));
                    jSONObject2.put("type", "wifi");
                    return jSONObject2;
                } catch (JSONException e) {
                }
            }
        }
        return z ? c7.a(o) : new JSONObject();
    }

    public static String g() {
        return g(true);
    }

    public static synchronized String g(boolean z) {
        if (c7.b(d2.c)) {
            String strD = k6.a().d();
            k = strD;
            return strD;
        }
        if (!z) {
            return c7.c;
        }
        return k;
    }
}
