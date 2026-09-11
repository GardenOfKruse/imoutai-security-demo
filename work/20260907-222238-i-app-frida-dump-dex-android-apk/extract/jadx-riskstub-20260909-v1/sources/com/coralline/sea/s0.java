package com.coralline.sea;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class s0 {
    public static char b = 'E';
    public static char c = 'C';
    public static char d = 'B';
    public static String e = c7.c + b + c + d;
    public static final String f = "UTF-8";
    public static final String g = "GBK";
    public byte[] a;

    public s0(String str) {
        this.a = null;
        this.a = str.getBytes();
    }

    public static String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                hexString = "0".concat(hexString);
            }
            stringBuffer.append(hexString.toUpperCase());
        }
        return stringBuffer.toString();
    }

    public static byte[] a(String str) {
        if (str.length() < 1) {
            return null;
        }
        byte[] bArr = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            int i2 = i * 2;
            int i3 = i2 + 1;
            bArr[i] = (byte) ((Integer.parseInt(str.substring(i2, i3), 16) * 16) + Integer.parseInt(str.substring(i3, i2 + 2), 16));
        }
        return bArr;
    }

    public String a(String str, String str2) throws Exception {
        return a(str, this.a, str2);
    }

    public final String a(String str, byte[] bArr, String str2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher cipher = Cipher.getInstance("AES/" + e + "/PKCS5Padding");
        cipher.init(2, secretKeySpec);
        return new String(cipher.doFinal(t0.a(str)), str2);
    }

    public void b(String str) {
        if (str != null) {
            if (str.length() > 16) {
                str = str.substring(0, 16);
            }
            this.a = str.getBytes();
        }
    }
}
