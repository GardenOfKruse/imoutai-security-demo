package com.coralline.sea;

import com.coralline.sea.k8;
import java.net.UnknownHostException;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class v2 {
    public static v2 d = new v2();
    public String a = c7.c;
    public String b = c7.c;
    public final int[] c = {23946, 27042, 27043};

    public static v2 b() {
        return d;
    }

    public boolean a() {
        int iA;
        String strValueOf;
        int i;
        k8 k8Var = k8.b.a;
        if (!k8Var.a()) {
            try {
            } catch (UnknownHostException e) {
            }
            for (int i2 : this.c) {
                if (k8Var.a("127.0.0.1", i2)) {
                    this.a = "port_using";
                    this.b = String.valueOf(i2);
                    return true;
                }
                if (!n3.a().g || (iA = i6.a()) <= 0) {
                    return false;
                }
                this.a = "ptrace";
                strValueOf = String.valueOf(iA);
            }
            if (n3.a().g) {
            }
            return false;
        }
        this.a = "debugger_connected";
        strValueOf = "debug";
        this.b = strValueOf;
        return true;
    }
}
