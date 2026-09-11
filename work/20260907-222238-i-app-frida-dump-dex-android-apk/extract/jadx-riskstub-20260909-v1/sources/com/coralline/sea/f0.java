package com.coralline.sea;

import android.text.TextUtils;
import android.util.Base64;
import java.io.IOException;
import java.util.List;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public abstract class f0 implements n4 {
    public d8 a = new d8();
    public String b = c7.c;
    public String c = c7.c;

    public static int a(char c, int i) throws Exception {
        int iDigit = Character.digit(c, 16);
        if (iDigit != -1) {
            return iDigit;
        }
        throw new Exception("Illegal hexadecimal character " + c + " at index " + i);
    }

    public static int a(char[] cArr, byte[] bArr, int i) throws Exception {
        int length = cArr.length;
        if ((length & 1) != 0) {
            throw new Exception("0dd number of characters.");
        }
        int i2 = length >> 1;
        if (bArr.length - i < i2) {
            throw new Exception("Output array is not large enough to accommodate decoded data.");
        }
        int i3 = 0;
        while (i3 < length) {
            int iA = a(cArr[i3], i3) << 4;
            int i4 = i3 + 1;
            bArr[i] = (byte) ((iA | a(cArr[i4], i4)) & 255);
            i3 = i4 + 1 + 1;
        }
        return i2;
    }

    public static String a(byte[] bArr, boolean z) {
        String str = c7.c;
        for (byte b : bArr) {
            str = str + Integer.toString((b & 255) + 256, 16).substring(1);
        }
        return z ? str.toUpperCase() : str;
    }

    public static byte[] a(char[] cArr) throws Exception {
        byte[] bArr = new byte[cArr.length >> 1];
        a(cArr, bArr, 0);
        return bArr;
    }

    public static String b(byte[] bArr) {
        return a(bArr, true);
    }

    @Override // com.coralline.sea.n4
    public String a(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            if (n3.a().C) {
                String strA = p9.a(str);
                if (strA == null) {
                    return null;
                }
                return v1.d(strA.getBytes());
            }
            byte[] bArrA = p9.a(str.getBytes());
            if (bArrA == null) {
                return null;
            }
            return (j6.c() ? "SYDWXGZ" : "YDWXGZ") + v1.d(bArrA);
        } catch (Exception e) {
            e.toString();
            return null;
        }
    }

    @Override // com.coralline.sea.n4
    public String a(String str, String str2, String str3) {
        return null;
    }

    @Override // com.coralline.sea.n4
    public String a(byte[] bArr) {
        if (bArr == null || bArr.length < 1) {
            return null;
        }
        if (n3.a().C) {
            String strA = v1.a(new String(bArr));
            if (strA == null) {
                return null;
            }
            return p9.b(strA);
        }
        byte[] bArrB = p9.b(v1.b(bArr));
        if (bArrB != null) {
            return new String(bArrB);
        }
        return null;
    }

    @Override // com.coralline.sea.n4
    public String a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        return null;
    }

    @Override // com.coralline.sea.n4
    public boolean a(s1 s1Var) {
        return l2.g().b().e() && !n3.a().g;
    }

    public List<String> b(String str) {
        return ja.c(str);
    }

    public void b() {
        this.c = i6.p();
    }

    public String c(String str) {
        String str2 = this.b;
        byte[] bArr = null;
        if (str2 == null) {
            return null;
        }
        byte[] bArr2 = new byte[0];
        try {
            ia.g(str2);
            ia.g(new String(Base64.decode(str, 2)));
        } catch (IOException e) {
            e.printStackTrace();
            bArr = bArr2;
        }
        return new String(bArr);
    }

    public byte[] d(String str) throws Exception {
        String str2 = this.c;
        if (str2 == null) {
            return null;
        }
        ia.g(str2);
        str.getBytes();
        return null;
    }
}
