package com.coralline.sea;

import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.RequiresApi;
import com.coralline.sea.a3;
import com.coralline.sea.b3;
import com.coralline.sea.m5;
import proxy.android.os.ParcelProxy;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class c3 {
    public float A;
    public long B;
    public long C;
    public int D;
    public int E;
    public int F;
    public String G;
    public String H;
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public Object f;
    public Object g;
    public String h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m;
    public int n;
    public int o;
    public int p;
    public b3 q;
    public int r;
    public int s;
    public float t;
    public int u;
    public int v;
    public Object w;
    public boolean x;
    public int y;
    public float z;

    public c3(ParcelProxy parcelProxy) {
        a(parcelProxy);
    }

    public String a() {
        return this.G;
    }

    public final void a(ParcelProxy parcelProxy) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 34) {
            e(parcelProxy);
            return;
        }
        if (i == 33) {
            d(parcelProxy);
        } else if (i >= 31) {
            c(parcelProxy);
        } else if (i == 30) {
            b(parcelProxy);
        }
    }

    public int b() {
        return this.k;
    }

    @RequiresApi(api = m5.b.B)
    public final void b(ParcelProxy parcelProxy) {
        this.a = parcelProxy.i();
        this.b = parcelProxy.i();
        this.c = parcelProxy.i();
        this.d = parcelProxy.i();
        this.f = parcelProxy.a((ClassLoader) null);
        this.g = parcelProxy.a((ClassLoader) null);
        this.h = parcelProxy.l();
        this.i = parcelProxy.i();
        this.j = parcelProxy.i();
        this.k = parcelProxy.i();
        this.l = parcelProxy.i();
        this.m = parcelProxy.i();
        this.n = parcelProxy.i();
        this.o = parcelProxy.i();
        this.p = parcelProxy.i();
        this.q = b3.a.a(parcelProxy);
        this.r = parcelProxy.i();
        this.s = parcelProxy.i();
        this.u = parcelProxy.i();
        int i = parcelProxy.i();
        for (int i2 = 0; i2 < i; i2++) {
            a3.b.g.a(parcelProxy);
        }
        this.v = parcelProxy.i();
        int i3 = parcelProxy.i();
        for (int i4 = 0; i4 < i3; i4++) {
            parcelProxy.i();
        }
        this.w = parcelProxy.a((ClassLoader) null);
        this.x = parcelProxy.e();
        this.y = parcelProxy.i();
        this.z = parcelProxy.h();
        this.A = parcelProxy.h();
        this.B = parcelProxy.j();
        this.C = parcelProxy.j();
        this.D = parcelProxy.i();
        this.F = parcelProxy.i();
        this.G = parcelProxy.l();
        this.H = parcelProxy.l();
    }

    public int c() {
        return this.c;
    }

    @RequiresApi(api = m5.b.B)
    public final void c(ParcelProxy parcelProxy) {
        this.a = parcelProxy.i();
        this.b = parcelProxy.i();
        this.c = parcelProxy.i();
        this.d = parcelProxy.i();
        this.e = parcelProxy.i();
        this.f = parcelProxy.a((ClassLoader) null);
        this.g = parcelProxy.a((ClassLoader) null);
        this.h = parcelProxy.l();
        this.i = parcelProxy.i();
        this.j = parcelProxy.i();
        this.k = parcelProxy.i();
        this.l = parcelProxy.i();
        this.m = parcelProxy.i();
        this.n = parcelProxy.i();
        this.o = parcelProxy.i();
        this.p = parcelProxy.i();
        this.q = b3.a.a(parcelProxy);
        this.r = parcelProxy.i();
        this.s = parcelProxy.i();
        this.u = parcelProxy.i();
        int i = parcelProxy.i();
        for (int i2 = 0; i2 < i; i2++) {
            a3.b.g.a(parcelProxy);
        }
        this.v = parcelProxy.i();
        int i3 = parcelProxy.i();
        for (int i4 = 0; i4 < i3; i4++) {
            parcelProxy.i();
        }
        this.w = parcelProxy.a((ClassLoader) null);
        this.x = parcelProxy.e();
        this.y = parcelProxy.i();
        this.z = parcelProxy.h();
        this.A = parcelProxy.h();
        this.B = parcelProxy.j();
        this.C = parcelProxy.j();
        this.D = parcelProxy.i();
        this.F = parcelProxy.i();
        this.G = parcelProxy.l();
        this.H = parcelProxy.l();
    }

    public String d() {
        return this.H;
    }

    @RequiresApi(api = m5.b.B)
    public final void d(ParcelProxy parcelProxy) {
        Class<?> cls;
        Class<?> cls2;
        Class<?> cls3;
        this.a = parcelProxy.i();
        this.b = parcelProxy.i();
        this.c = parcelProxy.i();
        this.d = parcelProxy.i();
        this.e = parcelProxy.i();
        try {
            cls = Class.forName("android.view.DisplayAddress");
        } catch (Exception e) {
            cls = null;
        }
        this.f = parcelProxy.a((ClassLoader) null, cls);
        try {
            cls2 = Class.forName("android.hardware.display.DeviceProductInfo");
        } catch (Exception e2) {
            cls2 = null;
        }
        this.g = parcelProxy.a((ClassLoader) null, cls2);
        this.h = parcelProxy.l();
        this.i = parcelProxy.i();
        this.j = parcelProxy.i();
        this.k = parcelProxy.i();
        this.l = parcelProxy.i();
        this.m = parcelProxy.i();
        this.n = parcelProxy.i();
        this.o = parcelProxy.i();
        this.p = parcelProxy.i();
        this.q = b3.a.a(parcelProxy);
        this.r = parcelProxy.i();
        this.s = parcelProxy.i();
        this.u = parcelProxy.i();
        int i = parcelProxy.i();
        for (int i2 = 0; i2 < i; i2++) {
            a3.b.g.a(parcelProxy);
        }
        this.v = parcelProxy.i();
        int i3 = parcelProxy.i();
        for (int i4 = 0; i4 < i3; i4++) {
            parcelProxy.i();
        }
        try {
            cls3 = Class.forName("android.view.Display$HdrCapabilities");
        } catch (Exception e3) {
            cls3 = null;
        }
        this.w = parcelProxy.a((ClassLoader) null, cls3);
        this.x = parcelProxy.e();
        this.y = parcelProxy.i();
        this.z = parcelProxy.h();
        this.A = parcelProxy.h();
        this.B = parcelProxy.j();
        this.C = parcelProxy.j();
        this.D = parcelProxy.i();
        this.F = parcelProxy.i();
        this.G = parcelProxy.l();
        this.H = parcelProxy.l();
    }

    @RequiresApi(api = m5.b.B)
    public final void e(ParcelProxy parcelProxy) {
        Class<?> cls;
        Class<?> cls2;
        this.a = parcelProxy.i();
        this.b = parcelProxy.i();
        this.c = parcelProxy.i();
        this.d = parcelProxy.i();
        this.e = parcelProxy.i();
        try {
            cls = Class.forName("android.view.DisplayAddress");
        } catch (Exception e) {
            e.printStackTrace();
            cls = null;
        }
        this.f = parcelProxy.a((ClassLoader) null, cls);
        try {
            cls2 = Class.forName("android.hardware.display.DeviceProductInfo");
        } catch (Exception e2) {
            e2.printStackTrace();
            cls2 = null;
        }
        this.g = parcelProxy.a((ClassLoader) null, cls2);
        this.h = parcelProxy.l();
        this.i = parcelProxy.i();
        this.j = parcelProxy.i();
        this.k = parcelProxy.i();
        this.l = parcelProxy.i();
        this.m = parcelProxy.i();
        this.n = parcelProxy.i();
        this.o = parcelProxy.i();
        this.p = parcelProxy.i();
        this.q = b3.a.a(parcelProxy);
        this.r = parcelProxy.i();
        this.s = parcelProxy.i();
        this.t = parcelProxy.h();
        this.u = parcelProxy.i();
        int i = parcelProxy.i();
        if (i > 0) {
            i--;
        }
        a3.b[] bVarArr = new a3.b[i];
        for (int i2 = 0; i2 < i; i2++) {
            a3.b bVarA = a3.b.g.a(parcelProxy);
            bVarArr[i2] = bVarA;
            bVarA.toString();
        }
        f(parcelProxy);
    }

    public final void f(ParcelProxy parcelProxy) {
        int iDataPosition = parcelProxy.d().dataPosition();
        for (int i = 0; i < 32; i += 4) {
            parcelProxy.d().setDataPosition(iDataPosition + i);
            try {
                g(parcelProxy);
            } catch (Exception e) {
            }
            if (!TextUtils.isEmpty(this.H)) {
                return;
            }
        }
    }

    public final void g(ParcelProxy parcelProxy) {
        int i;
        int i2 = parcelProxy.i();
        this.v = i2;
        if (i2 < 0 || i2 > 10 || (i = parcelProxy.i()) < 0 || i > 10) {
            return;
        }
        for (int i3 = 0; i3 < i; i3++) {
            parcelProxy.i();
        }
        try {
            this.w = parcelProxy.a((ClassLoader) null, Class.forName("android.view.Display$HdrCapabilities"));
            this.x = parcelProxy.e();
            this.y = parcelProxy.i();
            this.z = parcelProxy.h();
            this.A = parcelProxy.h();
            this.B = parcelProxy.j();
            this.C = parcelProxy.j();
            this.D = parcelProxy.i();
            this.E = parcelProxy.i();
            this.F = parcelProxy.i();
            this.G = parcelProxy.l();
            this.H = parcelProxy.l();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            e.toString();
        }
    }

    public String toString() {
        return "DisplayInfo{layerStack=" + this.a + ", flags=" + this.b + ", type=" + this.c + ", displayId=" + this.d + ", displayGroupId=" + this.e + ", name='" + this.h + "', appWidth=" + this.i + ", appHeight=" + this.j + ", logicalWidth=" + this.o + ", logicalHeight=" + this.p + ", rotation=" + this.r + ", modeId=" + this.s + ", defaultModeId=" + this.u + ", colorMode=" + this.v + ", hdrCapabilities=" + this.w + ", minimalPostProcessingSupported=" + this.x + ", logicalDensityDpi=" + this.y + ", physicalXDpi=" + this.z + ", physicalYDpi=" + this.A + ", ownerUid=" + this.F + ", ownerPackageName=" + this.G + ", uniqueId='" + this.H + "'}";
    }
}
