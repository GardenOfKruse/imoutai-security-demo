package com.coralline.sea;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class o1 {
    public x3 a;

    /* JADX WARN: Removed duplicated region for block: B:75:0x0118 A[Catch: JSONException -> 0x0140, TryCatch #4 {JSONException -> 0x0140, blocks: (B:43:0x00a8, B:46:0x00b3, B:48:0x00b9, B:50:0x00c0, B:52:0x00c6, B:54:0x00cd, B:56:0x00d3, B:60:0x00df, B:62:0x00e5, B:64:0x00f1, B:66:0x00f7, B:73:0x0112, B:75:0x0118, B:76:0x0134, B:77:0x0138, B:69:0x0101, B:71:0x0107), top: B:90:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0138 A[Catch: JSONException -> 0x0140, TRY_LEAVE, TryCatch #4 {JSONException -> 0x0140, blocks: (B:43:0x00a8, B:46:0x00b3, B:48:0x00b9, B:50:0x00c0, B:52:0x00c6, B:54:0x00cd, B:56:0x00d3, B:60:0x00df, B:62:0x00e5, B:64:0x00f1, B:66:0x00f7, B:73:0x0112, B:75:0x0118, B:76:0x0134, B:77:0x0138, B:69:0x0101, B:71:0x0107), top: B:90:0x00a8 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public JSONObject a(x3 x3Var, JSONObject jSONObject) {
        JSONArray jSONArrayA;
        JSONArray jSONArrayA2;
        JSONArray jSONArrayA3;
        String str;
        JSONObject jSONObject2;
        JSONArray jSONArray = new JSONArray();
        this.a = x3Var;
        try {
            if (n3.a().g) {
                jSONArrayA = jSONObject.has("property_analyse") ? r3.a().a(this, jSONObject.getJSONArray("property_analyse")) : null;
                try {
                    jSONArrayA2 = jSONObject.has("app_md5") ? p.a().a(this, jSONObject.getJSONArray("app_md5")) : null;
                    try {
                        jSONArrayA3 = jSONObject.has("accessibility_service") ? f.a().a(n3.a().a, this, jSONObject.getJSONArray("accessibility_service")) : null;
                    } catch (JSONException e) {
                        jSONArrayA3 = null;
                    }
                } catch (JSONException e2) {
                    jSONArrayA2 = null;
                    jSONArrayA3 = jSONArrayA2;
                }
            } else {
                jSONArrayA = null;
                jSONArrayA2 = null;
                jSONArrayA3 = null;
            }
        } catch (JSONException e3) {
            jSONArrayA = null;
            jSONArrayA2 = null;
        }
        JSONArray jSONArrayA4 = (jSONObject.has("xscript") && jSONObject.getJSONObject("xscript").has("name")) ? ta.a().a(this, jSONObject.getJSONObject("xscript").getString("name")) : null;
        if (jSONArrayA == null && jSONArrayA2 == null && jSONArrayA3 == null && jSONArrayA4 == null) {
            return null;
        }
        JSONObject jSONObject3 = new JSONObject();
        try {
            if (n3.a().g) {
                if (jSONArrayA != null && jSONArrayA.length() > 0) {
                    jSONObject3.put("so", jSONArrayA);
                }
                if (jSONArrayA2 != null && jSONArrayA2.length() > 0) {
                    jSONObject3.put("data", jSONArrayA2);
                }
                if (jSONArrayA3 != null && jSONArrayA3.length() > 0) {
                    jSONArrayA3.toString();
                    if (jSONArray.length() == 0) {
                        jSONArray = jSONArrayA3;
                    } else {
                        for (int i = 0; i < jSONArrayA3.length(); i++) {
                            jSONArray.put(jSONArrayA3.get(i));
                        }
                    }
                }
            }
            if (jSONArrayA4 == null || jSONArrayA4.length() <= 0) {
                jSONArrayA4 = jSONArray;
                if (jSONArrayA4.length() != 0) {
                    jSONObject2 = new JSONObject();
                    jSONObject2.put("game_plugin", jSONArrayA4);
                    jSONObject2.put("detail", new JSONArray().put("xscript"));
                    str = "extra";
                } else {
                    str = "extra";
                    jSONObject2 = new JSONObject();
                }
                jSONObject3.put(str, jSONObject2);
            } else {
                jSONArrayA4.toString();
                if (jSONArray.length() == 0) {
                    if (jSONArrayA4.length() != 0) {
                    }
                    jSONObject3.put(str, jSONObject2);
                } else {
                    for (int i2 = 0; i2 < jSONArrayA4.length(); i2++) {
                        jSONArray.put(jSONArrayA4.get(i2));
                    }
                    jSONArrayA4 = jSONArray;
                    if (jSONArrayA4.length() != 0) {
                    }
                    jSONObject3.put(str, jSONObject2);
                }
            }
        } catch (JSONException e4) {
        }
        jSONObject3.toString();
        return jSONObject3;
    }
}
