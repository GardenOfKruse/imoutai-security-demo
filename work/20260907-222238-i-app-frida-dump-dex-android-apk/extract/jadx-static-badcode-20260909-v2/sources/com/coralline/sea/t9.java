package com.coralline.sea;

import android.os.AsyncTask;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.util.Objects;
import java.util.regex.Matcher;
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
    */
    public static JSONObject a(String str, int i) throws Exception {
        Exception e;
        BufferedInputStream bufferedInputStream;
        ?? r1;
        Matcher matcher;
        byte[] bArr;
        JSONObject jSONObject = new JSONObject();
        if (TextUtils.isEmpty(str)) {
            throw new Exception("Traceroute.ping()->Illegal host! host=" + str);
        }
        String[] strArr = {"ping", "-c 1", "-w 2", p1.a("-t ", i), str};
        ?? processBuilder = new ProcessBuilder(strArr);
        processBuilder.redirectErrorStream(true);
        Objects.toString(processBuilder.command());
        Process processStart = processBuilder.start();
        ?? r9 = 0;
        r9 = 0;
        String string = null;
        try {
            try {
                processBuilder = new ByteArrayOutputStream();
            } catch (Throwable th) {
                th = th;
                r9 = strArr;
            }
            try {
                bufferedInputStream = new BufferedInputStream(processStart.getInputStream());
                try {
                    bArr = new byte[1024];
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    r1 = processBuilder;
                }
            } catch (Exception e3) {
                e = e3;
                bufferedInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                p9.a((Closeable) r9);
                p9.a((Closeable) processBuilder);
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            processBuilder = 0;
            bufferedInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            processBuilder = 0;
        }
        while (true) {
            int i2 = bufferedInputStream.read(bArr);
            if (i2 <= 0) {
                break;
            }
            processBuilder.write(bArr, 0, i2);
            p9.a(bufferedInputStream);
            p9.a((Closeable) r1);
            int iWaitFor = processStart.waitFor();
            matcher = a.matcher(string);
            if (matcher.find()) {
                throw new Exception("Traceroute.ping()->Unable to match target host! exitValue=[" + iWaitFor + "]; msg=[" + string + "]");
            }
            String strGroup = matcher.group(1);
            String strGroup2 = matcher.group(2);
            Matcher matcher2 = b.matcher(string);
            String strGroup3 = matcher2.find() ? matcher2.group(1) : "Timeout";
            jSONObject.put("targetName", strGroup);
            jSONObject.put("targetIp", strGroup2);
            jSONObject.put("replyIp", strGroup3);
            return jSONObject;
        }
        string = processBuilder.toString(s0.f);
        r1 = processBuilder;
        p9.a(bufferedInputStream);
        p9.a((Closeable) r1);
        int iWaitFor2 = processStart.waitFor();
        matcher = a.matcher(string);
        if (matcher.find()) {
        }
    }

    public static void a(String str, b bVar) {
        new a(str, bVar).execute(new Void[0]);
    }
}
