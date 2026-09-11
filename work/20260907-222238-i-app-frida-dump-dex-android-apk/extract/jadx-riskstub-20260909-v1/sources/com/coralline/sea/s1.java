package com.coralline.sea;

import android.text.TextUtils;
import com.coralline.sea.checkers.RiskRealTimeAPI;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class s1 {
    public static int o;
    public final String a;
    public final String b;
    public final String c;
    public final String d;
    public final boolean e;
    public final boolean f;
    public final long g;
    public byte[] h;
    public String i;
    public boolean j;
    public String k;
    public JSONObject l;
    public String m;
    public boolean n;

    public static final class a {
        public static final String a = "message";
        public static final String b = "header";
        public static final String c = "controllerName";
        public static final String d = "checkerName";
        public static final String e = "needPersist";
        public static final String f = "id";
    }

    public s1(String str, String str2, String str3, String str4, boolean z) {
        this(str, str2, str3, str4, z, h(), false);
    }

    public s1(String str, String str2, String str3, String str4, boolean z, long j, boolean z2) {
        this.n = false;
        this.a = str;
        this.b = str2;
        this.d = str3;
        this.c = str4;
        this.e = z;
        this.g = j;
        this.f = z2;
        this.i = null;
        this.h = null;
        this.j = false;
        this.k = null;
        this.l = null;
    }

    public static s1 a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(n3.a().B ? v1.a(str, i6.r(), i6.q()) : v1.a(str));
            String string = jSONObject.getString(a.d);
            String string2 = jSONObject.getString(a.c);
            String string3 = jSONObject.getString(a.b);
            String string4 = jSONObject.getString(a.a);
            s1 s1Var = new s1(string4, string3, string, string2, jSONObject.getBoolean(a.e), jSONObject.getLong("id"), true);
            s1Var.n = b(string4);
            return s1Var;
        } catch (Exception e) {
            return null;
        }
    }

    public static String a(s1 s1Var) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(a.d, s1Var.d);
            jSONObject.put(a.c, s1Var.c);
            jSONObject.put(a.b, s1Var.b);
            jSONObject.put(a.a, s1Var.a);
            jSONObject.put("id", s1Var.g);
            jSONObject.put(a.e, s1Var.e);
            return n3.a().B ? v1.b(jSONObject.toString(), i6.r(), i6.q()) : v1.c(jSONObject.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean b(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                return new JSONObject(str).optJSONObject(RiskRealTimeAPI.KEY_USERSENC) != null;
            }
        } catch (JSONException e) {
        }
        return false;
    }

    public static long h() {
        long j;
        long jCurrentTimeMillis = System.currentTimeMillis();
        synchronized (s1.class) {
            int i = o + 1;
            o = i;
            j = jCurrentTimeMillis + ((long) i);
        }
        return j;
    }

    public synchronized JSONObject a() {
        return a(false);
    }

    public synchronized JSONObject a(boolean z) {
        try {
            if (this.l == null) {
                this.l = y1.a(this.a, this.b);
            }
            if (z) {
                return new JSONObject(this.l.toString());
            }
            return this.l;
        } catch (Exception e) {
            return null;
        }
    }

    public void a(byte[] bArr) {
        this.h = bArr;
    }

    public synchronized String b() {
        String str;
        JSONObject jSONObjectA;
        String str2 = this.k;
        if (str2 != null) {
            return str2;
        }
        synchronized (this) {
            if (this.k == null && (jSONObjectA = a()) != null) {
                this.k = jSONObjectA.toString();
            }
            str = this.k;
        }
        return str;
    }

    public void b(boolean z) {
        this.n = z;
    }

    public String c() {
        return this.m;
    }

    public void c(String str) {
        this.m = str;
    }

    public void c(boolean z) {
        this.j = z;
    }

    public String d() {
        return this.i;
    }

    public void d(String str) {
        this.i = str;
    }

    public byte[] e() {
        return this.h;
    }

    public boolean f() {
        return this.n;
    }

    public boolean g() {
        return this.j;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String i() {
        byte b;
        String str = c7.c;
        String str2 = this.c;
        str2.getClass();
        int iHashCode = str2.hashCode();
        if (iHashCode != -1745954712) {
            if (iHashCode != -838595071) {
                b = (iHashCode == 1427818632 && str2.equals(e2.c)) ? (byte) 2 : (byte) -1;
            } else if (str2.equals(e2.b)) {
                b = 1;
            }
        } else if (str2.equals(e2.d)) {
            b = 0;
        }
        switch (b) {
            case 0:
                str = "K";
                break;
            case 1:
                str = "U";
                break;
            case 2:
                str = "D";
                break;
        }
        return str + " " + this.d;
    }

    public int j() {
        return this.b.length() + this.a.length();
    }

    public String toString() {
        return "id : " + this.g + ", checkerName : " + this.d + ", controllerName : " + this.c + ", needToPersist : " + this.e + ", message : " + this.a + ", header : " + this.b;
    }
}
