package com.postss.common.kylin.support.repository.init;

import com.postss.common.system.spring.registry.XmlRegisterConfiguration;

public interface XmlKylinRepositoryConfiguration extends XmlRegisterConfiguration {

    public Iterable<String> getBasePackages();

}
