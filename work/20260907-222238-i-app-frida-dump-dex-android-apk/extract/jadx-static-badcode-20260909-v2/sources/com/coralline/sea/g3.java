package com.coralline.sea;

import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.view.Surface;
import java.util.Objects;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public final class g3 {
    public static final int e = 1;
    public static final int f = 2;
    public static final String g = "emulator";
    public static final int h = 12610;
    public EGLDisplay a;
    public EGLContext b;
    public EGLConfig c;
    public int d;

    public g3() {
        this(null, 0);
    }

    public g3(EGLContext eGLContext, int i) {
        EGLConfig eGLConfigB;
        EGLDisplay eGLDisplay = EGL14.EGL_NO_DISPLAY;
        this.a = eGLDisplay;
        this.b = EGL14.EGL_NO_CONTEXT;
        this.c = null;
        this.d = -1;
        if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
            throw new RuntimeException("EGL already set up");
        }
        eGLContext = eGLContext == null ? EGL14.EGL_NO_CONTEXT : eGLContext;
        EGLDisplay eGLDisplayEglGetDisplay = EGL14.eglGetDisplay(0);
        this.a = eGLDisplayEglGetDisplay;
        if (eGLDisplayEglGetDisplay == EGL14.EGL_NO_DISPLAY) {
            throw new RuntimeException("unable to get EGL14 display");
        }
        int[] iArr = new int[2];
        if (!EGL14.eglInitialize(eGLDisplayEglGetDisplay, iArr, 0, iArr, 1)) {
            this.a = null;
            throw new RuntimeException("unable to initialize EGL14");
        }
        if ((i & 2) != 0 && (eGLConfigB = b(i, 3)) != null) {
            EGLContext eGLContextEglCreateContext = EGL14.eglCreateContext(this.a, eGLConfigB, eGLContext, new int[]{12440, 3, 12344}, 0);
            if (EGL14.eglGetError() == 12288) {
                this.c = eGLConfigB;
                this.b = eGLContextEglCreateContext;
                this.d = 3;
            }
        }
        if (this.b == EGL14.EGL_NO_CONTEXT) {
            EGLConfig eGLConfigB2 = b(i, 2);
            if (eGLConfigB2 == null) {
                throw new RuntimeException("Unable to find a suitable EGLConfig");
            }
            EGLContext eGLContextEglCreateContext2 = EGL14.eglCreateContext(this.a, eGLConfigB2, eGLContext, new int[]{12440, 2, 12344}, 0);
            a("eglCreateContext");
            this.c = eGLConfigB2;
            this.b = eGLContextEglCreateContext2;
            this.d = 2;
        }
        EGL14.eglQueryContext(this.a, this.b, 12440, new int[1], 0);
    }

    public static void b(String str) {
        EGLDisplay eGLDisplayEglGetCurrentDisplay = EGL14.eglGetCurrentDisplay();
        EGLContext eGLContextEglGetCurrentContext = EGL14.eglGetCurrentContext();
        EGLSurface eGLSurfaceEglGetCurrentSurface = EGL14.eglGetCurrentSurface(12377);
        Objects.toString(eGLDisplayEglGetCurrentDisplay);
        Objects.toString(eGLContextEglGetCurrentContext);
        Objects.toString(eGLSurfaceEglGetCurrentSurface);
    }

    public int a() {
        return this.d;
    }

    public int a(EGLSurface eGLSurface, int i) {
        int[] iArr = new int[1];
        EGL14.eglQuerySurface(this.a, eGLSurface, i, iArr, 0);
        return iArr[0];
    }

    public EGLSurface a(int i, int i2) {
        EGLSurface eGLSurfaceEglCreatePbufferSurface = EGL14.eglCreatePbufferSurface(this.a, this.c, new int[]{12375, i, 12374, i2, 12344}, 0);
        a("eglCreatePbufferSurface");
        if (eGLSurfaceEglCreatePbufferSurface != null) {
            return eGLSurfaceEglCreatePbufferSurface;
        }
        throw new RuntimeException("surface was null");
    }

    public EGLSurface a(Object obj) {
        if (!(obj instanceof Surface) && !(obj instanceof SurfaceTexture)) {
            throw new RuntimeException("invalid surface: " + obj);
        }
        EGLSurface eGLSurfaceEglCreateWindowSurface = EGL14.eglCreateWindowSurface(this.a, this.c, obj, new int[]{12344}, 0);
        a("eglCreateWindowSurface");
        if (eGLSurfaceEglCreateWindowSurface != null) {
            return eGLSurfaceEglCreateWindowSurface;
        }
        throw new RuntimeException("surface was null");
    }

    public String a(int i) {
        return EGL14.eglQueryString(this.a, i);
    }

    public void a(EGLSurface eGLSurface, long j) {
        EGLExt.eglPresentationTimeANDROID(this.a, eGLSurface, j);
    }

    public void a(EGLSurface eGLSurface, EGLSurface eGLSurface2) {
        EGLDisplay eGLDisplay = this.a;
        EGLDisplay eGLDisplay2 = EGL14.EGL_NO_DISPLAY;
        if (!EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface2, this.b)) {
            throw new RuntimeException("eglMakeCurrent(draw,read) failed");
        }
    }

    public final void a(String str) {
        int iEglGetError = EGL14.eglGetError();
        if (iEglGetError == 12288) {
            return;
        }
        throw new RuntimeException(str + ": EGL error: 0x" + Integer.toHexString(iEglGetError));
    }

    public boolean a(EGLSurface eGLSurface) {
        return this.b.equals(EGL14.eglGetCurrentContext()) && eGLSurface.equals(EGL14.eglGetCurrentSurface(12377));
    }

    public final EGLConfig b(int i, int i2) {
        int[] iArr = {12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, i2 >= 3 ? 68 : 4, 12344, 0, 12344};
        if ((i & 1) != 0) {
            iArr[10] = 12610;
            iArr[11] = 1;
        }
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (EGL14.eglChooseConfig(this.a, iArr, 0, eGLConfigArr, 0, 1, new int[1], 0)) {
            return eGLConfigArr[0];
        }
        return null;
    }

    public void b() {
        EGLDisplay eGLDisplay = this.a;
        EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
        if (!EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public void b(EGLSurface eGLSurface) {
        EGLDisplay eGLDisplay = this.a;
        EGLDisplay eGLDisplay2 = EGL14.EGL_NO_DISPLAY;
        if (!EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.b)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public void c() {
        EGLDisplay eGLDisplay = this.a;
        if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
            EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
            EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroyContext(this.a, this.b);
            EGL14.eglReleaseThread();
            EGL14.eglTerminate(this.a);
        }
        this.a = EGL14.EGL_NO_DISPLAY;
        this.b = EGL14.EGL_NO_CONTEXT;
        this.c = null;
    }

    public void c(EGLSurface eGLSurface) {
        EGL14.eglDestroySurface(this.a, eGLSurface);
    }

    public boolean d(EGLSurface eGLSurface) {
        return EGL14.eglSwapBuffers(this.a, eGLSurface);
    }

    public void finalize() throws Throwable {
        try {
            if (this.a != EGL14.EGL_NO_DISPLAY) {
                c();
            }
        } finally {
            super.finalize();
        }
    }
}
