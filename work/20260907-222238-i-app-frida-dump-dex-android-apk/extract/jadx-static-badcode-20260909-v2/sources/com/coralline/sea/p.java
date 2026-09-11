package com.coralline.sea;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class p {
    public static p c;
    public static Set<String> d = new HashSet();
    public JSONArray a;
    public final boolean b;

    public p() {
        this.b = Integer.parseInt(Build.VERSION.SDK) < 23;
    }

    public static synchronized p a() {
        if (c == null) {
            c = new p();
        }
        return c;
    }

    public String a(PackageManager packageManager, String str) {
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(str, 1);
        return packageArchiveInfo != null ? packageArchiveInfo.applicationInfo.packageName : i2.b;
    }

    public String a(String str) throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                byte[] bArr = new byte[1024];
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                int i = 0;
                while (i != -1) {
                    i = fileInputStream.read(bArr);
                    if (i > 0) {
                        messageDigest.update(bArr, 0, i);
                    }
                }
                String strA = a(messageDigest.digest());
                try {
                    fileInputStream.close();
                    return strA;
                } catch (Exception e) {
                    return strA;
                }
            } catch (Exception e2) {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                        return null;
                    } catch (Exception e3) {
                        return null;
                    }
                }
                return null;
            } catch (Throwable th) {
                th = th;
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception e4) {
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final String a(byte[] bArr) {
        String str = c7.c;
        for (byte b : bArr) {
            str = str + Integer.toString((b & 255) + 256, 16).substring(1);
        }
        return str.toLowerCase();
    }

    public final StringBuffer a(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        if (Build.VERSION.SDK_INT >= 21 && ((UsageStatsManager) context.getSystemService("usagestats")) != null) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            List<UsageStats> listC = ja.c(context);
            if (listC != null && !listC.isEmpty()) {
                for (int i = 0; i < listC.size(); i++) {
                    String packageName = listC.get(i).getPackageName();
                    if (jCurrentTimeMillis - Long.valueOf(listC.get(i).getLastTimeStamp()).longValue() < 60000) {
                        stringBuffer.append(packageName);
                    }
                }
            }
        }
        return stringBuffer;
    }

    public List<String> a(File file) throws IOException {
        ArrayList arrayList = new ArrayList();
        JarFile jarFile = new JarFile(file);
        try {
            Certificate[] certificateArrA = a(jarFile, jarFile.getJarEntry("AndroidManifest.xml"), new byte[8192]);
            if (certificateArrA != null) {
                for (Certificate certificate : certificateArrA) {
                    arrayList.add(b(certificate.getEncoded()));
                }
            }
        } catch (Exception e) {
        }
        return arrayList;
    }

    public JSONArray a(o1 o1Var, JSONArray jSONArray) {
        if (o1Var == null || jSONArray == null) {
            return null;
        }
        try {
            StringBuffer stringBufferB = b();
            if (stringBufferB == null || stringBufferB.length() == 0) {
                return null;
            }
            JSONArray jSONArray2 = new JSONArray();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("app_package");
                String strOptString = jSONObject.optString("app_name");
                if (stringBufferB.toString().contains(string) && !d.contains(string)) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("id", jSONObject.optInt("id", 0)).put("app_package", string).put("app_name", strOptString);
                    jSONArray2.put(jSONObject2);
                    d.add(string);
                }
            }
            if (jSONArray2.length() > 0) {
                return jSONArray2;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public Certificate[] a(JarFile jarFile, JarEntry jarEntry, byte[] bArr) {
        try {
            InputStream inputStream = jarFile.getInputStream(jarEntry);
            while (inputStream.read(bArr, 0, bArr.length) != -1) {
            }
            inputStream.close();
            if (jarEntry != null) {
                return jarEntry.getCertificates();
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public String b(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length * 2];
        for (int i = 0; i < length; i++) {
            byte b = bArr[i];
            int i2 = (b >> 4) & 15;
            int i3 = i * 2;
            cArr[i3] = (char) (i2 >= 10 ? (i2 + 97) - 10 : i2 + 48);
            int i4 = b & z3.h;
            cArr[i3 + 1] = (char) (i4 >= 10 ? (i4 + 97) - 10 : i4 + 48);
        }
        return new String(cArr);
    }

    public final StringBuffer b() {
        StringBuilder sb = new StringBuilder("Product Model: ");
        sb.append(Build.MODEL);
        sb.append(", ");
        sb.append(Build.VERSION.SDK);
        sb.append(", ");
        sb.append(Build.VERSION.RELEASE);
        if (!this.b) {
            return a(n3.a().a);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(b8.a());
        return stringBuffer;
    }

    public byte[] b(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = Integer.valueOf(str.substring(i2, i2 + 2), 16).byteValue();
        }
        return bArr;
    }

    public final String c(String str) throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        ByteArrayInputStream byteArrayInputStream2 = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(b(str));
            try {
                byte[] bArr = new byte[1024];
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                int i = 0;
                while (i != -1) {
                    i = byteArrayInputStream.read(bArr);
                    if (i > 0) {
                        messageDigest.update(bArr, 0, i);
                    }
                }
                String strA = a(messageDigest.digest());
                try {
                    byteArrayInputStream.close();
                    return strA;
                } catch (Exception e) {
                    return strA;
                }
            } catch (Exception e2) {
                if (byteArrayInputStream != null) {
                    try {
                        byteArrayInputStream.close();
                        return null;
                    } catch (Exception e3) {
                        return null;
                    }
                }
                return null;
            } catch (Throwable th) {
                th = th;
                byteArrayInputStream2 = byteArrayInputStream;
                if (byteArrayInputStream2 != null) {
                    try {
                        byteArrayInputStream2.close();
                    } catch (Exception e4) {
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
            byteArrayInputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final String d(String str) throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        ByteArrayInputStream byteArrayInputStream2 = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(b(str));
            try {
                byte[] bArr = new byte[1024];
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                int i = 0;
                while (i != -1) {
                    i = byteArrayInputStream.read(bArr);
                    if (i > 0) {
                        messageDigest.update(bArr, 0, i);
                    }
                }
                String strA = a(messageDigest.digest());
                try {
                    byteArrayInputStream.close();
                    return strA;
                } catch (Exception e) {
                    return strA;
                }
            } catch (Exception e2) {
                if (byteArrayInputStream != null) {
                    try {
                        byteArrayInputStream.close();
                        return null;
                    } catch (Exception e3) {
                        return null;
                    }
                }
                return null;
            } catch (Throwable th) {
                th = th;
                byteArrayInputStream2 = byteArrayInputStream;
                if (byteArrayInputStream2 != null) {
                    try {
                        byteArrayInputStream2.close();
                    } catch (Exception e4) {
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
            byteArrayInputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public JSONObject e(String str) throws IOException {
        String str2 = x1.a("ls " + str, true).c;
        if (str2 == null) {
            return null;
        }
        for (String str3 : str2.split("\n")) {
            String str4 = "/data/app/" + str3;
            String strA = a(n3.a().a.getPackageManager(), str4);
            for (int i = 0; i < this.a.length(); i++) {
                try {
                    JSONObject jSONObject = this.a.getJSONObject(i);
                    if (strA.equals(jSONObject.get("app_package"))) {
                        Iterator<String> it = a(new File(str4)).iterator();
                        while (it.hasNext()) {
                            if (d(it.next()).equals(jSONObject.get("app_md5"))) {
                                Objects.toString(jSONObject.get("app_package"));
                                return jSONObject;
                            }
                        }
                    } else {
                        continue;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void f(String str) {
        File file = new File(str);
        if (file.exists()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles.length == 0) {
                return;
            }
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    file2.getAbsolutePath();
                    f(file2.getAbsolutePath());
                } else {
                    file2.getAbsolutePath();
                }
            }
        }
    }
}
