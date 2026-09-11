package com.coralline.sea;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import java.io.File;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class a0 {
    public static final String b = "android";
    public PackageManager a;

    public a0(Context context) {
        this.a = null;
        this.a = context.getPackageManager();
    }

    public long a(Context context) {
        try {
            return new File(context.getPackageResourcePath()).length();
        } catch (Exception e) {
            return 0L;
        }
    }

    public long a(Context context, String str) {
        try {
            return new File(context.getPackageManager().getApplicationInfo(str, 0).sourceDir).length();
        } catch (Exception e) {
            return 0L;
        }
    }

    public boolean a(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Package name can not be null");
        }
        try {
            ApplicationInfo applicationInfo = this.a.getApplicationInfo(str, 0);
            if (applicationInfo != null && (applicationInfo.flags & 129) != 0) {
                return b(str);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }

    public boolean b(String str) {
        Signature[] signatureArr;
        PackageInfo packageInfoA = v6.a(n3.a().a, str, 64);
        return (packageInfoA == null || (signatureArr = packageInfoA.signatures) == null || !v6.a(n3.T.a, b, 64).signatures[0].equals(signatureArr[0])) ? false : true;
    }
}
