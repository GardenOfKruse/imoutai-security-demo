package com.coralline.sea;

import android.text.TextUtils;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public abstract class h0 implements q4 {
    public f4 a;
    public String b;
    public String c;
    public boolean d = true;
    public int e = -1;
    public int f = -1;
    public Set<String> g;
    public b0 h;
    public String i;

    public h0(f4 f4Var) {
        this.a = f4Var;
    }

    @Override // com.coralline.sea.q4
    public void a(JSONObject jSONObject) {
        String[] strArrSplit;
        if (jSONObject == null) {
            return;
        }
        try {
            String strOptString = jSONObject.optString("primary_key");
            this.b = strOptString;
            if (!TextUtils.isEmpty(strOptString)) {
                this.c = jSONObject.optString(this.b);
            }
            String strOptString2 = jSONObject.optString("swich");
            if ("on".equalsIgnoreCase(strOptString2)) {
                this.d = true;
            } else if ("off".equalsIgnoreCase(strOptString2)) {
                this.d = false;
            }
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("monitor_method");
            if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 0) {
                this.g = y8.b(jSONArrayOptJSONArray);
            }
            String strOptString3 = jSONObject.optString("api_scope");
            this.i = strOptString3;
            if (TextUtils.isEmpty(strOptString3) || (strArrSplit = this.i.split("-")) == null || strArrSplit.length != 2) {
                return;
            }
            this.e = Integer.parseInt(strArrSplit[0]);
            this.f = Integer.parseInt(strArrSplit[1]);
        } catch (Exception e) {
        }
    }

    @Override // com.coralline.sea.q4
    public boolean a() {
        return this.d;
    }

    @Override // com.coralline.sea.q4
    public String b() {
        return this.c;
    }

    @Override // com.coralline.sea.q4
    public b0 c() {
        return this.h;
    }

    @Override // com.coralline.sea.q4
    public int d() {
        return this.f;
    }

    @Override // com.coralline.sea.q4
    public Set<String> e() {
        return this.g;
    }

    @Override // com.coralline.sea.q4
    public f4 f() {
        return this.a;
    }

    @Override // com.coralline.sea.q4
    public int g() {
        return this.e;
    }

    @Override // com.coralline.sea.q4
    public void h() {
        try {
            if (!a()) {
                String str = this.a.a;
                return;
            }
            if (i()) {
                if (y8.a().a(this.c) != null) {
                    String str2 = this.a.a;
                    return;
                }
                boolean zJ = j();
                String str3 = this.a.a;
                if (zJ) {
                    y8.a().a(this.c, (q4) this);
                }
            }
        } catch (Exception e) {
        }
    }

    public boolean i() {
        int i;
        int i2 = this.e;
        if (i2 == -1 || (i = this.f) == -1) {
            return true;
        }
        int i3 = ja.b;
        if (i3 >= i2 && i3 <= i) {
            return true;
        }
        String str = f().a;
        return false;
    }

    public abstract boolean j();
}
