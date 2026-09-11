package com.moutai.mall;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.secneo.apkwrapper.H;

/* JADX INFO: loaded from: classes.dex */
public class IsoService extends Service {
    final Messenger a = new Messenger(new a());

    static class a extends Handler {
        a() {
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        @Override // android.os.Handler
        public void handleMessage(Message message) throws Exception {
            try {
                int[] iArr = new int[0];
                if (message.what != 1000) {
                    super.handleMessage(message);
                    return;
                }
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException unused) {
                }
                int iCis = H.cis(message.arg1);
                try {
                    Message messageObtain = Message.obtain((Handler) null, 1000);
                    messageObtain.arg2 = iCis;
                    message.replyTo.send(messageObtain);
                } catch (RemoteException unused2) {
                }
            } catch (Exception ex1) {
                throw ex1;
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // android.app.Service
    public IBinder onBind(Intent intent) throws Exception {
        try {
            int[] iArr = new int[0];
            return this.a.getBinder();
        } catch (Exception ex1) {
            throw ex1;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // android.app.Service
    public void onCreate() throws Exception {
        try {
            int[] iArr = new int[0];
            super.onCreate();
        } catch (Exception ex1) {
            throw ex1;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) throws Exception {
        try {
            int[] iArr = new int[0];
            return 2;
        } catch (Exception ex1) {
            throw ex1;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // android.app.Service
    public boolean onUnbind(Intent intent) throws Exception {
        try {
            int[] iArr = new int[0];
            boolean zOnUnbind = super.onUnbind(intent);
            System.exit(0);
            return zOnUnbind;
        } catch (Exception ex1) {
            throw ex1;
        }
    }
}
