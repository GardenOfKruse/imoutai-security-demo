package com.coralline.sea;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class t0 {
    public static final u0 a = new u0();
    public static String b = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    public static int a(String str, OutputStream outputStream) throws IOException {
        return a.a(str, outputStream);
    }

    public static int a(byte[] bArr, int i, int i2, OutputStream outputStream) throws IOException {
        return a.b(bArr, i, i2, outputStream);
    }

    public static int a(byte[] bArr, OutputStream outputStream) throws IOException {
        return a.b(bArr, 0, bArr.length, outputStream);
    }

    public static byte[] a(String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((str.length() / 4) * 3);
        try {
            a.a(str, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("exception decoding base64 string: " + e);
        }
    }

    public static byte[] a(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((bArr.length / 4) * 3);
        try {
            a.a(bArr, 0, bArr.length, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("exception decoding base64 string: " + e);
        }
    }

    public static boolean b(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (b.indexOf(Character.toString(str.charAt(i))) == -1) {
                return false;
            }
        }
        return true;
    }

    public static byte[] b(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(((bArr.length + 2) / 3) * 4);
        try {
            a.b(bArr, 0, bArr.length, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("exception encoding base64 string: " + e);
        }
    }
}
