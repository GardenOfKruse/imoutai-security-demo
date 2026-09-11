package com.coralline.sea;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class f6 {
    public static f6 j;
    public String a = null;
    public String b = null;
    public String c = null;
    public String d = null;
    public int[] e = {47250, 57486, 17870, 25983, 10827, 54278, 12463, 49364, 52857, 18529};
    public int f = -1;
    public boolean g = false;
    public boolean h = false;
    public JSONArray i = new JSONArray();

    public class a implements Runnable {
        public final /* synthetic */ ServerSocket a;
        public final /* synthetic */ String b;

        public a(ServerSocket serverSocket, String str) {
            this.a = serverSocket;
            this.b = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    Socket socketAccept = this.a.accept();
                    new DataInputStream(socketAccept.getInputStream()).readUTF();
                    new DataOutputStream(socketAccept.getOutputStream()).writeUTF(this.b);
                    socketAccept.close();
                } catch (Exception e) {
                    e.toString();
                }
            }
        }
    }

    public static PackageInfo c(Context context) {
        try {
            Object objC = q7.j("android.content.pm.IPackageManager$Stub").a("asInterface", (IBinder) q7.a(q7.j("android.os.ServiceManagerNative").a("asInterface", (IBinder) q7.j("com.android.internal.os.BinderInternal").b("getContextObject").c()).c()).a(y8.c, "package").c()).c();
            String strSubstring = (String) q7.a(objC).a("getNameForUid", ja.q()).c();
            if (strSubstring.contains(":")) {
                Matcher matcher = Pattern.compile("/data/(data|user/0)/(.*)").matcher(context.getFilesDir().getPath());
                if (matcher.find()) {
                    strSubstring = matcher.group(2).substring(0, matcher.group(2).indexOf("/"));
                }
            }
            return Build.VERSION.SDK_INT >= 33 ? (PackageInfo) q7.a(objC).a("getPackageInfo", strSubstring, 0L, 0).c() : (PackageInfo) q7.a(objC).a("getPackageInfo", strSubstring, 0, 0).c();
        } catch (Exception e) {
            return null;
        }
    }

    public static synchronized f6 d() {
        if (j == null) {
            j = new f6();
        }
        return j;
    }

    public final void a(int i, String str) throws Exception {
        new Thread(new a(new ServerSocket(i), str)).start();
    }

    public final boolean a() {
        int iB = i6.b("/data/data/" + this.a + "/.RiskStub" + n3.a().f + "/.doubleOpen", 66, m9.l);
        if (iB < 0) {
            this.i.put("detect multi open as create failed.");
            return true;
        }
        int iD = i6.d(iB, "hello,world".getBytes(), 11);
        int iF = i6.f(iB);
        if (iD >= 0 && iF >= 0) {
            return false;
        }
        this.i.put("detect multi open as write or close failed.");
        return true;
    }

    public final boolean a(Context context) {
        List<PackageInfo> listB = s.b(context, 0);
        if (listB == null) {
            return false;
        }
        Iterator<PackageInfo> it = listB.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (this.a.equals(it.next().packageName)) {
                i++;
            }
        }
        if (i > 1) {
            this.i.put("checkInstalledPackageName");
        }
        return i > 1;
    }

    public final boolean a(PackageInfo packageInfo) {
        StringBuilder sb = new StringBuilder("/data/user/\\d+/");
        sb.append(this.a);
        sb.append("/files");
        return Pattern.compile(sb.toString()).matcher(this.b).matches() && (packageInfo == null || this.a.equalsIgnoreCase(packageInfo.packageName));
    }

    public JSONObject b(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (this.h) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("pid", Process.myPid());
                PackageInfo packageInfoC = c(context);
                jSONObject2.put("is_system_open", a(packageInfoC));
                if (packageInfoC != null) {
                    jSONObject2.put("package", packageInfoC.packageName);
                    ApplicationInfo applicationInfo = packageInfoC.applicationInfo;
                    jSONObject2.put("name", applicationInfo != null ? applicationInfo.loadLabel(context.getPackageManager()).toString() : i2.b);
                } else {
                    jSONObject2.put("package", i2.b);
                    jSONObject2.put("name", i2.b);
                }
                jSONObject.put("copy_open", jSONObject2);
                jSONObject.put("detail", this.i);
                return jSONObject;
            }
        } catch (Exception e) {
        }
        return jSONObject;
    }

    public final boolean b() {
        String string;
        String strA = b8.a();
        String[] strArrSplit = strA == null ? null : strA.split("\n");
        if (strArrSplit == null || (string = ja.q().toString()) == null) {
            return false;
        }
        LinkedList linkedList = new LinkedList();
        for (String str : strArrSplit) {
            if (str.contains(string)) {
                String str2 = "/data/data/" + str.substring(str.lastIndexOf(" ") + 1);
                if (new File(str2).exists()) {
                    linkedList.add(str2);
                }
            }
        }
        if (linkedList.size() > 1) {
            this.i.put("checkPackageNameInAllProcess");
        }
        linkedList.size();
        linkedList.toString();
        return linkedList.size() > 1;
    }

    public final boolean c() {
        boolean z = !Pattern.matches("/data/(data|user/0)/" + this.a + "/files", this.b);
        if (z) {
            this.i.put("checkPrivateFilePath");
        }
        return z;
    }

    public boolean d(Context context) {
        if (this.g) {
            return this.h;
        }
        this.a = context.getPackageName();
        this.b = context.getFilesDir().getPath();
        this.c = "s1983rsdkjadfh9aewf" + this.a;
        this.d = "189r3yfdsjkaf1!%#@sadf" + this.a;
        boolean zA = a();
        boolean zC = c();
        if (zA || zC) {
            this.h = true;
        }
        this.g = true;
        return this.h;
    }

    public final boolean e() {
        for (int i : this.e) {
            if (i != this.f) {
                try {
                    Socket socket = new Socket("127.0.0.1", i);
                    new DataOutputStream(socket.getOutputStream()).writeUTF(this.c);
                    String utf = new DataInputStream(socket.getInputStream()).readUTF();
                    socket.close();
                    if (utf.equals(this.d)) {
                        return true;
                    }
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    public final boolean f() {
        boolean zE = e();
        g();
        if (zE) {
            this.i.put("makeSocketCheck");
        }
        return zE;
    }

    public final void g() {
        if (this.f != -1) {
            return;
        }
        int i = 0;
        while (true) {
            int[] iArr = this.e;
            if (i >= iArr.length) {
                break;
            }
            try {
                a(iArr[i], this.d);
                this.f = this.e[i];
                break;
            } catch (Exception e) {
                int i2 = this.e[i];
                e.toString();
                i++;
            }
        }
        int length = this.e.length;
    }
}
