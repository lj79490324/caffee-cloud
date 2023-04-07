package com.coffee.common.core.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA加密解密工具
 * @author rabit
 * @version v1.0
 * @date 2022/9/5 14:43
 */
public class SHAUtil {
    public static final String KEY_SHA = "SHA";
    public static final String ALGORITHM = "SHA-256";

    /***
     * SHA加密（比MD5更安全）
     * @param data 加密的字节码数据
     * @return 加密后的结果
     * @throws Exception 加密过程中会抛出异常
     */
    public static byte[] encryptSHA(byte[] data) throws Exception{
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);
        return sha.digest();
    }


    /**
     * SHA加密
     * @param content 需要加密字符串
     * @return 加密后的结果
     */
    public static String SHAEncrypt(String content) {
        try {
            MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
            byte[] sha_byte = sha.digest(content.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (byte b : sha_byte) {
                //将其中的每个字节转成十六进制字符串：byte类型的数据最高位是符号位，通过和0xff进行与操作，转换为int类型的正整数。
                String toHexString = Integer.toHexString(b & 0xff);
                hexValue.append(toHexString.length() == 1 ? "0" + toHexString : toHexString);
            }
            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * SHA-256加密
     * @param sourceStr 加密字符串
     * @return 加密后的结果
     */
    public static String SHA256Encrypt(String sourceStr) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (null != md) {
            md.update(sourceStr.getBytes());
            String digestStr = getDigestStr(md.digest());
            return digestStr;
        }

        return null;
    }


    /**
     * 将加密后字节码转换为字符串
     * @param origBytes 字节码
     * @return 返回加密后的对象
     */
    private static String getDigestStr(byte[] origBytes) {
        String tempStr = null;
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < origBytes.length; i++) {
            tempStr = Integer.toHexString(origBytes[i] & 0xff);
            if (tempStr.length() == 1) {
                stb.append("0");
            }
            stb.append(tempStr);

        }
        return stb.toString();
    }

}
