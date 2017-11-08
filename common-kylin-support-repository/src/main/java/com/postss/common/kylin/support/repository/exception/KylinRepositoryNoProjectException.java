package com.postss.common.kylin.support.repository.exception;

import com.postss.common.system.exception.SystemException;

public class KylinRepositoryNoProjectException extends SystemException {

    private static final long serialVersionUID = 46740241370420152L;

    private static String prompt = "KylinRepositoryBean注解中必须有project或cubeName！";

    public KylinRepositoryNoProjectException(String type) {
        super(prompt + ",type:" + type);
    }

}
