package com.postss.common.system.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alibaba.fastjson.JSONObject;
import com.postss.common.base.enums.ResultStatus;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.system.exception.BusinessException;
import com.postss.common.util.StringUtil;

/**
 * 验证使用此套框架的HTTP返回值工具类
 * @className CheckResponseUtil
 * @author jwSun
 * @date 2017年9月1日 下午7:57:20
 * @version 1.0.0
 */
public class CheckResponseUtil {

    private static Logger log = LoggerUtil.getLogger(CheckResponseUtil.class);

    public static boolean check(ResponseEntity<String> response) {
        if (response == null || response.getStatusCode() != HttpStatus.OK)
            return false;
        String body = response.getBody();
        if (!StringUtil.notEmpty(body)) {
            return false;
        }
        try {
            JSONObject jobj = JSONObject.parseObject(body);
            int status = jobj.getInteger("status");
            if (status == ResultStatus.SUCCESS.getValue()) {
                return true;
            }
        } catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn("body:{},exception is : {}", body, e.getLocalizedMessage());
            }
        }
        return false;
    }

    public static JSONObject getData(ResponseEntity<String> response) {
        if (!check(response)) {
            throw new BusinessException("response check error");
        }
        JSONObject jobj = JSONObject.parseObject(response.getBody());
        return jobj.getJSONObject("data");
    }
}
