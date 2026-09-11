package com.coralline.sea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Collection;
import java.util.HashSet;
import org.json.JSONArray;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class o0 {
    public b a;
    public SQLiteDatabase b;

    public interface a<T> {
        T a(SQLiteDatabase sQLiteDatabase);
    }

    public static class b extends SQLiteOpenHelper {
        public static final int a = 1;
        public static final String b = "OoO_config.db";
        public static final String c = "kv_str";
        public static final String d = "kv_int";

        public b(Context context) {
            super(context, b, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper, java.lang.AutoCloseable
        public synchronized void close() {
            super.close();
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("create table if not exists kv_str (k text UNIQUE on conflict replace, v text)");
            sQLiteDatabase.execSQL("create table if not exists kv_int (k text UNIQUE on conflict replace, v integer)");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    public o0(Context context) {
        b bVar = new b(context);
        this.a = bVar;
        bVar.setWriteAheadLoggingEnabled(true);
    }

    public static /* synthetic */ Object a(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS kv_str");
        sQLiteDatabase.execSQL("create table if not exists kv_str (k text UNIQUE on conflict replace, v text)");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS kv_int");
        sQLiteDatabase.execSQL("create table if not exists kv_int (k text UNIQUE on conflict replace, v integer)");
        return null;
    }

    public static /* synthetic */ String a(String str, int i, SQLiteDatabase sQLiteDatabase) {
        Cursor cursorRawQuery = sQLiteDatabase.rawQuery("select k from kv_int where v=?", new String[]{String.valueOf(i)});
        if (cursorRawQuery.moveToFirst()) {
            str = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("k"));
        }
        cursorRawQuery.close();
        return str;
    }

    public static /* synthetic */ String a(String str, String str2, SQLiteDatabase sQLiteDatabase) {
        Cursor cursorRawQuery = sQLiteDatabase.rawQuery("select v from kv_str where k=?", new String[]{str2});
        if (cursorRawQuery.moveToFirst()) {
            str = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("v"));
        }
        cursorRawQuery.close();
        return str;
    }

    public static /* synthetic */ Boolean b(String str, int i, SQLiteDatabase sQLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("k", str);
        contentValues.put("v", Integer.valueOf(i));
        return Boolean.valueOf(sQLiteDatabase.insert(b.d, null, contentValues) != -1);
    }

    public static /* synthetic */ Boolean b(String str, String str2, SQLiteDatabase sQLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("k", str);
        contentValues.put("v", str2);
        return Boolean.valueOf(sQLiteDatabase.insert(b.c, null, contentValues) != -1);
    }

    public <T> T a(a<T> aVar, T t) {
        try {
            if (c().isOpen()) {
                return aVar.a(c());
            }
        } catch (Exception e) {
        }
        return t;
    }

    public String a(final int i, final String str) {
        return (String) a((a<String>) new a() { // from class: com.coralline.sea.-$$Lambda$vL3JXewsfayNl-_ev0YmTwvFd9g
            @Override // com.coralline.sea.o0.a
            public final Object a(SQLiteDatabase sQLiteDatabase) {
                return o0.a(str, i, sQLiteDatabase);
            }
        }, str);
    }

    public String a(final String str, final String str2) {
        return (String) a((a<String>) new a() { // from class: com.coralline.sea.-$$Lambda$ZxWcdlRJvTlxUrWTLkgzDSL6Ey4
            @Override // com.coralline.sea.o0.a
            public final Object a(SQLiteDatabase sQLiteDatabase) {
                return o0.a(str2, str, sQLiteDatabase);
            }
        }, str2);
    }

    public HashSet<String> a(String str) {
        try {
            return f5.a(new JSONArray(a(str, (String) null)));
        } catch (Exception e) {
            return new HashSet<>();
        }
    }

    public void a() {
        a(new a() { // from class: com.coralline.sea.-$$Lambda$HqrT8tqFg80Z5lRmlOzWTwQg7Zc
            @Override // com.coralline.sea.o0.a
            public final Object a(SQLiteDatabase sQLiteDatabase) {
                return o0.a(sQLiteDatabase);
            }
        }, (Object) null);
    }

    public boolean a(final String str, final int i) {
        return ((Boolean) a((a<Boolean>) new a() { // from class: com.coralline.sea.-$$Lambda$FUs4zIHYjIWQFC0qwABxb__wrZQ
            @Override // com.coralline.sea.o0.a
            public final Object a(SQLiteDatabase sQLiteDatabase) {
                return o0.b(str, i, sQLiteDatabase);
            }
        }, Boolean.FALSE)).booleanValue();
    }

    public boolean a(String str, HashSet<String> hashSet) {
        if (hashSet == null || hashSet.size() <= 0) {
            return false;
        }
        return b(str, new JSONArray((Collection) hashSet).toString());
    }

    public void b() {
        try {
            SQLiteDatabase sQLiteDatabase = this.b;
            if (sQLiteDatabase != null) {
                sQLiteDatabase.close();
            }
        } catch (Exception e) {
        }
    }

    public boolean b(final String str, final String str2) {
        return ((Boolean) a((a<Boolean>) new a() { // from class: com.coralline.sea.-$$Lambda$G4uX_CETsDp0C1lSJZ4ORg8JFBc
            @Override // com.coralline.sea.o0.a
            public final Object a(SQLiteDatabase sQLiteDatabase) {
                return o0.b(str, str2, sQLiteDatabase);
            }
        }, Boolean.FALSE)).booleanValue();
    }

    public final SQLiteDatabase c() {
        if (this.b == null) {
            this.b = this.a.getWritableDatabase();
        }
        return this.b;
    }
}
