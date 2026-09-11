package proxy.android.os;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.RequiresApi;
import com.coralline.sea.m5;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class ParcelProxy {
    public long a;
    public Parcel b;

    public ParcelProxy(Parcel parcel, long j) {
        this.b = parcel;
        this.a = j;
    }

    public static ParcelProxy a() {
        Parcel parcelObtain = Parcel.obtain();
        return new ParcelProxy(parcelObtain, nativeParcelForJavaObject(parcelObtain));
    }

    private static native long nativeParcelForJavaObject(Parcel parcel);

    public static native String nativeReadString8(long j);

    public <T extends Parcelable> T a(ClassLoader classLoader) {
        return (T) this.b.readParcelable(classLoader);
    }

    @RequiresApi(api = m5.b.B)
    public <T extends Parcelable> T a(ClassLoader classLoader, Class<T> cls) {
        if (Build.VERSION.SDK_INT <= 29) {
            return null;
        }
        try {
            Method method = Parcel.class.getMethod("readParcelable", ClassLoader.class, Class.class);
            if (method == null) {
                return null;
            }
            method.setAccessible(true);
            return (T) method.invoke(this.b, classLoader, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = m5.b.B)
    public final <T> T a(Parcelable.Creator<T> creator) {
        if (Build.VERSION.SDK_INT <= 29) {
            return null;
        }
        try {
            Method method = Parcel.class.getMethod("readTypedObject", Parcelable.Creator.class);
            if (method == null) {
                return null;
            }
            method.setAccessible(true);
            return (T) method.invoke(this.b, creator);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final <T> void a(T[] tArr, Parcelable.Creator<T> creator) {
        this.b.readTypedArray(tArr, creator);
    }

    @RequiresApi(api = m5.b.B)
    public final float[] b() {
        if (Build.VERSION.SDK_INT <= 29) {
            return null;
        }
        try {
            Method method = Parcel.class.getMethod("createFloatArray", new Class[0]);
            if (method == null) {
                return null;
            }
            method.setAccessible(true);
            return (float[]) method.invoke(this.b, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = m5.b.B)
    public final int[] c() {
        if (Build.VERSION.SDK_INT <= 29) {
            return null;
        }
        try {
            Method method = Parcel.class.getMethod("createIntArray", new Class[0]);
            if (method == null) {
                return null;
            }
            method.setAccessible(true);
            return (int[]) method.invoke(this.b, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Parcel d() {
        return this.b;
    }

    @RequiresApi(api = m5.b.A)
    public boolean e() {
        return this.b.readBoolean();
    }

    public byte f() {
        return this.b.readByte();
    }

    public double g() {
        return this.b.readDouble();
    }

    public float h() {
        return this.b.readFloat();
    }

    public int i() {
        return this.b.readInt();
    }

    public long j() {
        return this.b.readLong();
    }

    public String k() {
        return this.b.readString();
    }

    @RequiresApi(api = m5.b.B)
    public final String l() {
        if (Build.VERSION.SDK_INT <= 29) {
            return null;
        }
        long j = this.a;
        if (j != 0) {
            return nativeReadString8(j);
        }
        return null;
    }
}
