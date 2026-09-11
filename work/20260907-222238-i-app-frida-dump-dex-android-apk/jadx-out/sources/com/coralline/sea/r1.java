package com.coralline.sea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.coralline.sea.m5;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class r1 {
    public static r1 l = null;
    public static final String m = "database";
    public static boolean n = true;
    public final String a;
    public SQLiteOpenHelper c;
    public t2 e;
    public SQLiteDatabase f;
    public String g = " isStandardForSafety = true";
    public boolean h = false;
    public boolean i = true;
    public int j = 0;
    public ArrayList<Long> k = new ArrayList<>();
    public final Object b = new Object();
    public boolean d = false;

    public class a extends SQLiteOpenHelper {
        public a(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i) {
            super(context, str, cursorFactory, i);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(i.m);
            sQLiteDatabase.execSQL(k.g);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            sQLiteDatabase.execSQL(i.n);
            onCreate(sQLiteDatabase);
        }
    }

    public class b implements j<Object> {
        public final /* synthetic */ List a;

        public b(List list) {
            this.a = list;
        }

        @Override // com.coralline.sea.r1.j
        public Object a(SQLiteDatabase sQLiteDatabase) {
            for (s1 s1Var : this.a) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(i.e, Long.valueOf(s1Var.g));
                contentValues.put(i.f, Long.valueOf(ja.o()));
                contentValues.put(i.g, s1.a(s1Var));
                if (n3.a().E) {
                    sQLiteDatabase.insertOrThrow(i.d, null, contentValues);
                    String unused = r1.this.g;
                } else {
                    r1.this.e.b(sQLiteDatabase, i.d, contentValues);
                }
            }
            return r1.this.b;
        }
    }

    public class c implements j<Object> {
        public final /* synthetic */ s1 a;

        public c(s1 s1Var) {
            this.a = s1Var;
        }

        @Override // com.coralline.sea.r1.j
        public Object a(SQLiteDatabase sQLiteDatabase) {
            boolean z;
            ContentValues contentValues = new ContentValues();
            if (n3.a().E) {
                contentValues.put(i.e, Long.valueOf(this.a.g));
                contentValues.put(i.f, Long.valueOf(ja.o()));
                contentValues.put(i.g, s1.a(this.a));
                sQLiteDatabase.insertOrThrow(i.d, null, contentValues);
                s1 s1Var = this.a;
                String str = s1Var.d;
                long j = s1Var.g;
                String unused = r1.this.g;
            } else {
                try {
                    JSONObject jSONObjectA = this.a.a();
                    jSONObjectA.toString();
                    String strOptString = jSONObjectA.optString("protol_type");
                    String strOptString2 = jSONObjectA.optString("udid");
                    if (TextUtils.equals(strOptString, "speed0") || TextUtils.equals(strOptString, "debug") || TextUtils.equals(strOptString, "camera") || TextUtils.equals(strOptString, "host_fraud") || TextUtils.equals(strOptString, j4.g) || TextUtils.equals(strOptString, k4.c) || TextUtils.equals(strOptString, "inject") || TextUtils.equals(strOptString, "hxb_login") || TextUtils.equals(strOptString, x9.k) || TextUtils.equals(strOptString, u6.c) || TextUtils.equals(strOptString, "screen_sharing") || TextUtils.equals(strOptString, p8.h) || TextUtils.equals(strOptString, com.coralline.sea.e.c) || TextUtils.equals(strOptString, com.coralline.sea.d.c) || TextUtils.equals(strOptString, "auto_click") || TextUtils.equals(strOptString, x8.i) || TextUtils.equals(strOptString, na.i) || TextUtils.equals(strOptString, "sim_switching")) {
                        com.coralline.sea.checkers.a.c().a(1);
                        z = true;
                    } else {
                        z = false;
                    }
                    if (TextUtils.equals(jSONObjectA.optString("type"), "gps_new")) {
                        com.coralline.sea.checkers.a.c().a(1);
                        z = true;
                    }
                    if (z) {
                        r1 r1Var = r1.this;
                        if (!r1Var.h) {
                            r1.n = false;
                            r1Var.h = true;
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("udid", strOptString2);
                            jSONObject.put("isRiskDevice", true);
                            m5.a().a(jSONObject, m5.b.C0005b.j);
                        }
                    }
                    boolean z2 = !TextUtils.equals(strOptString, g9.b);
                    contentValues.put(i.e, Long.valueOf(this.a.g));
                    contentValues.put(i.f, Long.valueOf(ja.o()));
                    contentValues.put(i.g, s1.a(this.a));
                    contentValues.put(i.h, strOptString);
                    contentValues.put(i.i, Boolean.FALSE);
                    contentValues.put(i.j, Boolean.valueOf(z));
                    contentValues.put(i.k, Boolean.valueOf(z2));
                    r1.this.e.b(sQLiteDatabase, i.d, contentValues);
                    s1 s1Var2 = this.a;
                    String str2 = s1Var2.d;
                    long j2 = s1Var2.g;
                } catch (Exception e) {
                }
            }
            return r1.this.b;
        }
    }

    public class d implements j {
        public final /* synthetic */ s1 a;

        public d(s1 s1Var) {
            this.a = s1Var;
        }

        @Override // com.coralline.sea.r1.j
        public Object a(SQLiteDatabase sQLiteDatabase) {
            if (n3.a().E) {
                sQLiteDatabase.delete(i.d, "a=?", new String[]{String.valueOf(this.a.g)});
                s1 s1Var = this.a;
                String str = s1Var.d;
                long j = s1Var.g;
                String unused = r1.this.g;
            } else {
                r1.this.e.a(sQLiteDatabase, i.d, "a=?", new String[]{String.valueOf(this.a.g)});
                s1 s1Var2 = this.a;
                String str2 = s1Var2.d;
                long j2 = s1Var2.g;
            }
            return r1.this.b;
        }
    }

    public class e implements j {
        public e() {
        }

        @Override // com.coralline.sea.r1.j
        public Object a(SQLiteDatabase sQLiteDatabase) {
            if (n3.a().E) {
                sQLiteDatabase.delete(i.d, null, null);
                String unused = r1.this.g;
            } else {
                r1.this.e.a(sQLiteDatabase, i.d, (String) null, (String[]) null);
            }
            return r1.this.b;
        }
    }

    public class f implements j<List<s1>> {
        public final /* synthetic */ String a;

        public f(String str) {
            this.a = str;
        }

        @Override // com.coralline.sea.r1.j
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public List<s1> a(SQLiteDatabase sQLiteDatabase) {
            Cursor cursorQuery = sQLiteDatabase.query(i.d, i.l, this.a, null, null, null, i.e);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            if (cursorQuery.getCount() > 0) {
                while (cursorQuery.moveToNext()) {
                    long j = cursorQuery.getLong(cursorQuery.getColumnIndex(i.e));
                    cursorQuery.getLong(cursorQuery.getColumnIndex(i.f));
                    s1 s1VarA = s1.a(cursorQuery.getString(cursorQuery.getColumnIndex(i.g)));
                    if (s1VarA == null) {
                        arrayList.add(String.valueOf(j));
                    } else {
                        arrayList2.add(s1VarA);
                    }
                }
            }
            cursorQuery.close();
            arrayList2.size();
            Arrays.toString(arrayList2.toArray());
            if (n3.a().E) {
                if (arrayList.size() > 0) {
                    sQLiteDatabase.delete(i.d, "a=?", (String[]) arrayList.toArray(new String[0]));
                    Arrays.toString(arrayList.toArray());
                    String unused = r1.this.g;
                    return arrayList2;
                }
            } else if (arrayList.size() > 0) {
                r1.this.e.a(sQLiteDatabase, i.d, "a=?", (String[]) arrayList.toArray(new String[0]));
                Arrays.toString(arrayList.toArray());
            }
            return arrayList2;
        }
    }

    public class g implements j<Object> {
        public g() {
        }

        @Override // com.coralline.sea.r1.j
        public Object a(SQLiteDatabase sQLiteDatabase) {
            Cursor cursorQuery = sQLiteDatabase.query(true, i.d, new String[]{i.f}, null, null, null, null, i.e, null);
            if (cursorQuery.getCount() > 0) {
                while (cursorQuery.moveToNext()) {
                    r1.this.k.add(Long.valueOf(cursorQuery.getLong(cursorQuery.getColumnIndex(i.f))));
                }
            }
            cursorQuery.close();
            return new Object();
        }
    }

    public class h implements j<Object> {
        public final /* synthetic */ String a;

        public h(String str) {
            this.a = str;
        }

        @Override // com.coralline.sea.r1.j
        public Object a(SQLiteDatabase sQLiteDatabase) {
            if (n3.a().E) {
                sQLiteDatabase.delete(i.d, this.a, null);
            } else {
                r1.this.e.a(sQLiteDatabase, i.d, this.a, (String[]) null);
            }
            if (r1.this.j > 0) {
                return r1.this.b;
            }
            return null;
        }
    }

    public static final class i {
        public static final long a = 94371840;
        public static final int b = 3;
        public static final String n = "drop table if exists table1";
        public static final String c = "bcedata_" + n3.a().f + "1.db";
        public static final String e = "a";
        public static final String f = "b";
        public static final String g = "c";
        public static final String h = "d";
        public static final String i = "e";
        public static final String j = "f";
        public static final String k = "g";
        public static String[] l = {e, f, g, h, i, j, k};
        public static final String d = "table1";
        public static final String m = String.format(Locale.CHINA, "create table if not exists %s (%s integer primary key, %s integer, %s text, %s text, %s integer, %s integer, %s integer)", d, e, f, g, h, i, j, k);
    }

    public interface j<T> {
        T a(SQLiteDatabase sQLiteDatabase);
    }

    public static final class k {
        public static final String b = "id";
        public static final String c = "subid";
        public static final String d = "slotindex";
        public static final String e = "time";
        public static String[] f = {c, d, e};
        public static final String a = "siminfo";
        public static final String g = String.format(Locale.CHINA, "create table if not exists %s (%s integer primary key, %s integer, %s integer, %s integer)", a, "id", c, d, e);
    }

    public r1() {
        s2 s2VarA = s2.a();
        this.e = new t2();
        s2VarA.a(new q2(this.e, i.d));
        a aVar = new a(n3.a().a, i.c, null, 3);
        this.c = aVar;
        this.a = aVar.getReadableDatabase().getPath();
        h();
        a();
        b();
    }

    public static synchronized r1 e() {
        if (l == null) {
            l = new r1();
        }
        return l;
    }

    public final long a() {
        return DatabaseUtils.queryNumEntries(this.c.getReadableDatabase(), i.d);
    }

    public final <T> T a(j<T> jVar) throws Throwable {
        SQLiteDatabase writableDatabase;
        try {
            writableDatabase = this.c.getWritableDatabase();
            try {
                writableDatabase.beginTransaction();
                T tA = jVar.a(writableDatabase);
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
                return tA;
            } catch (Exception e2) {
                if (writableDatabase != null) {
                    writableDatabase.endTransaction();
                }
                return null;
            } catch (Throwable th) {
                th = th;
                if (writableDatabase != null) {
                    writableDatabase.endTransaction();
                }
                throw th;
            }
        } catch (Exception e3) {
            writableDatabase = null;
        } catch (Throwable th2) {
            th = th2;
            writableDatabase = null;
        }
    }

    public void a(s1 s1Var) throws Throwable {
        if (s1Var == null) {
            return;
        }
        a(new d(s1Var));
    }

    public final boolean a(long j2) {
        StringBuilder sb = new StringBuilder("b = (select min(b) m_id from table1 where b != ");
        sb.append(j2);
        sb.append(")");
        return a(new h(sb.toString())) != null;
    }

    public boolean a(List<s1> list) throws Throwable {
        if (list == null || list.size() == 0 || this.d || !d()) {
            return false;
        }
        Object objA = a(new b(list));
        h();
        return objA != null;
    }

    public final long b() {
        return DatabaseUtils.longForQuery(this.c.getReadableDatabase(), "select count(distinct b) from table1", null);
    }

    public boolean b(s1 s1Var) throws Throwable {
        if (this.d || !d()) {
            return false;
        }
        Object objA = a(new c(s1Var));
        h();
        return objA != null;
    }

    public void c() {
        a(new e());
    }

    public final boolean d() {
        while (h() > i.a) {
            if (!a(ja.o())) {
                this.d = true;
                return false;
            }
        }
        return true;
    }

    public List<s1> f() throws Throwable {
        if (this.j == 0) {
            g();
        }
        if (this.j == this.k.size()) {
            return new ArrayList();
        }
        String str = "b = " + this.k.get(this.j) + " and b != " + ja.o();
        this.j++;
        return (List) a(new f(str));
    }

    public final void g() throws Throwable {
        if (this.k.isEmpty()) {
            a(new g());
        }
        this.k.size();
    }

    public final long h() {
        return new File(this.a).length();
    }
}
