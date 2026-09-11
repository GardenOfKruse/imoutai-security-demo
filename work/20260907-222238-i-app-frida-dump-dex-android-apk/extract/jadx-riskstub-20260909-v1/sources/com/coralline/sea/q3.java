package com.coralline.sea;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class q3 {
    public static final String b = "ExtraInfo";
    public static final String c = "chnlno";
    public static final String d = "did";
    public static final String e = "sid";
    public static q3 f;
    public JSONObject a;

    public static synchronized q3 b() {
        if (f == null) {
            f = new q3();
        }
        return f;
    }

    public JSONObject a() {
        JSONObject jSONObject;
        synchronized (this) {
            jSONObject = this.a;
        }
        return jSONObject;
    }

    @NonNull
    public final JSONObject a(@Nullable JSONObject jSONObject) {
        try {
            if (jSONObject == null) {
                return new JSONObject();
            }
            JSONObject jSONObject2 = new JSONObject();
            Object objOpt = jSONObject.opt(c);
            if (objOpt != null) {
                jSONObject2.put(c, objOpt);
            }
            Object objOpt2 = jSONObject.opt(d);
            if (objOpt2 != null) {
                jSONObject2.put(d, objOpt2);
            }
            Object objOpt3 = jSONObject.opt(e);
            if (objOpt3 != null) {
                jSONObject2.put(e, objOpt3);
            }
            return jSONObject2;
        } catch (Throwable th) {
            return new JSONObject();
        }
    }

    public void a(@Nullable Object obj) {
        if (obj == null) {
            return;
        }
        try {
            obj.toString();
            if (obj instanceof JSONObject) {
                JSONObject jSONObject = (JSONObject) obj;
                c(jSONObject);
                if (t1.b(p3.b)) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put(p3.b, jSONObject);
                    b(jSONObject2);
                }
            }
        } catch (Throwable th) {
        }
    }

    public final void b(@Nullable JSONObject jSONObject) {
        if (jSONObject == null) {
            return;
        }
        p3.a(new s1(jSONObject.toString(), y1.b(p3.b), p3.b, e2.b, true));
    }

    public void c(@Nullable JSONObject jSONObject) {
        if (jSONObject == null) {
            return;
        }
        JSONObject jSONObjectA = a(jSONObject);
        synchronized (this) {
            this.a = jSONObjectA;
        }
    }
}
