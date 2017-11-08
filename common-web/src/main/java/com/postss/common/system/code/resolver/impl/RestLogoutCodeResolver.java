package com.postss.common.system.code.resolver.impl;

import com.postss.common.base.entity.ResultEntity;
import com.postss.common.base.enums.ResultStatus;
import com.postss.common.util.QuickHashMap;

/**
 * REST退出
 * @author jwSun
 * @date 2017年5月16日 下午6:59:55
 */
public class RestLogoutCodeResolver extends AbstractOneCodeResolver {

    public RestLogoutCodeResolver(int code) {
        super(code);
    }

    //private final String SERVICE = "service";
    private String REST_URL = "restUrl";
    //private final String pattern = "(http|https)://(.*)\\?service=(.*)";

    @Override
    public ResultEntity resolve(ResultEntity resolveEntity) {
        if (resolveEntity.getInfo().getFunctionEntity().getFunctionParams() == null) {
            log.error("resolveEntity redicturl is null");
        } else {
            resolveEntity.setStatus(ResultStatus.SUCCESS.getValue());
            resolveEntity.setValidate(null);
            String restAndRedirectUrl = resolveEntity.getInfo().getFunctionEntity().getFunctionParams();
            String[] urlArray = restAndRedirectUrl.split(",");
            String restUrl;
            String redirectUrl;
            if (urlArray.length == 1) {
                restUrl = urlArray[0];
                redirectUrl = restUrl;
            } else {
                restUrl = urlArray[0];
                redirectUrl = urlArray[1];
            }
            resolveEntity.getInfo().getFunctionEntity()
                    .setEval("alert(\"" + getMessage() + "\");window.location.href=\"" + redirectUrl + "\"")
                    .setData(new QuickHashMap<String, String>().quickPut(REST_URL, restUrl));
            String message = getMessageByCode(resolveEntity.getInfo().getCode(), resolveEntity.getPromptMessage());
            resolveEntity.getInfo().setMessage(message);
        }
        return resolveEntity;
    }
}
