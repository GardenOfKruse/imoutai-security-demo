package com.coralline.sea;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import java.io.Closeable;
import java.io.FileInputStream;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class w2 {
    public static w2 a = null;
    public static final String b = "device_id";
    public static String c;

    public static synchronized w2 b() {
        if (a == null) {
            a = new w2();
        }
        return a;
    }

    public String a() {
        String string = i2.b;
        try {
            Context context = n3.a().a;
            if (context != null) {
                string = Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
            }
            return ja.w(string) ? string : i2.b;
        } catch (Exception e) {
            return string;
        }
    }

    public String a(Context context) {
        try {
            if (c7.a(d2.E)) {
                String str = c;
                if (str != null) {
                    return str;
                }
                String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
                c = string;
                return string;
            }
        } catch (Exception e) {
            e.toString();
        }
        return i2.b;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public String c() throws Throwable {
        FileInputStream fileInputStream;
        String str;
        FileInputStream fileInputStream2;
        FileInputStream fileInputStream3 = null;
        try {
            try {
                fileInputStream = new FileInputStream("sys/class/net/eth0/address");
                try {
                    byte[] bArr = new byte[8192];
                    int i = fileInputStream.read(bArr);
                    str = i > 0 ? new String(bArr, 0, i, StandardCharsets.UTF_8) : null;
                    if (str == null) {
                        p9.a(fileInputStream);
                        return i2.b;
                    }
                } catch (Exception e) {
                    str = null;
                    try {
                        fileInputStream2 = new FileInputStream("sys/class/net/wlan0/address");
                    } catch (Exception e2) {
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        byte[] bArr2 = new byte[8192];
                        int i2 = fileInputStream2.read(bArr2);
                        String str2 = i2 > 0 ? new String(bArr2, 0, i2, StandardCharsets.UTF_8) : str;
                        p9.a(fileInputStream2);
                        str = str2;
                    } catch (Exception e3) {
                        fileInputStream3 = fileInputStream2;
                        p9.a(fileInputStream3);
                    } catch (Throwable th2) {
                        th = th2;
                        fileInputStream3 = fileInputStream2;
                        p9.a(fileInputStream3);
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                p9.a((Closeable) null);
                throw th;
            }
        } catch (Exception e4) {
            fileInputStream = null;
            str = null;
        } catch (Throwable th4) {
            th = th4;
            p9.a((Closeable) null);
            throw th;
        }
        p9.a(fileInputStream);
        return str == null ? i2.b : str.trim();
    }

    public String d() {
        String str = n3.a().G;
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            NetworkInterface byName = NetworkInterface.getByName((String) q7.j("android.os.SystemProperties").a("get", "wifi.interface").c());
            if (byName == null) {
                return ja.x(i2.b);
            }
            byte[] hardwareAddress = byName.getHardwareAddress();
            if (hardwareAddress != null && hardwareAddress.length != 0) {
                StringBuilder sb = new StringBuilder();
                for (byte b2 : hardwareAddress) {
                    sb.append(String.format("%02X:", Byte.valueOf(b2)));
                }
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                String string = sb.toString();
                if (string == null || string.length() == 0) {
                    string = i2.b;
                }
                return ja.x(string);
            }
            return ja.x(i2.b);
        } catch (Exception e) {
            return ja.x(i2.b);
        }
    }
}
