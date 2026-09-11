package com.coralline.sea;

import com.coralline.sea.checkers.Checker;

/* JADX INFO: loaded from: assets/RiskStub.dex */
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
