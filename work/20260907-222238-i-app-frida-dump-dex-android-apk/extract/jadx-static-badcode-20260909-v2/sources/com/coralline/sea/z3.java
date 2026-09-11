package com.coralline.sea;

import android.util.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class z3 {
    public static final byte h = 15;
    public static final byte i = 14;
    public static z3 j = new z3();
    public String a = c7.c;
    public String b = c7.c;
    public String c = c7.c;
    public String d = c7.c;
    public byte[] e = null;
    public byte[] f = null;
    public byte[] g = null;

    public static z3 b() {
        return j;
    }

    public JSONObject a() {
        try {
            byte[] bArr = this.f;
            if (bArr == null || new String(bArr).trim().length() <= 0) {
                return null;
            }
            return a("anr", Base64.encodeToString(this.f, 0), false);
        } catch (Exception e) {
            return null;
        }
    }

    public final JSONObject a(String str, String str2, boolean z) {
        if (str2 != null) {
            try {
                if (str2.length() == 0) {
                    return null;
                }
                JSONObject jSONObject = new JSONObject();
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("crash_type", str);
                    jSONObject2.put("start_id", ja.o());
                    jSONObject2.put("stack", str2);
                    jSONObject.put("data", jSONObject2);
                    if (z) {
                        jSONObject.put("self_crash", true);
                    }
                } catch (JSONException | Exception e) {
                }
                return jSONObject;
            } catch (JSONException | Exception e2) {
            }
        }
        return null;
    }

    public final byte[] a(String str) throws Throwable {
        byte[] bArr;
        FileInputStream fileInputStream;
        InputStream inputStream = null;
        try {
            try {
                fileInputStream = new FileInputStream(str);
                try {
                    try {
                        byte[] bArr2 = new byte[fileInputStream.available()];
                        try {
                            fileInputStream.read(bArr2);
                            try {
                                fileInputStream.close();
                                return bArr2;
                            } catch (IOException e) {
                                e = e;
                                e.toString();
                                return bArr2;
                            }
                        } catch (IOException e2) {
                            inputStream = bArr2;
                            e = e2;
                            bArr = inputStream;
                            inputStream = fileInputStream;
                            e.toString();
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e3) {
                                    e = e3;
                                    bArr2 = bArr;
                                    e.toString();
                                    return bArr2;
                                }
                            }
                            return bArr;
                        } catch (Exception e4) {
                            inputStream = bArr2;
                            e = e4;
                            bArr = inputStream;
                            inputStream = fileInputStream;
                            e.toString();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            return bArr;
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e5) {
                                e5.toString();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e6) {
                    e = e6;
                } catch (Exception e7) {
                    e = e7;
                }
            } catch (Throwable th2) {
                th = th2;
                fileInputStream = null;
            }
        } catch (IOException e8) {
            e = e8;
            bArr = null;
        } catch (Exception e9) {
            e = e9;
            bArr = null;
        }
    }

    public JSONObject c() {
        try {
            byte[] bArr = this.e;
            if (bArr == null || bArr.length <= 0 || new String(bArr).trim().length() <= 0) {
                return null;
            }
            byte[] bArr2 = this.e;
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr2, 1, bArr2.length);
            byte b = this.e[0];
            if (b == 15) {
                return a("java", Base64.encodeToString(bArrCopyOfRange, 2), false);
            }
            if (b == 14) {
                return a("java", Base64.encodeToString(bArrCopyOfRange, 2), true);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject d() {
        try {
            byte[] bArr = this.g;
            if (bArr == null || new String(bArr).trim().length() <= 0) {
                return null;
            }
            return a("so", Base64.encodeToString(this.g, 0), false);
        } catch (Exception e) {
            return null;
        }
    }

    public void e() {
        this.d = n3.a().r;
        this.a = this.d + "/everisk_jcrash.txt";
        this.b = this.d + "/everisk_anrcrash.txt";
        this.c = this.d + "/everisk_ccrash.dmp";
        if (new File(this.a).exists()) {
            this.e = a(this.a);
        }
        if (new File(this.b).exists()) {
            this.f = a(this.b);
        }
        if (new File(this.c).exists()) {
            this.g = a(this.c);
        }
    }
}
