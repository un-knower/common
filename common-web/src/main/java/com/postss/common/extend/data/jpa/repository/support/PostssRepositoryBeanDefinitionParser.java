package com.postss.common.extend.data.jpa.repository.support;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.data.repository.config.RepositoryBeanDefinitionParser;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.w3c.dom.Element;

import com.postss.common.util.SpringNameSpaceUtil;

/**
 * postss标签参数解析器
 * @className PostssRepositoryBeanDefinitionParser
 * @author jwSun
 * @date 2017年7月6日 下午3:08:07
 * @version 1.0.0
 */
public class PostssRepositoryBeanDefinitionParser extends RepositoryBeanDefinitionParser {

    private static final String BASE_PACKAGE = "base-package";

    public PostssRepositoryBeanDefinitionParser(RepositoryConfigurationExtension extension) {
        super(extension);
    }

    public BeanDefinition parse(Element element, ParserContext parser) {
        SpringNameSpaceUtil.resolveWildcard(element, parser, BASE_PACKAGE);
        return super.parse(element, parser);
    }
}
