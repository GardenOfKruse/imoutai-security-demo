package com.coralline.sea;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/* JADX INFO: loaded from: assets/RiskStub.dex */
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
        To view partially-correct add '--show-bad-code' argument
    */
    public static android.util.Pair<java.lang.String, java.lang.String> a(android.content.Context r8, boolean r9) throws java.lang.Throwable {
        /*
            java.lang.String r0 = ""
            java.lang.String r1 = ""
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.io.File r3 = r8.getCacheDir()
            java.lang.String r3 = r3.getAbsolutePath()
            r2.append(r3)
            java.lang.String r3 = "/tmp"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r3 = 2048(0x800, float:2.87E-42)
            byte[] r4 = new byte[r3]
            r5 = 0
            java.lang.String r8 = r8.getPackageResourcePath()     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L7c
            r6 = 0
            int r8 = com.coralline.sea.i6.b(r8, r6, r6)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L7c
            r7 = -1
            if (r7 != r8) goto L34
            android.util.Pair r8 = new android.util.Pair     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L7c
            r8.<init>(r0, r1)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L7c
            return r8
        L34:
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L7c
            r0.<init>(r2)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L7c
            if (r9 == 0) goto L46
            java.lang.String r5 = "MD5"
            java.security.MessageDigest r5 = java.security.MessageDigest.getInstance(r5)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L44
            goto L46
        L42:
            r8 = move-exception
            goto L71
        L44:
            r8 = move-exception
            goto L7e
        L46:
            int r7 = com.coralline.sea.i6.c(r8, r4, r3)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6d
            if (r7 != r3) goto L55
            r0.write(r4)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L44
            if (r9 == 0) goto L46
            r5.update(r4)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L44
            goto L46
        L55:
            if (r7 <= 0) goto L5f
            r0.write(r4, r6, r7)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L44
            if (r9 == 0) goto L5f
            r5.update(r4, r6, r7)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L44
        L5f:
            if (r9 == 0) goto L80
            byte[] r8 = r5.digest()     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L44
            java.lang.String r8 = com.coralline.sea.c4.a(r8)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L44
            r1 = r8
            goto L80
        L6b:
            r8 = move-exception
            goto L71
        L6d:
            r8 = move-exception
            goto L7e
        L6f:
            r8 = move-exception
            r0 = r5
        L71:
            if (r0 == 0) goto L7b
            r0.close()     // Catch: java.io.IOException -> L77
            goto L7b
        L77:
            r9 = move-exception
            r9.printStackTrace()
        L7b:
            throw r8
        L7c:
            r8 = move-exception
            r0 = r5
        L7e:
            if (r0 == 0) goto L88
        L80:
            r0.close()     // Catch: java.io.IOException -> L84
            goto L88
        L84:
            r8 = move-exception
            r8.printStackTrace()
        L88:
            java.lang.String r8 = a(r2)
            java.io.File r9 = new java.io.File
            r9.<init>(r2)
            r9.delete()
            boolean r9 = android.text.TextUtils.isEmpty(r1)
            if (r9 == 0) goto L9c
            java.lang.String r1 = "N/A"
        L9c:
            android.util.Pair r9 = new android.util.Pair
            r9.<init>(r8, r1)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.u.a(android.content.Context, boolean):android.util.Pair");
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
