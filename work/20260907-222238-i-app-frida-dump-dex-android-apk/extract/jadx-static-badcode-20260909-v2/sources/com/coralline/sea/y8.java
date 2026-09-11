package com.coralline.sea;

import android.os.IBinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class y8 {
    public static final int a = 3;
    public static final String b = "android.os.ServiceManager";
    public static final String c = "getService";
    public static final String d = "sCache";
    public static final Map<String, q4> e = Collections.synchronizedMap(new HashMap());
    public static final Map<String, Object> f = new HashMap();
    public static final List<Integer> g = new ArrayList();
    public static final Object h = new Object();
    public static volatile JSONArray i;
    public static y8 j;

    public static synchronized y8 a() {
        if (j == null) {
            j = new y8();
        }
        return j;
    }

    public static Map<String, String> a(JSONArray jSONArray, String str, String str2) {
        if (jSONArray != null) {
            try {
                if (jSONArray.length() != 0) {
                    HashMap map = new HashMap();
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        try {
                            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i2);
                            map.put(jSONObjectOptJSONObject.optString(str), jSONObjectOptJSONObject.optString(str2));
                        } catch (Exception e2) {
                            return map;
                        }
                    }
                    return map;
                }
            } catch (Exception e3) {
            }
        }
        return null;
    }

    public static void a(ca caVar) {
        try {
            synchronized (h) {
                int iHashCode = caVar.hashCode();
                if (iHashCode == 0) {
                    return;
                }
                List<Integer> list = g;
                if (list.contains(Integer.valueOf(iHashCode))) {
                    return;
                }
                caVar.toString();
                if (i == null) {
                    i = new JSONArray();
                }
                i.put(caVar.g());
                list.add(Integer.valueOf(iHashCode));
            }
        } catch (Exception e2) {
        }
    }

    public static void a(String str, IBinder iBinder) {
        try {
            Map map = (Map) q7.j(b).c(d).c();
            if (map != null) {
                map.remove(str);
                map.put(str, iBinder);
            }
        } catch (Exception e2) {
        }
    }

    public static String[] a(JSONArray jSONArray) {
        if (jSONArray != null) {
            try {
                if (jSONArray.length() != 0) {
                    String[] strArr = new String[jSONArray.length()];
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        try {
                            strArr[i2] = jSONArray.getString(i2);
                        } catch (Exception e2) {
                        }
                    }
                    return strArr;
                }
            } catch (Exception e3) {
            }
        }
        return null;
    }

    public static IBinder b(String str) {
        try {
            return (IBinder) q7.j(b).a(c, str).c();
        } catch (Exception e2) {
            return null;
        }
    }

    public static String b() {
        try {
            synchronized (h) {
                if (i == null || i.length() <= 0) {
                    return null;
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("data", i);
                String string = jSONObject.toString();
                i = new JSONArray();
                return string;
            }
        } catch (Exception e2) {
            return null;
        }
    }

    public static Set<String> b(JSONArray jSONArray) {
        HashSet hashSet = new HashSet();
        if (jSONArray != null) {
            try {
                if (jSONArray.length() != 0) {
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        hashSet.add(jSONArray.getString(i2));
                    }
                }
            } catch (Exception e2) {
            }
        }
        return hashSet;
    }

    public static Set<String> c(JSONArray jSONArray) {
        Set<String> setB = b(jSONArray);
        if (setB == null) {
            setB = new HashSet<>();
        }
        setB.add(n3.a().a.getPackageName());
        return setB;
    }

    public q4 a(String str) {
        synchronized (this) {
            Map<String, q4> map = e;
            if (!map.containsKey(str)) {
                return null;
            }
            return map.get(str);
        }
    }

    public void a(String str, q4 q4Var) {
        synchronized (this) {
            Map<String, q4> map = e;
            if (!map.containsKey(str)) {
                map.put(str, q4Var);
            }
        }
    }

    public void a(String str, Object obj) {
        synchronized (this) {
            f.put(str, obj);
        }
    }

    public Object c(String str) {
        synchronized (this) {
            Map<String, Object> map = f;
            if (map == null || map.size() <= 0) {
                return null;
            }
            return map.containsKey(str) ? map.get(str) : null;
        }
    }

    public Set<Map.Entry<String, q4>> c() {
        Set<Map.Entry<String, q4>> setEntrySet;
        synchronized (this) {
            setEntrySet = e.entrySet();
        }
        return setEntrySet;
    }
}
