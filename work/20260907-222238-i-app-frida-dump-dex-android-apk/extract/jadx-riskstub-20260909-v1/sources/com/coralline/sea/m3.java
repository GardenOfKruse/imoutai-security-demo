package com.coralline.sea;

import com.coralline.sea.a;
import java.io.FileOutputStream;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class m3 implements a.g {
    @Override // com.coralline.sea.a.g
    public void a(String str) throws Throwable {
        b(str);
    }

    public final void b(String str) throws Throwable {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(n3.a().r + "/everisk_anrcrash.txt");
            } catch (Exception e) {
                return;
            }
        } catch (Exception e2) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            fileOutputStream.write(str.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e3) {
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (Exception e4) {
                }
            }
            throw th;
        }
    }
}
