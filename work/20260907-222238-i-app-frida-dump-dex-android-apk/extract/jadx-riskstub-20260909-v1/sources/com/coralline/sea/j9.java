package com.coralline.sea;

import java.util.List;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class j9 {
    public static String a(List list, String str) {
        if (list != null) {
            return a(list.toArray(), str);
        }
        throw new IllegalArgumentException();
    }

    public static String a(Object[] objArr, String str) {
        if (objArr == null) {
            throw new IllegalArgumentException();
        }
        if (objArr.length == 0) {
            return c7.c;
        }
        StringBuilder sb = new StringBuilder();
        for (Object obj : objArr) {
            sb.append(obj.toString() + str);
        }
        sb.delete(sb.length() - str.length(), sb.length());
        return sb.toString();
    }
}
