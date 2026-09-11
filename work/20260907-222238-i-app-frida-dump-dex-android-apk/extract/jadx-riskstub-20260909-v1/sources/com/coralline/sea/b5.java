package com.coralline.sea;

import android.os.Build;
import android.os.Process;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class b5 extends x6 {
    public static final String d = "inject";
    public static final String e = "inject";
    public HashSet<String> b;
    public HashSet<String> c;

    public b5() {
        super("inject", 30);
    }

    public JSONObject a() {
        try {
            if (!c5.d()) {
                return null;
            }
            JSONObject jSONObjectA = c5.a(getName(), this.b, this.c);
            if (!jSONObjectA.has("dlopen") && !jSONObjectA.has("hook") && !jSONObjectA.has("attack_frame") && !jSONObjectA.has("binder") && !jSONObjectA.has("mikrom") && !jSONObjectA.has("blackdex")) {
                if (jSONObjectA.has("fart")) {
                }
            }
            return jSONObjectA;
        } catch (Exception e2) {
            getName();
        }
        return null;
    }

    public final void b() throws Throwable {
        BufferedReader bufferedReader;
        HashSet<String> hashSet = new HashSet<>();
        HashSet<String> hashSet2 = new HashSet<>();
        BufferedReader bufferedReader2 = null;
        try {
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader(new File("/proc/" + Process.myPid() + "/maps")));
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        String[] strArrSplit = line.split("\\s+");
                        if (strArrSplit.length >= 6 && strArrSplit[5].endsWith(".so") && strArrSplit[1].charAt(2) == 'x') {
                            hashSet.add(strArrSplit[5]);
                            String[] strArrSplit2 = strArrSplit[5].split(File.separator);
                            hashSet2.add(strArrSplit2[strArrSplit2.length - 1]);
                        }
                    } catch (Exception e3) {
                        e = e3;
                        bufferedReader2 = bufferedReader;
                        e.printStackTrace();
                        if (bufferedReader2 != null) {
                            bufferedReader2.close();
                        }
                        this.b = hashSet;
                        this.c = hashSet2;
                    } catch (Throwable th) {
                        th = th;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        this.b = hashSet;
                        this.c = hashSet2;
                        throw th;
                    }
                }
                bufferedReader.close();
            } catch (Exception e5) {
                e = e5;
            }
            this.b = hashSet;
            this.c = hashSet2;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = bufferedReader2;
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        JSONObject jSONObjectA = a();
        if (jSONObjectA != null) {
            push(e2.b, "inject", jSONObjectA.toString());
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void initialize() throws Throwable {
        i6.e(Build.VERSION.SDK_INT);
        b();
        c5.e();
    }
}
