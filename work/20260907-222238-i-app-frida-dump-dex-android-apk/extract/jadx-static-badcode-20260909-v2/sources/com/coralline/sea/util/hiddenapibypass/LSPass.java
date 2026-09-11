package com.coralline.sea.util.hiddenapibypass;

import android.util.Property;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.coralline.sea.m5;
import dalvik.system.VMRuntime;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: assets/RiskStub.dex */
@RequiresApi(m5.b.z)
public final class LSPass {
    private static final String TAG = "LSPass";
    private static final Property<Class, Method[]> methods = Property.of(Class.class, Method[].class, "DeclaredMethods");
    private static final Property<Class, Constructor[]> constructors = Property.of(Class.class, Constructor[].class, "DeclaredConstructors");
    private static final Property<Class, Field[]> fields = Property.of(Class.class, Field[].class, "DeclaredFields");

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
        for (Constructor<?> constructor : getDeclaredConstructors(cls)) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == clsArr.length) {
                for (int i = 0; i < clsArr.length; i++) {
                    if (clsArr[i] != parameterTypes[i]) {
                        break;
                    }
                }
                return constructor;
            }
        }
        throw new NoSuchMethodException("Cannot find matching constructor");
    }

    public static List<Constructor<?>> getDeclaredConstructors(@NonNull Class<?> cls) {
        return Arrays.asList(constructors.get(cls));
    }

    public static List<Field> getDeclaredFields(@NonNull Class<?> cls) {
        return Arrays.asList(fields.get(cls));
    }

    @NonNull
    public static Method getDeclaredMethod(@NonNull Class<?> cls, @NonNull String str, @NonNull Class<?>... clsArr) throws NoSuchMethodException {
        for (Method method : getDeclaredMethods(cls)) {
            if (method.getName().equals(str)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == clsArr.length) {
                    for (int i = 0; i < clsArr.length; i++) {
                        if (clsArr[i] != parameterTypes[i]) {
                            break;
                        }
                    }
                    return method;
                }
                continue;
            }
        }
        throw new NoSuchMethodException("Cannot find matching method");
    }

    public static List<Method> getDeclaredMethods(@NonNull Class<?> cls) {
        return Arrays.asList(methods.get(cls));
    }

    @NonNull
    public static List<Field> getInstanceFields(@NonNull Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        for (Field field : getDeclaredFields(cls)) {
            if (!Modifier.isStatic(field.getModifiers())) {
                arrayList.add(field);
            }
        }
        return arrayList;
    }

    @NonNull
    public static List<Field> getStaticFields(@NonNull Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        for (Field field : getDeclaredFields(cls)) {
            if (Modifier.isStatic(field.getModifiers())) {
                arrayList.add(field);
            }
        }
        return arrayList;
    }

    public static Object invoke(@NonNull Class<?> cls, @Nullable Object obj, @NonNull String str, Object... objArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (Method method : getDeclaredMethods(cls)) {
            if (method.getName().equals(str) && Helper.checkArgsForInvokeMethod(method.getParameterTypes(), objArr)) {
                method.setAccessible(true);
                return method.invoke(obj, objArr);
            }
        }
        throw new NoSuchMethodException("Cannot find matching method");
    }

    public static Object newInstance(@NonNull Class<?> cls, Object... objArr) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        for (Constructor<?> constructor : getDeclaredConstructors(cls)) {
            if (Helper.checkArgsForInvokeMethod(constructor.getParameterTypes(), objArr)) {
                constructor.setAccessible(true);
                return constructor.newInstance(objArr);
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
