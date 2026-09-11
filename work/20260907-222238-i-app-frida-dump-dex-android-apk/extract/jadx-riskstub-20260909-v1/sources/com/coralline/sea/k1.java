package com.coralline.sea;

import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class k1 {
    public static k1 b = null;
    public static final String c = "carrier_info";
    public Map<String, b> a = new a();

    public class a extends HashMap<String, b> {
        public a() {
            b bVar = b.CMCC;
            put("00", bVar);
            b bVar2 = b.CUCC;
            put("01", bVar2);
            put("02", bVar);
            b bVar3 = b.CTCC;
            put("03", bVar3);
            put("04", bVar);
            put("05", bVar3);
            put("06", bVar2);
            put("07", bVar);
            put("08", bVar);
            put("09", bVar2);
            put("10", bVar2);
            put("11", bVar3);
            put("12", bVar3);
            put("13", bVar);
        }
    }

    public enum b {
        CMCC,
        CTCC,
        CUCC,
        UNKNOWN
    }

    public static synchronized k1 a() {
        if (b == null) {
            b = new k1();
        }
        return b;
    }

    public b a(String str) {
        return this.a.containsKey(str) ? this.a.get(str) : b.UNKNOWN;
    }

    public JSONObject a(Context context) {
        try {
            String networkOperator = ((TelephonyManager) context.getSystemService(m1.j)).getNetworkOperator();
            JSONObject jSONObject = new JSONObject();
            if (networkOperator.length() > 3) {
                jSONObject.put("mcc", networkOperator.substring(0, 3));
                jSONObject.put("mnc", networkOperator.substring(3));
            }
            return jSONObject;
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject a(TelephonyManager telephonyManager) {
        CdmaCellLocation cdmaCellLocation;
        try {
            if (ja.r("android.permission.ACCESS_FINE_LOCATION") == 0 && (cdmaCellLocation = (CdmaCellLocation) telephonyManager.getCellLocation()) != null) {
                new JSONObject().put("cid", cdmaCellLocation.getBaseStationId());
                return null;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public b b(Context context) {
        String strOptString = l6.a(context).optString("mnc", c7.c);
        return this.a.containsKey(strOptString) ? this.a.get(strOptString) : b.UNKNOWN;
    }

    public JSONObject b(TelephonyManager telephonyManager) {
        GsmCellLocation gsmCellLocation;
        try {
            if (ja.r("android.permission.ACCESS_FINE_LOCATION") == 0 && (gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation()) != null) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("cid", gsmCellLocation.getCid() & ua.h);
                jSONObject.put("lac", gsmCellLocation.getLac());
                return jSONObject;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Integer c(TelephonyManager telephonyManager) {
        List<CellInfo> listA = q5.a(telephonyManager);
        return Integer.valueOf(listA == null ? -1 : listA.size());
    }
}
