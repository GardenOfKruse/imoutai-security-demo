package com.coralline.sea;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
@Deprecated
public class e9 extends t6 {
    public e9() {
        super("smali");
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        String strT = i6.t();
        if (strT.equals("1")) {
            Context context = n3.a().a;
            if (context != null) {
                context.getPackageResourcePath();
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("debuggable", strT);
                push(e2.b, "smali", jSONObject.toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void start() {
    }
}
