package com.coralline.sea;

import com.coralline.sea.r1;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class w6 extends t6 {
    public static final String a = "page_hijack";
    public static String b = "###PD###";
    public static boolean c = false;
    public static boolean d = true;
    public static String e = "";
    public static double f = 3.5d;
    public static int g = 80;

    public w6() {
        super(a);
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        JSONObject jSONObjectA = z1.a(a);
        if (jSONObjectA == null || jSONObjectA.length() <= 0) {
            return;
        }
        c = jSONObjectA.optBoolean(t1.b, false);
        String strOptString = jSONObjectA.optString("toast_msg", c7.c);
        e = strOptString;
        if (strOptString.length() == 0) {
            e = ja.a(n3.a().a, n3.T.a.getPackageName()) + "已进入后台运行";
        }
        f = jSONObjectA.optDouble(r1.k.e, 3.5d);
        int iOptInt = jSONObjectA.optInt("site_type", 2);
        if (iOptInt == 0) {
            g = 48;
        }
        if (1 == iOptInt) {
            g = 17;
        }
        if (2 == iOptInt) {
            g = 80;
        }
    }
}
