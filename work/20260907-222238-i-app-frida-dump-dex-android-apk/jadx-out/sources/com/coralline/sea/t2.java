package com.coralline.sea;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class t2 {
    public static String b = "database";
    public SQLiteDatabase a;

    public int a(SQLiteDatabase sQLiteDatabase, String str, ContentValues contentValues, String str2, String[] strArr) {
        int iUpdate = sQLiteDatabase.update(str, contentValues, str2, strArr);
        if (iUpdate > 0) {
            s2.a().a(sQLiteDatabase, "UPDATE", str, contentValues);
        }
        return iUpdate;
    }

    public int a(SQLiteDatabase sQLiteDatabase, String str, String str2, String[] strArr) {
        int iDelete = sQLiteDatabase.delete(str, str2, strArr);
        if (iDelete > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("whereClause", str2);
            s2.a().a(sQLiteDatabase, "DELETE", str, contentValues);
        }
        return iDelete;
    }

    public long a(SQLiteDatabase sQLiteDatabase, String str, ContentValues contentValues) {
        long jInsert = sQLiteDatabase.insert(str, null, contentValues);
        if (jInsert != -1) {
            s2.a().a(sQLiteDatabase, "INSERT", str, contentValues);
        }
        return jInsert;
    }

    public Cursor a(SQLiteDatabase sQLiteDatabase, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5) {
        Cursor cursorQuery = sQLiteDatabase.query(str, strArr, str2, strArr2, str3, str4, str5);
        if (cursorQuery != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("query", "Performed a query operation.");
            s2.a().a(sQLiteDatabase, "QUERY", str, contentValues);
        }
        return cursorQuery;
    }

    public final s1 a(String str) {
        return s1.a(str);
    }

    public final String a(SQLiteDatabase sQLiteDatabase, String str, String str2, Long l) {
        Cursor cursorRawQuery = sQLiteDatabase.rawQuery("SELECT " + str2 + " FROM " + str + " WHERE b = ?", new String[]{String.valueOf(l)});
        String string = cursorRawQuery.moveToFirst() ? cursorRawQuery.getString(0) : null;
        cursorRawQuery.close();
        return string;
    }

    public void a(SQLiteDatabase sQLiteDatabase, String str, Long l) {
        sQLiteDatabase.delete(str, "start_id = ?", new String[]{String.valueOf(l)});
    }

    public boolean a(SQLiteDatabase sQLiteDatabase, String str, Long l, String str2) {
        s1 s1VarA = a(a(sQLiteDatabase, str, str2, l));
        return s1VarA.a.contains(g9.f) || s1VarA.a.contains(g9.h) || s1VarA.a.contains(g9.e) || s1VarA.a.contains("emulator");
    }

    public boolean a(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3) {
        boolean z = false;
        Cursor cursorRawQuery = sQLiteDatabase.rawQuery("SELECT EXISTS (SELECT 1 FROM " + str + " WHERE " + str2 + " = ? LIMIT 1)", new String[]{str3});
        if (cursorRawQuery.moveToFirst() && cursorRawQuery.getInt(0) == 1) {
            z = true;
        }
        cursorRawQuery.close();
        return z;
    }

    public long b(SQLiteDatabase sQLiteDatabase, String str, ContentValues contentValues) throws SQLException {
        long jInsertOrThrow = sQLiteDatabase.insertOrThrow(str, null, contentValues);
        if (jInsertOrThrow != -1) {
            s2.a().a(sQLiteDatabase, "INSERT", str, contentValues);
            return jInsertOrThrow;
        }
        throw new SQLException("Failed to insert row into " + str);
    }
}
