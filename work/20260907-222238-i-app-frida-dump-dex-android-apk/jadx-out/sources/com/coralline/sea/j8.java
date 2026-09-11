package com.coralline.sea;

import java.util.List;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class j8 {
    public String a;
    public String b;
    public b c;
    public b d;
    public a e;

    public static class a {
        public List<Integer> a;
        public List<Integer> b;

        public a(List<Integer> list, List<Integer> list2) {
            this.a = list;
            this.b = list2;
        }

        public List<Integer> a() {
            return this.a;
        }

        public void a(List<Integer> list) {
            this.a = list;
        }

        public List<Integer> b() {
            return this.b;
        }

        public void b(List<Integer> list) {
            this.b = list;
        }
    }

    public static class b {
        public int a;
        public int b;

        public b(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        public int a() {
            return this.b;
        }

        public void a(int i) {
            this.b = i;
        }

        public int b() {
            return this.a;
        }

        public void b(int i) {
            this.a = i;
        }
    }

    public j8(String str, String str2, a aVar, b bVar, b bVar2) {
        this.a = str;
        this.b = str2;
        this.e = aVar;
        this.c = bVar;
        this.d = bVar2;
    }

    public a a() {
        return this.e;
    }

    public void a(a aVar) {
        this.e = aVar;
    }

    public void a(b bVar) {
        this.c = bVar;
    }

    public void a(String str) {
        this.b = str;
    }

    public String b() {
        return this.b;
    }

    public void b(b bVar) {
        this.d = bVar;
    }

    public void b(String str) {
        this.a = str;
    }

    public b c() {
        return this.c;
    }

    public b d() {
        return this.d;
    }

    public String e() {
        return this.a;
    }
}
