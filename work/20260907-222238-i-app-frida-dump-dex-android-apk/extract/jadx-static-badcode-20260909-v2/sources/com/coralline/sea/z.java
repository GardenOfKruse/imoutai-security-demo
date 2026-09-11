package com.coralline.sea;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class z {
    public String a;
    public String b;
    public String c;
    public String d;
    public long e;

    public static class b {
        public String a;
        public String b;
        public String c;
        public String d;
        public long e;

        public b a(long j) {
            this.e = j;
            return this;
        }

        public b a(String str) {
            this.d = str;
            return this;
        }

        public z a() {
            return new z(this);
        }

        public b b(String str) {
            this.a = str;
            return this;
        }

        public b c(String str) {
            this.c = str;
            return this;
        }

        public b d(String str) {
            this.b = str;
            return this;
        }
    }

    public z(b bVar) {
        this.a = bVar.a;
        this.b = bVar.b;
        this.c = bVar.c;
        this.d = bVar.d;
        this.e = bVar.e;
    }

    public static b a() {
        return new b();
    }

    public static z a(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        String[] strArrSplit = str.split("\\|");
        if (strArrSplit.length < 5) {
            return null;
        }
        String str2 = strArrSplit[0];
        String str3 = strArrSplit[1];
        String str4 = strArrSplit[2];
        String str5 = strArrSplit[3];
        long j = Long.parseLong(strArrSplit[4]);
        b bVar = new b();
        bVar.a = str2;
        bVar.b = str3;
        bVar.c = str4;
        bVar.d = str5;
        bVar.e = j;
        return new z(bVar);
    }

    public String b() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append("|");
        String str = this.b;
        if (str == null) {
            str = i2.b;
        }
        sb.append(str);
        sb.append("|");
        String str2 = this.c;
        if (str2 == null) {
            str2 = i2.b;
        }
        sb.append(str2);
        sb.append("|");
        String str3 = this.d;
        if (str3 == null) {
            str3 = i2.b;
        }
        sb.append(str3);
        sb.append("|");
        sb.append(this.e);
        return sb.toString();
    }
}
