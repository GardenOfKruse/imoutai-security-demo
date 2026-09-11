package com.coralline.sea;

import android.text.TextUtils;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class z1 extends x6 {
    public static volatile JSONObject c = null;
    public static final String d = "checker";
    public static volatile JSONObject e = null;
    public static final String f = "infrastructure";
    public static volatile JSONObject g = null;
    public static final String h = "other";
    public static final String i = "on_demand";
    public static volatile JSONObject j = null;
    public static final String k = "config";
    public static final String l = "config_checker_key";
    public static final String m = "config_sync_time_key";
    public static final boolean n = d();
    public static boolean o = false;
    public static Lock p;
    public static Condition q;
    public static boolean r;
    public l4 b;

    public class a implements l4 {
        public a() {
        }

        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) {
            try {
                JSONObject jSONObject = new JSONObject(s1Var.d());
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("configuration");
                b9.a(jSONObject.optString(c2.c));
                z1.a(jSONObjectOptJSONObject);
            } catch (JSONException e) {
            }
        }
    }

    static {
        ReentrantLock reentrantLock = new ReentrantLock();
        p = reentrantLock;
        q = reentrantLock.newCondition();
        r = false;
    }

    public z1() {
        super(k, 60);
        this.b = new a();
    }

    public static synchronized JSONObject a(String str) {
        if (e == null) {
            return new JSONObject();
        }
        JSONObject jSONObjectOptJSONObject = e.optJSONObject(str);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        return jSONObjectOptJSONObject;
    }

    public static synchronized void a(JSONObject jSONObject) {
        try {
            c.toString();
            c = jSONObject;
            c.toString();
            if (n3.a().C && c.optJSONObject("checker") != null && c.optJSONObject("checker").optJSONObject(x8.i) != null && c.optJSONObject("checker").optJSONObject(x8.i).optJSONObject("mapping") != null && c.optJSONObject("checker").optJSONObject(x8.i).optJSONObject("mapping").optJSONArray("category") != null) {
                c.optJSONObject("checker").optJSONObject(x8.i).optJSONObject("mapping").put("category", new JSONArray());
                Objects.toString(c.optJSONObject("checker").optJSONObject(x8.i));
            }
            a9.b("config_checker_key", c);
            e = c.has("checker") ? c.optJSONObject("checker") : new JSONObject();
            g = c.has(f) ? c.optJSONObject(f) : new JSONObject();
            j = c.has(h) ? c.optJSONObject(h) : new JSONObject();
            a9.b(m, String.valueOf(System.currentTimeMillis()));
            j();
            i();
            if (!o) {
                o = true;
                h();
            }
        } catch (Exception e2) {
        }
    }

    public static synchronized JSONObject b() {
        if (c == null) {
            return new JSONObject();
        }
        JSONObject jSONObjectOptJSONObject = c.has(i) ? c.optJSONObject(i) : new JSONObject();
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        return jSONObjectOptJSONObject;
    }

    public static synchronized JSONObject b(String str) {
        if (g == null) {
            return new JSONObject();
        }
        JSONObject jSONObjectOptJSONObject = g.optJSONObject(str);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        return jSONObjectOptJSONObject;
    }

    public static synchronized JSONObject c(String str) {
        if (j == null) {
            return new JSONObject();
        }
        JSONObject jSONObjectOptJSONObject = j.optJSONObject(str);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        return jSONObjectOptJSONObject;
    }

    public static boolean c() {
        return o;
    }

    public static synchronized boolean d() {
        try {
            if (c != null) {
                return true;
            }
            c = a9.a("config_checker_key", (JSONObject) null);
            if (c == null || c.length() < 1) {
                String strA = a();
                c = TextUtils.isEmpty(strA) ? new JSONObject() : new JSONObject(strA);
                a9.b("config_checker_key", c.toString());
            }
            e = c.has("checker") ? c.optJSONObject("checker") : new JSONObject();
            g = c.has(f) ? c.optJSONObject(f) : new JSONObject();
            j = c.has(h) ? c.optJSONObject(h) : new JSONObject();
            c.toString();
            return true;
        } catch (JSONException e2) {
            return false;
        }
    }

    public static synchronized boolean d(String str) {
        try {
            JSONObject jSONObject = c.getJSONObject("persist");
            if (!jSONObject.getBoolean("enable")) {
                return false;
            }
            if (!str.equals(g9.a) && !str.equals("dev_info") && !str.equals(o.e)) {
                JSONArray jSONArray = jSONObject.getJSONArray("checker");
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    if (jSONArray.get(i2).equals(str)) {
                        return true;
                    }
                }
            }
            return true;
        } catch (Exception e2) {
        }
        return false;
    }

    public static boolean e() {
        return a(x9.n).optBoolean("switch_update_observer", false);
    }

    public static boolean f() {
        return c("background_conf").optBoolean("background_strict_mode_enabled", false);
    }

    public static boolean g() {
        if (g == null) {
            return false;
        }
        return g.optBoolean("ccb_query_route_disabled", false);
    }

    public static void h() {
        try {
            p.lock();
            q.signal();
        } catch (Exception e2) {
        } catch (Throwable th) {
            try {
                p.unlock();
            } catch (Exception e3) {
            }
            throw th;
        }
        try {
            p.unlock();
        } catch (Exception e4) {
        }
    }

    public static void i() {
        JSONObject jSONObjectOptJSONObject = j.optJSONObject("mulit_config");
        if (jSONObjectOptJSONObject != null) {
            h9.b().c().a(jSONObjectOptJSONObject.optInt("msg_size", 0), jSONObjectOptJSONObject.optInt("wait_time", 0));
        }
    }

    public static void j() {
        JSONObject jSONObjectOptJSONObject = j.optJSONObject("mulit_config");
        if (jSONObjectOptJSONObject == null || !jSONObjectOptJSONObject.has(t1.b)) {
            return;
        }
        r = jSONObjectOptJSONObject.optBoolean(t1.b);
    }

    public static boolean k() {
        Lock lock;
        boolean zAwait;
        try {
            p.lock();
            zAwait = q.await(2L, TimeUnit.SECONDS);
            try {
                lock = p;
            } catch (Exception e2) {
                return zAwait;
            }
        } catch (InterruptedException e3) {
            try {
                lock = p;
                zAwait = false;
            } catch (Exception e4) {
                return false;
            }
        } catch (Throwable th) {
            try {
                p.unlock();
            } catch (Exception e5) {
            }
            throw th;
        }
        lock.unlock();
        return zAwait;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        boolean z;
        j1.c(this.b, this.checkerName);
        long jA = a9.a(m, 0L);
        if (e == null || e.length() == 0 || jA == 0) {
            z = true;
        } else {
            z = false;
            try {
                if (System.currentTimeMillis() - jA >= e.getJSONObject(k).getLong("update_slice_time") * 1000) {
                    z = true;
                } else {
                    o = true;
                }
                j();
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        if (z) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONArray jSONArray = new JSONArray();
                jSONArray.put("configuration");
                jSONObject.put("type", jSONArray);
                jSONObject.put(c2.a, true);
                push(e2.c, e2.c, jSONObject.toString());
            } catch (Exception e3) {
            }
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void flush() {
        super.flush();
        a9.b(m, 0L);
        start();
        check();
    }

    @Override // com.coralline.sea.checkers.Checker
    public void start() {
    }

    public static String a() {
        try {
            String strB = ja.b(n3.T.a, n3.a().t);
            try {
                if (n3.T.s) {
                    return n3.T.B ? v1.a(strB, i6.r(), i6.q()) : v1.a(strB);
                }
                return strB;
            } catch (Exception e2) {
                return strB;
            }
        } catch (Exception e3) {
            return null;
        }
    }
}
