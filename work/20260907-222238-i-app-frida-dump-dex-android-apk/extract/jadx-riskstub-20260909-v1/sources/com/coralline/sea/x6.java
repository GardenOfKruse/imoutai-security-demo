package com.coralline.sea;

import com.coralline.sea.checkers.Checker;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public abstract class x6 extends Checker {
    public int a;

    public x6() {
        this.a = 5;
    }

    public x6(String str, int i) {
        this.checkerName = str;
        this.a = i;
        this.checkConfig = buildConfig(str, i);
    }
}
