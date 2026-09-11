package com.coralline.sea;

import android.content.Context;
import android.content.pm.PackageInfo;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
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
