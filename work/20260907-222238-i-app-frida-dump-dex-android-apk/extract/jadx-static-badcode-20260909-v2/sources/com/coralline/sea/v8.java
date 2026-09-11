package com.coralline.sea;

import android.content.Context;
import android.os.IBinder;
import android.text.TextUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class v8 extends j0 {
    public String k;

    public v8(f4 f4Var) {
        super(f4Var);
    }

    @Override // com.coralline.sea.j0, com.coralline.sea.h0, com.coralline.sea.q4
    public void a(JSONObject jSONObject) {
        super.a(jSONObject);
        if (jSONObject != null) {
            try {
                this.k = jSONObject.optString("service_sys_name");
            } catch (Exception e) {
            }
        }
    }

    @Override // com.coralline.sea.h0
    public boolean j() {
        try {
            IBinder iBinderB = y8.b(this.k);
            if (iBinderB == null || TextUtils.isEmpty(this.j)) {
                return false;
            }
            b0 b0Var = new b0(this);
            this.h = b0Var;
            u8 u8Var = new u8(iBinderB, b0Var);
            IBinder iBinder = (IBinder) Proxy.newProxyInstance(iBinderB.getClass().getClassLoader(), iBinderB.getClass().getInterfaces(), new w8(iBinderB, this.j, true, u8Var));
            u8Var.c = iBinder;
            y8.a(this.k, iBinder);
            y8.a().a(this.k, (Object) iBinder);
            l();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void l() {
        try {
            Class<?> cls = Class.forName("android.app.ContextImpl");
            Method declaredMethod = cls.getDeclaredMethod("getImpl", Context.class);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(null, n3.a().a);
            Field declaredField = cls.getDeclaredField("mServiceCache");
            declaredField.setAccessible(true);
            Object[] objArr = (Object[]) declaredField.get(objInvoke);
            if (objArr != null) {
                for (int i = 0; i < objArr.length; i++) {
                    Objects.toString(objArr[i]);
                    Object obj = objArr[i];
                    if (obj != null && obj.toString().contains(this.k)) {
                        objArr[i] = null;
                    }
                }
                declaredField.set(objInvoke, objArr);
            }
        } catch (Exception e) {
        }
    }

    public String m() {
        return this.k;
    }
}
