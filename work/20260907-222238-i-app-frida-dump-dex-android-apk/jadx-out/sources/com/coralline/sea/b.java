package com.coralline.sea;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class b extends x6 {
    public static boolean h = false;
    public static boolean i = false;
    public boolean b;
    public int c;
    public int d;
    public c e;
    public boolean f;
    public int g;

    public class a extends TimerTask {
        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (!b.h || j2.a()) {
                return;
            }
            b.i = false;
        }
    }

    /* JADX INFO: renamed from: com.coralline.sea.b$b, reason: collision with other inner class name */
    public class C0001b extends Timer {
        public C0001b(String str) {
            super(str);
        }
    }

    public class c extends BroadcastReceiver {
        public c() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.SCREEN_OFF".equals(action)) {
                b.this.b = false;
            } else {
                "android.intent.action.SCREEN_ON".equals(action);
            }
        }
    }

    public b() {
        super("accelerate", 5);
        this.c = 0;
        this.d = 0;
        this.f = true;
        this.g = 5;
    }

    public static void a() {
        new C0001b(" Risk-Timer-AccelerateChecker").scheduleAtFixedRate(new a(), 0L, 1000L);
    }

    public JSONObject b() {
        String str;
        String str2;
        h = true;
        int iP = i6.P();
        h = false;
        if (!i || !this.b || iP == 0) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            if (iP <= 0) {
                if (iP < 0) {
                    int i2 = this.d + 1;
                    this.d = i2;
                    if (i2 != 3) {
                        return null;
                    }
                    this.c = 0;
                    str = "status";
                    str2 = "deceleration";
                }
                jSONObject.put("detail", new JSONArray().put(c7.c + iP));
                return jSONObject;
            }
            int i3 = this.c + 1;
            this.c = i3;
            if (i3 != 3) {
                return null;
            }
            this.d = 0;
            str = "status";
            str2 = "acceleration";
            jSONObject.put(str, str2);
            jSONObject.put("detail", new JSONArray().put(c7.c + iP));
            return jSONObject;
        } catch (Exception e) {
            return null;
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        try {
            JSONObject jSONObjectA = z1.a("accelerate");
            if (jSONObjectA != null && jSONObjectA.length() > 0) {
                this.g = jSONObjectA.optInt("period", 5);
                if (!this.f) {
                    return;
                }
            }
            try {
                if (!j2.a()) {
                    i = false;
                    return;
                }
                i = true;
                if (!j2.a(n3.a().a)) {
                    this.b = false;
                    return;
                }
                this.b = true;
                JSONObject jSONObjectB = b();
                if (jSONObjectB != null) {
                    push(e2.b, "speed0", jSONObjectB.toString());
                }
            } catch (Exception e) {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void initialize() {
        com.coralline.sea.c.b();
        this.e = new c();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        n3.a().a.registerReceiver(this.e, intentFilter);
        a();
    }
}
