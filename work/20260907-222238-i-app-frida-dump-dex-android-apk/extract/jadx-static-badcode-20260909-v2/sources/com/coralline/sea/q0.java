package com.coralline.sea;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.DigestException;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class q0 implements p2 {
    public final ByteBuffer a;

    public q0(ByteBuffer byteBuffer) {
        this.a = byteBuffer.slice();
    }

    @Override // com.coralline.sea.p2
    public void a(o2 o2Var, long j, int i) throws DigestException, IOException {
        ByteBuffer byteBufferSlice;
        synchronized (this.a) {
            this.a.position(0);
            int i2 = (int) j;
            this.a.limit(i + i2);
            this.a.position(i2);
            byteBufferSlice = this.a.slice();
        }
        o2Var.a(byteBufferSlice);
    }

    @Override // com.coralline.sea.p2
    public long size() {
        return this.a.capacity();
    }
}
