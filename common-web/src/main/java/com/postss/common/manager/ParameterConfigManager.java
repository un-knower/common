package com.postss.common.manager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface ParameterConfigManager {

    public boolean checkParameter(Annotation parameterConfig, Object arg);

    public boolean checkParameter(Method domethod, Object[] args);

}
