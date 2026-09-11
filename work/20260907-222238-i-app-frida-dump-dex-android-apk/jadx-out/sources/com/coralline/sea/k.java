package com.coralline.sea;

import android.os.Build;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class k {
    public static String a() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("主板：" + Build.BOARD);
        stringBuffer.append("\n系统启动程序版本号：" + Build.BOOTLOADER);
        stringBuffer.append("\n系统定制商：" + Build.BRAND);
        stringBuffer.append("\ncpu指令集：" + Build.CPU_ABI);
        stringBuffer.append("\ncpu指令集2：" + Build.CPU_ABI2);
        stringBuffer.append("\n设置参数：" + Build.DEVICE);
        stringBuffer.append("\n显示屏参数：" + Build.DISPLAY);
        stringBuffer.append("\n无线电固件版本：" + Build.getRadioVersion());
        stringBuffer.append("\n硬件识别码：" + Build.FINGERPRINT);
        stringBuffer.append("\n硬件名称：" + Build.HARDWARE);
        stringBuffer.append("\nHOST:" + Build.HOST);
        stringBuffer.append("\n修订版本列表：" + Build.ID);
        stringBuffer.append("\n硬件制造商：" + Build.MANUFACTURER);
        stringBuffer.append("\n版本：" + Build.MODEL);
        stringBuffer.append("\n硬件序列号：" + Build.SERIAL);
        stringBuffer.append("\n手机制造商：" + Build.PRODUCT);
        stringBuffer.append("\n描述Build的标签：" + Build.TAGS);
        stringBuffer.append("\nTIME:" + Build.TIME);
        stringBuffer.append("\nbuilder类型：" + Build.TYPE);
        stringBuffer.append("\nUSER:" + Build.USER);
        return stringBuffer.toString();
    }
}
