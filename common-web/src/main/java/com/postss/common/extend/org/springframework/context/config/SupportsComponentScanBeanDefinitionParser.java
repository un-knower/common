package com.postss.common.extend.org.springframework.context.config;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ComponentScanBeanDefinitionParser;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AspectJTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.w3c.dom.Element;

public class SupportsComponentScanBeanDefinitionParser extends ComponentScanBeanDefinitionParser {

    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    private static final String NAME_GENERATOR_ATTRIBUTE = "name-generator";
    private static final String USE_DEFAULT_FILTERS_ATTRIBUTE = "use-default-filters";
    private static final BeanNameGenerator beanNameGenerator = new com.postss.common.system.spring.generator.ConfigAnnotationBeanNameGenerator();

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        element.setAttribute(BASE_PACKAGE_ATTRIBUTE, "com.postss,com.chinaoly");
        element.setAttribute(USE_DEFAULT_FILTERS_ATTRIBUTE, "false");
        return super.parse(element, parserContext);
    }

    @Override
    protected void parseBeanNameGenerator(Element element, ClassPathBeanDefinitionScanner scanner) {
        if (element.hasAttribute(NAME_GENERATOR_ATTRIBUTE)) {
            super.parseBeanNameGenerator(element, scanner);
        } else {
            scanner.setBeanNameGenerator(beanNameGenerator);
        }
    }

    @SuppressWarnings("unchecked")
    protected TypeFilter createTypeFilter(String filterType, String expression, ClassLoader classLoader,
            ParserContext parserContext) {
        expression = parserContext.getReaderContext().getEnvironment().resolvePlaceholders(expression);
        try {
            if ("annotation".equals(filterType)) {
                return new AnnotationTypeFilter((Class<Annotation>) classLoader.loadClass(expression));
            } else if ("assignable".equals(filterType)) {
                return new AssignableTypeFilter(classLoader.loadClass(expression));
            } else if ("aspectj".equals(filterType)) {
                return new AspectJTypeFilter(expression, classLoader);
            } else if ("regex".equals(filterType)) {
                return new RegexPatternTypeFilter(Pattern.compile(expression));
            } else if ("custom".equals(filterType)) {
                Class<?> filterClass = classLoader.loadClass(expression);
                if (!TypeFilter.class.isAssignableFrom(filterClass)) {
                    throw new IllegalArgumentException(
                            "Class is not assignable to [" + TypeFilter.class.getName() + "]: " + expression);
                }
                return (TypeFilter) BeanUtils.instantiateClass(filterClass);
            } else {
                throw new IllegalArgumentException("Unsupported filter type: " + filterType);
            }
        } catch (ClassNotFoundException ex) {
            throw new FatalBeanException("Type filter class not found: " + expression, ex);
        }
    }

}
