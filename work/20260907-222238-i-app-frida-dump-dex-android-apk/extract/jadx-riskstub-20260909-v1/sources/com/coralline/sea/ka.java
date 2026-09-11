package com.coralline.sea;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class ka extends ra {
    public final byte[] b;
    public int c;

    public ka(X509Certificate x509Certificate, byte[] bArr) {
        super(x509Certificate);
        this.c = -1;
        this.b = bArr;
    }

    @Override // java.security.cert.Certificate
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ka)) {
            return false;
        }
        try {
            return Arrays.equals(getEncoded(), ((ka) obj).getEncoded());
        } catch (CertificateEncodingException e) {
            return false;
        }
    }

    @Override // com.coralline.sea.ra, java.security.cert.Certificate
    public byte[] getEncoded() throws CertificateEncodingException {
        return this.b;
    }

    @Override // java.security.cert.Certificate
    public int hashCode() {
        if (this.c == -1) {
            try {
                this.c = Arrays.hashCode(getEncoded());
            } catch (CertificateEncodingException e) {
                this.c = 0;
            }
        }
        return this.c;
    }
}
