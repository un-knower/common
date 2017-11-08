package com.postss.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.postss.common.constant.Constant;

/**
 * @ClassName: ZipUtil
 * @author sjw
 * @date 2016年9月12日 上午10:44:06
 */
@SuppressWarnings("deprecation")
public class ZipUtil {

    /**
     * 根据url资源输出指定ZIP文件至html
     * @author sjw
     * @date 2016年9月12日 下午1:39:44
     * @param zipName 输出的文件名
     * @param picMap 文件url集合  key:资源地址 value:资源后缀名
     * @param response
     */
    public static void exportZipFromUrlToHtml(String zipName, Map<String, String> picMap,
            HttpServletResponse response) {
        InputStream inputStream = null;
        BufferedInputStream buffis = null;
        ZipOutputStream zipos = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + URLEncoder.encode(zipName + ".zip", Constant.CHARSET.UTF_8));
            os = response.getOutputStream();
            bos = new BufferedOutputStream(os);
            zipos = new ZipOutputStream(bos);

            if (null != picMap) {
                Iterator<Entry<String, String>> mapIterator = picMap.entrySet().iterator();
                while (mapIterator.hasNext()) {
                    Entry<String, String> mapEntry = mapIterator.next();
                    String returnString = WebResourceUtil.getURLResponse(mapEntry.getKey());
                    byte[] bytes = returnString.getBytes(Constant.CHARSET.ISO_8859_1);
                    inputStream = new ByteArrayInputStream(bytes);
                    byte[] bufs = new byte[1024];
                    ZipEntry zipEntry = new ZipEntry(
                            java.util.UUID.randomUUID().toString() + "." + mapEntry.getValue());
                    zipos.putNextEntry(zipEntry);
                    buffis = new BufferedInputStream(inputStream);
                    int len = 0;
                    while ((len = buffis.read(bufs)) != -1) {
                        zipos.write(bufs, 0, len);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(zipos);
            IOUtils.closeQuietly(buffis);
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(os);
        }
    }

    /**
     * 根据url资源输出指定ZIP文件至本地
     * @author sjw
     * @date 2016年9月12日 下午1:39:44
     * @param zipName 输出的文件名
     * @param picMap 文件url集合  key:资源地址 value:资源后缀名
     * @param response
     */
    public static void exportZipFromUrlToLocal(String zipName, String path, Map<String, String> picMap) {
        InputStream inputStream = null;
        BufferedInputStream buffis = null;
        ZipOutputStream zipos = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            File file = new File(path + "/" + zipName);
            os = new FileOutputStream(file);
            bos = new BufferedOutputStream(os);
            zipos = new ZipOutputStream(bos);

            if (null != picMap) {
                Iterator<Entry<String, String>> mapIterator = picMap.entrySet().iterator();
                while (mapIterator.hasNext()) {
                    Entry<String, String> mapEntry = mapIterator.next();
                    String returnString = WebResourceUtil.getURLResponse(mapEntry.getKey());
                    byte[] bytes = returnString.getBytes(Constant.CHARSET.ISO_8859_1);
                    inputStream = new ByteArrayInputStream(bytes);
                    byte[] bufs = new byte[1024];
                    ZipEntry zipEntry = new ZipEntry(
                            java.util.UUID.randomUUID().toString() + "." + mapEntry.getValue());
                    zipos.putNextEntry(zipEntry);
                    buffis = new BufferedInputStream(inputStream);
                    int len = 0;
                    while ((len = buffis.read(bufs)) != -1) {
                        zipos.write(bufs, 0, len);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(zipos);
            IOUtils.closeQuietly(buffis);
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(os);
        }
    }

    /**
     * 根据本地资源输出指定ZIP文件至html
     * @author sjw
     * @date 2016年9月12日 下午1:39:44
     * @param zipName 输出的文件名
     * @param fileUrlList 文件地址集合
     * @param response
     */
    public static void exportZipFromLocalToHtml(String zipName, List<String> filePathList,
            HttpServletResponse response) {
        InputStream inputStream = null;
        BufferedInputStream buffis = null;
        ZipOutputStream zipos = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            response.setCharacterEncoding(Constant.CHARSET.UTF_8);
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + URLEncoder.encode(zipName + ".zip", Constant.CHARSET.UTF_8));
            os = response.getOutputStream();
            bos = new BufferedOutputStream(os);
            zipos = new ZipOutputStream(bos);
            for (String filePath : filePathList) {
                File file = new File(filePath);
                inputStream = new FileInputStream(file);
                byte[] bufs = new byte[1024];
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipos.putNextEntry(zipEntry);
                buffis = new BufferedInputStream(inputStream);
                int len = 0;
                while ((len = buffis.read(bufs)) != -1) {
                    zipos.write(bufs, 0, len);
                }
                IOUtils.closeQuietly(buffis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(zipos);
            IOUtils.closeQuietly(buffis);
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(os);
        }
    }

    /**
     * 根据本地资源输出指定ZIP文件至本地
     * @author sjw
     * @date 2016年9月12日 下午1:39:44
     * @param zipName 输出的文件名
     * @param fileUrlList 文件地址集合
     * @param response
     */
    public static void exportZipFromLocalToLocal(String zipName, String path, List<String> filePathList) {
        InputStream inputStream = null;
        BufferedInputStream buffis = null;
        ZipOutputStream zipos = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            os = new FileOutputStream(new File(path + "/" + zipName));
            bos = new BufferedOutputStream(os);
            zipos = new ZipOutputStream(bos);
            for (String filePath : filePathList) {
                File file = new File(filePath);
                inputStream = new FileInputStream(file);
                byte[] bufs = new byte[1024];
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipos.putNextEntry(zipEntry);
                buffis = new BufferedInputStream(inputStream);
                int len = 0;
                while ((len = buffis.read(bufs)) != -1) {
                    zipos.write(bufs, 0, len);
                }
                buffis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(zipos);
            IOUtils.closeQuietly(buffis);
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(os);
        }
    }
}
