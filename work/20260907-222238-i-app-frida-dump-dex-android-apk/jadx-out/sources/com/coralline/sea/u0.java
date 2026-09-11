package com.coralline.sea;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class u0 {
    public final byte[] a = new byte[128];
    public final byte[] b = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    public byte c = 61;

    public u0() {
        a();
    }

    public final int a(OutputStream outputStream, char c, char c2, char c3, char c4) throws IOException {
        char c5 = this.c;
        if (c3 == c5) {
            byte[] bArr = this.a;
            outputStream.write((bArr[c] << 2) | (bArr[c2] >> 4));
            return 1;
        }
        if (c4 == c5) {
            byte[] bArr2 = this.a;
            byte b = bArr2[c];
            byte b2 = bArr2[c2];
            byte b3 = bArr2[c3];
            outputStream.write((b << 2) | (b2 >> 4));
            outputStream.write((b2 << 4) | (b3 >> 2));
            return 2;
        }
        byte[] bArr3 = this.a;
        byte b4 = bArr3[c];
        byte b5 = bArr3[c2];
        byte b6 = bArr3[c3];
        byte b7 = bArr3[c4];
        outputStream.write((b4 << 2) | (b5 >> 4));
        outputStream.write((b5 << 4) | (b6 >> 2));
        outputStream.write((b6 << 6) | b7);
        return 3;
    }

    public final int a(String str, int i, int i2) {
        while (i < i2 && a(str.charAt(i))) {
            i++;
        }
        return i;
    }

    public int a(String str, OutputStream outputStream) throws IOException {
        int length = str.length();
        while (length > 0 && a(str.charAt(length - 1))) {
            length--;
        }
        int i = length - 4;
        int i2 = 0;
        int iA = a(str, 0, i);
        while (iA < i) {
            int i3 = iA + 1;
            byte b = this.a[str.charAt(iA)];
            int iA2 = a(str, i3, i);
            int i4 = iA2 + 1;
            byte b2 = this.a[str.charAt(iA2)];
            int iA3 = a(str, i4, i);
            int i5 = iA3 + 1;
            byte b3 = this.a[str.charAt(iA3)];
            int iA4 = a(str, i5, i);
            int i6 = iA4 + 1;
            byte b4 = this.a[str.charAt(iA4)];
            outputStream.write((b << 2) | (b2 >> 4));
            outputStream.write((b2 << 4) | (b3 >> 2));
            outputStream.write((b3 << 6) | b4);
            i2 += 3;
            iA = a(str, i6, i);
        }
        return i2 + a(outputStream, str.charAt(i), str.charAt(length - 3), str.charAt(length - 2), str.charAt(length - 1));
    }

    public final int a(byte[] bArr, int i, int i2) {
        while (i < i2 && a((char) bArr[i])) {
            i++;
        }
        return i;
    }

    public int a(byte[] bArr, int i, int i2, OutputStream outputStream) throws IOException {
        int i3 = i2 + i;
        while (i3 > i && a((char) bArr[i3 - 1])) {
            i3--;
        }
        int i4 = i3 - 4;
        int iA = a(bArr, i, i4);
        int i5 = 0;
        while (iA < i4) {
            int i6 = iA + 1;
            byte b = this.a[bArr[iA]];
            int iA2 = a(bArr, i6, i4);
            int i7 = iA2 + 1;
            byte b2 = this.a[bArr[iA2]];
            int iA3 = a(bArr, i7, i4);
            int i8 = iA3 + 1;
            byte b3 = this.a[bArr[iA3]];
            int iA4 = a(bArr, i8, i4);
            int i9 = iA4 + 1;
            byte b4 = this.a[bArr[iA4]];
            outputStream.write((b << 2) | (b2 >> 4));
            outputStream.write((b2 << 4) | (b3 >> 2));
            outputStream.write((b3 << 6) | b4);
            i5 += 3;
            iA = a(bArr, i9, i4);
        }
        return i5 + a(outputStream, (char) bArr[i4], (char) bArr[i3 - 3], (char) bArr[i3 - 2], (char) bArr[i3 - 1]);
    }

    public void a() {
        int i = 0;
        while (true) {
            byte[] bArr = this.b;
            if (i >= bArr.length) {
                return;
            }
            this.a[bArr[i]] = (byte) i;
            i++;
        }
    }

    public final boolean a(char c) {
        return c == '\n' || c == '\r' || c == '\t' || c == ' ';
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int b(byte[] r10, int r11, int r12, java.io.OutputStream r13) throws java.io.IOException {
        /*
            r9 = this;
            int r0 = r12 % 3
            int r12 = r12 - r0
            r1 = r11
        L4:
            int r2 = r11 + r12
            r3 = 4
            r4 = 2
            if (r1 >= r2) goto L4c
            r2 = r10[r1]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r5 = r1 + 1
            r5 = r10[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            int r6 = r1 + 2
            r6 = r10[r6]
            r6 = r6 & 255(0xff, float:3.57E-43)
            byte[] r7 = r9.b
            int r8 = r2 >>> 2
            r8 = r8 & 63
            r7 = r7[r8]
            r13.write(r7)
            byte[] r7 = r9.b
            int r2 = r2 << r3
            int r3 = r5 >>> 4
            r2 = r2 | r3
            r2 = r2 & 63
            r2 = r7[r2]
            r13.write(r2)
            byte[] r2 = r9.b
            int r3 = r5 << 2
            int r4 = r6 >>> 6
            r3 = r3 | r4
            r3 = r3 & 63
            r2 = r2[r3]
            r13.write(r2)
            byte[] r2 = r9.b
            r3 = r6 & 63
            r2 = r2[r3]
            r13.write(r2)
            int r1 = r1 + 3
            goto L4
        L4c:
            r11 = 1
            if (r0 == r11) goto L7b
            if (r0 == r4) goto L52
            goto L9e
        L52:
            r1 = r10[r2]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r2 = r2 + r11
            r10 = r10[r2]
            r10 = r10 & 255(0xff, float:3.57E-43)
            int r11 = r1 >>> 2
            r11 = r11 & 63
            int r1 = r1 << r3
            int r2 = r10 >>> 4
            r1 = r1 | r2
            r1 = r1 & 63
            int r10 = r10 << r4
            r10 = r10 & 63
            byte[] r2 = r9.b
            r11 = r2[r11]
            r13.write(r11)
            byte[] r11 = r9.b
            r11 = r11[r1]
            r13.write(r11)
            byte[] r11 = r9.b
            r10 = r11[r10]
            goto L96
        L7b:
            r10 = r10[r2]
            r10 = r10 & 255(0xff, float:3.57E-43)
            int r11 = r10 >>> 2
            r11 = r11 & 63
            int r10 = r10 << r3
            r10 = r10 & 63
            byte[] r1 = r9.b
            r11 = r1[r11]
            r13.write(r11)
            byte[] r11 = r9.b
            r10 = r11[r10]
            r13.write(r10)
            byte r10 = r9.c
        L96:
            r13.write(r10)
            byte r10 = r9.c
            r13.write(r10)
        L9e:
            int r12 = r12 / 3
            int r12 = r12 * 4
            if (r0 != 0) goto La5
            r3 = 0
        La5:
            int r12 = r12 + r3
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.u0.b(byte[], int, int, java.io.OutputStream):int");
    }
}
