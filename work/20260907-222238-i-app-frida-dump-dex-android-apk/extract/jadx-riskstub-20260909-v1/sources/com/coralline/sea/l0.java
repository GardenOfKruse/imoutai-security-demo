package com.coralline.sea;

import java.io.File;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class l0 extends x6 {
    public static final String f = "black_app";
    public static final String g = "config_checker_key";
    public z2 b;
    public JSONArray c;
    public String d;
    public String e;

    public class a implements l4 {
        public a() {
        }

        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) {
            l0 l0Var = l0.this;
            l0Var.d = l0Var.e;
        }
    }

    public l0() {
        super(f, 15);
        this.b = new z2();
    }

    public final JSONObject a() {
        JSONArray jSONArray = this.c;
        if (jSONArray == null || jSONArray.length() <= 0) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < this.c.length(); i++) {
            try {
                JSONObject jSONObject = this.c.getJSONObject(i);
                JSONArray jSONArray3 = jSONObject.getJSONArray("character");
                String string = jSONObject.getString("pkg_name");
                String string2 = jSONObject.getString("name");
                jSONArray3.toString();
                JSONArray jSONArray4 = new JSONArray();
                for (int i2 = 0; i2 < jSONArray3.length(); i2++) {
                    String string3 = jSONArray3.getString(i2);
                    if (new File(string3).exists()) {
                        jSONArray4.put(string3);
                    }
                }
                if (jSONArray4.length() > 0) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("pkg_name", string);
                    jSONObject2.put("character", jSONArray4);
                    jSONObject2.put("name", string2);
                    jSONArray2.put(jSONObject2);
                }
            } catch (JSONException e) {
                return null;
            }
        }
        if (jSONArray2.length() <= 0) {
            return null;
        }
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("path_list", jSONArray2);
        return jSONObject3;
    }

    public final JSONArray b() {
        try {
            JSONObject jSONObjectA = z1.a(f);
            if (jSONObjectA != null && jSONObjectA.has("black_apps")) {
                return jSONObjectA.getJSONArray("black_apps");
            }
            return null;
        } catch (Exception e) {
            e.toString();
            return null;
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        JSONObject jSONObjectA = a();
        if (jSONObjectA == null || jSONObjectA.length() <= 0) {
            return;
        }
        String str = this.d;
        if (str == null || !str.equals(jSONObjectA.toString())) {
            try {
                push(e2.b, f, jSONObjectA.toString());
                this.e = jSONObjectA.toString();
            } catch (Exception e) {
            }
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void initialize() {
        try {
            this.c = b();
            j1.c(new a(), f);
        } catch (Exception e) {
        }
    }
}
