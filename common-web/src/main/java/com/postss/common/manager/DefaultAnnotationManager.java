package com.postss.common.manager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.annotation.AnnotationUtils;

import com.postss.common.annotation.FieldsConvert;
import com.postss.common.annotation.parameter.ParameterConfig;
import com.postss.common.base.annotation.AutoRootApplicationConfig;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.system.code.SystemCode;
import com.postss.common.system.exception.SystemCodeException;
import com.postss.common.util.ConvertUtil;
import com.postss.common.util.ReflectUtil;

/**
 * 默认注解管理器
 * @className DefaultAnnotationManager
 * @author jwSun
 * @date 2017年9月15日 下午2:34:20
 * @version 1.0.0
 */
@AutoRootApplicationConfig("defaultAnnotationManager")
public class DefaultAnnotationManager implements AnnotationManager {

    private static Logger log = LoggerUtil.getLogger(DefaultAnnotationManager.class);

    private static Set<Class<? extends Annotation>> annotationSet = new HashSet<>();

    static {
        annotationSet.add(FieldsConvert.class);
    }

    @SuppressWarnings("unchecked")
    public static Object resolveFieldsConvert(Method method, Object result) {
        FieldsConvert fieldsConvert = AnnotationUtils.findAnnotation(method, FieldsConvert.class);
        if (fieldsConvert != null) {
            log.debug("fieldsConvert is find ,now convert.");
            Class<?> clazz = fieldsConvert.converterClazz();
            if (clazz == Object.class) {
                if (java.util.Collection.class.isAssignableFrom(method.getReturnType())) {
                    throw new SystemCodeException(SystemCode.CHECK_FAIL, "@FieldConvert 没有指定转换集合中泛型class,转换失败");
                }
                clazz = method.getReturnType();
            } else {
                if ((!java.util.Collection.class.isAssignableFrom(method.getReturnType()))
                        && clazz != method.getReturnType()) {
                    throw new SystemCodeException(SystemCode.CHECK_FAIL, "@FieldConvert 指定转换class与方法返回类型不同");
                }
            }
            try {
                if (fieldsConvert.autoFields()) {
                    log.error("暂时不支持自动获取属性并解析,将返回原始数据");
                    //懒懒懒
                } else {
                    if (result instanceof java.util.List) {
                        log.debug("convert list<Object> to List<{}>", clazz.getName());
                        List<Object> resultList = (List<Object>) result;
                        List<Map<String, Object>> convertList = ConvertUtil.parseObjToMap(resultList,
                                fieldsConvert.fields());
                        List<Object> returnList = new ArrayList<Object>();
                        for (Map<String, Object> resultOne : convertList) {
                            returnList.add(ConvertUtil.mapConvertToBean(resultOne, clazz));
                        }
                        return returnList;
                    } else if (result.getClass().isArray()) {
                        log.debug("convert array to {}", clazz.getName());
                        Object[] resultArray = (Object[]) result;
                        Object clazzInstance = clazz.newInstance();
                        for (int i = 0; i < resultArray.length && i < fieldsConvert.fields().length; i++) {
                            Method writeMethod = ReflectUtil.getWriteMethod(clazz, fieldsConvert.fields()[i]);
                            if (writeMethod != null) {
                                writeMethod.invoke(clazzInstance, resultArray[i]);
                            }
                        }
                        return clazzInstance;
                    }
                }
            } catch (Exception e) {
                log.error("parse error :{}. return.", e.getLocalizedMessage());
            }
        }
        return result;
    }

    @Override
    public Object resolve(Class<? extends ParameterConfig> annotationClass) {
        // TODO Auto-generated method stub
        return null;
    }

}
