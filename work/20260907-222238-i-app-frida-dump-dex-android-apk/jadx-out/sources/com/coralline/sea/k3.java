package com.coralline.sea;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.opengl.GLES20;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public final class k3 {
    public static final String i = "(nox@";
    public static final String j = "(bambo@";
    public static final String k = "(denglibo@";
    public static final String l = "build@Build2";
    public static final String m = "build@Build3";
    public static final String n = "root@ian-VirtualBox";
    public static final String o = "genymotion";
    public static final String p = "@osboxes";
    public static final String q = "(root@(none))";
    public static final String r = "root@bluevr2";
    public static final String s = "dxu@mv-mobl1";
    public static final String t = "cjw@mv-dev1";
    public final Context e;
    public static final JSONObject u = new a();
    public static final c[] v = {new c("1583287307", "1583148204", "3.8", true), new c("1581469831", "1580719631", "3.83.0", true), new c("1592393859", "1591864349", "3.9", true), new c("1597320136", "1597315778", "3.98.0", true), new c("1592392848", c7.c, "4.0", false)};
    public static final String[] w = {"/sdcard/ldsdk", "/sdcard/Android/data/com.android.flysilkworm"};
    public static final String[] x = {"15555215554", "15555215556", "15555215558", "15555215560", "15555215562", "15555215564", "15555215566", "15555215568", "15555215570", "15555215572", "15555215574", "15555215576", "15555215578", "15555215580", "15555215582", "15555215584"};
    public static final String[] y = {"000000000000000", "e21833235b6eef10", "012345678912345"};
    public static final String[] z = {"310260000000000"};
    public static final String[] A = {"goldfish", "Core(TM)", "AuthenticAMD", "Ryzen", "Athlon", "Threadripper"};
    public static final HashMap<String, String> B = new b();
    public static final d[] C = {new d("f1vm", "/x8/plugins/touch.apk"), new d("dundi", "/system/etc/init.dundi.sh"), new d("dundi", "ueventd.dundi.rc"), new d("ddy", "/data/local/tmp/com.cyjh.ddy.id"), new d("bluestacks", "/storage/emulated/0/.bluestacks.prop"), new d("andy", "fstab.andy"), new d("windroye", "/system/bin/windroyed"), new d("andy", "ueventd.andy.rc"), new d("nox", "fstab.nox"), new d("nox", "init.nox.rc"), new d("nox", "ueventd.nox.rc"), new d("f1vm", "/x8.prop"), new d("f1vm", "/x8/config/full_vm"), new d("f1vm", "/vm.prop"), new d("f1vm", "/vm/config/full_vm"), new d("f1vm", "/system/bin/elflinker32"), new d("f1vm", "/system/bin/elflinker64"), new d("gsvm", "/system/lib/egl/libGLESv2_titan.so"), new d("gsvm", "/system/lib/egl/libGLESv1_CM_titan.so"), new d("gsvm", "/system/lib/egl/libEGL_titan.so"), new d("gsvm", "/system/lib/hw/libgralloc.titan.so"), new d(z1.h, "/dev/qemu_pipe"), new d(z1.h, "/dev/socket/qemud"), new d(z1.h, "/dev/socket/genyd"), new d(z1.h, "/dev/socket/baseband_genyd"), new d(z1.h, "ueventd.android_x86.rc"), new d(z1.h, "x86.prop"), new d(z1.h, "ueventd.ttVM_x86.rc"), new d(z1.h, "init.ttVM_x86.rc"), new d(z1.h, "fstab.ttVM_x86"), new d(z1.h, "fstab.vbox86"), new d(z1.h, "init.vbox86.rc"), new d(z1.h, "ueventd.vbox86.rc")};
    public static final d[] D = {new d("init.svc.qemud", null), new d("init.svc.qemu-props", null), new d("qemu.hw.mainkeys", null), new d("qemu.sf.fake_camera", null), new d("qemu.sf.lcd_density", null), new d("ro.bootloader", i2.b), new d("ro.bootmode", i2.b), new d("ro.hardware", "goldfish"), new d("ro.kernel.android.qemud", null), new d("ro.kernel.qemu.gles", null), new d("ro.kernel.qemu", "1"), new d("ro.product.device", "generic"), new d("ro.product.model", "sdk"), new d("ro.product.name", "sdk"), new d("ro.serialno", null)};
    public static String E = c7.c;
    public String a = "10";
    public String b = "0";
    public String c = "2";
    public String d = "15";
    public HashSet<String> f = new HashSet<>();
    public HashSet<String> g = new HashSet<>();
    public String h = null;

    public class a extends JSONObject {
        public a() throws Error {
            try {
                put("Microsoft", "Microsoft Windows Android Subsystem");
                put("google", "Android Virtual Device");
                put(k3.o, "Genymotion Android Emulator");
                put("tiantian", "Tiantian Android Emulator");
                put("nox", "Yeshen Android Emulator");
                put("droid4x", "Haimawan Android Emulator");
                put("microvirt", "Xiaoyao Android Emulator");
                put("bluestacks", "BlueStacks Android Emulator");
                put("itools", "iTools Android Emulator");
                put("mumu", "MuMu Android Emulator");
                put("andy", "Andy Android Emulator");
                put("momo", "MOMO Android Emulator");
                put("LeiDian", "LeiDian Android Emulator");
                put("f1vm", "F1 Virtual Machine");
                put("bluestacks_proc_version", "BlueStacks_proc_version Android Emulator");
                put("windroye", "windroye Android Emulator");
                put("tencent", "tencent Android Emulator");
                put("xiaoyi", "xiaoyi Android Emulator");
                put("Netease", "Netease Android Emulator");
                put("redfinger", "redfinger Android Emulator");
                put("AiYunRabbit", "AiYunRabbit Android Emulator");
                put("LuDaShi", "LuDaShi Android Emulator");
                put("LGAir", "LanGuang Air Android Device");
                put("LDAir", "LeiDian Air Android Device");
                put(z1.h, "other Android Emulator");
            } catch (Exception e) {
            }
        }
    }

    public class b extends HashMap {
        public b() {
            put("com.google.android.launcher.layouts.genymotion", k3.o);
            put("com.genymotion.genyd", k3.o);
            put("com.genymotion.superuser", k3.o);
            put("com.genymotion.systempatcher", k3.o);
            put("com.bluestacks", "bluestacks");
            put("com.bignox.app", "nox");
            put("com.vphone.launcher", "nox");
            put("com.bignox.app.store.hd", "nox");
            put("me.haima.androidassist", "droid4x");
            put("com.haimawan.push", "droid4x");
            put("com.microvirt.launcher", "microvirt");
            put("com.microvirt.download", "microvirt");
            put("com.microvirt.market", "microvirt");
            put("com.microvirt.memuime", "microvirt");
            put("com.kaopu001.tiantianserver", "tiantian");
            put("com.kaopu.android.assistant", "tiantian");
            put("com.kop.zkop", "tiantian");
            put("com.tiantian.ime", "tiantian");
            put("com.bluestacks.bstfolder", "bluestacks");
            put("com.bluestacks.BstCommandProcessor", "bluestacks");
            put("com.bluestacks.appmart", "bluestacks");
            put("com.bluestacks.appguidance", "bluestacks");
            put("com.bluestacks.home", "bluestacks");
            put("com.mumu.launcher", "mumu");
            put("com.mumu.store", "mumu");
            put("com.mumu.store.autoupdate", "mumu");
            put("com.mumu.acc", "mumu");
            put("com.netease.mumu.cloner", "mumu");
            put("cn.antplayer.appstore", "xiaoyi");
            put("com.android.emu.coreservice", "xiaoyi");
            put("com.android.emu.inputservice", "xiaoyi");
            put("com.ludashi.account.service", "LuDaShi");
        }
    }

    public static class c {
        public final String a = "aosp-user";
        public final String b;
        public final String c;
        public final String d;
        public final boolean e;

        public c(String str, String str2, String str3, boolean z) {
            this.b = str;
            this.c = str2;
            this.d = str3;
            this.e = z;
        }

        public String toString() {
            return "LeiDianBuildTime{ro_board_platform='aosp-user', persist_sys_build_time='" + this.b + "', ro_build_date_utc='" + this.c + "', version='" + this.d + "', is_check_date_utc=" + this.e + '}';
        }
    }

    public static class d {
        public final String a;
        public final String b;

        public d(String str, String str2) {
            this.a = str;
            this.b = str2;
        }
    }

    public k3(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        this.e = context.getApplicationContext();
    }

    public static boolean s() throws Throwable {
        String strI = ja.i("cat /proc/net/tcp");
        String strI2 = ja.i("cat /proc/net/tcp6");
        if (strI == null && strI2 == null) {
            return false;
        }
        if (!TextUtils.isEmpty(strI) && !"null".equalsIgnoreCase(strI)) {
            for (String str : strI.split("\n")) {
                if (str.toLowerCase().contains("226d") || str.toLowerCase().contains("226b")) {
                    return true;
                }
            }
        }
        if (!TextUtils.isEmpty(strI2) && !"null".equalsIgnoreCase(strI2)) {
            for (String str2 : strI2.split("\n")) {
                if (str2.toLowerCase().contains("226b") || str2.toLowerCase().contains("226d")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void v() throws Throwable {
        int iB;
        try {
            iB = i6.b("/proc/version", 0, 0);
            if (iB >= 0) {
                try {
                    byte[] bArr = new byte[300];
                    i6.c(iB, bArr, 300);
                    String str = new String(bArr);
                    E = str;
                    if (!str.contains("Linux version")) {
                        File file = new File("/proc/version");
                        if (file.exists() && file.canRead()) {
                            byte[] bArr2 = new byte[1024];
                            FileInputStream fileInputStream = null;
                            try {
                                FileInputStream fileInputStream2 = new FileInputStream(file);
                                try {
                                    fileInputStream2.read(bArr2);
                                    E += new String(bArr2);
                                    p9.a(fileInputStream2);
                                } catch (Exception e) {
                                    fileInputStream = fileInputStream2;
                                    p9.a(fileInputStream);
                                } catch (Throwable th) {
                                    th = th;
                                    fileInputStream = fileInputStream2;
                                    p9.a(fileInputStream);
                                    throw th;
                                }
                            } catch (Exception e2) {
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        }
                    }
                } catch (Exception e3) {
                } catch (Throwable th3) {
                    th = th3;
                    i6.f(iB);
                    throw th;
                }
            }
        } catch (Exception e4) {
            iB = 0;
        } catch (Throwable th4) {
            th = th4;
            iB = 0;
        }
        i6.f(iB);
    }

    public static void w() throws Throwable {
        String strI = ja.i("cat /proc/version");
        if (TextUtils.isEmpty(strI) || "null".equalsIgnoreCase(strI)) {
            return;
        }
        E += strI;
    }

    public final void a(String str, String str2) {
        try {
            this.f.add(str + "#" + str2);
            if (TextUtils.isEmpty(this.h) && u.has(str)) {
                this.h = str;
            }
            if (TextUtils.isEmpty(this.h) || !u.has(str) || z1.h.equals(str)) {
                return;
            }
            this.h = str;
        } catch (Exception e) {
        }
    }

    public final boolean a() {
        if (TextUtils.isEmpty(E)) {
            return false;
        }
        if (!E.contains(l) && !E.contains(m) && !E.contains(n)) {
            return false;
        }
        a("bluestacks_proc_version", "check QEmu proc_version contans build ");
        return true;
    }

    public final boolean a(int i2) {
        return ((i2 & 1) != 0) || ((i2 & 128) != 0);
    }

    public final boolean a(c cVar, String str, String str2, String str3) {
        return cVar.b.equals(str) && "aosp-user".equals(str3) && (cVar.e ? cVar.c.equals(str2) : true);
    }

    public final boolean a(StringBuffer stringBuffer, String str, String... strArr) {
        int iIndexOf;
        StringBuffer stringBuffer2 = new StringBuffer(stringBuffer.toString().toLowerCase());
        int length = strArr.length;
        int i2 = 0;
        boolean z2 = false;
        while (i2 < length) {
            String lowerCase = strArr[i2].toLowerCase();
            boolean z3 = z2;
            int iIndexOf2 = 0;
            int i3 = 0;
            while (iIndexOf2 < stringBuffer2.length() - 1 && (iIndexOf = stringBuffer2.indexOf(lowerCase, i3)) != -1) {
                int iLastIndexOf = stringBuffer2.lastIndexOf("\n", iIndexOf) + 1;
                iIndexOf2 = stringBuffer2.indexOf("\n", lowerCase.length() + iIndexOf);
                a(str, "Check QEmu Basic[" + stringBuffer.substring(iLastIndexOf, iIndexOf2) + "] is detected.");
                i3 = iIndexOf2 + 2;
                z3 = true;
            }
            i2++;
            z2 = z3;
        }
        return z2;
    }

    public final boolean a(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                if (("haima_cloudplay".equals(y2.a().a(jSONObject, "ro.hm.device.type")) && "haima_cloudplay".equals(y2.a().a(jSONObject, "ro.build.product")) && !"null".equals(y2.a().a(jSONObject, "ro.cloud.rentable"))) && E.contains(q)) {
                    a("AiYunRabbit", "check QEmu proc_version contains (root@(none)) , and hit features by getprop , and hit features by getprop");
                    return true;
                }
            } catch (Exception e) {
            }
        }
        return false;
    }

    public final boolean b() {
        String str = Build.BOOTLOADER;
        if (!str.equalsIgnoreCase("u-boot") || !s()) {
            return false;
        }
        String str2 = !TextUtils.isEmpty(this.h) ? this.h : "redfinger";
        a(str2, "Check QEmu bootloader[" + str + "] and port contains " + str2);
        return true;
    }

    public final boolean b(JSONObject jSONObject) throws Throwable {
        String[] strArr = {"ro.build.host"};
        if (jSONObject == null) {
            return false;
        }
        boolean z2 = false;
        for (int i2 = 0; i2 < 1; i2++) {
            try {
                String str = strArr[i2];
                String strA = y2.a().a(jSONObject, str);
                if (strA != null && (ja.h(strA) || strA.equals("null"))) {
                    strA = ja.i("getprop " + str);
                }
                if (strA != null && "bluevr2".equals(strA)) {
                    a("LGAir", "check Device host name is bluevr2");
                    z2 = true;
                }
                if (E.contains(r)) {
                    a("LGAir", "check Device proc_verison contains root@bluevr2");
                    z2 = true;
                }
            } catch (Exception e) {
            }
        }
        return z2;
    }

    public final boolean c() {
        if (TextUtils.isEmpty(E)) {
            return false;
        }
        if (!E.contains(o) && !E.contains("genymobile")) {
            return false;
        }
        a(o, "check QEmu proc_version contains genymotion");
        return true;
    }

    public final boolean c(JSONObject jSONObject) {
        for (String str : w) {
            File file = new File(str);
            if (file.exists() && file.isDirectory()) {
                if (u()) {
                    a("LeiDian", "x86 and check sdcard file:" + str);
                }
                return true;
            }
        }
        if (jSONObject != null) {
            try {
                String strA = y2.a().a(jSONObject, "persist.sys.build.time");
                String strA2 = y2.a().a(jSONObject, "ro.build.date.utc");
                String strA3 = y2.a().a(jSONObject, "ro.board.platform");
                for (c cVar : v) {
                    if (a(cVar, strA, strA2, strA3)) {
                        a("LeiDian", "check prop and mnt file build time:" + cVar.b);
                        cVar.toString();
                        return true;
                    }
                }
                jSONObject.toString();
                y2.a().a(jSONObject, "init.svc.ldinit");
                if (ja.u("ldinit")) {
                    a("LeiDian", "check by process, build time:" + strA);
                    return true;
                }
                if ((y2.a().a(jSONObject, "ro.boot.hardware").contains("x86") || y2.a().a(jSONObject, "ro.hardware").contains("x86")) && !y2.a().a(jSONObject, "init.svc.ldinit").equals(i2.b)) {
                    a("LeiDian", "check prop:ldinit,x86,build time:" + strA);
                    return true;
                }
            } catch (Exception e) {
            }
        }
        return false;
    }

    public final void d() {
        JSONObject jSONObjectA = x2.a();
        if (e(jSONObjectA) || i() || a() || o() || c() || j() || b() || a(jSONObjectA) || f() || b(jSONObjectA) || c(jSONObjectA) || e() || d(jSONObjectA)) {
            return;
        }
        h();
    }

    public final boolean d(JSONObject jSONObject) {
        boolean z2;
        if (TextUtils.isEmpty(E) || !(E.contains(s) || E.contains(t))) {
            z2 = false;
        } else {
            a("microvirt", "check Microvirt proc_version contans build ");
            z2 = true;
        }
        if (jSONObject != null) {
            try {
                String strA = y2.a().a(jSONObject, "ro.boot.hardware");
                String strA2 = y2.a().a(jSONObject, "ro.build.version.incremental");
                String strA3 = y2.a().a(jSONObject, "ro.build.tags");
                String strA4 = y2.a().a(jSONObject, "ro.build.type");
                String strA5 = y2.a().a(jSONObject, "ro.simulated.phone");
                String strA6 = y2.a().a(jSONObject, "ro.render");
                if (strA.equals("intel") && ((strA2.startsWith("rel.se.infra.202") || strA2.startsWith("rel.se.infra.2019")) && strA3.equals("release-keys") && strA4.equals("user") && ((strA5.equals("true") || strA5.equals("false")) && (strA6.equals("0") || strA6.equals("1"))))) {
                    a("microvirt", "check Microvirt proc_version contans ro ");
                    return true;
                }
            } catch (Exception e) {
            }
        }
        return z2;
    }

    public final boolean e() {
        try {
            if (!v7.f("com.cyjh.ddy") || !"huawei services".equals(v7.a("com.cyjh.ddy")) || !v7.f("com.cloud.launcher3") || !"雷电云界面".equals(v7.a("com.cloud.launcher3")) || !v7.f("com.ld.yunstore") || !"应用市场".equals(v7.a("com.ld.yunstore"))) {
                return false;
            }
            a("LDAir", "check apk is true");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public final boolean e(JSONObject jSONObject) throws Throwable {
        String str = c7.c;
        String str2 = E;
        if (str2 != null && str2.contains(k)) {
            str = "/proc/version;";
        }
        String[] strArr = {"ro.build.user", "ro.build.version.incremental", "ro.build.fingerprint", "ro.build.display.id", "ro.build.description"};
        if (jSONObject != null) {
            String str3 = str;
            for (int i2 = 0; i2 < 5; i2++) {
                String str4 = strArr[i2];
                String strA = y2.a().a(jSONObject, str4);
                if (strA == null || !(ja.h(strA) || strA.equals("null"))) {
                    if (strA == null && strA.contains("denglibo")) {
                        str3 = str3 + str4 + ";";
                    }
                } else if (!n3.a().g || !"ro.build.fingerprint".equals(str4)) {
                    strA = ja.i("getprop " + str4);
                    if (strA == null) {
                    }
                }
            }
            str = str3;
        }
        if (str == null || str.length() <= 0) {
            return false;
        }
        a("momo", "Check QEmu Author[denglibo] in [" + str + "]");
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0035 A[Catch: Exception -> 0x003d, TRY_LEAVE, TryCatch #0 {Exception -> 0x003d, blocks: (B:3:0x0001, B:5:0x000a, B:7:0x0012, B:8:0x001c, B:10:0x0022, B:15:0x0035), top: B:19:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean f() {
        /*
            r5 = this;
            r0 = 0
            java.lang.String r1 = "/mnt/apkinstallshare"
            boolean r1 = com.coralline.sea.s3.a(r1)     // Catch: java.lang.Exception -> L3d
            r2 = 1
            if (r1 == 0) goto L32
            java.lang.String r1 = "/mnt/apkinstallshareicon"
            boolean r1 = com.coralline.sea.s3.a(r1)     // Catch: java.lang.Exception -> L3d
            if (r1 == 0) goto L32
            java.lang.String r1 = "/mnt/apkinstallshare"
            java.util.List r1 = com.coralline.sea.ja.m(r1)     // Catch: java.lang.Exception -> L3d
            java.util.Iterator r1 = r1.iterator()     // Catch: java.lang.Exception -> L3d
        L1c:
            boolean r3 = r1.hasNext()     // Catch: java.lang.Exception -> L3d
            if (r3 == 0) goto L32
            java.lang.Object r3 = r1.next()     // Catch: java.lang.Exception -> L3d
            java.lang.String r3 = (java.lang.String) r3     // Catch: java.lang.Exception -> L3d
            java.lang.String r4 = "LDS_"
            boolean r3 = r3.startsWith(r4)     // Catch: java.lang.Exception -> L3d
            if (r3 == 0) goto L1c
            r1 = 1
            goto L33
        L32:
            r1 = 0
        L33:
            if (r1 == 0) goto L3e
            java.lang.String r1 = "LuDaShi"
            java.lang.String r3 = "check QEmu paths exist and name hit condition"
            r5.a(r1, r3)     // Catch: java.lang.Exception -> L3d
            return r2
        L3d:
            r1 = move-exception
        L3e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.k3.f():boolean");
    }

    public final void g() throws Throwable {
        String strI = ja.i("cat /proc/self/mounts");
        if (TextUtils.isEmpty(strI)) {
            return;
        }
        if (strI.contains("windows/InputMapper")) {
            a("bluestacks", "check QEmu mounts file detect InputMapper");
        }
        if (strI.contains("vboxsf")) {
            a(z1.h, "check QEmu mounts file detect vboxsf");
        }
        if (strI.contains("/mnt/shared/install_apk") && strI.contains("nemusf")) {
            a("momo", "check QEmu mounts file detect nemusf install_apk");
        }
        if (strI.contains("/mnt/shell/emulated/0/Music sharefolder")) {
            a("microvirt", "check QEmu mounts file detect sharefolder");
        }
    }

    public final boolean h() {
        String str = Build.MODEL;
        String str2 = Build.MANUFACTURER;
        if (!str.equals("MuMu") || !str2.equals("网易") || !u()) {
            return false;
        }
        a("momo", "Check QEmu  in [model: MuMu; manufacturer:网易;arch:x86]");
        return true;
    }

    public final boolean i() {
        String str;
        String str2;
        if (!TextUtils.isEmpty(E) && E.contains(i)) {
            str = "nox";
            str2 = "check QEmu proc_version contains nox";
        } else {
            if (!"游戏中心".equals(v7.a("com.android.Calendar")) || !"com.android.calculator2".equals(v7.a("com.android.calculator2"))) {
                return false;
            }
            str = "nox";
            str2 = "check com.android.Calendar,com.android.calculator2 contains nox";
        }
        a(str, str2);
        return true;
    }

    public final boolean j() {
        if (TextUtils.isEmpty(E) || !E.contains(p)) {
            return false;
        }
        a(z1.h, "check QEmu proc_version contains osboxes");
        return true;
    }

    public final void k() {
        ApplicationInfo applicationInfo;
        ApplicationInfo applicationInfo2;
        ApplicationInfo applicationInfo3;
        this.e.getPackageManager();
        List<PackageInfo> listA = v6.a(0);
        if (listA != null) {
            for (PackageInfo packageInfo : listA) {
                if (packageInfo != null && (applicationInfo3 = packageInfo.applicationInfo) != null && a(applicationInfo3.flags)) {
                    HashMap<String, String> map = B;
                    if (map.containsKey(packageInfo.packageName)) {
                        String str = map.get(packageInfo.packageName);
                        a(str, "Check QEmu ApplicationInfo[" + str + "][" + packageInfo.packageName + "] is detected");
                    }
                }
            }
        }
        List<PackageInfo> listA2 = v6.a(0);
        if (listA2 != null) {
            for (PackageInfo packageInfo2 : listA2) {
                if (packageInfo2 != null && (applicationInfo2 = packageInfo2.applicationInfo) != null && a(applicationInfo2.flags)) {
                    HashMap<String, String> map2 = B;
                    if (map2.containsKey(packageInfo2.packageName)) {
                        String str2 = map2.get(packageInfo2.packageName);
                        a(str2, "Check QEmu PackageInfo[" + str2 + "][" + packageInfo2.packageName + "] is detected");
                    }
                }
            }
        }
        Iterator<String> it = B.keySet().iterator();
        while (it.hasNext()) {
            try {
                PackageInfo packageInfoA = v6.a(this.e, it.next(), 64);
                if (packageInfoA != null && (applicationInfo = packageInfoA.applicationInfo) != null && a(applicationInfo.flags)) {
                    HashMap<String, String> map3 = B;
                    if (map3.containsKey(packageInfoA.packageName)) {
                        String str3 = map3.get(packageInfoA.packageName);
                        a(str3, "Check QEmu PackageInfo[" + str3 + "][" + packageInfoA.packageName + "] is detected");
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public final void l() throws Throwable {
        File[] fileArr = {new File("/proc/tty/drivers"), new File("/proc/cpuinfo")};
        for (int i2 = 0; i2 < 2; i2++) {
            File file = fileArr[i2];
            if (file.exists() && file.canRead()) {
                byte[] bArr = new byte[1024];
                FileInputStream fileInputStream = null;
                try {
                    FileInputStream fileInputStream2 = new FileInputStream(file);
                    try {
                        fileInputStream2.read(bArr);
                        p9.a(fileInputStream2);
                    } catch (Exception e) {
                        fileInputStream = fileInputStream2;
                        p9.a(fileInputStream);
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        p9.a(fileInputStream);
                        throw th;
                    }
                } catch (Exception e2) {
                } catch (Throwable th2) {
                    th = th2;
                }
                String str = new String(bArr);
                for (String str2 : A) {
                    if (str.contains(str2)) {
                        a(z1.h, "Check QEmu Drivers[" + str2 + "] in [" + file + "]");
                    }
                }
            }
        }
    }

    public final void m() {
        try {
            for (d dVar : C) {
                if (dVar.a.equals("f1vm")) {
                    if (s3.a("/vm.prop") && s3.a("/vm/config/full_vm")) {
                        new File("/vm.prop").exists();
                        new File("/vm/config/full_vm").exists();
                        a(dVar.a, "Check QEmu Files[" + dVar.a + "][/vm.prop][/vm/config/full_vm] is detected.");
                        return;
                    }
                    if (s3.a("/system/bin/elflinker32") && s3.a("/system/bin/elflinker64")) {
                        new File("/system/bin/elflinker32").exists();
                        new File("/system/bin/elflinker64").exists();
                        a(dVar.a, "Check QEmu Files[" + dVar.a + "][/system/bin/elflinker32][/system/bin/elflinker64] is detected.");
                        return;
                    }
                    if (s3.a("/x8.prop")) {
                        new File("/system/xbin/daemonsu").exists();
                        new File("/system/xbin/su").exists();
                        a(dVar.a, "Check QEmu Files[" + dVar.a + "][/x8.prop] is detected.");
                        return;
                    }
                    if (s3.a("/x8/config/full_vm")) {
                        new File("/system/xbin/daemonsu").exists();
                        new File("/system/xbin/su").exists();
                        a(dVar.a, "Check QEmu Files[" + dVar.a + "][/x8/config/full_vm] is detected.");
                        return;
                    }
                }
                if (dVar.a.equals("gsvm")) {
                    HashSet<String> hashSetA = d7.a();
                    if (s3.a("/system/lib/egl/libGLESv2_titan.so") && hashSetA.contains("/system/lib/egl/libGLESv2_titan.so")) {
                        new File("/system/xbin/daemonsu").exists();
                        new File("/system/xbin/su").exists();
                        a(dVar.a, "Check QEmu Files[" + dVar.a + "][/system/lib/egl/libGLESv2_titan.so] is detected.");
                        return;
                    }
                    if (s3.a("/system/lib/egl/libGLESv1_CM_titan.so") && hashSetA.contains("/system/lib/egl/libGLESv1_CM_titan.so")) {
                        new File("/system/xbin/daemonsu").exists();
                        new File("/system/xbin/su").exists();
                        a(dVar.a, "Check QEmu Files[" + dVar.a + "][/system/lib/egl/libGLESv1_CM_titan.so] is detected.");
                        return;
                    }
                    if (s3.a("/system/lib/egl/libEGL_titan.so") && hashSetA.contains("/system/lib/egl/libEGL_titan.so")) {
                        new File("/system/xbin/daemonsu").exists();
                        new File("/system/xbin/su").exists();
                        a(dVar.a, "Check QEmu Files[" + dVar.a + "][/system/lib/egl/libEGL_titan.so] is detected.");
                        return;
                    }
                    if (s3.a("/system/lib/hw/libgralloc.titan.so") && hashSetA.contains("/system/lib/hw/libgralloc.titan.so")) {
                        new File("/system/xbin/daemonsu").exists();
                        new File("/system/xbin/su").exists();
                        a(dVar.a, "Check QEmu Files[" + dVar.a + "][/system/lib/hw/libgralloc.titan.so] is detected.");
                        return;
                    }
                } else if (s3.a(dVar.b)) {
                    new File(dVar.b).exists();
                    a(dVar.a, "Check QEmu Files[" + dVar.a + "][" + dVar.b + "] is detected.");
                    return;
                }
            }
        } catch (Exception e) {
        }
    }

    public final void n() {
        if (ja.r("android.permission.INTERNET") != 0 || ja.t()) {
            return;
        }
        String[] strArr = {"/system/bin/netcfg"};
        StringBuilder sb = new StringBuilder();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(strArr);
            processBuilder.directory(new File("/system/bin/"));
            processBuilder.redirectErrorStream(true);
            InputStream inputStream = processBuilder.start().getInputStream();
            byte[] bArr = new byte[1024];
            while (inputStream.read(bArr) != -1) {
                sb.append(new String(bArr));
            }
            inputStream.close();
        } catch (Exception e) {
        }
        String string = sb.toString();
        if (TextUtils.isEmpty(string)) {
            return;
        }
        for (String str : string.split("\n")) {
            if ((str.contains("wlan0") || str.contains("tunl0") || str.contains("eth0")) && str.contains(r())) {
                a(z1.h, "Check QEmu IP[" + str + "] is detected");
            }
        }
    }

    public final boolean o() {
        if (TextUtils.isEmpty(E) || !E.contains(j)) {
            return false;
        }
        a("redfinger", "check QEmu proc_version contains redfinger");
        return true;
    }

    public final void p() {
        TelephonyManager telephonyManager;
        String strB;
        if (t() && (telephonyManager = (TelephonyManager) this.e.getSystemService(m1.j)) != null) {
            try {
                if (telephonyManager.getNetworkOperatorName().equalsIgnoreCase(a0.b)) {
                    a(z1.h, "Check QEmu operator name[android] is detected");
                }
            } catch (Exception e) {
            }
            if (!n3.a().g) {
                try {
                    String strA = o7.a();
                    this.g.add("deviceId->" + strA);
                    for (String str : y) {
                        if (str.equalsIgnoreCase(strA)) {
                            a(z1.h, "Check QEmu deviceId[" + str + "] is detected");
                        }
                    }
                } catch (Exception e2) {
                }
            }
            if (n3.T.g) {
                return;
            }
            try {
                if (TextUtils.isEmpty(a9.a("imsi", c7.c))) {
                    strB = o7.b();
                    a9.b("imsi", strB);
                } else {
                    strB = a9.a("imsi", c7.c);
                }
                for (String str2 : z) {
                    if (str2.equalsIgnoreCase(strB)) {
                        a(z1.h, "Check QEmu imsi[" + str2 + "] is detected");
                    }
                }
            } catch (Exception e3) {
            }
        }
    }

    public JSONObject q() throws Exception {
        HashSet<String> hashSet;
        this.h = null;
        this.f.clear();
        this.g.clear();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Build.FINGERPRINT->" + Build.FINGERPRINT + "\n");
        stringBuffer.append("Build.MODEL->" + Build.MODEL + "\n");
        stringBuffer.append("Build.MANUFACTURER->" + Build.MANUFACTURER + "\n");
        stringBuffer.append("Build.HARDWARE->" + Build.HARDWARE + "\n");
        stringBuffer.append("Build.PRODUCT->" + Build.PRODUCT + "\n");
        stringBuffer.append("Build.BOARD->" + Build.BOARD + "\n");
        stringBuffer.append("Build.BOOTLOADER->" + Build.BOOTLOADER + "\n");
        stringBuffer.append("Build.SERIAL->" + Build.SERIAL + "\n");
        StringBuilder sb = new StringBuilder("Build.BRAND->");
        String str = Build.BRAND;
        sb.append(str);
        sb.append("\n");
        stringBuffer.append(sb.toString());
        StringBuilder sb2 = new StringBuilder("Build.DEVICE->");
        String str2 = Build.DEVICE;
        sb2.append(str2);
        sb2.append("\n");
        stringBuffer.append(sb2.toString());
        stringBuffer.append("Build.HOST->" + Build.HOST + "\n");
        if (Build.VERSION.SDK_INT >= 17 && a4.a()) {
            g3 g3Var = new g3(null, 2);
            p6 p6Var = new p6(g3Var, 1, 1);
            p6Var.c();
            stringBuffer.append("GL.Vendor->" + GLES20.glGetString(7936) + "\n");
            stringBuffer.append("GL.Version->" + GLES20.glGetString(7938) + "\n");
            stringBuffer.append("GL.Renderer->" + GLES20.glGetString(7937) + "\n");
            stringBuffer.append("GL.GLSLVersion->" + GLES20.glGetString(35724) + "\n");
            stringBuffer.append("EGL.Vendor->" + g3Var.a(12371) + "\n");
            stringBuffer.append("EGL.Version->" + g3Var.a(12372) + "\n");
            stringBuffer.append("EGL.Apis->" + g3Var.a(12429) + "\n");
            p6Var.d();
            g3Var.c();
        }
        if (!a(stringBuffer, "itools", "itools") && !a(stringBuffer, "nox", "nox") && !a(stringBuffer, o, o) && !a(stringBuffer, "Netease", "Netease") && !a(stringBuffer, "droid4x", "droid4x") && !a(stringBuffer, "mumu", "mumu") && !a(stringBuffer, "tencent", "tencent") && !a(stringBuffer, "redfinger", "redfinger") && !a(stringBuffer, "Microsoft", "windows_x86") && !a(stringBuffer, "google", "google_sdk", "emulator", "Android SDK built for x86", "sdk_x86")) {
            a(stringBuffer, z1.h, "vbox86", "vbox86p", "vbox86tp", "goldfish", "chuangping", "osboxes");
        }
        if (str.startsWith("generic") && str2.startsWith("generic")) {
            a(z1.h, "Check QEmu Basic[Build.BRAND->generic;Build.DEVICE->generic] is detected.");
        }
        this.g.add(stringBuffer.toString());
        v();
        w();
        this.g.add("procVersion->" + E);
        m();
        if (TextUtils.isEmpty(this.h) || z1.h.equals(this.h)) {
            k();
        }
        if (TextUtils.isEmpty(this.h) || this.h.equals(z1.h)) {
            l();
            g();
            n();
            p();
            d();
        }
        if (TextUtils.isEmpty(this.h)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", this.h);
        jSONObject.put("name", u.optString(this.h, "其它"));
        if (this.h.equals(z1.h)) {
            this.g.addAll(this.f);
            hashSet = this.g;
        } else {
            hashSet = this.f;
        }
        jSONObject.put("detail", f5.a(hashSet));
        return jSONObject;
    }

    public final String r() {
        return this.a + "." + this.b + "." + this.c + "." + this.d;
    }

    public final boolean t() {
        return this.e.getPackageManager().hasSystemFeature("android.hardware.telephony");
    }

    public final boolean u() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(c7.c + Build.CPU_ABI);
        arrayList.add(c7.c + Build.CPU_ABI2);
        arrayList.add(c7.c + System.getProperty("ro.hardware"));
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            String str = (String) arrayList.get(size);
            if (str.contains("x86") || str.contains("X86")) {
                return true;
            }
        }
        if (Build.VERSION.SDK_INT > 20) {
            String[] strArr = Build.SUPPORTED_ABIS;
            String[] strArr2 = Build.SUPPORTED_32_BIT_ABIS;
            String[] strArr3 = Build.SUPPORTED_64_BIT_ABIS;
            for (String str2 : strArr) {
                if (str2.equals("x86") || str2.equals("X86")) {
                    return true;
                }
            }
            for (String str3 : strArr2) {
                if (str3.equals("x86") || str3.equals("X86")) {
                    return true;
                }
            }
            for (String str4 : strArr3) {
                if (str4.equals("x86") || str4.equals("X86")) {
                    return true;
                }
            }
        }
        return false;
    }
}
