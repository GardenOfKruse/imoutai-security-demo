package com.coralline.sea;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/* JADX INFO: loaded from: assets/RiskStub.dex */
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
        To view partially-correct add '--show-bad-code' argument
    */
    public final byte[] a(byte[] r18) {
        /*
            r17 = this;
            r0 = r18
            int r1 = r0.length
            int r2 = r1 % 64
            r3 = 63
            r4 = 56
            if (r2 >= r4) goto L12
            int r3 = 55 - r2
            int r2 = r1 - r2
        Lf:
            int r2 = r2 + 64
            goto L1e
        L12:
            if (r2 != r4) goto L17
            int r2 = r1 + 8
            goto Lf
        L17:
            int r3 = r3 - r2
            int r3 = r3 + r4
            int r5 = r1 + 64
            int r5 = r5 - r2
            int r2 = r5 + 64
        L1e:
            byte[] r2 = new byte[r2]
            r5 = 0
            java.lang.System.arraycopy(r0, r5, r2, r5, r1)
            int r0 = r1 + 1
            r6 = -128(0xffffffffffffff80, float:NaN)
            r2[r1] = r6
            r6 = r0
            r0 = 0
        L2c:
            if (r0 >= r3) goto L36
            int r7 = r6 + 1
            r2[r6] = r5
            int r0 = r0 + 1
            r6 = r7
            goto L2c
        L36:
            long r0 = (long) r1
            r7 = 8
            long r0 = r0 * r7
            r7 = 255(0xff, double:1.26E-321)
            long r9 = r0 & r7
            int r3 = (int) r9
            byte r3 = (byte) r3
            r5 = 8
            long r9 = r0 >> r5
            long r11 = r9 & r7
            int r5 = (int) r11
            byte r5 = (byte) r5
            r9 = 16
            long r9 = r0 >> r9
            long r11 = r9 & r7
            int r9 = (int) r11
            byte r9 = (byte) r9
            r10 = 24
            long r10 = r0 >> r10
            long r12 = r10 & r7
            int r10 = (int) r12
            byte r10 = (byte) r10
            r11 = 32
            long r11 = r0 >> r11
            long r13 = r11 & r7
            int r11 = (int) r13
            byte r11 = (byte) r11
            r12 = 40
            long r12 = r0 >> r12
            long r14 = r12 & r7
            int r12 = (int) r14
            byte r12 = (byte) r12
            r13 = 48
            long r13 = r0 >> r13
            r16 = r5
            long r4 = r13 & r7
            int r4 = (int) r4
            byte r4 = (byte) r4
            r5 = 56
            long r0 = r0 >> r5
            int r0 = (int) r0
            byte r0 = (byte) r0
            int r1 = r6 + 1
            r2[r6] = r0
            int r0 = r1 + 1
            r2[r1] = r4
            int r1 = r0 + 1
            r2[r0] = r12
            int r0 = r1 + 1
            r2[r1] = r11
            int r1 = r0 + 1
            r2[r0] = r10
            int r0 = r1 + 1
            r2[r1] = r9
            int r1 = r0 + 1
            r2[r0] = r16
            r2[r1] = r3
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.d4.a(byte[]):byte[]");
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
