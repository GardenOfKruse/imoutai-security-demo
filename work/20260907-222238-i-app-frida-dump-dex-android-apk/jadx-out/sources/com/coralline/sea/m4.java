package com.coralline.sea;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class m4 {
    public static Class a;

    public static Class a() {
        if (a == null) {
            b();
        }
        return a;
    }

    public static void b() {
        try {
            a = Class.forName("android.content.IContentProvider");
        } catch (ClassNotFoundException e) {
        }
    }
}
