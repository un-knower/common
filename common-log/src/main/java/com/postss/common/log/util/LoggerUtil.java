package com.postss.common.log.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.slf4j.LoggerFactory;

import com.postss.common.log.entity.Logger;
import com.postss.common.log.entity.LoggerTemplate;

/**
 * 日志工具类
 * @className LoggerUtil
 * @author jwSun
 * @date 2017年6月30日 上午11:29:19
 * @version 1.0.0
 */
public class LoggerUtil {

    /**
     * 获得包装Logger类
     * @param clazz 使用logger类
     * @return
     */
    public static Logger getLogger(Class<?> clazz) {
        return (Logger) Proxy.newProxyInstance(LoggerTemplate.class.getClassLoader(),
                LoggerTemplate.class.getInterfaces(), new LoggerInvocationHandler(LoggerFactory.getLogger(clazz)));
    }

}

class LoggerInvocationHandler implements InvocationHandler {

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