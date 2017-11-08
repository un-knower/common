package com.postss.common.util;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.IOUtils;

import com.postss.common.enums.CharsetEnum;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;

public class ClassPathDataUtil {

    private static Map<String, String> dataCache = new ConcurrentHashMap<>();

    private static Logger log = LoggerUtil.getLogger(ClassPathDataUtil.class);

    public static String loadData(String classpath) {
        InputStream stream = null;
        try {
            String classPtahReal = classpath;
            for (;;) {
                if (classPtahReal.indexOf(".") != classPtahReal.lastIndexOf(".")) {
                    classPtahReal = classPtahReal.replaceFirst("\\.", "/");
                } else {
                    break;
                }
            }
            if (dataCache.containsKey(classPtahReal)) {
                return dataCache.get(classPtahReal);
            }
            String filepath = classPtahReal;
            stream = ClassPathDataUtil.class.getClassLoader().getResourceAsStream(filepath);
            int len = 0;
            byte[] b = new byte[1024];
            StringBuffer stringBuffer = new StringBuffer();
            while ((len = stream.read(b)) != -1) {
                stringBuffer.append(new String(b, 0, len, CharsetEnum.UTF_8.getCharset()));
            }
            String value = StringUtil.format(stringBuffer.toString());
            dataCache.put(classPtahReal, value);
            return value;
        } catch (Exception e) {
            log.error("", e);
            return "";
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }
}
