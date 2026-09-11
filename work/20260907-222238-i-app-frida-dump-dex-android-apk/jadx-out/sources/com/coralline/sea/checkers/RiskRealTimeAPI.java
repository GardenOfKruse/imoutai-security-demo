package com.coralline.sea.checkers;

import android.text.TextUtils;
import androidx.annotation.Keep;
import com.coralline.sea.b6;
import com.coralline.sea.d;
import com.coralline.sea.d0;
import com.coralline.sea.e;
import com.coralline.sea.e2;
import com.coralline.sea.f5;
import com.coralline.sea.f6;
import com.coralline.sea.f8;
import com.coralline.sea.fa;
import com.coralline.sea.g2;
import com.coralline.sea.g8;
import com.coralline.sea.g9;
import com.coralline.sea.h1;
import com.coralline.sea.h2;
import com.coralline.sea.h4;
import com.coralline.sea.i3;
import com.coralline.sea.j4;
import com.coralline.sea.k4;
import com.coralline.sea.la;
import com.coralline.sea.m8;
import com.coralline.sea.ma;
import com.coralline.sea.n3;
import com.coralline.sea.n9;
import com.coralline.sea.na;
import com.coralline.sea.p8;
import com.coralline.sea.q8;
import com.coralline.sea.s1;
import com.coralline.sea.t1;
import com.coralline.sea.t5;
import com.coralline.sea.w7;
import com.coralline.sea.y1;
import com.coralline.sea.z1;
import com.coralline.sea.z7;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
@Keep
public class RiskRealTimeAPI {
    private static final String TAG = "RiskRealTimeAPI";
    private static final Pattern VERSION_TIME_PATTERN = Pattern.compile(".*[-._](\\d{7,})(?:[-._].*)?$");
    private static Map<String, String> keys = new HashMap();
    private static String KEY_DETAIL = "detail";
    public static String KEY_USERSENC = "userlocalscenariodata";
    private static String KEY_ACTION = "action";
    private static String KEY_CHECKED = "checked";
    private static String TYPE_WINDOWS11 = "emulator_win11";
    public static String checkerName = "startInfo";

