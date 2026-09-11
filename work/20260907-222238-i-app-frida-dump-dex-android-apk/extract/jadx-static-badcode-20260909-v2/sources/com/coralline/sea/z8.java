package com.coralline.sea;

import android.app.ActivityManager;
import com.coralline.sea.m5;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import org.json.JSONArray;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class z8 {
    public static HashSet<String> a = new HashSet<>();

    public static HashSet<String> a() {
        new JSONArray();
        new ArrayList();
        HashSet<String> hashSet = new HashSet<>();
        try {
            ActivityManager activityManager = (ActivityManager) n3.a().a.getSystemService("activity");
            if (activityManager != null) {
                Iterator<ActivityManager.RunningServiceInfo> it = activityManager.getRunningServices(m5.b.a.e).iterator();
                while (it.hasNext()) {
                    hashSet.add(it.next().process);
                }
            }
        } catch (Exception e) {
        }
        return hashSet;
    }

    public static JSONArray b() {
        JSONArray jSONArray = new JSONArray();
        try {
            HashSet<String> hashSetA = a();
            if (hashSetA.size() != 0) {
                hashSetA.removeAll(a);
                a.addAll(hashSetA);
                Iterator<String> it = hashSetA.iterator();
                while (it.hasNext()) {
                    jSONArray.put(it.next());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONArray;
    }
}
