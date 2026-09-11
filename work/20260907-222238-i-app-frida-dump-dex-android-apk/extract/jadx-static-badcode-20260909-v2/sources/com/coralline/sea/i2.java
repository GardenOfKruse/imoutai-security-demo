package com.coralline.sea;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.RouteInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class i2 {
    public static final String a = "CscbDeviceInfoCollector";
    public static final String b = "N/A";

    public static int a() {
        try {
            return Runtime.getRuntime().availableProcessors();
        } catch (Throwable th) {
            return -1;
        }
    }

    @Nullable
    public static String a(@Nullable File file) {
        FileInputStream fileInputStream;
        if (file == null || !file.exists() || !file.isFile() || !file.canRead()) {
            return null;
        }
        if (file.length() > 10485760) {
            file.getPath();
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = fileInputStream.read(bArr);
                    if (i == -1) {
                        String string = byteArrayOutputStream.toString();
                        try {
                            fileInputStream.close();
                            return string;
                        } catch (Throwable th) {
                            return string;
                        }
                    }
                    byteArrayOutputStream.write(bArr, 0, i);
                }
            } catch (Throwable th2) {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                        return null;
                    } catch (Throwable th3) {
                        return null;
                    }
                }
                return null;
            }
        } catch (Throwable th4) {
            fileInputStream = null;
        }
    }

    @Nullable
    public static JSONObject a(Context context) {
        if (context == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cpu_count", a());
            jSONObject.put("cpu_max_freq", b());
            jSONObject.put("total_space", g(context));
            jSONObject.put("device_name", d(context));
            jSONObject.put("board_platform", c(context));
            return jSONObject;
        } catch (Throwable th) {
            th.toString();
            return null;
        }
    }

    public static String b() {
        try {
            String strA = a(new File("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"));
            if (strA == null) {
                return b;
            }
            String strTrim = strA.trim();
            return TextUtils.isEmpty(strTrim) ? b : strTrim;
        } catch (Throwable th) {
            return b;
        }
    }

    @Nullable
    public static JSONObject b(Context context) {
        if (context == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("dns_servers", e(context));
            jSONObject.put("gateway", f(context));
            jSONObject.put("wifi_count", h(context));
            return jSONObject;
        } catch (Throwable th) {
            th.toString();
            return null;
        }
    }

    public static String c(Context context) {
        if (context == null) {
            return b;
        }
        try {
            return u9.a(context, "ro.board.platform", b);
        } catch (Throwable th) {
            return b;
        }
    }

    public static String d(Context context) {
        if (context == null || Build.VERSION.SDK_INT < 25) {
            return b;
        }
        try {
            return Settings.Global.getString(context.getContentResolver(), "device_name");
        } catch (Throwable th) {
            return b;
        }
    }

    @NonNull
    public static String e(Context context) {
        Network activeNetwork;
        LinkProperties linkProperties;
        if (context == null || Build.VERSION.SDK_INT < 23) {
            return b;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null || (activeNetwork = connectivityManager.getActiveNetwork()) == null || (linkProperties = connectivityManager.getLinkProperties(activeNetwork)) == null) {
                return b;
            }
            List<InetAddress> dnsServers = linkProperties.getDnsServers();
            StringBuilder sb = new StringBuilder();
            Iterator<InetAddress> it = dnsServers.iterator();
            while (it.hasNext()) {
                String hostAddress = it.next().getHostAddress();
                if (hostAddress != null) {
                    if (sb.length() != 0) {
                        sb.append(",");
                    }
                    sb.append(hostAddress);
                }
            }
            return sb.length() == 0 ? b : sb.toString();
        } catch (Throwable th) {
            return b;
        }
    }

    @NonNull
    public static String f(Context context) {
        LinkProperties linkProperties;
        InetAddress gateway;
        String hostAddress;
        if (context == null || Build.VERSION.SDK_INT < 23) {
            return b;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null || (linkProperties = connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork())) == null) {
                return b;
            }
            for (RouteInfo routeInfo : linkProperties.getRoutes()) {
                if (routeInfo.isDefaultRoute() && (gateway = routeInfo.getGateway()) != null && (hostAddress = gateway.getHostAddress()) != null) {
                    return hostAddress;
                }
            }
            return b;
        } catch (Throwable th) {
            return b;
        }
    }

    public static long g(Context context) {
        if (context == null) {
            return -1L;
        }
        try {
            return context.getFilesDir().getTotalSpace();
        } catch (Throwable th) {
            return -1L;
        }
    }

    public static int h(Context context) {
        if (context == null) {
            return -1;
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager == null) {
                return -1;
            }
            if (Build.VERSION.SDK_INT < 23 || (context.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0 && context.checkSelfPermission("android.permission.ACCESS_WIFI_STATE") == 0)) {
                return wifiManager.getScanResults().size();
            }
            return -1;
        } catch (Throwable th) {
            return -1;
        }
    }
}
