package com.postss.common.boost.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.postss.common.base.annotation.AutoWebApplicationConfig;
import com.postss.common.boost.handler.BoostHandlerAdapter;
import com.postss.common.boost.handler.BoostUrlHandlerMapping;

/**
 * 快速生成接口配置项
 * @className BoostConfig
 * @author jwSun
 * @date 2017年9月13日 下午5:37:46
 * @version 1.0.0
 */
@Configuration
@AutoWebApplicationConfig("boooooootConfig")
public class BoostConfig {

    @Bean
    public BoostUrlHandlerMapping boostUrlHandlerMapping() {
        return new BoostUrlHandlerMapping();
    }

    @Bean
    public BoostHandlerAdapter boostHandlerAdapter() {
        return new BoostHandlerAdapter();
    }

}
