package com.secneo.apkwrapper;

/* JADX INFO: loaded from: classes.dex */
public class H {
    public static java.lang.String ARM_LIBRARY;
    public static java.lang.String PC_FLAG;
    public static java.lang.String PKGNAME;
    private static java.lang.Boolean a;
    public static java.lang.String b;
    public static java.lang.String c;
    public static java.lang.String d;
    public static java.lang.String e;
    public static java.lang.String f;
    public static java.lang.String g;
    public static java.lang.String h;
    private static android.os.Messenger i;
    private static android.os.Messenger j;
    public static android.app.Application sApp;
    public static android.content.pm.ApplicationInfo sAppInfo;
    private static final android.content.ServiceConnection sConnection = null;

    public static class a extends android.os.Handler {
        public a() {
                r6 = this;
                r6.<init>()
                return
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message r7) {
                r6 = this;
                r0 = 0
                int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
                goto L9
            L5:
                r0 = move-exception
                throw r0
            L7:
                r0 = move-exception
                throw r0
            L9:
                goto Ld
                android.os.Debug.stopNativeTracing()
            Ld:
                int r0 = r7.what
                r1 = 1000(0x3e8, float:1.401E-42)
                if (r0 == r1) goto L17
                super.handleMessage(r7)
                goto L29
            L17:
                android.app.Application r0 = com.secneo.apkwrapper.H.sApp     // Catch: java.lang.Throwable -> L24
                if (r0 == 0) goto L24
                android.app.Application r0 = com.secneo.apkwrapper.H.sApp     // Catch: java.lang.Throwable -> L24
                android.content.Context r0 = r0.getApplicationContext()     // Catch: java.lang.Throwable -> L24
                com.secneo.apkwrapper.H.us(r0)     // Catch: java.lang.Throwable -> L24
            L24:
                int r7 = r7.arg2
                com.secneo.apkwrapper.H.c(r7)
            L29:
                return
        }
    }

    static {
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            com.secneo.apkwrapper.H.a = r0
            java.lang.String r0 = "com.moutai.mall"
            com.secneo.apkwrapper.H.PKGNAME = r0
            java.lang.String r0 = "com.moutai.mall.MTApp"
            com.secneo.apkwrapper.H.b = r0
            java.lang.String r0 = "androidx.core.app.CoreComponentFactory"
            com.secneo.apkwrapper.H.c = r0
            java.lang.String r0 = "###SOPHIX###"
            com.secneo.apkwrapper.H.d = r0
            java.lang.String r0 = "###HAVEX86###"
            com.secneo.apkwrapper.H.e = r0
            java.lang.String r0 = "###HAVEX8664###"
            com.secneo.apkwrapper.H.f = r0
            java.lang.String r0 = "DexHelper-x86"
            com.secneo.apkwrapper.H.g = r0
            java.lang.String r0 = "DexHelper"
            com.secneo.apkwrapper.H.ARM_LIBRARY = r0
            java.lang.String r0 = "com.secneo.apkwrapper.AW"
            com.secneo.apkwrapper.H.h = r0
            java.lang.String r0 = "0"
            com.secneo.apkwrapper.H.PC_FLAG = r0
            r0 = 0
            com.secneo.apkwrapper.H.i = r0
            com.secneo.apkwrapper.H.j = r0
            com.secneo.apkwrapper.b r0 = new com.secneo.apkwrapper.b
            r0.<init>()
            com.secneo.apkwrapper.H.sConnection = r0
            return
    }

    public H() {
            r6 = this;
            r6.<init>()
            return
    }

