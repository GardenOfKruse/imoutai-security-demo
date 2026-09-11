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
    */
    public static boolean c(String str) {
        if (!j2.a() && j2.a(str)) {
            return false;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jC = b7.c(str);
        b bVar = b.get(str);
        if (bVar == null) {
            bVar = new b();
        }
        bVar.a = str;
        boolean z = true;
        int i = bVar.c + 1;
        bVar.c = i;
        if (jC <= 60000) {
            int i2 = jC > 0 ? (int) (60000 / jC) : 0;
            if (i2 > 0) {
                if (jCurrentTimeMillis - bVar.b <= 60000) {
                    if (i > i2) {
                    }
                }
            }
        } else if (jCurrentTimeMillis - bVar.b > jC) {
            bVar.c = 1;
            bVar.b = jCurrentTimeMillis;
        } else {
            z = false;
        }
        b.put(str, bVar);
        return z;
    }
}
