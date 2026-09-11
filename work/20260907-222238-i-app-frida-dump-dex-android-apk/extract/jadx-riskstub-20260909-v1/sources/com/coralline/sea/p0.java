package com.coralline.sea;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class p0 extends OutputStream {
    public static final int g = 1024;
    public static final byte[] h = new byte[0];
    public final List<byte[]> a;
    public int b;
    public int c;
    public byte[] d;
    public int e;
    public boolean f;

    public p0() {
        this(1024);
    }

    public p0(int i) {
        this.a = new ArrayList();
        this.f = true;
        if (i < 0) {
            throw new IllegalArgumentException(p1.a("Negative initial size: ", i));
        }
        synchronized (this) {
            a(i);
        }
    }

    public static InputStream a(InputStream inputStream) throws IOException {
        return a(inputStream, 1024);
    }

    public static InputStream a(InputStream inputStream, int i) throws IOException {
        p0 p0Var = new p0(i);
        p0Var.b(inputStream);
        return p0Var.d();
    }

    public String a(String str) throws UnsupportedEncodingException {
        return new String(c(), str);
    }

    public String a(Charset charset) {
        return new String(c(), charset);
    }

    public synchronized void a() {
        this.e = 0;
        this.c = 0;
        this.b = 0;
        if (this.f) {
            this.d = this.a.get(0);
        } else {
            this.d = null;
            int length = this.a.get(0).length;
            this.a.clear();
            a(length);
            this.f = true;
        }
    }

    public final void a(int i) {
        int length;
        if (this.b < this.a.size() - 1) {
            this.c += this.d.length;
            int i2 = this.b + 1;
            this.b = i2;
            this.d = this.a.get(i2);
            return;
        }
        byte[] bArr = this.d;
        if (bArr == null) {
            length = 0;
        } else {
            i = Math.max(bArr.length << 1, i - this.c);
            length = this.c + this.d.length;
        }
        this.c = length;
        this.b++;
        byte[] bArr2 = new byte[i];
        this.d = bArr2;
        this.a.add(bArr2);
    }

    public synchronized void a(OutputStream outputStream) throws IOException {
        int i = this.e;
        for (byte[] bArr : this.a) {
            int iMin = Math.min(bArr.length, i);
            outputStream.write(bArr, 0, iMin);
            i -= iMin;
            if (i == 0) {
                break;
            }
        }
    }

    public synchronized int b() {
        return this.e;
    }

    public synchronized int b(InputStream inputStream) throws IOException {
        int i;
        int i2 = this.e - this.c;
        byte[] bArr = this.d;
        int i3 = inputStream.read(bArr, i2, bArr.length - i2);
        i = 0;
        while (i3 != -1) {
            i += i3;
            i2 += i3;
            this.e += i3;
            byte[] bArr2 = this.d;
            if (i2 == bArr2.length) {
                a(bArr2.length);
                i2 = 0;
            }
            byte[] bArr3 = this.d;
            i3 = inputStream.read(bArr3, i2, bArr3.length - i2);
        }
        return i;
    }

    public synchronized byte[] c() {
        int i = this.e;
        if (i == 0) {
            return h;
        }
        byte[] bArr = new byte[i];
        int i2 = 0;
        for (byte[] bArr2 : this.a) {
            int iMin = Math.min(bArr2.length, i);
            System.arraycopy(bArr2, 0, bArr, i2, iMin);
            i2 += iMin;
            i -= iMin;
            if (i == 0) {
                break;
            }
        }
        return bArr;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    public synchronized InputStream d() {
        int i = this.e;
        if (i == 0) {
            return new w1();
        }
        ArrayList arrayList = new ArrayList(this.a.size());
        for (byte[] bArr : this.a) {
            int iMin = Math.min(bArr.length, i);
            arrayList.add(new ByteArrayInputStream(bArr, 0, iMin));
            i -= iMin;
            if (i == 0) {
                break;
            }
        }
        this.f = false;
        return new SequenceInputStream(Collections.enumeration(arrayList));
    }

    @Deprecated
    public String toString() {
        return new String(c(), Charset.defaultCharset());
    }

    @Override // java.io.OutputStream
    public synchronized void write(int i) {
        int i2 = this.e;
        int i3 = i2 - this.c;
        if (i3 == this.d.length) {
            a(i2 + 1);
            i3 = 0;
        }
        this.d[i3] = (byte) i;
        this.e++;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) {
        int i3;
        if (i < 0 || i > bArr.length || i2 < 0 || (i3 = i + i2) > bArr.length || i3 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 == 0) {
            return;
        }
        synchronized (this) {
            int i4 = this.e;
            int i5 = i4 + i2;
            int i6 = i4 - this.c;
            while (i2 > 0) {
                int iMin = Math.min(i2, this.d.length - i6);
                System.arraycopy(bArr, i3 - i2, this.d, i6, iMin);
                i2 -= iMin;
                if (i2 > 0) {
                    a(i5);
                    i6 = 0;
                }
            }
            this.e = i5;
        }
    }
}
