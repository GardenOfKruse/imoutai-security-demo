package com.coralline.sea;

import android.os.Handler;
import android.os.HandlerThread;
import com.coralline.sea.m5;
import java.util.ArrayList;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class n6 {
    public static n6 c = null;
    public static String d = "";
    public Handler a;
    public HandlerThread b;

    public static class a implements Runnable {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public boolean f;

        public a(s1 s1Var) {
            try {
                s1Var.b();
                this.a = s1Var.a;
                this.b = new JSONObject(s1Var.b).getString("protol_type");
                this.c = s1Var.b;
                this.d = s1Var.d;
                this.e = s1Var.c;
                this.f = false;
            } catch (Exception e) {
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0034  */
        /* JADX WARN: Removed duplicated region for block: B:68:0x0152  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            byte b;
            byte b2;
            s1 s1Var;
            s1 s1Var2;
            ArrayList<s1> arrayList = new ArrayList();
            try {
                JSONObject jSONObject = new JSONObject(this.a);
                String str = this.b;
                int iHashCode = str.hashCode();
                if (iHashCode != -1573468565) {
                    b = (iHashCode == 731949878 && str.equals(p8.h)) ? (byte) 1 : (byte) -1;
                } else if (str.equals(g9.b)) {
                    b = 0;
                }
                if (b != 0) {
                    if (b != 1) {
                        s1Var2 = new s1(jSONObject.toString(), y1.b(this.b), this.d, this.e, this.f);
                    } else {
                        if (jSONObject.has(q8.b)) {
                            JSONObject jSONObject2 = new JSONObject();
                            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(q8.b);
                            if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 0) {
                                JSONObject jSONObject3 = jSONArrayOptJSONArray.getJSONObject(0);
                                jSONObject2.put("hit_plot", jSONObject3.optBoolean("mock_location") || jSONObject3.optBoolean("usb_debug") || jSONObject3.optBoolean("tcp_adb"));
                            }
                            jSONObject2.put(q8.b, jSONObject.optJSONArray(q8.b));
                            arrayList.add(new s1(jSONObject2.toString(), y1.b(q8.b), this.d, this.e, this.f));
                        }
                        if (jSONObject.has(q8.a)) {
                            JSONObject jSONObject4 = new JSONObject();
                            jSONObject4.put(q8.a, jSONObject.optJSONArray(q8.a));
                            s1Var2 = new s1(jSONObject4.toString(), y1.b(q8.a), this.d, this.e, this.f);
                        }
                    }
                    arrayList.add(s1Var2);
                } else {
                    JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("data");
                    if (jSONArrayOptJSONArray2 != null) {
                        s1 s1Var3 = null;
                        for (int i = 0; i < jSONArrayOptJSONArray2.length(); i++) {
                            JSONObject jSONObject5 = jSONArrayOptJSONArray2.optJSONObject(i) == null ? new JSONObject() : jSONArrayOptJSONArray2.optJSONObject(i);
                            String strOptString = jSONObject5.optString("protol_type", c7.c);
                            int iHashCode2 = strOptString.hashCode();
                            if (iHashCode2 != 109757538) {
                                if (iHashCode2 != 1249469840) {
                                    if (iHashCode2 != 1336193813) {
                                        b2 = (iHashCode2 == 1377276961 && strOptString.equals(g9.f)) ? (byte) 0 : (byte) -1;
                                    } else if (strOptString.equals("emulator")) {
                                        b2 = 2;
                                    }
                                } else if (strOptString.equals(g9.e)) {
                                    b2 = 1;
                                }
                            } else if (strOptString.equals(g9.c)) {
                                b2 = 3;
                            }
                            switch (b2) {
                                case 0:
                                    s1Var = new s1(jSONObject5.toString(), y1.b(g9.f), this.d, this.e, this.f);
                                    break;
                                case 1:
                                    s1Var = new s1(jSONObject5.toString(), y1.b(g9.e), this.d, this.e, this.f);
                                    break;
                                case 2:
                                    s1Var = new s1(jSONObject5.toString(), y1.b("emulator"), this.d, this.e, this.f);
                                    break;
                                case 3:
                                    s1Var3 = new s1(jSONObject5.toString(), y1.b(g9.c), this.d, this.e, this.f);
                                    continue;
                                    break;
                                default:
                                    continue;
                                    break;
                            }
                            arrayList.add(s1Var);
                        }
                        if (s1Var3 != null) {
                            arrayList.add(s1Var3);
                        }
                    }
                }
                for (s1 s1Var4 : arrayList) {
                    m5.a().a(s1Var4.a(true), m5.b.C0005b.b);
                    t7.a().b(s1Var4);
                    n6.b(s1Var4);
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public n6() {
        HandlerThread handlerThread = new HandlerThread("offline_callback");
        this.b = handlerThread;
        handlerThread.start();
        this.a = new Handler(this.b.getLooper());
    }

    public static synchronized n6 a() {
        if (c == null) {
            c = new n6();
        }
        return c;
    }

    public static boolean a(String str, JSONObject jSONObject) {
        if (!"inject".equals(str)) {
            return (q8.a.equals(str) || q8.b.equals(str)) ? a(str, jSONObject, str) : b(str, jSONObject);
        }
        if (a(str, jSONObject, "binder") || a(str, jSONObject, "dlopen") || a(str, jSONObject, "hook")) {
            return true;
        }
        return false;
    }

    public static boolean a(String str, JSONObject jSONObject, String str2) throws JSONException {
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(str2);
        boolean z = false;
        z = false;
        if (jSONObjectOptJSONObject == null) {
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(str2);
            if (jSONArrayOptJSONArray != null) {
                JSONArray jSONArray = new JSONArray();
                boolean z2 = false;
                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                    if (b(str, jSONArrayOptJSONArray.optJSONObject(i))) {
                        jSONArray.put(jSONArrayOptJSONArray.optJSONObject(i));
                        z2 = true;
                    }
                }
                if (z2) {
                    jSONObject.put(str2, jSONArray);
                    return z2;
                }
                z = z2;
            }
            return z;
        }
        if (b(str, jSONObjectOptJSONObject)) {
            return true;
        }
        jSONObject.remove(str2);
        return z;
    }

    public static void b(s1 s1Var) {
        try {
            s1Var.toString();
            if (!s1Var.d.equals(p8.g) || new JSONObject(s1Var.a).optBoolean("hit_plot", true)) {
                String string = new JSONObject(s1Var.b).getString("protol_type");
                JSONObject jSONObjectB = o6.f().b(string);
                Objects.toString(jSONObjectB);
                if (jSONObjectB == null || !a(string, s1Var.a())) {
                    return;
                }
                d = ja.a(jSONObjectB, d);
            }
        } catch (Exception e) {
        }
    }

    public static boolean b(String str, JSONObject jSONObject) {
        double dDoubleValue = n3.a().y.containsKey(str) ? n3.T.y.get(str).doubleValue() : 0.0d;
        return dDoubleValue != 0.0d && (jSONObject != null ? jSONObject.optDouble("credibility", 1.0d) : 0.0d) >= dDoubleValue;
    }

    public void c(s1 s1Var) {
        this.a.post(new a(s1Var));
    }
}
