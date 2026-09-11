package com.coralline.sea;

import java.io.BufferedReader;
import java.io.FileReader;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class q9 {
    public static JSONArray a() throws Throwable {
        JSONArray jSONArray = new JSONArray();
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/etc/hosts"));
            while (true) {
                try {
                    String line = bufferedReader2.readLine();
                    if (line == null) {
                        jSONArray.toString(4);
                        p9.a(bufferedReader2);
                        return jSONArray;
                    }
                    String[] strArrSplit = line.split("\\s+");
                    if (strArrSplit != null && strArrSplit.length == 2 && !strArrSplit[0].trim().equals("127.0.0.1") && !strArrSplit[0].trim().equalsIgnoreCase("::1")) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("ip", strArrSplit[0]);
                        jSONObject.put("host", strArrSplit[1]);
                        jSONArray.put(jSONObject);
                    }
                } catch (Exception e) {
                    bufferedReader = bufferedReader2;
                    p9.a(bufferedReader);
                    return jSONArray;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    p9.a(bufferedReader);
                    throw th;
                }
            }
        } catch (Exception e2) {
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
