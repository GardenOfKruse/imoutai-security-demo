package com.coralline.sea;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
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
    */
    public String i() {
        byte b;
        String str = c7.c;
        String str2 = this.c;
        str2.getClass();
        int iHashCode = str2.hashCode();
        if (iHashCode != -1745954712) {
            if (iHashCode != -838595071) {
                b = (iHashCode == 1427818632 && str2.equals(e2.c)) ? (byte) 2 : (byte) -1;
            } else if (str2.equals(e2.b)) {
                b = 1;
            }
        } else if (str2.equals(e2.d)) {
            b = 0;
        }
        switch (b) {
            case 0:
                str = "K";
                break;
            case 1:
                str = "U";
                break;
            case 2:
                str = "D";
                break;
        }
        StringBuilder sb = new StringBuilder("<");
        for (int i = 0; i < this.p.size(); i++) {
            sb.append(this.p.get(i).d);
            if (i != this.p.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(">");
        return str + " " + sb.toString();
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
