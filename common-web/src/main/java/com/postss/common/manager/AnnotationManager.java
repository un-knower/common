package com.postss.common.manager;

import com.postss.common.annotation.parameter.ParameterConfig;

public interface AnnotationManager {

    public Object resolve(Class<? extends ParameterConfig> annotationClass);

}
