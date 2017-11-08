package com.postss.common.util;

import com.postss.common.base.entity.ResultEntity;
import com.postss.common.system.code.resolver.CodeResolver;
import com.postss.common.system.code.resolver.impl.SimpleMultipartCodeResolver;

public class ResponseUtil {

    private static CodeResolver codeResolver = new SimpleMultipartCodeResolver();

    public static ResultEntity codeResolve(ResultEntity resolveEntity) {
        if (resolveEntity.getInfo().getSystemCode() != null) {
            return resolveEntity.getInfo().getSystemCode().getCodeResolver().resolve(resolveEntity);
        }
        return codeResolver.resolve(resolveEntity);
    }

    public static String codeResolveJson(ResultEntity resolveEntity) {
        return JSONObjectUtil.toJSONString(codeResolve(resolveEntity), resolveEntity.getFilterNames());
    }

    public static Object codePrompt(ResultEntity resolveEntity) {
        return resolveEntity.getInfo().getSystemCode().getCodeResolver().resolve(resolveEntity);
    }
}
