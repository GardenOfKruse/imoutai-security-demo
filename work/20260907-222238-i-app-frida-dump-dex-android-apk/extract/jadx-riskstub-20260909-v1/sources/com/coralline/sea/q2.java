package com.coralline.sea;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.coralline.sea.r1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class q2 implements r2 {
    public static boolean d = false;
    public static String e = "database";
    public Map<Long, List<ContentValues>> a = new HashMap();
    public t2 b;
    public String c;

    public class a implements Comparator<ContentValues> {
        public a() {
        }

        @Override // java.util.Comparator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(ContentValues contentValues, ContentValues contentValues2) {
            return (contentValues.getAsLong(r1.i.f).longValue() > contentValues2.getAsLong(r1.i.f).longValue() ? 1 : (contentValues.getAsLong(r1.i.f).longValue() == contentValues2.getAsLong(r1.i.f).longValue() ? 0 : -1));
        }
    }

    public q2(t2 t2Var, String str) {
        this.b = t2Var;
        this.c = str;
    }

    public final void a(ContentValues contentValues) {
    }

    public final void a(SQLiteDatabase sQLiteDatabase, ContentValues contentValues) {
        Long asLong = contentValues.getAsLong(r1.i.f);
        if (!this.a.containsKey(asLong)) {
            this.a.put(asLong, new ArrayList());
        }
        this.a.get(asLong).add(contentValues);
        this.a.toString();
        a(sQLiteDatabase, asLong);
    }

    public final void a(SQLiteDatabase sQLiteDatabase, Long l) {
        List<ContentValues> list = this.a.get(l);
        boolean zA = a(sQLiteDatabase, list);
        if (d) {
            zA = true;
        }
        if (list == null || !zA) {
            return;
        }
        Collections.sort(list, new a());
        a(list);
        this.a.remove(l);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0045  */
    @Override // com.coralline.sea.r2
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void a(SQLiteDatabase sQLiteDatabase, String str, String str2, ContentValues contentValues) {
        byte b;
        str.getClass();
        int iHashCode = str.hashCode();
        if (iHashCode != -2130463047) {
            if (iHashCode != -1785516855) {
                if (iHashCode != 77406376) {
                    b = (iHashCode == 2012838315 && str.equals("DELETE")) ? (byte) 3 : (byte) -1;
                } else if (str.equals("QUERY")) {
                    b = 2;
                }
            } else if (str.equals("UPDATE")) {
                b = 1;
            }
        } else if (str.equals("INSERT")) {
            b = 0;
        }
        switch (b) {
            case 0:
                a(sQLiteDatabase, contentValues);
                break;
            case 1:
                c(contentValues);
                break;
            case 2:
                a(contentValues);
                break;
            case 3:
                b(contentValues);
                break;
        }
    }

    public final void a(List<ContentValues> list) {
        try {
            Iterator<ContentValues> it = list.iterator();
            while (it.hasNext()) {
                s1 s1VarA = s1.a(it.next().getAsString(r1.i.g));
                String str = s1VarA.d;
                if (z1.r) {
                    h9.b().c().a(s1VarA);
                } else {
                    n8.c().a(s1VarA);
                }
                if (!d) {
                    d = true;
                }
            }
        } catch (Exception e2) {
        }
    }

    public final boolean a(SQLiteDatabase sQLiteDatabase, List<ContentValues> list) {
        try {
            for (ContentValues contentValues : list) {
                contentValues.getAsString(r1.i.h);
                TextUtils.equals(contentValues.getAsString(r1.i.h), g9.b);
                contentValues.getAsLong(r1.i.f);
                contentValues.toString();
                if (TextUtils.equals(contentValues.getAsString(r1.i.h), g9.b)) {
                    if (this.b.a(sQLiteDatabase, this.c, contentValues.getAsLong(r1.i.f), r1.i.g)) {
                        return true;
                    }
                } else if (contentValues.getAsBoolean(r1.i.j).booleanValue()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            return false;
        }
    }

    public final void b(ContentValues contentValues) {
    }

    public final void c(ContentValues contentValues) {
    }
}
