package com.coralline.sea;

import android.content.Context;
import android.content.pm.PackageInfo;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class u7 implements x4 {
    @Override // com.coralline.sea.x4
    public JSONObject a() {
        return t5.d().c();
    }

    @Override // com.coralline.sea.x4
    public JSONObject a(Context context) {
        return f6.d().b(context);
    }

    @Override // com.coralline.sea.x4
    public PackageInfo b(Context context) {
        f6.d();
        return f6.c(context);
    }

    @Override // com.coralline.sea.x4
    public boolean c(Context context) {
        return f6.d().d(context);
    }

    @Override // com.coralline.sea.x4
    public JSONObject d(Context context) {
        return z7.a(context).k();
    }
}
