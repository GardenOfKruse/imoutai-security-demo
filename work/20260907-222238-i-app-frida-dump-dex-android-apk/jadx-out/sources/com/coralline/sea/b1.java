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
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean a(java.lang.String r15) {
        /*
            Method dump skipped, instruction units count: 403
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.b1.a(java.lang.String):boolean");
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
