package com.coralline.sea;

import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class h2 {
    public static volatile h2 a;

    public static h2 b() {
        if (a == null) {
            synchronized (h2.class) {
                if (a == null) {
                    a = new h2();
                }
            }
        }
        return a;
    }

    public final String a(byte[] bArr) {
        char[] charArray = "0123456789ABCDEF".toCharArray();
        char[] cArr = new char[bArr.length * 2];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i] & 255;
            int i3 = i * 2;
            cArr[i3] = charArray[i2 >>> 4];
            cArr[i3 + 1] = charArray[i2 & 15];
        }
        return new String(cArr);
    }

    public final JSONArray a(List<X509Certificate> list) throws Exception {
        JSONArray jSONArray = new JSONArray();
        for (X509Certificate x509Certificate : list) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("version", x509Certificate.getVersion());
            jSONObject.put("serial", x509Certificate.getSerialNumber());
            jSONObject.put("issuer", x509Certificate.getIssuerDN());
            jSONObject.put("not_before", x509Certificate.getNotBefore().getTime());
            jSONObject.put("not_after", x509Certificate.getNotAfter().getTime());
            jSONObject.put("subject", x509Certificate.getSubjectDN().toString());
            jSONObject.put("signature_algorithm_name", x509Certificate.getSigAlgName());
            jSONObject.put("signature_algorithm_id", x509Certificate.getSigAlgOID());
            jSONObject.put("signature", a(x509Certificate.getSignature()));
            jSONObject.put("md5", a(MessageDigest.getInstance("MD5").digest(x509Certificate.getEncoded())));
            jSONObject.put("detail", new JSONArray().put(x509Certificate.getSigAlgName()));
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    public JSONObject a() {
        try {
            List<X509Certificate> listC = c();
            if (listC == null || listC.size() <= 0) {
                return null;
            }
            JSONArray jSONArrayA = a(listC);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("data", jSONArrayA);
            return jSONObject;
        } catch (Exception e) {
            return null;
        }
    }

    public final List<X509Certificate> c() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("AndroidCAStore");
        if (keyStore == null) {
            return null;
        }
        keyStore.load(null, null);
        Enumeration<String> enumerationAliases = keyStore.aliases();
        ArrayList arrayList = new ArrayList();
        while (enumerationAliases.hasMoreElements()) {
            String strNextElement = enumerationAliases.nextElement();
            if (!strNextElement.contains("system")) {
                arrayList.add((X509Certificate) keyStore.getCertificate(strNextElement));
            }
            if (g9.p && strNextElement.contains("system")) {
                arrayList.add((X509Certificate) keyStore.getCertificate(strNextElement));
            }
        }
        arrayList.size();
        return arrayList;
    }
}
