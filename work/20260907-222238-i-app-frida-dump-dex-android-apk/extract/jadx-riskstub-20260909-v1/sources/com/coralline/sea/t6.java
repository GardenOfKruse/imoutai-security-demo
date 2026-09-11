package com.coralline.sea;

import com.coralline.sea.checkers.Checker;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public abstract class t6 extends Checker {
    public t6() {
    }

    public t6(String str) {
        this.checkerName = str;
        this.checkConfig = buildConfig(str, 0);
    }
}
