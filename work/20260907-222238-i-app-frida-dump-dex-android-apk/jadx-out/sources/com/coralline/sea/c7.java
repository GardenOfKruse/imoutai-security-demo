package com.coralline.sea;

import android.location.Location;
import android.text.TextUtils;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class c7 {
    public static final String c = "";
    public static final boolean d = false;
    public static ConcurrentHashMap<String, Long> a = new ConcurrentHashMap<>(64);
    public static ConcurrentHashMap<String, b> b = new ConcurrentHashMap<>(64);
    public static final JSONArray e = new JSONArray();
    public static final JSONObject f = new JSONObject();
    public static final Location g = null;
    public static final List h = null;

    public static class b {
        public String a;
        public long b;
        public int c;

        public b() {
        }
    }

    public static JSONArray a(JSONArray jSONArray) {
        if (jSONArray == null) {
            return null;
        }
        try {
            return new JSONArray(jSONArray.toString());
        } catch (Exception e2) {
            return new JSONArray();
        }
    }

    public static JSONObject a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        try {
            return new JSONObject(jSONObject.toString());
        } catch (Exception e2) {
            return new JSONObject();
        }
    }

    public static boolean a(String str) {
        return b7.c(str) > 0;
    }

    public static boolean b(String str) {
        if (!j2.a() && j2.a(str)) {
            return false;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jC = b7.c(str);
        long jLongValue = a.get(str) == null ? 0L : a.get(str).longValue();
        if (TextUtils.equals(str, d2.c)) {
            jC = 100000;
        }
        if (jC <= 0 || jCurrentTimeMillis - jLongValue <= jC) {
            return false;
        }
        a.put(str, Long.valueOf(jCurrentTimeMillis));
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean c(java.lang.String r14) {
        /*
            boolean r0 = com.coralline.sea.j2.a()
            r1 = 0
            if (r0 != 0) goto Le
            boolean r0 = com.coralline.sea.j2.a(r14)
            if (r0 == 0) goto Le
            return r1
        Le:
            long r2 = java.lang.System.currentTimeMillis()
            long r4 = com.coralline.sea.b7.c(r14)
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.coralline.sea.c7$b> r0 = com.coralline.sea.c7.b
            java.lang.Object r0 = r0.get(r14)
            com.coralline.sea.c7$b r0 = (com.coralline.sea.c7.b) r0
            if (r0 != 0) goto L25
            com.coralline.sea.c7$b r0 = new com.coralline.sea.c7$b
            r0.<init>()
        L25:
            r0.a = r14
            int r6 = r0.c
            r7 = 1
            int r6 = r6 + r7
            r0.c = r6
            r8 = 60000(0xea60, double:2.9644E-319)
            int r10 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r10 <= 0) goto L3d
            long r8 = r0.b
            long r10 = r2 - r8
            int r6 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r6 <= 0) goto L5a
            goto L55
        L3d:
            r10 = 0
            int r12 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
            if (r12 <= 0) goto L47
            long r4 = r8 / r4
            int r4 = (int) r4
            goto L48
        L47:
            r4 = 0
        L48:
            if (r4 <= 0) goto L5a
            long r10 = r0.b
            long r12 = r2 - r10
            int r5 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1))
            if (r5 > 0) goto L55
            if (r6 > r4) goto L5a
            goto L5b
        L55:
            r0.c = r7
            r0.b = r2
            goto L5b
        L5a:
            r7 = 0
        L5b:
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.coralline.sea.c7$b> r1 = com.coralline.sea.c7.b
            r1.put(r14, r0)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.c7.c(java.lang.String):boolean");
    }
}
