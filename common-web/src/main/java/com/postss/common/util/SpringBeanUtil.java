package com.postss.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.postss.common.base.annotation.AutoRootApplicationConfig;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;

/**
 * 获得root spring bean对象 工具类
 * @author jwSun
 * @date 2017年2月6日 下午5:49:59
 */
@AutoRootApplicationConfig
public class SpringBeanUtil implements ApplicationContextAware {

    private static Logger log = LoggerUtil.getLogger(SpringBeanUtil.class);

    private static ApplicationContext applicationContext;

    public static Object getBean(String beanId) {
        try {
            return applicationContext.getBean(beanId);
        } catch (Exception e) {
            log.warn("spring bean :{} cant find.", beanId);
            return null;
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        try {
            return applicationContext.getBean(clazz);
        } catch (Exception e) {
            log.warn("spring bean :{} cant find.", clazz.getName());
            return null;
        }
    }

    public static ApplicationContext getApplicationContext() {
        try {
            return applicationContext;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }

}
