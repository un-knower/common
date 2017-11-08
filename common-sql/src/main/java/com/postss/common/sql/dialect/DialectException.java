package com.postss.common.sql.dialect;

import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.system.code.CodeMessageManager;
import com.postss.common.system.exception.BaseException;

public class DialectException extends BaseException {

    private static final long serialVersionUID = 9068556037747589647L;
    private static Logger log = LoggerUtil.getLogger(DialectException.class);
    private static final String CODE_MESSAGE = "common-sql-properties/code-message-common-sql.properties";

    static {
        log.info("load DialectCode message : {}", CODE_MESSAGE);
        CodeMessageManager.loadMessageByPath(CODE_MESSAGE);
    }

    @Override
    public boolean isWriteLog() {
        return false;
    }

    public DialectException(String message) {
        super(message);
    }

}
