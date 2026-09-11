package com.coralline.sea;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.webkit.WebView;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class n7 {
    public static Context a;
    public static b b;
    public static WebView c;
    public static final Messenger d = new Messenger(new c());
    public static final ServiceConnection e = new a();

    public class a implements ServiceConnection {
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                Messenger messenger = new Messenger(iBinder);
                Message messageObtain = Message.obtain((Handler) null, 1000);
                messageObtain.arg1 = Process.myPid();
                messageObtain.replyTo = n7.d;
                messenger.send(messageObtain);
            } catch (RemoteException e) {
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    public interface b {
        void a(Context context, int i);
    }

    public static class c extends Handler {
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1000) {
                super.handleMessage(message);
                return;
            }
            if (n7.b != null) {
                n7.b.a(n7.a, message.arg2);
            }
            n7.a(n7.a);
        }
    }

    public static void a(Context context) {
        if (context == null) {
            return;
        }
        context.unbindService(e);
        WebView webView = c;
        if (webView != null) {
            webView.destroy();
            c = null;
        }
    }

    public static void a(Context context, b bVar) {
        if (context == null) {
            return;
        }
        a = context;
        b = bVar;
        WebView webView = new WebView(context);
        c = webView;
        webView.getSettings().setJavaScriptEnabled(false);
        try {
            context.bindService(new Intent(context, n7.class.getClassLoader().loadClass("com.leopard.zeus.main.checkers.isolated.RSService")), e, 1);
        } catch (Exception e2) {
            e2.toString();
        }
    }
}
