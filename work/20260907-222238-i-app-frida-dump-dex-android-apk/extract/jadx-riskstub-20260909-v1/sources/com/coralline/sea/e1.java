package com.coralline.sea;

import java.util.List;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class e1 implements w4 {
    public static e1 a;

    public static synchronized e1 b() {
        if (a == null) {
            a = new e1();
        }
        return a;
    }

    @Override // com.coralline.sea.w4
    public List<Integer> a() {
        return null;
    }
}
