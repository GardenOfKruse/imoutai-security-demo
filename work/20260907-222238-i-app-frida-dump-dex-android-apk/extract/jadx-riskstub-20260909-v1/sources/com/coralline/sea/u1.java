package com.coralline.sea;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.math.ec.ECPoint;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class u1 {
    public ECPoint b;
    public SM3Digest c;
    public SM3Digest d;
    public int a = 1;
    public byte[] e = new byte[32];
    public byte f = 0;

    public final void a() {
        SM3Digest sM3Digest = new SM3Digest(this.c);
        sM3Digest.update((byte) ((this.a >> 24) & 255));
        sM3Digest.update((byte) ((this.a >> 16) & 255));
        sM3Digest.update((byte) ((this.a >> 8) & 255));
        sM3Digest.update((byte) (this.a & 255));
        sM3Digest.doFinal(this.e, 0);
        this.f = (byte) 0;
        this.a++;
    }

    public final void b() {
        this.c = new SM3Digest();
        this.d = new SM3Digest();
        byte[] bArrA = ia.a(this.b.getXCoord().toBigInteger());
        this.c.update(bArrA, 0, bArrA.length);
        this.d.update(bArrA, 0, bArrA.length);
        byte[] bArrA2 = ia.a(this.b.getYCoord().toBigInteger());
        this.c.update(bArrA2, 0, bArrA2.length);
        this.a = 1;
        a();
    }
}
