package com.postss.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询时使用between方式
 * @author jwSun
 * @date 2017年4月5日 下午2:19:29
 */
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
public @interface QueryByBetween {

    public String lowFieldName();

    public String highFieldName();
}