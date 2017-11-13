package com.postss.common.extend.org.springframework.context.config;

import java.util.Map;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.annotation.AnnotationUtils;

public class ProxyBeanFactoryBean<T> implements FactoryBean<T> {

    private static ClassLoader classLoader = org.springframework.util.ClassUtils.getDefaultClassLoader();

    private Class<T> repositoryInterface;
    private Class<AutoProxy> proxyClass;
    private Map<String, Object> configMap;
    private String config;

    @SuppressWarnings("unchecked")
    @Override
    public T getObject() throws Exception {
        ProxyFactory result = new ProxyFactory();
        result.addInterface(repositoryInterface);
        AutoProxyApplicationConfig autoProxyApplicationConfig = AnnotationUtils.getAnnotation(repositoryInterface,
                AutoProxyApplicationConfig.class);
        result.addAdvice(
                autoProxyApplicationConfig.proxyClass().newInstance().getObject(repositoryInterface, configMap));
        return (T) result.getProxy(classLoader);
    }

    @Override
    public Class<?> getObjectType() {
        return repositoryInterface;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public Class<T> getRepositoryInterface() {
        return repositoryInterface;
    }

    public void setRepositoryInterface(Class<T> repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }

    public Class<AutoProxy> getProxyClass() {
        return proxyClass;
    }

    public void setProxyClass(Class<AutoProxy> proxyClass) {
        this.proxyClass = proxyClass;
    }

    public Map<String, Object> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, Object> configMap) {
        this.configMap = configMap;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

}
