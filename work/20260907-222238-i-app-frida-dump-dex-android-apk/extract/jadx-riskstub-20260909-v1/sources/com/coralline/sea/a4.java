package com.coralline.sea;

import android.opengl.GLES20;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
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
