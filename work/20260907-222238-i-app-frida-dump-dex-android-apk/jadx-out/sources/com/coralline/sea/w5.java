package com.coralline.sea;

import android.content.Context;
import android.os.Build;
import android.os.Process;
import com.coralline.sea.k1;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class w5 {
    public static w5 b = new w5();
    public a0 a;

    public static /* synthetic */ class a {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[k1.b.values().length];
            a = iArr;
            try {
                iArr[k1.b.CMCC.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[k1.b.CTCC.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[k1.b.CUCC.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[k1.b.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public w5() {
        this.a = null;
        try {
            Context context = n3.a().a;
            if (context == null) {
                return;
            }
            this.a = new a0(context);
        } catch (Exception e) {
        }
    }

    public static String a() {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls.newInstance(), "gsm.version.baseband", "no message");
        } catch (Exception e) {
            return c7.c;
        }
    }

    public static w5 b() {
        return b;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public JSONObject c() throws Throwable {
        String str;
        String str2;
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        Context context = n3.a().a;
        if (context == null) {
            return jSONObject;
        }
        try {
            String packageName = context.getPackageName();
            String strI = ja.i("cat /proc/" + Process.myPid() + "/cmdline");
            if (strI == null) {
                synchronized (ja.class) {
                }
                strI = c7.c;
            }
            JSONObject jSONObjectD = a5.a().c().d(context);
            boolean zOptBoolean = jSONObjectD.optBoolean("is_root");
            ja.a(jSONObject2, "net_type", l6.b(context));
            jSONObject2.put("pid", Process.myPid());
            jSONObject2.put("pname", strI.trim());
            jSONObject2.put("uid", Process.myUid());
            jSONObject2.put("uname", ja.a(0, packageName));
            jSONObject2.put("udid_from", n3.T.h());
            jSONObject2.put("permission", ja.m());
            jSONObject2.put("time_zone", ja.k());
            jSONObject2.put("app_name", ja.a(context, packageName));
            jSONObject2.put("app_version", ja.f());
            jSONObject2.put("cert_md5", ja.g());
            jSONObject2.put("cert_time", ja.i());
            jSONObject2.put("package_size", this.a.a(n3.T.a, packageName));
            jSONObject2.put("is_double_open", a5.a().c().c(context));
            jSONObject2.put("is_root", zOptBoolean);
            if (zOptBoolean) {
                jSONObject2.put("root_reason", jSONObjectD.optString("root_reason", c7.c));
            }
            JSONObject jSONObjectA = x2.a(0);
            jSONObjectA.put("id", Build.ID);
            jSONObjectA.put("product", Build.PRODUCT);
            jSONObjectA.put("user", Build.USER);
            jSONObjectA.put("manufacture", Build.MANUFACTURER);
            jSONObjectA.put("baseband", a());
            switch (a.a[k1.a().b(n3.T.a).ordinal()]) {
                case 1:
                    str = "op";
                    str2 = "中国移动";
                    jSONObjectA.put(str, str2);
                    break;
                case 2:
                    str = "op";
                    str2 = "中国电信";
                    jSONObjectA.put(str, str2);
                    break;
                case 3:
                    str = "op";
                    str2 = "中国联通";
                    jSONObjectA.put(str, str2);
                    break;
                case 4:
                    str = "op";
                    str2 = aa.m;
                    jSONObjectA.put(str, str2);
                    break;
            }
            jSONObject.put(g9.g, jSONObjectA);
            jSONObject.put(g9.k, t.a().a(packageName, context));
            JSONObject jSONObjectF = l6.f(true);
            if (jSONObjectF == null) {
                jSONObjectF = new JSONObject();
            }
            String strI2 = ja.i("/proc/sys/kernel/random/boot_id");
            if (strI2 == null) {
                strI2 = c7.c;
            }
            jSONObjectF.put("boot_id", strI2);
            jSONObject.put("wifi", jSONObjectF);
            jSONObject.put(g9.c, jSONObject2);
            jSONObject.put("protol_type", "start_all");
            jSONObject.toString();
        } catch (Exception e) {
        }
        return jSONObject;
    }
}
