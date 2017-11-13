package com.postss.common.system.spring.registry;

import java.util.Collection;

import org.springframework.beans.factory.config.BeanDefinition;

/**
 * 自定义标签注册bean
 * @className XmlRegister
 * @author jwSun
 * @date 2017年8月23日 上午10:50:29
 * @version 1.0.0
 */
public interface XmlRegister {

    /**
     * 注册全部bean
     */
    public void registryBean();

    /**
     * 注册bean
     */
    public void registryBeans(Collection<BeanDefinition> beanDefinitions);

    /**
     * 注册单个bean
     * @param beanName bean名
     * @param beanDefinition BeanDefinition
     */
    public void registryBean(String beanName, BeanDefinition beanDefinition);

    /**
     * 为已有的bean增加别名
     * @param beanName 已注册的bean名
     * @param alias 别名
     */
    public void registryAlias(String beanName, String alias);

    /**
     * 获得配置实体
     * @return
     */
    public XmlRegisterConfiguration getXmlRegisterConfiguration();

}
