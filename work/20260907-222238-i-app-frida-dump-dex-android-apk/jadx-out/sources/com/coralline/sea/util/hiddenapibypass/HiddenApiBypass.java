package com.coralline.sea.util.hiddenapibypass;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.coralline.sea.m5;
import com.coralline.sea.r1;
import com.coralline.sea.util.hiddenapibypass.Helper;
import dalvik.system.VMRuntime;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: assets/RiskStub.dex */
@RequiresApi(m5.b.z)
public final class HiddenApiBypass {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String TAG = "HiddenApiBypass";
    private static final long artFieldBias;
    private static final long artFieldSize;
    private static final long artMethodBias;
    private static final long artMethodSize;
    private static final long artOffset;
    private static final long classOffset;
    private static final long iFieldOffset;
    private static final long methodOffset;
    private static final long methodsOffset;
    private static final long sFieldOffset;
    private static final Unsafe unsafe;

    static {
        long jObjectFieldOffset;
        long j;
        try {
            Unsafe unsafe2 = (Unsafe) Unsafe.class.getDeclaredMethod("getUnsafe", new Class[0]).invoke(null, new Object[0]);
            unsafe = unsafe2;
            CoreOjClassLoader coreOjClassLoader = new CoreOjClassLoader();
            Class<?> clsLoadClass = coreOjClassLoader.loadClass(Executable.class.getName());
            Class<?> clsLoadClass2 = coreOjClassLoader.loadClass(MethodHandle.class.getName());
            Class<?> clsLoadClass3 = coreOjClassLoader.loadClass(Class.class.getName());
            methodOffset = unsafe2.objectFieldOffset(clsLoadClass.getDeclaredField("artMethod"));
            classOffset = unsafe2.objectFieldOffset(clsLoadClass.getDeclaredField("declaringClass"));
            artOffset = unsafe2.objectFieldOffset(clsLoadClass2.getDeclaredField("artFieldOrMethod"));
            try {
                jObjectFieldOffset = unsafe2.objectFieldOffset(clsLoadClass3.getDeclaredField("fields"));
                j = jObjectFieldOffset;
            } catch (NoSuchFieldException e) {
                Unsafe unsafe3 = unsafe;
                long jObjectFieldOffset2 = unsafe3.objectFieldOffset(clsLoadClass3.getDeclaredField("iFields"));
                jObjectFieldOffset = unsafe3.objectFieldOffset(clsLoadClass3.getDeclaredField("sFields"));
                j = jObjectFieldOffset2;
            }
            iFieldOffset = j;
            sFieldOffset = jObjectFieldOffset;
            Unsafe unsafe4 = unsafe;
            long jObjectFieldOffset3 = unsafe4.objectFieldOffset(clsLoadClass3.getDeclaredField("methods"));
            methodsOffset = jObjectFieldOffset3;
            Method declaredMethod = Helper.NeverCall.class.getDeclaredMethod(r1.i.e, new Class[0]);
            Method declaredMethod2 = Helper.NeverCall.class.getDeclaredMethod(r1.i.f, new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod2.setAccessible(true);
            MethodHandle methodHandleUnreflect = MethodHandles.lookup().unreflect(declaredMethod);
            MethodHandle methodHandleUnreflect2 = MethodHandles.lookup().unreflect(declaredMethod2);
            long j2 = artOffset;
            long j3 = unsafe4.getLong(methodHandleUnreflect, j2);
            long j4 = unsafe4.getLong(methodHandleUnreflect2, j2);
            long j5 = unsafe4.getLong(Helper.NeverCall.class, jObjectFieldOffset3);
            long j6 = j4 - j3;
            artMethodSize = j6;
            artMethodBias = (j3 - j5) - j6;
            Field declaredField = Helper.NeverCall.class.getDeclaredField("i");
            Field declaredField2 = Helper.NeverCall.class.getDeclaredField("j");
            declaredField.setAccessible(true);
            declaredField2.setAccessible(true);
            MethodHandle methodHandleUnreflectGetter = MethodHandles.lookup().unreflectGetter(declaredField);
            MethodHandle methodHandleUnreflectGetter2 = MethodHandles.lookup().unreflectGetter(declaredField2);
            long j7 = unsafe4.getLong(methodHandleUnreflectGetter, j2);
            long j8 = unsafe4.getLong(methodHandleUnreflectGetter2, j2);
            long j9 = unsafe4.getLong(Helper.NeverCall.class, j);
            artFieldSize = j8 - j7;
            artFieldBias = j7 - j9;
        } catch (ReflectiveOperationException e2) {
            throw new ExceptionInInitializerError(e2);
        }
    }

    public static boolean addHiddenApiExemptions(String... strArr) {
        Set<String> set = Helper.signaturePrefixes;
        set.addAll(Arrays.asList(strArr));
        String[] strArr2 = new String[set.size()];
        set.toArray(strArr2);
        return setHiddenApiExemptions(strArr2);
    }

    public static boolean clearHiddenApiExemptions() {
        Helper.signaturePrefixes.clear();
        return setHiddenApiExemptions(new String[0]);
    }

    @NonNull
    public static Constructor<?> getDeclaredConstructor(@NonNull Class<?> cls, @NonNull Class<?>... clsArr) throws NoSuchMethodException {
        for (Executable executable : getDeclaredMethods(cls)) {
            if (executable instanceof Constructor) {
                Class<?>[] parameterTypes = executable.getParameterTypes();
                if (parameterTypes.length == clsArr.length) {
                    for (int i = 0; i < clsArr.length; i++) {
                        if (clsArr[i] != parameterTypes[i]) {
                            break;
                        }
                    }
                    return (Constructor) executable;
                }
                continue;
            }
        }
        throw new NoSuchMethodException("Cannot find matching constructor");
    }

    @NonNull
    public static Method getDeclaredMethod(@NonNull Class<?> cls, @NonNull String str, @NonNull Class<?>... clsArr) throws NoSuchMethodException {
        for (Executable executable : getDeclaredMethods(cls)) {
            if (executable.getName().equals(str) && (executable instanceof Method)) {
                Class<?>[] parameterTypes = executable.getParameterTypes();
                if (parameterTypes.length == clsArr.length) {
                    for (int i = 0; i < clsArr.length; i++) {
                        if (clsArr[i] != parameterTypes[i]) {
                            break;
                        }
                    }
                    return (Method) executable;
                }
                continue;
            }
        }
        throw new NoSuchMethodException("Cannot find matching method");
    }

    @NonNull
    public static List<Executable> getDeclaredMethods(@NonNull Class<?> cls) throws IllegalAccessException {
        MethodHandle methodHandleUnreflect;
        if ((cls.isPrimitive() || cls.isArray()) && Build.VERSION.SDK_INT >= 30) {
            return List.of();
        }
        try {
            Method declaredMethod = Helper.NeverCall.class.getDeclaredMethod(r1.i.e, new Class[0]);
            declaredMethod.setAccessible(true);
            methodHandleUnreflect = MethodHandles.lookup().unreflect(declaredMethod);
        } catch (IllegalAccessException | NoSuchMethodException e) {
            if (Build.VERSION.SDK_INT >= 30) {
                return List.of();
            }
            methodHandleUnreflect = null;
        }
        Unsafe unsafe2 = unsafe;
        long j = unsafe2.getLong(cls, methodsOffset);
        if (j == 0 && Build.VERSION.SDK_INT >= 30) {
            return List.of();
        }
        int i = unsafe2.getInt(j);
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            unsafe.putLong(methodHandleUnreflect, artOffset, (((long) i2) * artMethodSize) + j + artMethodBias);
            arrayList.add((Executable) MethodHandles.reflectAs(Executable.class, methodHandleUnreflect));
        }
        return arrayList;
    }

