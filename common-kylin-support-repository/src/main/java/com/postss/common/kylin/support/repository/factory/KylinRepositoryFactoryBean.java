package com.postss.common.kylin.support.repository.factory;

import org.springframework.beans.factory.FactoryBean;

import com.postss.common.kylin.support.repository.KylinRepository;

public class KylinRepositoryFactoryBean<T extends KylinRepository> implements FactoryBean<T> {

    private Class<? extends T> repositoryInterface;
    private String configPath;

    public T getObject() {
        return KylinRepositoryProxy.createProxy(repositoryInterface, configPath);
    }

    @SuppressWarnings("unchecked")
    public Class<? extends T> getObjectType() {
        return (Class<? extends T>) (null == repositoryInterface ? KylinRepository.class : repositoryInterface);
    }

    public boolean isSingleton() {
        return true;
    }

    public Class<? extends T> getRepositoryInterface() {
        return repositoryInterface;
    }

    public void setRepositoryInterface(Class<? extends T> repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

}
