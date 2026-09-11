package com.coralline.sea;

import android.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.coralline.sea.m5;
import java.util.Objects;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class t3 {
    public static final String j = "hijack";
    public static boolean k;
    public DisplayMetrics a;
    public TextView b;
    public WindowManager c;
    public boolean e;
    public boolean f;
    public Context g;
    public Handler d = new Handler(Looper.getMainLooper());
    public Runnable h = new a();
    public Runnable i = new b();

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        @TargetApi(m5.b.x)
        public void run() {
            boolean z;
            t3 t3Var;
            Toast toastMakeText;
            Toast toast = null;
            try {
                Objects.toString(t3.this.b.getText());
                Objects.toString(t3.this.b.getParent());
                z = true;
            } catch (Throwable th) {
            }
            if (o9.a(t3.this.g)) {
                t3Var = t3.this;
            } else {
                int i = Build.VERSION.SDK_INT;
                if (i >= 23) {
                    t3 t3Var2 = t3.this;
                    if (!t3Var2.e || i >= 25) {
                        try {
                            boolean unused = t3.k = t3Var2.g.getApplicationInfo().targetSdkVersion >= 30;
                            toastMakeText = t3.k ? Toast.makeText(t3.this.g, c7.c, 1) : new Toast(t3.this.g);
                        } catch (Exception e) {
                            e = e;
                        }
                        try {
                            if (t3.k) {
                                toastMakeText.setText(c7.c + ((Object) t3.this.b.getText()));
                            } else {
                                TextView textView = t3.this.b;
                                if (textView != null) {
                                    String str = (String) textView.getText();
                                    t3 t3Var3 = t3.this;
                                    t3Var3.b = null;
                                    t3Var3.b = t3.b(t3Var3.g);
                                    t3.this.b.setText(str);
                                }
                                toastMakeText.setView(t3.this.b);
                            }
                            toastMakeText.setGravity(w6.g, 0, 0);
                            toastMakeText.show();
                        } catch (Exception e2) {
                            e = e2;
                            e.printStackTrace();
                            Objects.toString(t3.this.b.getText());
                        } catch (Throwable th2) {
                            toast = toastMakeText;
                            try {
                                Objects.toString(t3.this.b.getText());
                                toast.setText(c7.c + ((Object) t3.this.b.getText()));
                                toast.show();
                            } catch (Exception e3) {
                            }
                        }
                        t3 t3Var4 = t3.this;
                        t3Var4.d.postDelayed(t3Var4.i, ((long) w6.f) * 1000);
                    }
                }
                t3Var = t3.this;
                z = false;
            }
            t3Var.b(z);
            t3 t3Var42 = t3.this;
            t3Var42.d.postDelayed(t3Var42.i, ((long) w6.f) * 1000);
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            t3 t3Var;
            try {
                try {
                    Objects.toString(t3.this.b.getParent());
                    t3 t3Var2 = t3.this;
                    if (t3Var2.f) {
                        if (t3Var2.b.getParent() != null) {
                            t3 t3Var3 = t3.this;
                            t3Var3.c.removeViewImmediate(t3Var3.b);
                        }
                        t3.this.f = false;
                    }
                    t3Var = t3.this;
                    if (t3Var.b == null) {
                        return;
                    }
                } catch (Exception e) {
                    e.getMessage();
                    t3Var = t3.this;
                    if (t3Var.b == null) {
                        return;
                    }
                }
                t3Var.b = null;
            } catch (Throwable th) {
                t3 t3Var4 = t3.this;
                if (t3Var4.b != null) {
                    t3Var4.b = null;
                }
                throw th;
            }
        }
    }

    @SuppressLint({"ShowToast"})
    public t3(Context context) {
        boolean z = false;
        this.e = false;
        this.g = context;
        this.c = (WindowManager) context.getSystemService("window");
        this.a = context.getResources().getDisplayMetrics();
        k = context.getApplicationInfo().targetSdkVersion >= 30;
        String strB = o9.b();
        if (!TextUtils.isEmpty(strB) && strB.startsWith("EmotionUI_5.")) {
            z = true;
        }
        this.e = z;
    }

    public static synchronized TextView b(Context context) {
        TextView textView;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(displayMetrics.density * 10.0f);
        gradientDrawable.setStroke((int) (((double) displayMetrics.density) + 0.5d), 1083281809);
        gradientDrawable.setColor(-1060320052);
        textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.setMargins(2, 2, 2, 2);
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(-16777216);
        if (Build.VERSION.SDK_INT >= 16) {
            textView.setBackground(gradientDrawable);
        } else {
            textView.setBackgroundDrawable(gradientDrawable);
        }
        int i = (int) (displayMetrics.density * 8.0f);
        textView.setPadding(i, i, i, i);
        return textView;
    }

    public synchronized void a(String str) {
        if (this.b == null) {
            this.b = b(this.g);
        }
        this.b.setText(str);
        Objects.toString(this.b.getText());
        Objects.toString(this.b.getParent());
        this.d.removeCallbacks(this.h);
        this.d.removeCallbacks(this.i);
        this.d.postDelayed(this.h, 100L);
    }

    public synchronized void b() {
        Objects.toString(this.b.getParent());
        this.d.removeCallbacks(this.h);
        this.d.removeCallbacks(this.i);
        this.d.post(this.i);
    }

    public final void b(boolean z) {
        this.f = true;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = z ? Build.VERSION.SDK_INT >= 26 ? 2038 : 2010 : 2005;
        layoutParams.height = -2;
        layoutParams.width = -2;
        layoutParams.format = -3;
        layoutParams.windowAnimations = R.style.Animation.Toast;
        layoutParams.flags = 152;
        layoutParams.gravity = w6.g;
        layoutParams.y = this.a.heightPixels / 8;
        if (this.b.getParent() == null) {
            this.c.addView(this.b, layoutParams);
        } else {
            this.c.updateViewLayout(this.b, layoutParams);
        }
    }
}
