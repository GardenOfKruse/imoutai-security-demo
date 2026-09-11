package com.coralline.sea;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;
import com.coralline.sea.s1;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.util.encoders.Hex;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class ja {
    public static final String g = "com.android.permission.GET_INSTALLED_APPS";
    public static final String h = "android.permission.QUERY_ALL_PACKAGES";
    public static final char[] a = "0123456789ABCDEF".toCharArray();
    public static int b = Build.VERSION.SDK_INT;
    public static long c = 0;
    public static String d = null;
    public static String e = i2.b;
    public static Integer f = null;
    public static final HashMap<String, String> i = new HashMap<>();
    public static final HashMap<String, Boolean> j = new HashMap<>(1);
    public static boolean k = true;

    public class a extends TimerTask {
        public final /* synthetic */ Toast[] a;
        public final /* synthetic */ Context b;
        public final /* synthetic */ String c;
        public final /* synthetic */ int d;

        /* JADX INFO: renamed from: com.coralline.sea.ja$a$a, reason: collision with other inner class name */
        public class RunnableC0004a implements Runnable {
            public RunnableC0004a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                a aVar = a.this;
                aVar.a[0] = Toast.makeText(aVar.b, aVar.c, 1);
                a aVar2 = a.this;
                aVar2.a[0].setGravity(aVar2.d, 0, 0);
                a.this.a[0].show();
            }
        }

        public a(Toast[] toastArr, Context context, String str, int i) {
            this.a = toastArr;
            this.b = context;
            this.c = str;
            this.d = i;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            new Handler(Looper.getMainLooper()).post(new RunnableC0004a());
        }
    }

    public class b extends TimerTask {
        public final /* synthetic */ Toast[] a;
        public final /* synthetic */ Timer b;

        public class a implements Runnable {
            public a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                b.this.a[0].cancel();
            }
        }

        public b(Toast[] toastArr, Timer timer) {
            this.a = toastArr;
            this.b = timer;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            new Handler(Looper.getMainLooper()).post(new a());
            this.b.cancel();
        }
    }

    public static int a(int i2, int i3) {
        return (int) ((new Random().nextFloat() * ((i3 - i2) + 1)) + i2);
    }

    public static int a(int i2, int i3, String str) {
        return (int) ((new Random().nextFloat() * ((i3 - i2) + 1)) + i2);
    }

    public static String a(int i2, String str) {
        String str2 = i2.b;
        try {
            String strA = b8.a();
            if (strA != null) {
                if (strA.length() < 1) {
                    return i2.b;
                }
                for (String str3 : strA.split("\n")) {
                    if (str3.contains(str)) {
                        str2 = str3.split("\\s+")[i2];
                    }
                }
            }
            return str2;
        } catch (Exception e2) {
            return str2;
        }
    }

    public static String a(Context context) {
        return u9.a(context, "hw_sc.build.platform.version", c7.c);
    }

    public static synchronized String a(Context context, int i2) {
        return c7.c;
    }

    public static synchronized String a(Context context, String str) {
        String string;
        PackageManager packageManager = context.getPackageManager();
        try {
            string = packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 0)).toString();
        } catch (Exception e2) {
            string = i2.b;
        }
        return string;
    }

    public static String a(Location location) {
        return location == null ? c7.c : String.format(Locale.CHINA, "+%f-%f", Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude()));
    }

    public static String a(String str, String str2) {
        try {
            try {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                return (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, str2);
            } catch (Exception e2) {
                e2.printStackTrace();
                return str2;
            }
        } catch (Throwable th) {
            return str2;
        }
    }

    public static String a(JSONObject jSONObject, String str) {
        jSONObject.toString();
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("instruction_v493");
            if (jSONObject2 != null) {
                String string = c7.c;
                boolean z = false;
                if (jSONObject2.has("source")) {
                    string = jSONObject2.getString("source");
                    if (!str.equals(string)) {
                        z = true;
                        str = string;
                    }
                }
                if (z && jSONObject2.has(s1.a.a) && jSONObject2.getString(s1.a.a).length() > 0) {
                    String strTrim = jSONObject2.getString(s1.a.a).trim();
                    String string2 = jSONObject2.has("title") ? jSONObject2.getString("title") : "温馨提示";
                    if (jSONObject2.optString("action", c7.c).length() > 0) {
                        String strTrim2 = jSONObject2.optString("action", c7.c).trim();
                        if (strTrim2.contains("quit")) {
                            j6.f();
                        }
                        fa.a(string2, strTrim, strTrim2, string);
                        return str;
                    }
                }
            }
        } catch (Exception e2) {
        }
        return str;
    }

    public static String a(boolean z, boolean z2, boolean z3, int i2) {
        char c2;
        int i3 = i2 / 60000;
        if (i3 < 0) {
            c2 = '-';
            i3 = -i3;
        } else {
            c2 = '+';
        }
        StringBuilder sb = new StringBuilder(9);
        if (z) {
            sb.append("GMT");
        }
        sb.append(c2);
        if (z2) {
            a(sb, 2, i3 / 60);
        } else {
            sb.append(i3 / 60);
        }
        if (z3) {
            sb.append(':');
            if (z2) {
                a(sb, 2, i3 % 60);
            } else {
                sb.append(i3 % 60);
            }
        }
        return sb.toString();
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return c7.c;
        }
        char[] cArr = new char[bArr.length * 2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = bArr[i2] & 255;
            int i4 = i2 * 2;
            char[] cArr2 = a;
            cArr[i4] = cArr2[i3 >>> 4];
            cArr[i4 + 1] = cArr2[i3 & 15];
        }
        return new String(cArr);
    }

    public static String a(String[] strArr, int i2, String str) {
        for (String str2 : strArr) {
            if (str2.contains(str)) {
                return str2.split("\\s+")[i2];
            }
        }
        return i2.b;
    }

    public static JSONObject a() {
        try {
            JSONObject jSONObject = new JSONObject("{\"INTERNET\":false,\"READ_PHONE_STATE\":false,\"ACCESS_NETWORK_STATE\":false,\"ACCESS_COARSE_LOCATION\":false,\"ACCESS_FINE_LOCATION\":false,\"ACCESS_WIFI_STATE\":false}");
            try {
                jSONObject.put("INTERNET", r("android.permission.INTERNET") == 0);
                jSONObject.put("READ_PHONE_STATE", r("android.permission.READ_PHONE_STATE") == 0);
                jSONObject.put("ACCESS_NETWORK_STATE", r("android.permission.ACCESS_NETWORK_STATE") == 0);
                jSONObject.put("ACCESS_COARSE_LOCATION", r("android.permission.ACCESS_COARSE_LOCATION") == 0);
                jSONObject.put("ACCESS_FINE_LOCATION", r("android.permission.ACCESS_FINE_LOCATION") == 0);
                jSONObject.put("ACCESS_WIFI_STATE", r("android.permission.ACCESS_WIFI_STATE") == 0);
                jSONObject.put("WRITE_EXTERNAL_STORAGE", r("android.permission.WRITE_EXTERNAL_STORAGE") == 0);
                return jSONObject;
            } catch (JSONException e2) {
                return jSONObject;
            }
        } catch (JSONException e3) {
            return null;
        }
    }

    public static JSONObject a(int i2) {
        JSONArray jSONArrayD;
        JSONArray jSONArray;
        try {
            new Date(System.currentTimeMillis()).toString();
            JSONObject jSONObject = new JSONObject();
            if (n3.a().a == null) {
                return null;
            }
            if (b >= 21) {
                jSONArrayD = o7.d("imei");
                jSONArray = o7.a("imsi", true);
            } else {
                String strA = o7.a();
                if (strA != null) {
                    JSONArray jSONArray2 = new JSONArray();
                    jSONArray2.put(strA);
                    jSONArrayD = jSONArray2;
                } else {
                    jSONArrayD = null;
                }
                if (TextUtils.isEmpty(a9.a("imsi", c7.c))) {
                    String strB = o7.b(true);
                    if (strB != null) {
                        jSONArray = new JSONArray();
                        jSONArray.put(strB);
                        a9.b("imsi", strB);
                    } else {
                        jSONArray = null;
                    }
                } else {
                    String strA2 = a9.a("imsi", c7.c);
                    jSONArray = new JSONArray();
                    jSONArray.put(strA2);
                }
            }
            System.currentTimeMillis();
            if (jSONArrayD != null && jSONArrayD.length() == 0) {
                jSONArrayD.put(o7.a());
            }
            a(jSONObject, "imei", jSONArrayD);
            System.currentTimeMillis();
            if (n3.T.d) {
                a(jSONObject, "meid", jSONArrayD);
            }
            a(jSONObject, "imsi", jSONArray);
            if (i2 == 0) {
                a(jSONObject, "android_id", d());
                System.currentTimeMillis();
                String strB2 = l6.b();
                a(jSONObject, "mac", strB2);
                System.currentTimeMillis();
                if (strB2 != null) {
                    jSONObject.put("mac_type", t() ? "random" : "normal");
                }
            }
            a(jSONObject, "bht_mac", l6.a());
            System.currentTimeMillis();
            y2.a().b(jSONObject);
            System.currentTimeMillis();
            if (n3.T.d) {
                jSONObject.put("origin_cert_md5", h());
            }
            System.currentTimeMillis();
            return jSONObject;
        } catch (Exception e2) {
            return null;
        }
    }

    public static synchronized JSONObject a(JSONObject jSONObject, JSONObject jSONObject2) {
        try {
            Iterator<String> itKeys = jSONObject2.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                jSONObject.put(next, jSONObject2.get(next));
            }
        } catch (JSONException e2) {
        }
        return jSONObject;
    }

    public static void a(float f2) {
        try {
            Thread.sleep((int) (f2 * 1000.0f));
        } catch (Exception e2) {
        }
    }

    public static void a(Context context, String str, double d2, int i2) {
        Toast[] toastArr = {Toast.makeText(context, str, 1)};
        Timer timer = new Timer();
        timer.schedule(new a(toastArr, context, str, i2), 0L, 3500L);
        new Timer().schedule(new b(toastArr, timer), (long) (d2 * 1000.0d));
    }

    public static void a(StringBuilder sb, int i2, int i3) {
        String string = Integer.toString(i3);
        for (int i4 = 0; i4 < i2 - string.length(); i4++) {
            sb.append('0');
        }
        sb.append(string);
    }

    public static void a(JSONObject jSONObject, String str, Object obj) {
        if (obj != null) {
            try {
                jSONObject.put(str, obj);
            } catch (Exception e2) {
            }
        }
    }

    public static boolean a(File file, File file2) throws Throwable {
        Throwable th;
        BufferedInputStream bufferedInputStream;
        BufferedOutputStream bufferedOutputStream;
        file.getAbsolutePath();
        file2.getAbsolutePath();
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            if (!file.exists() || !b(file2.getParent())) {
                throw null;
            }
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            try {
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
            } catch (Exception e2) {
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int i2 = bufferedInputStream.read(bArr);
                    if (i2 == -1) {
                        bufferedOutputStream.flush();
                        try {
                            bufferedInputStream.close();
                            bufferedOutputStream.close();
                            return true;
                        } catch (IOException e3) {
                            e3.printStackTrace();
                            return true;
                        }
                    }
                    bufferedOutputStream.write(bArr, 0, i2);
                }
            } catch (Exception e4) {
                bufferedOutputStream2 = bufferedOutputStream;
            } catch (Throwable th3) {
                th = th3;
                bufferedOutputStream2 = bufferedOutputStream;
                try {
                    bufferedInputStream.close();
                    bufferedOutputStream2.close();
                    throw th;
                } catch (IOException e5) {
                    e5.printStackTrace();
                    throw th;
                }
            }
        } catch (Exception e6) {
            bufferedInputStream = null;
        } catch (Throwable th4) {
            th = th4;
            bufferedInputStream = null;
        }
        try {
            bufferedInputStream.close();
            bufferedOutputStream2.close();
            return false;
        } catch (IOException e7) {
            e7.printStackTrace();
            return false;
        }
    }

    public static boolean a(String str) {
        try {
            return new File(str).delete();
        } catch (Exception e2) {
            return false;
        }
    }

    public static String b(Context context, String str) {
        InputStream inputStreamOpen;
        try {
            inputStreamOpen = context.getApplicationContext().getAssets().open(str);
        } catch (Exception e2) {
            inputStreamOpen = null;
        } catch (Throwable th) {
            th = th;
            inputStreamOpen = null;
        }
        try {
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            String str2 = new String(bArr);
            try {
                inputStreamOpen.close();
            } catch (IOException e3) {
            }
            return str2;
        } catch (Exception e4) {
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                    return null;
                } catch (IOException e5) {
                    return null;
                }
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (IOException e6) {
                }
            }
            throw th;
        }
    }

    public static void b() {
        try {
            Class.forName("android.content.pm.PackageParser$Package").getDeclaredConstructor(String.class).setAccessible(true);
        } catch (Exception e2) {
        }
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread", new Class[0]);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(null, new Object[0]);
            Field declaredField = cls.getDeclaredField("mHiddenApiWarningShown");
            declaredField.setAccessible(true);
            declaredField.setBoolean(objInvoke, true);
        } catch (Exception e3) {
        }
    }

    public static void b(int i2) {
        try {
            Thread.sleep(i2 * 1000);
        } catch (Exception e2) {
        }
    }

    public static boolean b(Context context) {
        if (Build.VERSION.SDK_INT <= 29) {
            return true;
        }
        if (context == null) {
            return false;
        }
        if (context.checkSelfPermission(h) == 0) {
            return true;
        }
        try {
            PermissionInfo permissionInfo = context.getPackageManager().getPermissionInfo(g, 0);
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(permissionInfo.packageName, 0);
            if ((permissionInfo.protectionLevel & 15) != 1 || (applicationInfo.flags & 1) == 0) {
                return true;
            }
            return context.checkSelfPermission(g) == 0;
        } catch (Exception e2) {
            return true;
        }
    }

    public static boolean b(String str) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                return file.mkdirs();
            }
            if (file.isDirectory()) {
                return true;
            }
            if (a(str)) {
                return file.mkdirs();
            }
            return false;
        } catch (Exception e2) {
            return false;
        }
    }

    public static boolean b(String str, String str2) {
        try {
            return Pattern.compile(str2).matcher(str).matches();
        } catch (Exception e2) {
            return false;
        }
    }

    public static String c() throws Throwable {
        try {
            String strI = i("ps");
            return strI == null ? i("ps -ef") : strI;
        } catch (Exception e2) {
            e2.printStackTrace();
            return c7.c;
        }
    }

    public static List<UsageStats> c(Context context) {
        UsageStatsManager usageStatsManager;
        if (Build.VERSION.SDK_INT < 21 || ((AppOpsManager) context.getSystemService("appops")).checkOpNoThrow("android:get_usage_stats", Process.myUid(), context.getPackageName()) != 0 || (usageStatsManager = (UsageStatsManager) context.getSystemService("usagestats")) == null) {
            return null;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        return usageStatsManager.queryUsageStats(4, jCurrentTimeMillis - 60000, jCurrentTimeMillis);
    }

    public static List<String> c(String str) {
        List<String> list = n3.a().l;
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (String strSubstring : list) {
            if (strSubstring.endsWith("/")) {
                strSubstring = strSubstring.substring(0, strSubstring.length() - 1);
            }
            arrayList.add(strSubstring + str);
        }
        return arrayList;
    }

    public static void c(String str, String str2) {
        if (str.contains("pm list package") || str.equals("ps") || str.equals("ps -ef")) {
            return;
        }
        i.put(str, str2);
    }

    public static String d() {
        String str = n3.a().H;
        try {
            return (TextUtils.isEmpty(str) || aa.m.equalsIgnoreCase(str) || i2.b.equalsIgnoreCase(str)) ? Settings.Secure.getString(n3.T.a.getContentResolver(), "android_id") : str;
        } catch (Exception e2) {
            return c7.c;
        }
    }

    public static String d(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] bArrDigest = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b2 : bArrDigest) {
                String hexString = Integer.toHexString(b2 & 255);
                if (hexString.length() == 1) {
                    sb.append('0');
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e2) {
            return null;
        }
    }

    public static void d(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            cls.getMethod("set", String.class, String.class).invoke(cls, str, str2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static String e() {
        try {
            Context context = n3.a().a;
            int i2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            if (i2 > 0) {
                return c7.c + i2;
            }
        } catch (Exception e2) {
        }
        return i2.b;
    }

    public static boolean e(String str) {
        return r(str) == 0;
    }

    public static String f() {
        try {
            return n3.a().a.getPackageManager().getPackageInfo(n3.T.a.getPackageName(), 0).versionName;
        } catch (Exception e2) {
            return i2.b;
        }
    }

    public static boolean f(String str) {
        if (str == null) {
            return false;
        }
        try {
            return str.replace("\\", c7.c).contains(i2.b);
        } catch (Exception e2) {
            return false;
        }
    }

    public static String g() throws Throwable {
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
                } catch (IOException e2) {
                }
                str = strB;
            } catch (Exception e3) {
                if (byteArrayInputStream != null) {
                    try {
                        byteArrayInputStream.close();
                    } catch (IOException e4) {
                    }
                }
            } catch (Throwable th) {
                th = th;
                byteArrayInputStream2 = byteArrayInputStream;
                if (byteArrayInputStream2 != null) {
                    try {
                        byteArrayInputStream2.close();
                    } catch (IOException e5) {
                    }
                }
                throw th;
            }
        } catch (Exception e6) {
            byteArrayInputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
        return str == null ? i2.b : str;
    }

    public static boolean g(String str) {
        if (str == null) {
            return false;
        }
        try {
            return str.replace("\\", c7.c).contains("N/P");
        } catch (Exception e2) {
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x016e A[Catch: all -> 0x01cb, TRY_LEAVE, TryCatch #11 {, blocks: (B:4:0x0003, B:6:0x000d, B:9:0x0011, B:31:0x008b, B:33:0x0090, B:37:0x0098, B:39:0x009d, B:43:0x00a5, B:44:0x00a8, B:48:0x00b0, B:49:0x00b3, B:53:0x00bb, B:52:0x00b8, B:47:0x00ad, B:42:0x00a2, B:36:0x0095, B:61:0x011b, B:62:0x011e, B:66:0x0126, B:68:0x012b, B:72:0x0133, B:73:0x0136, B:77:0x013e, B:138:0x01bc, B:142:0x01c4, B:141:0x01c1, B:76:0x013b, B:71:0x0130, B:65:0x0123, B:89:0x0154, B:91:0x0159, B:95:0x0161, B:97:0x0166, B:101:0x016e, B:103:0x0173, B:107:0x017b, B:109:0x0180, B:113:0x0188, B:114:0x018b, B:112:0x0185, B:106:0x0178, B:100:0x016b, B:94:0x015e, B:118:0x0190, B:120:0x0195, B:124:0x019d, B:126:0x01a2, B:130:0x01aa, B:132:0x01af, B:136:0x01b7, B:135:0x01b4, B:129:0x01a7, B:123:0x019a), top: B:163:0x0003, inners: #0, #1, #2, #3, #7, #8, #9, #12, #14, #16, #17, #18, #19, #21, #22 }] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x017b A[Catch: all -> 0x01cb, TRY_LEAVE, TryCatch #11 {, blocks: (B:4:0x0003, B:6:0x000d, B:9:0x0011, B:31:0x008b, B:33:0x0090, B:37:0x0098, B:39:0x009d, B:43:0x00a5, B:44:0x00a8, B:48:0x00b0, B:49:0x00b3, B:53:0x00bb, B:52:0x00b8, B:47:0x00ad, B:42:0x00a2, B:36:0x0095, B:61:0x011b, B:62:0x011e, B:66:0x0126, B:68:0x012b, B:72:0x0133, B:73:0x0136, B:77:0x013e, B:138:0x01bc, B:142:0x01c4, B:141:0x01c1, B:76:0x013b, B:71:0x0130, B:65:0x0123, B:89:0x0154, B:91:0x0159, B:95:0x0161, B:97:0x0166, B:101:0x016e, B:103:0x0173, B:107:0x017b, B:109:0x0180, B:113:0x0188, B:114:0x018b, B:112:0x0185, B:106:0x0178, B:100:0x016b, B:94:0x015e, B:118:0x0190, B:120:0x0195, B:124:0x019d, B:126:0x01a2, B:130:0x01aa, B:132:0x01af, B:136:0x01b7, B:135:0x01b4, B:129:0x01a7, B:123:0x019a), top: B:163:0x0003, inners: #0, #1, #2, #3, #7, #8, #9, #12, #14, #16, #17, #18, #19, #21, #22 }] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0195 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0173 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01af A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0166 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:170:0x01a2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0180 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:174:0x009d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0159 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a5 A[Catch: all -> 0x01cb, TRY_LEAVE, TryCatch #11 {, blocks: (B:4:0x0003, B:6:0x000d, B:9:0x0011, B:31:0x008b, B:33:0x0090, B:37:0x0098, B:39:0x009d, B:43:0x00a5, B:44:0x00a8, B:48:0x00b0, B:49:0x00b3, B:53:0x00bb, B:52:0x00b8, B:47:0x00ad, B:42:0x00a2, B:36:0x0095, B:61:0x011b, B:62:0x011e, B:66:0x0126, B:68:0x012b, B:72:0x0133, B:73:0x0136, B:77:0x013e, B:138:0x01bc, B:142:0x01c4, B:141:0x01c1, B:76:0x013b, B:71:0x0130, B:65:0x0123, B:89:0x0154, B:91:0x0159, B:95:0x0161, B:97:0x0166, B:101:0x016e, B:103:0x0173, B:107:0x017b, B:109:0x0180, B:113:0x0188, B:114:0x018b, B:112:0x0185, B:106:0x0178, B:100:0x016b, B:94:0x015e, B:118:0x0190, B:120:0x0195, B:124:0x019d, B:126:0x01a2, B:130:0x01aa, B:132:0x01af, B:136:0x01b7, B:135:0x01b4, B:129:0x01a7, B:123:0x019a), top: B:163:0x0003, inners: #0, #1, #2, #3, #7, #8, #9, #12, #14, #16, #17, #18, #19, #21, #22 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0161 A[Catch: all -> 0x01cb, TRY_LEAVE, TryCatch #11 {, blocks: (B:4:0x0003, B:6:0x000d, B:9:0x0011, B:31:0x008b, B:33:0x0090, B:37:0x0098, B:39:0x009d, B:43:0x00a5, B:44:0x00a8, B:48:0x00b0, B:49:0x00b3, B:53:0x00bb, B:52:0x00b8, B:47:0x00ad, B:42:0x00a2, B:36:0x0095, B:61:0x011b, B:62:0x011e, B:66:0x0126, B:68:0x012b, B:72:0x0133, B:73:0x0136, B:77:0x013e, B:138:0x01bc, B:142:0x01c4, B:141:0x01c1, B:76:0x013b, B:71:0x0130, B:65:0x0123, B:89:0x0154, B:91:0x0159, B:95:0x0161, B:97:0x0166, B:101:0x016e, B:103:0x0173, B:107:0x017b, B:109:0x0180, B:113:0x0188, B:114:0x018b, B:112:0x0185, B:106:0x0178, B:100:0x016b, B:94:0x015e, B:118:0x0190, B:120:0x0195, B:124:0x019d, B:126:0x01a2, B:130:0x01aa, B:132:0x01af, B:136:0x01b7, B:135:0x01b4, B:129:0x01a7, B:123:0x019a), top: B:163:0x0003, inners: #0, #1, #2, #3, #7, #8, #9, #12, #14, #16, #17, #18, #19, #21, #22 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static synchronized java.lang.String h() {
        /*
            Method dump skipped, instruction units count: 462
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.ja.h():java.lang.String");
    }

    public static boolean h(String str) {
        if (str == null) {
            return false;
        }
        try {
            return str.replace("\\", c7.c).equals(i2.b);
        } catch (Exception e2) {
            return false;
        }
    }

    public static String i() throws Throwable {
        JarFile jarFile;
        JarFile jarFile2 = null;
        str = null;
        str = null;
        String str = null;
        try {
            jarFile = new JarFile(n3.a().a.getPackageCodePath(), true);
        } catch (Exception e2) {
            jarFile = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            JarEntry jarEntry = jarFile.getJarEntry("META-INF/MANIFEST.MF");
            if (jarEntry == null) {
                try {
                    jarFile.close();
                    return i2.b;
                } catch (IOException e3) {
                    return i2.b;
                }
            }
            String str2 = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss", Locale.CHINA).format(new Date(jarEntry.getTime()));
            try {
                jarFile.close();
            } catch (IOException e4) {
            }
            str = str2;
        } catch (Exception e5) {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e6) {
                }
            }
        } catch (Throwable th2) {
            th = th2;
            jarFile2 = jarFile;
            if (jarFile2 != null) {
                try {
                    jarFile2.close();
                } catch (IOException e7) {
                }
            }
            throw th;
        }
        return str == null ? i2.b : str;
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00ab A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String i(java.lang.String r9) throws java.lang.Throwable {
        /*
            java.util.HashMap<java.lang.String, java.lang.String> r0 = com.coralline.sea.ja.i
            boolean r1 = r0.containsKey(r9)
            if (r1 == 0) goto Lf
            java.lang.Object r9 = r0.get(r9)
            java.lang.String r9 = (java.lang.String) r9
            return r9
        Lf:
            r0 = 0
            java.lang.String r1 = " "
            java.lang.String[] r1 = r9.split(r1)     // Catch: java.lang.Throwable -> L8f java.lang.Exception -> La1
            java.lang.ProcessBuilder r2 = new java.lang.ProcessBuilder     // Catch: java.lang.Throwable -> L8f java.lang.Exception -> La1
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L8f java.lang.Exception -> La1
            r1 = 0
            r2.redirectErrorStream(r1)     // Catch: java.lang.Throwable -> L8f java.lang.Exception -> La1
            java.lang.Process r2 = r2.start()     // Catch: java.io.IOException -> L86 java.lang.Throwable -> L8f java.lang.Exception -> La1
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L84
            java.io.InputStream r4 = r2.getInputStream()     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L84
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L84
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L80
            r4.<init>()     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L80
            r5 = 1024(0x400, float:1.435E-42)
            char[] r6 = new char[r5]     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L80
        L35:
            int r7 = r3.read(r6, r1, r5)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L80
            r8 = -1
            if (r7 == r8) goto L40
            r4.append(r6, r1, r7)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L80
            goto L35
        L40:
            r2.waitFor()     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L80
            int r1 = r2.exitValue()     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L80
            if (r1 == 0) goto L5a
            java.lang.String r1 = "exitValue is not equals 0"
            c(r9, r1)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L80
            r2.destroy()
            r3.close()     // Catch: java.io.IOException -> L55
            return r0
        L55:
            r9 = move-exception
            r9.printStackTrace()
            return r0
        L5a:
            java.lang.String r1 = r4.toString()     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L80
            if (r1 == 0) goto L6a
            int r5 = r1.length()     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L80
            if (r5 <= 0) goto L6a
        L66:
            c(r9, r1)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L80
            goto L6d
        L6a:
            java.lang.String r1 = ""
            goto L66
        L6d:
            java.lang.String r9 = r4.toString()     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L80
            r2.destroy()
            r3.close()     // Catch: java.io.IOException -> L78
            return r9
        L78:
            r0 = move-exception
            r0.printStackTrace()
            return r9
        L7d:
            r9 = move-exception
            r0 = r3
            goto L91
        L80:
            r9 = move-exception
            goto La4
        L82:
            r9 = move-exception
            goto L91
        L84:
            r9 = move-exception
            goto La3
        L86:
            r1 = move-exception
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L8f java.lang.Exception -> La1
            c(r9, r1)     // Catch: java.lang.Throwable -> L8f java.lang.Exception -> La1
            return r0
        L8f:
            r9 = move-exception
            r2 = r0
        L91:
            if (r2 == 0) goto L96
            r2.destroy()
        L96:
            if (r0 == 0) goto La0
            r0.close()     // Catch: java.io.IOException -> L9c
            goto La0
        L9c:
            r0 = move-exception
            r0.printStackTrace()
        La0:
            throw r9
        La1:
            r9 = move-exception
            r2 = r0
        La3:
            r3 = r0
        La4:
            if (r2 == 0) goto La9
            r2.destroy()
        La9:
            if (r3 == 0) goto Lb3
            r3.close()     // Catch: java.io.IOException -> Laf
            return r0
        Laf:
            r9 = move-exception
            r9.printStackTrace()
        Lb3:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.ja.i(java.lang.String):java.lang.String");
    }

    public static String j() {
        try {
            String property = System.getProperty("java.vm.version");
            return property == null ? i2.b : Integer.valueOf(property.substring(0, property.indexOf(".")).replace("v", c7.c)).intValue() >= 2 ? "ART" : "Dalvik";
        } catch (Exception e2) {
            return i2.b;
        }
    }

    public static String j(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            Process processExec = Runtime.getRuntime().exec(str);
            processExec.waitFor();
            InputStreamReader inputStreamReader = new InputStreamReader(processExec.getInputStream());
            char[] cArr = new char[1024];
            while (true) {
                int i2 = inputStreamReader.read(cArr, 0, 1024);
                if (i2 == -1) {
                    break;
                }
                sb.append(cArr, 0, i2);
            }
        } catch (Exception e2) {
        }
        return sb.toString();
    }

    public static String k() {
        return a(true, false, false, TimeZone.getDefault().getRawOffset());
    }

    public static String k(String str) {
        boolean z;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            z = true;
            if (i3 >= str.length()) {
                z = false;
                break;
            }
            if (i3 < str.length() - 3 && str.substring(i3, i3 + 4).equalsIgnoreCase(x9.b)) {
                i2 = i3 + 7;
                break;
            }
            if (i3 < str.length() - 4 && str.substring(i3, i3 + 5).equalsIgnoreCase(k4.c)) {
                i2 = i3 + 8;
                break;
            }
            i3++;
        }
        if (!z) {
            return c7.c;
        }
        int i4 = i2;
        while (i4 < str.length() && str.charAt(i4) != ':' && str.charAt(i4) != '/' && str.charAt(i4) != ' ') {
            i4++;
        }
        return str.substring(i2, i4);
    }

    public static String l() {
        return t() ? "random" : "normal";
    }

    public static JSONObject l(String str) {
        try {
            if (z1.c != null && z1.c.has(str)) {
                z1.c.toString();
                return z1.c.getJSONObject(str);
            }
        } catch (Exception e2) {
        }
        return new JSONObject();
    }

    public static List<String> m(String str) {
        File[] fileArrListFiles;
        ArrayList arrayList = new ArrayList();
        try {
            File file = new File(str);
            if (file.isDirectory() && (fileArrListFiles = file.listFiles()) != null && fileArrListFiles.length > 0) {
                for (File file2 : fileArrListFiles) {
                    if (file2.isDirectory()) {
                        arrayList.addAll(m(file2.getAbsolutePath()));
                    } else {
                        arrayList.add(file2.getName());
                    }
                }
            }
        } catch (Exception e2) {
        }
        return arrayList;
    }

    public static JSONArray m() {
        JSONObject jSONObjectA = a();
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
                        } catch (Exception e2) {
                        }
                    }
                    jSONArray.toString(4);
                    return jSONArray;
                }
            } catch (Exception e3) {
            }
        }
        return jSONArray;
    }

    public static int n() {
        if (Build.VERSION.SDK_INT < 23) {
            return 0;
        }
        try {
            return Build.VERSION.PREVIEW_SDK_INT;
        } catch (Throwable th) {
            return 0;
        }
    }

    public static String n(String str) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance("MD5").digest(str.getBytes());
            StringBuilder sb = new StringBuilder(40);
            for (byte b2 : bArrDigest) {
                int i2 = b2 & 255;
                if ((i2 >> 4) == 0) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i2));
            }
            return sb.toString();
        } catch (Exception e2) {
            return c7.c;
        }
    }

    public static synchronized long o() {
        long j2 = c;
        if (j2 != 0) {
            return j2;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        c = jCurrentTimeMillis;
        return jCurrentTimeMillis;
    }

    public static String o(String str) {
        return a(1, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004c A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String p(java.lang.String r3) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "cat /proc/"
            java.lang.String r1 = "N/A"
            if (r3 == 0) goto L4f
            java.lang.String r2 = ""
            boolean r2 = r2.equals(r3)     // Catch: java.lang.NumberFormatException -> L43
            if (r2 == 0) goto Lf
            goto L4f
        Lf:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.NumberFormatException -> L43
            r2.<init>(r0)     // Catch: java.lang.NumberFormatException -> L43
            r2.append(r3)     // Catch: java.lang.NumberFormatException -> L43
            java.lang.String r0 = "/cmdline"
            r2.append(r0)     // Catch: java.lang.NumberFormatException -> L43
            java.lang.String r0 = r2.toString()     // Catch: java.lang.NumberFormatException -> L43
            java.lang.String r0 = i(r0)     // Catch: java.lang.NumberFormatException -> L43
            if (r0 == 0) goto L2f
            int r1 = r0.length()     // Catch: java.lang.NumberFormatException -> L2d
            if (r1 != 0) goto L45
            goto L2f
        L2d:
            r3 = move-exception
            goto L45
        L2f:
            com.coralline.sea.n3 r1 = com.coralline.sea.n3.a()     // Catch: java.lang.NumberFormatException -> L2d
            android.content.Context r1 = r1.a     // Catch: java.lang.NumberFormatException -> L2d
            java.lang.Integer.parseInt(r3)     // Catch: java.lang.NumberFormatException -> L2d
            java.lang.Class<com.coralline.sea.ja> r3 = com.coralline.sea.ja.class
            monitor-enter(r3)     // Catch: java.lang.NumberFormatException -> L2d
            java.lang.String r1 = ""
            monitor-exit(r3)     // Catch: java.lang.NumberFormatException -> L2d
        L3e:
            r0 = r1
            goto L45
        L40:
            r1 = move-exception
            monitor-exit(r3)     // Catch: java.lang.NumberFormatException -> L2d
            throw r1     // Catch: java.lang.NumberFormatException -> L2d
        L43:
            r3 = move-exception
            goto L3e
        L45:
            if (r0 == 0) goto L4c
            java.lang.String r3 = r0.trim()
            goto L4e
        L4c:
            java.lang.String r3 = "N/A"
        L4e:
            return r3
        L4f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.ja.p(java.lang.String):java.lang.String");
    }

    public static synchronized Integer q() {
        if (f == null) {
            f = -1;
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/self/cgroup"));
                char[] cArr = new char[4096];
                StringBuffer stringBuffer = new StringBuffer();
                while (true) {
                    int i2 = bufferedReader.read(cArr);
                    if (i2 <= 0) {
                        break;
                    }
                    stringBuffer.append(cArr, 0, i2);
                }
                bufferedReader.close();
                String string = stringBuffer.toString();
                int iLastIndexOf = string.lastIndexOf("uid");
                int iLastIndexOf2 = string.lastIndexOf("/pid");
                if (iLastIndexOf < 0) {
                    return -1;
                }
                if (iLastIndexOf2 <= 0) {
                    iLastIndexOf2 = string.length();
                }
                f = Integer.valueOf(string.substring(iLastIndexOf + 4, iLastIndexOf2).replaceAll("\n", c7.c));
            } catch (Exception e2) {
            }
        }
        return f;
    }

    public static String q(String str) {
        return a(0, str);
    }

    @Deprecated
    public static int r(String str) {
        Context context = n3.a().a;
        int iCheckPermission = -1;
        if (context == null || TextUtils.isEmpty(str)) {
            return -1;
        }
        try {
            iCheckPermission = Build.VERSION.SDK_INT >= 23 ? context.checkPermission(str, Process.myPid(), Process.myUid()) : context.getPackageManager().checkPermission(str, context.getPackageName());
            return iCheckPermission;
        } catch (Exception e2) {
            e2.toString();
            return iCheckPermission;
        }
    }

    public static boolean r() {
        try {
            String strA = a9.a(a9.g, (String) null);
            String strE = e();
            if (strA != null) {
                return !strA.equals(strE);
            }
            return true;
        } catch (Exception e2) {
            return false;
        }
    }

    public static String s(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes(s0.f));
            byte[] bArrDigest = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i2 = 0; i2 < bArrDigest.length; i2++) {
                int i3 = bArrDigest[i2];
                if (i3 < 0) {
                    i3 += 256;
                }
                if (i3 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i3));
            }
            return stringBuffer.toString();
        } catch (Exception e2) {
            e2.printStackTrace();
            return c7.c;
        }
    }

    public static boolean s() {
        try {
            HashMap<String, Boolean> map = j;
            Boolean bool = map.get("isHarmonyOs");
            if (bool != null) {
                return bool.booleanValue();
            }
            Class<?> cls = Class.forName("com.huawei.system.BuildEx");
            Boolean boolValueOf = Boolean.valueOf("Harmony".equalsIgnoreCase(cls.getMethod("getOsBrand", new Class[0]).invoke(cls, new Object[0]).toString()));
            map.put("isHarmonyOs", boolValueOf);
            return boolValueOf.booleanValue();
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean t() {
        int i2 = Build.VERSION.SDK_INT;
        return (i2 == 28 && n() > 0) || i2 >= 29;
    }

    public static byte[] t(String str) {
        if (str == null) {
            return null;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length / 2;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            int iDigit = Character.digit(charArray[i3 + 1], 16) | (Character.digit(charArray[i3], 16) << 4);
            if (iDigit > 127) {
                iDigit -= 256;
            }
            bArr[i2] = (byte) iDigit;
        }
        return bArr;
    }

    public static boolean u(String str) throws Throwable {
        String[] strArrSplit;
        String strC = c();
        if (strC == null || strC.length() <= 0 || (strArrSplit = strC.split("\n")) == null || strArrSplit.length == 0) {
            return false;
        }
        for (String str2 : strArrSplit) {
            if (str2.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean v(String str) {
        PackageInfo packageInfoA;
        try {
            packageInfoA = v6.a(n3.a().a, str, 0);
        } catch (Exception e2) {
            packageInfoA = null;
        }
        return packageInfoA != null;
    }

    public static boolean w(String str) {
        return (TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str) || "unknow".equalsIgnoreCase(str) || aa.m.equalsIgnoreCase(str)) ? false : true;
    }

    public static String x(String str) {
        JSONObject jSONObjectA;
        String strB = l6.b(n3.a().a);
        return (strB == null || strB.equals(f2.e) || (jSONObjectA = a9.a(a9.c, (JSONObject) null)) == null || !jSONObjectA.has("mac")) ? str : jSONObjectA.optString("mac");
    }

    public static boolean y(String str) {
        try {
            return CustomNamedCurves.getByName("sm2p256v1").getCurve().decodePoint(Hex.decode(str)).isValid();
        } catch (Exception e2) {
            return false;
        }
    }

    public static String p() {
        String str = d;
        if (str != null) {
            return str;
        }
        try {
            String lowerCase = c4.a((o() + n3.a().f() + n3.T.k).toLowerCase()).toLowerCase();
            d = lowerCase + c4.a(lowerCase.substring(8, 24)).toLowerCase().substring(0, 4);
        } catch (NoSuchAlgorithmException e2) {
            d = i2.b;
        }
        return d;
    }
}
