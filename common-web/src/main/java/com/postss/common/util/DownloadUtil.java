package com.postss.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * 输出下载文件工具类
 * @className DownloadUtil
 * @author jwSun
 * @date 2017年6月30日 上午11:19:14
 * @version 1.0.0
 */
public class DownloadUtil {

    /**
     * 使用文件输出文件
     * @date 2016年9月29日 下午5:03:28 
     * @param fileName 文件名
     * @param file 文件对象
     * @param response
     */
    public static void download(String fileName, File file, HttpServletResponse response) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            download(fileName, inputStream, null, response);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用文件输出文件
     * @date 2016年9月30日 下午2:54:05 
     * @param fileName 文件名
     * @param file 文件对象
     * @param dataMap 传回的参数(存放header中)
     * @param response
     */
    public static void download(String fileName, File file, Map<String, String> dataMap, HttpServletResponse response) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            download(fileName, inputStream, dataMap, response);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用byte[]输出文件
     * @date 2016年8月15日 下午4:55:48
     * @param fileName 文件名
     * @param bytes byte[]对象
     * @param response
     */
    public static void download(String fileName, byte[] bytes, HttpServletResponse response) {
        download(fileName, new ByteArrayInputStream(bytes), null, response);
    }

    /**
     * 使用byte[]输出文件
     * @date 2016年8月15日 下午4:55:48
     * @param fileName 文件名
     * @param bytes byte[]对象
     * @param dataMap 传回的参数(存放header中)
     * @param response
     */
    public static void download(String fileName, byte[] bytes, Map<String, String> dataMap,
            HttpServletResponse response) {
        download(fileName, new ByteArrayInputStream(bytes), dataMap, response);
    }

    /**
     * 使用inputStream输出下载文件
     * @date 2016年8月15日 下午4:55:48
     * @param fileName 文件名
     * @param inputStream inputStream对象
     * @param dataMap 传回的参数(存放header中)
     * @param response
     */
    public static void download(String fileName, InputStream inputStream, Map<String, String> dataMap,
            HttpServletResponse response) {
        OutputStream outputStream = null;
        BufferedInputStream bis = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf-8"));
            //存放需要传递参数
            if (null != dataMap) {
                Iterator<Entry<String, String>> mapIterator = dataMap.entrySet().iterator();
                while (mapIterator.hasNext()) {
                    Entry<String, String> mapEntry = mapIterator.next();
                    response.setHeader(URLEncoder.encode(mapEntry.getKey(), "utf-8"),
                            URLEncoder.encode(mapEntry.getValue(), "utf-8"));
                }
            }
            bis = new BufferedInputStream(inputStream);
            outputStream = response.getOutputStream();
            byte[] b = new byte[1024];
            int length;
            while ((length = bis.read(b)) > 0) {
                outputStream.write(b, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(inputStream);
        }
    }

}
