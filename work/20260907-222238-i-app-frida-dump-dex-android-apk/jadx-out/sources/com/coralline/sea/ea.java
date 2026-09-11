package com.coralline.sea;

import androidx.annotation.Nullable;
import java.util.concurrent.ConcurrentLinkedQueue;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class ea extends x6 {
    public static final ConcurrentLinkedQueue<s1> b = new ConcurrentLinkedQueue<>();

    public ea() {
        super("userdata", 5);
    }

    public static void a(@Nullable s1 s1Var) {
        if (s1Var == null) {
            return;
        }
        b.add(s1Var);
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        ConcurrentLinkedQueue<s1> concurrentLinkedQueue = b;
        if (concurrentLinkedQueue.isEmpty()) {
            return;
        }
        while (true) {
            s1 s1VarPoll = concurrentLinkedQueue.poll();
            if (s1VarPoll == null) {
                return;
            }
            y9.b(s1VarPoll);
            concurrentLinkedQueue = b;
        }
    }
}
