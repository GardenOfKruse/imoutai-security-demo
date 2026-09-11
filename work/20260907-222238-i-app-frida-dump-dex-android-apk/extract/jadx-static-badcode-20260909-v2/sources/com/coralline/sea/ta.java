package com.coralline.sea;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class ta {
    public static ta a;

    public static synchronized ta a() {
        if (a == null) {
            a = new ta();
        }
        return a;
    }

    public JSONArray a(o1 o1Var, String str) throws Throwable {
        JSONArray jSONArray = new JSONArray();
        String strI = ja.i("cat /proc/sys/fs/inotify/max_user_watches");
        if (strI != null && strI.equals("0\n")) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", "xscript");
                jSONObject.put("name", str);
                if (!o1Var.a.b.contains(str)) {
                    x3 x3Var = o1Var.a;
                    x3Var.c = true;
                    x3Var.b.add(str);
                }
                jSONArray.put(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jSONArray.length() == 0) {
            return null;
        }
        return jSONArray;
    }
}
