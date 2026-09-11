package com.coralline.sea;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class c {
    public static final int d = 1;
    public static final int e = 2;
    public static final int f = 3;
    public static final int g = 4;
    public static final int h = 5;
    public static c i = new c();
    public double b = 0.2d;
    public long c = 0;
    public int a = 1;

    public static c b() {
        return i;
    }

    public int a() {
        return this.a;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0061 A[Catch: JSONException -> 0x0095, TryCatch #0 {JSONException -> 0x0095, blocks: (B:3:0x001a, B:5:0x0023, B:7:0x002f, B:25:0x0069, B:32:0x0079, B:33:0x007c, B:38:0x008b, B:39:0x008e, B:12:0x004b, B:23:0x0064, B:18:0x0058, B:22:0x0061, B:20:0x005d, B:24:0x0067), top: B:43:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0064 A[Catch: JSONException -> 0x0095, TryCatch #0 {JSONException -> 0x0095, blocks: (B:3:0x001a, B:5:0x0023, B:7:0x002f, B:25:0x0069, B:32:0x0079, B:33:0x007c, B:38:0x008b, B:39:0x008e, B:12:0x004b, B:23:0x0064, B:18:0x0058, B:22:0x0061, B:20:0x005d, B:24:0x0067), top: B:43:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public org.json.JSONObject a(int r14) {
        /*
            r13 = this;
            double r0 = r13.b
            r2 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r4 = r0 + r2
            float r14 = (float) r14
            double r6 = (double) r14
            double r4 = r4 * r6
            float r14 = (float) r4
            r4 = 1148846080(0x447a0000, float:1000.0)
            float r14 = r14 * r4
            double r2 = r2 - r0
            double r2 = r2 * r6
            float r0 = (float) r2
            float r0 = r0 * r4
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>()
            long r2 = r13.c     // Catch: org.json.JSONException -> L95
            r4 = 0
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            r2 = 1
            if (r6 != 0) goto L2f
            long r3 = java.lang.System.currentTimeMillis()     // Catch: org.json.JSONException -> L95
            r13.c = r3     // Catch: org.json.JSONException -> L95
            java.lang.String r14 = "code"
            r1.put(r14, r2)     // Catch: org.json.JSONException -> L95
            return r1
        L2f:
            long r3 = java.lang.System.currentTimeMillis()     // Catch: org.json.JSONException -> L95
            long r5 = r13.c     // Catch: org.json.JSONException -> L95
            long r7 = (long) r14     // Catch: org.json.JSONException -> L95
            long r9 = r5 + r7
            long r7 = (long) r0     // Catch: org.json.JSONException -> L95
            long r11 = r5 + r7
            r13.c = r3     // Catch: org.json.JSONException -> L95
            int r14 = r13.a     // Catch: org.json.JSONException -> L95
            int r0 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            r5 = 2
            r6 = 5
            r7 = 3
            if (r0 <= 0) goto L4e
            if (r14 == r2) goto L64
            if (r14 == r5) goto L4b
            goto L69
        L4b:
            r13.a = r7     // Catch: org.json.JSONException -> L95
            goto L69
        L4e:
            int r0 = (r3 > r11 ? 1 : (r3 == r11 ? 0 : -1))
            r3 = 4
            if (r0 >= 0) goto L5b
            if (r14 == r2) goto L61
            if (r14 == r3) goto L58
            goto L69
        L58:
            r13.a = r6     // Catch: org.json.JSONException -> L95
            goto L69
        L5b:
            if (r14 == r2) goto L69
            switch(r14) {
                case 2: goto L67;
                case 3: goto L64;
                case 4: goto L67;
                case 5: goto L61;
                default: goto L60;
            }     // Catch: org.json.JSONException -> L95
        L60:
            goto L69
        L61:
            r13.a = r3     // Catch: org.json.JSONException -> L95
            goto L69
        L64:
            r13.a = r5     // Catch: org.json.JSONException -> L95
            goto L69
        L67:
            r13.a = r2     // Catch: org.json.JSONException -> L95
        L69:
            int r0 = r13.a     // Catch: org.json.JSONException -> L95
            if (r0 == r14) goto L96
            if (r14 != r7) goto L74
            java.lang.String r14 = "status"
        L71:
            java.lang.String r0 = "normal"
            goto L79
        L74:
            if (r14 != r6) goto L7c
            java.lang.String r14 = "status"
            goto L71
        L79:
            r1.put(r14, r0)     // Catch: org.json.JSONException -> L95
        L7c:
            int r14 = r13.a     // Catch: org.json.JSONException -> L95
            if (r14 != r7) goto L85
            java.lang.String r14 = "status"
            java.lang.String r0 = "acceleration"
            goto L8b
        L85:
            if (r14 != r6) goto L8e
            java.lang.String r14 = "status"
            java.lang.String r0 = "deceleration"
        L8b:
            r1.put(r14, r0)     // Catch: org.json.JSONException -> L95
        L8e:
            java.lang.String r14 = "code"
            r0 = 0
            r1.put(r14, r0)     // Catch: org.json.JSONException -> L95
            return r1
        L95:
            r14 = move-exception
        L96:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.c.a(int):org.json.JSONObject");
    }
}
