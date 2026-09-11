package com.coralline.sea;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class g {
    public static Activity a() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object objInvoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
            Field declaredField = cls.getDeclaredField("mActivities");
            declaredField.setAccessible(true);
            Map map = (Map) declaredField.get(objInvoke);
            cls.getDeclaredField("mNewActivities").setAccessible(true);
        } catch (Throwable th) {
        }
        for (Object obj : map.values()) {
            Class<?> cls2 = obj.getClass();
            Field declaredField2 = cls2.getDeclaredField("paused");
            Field declaredField3 = cls2.getDeclaredField("stopped");
            declaredField2.setAccessible(true);
            declaredField3.setAccessible(true);
            if (!declaredField2.getBoolean(obj) && !declaredField3.getBoolean(obj)) {
                Field declaredField4 = cls2.getDeclaredField("activity");
                declaredField4.setAccessible(true);
                return (Activity) declaredField4.get(obj);
            }
            return null;
        }
        return null;
    }

    public static List<Activity> a(Application application) {
        ArrayList arrayList = new ArrayList();
        try {
            Field declaredField = Application.class.getDeclaredField("mLoadedApk");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(application);
            Field declaredField2 = obj.getClass().getDeclaredField("mActivityThread");
            declaredField2.setAccessible(true);
            Object obj2 = declaredField2.get(obj);
            Field declaredField3 = obj2.getClass().getDeclaredField("mActivities");
            declaredField3.setAccessible(true);
            Object obj3 = declaredField3.get(obj2);
            if (!(obj3 instanceof Map)) {
                return arrayList;
            }
            Iterator it = ((Map) obj3).entrySet().iterator();
            while (it.hasNext()) {
                Object value = ((Map.Entry) it.next()).getValue();
                Field declaredField4 = value.getClass().getDeclaredField("activity");
                declaredField4.setAccessible(true);
                arrayList.add((Activity) declaredField4.get(value));
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean a(Activity activity) {
        try {
            Method declaredMethod = Activity.class.getDeclaredMethod("isResumed", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(activity, new Object[0])).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean a(Context context) {
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            if (Process.myPid() == runningAppProcessInfo.pid) {
                return runningAppProcessInfo.importance != 100;
            }
        }
        return false;
    }

    public static boolean b(Activity activity) {
        try {
            return ((Boolean) q7.a((Object) activity).f("mStopped")).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
