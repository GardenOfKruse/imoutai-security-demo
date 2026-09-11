package com.coralline.sea;

import java.util.HashSet;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class f5 {
    public static HashSet<String> a(JSONArray jSONArray) {
        HashSet<String> hashSet = new HashSet<>();
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                hashSet.add(jSONArray.optString(i, c7.c));
            }
        }
        return hashSet;
    }

    public static JSONArray a(HashSet<String> hashSet) {
        JSONArray jSONArray = new JSONArray();
        if (hashSet != null) {
            Iterator<String> it = hashSet.iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next());
            }
        }
        return jSONArray;
    }

    public static JSONArray a(JSONArray jSONArray, JSONArray jSONArray2) {
        JSONArray jSONArray3 = new JSONArray();
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                jSONArray3.put(jSONArray.opt(i));
            }
        }
        if (jSONArray2 != null) {
            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                jSONArray3.put(jSONArray2.opt(i2));
            }
        }
        return jSONArray3;
    }

    public static boolean a(Object obj) {
        if (obj == null) {
            return true;
        }
        return obj instanceof JSONObject ? ((JSONObject) obj).length() <= 0 : !(obj instanceof JSONArray) || ((JSONArray) obj).length() <= 0;
    }
}
