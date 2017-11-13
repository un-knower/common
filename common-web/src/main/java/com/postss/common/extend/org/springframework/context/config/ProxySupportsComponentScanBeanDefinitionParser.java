package com.postss.common.extend.org.springframework.context.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.w3c.dom.Element;

import com.postss.common.extend.register.DefaultXmlRegisterConfiguration;
import com.postss.common.extend.register.ProxyClassPathBeanDefinitionScanner;
import com.postss.common.extend.register.ProxyXmlRegister;
import com.postss.common.system.spring.registry.XmlRegister;
import com.postss.common.system.spring.registry.XmlRegisterConfiguration;

public class ProxySupportsComponentScanBeanDefinitionParser extends SupportsComponentScanBeanDefinitionParser {

    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    protected void parseTypeFilters(Element element, ClassPathBeanDefinitionScanner scanner,
            ParserContext parserContext) {
        super.parseTypeFilters(element, scanner, parserContext);
        ClassLoader classLoader = scanner.getResourceLoader().getClassLoader();
        scanner.addIncludeFilter(createTypeFilter("annotation",
                "com.postss.common.extend.org.springframework.context.config.AutoProxyApplicationConfig", classLoader,
                parserContext));
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        element.setAttribute(BASE_PACKAGE_ATTRIBUTE, "com.postss,com.chinaoly");
        element.setAttribute("use-default-filters", "false");
        String basePackage = element.getAttribute(BASE_PACKAGE_ATTRIBUTE);
        basePackage = parserContext.getReaderContext().getEnvironment().resolvePlaceholders(basePackage);
        ClassPathBeanDefinitionScanner scanner = configureScanner(parserContext, element);
        XmlRegisterConfiguration xmlRegisterConfiguration = new DefaultXmlRegisterConfiguration(element, parserContext,
                scanner);
        XmlRegister defaultXmlRegister = new ProxyXmlRegister(xmlRegisterConfiguration);
        defaultXmlRegister.registryBean();
        return null;
    }

    protected ClassPathBeanDefinitionScanner createScanner(XmlReaderContext readerContext, boolean useDefaultFilters) {
        return new ProxyClassPathBeanDefinitionScanner(readerContext.getRegistry(), useDefaultFilters,
                readerContext.getEnvironment(), readerContext.getResourceLoader());
    }

}
