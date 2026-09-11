package com.coralline.sea;

import com.coralline.sea.checkers.Checker;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public abstract class t6 extends Checker {
    public t6() {
    }

    public t6(String str) {
        this.checkerName = str;
        this.checkConfig = buildConfig(str, 0);
    }
}
