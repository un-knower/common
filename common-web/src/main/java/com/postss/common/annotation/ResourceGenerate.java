package com.postss.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动生成资源注解(暂时不使用)
 * @author jwSun
 * @date 2017年5月26日 下午7:08:22
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
public @interface ResourceGenerate {

    /**
     * 资源中文名
     */
    public String nameCn();

    /**
     * 资源名
     */
    public String resourceName() default "GenerateResource";

    /**
     * 模块等级(弃用)
     */
    public int resourceLevel() default 0;

    /**
     * 资源别名
     */
    public String resourceAlias() default "";

    /**
    * 权重
    */
    public int weight() default 0;

    /**
     * 备注
     */
    public String remark() default "";

    /**
     * 是否有效
     */
    public int delFlag() default 0;

    public String createId() default "GenerateId";

    public String clientId() default "GenerateClient";
}