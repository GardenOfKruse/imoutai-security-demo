package com.coralline.sea;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class j6 {
    public static j6 f = null;
    public static final String g = "sa1";
    public static final String h = "sb1";
    public static boolean i = false;
    public HashSet<String> a;
    public AtomicBoolean b = new AtomicBoolean(false);
    public boolean c;
    public o0 d;
    public String e;

    public j6(Context context, String str, String str2) {
        this.a = new HashSet<>();
        o0 o0Var = new o0(context);
        this.d = o0Var;
        String strA = o0Var.a("jsv", c7.c);
        if (TextUtils.isEmpty(strA)) {
            this.d.b("jsv", str2);
        }
        if (TextUtils.isEmpty(strA) || TextUtils.equals(str2, strA)) {
            this.a = this.d.a(h);
            b();
            if (this.a.contains("breakpad")) {
                this.c = true;
            } else {
                boolean zExists = new File(str).exists();
                this.c = zExists;
                if (zExists) {
                    this.a.add("breakpad");
                    this.d.a(h, this.a);
                }
            }
        } else {
            this.d.a();
        }
        Objects.toString(this.a);
    }

    public static JSONArray a() {
        try {
            if (f != null) {
                JSONArray jSONArray = new JSONArray((Collection) f.a);
                if (!TextUtils.isEmpty(f.e)) {
                    jSONArray.put(new JSONObject().put("lastRecordMethod", f.e));
                }
                return jSONArray;
            }
        } catch (Exception e) {
        }
        return new JSONArray();
    }

    public static synchronized void a(Context context, String str, String str2) {
        if (f == null) {
            try {
                f = new j6(context, str, str2);
            } catch (Exception e) {
            }
        }
    }

    public static void a(String str, int i2) {
        j6 j6Var = f;
        if (j6Var == null || j6Var.d == null || j6Var.b.get()) {
            return;
        }
        if (com.coralline.sea.checkers.a.c().a()) {
            f();
        } else {
            f.d.a(str, i2);
        }
    }

    public static boolean a(String str) {
        j6 j6Var = f;
        if (j6Var == null) {
            return false;
        }
        if (j6Var.c) {
            return true;
        }
        j6Var.a.contains(str);
        return f.a.contains(str);
    }

    public static boolean c() {
        return d() || a("ed");
    }

    public static boolean d() {
        j6 j6Var = f;
        if (j6Var != null) {
            return j6Var.c;
        }
        return false;
    }

    public static boolean e() {
        j6 j6Var = f;
        return j6Var != null && j6Var.a.size() > 0;
    }

    public static void f() {
        j6 j6Var = f;
        if (j6Var != null) {
            j6Var.b.set(true);
            j6 j6Var2 = f;
            if (j6Var2.d != null) {
                if (j6Var2.a.size() == 0) {
                    f.d.a();
                }
                f.d.b();
                f.d = null;
            }
        }
    }

    public final void b() {
        String strA = this.d.a(g, c7.c);
        String strA2 = this.d.a(1, c7.c);
        this.e = strA2;
        if (TextUtils.isEmpty(strA2)) {
            if (TextUtils.isEmpty(strA)) {
                return;
            }
            this.d.b(g, c7.c);
            return;
        }
        this.d.a(this.e, 0);
        this.d.b(g, this.e);
        if (TextUtils.isEmpty(strA) || !TextUtils.equals(this.e, strA)) {
            return;
        }
        this.a.add(this.e);
        this.d.a(h, this.a);
    }
}
