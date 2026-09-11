package com.coralline.sea;

import java.util.HashSet;
import java.util.Objects;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class x3 extends x6 {
    public HashSet<String> b;
    public boolean c;
    public o1 d;
    public JSONObject e;

    public x3() {
        super("game_cheating", 5);
        this.b = new HashSet<>();
    }

    public final JSONObject a() {
        return n3.a().g ? o6.f().a(getName()) : z1.a(getName());
    }

    public JSONObject a(JSONObject jSONObject) {
        try {
            JSONObject jSONObjectA = this.d.a(this, jSONObject);
            if (jSONObjectA == null) {
                return null;
            }
            if (!this.c) {
                if (!n3.a().g) {
                    return null;
                }
            }
            return jSONObjectA;
        } catch (Exception e) {
            return null;
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        JSONObject jSONObjectA;
        this.c = false;
        JSONObject jSONObjectA2 = a();
        this.e = jSONObjectA2;
        if (jSONObjectA2 == null || (jSONObjectA = a(jSONObjectA2)) == null) {
            return;
        }
        push(e2.b, "game_plugin", jSONObjectA.toString());
    }

    @Override // com.coralline.sea.checkers.Checker
    public void start() {
        Objects.toString(a());
        this.d = new o1();
    }
}
