package com.coralline.sea;

import java.util.List;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class g4 extends x6 {
    public String b;

    public g4() {
        super("host", 30);
        this.b = c7.c;
    }

    public JSONObject a() throws Throwable {
        try {
            List<String> list = n3.a().l;
            if (list.size() == 0) {
                return null;
            }
            String str = list.get(0);
            String strK = ja.k(str);
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArrayA = q9.a();
            JSONObject jSONObjectA = s9.a(str);
            if (jSONArrayA.length() == 0 && jSONObjectA.length() == 0) {
                return null;
            }
            JSONObject jSONObjectA2 = t9.a(strK);
            JSONArray jSONArray = new JSONArray();
            jSONObject.put("data", jSONArrayA);
            jSONArray.put("data");
            Objects.toString(jSONObjectA);
            if (jSONObjectA.length() != 0) {
                jSONObject.put("wlan", jSONObjectA);
                jSONArray.put("wlan");
            }
            if (this.b.equals(jSONObject.toString())) {
                return null;
            }
            this.b = jSONObject.toString();
            jSONObject.put("traceroute", jSONObjectA2);
            jSONArray.put("traceroute");
            jSONObject.put("detail", jSONArray);
            return jSONObject;
        } catch (Exception e) {
            return null;
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() throws Throwable {
        JSONObject jSONObjectA = a();
        if (jSONObjectA != null) {
            push(e2.b, "host_fraud", jSONObjectA.toString());
        }
    }
}
