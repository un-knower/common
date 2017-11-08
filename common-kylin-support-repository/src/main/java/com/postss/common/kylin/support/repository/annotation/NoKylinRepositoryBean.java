package com.postss.common.kylin.support.repository.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 标记此接口不为kylin repository,将不会被扫描至ioc
 * @className NoKylinRepositoryBean
 * @author jwSun
 * @date 2017年8月22日 下午7:02:54
 * @version 1.0.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface NoKylinRepositoryBean {

}