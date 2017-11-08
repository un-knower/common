package com.postss.common.base.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * sql条件
 * @className SqlCondition
 * @author jwSun
 * @date 2017年11月3日 下午2:38:51
 * @version 1.0.0
 */
public class SqlCondition {

    private Map<String, Set<Object>> orMap = new HashMap<>();

    private Map<String, Set<Object>> andMap = new HashMap<>();

    private Map<String, Set<Object>> likeMap = new HashMap<>();

    public SqlCondition or(String key, String val) {
        return this.or(key, val, Boolean.FALSE);
    }

    public SqlCondition and(String key, String val) {
        return this.and(key, val, Boolean.FALSE);
    }

    public SqlCondition like(String key, String val) {
        return this.like(key, val, Boolean.FALSE);
    }

    public SqlCondition or(String key, String val, boolean canNull) {
        put(orMap, key, val, canNull);
        return this;
    }

    public SqlCondition and(String key, String val, boolean canNull) {
        put(andMap, key, val, canNull);
        return this;
    }

    public SqlCondition like(String key, String val, boolean canNull) {
        put(likeMap, key, val, canNull);
        return this;
    }

    private void put(Map<String, Set<Object>> putMap, String key, String val, boolean canNull) {
        if (putMap.get(key) == null)
            this.orMap.put(key, new HashSet<>());

        if (canNull) {
            putMap.get(key).add(val);
        } else {
            if (val != null) {
                putMap.get(key).add(val);
            }
        }
    }
}