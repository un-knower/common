package com.postss.common.extend.register;

import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.w3c.dom.Element;

public class ProxyXmlRegisterConfiguration extends DefaultXmlRegisterConfiguration {

    public ProxyXmlRegisterConfiguration(Element element, ParserContext parserContext,
            ClassPathBeanDefinitionScanner scanner) {
        super(element, parserContext, scanner);
    }

}
