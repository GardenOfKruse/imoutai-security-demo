package com.coralline.sea;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.text.TextUtils;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class k6 {
    public static volatile k6 c = null;
    public static final String d = "net_status";
    public static final int e = -1;
    public static final int f = 0;
    public static final int g = 1;
    public static final int h = 2;
    public static final int i = 3;
    public static final int j = 4;
    public static final int k = 5;
    public static final int l = 99;
    public static final int m = 16;
    public static final int n = 17;
    public static final int o = 18;
    public String a = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    public Pattern b = Pattern.compile("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)");

    public static k6 a() {
        if (c == null) {
            synchronized (k6.class) {
                if (c == null) {
                    c = new k6();
                }
            }
        }
        return c;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Integer a(android.content.Context r3) {
        /*
            r2 = this;
            java.lang.String r0 = "android.permission.ACCESS_NETWORK_STATE"
            int r0 = com.coralline.sea.ja.r(r0)
            if (r0 == 0) goto Le
            r3 = 0
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            return r3
        Le:
            java.lang.String r0 = "connectivity"
            java.lang.Object r3 = r3.getSystemService(r0)
            android.net.ConnectivityManager r3 = (android.net.ConnectivityManager) r3
            android.net.NetworkInfo r3 = r3.getActiveNetworkInfo()
            r0 = 1
            if (r3 == 0) goto L5b
            boolean r1 = r3.isConnectedOrConnecting()
            if (r1 == 0) goto L5b
            int r1 = r3.getType()
            if (r1 == 0) goto L2c
            if (r1 == r0) goto L5c
            goto L56
        L2c:
            int r0 = r3.getSubtype()
            switch(r0) {
                case 1: goto L54;
                case 2: goto L54;
                case 3: goto L59;
                case 4: goto L54;
                case 5: goto L59;
                case 6: goto L59;
                case 7: goto L54;
                case 8: goto L59;
                case 9: goto L59;
                case 10: goto L59;
                case 11: goto L54;
                case 12: goto L59;
                case 13: goto L52;
                case 14: goto L59;
                case 15: goto L59;
                case 16: goto L54;
                case 17: goto L59;
                case 18: goto L52;
                case 19: goto L33;
                case 20: goto L50;
                default: goto L33;
            }
        L33:
            java.lang.String r3 = r3.getSubtypeName()
            java.lang.String r0 = "TD-SCDMA"
            boolean r0 = r3.equalsIgnoreCase(r0)
            if (r0 != 0) goto L59
            java.lang.String r0 = "WCDMA"
            boolean r0 = r3.equalsIgnoreCase(r0)
            if (r0 != 0) goto L59
            java.lang.String r0 = "CDMA2000"
            boolean r3 = r3.equalsIgnoreCase(r0)
            if (r3 == 0) goto L56
            goto L59
        L50:
            r0 = 5
            goto L5c
        L52:
            r0 = 4
            goto L5c
        L54:
            r0 = 2
            goto L5c
        L56:
            r0 = 99
            goto L5c
        L59:
            r0 = 3
            goto L5c
        L5b:
            r0 = -1
        L5c:
            java.lang.Integer r3 = java.lang.Integer.valueOf(r0)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.k6.a(android.content.Context):java.lang.Integer");
    }

    public String b() {
        String strD;
        String strG = c7.c;
        try {
            strD = l6.d();
        } catch (Exception e2) {
        }
        try {
            if (l6.e(true) == null || (strG = l6.g(true)) == null || !this.b.matcher(strG).matches()) {
                return strD;
            }
            if (strG.equals("0.0.0.0")) {
                return strD;
            }
            return strG;
        } catch (Exception e3) {
            return strD;
        }
    }

    public String b(Context context) {
        switch (a(context).intValue()) {
            case -1:
                return "NETWORK_NO";
            case 0:
                return "N/P";
            case 1:
                return f2.e;
            case 2:
                return "NETWORK_2G";
            case 3:
                return "NETWORK_3G";
            case 4:
                return f2.f;
            case 5:
                return "NETWORK_5G";
            default:
                return "NETWORK_UNKNOWN";
        }
    }

    public String c() {
        NetworkInfo networkInfoA;
        try {
            if (ja.r("android.permission.ACCESS_NETWORK_STATE") != 0) {
                return "1.1.1.1";
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) n3.a().a.getSystemService("connectivity");
            if (connectivityManager != null && (networkInfoA = l6.a(connectivityManager, 0)) != null && networkInfoA.isConnected()) {
                String strA = e5.a();
                if (!TextUtils.isEmpty(strA) && this.b.matcher(strA).matches()) {
                    if (!strA.equals("0.0.0.0")) {
                        return strA;
                    }
                }
            }
        } catch (Exception e2) {
        }
        return c7.c;
    }

    public String d() {
        if (ja.r("android.permission.ACCESS_NETWORK_STATE") == 0 && ja.r("android.permission.ACCESS_WIFI_STATE") == 0) {
            WifiInfo wifiInfoE = l6.e();
            if (wifiInfoE != null) {
                String strA = e5.a(wifiInfoE.getIpAddress());
                if (!TextUtils.isEmpty(strA) && this.b.matcher(strA).matches()) {
                    if (!strA.equals("0.0.0.0")) {
                        return strA;
                    }
                }
            }
            return c7.c;
        }
        return "1.1.1.1";
    }
}
