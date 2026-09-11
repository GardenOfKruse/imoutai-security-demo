package com.coralline.sea;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class k0 {
    public static final String a = "BatteryManagerUtil";
    public static final int b = -12312340;

    public static double a(Context context) {
        try {
            return ((Double) Class.forName("com.android.internal.os.PowerProfile").getMethod("getBatteryCapacity", new Class[0]).invoke(Class.forName("com.android.internal.os.PowerProfile").getConstructor(Context.class).newInstance(context), new Object[0])).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0d;
        }
    }

    public static JSONObject b(Context context) {
        int i = Build.VERSION.SDK_INT;
        String str = c7.c;
        JSONObject jSONObject = new JSONObject();
        if (i >= 21) {
            BatteryManager batteryManager = (BatteryManager) context.getSystemService("batterymanager");
            int intProperty = batteryManager.getIntProperty(6);
            int intProperty2 = batteryManager.getIntProperty(1);
            int intProperty3 = batteryManager.getIntProperty(3);
            int intProperty4 = batteryManager.getIntProperty(2);
            int intProperty5 = batteryManager.getIntProperty(4);
            int intProperty6 = batteryManager.getIntProperty(5);
            String str2 = "status:" + intProperty + ";\ncounter:" + intProperty2 + ";ave:" + intProperty3 + ";now:" + intProperty4 + ";\ncapacity:" + intProperty5 + ";\nenergy_counter:" + intProperty6;
            try {
                jSONObject.put("status", intProperty);
                jSONObject.put("counter", intProperty2);
                jSONObject.put("average", intProperty3);
                jSONObject.put("now", intProperty4);
                jSONObject.put("capacity", intProperty5);
                jSONObject.put("energy_counter", intProperty6);
            } catch (Exception e) {
                e.printStackTrace();
            }
            str = str2;
        } else {
            try {
                jSONObject.put("now", b);
                jSONObject.put("counter", b);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        int intExtra = intentRegisterReceiver.getIntExtra("level", -1);
        int intExtra2 = intentRegisterReceiver.getIntExtra("scale", -1);
        int intExtra3 = intentRegisterReceiver.getIntExtra("plugged", -1);
        int intExtra4 = intentRegisterReceiver.getIntExtra("temperature", -1);
        boolean booleanExtra = intentRegisterReceiver.getBooleanExtra("present", false);
        double dA = a(context);
        String stringExtra = intentRegisterReceiver.getStringExtra("technology");
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("\n\nlevel:");
        sb.append(intExtra);
        sb.append(";scale:");
        sb.append(intExtra2);
        sb.append(";resPct:");
        sb.append((int) ((intExtra / intExtra2) * 100.0f));
        sb.append(";\nplugged:");
        sb.append(intExtra3);
        sb.append(";\n\npresent:");
        sb.append(booleanExtra);
        sb.append(";\ntemperature:");
        sb.append(intExtra4);
        sb.append(";\ntotal_capacity:");
        sb.append(dA);
        sb.append(";\ntechnology:");
        sb.append(stringExtra);
        try {
            jSONObject.put("level", intExtra);
            jSONObject.put("scale", intExtra2);
            jSONObject.put("plugged", intExtra3);
            jSONObject.put("temperature", intExtra4);
            jSONObject.put("technology", stringExtra);
            jSONObject.put("total_capacity", dA);
            return jSONObject;
        } catch (Exception e3) {
            e3.printStackTrace();
            return jSONObject;
        }
    }
}
