package com.coralline.sea;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class e3 {
    public static final int a = 2;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 1;
    public static final String e = "Drozer Detect";

    public static int a(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    public static String a(int i) {
        if (i == 1) {
            return "SYSTEM_REQUEST";
        }
        if (i == 2) {
            return "SYSTEM_RESPONSE";
        }
        return "UNKNOWN(" + i + ")";
    }

    public static void a(OutputStream outputStream) throws IOException {
        byte[] bArrA = a();
        byte[] bArr = new byte[bArrA.length + 8];
        a(bArr, 0, 2);
        a(bArr, 4, bArrA.length);
        System.arraycopy(bArrA, 0, bArr, 8, bArrA.length);
        outputStream.write(bArr);
        outputStream.flush();
    }

    public static void a(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    public static void a(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) ((i2 >> 24) & 255);
        bArr[i + 1] = (byte) ((i2 >> 16) & 255);
        bArr[i + 2] = (byte) ((i2 >> 8) & 255);
        bArr[i + 3] = (byte) (i2 & 255);
    }

    public static boolean a(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[8];
        if (inputStream.read(bArr) == 8) {
            a(bArr, 0);
            int iA = a(bArr, 4);
            byte[] bArr2 = new byte[iA];
            if (inputStream.read(bArr2) == iA) {
                return a(bArr2);
            }
        }
        return false;
    }

    public static boolean a(String str, int i) throws Throwable {
        Throwable th;
        Socket socket;
        OutputStream outputStream;
        Exception exc;
        InputStream inputStream;
        Socket socket2 = null;
        try {
            Socket socket3 = new Socket(str, i);
            try {
                socket3.setSoTimeout(a.m);
                OutputStream outputStream2 = socket3.getOutputStream();
                try {
                    inputStream = socket3.getInputStream();
                } catch (Exception e2) {
                    outputStream = outputStream2;
                    e = e2;
                } catch (Throwable th2) {
                    outputStream = outputStream2;
                    th = th2;
                }
                try {
                    a(outputStream2);
                    boolean zA = a(inputStream);
                    a((AutoCloseable) inputStream);
                    a((AutoCloseable) outputStream2);
                    a(socket3);
                    return zA;
                } catch (Exception e3) {
                    outputStream = outputStream2;
                    e = e3;
                    socket2 = inputStream;
                    exc = e;
                    socket = socket2;
                    socket2 = socket3;
                    try {
                        exc.getMessage();
                        a(socket);
                        a((AutoCloseable) outputStream);
                        a(socket2);
                        return false;
                    } catch (Throwable th3) {
                        th = th3;
                        a(socket);
                        a((AutoCloseable) outputStream);
                        a(socket2);
                        throw th;
                    }
                } catch (Throwable th4) {
                    outputStream = outputStream2;
                    th = th4;
                    socket2 = inputStream;
                    th = th;
                    socket = socket2;
                    socket2 = socket3;
                    a(socket);
                    a((AutoCloseable) outputStream);
                    a(socket2);
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                outputStream = null;
            } catch (Throwable th5) {
                th = th5;
                outputStream = null;
            }
        } catch (Exception e5) {
            exc = e5;
            socket = null;
            outputStream = null;
        } catch (Throwable th6) {
            th = th6;
            socket = null;
            outputStream = null;
        }
    }

    public static boolean a(byte[] bArr) {
        int i;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < bArr.length) {
            try {
                int i5 = bArr[i2] & 255;
                i2++;
                int i6 = i5 >> 3;
                int i7 = i5 & 7;
                if (i6 == 1) {
                    byte b2 = bArr[i2];
                } else if (i6 != 2) {
                    if (i6 == 6) {
                        int i8 = bArr[i2] & 255;
                        i2++;
                        if ((bArr[i2] & 255) == 8) {
                            i2++;
                            i4 = bArr[i2] & 255;
                        }
                        i = i8 - 1;
                    } else if (i7 == 0) {
                        while ((bArr[i2] & 128) != 0) {
                            i2++;
                        }
                    } else if (i7 == 2) {
                        i = (bArr[i2] & 255) + 1;
                    }
                    i2 += i;
                } else {
                    i3 = bArr[i2] & 255;
                    i2++;
                }
                i2++;
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        a(i3);
        b(i4);
        return i3 == 2 && i4 == 1;
    }

    public static byte[] a() {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(16);
        byteBufferAllocate.put((byte) 8);
        byteBufferAllocate.put((byte) 1);
        byteBufferAllocate.put((byte) 16);
        byteBufferAllocate.put((byte) 1);
        byteBufferAllocate.put((byte) 42);
        byteBufferAllocate.put((byte) 2);
        byteBufferAllocate.put((byte) 8);
        byteBufferAllocate.put((byte) 1);
        byte[] bArr = new byte[byteBufferAllocate.position()];
        byteBufferAllocate.rewind();
        byteBufferAllocate.get(bArr);
        return bArr;
    }

    public static String b(int i) {
        if (i == 1) {
            return "SYSTEM_REQUEST";
        }
        if (i == 2) {
            return "SYSTEM_RESPONSE";
        }
        return "UNKNOWN(" + i + ")";
    }
}
