package com.postss.common.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密工具类
 * @className AesUtil
 * @author jwSun
 * @date 2017年6月30日 上午11:14:02
 * @version 1.0.0
 */
public class AesUtil {

    /** 
     * 加密
     * @param rawKey 密钥 
     * @param clearPwd 明文字符串 
     * @return 密文字节数组 
     */
    public static String encrypt(String key, String content) {
        try {
            byte[] rawKey = getRawKey(key.getBytes());
            SecretKeySpec secretKeySpec = new SecretKeySpec(rawKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encypted = cipher.doFinal(content.getBytes());
            return EncryptUtil.encodeBase64(encypted);
        } catch (Exception e) {
            return "";
        }
    }

    /** 
     * 解密
     * @param content 密文字节数组 
     * @param key 密钥 
     * @return 解密后的字符串 
     */
    public static String decrypt(String key, String content) {
        try {
            byte[] rawKey = getRawKey(key.getBytes());
            byte[] encrypted = EncryptUtil.decodeBase64(content);
            SecretKeySpec secretKeySpec = new SecretKeySpec(rawKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted);
        } catch (Exception e) {
            return "";
        }
    }

    /** 
     * @param seed种子数据 
     * @return 密钥数据 
     */
    private static byte[] getRawKey(byte[] seed) {
        byte[] rawKey = null;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed);
            // AES加密数据块分组长度必须为128比特，密钥长度可以是128比特、192比特、256比特中的任意一个  
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            rawKey = secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
        }
        return rawKey;
    }

    public static void main(String[] args) {
        System.out.println(encrypt("root", "root"));
        System.out.println(decrypt("root", "KutR2ys5pJycitP2BfUWCA=="));
    }
}
