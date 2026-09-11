package com.coralline.sea;

import android.text.TextUtils;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class s9 {
    public static JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            String property = System.getProperty("http.proxyHost", c7.c);
            String property2 = System.getProperty("http.proxyPort", c7.c);
            if (!TextUtils.isEmpty(property) && !TextUtils.isEmpty(property)) {
                jSONObject.put("host", property);
                jSONObject.put("port", Integer.parseInt(property2));
                return jSONObject;
            }
        } catch (Exception e) {
        }
        return jSONObject;
    }

    public static JSONObject a(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (TextUtils.isEmpty(str)) {
                return jSONObject;
            }
            List<Proxy> listSelect = ProxySelector.getDefault().select(URI.create(str));
            for (int i = 0; listSelect != null && i < listSelect.size(); i++) {
                Proxy proxy2 = listSelect.get(i);
                Objects.toString(proxy2);
                if (proxy2.type() != Proxy.Type.DIRECT && (proxy2.address() instanceof InetSocketAddress)) {
                    InetSocketAddress inetSocketAddress = (InetSocketAddress) proxy2.address();
                    jSONObject.put("host", inetSocketAddress.getHostName());
                    jSONObject.put("port", inetSocketAddress.getPort());
                    return jSONObject;
                }
            }
        } catch (Exception e) {
        }
        return jSONObject;
    }

    public static JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        try {
            String property = System.getProperty("https.proxyHost", c7.c);
            String property2 = System.getProperty("https.proxyPort", c7.c);
            if (!TextUtils.isEmpty(property) && !TextUtils.isEmpty(property)) {
                jSONObject.put("host", property);
                jSONObject.put("port", Integer.parseInt(property2));
                return jSONObject;
            }
        } catch (Exception e) {
        }
        return jSONObject;
    }
}
