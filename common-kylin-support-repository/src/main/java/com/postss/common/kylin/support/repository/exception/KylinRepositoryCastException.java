package com.postss.common.kylin.support.repository.exception;

import com.postss.common.system.exception.SystemException;

public class KylinRepositoryCastException extends SystemException {

    private static final long serialVersionUID = 46740241370420152L;

    private static String prompt = "暂时只支持方法返回值为String 或 ApiSqlResultEntity 类型!";

    public KylinRepositoryCastException(String type) {
        super(prompt + ",type:" + type);
    }

}
