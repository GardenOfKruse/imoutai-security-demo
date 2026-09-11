package com.coralline.sea;

import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class p9 {
    public static boolean a = true;
    public static int b = 1;
    public static int c = 5;
    public static int d = 9;
    public static int e = 1024;
    public static int f = 10240;
    public static Set<String> g = new HashSet();
    public static int h = 1;

    public static String a(String str) {
        if (!a) {
            return str;
        }
        byte[] bArrA = a(str.getBytes());
        if (bArrA == null) {
            return null;
        }
        return Base64.encodeToString(bArrA, 2);
    }

    public static void a() {
        JSONObject jSONObjectB = z1.b("compression");
        if (jSONObjectB != null) {
            try {
                if (jSONObjectB.has("enable")) {
                    a = jSONObjectB.getBoolean("enable");
                }
            } catch (Exception e2) {
                jSONObjectB.toString();
                return;
            } catch (Throwable th) {
                jSONObjectB.toString();
                throw th;
            }
        }
        Objects.toString(jSONObjectB);
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e2) {
            }
        }
    }

    public static byte[] a(byte[] bArr) {
        return !a ? bArr : c(bArr);
    }

    public static String b(String str) {
        return !a ? str : new String(b(Base64.decode(str, 2)));
    }

    public static byte[] b(byte[] bArr) {
        return !a ? bArr : d(bArr);
    }

    public static byte[] c(byte[] bArr) {
        int i;
        int i2;
        byte[] bArr2 = new byte[0];
        if (bArr == null) {
            return null;
        }
        try {
            if (bArr.length <= e) {
                i2 = b;
            } else {
                int length = bArr.length;
                i = c;
                if (length <= i) {
                    p0 p0Var = new p0(bArr.length);
                    Deflater deflater = new Deflater(i);
                    DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(p0Var, deflater);
                    deflaterOutputStream.write(bArr);
                    deflaterOutputStream.close();
                    deflater.end();
                    return p0Var.c();
                }
                i2 = d;
            }
            i = i2;
            p0 p0Var2 = new p0(bArr.length);
            Deflater deflater2 = new Deflater(i);
            DeflaterOutputStream deflaterOutputStream2 = new DeflaterOutputStream(p0Var2, deflater2);
            deflaterOutputStream2.write(bArr);
            deflaterOutputStream2.close();
            deflater2.end();
            return p0Var2.c();
        } catch (IOException e2) {
            return bArr2;
        }
    }

    public static byte[] d(byte[] bArr) {
        Inflater inflater = new Inflater();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length);
        try {
            try {
                byte[] bArr2 = new byte[1024];
                inflater.setInput(bArr);
                while (!inflater.finished()) {
                    byteArrayOutputStream.write(bArr2, 0, inflater.inflate(bArr2));
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    return byteArray;
                } catch (Exception e2) {
                    return byteArray;
                }
            } catch (DataFormatException e3) {
                e3.printStackTrace();
                try {
                    byteArrayOutputStream.close();
                    inflater.end();
                    return bArr;
                } catch (Exception e4) {
                    return bArr;
                }
            }
        } finally {
            try {
                byteArrayOutputStream.close();
                inflater.end();
            } catch (Exception e5) {
            }
        }
    }
}
