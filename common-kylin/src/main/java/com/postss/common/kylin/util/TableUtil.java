package com.postss.common.kylin.util;

/**
 * kylin 表格相关操作工具类
 * @className TableUtil
 * @author jwSun
 * @date 2017年8月22日 下午7:25:54
 * @version 1.0.0
 */
public class TableUtil {

    /**
     * 获得真实表名，如DEFAULT.TABLE -> TABLE
     * @param tableName 原始表名
     * @return
     */
    public static String getTableName(String tableName) {
        int pointIndex = tableName.lastIndexOf(".");
        if (pointIndex == -1) {
            return tableName;
        } else {
            return tableName.substring(pointIndex + 1);
        }
    }

}
