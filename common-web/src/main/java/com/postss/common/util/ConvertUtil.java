package com.postss.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.NumberUtils;

/**
 * 类型转换工具类
 * @author sjw
 * @date   2016年9月29日 下午4:12:39
 */
public class ConvertUtil {

    /**
     * 将bean转化为Map<String, Object>
     * @author sjw
     * @date 2016年8月12日 下午5:42:48
     * @param bean 需要转换的bean对象
     * @return Map<String, Object>
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> beanConvertToMap(Object bean) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Class type = bean.getClass();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName(); //bean中的属性
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null) {
                        returnMap.put(propertyName, result);
                    } else {
                        returnMap.put(propertyName, "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }

    /**
     * 将map转换为bean(map的key对应bean的field名)
     * @author sjw
     * @param <T>
     * @date 2016年8月15日 上午11:42:06
     * @param map 需要转换的Map<String, Object>
     * @param bean 转换后的bean对象
     * @return Boolean:true为成功,false为失败
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> T mapConvertToBean(Map<String, Object> map, Class<T> type) {
        DateFormat dateFormat = new SimpleDateFormat();
        try {
            Assert.notNull(type);
            T bean = type.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();//bean中的属性
                Object propertyValue = map.get(propertyName);
                if (!propertyName.equals("class") && propertyValue != null
                        && !String.valueOf(propertyValue).equals("")) {
                    Class clazz = descriptor.getPropertyType();
                    //判断现有类型和bean中需要类型是否相同
                    Object propert = null;
                    if (!(clazz.isAssignableFrom(propertyValue.getClass()))) {
                        String value = String.valueOf(propertyValue);
                        //--------------转换类型-----------------//
                        if (java.lang.String.class.equals(clazz)) {
                            propert = propertyValue;
                        } else if (java.util.Date.class.equals(clazz)) {
                            propert = DateUtil.parse(value, "yyyy-MM-dd");
                        } else if (java.sql.Timestamp.class.equals(clazz)) {
                            propert = new Timestamp(dateFormat.parse(value).getTime());
                        } else {
                            propert = NumberUtils.parseNumber(value, clazz);
                        }
                    } else {
                        propert = propertyValue;
                    }
                    if (null != propert) {
                        Method writeMethod = descriptor.getWriteMethod();
                        writeMethod.invoke(bean, propert);
                    }
                }
            }
            return bean;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 将object型的数组转换为指定类型的数组对象
     * 推荐使用{@ parseObjToMap}
     * @author jwSun
     * @param <T>
     * @date 2016年10月30日下午7:23:24
     * @param array object类型数组
     * @param toClazz 需要转换成的类型
     * @return 转换指定类型后的数组
     */
    @Deprecated
    public static <T> Object parseArraytoClassArray(Object array, Class<T> toClazz) {
        if (array.getClass().isArray()) {
            int length = Array.getLength(array);
            Object retArray = Array.newInstance(toClazz, length);
            for (int i = 0; i < length; i++) {
                Object retObject = toClazz.cast(Array.get(array, i));
                Array.set(retArray, i, retObject);
            }
            return retArray;
        }
        return null;
    }

    /**
     * 装载为map 如
     * <pre>objList:[{a,b,c},{d,e,f}]
     *      fieldName:{name,age,sex} 
     *      -> [{name=a,age=b,sex=c},{name=d,age=e,sex=f}}
     * @author jwSun
     * @date 2017年4月26日 下午2:12:25
     * @param objList 
     * @param fieldName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> parseObjToMap(List<Object> objList, String[] fieldName) {
        List<Map<String, Object>> retList = new ArrayList<>();
        objList.forEach((Object obj) -> {
            List<Object> tempList = null;
            if (obj.getClass().isArray()) {
                Object[] objArray = (Object[]) obj;
                tempList = Arrays.asList(objArray);
            } else if (obj instanceof java.util.List) {
                tempList = (List<Object>) obj;
            } else {
                tempList = new ArrayList<>();
                tempList.add(obj);
            }
            Map<String, Object> tempMap = new LinkedHashMap<>();
            for (int i = 0; tempList != null && i < tempList.size(); i++) {
                tempMap.put(fieldName[i], tempList.get(i));
            }
            retList.add(tempMap);
        });
        return retList;
    }
}