    public static int a(int r6) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Debug.resetGlobalClassInitCount()
        Ld:
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch: java.io.IOException -> L71
            java.io.FileReader r1 = new java.io.FileReader     // Catch: java.io.IOException -> L71
            java.lang.String r2 = "/proc/self/status"
            r1.<init>(r2)     // Catch: java.io.IOException -> L71
            r0.<init>(r1)     // Catch: java.io.IOException -> L71
            r1 = 0
        L1a:
            java.lang.String r2 = r0.readLine()     // Catch: java.lang.Throwable -> L5d java.lang.Throwable -> L5f
            if (r2 == 0) goto L58
            if (r6 != 0) goto L3c
            java.lang.String r3 = "TracerPid:"
            boolean r3 = r2.startsWith(r3)     // Catch: java.lang.Throwable -> L5d java.lang.Throwable -> L5f
            if (r3 == 0) goto L1a
            r6 = 10
            java.lang.String r6 = r2.substring(r6)     // Catch: java.lang.Throwable -> L5d java.lang.Throwable -> L5f
            java.lang.String r6 = r6.trim()     // Catch: java.lang.Throwable -> L5d java.lang.Throwable -> L5f
            int r6 = java.lang.Integer.parseInt(r6)     // Catch: java.lang.Throwable -> L5d java.lang.Throwable -> L5f
            r0.close()     // Catch: java.io.IOException -> L71
            return r6
        L3c:
            r3 = 1
            if (r6 != r3) goto L1a
            java.lang.String r3 = "PPid:"
            boolean r3 = r2.startsWith(r3)     // Catch: java.lang.Throwable -> L5d java.lang.Throwable -> L5f
            if (r3 == 0) goto L1a
            r6 = 5
            java.lang.String r6 = r2.substring(r6)     // Catch: java.lang.Throwable -> L5d java.lang.Throwable -> L5f
            java.lang.String r6 = r6.trim()     // Catch: java.lang.Throwable -> L5d java.lang.Throwable -> L5f
            int r6 = java.lang.Integer.parseInt(r6)     // Catch: java.lang.Throwable -> L5d java.lang.Throwable -> L5f
            r0.close()     // Catch: java.io.IOException -> L71
            return r6
        L58:
            r0.close()     // Catch: java.io.IOException -> L71
            r6 = 0
            return r6
        L5d:
            r6 = move-exception
            goto L62
        L5f:
            r6 = move-exception
            r1 = r6
            throw r1     // Catch: java.lang.Throwable -> L5d
        L62:
            if (r1 == 0) goto L6d
            r0.close()     // Catch: java.lang.Throwable -> L68
            goto L70
        L68:
            r0 = move-exception
            r1.addSuppressed(r0)     // Catch: java.io.IOException -> L71
            goto L70
        L6d:
            r0.close()     // Catch: java.io.IOException -> L71
        L70:
            throw r6     // Catch: java.io.IOException -> L71
        L71:
            r6 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "read tracer"
            r0.<init>(r1, r6)
            throw r0
            return
    }

    public static int a(byte[] r6) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Debug.resetGlobalAllocCount()
        Ld:
            r0 = 0
            r1 = r6[r0]
            r0 = r0 | r1
            int r0 = r0 << 8
            r1 = 1
            r1 = r6[r1]
            r0 = r0 | r1
            int r0 = r0 << 8
            r1 = 2
            r1 = r6[r1]
            r0 = r0 | r1
            int r0 = r0 << 8
            r1 = 3
            r6 = r6[r1]
            r6 = r6 | r0
            return r6
    }

    static /* synthetic */ android.os.Messenger a() {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            junit.framework.Assert.fail()
        Ld:
            android.os.Messenger r0 = com.secneo.apkwrapper.H.j
            return r0
    }

    static /* synthetic */ android.os.Messenger a(android.os.Messenger r6) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Debug.resetGlobalExternalFreedSize()
        Ld:
            com.secneo.apkwrapper.H.j = r6
            return r6
    }

    public static java.lang.Object a(java.lang.Class<?> r6, java.lang.Object r7, java.lang.Object[] r8, java.lang.String r9, java.lang.Class<?>... r10) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Debug.stopAllocCounting()
        Ld:
            java.lang.reflect.Method r6 = r6.getDeclaredMethod(r9, r10)     // Catch: java.lang.reflect.InvocationTargetException -> L1a java.lang.IllegalArgumentException -> L1f java.lang.IllegalAccessException -> L24 java.lang.NoSuchMethodException -> L29
            r9 = 1
            r6.setAccessible(r9)     // Catch: java.lang.reflect.InvocationTargetException -> L1a java.lang.IllegalArgumentException -> L1f java.lang.IllegalAccessException -> L24 java.lang.NoSuchMethodException -> L29
            java.lang.Object r6 = r6.invoke(r7, r8)     // Catch: java.lang.reflect.InvocationTargetException -> L1a java.lang.IllegalArgumentException -> L1f java.lang.IllegalAccessException -> L24 java.lang.NoSuchMethodException -> L29
            return r6
        L1a:
            r6 = move-exception
            r6.printStackTrace()
            goto L2d
        L1f:
            r6 = move-exception
            r6.printStackTrace()
            goto L2d
        L24:
            r6 = move-exception
            r6.printStackTrace()
            goto L2d
        L29:
            r6 = move-exception
            r6.printStackTrace()
        L2d:
            r6 = 0
            return r6
    }

    public static void a(android.content.pm.ApplicationInfo r6) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Debug.startMethodTracing()
        Ld:
            java.lang.Boolean r0 = com.secneo.apkwrapper.H.a
            monitor-enter(r0)
            java.lang.Boolean r1 = com.secneo.apkwrapper.H.a     // Catch: java.lang.Throwable -> L30
            boolean r1 = r1.booleanValue()     // Catch: java.lang.Throwable -> L30
            if (r1 != 0) goto L2e
            boolean r1 = c()     // Catch: java.lang.Throwable -> L27 java.lang.Throwable -> L30
            if (r1 == 0) goto L24
            java.lang.String r1 = com.secneo.apkwrapper.H.g     // Catch: java.lang.Throwable -> L27 java.lang.Throwable -> L30
        L20:
            java.lang.System.loadLibrary(r1)     // Catch: java.lang.Throwable -> L27 java.lang.Throwable -> L30
            goto L2a
        L24:
            java.lang.String r1 = com.secneo.apkwrapper.H.ARM_LIBRARY     // Catch: java.lang.Throwable -> L27 java.lang.Throwable -> L30
            goto L20
        L27:
            b(r6)     // Catch: java.lang.Throwable -> L30
        L2a:
            java.lang.Boolean r6 = java.lang.Boolean.TRUE     // Catch: java.lang.Throwable -> L30
            com.secneo.apkwrapper.H.a = r6     // Catch: java.lang.Throwable -> L30
        L2e:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L30
            return
        L30:
            r6 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L30
            throw r6
            return
    }

    public static int b(int r6) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Debug.resetGlobalFreedCount()
        Ld:
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch: java.io.IOException -> L2f
            java.lang.String r1 = "/system/bin/app_process"
            r0.<init>(r1)     // Catch: java.io.IOException -> L2f
            r1 = 0
            r0.read()     // Catch: java.lang.Throwable -> L1c java.lang.Throwable -> L1e
            r0.close()     // Catch: java.io.IOException -> L2f
            goto L2f
        L1c:
            r2 = move-exception
            goto L20
        L1e:
            r1 = move-exception
            throw r1     // Catch: java.lang.Throwable -> L1c
        L20:
            if (r1 == 0) goto L2b
            r0.close()     // Catch: java.lang.Throwable -> L26
            goto L2e
        L26:
            r0 = move-exception
            r1.addSuppressed(r0)     // Catch: java.io.IOException -> L2f
            goto L2e
        L2b:
            r0.close()     // Catch: java.io.IOException -> L2f
        L2e:
            throw r2     // Catch: java.io.IOException -> L2f
        L2f:
            r0 = 2000(0x7d0, double:9.88E-321)
            android.os.SystemClock.sleep(r0)
            int r6 = a(r6)
            return r6
    }

    static /* synthetic */ android.os.Messenger b() {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Debug.resetThreadExternalAllocCount()
        Ld:
            android.os.Messenger r0 = com.secneo.apkwrapper.H.i
            return r0
    }

    static /* synthetic */ android.os.Messenger b(android.os.Messenger r6) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Debug.resetGlobalFreedCount()
        Ld:
            com.secneo.apkwrapper.H.i = r6
            return r6
    }

    public static void b(android.content.pm.ApplicationInfo r6) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Debug.resetThreadExternalAllocSize()
        Ld:
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 19
            if (r0 >= r1) goto L14
            return
        L14:
            int r0 = d()
            if (r0 != 0) goto L1b
            return
        L1b:
            java.lang.String r1 = r6.sourceDir
            java.io.File r2 = new java.io.File
            java.lang.String r6 = r6.dataDir
            java.lang.String r3 = ".cache"
            r2.<init>(r6, r3)
            boolean r6 = r2.exists()
            if (r6 != 0) goto L2f
            r2.mkdirs()
        L2f:
            java.io.File r6 = new java.io.File
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "lib"
            r3.append(r4)
            java.lang.String r4 = com.secneo.apkwrapper.H.g
            r3.append(r4)
            java.lang.String r4 = ".so"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r6.<init>(r2, r3)
            java.util.zip.ZipFile r2 = new java.util.zip.ZipFile     // Catch: java.lang.Exception -> L139
            r2.<init>(r1)     // Catch: java.lang.Exception -> L139
            r1 = 1
            r3 = 0
            if (r0 != r1) goto La8
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L139
            r0.<init>()     // Catch: java.lang.Exception -> L139
            java.lang.String r1 = "lib/armeabi-v7a/lib"
            r0.append(r1)     // Catch: java.lang.Exception -> L139
            java.lang.String r1 = com.secneo.apkwrapper.H.g     // Catch: java.lang.Exception -> L139
            r0.append(r1)     // Catch: java.lang.Exception -> L139
            r0.append(r4)     // Catch: java.lang.Exception -> L139
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> L139
            java.util.zip.ZipEntry r0 = r2.getEntry(r0)     // Catch: java.lang.Exception -> L139
            if (r0 != 0) goto L8b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L139
            r0.<init>()     // Catch: java.lang.Exception -> L139
            java.lang.String r1 = "lib/armeabi/lib"
            r0.append(r1)     // Catch: java.lang.Exception -> L139
            java.lang.String r1 = com.secneo.apkwrapper.H.g     // Catch: java.lang.Exception -> L139
            r0.append(r1)     // Catch: java.lang.Exception -> L139
            r0.append(r4)     // Catch: java.lang.Exception -> L139
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> L139
            java.util.zip.ZipEntry r0 = r2.getEntry(r0)     // Catch: java.lang.Exception -> L139
        L8b:
            if (r0 != 0) goto Ldb
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L139
            r0.<init>()     // Catch: java.lang.Exception -> L139
            java.lang.String r1 = "lib/x86/lib"
            r0.append(r1)     // Catch: java.lang.Exception -> L139
            java.lang.String r1 = com.secneo.apkwrapper.H.ARM_LIBRARY     // Catch: java.lang.Exception -> L139
            r0.append(r1)     // Catch: java.lang.Exception -> L139
            r0.append(r4)     // Catch: java.lang.Exception -> L139
        L9f:
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> L139
            java.util.zip.ZipEntry r0 = r2.getEntry(r0)     // Catch: java.lang.Exception -> L139
            goto Ldb
        La8:
            r1 = 2
            if (r0 != r1) goto Lda
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L139
            r0.<init>()     // Catch: java.lang.Exception -> L139
            java.lang.String r1 = "lib/arm64-v8a/lib"
            r0.append(r1)     // Catch: java.lang.Exception -> L139
            java.lang.String r1 = com.secneo.apkwrapper.H.g     // Catch: java.lang.Exception -> L139
            r0.append(r1)     // Catch: java.lang.Exception -> L139
            r0.append(r4)     // Catch: java.lang.Exception -> L139
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> L139
            java.util.zip.ZipEntry r0 = r2.getEntry(r0)     // Catch: java.lang.Exception -> L139
            if (r0 != 0) goto Ldb
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L139
            r0.<init>()     // Catch: java.lang.Exception -> L139
            java.lang.String r1 = "lib/x86_64/lib"
            r0.append(r1)     // Catch: java.lang.Exception -> L139
            java.lang.String r1 = com.secneo.apkwrapper.H.ARM_LIBRARY     // Catch: java.lang.Exception -> L139
            r0.append(r1)     // Catch: java.lang.Exception -> L139
            r0.append(r4)     // Catch: java.lang.Exception -> L139
            goto L9f
        Lda:
            r0 = r3
        Ldb:
            if (r0 != 0) goto Lde
            return
        Lde:
            java.io.InputStream r0 = r2.getInputStream(r0)     // Catch: java.lang.Throwable -> L10e java.lang.Exception -> L112
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L107 java.lang.Exception -> L10a
            r1.<init>(r6)     // Catch: java.lang.Throwable -> L107 java.lang.Exception -> L10a
            r2 = 4096(0x1000, float:5.74E-42)
            byte[] r2 = new byte[r2]     // Catch: java.lang.Throwable -> L103 java.lang.Exception -> L105
        Leb:
            int r3 = r0.read(r2)     // Catch: java.lang.Throwable -> L103 java.lang.Exception -> L105
            r4 = -1
            if (r3 == r4) goto Lf7
            r4 = 0
            r1.write(r2, r4, r3)     // Catch: java.lang.Throwable -> L103 java.lang.Exception -> L105
            goto Leb
        Lf7:
            r1.flush()     // Catch: java.lang.Throwable -> L103 java.lang.Exception -> L105
            if (r0 == 0) goto Lff
            r0.close()     // Catch: java.lang.Exception -> L139
        Lff:
            r1.close()     // Catch: java.lang.Exception -> L139
            goto L11f
        L103:
            r6 = move-exception
            goto L12e
        L105:
            r2 = move-exception
            goto L10c
        L107:
            r6 = move-exception
            r1 = r3
            goto L12e
        L10a:
            r2 = move-exception
            r1 = r3
        L10c:
            r3 = r0
            goto L114
        L10e:
            r6 = move-exception
            r0 = r3
            r1 = r0
            goto L12e
        L112:
            r2 = move-exception
            r1 = r3
        L114:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L12c
            if (r3 == 0) goto L11c
            r3.close()     // Catch: java.lang.Exception -> L139
        L11c:
            if (r1 == 0) goto L11f
            goto Lff
        L11f:
            java.lang.String r6 = r6.getAbsolutePath()     // Catch: java.lang.Throwable -> L127
            sl(r6)     // Catch: java.lang.Throwable -> L127
            goto L139
        L127:
            r6 = move-exception
            r6.printStackTrace()     // Catch: java.lang.Exception -> L139
            goto L139
        L12c:
            r6 = move-exception
            r0 = r3
        L12e:
            if (r0 == 0) goto L133
            r0.close()     // Catch: java.lang.Exception -> L139
        L133:
            if (r1 == 0) goto L138
            r1.close()     // Catch: java.lang.Exception -> L139
        L138:
            throw r6     // Catch: java.lang.Exception -> L139
        L139:
            return
    }

    public static native void bla(java.lang.String r0);

    public static native void blc();

    public static native boolean bli(java.lang.String r0);

    public static native boolean blq(java.lang.String r0);

    public static native void blr(java.lang.String r0);

    public static native boolean bls(long r0);

    public static native long blv();

    private static native java.lang.Object bs(android.content.Context r0, int r1);

    static /* synthetic */ void c(int r6) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Debug.resetAllCounts()
        Ld:
            he(r6)
            return
    }

    public static boolean c() {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Debug.startNativeTracing()
        Ld:
            java.lang.String r0 = com.secneo.apkwrapper.H.e
            java.lang.String r1 = "true"
            boolean r0 = r0.equalsIgnoreCase(r1)
            java.lang.String r2 = com.secneo.apkwrapper.H.f
            boolean r1 = r2.equalsIgnoreCase(r1)
            int r2 = d()
            r3 = 1
            if (r2 != r3) goto L25
            if (r0 != 0) goto L25
            return r3
        L25:
            r0 = 2
            if (r2 != r0) goto L2b
            if (r1 != 0) goto L2b
            return r3
        L2b:
            r0 = 0
            return r0
    }

    public static void callBS(android.content.Context r6) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.renderscript.RenderScript.releaseAllContexts()
        Ld:
            java.lang.String r0 = com.secneo.apkwrapper.H.PC_FLAG     // Catch: java.lang.Throwable -> L16
            int r0 = java.lang.Integer.parseInt(r0)     // Catch: java.lang.Throwable -> L16
            bs(r6, r0)     // Catch: java.lang.Throwable -> L16
        L16:
            return
    }

    public static native int cis(int r0);

    private static int d() {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Debug.resetGlobalClassInitCount()
        Ld:
            r0 = 20
            byte[] r0 = new byte[r0]
            r1 = 0
            r2 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L57
            java.lang.String r4 = "/proc/self/exe"
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L57
            r3.read(r0)     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            r2 = 4
            byte[] r2 = new byte[r2]     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            r2[r1] = r1     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            r4 = 1
            r2[r4] = r1     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            r5 = 19
            r5 = r0[r5]     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            r6 = 2
            r2[r6] = r5     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            r5 = 18
            r0 = r0[r5]     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            r5 = 3
            r2[r5] = r0     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            int r0 = a(r2)     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            if (r0 == r5) goto L46
            r2 = 6
            if (r0 == r2) goto L46
            r2 = 7
            if (r0 != r2) goto L40
            goto L46
        L40:
            r2 = 62
            if (r0 != r2) goto L47
            r1 = 2
            goto L47
        L46:
            r1 = 1
        L47:
            r3.close()     // Catch: java.io.IOException -> L5b
            goto L5b
        L4b:
            r0 = move-exception
            goto L51
        L4d:
            goto L58
        L4f:
            r0 = move-exception
            r3 = r2
        L51:
            if (r3 == 0) goto L56
            r3.close()     // Catch: java.io.IOException -> L56
        L56:
            throw r0
        L57:
            r3 = r2
        L58:
            if (r3 == 0) goto L5b
            goto L47
        L5b:
            return r1
    }

    public static native java.lang.String d(java.lang.String r0);

    public static native java.lang.Object[] gah();

    public static java.lang.Object getFieldValue(java.lang.Class<?> r6, java.lang.Object r7, java.lang.String r8) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.renderscript.RenderScript.releaseAllContexts()
        Ld:
            java.lang.reflect.Field r6 = r6.getDeclaredField(r8)     // Catch: java.lang.IllegalArgumentException -> L1a java.lang.IllegalAccessException -> L1f java.lang.NoSuchFieldException -> L24
            r8 = 1
            r6.setAccessible(r8)     // Catch: java.lang.IllegalArgumentException -> L1a java.lang.IllegalAccessException -> L1f java.lang.NoSuchFieldException -> L24
            java.lang.Object r6 = r6.get(r7)     // Catch: java.lang.IllegalArgumentException -> L1a java.lang.IllegalAccessException -> L1f java.lang.NoSuchFieldException -> L24
            return r6
        L1a:
            r6 = move-exception
            r6.printStackTrace()
            goto L28
        L1f:
            r6 = move-exception
            r6.printStackTrace()
            goto L28
        L24:
            r6 = move-exception
            r6.printStackTrace()
        L28:
            r6 = 0
            return r6
    }

    public static java.lang.Object getFieldValue(java.lang.String r6, java.lang.Object r7, java.lang.String r8) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.view.ViewDebug.stopRecyclerTracing()
        Ld:
            java.lang.Class r6 = java.lang.Class.forName(r6)     // Catch: java.lang.IllegalArgumentException -> L16 java.lang.ClassNotFoundException -> L1b
            java.lang.Object r6 = getFieldValue(r6, r7, r8)     // Catch: java.lang.IllegalArgumentException -> L16 java.lang.ClassNotFoundException -> L1b
            return r6
        L16:
            r6 = move-exception
            r6.printStackTrace()
            goto L1f
        L1b:
            r6 = move-exception
            r6.printStackTrace()
        L1f:
            r6 = 0
            return r6
    }

    public static native int gha(java.lang.String r0);

    public static native long ghc(java.lang.String r0);

    public static native int gv();

    private static native void he(int r0);

    public static native boolean is(int r0);

    public static void main(java.lang.String[] r6) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Debug.resetGlobalAllocCount()
        Ld:
            int r0 = r6.length
            r1 = 1
            r2 = 4
            if (r0 == r2) goto L15
            java.lang.System.exit(r1)
        L15:
            r0 = 0
            r2 = 0
            r3 = r6[r1]     // Catch: java.lang.Exception -> L29
            int r3 = java.lang.Integer.parseInt(r3)     // Catch: java.lang.Exception -> L29
            android.os.ParcelFileDescriptor r3 = android.os.ParcelFileDescriptor.adoptFd(r3)     // Catch: java.lang.Exception -> L29
            r4 = 3
            r6 = r6[r4]     // Catch: java.lang.Exception -> L2a
            int r0 = java.lang.Integer.parseInt(r6)     // Catch: java.lang.Exception -> L2a
            goto L2d
        L29:
            r3 = r2
        L2a:
            java.lang.System.exit(r1)
        L2d:
            int r6 = b(r0)     // Catch: java.lang.Throwable -> L5a
            java.io.DataOutputStream r0 = new java.io.DataOutputStream     // Catch: java.lang.Throwable -> L5a
            android.os.ParcelFileDescriptor$AutoCloseOutputStream r4 = new android.os.ParcelFileDescriptor$AutoCloseOutputStream     // Catch: java.lang.Throwable -> L5a
            r4.<init>(r3)     // Catch: java.lang.Throwable -> L5a
            r0.<init>(r4)     // Catch: java.lang.Throwable -> L5a
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch: java.lang.Throwable -> L46 java.lang.Throwable -> L48
            r0.writeUTF(r6)     // Catch: java.lang.Throwable -> L46 java.lang.Throwable -> L48
            r0.close()     // Catch: java.lang.Throwable -> L5a
            goto L5d
        L46:
            r6 = move-exception
            goto L4b
        L48:
            r6 = move-exception
            r2 = r6
            throw r2     // Catch: java.lang.Throwable -> L46
        L4b:
            if (r2 == 0) goto L56
            r0.close()     // Catch: java.lang.Throwable -> L51
            goto L59
        L51:
            r0 = move-exception
            r2.addSuppressed(r0)     // Catch: java.lang.Throwable -> L5a
            goto L59
        L56:
            r0.close()     // Catch: java.lang.Throwable -> L5a
        L59:
            throw r6     // Catch: java.lang.Throwable -> L5a
        L5a:
            java.lang.System.exit(r1)
        L5d:
            return
    }

    public static boolean setFieldValue(java.lang.Class<?> r6, java.lang.Object r7, java.lang.String r8, java.lang.Object r9) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Debug.resetThreadGcInvocationCount()
        Ld:
            java.lang.reflect.Field r6 = r6.getDeclaredField(r8)     // Catch: java.lang.IllegalArgumentException -> L19 java.lang.IllegalAccessException -> L1e java.lang.NoSuchFieldException -> L23
            r8 = 1
            r6.setAccessible(r8)     // Catch: java.lang.IllegalArgumentException -> L19 java.lang.IllegalAccessException -> L1e java.lang.NoSuchFieldException -> L23
            r6.set(r7, r9)     // Catch: java.lang.IllegalArgumentException -> L19 java.lang.IllegalAccessException -> L1e java.lang.NoSuchFieldException -> L23
            return r8
        L19:
            r6 = move-exception
            r6.printStackTrace()
            goto L27
        L1e:
            r6 = move-exception
            r6.printStackTrace()
            goto L27
        L23:
            r6 = move-exception
            r6.printStackTrace()
        L27:
            r6 = 0
            return r6
    }

    public static boolean setFieldValue(java.lang.String r6, java.lang.Object r7, java.lang.String r8, java.lang.Object r9) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Looper.loop()
        Ld:
            java.lang.Class r6 = java.lang.Class.forName(r6)     // Catch: java.lang.IllegalArgumentException -> L16 java.lang.ClassNotFoundException -> L1b
            setFieldValue(r6, r7, r8, r9)     // Catch: java.lang.IllegalArgumentException -> L16 java.lang.ClassNotFoundException -> L1b
            r6 = 1
            return r6
        L16:
            r6 = move-exception
            r6.printStackTrace()
            goto L1f
        L1b:
            r6 = move-exception
            r6.printStackTrace()
        L1f:
            r6 = 0
            return r6
    }

    public static native void sha(java.lang.String r0, int r1);

    public static void showToast(java.lang.String r6, int r7) {
            r0 = 0
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L5 java.lang.Exception -> L7
            goto L9
        L5:
            r0 = move-exception
            throw r0
        L7:
            r0 = move-exception
            throw r0
        L9:
            goto Ld
            android.os.Debug.resetGlobalExternalFreedSize()
        Ld:
            android.app.Application r0 = com.secneo.apkwrapper.H.sApp
            if (r0 == 0) goto L29
            android.content.Context r0 = r0.getBaseContext()
            if (r0 != 0) goto L18
            goto L29
        L18:
            android.os.Handler r0 = new android.os.Handler
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            r0.<init>(r1)
            com.secneo.apkwrapper.a r1 = new com.secneo.apkwrapper.a
            r1.<init>(r6, r7)
            r0.post(r1)
        L29:
            return
    }

    private static native void sl(java.lang.String r0);

    public static native int sn(java.lang.String r0);

    public static native void us(android.content.Context r0);
}
