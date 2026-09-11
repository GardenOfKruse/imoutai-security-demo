package com.coralline.sea;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
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
    */
    public static String a(String str) throws Throwable {
        Throwable th;
        ZipInputStream zipInputStream;
        ZipFile zipFile;
        FileInputStream fileInputStream;
        InputStream inputStream;
        InputStream inputStream2;
        X509Certificate x509CertificateA;
        String strB = c7.c;
        InputStream inputStream3 = null;
        inputStream = null;
        inputStream3 = null;
        InputStream inputStream4 = null;
        try {
            zipFile = new ZipFile(str);
            try {
                fileInputStream = new FileInputStream(str);
                try {
                    zipInputStream = new ZipInputStream(fileInputStream);
                    inputStream2 = null;
                    inputStream = null;
                } catch (Exception e) {
                    zipInputStream = null;
                    inputStream = null;
                } catch (Throwable th2) {
                    th = th2;
                    zipInputStream = null;
                    inputStream = null;
                }
            } catch (Exception e2) {
                zipInputStream = null;
                fileInputStream = null;
                inputStream = fileInputStream;
            } catch (Throwable th3) {
                th = th3;
                zipInputStream = null;
                fileInputStream = null;
                inputStream = fileInputStream;
                if (inputStream3 != null) {
                }
                if (inputStream != null) {
                }
                if (zipFile != null) {
                }
                if (zipInputStream != null) {
                }
                if (fileInputStream != null) {
                }
            }
        } catch (Exception e3) {
            zipInputStream = null;
            zipFile = null;
            fileInputStream = null;
        } catch (Throwable th4) {
            th = th4;
            zipInputStream = null;
            zipFile = null;
            fileInputStream = null;
        }
        while (true) {
            try {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry == null) {
                    break;
                }
                if (nextEntry.getName().startsWith("META-INF/") && nextEntry.getName().endsWith(".RSA")) {
                    InputStream inputStream5 = zipFile.getInputStream(nextEntry);
                    try {
                        inputStream = zipFile.getInputStream(nextEntry);
                        inputStream2 = inputStream5;
                    } catch (Exception e4) {
                        inputStream4 = inputStream5;
                    } catch (Throwable th5) {
                        th = th5;
                        inputStream3 = inputStream5;
                        if (inputStream3 != null) {
                            try {
                                inputStream3.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e6) {
                                e6.printStackTrace();
                            }
                        }
                        if (zipFile != null) {
                            try {
                                zipFile.close();
                            } catch (IOException e7) {
                                e7.printStackTrace();
                            }
                        }
                        if (zipInputStream != null) {
                            try {
                                zipInputStream.close();
                            } catch (IOException e8) {
                                e8.printStackTrace();
                            }
                        }
                        if (fileInputStream != null) {
                            throw th;
                        }
                        try {
                            fileInputStream.close();
                            throw th;
                        } catch (IOException e9) {
                            e9.printStackTrace();
                            throw th;
                        }
                    }
                }
            } catch (Exception e10) {
                inputStream4 = inputStream2;
            } catch (Throwable th6) {
                th = th6;
                inputStream3 = inputStream2;
            }
            if (inputStream4 != null) {
                try {
                    inputStream4.close();
                } catch (IOException e11) {
                    e11.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e12) {
                    e12.printStackTrace();
                }
            }
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e13) {
                    e13.printStackTrace();
                }
            }
            if (zipInputStream != null) {
                try {
                    zipInputStream.close();
                } catch (IOException e14) {
                    e14.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                    return strB;
                } catch (IOException e15) {
                    e15.printStackTrace();
                }
            }
            return strB;
        }
        if (inputStream2 == null || inputStream == null) {
            if (inputStream2 != null) {
                try {
                    inputStream2.close();
                } catch (IOException e16) {
                    e16.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e17) {
                    e17.printStackTrace();
                }
            }
            try {
                zipFile.close();
            } catch (IOException e18) {
                e18.printStackTrace();
            }
            try {
                zipInputStream.close();
            } catch (IOException e19) {
                e19.printStackTrace();
            }
            try {
                fileInputStream.close();
                return c7.c;
            } catch (IOException e20) {
                e20.printStackTrace();
                return c7.c;
            }
        }
        Certificate certificateB = b(inputStream2);
        byte[] encoded = certificateB != null ? certificateB.getEncoded() : null;
        if (encoded == null && (x509CertificateA = a(inputStream)) != null) {
            encoded = x509CertificateA.getEncoded();
        }
        if (encoded != null) {
            strB = c4.b(encoded);
        }
        if (inputStream2 != null) {
            try {
                inputStream2.close();
            } catch (IOException e21) {
                e21.printStackTrace();
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e22) {
                e22.printStackTrace();
            }
        }
        try {
            zipFile.close();
        } catch (IOException e23) {
            e23.printStackTrace();
        }
        try {
            zipInputStream.close();
        } catch (IOException e24) {
            e24.printStackTrace();
        }
        fileInputStream.close();
        return strB;
        if (inputStream4 != null) {
        }
        if (inputStream != null) {
        }
        if (zipFile != null) {
        }
        if (zipInputStream != null) {
        }
        if (fileInputStream != null) {
        }
        return strB;
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
