package com.coralline.sea;

import android.util.ArrayMap;
import android.util.Pair;
import androidx.annotation.RequiresApi;
import com.coralline.sea.m5;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class x {
    public static final int a = 2;
    public static final int b = 1896449818;
    public static final int c = -1091571699;

    public static class a {
        public final X509Certificate[][] a;
        public final byte[] b;

        public a(X509Certificate[][] x509CertificateArr, byte[] bArr) {
            this.a = x509CertificateArr;
            this.b = bArr;
        }
    }

    public static c9 a(RandomAccessFile randomAccessFile) throws IOException, d9 {
        return y.a(randomAccessFile, 1896449818);
    }

    @RequiresApi(api = m5.b.q)
    public static a a(RandomAccessFile randomAccessFile, c9 c9Var, boolean z) throws SecurityException, IOException {
        ArrayMap arrayMap = new ArrayMap();
        ArrayList arrayList = new ArrayList();
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            try {
                ByteBuffer byteBufferB = y.b(c9Var.a);
                int i = 0;
                while (byteBufferB.hasRemaining()) {
                    i++;
                    try {
                        arrayList.add(a(y.b(byteBufferB), arrayMap, certificateFactory));
                    } catch (IOException | SecurityException | BufferUnderflowException e) {
                        throw new SecurityException("Failed to parse/verify signer #" + i + " block", e);
                    }
                }
                if (i < 1) {
                    throw new SecurityException("No signers found");
                }
                if (arrayMap.isEmpty()) {
                    throw new SecurityException("No content digests found");
                }
                return new a((X509Certificate[][]) arrayList.toArray(new X509Certificate[arrayList.size()][]), arrayMap.containsKey(3) ? y.a((byte[]) arrayMap.get(3), randomAccessFile.length(), c9Var) : null);
            } catch (IOException e2) {
                throw new SecurityException("Failed to read list of signers", e2);
            }
        } catch (CertificateException e3) {
            throw new RuntimeException("Failed to obtain X.509 CertificateFactory", e3);
        }
    }

    @RequiresApi(api = m5.b.q)
    public static a a(String str, boolean z) throws IOException, SecurityException, d9 {
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
        try {
            a aVarA = a(randomAccessFile, z);
            randomAccessFile.close();
            return aVarA;
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
            } catch (Throwable th2) {
            }
            throw th;
        }
    }

    public static void a(ByteBuffer byteBuffer) throws IOException, SecurityException {
        while (byteBuffer.hasRemaining()) {
            ByteBuffer byteBufferB = y.b(byteBuffer);
            if (byteBufferB.remaining() < 4) {
                throw new IOException("Remaining buffer too short to contain additional attribute ID. Remaining: " + byteBufferB.remaining());
            }
            if (byteBufferB.getInt() == -1091571699) {
                if (byteBufferB.remaining() < 4) {
                    throw new IOException("V2 Signature Scheme Stripping Protection Attribute  value too small.  Expected 4 bytes, but found " + byteBufferB.remaining());
                }
                byteBufferB.getInt();
            }
        }
    }

    public static boolean a(int i) {
        if (i == 769 || i == 1057 || i == 1059 || i == 1061) {
            return true;
        }
        switch (i) {
            case y.b /* 257 */:
            case y.c /* 258 */:
            case y.d /* 259 */:
            case y.e /* 260 */:
                return true;
            default:
                switch (i) {
                    case y.f /* 513 */:
                    case y.g /* 514 */:
                        return true;
                    default:
                        return false;
                }
        }
    }

    @RequiresApi(api = m5.b.q)
    public static byte[] a(String str) throws IOException, SecurityException, d9 {
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
        try {
            y.a(randomAccessFile, 1896449818);
            byte[] bArr = a(randomAccessFile, false).b;
            randomAccessFile.close();
            return bArr;
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
            } catch (Throwable th2) {
            }
            throw th;
        }
    }

    @RequiresApi(api = m5.b.q)
    public static boolean b(String str) {
        try {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
                try {
                    y.a(randomAccessFile, 1896449818);
                    randomAccessFile.close();
                    return true;
                } catch (Throwable th) {
                    try {
                        randomAccessFile.close();
                    } catch (Throwable th2) {
                    }
                    throw th;
                }
            } catch (d9 e) {
                return false;
            }
        } catch (FileNotFoundException | IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @RequiresApi(api = m5.b.q)
    public static X509Certificate[][] c(String str) throws SecurityException, IOException, d9 {
        return a(str, false).a;
    }

    @RequiresApi(api = m5.b.q)
    public static X509Certificate[][] d(String str) throws SecurityException, IOException, d9 {
        return a(str, true).a;
    }

    @RequiresApi(api = m5.b.q)
    public static a a(RandomAccessFile randomAccessFile, boolean z) throws SecurityException, IOException, d9 {
        return a(randomAccessFile, y.a(randomAccessFile, 1896449818), z);
    }

    public static X509Certificate[] a(ByteBuffer byteBuffer, Map<Integer, byte[]> map, CertificateFactory certificateFactory) throws IOException, SecurityException {
        ByteBuffer byteBufferB = y.b(byteBuffer);
        ByteBuffer byteBufferB2 = y.b(byteBuffer);
        byte[] bArrC = y.c(byteBuffer);
        ArrayList arrayList = new ArrayList();
        byte[] bArrC2 = null;
        int i = 0;
        int i2 = -1;
        while (byteBufferB2.hasRemaining()) {
            i++;
            try {
                ByteBuffer byteBufferB3 = y.b(byteBufferB2);
                if (byteBufferB3.remaining() < 8) {
                    throw new SecurityException("Signature record too short");
                }
                int i3 = byteBufferB3.getInt();
                arrayList.add(Integer.valueOf(i3));
                if (a(i3) && (i2 == -1 || y.b(i3, i2) > 0)) {
                    bArrC2 = y.c(byteBufferB3);
                    i2 = i3;
                }
            } catch (IOException | BufferUnderflowException e) {
                throw new SecurityException(p1.a("Failed to parse signature record #", i), e);
            }
        }
        if (i2 == -1) {
            if (i == 0) {
                throw new SecurityException("No signatures found");
            }
            throw new SecurityException("No supported signatures found");
        }
        String strD = y.d(i2);
        Pair<String, ? extends AlgorithmParameterSpec> pairE = y.e(i2);
        String str = (String) pairE.first;
        AlgorithmParameterSpec algorithmParameterSpec = (AlgorithmParameterSpec) pairE.second;
        try {
            PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(strD).generatePublic(new X509EncodedKeySpec(bArrC));
            Signature signature = Signature.getInstance(str);
            signature.initVerify(publicKeyGeneratePublic);
            if (algorithmParameterSpec != null) {
                signature.setParameter(algorithmParameterSpec);
            }
            signature.update(byteBufferB);
            if (!signature.verify(bArrC2)) {
                throw new SecurityException(str + " signature did not verify");
            }
            byteBufferB.clear();
            ByteBuffer byteBufferB4 = y.b(byteBufferB);
            ArrayList arrayList2 = new ArrayList();
            byte[] bArrC3 = null;
            int i4 = 0;
            while (byteBufferB4.hasRemaining()) {
                i4++;
                try {
                    ByteBuffer byteBufferB5 = y.b(byteBufferB4);
                    if (byteBufferB5.remaining() < 8) {
                        throw new IOException("Record too short");
                    }
                    int i5 = byteBufferB5.getInt();
                    arrayList2.add(Integer.valueOf(i5));
                    if (i5 == i2) {
                        bArrC3 = y.c(byteBufferB5);
                    }
                } catch (IOException | BufferUnderflowException e2) {
                    throw new IOException(p1.a("Failed to parse digest record #", i4), e2);
                }
            }
            if (!arrayList.equals(arrayList2)) {
                throw new SecurityException("Signature algorithms don't match between digests and signatures records");
            }
            int iC = y.c(i2);
            byte[] bArrPut = map.put(Integer.valueOf(iC), bArrC3);
            if (bArrPut != null && !MessageDigest.isEqual(bArrPut, bArrC3)) {
                throw new SecurityException(y.a(iC) + " contents digest does not match the digest specified by a preceding signer");
            }
            ByteBuffer byteBufferB6 = y.b(byteBufferB);
            ArrayList arrayList3 = new ArrayList();
            int i6 = 0;
            while (byteBufferB6.hasRemaining()) {
                i6++;
                byte[] bArrC4 = y.c(byteBufferB6);
                try {
                    arrayList3.add(new ka((X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(bArrC4)), bArrC4));
                } catch (CertificateException e3) {
                    throw new SecurityException(p1.a("Failed to decode certificate #", i6), e3);
                }
            }
            if (arrayList3.isEmpty()) {
                throw new SecurityException("No certificates listed");
            }
            if (!Arrays.equals(bArrC, ((X509Certificate) arrayList3.get(0)).getPublicKey().getEncoded())) {
                throw new SecurityException("Public key mismatch between certificate and signature record");
            }
            a(y.b(byteBufferB));
            return (X509Certificate[]) arrayList3.toArray(new X509Certificate[arrayList3.size()]);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException e4) {
            throw new SecurityException("Failed to verify " + str + " signature", e4);
        }
    }
}
