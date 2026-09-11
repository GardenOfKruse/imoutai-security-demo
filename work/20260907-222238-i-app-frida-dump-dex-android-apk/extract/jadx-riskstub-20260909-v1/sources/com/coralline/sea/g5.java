package com.coralline.sea;

import android.util.Base64;
import androidx.annotation.NonNull;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class g5 {
    public static g5 c;
    public b b = new b();
    public Thread.UncaughtExceptionHandler a = Thread.getDefaultUncaughtExceptionHandler();

    public class a extends TimerTask {
        public a() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            b bVar = g5.this.b;
            if (defaultUncaughtExceptionHandler != bVar) {
                Thread.setDefaultUncaughtExceptionHandler(bVar);
            }
        }
    }

    public class b implements Thread.UncaughtExceptionHandler {
        public final String[] a;
        public final List<String> b = new ArrayList();

        public b() {
            String[] strArr = {"Y29yYWxsaW5lLnNlYQ==", "YmFuZ2NsZS4="};
            this.a = strArr;
            for (String str : strArr) {
                try {
                    this.b.add(new String(Base64.decode(str, 2)));
                } catch (Exception e) {
                }
            }
        }

        public final void a(String str, boolean z, boolean z2) {
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(n3.a().r + "/everisk_jcrash.txt");
            } catch (Exception e) {
                fileOutputStream = null;
            }
            try {
                if (z) {
                    fileOutputStream.write(15);
                } else if (z2) {
                    fileOutputStream.write(14);
                }
                fileOutputStream.write(str.getBytes());
                fileOutputStream.flush();
            } catch (Exception e2) {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (Exception e3) {
                    }
                }
            }
        }

        public final boolean a(String str) {
            n3 n3VarA = n3.a();
            boolean z = n3.T.e;
            String str2 = n3VarA.n;
            if (!n3.T.e) {
                return true;
            }
            String str3 = n3VarA.n;
            return str3 != null && str.contains(str3);
        }

        public final boolean b(String str) {
            Iterator<String> it = this.b.iterator();
            while (it.hasNext()) {
                if (str.contains(it.next())) {
                    return true;
                }
            }
            return false;
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(@NonNull Thread thread, @NonNull Throwable th) {
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
            StringWriter stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            String string = stringWriter.toString();
            boolean zB = b(string);
            a(string, !zB && a(string), zB);
            if (zB || (uncaughtExceptionHandler = g5.this.a) == null) {
                return;
            }
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }

    public g5() {
        new Timer("Risk-Timer-JavaCrashHandler").scheduleAtFixedRate(new a(), 0L, 60000L);
    }

    public static synchronized void a() {
        if (c != null) {
            return;
        }
        c = new g5();
    }
}
