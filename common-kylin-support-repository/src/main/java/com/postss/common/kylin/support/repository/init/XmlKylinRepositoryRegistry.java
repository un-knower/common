package com.postss.common.kylin.support.repository.init;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;

import com.postss.common.kylin.support.repository.annotation.KylinRepositoryBean;
import com.postss.common.system.spring.registry.AbstractXmlRegister;
import com.postss.common.system.spring.registry.XmlRegisterConfiguration;
import com.postss.common.util.StringUtil;

public class XmlKylinRepositoryRegistry extends AbstractXmlRegister {

    private XmlKylinRepositoryConfiguration XmlKylinRepositoryConfiguration;

    public XmlKylinRepositoryRegistry(XmlKylinRepositoryConfiguration xmlKylinRepositoryConfigurationSource) {
        this.XmlKylinRepositoryConfiguration = xmlKylinRepositoryConfigurationSource;
    }

    @Override
    public void registryBean() {
        for (BeanDefinition beanDefinition : this.XmlKylinRepositoryConfiguration.getCandidates()) {
            MutablePropertyValues values = beanDefinition.getPropertyValues();
            Class<?> clazz = (Class<?>) values.get("repositoryInterface");
            String beanName = getBeanName(clazz);
            super.registryBean(beanName, beanDefinition);
            String alias = getBeanAlias(clazz);
            if (StringUtil.notEmpty(alias)) {
                super.registryAlias(beanName, alias);
            }
        }
    }

    public String getBeanName(Class<?> repositoryInterface) {
        String name = repositoryInterface.getName();
        return name;
    }

    public String getBeanAlias(Class<?> repositoryInterface) {
        KylinRepositoryBean kylinRepository = repositoryInterface.getAnnotation(KylinRepositoryBean.class);
        if (StringUtil.notEmpty(kylinRepository.value())) {
            return kylinRepository.value();
        } else {
            return null;
        }
    }

    @Override
    public XmlRegisterConfiguration getXmlRegisterConfiguration() {
        return this.XmlKylinRepositoryConfiguration;
    }

}
