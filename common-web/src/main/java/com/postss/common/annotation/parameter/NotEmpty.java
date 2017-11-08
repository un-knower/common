package com.postss.common.annotation.parameter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数值不为空且不为空字符串
 * @className NotEmpty
 * @author jwSun
 * @date 2017年9月14日 上午10:31:36
 * @version 1.0.0
 */
@Target({ ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ParameterConfig
public @interface NotEmpty {

    String value() default "";

    Class<? extends ParameterConfigChecker> checker = NotEmptyChecker.class;

}
