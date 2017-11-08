package com.postss.common.util;

import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

public class SpringBeanRegisterUtil {

    /**
     * 注册单例对象
     * @param beanId
     * @param clazz
     * @param properties
     * @param beanFactory
     */
    public static void registerSingleton(String beanId, Class<?> clazz, Map<String, Object> properties,
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
    public static void registerBeanDefinition(String beanId, Class<?> clazz, Map<String, Object> properties,
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
