package com.postss.common.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * web/root application自动扫描注解
 * @className AutoConfig
 * @author jwSun
 * @date 2017年9月13日 下午8:17:08
 * @version 1.0.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoConfig {

}
