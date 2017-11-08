package com.postss.common.kylin.support.repository.init;

import java.util.Arrays;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.ReaderContext;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.postss.common.kylin.support.repository.annotation.KylinRepositoryBean;
import com.postss.common.util.SpringNameSpaceUtil;

public class KylinRepositoryBeanDefinitionParser implements BeanDefinitionParser {

    private static final String BASE_PACKAGE = "base-package";

    public Iterable<String> getBasePackages(Element element) {
        String attribute = element.getAttribute(BASE_PACKAGE);
        return Arrays.asList(StringUtils.delimitedListToStringArray(attribute, ",", " "));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        SpringNameSpaceUtil.resolveWildcard(element, parserContext, BASE_PACKAGE);
        try {
            Environment environment = parserContext.getDelegate().getEnvironment();
            XmlKylinRepositoryConfigurationSource xmlKylinRepositoryConfigurationSource = new XmlKylinRepositoryConfigurationSource(
                    element, parserContext, environment);
            XmlKylinRepositoryRegistry xmlKylinRepositoryRegistry = new XmlKylinRepositoryRegistry(
                    xmlKylinRepositoryConfigurationSource);
            xmlKylinRepositoryRegistry.registryBean();
            return null;
        } catch (RuntimeException e) {
            handleError(e, element, parserContext.getReaderContext());
        }

        return null;
    }

    private void handleError(Exception e, Element source, ReaderContext reader) {
        reader.error(e.getMessage(), reader.extractSource(source), e);
    }

    public static void main(String[] args) {
        System.out.println(KylinRepositoryBean.class.getName());
    }

}
