package com.coralline.sea;

import android.content.pm.PackageManager;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class s8 extends j0 {
    public String k;
    public String l;

    public s8(f4 f4Var) {
        super(f4Var);
    }

    @Override // com.coralline.sea.j0, com.coralline.sea.h0, com.coralline.sea.q4
    public void a(JSONObject jSONObject) {
        super.a(jSONObject);
        if (jSONObject != null) {
            try {
                this.k = jSONObject.optString("service_field_name");
                this.l = jSONObject.optString("reflect_class_name");
            } catch (Exception e) {
            }
        }
    }

    @Override // com.coralline.sea.h0
    public boolean j() {
        try {
            Object objC = q7.j(this.l).c(this.k).c();
            Class<?> cls = Class.forName(this.j);
            this.h = new b0(this);
            Object objNewProxyInstance = Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new t8(objC, this.h));
            q7.j(this.l).a(this.k, objNewProxyInstance);
            if (this.k.equals("sPackageManager")) {
                PackageManager packageManager = n3.a().a.getPackageManager();
                Field declaredField = packageManager.getClass().getDeclaredField("mPM");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(packageManager);
                declaredField.set(packageManager, objNewProxyInstance);
                y8.a().a(this.k, obj.getClass().getName());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String l() {
        return this.l;
    }

    public String m() {
        return this.k;
    }
}