    private static JSONObject buildCommonResult(boolean z, Object obj) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("client_time", System.currentTimeMillis());
            jSONObject.put("checked", z);
            if (obj != null) {
                jSONObject.put(KEY_DETAIL, obj);
                return jSONObject;
            }
        } catch (JSONException e) {
            e.toString();
        }
        return jSONObject;
    }

    private static JSONObject buildFailResult() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("client_time", System.currentTimeMillis());
            jSONObject.put("checked", false);
            return jSONObject;
        } catch (JSONException e) {
            e.toString();
            return jSONObject;
        }
    }

    public static JSONObject checkAccessibilityAppsStatus(JSONObject jSONObject) {
        JSONObject jSONObjectOptJSONObject;
        JSONObject jSONObject2 = new JSONObject();
        try {
            if (!t1.d(d.c)) {
                return null;
            }
            jSONObject2.put("client_time", System.currentTimeMillis());
            boolean z = false;
            jSONObject2.put("checked", false);
            JSONObject jSONObjectA = d.c().a();
            uploadRiskInfo(jSONObjectA, jSONObject, d.c, null);
            if (jSONObjectA != null && (jSONObjectOptJSONObject = jSONObjectA.optJSONObject("detail")) != null) {
                JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("accessibility_service");
                if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 0) {
                    z = true;
                }
                jSONObject2.put("checked", z);
                if (z) {
                    jSONObject2.put(KEY_DETAIL, jSONArrayOptJSONArray);
                    return jSONObject2;
                }
            }
        } catch (Exception e) {
            e.toString();
        }
        return jSONObject2;
    }

    public static JSONObject checkAccessibilityStatus(JSONObject jSONObject) {
        JSONObject jSONObjectOptJSONObject;
        JSONObject jSONObject2 = new JSONObject();
        try {
            if (!t1.d(e.c)) {
                return null;
            }
            jSONObject2.put("client_time", System.currentTimeMillis());
            jSONObject2.put("checked", false);
            JSONObject jSONObjectA = e.b().a();
            uploadRiskInfo(jSONObjectA, jSONObject, e.c, null);
            if (jSONObjectA != null && (jSONObjectOptJSONObject = jSONObjectA.optJSONObject("detail")) != null) {
                boolean z = jSONObjectOptJSONObject.optInt("is_accessibility_enabled", 0) != 0;
                jSONObject2.put("checked", z);
                if (z) {
                    jSONObject2.put(KEY_DETAIL, new JSONArray().put(0, jSONObjectOptJSONObject));
                    return jSONObject2;
                }
            }
        } catch (Exception e) {
            e.toString();
        }
        return jSONObject2;
    }

    public static JSONObject checkAutoClickStatus(JSONObject jSONObject) {
        JSONObject jSONObjectOptJSONObject;
        JSONObject jSONObject2 = new JSONObject();
        try {
            if (!t1.d("auto_click")) {
                return null;
            }
            jSONObject2.put("client_time", System.currentTimeMillis());
            jSONObject2.put("checked", false);
            JSONObject jSONObjectA = d0.b().a();
            uploadRiskInfo(jSONObjectA, jSONObject, "auto_click", null);
            if (jSONObjectA != null && (jSONObjectOptJSONObject = jSONObjectA.optJSONObject("detail")) != null) {
                boolean zOptBoolean = jSONObjectOptJSONObject.optBoolean("is_auto_click_on_touch", false);
                jSONObject2.put("checked", zOptBoolean);
                if (zOptBoolean) {
                    jSONObject2.put(KEY_DETAIL, new JSONArray().put(0, jSONObjectOptJSONObject));
                    return jSONObject2;
                }
            }
        } catch (Exception e) {
            e.toString();
        }
        return jSONObject2;
    }

    public static JSONObject checkCallStatus(JSONObject jSONObject) {
        try {
            if (!t1.d(h1.c)) {
                return null;
            }
            JSONObject jSONObjectA = h1.b.a.a(jSONObject);
            if (jSONObjectA != null && jSONObjectA.has(KEY_DETAIL)) {
                uploadRiskInfo(jSONObjectA, jSONObject, h1.d, null);
                return buildCommonResult(true, jSONObjectA.optJSONArray("detail"));
            }
        } catch (Exception e) {
            e.toString();
        }
        return buildFailResult();
    }

    public static JSONObject checkCredentialStatus(JSONObject jSONObject) {
        JSONObject jSONObject2 = null;
        try {
            if (!t1.d(g2.c)) {
                return null;
            }
            JSONObject jSONObjectA = h2.b().a();
            uploadRiskInfo(jSONObjectA, jSONObject, g2.c, null);
            JSONObject baseInfo = getBaseInfo(jSONObjectA);
            if (jSONObjectA == null) {
                return baseInfo;
            }
            try {
                if (!jSONObjectA.has("data")) {
                    return baseInfo;
                }
                baseInfo.put(KEY_DETAIL, jSONObjectA.optJSONArray("data"));
                return baseInfo;
            } catch (Exception e) {
                jSONObject2 = baseInfo;
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
        }
        e.toString();
        return jSONObject2;
    }

    public static JSONObject checkEmulatorStatus(JSONObject jSONObject) {
        JSONObject jSONObject2;
        JSONObject jSONObject3 = null;
        try {
            if (!t1.d("emulator")) {
                return null;
            }
            JSONObject jSONObjectA = i3.a();
            if (f5.a(jSONObjectA)) {
                jSONObject2 = null;
            } else {
                jSONObject2 = new JSONObject(jSONObjectA.toString());
                jSONObject2.put("invoke_method_type", "emulator");
            }
            uploadRiskInfo(jSONObject2, jSONObject, "emulator", null);
            JSONObject baseInfo = getBaseInfo(jSONObjectA);
            try {
                if (f5.a(jSONObjectA)) {
                    return baseInfo;
                }
                baseInfo.put(KEY_DETAIL, new JSONArray().put(0, jSONObjectA));
                return baseInfo;
            } catch (Exception e) {
                jSONObject3 = baseInfo;
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
        }
        e.toString();
        return jSONObject3;
    }

    public static JSONObject checkHttpProxyStatus(JSONObject jSONObject) {
        JSONObject jSONObject2 = null;
        try {
            if (!t1.d(j4.g)) {
                return null;
            }
            JSONObject jSONObjectA = h4.d().a();
            uploadRiskInfo(jSONObjectA, jSONObject, j4.g, null);
            JSONObject baseInfo = getBaseInfo(jSONObjectA);
            try {
                if (f5.a(jSONObjectA)) {
                    return baseInfo;
                }
                baseInfo.put(KEY_DETAIL, new JSONArray().put(0, jSONObjectA));
                return baseInfo;
            } catch (Exception e) {
                jSONObject2 = baseInfo;
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
        }
        e.toString();
        return jSONObject2;
    }

    public static JSONObject checkHttpsStatus(JSONObject jSONObject) {
        JSONObject jSONObject2 = null;
        try {
            if (!t1.d(k4.c)) {
                return null;
            }
            JSONObject jSONObjectB = h4.d().b();
            uploadRiskInfo(jSONObjectB, jSONObject, k4.c, null);
            JSONObject baseInfo = getBaseInfo(jSONObjectB);
            try {
                if (f5.a(jSONObjectB)) {
                    return baseInfo;
                }
                baseInfo.put(KEY_DETAIL, new JSONArray().put(0, jSONObjectB));
                return baseInfo;
            } catch (Exception e) {
                jSONObject2 = baseInfo;
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
        }
        e.toString();
        return jSONObject2;
    }

    public static String checkMagiskDeltaRiskStatus(JSONObject jSONObject) {
        if (t1.d(g9.f)) {
            return t5.d().a(n3.a().a);
        }
        return null;
    }

    public static JSONObject checkMicrophone(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            if (t1.c(b6.c)) {
                return null;
            }
            jSONObject2.put("client_time", System.currentTimeMillis());
            jSONObject2.put(KEY_CHECKED, false);
            JSONObject jSONObjectA = b6.a();
            uploadRiskInfo(jSONObjectA, jSONObject, b6.c, null);
            boolean z = jSONObjectA != null;
            jSONObject2.put(KEY_CHECKED, z);
            if (z) {
                jSONObject2.put(KEY_DETAIL, new JSONArray().put(0, jSONObjectA));
                return jSONObject2;
            }
        } catch (Exception e) {
            e.toString();
        }
        return jSONObject2;
    }

    public static JSONObject checkMultiOpenStatus(JSONObject jSONObject) {
        JSONObject jSONObject2 = null;
        try {
            if (!t1.d(g9.e)) {
                return null;
            }
            JSONObject jSONObjectB = f6.d().d(n3.a().a) ? f6.d().b(n3.T.a) : null;
            uploadRiskInfo(jSONObjectB, jSONObject, g9.e, null);
            JSONObject baseInfo = getBaseInfo(jSONObjectB);
            try {
                if (f5.a(jSONObjectB)) {
                    return baseInfo;
                }
                baseInfo.put(KEY_DETAIL, new JSONArray().put(0, jSONObjectB));
                return baseInfo;
            } catch (Exception e) {
                jSONObject2 = baseInfo;
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
        }
        e.toString();
        return jSONObject2;
    }

    public static JSONObject checkRiskFrameStatus(JSONObject jSONObject) {
        JSONObject baseInfo;
        try {
        } catch (Exception e) {
            e = e;
            baseInfo = null;
        }
        if (!t1.d(p8.g)) {
            return null;
        }
        JSONArray jSONArrayX = w7.x();
        baseInfo = getBaseInfo(jSONArrayX);
        try {
            if (!f5.a((Object) jSONArrayX)) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(q8.a, jSONArrayX);
                uploadRiskInfo(jSONObject2, jSONObject, p8.h, q8.a);
                baseInfo.put(KEY_DETAIL, jSONArrayX);
                return baseInfo;
            }
        } catch (Exception e2) {
            e = e2;
            e.toString();
        }
        return baseInfo;
        e.toString();
        return baseInfo;
    }

    public static JSONObject checkRootStatus(JSONObject jSONObject) {
        JSONObject jSONObject2 = null;
        try {
            if (!t1.d(g9.f)) {
                return null;
            }
            JSONObject jSONObjectK = z7.a(n3.a().a).k();
            if (jSONObjectK == null) {
                return new JSONObject();
            }
            uploadRiskInfo(jSONObjectK, jSONObject, g9.f, null);
            JSONObject baseInfo = getBaseInfo(jSONObjectK);
            try {
                if (f5.a(jSONObjectK)) {
                    return baseInfo;
                }
                baseInfo.put(KEY_DETAIL, new JSONArray().put(0, jSONObjectK));
                return baseInfo;
            } catch (Exception e) {
                jSONObject2 = baseInfo;
                e = e;
                e.toString();
                return jSONObject2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public static JSONObject checkScreenSharingStatus(JSONObject jSONObject) {
        JSONObject jSONObject2 = null;
        try {
            if (!t1.d("screen_sharing") || !g8.i) {
                return null;
            }
            JSONObject jSONObjectA = f8.c().a();
            uploadRiskInfo(jSONObjectA, jSONObject, "screen_sharing", null);
            JSONObject baseInfo = getBaseInfo(jSONObjectA);
            try {
                if (f5.a(jSONObjectA)) {
                    return baseInfo;
                }
                String str = KEY_DETAIL;
                baseInfo.put(str, jSONObjectA.optJSONArray(str));
                return baseInfo;
            } catch (Exception e) {
                jSONObject2 = baseInfo;
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
        }
        e.toString();
        return jSONObject2;
    }

    public static JSONObject checkSysConfStatus(JSONObject jSONObject) {
        JSONObject baseInfo;
        try {
        } catch (Exception e) {
            e = e;
            baseInfo = null;
        }
        if (!t1.d(p8.g)) {
            return null;
        }
        JSONArray jSONArrayB = n9.b();
        baseInfo = getBaseInfo(jSONArrayB);
        try {
            if (!f5.a((Object) jSONArrayB)) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(q8.b, jSONArrayB);
                uploadRiskInfo(jSONObject2, jSONObject, p8.h, q8.b);
                baseInfo.put(KEY_DETAIL, jSONArrayB);
                return baseInfo;
            }
        } catch (Exception e2) {
            e = e2;
            e.toString();
        }
        return baseInfo;
        e.toString();
        return baseInfo;
    }

    public static JSONObject checkVirtualEnvStatus(JSONObject jSONObject) {
        JSONObject baseInfo;
        try {
        } catch (JSONException e) {
            e = e;
            baseInfo = null;
        }
        if (!t1.d(ma.a)) {
            return null;
        }
        JSONArray jSONArrayA = la.a();
        baseInfo = getBaseInfo(jSONArrayA);
        try {
            if (!f5.a((Object) jSONArrayA)) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("data", jSONArrayA);
                uploadRiskInfo(jSONObject2, jSONObject, ma.a, null);
                baseInfo.put(KEY_DETAIL, jSONArrayA);
                return baseInfo;
            }
        } catch (JSONException e2) {
            e = e2;
            e.toString();
        }
        return baseInfo;
        e.toString();
        return baseInfo;
    }

    public static JSONObject checkVpnProxyStatus(JSONObject jSONObject) {
        JSONObject jSONObject2 = null;
        try {
            if (!t1.d(na.i)) {
                return null;
            }
            JSONObject jSONObjectC = h4.d().c();
            uploadRiskInfo(jSONObjectC, jSONObject, na.i, null);
            JSONObject baseInfo = getBaseInfo(jSONObjectC);
            try {
                if (f5.a(jSONObjectC)) {
                    return baseInfo;
                }
                baseInfo.put(KEY_DETAIL, new JSONArray().put(0, jSONObjectC));
                return baseInfo;
            } catch (Exception e) {
                jSONObject2 = baseInfo;
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
        }
        e.toString();
        return jSONObject2;
    }

    public static JSONObject checkWindowsEmulatorStatus(JSONObject jSONObject) {
        JSONObject baseInfo;
        try {
            if (!t1.d("emulator")) {
                return null;
            }
            JSONObject jSONObjectA = i3.a();
            Objects.toString(jSONObjectA);
            baseInfo = getBaseInfo(jSONObjectA);
            try {
                if (!f5.a(jSONObjectA)) {
                    String strOptString = jSONObjectA.optString("type");
                    if (!TextUtils.isEmpty(strOptString) && "Microsoft".equalsIgnoreCase(strOptString)) {
                        JSONObject jSONObject2 = new JSONObject(jSONObjectA.toString());
                        jSONObject2.put("invoke_method_type", "windows");
                        uploadRiskInfo(jSONObject2, jSONObject, "emulator", TYPE_WINDOWS11);
                        baseInfo.put(KEY_DETAIL, new JSONArray().put(0, jSONObjectA));
                        return baseInfo;
                    }
                }
                baseInfo.put(KEY_CHECKED, false);
                return baseInfo;
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            baseInfo = null;
        }
        e.toString();
        return baseInfo;
    }

    private static int compareMainVersion(String str, String str2) {
        int i;
        int i2;
        String[] strArrSplit = str.split("\\.");
        String[] strArrSplit2 = str2.split("\\.");
        int iMax = Math.max(strArrSplit.length, strArrSplit2.length);
        int i3 = 0;
        while (i3 < iMax) {
            String str3 = i3 < strArrSplit.length ? strArrSplit[i3] : "0";
            String str4 = i3 < strArrSplit2.length ? strArrSplit2[i3] : "0";
            try {
                i = Integer.parseInt(str3);
                i2 = Integer.parseInt(str4);
            } catch (NumberFormatException e) {
                int iCompareToIgnoreCase = str3.compareToIgnoreCase(str4);
                if (iCompareToIgnoreCase != 0) {
                    return iCompareToIgnoreCase;
                }
            }
            if (i != i2) {
                return i - i2;
            }
            i3++;
        }
        return 0;
    }

    private static int compareTimeNumbers(String str, String str2) {
        try {
            return (Long.parseLong(str) > Long.parseLong(str2) ? 1 : (Long.parseLong(str) == Long.parseLong(str2) ? 0 : -1));
        } catch (NumberFormatException e) {
            return str.compareToIgnoreCase(str2);
        }
    }

    private static int compareVersions(String str, String str2) {
        if (TextUtils.equals(str, str2)) {
            return 0;
        }
        String strExtractTimeNumber = extractTimeNumber(str);
        String strExtractTimeNumber2 = extractTimeNumber(str2);
        if (!TextUtils.isEmpty(strExtractTimeNumber) && !TextUtils.isEmpty(strExtractTimeNumber2)) {
            return compareTimeNumbers(strExtractTimeNumber, strExtractTimeNumber2);
        }
        return compareMainVersion(parseVersion(str)[0], parseVersion(str2)[0]);
    }

    private static String extractTimeNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Matcher matcher = VERSION_TIME_PATTERN.matcher(str);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    private static JSONObject geStrategyInfo(String str, String str2) {
        JSONObject jSONObjectOptJSONObject = z1.b().optJSONObject(getAlertType(str, str2));
        if (jSONObjectOptJSONObject == null) {
            return null;
        }
        JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("sdk_version_filter_rules");
        if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() <= 0) {
            if (isCurrentSdkVersionInFilter(jSONObjectOptJSONObject.optJSONArray("sdk_version_filter"))) {
                return null;
            }
        } else if (isCurrentSdkVersionInFilterRules(jSONArrayOptJSONArray)) {
            return null;
        }
        return jSONObjectOptJSONObject.optJSONObject("instruction");
    }

    private static String getAlertType(String str, String str2) {
        String str3 = g9.f.equals(str) ? "root" : str;
        if (p8.h.equals(str) && q8.a.equals(str2)) {
            str3 = q8.a;
        }
        if (p8.h.equals(str) && q8.b.equals(str2)) {
            str3 = q8.b;
        }
        return ("emulator".equals(str) && TYPE_WINDOWS11.equals(str2)) ? TYPE_WINDOWS11 : str3;
    }

    private static JSONObject getBaseInfo(Object obj) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("client_time", System.currentTimeMillis());
            jSONObject.put("checked", !f5.a(obj));
            return jSONObject;
        } catch (JSONException e) {
            return jSONObject;
        }
    }

    private static boolean isCurrentSdkVersionInFilter(JSONArray jSONArray) {
        if (jSONArray == null || jSONArray.length() == 0) {
            return false;
        }
        String str = n3.a().z;
        for (int i = 0; i < jSONArray.length(); i++) {
            if (TextUtils.equals(jSONArray.optString(i), str)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCurrentSdkVersionInFilterRules(JSONArray jSONArray) {
        if (jSONArray == null || jSONArray.length() == 0) {
            return false;
        }
        String str = n3.a().z;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
            if (jSONObjectOptJSONObject != null && matchesRule(str, jSONObjectOptJSONObject)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean matchesCondition(java.lang.String r4, java.lang.String r5, java.lang.String r6) {
        /*
            r0 = 0
            int r4 = compareVersions(r4, r6)     // Catch: java.lang.Exception -> L71
            java.lang.String r5 = r5.toLowerCase()     // Catch: java.lang.Exception -> L71
            r6 = -1
            int r1 = r5.hashCode()     // Catch: java.lang.Exception -> L71
            r2 = 3244(0xcac, float:4.546E-42)
            r3 = 1
            if (r1 == r2) goto L4e
            r2 = 3309(0xced, float:4.637E-42)
            if (r1 == r2) goto L44
            r2 = 3464(0xd88, float:4.854E-42)
            if (r1 == r2) goto L3a
            r2 = 102680(0x19118, float:1.43885E-40)
            if (r1 == r2) goto L30
            r2 = 107485(0x1a3dd, float:1.50619E-40)
            if (r1 == r2) goto L26
            goto L58
        L26:
            java.lang.String r1 = "lte"
            boolean r5 = r5.equals(r1)     // Catch: java.lang.Exception -> L71
            if (r5 == 0) goto L58
            r5 = 4
            goto L59
        L30:
            java.lang.String r1 = "gte"
            boolean r5 = r5.equals(r1)     // Catch: java.lang.Exception -> L71
            if (r5 == 0) goto L58
            r5 = 3
            goto L59
        L3a:
            java.lang.String r1 = "lt"
            boolean r5 = r5.equals(r1)     // Catch: java.lang.Exception -> L71
            if (r5 == 0) goto L58
            r5 = 1
            goto L59
        L44:
            java.lang.String r1 = "gt"
            boolean r5 = r5.equals(r1)     // Catch: java.lang.Exception -> L71
            if (r5 == 0) goto L58
            r5 = 0
            goto L59
        L4e:
            java.lang.String r1 = "eq"
            boolean r5 = r5.equals(r1)     // Catch: java.lang.Exception -> L71
            if (r5 == 0) goto L58
            r5 = 2
            goto L59
        L58:
            r5 = -1
        L59:
            switch(r5) {
                case 0: goto L6d;
                case 1: goto L69;
                case 2: goto L65;
                case 3: goto L61;
                case 4: goto L5d;
                default: goto L5c;
            }
        L5c:
            return r0
        L5d:
            if (r4 > 0) goto L60
            r0 = 1
        L60:
            return r0
        L61:
            if (r4 < 0) goto L64
            r0 = 1
        L64:
            return r0
        L65:
            if (r4 != 0) goto L68
            r0 = 1
        L68:
            return r0
        L69:
            if (r4 >= 0) goto L6c
            r0 = 1
        L6c:
            return r0
        L6d:
            if (r4 <= 0) goto L70
            r0 = 1
        L70:
            return r0
        L71:
            r4 = move-exception
            r4.getMessage()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.checkers.RiskRealTimeAPI.matchesCondition(java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    private static boolean matchesRule(String str, JSONObject jSONObject) {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("conditions");
        if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() == 0) {
            return false;
        }
        for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
            JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
            if (jSONObjectOptJSONObject != null) {
                String strOptString = jSONObjectOptJSONObject.optString("operator");
                String strOptString2 = jSONObjectOptJSONObject.optString("version");
                if (!TextUtils.isEmpty(strOptString) && !TextUtils.isEmpty(strOptString2) && !matchesCondition(str, strOptString, strOptString2)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static String[] parseVersion(String str) {
        if (TextUtils.isEmpty(str)) {
            return new String[]{"0"};
        }
        int iLastIndexOf = str.lastIndexOf(45);
        return (iLastIndexOf <= 0 || iLastIndexOf >= str.length() - 1) ? new String[]{str} : new String[]{str.substring(0, iLastIndexOf), str.substring(iLastIndexOf + 1)};
    }

    private static void push(String str, String str2) {
        JSONObject jSONObjectA = y1.a().a(str);
        if (f5.a(jSONObjectA)) {
            return;
        }
        jSONObjectA.remove("userscenariodata");
        s1 s1Var = new s1(str2, jSONObjectA.toString(), str, e2.b, true);
        s1Var.n = true;
        m8.b().b(s1Var);
    }

    public static void setScreenShare(Boolean bool) {
        if (bool == null) {
            return;
        }
        g8.i = bool.booleanValue();
    }

    private static void uploadRiskInfo(JSONObject jSONObject, JSONObject jSONObject2, String str, String str2) {
        try {
            if (!f5.a(jSONObject)) {
                jSONObject.put(KEY_USERSENC, jSONObject2 == null ? new JSONObject() : jSONObject2);
                JSONObject jSONObjectGeStrategyInfo = geStrategyInfo(str, str2);
                if (jSONObjectGeStrategyInfo != null) {
                    jSONObjectGeStrategyInfo.toString();
                }
                jSONObject.put(KEY_ACTION, jSONObjectGeStrategyInfo);
                push(str, jSONObject.toString());
                jSONObject.remove(KEY_USERSENC);
                jSONObject.remove(KEY_ACTION);
                String str3 = str2 == null ? str : str2;
                if (f5.a(jSONObjectGeStrategyInfo) || TextUtils.isEmpty(jSONObjectGeStrategyInfo.optString("action"))) {
                    return;
                }
                fa.a(jSONObjectGeStrategyInfo.optString("title"), jSONObjectGeStrategyInfo.optString(s1.a.a), jSONObjectGeStrategyInfo.optString("action"), jSONObjectGeStrategyInfo.optString("source"), str3, jSONObject2 == null ? new JSONObject().toString() : jSONObject2.toString());
            }
        } catch (Exception e) {
            e.toString();
        }
    }
}
