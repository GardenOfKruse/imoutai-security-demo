package com.coralline.sea;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class t5 {
    public static final String f = "MagiskCheck";
    public static final String g = "/proc/net/unix";
    public static final String h = "^[0-9a-zA-Z]{32}$";
    public static t5 i = null;
    public static JSONObject j = null;
    public static boolean k = false;
    public static String l = "magiskd";
    public String[] a = {"/sbin/magisk", "/data/adb/magisk", "/sbin/.magisk", "/cache/.disable_magisk", "/dev/.magisk.unblock", "/cache/magisk.log", "/data/adb/magisk.img", "/data/adb/magisk.db", "/data/adb/.boot_count", "/data/adb/magisk_simple", "/init.magisk.rc", "/data/dalvik-cache/profiles/com.topjohn"};
    public String b = "com.topjohnwu.magisk";
    public String c = "com.topjohnwu.magisk-2";
    public String d = c7.c;
    public boolean e = true;

    public static synchronized t5 d() {
        if (i == null) {
            i = new t5();
        }
        return i;
    }

    public String a(Context context) {
        try {
            return i6.a(context);
        } catch (Exception e) {
            return c7.c;
        }
    }

    public final boolean a() {
        return ja.v(this.b);
    }

    public final boolean a(JSONArray jSONArray) {
        JSONArray jSONArrayA = s3.a(this.a);
        if (jSONArrayA.length() <= 0) {
            return false;
        }
        try {
            jSONArray.put(new JSONObject().put("file", jSONArrayA));
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    public final boolean b() {
        return ja.v(this.c);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00a4 A[Catch: all -> 0x00dc, Exception -> 0x00de, TryCatch #0 {Exception -> 0x00de, blocks: (B:3:0x0001, B:5:0x002a, B:9:0x0030, B:15:0x0040, B:16:0x0055, B:18:0x005b, B:20:0x0070, B:21:0x0073, B:24:0x007c, B:34:0x009b, B:35:0x009e, B:37:0x00a4, B:38:0x00a7, B:40:0x00ad, B:41:0x00d9, B:26:0x0087, B:12:0x0038), top: B:51:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ad A[Catch: all -> 0x00dc, Exception -> 0x00de, TryCatch #0 {Exception -> 0x00de, blocks: (B:3:0x0001, B:5:0x002a, B:9:0x0030, B:15:0x0040, B:16:0x0055, B:18:0x005b, B:20:0x0070, B:21:0x0073, B:24:0x007c, B:34:0x009b, B:35:0x009e, B:37:0x00a4, B:38:0x00a7, B:40:0x00ad, B:41:0x00d9, B:26:0x0087, B:12:0x0038), top: B:51:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized org.json.JSONObject c() {
        /*
            Method dump skipped, instruction units count: 229
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.t5.c():org.json.JSONObject");
    }

    public final String e() {
        return a9.a(a9.i, "false");
    }
}
