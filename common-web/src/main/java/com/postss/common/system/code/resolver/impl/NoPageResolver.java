package com.postss.common.system.code.resolver.impl;

import com.postss.common.base.entity.ResultEntity;

/**
 * 404页面
 * @author jwSun
 * @date 2017年5月16日 下午7:00:18
 */
public class NoPageResolver extends AbstractOneCodeResolver {

    public NoPageResolver(int code) {
        super(code);
    }

    @Override
    public ResultEntity resolve(ResultEntity resolveEntity) {
        String message = getMessageByCode(resolveEntity.getInfo().getCode(), resolveEntity.getPromptMessage());
        resolveEntity.getInfo().setMessage(message);
        return resolveEntity;
    }

}
