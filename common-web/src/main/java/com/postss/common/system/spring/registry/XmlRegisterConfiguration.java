package com.postss.common.system.spring.registry;

import java.util.Collection;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.NamedNodeMap;

/**
 * 自定义标签注册bean配置接口
 * @className XmlRegisterConfiguration
 * @author jwSun
 * @date 2017年8月23日 上午10:51:59
 * @version 1.0.0
 */
public interface XmlRegisterConfiguration {

    /**
     * 获得扫描bean集合
     * @return
     */
    public Collection<BeanDefinition> getCandidates();

    /**
     * 获得ResourceLoader
     * @return
     */
    public ResourceLoader getResourceLoader();

    /**
     * 获得bean注册器
     * @return
     */
    public BeanDefinitionRegistry getBeanDefinitionRegistry();

    /**
     * 获得所有参数
     * @return
     */
    public NamedNodeMap getAttributes();

    /**
     * 获得指定参数
     * @return
     */
    public String getAttribute(String name);

}
