package com.postss.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;

/**
 * 反射工具类
 * @className ReflectUtil
 * @author jwSun
 * @date 2017年6月30日 上午11:36:09
 * @version 1.0.0
 */
public class ReflectUtil {

    enum MethodPrefix {
        get, set
    }

    private static Logger log = LoggerUtil.getLogger(ReflectUtil.class);

    /**
     * 获得泛型类型(暂时只能获得编码中已确定的泛型，运行中泛型无法获得，只能获得)
     * @author jwSun
     * @date 2017年3月29日 上午9:37:20
     * @param obj 存在泛型的类
     * @param index 第几个泛型
     * @return Class<?>
     */
    public static Class<?> getGenericInterfaces(Object obj, int index) {
        return getGenericInterfaces(obj, index, 0);
    }

    /**
     * 获得泛型类型(暂时只能获得编码中已确定的泛型，运行中泛型无法获得，只能获得)
     * @param obj  存在泛型的类
     * @param index 第几个泛型
     * @param index2 第几个泛型中的第几个
     * @return 泛型类
     */
    public static Class<?> getGenericInterfaces(Object obj, int index, int index2) {
        Type[] type = obj.getClass().getGenericInterfaces();
        return (Class<?>) ((ParameterizedType) type[index]).getActualTypeArguments()[index2];
    }

    /**
     * 获得属性的读方法
     * @author jwSun
     * @date 2017年3月29日 上午9:40:50
     * @param clazz 类
     * @param fieldName 属性名
     * @return 读方法
     */
    public static Method getReadMethod(Class<?> clazz, String fieldName) {
        try {
            Assert.notNull(fieldName);
            clazz.getDeclaredField(fieldName);
            char cha = Character.toUpperCase(fieldName.charAt(0));
            //获得方法后名称
            String methodName = MethodPrefix.get + String.valueOf(cha) + fieldName.substring(1);
            return clazz.getMethod(methodName);
        } catch (Exception e) {
            log.info("getReadMethod ERROR: {}", e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 获得类的所有读方法
     * @param clazz  要获取的类
     * @return 读方法集合
     */
    public static List<Method> getAllReadMethod(Class<?> clazz, String... ignoreFieldNames) {
        List<Method> methodList = new ArrayList<>();
        try {
            Field[] fields = clazz.getDeclaredFields();
            Set<String> set = null;
            if (ignoreFieldNames != null && ignoreFieldNames.length > 0) {
                set = new HashSet<>();
                Collections.addAll(set, ignoreFieldNames);
            }
            for (Field field : fields) {
                if (set == null || set.contains(field.getName())) {
                    Method readMethod = ReflectUtil.getReadMethod(clazz, field.getName());
                    if (readMethod == null)
                        continue;
                    methodList.add(readMethod);
                }
            }
        } catch (Exception e) {
            log.info("getAllReadMethod ERROR: {}", e.getLocalizedMessage());
        }
        return methodList;
    }

    /**
     * 获得属性的set方法
     * @date 2017年3月29日 上午9:41:13
     * @param clazz   要获取的类
     * @param fieldName 属性名
     * @return 写方法
     */
    public static Method getWriteMethod(Class<?> clazz, String fieldName) {
        try {
            Assert.notNull(fieldName);
            Field f = clazz.getDeclaredField(fieldName);
            char cha = Character.toUpperCase(fieldName.charAt(0));
            //获得方法后名称
            String methodName = MethodPrefix.set + String.valueOf(cha) + fieldName.substring(1);
            return clazz.getMethod(methodName, f.getType());
        } catch (Exception e) {
            log.info("getWriteMethod ERROR: {}", e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 根据类的读方法获取写方法
     * @param clazz   要获取的类
     * @param method 对应的读方法
     * @return 对应的写方法
     */
    public static Method getWriteMethodByRead(Class<?> clazz, Method method) {
        try {
            Assert.notNull(clazz);
            Assert.notNull(method);
            String methodName = method.getName();
            if (methodName.startsWith(MethodPrefix.get.toString())) {
                String fieldName = methodName.substring(3);
                char cha = Character.toLowerCase(fieldName.charAt(0));
                return getWriteMethod(clazz, String.valueOf(cha) + fieldName.substring(1));
            }
        } catch (Exception e) {
            log.info("getWriteMethod ERROR: {}", e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 获得类的所有写方法
     * @param clazz   要获取的类
     * @return 写方法集合
     */
    public static List<Method> getAllWriteMethod(Class<?> clazz, String... ignoreFieldNames) {
        List<Method> methodList = new ArrayList<>();
        try {
            Field[] fields = clazz.getDeclaredFields();
            Set<String> set = null;
            if (ignoreFieldNames != null && ignoreFieldNames.length > 0) {
                set = new HashSet<>();
                Collections.addAll(set, ignoreFieldNames);
            }
            for (Field field : fields) {
                if (set == null || set.contains(field.getName())) {
                    Method readMethod = ReflectUtil.getWriteMethod(clazz, field.getName());
                    if (readMethod == null)
                        continue;
                    methodList.add(readMethod);
                }
            }
        } catch (Exception e) {
            log.info("getAllWriteMethod ERROR: {}", e.getLocalizedMessage());
        }
        return methodList;
    }

    /**
     * 新建对象
     * @param clazz 新建对象class
     * @return 对象
     */
    public static <T> T newInstance(Class<T> clazz) {
        if (clazz == null) {
            String msg = "Class method parameter cannot be null.";
            throw new IllegalArgumentException(msg);
        }
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Unable to instantiate class [" + clazz.getName() + "]", e);
        }
    }

}
