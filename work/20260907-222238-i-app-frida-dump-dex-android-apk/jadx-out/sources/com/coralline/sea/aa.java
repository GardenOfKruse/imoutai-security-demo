package com.coralline.sea;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Pair;
import com.coralline.sea.m5;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class aa {
    public static final String m = "unknown";
    public static final String n = "00000000-0000-0000-0000-000000000000";
    public static final String o = "UDID";
    public static final String p = "server";
    public static final String r = "tmp_d2";
    public static final String s = "null_h";
    public String a = n;
    public String b = n;
    public String c = m;
    public String d = m;
    public boolean e = false;
    public boolean f = true;
    public boolean g = false;
    public final String h = "unique_equipment";
    public final String i = "invalid_udid";
    public final String j = "invalid_factor";
    public final String k = "null";
    public String l = c7.c;
    public static aa q = new aa();
    public static final String[] t = {j7.b, "00000000", "000000000000000000", "111111", "111111111111111111", "123456", "12345678", "0123456789ABCDEF", "NA", "NP"};
    public static final String[] u = {"00:11:22:33:", "11:22:33:44", "aa:bb:cc:dd", "00:00:00:00:00", "02:00:00:00:00:00", "6a:aa:6a:aa:6a:6a", "00:02:00:00:00:00", "00:00:00:80:00:00", "10:00:00:00:00:12", "f2:0f:f0:02:f0:22", "32:12:31:23:32:32", "66:00:44:40:06:66", "c0:00:00:00:00:d0", "04:00:00:50:54:04", "NA", "NP"};

    public class a implements Runnable {
        public final /* synthetic */ String a;

        public a(String str) {
            this.a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                JSONObject jSONObjectB = z9.b();
                jSONObjectB.remove("fg_factor");
                jSONObjectB.put("udid", n3.a().f());
                jSONObjectB.put("uaid", z9.c());
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("field_value", jSONObjectB);
                jSONObject.put("platform", a0.b);
                jSONObject.put("agent_id", n3.T.k);
                jSONObject.put("protol_type", "unique_equipment");
                n4 n4VarA = e2.a().a(e2.b);
                String strA = n4VarA.a(i4.a().a(this.a + "/3/3", n4VarA.a(jSONObject.toString()).getBytes(), com.coralline.sea.a.m));
                if (strA == null || !strA.contains("\"status\":0")) {
                    return;
                }
                z9.a(Long.toString(System.currentTimeMillis()));
            } catch (Exception e) {
            }
        }
    }

    public static void a(JSONObject jSONObject) throws JSONException {
        Iterator<String> itKeys = jSONObject.keys();
        ArrayList arrayList = new ArrayList();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            if (jSONObject.getString(next).length() <= 6) {
                arrayList.add(next);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            jSONObject.remove((String) it.next());
        }
    }

    public static aa f() {
        return q;
    }

    public final String a(Context context) {
        try {
            String string = context.getSharedPreferences("tmp_d2", 0).getString("null_h", c7.c);
            return !TextUtils.isEmpty(string) ? v1.a(string) : c7.c;
        } catch (Exception e) {
            return c7.c;
        }
    }

    public final void a() {
        String strOptString = z1.c("token").optString("dev_mark_url", c7.c);
        if (strOptString.endsWith("/")) {
            strOptString = strOptString.substring(0, strOptString.length() - 1);
        }
        String strSubstring = n3.a().l.get(0);
        if (strSubstring.endsWith("/")) {
            strSubstring = strSubstring.substring(0, strSubstring.length() - 1);
        }
        if (TextUtils.isEmpty(strOptString) || !this.f || !TextUtils.isEmpty(z9.a()) || strOptString.equals(strSubstring)) {
            z9.a();
        } else {
            this.f = false;
            new Thread(new a(strSubstring)).start();
        }
    }

    public final void a(Context context, String str) {
        try {
            context.getSharedPreferences("tmp_d2", 0).edit().putString("null_h", v1.c(str)).apply();
        } catch (Exception e) {
        }
    }

    public final void a(String str) throws Throwable {
        if (str == null) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            String strOptString = jSONObject.optString("udid", c7.c);
            String strOptString2 = jSONObject.optString("udid_used", c7.c);
            String strOptString3 = jSONObject.optString("uaid", c7.c);
            JSONObject jSONObjectB = z9.b();
            Objects.toString(jSONObjectB);
            Context context = n3.a().a;
            if (!TextUtils.equals(strOptString2, "null")) {
                if (TextUtils.isEmpty(strOptString)) {
                    return;
                }
                this.a = strOptString;
                this.c = p;
                i();
                y1.c(strOptString);
                z9.c(strOptString);
                v9.a(strOptString3);
                a();
                return;
            }
            if (jSONObjectB.has(this.l)) {
                jSONObjectB.remove(this.l);
            }
            if (jSONObject.has("invalid_factor")) {
                Iterator<String> itKeys = jSONObject.optJSONObject("invalid_factor").keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    if (jSONObjectB.has(next)) {
                        jSONObjectB.remove(next);
                    }
                }
                jSONObjectB.toString();
                Iterator<String> itKeys2 = jSONObjectB.keys();
                ArrayList arrayList = new ArrayList();
                while (itKeys2.hasNext()) {
                    String next2 = itKeys2.next();
                    if (!d(jSONObjectB.getString(next2))) {
                        arrayList.add(next2);
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    jSONObjectB.remove((String) it.next());
                }
            }
            String strB = b(jSONObjectB);
            if (d(strB)) {
                String string = UUID.nameUUIDFromBytes(strB.getBytes()).toString();
                this.b = string;
                jSONObjectB.put("udid", string);
                jSONObjectB.put("fg_factor", strB);
                jSONObjectB.put("udid_from", this.l);
            } else {
                String string2 = Double.toString(new SecureRandom().nextDouble());
                String string3 = UUID.nameUUIDFromBytes(string2.getBytes()).toString();
                this.l = "random";
                this.b = string3;
                jSONObjectB.put("udid", string3);
                jSONObjectB.put("fg_factor", string2);
                jSONObjectB.put("udid_from", this.l);
                jSONObjectB.toString();
            }
            try {
                jSONObjectB.toString();
                context.getSharedPreferences("tmp_d2", 0).edit().putString("null_h", v1.c(jSONObjectB.toString())).apply();
                g();
            } catch (Exception e) {
            }
        } catch (Exception e2) {
        }
    }

    public void a(String str, String str2, boolean z) {
        if (!p.equals(str2) && !z) {
            this.b = str;
            this.d = str2;
        } else {
            this.a = str;
            this.c = str2;
            this.g = z;
        }
    }

    public final String b(JSONObject jSONObject) {
        String strOptString;
        String str;
        String strOptString2 = c7.c;
        if (Build.VERSION.SDK_INT > 26) {
            if (jSONObject.has("android_id")) {
                strOptString2 = jSONObject.optString("android_id", m);
                this.l = "android_id";
            }
            if (jSONObject.has("drmid") && !d(strOptString2)) {
                strOptString2 = jSONObject.optString("drmid", m);
                this.l = "drmid";
            }
            if (jSONObject.has("mac") && (!d(strOptString2) || !c(strOptString2))) {
                strOptString2 = jSONObject.optString("mac", m);
                this.l = "mac";
            }
            if (jSONObject.has("imei") && !d(strOptString2)) {
                strOptString2 = jSONObject.optString("imei", m);
                this.l = "imei";
            }
            if (jSONObject.has("serial") && !d(strOptString2)) {
                strOptString = jSONObject.optString("serial", m);
                str = "serial";
                this.l = str;
                return strOptString;
            }
            return strOptString2;
        }
        if (jSONObject.has("mac") && (!d(c7.c) || !c(c7.c))) {
            strOptString2 = jSONObject.optString("mac", m);
            this.l = "mac";
        }
        if (jSONObject.has("imei") && !d(strOptString2)) {
            strOptString2 = jSONObject.optString("imei", m);
            this.l = "imei";
        }
        if (jSONObject.has("serial") && !d(strOptString2)) {
            strOptString2 = jSONObject.optString("serial", m);
            this.l = "serial";
        }
        if (jSONObject.has("android_id") && !d(strOptString2)) {
            strOptString2 = jSONObject.optString("android_id", m);
            this.l = "android_id";
        }
        if (jSONObject.has("drmid") && !d(strOptString2)) {
            strOptString = jSONObject.optString("drmid", m);
            str = "drmid";
            this.l = str;
            return strOptString;
        }
        return strOptString2;
    }

    public final boolean b() throws Throwable {
        if (this.a.equals(n)) {
            this.l = n3.a().b();
            g();
        }
        return !this.a.equals(n);
    }

    public final boolean b(String str) {
        StringBuilder sb = new StringBuilder(str);
        if (sb.length() < 3) {
            return false;
        }
        int iCharAt = sb.charAt(1) - sb.charAt(0);
        for (int i = 2; i < sb.length(); i++) {
            if (iCharAt != sb.charAt(i) - sb.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public void c() {
        if (this.g) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        if (b()) {
            long jCurrentTimeMillis2 = System.currentTimeMillis() / 1000;
            v9.c();
            a();
        } else {
            this.a = this.b;
            this.c = this.d;
            i();
            long jCurrentTimeMillis3 = System.currentTimeMillis() / 1000;
        }
    }

    public final boolean c(String str) {
        for (String str2 : u) {
            if (str.startsWith(str2)) {
                return false;
            }
        }
        return true;
    }

    public String d() {
        return this.a.equals(n) ? this.b : this.a;
    }

    public final boolean d(String str) {
        if (str == null || str.length() <= 0 || str.equals(m)) {
            return false;
        }
        for (String str2 : t) {
            if (str2.equals(str)) {
                return false;
            }
        }
        if (new HashSet(Arrays.asList(str.toLowerCase().split(c7.c))).size() < 3) {
            return false;
        }
        return !b(str);
    }

    public String e() {
        return this.c.equals(m) ? this.d : this.c;
    }

    public final boolean e(String str) {
        return !TextUtils.isEmpty(str) && str.matches("([A-Fa-f0-9]{2}[:]){5}[A-Fa-f0-9]{2}") && new HashSet(Arrays.asList(str.toLowerCase().split(":"))).size() > 2;
    }

    public void g() throws Throwable {
        String strA;
        String strA2;
        try {
            JSONObject jSONObjectB = z9.b();
            Objects.toString(jSONObjectB);
            String strOptString = c7.c;
            if (jSONObjectB.has("udid")) {
                strOptString = jSONObjectB.optString("udid");
                this.b = jSONObjectB.getString("udid");
            }
            if (jSONObjectB.has("udid_from")) {
                this.l = jSONObjectB.optString("udid_from");
            }
            if (Build.VERSION.SDK_INT > 26) {
                jSONObjectB.put("mac", m);
                jSONObjectB.put("imei", m);
                jSONObjectB.put("serial", m);
                n3.a().a.getSharedPreferences("tmp_d2", 0).edit().putString("null_h", v1.c(jSONObjectB.toString())).apply();
            }
            jSONObjectB.remove("fg_factor");
            JSONObject jSONObjectA = v9.a();
            jSONObjectB.put("type", jSONObjectA.opt("type"));
            jSONObjectB.put("token", jSONObjectA.opt("token"));
            jSONObjectB.put(v9.c, jSONObjectA.opt(v9.c));
            jSONObjectB.put("udid", this.b);
            jSONObjectB.put("udid_from", this.l);
            JSONObject jSONObject = new JSONObject();
            StringBuilder sb = new StringBuilder();
            String strI = ja.i("getprop");
            if (strI != null) {
                for (String str : strI.split("\n")) {
                    if (str.startsWith("[system.ro.") || str.startsWith("[ro.")) {
                        sb.append(str);
                    }
                }
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("ro_properties", sb);
            jSONObject2.put("udid", strOptString);
            jSONObject2.put("ext_info", jSONObject);
            jSONObject2.put("field_value", jSONObjectB);
            jSONObject2.put("platform", a0.b);
            jSONObject2.put("agent_id", n3.a().k);
            jSONObject2.put("protol_type", "unique_equipment");
            String[] strArrSplit = null;
            if (n3.T != null && !jSONObject2.has("extra")) {
                JSONObject jSONObjectA2 = q5.a();
                JSONObject jSONObjectA3 = a9.a(ga.d, (JSONObject) null);
                Objects.toString(jSONObjectA3);
                if (jSONObjectA3 != null) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("location", jSONObjectA3);
                    jSONObject2.put("extra", jSONObject3);
                } else if (jSONObjectA2 != null) {
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.put("location", jSONObjectA2);
                    jSONObject2.put("extra", jSONObject4);
                }
            }
            jSONObject2.toString();
            if (n3.T.c) {
                n4 n4VarA = l2.g().a();
                m1.a(jSONObject2);
                jSONObject2.put("version_client", n3.T.z);
                String strA3 = n4VarA.a(jSONObject2.toString());
                jSONObject2.put("protol_type", g9.g);
                new s1(jSONObject2.toString(), y1.b(e2.b), c7.c, e2.b, false);
                Iterator<String> it = n4VarA.a().iterator();
                while (it.hasNext()) {
                    a(n4VarA.a(i4.a().a(it.next(), strA3.getBytes(), 30000)));
                }
                return;
            }
            n4 n4VarA2 = e2.a().a(e2.b);
            String str2 = c7.c;
            String str3 = c7.c;
            if (n3.T.B) {
                String strA4 = l9.a(16);
                String strA5 = l9.a(16);
                strA = n4VarA2.a(jSONObject2.toString(), strA5, strA4);
                str3 = strA5;
                str2 = strA4;
            } else {
                strA = n4VarA2.a(jSONObject2.toString());
            }
            String strOptString2 = z1.c("token").optString("dev_mark_url", c7.c);
            String str4 = n3.T.l.get(0);
            if (TextUtils.isEmpty(strOptString2)) {
                this.f = false;
                strOptString2 = str4;
            }
            if (strOptString2.endsWith("/")) {
                strOptString2 = strOptString2.substring(0, strOptString2.length() - 1);
            }
            byte[] bArrA = i4.a().a(strOptString2 + "/2/4", strA.getBytes(), 30000);
            if (n3.T.B) {
                String str5 = new String(bArrA);
                if (str5.contains("|")) {
                    strArrSplit = str5.split("\\|");
                    String str6 = strArrSplit[0];
                }
                if (strArrSplit == null) {
                    return;
                } else {
                    strA2 = n4VarA2.a(strArrSplit[0].getBytes(), str3.getBytes(), str2.getBytes());
                }
            } else {
                strA2 = n4VarA2.a(bArrA);
            }
            a(strA2);
        } catch (Exception e) {
        }
    }

    public void h() {
        JSONObject jSONObjectB = z9.b();
        if (jSONObjectB.has("udid")) {
            this.a = jSONObjectB.optString("udid");
        }
        if (jSONObjectB.has("udid_from")) {
            this.c = jSONObjectB.optString("udid_from");
        }
        m5.a().a(new Pair(this.a, this.c), m5.b.C0005b.e);
    }

    public final void i() {
        if (this.e) {
            return;
        }
        this.e = true;
        m5.a().a(new Pair(this.a, this.c), m5.b.C0005b.e);
    }
}
