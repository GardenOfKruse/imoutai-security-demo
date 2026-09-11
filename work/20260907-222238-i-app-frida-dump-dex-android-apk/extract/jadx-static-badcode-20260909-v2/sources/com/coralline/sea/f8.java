package com.coralline.sea;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.media.AudioManager;
import android.media.AudioRecordingConfiguration;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.coralline.sea.checkers.screen.RustDeskDetector;
import com.coralline.sea.m5;
import com.coralline.sea.util.hiddenapibypass.HiddenApiBypass;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class f8 {
    public static final String i = "ScreenShareManager";
    public static final String j = "screen_sharing";
    public static final String k = "youqu.android.todesk.ScreenProjection_CONN";
    public static final String l = "youqu.android.todesk.ScreenProjection_DISCONN";
    public static final String m = "config_checker_key";
    public static f8 n;
    public DisplayManager a;
    public Context b;
    public boolean c;
    public boolean d;
    public final Set<Integer> e = new CopyOnWriteArraySet();
    public BroadcastReceiver f = new a();
    public Handler g = new b(Looper.getMainLooper());
    public DisplayManager.DisplayListener h = new c();

    public class a extends BroadcastReceiver {
        public a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            f8 f8Var;
            boolean z;
            if (intent == null || TextUtils.isEmpty(intent.getAction())) {
                return;
            }
            if (f8.k.equals(intent.getAction())) {
                f8Var = f8.this;
                z = true;
            } else {
                if (!f8.l.equals(intent.getAction())) {
                    return;
                }
                f8Var = f8.this;
                z = false;
            }
            f8Var.c = z;
        }
    }

    public class b extends Handler {
        public b(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message message) {
            super.handleMessage(message);
        }
    }

    public class c implements DisplayManager.DisplayListener {
        public c() {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
            f8 f8Var = f8.this;
            if (f8Var.d) {
                f8Var.e.add(Integer.valueOf(i));
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            f8.this.e.remove(Integer.valueOf(i));
        }
    }

    public f8() {
        this.d = false;
        this.d = z1.a("screen_sharing").optBoolean("switch_call_back");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(k);
        intentFilter.addAction(l);
        if (n3.a().P >= 34) {
            n3.a().a.registerReceiver(this.f, intentFilter, 2);
        } else {
            n3.a().a.registerReceiver(this.f, intentFilter);
        }
        Context context = n3.a().a;
        this.b = context;
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        this.a = displayManager;
        displayManager.registerDisplayListener(this.h, new Handler(Looper.getMainLooper()));
        this.b = n3.a().a;
        h8.a();
    }

    public static Pair<Integer, Boolean> c(Context context) {
        Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        return new Pair<>(Integer.valueOf(intentRegisterReceiver.getIntExtra("level", -1)), Boolean.valueOf(intentRegisterReceiver.getIntExtra("plugged", -1) != 0));
    }

    public static f8 c() {
        if (n == null) {
            synchronized (f8.class) {
                n = new f8();
            }
        }
        return n;
    }

    public static long d(Context context) {
        long totalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == -1 ? 0L : TrafficStats.getTotalRxBytes() / 1024;
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
        }
        return (TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) != -1 ? TrafficStats.getTotalRxBytes() / 1024 : 0L) - totalRxBytes;
    }

    public static long e(Context context) {
        long totalTxBytes = TrafficStats.getUidTxBytes(context.getApplicationInfo().uid) == -1 ? 0L : TrafficStats.getTotalTxBytes() / 1024;
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
        }
        return (TrafficStats.getUidTxBytes(context.getApplicationInfo().uid) != -1 ? TrafficStats.getTotalTxBytes() / 1024 : 0L) - totalTxBytes;
    }

    public static boolean f(Context context) {
        if (((PowerManager) context.getSystemService("power")) == null || Build.VERSION.SDK_INT < 20) {
            return false;
        }
        return !r2.isInteractive();
    }

    public static boolean g(Context context) {
        return ((TelephonyManager) context.getSystemService(m1.j)).getCallState() != 0;
    }

    @RequiresApi(api = m5.b.z)
    public final Object a(Display display) throws IllegalAccessException {
        Field fieldA = a(HiddenApiBypass.getInstanceFields(Display.class), "mDisplayInfo");
        if (fieldA == null) {
            throw new IllegalStateException("mDisplayInfo field not found in Display class.");
        }
        fieldA.setAccessible(true);
        return fieldA.get(display);
    }

    public final Field a(List<Field> list, String str) {
        for (Field field : list) {
            if (field.getName().equals(str)) {
                return field;
            }
        }
        return null;
    }

    @RequiresApi(api = 17)
    public JSONObject a() {
        boolean z;
        String str;
        String str2;
        Context context = n3.a().a;
        boolean zA = RustDeskDetector.a(context);
        JSONObject jSONObjectB = b(context);
        if (jSONObjectB != null && e()) {
            return jSONObjectB;
        }
        JSONObject jSONObjectB2 = new h8().b(context);
        if (jSONObjectB2 != null && e()) {
            return jSONObjectB2;
        }
        if (zA && e()) {
            JSONObject jSONObjectA = z1.a("screen_sharing");
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            try {
                JSONArray jSONArray2 = jSONObjectA.getJSONArray("configurations");
                jSONArray2.toString();
                int i2 = RustDeskDetector.a[0];
                for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                    JSONObject jSONObject2 = jSONArray2.getJSONObject(i3);
                    int iOptInt = jSONObject2.optInt("server_port");
                    if (iOptInt == RustDeskDetector.a[0]) {
                        if (n3.T.c) {
                            str = "package";
                            str2 = "com.ynraw.pfywullcp";
                        } else {
                            str = "package";
                            str2 = i2.b;
                        }
                        jSONObject.put(str, str2);
                        jSONObject.put("app_name", jSONObject2.optString("app_name"));
                        jSONObject.put("name", jSONObject2.optString("app_name"));
                        jSONObject.put("display_id", -1);
                        jSONObject.put("display_info", i2.b);
                        jSONObject.put("port", iOptInt);
                        jSONObject.put("check_method", "rust_desk");
                        jSONArray.put(jSONObject);
                        if (jSONArray.length() > 0) {
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put("detail", jSONArray);
                            jSONObject3.toString();
                            return jSONObject3;
                        }
                    }
                }
            } catch (Exception e) {
                Log.getStackTraceString(e);
            }
        }
        JSONObject jSONObjectB3 = b();
        if (jSONObjectB3 != null && e()) {
            return jSONObjectB3;
        }
        i8.a();
        synchronized (i8.class) {
            z = i8.d;
        }
        if (z) {
            JSONObject jSONObject4 = new JSONObject();
            JSONArray jSONArray3 = new JSONArray();
            try {
                jSONObject4.put("package", i2.b);
                jSONObject4.put("app_name", i2.b);
                jSONObject4.put("name", "未知应用");
                jSONObject4.put("display_id", -1);
                jSONObject4.put("display_info", i2.b);
                jSONObject4.put("check_method", "system_api");
                jSONArray3.put(jSONObject4);
                if (jSONArray3.length() > 0) {
                    JSONObject jSONObject5 = new JSONObject();
                    jSONObject5.put("detail", jSONArray3);
                    return jSONObject5;
                }
            } catch (Exception e2) {
                Log.getStackTraceString(e2);
            }
        }
        return jSONObjectB3;
    }

    public JSONObject a(Display display, String str, int i2, int i3, int i4, String str2) {
        JSONObject jSONObject = new JSONObject();
        if (display == null) {
            try {
                jSONObject.put("package", i2.b);
                jSONObject.put("app_name", i2.b);
                jSONObject.put("name", i2.b);
                jSONObject.put("display_id", -1);
                jSONObject.put("display_info", i2.b);
                jSONObject.put("check_method", str2);
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(jSONObject);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("detail", jSONArray);
                return jSONObject2;
            } catch (Throwable th) {
            }
        }
        if (TextUtils.equals(str, c7.c)) {
            str = (String) q7.a(display).f("mOwnerPackageName");
        }
        if (TextUtils.equals(str, n3.a().a.getPackageName()) || TextUtils.equals(display.getName(), "game_idle")) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        try {
            if (TextUtils.isEmpty(str)) {
                jSONObject.put("package", i2.b);
                jSONObject.put("app_name", i2.b);
            } else {
                jSONObject.put("package", str);
                jSONObject.put("app_name", v7.a(str));
            }
            jSONObject.put("name", display.getName());
            jSONObject.put("display_id", display.getDisplayId());
            jSONObject.put("display_info", display.toString());
            jSONObject.put("state", i2);
            jSONObject.put("type", i3);
            jSONObject.put("smallest_nominal_app_width", i4);
            jSONObject.put("check_method", str2);
            jSONArray2.put(jSONObject);
            if (jSONArray2.length() > 0) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("detail", jSONArray2);
                return jSONObject3;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public final boolean a(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 24) {
            List<AudioRecordingConfiguration> activeRecordingConfigurations = audioManager.getActiveRecordingConfigurations();
            Objects.toString(activeRecordingConfigurations);
            if (activeRecordingConfigurations.size() > 0) {
                z = true;
            }
        }
        audioManager.getMode();
        if (audioManager.getMode() == 3) {
            return true;
        }
        return z;
    }

    @RequiresApi(api = m5.b.z)
    public String b(Display display) {
        try {
            Object objA = a(display);
            Field fieldA = a(HiddenApiBypass.getInstanceFields(objA.getClass()), "ownerPackageName");
            if (fieldA == null) {
                throw new IllegalStateException("mOwnerPackageName field not found in mDisplayInfo.");
            }
            fieldA.setAccessible(true);
            return (String) fieldA.get(objA);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public final JSONObject b() {
        if (!this.d || this.e.isEmpty() || this.a == null) {
            return null;
        }
        try {
            for (Integer num : this.e) {
                if (this.a.getDisplay(num.intValue()) == null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("package", i2.b);
                    jSONObject.put("app_name", i2.b);
                    jSONObject.put("name", i2.b);
                    jSONObject.put("display_id", num);
                    jSONObject.put("display_info", i2.b);
                    jSONObject.put("check_method", "added_display");
                    JSONArray jSONArray = new JSONArray();
                    jSONArray.put(jSONObject);
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("detail", jSONArray);
                    return jSONObject2;
                }
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    @RequiresApi(api = 17)
    public final JSONObject b(Context context) {
        try {
            int i2 = 0;
            if (Build.VERSION.SDK_INT > 29) {
                Display[] displays = this.a.getDisplays();
                int length = displays.length;
                while (i2 < length) {
                    Display display = displays[i2];
                    String strB = b(display);
                    display.getDisplayId();
                    int iD = d(display);
                    int state = display.getState();
                    int iC = c(display);
                    if (iD == 5 && state == 2 && iC > 20) {
                        return a(display, strB, state, iD, iC, "virtual_display");
                    }
                    i2++;
                }
            } else {
                Display[] displays2 = ((DisplayManager) context.getSystemService("display")).getDisplays();
                int length2 = displays2.length;
                while (i2 < length2) {
                    Display display2 = displays2[i2];
                    try {
                        Object objF = q7.a(display2).f("mDisplayInfo");
                        int iIntValue = ((Integer) q7.a(objF).f("type")).intValue();
                        int iIntValue2 = ((Integer) q7.a(objF).f("smallestNominalAppWidth")).intValue();
                        if (Build.VERSION.SDK_INT >= 20) {
                            display2.getDisplayId();
                            display2.getState();
                            if (iIntValue == 5 && display2.getState() == 2 && iIntValue2 > 20) {
                                return a(display2, c7.c, display2.getState(), iIntValue, iIntValue2, "virtual_display");
                            }
                        } else {
                            continue;
                        }
                    } catch (Exception e) {
                    }
                    i2++;
                }
            }
            if (this.c) {
                return d();
            }
            return null;
        } catch (Exception e2) {
            return null;
        }
    }

    @RequiresApi(api = m5.b.z)
    public int c(Display display) {
        try {
            Object objA = a(display);
            Field fieldA = a(HiddenApiBypass.getInstanceFields(objA.getClass()), "smallestNominalAppWidth");
            if (fieldA == null) {
                throw new IllegalStateException("uniqueId field not found in mDisplayInfo.");
            }
            fieldA.setAccessible(true);
            return ((Integer) fieldA.get(objA)).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    @RequiresApi(api = m5.b.z)
    public int d(Display display) {
        try {
            Object objA = a(display);
            Field fieldA = a(HiddenApiBypass.getInstanceFields(objA.getClass()), "type");
            if (fieldA == null) {
                throw new IllegalStateException("uniqueId field not found in mDisplayInfo.");
            }
            fieldA.setAccessible(true);
            return ((Integer) fieldA.get(objA)).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public final JSONObject d() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("package", "youqu.android.todesk");
        jSONObject.put("app_name", "ToDesk");
        jSONObject.put("check_method", "todesk_broadcast");
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(jSONObject);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("detail", jSONArray);
        return jSONObject2;
    }

    @RequiresApi(api = m5.b.z)
    public String e(Display display) {
        try {
            Object objA = a(display);
            Field fieldA = a(HiddenApiBypass.getInstanceFields(objA.getClass()), "uniqueId");
            if (fieldA == null) {
                throw new IllegalStateException("uniqueId field not found in mDisplayInfo.");
            }
            fieldA.setAccessible(true);
            return (String) fieldA.get(objA);
        } catch (Exception e) {
            return null;
        }
    }

    public final boolean e() {
        if (Build.VERSION.SDK_INT < 35) {
            return true;
        }
        return i8.b();
    }
}
