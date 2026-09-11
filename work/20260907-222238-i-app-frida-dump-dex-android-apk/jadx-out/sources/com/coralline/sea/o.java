package com.coralline.sea;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import androidx.annotation.Nullable;
import com.coralline.sea.checkers.Checker;
import com.coralline.sea.q;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class o extends t6 {
    public static boolean d = true;
    public static final String e = "apk_info";
    public static final Set<Runnable> f = Collections.newSetFromMap(new ConcurrentHashMap());
    public static final ConcurrentHashMap<String, String> g = new ConcurrentHashMap<>();
    public static boolean h = false;
    public static String i = c7.c;
    public boolean a;
    public q b;
    public l4 c;

    public class a implements l4 {
        public a() {
        }

        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) {
            synchronized (o.this) {
                if (o.this.b != null) {
                    q.a(o.this.b);
                    o.this.b = null;
                }
            }
        }
    }

    public static class b implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
            o oVarC;
            try {
                for (Map.Entry entry : o.g.entrySet()) {
                    String str = (String) entry.getKey();
                    String str2 = (String) entry.getValue();
                    if ("android.intent.action.PACKAGE_ADDED".equals(str2)) {
                        v6.a(str);
                    } else if ("android.intent.action.PACKAGE_REMOVED".equals(str2)) {
                        v6.b(str);
                    }
                }
                o.g.clear();
                if (o.d() && (oVarC = o.c()) != null) {
                    oVarC.check();
                }
            } finally {
                try {
                } finally {
                }
            }
        }
    }

    public static class c extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String schemeSpecificPart;
            try {
                String action = intent.getAction();
                Uri data = intent.getData();
                if (action == null || data == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
                    return;
                }
                o.g.put(schemeSpecificPart, action);
                Set<Runnable> set = o.f;
                if (set.isEmpty()) {
                    b bVar = new b();
                    com.coralline.sea.checkers.a.c().d().postDelayed(bVar, 10000L);
                    set.add(bVar);
                }
            } catch (Throwable th) {
                th.toString();
            }
        }
    }

    public o() {
        super(e);
        this.a = false;
        this.c = new a();
    }

    public static synchronized void a(Context context) {
        try {
            if (t1.a(e)) {
                return;
            }
            if (!z1.e()) {
                return;
            }
            if (h) {
                return;
            }
            h = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addDataScheme("package");
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            context.registerReceiver(new c(), intentFilter);
        } finally {
        }
    }

    @Nullable
    public static o c() {
        Checker checkerB;
        com.coralline.sea.checkers.a aVarB = com.coralline.sea.checkers.a.b();
        if (aVarB == null || (checkerB = aVarB.b(e)) == null || !(checkerB instanceof o)) {
            return null;
        }
        return (o) checkerB;
    }

    public static boolean d() {
        o oVarC = c();
        return oVarC != null && oVarC.a;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public final JSONObject a(q qVar, q qVar2) throws JSONException {
        long j;
        HashMap<String, q.a> map = qVar.b;
        HashMap<String, q.a> map2 = qVar2.b;
        JSONObject jSONObject = new JSONObject();
        if (map.size() == 0) {
            jSONObject.put("apk_flag", "all");
            jSONObject.put("data", qVar2.b());
            jSONObject.put("previous_apk_id", 0);
            jSONObject.put("current_apk_id", qVar2.a);
            return jSONObject;
        }
        HashSet<String> hashSet = new HashSet();
        hashSet.addAll(map.keySet());
        hashSet.addAll(map2.keySet());
        JSONArray jSONArray = new JSONArray();
        for (String str : hashSet) {
            JSONObject jSONObjectA = q.a.a(map.get(str), map2.get(str));
            if (jSONObjectA != null) {
                jSONArray.put(jSONObjectA);
            }
        }
        if (jSONArray.length() == 0) {
            jSONObject.put("apk_flag", "same");
            jSONObject.put("previous_apk_id", qVar.a);
            j = qVar.a;
        } else {
            jSONObject.put("apk_flag", "update");
            jSONObject.put("data", jSONArray);
            jSONObject.put("previous_apk_id", qVar.a);
            j = qVar2.a;
        }
        jSONObject.put("current_apk_id", j);
        return jSONObject;
    }

    public final void a(JSONObject jSONObject) throws JSONException {
        jSONObject.put("apk_prot_ver", 1);
        push(e2.b, x9.n, jSONObject.toString());
    }

    public void a(boolean z) {
        d = z;
    }

    public final void b(boolean z) {
        try {
            q qVar = z ? new q(0L) : q.c();
            q qVarA = q.a();
            JSONObject jSONObjectA = a(qVar, qVarA);
            if (!jSONObjectA.get("apk_flag").equals("same")) {
                synchronized (this) {
                    this.b = qVarA;
                    j1.c(this.c, this.checkerName);
                }
            }
            if (z) {
                jSONObjectA.put("resend", true);
            }
            if (n3.a().d) {
                jSONObjectA.put("total_count", qVarA.d());
            }
            String str = ja.o() + jSONObjectA.toString();
            if (!i.equals(str)) {
                jSONObjectA.put("is_first_upload", d);
                jSONObjectA.toString();
                a(jSONObjectA);
                d = false;
                i = str;
            }
        } catch (Exception e2) {
        } catch (Throwable th) {
            this.a = true;
            throw th;
        }
        this.a = true;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        b(false);
    }

    public final void e() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("apk_flag", "forbidden");
        jSONObject.put("previous_apk_id", 0);
        jSONObject.put("current_apk_id", 0);
        a(jSONObject);
    }

    @Override // com.coralline.sea.checkers.Checker
    public void flush() {
        b(true);
    }

    @Override // com.coralline.sea.checkers.Checker
    public void initialize() {
        System.currentTimeMillis();
        t.a(n3.a().a);
        System.currentTimeMillis();
    }
}
