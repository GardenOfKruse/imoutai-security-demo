package com.coralline.sea;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.pm.ServiceInfo;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityManager;
import java.util.HashSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class d extends x6 {
    public static final String c = "accessibility_apps";
    public static d d;
    public String b;

    public d() {
        super(c, 10);
        this.b = c7.c;
    }

    public static d c() {
        if (d == null) {
            synchronized (d.class) {
                if (d == null) {
                    d = new d();
                }
            }
        }
        return d;
    }

    public final String a(String str, String str2) {
        return str + "/" + str2;
    }

    public final JSONArray a(Context context) {
        try {
            JSONArray jSONArray = new JSONArray();
            for (AccessibilityServiceInfo accessibilityServiceInfo : ((AccessibilityManager) context.getSystemService("accessibility")).getEnabledAccessibilityServiceList(-1)) {
                String str = accessibilityServiceInfo.getResolveInfo().serviceInfo.packageName;
                String str2 = accessibilityServiceInfo.getResolveInfo().serviceInfo.name;
                String str3 = accessibilityServiceInfo.getResolveInfo().serviceInfo.name;
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("package_name", str);
                    jSONObject.put("service_name", str2);
                    jSONObject.put("is_system_service", a(accessibilityServiceInfo));
                    jSONObject.put("app_name", v7.a(str));
                    jSONObject.put("install_time", v7.c(str));
                    jSONObject.put("name", str3);
                    jSONObject.put("detection_method", "accessibility manager");
                    jSONArray.put(jSONObject);
                } catch (JSONException e) {
                }
            }
            return jSONArray;
        } catch (Exception e2) {
            e2.getMessage();
            return null;
        }
    }

    public JSONObject a() {
        try {
            JSONArray jSONArrayB = b();
            if (jSONArrayB.length() <= 0) {
                return null;
            }
            for (int length = jSONArrayB.length() - 1; length >= 0; length--) {
                JSONObject jSONObject = jSONArrayB.getJSONObject(length);
                if (isInWhitelist(jSONObject.optString("package_name"), jSONObject.optString("app_name"), c)) {
                    jSONArrayB.remove(length);
                }
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("accessibility_service", jSONArrayB);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("type", "accessibility");
            jSONObject3.put("detail", jSONObject2);
            return jSONObject3;
        } catch (Exception e) {
            e.toString();
            return null;
        }
    }

    public final boolean a(AccessibilityServiceInfo accessibilityServiceInfo) {
        ServiceInfo serviceInfo = accessibilityServiceInfo.getResolveInfo().serviceInfo;
        return ((serviceInfo.applicationInfo.flags & 129) != 0) || a(serviceInfo.packageName);
    }

    public final boolean a(String str) {
        return str.startsWith("com.android.") || str.startsWith("com.google.android.") || str.startsWith("android.");
    }

    public final String b(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), "enabled_accessibility_services");
        return string != null ? string : c7.c;
    }

    public JSONArray b() {
        JSONArray jSONArray = new JSONArray();
        HashSet hashSet = new HashSet();
        Context context = n3.a().a;
        try {
            JSONArray jSONArrayA = a(context);
            if (jSONArrayA != null && jSONArrayA.length() > 0) {
                for (int i = 0; i < jSONArrayA.length(); i++) {
                    JSONObject jSONObject = jSONArrayA.getJSONObject(i);
                    String strA = a(jSONObject.optString("package_name"), jSONObject.optString("service_name"));
                    if (!hashSet.contains(strA)) {
                        hashSet.add(strA);
                        jSONArray.put(jSONObject);
                    }
                }
            }
            String strB = b(context);
            if (!TextUtils.isEmpty(strB)) {
                for (String str : strB.split(":")) {
                    if (!TextUtils.isEmpty(str)) {
                        String[] strArrSplit = str.split("/");
                        if (strArrSplit.length >= 2) {
                            String str2 = strArrSplit[0];
                            String str3 = strArrSplit[1];
                            String strA2 = a(str2, str3);
                            if (!hashSet.contains(strA2) && !TextUtils.isEmpty(str2)) {
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put("package_name", str2);
                                jSONObject2.put("service_name", str3);
                                jSONObject2.put("app_name", v7.a(str2));
                                jSONObject2.put("install_time", v7.c(str2));
                                jSONObject2.put("is_system_service", a(str2));
                                jSONObject2.put("detection_method", "settings");
                                hashSet.add(strA2);
                                jSONArray.put(jSONObject2);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return jSONArray;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        JSONObject jSONObjectA = a();
        if (jSONObjectA == null) {
            return;
        }
        if (this.b.equals(ja.o() + jSONObjectA.toString())) {
            return;
        }
        this.b = ja.o() + jSONObjectA.toString();
        push(e2.b, c, jSONObjectA.toString());
    }
}
