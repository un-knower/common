package com.postss.common.util;

import org.apache.commons.lang.SystemUtils;

/**
 * 系统参数工具类
 * @className SystemUtil
 * @author jwSun
 * @date 2017年6月30日 下午12:01:10
 * @version 1.0.0
 */
public class SystemUtil extends SystemUtils {

    /**
     * 获得系统参数
     * @param property
     * @return 参数值
     */
    public static String getSystemProperty(String property) {
        try {
            return System.getProperty(property);
        } catch (SecurityException ex) {
            System.err.println("Caught a SecurityException reading the system property '" + property
                    + "'; the SystemUtils property value will default to null.");
            return null;
        }
    }

}
