package com.coralline.sea;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class z2 {
    public final Map<String, Long> a = new HashMap();
    public b b;

    public class a implements b {
        @Override // com.coralline.sea.z2.b
        public void a(String str) {
            System.out.println("File modified: " + str);
        }

        @Override // com.coralline.sea.z2.b
        public void b(String str) {
            System.out.println("File deleted: " + str);
        }

        @Override // com.coralline.sea.z2.b
        public void c(String str) {
            System.out.println("File created: " + str);
        }
    }

    public interface b {
        void a(String str);

        void b(String str);

        void c(String str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(File file, long j) {
        while (true) {
            a(file);
            try {
                Thread.sleep(j);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void a(String[] strArr) {
        z2 z2Var = new z2();
        z2Var.b = new a();
        z2Var.b(new File("/storage/emulated/0/"), 5000L);
        z2Var.b(new File("/data/data/com.example.myapp/"), 5000L);
        z2Var.b(new File("/data/app/"), 5000L);
    }

    public void a(b bVar) {
        this.b = bVar;
    }

    public final void a(File file) {
        boolean z;
        if (!file.isDirectory()) {
            String absolutePath = file.getAbsolutePath();
            long jLastModified = file.lastModified();
            if (!this.a.containsKey(absolutePath)) {
                b bVar = this.b;
                if (bVar != null) {
                    bVar.c(absolutePath);
                }
            } else {
                if (jLastModified == this.a.get(absolutePath).longValue()) {
                    return;
                }
                b bVar2 = this.b;
                if (bVar2 != null) {
                    bVar2.a(absolutePath);
                }
            }
            this.a.put(absolutePath, Long.valueOf(jLastModified));
            return;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                long jLastModified2 = file2.lastModified();
                String absolutePath2 = file2.getAbsolutePath();
                if (!this.a.containsKey(absolutePath2)) {
                    b bVar3 = this.b;
                    if (bVar3 != null) {
                        bVar3.c(absolutePath2);
                    }
                } else if (jLastModified2 != this.a.get(absolutePath2).longValue()) {
                    b bVar4 = this.b;
                    if (bVar4 != null) {
                        bVar4.a(absolutePath2);
                    }
                }
                this.a.put(absolutePath2, Long.valueOf(jLastModified2));
            }
            ArrayList arrayList = new ArrayList();
            Iterator<Map.Entry<String, Long>> it = this.a.entrySet().iterator();
            while (it.hasNext()) {
                String key = it.next().getKey();
                int length = fileArrListFiles.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        z = false;
                        break;
                    } else {
                        if (fileArrListFiles[i].getAbsolutePath().equals(key)) {
                            z = true;
                            break;
                        }
                        i++;
                    }
                }
                if (!z) {
                    b bVar5 = this.b;
                    if (bVar5 != null) {
                        bVar5.b(key);
                    }
                    arrayList.add(key);
                }
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                this.a.remove((String) it2.next());
            }
        }
    }

    public final void b(File file) {
        boolean zIsDirectory = file.isDirectory();
        file.toString();
        if (!zIsDirectory) {
            this.a.put(file.getAbsolutePath(), Long.valueOf(file.lastModified()));
            return;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                this.a.put(file2.getAbsolutePath(), Long.valueOf(file2.lastModified()));
            }
        }
    }

    public void b(final File file, final long j) {
        if (!file.exists()) {
            file.toString();
        } else {
            b(file);
            new Thread(new Runnable() { // from class: com.coralline.sea.-$$Lambda$z2$hMoiLVlkBaLaSzhWBDmONZsFpJc
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(file, j);
                }
            }, "Risk-thread-dirMonitor").start();
        }
    }
}
