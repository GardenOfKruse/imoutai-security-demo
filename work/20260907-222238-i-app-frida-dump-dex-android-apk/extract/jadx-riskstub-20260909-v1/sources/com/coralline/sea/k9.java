package com.coralline.sea;

import android.content.Context;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class k9 {
    public static final String a = "SubscriptionUtil";

    public static List<SubscriptionInfo> a() {
        ArrayList arrayList = new ArrayList();
        try {
            return (List) q7.a((SubscriptionManager) n3.a().a.getSystemService("telephony_subscription_service")).b("getAllSubscriptionInfoList").c();
        } catch (Throwable th) {
            th.getMessage();
            return arrayList;
        }
    }

    public static boolean a(Context context, String str) {
        return Build.VERSION.SDK_INT < 16 || context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }
}