    @NonNull
    public static List<Field> getInstanceFields(@NonNull Class<?> cls) {
        if (cls.isPrimitive() || cls.isArray()) {
            return List.of();
        }
        try {
            Field declaredField = Helper.NeverCall.class.getDeclaredField("i");
            declaredField.setAccessible(true);
            MethodHandle methodHandleUnreflectGetter = MethodHandles.lookup().unreflectGetter(declaredField);
            Unsafe unsafe2 = unsafe;
            long j = unsafe2.getLong(cls, iFieldOffset);
            if (j == 0) {
                return List.of();
            }
            int i = unsafe2.getInt(j);
            ArrayList arrayList = new ArrayList(i);
            for (int i2 = 0; i2 < i; i2++) {
                unsafe.putLong(methodHandleUnreflectGetter, artOffset, (((long) i2) * artFieldSize) + j + artFieldBias);
                Field field = (Field) MethodHandles.reflectAs(Field.class, methodHandleUnreflectGetter);
                if (!Modifier.isStatic(field.getModifiers())) {
                    arrayList.add(field);
                }
            }
            return arrayList;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return List.of();
        }
    }

    @NonNull
    public static List<Field> getStaticFields(@NonNull Class<?> cls) {
        if (cls.isPrimitive() || cls.isArray()) {
            return List.of();
        }
        try {
            Field declaredField = Helper.NeverCall.class.getDeclaredField("s");
            declaredField.setAccessible(true);
            MethodHandle methodHandleUnreflectGetter = MethodHandles.lookup().unreflectGetter(declaredField);
            Unsafe unsafe2 = unsafe;
            long j = unsafe2.getLong(cls, sFieldOffset);
            if (j == 0) {
                return List.of();
            }
            int i = unsafe2.getInt(j);
            ArrayList arrayList = new ArrayList(i);
            for (int i2 = 0; i2 < i; i2++) {
                unsafe.putLong(methodHandleUnreflectGetter, artOffset, (((long) i2) * artFieldSize) + j + artFieldBias);
                Field field = (Field) MethodHandles.reflectAs(Field.class, methodHandleUnreflectGetter);
                if (Modifier.isStatic(field.getModifiers())) {
                    arrayList.add(field);
                }
            }
            return arrayList;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return List.of();
        }
    }

