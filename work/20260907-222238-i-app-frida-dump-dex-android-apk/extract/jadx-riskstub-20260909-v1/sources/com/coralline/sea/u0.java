package com.coralline.sea;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
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
    */
    public int b(byte[] bArr, int i, int i2, OutputStream outputStream) throws IOException {
        int i3;
        byte b;
        int i4 = i2 % 3;
        int i5 = i2 - i4;
        int i6 = i;
        while (true) {
            i3 = i + i5;
            if (i6 >= i3) {
                break;
            }
            int i7 = bArr[i6] & 255;
            int i8 = bArr[i6 + 1] & 255;
            int i9 = bArr[i6 + 2] & 255;
            outputStream.write(this.b[(i7 >>> 2) & 63]);
            outputStream.write(this.b[((i7 << 4) | (i8 >>> 4)) & 63]);
            outputStream.write(this.b[((i8 << 2) | (i9 >>> 6)) & 63]);
            outputStream.write(this.b[i9 & 63]);
            i6 += 3;
        }
        if (i4 != 1) {
            if (i4 == 2) {
                int i10 = bArr[i3] & 255;
                int i11 = bArr[i3 + 1] & 255;
                outputStream.write(this.b[(i10 >>> 2) & 63]);
                outputStream.write(this.b[((i10 << 4) | (i11 >>> 4)) & 63]);
                b = this.b[(i11 << 2) & 63];
            }
            return ((i5 / 3) * 4) + (i4 == 0 ? 0 : 4);
        }
        int i12 = bArr[i3] & 255;
        outputStream.write(this.b[(i12 >>> 2) & 63]);
        outputStream.write(this.b[(i12 << 4) & 63]);
        b = this.c;
        outputStream.write(b);
        outputStream.write(this.c);
        return ((i5 / 3) * 4) + (i4 == 0 ? 0 : 4);
    }
}
