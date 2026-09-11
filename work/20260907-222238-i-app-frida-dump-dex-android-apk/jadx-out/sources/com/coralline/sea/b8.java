package com.coralline.sea;

import android.os.Build;
import java.security.SecureRandom;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class b8 extends c7 {
    public static String i = "";
    public static boolean j = false;
    public static long k;

    public static String a() {
        return a(true);
    }

    public static synchronized String a(boolean z) {
        if (!c7.b(d2.D)) {
            if (!z) {
                return c7.c;
            }
            return i;
        }
        if (j && i.equals(c7.c)) {
            return i;
        }
        if (k > 0) {
            if ((System.currentTimeMillis() / 1000) - k < new SecureRandom().nextInt(10) + 70) {
                return i;
            }
        }
        if (Build.VERSION.SDK_INT < 26) {
            k = System.currentTimeMillis() / 1000;
            i = ja.i("ps");
        } else {
            k = System.currentTimeMillis() / 1000;
            i = ja.i("ps -ef");
        }
        j = true;
        if (i == null) {
            i = c7.c;
        }
        return i;
    }

    public static String b() {
        return i;
    }
}
