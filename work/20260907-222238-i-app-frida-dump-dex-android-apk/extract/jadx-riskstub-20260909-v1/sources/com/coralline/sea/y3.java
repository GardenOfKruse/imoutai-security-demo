package com.coralline.sea;

import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class y3 extends x6 {
    public static final String d = "running_context";
    public static final String e = "first-time";
    public static final String f = "process";
    public static final String g = "self_process";
    public static final String h = "service";
    public static boolean i = true;
    public static boolean j = true;
    public static boolean k = true;
    public int b;
    public boolean c;

    public y3() {
        super(d, 20);
        this.b = 0;
        this.c = true;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        JSONArray jSONArrayB;
        try {
            JSONObject jSONObject = new JSONObject();
            if ((this.b % 6 == 0 || !this.c) && (jSONArrayB = e7.b()) != null && jSONArrayB.length() != 0) {
                jSONObject = new JSONObject();
                jSONObject.put("protol_type", f);
                jSONObject.put("processes", jSONArrayB);
                jSONObject.put(e, e7.a);
                push(e2.b, f, jSONObject.toString());
            }
            if (this.b % 6 == 2 || !this.c) {
                JSONArray jSONArrayB2 = l8.b();
                if (jSONArrayB2.length() != 0) {
                    jSONObject = new JSONObject();
                    jSONObject.put("protol_type", g);
                    jSONObject.put("3pp_so", new JSONArray());
                    jSONObject.put("so", jSONArrayB2);
                    jSONObject.put(e, j);
                    if (j) {
                        j = false;
                    }
                    push(e2.b, g, jSONObject.toString());
                }
            }
            if (this.c) {
                this.b++;
            } else {
                this.c = true;
            }
            jSONObject.length();
        } catch (Exception e2) {
        }
    }
}
