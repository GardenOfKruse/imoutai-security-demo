package com.coralline.sea;

/* JADX INFO: loaded from: assets/RiskStub.dex */
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
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.coralline.sea.x1.a a(java.lang.String[] r8, boolean r9) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 373
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.x1.a(java.lang.String[], boolean):com.coralline.sea.x1$a");
    }
}
