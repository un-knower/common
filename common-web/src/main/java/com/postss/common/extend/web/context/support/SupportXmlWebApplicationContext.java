package com.postss.common.extend.web.context.support;

import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.postss.common.util.CollectionUtil;

/**
 * XmlWebApplicationContext 扩展
 * <pre>
 *      mvcContext:配合{@link com.postss.common.extend.DispatcherServlet}使用
 *      rootContext:暂无
 * </pre>
 * @className SupportXmlWebApplicationContext
 * @author jwSun
 * @date 2017年8月6日 下午2:00:25
 * @version 1.0.0
 */
public class SupportXmlWebApplicationContext extends XmlWebApplicationContext {

    /**
     * 初始化beanFactory
     * 初始化完成后将执行自动注入
     */
    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        ConfigurableListableBeanFactory beanFactory = super.obtainFreshBeanFactory();
        //TODO 实现注入以前增加bean，从而顺利注入在此增加的bean
        return beanFactory;
    }

    /**
     * 注册单例对象
     * @param beanId
     * @param clazz
     * @param properties
     * @param beanFactory
     */
    public void registerSingleton(String beanId, Class<?> clazz, Map<String, Object> properties,
            ConfigurableListableBeanFactory beanFactory) {
        if (beanFactory instanceof SingletonBeanRegistry) {
            SingletonBeanRegistry registry = (SingletonBeanRegistry) beanFactory;
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            if (!CollectionUtil.isEmpty(properties)) {
                properties.forEach((name, value) -> {
                    builder.addPropertyValue(name, value);
                });
            }
            registry.registerSingleton(beanId, builder.getBeanDefinition());
        }
    }

    /**
     * 注册BeanDefinition
     * @param beanId
     * @param clazz
     * @param properties
     * @param beanFactory
     */
    public void registerBeanDefinition(String beanId, Class<?> clazz, Map<String, Object> properties,
            ConfigurableListableBeanFactory beanFactory) {
        if (beanFactory instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            if (!CollectionUtil.isEmpty(properties)) {
                properties.forEach((name, value) -> {
                    builder.addPropertyValue(name, value);
                });
            }
            registry.registerBeanDefinition(beanId, builder.getBeanDefinition());
        }
    }
}