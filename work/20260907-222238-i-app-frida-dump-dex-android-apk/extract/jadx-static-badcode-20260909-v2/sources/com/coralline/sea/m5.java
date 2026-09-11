package com.coralline.sea;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import java.util.Map;
import java.util.Objects;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class m5 {
    public static m5 e;
    public Handler a;
    public Handler b;
    public a c;
    public boolean d;

    public class a implements Handler.Callback {
        public a() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(@NonNull Message message) {
            int i = message.what;
            if (i == 512) {
                q3.b().a(message.obj);
                return false;
            }
            switch (i) {
                case 100:
                    ga.a().b(message.obj);
                    break;
                case 101:
                    ga.a().c(message.obj);
                    break;
                case b.a.c /* 102 */:
                    ha.a().a(message.obj);
                    break;
                case b.a.d /* 103 */:
                    ga.a().a(message.obj);
                    break;
                default:
                    switch (i) {
                        case b.a.e /* 500 */:
                            s5.b(message.obj);
                            break;
                        case b.a.f /* 501 */:
                            s5.a(message.obj);
                            break;
                        default:
                            switch (i) {
                                case b.a.h /* 503 */:
                                    w6.d = ((Boolean) message.obj).booleanValue();
                                    break;
                                case b.a.i /* 504 */:
                                    Context context = (Context) message.obj;
                                    if (context instanceof Activity) {
                                        e0.a((Activity) context);
                                    }
                                    break;
                                case b.a.j /* 505 */:
                                    f8.c().a();
                                    break;
                                default:
                                    switch (i) {
                                        case b.a.k /* 508 */:
                                            y1.a(message.obj);
                                            break;
                                        case b.a.l /* 509 */:
                                            com.coralline.sea.checkers.a.c().f();
                                            break;
                                        case b.a.m /* 510 */:
                                            y1.a((JSONObject) message.obj);
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
            return false;
        }
    }

    public static class b {
        public static final int A = 29;
        public static final int B = 30;
        public static final int C = 31;
        public static final int D = 32;
        public static final int E = 33;
        public static final int F = 34;
        public static final int G = 35;
        public static final int H = 36;
        public static final int I = 37;
        public static final int J = 38;
        public static final int a = 1;
        public static final int b = 2;
        public static final int c = 3;
        public static final int d = 4;
        public static final int e = 5;
        public static final int f = 6;
        public static final int g = 7;
        public static final int h = 10;
        public static final int i = 11;
        public static final int j = 12;
        public static final int k = 13;
        public static final int l = 14;
        public static final int m = 15;
        public static final int n = 16;
        public static final int o = 17;
        public static final int p = 18;
        public static final int q = 19;
        public static final int r = 20;
        public static final int s = 21;
        public static final int t = 22;
        public static final int u = 23;
        public static final int v = 24;
        public static final int w = 25;
        public static final int x = 26;
        public static final int y = 27;
        public static final int z = 28;

        public static class a {
            public static final int a = 100;
            public static final int b = 101;
            public static final int c = 102;
            public static final int d = 103;
            public static final int e = 500;
            public static final int f = 501;
            public static final int g = 502;
            public static final int h = 503;
            public static final int i = 504;
            public static final int j = 505;
            public static final int k = 508;
            public static final int l = 509;
            public static final int m = 510;
            public static final int n = 512;
        }

        /* JADX INFO: renamed from: com.coralline.sea.m5$b$b, reason: collision with other inner class name */
        public static class C0005b {
            public static final int a = 200;
            public static final int b = 201;
            public static final int c = 202;
            public static final int d = 203;
            public static final int e = 204;
            public static final int f = 205;
            public static final int g = 206;
            public static final int h = 207;
            public static final int i = 210;
            public static final int j = 211;
        }
    }

    public m5() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = false;
    }

    public m5(Looper looper, Handler handler) {
        this.a = handler;
        this.c = new a();
        this.b = new Handler(looper, this.c);
        this.d = true;
    }

    public static synchronized m5 a() {
        if (e == null) {
            e = new m5();
        }
        return e;
    }

    public static void a(Context context) {
        l5.a().a(context);
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            k7.a(activity);
            e0.a(activity);
        } else {
            k7.e();
        }
        n3.U = context;
    }

    public static boolean a(Map<Integer, Object> map) {
        try {
            if (e != null) {
                return false;
            }
            e = new m5((Looper) map.get(4), (Handler) map.get(5));
            n3.a(map, false);
            n3.T.k();
            n3.T.j();
            m8.c();
            if (j2.b("suspendMainEntryStart") || !v5.b()) {
                return false;
            }
            e.b();
            JSONObject jSONObjectC = n3.T.c();
            Objects.toString(jSONObjectC);
            if (jSONObjectC != null) {
                ga.a().d(jSONObjectC);
            }
        } catch (Exception e2) {
            x9.a("-201#" + e2.getMessage());
        }
        return false;
    }

    public void a(Object obj, int i) {
        if (this.d) {
            this.a.sendMessage(this.a.obtainMessage(i, obj));
        }
    }

    public final void b() {
        a(this.b, b.C0005b.a);
    }
}
