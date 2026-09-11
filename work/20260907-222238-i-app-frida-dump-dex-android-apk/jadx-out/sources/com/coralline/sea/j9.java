package com.coralline.sea;

import java.util.List;

/* JADX INFO: loaded from: assets/RiskStub.dex */
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
