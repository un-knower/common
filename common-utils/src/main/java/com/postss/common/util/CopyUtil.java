package com.postss.common.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;

/**
 * 复制工具类
 * @author jwSun
 * @date 2017年6月6日 上午11:00:09
 */
public class CopyUtil {

    private static Logger log = LoggerUtil.getLogger(CopyUtil.class);

    /**
     * 复制一个bean
     * @param clazz
     * @param bean
     * @param filterNames
     * @return
     */
    public static <T> T copyBean(Class<T> clazz, T bean, String... filterNames) {
        Assert.notNull(bean, "bean is null");
        Assert.notNull(clazz, "clazz is null");
        try {
            T instance = ReflectUtil.newInstance(clazz);
            List<Method> allReadMethod = ReflectUtil.getAllReadMethod(clazz);
            for (Method method : allReadMethod) {
                if (checkField(method, filterNames)) {
                    Method writeMethod = ReflectUtil.getWriteMethodByRead(clazz, method);
                    if (writeMethod != null) {
                        writeMethod.invoke(instance, method.invoke(bean));
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * 复制一个bean
     * @param clazz
     * @param bean
     * @param filterNames
     * @return
     */
    public static <T> T copyBeanByNeed(Class<T> clazz, T bean, String... needNames) {
        Assert.notNull(bean, "bean is null");
        Assert.notNull(clazz, "clazz is null");
        try {
            T instance = ReflectUtil.newInstance(clazz);
            List<Method> allReadMethod = ReflectUtil.getAllReadMethod(clazz);
            for (Method method : allReadMethod) {
                if (!checkField(method, needNames)) {
                    Method writeMethod = ReflectUtil.getWriteMethodByRead(clazz, method);
                    if (writeMethod != null) {
                        writeMethod.invoke(instance, method.invoke(bean));
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static <K, V> Map<K, V> copyMapByNeed(Map<K, V> copyMap, String... needNames) {
        Assert.notNull(copyMap, "bean is null");
        Map<K, V> returnMap = new HashMap<>();
        try {
            for (Map.Entry<K, V> entry : copyMap.entrySet()) {
                if (!checkName(entry.getKey(), needNames)) {
                    returnMap.put(entry.getKey(), entry.getValue());
                }
            }
            return returnMap;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static <K, V> List<Map<K, V>> copyMapByMapList(List<Map<K, V>> copyList, String... needNames) {
        Assert.notNull(copyList, "bean is null");
        List<Map<K, V>> returnList = new ArrayList<>();
        try {
            for (Map<K, V> copyMap : copyList) {
                Map<K, V> returnMap = new HashMap<>();
                for (Map.Entry<K, V> entry : copyMap.entrySet()) {
                    if (!checkName(entry.getKey(), needNames)) {
                        returnMap.put(entry.getKey(), entry.getValue());
                    }
                }
                returnList.add(returnMap);
            }

            return returnList;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static List<JSONObject> copyByJSONObject(List<JSONObject> copyList, String... needNames) {
        Assert.notNull(copyList, "bean is null");
        List<JSONObject> returnList = new ArrayList<>();
        try {
            for (JSONObject copyMap : copyList) {
                returnList.add(copyByJSONObject(copyMap, needNames));
            }
            return returnList;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static JSONObject copyByJSONObject(JSONObject copyMap, String... needNames) {
        Assert.notNull(copyMap, "bean is null");
        try {
            JSONObject returnMap = new JSONObject();
            for (Map.Entry<String, Object> entry : copyMap.entrySet()) {
                if (!checkName(entry.getKey(), needNames)) {
                    returnMap.put(entry.getKey(), entry.getValue());
                }
            }
            return returnMap;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    private static boolean checkField(Method method, String... filterNames) {
        if (filterNames == null)
            return true;
        for (String filterName : filterNames) {
            if (method.getName().equalsIgnoreCase("get" + filterName)) {
                return false;
            }
        }
        return true;
    }

    private static <K> boolean checkName(K name, String... filterNames) {
        if (filterNames == null)
            return true;
        for (String filterName : filterNames) {
            if (name.equals(filterName)) {
                return false;
            }
        }
        return true;
    }

}
