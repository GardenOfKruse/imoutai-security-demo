package com.coralline.sea;

import android.net.wifi.WifiInfo;
import android.os.Handler;
import android.os.HandlerThread;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class pa {
    public static final String f = "<unknown ssid>";
    public String a;
    public Handler c;
    public boolean b = true;
    public String d = c7.c;
    public String e = c7.c;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            pa.this.b = true;
        }
    }

    public pa() {
        HandlerThread handlerThread = new HandlerThread("tasks");
        handlerThread.start();
        this.c = new Handler(handlerThread.getLooper());
    }

    public JSONObject a() {
        try {
            JSONObject jSONObjectA = a9.a(o8.c, new JSONObject());
            if (jSONObjectA.has("sensitive_info_collect_switch")) {
                JSONObject jSONObjectOptJSONObject = jSONObjectA.optJSONObject("sensitive_info_collect_switch");
                long jOptLong = jSONObjectOptJSONObject.optLong(d2.b);
                long jOptLong2 = jSONObjectOptJSONObject.optLong(d2.e);
                if (jOptLong == 0 || jOptLong2 == 0) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("bssid", "N/P");
                        jSONObject.put("ssid", "N/P");
                        jSONObject.put("mac_type", "N/P");
                        jSONObject.put("ip", "N/P");
                        JSONObject jSONObject2 = new JSONObject();
                        JSONArray jSONArray = new JSONArray();
                        jSONArray.put(jSONObject);
                        jSONObject2.put("wifi_info", jSONArray);
                        jSONObject2.put("detail", new JSONArray().put("wifi"));
                        jSONObject2.put("type", "wifi");
                        return jSONObject2;
                    } catch (JSONException e) {
                    }
                }
            }
            WifiInfo wifiInfoE = l6.e();
            if (wifiInfoE == null) {
                return null;
            }
            if (this.b) {
                this.d = wifiInfoE.getBSSID();
                this.e = wifiInfoE.getSSID();
                this.b = false;
                this.c.postDelayed(new a(), 100000L);
            }
            String str = this.a;
            if (str != null && str.equals(this.d)) {
                return null;
            }
            this.a = this.d;
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("bssid", this.d);
            jSONObject3.put("ssid", this.e);
            jSONObject3.put("mac_type", ja.l());
            jSONObject3.put("ip", l6.g(true));
            JSONObject jSONObject4 = new JSONObject();
            JSONArray jSONArray2 = new JSONArray();
            jSONArray2.put(jSONObject3);
            jSONObject4.put("wifi_info", jSONArray2);
            jSONObject4.put("detail", new JSONArray().put("wifi"));
            jSONObject4.put("type", "wifi");
            return jSONObject4;
        } catch (Exception e2) {
            return null;
        }
    }
}
