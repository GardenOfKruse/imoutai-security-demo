package com.coralline.sea;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class m1 {
    public static final String E = "TransactionRoute_1";
    public static final String b = "com.ccb.shop.view";
    public static final String c = "com.ccb.common.gps.util.location.CCBLocationUtil";
    public static final String d = "com.ccb.shop.utils.LocationUtils";
    public static final String e = "PROVINCE";
    public static final String f = "CITY";
    public static final String g = "getLocationMsg";
    public static final String h = "id";
    public static final String i = "id_card";
    public static final String j = "phone";
    public static final String k = "bank_subbranch";
    public static final String l = "CCB.NETBANK.SAFE.eSafeLib";
    public static final String m = "infoCollect_getBranchId";
    public static final String n = "infoCollect_getPhoneNum";
    public static final String o = "infoCollect_getClientNum";
    public static m1 p = null;
    public static String q = "";
    public List<String> a;
    public static JSONObject r = new JSONObject();
    public static JSONArray s = null;
    public static int t = 1;
    public static volatile boolean u = false;
    public static volatile boolean v = false;
    public static String w = null;
    public static String x = null;
    public static String y = null;
    public static String z = null;
    public static String A = null;
    public static String B = null;
    public static Object C = null;
    public static String D = null;
    public static HashMap<String, String> F = new c();

    public class a extends HashMap<String, String> {
        public a() {
            put(v9.c, v9.c);
            put("cityCode", "city_code");
            put("city", "city");
            put("district", "district");
            put("addrStr", "address");
        }
    }

    public class b extends TimerTask {
        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (!m1.v) {
                Looper.prepare();
                boolean unused = m1.v = true;
            }
            try {
                if (l2.g().b().b()) {
                    cancel();
                }
                if (m1.C == null) {
                    Object objC = q7.j(m1.l).a(n3.a().a).c();
                    m1.C = objC;
                    if (objC == null) {
                        throw new Exception();
                    }
                }
                m1.w = (String) q7.a(m1.C).b(m1.m).c();
                m1.x = (String) q7.a(m1.C).b(m1.n).c();
                m1.y = (String) q7.a(m1.C).b(m1.o).c();
                if (m1.w == null || m1.x == null || m1.y == null) {
                    return;
                }
                String str = m1.w;
                String str2 = m1.x;
                String str3 = m1.y;
                if ((m1.z != null || m1.A != null || m1.B != null) && m1.w.equals(m1.z) && m1.x.equals(m1.A) && m1.y.equals(m1.B)) {
                    throw new Exception();
                }
                m1.z = m1.w;
                m1.A = m1.x;
                m1.B = m1.y;
                m1.a(m1.w, m1.x, m1.y);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public class c extends HashMap {
        public c() {
            put("PushAppCrashInfo_sm4", m1.E);
            put("PushAppThreatLevelInfo_1_sm4", m1.E);
            put("PushAppThreatLevelInfo_2_sm4", m1.E);
            put("PushAppThreatLevelInfo_3_sm4", m1.E);
            put("PushAppThreatLevelInfo_4_sm4", m1.E);
            put("PushAppThreatLevelInfo_5_sm4", m1.E);
            put("PushAppThreatLevelInfo_6_sm4", m1.E);
            put("PushAppCrashInfo", "CrashInfo");
            put("PushAppThreatLevelInfo_1", "ThreatInfo_1");
            put("PushAppThreatLevelInfo_2", "ThreatInfo_2");
            put("PushAppThreatLevelInfo_3", "ThreatInfo_3");
            put("PushAppThreatLevelInfo_4", "ThreatInfo_4");
            put("PushAppThreatLevelInfo_5", "ThreatInfo_5");
            put("PushAppThreatLevelInfo_6", "ThreatInfo_6");
        }
    }

    public m1() {
        this.a = null;
        try {
            this.a = n3.a().l;
        } catch (Exception e2) {
        }
    }

    public static void a(String str, String str2, String str3) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", t);
            jSONObject.put(k, str);
            jSONObject.put(j, str2);
            jSONObject.put(i, str3);
            ga.a().b((Object) jSONObject);
            t++;
        } catch (Exception e2) {
        }
    }

    public static void a(JSONObject jSONObject) {
        try {
            String strJ = j().j("DEFAULT");
            if (strJ == null || c7.c.equals(strJ)) {
                return;
            }
            JSONObject jSONObject2 = new JSONObject(strJ);
            if (jSONObject2.length() > 0) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("location", jSONObject2);
                jSONObject.put("extra", jSONObject3);
            }
        } catch (Exception e2) {
        }
    }

    public static boolean a(Context context) {
        try {
            return b.equals(q) ? k() : b(context);
        } catch (Exception e2) {
            return false;
        }
    }

    public static boolean b(Context context) {
        Method method;
        try {
            Class<?> cls = Class.forName(c);
            Method method2 = cls.getMethod("getInstance", Context.class);
            if (method2 == null) {
                return false;
            }
            Object[] objArr = {context};
            Object objInvoke = null;
            Object objInvoke2 = method2.invoke(null, objArr);
            if (objInvoke2 == null || (method = cls.getMethod("getLocationModel", new Class[0])) == null) {
                return false;
            }
            for (int i2 = 6; i2 >= 0; i2--) {
                objInvoke = method.invoke(objInvoke2, new Object[0]);
                if (objInvoke != null) {
                    break;
                }
                SystemClock.sleep(1000L);
            }
            if (objInvoke == null) {
                objInvoke = method.invoke(objInvoke2, new Object[0]);
            }
            if (objInvoke == null) {
                return false;
            }
            a aVar = new a();
            Class<?> cls2 = objInvoke.getClass();
            for (String str : aVar.keySet()) {
                try {
                    Field declaredField = cls2.getDeclaredField(str);
                    declaredField.setAccessible(true);
                    Object obj = declaredField.get(objInvoke);
                    if (!(obj instanceof String) || c7.c.equals(obj)) {
                        obj = "未知";
                    }
                    r.put((String) aVar.get(str), obj);
                } catch (Exception e2) {
                }
            }
            r.put("country", "中国");
            r.put("country_code", "86");
            r.toString();
            return true;
        } catch (Exception e3) {
            return false;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static String c(Context context) {
        try {
            q = context.getPackageName();
            a(context);
            String strJ = j().j(v9.c);
            if (strJ != null) {
                return strJ;
            }
            try {
                r.put(v9.c, "未知");
            } catch (Exception e2) {
            }
            return "未知";
        } catch (Exception e3) {
            return null;
        }
    }

    public static m1 j() {
        if (p == null) {
            synchronized (m1.class) {
                if (p == null) {
                    p = new m1();
                }
            }
        }
        return p;
    }

    public static boolean k() {
        Map map = null;
        for (int i2 = 6; i2 >= 0; i2--) {
            try {
                map = (Map) q7.j(d).b(g).c();
                if (map != null && map.size() != 0) {
                    break;
                }
                SystemClock.sleep(1000L);
            } catch (Exception e2) {
                return false;
            }
        }
        if (map == null || map.size() == 0) {
            map = (Map) q7.j(d).b(g).c();
        }
        if (map != null && map.size() != 0) {
            String str = map.containsKey(e) ? (String) map.get(e) : c7.c;
            String str2 = map.containsKey(f) ? (String) map.get(f) : c7.c;
            if (TextUtils.isEmpty(str)) {
                str = "未知";
            }
            if (TextUtils.isEmpty(str2)) {
                str2 = "未知";
            }
            r.put(v9.c, str);
            r.put("cityName", str2);
            r.toString();
            return true;
        }
        return false;
    }

    public static String l(String str) {
        if (D == null) {
            D = str.equals(b) ? j7.b(j().j(v9.c)) : j7.a(j().j("city_code"));
            if (D.equals(j7.a)) {
                D = j7.b;
            }
        }
        return D;
    }

    public static boolean m() {
        return k2.d().e() || z1.g();
    }

    public static boolean n() {
        try {
            String packageName = n3.a().a.getPackageName();
            JSONObject jSONObjectA = z1.a("mobile_pool");
            if (jSONObjectA != null && jSONObjectA.has("effect_range")) {
                jSONObjectA.toString();
                JSONArray jSONArrayOptJSONArray = jSONObjectA.optJSONArray("effect_range");
                if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 0) {
                    for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                        if (packageName.equals(jSONArrayOptJSONArray.optString(i2))) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e2) {
        }
        return false;
    }

    public static void p() {
        if (u) {
            return;
        }
        new Timer("ccb-user_data").schedule(new b(), 5000L, 5000L);
        u = true;
    }

    public String a(String str, String str2) {
        return a(str, c7.c, c7.c, str2);
    }

    public String a(String str, String str2, String str3, String str4) {
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        StringBuffer stringBuffer = new StringBuffer();
        String str11 = str4 == null ? c7.c : str4;
        if (n3.a().A) {
            if (str == null) {
                str5 = "PushAppThreatLevelInfo_5_sm4";
                str6 = str5;
            }
            str6 = str;
        } else {
            if (str == null) {
                str5 = "PushAppThreatLevelInfo_5";
                str6 = str5;
            }
            str6 = str;
        }
        String str12 = str3 == null ? c7.c : str3;
        String strB = l6.b();
        if (strB == null) {
            strB = i2.b;
        }
        stringBuffer.append("&USERID=" + str12 + "&COMMPKG=" + a(str11, strB, n3.T.k, str6, null, null, null, l(q)));
        try {
            String strB2 = z0.a().b(stringBuffer.toString());
            if (n3.T.A) {
                str7 = "5000";
                str8 = "N-SDS";
                str9 = "03";
                str10 = "20220527";
            } else {
                str7 = "0760";
                str8 = "com.test.monitor";
                str9 = "01";
                str10 = "1.0";
            }
            return a(str7, str8, str9, str10, str6, c7.c, strB2, c7.c, c7.c, c7.c);
        } catch (Exception e2) {
            return null;
        }
    }

    public final String a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        JSONObject jSONObject = new JSONObject();
        if (str == null) {
            str = c7.c;
        }
        if (str2 == null) {
            str2 = c7.c;
        }
        if (str3 == null) {
            str3 = c7.c;
        }
        if (str4 == null) {
            str4 = c7.c;
        }
        if (str5 == null) {
            str5 = c7.c;
        }
        if (str6 == null) {
            str6 = c7.c;
        }
        if (str7 == null) {
            str7 = c7.c;
        }
        if (str8 == null) {
            str8 = c7.c;
        }
        try {
            if ("GetFewAppConf_sm4".equals(str4) || "GetFewAppConf".equals(str4)) {
                str4 = "KeepAlive";
                str7 = str;
            }
            jSONObject.put("SYSTEM_TIME", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            jSONObject.put("HARDWARESN", str2);
            jSONObject.put("Stm_Chnl_ID", str3);
            jSONObject.put("Stm_Chnl_Txn_CD", str4);
            if ("GetAppConf".equals(str4) || "GetAppConf_sm4".equals(str4)) {
                jSONObject.put("Ext_Stm_Only1_Ind", n3.a().k);
                jSONObject.put("Usr_Inf_Dsc", str);
            } else {
                jSONObject.put("base64_ECD_Txn_Inf", str);
                jSONObject.put("base64_Ecrp_Txn_Inf", str5);
                jSONObject.put("Usr_Inf_Dsc", str6);
            }
            jSONObject.put("Eqmt_Inf_Dsc", str7);
            jSONObject.put("Aflt_Inf_Dsc", str8);
        } catch (JSONException e2) {
        }
        jSONObject.toString();
        return Base64.encodeToString(jSONObject.toString().getBytes(), 0);
    }

    public final String a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str == null) {
            str = c7.c;
        }
        if (str2 == null) {
            str2 = c7.c;
        }
        if (str3 == null) {
            str3 = c7.c;
        }
        if (str4 == null) {
            str4 = c7.c;
        }
        if (str5 == null) {
            str5 = c7.c;
        }
        if (str6 == null) {
            str6 = c7.c;
        }
        if (str7 == null) {
            str7 = c7.c;
        }
        if (str8 == null) {
            str8 = c7.c;
        }
        if (str9 == null) {
            str9 = c7.c;
        }
        if (str10 == null) {
            str10 = c7.c;
        }
        stringBuffer.append("SYS_CODE=".concat(str));
        stringBuffer.append("&APP_NAME=".concat(str2));
        stringBuffer.append("&MP_CODE=".concat(str3));
        stringBuffer.append("&SEC_VERSION=".concat(str4));
        stringBuffer.append("&TXCODE=".concat(str5));
        stringBuffer.append("&BRANCHID=".concat(str6));
        stringBuffer.append("&ccbParam=".concat(str7));
        stringBuffer.append("&Rmrk_1_Rcrd_Cntnt=".concat(str8));
        stringBuffer.append("&Rmrk_2_Rcrd_Cntnt=".concat(str9));
        stringBuffer.append("&Rmrk_3_Rcrd_Cntnt=".concat(str10));
        return stringBuffer.toString();
    }

    public final String g(String str) {
        if (F.containsKey(str)) {
            str = F.get(str);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(n3.a().A ? "TXCODE=PushAppThreatLevelInfo_1_sm4&" : "TXCODE=PushAppQuery&");
        stringBuffer.append("BRANCHNO=123000000&DEVICETAG=123&");
        stringBuffer.append("LOCATION=" + l(q) + "&");
        stringBuffer.append("CLIENT_NAME=" + n3.T.k + "&");
        stringBuffer.append("MOBILE_MAFT=&OS_VERSION=&");
        stringBuffer.append("ccbParam=" + str + "&");
        stringBuffer.append("Rmrk_1_Rcrd_Cntnt=");
        return stringBuffer.toString();
    }

    public synchronized boolean h(String str) {
        String strK;
        if (m()) {
            return true;
        }
        try {
            strK = k(str);
        } catch (Exception e2) {
        }
        if (strK == null) {
            c1.a().a(false);
            return false;
        }
        List<String> list = this.a;
        if (list != null && list.size() > 0) {
            String strL = l();
            Iterator<String> it = this.a.iterator();
            while (it.hasNext()) {
                byte[] bArrA = i4.a().a(it.next() + strL, g(strK).getBytes());
                if (bArrA != null) {
                    new String(bArrA);
                    JSONObject jSONObject = new JSONObject(new String(bArrA));
                    if (jSONObject.has("Res_Rtn_Code") && jSONObject.getString("Res_Rtn_Code").equals(j7.b)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String i() {
        try {
            JSONObject jSONObject = new JSONObject(v1.a(n3.a().o));
            return n3.T.A ? jSONObject.has("customer_route_suffix_sm4") ? jSONObject.getString("customer_route_suffix_sm4") : "/NCCB/CCBCommonTXRoute" : jSONObject.has("customer_route_suffix") ? jSONObject.getString("customer_route_suffix") : "/NCCB/CCBCommonTXRoute";
        } catch (Exception e2) {
            return "/NCCB/CCBCommonTXRoute";
        }
    }

    public String i(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (j7.b.equals(jSONObject.optString("Res_Rtn_Code", c7.c)) && !TextUtils.isEmpty(jSONObject.optString("Ret_Enc_Inf", c7.c))) {
                String strA = z0.a().a(jSONObject.getString("Ret_Enc_Inf"));
                return !strA.contains("base64_ECD_Txn_Inf") ? strA : new JSONObject(strA).optString("base64_ECD_Txn_Inf", null);
            }
        } catch (Exception e2) {
        }
        return null;
    }

    public String j(String str) {
        try {
            if ("DEFAULT".equals(str)) {
                return r.toString();
            }
            if (r.has(str)) {
                return r.getString(str);
            }
            return null;
        } catch (JSONException e2) {
            e2.toString();
            return null;
        }
    }

    public String k(String str) {
        if (str != null && !c7.c.equals(str)) {
            try {
                String string = new JSONObject(str).getString("protol_type");
                boolean z2 = false;
                for (int i2 = 0; i2 < s.length(); i2++) {
                    JSONObject jSONObject = s.getJSONObject(i2);
                    JSONArray jSONArray = jSONObject.getJSONArray("list");
                    int i3 = 0;
                    while (true) {
                        if (i3 >= jSONArray.length()) {
                            break;
                        }
                        if (jSONArray.getString(i3).equalsIgnoreCase(string)) {
                            z2 = true;
                            break;
                        }
                        i3++;
                    }
                    if (z2) {
                        String strOptString = jSONObject.optString("txcode", null);
                        if (!n3.a().A) {
                            return (strOptString == null || !strOptString.endsWith("_sm4")) ? strOptString : strOptString.replace("_sm4", c7.c);
                        }
                        if (strOptString == null || strOptString.endsWith("_sm4")) {
                            return strOptString;
                        }
                        return strOptString + "_sm4";
                    }
                }
            } catch (Exception e2) {
            }
        }
        return null;
    }

    public final String l() {
        try {
            JSONObject jSONObject = new JSONObject(v1.a(n3.a().o));
            return n3.T.A ? jSONObject.has("query_route_suffix_sm4") ? jSONObject.getString("query_route_suffix_sm4") : "/NCCB/CCBQueryRoute" : jSONObject.has("query_route_suffix") ? jSONObject.getString("query_route_suffix") : "/NCCB/CCBQueryRoute";
        } catch (Exception e2) {
            return "/NCCB/CCBQueryRoute";
        }
    }

    public JSONObject o() {
        try {
            JSONObject jSONObjectB = z1.b("upload_policy");
            if (jSONObjectB == null || jSONObjectB.length() <= 0) {
                return null;
            }
            jSONObjectB.toString();
            s = jSONObjectB.has("mapping") ? jSONObjectB.getJSONArray("mapping") : new JSONArray();
            return jSONObjectB;
        } catch (JSONException e2) {
            return null;
        }
    }
}
