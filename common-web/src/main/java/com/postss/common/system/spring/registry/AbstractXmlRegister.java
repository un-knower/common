package com.postss.common.system.spring.registry;

import java.util.Collection;

import org.springframework.beans.factory.config.BeanDefinition;

/**
 * xml注册抽象类
 * @className AbstractXmlRegister
 * @author jwSun
 * @date 2017年8月23日 上午11:23:40
 * @version 1.0.0
 */
public abstract class AbstractXmlRegister implements XmlRegister {

    @Override
    public void registryBean() {
        for (BeanDefinition beanDefinition : getXmlRegisterConfiguration().getCandidates()) {
            registryBean(beanDefinition.getBeanClassName(), beanDefinition);
        }
    }

    @Override
    public void registryBean(String beanName, BeanDefinition beanDefinition) {
        getXmlRegisterConfiguration().getBeanDefinitionRegistry().registerBeanDefinition(beanName, beanDefinition);
    }

    @Override
    public void registryAlias(String beanName, String alias) {
        getXmlRegisterConfiguration().getBeanDefinitionRegistry().registerAlias(beanName, alias);
    }

    @Override
    public void registryBeans(Collection<BeanDefinition> beanDefinitions) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            registryBean(beanDefinition.getBeanClassName(), beanDefinition);
        }
    }

}
