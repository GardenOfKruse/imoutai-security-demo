package com.coralline.sea;

import java.io.Serializable;
import java.io.Writer;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class i9 extends Writer implements Serializable {
    public static final long b = -146927496096066153L;
    public final StringBuilder a;

    public i9() {
        this.a = new StringBuilder();
    }

    public i9(int i) {
        this.a = new StringBuilder(i);
    }

    public i9(StringBuilder sb) {
        this.a = sb == null ? new StringBuilder() : sb;
    }

    public StringBuilder a() {
        return this.a;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(char c) {
        this.a.append(c);
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence) {
        this.a.append(charSequence);
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence, int i, int i2) {
        this.a.append(charSequence, i, i2);
        return this;
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
    }

    public String toString() {
        return this.a.toString();
    }

    @Override // java.io.Writer
    public void write(String str) {
        if (str != null) {
            this.a.append(str);
        }
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        if (cArr != null) {
            this.a.append(cArr, i, i2);
        }
    }
}
