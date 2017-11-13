package com.postss.common.kylin.support.repository.init;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.data.config.TypeFilterParser;
import org.springframework.data.config.TypeFilterParser.Type;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import com.postss.common.kylin.support.repository.factory.KylinRepositoryFactoryBean;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;

public class XmlKylinRepositoryConfigurationSource extends XmlKylinRepositoryConfigurationSourceSupport {

    private static final String BASE_PACKAGE = "base-package";
    private static final String CONFIG_PATH = "config-path";

    private Logger log = LoggerUtil.getLogger(getClass());
    private static final String CLASS_LOADING_ERROR = "%s - Could not load type %s using class loader %s.";

    private final Element element;
    private final ParserContext parserContext;

    private final Collection<TypeFilter> includeFilters;
    private final Collection<TypeFilter> excludeFilters;

    public XmlKylinRepositoryConfigurationSource(Element element, ParserContext parserContext,
            Environment environment) {
        super(environment);
        Assert.notNull(element);
        Assert.notNull(parserContext);
        this.element = element;
        this.parserContext = parserContext;
        TypeFilterParser parser = new TypeFilterParser(parserContext.getReaderContext());
        this.includeFilters = parser.parseTypeFilters(element, Type.INCLUDE);
        this.excludeFilters = parser.parseTypeFilters(element, Type.EXCLUDE);
    }

    public Collection<BeanDefinition> getCandidates(ResourceLoader loader) {
        Collection<BeanDefinition> find = super.getCandidates(loader);
        Set<BeanDefinition> result = new HashSet<BeanDefinition>();
        ClassLoader classLoader = org.springframework.util.ClassUtils.getDefaultClassLoader();
        for (BeanDefinition bean : find) {
            String repositoryInterface = bean.getBeanClassName();
            try {
                BeanDefinitionBuilder builder = BeanDefinitionBuilder
                        .rootBeanDefinition(KylinRepositoryFactoryBean.class.getName())
                        .addPropertyValue("repositoryInterface",
                                org.springframework.util.ClassUtils.forName(repositoryInterface, classLoader));
                builder.addPropertyValue("configPath", getAttribute(CONFIG_PATH));
                result.add(builder.getBeanDefinition());
            } catch (ClassNotFoundException e) {
                log.warn(String.format(CLASS_LOADING_ERROR, repositoryInterface, repositoryInterface, classLoader), e);
            } catch (LinkageError e) {
                log.warn(String.format(CLASS_LOADING_ERROR, repositoryInterface, repositoryInterface, classLoader), e);
            }
        }
        return result;
    }

    public Object getSource() {
        return parserContext.extractSource(element);
    }

    public Iterable<String> getBasePackages() {
        String attribute = element.getAttribute(BASE_PACKAGE);
        return Arrays.asList(StringUtils.delimitedListToStringArray(attribute, ",", " "));
    }

    public Element getElement() {
        return element;
    }

    @Override
    protected Iterable<TypeFilter> getExcludeFilters() {
        return excludeFilters;
    }

    @Override
    protected Iterable<TypeFilter> getIncludeFilters() {
        return includeFilters;
    }

    public String getAttribute(String name) {
        String attribute = element.getAttribute(name);
        return StringUtils.hasText(attribute) ? attribute : null;
    }

    public boolean usesExplicitFilters() {
        return !(this.includeFilters.isEmpty() && this.excludeFilters.isEmpty());
    }

    @Override
    public Collection<BeanDefinition> getCandidates() {
        return getCandidates(getResourceLoader());
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return parserContext.getReaderContext().getResourceLoader();
    }

    @Override
    public BeanDefinitionRegistry getBeanDefinitionRegistry() {
        return parserContext.getRegistry();
    }

    @Override
    public NamedNodeMap getAttributes() {
        return element.getAttributes();
    }

}
