package com.coralline.sea;

import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class y5 {
    public static void a(Set<Map.Entry<String, q4>> set) {
        if (set == null || set.size() == 0) {
            return;
        }
        for (Map.Entry<String, q4> entry : set) {
            if (entry != null && entry.getValue() != null && entry.getValue().c() != null) {
                entry.getValue().c().a();
            }
        }
    }

    public static void a(Set<Map.Entry<String, q4>> set, Set<String> set2, int i) {
        if (set == null || set.size() == 0) {
            return;
        }
        for (Map.Entry<String, q4> entry : set) {
            if (entry != null && entry.getValue() != null) {
                l1 l1Var = new l1(null, null, set2, i);
                e6 e6Var = new e6(entry.getKey(), entry.getValue().e());
                e6Var.c = l1Var;
                e6Var.d();
            }
        }
    }

    public static void a(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                int i = jSONObject.has("sdk_name_intercept_level") ? jSONObject.getInt("sdk_name_intercept_level") : 3;
                Set<String> setC = y8.c(jSONObject.optJSONArray("white_list"));
                if (jSONObject.has("category")) {
                    JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("category");
                    Set<Map.Entry<String, q4>> setC2 = y8.a().c();
                    a(setC2);
                    if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() == 0) {
                        a(setC2, setC, i);
                        return;
                    }
                    int length = jSONArrayOptJSONArray.length();
                    for (int i2 = 0; i2 < length; i2++) {
                        JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i2);
                        l1 l1Var = new l1(jSONObjectOptJSONObject.optString("category_name"), y8.a(jSONObjectOptJSONObject.optJSONArray("policy"), "sdk_name", "sdk_policy"), setC, i);
                        JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray("monitor_behavior");
                        if (jSONArrayOptJSONArray2 != null && jSONArrayOptJSONArray2.length() != 0) {
                            int length2 = jSONArrayOptJSONArray2.length();
                            for (int i3 = 0; i3 < length2; i3++) {
                                JSONObject jSONObjectOptJSONObject2 = jSONArrayOptJSONArray2.optJSONObject(i3);
                                e6 e6Var = new e6(jSONObjectOptJSONObject2.optString(y3.h), y8.b(jSONObjectOptJSONObject2.optJSONArray("methods")));
                                e6Var.c = l1Var;
                                e6Var.d();
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }
}
