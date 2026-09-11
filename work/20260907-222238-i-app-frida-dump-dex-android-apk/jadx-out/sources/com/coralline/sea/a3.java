package com.coralline.sea;

import android.os.Build;
import java.util.Arrays;
import proxy.android.os.ParcelProxy;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class a3 {

    public interface a<T> {
        b a(ParcelProxy parcelProxy);
    }

    public static class b {
        public static final a<b> g = new a();
        public int a;
        public int b;
        public int c;
        public float d;
        public float[] e;
        public int[] f;

        public class a implements a<b> {
            @Override // com.coralline.sea.a3.a
            public b a(ParcelProxy parcelProxy) {
                return new b(parcelProxy);
            }
        }

        public b(ParcelProxy parcelProxy) {
            int i = Build.VERSION.SDK_INT;
            if (i >= 34) {
                this.a = parcelProxy.i();
                this.b = parcelProxy.i();
                this.c = parcelProxy.i();
                this.d = parcelProxy.h();
                this.e = parcelProxy.b();
                this.f = parcelProxy.c();
            }
            if (i >= 31) {
                this.a = parcelProxy.i();
                this.b = parcelProxy.i();
                this.c = parcelProxy.i();
                this.d = parcelProxy.h();
                this.e = parcelProxy.b();
                return;
            }
            if (i >= 30) {
                this.a = parcelProxy.i();
                this.b = parcelProxy.i();
                this.c = parcelProxy.i();
                this.d = parcelProxy.h();
            }
        }

        public String toString() {
            return "Mode{mModeId=" + this.a + ", mWidth=" + this.b + ", mHeight=" + this.c + ", mRefreshRate=" + this.d + ", mAlternativeRefreshRates=" + Arrays.toString(this.e) + ", mSupportedHdrTypes=" + Arrays.toString(this.f) + '}';
        }
    }
}
