package com.coralline.sea;

import android.util.Pair;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.Arrays;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public final class y {
    public static final int a = 1048576;
    public static final int b = 257;
    public static final int c = 258;
    public static final int d = 259;
    public static final int e = 260;
    public static final int f = 513;
    public static final int g = 514;
    public static final int h = 769;
    public static final int i = 1057;
    public static final int j = 1059;
    public static final int k = 1061;
    public static final int l = 1;
    public static final int m = 2;
    public static final int n = 3;
    public static final long o = 3617552046287187010L;
    public static final long p = 2334950737559900225L;
    public static final int q = 32;

    public static class a implements o2 {
        public final MessageDigest[] a;

        public a(MessageDigest[] messageDigestArr) {
            this.a = messageDigestArr;
        }

        @Override // com.coralline.sea.o2
        public void a(ByteBuffer byteBuffer) {
            ByteBuffer byteBufferSlice = byteBuffer.slice();
            for (MessageDigest messageDigest : this.a) {
                byteBufferSlice.position(0);
                messageDigest.update(byteBufferSlice);
            }
        }
    }

    public static int a(int i2, int i3) {
        if (i2 == 1) {
            if (i3 == 1) {
                return 0;
            }
            if (i3 == 2 || i3 == 3) {
                return -1;
            }
            throw new IllegalArgumentException(p1.a("Unknown digestAlgorithm2: ", i3));
        }
        if (i2 == 2) {
            if (i3 != 1) {
                if (i3 == 2) {
                    return 0;
                }
                if (i3 != 3) {
                    throw new IllegalArgumentException(p1.a("Unknown digestAlgorithm2: ", i3));
                }
            }
            return 1;
        }
        if (i2 != 3) {
            throw new IllegalArgumentException(p1.a("Unknown digestAlgorithm1: ", i2));
        }
        if (i3 == 1) {
            return 1;
        }
        if (i3 == 2) {
            return -1;
        }
        if (i3 == 3) {
            return 0;
        }
        throw new IllegalArgumentException(p1.a("Unknown digestAlgorithm2: ", i3));
    }

    public static long a(long j2) {
        return ((j2 + 1048576) - 1) / 1048576;
    }

    public static long a(ByteBuffer byteBuffer, long j2) throws d9 {
        long jC = ua.c(byteBuffer);
        if (jC <= j2) {
            if (ua.d(byteBuffer) + jC == j2) {
                return jC;
            }
            throw new d9("ZIP Central Directory is not immediately followed by End of Central Directory");
        }
        throw new d9("ZIP Central Directory offset out of range: " + jC + ". ZIP End of Central Directory offset: " + j2);
    }

    public static Pair<ByteBuffer, Long> a(RandomAccessFile randomAccessFile) throws IOException, d9 {
        Pair<ByteBuffer, Long> pairA = ua.a(randomAccessFile);
        if (pairA != null) {
            return pairA;
        }
        throw new d9("Not an APK file: ZIP End of Central Directory record not found");
    }

    public static Pair<ByteBuffer, Long> a(RandomAccessFile randomAccessFile, long j2) throws IOException, d9 {
        if (j2 < 32) {
            throw new d9("APK too small for APK Signing Block. ZIP Central Directory offset: " + j2);
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(24);
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        byteBufferAllocate.order(byteOrder);
        randomAccessFile.seek(j2 - ((long) byteBufferAllocate.capacity()));
        randomAccessFile.readFully(byteBufferAllocate.array(), byteBufferAllocate.arrayOffset(), byteBufferAllocate.capacity());
        if (byteBufferAllocate.getLong(8) != p || byteBufferAllocate.getLong(16) != o) {
            throw new d9("No APK Signing Block before ZIP Central Directory");
        }
        long j3 = byteBufferAllocate.getLong(0);
        if (j3 < byteBufferAllocate.capacity() || j3 > 2147483639) {
            throw new d9("APK Signing Block size out of range: " + j3);
        }
        int i2 = (int) (j3 + 8);
        long j4 = j2 - ((long) i2);
        if (j4 < 0) {
            throw new d9("APK Signing Block offset out of range: " + j4);
        }
        ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(i2);
        byteBufferAllocate2.order(byteOrder);
        randomAccessFile.seek(j4);
        randomAccessFile.readFully(byteBufferAllocate2.array(), byteBufferAllocate2.arrayOffset(), byteBufferAllocate2.capacity());
        long j5 = byteBufferAllocate2.getLong(0);
        if (j5 == j3) {
            return Pair.create(byteBufferAllocate2, Long.valueOf(j4));
        }
        throw new d9("APK Signing Block sizes in header and footer do not match: " + j5 + " vs " + j3);
    }

    public static c9 a(RandomAccessFile randomAccessFile, int i2) throws IOException, d9 {
        Pair<ByteBuffer, Long> pairA = a(randomAccessFile);
        ByteBuffer byteBuffer = (ByteBuffer) pairA.first;
        long jLongValue = ((Long) pairA.second).longValue();
        if (ua.a(randomAccessFile, jLongValue)) {
            throw new d9("ZIP64 APK not supported");
        }
        long jA = a(byteBuffer, jLongValue);
        Pair<ByteBuffer, Long> pairA2 = a(randomAccessFile, jA);
        ByteBuffer byteBuffer2 = (ByteBuffer) pairA2.first;
        return new c9(a(byteBuffer2, i2), ((Long) pairA2.second).longValue(), jA, jLongValue, byteBuffer);
    }

    public static void a(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) (i2 & 255);
        bArr[i3 + 1] = (byte) ((i2 >>> 8) & 255);
        bArr[i3 + 2] = (byte) ((i2 >>> 16) & 255);
        bArr[i3 + 3] = (byte) ((i2 >>> 24) & 255);
    }

    public static void a(ByteBuffer byteBuffer) {
        if (byteBuffer.order() != ByteOrder.LITTLE_ENDIAN) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
    }

    public static byte[] a(byte[] bArr, long j2, c9 c9Var) throws SecurityException {
        if (bArr.length != 40) {
            throw new SecurityException("Verity digest size is wrong: " + bArr.length);
        }
        ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.position(32);
        if (byteBufferOrder.getLong() == j2 - (c9Var.c - c9Var.b)) {
            return Arrays.copyOfRange(bArr, 0, 32);
        }
        throw new SecurityException("APK content size did not verify");
    }

    public static byte[][] a(int[] iArr, p2[] p2VarArr) throws DigestException {
        p2[] p2VarArr2 = p2VarArr;
        int length = p2VarArr2.length;
        long j2 = 0;
        int i2 = 0;
        long j3 = 0;
        int i3 = 0;
        while (i3 < length) {
            long jA = a(p2VarArr2[i3].size()) + j3;
            i3++;
            j3 = jA;
        }
        if (j3 >= 2097151) {
            throw new DigestException("Too many chunks: " + j3);
        }
        int i4 = (int) j3;
        byte[][] bArr = new byte[iArr.length][];
        for (int i5 = 0; i5 < iArr.length; i5++) {
            byte[] bArr2 = new byte[(b(iArr[i5]) * i4) + 5];
            bArr2[0] = 90;
            a(i4, bArr2, 1);
            bArr[i5] = bArr2;
        }
        byte[] bArr3 = new byte[5];
        bArr3[0] = -91;
        int length2 = iArr.length;
        MessageDigest[] messageDigestArr = new MessageDigest[length2];
        for (int i6 = 0; i6 < iArr.length; i6++) {
            String strA = a(iArr[i6]);
            try {
                messageDigestArr[i6] = MessageDigest.getInstance(strA);
            } catch (NoSuchAlgorithmException e2) {
                throw new RuntimeException(strA + " digest not supported", e2);
            }
        }
        a aVar = new a(messageDigestArr);
        int length3 = p2VarArr2.length;
        int i7 = 0;
        int i8 = 0;
        while (i7 < length3) {
            p2 p2Var = p2VarArr2[i7];
            a aVar2 = aVar;
            int i9 = length3;
            int i10 = i7;
            long size = p2Var.size();
            long j4 = j2;
            while (size > j2) {
                int iMin = (int) Math.min(size, 1048576L);
                a(iMin, bArr3, 1);
                for (int i11 = 0; i11 < length2; i11++) {
                    messageDigestArr[i11].update(bArr3);
                }
                a aVar3 = aVar2;
                try {
                    p2Var.a(aVar3, j4, iMin);
                    int i12 = 0;
                    while (i12 < iArr.length) {
                        int i13 = iArr[i12];
                        byte[] bArr4 = bArr3;
                        byte[] bArr5 = bArr[i12];
                        int iB = b(i13);
                        int i14 = length2;
                        MessageDigest messageDigest = messageDigestArr[i12];
                        MessageDigest[] messageDigestArr2 = messageDigestArr;
                        int iDigest = messageDigest.digest(bArr5, (i8 * iB) + 5, iB);
                        if (iDigest != iB) {
                            throw new RuntimeException("Unexpected output size of " + messageDigest.getAlgorithm() + " digest: " + iDigest);
                        }
                        i12++;
                        bArr3 = bArr4;
                        length2 = i14;
                        messageDigestArr = messageDigestArr2;
                    }
                    long j5 = iMin;
                    i8++;
                    j4 += j5;
                    size -= j5;
                    aVar2 = aVar3;
                    bArr3 = bArr3;
                    length2 = length2;
                    messageDigestArr = messageDigestArr;
                    j2 = 0;
                } catch (IOException e3) {
                    throw new DigestException("Failed to digest chunk #" + i8 + " of section #" + i2, e3);
                }
            }
            i2++;
            i7 = i10 + 1;
            length3 = i9;
            aVar = aVar2;
            p2VarArr2 = p2VarArr;
            j2 = 0;
        }
        byte[][] bArr6 = new byte[iArr.length][];
        for (int i15 = 0; i15 < iArr.length; i15++) {
            int i16 = iArr[i15];
            byte[] bArr7 = bArr[i15];
            String strA2 = a(i16);
            try {
                bArr6[i15] = MessageDigest.getInstance(strA2).digest(bArr7);
            } catch (NoSuchAlgorithmException e4) {
                throw new RuntimeException(strA2 + " digest not supported", e4);
            }
        }
        return bArr6;
    }

    public static int b(int i2, int i3) {
        return a(c(i2), c(i3));
    }

    public static ByteBuffer b(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer.remaining() < 4) {
            throw new IOException("Remaining buffer too short to contain length of length-prefixed field. Remaining: " + byteBuffer.remaining());
        }
        int i2 = byteBuffer.getInt();
        if (i2 < 0) {
            throw new IllegalArgumentException("Negative length");
        }
        if (i2 <= byteBuffer.remaining()) {
            return b(byteBuffer, i2);
        }
        throw new IOException("Length-prefixed field longer than remaining buffer. Field length: " + i2 + ", remaining: " + byteBuffer.remaining());
    }

    public static ByteBuffer b(ByteBuffer byteBuffer, int i2) throws BufferUnderflowException {
        if (i2 < 0) {
            throw new IllegalArgumentException(p1.a("size: ", i2));
        }
        int iLimit = byteBuffer.limit();
        int iPosition = byteBuffer.position();
        int i3 = i2 + iPosition;
        if (i3 < iPosition || i3 > iLimit) {
            throw new BufferUnderflowException();
        }
        byteBuffer.limit(i3);
        try {
            ByteBuffer byteBufferSlice = byteBuffer.slice();
            byteBufferSlice.order(byteBuffer.order());
            byteBuffer.position(i3);
            return byteBufferSlice;
        } finally {
            byteBuffer.limit(iLimit);
        }
    }

    public static int c(int i2) {
        if (i2 == 769) {
            return 1;
        }
        if (i2 == 1057 || i2 == 1059 || i2 == 1061) {
            return 3;
        }
        switch (i2) {
            case b /* 257 */:
            case d /* 259 */:
                return 1;
            case c /* 258 */:
            case e /* 260 */:
                return 2;
            default:
                switch (i2) {
                    case f /* 513 */:
                        return 1;
                    case g /* 514 */:
                        return 2;
                    default:
                        throw new IllegalArgumentException("Unknown signature algorithm: 0x" + Long.toHexString(i2 & (-1)));
                }
        }
    }

    public static byte[] c(ByteBuffer byteBuffer) throws IOException {
        int i2 = byteBuffer.getInt();
        if (i2 < 0) {
            throw new IOException("Negative length");
        }
        if (i2 <= byteBuffer.remaining()) {
            byte[] bArr = new byte[i2];
            byteBuffer.get(bArr);
            return bArr;
        }
        throw new IOException("Underflow while reading length-prefixed value. Length: " + i2 + ", available: " + byteBuffer.remaining());
    }

    public static String d(int i2) {
        if (i2 == 769) {
            return "DSA";
        }
        if (i2 == 1057) {
            return "RSA";
        }
        if (i2 == 1059) {
            return "EC";
        }
        if (i2 == 1061) {
            return "DSA";
        }
        switch (i2) {
            case b /* 257 */:
            case c /* 258 */:
            case d /* 259 */:
            case e /* 260 */:
                return "RSA";
            default:
                switch (i2) {
                    case f /* 513 */:
                    case g /* 514 */:
                        return "EC";
                    default:
                        throw new IllegalArgumentException("Unknown signature algorithm: 0x" + Long.toHexString(i2 & (-1)));
                }
        }
    }

    public static Pair<String, ? extends AlgorithmParameterSpec> e(int i2) {
        if (i2 != 769) {
            if (i2 != 1057) {
                if (i2 != 1059) {
                    if (i2 != 1061) {
                        switch (i2) {
                            case b /* 257 */:
                                return Pair.create("SHA256withRSA/PSS", new PSSParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, 32, 1));
                            case c /* 258 */:
                                return Pair.create("SHA512withRSA/PSS", new PSSParameterSpec("SHA-512", "MGF1", MGF1ParameterSpec.SHA512, 64, 1));
                            case d /* 259 */:
                                break;
                            case e /* 260 */:
                                return Pair.create("SHA512withRSA", null);
                            default:
                                switch (i2) {
                                    case f /* 513 */:
                                        break;
                                    case g /* 514 */:
                                        return Pair.create("SHA512withECDSA", null);
                                    default:
                                        throw new IllegalArgumentException("Unknown signature algorithm: 0x" + Long.toHexString(i2 & (-1)));
                                }
                                break;
                        }
                    }
                }
                return Pair.create("SHA256withECDSA", null);
            }
            return Pair.create("SHA256withRSA", null);
        }
        return Pair.create("SHA256withDSA", null);
    }

    public static int b(int i2) {
        if (i2 == 1) {
            return 32;
        }
        if (i2 == 2) {
            return 64;
        }
        if (i2 == 3) {
            return 32;
        }
        throw new IllegalArgumentException(p1.a("Unknown content digest algorthm: ", i2));
    }

    public static ByteBuffer a(ByteBuffer byteBuffer, int i2) throws d9 {
        a(byteBuffer);
        ByteBuffer byteBufferA = a(byteBuffer, 8, byteBuffer.capacity() - 24);
        int i3 = 0;
        while (byteBufferA.hasRemaining()) {
            i3++;
            if (byteBufferA.remaining() < 8) {
                throw new d9(p1.a("Insufficient data to read size of APK Signing Block entry #", i3));
            }
            long j2 = byteBufferA.getLong();
            if (j2 < 4 || j2 > 2147483647L) {
                throw new d9("APK Signing Block entry #" + i3 + " size out of range: " + j2);
            }
            int i4 = (int) j2;
            int iPosition = byteBufferA.position() + i4;
            if (i4 > byteBufferA.remaining()) {
                throw new d9("APK Signing Block entry #" + i3 + " size out of range: " + i4 + ", available: " + byteBufferA.remaining());
            }
            if (byteBufferA.getInt() == i2) {
                return b(byteBufferA, i4 - 4);
            }
            byteBufferA.position(iPosition);
        }
        throw new d9("No block with ID " + i2 + " in APK Signing Block.");
    }

    public static String a(int i2) {
        if (i2 == 1) {
            return "SHA-256";
        }
        if (i2 == 2) {
            return "SHA-512";
        }
        if (i2 == 3) {
            return "SHA-256";
        }
        throw new IllegalArgumentException(p1.a("Unknown content digest algorthm: ", i2));
    }

    public static ByteBuffer a(ByteBuffer byteBuffer, int i2, int i3) {
        if (i2 < 0) {
            throw new IllegalArgumentException(p1.a("start: ", i2));
        }
        if (i3 < i2) {
            throw new IllegalArgumentException("end < start: " + i3 + " < " + i2);
        }
        int iCapacity = byteBuffer.capacity();
        if (i3 > byteBuffer.capacity()) {
            throw new IllegalArgumentException("end > capacity: " + i3 + " > " + iCapacity);
        }
        int iLimit = byteBuffer.limit();
        int iPosition = byteBuffer.position();
        try {
            byteBuffer.position(0);
            byteBuffer.limit(i3);
            byteBuffer.position(i2);
            ByteBuffer byteBufferSlice = byteBuffer.slice();
            byteBufferSlice.order(byteBuffer.order());
            return byteBufferSlice;
        } finally {
            byteBuffer.position(0);
            byteBuffer.limit(iLimit);
            byteBuffer.position(iPosition);
        }
    }
}
