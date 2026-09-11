package com.coralline.sea;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class d4 {
    public final int[] a = {1732584193, -271733879, -1732584194, 271733878, -1009589776};
    public int[] b = new int[5];
    public int[] c = new int[80];

    public static void a(String[] strArr) {
        byte[] bArrA;
        byte[] bArr = new byte[0];
        try {
            bArrA = a("zhpt_inner_test1jsonA07F8458AC429D517E13DA47E180E2A57495B89B34E3A48B697C72FBEE864E43135C121877B2D873A5B74ABAEF5693B7842BA5D474810D3A99EADEA0EFBD0FED5F63E3DC0811C3FE114F4876ABFE38C3414653E6206E22A2ECFD1E60BF8C2698EF7A91F542126B173C9601BDB37EF10ADE3876AFC0313F38CEDC0CA3E5A666EEv1.5", "sAecMFcAlIXes93VaWXgr3jgMup4Y0a6");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            bArrA = bArr;
        }
        System.out.println("原字符串zhpt_inner_test1jsonA07F8458AC429D517E13DA47E180E2A57495B89B34E3A48B697C72FBEE864E43135C121877B2D873A5B74ABAEF5693B7842BA5D474810D3A99EADEA0EFBD0FED5F63E3DC0811C3FE114F4876ABFE38C3414653E6206E22A2ECFD1E60BF8C2698EF7A91F542126B173C9601BDB37EF10ADE3876AFC0313F38CEDC0CA3E5A666EEv1.5");
        System.out.println("加密后字符串" + b(bArrA));
        System.out.println("加密后字符串" + ja.a(bArrA));
    }

    public static byte[] a(String str, String str2) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        byte[] bArr = new byte[64];
        byte[] bArr2 = new byte[64];
        byte[] bArr3 = new byte[64];
        int length = str2.length();
        d4 d4Var = new d4();
        if (str2.length() > 64) {
            byte[] bArrC = d4Var.c(str2.getBytes(s0.f));
            length = bArrC.length;
            for (int i = 0; i < length; i++) {
                bArr3[i] = bArrC[i];
            }
        } else {
            byte[] bytes = str2.getBytes(s0.f);
            for (int i2 = 0; i2 < bytes.length; i2++) {
                bArr3[i2] = bytes[i2];
            }
        }
        while (length < 64) {
            bArr3[length] = 0;
            length++;
        }
        for (int i3 = 0; i3 < 64; i3++) {
            bArr[i3] = (byte) (bArr3[i3] ^ 54);
            bArr2[i3] = (byte) (bArr3[i3] ^ 92);
        }
        return d4Var.c(a(bArr2, d4Var.c(a(bArr, str.getBytes(s0.f)))));
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr3[i] = bArr[i];
        }
        for (int i2 = 0; i2 < bArr2.length; i2++) {
            bArr3[bArr.length + i2] = bArr2[i2];
        }
        return bArr3;
    }

    public static String b(String str, String str2) throws UnsupportedEncodingException {
        return ja.a(a(str, str2));
    }

    public static String b(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String upperCase = Integer.toHexString(b & 255).toUpperCase(Locale.CHINA);
            if (upperCase.length() < 2) {
                sb.append(0);
            }
            sb.append(upperCase);
        }
        return sb.toString();
    }

    public final int a(int i, int i2) {
        return (i >>> (32 - i2)) | (i << i2);
    }

    public final int a(int i, int i2, int i3) {
        return ((i ^ (-1)) & i3) | (i2 & i);
    }

    public final int a(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    public final void a() {
        for (int i = 16; i <= 79; i++) {
            int[] iArr = this.c;
            iArr[i] = a(((iArr[i - 3] ^ iArr[i - 8]) ^ iArr[i - 14]) ^ iArr[i - 16], 1);
        }
        int[] iArr2 = new int[5];
        for (int i2 = 0; i2 < 5; i2++) {
            iArr2[i2] = this.b[i2];
        }
        for (int i3 = 0; i3 <= 19; i3++) {
            int iA = a(iArr2[1], iArr2[2], iArr2[3]) + a(iArr2[0], 5) + iArr2[4] + this.c[i3] + 1518500249;
            iArr2[4] = iArr2[3];
            iArr2[3] = iArr2[2];
            iArr2[2] = a(iArr2[1], 30);
            iArr2[1] = iArr2[0];
            iArr2[0] = iA;
        }
        for (int i4 = 20; i4 <= 39; i4++) {
            int iB = b(iArr2[1], iArr2[2], iArr2[3]) + a(iArr2[0], 5) + iArr2[4] + this.c[i4] + 1859775393;
            iArr2[4] = iArr2[3];
            iArr2[3] = iArr2[2];
            iArr2[2] = a(iArr2[1], 30);
            iArr2[1] = iArr2[0];
            iArr2[0] = iB;
        }
        for (int i5 = 40; i5 <= 59; i5++) {
            int iC = (((c(iArr2[1], iArr2[2], iArr2[3]) + a(iArr2[0], 5)) + iArr2[4]) + this.c[i5]) - 1894007588;
            iArr2[4] = iArr2[3];
            iArr2[3] = iArr2[2];
            iArr2[2] = a(iArr2[1], 30);
            iArr2[1] = iArr2[0];
            iArr2[0] = iC;
        }
        for (int i6 = 60; i6 <= 79; i6++) {
            int iB2 = (((b(iArr2[1], iArr2[2], iArr2[3]) + a(iArr2[0], 5)) + iArr2[4]) + this.c[i6]) - 899497514;
            iArr2[4] = iArr2[3];
            iArr2[3] = iArr2[2];
            iArr2[2] = a(iArr2[1], 30);
            iArr2[1] = iArr2[0];
            iArr2[0] = iB2;
        }
        for (int i7 = 0; i7 < 5; i7++) {
            int[] iArr3 = this.b;
            iArr3[i7] = iArr3[i7] + iArr2[i7];
        }
        int i8 = 0;
        while (true) {
            int[] iArr4 = this.c;
            if (i8 >= iArr4.length) {
                return;
            }
            iArr4[i8] = 0;
            i8++;
        }
    }

    public final void a(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i >>> 24);
        bArr[i2 + 1] = (byte) (i >>> 16);
        bArr[i2 + 2] = (byte) (i >>> 8);
        bArr[i2 + 3] = (byte) i;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002e A[LOOP:0: B:10:0x002c->B:11:0x002e, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final byte[] a(byte[] bArr) {
        int i;
        int i2;
        int i3;
        int length = bArr.length;
        int i4 = length % 64;
        int i5 = 63;
        if (i4 < 56) {
            i5 = 55 - i4;
            i2 = length - i4;
        } else {
            if (i4 != 56) {
                i5 = (63 - i4) + 56;
                i = ((length + 64) - i4) + 64;
                byte[] bArr2 = new byte[i];
                System.arraycopy(bArr, 0, bArr2, 0, length);
                bArr2[length] = -128;
                int i6 = length + 1;
                i3 = 0;
                while (i3 < i5) {
                    bArr2[i6] = 0;
                    i3++;
                    i6++;
                }
                long j = ((long) length) * 8;
                byte b = (byte) (j & 255);
                byte b2 = (byte) ((j >> 8) & 255);
                byte b3 = (byte) ((j >> 16) & 255);
                byte b4 = (byte) ((j >> 24) & 255);
                byte b5 = (byte) ((j >> 32) & 255);
                byte b6 = (byte) ((j >> 40) & 255);
                byte b7 = (byte) ((j >> 48) & 255);
                int i7 = i6 + 1;
                bArr2[i6] = (byte) (j >> 56);
                int i8 = i7 + 1;
                bArr2[i7] = b7;
                int i9 = i8 + 1;
                bArr2[i8] = b6;
                int i10 = i9 + 1;
                bArr2[i9] = b5;
                int i11 = i10 + 1;
                bArr2[i10] = b4;
                int i12 = i11 + 1;
                bArr2[i11] = b3;
                bArr2[i12] = b2;
                bArr2[i12 + 1] = b;
                return bArr2;
            }
            i2 = length + 8;
        }
        i = i2 + 64;
        byte[] bArr22 = new byte[i];
        System.arraycopy(bArr, 0, bArr22, 0, length);
        bArr22[length] = -128;
        int i62 = length + 1;
        i3 = 0;
        while (i3 < i5) {
        }
        long j2 = ((long) length) * 8;
        byte b8 = (byte) (j2 & 255);
        byte b22 = (byte) ((j2 >> 8) & 255);
        byte b32 = (byte) ((j2 >> 16) & 255);
        byte b42 = (byte) ((j2 >> 24) & 255);
        byte b52 = (byte) ((j2 >> 32) & 255);
        byte b62 = (byte) ((j2 >> 40) & 255);
        byte b72 = (byte) ((j2 >> 48) & 255);
        int i72 = i62 + 1;
        bArr22[i62] = (byte) (j2 >> 56);
        int i82 = i72 + 1;
        bArr22[i72] = b72;
        int i92 = i82 + 1;
        bArr22[i82] = b62;
        int i102 = i92 + 1;
        bArr22[i92] = b52;
        int i112 = i102 + 1;
        bArr22[i102] = b42;
        int i122 = i112 + 1;
        bArr22[i112] = b32;
        bArr22[i122] = b22;
        bArr22[i122 + 1] = b8;
        return bArr22;
    }

    public final int b(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    public final int c(int i, int i2, int i3) {
        return (i & i3) | (i & i2) | (i2 & i3);
    }

    public byte[] c(byte[] bArr) {
        d(bArr);
        byte[] bArr2 = new byte[20];
        int i = 0;
        while (true) {
            int[] iArr = this.b;
            if (i >= iArr.length) {
                return bArr2;
            }
            a(iArr[i], bArr2, i * 4);
            i++;
        }
    }

    public final int d(byte[] bArr) {
        int[] iArr = this.a;
        System.arraycopy(iArr, 0, this.b, 0, iArr.length);
        byte[] bArrA = a(bArr);
        int length = bArrA.length / 64;
        for (int i = 0; i < length; i++) {
            for (int i2 = 0; i2 < 16; i2++) {
                this.c[i2] = a(bArrA, (i2 * 4) + (i * 64));
            }
            a();
        }
        return 20;
    }
}
