package com.coralline.sea;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.coralline.sea.n7;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class z7 {
    public static z7 h;
    public JSONObject a = new JSONObject();
    public JSONObject b = new JSONObject();
    public JSONObject c = new JSONObject();
    public boolean d = false;
    public boolean e = false;
    public int f = 0;
    public boolean g = false;

    public enum a {
        FILE,
        APP,
        PATH_MOUNT,
        MOUNT_INFO,
        PROC_SELF_NET_UNIX,
        CONFIG
    }

    public z7(Context context) {
    }

    public static synchronized z7 a(Context context) {
        if (h == null) {
            h = new z7(context);
        }
        return h;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, int i) {
        this.g = true;
        this.f = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void l() {
        n7.a(n3.a().a, new n7.b() { // from class: com.coralline.sea.-$$Lambda$z7$4TIqsVu9h21vsJ4yb7KohC7Uj1E
            @Override // com.coralline.sea.n7.b
            public final void a(Context context, int i) {
                this.f$0.a(context, i);
            }
        });
    }

    public final void a(a aVar, String str) {
        String lowerCase = aVar.name().toLowerCase();
        try {
            if (this.c.has(lowerCase)) {
                this.c.getJSONArray(lowerCase).put(str);
            } else {
                this.c.put(lowerCase, new JSONArray().put(str));
            }
        } catch (JSONException e) {
        }
    }

    public final void a(a aVar, JSONArray jSONArray) {
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                a(aVar, jSONArray.optString(i));
            } catch (Exception e) {
                return;
            }
        }
    }

    public final synchronized void a(String str, String str2) {
        try {
            JSONArray jSONArrayOptJSONArray = this.b.optJSONArray(str);
            if (jSONArrayOptJSONArray == null) {
                jSONArrayOptJSONArray = new JSONArray();
            }
            jSONArrayOptJSONArray.put(str2);
            this.b.put(str, jSONArrayOptJSONArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean a() {
        return true;
    }

    public boolean a(String str) {
        for (String str2 : b2.d) {
            String str3 = str2 + str;
            if (new File(str3).exists()) {
                a(a.FILE, str3);
                a("su_paths_exist", str3);
                return true;
            }
        }
        return false;
    }

    public boolean b() {
        String strL = i6.l();
        if (TextUtils.isEmpty(strL)) {
            return false;
        }
        a(a.FILE, strL);
        a("bin_paths_exist", strL);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00e7  */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 3 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean c() throws Throwable {
        BufferedReader bufferedReader;
        String str;
        Throwable th;
        Process processStart;
        boolean z;
        Process processStart2;
        BufferedReader bufferedReader2;
        String str2 = "ro.debuggable";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("getprop", "ro.debuggable");
            processBuilder.redirectErrorStream(true);
            processStart = processBuilder.start();
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(processStart.getInputStream()));
                try {
                    String line = bufferedReader.readLine();
                    if (line == null || !line.equals("1")) {
                        z = false;
                    } else {
                        a(a.CONFIG, "ro.debuggable is open");
                        z = true;
                    }
                    if (z) {
                        str = "ro.debuggable";
                        processStart2 = processStart;
                        bufferedReader2 = bufferedReader;
                    } else {
                        str = "ro.secure";
                        try {
                            ProcessBuilder processBuilder2 = new ProcessBuilder("getprop", "ro.secure");
                            processBuilder2.redirectErrorStream(true);
                            processStart2 = processBuilder2.start();
                            try {
                                bufferedReader2 = new BufferedReader(new InputStreamReader(processStart2.getInputStream()));
                                try {
                                    String line2 = bufferedReader2.readLine();
                                    if (line2 != null && line2.equals("0")) {
                                        a(a.CONFIG, "ro.secure is close");
                                        z = true;
                                    }
                                } catch (Exception e) {
                                    bufferedReader = bufferedReader2;
                                    processStart = processStart2;
                                    str2 = "ro.secure";
                                    if (z) {
                                        a("dangerous_props_exist", str2);
                                    }
                                    p9.a(bufferedReader);
                                    if (processStart != null) {
                                        processStart.destroy();
                                    }
                                    return false;
                                } catch (Throwable th2) {
                                    th = th2;
                                    bufferedReader = bufferedReader2;
                                    processStart = processStart2;
                                    if (z) {
                                        a("dangerous_props_exist", str);
                                    }
                                    p9.a(bufferedReader);
                                    if (processStart != null) {
                                        processStart.destroy();
                                    }
                                    throw th;
                                }
                            } catch (Exception e2) {
                            } catch (Throwable th3) {
                                th = th3;
                            }
                        } catch (Exception e3) {
                        } catch (Throwable th4) {
                            th = th4;
                        }
                    }
                    if (z) {
                        a("dangerous_props_exist", str);
                    }
                    p9.a(bufferedReader2);
                    processStart2.destroy();
                    return z;
                } catch (Exception e4) {
                    z = false;
                    if (z) {
                    }
                    p9.a(bufferedReader);
                    if (processStart != null) {
                    }
                    return false;
                } catch (Throwable th5) {
                    th = th5;
                    str = "ro.debuggable";
                    z = false;
                    if (z) {
                    }
                    p9.a(bufferedReader);
                    if (processStart != null) {
                    }
                    throw th;
                }
            } catch (Exception e5) {
                bufferedReader = null;
            } catch (Throwable th6) {
                bufferedReader = null;
                str = "ro.debuggable";
                th = th6;
            }
        } catch (Exception e6) {
            processStart = null;
            bufferedReader = null;
        } catch (Throwable th7) {
            bufferedReader = null;
            str = "ro.debuggable";
            th = th7;
            processStart = null;
        }
    }

    public boolean d() throws Throwable {
        Throwable th;
        String str;
        BufferedReader bufferedReader = null;
        try {
            char c = 1;
            ProcessBuilder processBuilder = new ProcessBuilder("mount");
            processBuilder.redirectErrorStream(true);
            Process processStart = processBuilder.start();
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(processStart.getInputStream()));
            boolean z = false;
            while (true) {
                try {
                    String line = bufferedReader2.readLine();
                    if (line == null) {
                        processStart.waitFor();
                        Objects.toString(processBuilder.command());
                        p9.a(bufferedReader2);
                        return z;
                    }
                    String[] strArrSplit = line.split(" ");
                    if (strArrSplit.length >= 4) {
                        String str2 = strArrSplit[c];
                        String str3 = strArrSplit[3];
                        String[] strArr = b2.e;
                        int length = strArr.length;
                        boolean z2 = z;
                        int i = 0;
                        while (i < length) {
                            String str4 = strArr[i];
                            if (str2.equalsIgnoreCase(str4)) {
                                String[] strArrSplit2 = str3.split(",");
                                int length2 = strArrSplit2.length;
                                int i2 = 0;
                                while (i2 < length2) {
                                    str = str2;
                                    if (strArrSplit2[i2].equalsIgnoreCase("rw")) {
                                        a(a.PATH_MOUNT, str4);
                                        a("system_paths_mountable", str3);
                                        z2 = true;
                                        break;
                                    }
                                    i2++;
                                    str2 = str;
                                }
                                str = str2;
                            } else {
                                str = str2;
                            }
                            i++;
                            str2 = str;
                            c = 1;
                        }
                        z = z2;
                    }
                } catch (Exception e) {
                    bufferedReader = bufferedReader2;
                    p9.a(bufferedReader);
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                    p9.a(bufferedReader);
                    throw th;
                }
            }
        } catch (Exception e2) {
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public boolean e() {
        if (!a()) {
            return false;
        }
        int i = 0;
        while (true) {
            String[] strArr = b2.d;
            if (i >= strArr.length) {
                return false;
            }
            String str = strArr[i] + x1.b;
            if (s3.a(str)) {
                a("su_files_exist", str);
                a(a.FILE, str);
                return true;
            }
            i++;
        }
    }

    public boolean f() {
        if (!s3.a(b2.f)) {
            return false;
        }
        a("KingRoot", b2.f);
        a(a.FILE, b2.f);
        return true;
    }

    public final synchronized boolean g() {
        String str;
        a aVar;
        if (!n3.a().D) {
            return false;
        }
        if (!com.coralline.sea.a.b.bcm()) {
            if (!this.e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.coralline.sea.-$$Lambda$z7$N1dkQhxBtRboXHoNzsZoCyhUYHA
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.l();
                    }
                });
                this.e = true;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            while (!this.g && System.currentTimeMillis() - jCurrentTimeMillis <= 60000) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                }
            }
            if (this.f == 1) {
                str = "mountInfo: " + this.f;
                a(b2.g, str);
                aVar = a.MOUNT_INFO;
            }
            return false;
        }
        str = "isMountIdChecked: true";
        a(b2.g, "isMountIdChecked: true");
        aVar = a.MOUNT_INFO;
        a(aVar, str);
        return true;
    }

    public final boolean h() {
        if (!com.coralline.sea.a.b.cpsnu()) {
            return false;
        }
        a(b2.h, "Found the features of Magisk or KernelSU in the /proc/self/net/unix file");
        a(a.PROC_SELF_NET_UNIX, "Found the features of Magisk or KernelSU in the /proc/self/net/unix file");
        return true;
    }

    public boolean i() {
        BufferedReader bufferedReader;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("which", x1.b);
            processBuilder.redirectErrorStream(false);
            Process processStart = processBuilder.start();
            bufferedReader = new BufferedReader(new InputStreamReader(processStart.getInputStream()));
            try {
                String line = bufferedReader.readLine();
                processStart.waitFor();
                Objects.toString(processBuilder.command());
                p9.a(bufferedReader);
                if (line != null && line.endsWith(x1.b)) {
                    a(a.FILE, line);
                    a("which_su", line);
                    return true;
                }
            } catch (Throwable th) {
                if (bufferedReader != null) {
                    p9.a(bufferedReader);
                }
            }
        } catch (Throwable th2) {
            bufferedReader = null;
        }
        return false;
    }

    public boolean j() {
        JSONObject jSONObjectC = t5.d().c();
        if (jSONObjectC != null) {
            JSONArray jSONArrayOptJSONArray = jSONObjectC.optJSONArray("detail");
            JSONArray jSONArrayOptJSONArray2 = jSONObjectC.optJSONArray("magisk_reason");
            for (int i = 0; i < jSONArrayOptJSONArray2.length(); i++) {
                try {
                    Object obj = jSONArrayOptJSONArray2.get(i);
                    if (obj instanceof String) {
                        a(a.APP, (String) obj);
                    } else if (obj instanceof JSONObject) {
                        a(a.FILE, ((JSONObject) obj).optJSONArray("file"));
                    }
                } catch (Exception e) {
                }
            }
            for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                try {
                    a("magisk_root", jSONArrayOptJSONArray.optString(i2));
                } catch (Exception e2) {
                }
            }
            return true;
        }
        return false;
    }

    public synchronized JSONObject k() {
        if (v6.b != 1) {
            x9.a("The list of applications has not been obtained!");
            return null;
        }
        if (this.d) {
            return this.a;
        }
        boolean zF = f();
        boolean zA = a(x1.b);
        boolean zC = c();
        boolean zD = d();
        boolean zI = i();
        boolean zE = e();
        boolean zB = b();
        boolean zJ = j();
        boolean zG = g();
        boolean zH = h();
        if (zF || zA || zC || zD || zI || zE || zJ || zB || zG || zH) {
            this.a.put("is_root", true);
            this.a.put("detail", this.b);
            this.a.put("root_reason", this.c);
        }
        this.d = true;
        return this.a;
    }
}
