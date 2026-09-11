package com.coralline.sea;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.view.accessibility.AccessibilityManager;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class f {
    public static f a;

    public static synchronized f a() {
        if (a == null) {
            a = new f();
        }
        return a;
    }

    public JSONArray a(Context context, o1 o1Var, JSONArray jSONArray) {
        JSONArray jSONArray2 = new JSONArray();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (jSONObject.has("service_id") && jSONObject.has("name")) {
                    arrayList.add(jSONObject.getString("service_id"));
                    arrayList2.add(jSONObject.getString("name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Iterator<AccessibilityServiceInfo> it = ((AccessibilityManager) context.getSystemService("accessibility")).getEnabledAccessibilityServiceList(-1).iterator();
        while (it.hasNext()) {
            String id = it.next().getId();
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (id.equals(arrayList.get(i2))) {
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put("type", "accessibility_service");
                        String str = (String) arrayList2.get(i2);
                        jSONObject2.put("name", str);
                        if (!o1Var.a.b.contains(str)) {
                            x3 x3Var = o1Var.a;
                            x3Var.c = true;
                            x3Var.b.add(str);
                        }
                        jSONArray2.put(jSONObject2);
                        arrayList.remove(i2);
                        arrayList2.remove(i2);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        if (jSONArray2.length() == 0) {
            return null;
        }
        return jSONArray2;
    }
}
