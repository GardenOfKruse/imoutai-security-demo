package com.coralline.sea;

import android.text.TextUtils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class m7 {
    public static final StackTraceElement[] a = new StackTraceElement[0];

    public static y6 a(Map<String, String> map, String str) {
        y6 y6Var = y6.monitor;
        return (!TextUtils.isEmpty(str) && map != null && map.size() > 0 && map.containsKey(str)) ? y6.valueOf(map.get(str)) : y6Var;
    }

    public static String a(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split("\\.", i + 1);
        StringBuffer stringBuffer = new StringBuffer();
        int iMin = Math.min(i, strArrSplit.length);
        for (int i2 = 0; i2 < iMin; i2++) {
            stringBuffer.append(strArrSplit[i2]);
            stringBuffer.append(".");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }

    public static String a(String str, Object[] objArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str + "(");
        if (objArr != null && objArr.length > 0) {
            for (Object obj : objArr) {
                String simpleName = null;
                if (obj != null) {
                    simpleName = obj.getClass().getSimpleName();
                }
                stringBuffer.append(simpleName + ",");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        stringBuffer.append(");");
        return stringBuffer.toString();
    }

    public static String a(StackTraceElement[] stackTraceElementArr) {
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= stackTraceElementArr.length) {
                break;
            }
            if (stackTraceElementArr[i2].getClassName().startsWith("$Proxy")) {
                i = i2;
                break;
            }
            i2++;
        }
        while (true) {
            i++;
            if (i >= stackTraceElementArr.length) {
                return null;
            }
            String className = stackTraceElementArr[i].getClassName();
            if (!className.startsWith("java") && !className.startsWith("javax") && !className.startsWith(a0.b) && !className.startsWith("$Proxy") && !className.startsWith("de.robv.android.xposed")) {
                return className;
            }
        }
    }

    public static void a(Throwable th) {
        StringBuffer stringBuffer;
        try {
            stringBuffer = new StringBuffer();
            try {
                StackTraceElement[] stackTrace = th.getStackTrace();
                for (int i = 3; i < stackTrace.length; i++) {
                    stringBuffer.append(stackTrace[i].getClassName() + "\n");
                }
            } catch (Exception e) {
            }
        } catch (Exception e2) {
            stringBuffer = null;
        }
        if (stringBuffer == null) {
            throw new SecurityException();
        }
        SecurityException securityException = new SecurityException(stringBuffer.toString());
        securityException.setStackTrace(a);
        throw securityException;
    }

    public static boolean a(String str, Set<String> set) {
        if (str == null) {
            return true;
        }
        try {
            if (str.length() == 0) {
                return true;
            }
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                if (str.startsWith(it.next())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean a(Set<String> set, String str) {
        if (set == null || set.size() == 0) {
            return true;
        }
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        if (set.size() > 0) {
            for (String str2 : set) {
                if (!TextUtils.isEmpty(str2)) {
                    if (str2.startsWith("!")) {
                        hashSet2.add(str2.substring(1));
                    } else {
                        hashSet.add(str2);
                    }
                }
            }
        }
        if (hashSet.size() != 0) {
            return hashSet.contains(str);
        }
        if (hashSet2.size() != 0) {
            return !hashSet2.contains(str);
        }
        return true;
    }
}
