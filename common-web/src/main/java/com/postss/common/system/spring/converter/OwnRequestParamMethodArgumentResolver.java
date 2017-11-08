package com.postss.common.system.spring.converter;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.postss.common.base.annotation.AutoWebApplicationConfig;

/**
 * {@link RequestMappingHandlerAdapter} 自定义参数解析器,使自定义转换器生效
 * @see com.postss.common.extend.DispatcherServlet
 * @className OwnRequestParamMethodArgumentResolver  -> initHandlerAdapters
 * @author jwSun
 * @date 2017年9月24日 下午7:43:23
 * @version 1.0.0
 */
@AutoWebApplicationConfig
public class OwnRequestParamMethodArgumentResolver extends RequestParamMethodArgumentResolver
        implements ParamMethodArgumentResolver {

    /**是否使用自定义解析类型**/
    private boolean useOwnDefaultResolution = true;
    private Set<Class<?>> supportsPropertySet = new HashSet<>();

    {
        supportsPropertySet.add(JSONArray.class);
        supportsPropertySet.add(JSONObject.class);
    }

    public OwnRequestParamMethodArgumentResolver() {
        super(null, true);
    }

    public OwnRequestParamMethodArgumentResolver(ConfigurableBeanFactory beanFactory, boolean useDefaultResolution) {
        super(beanFactory, useDefaultResolution);
    }

    public boolean supportsParameter(MethodParameter parameter) {
        boolean supportsParameter = super.supportsParameter(parameter);
        if (!supportsParameter) {
            if (this.useOwnDefaultResolution)
                return isSupportsProperty(parameter.getNestedParameterType());
        }
        return supportsParameter;
    }

    public boolean isSupportsProperty(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        if (ParamMethodArgumentEntity.class.isAssignableFrom(clazz)) {
            return true;
        }
        return supportsPropertySet.contains(clazz);
    }

}
