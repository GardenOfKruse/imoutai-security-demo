package com.coralline.sea;

import android.graphics.Bitmap;
import android.opengl.EGL14;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class h3 {
    public static final String e = "emulator";
    public g3 a;
    public EGLSurface b = EGL14.EGL_NO_SURFACE;
    public int c = -1;
    public int d = -1;

    public h3(g3 g3Var) {
        this.a = g3Var;
    }

    public int a() {
        int i = this.d;
        return i < 0 ? this.a.a(this.b, 12374) : i;
    }

    public void a(int i, int i2) {
        if (this.b != EGL14.EGL_NO_SURFACE) {
            throw new IllegalStateException("surface already created");
        }
        this.b = this.a.a(i, i2);
        this.c = i;
        this.d = i2;
    }

    public void a(long j) {
        this.a.a(this.b, j);
    }

    public void a(h3 h3Var) {
        this.a.a(this.b, h3Var.b);
    }

    public void a(File file) throws Throwable {
        if (!this.a.a(this.b)) {
            throw new RuntimeException("Expected EGL context/surface is not current");
        }
        String string = file.toString();
        int iB = b();
        int iA = a();
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(iB * iA * 4);
        byteBufferAllocateDirect.order(ByteOrder.LITTLE_ENDIAN);
        GLES20.glReadPixels(0, 0, iB, iA, 6408, 5121, byteBufferAllocateDirect);
        a4.a("glReadPixels");
        byteBufferAllocateDirect.rewind();
        BufferedOutputStream bufferedOutputStream = null;
        try {
            BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(string));
            try {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iB, iA, Bitmap.Config.ARGB_8888);
                bitmapCreateBitmap.copyPixelsFromBuffer(byteBufferAllocateDirect);
                bitmapCreateBitmap.compress(Bitmap.CompressFormat.PNG, 90, bufferedOutputStream2);
                bitmapCreateBitmap.recycle();
                bufferedOutputStream2.close();
            } catch (Throwable th) {
                th = th;
                bufferedOutputStream = bufferedOutputStream2;
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void a(Object obj) {
        if (this.b != EGL14.EGL_NO_SURFACE) {
            throw new IllegalStateException("surface already created");
        }
        this.b = this.a.a(obj);
    }

    public int b() {
        int i = this.c;
        return i < 0 ? this.a.a(this.b, 12375) : i;
    }

    public void c() {
        this.a.b(this.b);
    }

    public void d() {
        g3 g3Var = this.a;
        EGL14.eglDestroySurface(g3Var.a, this.b);
        this.b = EGL14.EGL_NO_SURFACE;
        this.d = -1;
        this.c = -1;
    }

    public boolean e() {
        return this.a.d(this.b);
    }
}
