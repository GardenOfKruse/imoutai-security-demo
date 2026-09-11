package com.coralline.sea;

import android.os.Process;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class d7 {
    public static boolean d = true;
    public static long e;
    public static HashSet<String> f = new HashSet<>();
    public static HashSet<String> g = new HashSet<>();
    public static ArrayList<d7> h = new ArrayList<>();
    public static StringBuffer i = new StringBuffer();
    public String a;
    public String b;
    public String c;

    public class a implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
            d7.b(true);
        }
    }

    public static d7 a(List<d7> list, String str) {
        for (d7 d7Var : list) {
            if (d7Var.c.endsWith(str)) {
                return d7Var;
            }
        }
        return null;
    }

    public static HashSet<String> a() {
        b(false);
        return f;
    }

    public static void a(String str) {
        new Thread(new a(), "everisk_get_maps").start();
    }

    public static ArrayList<d7> b() {
        BufferedReader bufferedReader;
        try {
            StringBuffer stringBuffer = i;
            if (stringBuffer != null && stringBuffer.length() > 0) {
                i.setLength(0);
            }
            if (!h.isEmpty()) {
                h.clear();
            }
            bufferedReader = new BufferedReader(new FileReader(new File("/proc/" + Process.myPid() + "/maps")));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            String[] strArrSplit = line.split("\\s+");
            if (strArrSplit.length >= 6) {
                if (!b(strArrSplit[5])) {
                    f.add(strArrSplit[5]);
                }
                g.add(strArrSplit[5]);
                if (strArrSplit[5].endsWith(".so")) {
                    i.append("'" + strArrSplit[5]);
                    d7 d7Var = new d7();
                    String str = strArrSplit[0];
                    d7Var.a = "0x" + str.substring(0, str.indexOf(45));
                    d7Var.b = "0x" + str.substring(str.indexOf(45) + 1);
                    d7Var.c = strArrSplit[5];
                    h.add(d7Var);
                }
            }
            i.append("'");
            return h;
        }
        bufferedReader.close();
        i.append("'");
        return h;
    }

    public static synchronized void b(boolean z) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (d || z) {
            b();
            d = false;
        } else if (jCurrentTimeMillis - e > 30000) {
            b();
        }
        e = jCurrentTimeMillis;
    }

    public static boolean b(String str) {
        if (str.startsWith("/system/lib64/") || str.startsWith("/system/lib") || str.startsWith("/system/vendor/lib64/") || str.startsWith("/system/vendor/lib/")) {
            return true;
        }
        return (str.endsWith(".so") || str.endsWith(".dex") || str.endsWith(".odex")) ? false : true;
    }

    public static HashSet<String> c() {
        b(false);
        return g;
    }

    public static ArrayList<d7> d() {
        b(false);
        return new ArrayList<>(h);
    }

    public String toString() {
        return "ProcMaps{start_addr='" + this.a + "', end_addr='" + this.b + "', path='" + this.c + "'}";
    }
}
