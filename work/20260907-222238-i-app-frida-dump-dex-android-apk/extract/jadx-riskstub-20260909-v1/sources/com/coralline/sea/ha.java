package com.coralline.sea;

import android.text.TextUtils;
import com.coralline.sea.m5;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class ha {
    public static final String a = "{\"status\": -5, \"msg\": \"Send msg error, server no response, from plugin\"}";
    public static final String b = "{\"status\": -6, \"msg\": \"Send msg failed, message has been refused by GateWay\"}";
    public static final String c = "{\"status\": -7, \"msg\": \"Send msg failed, no messages allowed, stop sending!\"}";
    public static ha d;

    public class a implements l4 {
        public final /* synthetic */ s1 a;
        public final /* synthetic */ CountDownLatch b;

        public a(s1 s1Var, CountDownLatch countDownLatch) {
            this.a = s1Var;
            this.b = countDownLatch;
        }

        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
            ha.this.a(s1Var, this.b);
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) {
            if (s1Var == this.a) {
                ha.this.a(s1Var, this.b);
            }
        }
    }

    public static synchronized ha a() {
        if (d == null) {
            d = new ha();
        }
        return d;
    }

    public final void a(s1 s1Var, CountDownLatch countDownLatch) {
        String str;
        try {
            String str2 = a;
            String strD = s1Var.d();
            if (TextUtils.isEmpty(strD)) {
                String strC = s1Var.c();
                if (strC != null) {
                    byte b2 = -1;
                    int iHashCode = strC.hashCode();
                    if (iHashCode != 1390186) {
                        if (iHashCode == 1390187 && strC.equals(r5.h)) {
                            b2 = 1;
                        }
                    } else if (strC.equals(r5.g)) {
                        b2 = 0;
                    }
                    if (b2 == 0) {
                        str = b;
                    } else if (b2 == 1) {
                        str = c;
                    }
                    str2 = str;
                }
                strD = str2;
            }
            a(strD, countDownLatch);
        } catch (Exception e) {
        }
    }

    public void a(Object obj) {
        if (!(obj instanceof HashMap)) {
            obj.getClass();
        } else {
            HashMap map = (HashMap) obj;
            a((String) map.get("message_type"), (String) map.get("protol_type"), (String) map.get("body"), (CountDownLatch) map.get("latch"));
        }
    }

    public final void a(String str, String str2, String str3, CountDownLatch countDownLatch) {
        if (l2.g().b().b()) {
            x9.a(r5.e);
            a(c, countDownLatch);
        } else if (l2.g().b().f()) {
            x9.a(r5.f);
            a(c, countDownLatch);
        } else {
            s1 s1Var = new s1(str3, y1.b(str2), str2, str, false);
            j1.c(new a(s1Var, countDownLatch), str2);
            y9.b(s1Var);
        }
    }

    public final void a(String str, CountDownLatch countDownLatch) {
        HashMap map = new HashMap();
        map.put("response", str);
        map.put("latch", countDownLatch);
        m5.a().a(map, m5.b.C0005b.d);
    }
}
