package com.coralline.sea;

import android.os.Process;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import org.json.JSONArray;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class l8 {
    public static HashSet<String> a = new HashSet<>();

    public static HashSet<String> a() throws Throwable {
        BufferedReader bufferedReader;
        HashSet<String> hashSet = new HashSet<>();
        BufferedReader bufferedReader2 = null;
        try {
            try {
                try {
                    bufferedReader = new BufferedReader(new FileReader(new File("/proc/" + Process.myPid() + "/maps")));
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                bufferedReader.close();
                                bufferedReader.close();
                                return hashSet;
                            }
                            String[] strArrSplit = line.split("\\s+");
                            if (strArrSplit.length >= 6 && !a(strArrSplit[5])) {
                                hashSet.add(strArrSplit[5]);
                            }
                        } catch (Exception e) {
                            e = e;
                            bufferedReader2 = bufferedReader;
                            e.printStackTrace();
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                                return hashSet;
                            }
                            return hashSet;
                        } catch (Throwable th) {
                            th = th;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e2) {
                                }
                            }
                            throw th;
                        }
                    }
                } catch (IOException e3) {
                    return hashSet;
                }
            } catch (Exception e4) {
                e = e4;
            }
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = bufferedReader2;
        }
    }

    public static boolean a(String str) {
        if (str.startsWith("/system/lib64/") || str.startsWith("/system/lib") || str.startsWith("/system/vendor/lib64/") || str.startsWith("/system/vendor/lib/")) {
            return true;
        }
        return (str.endsWith(".so") || str.endsWith(".dex") || str.endsWith(".odex")) ? false : true;
    }

    public static JSONArray b() {
        JSONArray jSONArray = new JSONArray();
        try {
            HashSet<String> hashSetA = d7.a();
            hashSetA.removeAll(a);
            Iterator<String> it = hashSetA.iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next());
            }
            a.addAll(hashSetA);
            return jSONArray;
        } catch (Exception e) {
            e.printStackTrace();
            return jSONArray;
        }
    }
}
