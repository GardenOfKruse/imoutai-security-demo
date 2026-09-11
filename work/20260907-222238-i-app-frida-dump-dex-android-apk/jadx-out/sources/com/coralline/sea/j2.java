package com.coralline.sea;

import android.content.Context;
import android.os.PowerManager;
import com.coralline.sea.checkers.Checker;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class j2 {
    public static final int a = 0;

    public static boolean a() {
        return !l5.i.get();
    }

    public static boolean a(Context context) {
        try {
            return ((PowerManager) context.getSystemService("power")).isScreenOn();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean a(Checker checker) {
        try {
            if (!a()) {
                if ((System.currentTimeMillis() - l5.k.get()) / 1000 >= z1.c("background_conf").optInt("stop_delay", 0)) {
                    checker.getName();
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean a(Checker checker, int i) {
        try {
            if (z1.f()) {
                boolean zA = a(checker);
                checker.getName();
                l5.i.get();
                return zA;
            }
            boolean z = true;
            if (i == 1 || !a(checker)) {
                z = false;
            }
            checker.getName();
            l5.i.get();
            return z;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public static boolean a(String str) {
        try {
            if (z1.f() && !a()) {
                if ((System.currentTimeMillis() - l5.k.get()) / 1000 >= z1.c("background_conf").optInt("stop_delay", 0)) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean b() {
        return l5.i.get();
    }

    public static boolean b(String str) {
        try {
            if (z1.f()) {
                return l5.i.get();
            }
            return false;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public static boolean c() {
        return b("suspendMainEntryStart");
    }

    public static boolean d() {
        return b("suspendSendStartInfo");
    }

    public static boolean e() {
        return b("suspendTriggerJobs");
    }
}
