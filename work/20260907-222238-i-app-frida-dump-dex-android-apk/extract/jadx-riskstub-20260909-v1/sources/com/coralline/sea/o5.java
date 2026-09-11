package com.coralline.sea;

import android.content.Context;
import android.location.Location;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class o5 {
    public Location a;
    public Location b;
    public long c;
    public Location d;

    public static o5 a() {
        Context context = n3.a().a;
        o5 o5Var = new o5();
        o5Var.c = System.currentTimeMillis();
        o5Var.a = q5.a(context, "gps");
        o5Var.b = q5.a(context, "network", true);
        o5Var.d = w3.b().a(o5Var.a, o5Var.b) ? o5Var.a : o5Var.b;
        return o5Var;
    }

    public final String a(long j) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(j));
    }

    public final String a(Location location) {
        return location == null ? i2.b : String.format(Locale.CHINA, "latitude : %f, longitude : %f, speed : %f, accuracy : %f, time : %s", Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude()), Float.valueOf(location.getSpeed()), Float.valueOf(location.getAccuracy()), a(location.getTime()));
    }
}
