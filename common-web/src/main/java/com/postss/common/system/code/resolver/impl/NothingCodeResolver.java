package com.postss.common.system.code.resolver.impl;

import com.postss.common.base.entity.ResultEntity;

/**
 * 原样返回
 * @className NothingCodeResolver
 * @author jwSun
 * @date 2017年8月4日 上午11:37:16
 * @version 1.0.0
 */
public class NothingCodeResolver extends AbstractMultipartCodeResolver {

    @Override
    public ResultEntity resolve(ResultEntity resolveEntity) {
        return resolveEntity;
    }

}
