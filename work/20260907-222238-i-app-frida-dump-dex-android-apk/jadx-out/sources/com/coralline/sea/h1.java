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
        To view partially-correct add '--show-bad-code' argument
    */
    public org.json.JSONObject a(org.json.JSONObject r5) {
        /*
            r4 = this;
            r0 = 0
            if (r5 == 0) goto L14
            java.lang.String r1 = "custom_param"
            boolean r1 = r5.has(r1)     // Catch: java.lang.Exception -> L12
            if (r1 == 0) goto L14
            java.lang.String r1 = "custom_param"
            java.lang.String r5 = r5.optString(r1)     // Catch: java.lang.Exception -> L12
            goto L15
        L12:
            r5 = move-exception
            return r0
        L14:
            r5 = r0
        L15:
            com.coralline.sea.n3 r1 = com.coralline.sea.n3.a()     // Catch: java.lang.Exception -> L12
            android.content.Context r1 = r1.a     // Catch: java.lang.Exception -> L12
            java.lang.String r2 = "phone"
            java.lang.Object r1 = r1.getSystemService(r2)     // Catch: java.lang.Exception -> L12
            android.telephony.TelephonyManager r1 = (android.telephony.TelephonyManager) r1     // Catch: java.lang.Exception -> L12
            if (r1 != 0) goto L26
            return r0
        L26:
            int r2 = r1.getSimState()     // Catch: java.lang.Exception -> L12
            r3 = 1
            if (r2 != r3) goto L2e
            return r0
        L2e:
            int r2 = r1.getCallState()     // Catch: java.lang.Exception -> L12
            r3 = 2
            if (r2 != r3) goto L3e
            int r1 = r1.getCallState()     // Catch: java.lang.Exception -> L12
            org.json.JSONObject r5 = r4.a(r1, r5)     // Catch: java.lang.Exception -> L12
            return r5
        L3e:
            r5 = r0
            return r5
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.h1.a(org.json.JSONObject):org.json.JSONObject");
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
