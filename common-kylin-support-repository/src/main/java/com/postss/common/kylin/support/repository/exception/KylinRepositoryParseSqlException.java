package com.postss.common.kylin.support.repository.exception;

import com.postss.common.system.exception.SystemException;

public class KylinRepositoryParseSqlException extends SystemException {

    private static final long serialVersionUID = 46740241370420152L;

    public KylinRepositoryParseSqlException(String message) {
        super(message);
    }

}
