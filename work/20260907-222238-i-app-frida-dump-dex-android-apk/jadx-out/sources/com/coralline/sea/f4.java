package com.coralline.sea;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public enum f4 {
    service_interface("service_interface"),
    service_field("service_field"),
    provider("provider");

    public String a;

    f4(String str) {
        this.a = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.a;
    }
}
