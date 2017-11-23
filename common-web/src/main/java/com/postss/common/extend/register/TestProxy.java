package com.postss.common.extend.register;

import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;

import com.postss.common.extend.org.springframework.context.config.AutoProxy;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;

public class TestProxy implements AutoProxy {

    Logger log = LoggerUtil.getLogger(getClass());

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("123");
        return null;
    }

    @Override
    public AutoProxy getObject(Class<?> clazz, Map<String, Object> config) {
        return new TestProxy();
    }

}
