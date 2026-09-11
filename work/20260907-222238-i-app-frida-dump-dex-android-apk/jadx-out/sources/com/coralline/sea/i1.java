package com.coralline.sea;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import androidx.annotation.Nullable;
import com.coralline.sea.m5;
import com.coralline.sea.r1;
import java.util.LinkedList;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class i1 implements Window.Callback {
    public static final int e = 16;
    public static final String f = "touchData";
    public Window.Callback a;
    public LinkedList<JSONObject> b;
    public float d = 0.0f;
    public float c = 0.0f;

    public i1(Activity activity, Window.Callback callback, LinkedList<JSONObject> linkedList) {
        this.a = callback;
        this.b = linkedList;
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
        if (motionEvent.getAction() == 0) {
            try {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                float size = motionEvent.getSize();
                float pressure = motionEvent.getPressure();
                if (Math.abs(this.c - x) > 0.1d && Math.abs(this.d - y) > 0.1d) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("x", x);
                    jSONObject.put("y", y);
                    jSONObject.put("size", size);
                    jSONObject.put("pressure", pressure);
                    jSONObject.put(r1.k.e, System.currentTimeMillis());
                    synchronized (this.b) {
                        this.b.add(jSONObject);
                        if (this.b.size() > 16) {
                            this.b.removeFirst();
                        }
                    }
                    this.c = x;
                    this.d = y;
                }
            } catch (Exception e2) {
                e2.toString();
            }
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
