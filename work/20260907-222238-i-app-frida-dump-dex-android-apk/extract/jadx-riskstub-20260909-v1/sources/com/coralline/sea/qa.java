package com.coralline.sea;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.coralline.sea.m5;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class qa implements Window.Callback {
    public static final String v = "auto_click";
    public static final int w = 10;
    public Window.Callback a;
    public int c;
    public int d;
    public int e;
    public float f;
    public int g;
    public float h;
    public WindowManager k;
    public DisplayMetrics l;
    public final boolean n;
    public final m b = new m();
    public JSONArray i = new JSONArray();
    public long j = -1;
    public int m = 1;
    public JSONArray o = new JSONArray();
    public float p = 0.0f;
    public float q = 0.0f;
    public float r = 0.0f;
    public float s = 0.0f;
    public int t = 0;
    public int u = 0;

    public qa(Activity activity, Window.Callback callback) {
        this.a = callback;
        WindowManager windowManager = activity.getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        this.c = displayMetrics.widthPixels;
        this.d = displayMetrics.heightPixels;
        this.f = displayMetrics.density;
        this.g = displayMetrics.densityDpi;
        this.h = displayMetrics.scaledDensity;
        this.e = f3.a(activity);
        this.n = c0.b();
    }

    public static String a(int i) {
        try {
            return (String) MotionEvent.class.getMethod("toolTypeToString", Integer.TYPE).invoke(null, Integer.valueOf(i));
        } catch (Throwable th) {
            return String.valueOf(i);
        }
    }

    public final String a(long j) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(j));
    }

    public final void a(MotionEvent motionEvent) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("eventSequence", this.o.length() + 1);
            jSONObject.put("event.toString()", motionEvent.toString());
            jSONObject.put("event.getEventTime()", motionEvent.getEventTime());
            jSONObject.put("event.getX()", motionEvent.getX());
            jSONObject.put("event.getY()", motionEvent.getY());
            jSONObject.put("event.getRawX()", motionEvent.getRawX());
            jSONObject.put("event.getRawY()", motionEvent.getRawY());
            jSONObject.put("event.getPressure()", motionEvent.getPressure());
            jSONObject.put("event.getSize()", motionEvent.getSize());
            this.o.put(jSONObject);
        } catch (Throwable th) {
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public final void b(MotionEvent motionEvent) {
        int historySize;
        String str;
        int i;
        if (c0.g) {
            return;
        }
        int action = motionEvent.getAction();
        if (this.n) {
            if (action == 0) {
                this.o = new JSONArray();
            }
            a(motionEvent);
        }
        if (action == 0) {
            this.p = motionEvent.getX();
            this.q = motionEvent.getY();
            this.r = motionEvent.getPressure();
            this.s = motionEvent.getSize();
            historySize = 0;
            this.t = 0;
        } else {
            if (action == 1) {
                long downTime = motionEvent.getDownTime();
                long eventTime = motionEvent.getEventTime();
                long j = eventTime - downTime;
                long j2 = this.j;
                long j3 = j2 >= 0 ? downTime - j2 : -1L;
                JSONObject jSONObject = new JSONObject();
                try {
                    int i2 = this.m;
                    str = "0x";
                    try {
                        this.m = i2 + 1;
                        jSONObject.put("touchSequence", i2);
                        jSONObject.put("touch_X_Y", motionEvent.getX() + "," + motionEvent.getY());
                        jSONObject.put("downTime", downTime);
                        jSONObject.put("upTime", eventTime);
                        jSONObject.put("duration", j);
                        jSONObject.put("intervalSinceLastTouch", j3);
                        jSONObject.put("toolType", a(motionEvent.getToolType(0)));
                        jSONObject.put("pressure", motionEvent.getPressure());
                        jSONObject.put("size", motionEvent.getSize());
                        jSONObject.put("downPressure", this.r);
                        jSONObject.put("downSize", this.s);
                        jSONObject.put("flags", "0x" + Integer.toHexString(motionEvent.getFlags()));
                        jSONObject.put("rawX", (double) motionEvent.getRawX());
                        jSONObject.put("rawY", (double) motionEvent.getRawY());
                        if (this.n) {
                            jSONObject.put("debugData", this.o);
                        }
                    } catch (JSONException e) {
                    }
                } catch (JSONException e2) {
                    str = "0x";
                }
                this.i.length();
                if (this.i.length() >= 10) {
                    i = 0;
                    this.i.remove(0);
                } else {
                    i = 0;
                }
                this.i.length();
                this.i.put(jSONObject);
                this.j = eventTime;
                if (motionEvent.getAction() == 1) {
                    motionEvent.getToolType(i);
                    motionEvent.getSize();
                    motionEvent.getPressure();
                    if ((motionEvent.getSize() == 1.0f && motionEvent.getPressure() == 0.0f) || (motionEvent.getSize() == 1.0f && motionEvent.getPressure() == 1.0f && motionEvent.getToolType(0) != 1)) {
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        float f = this.e;
                        if (x < f || x > this.c - r5 || y < f || y > this.d - r5) {
                            return;
                        }
                        boolean z = true;
                        e0.a = true;
                        JSONObject jSONObject2 = new JSONObject();
                        e0.b = jSONObject2;
                        try {
                            jSONObject2.put("touchSize", motionEvent.getSize());
                            e0.b.put("touchPressure", motionEvent.getPressure());
                            e0.b.put("toolType", a(motionEvent.getToolType(0)));
                            e0.b.put("deviceId", motionEvent.getDeviceId());
                            e0.b.put("source", str + Integer.toHexString(motionEvent.getSource()));
                            e0.b.put("flags", "0x" + Integer.toHexString(motionEvent.getFlags()));
                            e0.b.put("edgeFlags", "0x" + Integer.toHexString(motionEvent.getEdgeFlags()));
                            e0.b.put("metaState", "0x" + Integer.toHexString(motionEvent.getMetaState()));
                            e0.b.put("buttonState", "0x" + Integer.toHexString(motionEvent.getButtonState()));
                            e0.b.put("xPrecision", (double) motionEvent.getXPrecision());
                            e0.b.put("yPrecision", (double) motionEvent.getYPrecision());
                            e0.b.put("moveEventCount", this.t);
                            e0.b.put("maxHistorySize", this.u);
                            e0.b.put("isAccessibilityEvent", (motionEvent.getFlags() & 2048) != 0);
                            e0.b.put("isInjectedFromAccessibilityTool", (motionEvent.getFlags() & 4096) != 0);
                            JSONObject jSONObject3 = e0.b;
                            if (this.p != x || this.q != y) {
                                z = false;
                            }
                            jSONObject3.put("isPreciseNoJitter", z);
                            e0.b.put("downPressure", this.r);
                            e0.b.put("downSize", this.s);
                            int i3 = Build.VERSION.SDK_INT;
                            if (i3 >= 23) {
                                e0.b.put("actionButton", motionEvent.getActionButton());
                            }
                            if (i3 >= 29) {
                                e0.b.put("classification", motionEvent.getClassification());
                            }
                            e0.b.put("rawX", motionEvent.getRawX());
                            e0.b.put("rawY", motionEvent.getRawY());
                            e0.b.put("touchMajor", motionEvent.getTouchMajor());
                            e0.b.put("touchMinor", motionEvent.getTouchMinor());
                            e0.b.put("toolMajor", motionEvent.getToolMajor());
                            e0.b.put("toolMinor", motionEvent.getToolMinor());
                            e0.b.put("pointerCount", motionEvent.getPointerCount());
                            e0.b.put("duration", j);
                            e0.b.put("orientation", motionEvent.getOrientation());
                            e0.b.put("displayId", c(motionEvent));
                            JSONObject jSONObjectD = d(motionEvent);
                            e0.b.put("inputDeviceName", jSONObjectD.optString("inputDeviceName", i2.b));
                            e0.b.put("inputDeviceIsVirtual", jSONObjectD.optBoolean("inputDeviceIsVirtual", false));
                            e0.b.put("touch_X_Y", x + "," + y);
                            e0.b.put("displayEdge_margin", this.e);
                            e0.b.put("displayWidth", this.c);
                            e0.b.put("displayHeight", this.d);
                            e0.b.put("displayDensity", (double) this.f);
                            e0.b.put("displayDensityDpi", this.g);
                            e0.b.put("displayScaledDensity", this.h);
                            e0.b.put("touchEvents", this.i);
                        } catch (JSONException e3) {
                        }
                        motionEvent.getSize();
                        motionEvent.getPressure();
                        c0.c();
                        return;
                    }
                    return;
                }
                return;
            }
            if (action != 2) {
                return;
            }
            this.t++;
            historySize = motionEvent.getHistorySize();
            if (historySize <= this.u) {
                return;
            }
        }
        this.u = historySize;
    }

    public final int c(MotionEvent motionEvent) {
        if (Build.VERSION.SDK_INT < 29) {
            return -1;
        }
        try {
            return ((Integer) MotionEvent.class.getMethod("getDisplayId", new Class[0]).invoke(motionEvent, new Object[0])).intValue();
        } catch (Throwable th) {
            return -1;
        }
    }

    @NonNull
    public final JSONObject d(MotionEvent motionEvent) {
        JSONObject jSONObject = new JSONObject();
        try {
            String name = i2.b;
            boolean zIsVirtual = false;
            InputDevice device = motionEvent.getDevice();
            if (device != null) {
                name = device.getName();
                zIsVirtual = device.isVirtual();
            }
            jSONObject.put("inputDeviceName", name);
            jSONObject.put("inputDeviceIsVirtual", zIsVirtual);
            return jSONObject;
        } catch (Throwable th) {
            return jSONObject;
        }
    }

    @Override // android.view.Window.Callback
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        return this.a.dispatchGenericMotionEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return this.a.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return this.a.dispatchKeyShortcutEvent(keyEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return this.a.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            b(motionEvent);
        } catch (Throwable th) {
        }
        return this.a.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        return this.a.dispatchTrackballEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public void onActionModeFinished(ActionMode actionMode) {
        this.a.onActionModeFinished(actionMode);
    }

    @Override // android.view.Window.Callback
    public void onActionModeStarted(ActionMode actionMode) {
        this.a.onActionModeStarted(actionMode);
    }

    @Override // android.view.Window.Callback
    public void onAttachedToWindow() {
        this.a.onAttachedToWindow();
    }

    @Override // android.view.Window.Callback
    public void onContentChanged() {
        this.a.onContentChanged();
    }

    @Override // android.view.Window.Callback
    public boolean onCreatePanelMenu(int i, Menu menu) {
        return this.a.onCreatePanelMenu(i, menu);
    }

    @Override // android.view.Window.Callback
    @Nullable
    public View onCreatePanelView(int i) {
        return this.a.onCreatePanelView(i);
    }

    @Override // android.view.Window.Callback
    public void onDetachedFromWindow() {
        this.a.onDetachedFromWindow();
    }

    @Override // android.view.Window.Callback
    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        return this.a.onMenuItemSelected(i, menuItem);
    }

    @Override // android.view.Window.Callback
    public boolean onMenuOpened(int i, Menu menu) {
        return this.a.onMenuOpened(i, menu);
    }

    @Override // android.view.Window.Callback
    public void onPanelClosed(int i, Menu menu) {
        this.a.onPanelClosed(i, menu);
    }

    @Override // android.view.Window.Callback
    public boolean onPreparePanel(int i, View view, Menu menu) {
        return this.a.onPreparePanel(i, view, menu);
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested() {
        return this.a.onSearchRequested();
    }

    @Override // android.view.Window.Callback
    @TargetApi(m5.b.u)
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return this.a.onSearchRequested(searchEvent);
    }

    @Override // android.view.Window.Callback
    public void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        this.a.onWindowAttributesChanged(layoutParams);
    }

    @Override // android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        this.a.onWindowFocusChanged(z);
    }

    @Override // android.view.Window.Callback
    @Nullable
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return this.a.onWindowStartingActionMode(callback);
    }

    @Override // android.view.Window.Callback
    @Nullable
    @TargetApi(m5.b.u)
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
        return this.a.onWindowStartingActionMode(callback, i);
    }
}
