package com.postss.common.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 集合工具类
 * @ClassName: CollectionUtil
 * @author sjw
 * @date 2016年9月12日 上午10:44:06
 */
public class CollectionUtil {

    @SuppressWarnings("unchecked")
    public static final Map<String, Object> EMPTY_MAP = Collections.EMPTY_MAP;

    /**
     * 判断是否为空
     * @param collection 集合
     * @return Boolean
     */
    public static <T> Boolean isEmpty(Collection<T> collection) {
        if (collection == null || collection.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 集合内容过滤
     * @date 2017年5月27日 下午3:52:06
     * @param beConvertList 未过滤集合
     * @return
     */
    public static <T> CollectionDataFilter<T> createFilter(List<T> beConvertList) {
        return new CollectionDataFilter<T>(beConvertList);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        if (map == null || map.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

}
