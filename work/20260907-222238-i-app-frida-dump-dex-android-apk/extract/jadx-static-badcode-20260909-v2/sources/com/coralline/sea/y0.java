package com.coralline.sea;

import android.util.Base64;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class y0 extends w0 {
    @Override // com.coralline.sea.w0
    public String a(String str) throws Exception {
        return new String(i6.a(Base64.decode(str, 2), 0, 0, true, 4), s0.f);
    }

    @Override // com.coralline.sea.w0
    public String b(String str) throws Exception {
        String strEncodeToString = Base64.encodeToString(i6.a(str.getBytes(s0.f), 1, 1, true, 3), 2);
        return (strEncodeToString == null || strEncodeToString.trim().length() <= 0) ? strEncodeToString : Pattern.compile("\\s*|\t|\r|\n").matcher(strEncodeToString).replaceAll(c7.c);
    }
}
