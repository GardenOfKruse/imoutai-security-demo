package com.coralline.sea;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import androidx.core.app.ActivityCompat;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class oa {
    public static oa a = null;
    public static final String b = "wifi";
    public static final String c = "<unknown ssid>";
    public static String d = "";
    public static String e = "";

    public static synchronized oa b() {
        if (a == null) {
            a = new oa();
        }
        return a;
    }

    public NetworkInfo a(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                return connectivityManager.getActiveNetworkInfo();
            }
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public String a() {
        byte[] hardwareAddress;
        if (!TextUtils.isEmpty(d) && !d.equals("02:00:00:00:00:00")) {
            return d;
        }
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                for (InetAddress inetAddress : Collections.list(networkInterface.getInetAddresses())) {
                    if (!inetAddress.isLinkLocalAddress() && !inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address) && (hardwareAddress = networkInterface.getHardwareAddress()) != null) {
                        StringBuilder sb = new StringBuilder();
                        for (byte b2 : hardwareAddress) {
                            sb.append(String.format("%02X:", Byte.valueOf(b2)));
                        }
                        if (sb.length() > 0) {
                            sb.deleteCharAt(sb.length() - 1);
                        }
                        return sb.toString();
                    }
                }
            }
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public final String b(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (wifiManager == null) {
            return "<unknown ssid>";
        }
        int networkId = wifiManager.getConnectionInfo().getNetworkId();
        ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION");
        for (WifiConfiguration wifiConfiguration : wifiManager.getConfiguredNetworks()) {
            if (wifiConfiguration.networkId == networkId) {
                return wifiConfiguration.SSID;
            }
        }
        return "<unknown ssid>";
    }

    public WifiInfo c() {
        WifiManager wifiManager;
        WifiInfo connectionInfo;
        Context context = n3.a().a;
        try {
            NetworkInfo networkInfoA = l6.a((ConnectivityManager) context.getSystemService("connectivity"), 1);
            if (networkInfoA == null || !networkInfoA.isConnected() || (wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi")) == null || (connectionInfo = wifiManager.getConnectionInfo()) == null) {
                return null;
            }
            if (connectionInfo.getNetworkId() == -1) {
                return null;
            }
            return connectionInfo;
        } catch (Exception e2) {
            return null;
        }
    }

    public String d() {
        if (!TextUtils.isEmpty(e) && !"<unknown ssid>".equalsIgnoreCase(e)) {
            return e;
        }
        Context context = n3.a().a;
        WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        String ssid = connectionInfo != null ? connectionInfo.getSSID() : null;
        String strTrim = ssid != null ? ssid.trim() : null;
        if (!TextUtils.isEmpty(strTrim) && strTrim.charAt(0) == '\"' && strTrim.charAt(strTrim.length() - 1) == '\"') {
            strTrim = strTrim.substring(1, strTrim.length() - 1);
        }
        if (TextUtils.isEmpty(strTrim) || "<unknown ssid>".equalsIgnoreCase(strTrim.trim())) {
            NetworkInfo networkInfoA = a(context);
            if (networkInfoA.isConnected() && networkInfoA.getExtraInfo() != null) {
                strTrim = networkInfoA.getExtraInfo().replace("\"", c7.c);
            }
        }
        if (TextUtils.isEmpty(strTrim) || "<unknown ssid>".equalsIgnoreCase(strTrim.trim())) {
            strTrim = b(context);
        }
        e = strTrim;
        return strTrim;
    }
}
