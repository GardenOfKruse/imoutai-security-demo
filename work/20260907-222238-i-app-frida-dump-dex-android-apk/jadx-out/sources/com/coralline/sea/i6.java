package com.coralline.sea;

import android.content.Context;
import android.os.Build;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class i6 {

    public interface a<T> {
        T a();
    }

    public static Object N() {
        return a(new a() { // from class: com.coralline.sea.-$$Lambda$7B-nsCuRfB72kAHG4BUv-ro6_yY
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.pc();
            }
        }, "pc", (Object) null);
    }

    public static int O() {
        return ((Integer) a(new a() { // from class: com.coralline.sea.-$$Lambda$40tnZmv_9u0zvhX9sfx_5QKVL3k
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Integer.valueOf(com.coralline.sea.a.b.za());
            }
        }, "za", -1)).intValue();
    }

    public static int P() {
        return ((Integer) a(new a() { // from class: com.coralline.sea.-$$Lambda$Q93L4T4wUyx_pqLK94j8xRb0NEY
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Integer.valueOf(com.coralline.sea.a.b.at());
            }
        }, "at", -1)).intValue();
    }

    public static int a() {
        return ((Integer) a(new a() { // from class: com.coralline.sea.-$$Lambda$BiUgvXs1bSuzRi0uptDKnI2Y0FY
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Integer.valueOf(com.coralline.sea.a.b.dc());
            }
        }, "dc", -1)).intValue();
    }

    public static long a(final int i, final String str, final String str2) {
        return ((Long) a((a<long>) new a() { // from class: com.coralline.sea.-$$Lambda$JxbO94lp8yWbrdMPJuLvK6prA4o
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Long.valueOf(com.coralline.sea.a.b.ci(i, str, str2));
            }
        }, "ci", 0L)).longValue();
    }

    public static long a(final String str, final String str2) {
        return ((Long) a((a<long>) new a() { // from class: com.coralline.sea.-$$Lambda$mWxtYY_CsSQsmgsBHCbsovgcioo
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Long.valueOf(com.coralline.sea.a.b.in(str, str2));
            }
        }, "in", -1L)).longValue();
    }

    public static long a(final String str, final String[] strArr) {
        return ((Long) a((a<long>) new a() { // from class: com.coralline.sea.-$$Lambda$glzLDdraYyAI3kFYaNhVqqNPuNo
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Long.valueOf(com.coralline.sea.a.b.ia(str, strArr));
            }
        }, "ia", -1L)).longValue();
    }

    public static <T> T a(a<T> aVar, String str, T t) {
        try {
            if (j6.a(str)) {
                return t;
            }
            j6.a(str, 1);
            T tA = aVar.a();
            j6.a(str, 0);
            return tA;
        } catch (Throwable th) {
            return t;
        }
    }

    public static String a(final Context context) {
        return (String) a((a<String>) new a() { // from class: com.coralline.sea.-$$Lambda$5DFo_6v2sl-V2zvlW0-yhIuvFAM
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.cdz(context);
            }
        }, "cdz", c7.c);
    }

    public static String a(final String str) {
        return (String) a((a<String>) new a() { // from class: com.coralline.sea.-$$Lambda$BCWFulSuELu_2Pze7OEDq_wx7j4
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.gsm(str);
            }
        }, "gsm", c7.c);
    }

    public static String a(final boolean z, final int i, final String str, final String str2, final String str3, final boolean z2, final Method method) {
        return (String) a((a<String>) new a() { // from class: com.coralline.sea.-$$Lambda$8jfLr0uNHv9sNaGMQuETiqgWAQI
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.iz(z, i, str, str2, str3, z2, method);
            }
        }, "iz", c7.c);
    }

    public static boolean a(final int i) {
        return ((Boolean) a((a<Boolean>) new a() { // from class: com.coralline.sea.-$$Lambda$eeSQlRODGg0E4ym8GW9xpGjxkHk
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Boolean.valueOf(com.coralline.sea.a.b.no(i));
            }
        }, "no", Boolean.FALSE)).booleanValue();
    }

    public static byte[] a(byte[] bArr) {
        return bArr;
    }

    public static byte[] a(final byte[] bArr, final int i, final int i2, final boolean z, final int i3) {
        byte[] bArr2 = new byte[0];
        return (bArr == null || bArr.length < 1) ? bArr2 : j6.c() ? bArr : (byte[]) a((a<byte[]>) new a() { // from class: com.coralline.sea.-$$Lambda$5_1q3v7m8ZpJ8BTh5SKID-A3T8Q
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.ed(bArr, i, i2, z, i3);
            }
        }, "ed", bArr2);
    }

    public static byte[] a(final byte[] bArr, final int i, final int i2, final boolean z, final int i3, final byte[] bArr2, final byte[] bArr3) {
        byte[] bArr4 = new byte[0];
        return (bArr == null || bArr.length < 1) ? bArr4 : j6.c() ? bArr : (byte[]) a((a<byte[]>) new a() { // from class: com.coralline.sea.-$$Lambda$NmeuC2cC9-DXy8hurqekn8aqd0c
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.edab(bArr, i, i2, z, i3, bArr2, bArr3);
            }
        }, "edab", bArr4);
    }

    public static int b(final String str, final int i, final int i2) {
        return ((Integer) a((a<int>) new a() { // from class: com.coralline.sea.-$$Lambda$TdNtwKT0DMnFTVlwNs01NMYzr04
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Integer.valueOf(com.coralline.sea.a.b.so(str, i, i2));
            }
        }, "so", -1)).intValue();
    }

    public static long b(final String str, final String str2) {
        return ((Long) a((a<long>) new a() { // from class: com.coralline.sea.-$$Lambda$Bmy85mCIMvriTCCVwo3cJMH9nOA
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Long.valueOf(com.coralline.sea.a.b.ix(str, str2));
            }
        }, "ix", -1L)).longValue();
    }

    public static String b() {
        return (String) a(new a() { // from class: com.coralline.sea.-$$Lambda$vSpLBaVE0oQ3KpYun_zHVb_AvyE
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.bd();
            }
        }, "bd", c7.c);
    }

    public static String b(final Context context) {
        return (String) a((a<String>) new a() { // from class: com.coralline.sea.-$$Lambda$kup2CUlzyV3QgzTFuLYLOUMCgGM
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.cz(context);
            }
        }, "cz", c7.c);
    }

    public static String b(final String str) {
        return (String) a((a<String>) new a() { // from class: com.coralline.sea.-$$Lambda$Bs0UelEv6fiZk_gP3J37rnH3f2Q
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.gp(str);
            }
        }, "gp", c7.c);
    }

    public static int c(final int i, final byte[] bArr, final int i2) {
        return ((Integer) a((a<int>) new a() { // from class: com.coralline.sea.-$$Lambda$9_Wl4J2jcLXCLyJ5RUY1JrN1kQY
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Integer.valueOf(com.coralline.sea.a.b.sr(i, bArr, i2));
            }
        }, "sr", -1)).intValue();
    }

    public static String c() {
        return com.coralline.sea.a.b.cex();
    }

    public static int d(final int i, final byte[] bArr, final int i2) {
        return ((Integer) a((a<int>) new a() { // from class: com.coralline.sea.-$$Lambda$0wTI9aXOhLTSww3NDkxiFedpIeA
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Integer.valueOf(com.coralline.sea.a.b.sw(i, bArr, i2));
            }
        }, "sw", -1)).intValue();
    }

    public static String d() {
        return (String) a(new a() { // from class: com.coralline.sea.-$$Lambda$IuyPESF8sO7vUzqA54RTCj1bEk4
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.ft();
            }
        }, "ft", c7.c);
    }

    public static int e() {
        return ((Integer) a(new a() { // from class: com.coralline.sea.-$$Lambda$juj6Q67UjftfKYEEGeJEsHWobR0
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Integer.valueOf(com.coralline.sea.a.b.zb());
            }
        }, "zb", -1)).intValue();
    }

    public static void e(final int i) {
        a((a<int>) new a() { // from class: com.coralline.sea.-$$Lambda$o9bBz5nNiA-M7z0Snlg8I-ophuU
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Integer.valueOf(com.coralline.sea.a.b.zc(i));
            }
        }, "zc", -1);
    }

    public static int f(final int i) {
        return ((Integer) a((a<int>) new a() { // from class: com.coralline.sea.-$$Lambda$RY5DIN-QJP5ZRiMmW4tOy3fHsFE
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Integer.valueOf(com.coralline.sea.a.b.sc(i));
            }
        }, "sc", -1)).intValue();
    }

    public static String f() {
        return com.coralline.sea.a.b.clp(Build.VERSION.SDK_INT);
    }

    public static boolean f(final String str) {
        return ((Boolean) a((a<Boolean>) new a() { // from class: com.coralline.sea.-$$Lambda$YJclinxEZ4UWQp1G1q0ZmNSQ3ds
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Boolean.valueOf(com.coralline.sea.a.b.se(str));
            }
        }, "se", Boolean.FALSE)).booleanValue();
    }

    public static boolean f(final String str, final String str2) {
        return ((Boolean) a((a<Boolean>) new a() { // from class: com.coralline.sea.-$$Lambda$hfbKU561achzJbcf_yPjsnp_jDE
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Boolean.valueOf(com.coralline.sea.a.b.sb(str, str2));
            }
        }, "sb", Boolean.FALSE)).booleanValue();
    }

    public static String g() {
        return (String) a(new a() { // from class: com.coralline.sea.-$$Lambda$SxaXCFf5bfzwygDiFPqEsFgIECc
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.mr();
            }
        }, "mr", c7.c);
    }

    public static String h() {
        return com.coralline.sea.a.b.vpn();
    }

    public static boolean i() {
        return com.coralline.sea.a.b.cpsnu();
    }

    public static String j() {
        return (String) a(new a() { // from class: com.coralline.sea.-$$Lambda$TB3E1YjzN9gTiqZuv7Q7RtimZ1o
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.tm();
            }
        }, "tm", c7.c);
    }

    public static String k() {
        return com.coralline.sea.a.b.cmz();
    }

    public static String l() {
        return (String) a(new a() { // from class: com.coralline.sea.-$$Lambda$dGzNprROt_KnkSyaJBRZpyAIk8Y
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.cbr();
            }
        }, "cbr", c7.c);
    }

    public static String m() {
        return (String) a(new a() { // from class: com.coralline.sea.-$$Lambda$NtvKcYSADhfgmpQNXz9GjhwPZ4k
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.gk2();
            }
        }, "getKey2", c7.c);
    }

    public static String[] n() {
        return (String[]) a(new a() { // from class: com.coralline.sea.-$$Lambda$jPAoHlYVQoAfRFUp1Yf3GM7CWLU
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.gkai();
            }
        }, "getKeyAndIv", c7.c);
    }

    public static boolean o() {
        return com.coralline.sea.a.b.bcm();
    }

    public static String p() {
        return (String) a(new a() { // from class: com.coralline.sea.-$$Lambda$lac8z-CncoJGX6N8S_Z4cmp2U78
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.pbk();
            }
        }, "pbk", c7.c);
    }

    public static String q() {
        return (String) a(new a() { // from class: com.coralline.sea.-$$Lambda$Eo9iNGW05a0DC5caM4cjD6NUYJs
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.piv();
            }
        }, "piv", c7.c);
    }

    public static String r() {
        return (String) a(new a() { // from class: com.coralline.sea.-$$Lambda$o7VbiJDj3A1EihgtwrHJUo39WXg
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.pk();
            }
        }, "pk", c7.c);
    }

    public static int s() {
        return ((Integer) a(new a() { // from class: com.coralline.sea.-$$Lambda$nmYNa0OokeVz7ZWGlwi3Va8mJw0
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return Integer.valueOf(com.coralline.sea.a.b.ug());
            }
        }, "ug", -1)).intValue();
    }

    public static String t() {
        return (String) a(new a() { // from class: com.coralline.sea.-$$Lambda$tfXIligGJz5D-24ki8OJFe4mZIg
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.sm();
            }
        }, "sm", c7.c);
    }

    public static String u() {
        return (String) a(new a() { // from class: com.coralline.sea.-$$Lambda$eK8rl7EZxuYRjBm3jnihIcJ4-7k
            @Override // com.coralline.sea.i6.a
            public final Object a() {
                return com.coralline.sea.a.b.io();
            }
        }, "io", c7.c);
    }
}
