package com.coralline.sea;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class q {
    public static boolean c = false;
    public final long a;
    public HashMap<String, a> b = new HashMap<>();

    public static class a {
        public final String a;
        public String b;
        public JSONObject c;

        public a(JSONObject jSONObject) throws JSONException {
            this.a = jSONObject.getString("pkg_name");
            String string = jSONObject.getString("apk_type");
            this.b = string;
            if (!string.equals("normal") && !this.b.equals(aa.m)) {
                throw new RuntimeException("neither normal nor unknown!");
            }
            this.c = jSONObject;
            a("install");
        }

        public static JSONObject a(a aVar, a aVar2) throws JSONException {
            if (aVar == null) {
                aVar2.a("install");
                return aVar2.a();
            }
            if (aVar2 == null) {
                aVar.a("uninstall");
                return aVar.a();
            }
            if (!aVar.b.equals(aVar2.b)) {
                aVar2.a(aVar2.b.equals(aa.m) ? "to_unknown" : "to_normal");
                return aVar2.a();
            }
            long jOptLong = aVar.c.optLong("install_time", 0L);
            long jOptLong2 = aVar2.c.optLong("install_time", 0L);
            boolean z = false;
            boolean z2 = (jOptLong == 0 || jOptLong2 == 0 || jOptLong == jOptLong2) ? false : true;
            long jOptLong3 = aVar.c.optLong("update_time", 0L);
            long jOptLong4 = aVar2.c.optLong("update_time", 0L);
            if (jOptLong3 != 0 && jOptLong4 != 0 && jOptLong3 != jOptLong4) {
                z = true;
            }
            if (z2 || z) {
                aVar2.a("reinstall");
                return aVar2.a();
            }
            if (aVar.a(aVar2)) {
                return null;
            }
            aVar2.a("reinstall");
            return aVar2.a();
        }

        public JSONObject a() {
            return this.c;
        }

        public void a(String str) throws JSONException {
            this.c.put("type", str);
        }

        public boolean a(a aVar) throws JSONException {
            if (!this.a.equals(aVar.a)) {
                return false;
            }
            if (this.b.equals(aa.m)) {
                return true;
            }
            JSONObject jSONObject = aVar.c;
            return this.c.get("md5").equals(jSONObject.get("md5")) && this.c.get("ver_code").equals(jSONObject.get("ver_code"));
        }
    }

    public q(long j) {
        this.a = j;
    }

    public static q a() {
        HashSet<String> hashSet;
        JSONObject jSONObjectA = t.a().a(n3.a().a, true);
        if (jSONObjectA == null) {
            jSONObjectA = new JSONObject();
        }
        if (c) {
            hashSet = t.a().a(true);
            c = false;
        } else {
            hashSet = new HashSet<>();
        }
        return a(jSONObjectA, hashSet);
    }

    public static q a(JSONObject jSONObject, HashSet<String> hashSet) {
        q qVar = new q(System.currentTimeMillis());
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            if (!hashSet.isEmpty()) {
                hashSet.remove(next);
            }
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject(next);
                jSONObject2.put("apk_type", "normal");
                qVar.a(new a(jSONObject2));
            } catch (Exception e) {
            }
        }
        if (!hashSet.isEmpty()) {
            hashSet.toString();
            for (String str : hashSet) {
                try {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("pkg_name", str);
                    jSONObject3.put("apk_type", aa.m);
                    qVar.a(new a(jSONObject3));
                } catch (Exception e2) {
                }
            }
        }
        return qVar;
    }

    public static void a(q qVar) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("apk_list_id", qVar.a);
            jSONObject.put("data", qVar.b());
            a9.b(a9.h, jSONObject);
        } catch (Exception e) {
        }
    }

    public static q c() {
        try {
            JSONObject jSONObjectA = a9.a(a9.h, (JSONObject) null);
            if (jSONObjectA != null) {
                q qVar = new q(jSONObjectA.getLong("apk_list_id"));
                JSONArray jSONArray = jSONObjectA.getJSONArray("data");
                for (int i = 0; i < jSONArray.length(); i++) {
                    qVar.a(new a(jSONArray.getJSONObject(i)));
                }
                return qVar;
            }
        } catch (Exception e) {
        }
        return new q(0L);
    }

    public final void a(a aVar) {
        if (this.b.containsKey(aVar.a)) {
            return;
        }
        this.b.put(aVar.a, aVar);
    }

    public JSONArray b() {
        JSONArray jSONArray = new JSONArray();
        Iterator<a> it = this.b.values().iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().a());
        }
        return jSONArray;
    }

    public int d() {
        return this.b.size();
    }
}
