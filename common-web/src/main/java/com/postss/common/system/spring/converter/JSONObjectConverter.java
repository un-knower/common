package com.postss.common.system.spring.converter;

import org.springframework.core.convert.converter.Converter;

import com.alibaba.fastjson.JSONObject;
import com.postss.common.base.annotation.AutoWebApplicationConfig;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.system.code.SystemCode;
import com.postss.common.system.exception.BusinessCodeException;
import com.postss.common.util.StringUtil;

/**
 * JSONObject 解析 (map)
 * @className JSONArrayConverter
 * @author jwSun
 * @date 2017年9月13日 下午10:10:37
 * @version 1.0.0
 */
@AutoWebApplicationConfig
public class JSONObjectConverter implements Converter<String, JSONObject> {

    private Logger log = LoggerUtil.getLogger(getClass());

    @Override
    public JSONObject convert(String source) {
        if (StringUtil.notEmpty(source)) {
            try {
                return JSONObject.parseObject(source);
            } catch (Exception o_o) {
                log.info("parse JSONObject error! source is {}", source);
                throw new BusinessCodeException(SystemCode.PARSE_ERROR.getCode()).setPromptMessage("JSONObject",
                        source);
            }
        }
        return null;
    }

}