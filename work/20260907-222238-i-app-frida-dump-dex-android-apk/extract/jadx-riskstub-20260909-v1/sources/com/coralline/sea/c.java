package com.coralline.sea;

import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
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
    */
    public JSONObject a(int i2) {
        String str;
        String str2;
        double d2 = this.b;
        double d3 = i2;
        float f2 = ((float) ((d2 + 1.0d) * d3)) * 1000.0f;
        float f3 = ((float) ((1.0d - d2) * d3)) * 1000.0f;
        JSONObject jSONObject = new JSONObject();
        if (this.c == 0) {
            this.c = System.currentTimeMillis();
            jSONObject.put("code", 1);
            return jSONObject;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = this.c;
        long j2 = j + ((long) f2);
        long j3 = j + ((long) f3);
        this.c = jCurrentTimeMillis;
        int i3 = this.a;
        if (jCurrentTimeMillis > j2) {
            if (i3 == 1) {
                this.a = 2;
            } else if (i3 == 2) {
                this.a = 3;
            }
            if (this.a != i3) {
                if (i3 == 3 || i3 == 5) {
                    String str3 = "status";
                    jSONObject.put(str3, "normal");
                }
                int i4 = this.a;
                if (i4 != 3) {
                    if (i4 == 5) {
                        str = "status";
                        str2 = "deceleration";
                    }
                    jSONObject.put("code", 0);
                    return jSONObject;
                }
                str = "status";
                str2 = "acceleration";
                jSONObject.put(str, str2);
                jSONObject.put("code", 0);
                return jSONObject;
            }
        } else if (jCurrentTimeMillis < j3) {
            if (i3 == 1) {
                this.a = 4;
            } else if (i3 == 4) {
                this.a = 5;
            }
            if (this.a != i3) {
            }
        } else {
            if (i3 != 1) {
                switch (i3) {
                    case 2:
                    case 4:
                        this.a = 1;
                        break;
                }
            }
            if (this.a != i3) {
            }
        }
        return jSONObject;
    }
}
