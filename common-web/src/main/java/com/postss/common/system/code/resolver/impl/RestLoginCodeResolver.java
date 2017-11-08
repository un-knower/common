package com.postss.common.system.code.resolver.impl;

import com.postss.common.base.entity.ResultEntity;
import com.postss.common.util.QuickHashMap;
import com.postss.common.util.StringUtil;

/**
 * 重定向登录解析
 * @author jwSun
 * @date 2017年5月16日 下午6:59:55
 */
public class RestLoginCodeResolver extends AbstractOneCodeResolver {

    public RestLoginCodeResolver(int code) {
        super(code);
    }

    private final String SERVICE = "service";
    private String REST_URL = "restUrl";
    private final String pattern = "(http|https)://(.*)\\?service=(.*)";

    @Override
    public ResultEntity resolve(ResultEntity resolveEntity) {
        if (resolveEntity.getInfo().getFunctionEntity().getFunctionParams() == null) {
            log.error("resolveEntity redicturl is null");
        } else {
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
            String urlPrefix = StringUtil.getAllPatternMattcher(restUrl, pattern, 1);
            String requestContext = urlPrefix + "://" + StringUtil.getAllPatternMattcher(restUrl, pattern, 2);
            String service = StringUtil.getAllPatternMattcher(restUrl, pattern, 3);
            resolveEntity.getInfo().getFunctionEntity()
                    .setEval("alert(\"" + getMessage() + "\");window.location.href=\"" + redirectUrl + "\"")
                    .setData(new QuickHashMap<String, String>().quickPut(SERVICE, service).quickPut(REST_URL,
                            requestContext));
            String message = getMessageByCode(resolveEntity.getInfo().getCode(), resolveEntity.getPromptMessage());
            resolveEntity.getInfo().setMessage(message);
        }
        return resolveEntity;
    }
}
