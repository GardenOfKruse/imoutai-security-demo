package com.coralline.sea;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class t4 {
    public static final int a = -1;
    public static final char b = '/';
    public static final char c = '\\';
    public static final char d = File.separatorChar;
    public static final String e = "\n";
    public static final String f = "\r\n";
    public static final String g;
    public static final int h = 4096;
    public static final int i = 2048;
    public static char[] j;
    public static byte[] k;

    static {
        i9 i9Var = new i9(4);
        try {
            new PrintWriter(i9Var).println();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        g = i9Var.a.toString();
    }

    public static int a(InputStream inputStream, OutputStream outputStream) throws IOException {
        long jB = b(inputStream, outputStream);
        if (jB > 2147483647L) {
            return -1;
        }
        return (int) jB;
    }

    public static int a(InputStream inputStream, byte[] bArr) throws IOException {
        return a(inputStream, bArr, 0, bArr.length);
    }

    public static int a(InputStream inputStream, byte[] bArr, int i2, int i3) throws IOException {
        if (i3 < 0) {
            throw new IllegalArgumentException(p1.a("Length must not be negative: ", i3));
        }
        int i4 = i3;
        while (i4 > 0) {
            int i5 = inputStream.read(bArr, (i3 - i4) + i2, i4);
            if (-1 == i5) {
                break;
            }
            i4 -= i5;
        }
        return i3 - i4;
    }

    public static int a(Reader reader, Writer writer) throws IOException {
        long jB = b(reader, writer);
        if (jB > 2147483647L) {
            return -1;
        }
        return (int) jB;
    }

    public static int a(Reader reader, char[] cArr) throws IOException {
        return a(reader, cArr, 0, cArr.length);
    }

    public static int a(ReadableByteChannel readableByteChannel, ByteBuffer byteBuffer) throws IOException {
        int iRemaining = byteBuffer.remaining();
        while (byteBuffer.remaining() > 0 && -1 != readableByteChannel.read(byteBuffer)) {
        }
        return iRemaining - byteBuffer.remaining();
    }

    public static long a(InputStream inputStream, long j2) throws IOException {
        if (j2 < 0) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + j2);
        }
        if (k == null) {
            k = new byte[2048];
        }
        long j3 = j2;
        while (j3 > 0) {
            long j4 = inputStream.read(k, 0, (int) Math.min(j3, 2048L));
            if (j4 < 0) {
                break;
            }
            j3 -= j4;
        }
        return j2 - j3;
    }

    public static long a(InputStream inputStream, OutputStream outputStream, int i2) throws IOException {
        return a(inputStream, outputStream, new byte[i2]);
    }

    public static long a(InputStream inputStream, OutputStream outputStream, long j2, long j3) throws IOException {
        return a(inputStream, outputStream, j2, j3, new byte[4096]);
    }

    public static long a(InputStream inputStream, OutputStream outputStream, long j2, long j3, byte[] bArr) throws IOException {
        if (j2 > 0) {
            b(inputStream, j2);
        }
        if (j3 == 0) {
            return 0L;
        }
        int length = bArr.length;
        int iMin = (j3 <= 0 || j3 >= ((long) length)) ? length : (int) j3;
        long j4 = 0;
        while (iMin > 0) {
            int i2 = inputStream.read(bArr, 0, iMin);
            if (-1 == i2) {
                break;
            }
            outputStream.write(bArr, 0, i2);
            long j5 = j4 + ((long) i2);
            if (j3 > 0) {
                iMin = (int) Math.min(j3 - j5, length);
            }
            j4 = j5;
        }
        return j4;
    }

    public static long a(InputStream inputStream, OutputStream outputStream, byte[] bArr) throws IOException {
        long j2 = 0;
        while (true) {
            int i2 = inputStream.read(bArr);
            if (-1 == i2) {
                return j2;
            }
            outputStream.write(bArr, 0, i2);
            j2 += (long) i2;
        }
    }

    public static long a(Reader reader, long j2) throws IOException {
        if (j2 < 0) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + j2);
        }
        if (j == null) {
            j = new char[2048];
        }
        long j3 = j2;
        while (j3 > 0) {
            long j4 = reader.read(j, 0, (int) Math.min(j3, 2048L));
            if (j4 < 0) {
                break;
            }
            j3 -= j4;
        }
        return j2 - j3;
    }

    public static long a(Reader reader, Writer writer, long j2, long j3) throws IOException {
        return a(reader, writer, j2, j3, new char[4096]);
    }

    public static long a(Reader reader, Writer writer, long j2, long j3, char[] cArr) throws IOException {
        if (j2 > 0) {
            b(reader, j2);
        }
        if (j3 == 0) {
            return 0L;
        }
        int length = cArr.length;
        if (j3 > 0 && j3 < cArr.length) {
            length = (int) j3;
        }
        long j4 = 0;
        while (length > 0) {
            int i2 = reader.read(cArr, 0, length);
            if (-1 == i2) {
                break;
            }
            writer.write(cArr, 0, i2);
            long j5 = j4 + ((long) i2);
            if (j3 > 0) {
                length = (int) Math.min(j3 - j5, cArr.length);
            }
            j4 = j5;
        }
        return j4;
    }

    public static long a(Reader reader, Writer writer, char[] cArr) throws IOException {
        long j2 = 0;
        while (true) {
            int i2 = reader.read(cArr);
            if (-1 == i2) {
                return j2;
            }
            writer.write(cArr, 0, i2);
            j2 += (long) i2;
        }
    }

    public static long a(ReadableByteChannel readableByteChannel, long j2) throws IOException {
        if (j2 < 0) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + j2);
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate((int) Math.min(j2, 2048L));
        long j3 = j2;
        while (j3 > 0) {
            byteBufferAllocate.position(0);
            byteBufferAllocate.limit((int) Math.min(j3, 2048L));
            int i2 = readableByteChannel.read(byteBufferAllocate);
            if (i2 == -1) {
                break;
            }
            j3 -= (long) i2;
        }
        return j2 - j3;
    }

    public static BufferedInputStream a(InputStream inputStream) {
        inputStream.getClass();
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream);
    }

    public static BufferedInputStream a(InputStream inputStream, int i2) {
        inputStream.getClass();
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream, i2);
    }

    public static BufferedOutputStream a(OutputStream outputStream) {
        outputStream.getClass();
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream);
    }

    public static BufferedOutputStream a(OutputStream outputStream, int i2) {
        outputStream.getClass();
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream, i2);
    }

    public static BufferedReader a(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static BufferedReader a(Reader reader, int i2) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader, i2);
    }

    public static BufferedWriter a(Writer writer) {
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer);
    }

    public static BufferedWriter a(Writer writer, int i2) {
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer, i2);
    }

    public static InputStream a(CharSequence charSequence, Charset charset) {
        return b(charSequence.toString(), charset);
    }

    public static InputStream a(String str, String str2) throws IOException {
        return new ByteArrayInputStream(str.getBytes(n1.a(str2)));
    }

    public static String a(String str, Charset charset) throws IOException {
        return a(str, charset, (ClassLoader) null);
    }

    public static String a(URI uri, String str) throws IOException {
        return a(uri, n1.a(str));
    }

    public static String a(URL url, Charset charset) throws IOException {
        return c(url.openStream(), charset);
    }

    @Deprecated
    public static String a(byte[] bArr) throws IOException {
        return new String(bArr, Charset.defaultCharset());
    }

    public static String a(byte[] bArr, String str) throws IOException {
        return new String(bArr, n1.a(str));
    }

    public static List<String> a(InputStream inputStream, String str) throws IOException {
        return a(inputStream, n1.a(str));
    }

    public static List<String> a(InputStream inputStream, Charset charset) throws IOException {
        return c(new InputStreamReader(inputStream, n1.a(charset)));
    }

    @Deprecated
    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e2) {
            }
        }
    }

    @Deprecated
    public static void a(InputStream inputStream, Writer writer) throws IOException {
        a(inputStream, writer, Charset.defaultCharset());
    }

    public static void a(InputStream inputStream, Writer writer, String str) throws IOException {
        a(inputStream, writer, n1.a(str));
    }

    public static void a(InputStream inputStream, Writer writer, Charset charset) throws IOException {
        a(new InputStreamReader(inputStream, n1.a(charset)), writer);
    }

    @Deprecated
    public static void a(Reader reader, OutputStream outputStream) throws IOException {
        a(reader, outputStream, Charset.defaultCharset());
    }

    public static void a(Reader reader, OutputStream outputStream, String str) throws IOException {
        a(reader, outputStream, n1.a(str));
    }

    public static void a(Reader reader, OutputStream outputStream, Charset charset) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, n1.a(charset));
        a(reader, outputStreamWriter);
        outputStreamWriter.flush();
    }

    @Deprecated
    public static void a(CharSequence charSequence, OutputStream outputStream) throws IOException {
        a(charSequence, outputStream, Charset.defaultCharset());
    }

    public static void a(CharSequence charSequence, OutputStream outputStream, String str) throws IOException {
        a(charSequence, outputStream, n1.a(str));
    }

    public static void a(CharSequence charSequence, OutputStream outputStream, Charset charset) throws IOException {
        if (charSequence != null) {
            a(charSequence.toString(), outputStream, charset);
        }
    }

    @Deprecated
    public static void a(String str, OutputStream outputStream) throws IOException {
        a(str, outputStream, Charset.defaultCharset());
    }

    public static void a(String str, OutputStream outputStream, String str2) throws IOException {
        a(str, outputStream, n1.a(str2));
    }

    public static void a(String str, OutputStream outputStream, Charset charset) throws IOException {
        if (str != null) {
            outputStream.write(str.getBytes(n1.a(charset)));
        }
    }

    public static void a(String str, Writer writer) throws IOException {
        if (str != null) {
            writer.write(str);
        }
    }

    @Deprecated
    public static void a(StringBuffer stringBuffer, OutputStream outputStream) throws IOException {
        a(stringBuffer, outputStream, (String) null);
    }

    @Deprecated
    public static void a(StringBuffer stringBuffer, OutputStream outputStream, String str) throws IOException {
        if (stringBuffer != null) {
            outputStream.write(stringBuffer.toString().getBytes(n1.a(str)));
        }
    }

    @Deprecated
    public static void a(StringBuffer stringBuffer, Writer writer) throws IOException {
        if (stringBuffer != null) {
            writer.write(stringBuffer.toString());
        }
    }

    @Deprecated
    public static void a(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e2) {
            }
        }
    }

    @Deprecated
    public static void a(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e2) {
            }
        }
    }

    public static void a(URLConnection uRLConnection) {
        if (uRLConnection instanceof HttpURLConnection) {
            ((HttpURLConnection) uRLConnection).disconnect();
        }
    }

    @Deprecated
    public static void a(Selector selector) {
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e2) {
            }
        }
    }

    @Deprecated
    public static void a(Collection<?> collection, String str, OutputStream outputStream) throws IOException {
        a(collection, str, outputStream, Charset.defaultCharset());
    }

    public static void a(Collection<?> collection, String str, OutputStream outputStream, String str2) throws IOException {
        a(collection, str, outputStream, n1.a(str2));
    }

    public static void a(Collection<?> collection, String str, OutputStream outputStream, Charset charset) throws IOException {
        if (collection == null) {
            return;
        }
        if (str == null) {
            str = g;
        }
        Charset charsetA = n1.a(charset);
        for (Object obj : collection) {
            if (obj != null) {
                outputStream.write(obj.toString().getBytes(charsetA));
            }
            outputStream.write(str.getBytes(charsetA));
        }
    }

    public static void a(Collection<?> collection, String str, Writer writer) throws IOException {
        if (collection == null) {
            return;
        }
        if (str == null) {
            str = g;
        }
        for (Object obj : collection) {
            if (obj != null) {
                writer.write(obj.toString());
            }
            writer.write(str);
        }
    }

    public static void a(byte[] bArr, OutputStream outputStream) throws IOException {
        if (bArr != null) {
            outputStream.write(bArr);
        }
    }

    @Deprecated
    public static void a(byte[] bArr, Writer writer) throws IOException {
        a(bArr, writer, Charset.defaultCharset());
    }

    public static void a(byte[] bArr, Writer writer, String str) throws IOException {
        a(bArr, writer, n1.a(str));
    }

    public static void a(byte[] bArr, Writer writer, Charset charset) throws IOException {
        if (bArr != null) {
            writer.write(new String(bArr, n1.a(charset)));
        }
    }

    @Deprecated
    public static void a(char[] cArr, OutputStream outputStream) throws IOException {
        a(cArr, outputStream, Charset.defaultCharset());
    }

    public static void a(char[] cArr, OutputStream outputStream, String str) throws IOException {
        a(cArr, outputStream, n1.a(str));
    }

    public static void a(char[] cArr, OutputStream outputStream, Charset charset) throws IOException {
        if (cArr != null) {
            outputStream.write(new String(cArr).getBytes(n1.a(charset)));
        }
    }

    public static void a(char[] cArr, Writer writer) throws IOException {
        if (cArr != null) {
            writer.write(cArr);
        }
    }

    @Deprecated
    public static void a(Closeable... closeableArr) {
        if (closeableArr == null) {
            return;
        }
        for (Closeable closeable : closeableArr) {
            a(closeable);
        }
    }

    public static boolean a(InputStream inputStream, InputStream inputStream2) throws IOException {
        int i2;
        if (inputStream == inputStream2) {
            return true;
        }
        if (!(inputStream instanceof BufferedInputStream)) {
            inputStream = new BufferedInputStream(inputStream);
        }
        if (!(inputStream2 instanceof BufferedInputStream)) {
            inputStream2 = new BufferedInputStream(inputStream2);
        }
        do {
            i2 = inputStream.read();
            if (-1 == i2) {
                return inputStream2.read() == -1;
            }
        } while (i2 == inputStream2.read());
        return false;
    }

    public static boolean a(Reader reader, Reader reader2) throws IOException {
        int i2;
        if (reader == reader2) {
            return true;
        }
        BufferedReader bufferedReaderD = d(reader);
        BufferedReader bufferedReaderD2 = d(reader2);
        do {
            i2 = bufferedReaderD.read();
            if (-1 == i2) {
                return bufferedReaderD2.read() == -1;
            }
        } while (i2 == bufferedReaderD2.read());
        return false;
    }

    public static byte[] a(Reader reader, String str) throws IOException {
        return a(reader, n1.a(str));
    }

    public static byte[] a(String str, ClassLoader classLoader) throws IOException {
        return a(b(str, classLoader));
    }

    public static byte[] a(URI uri) throws IOException {
        return a(uri.toURL());
    }

    public static long b(InputStream inputStream, OutputStream outputStream) throws IOException {
        return a(inputStream, outputStream, new byte[4096]);
    }

    public static long b(Reader reader, Writer writer) throws IOException {
        return a(reader, writer, new char[4096]);
    }

    public static BufferedReader b(Reader reader, int i2) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader, i2);
    }

    public static InputStream b(String str, Charset charset) {
        return new ByteArrayInputStream(str.getBytes(n1.a(charset)));
    }

    @Deprecated
    public static String b(URI uri) throws IOException {
        return a(uri, Charset.defaultCharset());
    }

    public static URL b(String str) throws IOException {
        return b(str, (ClassLoader) null);
    }

    public static URL b(String str, ClassLoader classLoader) throws IOException {
        URL resource = classLoader == null ? t4.class.getResource(str) : classLoader.getResource(str);
        if (resource != null) {
            return resource;
        }
        throw new IOException("Resource not found: " + str);
    }

    @Deprecated
    public static void b(InputStream inputStream) {
        a((Closeable) inputStream);
    }

    public static void b(InputStream inputStream, long j2) throws IOException {
        if (j2 < 0) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + j2);
        }
        long jA = a(inputStream, j2);
        if (jA == j2) {
            return;
        }
        throw new EOFException("Bytes to skip: " + j2 + " actual: " + jA);
    }

    public static void b(InputStream inputStream, byte[] bArr) throws IOException {
        b(inputStream, bArr, 0, bArr.length);
    }

    public static void b(InputStream inputStream, byte[] bArr, int i2, int i3) throws IOException {
        int iA = a(inputStream, bArr, i2, i3);
        if (iA == i3) {
            return;
        }
        throw new EOFException("Length to read: " + i3 + " actual: " + iA);
    }

    @Deprecated
    public static void b(OutputStream outputStream) {
        a((Closeable) outputStream);
    }

    @Deprecated
    public static void b(Reader reader) {
        a((Closeable) reader);
    }

    public static void b(Reader reader, long j2) throws IOException {
        long jA = a(reader, j2);
        if (jA == j2) {
            return;
        }
        throw new EOFException("Chars to skip: " + j2 + " actual: " + jA);
    }

    public static void b(Reader reader, char[] cArr) throws IOException {
        b(reader, cArr, 0, cArr.length);
    }

    public static void b(Reader reader, char[] cArr, int i2, int i3) throws IOException {
        int iA = a(reader, cArr, i2, i3);
        if (iA == i3) {
            return;
        }
        throw new EOFException("Length to read: " + i3 + " actual: " + iA);
    }

    @Deprecated
    public static void b(Writer writer) {
        a((Closeable) writer);
    }

    public static void b(ReadableByteChannel readableByteChannel, long j2) throws IOException {
        if (j2 < 0) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + j2);
        }
        long jA = a(readableByteChannel, j2);
        if (jA == j2) {
            return;
        }
        throw new EOFException("Bytes to skip: " + j2 + " actual: " + jA);
    }

    public static void b(ReadableByteChannel readableByteChannel, ByteBuffer byteBuffer) throws IOException {
        int iRemaining = byteBuffer.remaining();
        int iA = a(readableByteChannel, byteBuffer);
        if (iA == iRemaining) {
            return;
        }
        throw new EOFException("Length to read: " + iRemaining + " actual: " + iA);
    }

    public static void b(byte[] bArr, OutputStream outputStream) throws IOException {
        if (bArr != null) {
            int length = bArr.length;
            int i2 = 0;
            while (length > 0) {
                int iMin = Math.min(length, 4096);
                outputStream.write(bArr, i2, iMin);
                length -= iMin;
                i2 += iMin;
            }
        }
    }

    public static void b(char[] cArr, Writer writer) throws IOException {
        if (cArr != null) {
            int length = cArr.length;
            int i2 = 0;
            while (length > 0) {
                int iMin = Math.min(length, 4096);
                writer.write(cArr, i2, iMin);
                length -= iMin;
                i2 += iMin;
            }
        }
    }

    public static boolean b(Reader reader, Reader reader2) throws IOException {
        String line;
        String line2;
        if (reader == reader2) {
            return true;
        }
        BufferedReader bufferedReaderD = d(reader);
        BufferedReader bufferedReaderD2 = d(reader2);
        do {
            line = bufferedReaderD.readLine();
            line2 = bufferedReaderD2.readLine();
            if (line == null || line2 == null) {
                break;
            }
        } while (line.equals(line2));
        return line == null ? line2 == null : line.equals(line2);
    }

    public static byte[] b(InputStream inputStream, int i2) throws IOException {
        byte[] bArr = new byte[i2];
        b(inputStream, bArr, 0, i2);
        return bArr;
    }

    public static byte[] b(URLConnection uRLConnection) throws IOException {
        return e(uRLConnection.getInputStream());
    }

    public static char[] b(InputStream inputStream, String str) throws IOException {
        return b(inputStream, n1.a(str));
    }

    public static char[] b(InputStream inputStream, Charset charset) throws IOException {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        a(inputStream, charArrayWriter, charset);
        return charArrayWriter.toCharArray();
    }

    public static InputStream c(InputStream inputStream, int i2) throws IOException {
        return p0.a(inputStream, i2);
    }

    public static String c(InputStream inputStream, String str) throws IOException {
        return c(inputStream, n1.a(str));
    }

    public static String c(InputStream inputStream, Charset charset) throws IOException {
        i9 i9Var = new i9();
        a(inputStream, i9Var, charset);
        return i9Var.a.toString();
    }

    @Deprecated
    public static List<String> c(InputStream inputStream) throws IOException {
        return a(inputStream, Charset.defaultCharset());
    }

    public static List<String> c(Reader reader) throws IOException {
        BufferedReader bufferedReaderD = d(reader);
        ArrayList arrayList = new ArrayList();
        while (true) {
            String line = bufferedReaderD.readLine();
            if (line == null) {
                return arrayList;
            }
            arrayList.add(line);
        }
    }

    public static byte[] c(InputStream inputStream, long j2) throws IOException {
        if (j2 <= 2147483647L) {
            return d(inputStream, (int) j2);
        }
        throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + j2);
    }

    @Deprecated
    public static byte[] c(String str) throws IOException {
        return str.getBytes(Charset.defaultCharset());
    }

    public static BufferedReader d(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static InputStream d(InputStream inputStream) throws IOException {
        return p0.a(inputStream);
    }

    @Deprecated
    public static InputStream d(String str) {
        return b(str, Charset.defaultCharset());
    }

    public static byte[] d(InputStream inputStream, int i2) throws IOException {
        if (i2 < 0) {
            throw new IllegalArgumentException(p1.a("Size must be equal or greater than zero: ", i2));
        }
        int i3 = 0;
        if (i2 == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[i2];
        while (i3 < i2) {
            int i4 = inputStream.read(bArr, i3, i2 - i3);
            if (i4 == -1) {
                break;
            }
            i3 += i4;
        }
        if (i3 == i2) {
            return bArr;
        }
        throw new IOException("Unexpected read size. current: " + i3 + ", expected: " + i2);
    }

    public static byte[] e(InputStream inputStream) throws IOException {
        p0 p0Var = new p0(1024);
        a(inputStream, p0Var);
        return p0Var.c();
    }

    @Deprecated
    public static byte[] e(Reader reader) throws IOException {
        return a(reader, Charset.defaultCharset());
    }

    @Deprecated
    public static char[] f(InputStream inputStream) throws IOException {
        return b(inputStream, Charset.defaultCharset());
    }

    public static char[] f(Reader reader) throws IOException {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        a(reader, charArrayWriter);
        return charArrayWriter.toCharArray();
    }

    @Deprecated
    public static String g(InputStream inputStream) throws IOException {
        return c(inputStream, Charset.defaultCharset());
    }

    public static String g(Reader reader) throws IOException {
        i9 i9Var = new i9();
        a(reader, i9Var);
        return i9Var.a.toString();
    }

    @Deprecated
    public static String b(URL url) throws IOException {
        return c(url.openStream(), Charset.defaultCharset());
    }

    public static int a(Reader reader, char[] cArr, int i2, int i3) throws IOException {
        if (i3 < 0) {
            throw new IllegalArgumentException(p1.a("Length must not be negative: ", i3));
        }
        int i4 = i3;
        while (i4 > 0) {
            int i5 = reader.read(cArr, (i3 - i4) + i2, i4);
            if (-1 == i5) {
                break;
            }
            i4 -= i5;
        }
        return i3 - i4;
    }

    public static byte[] a(String str) throws IOException {
        return a(b(str, (ClassLoader) null));
    }

    public static String a(String str, Charset charset, ClassLoader classLoader) throws IOException {
        return c(b(str, classLoader).openStream(), charset);
    }

    public static byte[] a(Reader reader, Charset charset) throws IOException {
        p0 p0Var = new p0(1024);
        a(reader, p0Var, charset);
        return p0Var.c();
    }

    public static byte[] a(URL url) throws IOException {
        URLConnection uRLConnectionOpenConnection = url.openConnection();
        try {
            return e(uRLConnectionOpenConnection.getInputStream());
        } finally {
            a(uRLConnectionOpenConnection);
        }
    }

    @Deprecated
    public static InputStream a(CharSequence charSequence) {
        return b(charSequence.toString(), Charset.defaultCharset());
    }

    public static InputStream a(CharSequence charSequence, String str) throws IOException {
        return b(charSequence.toString(), n1.a(str));
    }

    public static String a(URI uri, Charset charset) throws IOException {
        URL url = uri.toURL();
        return c(url.openStream(), n1.a(charset));
    }

    public static String a(URL url, String str) throws IOException {
        return c(url.openStream(), n1.a(str));
    }

    public static void a(CharSequence charSequence, Writer writer) throws IOException {
        String string;
        if (charSequence == null || (string = charSequence.toString()) == null) {
            return;
        }
        writer.write(string);
    }
}
