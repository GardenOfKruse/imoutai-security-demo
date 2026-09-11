package com.coralline.sea;

import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class v {
    /* JADX WARN: Removed duplicated region for block: B:148:0x0132 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:150:0x00f0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0128 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0104 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:164:0x013c A[EXC_TOP_SPLITTER, PHI: r0 r3
  0x013c: PHI (r0v4 java.lang.String) = (r0v0 java.lang.String), (r0v9 java.lang.String), (r0v9 java.lang.String) binds: [B:138:0x013a, B:50:0x0089, B:47:0x0083] A[DONT_GENERATE, DONT_INLINE]
  0x013c: PHI (r3v5 java.io.FileInputStream) = (r3v6 java.io.FileInputStream), (r3v9 java.io.FileInputStream), (r3v9 java.io.FileInputStream) binds: [B:138:0x013a, B:50:0x0089, B:47:0x0083] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:166:0x00fa A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0114 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:172:0x00e6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:176:0x011e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x00dc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:199:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String a(java.lang.String r9) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 325
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.v.a(java.lang.String):java.lang.String");
    }

    public static X509Certificate a(InputStream inputStream) {
        try {
            return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Certificate b(InputStream inputStream) {
        try {
            return ((Certificate[]) q7.j("sun.security.pkcs.PKCS7").a(inputStream).b("getCertificates").c())[0];
        } catch (Exception e) {
            return null;
        }
    }
}
