package com.coralline.sea;

import android.content.Context;
import android.os.Process;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class x5 {
    public static x5 b = new x5();
    public a0 a;

    public x5() {
        this.a = null;
        try {
            Context context = n3.a().a;
            if (context == null) {
                return;
            }
            this.a = new a0(context);
        } catch (Exception e) {
        }
    }

    public static x5 c() {
        return b;
    }

    public final String a() throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        Context context = n3.a().a;
        ByteArrayInputStream byteArrayInputStream2 = null;
        str = null;
        str = null;
        String str = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(v6.a(context, context.getPackageName(), 64).signatures[0].toByteArray());
            try {
                String strB = c4.b(((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(byteArrayInputStream)).getEncoded());
                try {
                    byteArrayInputStream.close();
                    str = strB;
                } catch (IOException e) {
                    str = strB;
                    e = e;
                    e.printStackTrace();
                }
            } catch (Exception e2) {
                if (byteArrayInputStream != null) {
                    try {
                        byteArrayInputStream.close();
                    } catch (IOException e3) {
                        e = e3;
                        e.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                th = th;
                byteArrayInputStream2 = byteArrayInputStream;
                if (byteArrayInputStream2 != null) {
                    try {
                        byteArrayInputStream2.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
            byteArrayInputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
        return str == null ? i2.b : str;
    }

    public final String b() throws Throwable {
        JarFile jarFile;
        JarEntry jarEntry;
        JarFile jarFile2 = null;
        str = null;
        str = null;
        String str = null;
        try {
            jarFile = new JarFile(n3.a().a.getPackageCodePath(), true);
        } catch (Exception e) {
            jarFile = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            jarEntry = jarFile.getJarEntry("META-INF/MANIFEST.MF");
        } catch (Exception e2) {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e3) {
                }
            }
        } catch (Throwable th2) {
            th = th2;
            jarFile2 = jarFile;
            if (jarFile2 != null) {
                try {
                    jarFile2.close();
                } catch (IOException e4) {
                }
            }
            throw th;
        }
        if (jarEntry == null) {
            jarFile.close();
            try {
                jarFile.close();
                return i2.b;
            } catch (IOException e5) {
                return i2.b;
            }
        }
        String str2 = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss", Locale.CHINA).format(new Date(jarEntry.getTime()));
        try {
            jarFile.close();
        } catch (IOException e6) {
        }
        str = str2;
        return str == null ? i2.b : str;
    }

    public JSONArray d() {
        JSONObject jSONObjectA = ja.a();
        JSONArray jSONArray = new JSONArray();
        if (jSONObjectA != null) {
            try {
                if (jSONObjectA.length() > 0) {
                    Iterator<String> itKeys = jSONObjectA.keys();
                    while (itKeys.hasNext()) {
                        try {
                            String next = itKeys.next();
                            boolean z = jSONObjectA.getBoolean(next);
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("perm_name", next);
                            jSONObject.put("perm_value", z);
                            jSONArray.put(jSONObject);
                        } catch (Exception e) {
                        }
                    }
                    jSONArray.toString(4);
                    return jSONArray;
                }
            } catch (Exception e2) {
            }
        }
        return jSONArray;
    }

    public JSONObject e() {
        JSONObject jSONObjectB;
        JSONObject jSONObject = new JSONObject();
        Context context = n3.a().a;
        if (context == null) {
            return jSONObject;
        }
        try {
            String packageName = context.getPackageName();
            String strI = ja.i("cat /proc/" + Process.myPid() + "/cmdline");
            jSONObject.put("net_type", l6.b(context));
            jSONObject.put("pid", Process.myPid());
            jSONObject.put("pname", strI != null ? strI.trim() : c7.c);
            jSONObject.put("uid", Process.myUid());
            jSONObject.put("uname", ja.a(0, packageName));
            jSONObject.put("udid_from", n3.T.h());
            jSONObject.put("permission", d());
            jSONObject.put("time_zone", ja.k());
            jSONObject.put("app_name", ja.a(context, packageName));
            jSONObject.put("app_version", ja.f());
            jSONObject.put("cert_md5", u.a());
            jSONObject.put("cert_time", ja.i());
            jSONObject.put("package_size", this.a.a(n3.T.a, packageName));
            jSONObject.put("magic", n3.T.j);
            jSONObject.put("fg_factor", n3.T.g().optString("fg_factor", i2.b));
            if (j6.e()) {
                jSONObject.put("safe_mode", j6.a());
            }
            if (n3.T.d() && (jSONObjectB = i2.b(n3.T.a)) != null) {
                Iterator<String> itKeys = jSONObjectB.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    Object objOpt = jSONObjectB.opt(next);
                    if (next != null && objOpt != null) {
                        jSONObject.put(next, objOpt);
                    }
                }
            }
        } catch (Exception e) {
        }
        return jSONObject;
    }
}
