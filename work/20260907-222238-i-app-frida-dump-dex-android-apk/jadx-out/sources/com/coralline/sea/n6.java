package com.coralline.sea;

import android.os.Handler;
import android.os.HandlerThread;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class n6 {
    public static n6 c = null;
    public static String d = "";
    public Handler a;
    public HandlerThread b;

    public static class a implements Runnable {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public boolean f;

        public a(s1 s1Var) {
            try {
                s1Var.b();
                this.a = s1Var.a;
                this.b = new JSONObject(s1Var.b).getString("protol_type");
                this.c = s1Var.b;
                this.d = s1Var.d;
                this.e = s1Var.c;
                this.f = false;
            } catch (Exception e) {
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0034  */
        /* JADX WARN: Removed duplicated region for block: B:68:0x0152  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void run() {
            /*
                Method dump skipped, instruction units count: 504
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.n6.a.run():void");
        }
    }

    public n6() {
        HandlerThread handlerThread = new HandlerThread("offline_callback");
        this.b = handlerThread;
        handlerThread.start();
        this.a = new Handler(this.b.getLooper());
    }

    public static synchronized n6 a() {
        if (c == null) {
            c = new n6();
        }
        return c;
    }

    public static boolean a(String str, JSONObject jSONObject) {
        if (!"inject".equals(str)) {
            return (q8.a.equals(str) || q8.b.equals(str)) ? a(str, jSONObject, str) : b(str, jSONObject);
        }
        if (a(str, jSONObject, "binder") || a(str, jSONObject, "dlopen") || a(str, jSONObject, "hook")) {
            return true;
        }
        return false;
    }

    public static boolean a(String str, JSONObject jSONObject, String str2) throws JSONException {
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(str2);
        boolean z = false;
        z = false;
        if (jSONObjectOptJSONObject == null) {
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(str2);
            if (jSONArrayOptJSONArray != null) {
                JSONArray jSONArray = new JSONArray();
                boolean z2 = false;
                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                    if (b(str, jSONArrayOptJSONArray.optJSONObject(i))) {
                        jSONArray.put(jSONArrayOptJSONArray.optJSONObject(i));
                        z2 = true;
                    }
                }
                if (z2) {
                    jSONObject.put(str2, jSONArray);
                    return z2;
                }
                z = z2;
            }
            return z;
        }
        if (b(str, jSONObjectOptJSONObject)) {
            return true;
        }
        jSONObject.remove(str2);
        return z;
    }

    public static void b(s1 s1Var) {
        try {
            s1Var.toString();
            if (!s1Var.d.equals(p8.g) || new JSONObject(s1Var.a).optBoolean("hit_plot", true)) {
                String string = new JSONObject(s1Var.b).getString("protol_type");
                JSONObject jSONObjectB = o6.f().b(string);
                Objects.toString(jSONObjectB);
                if (jSONObjectB == null || !a(string, s1Var.a())) {
                    return;
                }
                d = ja.a(jSONObjectB, d);
            }
        } catch (Exception e) {
        }
    }

    public static boolean b(String str, JSONObject jSONObject) {
        double dDoubleValue = n3.a().y.containsKey(str) ? n3.T.y.get(str).doubleValue() : 0.0d;
        return dDoubleValue != 0.0d && (jSONObject != null ? jSONObject.optDouble("credibility", 1.0d) : 0.0d) >= dDoubleValue;
    }

    public void c(s1 s1Var) {
        this.a.post(new a(s1Var));
    }
}
