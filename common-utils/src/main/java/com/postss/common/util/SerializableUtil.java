package com.postss.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.io.IOUtils;

import com.postss.common.log.util.LoggerUtil;

/**
 * 序列化工具类
 * @className SerializableUtil
 * @author jwSun
 * @date 2016年11月11日 下午1:19:22
 * @version 1.0.0
 */
public class SerializableUtil {

    private static final org.slf4j.Logger log = LoggerUtil.getLogger(SerializableUtil.class);

    /**
     * 序列化对象
     * @date 2016年11月11日 下午1:19:39 
     * @param obj 要序列化的对象
     * @return 对象字节数组
     */
    public static byte[] serializable(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(obj);
            bytes = bos.toByteArray();
        } catch (Exception e) {
            log.error("serializable ERROR", e);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(bos);
        }
        return bytes;
    }

    /**
     * 反序列化对象
     * @date 2016年11月11日 下午1:19:39 
     * @param bytes 要反序列化的对象字节数组
     * @return 反序列化得到的对象
     */
    public static Object unSerializable(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream bos = null;
        ObjectInputStream os = null;
        try {
            bos = new ByteArrayInputStream(bytes);
            os = new ObjectInputStream(bos);
            obj = os.readObject();
        } catch (Exception e) {
            log.error("unSerializable ERROR", e);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(bos);
        }
        return obj;
    }

    /**
     * 获得已知对象类型的反序列化对象
     * @param bytes  要反序列化的对象字节数组
     * @param clazz 反序列化后强转类型
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T unSerializable(byte[] bytes, Class<T> clazz) {
        return (T) unSerializable(bytes);
    }

}
