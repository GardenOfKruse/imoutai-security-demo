package com.coralline.sea;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class x1 {
    public static final String a = "CommandExecution";
    public static final String b = "su";
    public static final String c = "sh";
    public static final String d = "exit\n";
    public static final String e = "\n";

    public static class a {
        public int a = -1;
        public String b;
        public String c;
    }

    public static a a(String str, boolean z) {
        return a(new String[]{str}, z);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:7|(13:136|8|(1:10)(1:11)|12|143|13|145|14|(3:16|(2:18|149)(1:150)|19)|148|20|139|21)|(5:141|22|(1:24)(1:151)|109|110)|(4:25|(1:27)(1:152)|109|110)|28|132|29|109|110) */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b4, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00b9, code lost:
    
        if (r8.getMessage() == null) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00bd, code lost:
    
        r8.printStackTrace();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0134 A[Catch: IOException -> 0x0130, TryCatch #9 {IOException -> 0x0130, blocks: (B:96:0x012c, B:100:0x0134, B:102:0x0139), top: B:134:0x012c }] */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0139 A[Catch: IOException -> 0x0130, TRY_LEAVE, TryCatch #9 {IOException -> 0x0130, blocks: (B:96:0x012c, B:100:0x0134, B:102:0x0139), top: B:134:0x012c }] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0149 A[PHI: r9
  0x0149: PHI (r9v11 ??) = (r9v9 ??), (r9v10 ??), (r9v14 ??), (r9v14 ??), (r9v14 ??) binds: [B:86:0x0119, B:108:0x0147, B:33:0x00b9, B:35:0x00bd, B:29:0x00a9] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x015b A[Catch: IOException -> 0x0157, TryCatch #1 {IOException -> 0x0157, blocks: (B:114:0x0153, B:118:0x015b, B:120:0x0160), top: B:130:0x0153 }] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0160 A[Catch: IOException -> 0x0157, TRY_LEAVE, TryCatch #1 {IOException -> 0x0157, blocks: (B:114:0x0153, B:118:0x015b, B:120:0x0160), top: B:130:0x0153 }] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0153 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x012c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x00fe A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:153:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00f9 A[Catch: all -> 0x014d, TRY_LEAVE, TryCatch #11 {all -> 0x014d, blocks: (B:69:0x00f2, B:72:0x00f9, B:91:0x0120, B:94:0x0127), top: B:136:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0106 A[Catch: IOException -> 0x0102, TryCatch #15 {IOException -> 0x0102, blocks: (B:74:0x00fe, B:78:0x0106, B:80:0x010b), top: B:137:0x00fe }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x010b A[Catch: IOException -> 0x0102, TRY_LEAVE, TryCatch #15 {IOException -> 0x0102, blocks: (B:74:0x00fe, B:78:0x0106, B:80:0x010b), top: B:137:0x00fe }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0127 A[Catch: all -> 0x014d, TRY_LEAVE, TryCatch #11 {all -> 0x014d, blocks: (B:69:0x00f2, B:72:0x00f9, B:91:0x0120, B:94:0x0127), top: B:136:0x000c }] */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v21 */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r9v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11, types: [java.lang.Process] */
    /* JADX WARN: Type inference failed for: r9v14, types: [java.lang.Process] */
    /* JADX WARN: Type inference failed for: r9v16 */
    /* JADX WARN: Type inference failed for: r9v17 */
    /* JADX WARN: Type inference failed for: r9v18 */
    /* JADX WARN: Type inference failed for: r9v19 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v20 */
    /* JADX WARN: Type inference failed for: r9v21 */
    /* JADX WARN: Type inference failed for: r9v22 */
    /* JADX WARN: Type inference failed for: r9v23 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6 */
    /* JADX WARN: Type inference failed for: r9v7, types: [java.lang.Process] */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference failed for: r9v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static a a(String[] strArr, boolean z) throws Throwable {
        ?? r9;
        DataOutputStream dataOutputStream;
        ?? r5;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        BufferedReader bufferedReader3;
        ?? r92;
        BufferedReader bufferedReader4;
        int i;
        StringBuilder sb;
        StringBuilder sb2;
        a aVar = new a();
        if (strArr == null || strArr.length == 0) {
            return aVar;
        }
        BufferedReader bufferedReader5 = null;
        bufferedReader5 = null;
        DataOutputStream dataOutputStream2 = null;
        DataOutputStream dataOutputStream3 = null;
        try {
            try {
                String[] strArr2 = new String[1];
                strArr2[0] = z != 0 ? b : c;
                z = new ProcessBuilder(strArr2).start();
            } catch (Throwable th) {
                th = th;
                dataOutputStream = null;
            }
            try {
                dataOutputStream = new DataOutputStream(z.getOutputStream());
            } catch (IOException e2) {
                e = e2;
                bufferedReader = null;
                bufferedReader3 = null;
                z = z;
                if (e.getMessage() != null) {
                }
                if (dataOutputStream2 != null) {
                }
                if (bufferedReader != null) {
                }
                if (bufferedReader3 != null) {
                }
                if (z != 0) {
                }
                return aVar;
            } catch (Exception e3) {
                e = e3;
                bufferedReader = null;
                bufferedReader2 = null;
                z = z;
                if (e.getMessage() != null) {
                }
                if (dataOutputStream3 != null) {
                }
                if (bufferedReader != null) {
                }
                if (bufferedReader2 != null) {
                }
                if (z != 0) {
                }
                return aVar;
            } catch (Throwable th2) {
                th = th2;
                dataOutputStream = null;
                r9 = z;
                r5 = dataOutputStream;
                r92 = r9;
                if (dataOutputStream != null) {
                }
                if (bufferedReader5 != null) {
                }
                if (r5 != 0) {
                }
                if (r92 != 0) {
                }
            }
            try {
                for (String str : strArr) {
                    if (str != null) {
                        dataOutputStream.write(str.getBytes());
                        dataOutputStream.writeBytes("\n");
                        dataOutputStream.flush();
                    }
                }
                dataOutputStream.writeBytes(d);
                dataOutputStream.flush();
                aVar.a = z.waitFor();
                sb = new StringBuilder();
                sb2 = new StringBuilder();
                bufferedReader = new BufferedReader(new InputStreamReader(z.getInputStream()));
                try {
                    bufferedReader4 = new BufferedReader(new InputStreamReader(z.getErrorStream()));
                } catch (IOException e4) {
                    e = e4;
                    bufferedReader4 = null;
                } catch (Exception e5) {
                    e = e5;
                    bufferedReader4 = null;
                } catch (Throwable th3) {
                    th = th3;
                    bufferedReader4 = null;
                }
            } catch (IOException e6) {
                e = e6;
                bufferedReader = null;
                bufferedReader4 = null;
            } catch (Exception e7) {
                e = e7;
                bufferedReader = null;
                bufferedReader4 = null;
            } catch (Throwable th4) {
                th = th4;
                r5 = 0;
                r92 = z;
                if (dataOutputStream != null) {
                }
                if (bufferedReader5 != null) {
                }
                if (r5 != 0) {
                }
                if (r92 != 0) {
                }
            }
        } catch (IOException e8) {
            e = e8;
            z = 0;
        } catch (Exception e9) {
            e = e9;
            z = 0;
        } catch (Throwable th5) {
            th = th5;
            r9 = 0;
            dataOutputStream = null;
        }
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line + "\n");
            } catch (IOException e10) {
                e = e10;
                dataOutputStream2 = dataOutputStream;
                bufferedReader3 = bufferedReader4;
                z = z;
                if (e.getMessage() != null) {
                    e.printStackTrace();
                }
                if (dataOutputStream2 != null) {
                    try {
                        dataOutputStream2.close();
                    } catch (IOException e11) {
                        if (e11.getMessage() == null) {
                            e11.printStackTrace();
                        }
                        if (z != 0) {
                            z.destroy();
                        }
                        return aVar;
                    }
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedReader3 != null) {
                    bufferedReader3.close();
                }
                if (z != 0) {
                }
                return aVar;
            } catch (Exception e12) {
                e = e12;
                dataOutputStream3 = dataOutputStream;
                bufferedReader2 = bufferedReader4;
                z = z;
                if (e.getMessage() != null) {
                    e.printStackTrace();
                }
                if (dataOutputStream3 != null) {
                    try {
                        dataOutputStream3.close();
                    } catch (IOException e13) {
                        if (e13.getMessage() == null) {
                            e13.printStackTrace();
                        }
                        if (z != 0) {
                        }
                        return aVar;
                    }
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedReader2 != null) {
                    bufferedReader2.close();
                }
                if (z != 0) {
                }
                return aVar;
            } catch (Throwable th6) {
                th = th6;
                bufferedReader5 = bufferedReader;
                r5 = bufferedReader4;
                r92 = z;
                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e14) {
                        if (e14.getMessage() == null) {
                            e14.printStackTrace();
                        }
                        if (r92 != 0) {
                            throw th;
                        }
                        r92.destroy();
                        throw th;
                    }
                }
                if (bufferedReader5 != null) {
                    bufferedReader5.close();
                }
                if (r5 != 0) {
                    r5.close();
                }
                if (r92 != 0) {
                }
            }
            z.destroy();
            return aVar;
        }
        while (true) {
            String line2 = bufferedReader4.readLine();
            if (line2 == null) {
                break;
            }
            sb2.append(line2);
            z.destroy();
            return aVar;
        }
        aVar.c = sb.toString();
        aVar.b = sb2.toString();
        dataOutputStream.close();
        bufferedReader.close();
        bufferedReader4.close();
        z.destroy();
        return aVar;
    }
}
