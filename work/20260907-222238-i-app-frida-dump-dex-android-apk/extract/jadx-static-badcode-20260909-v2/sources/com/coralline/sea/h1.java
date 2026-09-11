package com.coralline.sea;

import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class h1 extends t6 {
    public static final String c = "call_status";
    public static final String d = "on_the_phone";
    public static final String e = "CallStatusChecker";
    public PhoneStateListener a;
    public boolean b;

    public class a extends PhoneStateListener {
        public a() {
        }

        @Override // android.telephony.PhoneStateListener
        public void onCallStateChanged(int i, String str) {
            h1 h1Var = h1.this;
            if (!h1Var.b && i == 2) {
                h1Var.push(e2.b, h1.d, h1Var.a(i, (String) null).toString());
                h1.this.b = true;
            }
        }
    }

    public static final class b {
        public static final h1 a = new h1();
    }

    public h1() {
        super(c);
        this.b = false;
    }

    public static h1 a() {
        return b.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) n3.a().a.getSystemService(m1.j);
            if (telephonyManager == null) {
                return;
            }
            if (telephonyManager.getCallState() == 2) {
                push(e2.b, d, a(2, (String) null).toString());
                this.b = true;
            }
            a aVar = new a();
            this.a = aVar;
            telephonyManager.listen(aVar, 32);
        } catch (Exception e2) {
        }
    }

    public final JSONObject a(int i, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(n5.n, "state:" + i);
            jSONArray.put(jSONObject2.toString());
            if (TextUtils.isEmpty(str)) {
                jSONObject.put("business_type", c7.c);
            } else {
                jSONObject.put("business_type", str);
            }
            jSONObject.put("detail", jSONArray);
            jSONObject.put(c, i == 2);
            return jSONObject;
        } catch (JSONException e2) {
            return jSONObject;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0014  */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public JSONObject a(JSONObject jSONObject) {
        String strOptString;
        if (jSONObject != null) {
            try {
                strOptString = jSONObject.has("custom_param") ? jSONObject.optString("custom_param") : null;
            } catch (Exception e2) {
                return null;
            }
        }
        TelephonyManager telephonyManager = (TelephonyManager) n3.a().a.getSystemService(m1.j);
        if (telephonyManager != null && telephonyManager.getSimState() != 1 && telephonyManager.getCallState() == 2) {
            return a(telephonyManager.getCallState(), strOptString);
        }
        return null;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.coralline.sea.-$$Lambda$h1$2sJN4wBLTfj96o_q6glPcLKTVU4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.b();
            }
        });
    }
}
