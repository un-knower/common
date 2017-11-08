package com.postss.common.kylin.support.repository.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 标记此接口为kylinRepository
 * <pre>
 * 使用    &lt;kylin:repositories&gt;    将会扫描此类并添加至spring bean容器
 * 支持类型与名称注入
 * </pre>
 * @className KylinRepositoryBean
 * @author jwSun
 * @date 2017年8月22日 下午6:58:12
 * @version 1.0.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface KylinRepositoryBean {

    /**自定义bean名,支持使用名字注入**/
    String value() default "";

    /**kylin project名称 与  cubeName 必须选择一个填写 如知道project名推荐使用此属性**/
    String project() default "";

    /**如不知道project名则填写此属性**/
    String cubeName() default "";

}