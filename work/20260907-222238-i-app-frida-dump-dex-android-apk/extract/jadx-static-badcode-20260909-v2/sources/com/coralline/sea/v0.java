package com.coralline.sea;

import android.text.TextUtils;
import java.util.List;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class v0 implements n4 {
    public static final String a = "CCBController";
    public static v0 b;

    public static synchronized v0 b() {
        if (b == null) {
            b = new v0();
        }
        return b;
    }

    @Override // com.coralline.sea.n4
    public String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return m1.j().a(m1.j().k(str), p9.a(str));
        } catch (Exception e) {
            return null;
        }
    }

    @Override // com.coralline.sea.n4
    public String a(String str, String str2, String str3) {
        return null;
    }

    @Override // com.coralline.sea.n4
    public String a(byte[] bArr) {
        if (bArr == null || bArr.length < 1) {
            return null;
        }
        try {
            return p9.b(m1.j().i(new String(bArr)));
        } catch (Exception e) {
            return null;
        }
    }

    @Override // com.coralline.sea.n4
    public String a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        return null;
    }

    @Override // com.coralline.sea.n4
    public List<String> a() {
        return b(m1.j().i());
    }

    @Override // com.coralline.sea.n4
    public boolean a(s1 s1Var) {
        return l2.g().b().e() && !n3.a().g;
    }

    public List<String> b(String str) {
        return ja.c(str);
    }
}
