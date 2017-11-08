package com.postss.common.kylin.support.repository.init;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * kylin repository
 * @className PostssKylinRepositoryNameSpaceHandler
 * @author jwSun
 * @date 2017年7月6日 下午3:07:35
 * @version 1.0.0
 */
public class PostssKylinRepositoryNameSpaceHandler extends NamespaceHandlerSupport {

    public void init() {
        registerBeanDefinitionParser("repositories", new KylinRepositoryBeanDefinitionParser());
    }
}
