package com.postss.common.extend.register;

import com.postss.common.system.spring.registry.AbstractXmlRegister;
import com.postss.common.system.spring.registry.XmlRegisterConfiguration;

public class DefaultXmlRegister extends AbstractXmlRegister {

    private XmlRegisterConfiguration xmlRegisterConfiguration;

    public DefaultXmlRegister(XmlRegisterConfiguration xmlRegisterConfiguration) {
        super();
        this.xmlRegisterConfiguration = xmlRegisterConfiguration;
    }

    @Override
    public XmlRegisterConfiguration getXmlRegisterConfiguration() {
        return xmlRegisterConfiguration;
    }

}