    public static Object invoke(@NonNull Class<?> cls, @Nullable Object obj, @NonNull String str, Object... objArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (obj != null && !cls.isInstance(obj)) {
            throw new IllegalArgumentException("this object is not an instance of the given class");
        }
        Method declaredMethod = Helper.InvokeStub.class.getDeclaredMethod("invoke", Object[].class);
        declaredMethod.setAccessible(true);
        Unsafe unsafe2 = unsafe;
        long j = unsafe2.getLong(cls, methodsOffset);
        if (j == 0) {
            throw new NoSuchMethodException("Cannot find matching method");
        }
        int i = unsafe2.getInt(j);
        for (int i2 = 0; i2 < i; i2++) {
            unsafe.putLong(declaredMethod, methodOffset, (((long) i2) * artMethodSize) + j + artMethodBias);
            if (str.equals(declaredMethod.getName()) && Helper.checkArgsForInvokeMethod(declaredMethod.getParameterTypes(), objArr)) {
                return declaredMethod.invoke(obj, objArr);
            }
        }
        throw new NoSuchMethodException("Cannot find matching method");
    }

    public static Object newInstance(@NonNull Class<?> cls, Object... objArr) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Method declaredMethod = Helper.InvokeStub.class.getDeclaredMethod("invoke", Object[].class);
        Constructor declaredConstructor = Helper.InvokeStub.class.getDeclaredConstructor(Object[].class);
        declaredConstructor.setAccessible(true);
        Unsafe unsafe2 = unsafe;
        long j = unsafe2.getLong(cls, methodsOffset);
        if (j == 0) {
            throw new NoSuchMethodException("Cannot find matching constructor");
        }
        int i = unsafe2.getInt(j);
        for (int i2 = 0; i2 < i; i2++) {
            long j2 = (((long) i2) * artMethodSize) + j + artMethodBias;
            Unsafe unsafe3 = unsafe;
            long j3 = methodOffset;
            unsafe3.putLong(declaredMethod, j3, j2);
            if ("<init>".equals(declaredMethod.getName())) {
                unsafe3.putLong(declaredConstructor, j3, j2);
                unsafe3.putObject(declaredConstructor, classOffset, cls);
                if (Helper.checkArgsForInvokeMethod(declaredConstructor.getParameterTypes(), objArr)) {
                    return declaredConstructor.newInstance(objArr);
                }
            }
        }
        throw new NoSuchMethodException("Cannot find matching constructor");
    }

    public static boolean setHiddenApiExemptions(@NonNull String... strArr) {
        try {
            invoke(VMRuntime.class, invoke(VMRuntime.class, null, "getRuntime", new Object[0]), "setHiddenApiExemptions", strArr);
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }
}
