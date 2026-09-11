package com.coralline.sea;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.text.TextUtils;
import com.coralline.sea.m5;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
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
    */
    public final Integer a(Context context) {
        if (ja.r("android.permission.ACCESS_NETWORK_STATE") != 0) {
            return 0;
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        int i2 = 1;
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
            int type = activeNetworkInfo.getType();
            if (type == 0) {
                switch (activeNetworkInfo.getSubtype()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case m5.b.i /* 11 */:
                    case 16:
                        i2 = 2;
                        break;
                    case 3:
                    case 5:
                    case m5.b.f /* 6 */:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case m5.b.l /* 14 */:
                    case m5.b.m /* 15 */:
                    case 17:
                        i2 = 3;
                        break;
                    case m5.b.k /* 13 */:
                    case 18:
                        i2 = 4;
                        break;
                    case m5.b.q /* 19 */:
                    default:
                        String subtypeName = activeNetworkInfo.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA") || subtypeName.equalsIgnoreCase("CDMA2000")) {
                        }
                        break;
                    case 20:
                        i2 = 5;
                        break;
                }
            } else if (type != 1) {
                i2 = 99;
            }
        } else {
            i2 = -1;
        }
        return Integer.valueOf(i2);
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
