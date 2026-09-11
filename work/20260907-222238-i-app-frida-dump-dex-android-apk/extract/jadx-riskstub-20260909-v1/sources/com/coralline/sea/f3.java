package com.coralline.sea;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import android.view.WindowManager;
import java.util.Iterator;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class f3 {
    public static int a(Context context) {
        int iOptInt;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float f = displayMetrics.density;
        int i = displayMetrics.widthPixels;
        String lowerCase = Build.MANUFACTURER.toLowerCase();
        String lowerCase2 = Build.MODEL.toLowerCase();
        try {
            JSONObject jSONObjectOptJSONObject = z1.a("auto_click").optJSONObject("edge_margin");
            Objects.toString(jSONObjectOptJSONObject);
            iOptInt = jSONObjectOptJSONObject.optInt("default_margin_dp", 50);
            try {
                JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject("brands");
                if (jSONObjectOptJSONObject2 != null) {
                    Iterator<String> itKeys = jSONObjectOptJSONObject2.keys();
                    while (true) {
                        if (!itKeys.hasNext()) {
                            break;
                        }
                        String next = itKeys.next();
                        JSONObject jSONObjectOptJSONObject3 = jSONObjectOptJSONObject2.optJSONObject(next);
                        if (jSONObjectOptJSONObject3 != null) {
                            int iOptInt2 = jSONObjectOptJSONObject3.optInt("base_margin_dp", iOptInt);
                            if (next.equalsIgnoreCase("samsung") && lowerCase.contains("samsung")) {
                                iOptInt = iOptInt2;
                                break;
                            }
                            JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject3.optJSONArray("models");
                            if (lowerCase.contains(next.toLowerCase()) && jSONArrayOptJSONArray != null) {
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= jSONArrayOptJSONArray.length()) {
                                        break;
                                    }
                                    if (lowerCase2.contains(jSONArrayOptJSONArray.optString(i2).toLowerCase())) {
                                        iOptInt = iOptInt2;
                                        break;
                                    }
                                    i2++;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
        } catch (Exception e2) {
            iOptInt = 50;
        }
        if (displayMetrics.densityDpi >= 480 || i >= 1440) {
            iOptInt += 5;
        }
        if (b(context)) {
            iOptInt += 5;
        }
        return (int) ((iOptInt * f) + 0.5f);
    }

    public static boolean a(String str) {
        if (str.contains("honor")) {
            return str.contains("magic") || str.contains("v40") || str.contains("x40") || str.contains("x50");
        }
        return false;
    }

    @SuppressLint({"ObsoleteSdkInt"})
    public static boolean b(Context context) {
        WindowInsets rootWindowInsets;
        int i = Build.VERSION.SDK_INT;
        if (i >= 28) {
            try {
                WindowManager windowManager = (WindowManager) context.getSystemService("window");
                if (windowManager == null) {
                    return false;
                }
                if (i >= 30) {
                    return !windowManager.getCurrentWindowMetrics().getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.displayCutout()).equals(Insets.NONE);
                }
                if ((context instanceof Activity) && (rootWindowInsets = ((Activity) context).getWindow().getDecorView().getRootWindowInsets()) != null) {
                    DisplayCutout displayCutout = rootWindowInsets.getDisplayCutout();
                    return (displayCutout == null || displayCutout.getBoundingRects().isEmpty()) ? false : true;
                }
                Display defaultDisplay = windowManager.getDefaultDisplay();
                Rect rect = new Rect();
                defaultDisplay.getRectSize(rect);
                Point point = new Point();
                defaultDisplay.getRealSize(point);
                return rect.width() < point.x || rect.height() < point.y;
            } catch (Exception e) {
            }
        }
        return false;
    }

    public static boolean b(String str) {
        return str.contains("mate30") || str.contains("mate40") || str.contains("p40") || str.contains("p50") || str.contains("p60") || str.contains("nova10") || str.contains("x3") || str.contains("x5");
    }

    public static boolean c(String str) {
        return str.contains("findx2") || str.contains("findx3") || str.contains("findx5") || str.contains("reno9") || str.contains("reno10");
    }

    public static boolean d(String str) {
        return str.contains("x60") || str.contains("x70") || str.contains("x80") || str.contains("x90") || str.contains("x100") || str.contains("nex") || str.contains("iqoo");
    }

    public static boolean e(String str) {
        return str.contains("mi10") || str.contains("mi11") || str.contains("mi12") || str.contains("mi13") || str.contains("civi") || str.contains("mix");
    }
}
