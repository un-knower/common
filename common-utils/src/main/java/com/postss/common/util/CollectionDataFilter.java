package com.postss.common.util;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 集合过滤工具类
 * @author jwSun
 * @date 2017年5月27日 下午4:04:20
 */
public class CollectionDataFilter<T> extends DataFilter<T> {

    protected Collection<T> collection;

    public CollectionDataFilter(Collection<T> collection) {
        super();
        this.collection = collection;
    }

    public CollectionDataFilter<T> is(String fieldName, Object filterVal) {
        this.isMap.put(fieldName, filterVal);
        return this;
    }

    public CollectionDataFilter<T> not(String fieldName, Object filterVal) {
        this.notMap.put(fieldName, filterVal);
        return this;
    }

    /**
     * 执行过滤
     * @author jwSun
     * @date 2017年5月27日 下午4:04:33
     * @return
     */
    @SuppressWarnings("unchecked")
    public Collection<T> filter() {
        Collection<T> returnCollection = null;
        try {
            returnCollection = collection.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e1) {
            e1.printStackTrace();
            throw new RuntimeException(e1.getLocalizedMessage());
        }
        for (T t : collection) {
            if (check(t, isMap, true) && check(t, notMap, false)) {
                returnCollection.add(t);
            }
        }
        return returnCollection;
    }

    protected boolean check(T t, Map<String, Object> checkMap, boolean is) {
        if (checkMap.size() != 0) {
            for (Entry<String, Object> entry : checkMap.entrySet()) {
                try {
                    Object val = ReflectUtil.getReadMethod(t.getClass(), entry.getKey()).invoke(t);
                    if (is) {
                        if (ComparatorUtil.equals(val, entry.getValue())) {
                            continue;
                        } else {
                            return false;
                        }
                    } else {
                        if (!ComparatorUtil.equals(val, entry.getValue())) {
                            continue;
                        } else {
                            return false;
                        }
                    }
                } catch (Exception e) {

                }
            }
        }
        return true;
    }
}
