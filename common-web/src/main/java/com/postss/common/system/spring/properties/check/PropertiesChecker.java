package com.postss.common.system.spring.properties.check;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;

import com.postss.common.base.annotation.AutoRootApplicationConfig;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.system.exception.SystemException;
import com.postss.common.util.PropertiesUtil;
import com.postss.common.util.StringUtil;

/**
 * 配置值检测,检测失败抛出异常,需要在spring中注册
 * @author jwSun
 * @date 2017年6月12日 下午4:32:03
 */
@AutoRootApplicationConfig("propertiesChecker")
public class PropertiesChecker implements InitializingBean {

    private Logger log = LoggerUtil.getLogger(getClass());

    /**不能为空字段 key:properties-name,val:properties-file**/
    private Map<String, String> notNullMap;
    /**可以为空字段,但必须有key key:properties-name,val:properties-file**/
    private Map<String, String> canNullMap;

    public Map<String, String> getNotNullMap() {
        return notNullMap;
    }

    public void setNotNullMap(Map<String, String> notNullMap) {
        this.notNullMap = notNullMap;
    }

    public Map<String, String> getCanNullMap() {
        return canNullMap;
    }

    public void setCanNullMap(Map<String, String> canNullMap) {
        this.canNullMap = canNullMap;
    }

    @PostConstruct
    public void checkProperties() {

    }

    public void afterPropertiesSet() throws Exception {
        log.info("properties配置值检测 init...");
        if (notNullMap != null) {
            for (Map.Entry<String, String> nutNullEntry : notNullMap.entrySet()) {
                String propertiesVal = null;
                if ("".equals(nutNullEntry.getValue())) {
                    propertiesVal = PropertiesUtil.getProperty(nutNullEntry.getKey());
                } else {
                    propertiesVal = PropertiesUtil.getProperty(nutNullEntry.getKey(), nutNullEntry.getValue());
                }
                log.debug("find notNullMap properties : {} in {} , value is {}.", nutNullEntry.getKey(),
                        nutNullEntry.getValue(), propertiesVal);
                if (StringUtil.notEmpty(propertiesVal)) {
                    continue;
                } else {
                    throw new SystemException("notNullMap properties : " + nutNullEntry.getKey() + " in "
                            + nutNullEntry.getValue() + " ,value is null or empty.");
                }
            }
        }
        if (canNullMap != null) {
            for (Map.Entry<String, String> canNullEntry : canNullMap.entrySet()) {
                String propertiesVal = null;
                if ("".equals(canNullEntry.getValue())) {
                    propertiesVal = PropertiesUtil.getProperty(canNullEntry.getKey());
                } else {
                    propertiesVal = PropertiesUtil.getProperty(canNullEntry.getKey(), canNullEntry.getValue());
                }
                log.debug("find canNullMap properties : {} in {} , value is {}.", canNullEntry.getKey(),
                        canNullEntry.getValue(), propertiesVal);
                if (propertiesVal == null) {
                    throw new SystemException("canNullMap properties : " + canNullEntry.getKey() + " in "
                            + canNullEntry.getValue() + " ,value is null.");
                }
            }
        }
        log.info("all properties check success");
    }

}
