package com.coralline.sea;

import android.content.Context;
import android.content.res.AssetManager;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class x7 {
    public static void a(AssetManager assetManager, String str, File file) throws Exception {
        InputStream inputStreamOpen = assetManager.open(str);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                byte[] bArr = new byte[4096];
                while (true) {
                    int i = inputStreamOpen.read(bArr);
                    if (i <= 0) {
                        fileOutputStream.close();
                        inputStreamOpen.close();
                        return;
                    }
                    fileOutputStream.write(bArr, 0, i);
                }
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (Throwable th4) {
                }
            }
            throw th3;
        }
    }

    public static boolean a(Context context, String str) {
        boolean z;
        try {
            AssetManager assets = context.getAssets();
            String[] list = assets.list(c7.c);
            if (list != null) {
                for (String str2 : list) {
                    if (str2.equals(str)) {
                        z = true;
                        break;
                    }
                }
                z = false;
            } else {
                z = false;
            }
            if (!z) {
                x9.a("Not fount dex: " + str + " , return");
                return false;
            }
            File file = new File(context.getFilesDir().getParent() + "/.RiskStub", ".checker_manager_opt");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, str);
            if (!file2.exists()) {
                a(assets, str, file2);
            }
            Class clsLoadClass = new DexClassLoader(file2.getAbsolutePath(), file.getAbsolutePath(), null, x7.class.getClassLoader()).loadClass("com.coralline.sea.plugins.manager.PluginsManager");
            for (Constructor<?> constructor : clsLoadClass.getConstructors()) {
                x9.a("Constructor: " + constructor);
            }
            clsLoadClass.getMethod(x9.a, Context.class).invoke(clsLoadClass.getConstructor(new Class[0]).newInstance(new Object[0]), context);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
