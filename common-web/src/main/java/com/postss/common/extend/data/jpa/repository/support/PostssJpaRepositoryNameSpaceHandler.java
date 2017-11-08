package com.postss.common.extend.data.jpa.repository.support;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.data.jpa.repository.config.AuditingBeanDefinitionParser;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.data.repository.config.RepositoryBeanDefinitionParser;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

/**
 * postss namespace解析
 * @className PostssJpaRepositoryNameSpaceHandler
 * @author jwSun
 * @date 2017年7月6日 下午3:07:35
 * @version 1.0.0
 */
public class PostssJpaRepositoryNameSpaceHandler extends NamespaceHandlerSupport {

    public void init() {
        RepositoryConfigurationExtension extension = new JpaRepositoryConfigExtension();
        RepositoryBeanDefinitionParser repositoryBeanDefinitionParser = new PostssRepositoryBeanDefinitionParser(
                extension);

        registerBeanDefinitionParser("repositories", repositoryBeanDefinitionParser);
        registerBeanDefinitionParser("auditing", new AuditingBeanDefinitionParser());
    }
}
