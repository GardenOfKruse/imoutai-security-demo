package com.coralline.sea;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class n3 {
    public static final int R = 1703;
    public static final String S = "593D/3eNwSkkjBUEi6TlUliSq8FdLOwZWrCnWfqE4SlQqLE8YnQP4WEo33Q0lbp10aZPxF0Awqrpz6N1OwbuTLthLwrJv2OkQgXnzRiZ7cw=";

    @SuppressLint({"StaticFieldLeak"})
    public static volatile n3 T = null;
    public static Context U = null;
    public static String V = "userdata_now";
    public final boolean A;
    public final boolean B;
    public final boolean C;
    public final boolean D;
    public final boolean E;
    public final List F;
    public String G;
    public String H;
    public String I;
    public String J;
    public String K;
    public String L;
    public boolean M;
    public JSONObject N;
    public JSONObject O;
    public int P;
    public final String Q;
    public final Context a;
    public final String b;
    public final boolean c;
    public final boolean d;
    public final boolean e;
    public final String f;
    public final boolean g;
    public final int h;
    public final int i;
    public final String j;
    public final String k;
    public final List<String> l;
    public final boolean m;
    public final String n;
    public final String o;
    public final int p;
    public final boolean q;
    public final String r;
    public final boolean s;
    public final String t;
    public final String u;
    public final int v;
    public final String w;
    public final String x;
    public final HashMap<String, Double> y;
    public String z;

    /* JADX WARN: Removed duplicated region for block: B:55:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x02f2  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0305 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x032f  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0335  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0347  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0363  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public n3(Map<Integer, Object> map, boolean z) {
        String str;
        boolean z2;
        boolean z3;
        String strReplace;
        this.G = c7.c;
        this.H = c7.c;
        this.I = c7.c;
        this.J = c7.c;
        this.K = c7.c;
        this.L = c7.c;
        this.N = null;
        this.O = null;
        String str2 = (String) a(map, 2, c7.c);
        String str3 = (String) a(map, 3, c7.c);
        x9.w = ((Boolean) a(map, 18, Boolean.valueOf(x9.w))).booleanValue();
        String str4 = (String) a(map, 20, c7.c);
        this.Q = str4;
        Context context = (Context) a(map, 1, (Object) null);
        this.a = context;
        m5.a(context);
        i8.a().a(context);
        if (!str2.startsWith("/")) {
            str2 = context.getFilesDir().getParent() + "/" + str2;
        }
        this.r = str2;
        String str5 = (String) a(map, 6, (Object) null);
        this.w = str5;
        String str6 = (String) a(map, 7, (Object) null);
        this.x = str6;
        String str7 = (String) a(map, 19, c7.c);
        this.f = str7;
        this.e = !TextUtils.isEmpty(str7);
        Boolean bool = Boolean.FALSE;
        boolean zBooleanValue = ((Boolean) a(map, 10, bool)).booleanValue();
        this.g = zBooleanValue;
        int iIntValue = ((Integer) a(map, 11, 0)).intValue();
        this.h = iIntValue;
        int iIntValue2 = ((Integer) a(map, 13, 0)).intValue();
        this.i = iIntValue2;
        this.v = ((Integer) a(map, 12, 0)).intValue();
        this.k = (String) a(map, 14, c7.c);
        this.l = (List) a(map, 15, new ArrayList());
        this.n = (String) a(map, 16, (Object) null);
        this.p = a(a(map, 21, (Object) null));
        this.o = (String) a(map, 17, c7.c);
        this.q = z;
        this.y = (HashMap) a(map, 22, new HashMap());
        this.z = (String) a(map, 23, c7.c);
        if (map.get(31) != null) {
            this.N = (JSONObject) map.get(31);
        }
        if (map.get(35) != null) {
            this.O = (JSONObject) map.get(35);
        }
        String str8 = (String) map.get(26);
        String str9 = (String) map.get(27);
        String str10 = (String) map.get(28);
        String str11 = (String) map.get(29);
        String str12 = (String) map.get(30);
        this.G = aa.m.equals(str8) ? i2.b : str8;
        this.H = aa.m.equals(str9) ? i2.b : str9;
        this.I = aa.m.equals(str10) ? i2.b : str10;
        this.J = aa.m.equals(str11) ? i2.b : str11;
        this.K = aa.m.equals(str12) ? i2.b : str12;
        this.L = (String) map.get(33);
        this.j = S;
        boolean z4 = iIntValue != 1;
        this.s = z4;
        if (z4) {
            str = "defaultv0" + str7;
        } else {
            str = "default.json";
        }
        this.t = str;
        this.u = "everisk_cache" + str7;
        Map map2 = (Map) a(map, 21, (Object) null);
        Boolean bool2 = Boolean.TRUE;
        this.M = ((Boolean) a((Map<Boolean, Boolean>) map2, "INITAPPLIST", bool2)).booleanValue();
        this.P = u9.a(context, "ro.build.version.sdk", Build.VERSION.SDK_INT).intValue();
        if (!TextUtils.isEmpty(str4)) {
            if ("ccb".equals(str4)) {
                z2 = true;
                this.c = true;
                z3 = false;
                this.d = false;
            } else {
                z2 = true;
                z3 = false;
                if (!"hxbank".equals(str4)) {
                    if (!"abc".equals(str4)) {
                        if ("poc".equals(str4)) {
                            this.c = false;
                            this.d = false;
                            this.B = false;
                            this.D = true;
                        }
                        this.A = ((Boolean) a(map, 24, bool)).booleanValue();
                        this.F = (List) a(map, 25, new ArrayList());
                        this.C = ((Boolean) a(map, 32, bool)).booleanValue();
                        if (this.B) {
                        }
                        this.m = (!this.c || zBooleanValue || this.d) ? false : z2;
                        j6.a(context, str2 + "/everisk_ccrash.dmp", Integer.toString(iIntValue2));
                        if (TextUtils.isEmpty(str3)) {
                        }
                        strReplace = this.z;
                        if (strReplace.startsWith("4.")) {
                            strReplace = this.z.replace(".0.", ".");
                        }
                        this.z = strReplace.contains("-") ? strReplace.replace("-", ".") : strReplace;
                        aa.f().a(str5, str6, zBooleanValue);
                        q3.b().c((JSONObject) a(map, 38, new JSONObject()));
                        e();
                    }
                    this.B = true;
                    this.d = false;
                    this.c = false;
                    this.D = z3;
                    this.A = ((Boolean) a(map, 24, bool)).booleanValue();
                    this.F = (List) a(map, 25, new ArrayList());
                    this.C = ((Boolean) a(map, 32, bool)).booleanValue();
                    if (this.B) {
                        this.E = ((Boolean) a(map, 34, bool2)).booleanValue();
                    } else {
                        this.E = false;
                    }
                    this.m = (!this.c || zBooleanValue || this.d) ? false : z2;
                    j6.a(context, str2 + "/everisk_ccrash.dmp", Integer.toString(iIntValue2));
                    if (TextUtils.isEmpty(str3)) {
                        System.load(str3);
                        this.b = str3;
                    } else {
                        System.loadLibrary("RiskStub");
                        this.b = null;
                    }
                    strReplace = this.z;
                    if (strReplace.startsWith("4.") && this.z.contains(".0.")) {
                        strReplace = this.z.replace(".0.", ".");
                    }
                    this.z = strReplace.contains("-") ? strReplace.replace("-", ".") : strReplace;
                    aa.f().a(str5, str6, zBooleanValue);
                    q3.b().c((JSONObject) a(map, 38, new JSONObject()));
                    e();
                }
                this.d = true;
                this.c = false;
            }
            this.B = z3;
            this.D = z3;
            this.A = ((Boolean) a(map, 24, bool)).booleanValue();
            this.F = (List) a(map, 25, new ArrayList());
            this.C = ((Boolean) a(map, 32, bool)).booleanValue();
            if (this.B) {
            }
            this.m = (!this.c || zBooleanValue || this.d) ? false : z2;
            j6.a(context, str2 + "/everisk_ccrash.dmp", Integer.toString(iIntValue2));
            if (TextUtils.isEmpty(str3)) {
            }
            strReplace = this.z;
            if (strReplace.startsWith("4.")) {
            }
            this.z = strReplace.contains("-") ? strReplace.replace("-", ".") : strReplace;
            aa.f().a(str5, str6, zBooleanValue);
            q3.b().c((JSONObject) a(map, 38, new JSONObject()));
            e();
        }
        z2 = true;
        z3 = false;
        this.D = z3;
        this.B = z3;
        this.d = z3;
        this.c = z3;
        this.A = ((Boolean) a(map, 24, bool)).booleanValue();
        this.F = (List) a(map, 25, new ArrayList());
        this.C = ((Boolean) a(map, 32, bool)).booleanValue();
        if (this.B) {
        }
        this.m = (!this.c || zBooleanValue || this.d) ? false : z2;
        j6.a(context, str2 + "/everisk_ccrash.dmp", Integer.toString(iIntValue2));
        if (TextUtils.isEmpty(str3)) {
        }
        strReplace = this.z;
        if (strReplace.startsWith("4.")) {
        }
        this.z = strReplace.contains("-") ? strReplace.replace("-", ".") : strReplace;
        aa.f().a(str5, str6, zBooleanValue);
        q3.b().c((JSONObject) a(map, 38, new JSONObject()));
        e();
    }

    public static int a(Object obj) {
        if (obj == null) {
            return -1;
        }
        HashMap map = (HashMap) obj;
        for (Object obj2 : map.keySet()) {
            if ("START_DELAY".equalsIgnoreCase(obj2.toString())) {
                Object obj3 = map.get(obj2);
                if (obj3 instanceof Integer) {
                    return ((Integer) obj3).intValue();
                }
            }
        }
        return -1;
    }

    public static n3 a() {
        return T;
    }

    public static <T> T a(Map<Integer, Object> map, int i, T t) {
        return (map == null || map.get(Integer.valueOf(i)) == null) ? t : (T) map.get(Integer.valueOf(i));
    }

    public static <T> T a(Map<T, T> map, String str, T t) {
        if (map != null) {
            for (T t2 : map.keySet()) {
                if (t2.toString().equalsIgnoreCase(str)) {
                    t = map.get(t2);
                }
            }
        }
        return t;
    }

    public static synchronized void a(Context context, String str, String str2, boolean z, boolean z2, boolean z3, String str3, String str4, String str5, String str6, String str7, String str8, String str9, List<String> list) throws Exception {
        Throwable th;
        InputStream inputStreamOpen;
        if (T != null) {
            throw new RuntimeException("error");
        }
        try {
            inputStreamOpen = context.getAssets().open("configure.json");
            try {
                int iAvailable = inputStreamOpen.available();
                byte[] bArr = new byte[iAvailable];
                if (iAvailable != inputStreamOpen.read(bArr)) {
                    inputStreamOpen.close();
                    return;
                }
                JSONObject jSONObject = new JSONObject(new String(bArr));
                int iOptInt = jSONObject.optInt("c_ver", 1);
                int iOptInt2 = jSONObject.optInt("loader_ver", 1);
                int iOptInt3 = jSONObject.optInt("java_ver", 1);
                File file = new File(context.getFilesDir().getParent() + "/.RiskStub");
                if (!file.exists()) {
                    file.mkdir();
                }
                HashMap map = new HashMap();
                map.put(1, context);
                map.put(2, ".RiskStub");
                map.put(3, null);
                map.put(6, str);
                map.put(7, str2);
                map.put(20, null);
                map.put(19, c7.c);
                map.put(10, Boolean.valueOf(z3));
                map.put(11, Integer.valueOf(iOptInt2));
                map.put(12, Integer.valueOf(iOptInt));
                map.put(13, Integer.valueOf(iOptInt3));
                map.put(14, str8);
                map.put(15, list);
                map.put(16, null);
                map.put(17, null);
                map.put(23, str9);
                map.put(26, str3);
                map.put(27, str6);
                map.put(28, str4);
                map.put(29, str7);
                map.put(30, str5);
                a(map, false);
                inputStreamOpen.close();
            } catch (Throwable th2) {
                th = th2;
                if (inputStreamOpen == null) {
                    throw th;
                }
                inputStreamOpen.close();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            inputStreamOpen = null;
        }
    }

    public static synchronized void a(Map<Integer, Object> map, boolean z) {
        if (T != null) {
            throw new RuntimeException("error");
        }
        T = new n3(map, z);
        b7.e();
    }

    public void a(String str) {
        this.L = str;
    }

    public String b() {
        return this.L;
    }

    public JSONObject c() {
        return this.N;
    }

    public boolean d() {
        return "cscb".equals(this.Q);
    }

    public void e() {
        Objects.toString(this.a);
        Objects.toString(this.l);
        Objects.toString(this.y);
        Objects.toString(this.N);
        Objects.toString(this.O);
    }

    public String f() {
        return aa.f().d();
    }

    public JSONObject g() {
        return z9.b();
    }

    public String h() {
        return aa.f().e();
    }

    public boolean i() {
        return this.w != null;
    }

    public void j() {
        JSONObject jSONObject = this.O;
        if (jSONObject != null) {
            a9.b(ga.d, jSONObject);
        }
    }

    public void k() {
        JSONObject jSONObject = this.N;
        if (jSONObject != null) {
            a9.b("userdata_pre", jSONObject);
        }
    }
}
