package com.coralline.sea;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class n1 {
    public static final Charset a = Charset.forName("US-ASCII");
    public static final Charset b = Charset.forName("ISO-8859-1");
    public static final Charset c = Charset.forName(s0.f);
    public static final Charset d = Charset.forName("UTF-16BE");
    public static final Charset e = Charset.forName("UTF-16LE");
    public static final Charset f = Charset.forName("UTF-16");

    public static Charset a(String str) {
        return str == null ? Charset.defaultCharset() : Charset.forName(str);
    }

    public static Charset a(Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }

    public static SortedMap<String, Charset> a() {
        TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        Charset charset = b;
        treeMap.put(charset.name(), charset);
        Charset charset2 = a;
        treeMap.put(charset2.name(), charset2);
        Charset charset3 = f;
        treeMap.put(charset3.name(), charset3);
        Charset charset4 = d;
        treeMap.put(charset4.name(), charset4);
        Charset charset5 = e;
        treeMap.put(charset5.name(), charset5);
        Charset charset6 = c;
        treeMap.put(charset6.name(), charset6);
        return Collections.unmodifiableSortedMap(treeMap);
    }
}
