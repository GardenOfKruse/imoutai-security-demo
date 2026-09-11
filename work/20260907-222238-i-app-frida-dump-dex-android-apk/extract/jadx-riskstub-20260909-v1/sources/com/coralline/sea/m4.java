package com.coralline.sea;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
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
