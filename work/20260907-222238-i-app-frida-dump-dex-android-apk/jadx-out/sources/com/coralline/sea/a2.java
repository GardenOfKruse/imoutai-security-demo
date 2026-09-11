package com.coralline.sea;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class a2 {
    public static final String a = "checker";

    @NonNull
    public static JSONArray a(@Nullable JSONObject jSONObject, @Nullable String str, @Nullable String str2) {
        JSONArray jSONArrayOptJSONArray;
        if (jSONObject == null || str == null || str2 == null) {
            return new JSONArray();
        }
        JSONObject jSONObjectA = a(jSONObject, str);
        return (jSONObjectA == null || (jSONArrayOptJSONArray = jSONObjectA.optJSONArray(str2)) == null) ? new JSONArray() : jSONArrayOptJSONArray;
    }

    @Nullable
    public static JSONObject a(@Nullable JSONObject jSONObject, @Nullable String str) {
        JSONObject jSONObjectOptJSONObject;
        if (jSONObject == null || str == null || (jSONObjectOptJSONObject = jSONObject.optJSONObject("checker")) == null) {
            return null;
        }
        return jSONObjectOptJSONObject.optJSONObject(str);
    }

    public static boolean a(@Nullable JSONObject jSONObject, @Nullable String str, @Nullable String str2, boolean z) {
        JSONObject jSONObjectA;
        return (jSONObject == null || str == null || str2 == null || (jSONObjectA = a(jSONObject, str)) == null) ? z : jSONObjectA.optBoolean(str2, z);
    }

    public static boolean b(@Nullable JSONObject jSONObject, @Nullable String str, @Nullable String str2) {
        JSONObject jSONObjectA;
        if (jSONObject == null || str == null || str2 == null || (jSONObjectA = a(jSONObject, str)) == null) {
            return false;
        }
        return jSONObjectA.has(str2);
    }
}
