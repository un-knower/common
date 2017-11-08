package com.postss.common.kylin.support.repository.init;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;

import com.postss.common.kylin.support.repository.annotation.KylinRepositoryBean;
import com.postss.common.kylin.support.repository.annotation.NoKylinRepositoryBean;

/**
 * kylin-repository扫描
 * @className KylinRepositoryComponentProvider
 * @author jwSun
 * @date 2017年8月20日 上午11:18:02
 * @version 1.0.0
 */
public class KylinRepositoryComponentProvider extends ClassPathScanningCandidateComponentProvider {

    private static final String METHOD_NOT_PUBLIC = "AnnotationConfigUtils.processCommonDefinitionAnnotations(…) is not public! Make sure you're using Spring 3.2.5 or better. The class was loaded from %s.";

    private boolean considerNestedRepositoryInterfaces;

    /**
     * 增加包含KylinRepository 与不包含NoKylinRepositoryBean过滤器
     * @param includeFilters
     */
    public KylinRepositoryComponentProvider(Iterable<? extends TypeFilter> includeFilters) {
        super(false);
        assertRequiredSpringVersionPresent();
        Assert.notNull(includeFilters);
        if (includeFilters.iterator().hasNext()) {
            for (TypeFilter filter : includeFilters) {
                addIncludeFilter(filter);
            }
        } else {
            super.addIncludeFilter(new InterfaceTypeFilter(KylinRepositoryBean.class));
        }
        addExcludeFilter(new AnnotationTypeFilter(NoKylinRepositoryBean.class));
    }

    /**
     * 增加自定义扫描包含KylinRepository注解
     * @see ClassPathScanningCandidateComponentProvider#addIncludeFilter(TypeFilter)
     */
    @Override
    public void addIncludeFilter(TypeFilter includeFilter) {
        List<TypeFilter> filterPlusInterface = new ArrayList<TypeFilter>(2);
        filterPlusInterface.add(includeFilter);
        filterPlusInterface.add(new InterfaceTypeFilter(KylinRepositoryBean.class));
        super.addIncludeFilter(new AllTypeFilter(filterPlusInterface));
    }

    /**
     * 是否扫描  判断有KylinRepository注解的扫描
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        boolean isNonRepositoryInterface = !KylinRepositoryBean.class.getName().equals(beanDefinition.getBeanClassName());
        boolean isTopLevelType = !beanDefinition.getMetadata().hasEnclosingClass();
        boolean isConsiderNestedRepositories = isConsiderNestedRepositoryInterfaces();
        return isNonRepositoryInterface && (isTopLevelType || isConsiderNestedRepositories);
    }

    /**
     * 扫描并组装bean
     */
    @Override
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = super.findCandidateComponents(basePackage);
        for (BeanDefinition candidate : candidates) {
            if (candidate instanceof AnnotatedBeanDefinition) {
                AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
            }
        }
        return candidates;
    }

    /**
     * @return the considerNestedRepositoryInterfaces
     */
    public boolean isConsiderNestedRepositoryInterfaces() {
        return considerNestedRepositoryInterfaces;
    }

    /**
     * Controls whether nested inner-class {@link KylinRepositoryBean} interface definitions should be considered for automatic
     * discovery. This defaults to {@literal false}.
     * 
     * @param considerNestedRepositoryInterfaces
     */
    public void setConsiderNestedRepositoryInterfaces(boolean considerNestedRepositoryInterfaces) {
        this.considerNestedRepositoryInterfaces = considerNestedRepositoryInterfaces;
    }

    /**
     * Makes sure {@link AnnotationConfigUtils#processCommonDefinitionAnnotations(AnnotatedBeanDefinition) is public and
     * indicates the offending JAR if not.
     */
    private static void assertRequiredSpringVersionPresent() {

        try {
            AnnotationConfigUtils.class.getMethod("processCommonDefinitionAnnotations", AnnotatedBeanDefinition.class);
        } catch (NoSuchMethodException o_O) {
            throw new IllegalStateException(String.format(METHOD_NOT_PUBLIC,
                    AnnotationConfigUtils.class.getProtectionDomain().getCodeSource().getLocation()), o_O);
        }
    }

    /**
     * {@link org.springframework.core.type.filter.TypeFilter} that only matches interfaces. Thus setting this up makes
     * only sense providing an interface type as {@code targetType}.
     * @author Oliver Gierke
     */
    private static class InterfaceTypeFilter extends AssignableTypeFilter {

        /**
         * Creates a new {@link InterfaceTypeFilter}.
         * @param targetType
         */
        public InterfaceTypeFilter(Class<?> targetType) {
            super(targetType);
        }

        /**
         * 匹配有KylinRepository注解的类
         */
        @Override
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
                throws IOException {
            boolean hasKylinrepository = metadataReader.getAnnotationMetadata()
                    .hasAnnotation(KylinRepositoryBean.class.getName());
            return metadataReader.getClassMetadata().isInterface() && hasKylinrepository;
        }
    }

    /**
     * Helper class to create a {@link TypeFilter} that matches if all the delegates match.
     * 
     * @author Oliver Gierke
     */
    private static class AllTypeFilter implements TypeFilter {

        private final List<TypeFilter> delegates;

        /**
         * Creates a new {@link AllTypeFilter} to match if all the given delegates match.
         * 
         * @param delegates must not be {@literal null}.
         */
        public AllTypeFilter(List<TypeFilter> delegates) {

            Assert.notNull(delegates);
            this.delegates = delegates;
        }

        /* 
         * (non-Javadoc)
         * @see org.springframework.core.type.filter.TypeFilter#match(org.springframework.core.type.classreading.MetadataReader, org.springframework.core.type.classreading.MetadataReaderFactory)
         */
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
                throws IOException {

            for (TypeFilter filter : delegates) {
                if (!filter.match(metadataReader, metadataReaderFactory)) {
                    return false;
                }
            }

            return true;
        }
    }
}
