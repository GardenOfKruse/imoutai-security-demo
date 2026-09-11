package com.coralline.sea;

import android.os.AsyncTask;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class t9 {
    public static final Pattern a = Pattern.compile("PING (\\S+) [(]?([0-9.]+)[)]?", 2);
    public static final Pattern b = Pattern.compile("[(]?([0-9.]+)[)]?: icmp_seq=", 2);

    public class a extends AsyncTask<Void, Void, JSONObject> {
        public final /* synthetic */ String a;
        public final /* synthetic */ b b;

        public a(String str, b bVar) {
            this.a = str;
            this.b = bVar;
        }

        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public JSONObject doInBackground(Void... voidArr) {
            return t9.a(this.a);
        }

        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(JSONObject jSONObject) {
            this.b.a(jSONObject);
        }
    }

    public interface b {
        void a(JSONObject jSONObject);
    }

    public static JSONObject a(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObjectA = a(str, 64);
            Object objOptString = jSONObjectA.optString("targetName", c7.c);
            String strOptString = jSONObjectA.optString("targetIp", c7.c);
            jSONObject.put("target_name", objOptString);
            jSONObject.put("target_ip", strOptString);
            boolean zEqualsIgnoreCase = strOptString.equalsIgnoreCase(jSONObjectA.optString("replyIp", "Timeout"));
            for (int i = 1; i <= 20; i++) {
                JSONObject jSONObjectA2 = a(str, i);
                String strOptString2 = jSONObjectA2.optString("targetIp", c7.c);
                String strOptString3 = jSONObjectA2.optString("replyIp", "Timeout");
                if (!strOptString3.equals("Timeout")) {
                    jSONArray.put(strOptString3);
                }
                if (strOptString3.equalsIgnoreCase(strOptString2)) {
                    break;
                }
                if (!strOptString3.matches("[0-9.]+") && !zEqualsIgnoreCase) {
                    break;
                }
            }
            jSONObject.put("ip", jSONArray);
            return jSONObject;
        } catch (Exception e) {
            return jSONObject;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b4  */
    /* JADX WARN: Type inference failed for: r1v13, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.ProcessBuilder] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6, types: [java.io.Closeable] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static org.json.JSONObject a(java.lang.String r8, int r9) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 242
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coralline.sea.t9.a(java.lang.String, int):org.json.JSONObject");
    }

    public static void a(String str, b bVar) {
        new a(str, bVar).execute(new Void[0]);
    }
}
