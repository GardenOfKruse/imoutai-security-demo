package com.coralline.sea;

import android.util.Pair;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public abstract class ua {
    public static final int a = 22;
    public static final int b = 101010256;
    public static final int c = 12;
    public static final int d = 16;
    public static final int e = 20;
    public static final int f = 20;
    public static final int g = 1347094023;
    public static final int h = 65535;

    public static int a(ByteBuffer byteBuffer, int i) {
        return byteBuffer.getShort(i) & 65535;
    }

    public static Pair<ByteBuffer, Long> a(RandomAccessFile randomAccessFile) throws IOException {
        if (randomAccessFile.length() < 22) {
            return null;
        }
        Pair<ByteBuffer, Long> pairA = a(randomAccessFile, 0);
        return pairA != null ? pairA : a(randomAccessFile, h);
    }

    public static Pair<ByteBuffer, Long> a(RandomAccessFile randomAccessFile, int i) throws IOException {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException(p1.a("maxCommentSize: ", i));
        }
        long length = randomAccessFile.length();
        if (length < 22) {
            return null;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(((int) Math.min(i, length - 22)) + 22);
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        byteBufferAllocate.order(byteOrder);
        long jCapacity = length - ((long) byteBufferAllocate.capacity());
        randomAccessFile.seek(jCapacity);
        randomAccessFile.readFully(byteBufferAllocate.array(), byteBufferAllocate.arrayOffset(), byteBufferAllocate.capacity());
        int iB = b(byteBufferAllocate);
        if (iB == -1) {
            return null;
        }
        byteBufferAllocate.position(iB);
        ByteBuffer byteBufferSlice = byteBufferAllocate.slice();
        byteBufferSlice.order(byteOrder);
        return Pair.create(byteBufferSlice, Long.valueOf(jCapacity + ((long) iB)));
    }

    public static void a(ByteBuffer byteBuffer) {
        if (byteBuffer.order() != ByteOrder.LITTLE_ENDIAN) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
    }

    public static void a(ByteBuffer byteBuffer, int i, long j) {
        if (j >= 0 && j <= 4294967295L) {
            byteBuffer.putInt(byteBuffer.position() + i, (int) j);
        } else {
            throw new IllegalArgumentException("uint32 value of out range: " + j);
        }
    }

    public static void a(ByteBuffer byteBuffer, long j) {
        a(byteBuffer);
        a(byteBuffer, byteBuffer.position() + 16, j);
    }

    public static final boolean a(RandomAccessFile randomAccessFile, long j) throws IOException {
        long j2 = j - 20;
        if (j2 < 0) {
            return false;
        }
        randomAccessFile.seek(j2);
        return randomAccessFile.readInt() == 1347094023;
    }

    public static int b(ByteBuffer byteBuffer) {
        a(byteBuffer);
        int iCapacity = byteBuffer.capacity();
        if (iCapacity < 22) {
            return -1;
        }
        int i = iCapacity - 22;
        int iMin = Math.min(i, h);
        for (int i2 = 0; i2 <= iMin; i2++) {
            int i3 = i - i2;
            if (byteBuffer.getInt(i3) == 101010256 && a(byteBuffer, i3 + 20) == i2) {
                return i3;
            }
        }
        return -1;
    }

    public static long b(ByteBuffer byteBuffer, int i) {
        return ((long) byteBuffer.getInt(i)) & 4294967295L;
    }

    public static long c(ByteBuffer byteBuffer) {
        a(byteBuffer);
        return b(byteBuffer, byteBuffer.position() + 16);
    }

    public static long d(ByteBuffer byteBuffer) {
        a(byteBuffer);
        return b(byteBuffer, byteBuffer.position() + 12);
    }
}
