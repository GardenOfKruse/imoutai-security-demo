package com.coralline.sea;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import com.coralline.sea.q7;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class q6 {
    public static q6 g;
    public static final String h = new String(Base64.decode("Y29tLmJhbmdjbGUuZXZlcmlzay5jb3JlLg==", 2));
    public final Class<?> a;
    public final Map<?, ?> b;
    public final ExecutorService c = Executors.newSingleThreadExecutor();
    public final Class<?> d;
    public final Map<Object, List<Object>> e;
    public final Map<Object, List<Object>> f;

    public class a implements Runnable {
        public final /* synthetic */ String a;
        public final /* synthetic */ String b;
        public final /* synthetic */ String c;

        public a(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                Context context = n3.a().a;
                Intent intent = new Intent(context, (Class<?>) q6.this.a);
                Bundle bundle = new Bundle();
                bundle.putString("alertActionKey", new JSONObject().put("title", this.a).put("body", this.b).put("action", this.c).toString());
                intent.putExtras(bundle);
                context.startActivity(intent);
            } catch (Exception e) {
            }
        }
    }

    public class b implements Runnable {
        public final /* synthetic */ JSONObject a;

        public b(JSONObject jSONObject) {
            this.a = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                String strOptString = this.a.optString("protol_type");
                if (strOptString.length() == 0) {
                    return;
                }
                for (Object obj : q6.this.b.keySet()) {
                    if (obj.toString().toLowerCase().equals(strOptString)) {
                        Set set = (Set) q6.this.b.get(obj);
                        if (set != null) {
                            Iterator it = set.iterator();
                            while (it.hasNext()) {
                                q7.a(it.next()).a("onResult", obj, this.a);
                            }
                            return;
                        }
                        return;
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public class c implements Runnable {
        public int a = 0;
        public int b = 0;

        public c() {
        }

        public final int a(Map map, String str, int i, List<Object> list) {
            if (map == null) {
                return i;
            }
            try {
                List list2 = (List) map.get(str);
                if (list2 == null || list2.size() <= i) {
                    return i;
                }
                for (int i2 = i; i2 < list2.size(); i2++) {
                    list.add(list2.get(i2));
                }
                return list2.size();
            } catch (Exception e) {
                return i;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            synchronized (q6.this.d) {
                this.a = a(q6.this.e, "userData", this.a, arrayList);
                this.b = a(q6.this.f, "userID", this.b, arrayList2);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ga.a().b(it.next());
            }
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                ga.a().c(it2.next());
            }
        }
    }

    public q6(Class<?> cls, Map<?, ?> map, Map<Object, List<Object>> map2, Map<Object, List<Object>> map3, Class<?> cls2) {
        this.a = cls;
        this.b = map;
        this.e = map2;
        this.f = map3;
        this.d = cls2;
        a();
    }

    public static <T> T a(String str, String str2, boolean z) {
        try {
            StringBuilder sb = new StringBuilder();
            String str3 = h;
            sb.append(str3);
            sb.append(str);
            if (!q7.j(sb.toString()).i(str2) && z) {
                return null;
            }
            return (T) q7.j(str3 + str).b(str2).c();
        } catch (q7.c e) {
            return null;
        }
    }

    public static boolean a(Context context, String str) {
        if (g != null) {
            return false;
        }
        try {
            String str2 = (String) b("loader.Plugin", "PLUGIN_HOME_DIR", false);
            String str3 = (String) b("loaderUtils.Udid", "udid", false);
            String str4 = (String) b("loaderUtils.Udid", "s_udidType", false);
            String str5 = (String) a("agent.Conf", "getAgentId", false);
            List list = (List) b("agent.Conf", "businessURL", false);
            String str6 = (String) a("agent.Conf", "getG_key", false);
            String str7 = (String) a("agent.Conf", "getVersion", false);
            Map map = (Map) b("RiskStubAPI", "registerListener", false);
            Map map2 = (Map) b("RiskStubAPI", "userExtraJsonObjDataMaps", true);
            Map map3 = (Map) b("RiskStubAPI", "userExtraIDMaps", true);
            String str8 = (String) a("loaderUtils.a", "ii", true);
            String str9 = (String) a("loaderUtils.a", "iii", true);
            if (str9 == null) {
                str9 = c7.c;
            }
            String str10 = str9;
            String str11 = (String) a("loaderUtils.a", "iiii", true);
            StringBuilder sb = new StringBuilder();
            String str12 = h;
            sb.append(str12);
            sb.append("activity.AlertActivity");
            g = new q6(Class.forName(sb.toString()), map, map2, map3, Class.forName(str12 + "RiskStubAPI"));
            String str13 = c7.c;
            if (str8 != null && str8.equals("CCB")) {
                str13 = "ccb";
            }
            TextUtils.isEmpty(str10);
            HashMap map4 = new HashMap();
            map4.put(1, context);
            map4.put(2, str2);
            map4.put(3, str);
            map4.put(6, str3);
            map4.put(7, str4);
            map4.put(20, str13);
            map4.put(19, str10);
            map4.put(10, Boolean.FALSE);
            map4.put(11, Integer.valueOf(str7));
            map4.put(14, str5);
            map4.put(15, list);
            map4.put(16, str11);
            map4.put(17, str6);
            n3.a(map4, true);
            v5.b();
            return true;
        } catch (Exception e) {
            x9.a("-200#" + e.getMessage());
            return false;
        }
    }

    public static <T> T b(String str, String str2, boolean z) {
        try {
            StringBuilder sb = new StringBuilder();
            String str3 = h;
            sb.append(str3);
            sb.append(str);
            if (!q7.j(sb.toString()).h(str2) && z) {
                return null;
            }
            return (T) q7.j(str3 + str).c(str2).c();
        } catch (q7.c e) {
            return null;
        }
    }

    public final void a() {
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new c(), 0L, 5L, TimeUnit.SECONDS);
    }

    public void a(String str, String str2, String str3) {
        new Handler(Looper.getMainLooper()).post(new a(str, str2, str3));
    }

    public void a(JSONObject jSONObject) {
        this.c.execute(new b(jSONObject));
    }
}
