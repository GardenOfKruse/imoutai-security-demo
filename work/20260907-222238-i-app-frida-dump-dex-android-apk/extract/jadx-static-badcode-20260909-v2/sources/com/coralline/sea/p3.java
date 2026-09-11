package com.coralline.sea;

import androidx.annotation.Nullable;
import java.util.concurrent.ConcurrentLinkedQueue;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class p3 extends x6 {
    public static final String b = "extra_info";
    public static final String c = "ExtraInfo";
    public static final ConcurrentLinkedQueue<s1> d = new ConcurrentLinkedQueue<>();

    public p3() {
        super(b, 5);
    }

    public static void a(@Nullable s1 s1Var) {
        if (s1Var == null) {
            return;
        }
        d.add(s1Var);
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        ConcurrentLinkedQueue<s1> concurrentLinkedQueue = d;
        if (concurrentLinkedQueue.isEmpty()) {
            return;
        }
        while (true) {
            s1 s1VarPoll = concurrentLinkedQueue.poll();
            if (s1VarPoll == null) {
                return;
            }
            y9.b(s1VarPoll);
            concurrentLinkedQueue = d;
        }
    }
}
