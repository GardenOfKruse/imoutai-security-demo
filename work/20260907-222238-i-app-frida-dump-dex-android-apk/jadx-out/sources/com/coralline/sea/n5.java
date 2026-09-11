package com.coralline.sea;

import android.location.Location;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class n5 {
    public static final String i = "gps_a";
    public static final String j = "gps_b";
    public static final String k = "accuracy";
    public static final String l = "gps_speed";
    public static final String m = "type";
    public static final String n = "reason";
    public static final String o = "cycle_time";
    public static final String p = "cycle_distance";
    public static final String q = "location_threshold";
    public static boolean r = false;
    public static boolean s = false;
    public static boolean t = false;
    public static boolean u = false;
    public Integer h = null;
    public int a = 16;
    public int b = 3200;
    public int c = 10000;
    public int d = 20;
    public double e = 0.0d;
    public int f = 10;
    public List<o5> g = new ArrayList();

    public final void a() {
        JSONObject jSONObjectOptJSONObject;
        try {
            JSONObject jSONObjectA = z1.a("position");
            if (jSONObjectA == null || jSONObjectA.length() == 0 || (jSONObjectOptJSONObject = jSONObjectA.optJSONObject("gps")) == null || jSONObjectOptJSONObject.length() == 0) {
                return;
            }
            this.a = jSONObjectOptJSONObject.optInt(o, this.a);
            this.b = jSONObjectOptJSONObject.optInt("distance", this.b);
            this.c = jSONObjectOptJSONObject.optInt(q, this.c);
            this.d = jSONObjectOptJSONObject.optInt("location_shift", this.d);
        } catch (Exception e) {
        }
    }

    public final void a(Location location, Location location2, String str, JSONArray jSONArray, String str2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(i, ja.a(location));
            jSONObject.put(j, ja.a(location2));
            jSONObject.put(n, str);
            jSONObject.put("type", str2);
            jSONObject.put(o, this.a);
            jSONObject.put(p, this.b);
            jSONObject.put(q, this.c);
            jSONArray.put(jSONObject);
        } catch (Exception e) {
        }
    }

    public void a(o5 o5Var, JSONObject jSONObject) {
        try {
            if (u) {
                return;
            }
            o5 o5Var2 = null;
            o5 o5Var3 = this.g.size() > 0 ? this.g.get(0) : null;
            if (this.g.size() > 1) {
                o5Var2 = this.g.get(r1.size() - 2);
            }
            if (o5Var3 == null || o5Var2 == null || o5Var3.a == null || o5Var2.a == null || o5Var.a == null) {
                return;
            }
            int i2 = (int) ((o5Var.c - o5Var3.c) / 1000);
            double dCeil = Math.ceil(((double) this.b) / ((double) this.a));
            double dCeil2 = this.e + Math.ceil(o5Var.a.distanceTo(o5Var2.a));
            this.e = dCeil2;
            double dCeil3 = Math.ceil(dCeil2 / ((double) i2));
            o5Var3.a.getLongitude();
            if (dCeil3 > dCeil) {
                JSONObject jSONObject2 = new JSONObject();
                JSONObject jSONObject3 = new JSONObject();
                jSONObject2.put("longitude", o5Var3.a.getLongitude());
                jSONObject2.put("latitude", o5Var3.a.getLatitude());
                jSONObject3.put("longitude", o5Var.a.getLongitude());
                jSONObject3.put("latitude", o5Var.a.getLatitude());
                jSONObject.put(i, jSONObject2);
                jSONObject.put(j, jSONObject3);
                jSONObject.put("distance_from_config", this.b);
                jSONObject.put("period_from_config", this.a);
                jSONObject.put("distance", this.e);
                jSONObject.put("type", "gps_new");
                jSONObject.put("cycle_ver_speed", dCeil3);
                jSONObject.put("cycle_time_range", o5Var3.c + "-" + o5Var.c);
                u = true;
            }
        } catch (Exception e) {
        }
    }

    public final void b() {
        try {
            List<o5> list = this.g;
            if (list == null || list.size() <= 1) {
                return;
            }
            for (int size = this.g.size() - 1; size > 0; size--) {
                this.g.remove(size);
            }
        } catch (Exception e) {
        }
    }

    public JSONObject c() {
        JSONObject jSONObject;
        o5 o5VarA;
        List<o5> list;
        try {
            try {
                a();
                o5VarA = o5.a();
            } finally {
                List<o5> list2 = this.g;
                if (list2 != null && list2.size() > this.f) {
                    b();
                }
            }
        } catch (Exception e) {
            jSONObject = null;
        }
        if (o5VarA.d == null) {
            return null;
        }
        if (o5VarA.a != null) {
            this.g.add(o5VarA);
        }
        jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            if (jSONArray.length() == 0) {
                a(o5VarA, jSONObject);
            } else {
                jSONObject.put("data", jSONArray);
                jSONObject.put("type", "cross_validation");
            }
            o5VarA.toString();
            list = this.g;
        } catch (Exception e2) {
            List<o5> list3 = this.g;
            if (list3 != null && list3.size() > this.f) {
            }
            return jSONObject;
        }
        if (list != null && list.size() > this.f) {
            b();
        }
        return jSONObject;
    }
}
