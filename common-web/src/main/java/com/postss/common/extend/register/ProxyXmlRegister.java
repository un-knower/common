package com.postss.common.extend.register;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.annotation.AnnotationUtils;

import com.postss.common.extend.org.springframework.context.config.AutoProxyApplicationConfig;
import com.postss.common.extend.org.springframework.context.config.ProxyBeanFactoryBean;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.system.spring.registry.XmlRegisterConfiguration;
import com.postss.common.util.StringUtil;

public class ProxyXmlRegister extends DefaultXmlRegister {

    private final String CONFIG = "config";
    private final String CLASS_LOADING_ERROR = "CLASS_LOADING_ERROR : interfaceName is %s, classLoader is %s";

    private Logger log = LoggerUtil.getLogger(getClass());

    public ProxyXmlRegister(XmlRegisterConfiguration xmlRegisterConfiguration) {
        super(xmlRegisterConfiguration);
    }

    /**
     * <pre>
     * 注册代理类
     * 支持继承AutoProxyApplicationConfig注解的形式实现自定义注解
     * beanName以优先级取，取不到则使用下一级
     * 1.取继承的注解value值
     * 2.取AutoProxyApplicationConfig注解value值
     * 3.取类名
     * </pre>
     */
    public void registryBean() {
        Collection<BeanDefinition> beanDefinitions = getFactoryBeans();
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Class<?> clazz = (Class<?>) beanDefinition.getPropertyValues().get("repositoryInterface");
            Annotation[] annotations = AnnotationUtils.getAnnotations(clazz);
            Annotation config = null;
            for (Annotation annotation : annotations) {
                if (AnnotationUtils.isAnnotationMetaPresent(annotation.getClass(), AutoProxyApplicationConfig.class)) {
                    config = annotation;
                    break;
                }
            }
            if (config == null || StringUtil.notEmpty(AnnotationUtils.getValue(config, "value"))) {
                config = AnnotationUtils.getAnnotation(clazz, AutoProxyApplicationConfig.class);
            }
            String beanName;
            if ("".equals(AnnotationUtils.getValue(config, "value"))) {
                beanName = clazz.getName();
            } else {
                beanName = (String) AnnotationUtils.getValue(config, "value");
            }
            registryBean(beanName, beanDefinition);
        }
    }

    protected Collection<BeanDefinition> getFactoryBeans() {
        Collection<BeanDefinition> find = getXmlRegisterConfiguration().getCandidates();
        Set<BeanDefinition> result = new HashSet<BeanDefinition>();
        ClassLoader classLoader = org.springframework.util.ClassUtils.getDefaultClassLoader();
        for (BeanDefinition bean : find) {
            String interfaceName = bean.getBeanClassName();
            try {
                BeanDefinitionBuilder builder = BeanDefinitionBuilder
                        .rootBeanDefinition(ProxyBeanFactoryBean.class.getName())
                        .addPropertyValue("repositoryInterface",
                                org.springframework.util.ClassUtils.forName(interfaceName, classLoader));
                String config = getXmlRegisterConfiguration().getAttribute(CONFIG);
                builder.addPropertyValue("config", config);
                result.add(builder.getBeanDefinition());
            } catch (ClassNotFoundException e) {
                log.warn(String.format(CLASS_LOADING_ERROR, interfaceName, classLoader), e);
            } catch (LinkageError e) {
                log.warn(String.format(CLASS_LOADING_ERROR, interfaceName, classLoader), e);
            }
        }
        return result;
    }

}
