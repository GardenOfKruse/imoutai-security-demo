package com.coralline.sea;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecordingConfiguration;
import android.os.Build;
import com.coralline.sea.m5;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class b6 extends x6 {
    public static final String c = "microphone_usage";
    public boolean b;

    public b6() {
        super(c, 10);
        this.b = false;
    }

    public static String a(int i) {
        switch (i) {
            case 0:
                return "DEFAULT";
            case 1:
                return "MIC";
            case 2:
                return "VOICE_UPLINK";
            case 3:
                return "VOICE_DOWNLINK";
            case 4:
                return "VOICE_CALL";
            case 5:
                return "CAMCORDER";
            case m5.b.f /* 6 */:
                return "VOICE_RECOGNITION";
            case 7:
                return "VOICE_COMMUNICATION";
            case 8:
                return "REMOTE_SUBMIX";
            default:
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 24 && i == 9) {
                    return "UNPROCESSED";
                }
                if (i2 >= 23 && i == 1999) {
                    return "HOTWORD";
                }
                if (i2 >= 28 && i == 1998) {
                    return "RADIO_TUNER";
                }
                if (i2 >= 29 && i == 10) {
                    return "VOICE_PERFORMANCE";
                }
                return "UNKNOWN(" + i + ")";
        }
    }

    public static JSONArray a(AudioManager audioManager) {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                List<AudioRecordingConfiguration> activeRecordingConfigurations = audioManager.getActiveRecordingConfigurations();
                if (activeRecordingConfigurations.isEmpty()) {
                    return null;
                }
                JSONArray jSONArray = new JSONArray();
                for (AudioRecordingConfiguration audioRecordingConfiguration : activeRecordingConfigurations) {
                    JSONObject jSONObject = new JSONObject();
                    int clientAudioSource = audioRecordingConfiguration.getClientAudioSource();
                    if (clientAudioSource != 8) {
                        int clientAudioSessionId = audioRecordingConfiguration.getClientAudioSessionId();
                        boolean zIsClientSilenced = Build.VERSION.SDK_INT >= 29 ? audioRecordingConfiguration.isClientSilenced() : false;
                        AudioFormat clientFormat = audioRecordingConfiguration.getClientFormat();
                        jSONObject.put("audio_source", clientAudioSource);
                        jSONObject.put("audio_source_description", a(clientAudioSource));
                        jSONObject.put("session_id", clientAudioSessionId);
                        jSONObject.put("client_silenced", zIsClientSilenced);
                        jSONObject.put("sample_rate", clientFormat.getSampleRate());
                        jSONObject.put("channel_count", clientFormat.getChannelCount());
                        jSONObject.put("encoding", clientFormat.getEncoding());
                        jSONObject.put("config_string", audioRecordingConfiguration.toString());
                        jSONArray.put(jSONObject);
                    }
                }
                return jSONArray;
            }
        } catch (Exception e) {
            e.toString();
        }
        return null;
    }

    public static JSONObject a() {
        JSONArray jSONArrayA;
        Context context = n3.a().a;
        JSONObject jSONObject = null;
        if (context == null) {
            return null;
        }
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        int mode = audioManager.getMode();
        try {
        } catch (Exception e) {
            e = e;
        }
        if (3 != mode && 2 != mode) {
            if (z1.a(c).optBoolean("active_audio_recording_enabled", true) && (jSONArrayA = a(audioManager)) != null) {
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("microphone_in_use", true);
                    jSONObject2.put("microphone_describe", "检测到活动的音频录制配置，麦克风可能正在被使用");
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("recording_configurations", jSONArrayA);
                    jSONObject3.put("active_recording_count", jSONArrayA.length());
                    jSONObject2.put("detail", jSONObject3);
                    return jSONObject2;
                } catch (Exception e2) {
                    e = e2;
                    jSONObject = jSONObject2;
                }
            }
            return jSONObject;
        }
        JSONObject jSONObject4 = new JSONObject();
        try {
            jSONObject4.put("microphone_describe", "麦克风正被调用，在电话通话中");
            jSONObject4.put("microphone_in_use", true);
            jSONObject4.put("microphone_mode", mode);
            return jSONObject4;
        } catch (Exception e3) {
            jSONObject = jSONObject4;
            e = e3;
        }
        e.toString();
        return jSONObject;
    }

    @Override // com.coralline.sea.checkers.Checker
    public void check() {
        JSONObject jSONObjectA;
        if (this.b || (jSONObjectA = a()) == null) {
            return;
        }
        push(e2.b, c, jSONObjectA.toString());
        this.b = true;
    }
}
