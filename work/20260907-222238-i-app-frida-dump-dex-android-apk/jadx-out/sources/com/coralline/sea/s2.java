package com.coralline.sea;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class s2 {
    public static s2 b;
    public List<r2> a = new ArrayList();

    public static synchronized s2 a() {
        if (b == null) {
            b = new s2();
        }
        return b;
    }

    public void a(SQLiteDatabase sQLiteDatabase, String str, String str2, ContentValues contentValues) {
        this.a.size();
        Iterator<r2> it = this.a.iterator();
        while (it.hasNext()) {
            it.next().a(sQLiteDatabase, str, str2, contentValues);
        }
    }

    public void a(r2 r2Var) {
        if (this.a.contains(r2Var)) {
            return;
        }
        this.a.add(r2Var);
    }

    public void b(r2 r2Var) {
        this.a.remove(r2Var);
    }
}
