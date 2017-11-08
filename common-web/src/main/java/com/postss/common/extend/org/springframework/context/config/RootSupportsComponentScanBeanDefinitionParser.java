package com.postss.common.extend.org.springframework.context.config;

import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.w3c.dom.Element;

public class RootSupportsComponentScanBeanDefinitionParser extends SupportsComponentScanBeanDefinitionParser {

    protected void parseTypeFilters(Element element, ClassPathBeanDefinitionScanner scanner,
            ParserContext parserContext) {
        ClassLoader classLoader = scanner.getResourceLoader().getClassLoader();
        scanner.addIncludeFilter(
                createTypeFilter("annotation", "org.springframework.stereotype.Service", classLoader, parserContext));
        scanner.addIncludeFilter(createTypeFilter("annotation",
                "com.postss.common.base.annotation.AutoRootApplicationConfig", classLoader, parserContext));
    }

}
