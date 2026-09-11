package com.coralline.sea.checkers.screen;

import android.content.Context;
import android.util.Base64;
import com.coralline.sea.z1;
import java.util.ArrayList;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class RustDeskDetector {
    public static int[] a;

    public static class a {
        public final String a;
        public final int b;
        public final byte[] c;
        public final byte[] d;

        public a(String str, int i, byte[] bArr, byte[] bArr2) {
            this.a = str;
            this.b = i;
            this.c = bArr;
            this.d = bArr2;
        }
    }

    public static boolean a(Context context) {
        try {
            for (a aVar : b(context)) {
                int[] iArr = new int[1];
                a = iArr;
                if (nativeDetect(aVar.a, aVar.b, aVar.c, aVar.d, iArr)) {
                    int i = a[0];
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static ArrayList<a> b(Context context) throws Exception {
        ArrayList<a> arrayList = new ArrayList<>();
        JSONArray jSONArray = z1.a("screen_sharing").getJSONArray("configurations");
        jSONArray.length();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            Objects.toString(jSONObject);
            byte[] bArrDecode = Base64.decode(jSONObject.getString("send_buffer"), 0);
            byte[] bArrDecode2 = Base64.decode(jSONObject.getString("expect_header"), 0);
            new String(bArrDecode);
            new String(bArrDecode2);
            arrayList.add(new a(jSONObject.getString("server_ip"), jSONObject.getInt("server_port"), bArrDecode, bArrDecode2));
        }
        return arrayList;
    }

    private static native boolean nativeDetect(String str, int i, byte[] bArr, byte[] bArr2, int[] iArr);
}
