package com.coralline.sea;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class d6 extends x6 {
    public String b;
    public JSONArray c;
    public JSONArray d;
    public JSONObject e;
    public Pattern f;
    public Set<String> g;
    public boolean h;
    public Handler i;
    public HashMap<String, List<String>> j;

    public class a extends HashMap<String, List<String>> {
        public a() {
            put("00:08:22", new ArrayList(Collections.singletonList("*")));
            put("c8:0e:77", new ArrayList(Arrays.asList("Letv", "LeMobile")));
            put("74:ac:5f", new ArrayList(Collections.singletonList("360")));
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            JSONObject jSONObjectA;
            JSONObject jSONObjectA2;
            synchronized (this) {
                try {
                    jSONObjectA = a9.a(a9.a, (JSONObject) null);
                    jSONObjectA2 = x2.a(1);
                } catch (Exception e) {
                }
                if (jSONObjectA2 == null) {
                    return;
                }
                Context context = n3.a().a;
                if (jSONObjectA == null) {
                    jSONObjectA = context != null ? d6.this.a(context) : null;
                    if (jSONObjectA == null) {
                        a9.b(a9.a, jSONObjectA2.toString());
                        if (context != null) {
                            d6.this.a(jSONObjectA2, context);
                        }
                        return;
                    }
                    a9.b(a9.a, jSONObjectA.toString());
                }
                d6.this.h = false;
                d6.this.b = jSONObjectA2.optString("manufacturer");
                d6.this.c = new JSONArray();
                d6.this.d = new JSONArray();
                d6.this.a(jSONObjectA, jSONObjectA2);
                if (d6.this.c.length() > 0) {
                    d6 d6Var = d6.this;
                    if (!d6Var.a(d6Var.c)) {
                        String string = d6.this.c.toString();
                        if (!d6.this.g.contains(string)) {
                            d6.this.g.add(string);
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("detail", d6.this.d);
                            jSONObject.put("data", d6.this.c);
                            d6.this.push(e2.b, x9.k, jSONObject.toString());
                        }
                    }
                }
                if (d6.this.h) {
                    a9.b(a9.a, jSONObjectA2.toString());
                    if (context != null) {
                        d6.this.a(jSONObjectA2, context);
                    }
                }
            }
        }
    }

    public d6() {
        super(x9.k, 30);
        this.j = new a();
    }

    public final List<String> a(String str) throws Exception {
        JSONArray jSONArray = new JSONArray(str);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            String strOptString = jSONArray.optString(i);
            if (strOptString.length() != 0 && !ja.h(strOptString)) {
                arrayList.add(strOptString);
            }
        }
        return arrayList;
    }

    public JSONObject a(Context context) {
        try {
            if (Build.VERSION.SDK_INT < 23 || context.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                return null;
            }
            String str = context.getPackageName() + ".persist";
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(str);
            if (!externalStoragePublicDirectory.exists()) {
                return null;
            }
            File file = new File(externalStoragePublicDirectory.getAbsolutePath() + File.separator + str);
            if (file.exists()) {
                StringBuilder sb = new StringBuilder();
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = fileInputStream.read(bArr);
                    if (i <= 0) {
                        fileInputStream.close();
                        return new JSONObject(new String(Base64.decode(sb.toString(), 2)));
                    }
                    sb.append(new String(bArr, 0, i));
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final void a() {
        try {
            JSONObject jSONObjectOptJSONObject = z1.a("device_reuse").optJSONObject("virtual_mac_prefix");
            if (jSONObjectOptJSONObject == null) {
                for (Map.Entry<String, List<String>> entry : this.j.entrySet()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(entry.getKey());
                    sb.append(" -> ");
                    sb.append(TextUtils.join(", ", entry.getValue()));
                }
                return;
            }
            JSONArray jSONArrayNames = jSONObjectOptJSONObject.names();
            if (jSONArrayNames == null) {
                for (Map.Entry<String, List<String>> entry2 : this.j.entrySet()) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(entry2.getKey());
                    sb2.append(" -> ");
                    sb2.append(TextUtils.join(", ", entry2.getValue()));
                }
                return;
            }
            for (int i = 0; i < jSONArrayNames.length(); i++) {
                String string = jSONArrayNames.getString(i);
                JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray(string);
                if (jSONArrayOptJSONArray != null) {
                    for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                        String strOptString = jSONArrayOptJSONArray.optString(i2);
                        if (strOptString != null) {
                            String lowerCase = strOptString.toLowerCase();
                            List<String> arrayList = this.j.get(lowerCase);
                            if (arrayList == null) {
                                arrayList = new ArrayList<>();
                                this.j.put(lowerCase, arrayList);
                            }
                            if (!arrayList.contains(string)) {
                                arrayList.add(string);
                            }
                        }
                    }
                }
            }
            for (Map.Entry<String, List<String>> entry3 : this.j.entrySet()) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(entry3.getKey());
                sb3.append(" -> ");
                sb3.append(TextUtils.join(", ", entry3.getValue()));
            }
        } catch (Exception e) {
            for (Map.Entry<String, List<String>> entry4 : this.j.entrySet()) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(entry4.getKey());
                sb4.append(" -> ");
                sb4.append(TextUtils.join(", ", entry4.getValue()));
            }
        } catch (Throwable th) {
            for (Map.Entry<String, List<String>> entry5 : this.j.entrySet()) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append(entry5.getKey());
                sb5.append(" -> ");
                sb5.append(TextUtils.join(", ", entry5.getValue()));
            }
            throw th;
        }
    }

    public final void a(String str, String str2) throws Exception {
        boolean z;
        List<String> listA = a(str2);
        List<String> listA2 = a(str);
        if (listA.size() == 0 || listA2.size() == 0) {
            z = false;
        } else if (listA.size() != listA2.size()) {
            List<String> list = listA.size() > listA2.size() ? listA2 : listA;
            List<String> list2 = listA.size() > listA2.size() ? listA : listA2;
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                if (!list2.contains(it.next())) {
                    z = true;
                    break;
                }
            }
            z = false;
        } else {
            Iterator<String> it2 = listA.iterator();
            while (it2.hasNext()) {
                if (!listA2.contains(it2.next())) {
                    z = true;
                    break;
                }
            }
            z = false;
        }
        if (z) {
            ListIterator<String> listIterator = listA.listIterator();
            while (listIterator.hasNext()) {
                String next = listIterator.next();
                if (listA2.contains(next)) {
                    listA2.remove(next);
                    listIterator.remove();
                }
            }
            boolean zA = a(listA);
            boolean zA2 = a(listA2);
            if (!zA || zA2) {
                if (zA || !zA2) {
                    b("imei", str, str2);
                }
            }
        }
    }

    public final void a(String str, String str2, String str3) throws Exception {
        if (str3.equals(str2)) {
            return;
        }
        this.h = true;
        if (c(str) || b(str2) || b(str3)) {
            return;
        }
        if (str.equals("mac")) {
            b(str2, str3);
            return;
        }
        if (str.equals("imei")) {
            a(str2, str3);
        } else {
            if (str.equals("bht_mac") && str2.equalsIgnoreCase(str3)) {
                return;
            }
            b(str, str2, str3);
        }
    }

    public void a(JSONObject jSONObject, Context context) {
        try {
            if (Build.VERSION.SDK_INT < 23 || context.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                return;
            }
            String str = context.getPackageName() + ".persist";
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(str);
            if (!externalStoragePublicDirectory.exists()) {
                externalStoragePublicDirectory.mkdir();
            }
            File file = new File(externalStoragePublicDirectory.getAbsolutePath() + File.separator + str);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(Base64.encodeToString(jSONObject.toString().getBytes(StandardCharsets.UTF_8), 2).getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void a(JSONObject jSONObject, JSONObject jSONObject2) {
        Iterator<String> itKeys = jSONObject2.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            try {
                a(next, jSONObject.optString(next), jSONObject2.optString(next));
            } catch (Exception e) {
            }
        }
    }

    public final boolean a(List<String> list) {
        v4 v4VarB = a5.a().b();
        Context context = n3.a().a;
        for (String str : list) {
            if (v4VarB.a(context) || v4VarB.b(context)) {
                if (this.f.matcher(str).matches()) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean a(JSONArray jSONArray) {
        byte b2;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                String string = ((JSONObject) jSONArray.get(i)).getString("field");
                int iHashCode = string.hashCode();
                if (iHashCode != -1430655860) {
                    if (iHashCode != -1375934236) {
                        if (iHashCode != -910241155) {
                            b2 = (iHashCode == 1099983256 && string.equals("dev_date")) ? (byte) 0 : (byte) -1;
                        } else if (string.equals("dev_date_utc")) {
                            b2 = 1;
                        }
                    } else if (string.equals("fingerprint")) {
                        b2 = 2;
                    }
                } else if (string.equals("build_id")) {
                    b2 = 3;
                }
                switch (b2) {
                    case 0:
                        z = true;
                        continue;
                        break;
                    case 1:
                        z2 = true;
                        continue;
                        break;
                    case 2:
                        z4 = true;
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
                z3 = true;
            } catch (Exception e) {
            }
        }
        if (z && z2) {
            return true;
        }
        return z3 && z4;
    }

    public final void b(String str, String str2) throws Exception {
        String strB;
        if (Build.VERSION.SDK_INT >= 29 || (strB = l6.b(n3.a().a)) == null || strB.equals(f2.e) || d(str2)) {
            return;
        }
        String strOptString = this.e.optString("mac");
        if (d(str)) {
            str = strOptString;
        }
        if (!TextUtils.isEmpty(str) && !str2.equalsIgnoreCase(str)) {
            b("mac", str, str2);
        }
        if (str2.equalsIgnoreCase(strOptString)) {
            return;
        }
        c("mac", str2);
    }

    public final void b(String str, String str2, String str3) throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("field", str);
        jSONObject.put("old", str2);
        jSONObject.put("cur", str3);
        this.c.put(jSONObject);
        this.d.put(str);
    }

    public final boolean b(String str) {
        return str == null || str.isEmpty() || str.equalsIgnoreCase("null") || str.equalsIgnoreCase("ni_null") || str.equalsIgnoreCase("addr_null") || str.equalsIgnoreCase("exception") || str.equalsIgnoreCase(aa.m) || ja.f(str) || ja.g(str) || str.equalsIgnoreCase("No Permission") || str.equalsIgnoreCase("NO READ_PHONE_STATE") || str.equalsIgnoreCase("Need_BLUETOOTH_permission") || str.equalsIgnoreCase("NO ACCESS_WIFI_STATE") || str.equals("00:00:00:00:00:00") || str.equals("02:00:00:00:00:00") || str.equalsIgnoreCase("ff:ff:ff:ff:ff:ff");
    }

    public final void c(String str, String str2) {
        try {
            this.e.put(str, str2);
            a9.b(a9.c, this.e);
        } catch (Exception e) {
        }
    }

    public final boolean c(String str) {
        if (Arrays.asList("cpu_abi", "phone_number", "run_mode", "imsi", "sim_serial_number", "origin_cert_md5").contains(str)) {
            return true;
        }
        return str.startsWith("resolution_");
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        this.i.postDelayed(new b(), ja.a(0, 10000));
    }

    public final boolean d(String str) {
        String lowerCase = str.toLowerCase();
        for (Map.Entry<String, List<String>> entry : this.j.entrySet()) {
            if (lowerCase.startsWith(entry.getKey())) {
                for (String str2 : entry.getValue()) {
                    if (str2.equals("*") || this.b.equalsIgnoreCase(str2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void initialize() {
        JSONObject jSONObjectA = a9.a(a9.c, (JSONObject) null);
        this.e = jSONObjectA;
        if (jSONObjectA == null) {
            this.e = new JSONObject();
        }
        this.f = Pattern.compile("^[9,a-fA-F][0-9a-fA-F]{13}$");
        this.g = new HashSet();
        this.h = false;
        a();
        HandlerThread handlerThread = new HandlerThread("Risk-Thread-MocDevCheck");
        handlerThread.start();
        this.i = new Handler(handlerThread.getLooper());
    }
}
