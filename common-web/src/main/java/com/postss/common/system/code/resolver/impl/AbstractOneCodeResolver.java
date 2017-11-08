package com.postss.common.system.code.resolver.impl;

/**
 * 基本解析类
 * @author jwSun
 * @date 2017年5月16日 下午6:56:23
 */
public abstract class AbstractOneCodeResolver extends AbstractCodeResolver {

    protected int code;

    protected int getCode() {
        return code;
    }

    protected String getMessage() {
        return getMessageByCode(code);
    }

    public AbstractOneCodeResolver(int code) {
        super();
        this.code = code;
    }

}
