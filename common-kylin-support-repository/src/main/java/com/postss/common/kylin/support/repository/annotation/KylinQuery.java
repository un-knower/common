package com.postss.common.kylin.support.repository.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * sql查询注解
 * @className KylinQuery
 * @author jwSun
 * @date 2017年8月22日 下午6:57:20
 * @version 1.0.0
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface KylinQuery {

    /**查询sql**/
    String value();

    /**配置文件地址**/
    String configPath() default "";

}