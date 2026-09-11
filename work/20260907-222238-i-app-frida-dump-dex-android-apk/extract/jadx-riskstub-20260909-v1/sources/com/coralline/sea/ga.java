package com.coralline.sea;

import java.util.Iterator;
import java.util.Objects;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class ga {
    public static ga b = null;
    public static final String c = "userdata_pre";
    public static final String d = "location_pre";
    public JSONObject a;

    public static synchronized ga a() {
        if (b == null) {
            b = new ga();
        }
        return b;
    }

    public final JSONObject a(JSONObject jSONObject) {
        if (n3.a().d) {
            return jSONObject;
        }
        try {
            if (n3.T.c || !t1.b("userdata")) {
                return jSONObject;
            }
            JSONObject jSONObject2 = new JSONObject();
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                jSONObject2.put("ud_" + next, jSONObject.getString(next));
            }
            return jSONObject2;
        } catch (Exception e) {
            return null;
        }
    }

    public void a(Object obj) {
        Objects.toString(obj);
        if (obj instanceof JSONObject) {
            a9.b(d, (JSONObject) obj);
        } else {
            obj.getClass();
        }
    }

    public JSONObject b() {
        JSONObject jSONObject;
        synchronized (this) {
            jSONObject = this.a;
        }
        return jSONObject;
    }

    public final JSONObject b(JSONObject jSONObject) {
        try {
            Object objOpt = jSONObject.opt("userdata");
            if (objOpt == null) {
                return jSONObject;
            }
            if (!(objOpt instanceof String)) {
                return null;
            }
            try {
                return new JSONObject((String) objOpt);
            } catch (Exception e) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("content", objOpt);
                return jSONObject2;
            }
        } catch (Exception e2) {
            return null;
        }
    }

    public void b(Object obj) {
        String str;
        Objects.toString(obj);
        if (!(obj instanceof JSONObject)) {
            obj.getClass();
            return;
        }
        JSONObject jSONObject = (JSONObject) obj;
        d(jSONObject);
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("user_data_type", "user_extra");
            if (!n3.a().c) {
                str = t1.b("userdata") ? "userdata" : "user_data";
                a9.b("userdata_pre", jSONObject);
                c(jSONObject2);
            }
            jSONObject2.put(str, jSONObject);
            a9.b("userdata_pre", jSONObject);
            c(jSONObject2);
        } catch (Exception e) {
        }
    }

    public void c(Object obj) {
        Objects.toString(obj);
        if (!(obj instanceof JSONObject)) {
            obj.getClass();
            return;
        }
        JSONObject jSONObject = (JSONObject) obj;
        try {
            jSONObject.put("user_data_type", "account_switch");
            c(jSONObject);
        } catch (Exception e) {
        }
    }

    public final void c(JSONObject jSONObject) {
        ea.a(new s1(jSONObject.toString(), y1.b("userdata"), "userdata", e2.b, true));
    }

    public void d(JSONObject jSONObject) {
        JSONObject jSONObjectA;
        if (jSONObject == null || (jSONObjectA = a(jSONObject)) == null) {
            return;
        }
        synchronized (this) {
            this.a = jSONObjectA;
        }
    }
}
