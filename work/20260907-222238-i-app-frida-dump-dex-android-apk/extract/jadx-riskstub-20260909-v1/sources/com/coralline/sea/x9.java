package com.coralline.sea;

import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class x9 {
    public static final String a = "init";
    public static final String b = "http";
    public static final String c = "ccbcipher";
    public static final String d = "extradata";
    public static final String e = "emulator";
    public static final String f = "crash";
    public static final String g = "util";
    public static final String h = "license";
    public static final String i = "3rdSDK";
    public static final String j = "extworker";
    public static final String k = "moc_dev";
    public static final String l = "oldloader";
    public static final String m = "messenger";
    public static final String n = "apkinfo";
    public static final String o = "stepinfo";
    public static final String p = "offline";
    public static final String q = "riskprocess";
    public static final String r = "cheatdetect";
    public static final String s = "messagecache";
    public static final String t = "privacy";
    public static final String u = "crashMonitor";
    public static final String v = "envcheck";
    public static boolean w = true;

    public static String a(Thread thread) {
        String str;
        StringBuilder sb = new StringBuilder("Thread name: ");
        sb.append(thread.getName());
        sb.append("; Thread State: ");
        sb.append(thread.getState());
        sb.append("\n");
        for (StackTraceElement stackTraceElement : thread.getStackTrace()) {
            sb.append("at ");
            sb.append(stackTraceElement.getClassName());
            sb.append(".");
            sb.append(stackTraceElement.getMethodName());
            sb.append("(");
            if (stackTraceElement.getLineNumber() > 0) {
                sb.append(stackTraceElement.getFileName());
                sb.append(":");
                sb.append(stackTraceElement.getLineNumber());
                str = ")\n";
            } else {
                str = "Native Method)\n";
            }
            sb.append(str);
        }
        return sb.toString();
    }

    public static void a(int i2, String str, Object obj) {
        String strConcat = str == null ? n0.d : "Controller .".concat(str);
        int i3 = 0;
        if (strConcat.length() > 23) {
            strConcat = strConcat.substring(0, 23);
        }
        String string = null;
        if (obj instanceof String) {
            string = (String) obj;
        } else if (obj instanceof Throwable) {
            StringWriter stringWriter = new StringWriter();
            ((Throwable) obj).printStackTrace(new PrintWriter(stringWriter));
            string = stringWriter.getBuffer().toString();
        }
        if (string == null) {
            return;
        }
        if (string.length() < 4000) {
            Log.println(i2, strConcat, string);
            return;
        }
        while (i3 < string.length()) {
            int length = i3 + 4000;
            if (length >= string.length()) {
                length = string.length();
            }
            Log.println(i2, strConcat, string.substring(i3, length));
            i3 = length;
        }
    }

    public static void a(String str) {
        if (w) {
            a(4, null, str);
        }
    }

    public static void a(String str, Object obj) {
        a(3, str, obj);
    }

    public static void a(Throwable th) {
    }

    public static void b(String str, Object obj) {
        a(6, str, obj);
    }

    public static void b(Throwable th) {
    }

    public static void c(String str, Object obj) {
        a(4, str, obj);
    }

    public static void c(Throwable th) {
    }

    public static void d(String str, Object obj) {
        a(2, str, obj);
    }

    public static void d(Throwable th) {
    }

    public static void e(String str, Object obj) {
        a(5, str, obj);
    }

    public static void e(Throwable th) {
    }
}
