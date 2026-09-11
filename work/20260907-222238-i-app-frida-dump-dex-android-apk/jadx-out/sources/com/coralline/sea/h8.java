package com.coralline.sea;

import android.content.Context;
import android.text.TextUtils;
import com.coralline.sea.j8;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class h8 {
    public static final String a = "config_checker_key";
    public static Map<String, j8> b = new HashMap();

    public static class a {
        public String a;
        public String b;
        public int c = -1;
        public int d = -1;
        public int e;
        public int f;
        public int g;
        public int h;

        public String toString() {
            return "PortScanInfo{packageName='" + this.a + "', tcpPort=" + this.c + ", tcpStartPort=" + this.e + ", tcpMaxOffset=" + this.f + ", udpStartPort=" + this.g + ", udpMaxOffset=" + this.h + '}';
        }
    }

    public static void a() {
        List<a> listB = b();
        if (listB == null || listB.size() < 0) {
            return;
        }
        for (a aVar : listB) {
            ArrayList arrayList = new ArrayList();
            int i = aVar.c;
            if (i != -1) {
                arrayList.add(Integer.valueOf(i));
            }
            int i2 = aVar.d;
            ArrayList arrayList2 = new ArrayList();
            if (i2 != -1) {
                arrayList2.add(Integer.valueOf(i2));
            }
            b.put(aVar.a, new j8(aVar.b, aVar.a, new j8.a(arrayList, arrayList2), new j8.b(aVar.e, aVar.f), new j8.b(aVar.g, aVar.h)));
        }
    }

    public static List<a> b() {
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArrayOptJSONArray = z1.a("screen_sharing").optJSONArray("port_scan_list");
            if (jSONArrayOptJSONArray != null) {
                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArrayOptJSONArray.getJSONObject(i);
                    a aVar = new a();
                    aVar.a = jSONObject.optString("package_name");
                    aVar.b = jSONObject.optString("app_name");
                    if (!TextUtils.isEmpty(aVar.a)) {
                        aVar.c = jSONObject.optInt("tcp_port", -1);
                        aVar.d = jSONObject.optInt("udp_port", -1);
                        if (jSONObject.has("tcp")) {
                            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("tcp");
                            aVar.e = jSONObjectOptJSONObject.optInt("startPort", -1);
                            aVar.f = jSONObjectOptJSONObject.optInt("maxOffset", -1);
                        }
                        if (jSONObject.has("udp")) {
                            JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("udp");
                            aVar.g = jSONObjectOptJSONObject2.optInt("startPort", -1);
                            aVar.h = jSONObjectOptJSONObject2.optInt("maxOffset", -1);
                        }
                        arrayList.add(aVar);
                    }
                }
            }
        } catch (Exception e) {
            e.toString();
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00f4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x000a A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.List<java.lang.String> a(android.content.Context r12) {
        /*
            Method dump skipped, instruction units count: 267
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.h8.a(android.content.Context):java.util.List");
    }

    public final boolean a(Context context, String str) {
        if ("com.oray.sunlogin.service".equals(str) || "com.oray.sunlogin".equals(str)) {
            if (v6.a(context, "com.oray.sunlogin", 0) == null && v6.a(context, "com.oray.sunlogin.service", 0) == null) {
                return false;
            }
        } else if (v6.a(n3.a().a, str, 0) == null) {
            return false;
        }
        return true;
    }

    public final boolean a(List<Integer> list) {
        return false;
    }

    public JSONObject b(Context context) {
        try {
            List<String> listA = a(context);
            if (listA == null || listA.isEmpty()) {
                return null;
            }
            return c(listA);
        } catch (Exception e) {
            return null;
        }
    }

    public final boolean b(List<Integer> list) {
        return false;
    }

    public final JSONObject c(List<String> list) {
        JSONObject jSONObject = new JSONObject();
        String str = list.get(0);
        JSONArray jSONArray = new JSONArray();
        try {
            if (TextUtils.isEmpty(str)) {
                jSONObject.put("package", i2.b);
                jSONObject.put("app_name", i2.b);
            } else {
                jSONObject.put("package", str);
                jSONObject.put("app_name", b.get(str).e());
            }
            jSONObject.put("check_method", "port_scan");
            jSONArray.put(jSONObject);
            if (jSONArray.length() <= 0) {
                return null;
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("detail", jSONArray);
            return jSONObject2;
        } catch (Exception e) {
            return null;
        }
    }
}
