package com.postss.common.extend.org.springframework.context.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.postss.common.base.annotation.AutoConfig;

/**
 * proxy application自动扫描注解
 * @className AutoWebApplicationConfig
 * @author jwSun
 * @date 2017年9月13日 下午8:16:35
 * @version 1.0.0
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AutoConfig
public @interface AutoProxyApplicationConfig {

    String value() default "";

    /**
     * 代理类执行
     * @return
     */
    Class<? extends AutoProxy> proxyClass();

}
