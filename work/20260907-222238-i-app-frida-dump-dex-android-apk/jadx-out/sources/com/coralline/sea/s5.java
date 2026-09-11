package com.coralline.sea;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Window;
import com.coralline.sea.r1;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class s5 extends x6 {
    public static final String b = "LoginChecker";
    public static final int c = 3;
    public static final int d = 3000;
    public static final Context e;
    public static final String f;
    public static final r8 g;
    public static String h;
    public static boolean i;
    public static boolean j;
    public static LinkedList<JSONObject> k;

    public class a extends Thread {
        public a() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                JSONObject jSONObject3 = new JSONObject();
                jSONObject.put("os_type", a0.b);
                jSONObject.put("scene", "login");
                jSONObject.put("app_id", s5.f);
                jSONObject.put("start_id", ja.o());
                JSONArray jSONArrayD = s5.this.d();
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("eventList", jSONArrayD);
                jSONObject4.put("supported", true);
                jSONObject3.put(i1.f, jSONObject4);
                r8 r8Var = s5.g;
                JSONArray jSONArrayD2 = r8Var.d();
                if (jSONArrayD2 != null) {
                    JSONObject jSONObject5 = new JSONObject();
                    jSONObject5.put("eventList", jSONArrayD2);
                    jSONObject5.put("supported", true);
                    jSONObject3.put("lightData", jSONObject5);
                }
                JSONArray jSONArrayF = r8Var.f();
                if (jSONArrayF != null) {
                    JSONObject jSONObject6 = new JSONObject();
                    jSONObject6.put("eventList", jSONArrayF);
                    jSONObject6.put("supported", true);
                    jSONObject3.put("orientationData", jSONObject6);
                }
                JSONArray jSONArrayB = r8Var.b();
                if (jSONArrayB != null) {
                    JSONObject jSONObject7 = new JSONObject();
                    jSONObject7.put("eventList", jSONArrayB);
                    jSONObject7.put("supported", true);
                    jSONObject3.put("angularVelocityData", jSONObject7);
                }
                JSONArray jSONArrayG = r8Var.g();
                if (jSONArrayG != null) {
                    JSONObject jSONObject8 = new JSONObject();
                    jSONObject8.put("eventList", jSONArrayG);
                    jSONObject8.put("supported", true);
                    jSONObject3.put("pressureData", jSONObject8);
                }
                JSONArray jSONArrayE = r8Var.e();
                if (jSONArrayE != null) {
                    JSONObject jSONObject9 = new JSONObject();
                    jSONObject9.put("eventList", jSONArrayE);
                    jSONObject9.put("supported", true);
                    jSONObject3.put("magneticData", jSONObject9);
                }
                JSONArray jSONArrayA = r8Var.a();
                if (jSONArrayA != null) {
                    JSONObject jSONObject10 = new JSONObject();
                    jSONObject10.put("eventList", jSONArrayA);
                    jSONObject10.put("supported", true);
                    jSONObject3.put("accelerationData", jSONObject10);
                }
                JSONArray jSONArrayH = r8Var.h();
                if (jSONArrayH != null) {
                    JSONObject jSONObject11 = new JSONObject();
                    jSONObject11.put("eventList", jSONArrayH);
                    jSONObject11.put("supported", true);
                    jSONObject3.put("proximityData", jSONObject11);
                }
                jSONObject2.put("rawSensor", jSONObject3);
                jSONObject.put("behavior_info", jSONObject2);
                jSONObject.put("protol_type", "hxb_login");
                jSONObject.put("account", s5.h);
                jSONObject.put("scene", "login");
                jSONObject.put("os_type", a0.b);
                s5.this.push(e2.b, "hxb_login", jSONObject.toString());
            } catch (Exception e) {
            }
        }
    }

    static {
        Context context = n3.a().a;
        e = context;
        f = context.getApplicationInfo().packageName;
        g = r8.c();
        h = c7.c;
        i = false;
        j = false;
    }

    public s5() {
        super("login", 5);
    }

    public static boolean a(Object obj) {
        Window.Callback callback;
        try {
            if (!(obj instanceof Activity)) {
                return false;
            }
            if (k == null) {
                k = new LinkedList<>();
            }
            Activity activity = (Activity) obj;
            Window window = activity.getWindow();
            if (window == null || (callback = window.getCallback()) == null) {
                return false;
            }
            if (callback.getClass().equals(i1.class)) {
                return true;
            }
            window.setCallback(new i1(activity, window.getCallback(), k));
            return true;
        } catch (Exception e2) {
            return false;
        }
    }

    public static boolean b(Object obj) {
        if (!(obj instanceof String)) {
            return false;
        }
        j = true;
        h = (String) obj;
        return true;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        if (!i && j) {
            i = true;
            new a().start();
        }
    }

    public JSONArray d() {
        int size = k.size();
        JSONArray jSONArray = new JSONArray();
        if (size == 0) {
            return jSONArray;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (int i2 = size - 1; i2 > 0; i2--) {
            JSONObject jSONObject = k.get(i2);
            try {
                if (jCurrentTimeMillis - jSONObject.getLong(r1.k.e) < 3000) {
                    jSONArray.put(jSONObject);
                }
            } catch (Exception e2) {
            }
        }
        return jSONArray;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void initialize() {
        r8 r8Var = g;
        Context context = e;
        r8Var.a(context);
        Application application = (Application) context.getApplicationContext();
        Context applicationContext = application.getApplicationContext();
        if (k == null) {
            k = new LinkedList<>();
        }
        application.registerActivityLifecycleCallbacks(new h(applicationContext, k));
    }
}
