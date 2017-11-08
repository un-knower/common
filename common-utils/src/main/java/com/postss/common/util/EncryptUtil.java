/**
 * 
 */
package com.postss.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.xml.bind.DatatypeConverter;

import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;

/**
 * 加密类MD5/BASE64/GZIP
 * @ClassName: EncryptUtil
 * @author jwSun
 * @date 2016年7月27日 上午9:56:43
 */
public class EncryptUtil {

    private static Logger log = LoggerUtil.getLogger(EncryptUtil.class);

    /*** 
     * Base64 加密
     */
    public static String encodeBase64(byte[] input) {
        String encodedString = DatatypeConverter.printBase64Binary(input);
        return encodedString;
    }

    /*** 
     * Base64 安全加密
     */
    public static String encodeBase64Safe(byte[] input) {
        String encodedString = DatatypeConverter.printBase64Binary(input);
        encodedString = encodedString.replace('+', '-').replace('/', '_');
        return encodedString;
    }

    /*** 
     * Base64 解密
     */
    public static byte[] decodeBase64(String input) {
        byte[] bytes = DatatypeConverter.parseBase64Binary(input);
        return bytes;
    }

    /***
     * 压缩GZip
     */
    public static byte[] gZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 解压GZip
     */
    public static byte[] unGZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            gzip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /**
     * MD5加密
     * @param 要加密的文本(utf-8编码)
     * @return 加密后的文本
     */
    public static String md5(String plainText) {
        String result = null;
        try {
            result = StringUtil.bytes2HexString(
                    MessageDigest.getInstance("md5").digest(plainText.getBytes(SystemUtil.FILE_ENCODING)));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return result;
    }

    /**
     * MD5加密
     * @param 要加密的文本(utf-8编码)
     * @return 加密后的文本
     */
    public static String sha(String plainText) {
        String result = null;
        try {
            result = StringUtil.bytes2HexString(
                    MessageDigest.getInstance("sha-1").digest(plainText.getBytes(SystemUtil.FILE_ENCODING)));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return result;
    }

    /**
     * 默认加密方法
     * @author jwSun
     * @date 2017年5月15日 上午10:14:29
     * @param plainText
     * @return
     */
    public static String encode(String plainText) {
        return md5(plainText);
    }
}
