package com.coralline.sea;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public final class c4 {
    public static String a(InputStream inputStream) throws Exception {
        BufferedInputStream bufferedInputStream;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            bufferedInputStream = new BufferedInputStream(inputStream);
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = bufferedInputStream.read(bArr);
                    if (i <= 0) {
                        String strA = a(messageDigest.digest());
                        bufferedInputStream.close();
                        return strA;
                    }
                    messageDigest.update(bArr, 0, i);
                }
            } catch (Throwable th) {
                th = th;
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            bufferedInputStream = null;
        }
    }

    public static String a(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(str.getBytes(), 0, str.length());
        byte[] bArrDigest = messageDigest.digest();
        StringBuilder sb = new StringBuilder(bArrDigest.length * 2);
        for (byte b : bArrDigest) {
            int i = b & 255;
            if (i <= 15) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString().toUpperCase(Locale.getDefault());
    }

    public static String a(byte[] bArr) {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] cArr2 = new char[32];
        int i = 0;
        for (int i2 = 0; i2 < 16; i2++) {
            byte b = bArr[i2];
            int i3 = i + 1;
            cArr2[i] = cArr[(b >>> 4) & 15];
            i = i3 + 1;
            cArr2[i3] = cArr[b & z3.h];
        }
        return new String(cArr2);
    }

    public static String b(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.update(str.getBytes(), 0, str.length());
        byte[] bArrDigest = messageDigest.digest();
        StringBuilder sb = new StringBuilder(bArrDigest.length * 2);
        for (byte b : bArrDigest) {
            int i = b & 255;
            if (i <= 15) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString().toUpperCase(Locale.getDefault());
    }

    public static String b(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr);
            return a(messageDigest.digest());
        } catch (Exception e) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:75:0x0069 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String c(java.lang.String r4) throws java.lang.Throwable {
        /*
            java.lang.String r0 = ""
            r1 = 0
            java.util.jar.JarFile r2 = new java.util.jar.JarFile     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L59 java.io.IOException -> L65 java.lang.SecurityException -> L71
            r3 = 0
            r2.<init>(r4, r3)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L59 java.io.IOException -> L65 java.lang.SecurityException -> L71
            java.lang.String r4 = "META-INF/MANIFEST.MF"
            java.util.jar.JarEntry r4 = r2.getJarEntry(r4)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b java.io.IOException -> L3f java.lang.SecurityException -> L43
            if (r4 != 0) goto L17
            r2.close()     // Catch: java.lang.Exception -> L15
            return r0
        L15:
            r4 = move-exception
            return r0
        L17:
            java.io.InputStream r4 = r2.getInputStream(r4)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b java.io.IOException -> L3f java.lang.SecurityException -> L43
            java.lang.String r1 = a(r4)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2e java.io.IOException -> L31 java.lang.SecurityException -> L34
            r2.close()     // Catch: java.lang.Exception -> L23
            goto L24
        L23:
            r0 = move-exception
        L24:
            if (r4 == 0) goto L29
            r0 = r1
            goto L7c
        L29:
            r0 = r1
            return r0
        L2b:
            r0 = move-exception
            r1 = r4
            goto L38
        L2e:
            r1 = move-exception
            r1 = r4
            goto L3c
        L31:
            r1 = move-exception
            r1 = r4
            goto L40
        L34:
            r1 = move-exception
            r1 = r4
            goto L44
        L37:
            r0 = move-exception
        L38:
            r4 = r1
            r1 = r2
            goto L4a
        L3b:
            r4 = move-exception
        L3c:
            r4 = r1
            r1 = r2
            goto L5b
        L3f:
            r4 = move-exception
        L40:
            r4 = r1
            r1 = r2
            goto L67
        L43:
            r4 = move-exception
        L44:
            r4 = r1
            r1 = r2
            goto L73
        L47:
            r4 = move-exception
            r0 = r4
            r4 = r1
        L4a:
            if (r1 == 0) goto L51
            r1.close()     // Catch: java.lang.Exception -> L50
            goto L51
        L50:
            r1 = move-exception
        L51:
            if (r4 == 0) goto L58
            r4.close()     // Catch: java.lang.Exception -> L57
            goto L58
        L57:
            r4 = move-exception
        L58:
            throw r0
        L59:
            r4 = move-exception
            r4 = r1
        L5b:
            if (r1 == 0) goto L62
            r1.close()     // Catch: java.lang.Exception -> L61
            goto L62
        L61:
            r1 = move-exception
        L62:
            if (r4 == 0) goto L81
            goto L7c
        L65:
            r4 = move-exception
            r4 = r1
        L67:
            if (r1 == 0) goto L6e
            r1.close()     // Catch: java.lang.Exception -> L6d
            goto L6e
        L6d:
            r1 = move-exception
        L6e:
            if (r4 == 0) goto L81
            goto L7c
        L71:
            r4 = move-exception
            r4 = r1
        L73:
            if (r1 == 0) goto L7a
            r1.close()     // Catch: java.lang.Exception -> L79
            goto L7a
        L79:
            r1 = move-exception
        L7a:
            if (r4 == 0) goto L81
        L7c:
            r4.close()     // Catch: java.lang.Exception -> L80
            return r0
        L80:
            r4 = move-exception
        L81:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.c4.c(java.lang.String):java.lang.String");
    }

    public static String d(String str) throws Throwable {
        String str2 = c7.c;
        File file = new File(str);
        if (!file.exists()) {
            return c7.c;
        }
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                String strA = a(fileInputStream2);
                try {
                    fileInputStream2.close();
                    return strA;
                } catch (IOException e) {
                    str2 = strA;
                    e = e;
                    e.printStackTrace();
                    return str2;
                }
            } catch (Exception e2) {
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                        return c7.c;
                    } catch (IOException e3) {
                        e = e3;
                        e.printStackTrace();
                        return str2;
                    }
                }
                return str2;
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
