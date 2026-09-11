package com.coralline.sea;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class u {
    public static Object a;

    public static synchronized PackageInfo a(String str, int i) {
        try {
            if (a == null) {
                a = q7.j("android.content.pm.IPackageManager$Stub").a("asInterface", (IBinder) q7.a(q7.j("android.os.ServiceManagerNative").a("asInterface", (IBinder) q7.j("com.android.internal.os.BinderInternal").b("getContextObject").c()).c()).a(y8.c, "package").c()).c();
            }
            if (n3.a().P >= 33) {
                return (PackageInfo) q7.a(a).a("getPackageInfo", str, 134217728L, 0).c();
            }
            return (PackageInfo) q7.a(a).a("getPackageInfo", str, Integer.valueOf(i), 0).c();
        } catch (Exception e) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0073 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Pair<String, String> a(Context context, boolean z) throws Throwable {
        FileOutputStream fileOutputStream;
        int iB;
        int iC;
        String strA = c7.c;
        String str = context.getCacheDir().getAbsolutePath() + "/tmp";
        byte[] bArr = new byte[2048];
        MessageDigest messageDigest = null;
        try {
            iB = i6.b(context.getPackageResourcePath(), 0, 0);
        } catch (Exception e) {
            fileOutputStream = null;
        } catch (Throwable th) {
            th = th;
            fileOutputStream = null;
        }
        if (-1 == iB) {
            return new Pair<>(c7.c, c7.c);
        }
        fileOutputStream = new FileOutputStream(str);
        if (z) {
            try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (Exception e2) {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                String strA2 = a(str);
                new File(str).delete();
                if (TextUtils.isEmpty(strA)) {
                }
                return new Pair<>(strA2, strA);
            } catch (Throwable th2) {
                th = th2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                throw th;
            }
        }
        while (true) {
            try {
                iC = i6.c(iB, bArr, 2048);
                if (iC != 2048) {
                    break;
                }
                fileOutputStream.write(bArr);
                if (z) {
                    messageDigest.update(bArr);
                }
            } catch (Exception e4) {
                if (fileOutputStream != null) {
                }
                String strA22 = a(str);
                new File(str).delete();
                if (TextUtils.isEmpty(strA)) {
                }
                return new Pair<>(strA22, strA);
            } catch (Throwable th3) {
                th = th3;
                if (fileOutputStream != null) {
                }
                throw th;
            }
        }
        if (iC > 0) {
            fileOutputStream.write(bArr, 0, iC);
            if (z) {
                messageDigest.update(bArr, 0, iC);
            }
        }
        if (z) {
            strA = c4.a(messageDigest.digest());
        }
        try {
            fileOutputStream.close();
        } catch (IOException e5) {
            e5.printStackTrace();
        }
        String strA222 = a(str);
        new File(str).delete();
        if (TextUtils.isEmpty(strA)) {
            strA = i2.b;
        }
        return new Pair<>(strA222, strA);
    }

    public static String a() {
        Context context = n3.a().a;
        Pair<Boolean, Boolean> pairD = la.d();
        return (((Boolean) pairD.first).booleanValue() || ((Boolean) pairD.second).booleanValue()) ? b(context) : a(context);
    }

    public static String a(Context context) throws Throwable {
        PackageInfo packageInfoA;
        Signature[] signatureArr;
        ByteArrayInputStream byteArrayInputStream;
        String str = c7.c;
        PackageInfo packageInfoA2 = a(context.getPackageName(), 64);
        if (packageInfoA2 == null) {
            try {
                packageInfoA = v6.a(context, context.getPackageName(), 64);
            } catch (Exception e) {
                packageInfoA = packageInfoA2;
            }
        } else {
            packageInfoA = packageInfoA2;
        }
        ByteArrayInputStream byteArrayInputStream2 = null;
        try {
            try {
                if (n3.a().P >= 33) {
                    try {
                        signatureArr = (Signature[]) q7.a(q7.a(packageInfoA).c("signingInfo").c()).b("getApkContentsSigners").c();
                    } catch (Exception e2) {
                        signatureArr = null;
                    }
                } else {
                    signatureArr = packageInfoA.signatures;
                }
                byteArrayInputStream = new ByteArrayInputStream(signatureArr[0].toByteArray());
            } catch (Exception e3) {
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            String strB = c4.b(v.a(byteArrayInputStream).getEncoded());
            try {
                byteArrayInputStream.close();
            } catch (IOException e4) {
            }
            str = strB;
        } catch (Exception e5) {
            byteArrayInputStream2 = byteArrayInputStream;
            if (byteArrayInputStream2 != null) {
                try {
                    byteArrayInputStream2.close();
                } catch (IOException e6) {
                }
            }
        } catch (Throwable th2) {
            th = th2;
            byteArrayInputStream2 = byteArrayInputStream;
            if (byteArrayInputStream2 != null) {
                try {
                    byteArrayInputStream2.close();
                } catch (IOException e7) {
                }
            }
            throw th;
        }
        return TextUtils.isEmpty(str) ? i2.b : str;
    }

    public static String a(String str) throws Throwable {
        String strA = v.a(str);
        if (TextUtils.isEmpty(strA) && Build.VERSION.SDK_INT >= 19) {
            strA = w.b(str);
        }
        return TextUtils.isEmpty(strA) ? i2.b : strA;
    }

    public static String b(Context context) {
        return (String) a(context, false).first;
    }
}
