package com.coralline.sea;

import android.content.Context;
import android.text.TextUtils;
import java.util.Iterator;
import java.util.Random;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class b1 extends g0 implements o4 {
    public static final String c = "flow_control";
    public static final String d = "location";
    public static final String e = "range";
    public static final String f = "total";
    public static final String g = "open";
    public static final String h = "msg_frequency";
    public static final String i = "tps";
    public static final String j = "base_number";
    public static final String k = "operand";
    public static final String l = "offset";
    public static final String m = "whole_country";
    public static final String n = "run";
    public static boolean o = false;
    public static Object p = null;
    public static int q = 0;
    public static b1 r = null;
    public static String s = null;
    public static boolean t = false;
    public static final String u = "last_flow_control";

    public static synchronized b1 i() {
        if (r == null) {
            r = new b1();
        }
        return r;
    }

    public v3 a(Context context) {
        if (a(j()) && o) {
            v3 v3Var = v3.FULL_START;
            a(v3Var);
            m1.p();
            return v3Var;
        }
        if (g1.b().a()) {
            v3 v3Var2 = v3.ONLY_KEEPALIVE;
            a(v3Var2);
            return v3Var2;
        }
        v3 v3Var3 = v3.STOP_RUNNING;
        a(v3Var3);
        return v3Var3;
    }

    @Override // com.coralline.sea.o4
    public String a() {
        return null;
    }

    public boolean a(int i2, int i3) {
        return new Random().nextInt(i3) < i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0164 A[Catch: Exception -> 0x0159, TryCatch #1 {Exception -> 0x0159, blocks: (B:69:0x0154, B:79:0x017a, B:74:0x015b, B:78:0x0169, B:77:0x0164), top: B:86:0x0154 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean a(String str) {
        double d2;
        int i2;
        double d3;
        double d4;
        int i3;
        String strA;
        try {
            JSONObject jSONObjectA = a9.a(a9.d, (JSONObject) null);
            if (jSONObjectA == null || ja.r()) {
                String strA2 = z1.a();
                if (strA2 == null) {
                    return false;
                }
                jSONObjectA = new JSONObject(strA2).getJSONObject("flow_control");
                a9.b(a9.g, ja.e());
                a9.b(a9.d, jSONObjectA.toString());
            }
            int i4 = 1;
            if (!jSONObjectA.has("location") || jSONObjectA.getJSONObject("location").length() <= 0) {
                d2 = 70.0d;
                i2 = 1;
                d3 = 0.01d;
                d4 = 0.09d;
                i3 = 0;
            } else {
                JSONObject jSONObject = jSONObjectA.getJSONObject("location");
                jSONObject.toString();
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    if (next.equals(str) || next.indexOf(str) != -1 || str.indexOf(next) != -1) {
                        JSONObject jSONObject2 = jSONObject.has(next) ? jSONObject.getJSONObject(next) : new JSONObject();
                        i2 = jSONObject2.has("range") ? jSONObject2.getInt("range") : 1;
                        i = jSONObject2.has("total") ? jSONObject2.getInt("total") : 10000000;
                        o = jSONObject2.has("open") && jSONObject2.getBoolean("open");
                        p = jSONObject2.has("msg_frequency") ? jSONObject2.get("msg_frequency") : Float.valueOf(1.0f);
                        JSONObject jSONObject3 = jSONObjectA.has(m) ? jSONObjectA.getJSONObject(m) : new JSONObject();
                        int i5 = jSONObject2.has(i) ? jSONObject2.getInt(i) : 0;
                        double d5 = Double.parseDouble(jSONObject3.has(j) ? jSONObject3.getString(j) : "0.09");
                        double d6 = Double.parseDouble(jSONObject3.has(k) ? jSONObject3.getString(k) : "0.01");
                        d2 = Double.parseDouble(jSONObject3.has(l) ? jSONObject3.getString(l) : "70");
                        d3 = d6;
                        d4 = d5;
                        i3 = i5;
                    }
                }
                d2 = 70.0d;
                i2 = 1;
                d3 = 0.01d;
                d4 = 0.09d;
                i3 = 0;
            }
            boolean zA = a(i2, i);
            if (zA) {
                try {
                    if (!o) {
                        strA = a9.a(a9.e, (String) null);
                        if (strA == null) {
                            i4 = 1 + Integer.parseInt(strA);
                        }
                        q = i4;
                        g1.b().a(q, g1.c.a(i3, d4, d3, d2));
                    }
                } catch (Exception e2) {
                    return zA;
                }
            } else {
                strA = a9.a(a9.e, (String) null);
                if (strA == null) {
                }
                q = i4;
                g1.b().a(q, g1.c.a(i3, d4, d3, d2));
            }
            h9.b().e().a(g1.b().a(p));
            return zA;
        } catch (Exception e3) {
            return false;
        }
    }

    @Override // com.coralline.sea.o4
    public boolean c() {
        return h() == null || h() == v3.FULL_START;
    }

    @Override // com.coralline.sea.o4
    public boolean d() {
        return v3.FULL_START == a(n3.a().a);
    }

    @Override // com.coralline.sea.o4
    public String g() {
        return m1.j().j("DEFAULT");
    }

    public final String j() {
        String strA = a9.a(u, c7.c);
        String next = null;
        try {
            if (!TextUtils.isEmpty(strA)) {
                JSONObject jSONObject = new JSONObject(strA);
                JSONObject jSONObject2 = jSONObject.has("location") ? jSONObject.getJSONObject("location") : null;
                if (jSONObject2 != null) {
                    Iterator<String> itKeys = jSONObject2.keys();
                    if (itKeys.hasNext()) {
                        next = itKeys.next();
                    }
                }
            }
            if (TextUtils.isEmpty(next)) {
                return m1.c(n3.a().a);
            }
        } catch (Exception e2) {
        }
        return next;
    }

    public boolean k() {
        return true;
    }
}
