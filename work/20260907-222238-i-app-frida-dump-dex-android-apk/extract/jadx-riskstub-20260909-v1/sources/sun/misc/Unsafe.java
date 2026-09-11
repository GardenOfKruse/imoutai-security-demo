package sun.misc;

import java.lang.reflect.Field;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class Unsafe {
    public native int addressSize();

    public int arrayBaseOffset(Class cls) {
        throw new RuntimeException("Stub!");
    }

    public native byte getByte(long j);

    public native int getInt(long j);

    public native int getInt(Object obj, long j);

    public native long getLong(long j);

    public native long getLong(Object obj, long j);

    public native Object getObject(Object obj, long j);

    public native short getShort(Object obj, long j);

    public long objectFieldOffset(Field field) {
        throw new RuntimeException("Stub!");
    }

    public native void putByte(long j, byte b);

    public native void putInt(Object obj, long j, int i);

    public native void putLong(Object obj, long j, long j2);

    public native void putObject(Object obj, long j, Object obj2);
}
