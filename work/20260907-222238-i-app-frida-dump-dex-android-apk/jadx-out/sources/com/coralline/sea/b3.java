package com.coralline.sea;

import android.graphics.Rect;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.coralline.sea.m5;
import proxy.android.os.ParcelProxy;

/* JADX INFO: loaded from: assets/RiskStub.dex */
@RequiresApi(api = m5.b.B)
public class b3 {
    public int a;
    public int b;
    public int c;
    public int d;
    public float e;
    public String f;
    public int g;
    public float h;
    public float i;

    @RequiresApi(api = m5.b.B)
    public static final class a {
        public static b3 a(ParcelProxy parcelProxy) {
            int i = Build.VERSION.SDK_INT;
            if (i >= 34) {
                return e(parcelProxy);
            }
            if (i == 33) {
                return d(parcelProxy);
            }
            if (i >= 31) {
                return c(parcelProxy);
            }
            if (i == 30) {
                return b(parcelProxy);
            }
            return null;
        }

        @RequiresApi(api = m5.b.B)
        public static b3 b(ParcelProxy parcelProxy) {
            int i = parcelProxy.i();
            if (i == -1 || i == 0) {
                return null;
            }
            parcelProxy.a(new Rect[4], Rect.CREATOR);
            return new b3(0, 0, 0, 0, 0.0f, null, 0, 0.0f, 0.0f);
        }

        @RequiresApi(api = m5.b.B)
        public static b3 c(ParcelProxy parcelProxy) {
            int i = parcelProxy.i();
            if (i == -1 || i == 0) {
                return null;
            }
            parcelProxy.a(new Rect[4], Rect.CREATOR);
            return new b3(parcelProxy.i(), parcelProxy.i(), -1, -1, parcelProxy.h(), parcelProxy.k(), parcelProxy.i(), parcelProxy.h(), 0.0f);
        }

        @RequiresApi(api = m5.b.B)
        public static b3 d(ParcelProxy parcelProxy) {
            int i = parcelProxy.i();
            if (i == -1 || i == 0) {
                return null;
            }
            parcelProxy.a(new Rect[4], Rect.CREATOR);
            return new b3(parcelProxy.i(), parcelProxy.i(), parcelProxy.i(), parcelProxy.i(), parcelProxy.h(), parcelProxy.k(), parcelProxy.i(), parcelProxy.h(), parcelProxy.h());
        }

        public static b3 e(ParcelProxy parcelProxy) {
            int i = parcelProxy.i();
            if (i == -1 || i == 0) {
                return null;
            }
            parcelProxy.a(new Rect[4], Rect.CREATOR);
            return new b3(parcelProxy.i(), parcelProxy.i(), parcelProxy.i(), parcelProxy.i(), parcelProxy.h(), parcelProxy.k(), parcelProxy.i(), parcelProxy.h(), parcelProxy.h());
        }
    }

    public b3(int i, int i2, int i3, int i4, float f, String str, int i5, float f2, float f3) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
        this.e = f;
        this.f = str;
        this.g = i5;
        this.h = f2;
        this.i = f3;
    }
}
