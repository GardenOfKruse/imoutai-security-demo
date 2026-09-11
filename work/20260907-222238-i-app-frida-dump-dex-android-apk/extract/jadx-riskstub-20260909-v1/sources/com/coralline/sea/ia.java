package com.coralline.sea;

import java.math.BigInteger;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class ia {
    public static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static byte a(byte b2) {
        return (byte) (((byte) (Byte.decode("0x".concat(new String(new byte[]{b2}))).byteValue() << 4)) ^ z3.h);
    }

    public static byte a(byte b2, byte b3) {
        return (byte) (((byte) (Byte.decode("0x".concat(new String(new byte[]{b2}))).byteValue() << 4)) ^ Byte.decode("0x".concat(new String(new byte[]{b3}))).byteValue());
    }

    public static byte a(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static int a(char c, int i) {
        int iDigit = Character.digit(c, 16);
        if (iDigit != -1) {
            return iDigit;
        }
        throw new RuntimeException("Illegal hexadecimal character " + c + " at index " + i);
    }

    public static int a(String str, int i, int i2) {
        try {
            return Integer.parseInt(str, i2);
        } catch (NumberFormatException e) {
            return i;
        }
    }

    public static String a(int i) {
        String hexString = Integer.toHexString(i);
        if (hexString.length() % 2 == 1) {
            hexString = "0".concat(hexString);
        }
        return hexString.toUpperCase();
    }

    public static String a(int i, int i2) {
        String hexString = Integer.toHexString(i);
        if (hexString.length() % 2 == 1) {
            hexString = "0".concat(hexString);
        }
        return c(hexString.toUpperCase(), i2);
    }

    public static String a(String str) {
        String str2 = c7.c;
        int length = str.length() / 2;
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            str2 = str2 + String.valueOf((char) e(str.substring(i2, i2 + 2)));
        }
        return str2;
    }

    public static String a(String str, int i) {
        String str2 = c7.c;
        int length = str.length() / i;
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 * i;
            i2++;
            str2 = str2 + ((char) e(str.substring(i3, i2 * i)));
        }
        return str2;
    }

    public static String a(byte[] bArr) {
        String str = c7.c;
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                hexString = "0".concat(hexString);
            }
            str = str + hexString.toUpperCase();
        }
        return str;
    }

    public static void a(String str, byte[] bArr) {
        System.out.print(str);
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                hexString = "0".concat(hexString);
            }
            System.out.print(hexString.toUpperCase() + " ");
        }
        System.out.println(c7.c);
    }

    public static byte[] a(long j) {
        byte[] bArr = new byte[8];
        for (int i = 0; i < 8; i++) {
            bArr[i] = (byte) ((j >> (i * 8)) & 255);
        }
        return bArr;
    }

    public static byte[] a(BigInteger bigInteger) {
        if (bigInteger == null) {
            return null;
        }
        if (bigInteger.toByteArray().length == 33) {
            byte[] bArr = new byte[32];
            System.arraycopy(bigInteger.toByteArray(), 1, bArr, 0, 32);
            return bArr;
        }
        if (bigInteger.toByteArray().length == 32) {
            return bigInteger.toByteArray();
        }
        byte[] bArr2 = new byte[32];
        for (int i = 0; i < 32 - bigInteger.toByteArray().length; i++) {
            bArr2[i] = 0;
        }
        System.arraycopy(bigInteger.toByteArray(), 0, bArr2, 32 - bigInteger.toByteArray().length, bigInteger.toByteArray().length);
        return bArr2;
    }

    public static byte[] a(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            bArr2[i3] = bArr[i3 + i];
        }
        return bArr2;
    }

    public static byte[] a(char[] cArr) {
        int length = cArr.length;
        if ((length & 1) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }
        byte[] bArr = new byte[length >> 1];
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int iA = a(cArr[i], i) << 4;
            int i3 = i + 1;
            int iA2 = iA | a(cArr[i3], i3);
            i = i3 + 1;
            bArr[i2] = (byte) (iA2 & 255);
            i2++;
        }
        return bArr;
    }

    public static char[] a(byte[] bArr, boolean z) {
        return a(bArr, z ? a : b);
    }

    public static char[] a(byte[] bArr, char[] cArr) {
        char[] cArr2 = new char[bArr.length << 1];
        int i = 0;
        for (byte b2 : bArr) {
            int i2 = i + 1;
            cArr2[i] = cArr[(b2 & 240) >>> 4];
            i = i2 + 1;
            cArr2[i2] = cArr[b2 & z3.h];
        }
        return cArr2;
    }

    public static int b(String str, int i) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return i;
        }
    }

    public static String b(byte[] bArr, boolean z) {
        return b(bArr, z ? a : b);
    }

    public static String b(byte[] bArr, char[] cArr) {
        return new String(a(bArr, cArr));
    }

    public static BigInteger b(byte[] bArr) {
        if (bArr[0] >= 0) {
            return new BigInteger(bArr);
        }
        byte[] bArr2 = new byte[bArr.length + 1];
        bArr2[0] = 0;
        System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
        return new BigInteger(bArr2);
    }

    public static byte[] b(int i) {
        return new byte[]{(byte) ((i >> 0) & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)};
    }

    public static byte[] b(String str) {
        byte[] bArr = new byte[8];
        byte[] bytes = str.getBytes();
        for (int i = 0; i < 8; i++) {
            int i2 = i * 2;
            bArr[i] = a(bytes[i2], bytes[i2 + 1]);
        }
        return bArr;
    }

    public static String c(String str) {
        String str2 = c7.c;
        int length = str.length();
        for (int i = 0; i < length; i++) {
            str2 = str2 + Integer.toHexString(str.charAt(i));
        }
        return str2;
    }

    public static String c(String str, int i) {
        String str2 = c7.c;
        for (int i2 = 0; i2 < i - str.length(); i2++) {
            str2 = "0" + str2;
        }
        return (str2 + str).substring(0, i);
    }

    public static String c(byte[] bArr) {
        StringBuilder sb;
        if (bArr == null) {
            throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
        }
        String string = c7.c;
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                sb = new StringBuilder();
                sb.append(string);
                string = "0";
            } else {
                sb = new StringBuilder();
            }
            sb.append(string);
            sb.append(hexString);
            string = sb.toString();
        }
        return string.toUpperCase();
    }

    public static String c(byte[] bArr, boolean z) {
        String str = c7.c;
        for (byte b2 : bArr) {
            str = str + Integer.toString((b2 & 255) + 256, 16).substring(1);
        }
        return z ? str.toUpperCase() : str;
    }

    public static int d(String str) {
        int iPow = 0;
        for (int length = str.length(); length > 0; length--) {
            iPow = (int) ((Math.pow(2.0d, r0 - length) * ((double) (str.charAt(length - 1) - '0'))) + iPow);
        }
        return iPow;
    }

    public static int d(byte[] bArr) {
        return ((bArr[3] & 255) << 24) | 0 | ((bArr[0] & 255) << 0) | ((bArr[1] & 255) << 8) | ((bArr[2] & 255) << 16);
    }

    public static int e(String str) {
        String upperCase = str.toUpperCase();
        int iPow = 0;
        for (int length = upperCase.length(); length > 0; length--) {
            char cCharAt = upperCase.charAt(length - 1);
            iPow = (int) ((Math.pow(16.0d, r0 - length) * ((double) ((cCharAt < '0' || cCharAt > '9') ? cCharAt - '7' : cCharAt - '0'))) + ((double) iPow));
        }
        return iPow;
    }

    public static String e(byte[] bArr) {
        String str = c7.c;
        for (byte b2 : bArr) {
            str = str + ((char) b2);
        }
        return str;
    }

    public static String f(String str) {
        StringBuilder sb;
        String str2;
        String upperCase = str.toUpperCase();
        String string = c7.c;
        int length = upperCase.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = upperCase.charAt(i);
            switch (cCharAt) {
                case '0':
                    sb = new StringBuilder();
                    sb.append(string);
                    str2 = "0000";
                    sb.append(str2);
                    string = sb.toString();
                    break;
                case '1':
                    sb = new StringBuilder();
                    sb.append(string);
                    str2 = "0001";
                    sb.append(str2);
                    string = sb.toString();
                    break;
                case '2':
                    sb = new StringBuilder();
                    sb.append(string);
                    str2 = "0010";
                    sb.append(str2);
                    string = sb.toString();
                    break;
                case '3':
                    sb = new StringBuilder();
                    sb.append(string);
                    str2 = "0011";
                    sb.append(str2);
                    string = sb.toString();
                    break;
                case '4':
                    sb = new StringBuilder();
                    sb.append(string);
                    str2 = "0100";
                    sb.append(str2);
                    string = sb.toString();
                    break;
                case '5':
                    sb = new StringBuilder();
                    sb.append(string);
                    str2 = "0101";
                    sb.append(str2);
                    string = sb.toString();
                    break;
                case '6':
                    sb = new StringBuilder();
                    sb.append(string);
                    str2 = "0110";
                    sb.append(str2);
                    string = sb.toString();
                    break;
                case '7':
                    sb = new StringBuilder();
                    sb.append(string);
                    str2 = "0111";
                    sb.append(str2);
                    string = sb.toString();
                    break;
                case m9.p /* 56 */:
                    sb = new StringBuilder();
                    sb.append(string);
                    str2 = "1000";
                    sb.append(str2);
                    string = sb.toString();
                    break;
                case '9':
                    sb = new StringBuilder();
                    sb.append(string);
                    str2 = "1001";
                    sb.append(str2);
                    string = sb.toString();
                    break;
                default:
                    switch (cCharAt) {
                        case 'A':
                            sb = new StringBuilder();
                            sb.append(string);
                            str2 = "1010";
                            sb.append(str2);
                            string = sb.toString();
                            break;
                        case 'B':
                            sb = new StringBuilder();
                            sb.append(string);
                            str2 = "1011";
                            sb.append(str2);
                            string = sb.toString();
                            break;
                        case 'C':
                            sb = new StringBuilder();
                            sb.append(string);
                            str2 = "1100";
                            sb.append(str2);
                            string = sb.toString();
                            break;
                        case 'D':
                            sb = new StringBuilder();
                            sb.append(string);
                            str2 = "1101";
                            sb.append(str2);
                            string = sb.toString();
                            break;
                        case 'E':
                            sb = new StringBuilder();
                            sb.append(string);
                            str2 = "1110";
                            sb.append(str2);
                            string = sb.toString();
                            break;
                        case 'F':
                            sb = new StringBuilder();
                            sb.append(string);
                            str2 = "1111";
                            sb.append(str2);
                            string = sb.toString();
                            break;
                    }
                    break;
            }
        }
        return string;
    }

    public static char[] f(byte[] bArr) {
        return a(bArr, true);
    }

    public static String g(byte[] bArr) {
        return b(bArr, true);
    }

    public static byte[] g(String str) {
        if (str == null || str.equals(c7.c)) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (a(charArray[i2 + 1]) | (a(charArray[i2]) << 4));
        }
        return bArr;
    }

    public static String h(String str) {
        String str2;
        Exception e;
        if (str == null || str.equals(c7.c)) {
            return null;
        }
        String strReplace = str.replace(" ", c7.c);
        int length = strReplace.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            try {
                bArr[i] = (byte) (Integer.parseInt(strReplace.substring(i2, i2 + 2), 16) & 255);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        try {
            str2 = new String(bArr, "gbk");
        } catch (Exception e3) {
            str2 = strReplace;
            e = e3;
        }
        try {
            new String();
            return str2;
        } catch (Exception e4) {
            e = e4;
            e.printStackTrace();
            return str2;
        }
    }

    public static String h(byte[] bArr) {
        return c(bArr, true);
    }

    public static void i(byte[] bArr) {
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                hexString = "0".concat(hexString);
            }
            System.out.print("0x" + hexString.toUpperCase() + ",");
        }
        System.out.println(c7.c);
    }

    public static byte[] i(String str) throws IllegalArgumentException {
        if (str.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] charArray = str.toCharArray();
        byte[] bArr = new byte[str.length() / 2];
        int length = str.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            StringBuilder sb = new StringBuilder(c7.c);
            int i3 = i + 1;
            sb.append(charArray[i]);
            sb.append(charArray[i3]);
            bArr[i2] = new Integer(Integer.parseInt(sb.toString(), 16) & 255).byteValue();
            i = i3 + 1;
            i2++;
        }
        return bArr;
    }

    public static String j(String str) {
        String str2 = c7.c;
        for (int i = 0; i < str.length(); i++) {
            str2 = str2 + Integer.toHexString(str.charAt(i));
        }
        return str2;
    }
}
