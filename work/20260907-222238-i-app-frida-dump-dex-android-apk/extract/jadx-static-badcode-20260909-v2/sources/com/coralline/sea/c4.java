package com.coralline.sea;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
    */
    public static String c(String str) throws Throwable {
        Throwable th;
        InputStream inputStream;
        InputStream inputStream2;
        JarFile jarFile;
        JarEntry jarEntry;
        String strA;
        String str2 = c7.c;
        JarFile jarFile2 = null;
        InputStream inputStream3 = null;
        InputStream inputStream4 = null;
        InputStream inputStream5 = null;
        InputStream inputStream6 = null;
        JarFile jarFile3 = null;
        JarFile jarFile4 = null;
        JarFile jarFile5 = null;
        try {
            jarFile = new JarFile(str, false);
            try {
                jarEntry = jarFile.getJarEntry("META-INF/MANIFEST.MF");
            } catch (IOException e) {
            } catch (SecurityException e2) {
            } catch (Exception e3) {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e4) {
            inputStream2 = null;
        } catch (SecurityException e5) {
            inputStream2 = null;
        } catch (Exception e6) {
            inputStream2 = null;
        } catch (Throwable th3) {
            th = th3;
            inputStream = null;
        }
        if (jarEntry == null) {
            try {
                jarFile.close();
                return c7.c;
            } catch (Exception e7) {
                return c7.c;
            }
        }
        inputStream2 = jarFile.getInputStream(jarEntry);
        try {
            strA = a(inputStream2);
            try {
                jarFile.close();
            } catch (Exception e8) {
            }
        } catch (IOException e9) {
            inputStream3 = inputStream2;
            inputStream2 = inputStream3;
            jarFile3 = jarFile;
            if (jarFile3 != null) {
            }
            if (inputStream2 != null) {
            }
            return str2;
        } catch (SecurityException e10) {
            inputStream4 = inputStream2;
            inputStream2 = inputStream4;
            jarFile4 = jarFile;
            if (jarFile4 != null) {
                try {
                    jarFile4.close();
                } catch (Exception e11) {
                }
            }
            if (inputStream2 != null) {
            }
            return str2;
        } catch (Exception e12) {
            inputStream5 = inputStream2;
            inputStream2 = inputStream5;
            jarFile5 = jarFile;
            if (jarFile5 != null) {
                try {
                    jarFile5.close();
                } catch (Exception e13) {
                }
            }
            if (inputStream2 != null) {
            }
            return str2;
        } catch (Throwable th4) {
            th = th4;
            inputStream6 = inputStream2;
            inputStream = inputStream6;
            jarFile2 = jarFile;
            if (jarFile2 != null) {
                try {
                    jarFile2.close();
                } catch (Exception e14) {
                }
            }
            if (inputStream == null) {
                throw th;
            }
            try {
                inputStream.close();
                throw th;
            } catch (Exception e15) {
                throw th;
            }
        }
        if (inputStream2 == null) {
            return strA;
        }
        str2 = strA;
        try {
            inputStream2.close();
            return str2;
        } catch (Exception e16) {
        }
        inputStream2 = inputStream3;
        jarFile3 = jarFile;
        if (jarFile3 != null) {
            try {
                jarFile3.close();
            } catch (Exception e17) {
            }
        }
        if (inputStream2 != null) {
            inputStream2.close();
            return str2;
        }
        return str2;
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
