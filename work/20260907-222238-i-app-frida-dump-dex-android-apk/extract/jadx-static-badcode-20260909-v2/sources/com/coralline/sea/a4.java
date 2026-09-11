package com.coralline.sea;

import android.opengl.GLES20;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class a4 {
    public static void a(String str) {
        int iGlGetError = GLES20.glGetError();
        if (iGlGetError == 0) {
            return;
        }
        throw new RuntimeException(str + ": glError 0x" + Integer.toHexString(iGlGetError));
    }

    public static boolean a() {
        return q7.g("android.opengl.GLES20") && q7.g("android.opengl.EGL14") && q7.g("android.opengl.EGLConfig") && q7.g("android.opengl.EGLContext") && q7.g("android.opengl.EGLDisplay") && q7.g("android.opengl.EGLExt") && q7.g("android.opengl.EGLSurface");
    }
}
