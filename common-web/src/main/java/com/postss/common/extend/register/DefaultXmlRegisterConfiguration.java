package com.postss.common.extend.register;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import com.postss.common.system.spring.registry.XmlRegisterConfiguration;

public class DefaultXmlRegisterConfiguration implements XmlRegisterConfiguration {

    private final ParserContext parserContext;
    private final Element element;
    private final ClassPathBeanDefinitionScanner scanner;

    public DefaultXmlRegisterConfiguration(Element element, ParserContext parserContext,
            ClassPathBeanDefinitionScanner scanner) {
        super();
        this.parserContext = parserContext;
        this.element = element;
        this.scanner = scanner;
    }

    @Override
    public Collection<BeanDefinition> getCandidates() {
        Set<BeanDefinition> find = new HashSet<BeanDefinition>();
        String attribute = element.getAttribute("base-package");
        List<String> basePackages = Arrays.asList(StringUtils.delimitedListToStringArray(attribute, ",", " "));
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidate = scanner.findCandidateComponents(basePackage);
            find.addAll(candidate);
        }
        return find;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return this.parserContext.getReaderContext().getResourceLoader();
    }

    @Override
    public BeanDefinitionRegistry getBeanDefinitionRegistry() {
        return this.parserContext.getRegistry();
    }

    @Override
    public NamedNodeMap getAttributes() {
        return element.getAttributes();
    }

    @Override
    public String getAttribute(String name) {
        return element.getAttribute(name);
    }

}
