package com.coralline.sea;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class f1 {
    public byte[] a;
    public byte[] b;

    public f1(String str, String str2) {
        this.a = null;
        this.b = null;
        if (str == null || str2 == null) {
            return;
        }
        this.a = (str.length() > 24 ? str.substring(0, 24) : str).getBytes();
        this.b = (str2.length() > 8 ? str2.substring(0, 8) : str2).getBytes();
    }

    public String a(String str, String str2) {
        Cipher cipher;
        byte[] bArrDoFinal;
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.a, "DESede");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(this.b);
        try {
            cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | Exception e) {
            e.printStackTrace();
            cipher = null;
        }
        try {
            cipher.init(1, secretKeySpec, ivParameterSpec);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | Exception e2) {
            e2.printStackTrace();
        }
        try {
            bArrDoFinal = cipher.doFinal(str.getBytes(str2));
        } catch (UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException | Exception e3) {
            e3.printStackTrace();
            bArrDoFinal = null;
        }
        return new String(t0.b(bArrDoFinal));
    }
}
