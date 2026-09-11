package com.coralline.sea;

import android.app.Activity;
import android.view.Window;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class e0 {
    public static boolean a = false;
    public static JSONObject b;

    public static void a(Activity activity) {
        Window window = activity.getWindow();
        if (window.getCallback().getClass().equals(qa.class)) {
            return;
        }
        window.setCallback(new qa(activity, window.getCallback()));
    }
}
