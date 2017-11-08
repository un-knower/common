package com.postss.common.system.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;

import com.postss.common.base.annotation.AutoWebApplicationConfig;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.manager.DefaultParameterConfigManager;
import com.postss.common.manager.ParameterConfigManager;

/**
 * 参数验证aop
 * <pre>@Around -> @Before -> invokeMethod -> @After</pre>
 * @see com.postss.common.annotation.Explain
 * @className ParameterAspect
 * @author jwSun
 * @date 2017年6月28日 下午7:36:36
 * @version 1.0.0
 */
@Aspect
@AutoWebApplicationConfig("defaultParameterAspect")
public class ParameterAspect implements Ordered {

    private Logger log = LoggerUtil.getLogger(getClass());

    private ParameterConfigManager manager = new DefaultParameterConfigManager();

    @Pointcut("@annotation(com.postss.common.annotation.Explain)")
    public void explainParameterAspect() {
    }

    /**
     * 方法执行之前
     * @param joinPoint 目标类连接点对象
     */

    @Before("explainParameterAspect()")
    public void beforeAop(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = ((MethodSignature) joinPoint.getSignature());
        Method domethod = methodSignature.getMethod();
        if (log.isDebugEnabled()) {
            log.debug("checkPatameter for {}", domethod);
        }
        manager.checkParameter(domethod, args);
    }

    /**
     * 事物order:3
     */
    @Override
    public int getOrder() {
        return 2;
    }
}
