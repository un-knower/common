package com.postss.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 注解工具类
 * @className AnnotationUtil
 * @author jwSun
 * @date 2017年8月4日 上午11:17:40
 * @version 1.0.0
 */
public class AnnotationUtil {

    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T getAnnotation(Method method, Class<T> annotationClass) {
        Annotation[] annotations = method.getDeclaredAnnotations();// 获得正在执行方法的所有注解
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == annotationClass) {
                return (T) annotation;
            }
        }
        return null;
    }
}
