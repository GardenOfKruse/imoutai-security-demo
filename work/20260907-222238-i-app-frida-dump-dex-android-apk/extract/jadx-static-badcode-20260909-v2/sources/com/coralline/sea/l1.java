package com.coralline.sea;

import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class l1 {
    public String a;
    public Map<String, String> b;
    public Set<String> c;
    public int d;

    public l1(String str, Map<String, String> map, Set<String> set, int i) {
        this.a = str;
        this.b = map;
        this.c = set;
        this.d = i;
    }

    public String a() {
        return this.a;
    }

    public void a(int i) {
        this.d = i;
    }

    public void a(String str) {
        this.a = str;
    }

    public void a(Map<String, String> map) {
        this.b = map;
    }

    public void a(Set<String> set) {
        this.c = set;
    }

    public Map<String, String> b() {
        return this.b;
    }

    public int c() {
        return this.d;
    }

    public Set<String> d() {
        return this.c;
    }
}
