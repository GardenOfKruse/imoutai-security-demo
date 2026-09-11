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

/* JADX INFO: loaded from: assets/RiskStub.dex */
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
        To view partially-correct add '--show-bad-code' argument
    */
    public void a(android.database.sqlite.SQLiteDatabase r2, java.lang.String r3, java.lang.String r4, android.content.ContentValues r5) {
        /*
            r1 = this;
            r3.getClass()
            int r4 = r3.hashCode()
            r0 = -2130463047(0xffffffff8103b6b9, float:-2.4191997E-38)
            if (r4 == r0) goto L3d
            r0 = -1785516855(0xffffffff95932cc9, float:-5.9443486E-26)
            if (r4 == r0) goto L32
            r0 = 77406376(0x49d20a8, float:3.6940513E-36)
            if (r4 == r0) goto L27
            r0 = 2012838315(0x77f979ab, float:1.0119919E34)
            if (r4 == r0) goto L1c
            goto L45
        L1c:
            java.lang.String r4 = "DELETE"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L25
            goto L45
        L25:
            r3 = 3
            goto L48
        L27:
            java.lang.String r4 = "QUERY"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L30
            goto L45
        L30:
            r3 = 2
            goto L48
        L32:
            java.lang.String r4 = "UPDATE"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L3b
            goto L45
        L3b:
            r3 = 1
            goto L48
        L3d:
            java.lang.String r4 = "INSERT"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L47
        L45:
            r3 = -1
            goto L48
        L47:
            r3 = 0
        L48:
            switch(r3) {
                case 0: goto L58;
                case 1: goto L54;
                case 2: goto L50;
                case 3: goto L4c;
                default: goto L4b;
            }
        L4b:
            return
        L4c:
            r1.b(r5)
            return
        L50:
            r1.a(r5)
            return
        L54:
            r1.c(r5)
            return
        L58:
            r1.a(r2, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.q2.a(android.database.sqlite.SQLiteDatabase, java.lang.String, java.lang.String, android.content.ContentValues):void");
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
