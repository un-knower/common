package com.postss.common.extend.org.springframework.context.config;

import org.springframework.context.annotation.ComponentScanBeanDefinitionParser;
import org.springframework.context.config.ContextNamespaceHandler;

public class ContextNamespaceHandlerSupports extends ContextNamespaceHandler {

    @Override
    public void init() {
        registerBeanDefinitionParser("component-scan", new ComponentScanBeanDefinitionParser());
        registerBeanDefinitionParser("web-component-scan", new WebSupportsComponentScanBeanDefinitionParser());
        registerBeanDefinitionParser("root-component-scan", new RootSupportsComponentScanBeanDefinitionParser());
    }

}
