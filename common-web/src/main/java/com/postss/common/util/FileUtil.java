/**
 * 
 */
package com.postss.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.postss.common.constant.Constant;

/**
 * 导入导出文件工具类
 * @ClassName: FileUtil
 * @author sjw
 * @date 2016年7月27日 上午10:15:59
 */
public class FileUtil {

    private static Logger log = Logger.getLogger(FileUtil.class);

    /**
     * 导出文件
     * @param @param path 导出文件地址
     * @param @param bytes 文件数据
     * @return void 返回类型
     */
    public static void exportFile(File file, byte[] bytes) {
        OutputStream outputStream = null;
        BufferedOutputStream bis = null;

        try {
            outputStream = new FileOutputStream(file);
            bis = new BufferedOutputStream(outputStream);
            outputStream.write(bytes);
        } catch (Exception e) {
            if (e instanceof java.io.FileNotFoundException) {
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    try {
                        file.createNewFile();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                exportFile(file, bytes);
            } else {
                log.error("写出文件出错,原因为:" + e);
            }
        } finally {
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * 导入文件数据，编码为 ISO-8859-1
     * @param @param path 文件地址
     * @param @return 设定文件
     * @return String 返回类型
     */
    public static String importFile(File file) {
        try {
            return importFile(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 导入文件数据，编码为 ISO-8859-1
     * @param @param path 文件地址
     * @param @return 设定文件
     * @return String 返回类型
     */
    public static String importFile(MultipartFile file) {
        try {
            return importFile(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String importFile(InputStream inputStream) {
        BufferedInputStream bis = null;
        StringBuffer stringBuffer = null;

        try {
            bis = new BufferedInputStream(inputStream);
            int len = 0;
            byte[] b = new byte[1024];
            stringBuffer = new StringBuffer();
            while ((len = bis.read(b)) != -1) {
                stringBuffer.append(new String(b, 0, len, Constant.CHARSET.ISO_8859_1));
            }
        } catch (Exception e) {
            log.error("读取文件出错，原因为:" + e);
        } finally {
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(inputStream);
        }

        return stringBuffer.toString();
    }

    /**
     * 导入文本
     * @param @param path
     * @param @return 设定文件
     * @return List<String> 返回类型
     */
    public static List<String> importText(String path) {
        File file = new File(path);
        try {
            return importText(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> importText(InputStream inputStream) {
        Reader reader = null;
        BufferedReader br = null;
        List<String> list = null;
        try {
            reader = new InputStreamReader(inputStream);
            br = new BufferedReader(reader);
            list = new ArrayList<String>();
            String str = "";
            while ((str = br.readLine()) != null) {
                list.add(str);
            }
        } catch (Exception e) {
            log.error("读取文件出错，原因为:" + e);
        } finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(reader);
        }

        return list;
    }
}
