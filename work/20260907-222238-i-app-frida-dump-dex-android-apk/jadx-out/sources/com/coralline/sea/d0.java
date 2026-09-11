package com.coralline.sea;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class d0 {
    public static d0 d;
    public static List<PackageInfo> e;
    public final String a = "AutoClickDetect";
    public JSONObject b = null;
    public JSONObject c = null;

    public static JSONArray a(Context context, List<PackageInfo> list, JSONArray jSONArray) {
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                String string = jSONArray.getJSONObject(i).getString("package");
                Iterator<PackageInfo> it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        PackageInfo next = it.next();
                        if (next.packageName.equals(string)) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("name", next.applicationInfo.loadLabel(context.getPackageManager()).toString());
                            jSONObject.put("package", next.packageName);
                            jSONArray2.put(jSONObject);
                            break;
                        }
                    }
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return jSONArray2;
    }

    public static synchronized d0 b() {
        if (d == null) {
            d = new d0();
        }
        return d;
    }

    public static JSONArray b(Context context, List<PackageInfo> list, JSONArray jSONArray) {
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                String string = jSONArray.getJSONObject(i).getString("package");
                Iterator<PackageInfo> it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        PackageInfo next = it.next();
                        if (next.packageName.equals(string)) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("name", next.applicationInfo.loadLabel(context.getPackageManager()).toString());
                            jSONObject.put("package", next.packageName);
                            jSONArray2.put(jSONObject);
                            break;
                        }
                    }
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return jSONArray2;
    }

    public JSONObject a() {
        try {
            if (!e0.a) {
                return null;
            }
            JSONObject jSONObject = e0.b;
            jSONObject.put("is_auto_click_on_touch", true);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", "auto_click");
            jSONObject2.put("detail", jSONObject);
            return jSONObject2;
        } catch (Exception e2) {
            e2.toString();
            return null;
        }
    }

    public final JSONObject a(AccessibilityServiceInfo accessibilityServiceInfo) {
        String[] strArr;
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        if (accessibilityServiceInfo != null) {
            int capabilities = accessibilityServiceInfo.getCapabilities();
            while (capabilities != 0) {
                int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(capabilities);
                jSONArray.put(AccessibilityServiceInfo.capabilityToString(iNumberOfTrailingZeros));
                capabilities &= iNumberOfTrailingZeros ^ (-1);
            }
        }
        try {
            jSONObject.put("capabilities", jSONArray);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        if (accessibilityServiceInfo != null) {
            try {
                jSONObject.put("id", accessibilityServiceInfo.getId());
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
        JSONArray jSONArray2 = new JSONArray();
        if (accessibilityServiceInfo != null) {
            int i = accessibilityServiceInfo.feedbackType;
            while (i != 0) {
                int iNumberOfTrailingZeros2 = 1 << Integer.numberOfTrailingZeros(i);
                String strFeedbackTypeToString = AccessibilityServiceInfo.feedbackTypeToString(iNumberOfTrailingZeros2);
                if (!"[]".equalsIgnoreCase(strFeedbackTypeToString) && !TextUtils.isEmpty(strFeedbackTypeToString)) {
                    jSONArray2.put(strFeedbackTypeToString.replace("[", c7.c).replace("]", c7.c));
                }
                i &= iNumberOfTrailingZeros2 ^ (-1);
            }
        }
        try {
            jSONObject.put("feedbackTypes", jSONArray2);
        } catch (JSONException e4) {
            e4.printStackTrace();
        }
        JSONArray jSONArray3 = new JSONArray();
        if (accessibilityServiceInfo != null) {
            int i2 = accessibilityServiceInfo.eventTypes;
            while (i2 != 0) {
                int iNumberOfTrailingZeros3 = 1 << Integer.numberOfTrailingZeros(i2);
                jSONArray3.put(AccessibilityEvent.eventTypeToString(iNumberOfTrailingZeros3));
                i2 &= iNumberOfTrailingZeros3 ^ (-1);
            }
        }
        try {
            jSONObject.put("eventTypes", jSONArray3);
        } catch (JSONException e5) {
            e5.printStackTrace();
        }
        JSONArray jSONArray4 = new JSONArray();
        if (accessibilityServiceInfo != null && (strArr = accessibilityServiceInfo.packageNames) != null) {
            for (String str : strArr) {
                jSONArray4.put(str);
            }
        }
        try {
            jSONObject.put("packageNames", jSONArray4);
            return jSONObject;
        } catch (JSONException e6) {
            e6.printStackTrace();
            return jSONObject;
        }
    }

    public final void a(JSONObject jSONObject) {
        this.b = jSONObject;
    }

    public final void b(JSONObject jSONObject) {
        this.c = jSONObject;
    }
}
