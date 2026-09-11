package com.coralline.sea;

import com.coralline.sea.m5;
import com.coralline.sea.s1;
import java.util.HashMap;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class fa {

    public class a extends HashMap<String, String> {
        public final /* synthetic */ String a;
        public final /* synthetic */ String b;
        public final /* synthetic */ String c;
        public final /* synthetic */ String d;

        public a(String str, String str2, String str3, String str4) {
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = str4;
            put("title", str);
            put(s1.a.a, str2);
            put("action", str3);
            put("source", str4);
        }
    }

    public class b extends HashMap<String, String> {
        public final /* synthetic */ String a;
        public final /* synthetic */ String b;
        public final /* synthetic */ String c;
        public final /* synthetic */ String d;
        public final /* synthetic */ String e;
        public final /* synthetic */ String f;

        public b(String str, String str2, String str3, String str4, String str5, String str6) {
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = str4;
            this.e = str5;
            this.f = str6;
            put("title", str);
            put(s1.a.a, str2);
            put("action", str3);
            put("source", str4);
            put("type", str5);
            put("userScenario", str6);
            put("isOnDemand", "1");
        }
    }

    public static void a(String str, String str2, String str3, String str4) {
        if (n3.a().q) {
            q6.g.a(str, str2, str3);
        } else {
            m5.a().a(new a(str, str2, str3, str4), m5.b.C0005b.c);
        }
    }

    public static void a(String str, String str2, String str3, String str4, String str5, String str6) {
        if (n3.a().q) {
            q6.g.a(str, str2, str3);
        } else {
            m5.a().a(new b(str, str2, str3, str4, str5, str6), m5.b.C0005b.c);
        }
    }
}
