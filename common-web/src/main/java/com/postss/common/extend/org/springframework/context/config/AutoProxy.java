package com.postss.common.extend.org.springframework.context.config;

import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;

public interface AutoProxy extends MethodInterceptor {

    public AutoProxy getObject(Class<?> clazz, Map<String, Object> config);

}
