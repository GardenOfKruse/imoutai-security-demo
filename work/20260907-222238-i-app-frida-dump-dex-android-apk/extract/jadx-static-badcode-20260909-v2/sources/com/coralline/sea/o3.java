package com.coralline.sea;

import android.content.Context;
import android.os.Environment;
import java.io.File;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class o3 extends c7 {
    public static String i = "";
    public static File j;
    public static File k;

    public static File a(Context context, String str) {
        return a(context, str, true);
    }

    public static synchronized File a(Context context, String str, boolean z) {
        if (Environment.DIRECTORY_DOWNLOADS.equals(str)) {
            if (c7.c(d2.C)) {
                File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                j = externalFilesDir;
                return externalFilesDir;
            }
            if (z) {
                return j;
            }
        } else if (Environment.DIRECTORY_MOVIES.equals(str)) {
            if (c7.c(d2.C)) {
                File externalFilesDir2 = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
                k = externalFilesDir2;
                return externalFilesDir2;
            }
            if (z) {
                return k;
            }
        } else if (c7.c(d2.C)) {
            return context.getExternalFilesDir(str);
        }
        return null;
    }

    public static String a() {
        return a(true);
    }

    public static synchronized String a(boolean z) {
        if (c7.b(d2.B)) {
            String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            i = absolutePath;
            return absolutePath;
        }
        if (!z) {
            return c7.c;
        }
        return i;
    }
}
