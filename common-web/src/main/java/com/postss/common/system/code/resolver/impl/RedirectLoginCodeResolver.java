package com.postss.common.system.code.resolver.impl;

import com.postss.common.base.entity.ResultEntity;

/**
 * 重定向登录解析
 * @author jwSun
 * @date 2017年5月16日 下午6:59:55
 */
public class RedirectLoginCodeResolver extends AbstractOneCodeResolver {

    public RedirectLoginCodeResolver(int code) {
        super(code);
    }

    @Override
    public ResultEntity resolve(ResultEntity resolveEntity) {
        if (resolveEntity.getInfo().getFunctionEntity().getFunctionParams() == null) {
            log.error("resolveEntity redicturl is null");
        } else {
            resolveEntity.getInfo().getFunctionEntity()
                    .setEval("alert(\"" + getMessage() + "\");window.location.href=\""
                            + resolveEntity.getInfo().getFunctionEntity().getFunctionParams() + "\"");
            String message = getMessageByCode(resolveEntity.getInfo().getCode(), resolveEntity.getPromptMessage());
            resolveEntity.getInfo().setMessage(message);
        }
        return resolveEntity;
    }
}
