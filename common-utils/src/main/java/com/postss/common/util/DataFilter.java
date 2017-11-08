package com.postss.common.util;

/**
 * 数据过滤类
 * @className DataFilter
 * @author jwSun
 * @date 2017年6月30日 上午11:18:48
 * @version 1.0.0
 * @param <T>
 */
public abstract class DataFilter<T> {

    protected QuickHashMap<String, Object> isMap = new QuickHashMap<>();
    protected QuickHashMap<String, Object> notMap = new QuickHashMap<>();

    public DataFilter() {

    }
}
