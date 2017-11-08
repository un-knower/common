package com.postss.common.kylin.support.repository.init;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.data.repository.config.RepositoryConfigurationSourceSupport;
import org.springframework.util.Assert;

public abstract class XmlKylinRepositoryConfigurationSourceSupport implements XmlKylinRepositoryConfiguration {

    private final Environment environment;

    /**
     * Creates a new {@link RepositoryConfigurationSourceSupport} with the given environment.
     * 
     * @param environment must not be {@literal null}.
     */
    public XmlKylinRepositoryConfigurationSourceSupport(Environment environment) {

        Assert.notNull(environment, "Environment must not be null!");
        this.environment = environment;
    }

    /* 
     * (non-Javadoc)
     * @see org.springframework.data.repository.config.RepositoryConfiguration#getCandidates(org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider)
     */
    public Collection<BeanDefinition> getCandidates(ResourceLoader loader) {
        KylinRepositoryComponentProvider scanner = new KylinRepositoryComponentProvider(getIncludeFilters());
        scanner.setConsiderNestedRepositoryInterfaces(shouldConsiderNestedRepositories());
        scanner.setResourceLoader(loader);
        scanner.setEnvironment(environment);

        for (TypeFilter filter : getExcludeFilters()) {
            scanner.addExcludeFilter(filter);
        }

        Set<BeanDefinition> find = new HashSet<BeanDefinition>();

        for (String basePackage : getBasePackages()) {
            Set<BeanDefinition> candidate = scanner.findCandidateComponents(basePackage);
            find.addAll(candidate);
        }

        return find;
    }

    /**
     * Return the {@link TypeFilter}s to define which types to exclude when scanning for repositories. Default
     * implementation returns an empty collection.
     * 
     * @return must not be {@literal null}.
     */
    protected Iterable<TypeFilter> getExcludeFilters() {
        return Collections.emptySet();
    }

    /**
     * Return the {@link TypeFilter}s to define which types to include when scanning for repositories. Default
     * implementation returns an empty collection.
     * 
     * @return must not be {@literal null}.
     */
    protected Iterable<TypeFilter> getIncludeFilters() {
        return Collections.emptySet();
    }

    /**
     * Returns whether we should consider nested repositories, i.e. repository interface definitions nested in other
     * classes.
     * 
     * @return {@literal true} if the container should look for nested repository interface definitions.
     */
    public boolean shouldConsiderNestedRepositories() {
        return false;
    }
}
