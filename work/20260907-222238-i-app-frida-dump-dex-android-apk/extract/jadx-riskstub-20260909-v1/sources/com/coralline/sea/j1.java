package com.coralline.sea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class j1 {
    public static final String c = "callback-for-all-response";
    public static j1 d;
    public final Map<String, Set<l4>> a = Collections.synchronizedMap(new HashMap());
    public volatile boolean b = false;

    public static synchronized j1 b() {
        if (d == null) {
            d = new j1();
        }
        return d;
    }

    public static void c(l4 l4Var, String str) {
        j1 j1VarB = b();
        if (str == null) {
            str = c;
        }
        j1VarB.a(l4Var, str);
    }

    public static void d(l4 l4Var, String str) {
        j1 j1VarB = b();
        if (str == null) {
            str = c;
        }
        j1VarB.b(l4Var, str);
    }

    public final void a(l4 l4Var, String str) {
        synchronized (this.a) {
            Set<l4> set = this.a.get(str);
            if (set == null) {
                HashSet hashSet = new HashSet();
                hashSet.add(l4Var);
                this.a.put(str, hashSet);
            } else {
                set.add(l4Var);
            }
        }
    }

    public void a(s1 s1Var, boolean z, boolean z2) {
        a(this.a, s1Var, z, z2);
    }

    public void a(String str) {
        Set<l4> set = this.a.get(str);
        if (set != null) {
            Iterator<l4> it = set.iterator();
            while (it.hasNext()) {
                try {
                    it.next().b(null);
                } catch (Exception e) {
                }
            }
        }
    }

    public final void a(Map<String, Set<l4>> map, s1 s1Var, boolean z, boolean z2) {
        synchronized (map) {
            this.b = false;
            try {
                Set<l4> set = map.get(c);
                Set<l4> set2 = map.get(s1Var.d);
                ArrayList<l4> arrayList = new ArrayList();
                if (set != null) {
                    arrayList.addAll(set);
                }
                if (set2 != null) {
                    arrayList.addAll(set2);
                }
                for (l4 l4Var : arrayList) {
                    if (!l4Var.toString().contains("EventMonitor") || !z2) {
                        if (z) {
                            l4Var.b(s1Var);
                        } else {
                            l4Var.a(s1Var);
                        }
                    }
                }
            } catch (Exception e) {
            }
            this.b = true;
        }
    }

    public boolean a() {
        return this.b;
    }

    public final void b(l4 l4Var, String str) {
        synchronized (this.a) {
            Set<l4> set = this.a.get(str);
            if (set != null) {
                set.remove(l4Var);
            }
        }
    }
}
