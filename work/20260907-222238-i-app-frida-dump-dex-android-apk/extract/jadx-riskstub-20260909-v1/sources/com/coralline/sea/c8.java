package com.coralline.sea;

import android.telephony.TelephonyManager;
import com.coralline.sea.m5;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class c8 {
    public static final String a = "RxNetUtils";

    public static String a() {
        TelephonyManager telephonyManager = (TelephonyManager) n3.a().a.getSystemService(m1.j);
        try {
            return (String) q7.a(telephonyManager).b("getNetworkTypeName").c();
        } catch (Exception e) {
            return a(telephonyManager.getNetworkType());
        }
    }

    public static String a(int i) {
        switch (i) {
            case 1:
                return "GPRS";
            case 2:
                return "EDGE";
            case 3:
                return "UMTS";
            case 4:
                return "CDMA";
            case 5:
                return "CDMA - EvDo rev. 0";
            case m5.b.f /* 6 */:
                return "CDMA - EvDo rev. A";
            case 7:
                return "CDMA - 1xRTT";
            case 8:
                return "HSDPA";
            case 9:
                return "HSUPA";
            case 10:
                return "HSPA";
            case m5.b.i /* 11 */:
                return "iDEN";
            case 12:
                return "CDMA - EvDo rev. B";
            case m5.b.k /* 13 */:
                return "LTE";
            case m5.b.l /* 14 */:
                return "CDMA - eHRPD";
            case m5.b.m /* 15 */:
                return "HSPA+";
            case 16:
                return "GSM";
            case 17:
                return "TD_SCDMA";
            case 18:
                return "IWLAN";
            case m5.b.q /* 19 */:
            default:
                return "UNKNOWN";
            case 20:
                return "NR";
        }
    }
}
