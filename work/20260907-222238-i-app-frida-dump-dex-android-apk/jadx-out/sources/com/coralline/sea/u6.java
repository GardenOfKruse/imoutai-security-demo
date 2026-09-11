package com.coralline.sea;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityNr;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoNr;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.coralline.sea.k1;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
@Deprecated
public class u6 extends x6 {
    public static final String c = "operator_fake";
    public boolean b;

    public u6() {
        super(c, 60);
        this.b = false;
    }

    public static boolean a(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "airplane_mode_on", 0) == 1;
    }

    public String a(CellInfo cellInfo) {
        StringBuilder sb;
        int mnc;
        String mncString = c7.c;
        if (cellInfo instanceof CellInfoLte) {
            CellIdentityLte cellIdentity = ((CellInfoLte) cellInfo).getCellIdentity();
            if (Build.VERSION.SDK_INT >= 28) {
                mncString = cellIdentity.getMncString();
            } else {
                sb = new StringBuilder();
                mnc = cellIdentity.getMnc();
                sb.append(mnc);
                sb.append(c7.c);
                mncString = sb.toString();
            }
        } else if (cellInfo instanceof CellInfoGsm) {
            CellIdentityGsm cellIdentity2 = ((CellInfoGsm) cellInfo).getCellIdentity();
            if (Build.VERSION.SDK_INT >= 28) {
                mncString = cellIdentity2.getMncString();
            } else {
                sb = new StringBuilder();
                mnc = cellIdentity2.getMnc();
                sb.append(mnc);
                sb.append(c7.c);
                mncString = sb.toString();
            }
        } else if (cellInfo instanceof CellInfoWcdma) {
            CellIdentityWcdma cellIdentity3 = ((CellInfoWcdma) cellInfo).getCellIdentity();
            if (Build.VERSION.SDK_INT >= 28) {
                mncString = cellIdentity3.getMncString();
            } else {
                sb = new StringBuilder();
                mnc = cellIdentity3.getMnc();
                sb.append(mnc);
                sb.append(c7.c);
                mncString = sb.toString();
            }
        } else if (!(cellInfo instanceof CellInfoCdma)) {
            mncString = (Build.VERSION.SDK_INT < 29 || !(cellInfo instanceof CellInfoNr)) ? i2.b : ((CellIdentityNr) ((CellInfoNr) cellInfo).getCellIdentity()).getMncString();
        }
        return mncString == null ? c7.c : mncString;
    }

    public final HashSet a(TelephonyManager telephonyManager, JSONArray jSONArray) {
        String strA;
        k1.b bVarA;
        HashSet hashSet = new HashSet();
        List<CellInfo> listA = q5.a(telephonyManager);
        if (listA != null) {
            for (int i = 0; i < listA.size(); i++) {
                if (listA.get(i).isRegistered() && k1.b.UNKNOWN != (bVarA = k1.a().a((strA = a(listA.get(i)))))) {
                    hashSet.add(bVarA);
                    jSONArray.put(strA);
                }
            }
        }
        return hashSet;
    }

    public final void a(TelephonyManager telephonyManager) {
    }

    public final void a(k1.b bVar, String str, JSONArray jSONArray) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (!bVar.equals(k1.b.CUCC) ? !bVar.equals(k1.b.CMCC) ? !(!bVar.equals(k1.b.CTCC) || str.toLowerCase().contains("china telecom") || str.contains("中国电信")) : !(str.toLowerCase().contains("china mobile") || str.toLowerCase().contains("cmcc") || str.contains("中国移动")) : str.toLowerCase().contains("china unicom") || str.contains("中国联通")) {
            jSONArray.put(str);
        }
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        k1.b bVar;
        try {
            if (this.b) {
                return;
            }
            Context context = n3.a().a;
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObjectA = l6.a(context);
            Objects.toString(jSONObjectA);
            if (jSONObjectA == null) {
                return;
            }
            String strOptString = jSONObjectA.optString("mcc", c7.c);
            String strOptString2 = jSONObjectA.optString("mnc", c7.c);
            k1.b bVarA = k1.a().a(strOptString2);
            Objects.toString(bVarA);
            if (TextUtils.equals(strOptString, "460") && (bVar = k1.b.UNKNOWN) != bVarA) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(m1.j);
                JSONArray jSONArray = new JSONArray();
                HashSet hashSetA = a(telephonyManager, jSONArray);
                String strTrim = c7.c;
                JSONArray jSONArray2 = new JSONArray();
                String strName = bVarA.name();
                Objects.toString(hashSetA);
                int simState = telephonyManager.getSimState();
                String simOperator = telephonyManager.getSimOperator();
                if (!TextUtils.isEmpty(simOperator)) {
                    strTrim = simOperator.startsWith("460") ? simOperator.replace("460", c7.c).trim() : c7.c;
                    k1.b bVarA2 = k1.a().a(strTrim);
                    Objects.toString(bVarA2);
                    if (bVar != bVarA2 && bVarA2 != bVarA) {
                        jSONArray2.put(strTrim);
                        jSONArray2.put(strName);
                    } else if ((bVar == bVarA2 || bVarA2 != bVarA) && bVar == bVarA2) {
                        jSONArray2.put(strTrim);
                    }
                    jSONArray2.length();
                }
                String simOperatorName = telephonyManager.getSimOperatorName();
                a(bVarA, simOperatorName, jSONArray2);
                if (hashSetA.size() > 0 && !l6.b(context, true).equals("NETWORK_5G") && !hashSetA.contains(bVarA)) {
                    jSONArray2.put(jSONArray.get(0));
                }
                jSONArray2.length();
                if (jSONArray2.length() > 0) {
                    this.b = true;
                    jSONObject.put("mnc_network", strOptString2).put("mnc_cell", jSONArray).put("mnc_sim", strTrim).put("reg_cell_count", jSONArray.length()).put("sim_state", simState).put("sim_oper_name", simOperatorName).put("detail", jSONArray2);
                    push(e2.b, c, jSONObject.toString());
                }
            }
        } catch (Exception e) {
        }
    }
}
