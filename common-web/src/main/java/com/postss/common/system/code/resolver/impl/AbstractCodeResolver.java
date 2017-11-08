package com.postss.common.system.code.resolver.impl;

import com.postss.common.base.entity.ResultEntity;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.system.code.CodeMessageManager;
import com.postss.common.system.code.resolver.CodeResolver;

/**
 * 基本解析类
 * @author jwSun
 * @date 2017年5月16日 下午6:56:23
 */
public abstract class AbstractCodeResolver implements CodeResolver {

    protected Logger log = LoggerUtil.getLogger(getClass());

    /*public enum ReturnInfo {
        info, code, url, message, callback;
    }*/

    protected synchronized String getMessageByCode(int code, Object... promptMessage) {
        return CodeMessageManager.getMessageByCode(code, promptMessage);
    }

    protected synchronized String getMessage(ResultEntity resolveEntity) {
        String message = null;
        if (resolveEntity.getInfo().getCode() == CodeMessageManager.NO_MESSAGE_CODE
                && resolveEntity.getThrowable() != null) {
            message = resolveEntity.getThrowable().getLocalizedMessage();
        } else {
            message = getMessageByCode(resolveEntity.getInfo().getCode(), resolveEntity.getPromptMessage());
        }
        return message;
    }

    public AbstractCodeResolver() {
        super();
    }

    /*protected Map<Object, Object> getReturnMap(int code) {
        Map<Object, Object> resolveMap = new HashMap<>();
        resolveMap.put(ReturnInfo.code, code);
        resolveMap.put(ReturnInfo.message, getMessageByCode(code));
        return resolveMap;
    }*/
}
