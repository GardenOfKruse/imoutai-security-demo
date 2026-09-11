package com.coralline.sea;

import android.content.Context;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class i4 {
    public static volatile i4 f;
    public Context a;
    public int b;
    public String c;
    public boolean d;
    public TrustManager[] e;

    public class a implements X509TrustManager {
        public a() {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            Date date = new Date();
            for (X509Certificate x509Certificate : x509CertificateArr) {
                if (x509Certificate.getNotAfter().before(date)) {
                    throw new CertificateException("Client certificate has expired");
                }
            }
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            Date date = new Date();
            for (X509Certificate x509Certificate : x509CertificateArr) {
                if (x509Certificate.getNotAfter().before(date)) {
                    throw new CertificateException("Server certificate has expired");
                }
            }
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    public class b implements HostnameVerifier {
        public final /* synthetic */ String a;

        public b(String str) {
            this.a = str;
        }

        @Override // javax.net.ssl.HostnameVerifier
        public boolean verify(String str, SSLSession sSLSession) {
            return this.a.contains(str);
        }
    }

    public class c extends SSLSocketFactory {
        public SSLSocketFactory a;

        public c(TrustManager[] trustManagerArr) throws NoSuchAlgorithmException, KeyManagementException {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, trustManagerArr, null);
            this.a = sSLContext.getSocketFactory();
        }

        public final Socket a(Socket socket) {
            ((SSLSocket) socket).setEnabledProtocols(new String[]{"TLSv1.2", "TLSv1.1"});
            return socket;
        }

        @Override // javax.net.SocketFactory
        public Socket createSocket() throws IOException {
            return a(this.a.createSocket());
        }

        @Override // javax.net.SocketFactory
        public Socket createSocket(String str, int i) throws IOException {
            return a(this.a.createSocket(str, i));
        }

        @Override // javax.net.SocketFactory
        public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException {
            return a(this.a.createSocket(str, i, inetAddress, i2));
        }

        @Override // javax.net.SocketFactory
        public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
            return a(this.a.createSocket(inetAddress, i));
        }

        @Override // javax.net.SocketFactory
        public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
            return a(this.a.createSocket(inetAddress, i, inetAddress2, i2));
        }

        @Override // javax.net.ssl.SSLSocketFactory
        public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
            return a(this.a.createSocket(socket, str, i, z));
        }

        @Override // javax.net.ssl.SSLSocketFactory
        public String[] getDefaultCipherSuites() {
            return this.a.getDefaultCipherSuites();
        }

        @Override // javax.net.ssl.SSLSocketFactory
        public String[] getSupportedCipherSuites() {
            return this.a.getSupportedCipherSuites();
        }
    }

    public i4(int i) {
        this.d = false;
        this.e = new TrustManager[]{new a()};
        this.b = i * 1000;
    }

    public i4(int i, Context context) {
        this.d = false;
        this.e = new TrustManager[]{new a()};
        this.b = i * 1000;
        this.a = context;
    }

    public static i4 a() {
        if (f == null) {
            synchronized (i4.class) {
                if (f == null) {
                    f = new i4(15, n3.a().a);
                }
            }
        }
        return f;
    }

    public void a(int i) {
        this.b = i * 1000;
    }

    public void a(String str, boolean z) {
        this.c = str;
        this.d = z;
    }

    public final boolean a(Context context) {
        try {
            String strB = l6.b(context);
            if (strB != null) {
                return !strB.equals("NETWORK_NO");
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean a(String str, byte[] bArr, s1 s1Var) {
        byte[] bArrA = a(str, bArr, bArr != null, this.b);
        s1Var.a(bArrA);
        return bArrA != null;
    }

    public byte[] a(String str, byte[] bArr) {
        return a(str, bArr, bArr != null, this.b);
    }

    public byte[] a(String str, byte[] bArr, int i) {
        return a(str, bArr, bArr != null, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x0129 A[Catch: IOException -> 0x0125, TRY_LEAVE, TryCatch #4 {IOException -> 0x0125, blocks: (B:61:0x0121, B:65:0x0129), top: B:79:0x0121 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0139 A[Catch: IOException -> 0x0135, TRY_LEAVE, TryCatch #5 {IOException -> 0x0135, blocks: (B:70:0x0131, B:74:0x0139), top: B:81:0x0131 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0121 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0131 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public byte[] a(java.lang.String r9, byte[] r10, boolean r11, int r12) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 317
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.i4.a(java.lang.String, byte[], boolean, int):byte[]");
    }
}
