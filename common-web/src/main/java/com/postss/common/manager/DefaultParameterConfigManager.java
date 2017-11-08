package com.postss.common.manager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import com.postss.common.annotation.parameter.NotEmpty;
import com.postss.common.annotation.parameter.NotNull;
import com.postss.common.annotation.parameter.ParameterConfig;
import com.postss.common.annotation.parameter.ParameterConfigChecker;
import com.postss.common.base.annotation.AutoWebApplicationConfig;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.system.exception.BusinessException;
import com.postss.common.util.ReflectUtil;

/**
 * 默认参数验证管理器
 * @className DefaultParameterConfigManager
 * @author jwSun
 * @date 2017年9月15日 下午2:34:03
 * @version 1.0.0
 */
@AutoWebApplicationConfig("defaultParameterConfigManager")
public class DefaultParameterConfigManager implements ParameterConfigManager {

    private Logger log = LoggerUtil.getLogger(getClass());

    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    Map<Class<? extends Annotation>, ParameterConfigChecker> parameterConfigMap = new HashMap<>();

    {
        parameterConfigMap.put(NotNull.class, ReflectUtil.newInstance(NotNull.checker));
        parameterConfigMap.put(NotEmpty.class, ReflectUtil.newInstance(NotEmpty.checker));
        log.info("manager init:{}", parameterConfigMap);
    }

    @Override
    public boolean checkParameter(Annotation parameterConfig, Object arg) {
        if (parameterConfig.annotationType().isAnnotationPresent(ParameterConfig.class)) {
            ParameterConfigChecker checker = parameterConfigMap.get(parameterConfig.annotationType());
            if (checker == null) {
                throw new BusinessException();
            }
            return checker.check(arg, parameterConfig, "");
        }
        if (log.isWarnEnabled()) {
            log.warn("Annotation {} is not a parameterConfig annotation", parameterConfig);
        }
        return false;
    }

    @Override
    public boolean checkParameter(Method domethod, Object[] args) {
        Annotation[][] paramsAnnotationsArray = domethod.getParameterAnnotations();
        for (int i = 0; i < paramsAnnotationsArray.length; i++) {
            Annotation[] paramAnnotationsArray = paramsAnnotationsArray[i];
            for (int j = 0; j < paramAnnotationsArray.length; j++) {
                Annotation paramAnnotation = paramAnnotationsArray[j];
                if (paramAnnotation.annotationType().isAnnotationPresent(ParameterConfig.class)) {
                    ParameterConfigChecker checker = parameterConfigMap.get(paramAnnotation.annotationType());
                    log.debug("get checker:{}", checker);
                    if (checker == null) {
                        throw new BusinessException();
                    }
                    if (!checker.check(args[i], paramAnnotation,
                            parameterNameDiscoverer.getParameterNames(domethod)[i])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
