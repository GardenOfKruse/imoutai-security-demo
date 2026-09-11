package com.coralline.sea;

import android.os.Process;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class r3 {
    public static r3 a;

    public static synchronized r3 a() {
        if (a == null) {
            a = new r3();
        }
        return a;
    }

    /* JADX WARN: Removed duplicated region for block: B:115:0x0148 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:136:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0144 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0145 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public JSONArray a(o1 o1Var, JSONArray jSONArray) throws Throwable {
        Throwable th;
        BufferedReader bufferedReader;
        Exception exc;
        JSONException jSONException;
        JSONObject jSONObject;
        if (o1Var == null || jSONArray == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        JSONArray jSONArray2 = new JSONArray();
        try {
            bufferedReader = new BufferedReader(new FileReader(new File("/proc/" + Process.myPid() + "/maps")));
            try {
                try {
                    String strA = b8.a();
                    String[] strArrSplit = strA.length() > 0 ? strA.split("\n") : null;
                    if (strArrSplit != null && strArrSplit.length != 0) {
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            for (int i = 0; i < jSONArray.length(); i++) {
                                try {
                                    jSONObject = jSONArray.getJSONObject(i);
                                } catch (JSONException e) {
                                    e = e;
                                }
                                if (!jSONObject.has("name")) {
                                    try {
                                        bufferedReader.close();
                                        return null;
                                    } catch (IOException e2) {
                                        return null;
                                    }
                                }
                                String string = jSONObject.getString("name");
                                if (string == null) {
                                    try {
                                        bufferedReader.close();
                                        return null;
                                    } catch (IOException e3) {
                                        return null;
                                    }
                                }
                                if (!hashSet.contains(string) && line.contains(string)) {
                                    String strA2 = ja.a(strArrSplit, 1, string);
                                    JSONObject jSONObject2 = new JSONObject();
                                    jSONObject2.put("so_path", string);
                                    if (!jSONObject.has("id")) {
                                        try {
                                            bufferedReader.close();
                                            return null;
                                        } catch (IOException e4) {
                                            return null;
                                        }
                                    }
                                    String string2 = jSONObject.getString("id");
                                    if (string2 == null) {
                                        try {
                                            bufferedReader.close();
                                            return null;
                                        } catch (IOException e5) {
                                            return null;
                                        }
                                    }
                                    jSONObject2.put("policy", string2);
                                    if (!jSONObject.has("app_name")) {
                                        try {
                                            bufferedReader.close();
                                            return null;
                                        } catch (IOException e6) {
                                            return null;
                                        }
                                    }
                                    String string3 = jSONObject.getString("app_name");
                                    if (string3 == null) {
                                        try {
                                            bufferedReader.close();
                                            return null;
                                        } catch (IOException e7) {
                                            return null;
                                        }
                                    }
                                    jSONObject2.put("app_name", string3);
                                    if (!o1Var.a.b.contains(string3)) {
                                        try {
                                            x3 x3Var = o1Var.a;
                                            x3Var.c = true;
                                            x3Var.b.add(string3);
                                        } catch (JSONException e8) {
                                            jSONException = e8;
                                            jSONException.printStackTrace();
                                        }
                                    }
                                    jSONObject2.put("pid", strA2);
                                    jSONObject2.put("pname", ja.p(strA2));
                                    try {
                                        jSONObject2.put("uname", ja.a(strArrSplit, 0, string));
                                        hashSet.add(string);
                                        jSONArray2.put(jSONObject2);
                                    } catch (JSONException e9) {
                                        e = e9;
                                        jSONException = e;
                                        jSONException.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    bufferedReader.close();
                } catch (Exception e10) {
                    exc = e10;
                    exc.toString();
                    if (bufferedReader != null) {
                    }
                    if (jSONArray2.length() != 0) {
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (bufferedReader != null) {
                    throw th;
                }
                try {
                    bufferedReader.close();
                    throw th;
                } catch (IOException e11) {
                    throw th;
                }
            }
        } catch (Exception e12) {
            exc = e12;
            bufferedReader = null;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
            if (bufferedReader != null) {
            }
        }
        try {
            bufferedReader.close();
        } catch (IOException e13) {
        }
        if (jSONArray2.length() != 0) {
            return null;
        }
        return jSONArray2;
    }
}
