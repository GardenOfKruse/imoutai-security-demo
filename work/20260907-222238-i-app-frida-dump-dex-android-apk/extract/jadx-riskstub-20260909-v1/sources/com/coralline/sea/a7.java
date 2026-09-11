package com.coralline.sea;

import android.os.Handler;
import android.os.Looper;
import java.util.HashSet;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class a7 extends x6 {
    public int b;
    public n5 c;
    public int d;
    public int e;
    public int f;
    public Handler g;
    public HashSet<String> h;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a7 a7Var = a7.this;
            a7Var.a(a7Var.c.c(), new JSONArray().put("gps"));
        }
    }

    public a7() {
        super("position", 1800);
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = new Handler(Looper.getMainLooper());
        this.h = new HashSet<>();
    }

    public final void a(JSONObject jSONObject, JSONArray jSONArray) {
        if (jSONObject != null) {
            try {
                if (jSONObject.length() == 0) {
                    return;
                }
                String str = jSONObject.toString() + jSONArray.toString();
                if (this.h.contains(str)) {
                    return;
                }
                this.h.toString();
                jSONObject.put("detail", jSONArray);
                jSONObject.put("mock_location", q5.a(n3.a().a));
                push(e2.b, "location", jSONObject.toString());
                this.h.add(str);
            } catch (Exception e) {
            }
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        this.g.postDelayed(new a(), ja.a(0, 3000));
        int i = this.b;
        this.f = i;
        if (i % this.d == 0) {
            a(l6.c(), new JSONArray().put("cell"));
            int i2 = this.a;
            this.d = ja.a(i2 - 3, i2 + 3);
        }
        if (this.f % this.e == 0) {
            a(l6.f(), new JSONArray().put("wifi"));
            int i3 = this.a;
            this.e = ja.a(i3 - 3, i3 + 3);
        }
        this.b++;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void initialize() {
        this.b = 0;
        this.c = new n5();
        int i = this.a;
        this.d = i;
        this.e = i;
    }
}
