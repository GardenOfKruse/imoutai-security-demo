package com.coralline.sea;

import com.coralline.sea.r1;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class ca {
    public String a;
    public String b;
    public String c;
    public String d;
    public long e = System.currentTimeMillis();
    public String f;

    public String a() {
        return this.f;
    }

    public void a(long j) {
        this.e = j;
    }

    public void a(String str) {
        this.f = str;
    }

    public String b() {
        return this.b;
    }

    public void b(String str) {
        this.b = str;
    }

    public String c() {
        return this.a;
    }

    public void c(String str) {
        this.a = str;
    }

    public String d() {
        return this.d;
    }

    public void d(String str) {
        this.d = str;
    }

    public String e() {
        return this.c;
    }

    public void e(String str) {
        this.c = str;
    }

    public long f() {
        return this.e;
    }

    public JSONObject g() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sdk_name", this.a);
            jSONObject.put("sdk_class_name", this.b);
            jSONObject.put(y3.h, this.c);
            jSONObject.put("sensitive_behavior", this.d);
            jSONObject.put(r1.k.e, this.e);
            jSONObject.put("hit_policy", this.f);
            return jSONObject;
        } catch (JSONException e) {
            return jSONObject;
        }
    }

    public int hashCode() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("sdk_name", c());
            jSONObject.put(y3.h, e());
            jSONObject.put("sensitive_behavior", d());
            return jSONObject.toString().hashCode();
        } catch (Exception e) {
            return 0;
        }
    }

    public String toString() {
        return g().toString();
    }
}
