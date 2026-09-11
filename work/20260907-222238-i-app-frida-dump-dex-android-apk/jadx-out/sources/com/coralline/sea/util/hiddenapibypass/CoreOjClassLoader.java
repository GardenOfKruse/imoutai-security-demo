package com.coralline.sea.util.hiddenapibypass;

import androidx.annotation.RequiresApi;
import com.coralline.sea.c7;
import com.coralline.sea.m5;
import com.coralline.sea.util.hiddenapibypass.Helper;
import dalvik.system.PathClassLoader;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Executable;

/* JADX INFO: loaded from: assets/RiskStub.dex */
@RequiresApi(m5.b.z)
final class CoreOjClassLoader extends PathClassLoader {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public CoreOjClassLoader() {
        super(getCoreOjPath(), null);
    }

    private static String getCoreOjPath() {
        return System.getProperty("java.boot.class.path", c7.c).split(":", 2)[0];
    }

    @Override // java.lang.ClassLoader
    public Class<?> loadClass(String str) throws ClassNotFoundException {
        if (Object.class.getName().equals(str)) {
            return Object.class;
        }
        try {
            return findClass(str);
        } catch (ClassNotFoundException e) {
            return Executable.class.getName().equals(str) ? Helper.Executable.class : MethodHandle.class.getName().equals(str) ? Helper.MethodHandle.class : Class.class.getName().equals(str) ? Helper.Class.class : super.loadClass(str);
        }
    }
}
