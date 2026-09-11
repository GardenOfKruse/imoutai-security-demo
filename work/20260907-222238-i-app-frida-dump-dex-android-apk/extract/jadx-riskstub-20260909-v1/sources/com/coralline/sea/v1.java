package com.coralline.sea;

import android.text.TextUtils;
import android.util.Base64;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class v1 {
    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return c7.c;
        }
        try {
            return new String(a(Base64.decode(str, 2)));
        } catch (Exception e) {
            return c7.c;
        }
    }

    public static String a(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            return c7.c;
        }
        try {
            return new String(a(Base64.decode(str, 2), str2.getBytes(), str3.getBytes()));
        } catch (Exception e) {
            return c7.c;
        }
    }

    public static byte[] a(byte[] bArr) {
        return i6.a(bArr, 0, 1, true, 0);
    }

    public static byte[] a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        return i6.a(bArr, 0, 1, true, 0, bArr2, bArr3);
    }

    public static String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return c7.c;
        }
        try {
            return new String(i6.a(Base64.decode(str, 2), 0, 1, true, 1));
        } catch (Exception e) {
            return c7.c;
        }
    }

    public static String b(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            return c7.c;
        }
        try {
            return Base64.encodeToString(c(str.getBytes(), str2.getBytes(), str3.getBytes()), 2);
        } catch (Exception e) {
            return c7.c;
        }
    }

    public static byte[] b(byte[] bArr) {
        try {
            return a(Base64.decode(bArr, 2));
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public static byte[] b(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArr4 = new byte[0];
        try {
            byte[] bArrDecode = Base64.decode(bArr, 2);
            int length = bArrDecode.length;
            byte[] bArrA = a(bArrDecode, bArr2, bArr3);
            try {
                int length2 = bArrA.length;
                return bArrA;
            } catch (Exception e) {
                return bArrA;
            }
        } catch (Exception e2) {
            return bArr4;
        }
    }

    public static String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return c7.c;
        }
        try {
            return Base64.encodeToString(c(str.getBytes()), 2);
        } catch (Exception e) {
            return c7.c;
        }
    }

    public static byte[] c(byte[] bArr) {
        return i6.a(bArr, 1, 1, true, 0);
    }

    public static byte[] c(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        return i6.a(bArr, 1, 1, true, 0, bArr2, bArr3);
    }

    public static String d(String str) {
        if (TextUtils.isEmpty(str)) {
            return c7.c;
        }
        try {
            return Base64.encodeToString(i6.a(str.getBytes(), 1, 1, true, 2), 2);
        } catch (Exception e) {
            return c7.c;
        }
    }

    public static String d(byte[] bArr) {
        try {
            return Base64.encodeToString(c(bArr), 2);
        } catch (Exception e) {
            return c7.c;
        }
    }

    public static String d(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            return Base64.encodeToString(c(bArr, bArr2, bArr3), 2);
        } catch (Exception e) {
            return c7.c;
        }
    }
}
