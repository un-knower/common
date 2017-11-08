package com.postss.common.util;

/**
 * 比较工具类
 * @className ComparatorUtil
 * @author jwSun
 * @date 2017年6月30日 上午11:17:48
 * @version 1.0.0
 */
public class ComparatorUtil {

    /**
     * 是否相同
     * @param obj 比较类
     * @param comparator 比较类
     * @return
     */
    public static boolean equals(Object obj, Object comparator) {
        if (obj == comparator)
            return true;

        if (obj == null)
            return comparator.equals(obj);
        else
            return obj.equals(comparator);
    }

    public static boolean notNull(Object... obj) {
        if (obj == null) {
            return false;
        }
        Object[] compartorArray = obj;
        for (Object object : compartorArray) {
            if (object == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNull(Object... obj) {
        if (obj == null) {
            return true;
        }
        Object[] compartorArray = obj;
        for (Object object : compartorArray) {
            if (object != null) {
                return false;
            }
        }
        return true;
    }

}
