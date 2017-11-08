package com.postss.common.system.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

public class SpringDataJpaAspect {

    // private Logger log = LoggerUtil.getLogger(getClass());

    @Pointcut("execution(* org.springframework.data.repository.core.support.RepositoryFactorySupport.QueryExecutorMethodInterceptor.invoke(..))")
    public void springDataJpaAspect() {
    }

    @Before("springDataJpaAspect()")
    public void beforeAop(JoinPoint joinPoint) {
        System.out.println("123");
    }
}
