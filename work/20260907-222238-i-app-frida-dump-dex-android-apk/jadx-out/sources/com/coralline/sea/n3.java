package com.coralline.sea;

import android.annotation.SuppressLint;
import android.content.Context;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
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
        To view partially-correct add '--show-bad-code' argument
    */
    public n3(java.util.Map<java.lang.Integer, java.lang.Object> r20, boolean r21) {
        /*
            Method dump skipped, instruction units count: 912
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.n3.<init>(java.util.Map, boolean):void");
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
