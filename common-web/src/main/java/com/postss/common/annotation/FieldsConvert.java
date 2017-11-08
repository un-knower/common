package com.postss.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于spring data jpa repository,将查询到的数组对象或单个对象转换为所需对象
 * <pre>
 * 使用方式
 *  @FieldsConvert(converterClazz = Demo.class, fields = { "logId", "createId" })
 *  @Query("select a.logId,a.createId from Demo a where a.logId=?1")
 *  List&ltDemo&gt findBySqlConvertList(String logId);
 * </pre>
 * @className Explain
 * @author jwSun
 * @date 2017年6月26日 下午6:51:16
 * @version 1.0.0
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldsConvert {

    /**要转化的类**/
    Class<?> converterClazz() default Object.class;

    /**查询resultSet字段**/
    String[] fields() default {};

    /**自动获取查询字段(待定)**/
    boolean autoFields() default false;

}