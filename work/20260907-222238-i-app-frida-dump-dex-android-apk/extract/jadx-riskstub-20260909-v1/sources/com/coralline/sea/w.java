package com.coralline.sea;

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

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class w {
    public static final String a = "cert_v2";
    public static final int b = 1896449818;

    public static c9 a(RandomAccessFile randomAccessFile) throws IOException, d9 {
        return y.a(randomAccessFile, 1896449818);
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
    public static X509Certificate[][] a(String str) throws Exception {
        c9 c9VarA = y.a(new RandomAccessFile(str, "r"), 1896449818);
        ArrayList arrayList = new ArrayList();
        try {
            ByteBuffer byteBufferB = y.b(c9VarA.a);
            int i = 0;
            while (byteBufferB.hasRemaining()) {
                i++;
                try {
                    arrayList.add(a(y.b(byteBufferB)));
                } catch (IOException | SecurityException | BufferUnderflowException e) {
                    throw new SecurityException("Failed to parse/verify signer #" + i + " block", e);
                }
            }
            if (i >= 1) {
                return (X509Certificate[][]) arrayList.toArray(new X509Certificate[arrayList.size()][]);
            }
            throw new SecurityException("No signers found");
        } catch (IOException e2) {
            throw new SecurityException("Failed to read list of signers", e2);
        }
    }

    @RequiresApi(api = m5.b.q)
    public static String b(String str) {
        if (!c(str)) {
            return c7.c;
        }
        try {
            return c4.b(a(str)[0][0].getEncoded());
        } catch (Exception e) {
            return c7.c;
        }
    }

    @RequiresApi(api = m5.b.q)
    public static boolean c(String str) {
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
    public static X509Certificate[] a(ByteBuffer byteBuffer) throws IOException, SecurityException {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            ByteBuffer byteBufferB = y.b(byteBuffer);
            ByteBuffer byteBufferB2 = y.b(byteBuffer);
            byte[] bArrC = y.c(byteBuffer);
            ArrayList arrayList = new ArrayList();
            int i = 0;
            int i2 = 0;
            int i3 = -1;
            while (byteBufferB2.hasRemaining()) {
                i2++;
                try {
                    ByteBuffer byteBufferB3 = y.b(byteBufferB2);
                    if (byteBufferB3.remaining() < 8) {
                        throw new SecurityException("Signature record too short");
                    }
                    int i4 = byteBufferB3.getInt();
                    arrayList.add(Integer.valueOf(i4));
                    if (a(i4) && (i3 == -1 || y.b(i4, i3) > 0)) {
                        y.c(byteBufferB3);
                        i3 = i4;
                    }
                } catch (IOException | BufferUnderflowException e) {
                    throw new SecurityException(p1.a("Failed to parse signature record #", i2), e);
                }
            }
            if (i3 == -1) {
                if (i2 == 0) {
                    throw new SecurityException("No signatures found");
                }
                throw new SecurityException("No supported signatures found");
            }
            String strD = y.d(i3);
            Pair<String, ? extends AlgorithmParameterSpec> pairE = y.e(i3);
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
                byteBufferB.clear();
                ByteBuffer byteBufferB4 = y.b(byteBufferB);
                ArrayList arrayList2 = new ArrayList();
                int i5 = 0;
                while (byteBufferB4.hasRemaining()) {
                    i5++;
                    try {
                        ByteBuffer byteBufferB5 = y.b(byteBufferB4);
                        if (byteBufferB5.remaining() < 8) {
                            throw new IOException("Record too short");
                        }
                        arrayList2.add(Integer.valueOf(byteBufferB5.getInt()));
                    } catch (IOException | BufferUnderflowException e2) {
                        throw new IOException(p1.a("Failed to parse digest record #", i5), e2);
                    }
                }
                if (!arrayList.equals(arrayList2)) {
                    throw new SecurityException("Signature algorithms don't match between digests and signatures records");
                }
                ByteBuffer byteBufferB6 = y.b(byteBufferB);
                ArrayList arrayList3 = new ArrayList();
                while (byteBufferB6.hasRemaining()) {
                    i++;
                    byte[] bArrC2 = y.c(byteBufferB6);
                    try {
                        arrayList3.add(new ka((X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(bArrC2)), bArrC2));
                    } catch (CertificateException e3) {
                        throw new SecurityException(p1.a("Failed to decode certificate #", i), e3);
                    }
                }
                if (arrayList3.isEmpty()) {
                    throw new SecurityException("No certificates listed");
                }
                return (X509Certificate[]) arrayList3.toArray(new X509Certificate[arrayList3.size()]);
            } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException e4) {
                throw new SecurityException("Failed to verify " + str + " signature", e4);
            }
        } catch (CertificateException e5) {
            throw new RuntimeException("Failed to obtain X.509 CertificateFactory", e5);
        }
    }
}
