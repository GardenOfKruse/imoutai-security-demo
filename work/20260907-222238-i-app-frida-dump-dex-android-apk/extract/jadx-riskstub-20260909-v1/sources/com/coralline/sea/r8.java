package com.coralline.sea;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.coralline.sea.r1;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract\runtime-code-files-25103-20260909-raw\RiskStub-a.dex */
public class r8 {
    public static final boolean q = true;
    public static final String r = "SensorData";
    public static final int s = 2;
    public static final int t = 8;
    public static final int u = 3000;
    public static r8 v;
    public SensorManager a;
    public d b;
    public f c;
    public c d;
    public g e;
    public e f;
    public b g;
    public h h;
    public boolean i = false;
    public Sensor j;
    public Sensor k;
    public Sensor l;
    public Sensor m;
    public Sensor n;
    public Sensor o;
    public Sensor p;

    public class b implements SensorEventListener {
        public LinkedList<JSONObject> a;

        public b() {
            this.a = new LinkedList<>();
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == 10) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("x", sensorEvent.values[0]);
                    jSONObject.put("y", sensorEvent.values[1]);
                    jSONObject.put("z", sensorEvent.values[2]);
                    jSONObject.put(r1.k.e, System.currentTimeMillis());
                    this.a.add(jSONObject);
                    if (this.a.size() >= 8) {
                        this.a.removeFirst();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public class c implements SensorEventListener {
        public LinkedList<JSONObject> a;

        public c() {
            this.a = new LinkedList<>();
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == 4) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("x", sensorEvent.values[0]);
                    jSONObject.put("y", sensorEvent.values[1]);
                    jSONObject.put("z", sensorEvent.values[2]);
                    jSONObject.put(r1.k.e, System.currentTimeMillis());
                    this.a.add(jSONObject);
                    if (this.a.size() >= 8) {
                        this.a.removeFirst();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public class d implements SensorEventListener {
        public LinkedList<JSONObject> a;

        public d() {
            this.a = new LinkedList<>();
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == 5) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("light_lux", sensorEvent.values[0]);
                    jSONObject.put(r1.k.e, System.currentTimeMillis());
                    this.a.add(jSONObject);
                    if (this.a.size() >= 8) {
                        this.a.removeFirst();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public class e implements SensorEventListener {
        public LinkedList<JSONObject> a;

        public e() {
            this.a = new LinkedList<>();
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == 2) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("x", sensorEvent.values[0]);
                    jSONObject.put("y", sensorEvent.values[1]);
                    jSONObject.put("z", sensorEvent.values[2]);
                    jSONObject.put(r1.k.e, System.currentTimeMillis());
                    this.a.add(jSONObject);
                    if (this.a.size() >= 8) {
                        this.a.removeFirst();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public class f implements SensorEventListener {
        public LinkedList<JSONObject> a;

        public f() {
            this.a = new LinkedList<>();
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == 3) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("x", sensorEvent.values[0]);
                    jSONObject.put("y", sensorEvent.values[1]);
                    jSONObject.put("z", sensorEvent.values[2]);
                    jSONObject.put(r1.k.e, System.currentTimeMillis());
                    this.a.add(jSONObject);
                    if (this.a.size() >= 8) {
                        this.a.removeFirst();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public class g implements SensorEventListener {
        public LinkedList<JSONObject> a;

        public g() {
            this.a = new LinkedList<>();
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == 6) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("pressure", sensorEvent.values[0]);
                    jSONObject.put(r1.k.e, System.currentTimeMillis());
                    this.a.add(jSONObject);
                    if (this.a.size() >= 8) {
                        this.a.removeFirst();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public class h implements SensorEventListener {
        public LinkedList<JSONObject> a;

        public h() {
            this.a = new LinkedList<>();
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == 8) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("proximity", sensorEvent.values[0]);
                    jSONObject.put(r1.k.e, System.currentTimeMillis());
                    this.a.add(jSONObject);
                    if (this.a.size() >= 8) {
                        this.a.removeFirst();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public static r8 c() {
        if (v == null) {
            v = new r8();
        }
        return v;
    }

    public JSONArray a() {
        JSONArray jSONArray = new JSONArray();
        b bVar = this.g;
        if (bVar == null) {
            return jSONArray;
        }
        this.a.unregisterListener(bVar, this.o);
        if (this.g.a.size() == 0) {
            return jSONArray;
        }
        int size = this.g.a.size();
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (int i = size - 1; i >= 0; i--) {
            try {
                JSONObject jSONObject = this.g.a.get(i);
                if (jCurrentTimeMillis - jSONObject.getLong(r1.k.e) > 3000) {
                    break;
                }
                jSONArray.put(jSONObject);
            } catch (Exception e2) {
            }
        }
        return jSONArray;
    }

    public void a(Context context) {
        if (this.i) {
            return;
        }
        this.i = true;
        SensorManager sensorManager = (SensorManager) context.getApplicationContext().getSystemService("sensor");
        this.a = sensorManager;
        this.j = sensorManager.getDefaultSensor(5);
        this.k = this.a.getDefaultSensor(3);
        this.l = this.a.getDefaultSensor(4);
        this.m = this.a.getDefaultSensor(6);
        this.n = this.a.getDefaultSensor(2);
        this.o = this.a.getDefaultSensor(10);
        this.p = this.a.getDefaultSensor(8);
        if (this.j != null) {
            d dVar = new d();
            this.b = dVar;
            this.a.registerListener(dVar, this.j, 3);
        }
        if (this.k != null) {
            f fVar = new f();
            this.c = fVar;
            this.a.registerListener(fVar, this.k, 3);
        }
        if (this.l != null) {
            c cVar = new c();
            this.d = cVar;
            this.a.registerListener(cVar, this.l, 3);
        }
        if (this.m != null) {
            g gVar = new g();
            this.e = gVar;
            this.a.registerListener(gVar, this.m, 3);
        }
        if (this.n != null) {
            e eVar = new e();
            this.f = eVar;
            this.a.registerListener(eVar, this.n, 3);
        }
        if (this.o != null) {
            b bVar = new b();
            this.g = bVar;
            this.a.registerListener(bVar, this.o, 3);
        }
        if (this.p != null) {
            h hVar = new h();
            this.h = hVar;
            this.a.registerListener(hVar, this.p, 3);
        }
    }

    public JSONArray b() {
        JSONArray jSONArray = new JSONArray();
        c cVar = this.d;
        if (cVar == null) {
            return jSONArray;
        }
        this.a.unregisterListener(cVar, this.l);
        if (this.d.a.size() == 0) {
            return jSONArray;
        }
        int size = this.d.a.size();
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (int i = size - 1; i >= 0; i--) {
            try {
                JSONObject jSONObject = this.d.a.get(i);
                if (jCurrentTimeMillis - jSONObject.getLong(r1.k.e) > 3000) {
                    break;
                }
                jSONArray.put(jSONObject);
            } catch (Exception e2) {
            }
        }
        return jSONArray;
    }

    public JSONArray d() {
        JSONArray jSONArray = new JSONArray();
        d dVar = this.b;
        if (dVar == null) {
            return jSONArray;
        }
        this.a.unregisterListener(dVar, this.j);
        if (this.b.a.size() == 0) {
            return jSONArray;
        }
        int size = this.b.a.size();
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (int i = size - 1; i >= 0; i--) {
            try {
                JSONObject jSONObject = this.b.a.get(i);
                if (jCurrentTimeMillis - jSONObject.getLong(r1.k.e) > 3000) {
                    break;
                }
                jSONArray.put(jSONObject);
            } catch (Exception e2) {
            }
        }
        return jSONArray;
    }

    public JSONArray e() {
        JSONArray jSONArray = new JSONArray();
        e eVar = this.f;
        if (eVar == null) {
            return jSONArray;
        }
        this.a.unregisterListener(eVar, this.n);
        if (this.f.a.size() == 0) {
            return jSONArray;
        }
        int size = this.f.a.size();
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (int i = size - 1; i >= 0; i--) {
            try {
                JSONObject jSONObject = this.f.a.get(i);
                if (jCurrentTimeMillis - jSONObject.getLong(r1.k.e) > 3000) {
                    break;
                }
                jSONArray.put(jSONObject);
            } catch (Exception e2) {
            }
        }
        return jSONArray;
    }

    public JSONArray f() {
        JSONArray jSONArray = new JSONArray();
        f fVar = this.c;
        if (fVar == null) {
            return jSONArray;
        }
        this.a.unregisterListener(fVar, this.k);
        if (this.c.a.size() == 0) {
            return jSONArray;
        }
        int size = this.c.a.size();
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (int i = size - 1; i >= 0; i--) {
            try {
                JSONObject jSONObject = this.c.a.get(i);
                if (jCurrentTimeMillis - jSONObject.getLong(r1.k.e) > 3000) {
                    break;
                }
                jSONArray.put(jSONObject);
            } catch (Exception e2) {
            }
        }
        return jSONArray;
    }

    public JSONArray g() {
        JSONArray jSONArray = new JSONArray();
        g gVar = this.e;
        if (gVar == null) {
            return jSONArray;
        }
        this.a.unregisterListener(gVar, this.m);
        if (this.e.a.size() == 0) {
            return jSONArray;
        }
        int size = this.e.a.size();
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (int i = size - 1; i >= 0; i--) {
            try {
                JSONObject jSONObject = this.e.a.get(i);
                if (jCurrentTimeMillis - jSONObject.getLong(r1.k.e) > 3000) {
                    break;
                }
                jSONArray.put(jSONObject);
            } catch (Exception e2) {
            }
        }
        return jSONArray;
    }

    public JSONArray h() {
        JSONArray jSONArray = new JSONArray();
        h hVar = this.h;
        if (hVar == null) {
            return jSONArray;
        }
        this.a.unregisterListener(hVar, this.p);
        if (this.h.a.size() == 0) {
            return jSONArray;
        }
        int size = this.h.a.size();
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (int i = size - 1; i >= 0; i--) {
            try {
                JSONObject jSONObject = this.h.a.get(i);
                if (jCurrentTimeMillis - jSONObject.getLong(r1.k.e) > 3000) {
                    break;
                }
                jSONArray.put(jSONObject);
            } catch (Exception e2) {
            }
        }
        return jSONArray;
    }

    public void i() {
        SensorManager sensorManager;
        if (!this.i || (sensorManager = this.a) == null) {
            return;
        }
        this.i = false;
        sensorManager.unregisterListener(this.b);
    }
}
