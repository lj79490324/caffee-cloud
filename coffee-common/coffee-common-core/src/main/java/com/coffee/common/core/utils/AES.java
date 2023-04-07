package com.coffee.common.core.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.kerberos.EncryptionKey;
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
    /**
     * Aes加密，先加密一次，然后在base64加密
     * @param sSrc 需要加密的原字符串
     * @param sKey 加密的key
     * @return 加密后的结果
     */
    public static String Encrypt(String sSrc, String sKey) {
        try {
            byte[] raw = getKey(sKey);
            assert raw != null;
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));
            //此处使用BASE64做转码功能，同时能起到2次加密的作用。
            return new String(Base64.getEncoder().encode(encrypted));
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * Aes解密,先base64解密，然后在进行解密
     * @param sSrc 加密后的字符串
     * @param sKey AES使用的key
     * @return 解密后的字符串
     */
    public static String Decrypt(String sSrc, String sKey) {
        try {
            byte[] raw = getKey(sKey);
            assert raw != null;
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            //先用base64解密
            byte[] encrypted1 = Base64.getDecoder().decode(sSrc);
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
            throw new RuntimeException("AES的Key为空null");
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            throw new RuntimeException("AES的Key长度不是16位");
        }
        return sKey.getBytes(StandardCharsets.UTF_8);
    }
}
