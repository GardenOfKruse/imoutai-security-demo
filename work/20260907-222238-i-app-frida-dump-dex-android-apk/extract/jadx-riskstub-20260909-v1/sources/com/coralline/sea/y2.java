package com.coralline.sea;

import android.os.Build;
import android.util.DisplayMetrics;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class y2 {
    public static y2 b = null;
    public static final String c = "device_sys_info";
    public static String[] d = {"vmos.camera.enable", "vmpro.gps", "vmpro.gsm", "vmpro.hw-control", "vmpro.sensors", "vmpro.wifi", "vmprop.androidid", "vmprop.countryname", "vmprop.dataconnectionstate", "vmprop.datanetworktype", "vmprop.dev_ashmem", "vmprop.groupidlevel1", "vmprop.imei", "vmprop.imeisv", "vmprop.ip", "vmprop.line1number", "vmprop.networkoperator", "vmprop.networkoperatorname", "vmprop.networktype", "vmprop.simcountryiso", "vmprop.simoperator", "vmprop.simoperatorname", "vmprop.simserialnumber", "vmprop.simstate", "vmprop.subscriberid", "vmprop.wifibssid", "vmprop.wifissid", "vmproxy.port"};
    public Boolean a = Boolean.FALSE;

    public static synchronized y2 a() {
        if (b == null) {
            b = new y2();
        }
        return b;
    }

    public String a(JSONObject jSONObject, String str) {
        return jSONObject.optString(str, i2.b);
    }

    public final HashSet<String> a(JSONObject jSONObject) {
        HashSet<String> hashSet = new HashSet<>();
        try {
            JSONArray jSONArrayNames = jSONObject.names();
            if (jSONArrayNames != null && jSONArrayNames.length() > 0) {
                String string = jSONArrayNames.toString();
                int i = 0;
                while (true) {
                    String[] strArr = d;
                    if (i >= strArr.length) {
                        break;
                    }
                    if (string.contains(strArr[i])) {
                        hashSet.add(d[i]);
                    }
                    i++;
                }
            }
        } catch (Throwable th) {
        }
        return hashSet;
    }

    public JSONObject a(JSONArray jSONArray) {
        JSONObject jSONObject = new JSONObject();
        if (jSONArray != null && jSONArray.length() > 0) {
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    String string = jSONArray.getString(i);
                    jSONObject.put(string, string.endsWith(".jar") ? c4.c(string) : c4.d(string));
                } catch (JSONException e) {
                }
            }
        }
        return jSONObject;
    }

    public final String b() {
        return ja.a("ro.boot.flash.locked", "3");
    }

    public void b(JSONObject jSONObject) {
        JSONObject jSONObjectA;
        try {
            JSONObject jSONObjectA2 = x2.a();
            jSONObject.put("brand", Build.BRAND);
            jSONObject.put("model", Build.MODEL);
            jSONObject.put("model_prop", a(jSONObjectA2, "ro.product.model"));
            jSONObject.put("board", Build.BOARD);
            jSONObject.put("board_prop", a(jSONObjectA2, "ro.product.board"));
            jSONObject.put("device", Build.DEVICE);
            jSONObject.put("device_prop", a(jSONObjectA2, "ro.product.device"));
            jSONObject.put("manufacturer", Build.MANUFACTURER);
            jSONObject.put("manufacturer_prop", a(jSONObjectA2, "ro.product.manufacturer"));
            jSONObject.put("os_version", Build.VERSION.RELEASE);
            String str = Build.DISPLAY;
            jSONObject.put("os_name", str);
            jSONObject.put("sdk_version", Build.VERSION.SDK_INT);
            jSONObject.put("run_mode", ja.j());
            jSONObject.put("cpu_abi", Build.CPU_ABI);
            jSONObject.put("cpu_abi_prop", a(jSONObjectA2, "ro.product.cpu.abi"));
            jSONObject.put("cpu_abi2", Build.CPU_ABI2);
            jSONObject.put("cpu_abi2_prop", a(jSONObjectA2, "ro.product.cpu.abi2"));
            jSONObject.put("dev_changelist", a(jSONObjectA2, "ro.build.changelist"));
            jSONObject.put("dev_date_utc", a(jSONObjectA2, "ro.build.date.utc"));
            jSONObject.put("dev_date", a(jSONObjectA2, "ro.build.date"));
            jSONObject.put("serial", n3.a().J);
            jSONObject.put("serial_prop", a(jSONObjectA2, "ril.serialnumber"));
            jSONObject.put("bootloader", Build.BOOTLOADER);
            jSONObject.put("bootloader_prop", a(jSONObjectA2, "ro.boot.bootloader"));
            jSONObject.put("bootloader_enabled", b());
            jSONObject.put("virtual_camera", c());
            jSONObject.put("virtual_camera_detail", c() ? "/data/local/tmp/h.log" : c7.c);
            jSONObject.put("display", str);
            jSONObject.put("display_prop", a(jSONObjectA2, "ro.build.display.id"));
            jSONObject.put("hardware", Build.HARDWARE);
            jSONObject.put("host", Build.HOST);
            jSONObject.put("host_prop", a(jSONObjectA2, "ro.build.host"));
            jSONObject.put("build_id", Build.ID);
            jSONObject.put("build_id_prop", a(jSONObjectA2, "ro.build.id"));
            jSONObject.put("hidden_ver", a(jSONObjectA2, "ro.build.hidden_ver"));
            jSONObject.put("fingerprint", Build.FINGERPRINT);
            jSONObject.put("fingerprint_prop", a(jSONObjectA2, "ro.build.fingerprint"));
            try {
                DisplayMetrics displayMetrics = n3.T.a.getResources().getDisplayMetrics();
                jSONObject.put("resolution_w", displayMetrics.widthPixels);
                jSONObject.put("resolution_h", displayMetrics.heightPixels);
            } catch (Exception e) {
                jSONObject.put("resolution_w", 0);
                jSONObject.put("resolution_h", 0);
            }
            if (jSONObjectA2.length() > 0) {
                HashSet<String> hashSetA = a(jSONObjectA2);
                if (hashSetA.size() > 0) {
                    jSONObject.put("vmos_count", hashSetA.size());
                    jSONObject.put("vmos_detail", hashSetA);
                }
            }
            if (!n3.T.d() || (jSONObjectA = i2.a(n3.T.a)) == null) {
                return;
            }
            Iterator<String> itKeys = jSONObjectA.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                Object objOpt = jSONObjectA.opt(next);
                if (next != null && objOpt != null) {
                    jSONObject.put(next, objOpt);
                }
            }
        } catch (Exception e2) {
        }
    }

    public boolean c() {
        if (!this.a.booleanValue()) {
            File file = new File("/data/local/tmp/h.log");
            if (file.exists() && file.isFile()) {
                this.a = Boolean.TRUE;
            }
        }
        return this.a.booleanValue();
    }

    public JSONObject d() throws Throwable {
        JSONObject jSONObject = new JSONObject();
        try {
            String strI = ja.i("getprop");
            if (strI == null) {
                return jSONObject;
            }
            String[] strArrSplit = strI.split("\n");
            Pattern patternCompile = Pattern.compile("\\[(.*[^\\]])\\]*");
            for (String str : strArrSplit) {
                String[] strArrSplit2 = str.split(":", 2);
                if (strArrSplit2.length == 2) {
                    String strTrim = strArrSplit2[0].trim();
                    String strTrim2 = strArrSplit2[1].trim();
                    Matcher matcher = patternCompile.matcher(strTrim);
                    Matcher matcher2 = patternCompile.matcher(strTrim2);
                    if (matcher.find()) {
                        jSONObject.put(matcher.group(1), matcher2.find() ? matcher2.group(1) : i2.b);
                    }
                }
            }
            return jSONObject;
        } catch (Exception e) {
            return jSONObject;
        }
    }
}
