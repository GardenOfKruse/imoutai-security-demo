package com.coralline.sea;

import android.content.Context;
import android.os.Build;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
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
    */
    public byte[] a(String str, byte[] bArr, boolean z, int i) throws Throwable {
        OutputStream outputStream;
        InputStream inputStream;
        String str2;
        String str3;
        OutputStream outputStream2;
        SSLSocketFactory socketFactory;
        OutputStream outputStream3 = null;
        try {
            try {
                int i2 = i / 1000;
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                    if (httpURLConnection instanceof HttpsURLConnection) {
                        if (Build.VERSION.SDK_INT <= 19) {
                            socketFactory = new c(this.e);
                        } else {
                            SSLContext sSLContext = SSLContext.getInstance("SSL");
                            sSLContext.init(null, this.e, new SecureRandom());
                            socketFactory = sSLContext.getSocketFactory();
                        }
                        ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(socketFactory);
                        ((HttpsURLConnection) httpURLConnection).setHostnameVerifier(new b(str));
                    }
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setConnectTimeout(i);
                    httpURLConnection.setReadTimeout(i);
                    httpURLConnection.setRequestProperty("Connection", "close");
                    if (z) {
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setRequestProperty("Content-Length", c7.c + bArr.length);
                        if (n3.a().c) {
                            str2 = "Content-Type";
                            str3 = "application/x-www-form-urlencoded";
                        } else {
                            str2 = "Content-Type";
                            str3 = "application/text";
                        }
                        httpURLConnection.setRequestProperty(str2, str3);
                        httpURLConnection.setRequestProperty("Charset", s0.f);
                        outputStream2 = httpURLConnection.getOutputStream();
                        try {
                            outputStream2.write(bArr);
                        } catch (Exception e) {
                            e = e;
                            inputStream = null;
                            Exception exc = e;
                            outputStream = outputStream2;
                            e = exc;
                            try {
                                x9.a("-207#" + e.getMessage());
                                if (outputStream != null) {
                                }
                                if (inputStream != null) {
                                }
                                return null;
                            } catch (Throwable th) {
                                th = th;
                                outputStream3 = outputStream;
                                if (outputStream3 != null) {
                                    try {
                                        outputStream3.close();
                                    } catch (IOException e2) {
                                        throw th;
                                    }
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            inputStream = null;
                            outputStream3 = outputStream2;
                            th = th;
                            if (outputStream3 != null) {
                            }
                            if (inputStream != null) {
                            }
                            throw th;
                        }
                    } else {
                        httpURLConnection.setRequestMethod("GET");
                        outputStream2 = null;
                    }
                    int responseCode = httpURLConnection.getResponseCode();
                    if (200 != responseCode) {
                        httpURLConnection.getResponseCode();
                        x9.a("-207#" + responseCode);
                        if (outputStream2 != null) {
                            try {
                                outputStream2.close();
                                return null;
                            } catch (IOException e3) {
                            }
                        }
                        return null;
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    inputStream = httpURLConnection.getInputStream();
                    try {
                        byte[] bArr2 = new byte[1024];
                        while (true) {
                            int i3 = inputStream.read(bArr2, 0, 1024);
                            if (i3 == -1) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr2, 0, i3);
                        }
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        if (outputStream2 != null) {
                            try {
                                outputStream2.close();
                            } catch (IOException e4) {
                                return byteArray;
                            }
                        }
                        inputStream.close();
                        return byteArray;
                    } catch (Exception e5) {
                        e = e5;
                        Exception exc2 = e;
                        outputStream = outputStream2;
                        e = exc2;
                        x9.a("-207#" + e.getMessage());
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e6) {
                                return null;
                            }
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        return null;
                    } catch (Throwable th3) {
                        th = th3;
                        outputStream3 = outputStream2;
                        th = th;
                        if (outputStream3 != null) {
                        }
                        if (inputStream != null) {
                        }
                        throw th;
                    }
                } catch (Exception e7) {
                    e7.getMessage();
                    return null;
                }
            } catch (Throwable th4) {
                th = th4;
                inputStream = null;
            }
        } catch (Exception e8) {
            e = e8;
            outputStream = null;
            inputStream = null;
        }
    }
}
