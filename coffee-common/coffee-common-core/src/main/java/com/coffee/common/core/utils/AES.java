package com.coffee.common.core.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Aes加密解密工具
 *
 * @author rabit
 * @version v1.0
 * @date 2022/9/5 15:22
 */
public class AES {
    // 加密
    public static String Encrypt(String sSrc, String sKey) {
        try {
            byte[] raw = getKey(sKey);
            assert raw != null;
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(encrypted));//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) {
        try {
            byte[] raw = getKey(sKey);
            assert raw != null;
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] encrypted1 = Base64.getDecoder().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original, StandardCharsets.UTF_8);
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static byte[] getKey(String sKey) {
        // 判断Key是否正确
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        return sKey.getBytes(StandardCharsets.UTF_8);
    }
}
