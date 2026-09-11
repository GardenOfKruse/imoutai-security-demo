package com.coralline.sea;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.coralline.sea.m5;
import java.util.Arrays;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class h5 extends x6 {
    public static String d = "";
    public c b;
    public boolean c;

    public class a implements l4 {
        public a() {
        }

        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) throws Throwable {
            if (s1Var.c.equals(e2.b) && !(s1Var instanceof h6)) {
                try {
                    if (s1Var.a().getLong("start_id") == ja.o()) {
                        h5.this.b.b();
                    }
                } catch (Exception e) {
                }
            }
            if (s1Var.c.equals(e2.d)) {
                return;
            }
            h5.b(s1Var);
        }
    }

    public static class b implements l4 {
        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) throws Throwable {
            h5.b(s1Var);
        }
    }

    public class c implements Handler.Callback {
        public final int e;
        public final int f;
        public final int g;
        public Handler h;
        public Boolean i;

        @NonNull
        public final int[] j;
        public final int a = 100;
        public final int b = 101;
        public final int c = m5.b.a.c;
        public final int d = m5.b.a.d;
        public int k = 0;

        public c(int i, int i2, int i3, int[] iArr) {
            StringBuilder sb;
            this.e = i;
            this.f = i2;
            this.g = i3;
            this.j = iArr == null ? new int[0] : iArr;
            if (h5.this.c) {
                sb = new StringBuilder("no event mode(no_event_period : ");
                sb.append(i);
            } else {
                sb = new StringBuilder("normal mode(query_window : ");
                sb.append(i2);
                sb.append(", query_frequency : ");
                sb.append(i3);
                sb.append(" , intervals: ");
                sb.append(Arrays.toString(iArr));
            }
            sb.append(")");
            this.i = Boolean.FALSE;
            HandlerThread handlerThread = new HandlerThread("HandlerThread#ka");
            handlerThread.start();
            this.h = new Handler(handlerThread.getLooper(), this);
        }

        public void a() {
            if (!h5.this.c || this.i.booleanValue()) {
                return;
            }
            this.i = Boolean.TRUE;
            this.h.sendEmptyMessage(100);
        }

        public void b() {
            if (h5.this.c) {
                return;
            }
            this.h.sendEmptyMessage(101);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            Handler handler;
            long j;
            int i;
            int i2 = message.what;
            int i3 = m5.b.a.d;
            switch (i2) {
                case 100:
                    h5.this.c();
                    handler = this.h;
                    j = this.e * 1000;
                    i3 = 100;
                    handler.sendEmptyMessageDelayed(i3, j);
                    break;
                case 101:
                    this.h.removeMessages(m5.b.a.c);
                    this.h.removeMessages(101);
                    this.h.removeMessages(m5.b.a.d);
                    int[] iArr = this.j;
                    if (iArr.length <= 0) {
                        this.h.sendEmptyMessageDelayed(m5.b.a.d, this.g * 1000);
                        this.h.sendEmptyMessageDelayed(m5.b.a.c, this.f * 1000);
                    } else {
                        this.k = 0;
                        i = iArr[0];
                        this.h.sendEmptyMessageDelayed(m5.b.a.d, ((long) i) * 1000);
                    }
                    break;
                case m5.b.a.c /* 102 */:
                    this.h.removeMessages(m5.b.a.d);
                    break;
                case m5.b.a.d /* 103 */:
                    h5.this.c();
                    int[] iArr2 = this.j;
                    if (iArr2.length <= 0) {
                        handler = this.h;
                        j = this.g * 1000;
                        handler.sendEmptyMessageDelayed(i3, j);
                    } else {
                        int i4 = this.k + 1;
                        this.k = i4;
                        if (i4 < iArr2.length) {
                            i = iArr2[i4];
                            this.h.sendEmptyMessageDelayed(m5.b.a.d, ((long) i) * 1000);
                        }
                    }
                    break;
            }
            return true;
        }
    }

    public h5() {
        super(e2.d, 15);
    }

    public static JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            String strA = a9.a(c2.b, (String) null);
            if (!TextUtils.isEmpty(strA)) {
                jSONObject.put(c2.b, strA);
            }
            String strA2 = a9.a(c2.c, (String) null);
            if (!TextUtils.isEmpty(strA2)) {
                jSONObject.put(c2.c, strA2);
            }
            String strA3 = a9.a(c2.d, (String) null);
            if (!TextUtils.isEmpty(strA3)) {
                jSONObject.put(c2.d, strA3);
            }
            jSONObject.put(c2.a, true);
            return jSONObject;
        } catch (JSONException e) {
            return jSONObject;
        }
    }

    public static void b(s1 s1Var) throws Throwable {
        try {
            JSONObject jSONObject = new JSONObject(s1Var.d());
            if (jSONObject.optLong("status") != 0) {
                return;
            }
            if (jSONObject.has("instruction_v493")) {
                Objects.toString(jSONObject.getJSONObject("instruction_v493"));
                d = ja.a(jSONObject, d);
            }
            if (jSONObject.has("global")) {
                l2.g().b().a(jSONObject.getBoolean("global"));
            }
            if (jSONObject.has(t1.b)) {
                JSONArray jSONArray = jSONObject.getJSONArray(t1.b);
                jSONArray.toString();
                for (int i = 0; i < jSONArray.length(); i++) {
                    com.coralline.sea.checkers.a.c().a(jSONArray.get(i).toString());
                }
            }
            if (jSONObject.has("invalid_udid")) {
                aa.f().g();
            }
            b(jSONObject);
            b9.a(jSONObject);
        } catch (Exception e) {
        }
    }

    public static void b(JSONObject jSONObject) {
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("configuration");
        if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.length() > 0) {
            z1.a(jSONObjectOptJSONObject);
        }
        JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject(x9.h);
        if (jSONObjectOptJSONObject2 == null || jSONObjectOptJSONObject2.length() <= 0) {
            return;
        }
        k5.a().a(jSONObjectOptJSONObject2);
    }

    @NonNull
    public final int[] a(@Nullable JSONObject jSONObject) {
        if (jSONObject == null || jSONObject.length() == 0) {
            return new int[0];
        }
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("intervals");
        if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() == 0) {
            return new int[0];
        }
        int[] iArr = new int[jSONArrayOptJSONArray.length()];
        for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
            iArr[i] = jSONArrayOptJSONArray.optInt(i);
        }
        return iArr;
    }

    public final c b() {
        try {
            JSONObject jSONObjectA = z1.a(this.checkerName);
            int i = jSONObjectA.getInt("no_event_period");
            int i2 = jSONObjectA.getInt("query_window");
            int i3 = jSONObjectA.getInt("query_frequency");
            if (i3 <= i2) {
                return new c(i, i2, i3, a(jSONObjectA));
            }
            throw new Exception("query_frequency is smaller than query_window");
        } catch (Exception e) {
            e.toString();
            return new c(3600, 120, 15, null);
        }
    }

    public final void c() {
        try {
            push(e2.d, e2.d, a().toString());
        } catch (Exception e) {
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        this.b.a();
    }

    @Override // com.coralline.sea.checkers.Checker
    public void initialize() {
        super.initialize();
        this.c = !l2.g().b().c();
        this.b = b();
        j1.c(new a(), null);
    }
}
