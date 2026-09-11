package com.coralline.sea;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
@Deprecated
public class s7 extends t6 {
    public s7() {
        super("resign");
    }

    public final String a() {
        try {
            if (n3.a().g) {
                return o6.f().a(this.checkerName).optString("cert_md5");
            }
            JSONObject jSONObjectA = z1.a(this.checkerName);
            return (jSONObjectA == null || !jSONObjectA.has("fingerprints_md5")) ? c7.c : jSONObjectA.getString("fingerprints_md5").replace(":", c7.c).toLowerCase();
        } catch (JSONException e) {
            e.printStackTrace();
            return c7.c;
        }
    }

    public final String a(byte[] bArr) {
        CertificateFactory certificateFactory;
        X509Certificate x509Certificate;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        try {
            certificateFactory = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            certificateFactory = null;
        }
        try {
            x509Certificate = (X509Certificate) certificateFactory.generateCertificate(byteArrayInputStream);
        } catch (CertificateException e2) {
            x509Certificate = null;
        }
        try {
            return ja.a(MessageDigest.getInstance("MD5").digest(x509Certificate.getEncoded()));
        } catch (NoSuchAlgorithmException | CertificateEncodingException e3) {
            return null;
        }
    }

    public final byte[] a(String str) {
        Signature[] signatureArr;
        try {
            List<PackageInfo> listA = v6.a(64);
            if (listA == null) {
                return null;
            }
            for (PackageInfo packageInfo : listA) {
                if (TextUtils.equals(packageInfo.packageName, str) && (signatureArr = packageInfo.signatures) != null && signatureArr.length > 0) {
                    return signatureArr[0].toByteArray();
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public final String b() {
        byte[] bArrA = a(n3.a().a.getPackageName());
        return bArrA == null ? c7.c : a(bArrA).toLowerCase();
    }

    public final String c() {
        Signature[] signatureArr;
        Signature signature;
        try {
            PackageInfo packageInfoA = v6.a(n3.a().a, n3.T.a.getPackageName(), 64);
            return (packageInfoA == null || (signatureArr = packageInfoA.signatures) == null || (signature = signatureArr[0]) == null || signature.toByteArray() == null) ? c7.c : a(packageInfoA.signatures[0].toByteArray()).toLowerCase();
        } catch (Exception e) {
            e.toString();
            return c7.c;
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        JSONObject jSONObjectE = e();
        if (jSONObjectE != null) {
            push(e2.b, "resign", jSONObjectE.toString());
        }
    }

    public final boolean d() {
        Objects.toString(n3.a().a);
        try {
            PackageManager packageManager = n3.T.a.getPackageManager();
            Field declaredField = packageManager.getClass().getDeclaredField("mPM");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(packageManager);
            if (obj == null) {
                return false;
            }
            String name = obj.getClass().getName();
            String str = (String) y8.a().c("sPackageManager");
            if (!TextUtils.isEmpty(str)) {
                name = str;
            }
            return !"android.content.pm.IPackageManager$Stub$Proxy".equals(name);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }

    public JSONObject e() {
        try {
            String strA = a();
            String strB = b();
            String strC = c();
            boolean zD = d();
            if (!zD && (strA.length() != 0 || strB.equals(strC))) {
                if (strA.length() <= 0) {
                    return null;
                }
                if (strB.equals(strC) && strA.equals(strB)) {
                    return null;
                }
            }
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            if (strA.length() > 0) {
                jSONObject2.put("legal_sign", strA);
            } else {
                jSONObject2.put("legal_sign", strC);
            }
            jSONObject2.put("illegal_sign", strB);
            jSONObject2.put("hooked", zD);
            jSONObject.put("resign", jSONObject2);
            return jSONObject;
        } catch (Exception e) {
            getName();
            return null;
        }
    }
}
