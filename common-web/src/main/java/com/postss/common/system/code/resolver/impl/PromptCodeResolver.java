package com.postss.common.system.code.resolver.impl;

import com.postss.common.base.entity.ResultEntity;

public class PromptCodeResolver extends AbstractMultipartCodeResolver {

    @Override
    public ResultEntity resolve(ResultEntity resolveEntity) {
        String message = getMessage(resolveEntity);
        resolveEntity.getInfo().setMessage(message);
        return resolveEntity;
    }

}
