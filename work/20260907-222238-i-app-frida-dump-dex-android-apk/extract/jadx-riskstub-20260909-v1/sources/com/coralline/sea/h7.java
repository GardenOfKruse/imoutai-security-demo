package com.coralline.sea;

import android.content.Context;
import android.content.ContextWrapper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class h7 extends h0 {
    public String j;

    public h7(f4 f4Var) {
        super(f4Var);
    }

    @Override // com.coralline.sea.h0, com.coralline.sea.q4
    public void a(JSONObject jSONObject) {
        super.a(jSONObject);
        if (jSONObject != null) {
            try {
                this.j = jSONObject.optString("uri_auth");
            } catch (Exception e) {
            }
        }
    }

    @Override // com.coralline.sea.h0
    public boolean j() {
        try {
            Context context = n3.a().a;
            if (context instanceof ContextWrapper) {
                ContextWrapper contextWrapper = (ContextWrapper) context;
                Class clsA = m4.a();
                try {
                    Object objInvoke = i.a().invoke(i.b(), contextWrapper.getBaseContext(), this.j, 0, Boolean.TRUE);
                    this.h = new b0(this);
                    i.a(this.j).b(Proxy.newProxyInstance(clsA.getClassLoader(), new Class[]{clsA, clsA.getInterfaces()[0]}, new i7(objInvoke, this.h)));
                    return true;
                } catch (InvocationTargetException e) {
                    e.toString();
                    return false;
                }
            }
        } catch (Exception e2) {
            e2.toString();
        }
        return false;
    }

    public String k() {
        return this.j;
    }
}
