package com.coralline.sea;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class e4 implements r4 {
    public static e4 a;

    public static /* synthetic */ class a {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[f4.values().length];
            a = iArr;
            try {
                iArr[f4.service_interface.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[f4.service_field.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[f4.provider.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public static final e4 a() {
        if (a == null) {
            synchronized (e4.class) {
                if (a == null) {
                    a = new e4();
                }
            }
        }
        return a;
    }

    @Override // com.coralline.sea.r4
    public q4 a(f4 f4Var) {
        int i = a.a[f4Var.ordinal()];
        if (i == 1) {
            return new v8(f4Var);
        }
        if (i == 2) {
            return new s8(f4Var);
        }
        if (i != 3) {
            return null;
        }
        return new h7(f4Var);
    }
}
