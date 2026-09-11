package com.coralline.sea;

import android.text.TextUtils;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class na extends x6 {
    public static final String h = "VpnProxyChecker";
    public static final String i = "vpnproxy";
    public boolean b;
    public int c;
    public List<String> d;
    public JSONObject e;
    public boolean f;
    public boolean g;

    public na() {
        super(i, 15);
        this.b = true;
        this.c = 15;
        this.d = new ArrayList();
        this.e = new JSONObject();
        this.f = false;
        this.g = false;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        if (this.d.size() > 1000000) {
            this.d.clear();
        }
        HashMap map = new HashMap();
        try {
            JSONObject jSONObjectA = z1.a(i);
            if (jSONObjectA != null && jSONObjectA.length() > 0) {
                this.b = jSONObjectA.optBoolean(t1.b, true);
                this.c = jSONObjectA.optInt("period", 15);
                if (this.b) {
                    Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                    if (networkInterfaces == null) {
                        return;
                    }
                    for (NetworkInterface networkInterface : Collections.list(networkInterfaces)) {
                        if (networkInterface.isUp()) {
                            map.put((TextUtils.isEmpty(networkInterface.getName()) || "null".equals(networkInterface.getName())) ? networkInterface.getDisplayName() : networkInterface.getName(), networkInterface);
                        }
                    }
                    for (String str : map.keySet()) {
                        if (str.contains("tun") || str.contains("ppp") || str.contains("pptp")) {
                            String displayName = ((NetworkInterface) map.get(str)).getDisplayName();
                            boolean z = false;
                            Iterator<String> it = this.d.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                String next = it.next();
                                if (!TextUtils.isEmpty(next) && next.equals(displayName)) {
                                    z = true;
                                    break;
                                }
                            }
                            if (!z) {
                                this.d.add(displayName);
                                JSONObject jSONObject = new JSONObject();
                                jSONObject.put("vpn_adapter", displayName);
                                jSONObject.put("detail", new JSONArray().put(displayName));
                                this.e = jSONObject;
                            }
                        }
                    }
                }
            }
            String strVpn = com.coralline.sea.a.b.vpn();
            if (TextUtils.isEmpty(strVpn) || this.f) {
                if (this.e.length() <= 0 || this.g) {
                    return;
                }
                push(e2.b, i, this.e.toString());
                this.g = true;
                return;
            }
            this.e.put("vpn_adapter", "noVPN");
            this.e.put("detail", strVpn);
            push(e2.b, i, this.e.toString());
            this.f = true;
            this.e = new JSONObject();
        } catch (Exception e) {
        }
    }
}
