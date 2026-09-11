package com.coralline.sea;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class h6 extends s1 {
    public ArrayList<s1> p;
    public int q;
    public JSONObject r;

    public class a implements l4 {
        public a() {
        }

        @Override // com.coralline.sea.l4
        public void a(s1 s1Var) {
            h6 h6Var = h6.this;
            if (h6Var == s1Var) {
                Iterator it = h6Var.p.iterator();
                while (it.hasNext()) {
                    t7.a().b((s1) it.next(), h6.this.c());
                }
            }
        }

        @Override // com.coralline.sea.l4
        public void b(s1 s1Var) {
            h6 h6Var = h6.this;
            if (h6Var == s1Var) {
                Iterator it = h6Var.p.iterator();
                while (it.hasNext()) {
                    t7.a().a((s1) it.next());
                }
                j1.d(this, h6.this.d);
            }
        }
    }

    public h6() {
        super(c7.c, y1.b("multi_message"), "multi_message", e2.b, !n3.a().E);
        this.p = new ArrayList<>();
        this.q = this.b.length();
        j1.c(new a(), this.d);
        try {
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("data", jSONArray);
            this.r = y1.a(jSONObject.toString(), this.b);
        } catch (JSONException e) {
        }
    }

    @Override // com.coralline.sea.s1
    public JSONObject a() {
        return this.r;
    }

    @Override // com.coralline.sea.s1
    public void a(byte[] bArr) {
        this.h = bArr;
        for (int i = 0; i < this.p.size(); i++) {
            this.p.get(i).a(bArr);
        }
    }

    @Override // com.coralline.sea.s1
    public String b() {
        return a().toString();
    }

    public void b(s1 s1Var) {
        this.q = s1Var.j() + this.q;
        this.p.add(s1Var);
        try {
            this.r.getJSONArray("data").put(s1Var.a());
        } catch (Exception e) {
        }
    }

    @Override // com.coralline.sea.s1
    public void c(String str) {
        for (int i = 0; i < this.p.size(); i++) {
            this.p.get(i).c(str);
        }
    }

    @Override // com.coralline.sea.s1
    public void d(String str) {
        this.i = str;
        for (int i = 0; i < this.p.size(); i++) {
            this.p.get(i).d(str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x003b  */
    @Override // com.coralline.sea.s1
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String i() {
        /*
            r6 = this;
            java.lang.String r0 = ""
            java.lang.String r1 = r6.c
            r1.getClass()
            int r2 = r1.hashCode()
            r3 = -1745954712(0xffffffff97eed868, float:-1.5435018E-24)
            r4 = 0
            r5 = 1
            if (r2 == r3) goto L33
            r3 = -838595071(0xffffffffce040e01, float:-5.5387757E8)
            if (r2 == r3) goto L28
            r3 = 1427818632(0x551ac888, float:1.06366291E13)
            if (r2 == r3) goto L1d
            goto L3b
        L1d:
            java.lang.String r2 = "download"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L26
            goto L3b
        L26:
            r1 = 2
            goto L3e
        L28:
            java.lang.String r2 = "upload"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L31
            goto L3b
        L31:
            r1 = 1
            goto L3e
        L33:
            java.lang.String r2 = "keepalive"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L3d
        L3b:
            r1 = -1
            goto L3e
        L3d:
            r1 = 0
        L3e:
            switch(r1) {
                case 0: goto L48;
                case 1: goto L45;
                case 2: goto L42;
                default: goto L41;
            }
        L41:
            goto L4a
        L42:
            java.lang.String r0 = "D"
            goto L4a
        L45:
            java.lang.String r0 = "U"
            goto L4a
        L48:
            java.lang.String r0 = "K"
        L4a:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "<"
            r1.<init>(r2)
        L51:
            java.util.ArrayList<com.coralline.sea.s1> r2 = r6.p
            int r2 = r2.size()
            if (r4 >= r2) goto L77
            java.util.ArrayList<com.coralline.sea.s1> r2 = r6.p
            java.lang.Object r2 = r2.get(r4)
            com.coralline.sea.s1 r2 = (com.coralline.sea.s1) r2
            java.lang.String r2 = r2.d
            r1.append(r2)
            java.util.ArrayList<com.coralline.sea.s1> r2 = r6.p
            int r2 = r2.size()
            int r2 = r2 - r5
            if (r4 == r2) goto L74
            java.lang.String r2 = ","
            r1.append(r2)
        L74:
            int r4 = r4 + 1
            goto L51
        L77:
            java.lang.String r2 = ">"
            r1.append(r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            java.lang.String r0 = " "
            r2.append(r0)
            java.lang.String r0 = r1.toString()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.h6.i():java.lang.String");
    }

    @Override // com.coralline.sea.s1
    public int j() {
        return this.q;
    }

    public List<s1> k() {
        return this.p;
    }

    public int l() {
        return this.p.size();
    }

    @Override // com.coralline.sea.s1
    public String toString() {
        return "Multiple Message -> " + b();
    }
}
