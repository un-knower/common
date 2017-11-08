package com.postss.common.annotation.parameter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数值不能为空
 * @className NotNull
 * @author jwSun
 * @date 2017年9月14日 上午10:32:01
 * @version 1.0.0
 */
@Target({ ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParameterConfig {

    String[] value() default {};

}
