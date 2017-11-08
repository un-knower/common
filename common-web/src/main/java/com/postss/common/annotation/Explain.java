package com.postss.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.postss.common.system.code.SystemCode;

/**
 * 方法说明注解
 * @className Explain
 * @author jwSun
 * @date 2017年6月26日 下午6:51:16
 * @version 1.0.0
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Explain {

    String value();

    int exceptionCode() default 0;

    SystemCode systemCode() default SystemCode.NOTHING;

}