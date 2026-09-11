package com.coralline.sea;

import java.util.ArrayList;
import java.util.HashSet;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class d5 {
    public static d5 c = new d5();
    public HashSet a = new HashSet();
    public ArrayList<String> b = new ArrayList<>();

    public static synchronized d5 b() {
        return c;
    }

    public ArrayList<String> a() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(this.b);
        this.b.clear();
        return arrayList;
    }

    public void a(String str) {
        if (str == null || this.a.contains(str)) {
            return;
        }
        this.b.add(str);
        this.a.add(str);
    }
}
