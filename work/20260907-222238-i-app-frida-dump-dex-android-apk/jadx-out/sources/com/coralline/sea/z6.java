package com.coralline.sea;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class z6 {
    public static List<String> a(String str, Class<?> cls, List<String> list) {
        Vector vector = new Vector();
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            for (Map.Entry entry : ((HashMap) declaredField.get(null)).entrySet()) {
                Iterator<String> it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((String) entry.getKey()).contains(it.next())) {
                            vector.add((String) entry.getKey());
                            break;
                        }
                    }
                }
            }
            return vector;
        } catch (IllegalAccessException e) {
            return vector;
        } catch (NoSuchFieldException e2) {
            return vector;
        }
    }

    public static List<String> a(String str, List<String> list, List<String> list2) {
        Vector vector = new Vector();
        try {
            Class<?> clsLoadClass = ClassLoader.getSystemClassLoader().loadClass(str);
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                vector.addAll(a(it.next(), clsLoadClass, list2));
            }
            return vector;
        } catch (ClassNotFoundException e) {
            return vector;
        }
    }
}
