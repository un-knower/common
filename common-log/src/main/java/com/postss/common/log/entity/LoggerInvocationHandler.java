package com.postss.common.log.entity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggerInvocationHandler implements InvocationHandler {

    private org.slf4j.Logger logger;

    public LoggerInvocationHandler(org.slf4j.Logger logger) {
        super();
        this.logger = logger;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            result = logger.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(logger, args);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

}
