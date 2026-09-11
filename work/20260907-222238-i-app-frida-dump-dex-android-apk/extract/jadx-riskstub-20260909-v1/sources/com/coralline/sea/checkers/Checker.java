package com.coralline.sea.checkers;

import com.coralline.sea.l9;
import com.coralline.sea.q1;
import com.coralline.sea.s1;
import com.coralline.sea.y1;
import com.coralline.sea.y9;
import com.coralline.sea.z1;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public abstract class Checker {
    protected q1 checkConfig;
    protected String checkerName;
    protected boolean needCheck = true;
    protected boolean toPersist;

    public q1 buildConfig(String str, int i) {
        int i2;
        int i3 = 0;
        try {
            JSONObject jSONObjectA = z1.a(str);
            if (i == 0 || jSONObjectA == null || !jSONObjectA.has("period") || (i2 = jSONObjectA.getInt("period")) <= 0) {
                i2 = i;
            }
            if (jSONObjectA != null && jSONObjectA.has("boost_delay") && jSONObjectA.getInt("boost_delay") > 0) {
                i3 = jSONObjectA.getInt("boost_delay");
            }
            q1 q1VarD = i2 == 0 ? q1.d() : q1.a(i2, i3);
            this.toPersist = z1.d(str);
            return q1VarD;
        } catch (Exception e) {
            return i == 0 ? q1.d() : q1.a(i, i3);
        }
    }

    public abstract void check();

    public void checkNoTask() {
    }

    public void flush() {
    }

    public q1 getDefaultCheckerConfig() {
        return this.checkConfig;
    }

    public String getName() {
        return this.checkerName;
    }

    public boolean getNeedCheck(boolean z) {
        return this.needCheck;
    }

    public void initialize() {
    }

    public boolean isInWhitelist(String str, String str2, String str3) throws JSONException {
        JSONObject jSONObjectA = z1.a(str3);
        if (jSONObjectA.has("whitelist")) {
            JSONArray jSONArray = jSONObjectA.getJSONArray("whitelist");
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
                String strOptString = jSONObjectOptJSONObject.optString("matchMode");
                String strOptString2 = jSONObjectOptJSONObject.optString("packageName");
                String strOptString3 = jSONObjectOptJSONObject.optString("appName");
                if ("0".equals(strOptString) && str.equals(strOptString2)) {
                    return true;
                }
                if ("1".equals(strOptString) && str2.equals(strOptString3)) {
                    return true;
                }
                if ("2".equals(strOptString) && str.equals(strOptString2) && str2.equals(strOptString3)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean needToPersist() {
        return this.toPersist;
    }

    public boolean push(String str, String str2, String str3) {
        boolean zA;
        if (y9.d() || l9.b().d()) {
            return false;
        }
        s1 s1Var = new s1(str3, y1.b(str2), this.checkerName, str, needToPersist());
        synchronized (y9.class) {
            zA = y9.c().a(s1Var);
        }
        return zA;
    }

    public void setNeedCheck(boolean z) {
        this.needCheck = z;
    }

    public void start() {
    }

    public void stop() {
    }
}
