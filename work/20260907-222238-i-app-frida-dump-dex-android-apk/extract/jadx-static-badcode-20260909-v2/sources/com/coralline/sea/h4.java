package com.coralline.sea;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.net.NetworkInterface;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class h4 {
    public static final String b = "HttpsCheckHandler";
    public static volatile h4 c;
    public String a = c7.c;

    public static boolean a(Context context) {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        return (context == null || (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
    }

    public static h4 d() {
        if (c == null) {
            synchronized (h4.class) {
                if (c == null) {
                    c = new h4();
                }
            }
        }
        return c;
    }

    public JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            String property = System.getProperty("http.proxyHost");
            String property2 = System.getProperty("http.proxyPort");
            if (TextUtils.isEmpty(property) || TextUtils.isEmpty(property2)) {
                return jSONObject;
            }
            jSONObject.put("agency", "http://" + property + ":" + property2);
            jSONObject.put("detail", new JSONArray().put("agency"));
            return jSONObject;
        } catch (Exception e) {
        }
        return jSONObject;
    }

    public JSONObject a(String str) {
        if (!b(str)) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(str);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("detail", jSONArray);
            jSONObject.put("https_hijack", true);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    public JSONObject b() {
        return a("https://www.baidu.com");
    }

    public boolean b(String str) {
        HttpsURLConnection httpsURLConnection;
        if (!a(n3.a().a)) {
            return false;
        }
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            keyStore.setCertificateEntry("testCert", CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(Base64.decode("MIIDWzCCAkOgAwIBAgIENhsgwTANBgkqhkiG9w0BAQsFADBeMQswCQYDVQQGEwJCSjEQMA4GA1UECBMHQmVpSmluZzEQMA4GA1UEBxMHQmVpSmluZzENMAsGA1UEChMEdGVzdDENMAsGA1UECxMEdGVzdDENMAsGA1UEAxMEdGVzdDAeFw0xOTAxMTUwMzExMjlaFw0yOTAxMTIwMzExMjlaMF4xCzAJBgNVBAYTAkJKMRAwDgYDVQQIEwdCZWlKaW5nMRAwDgYDVQQHEwdCZWlKaW5nMQ0wCwYDVQQKEwR0ZXN0MQ0wCwYDVQQLEwR0ZXN0MQ0wCwYDVQQDEwR0ZXN0MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApXul4WjJ+JdOQG5sOziLYQ7SoBvs0F3HToVw+hf/kJm7zjz2XVZ3xCSdpavb4J9jNvae2VUyw1+0TE94UPIYF6fQGcGOkINbZCVXGuEItLAJ2FJwGco4Aoacc7LvHcm72OhoFr6H3bWjeugxz5ZJ3W6QK3ocF11DeUO/bOsQ6VSgtlSMNKEi30lr2M4NT8xfzEKE3euDY5JeGCCN+WfnOn6GDXKX+iMcCp0K4o0rFJN/0gVEXgBrmtW/z9VCnxpjkp0mloVq05392W7gAFnO9DzNh2Sjgjsb+IvWB17mCrFHtBTHR7ANuYFtVZxlwO82CFKyuntl8831/ZVmSfs/HwIDAQABoyEwHzAdBgNVHQ4EFgQUhQbdSNgS/4P0wp9pHd6BO1jSdWMwDQYJKoZIhvcNAQELBQADggEBAC12CHOOb5XVlD7x8T++TKiWI61Dt8dGEsSzxv7/Ck6JDA7Ipbe8aKONUA6XXnbxRFjNArvl4kQZRoqbqog+DLS2Slf/rsIC33ZqGjVpZ+YP/Z1CWADoVlZ9HwtlKW1r4COLk92JfzqVOdEgqJig8EP2YBtN4pElLq3eR+P7tY9iCZZaTWxJTUF2KAwveZH5G3wR+3FFyeC/gcGYbcu+HvYmmCq2QX65AJbIVDGsEQ2/SOsRCPbTR//jCekclx8avWumORCyrf17XHFbHiMBfsKS1cheYag7oPpPhON2qO7jK0J0PCcj2H8rods6eLlxj68Mg5aDdbW3KHbNf8iT5bM=", 0))));
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sSLContext.init(null, trustManagerFactory.getTrustManagers(), null);
            SSLSocketFactory socketFactory = sSLContext.getSocketFactory();
            httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection();
            try {
                httpsURLConnection.setSSLSocketFactory(socketFactory);
                httpsURLConnection.setConnectTimeout(a.m);
                httpsURLConnection.setReadTimeout(a.m);
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.connect();
                if (200 == httpsURLConnection.getResponseCode()) {
                    httpsURLConnection.disconnect();
                    return true;
                }
            } catch (Exception e) {
                if (httpsURLConnection != null) {
                }
                return false;
            } catch (Throwable th) {
                if (httpsURLConnection != null) {
                }
                return false;
            }
        } catch (Exception e2) {
            httpsURLConnection = null;
        } catch (Throwable th2) {
            httpsURLConnection = null;
        }
        httpsURLConnection.disconnect();
        return false;
    }

    public JSONObject c() {
        JSONObject jSONObject = new JSONObject();
        HashMap map = new HashMap();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces == null) {
                return null;
            }
            for (NetworkInterface networkInterface : Collections.list(networkInterfaces)) {
                if (networkInterface.isUp()) {
                    map.put((TextUtils.isEmpty(networkInterface.getName()) || "null".equals(networkInterface.getName())) ? networkInterface.getDisplayName() : networkInterface.getName(), networkInterface);
                }
            }
            for (String str : map.keySet()) {
                if (str.contains("tun") || str.contains("ppp") || str.contains("pptp")) {
                    String displayName = ((NetworkInterface) map.get(str)).getDisplayName();
                    jSONObject.put("vpn_adapter", displayName);
                    jSONObject.put("detail", new JSONArray().put(displayName));
                }
            }
            String strVpn = com.coralline.sea.a.b.vpn();
            if (!TextUtils.isEmpty(strVpn)) {
                jSONObject.put("vpn_adapter", "noVPN");
                jSONObject.put("detail", strVpn);
            }
        } catch (Exception e) {
            e.toString();
        }
        return jSONObject;
    }
}
