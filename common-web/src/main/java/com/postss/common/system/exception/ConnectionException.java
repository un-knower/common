package com.postss.common.system.exception;

import com.postss.common.system.code.SystemCode;

/**
 * 接口连接异常
 * @className ConnectionException
 * @author jwSun
 * @date 2017年7月10日 下午7:56:22
 * @version 1.0.0
 */
public class ConnectionException extends SystemCodeException {

    private static final long serialVersionUID = 6544383882340546508L;

    private static final SystemCode systemCode = SystemCode.CONNECTION_ERROR;

    private static final boolean writeLog = true;

    public ConnectionException() {
        super(systemCode, writeLog);
    }

    public ConnectionException(String message) {
        super(systemCode, message, writeLog);
    }

    public ConnectionException(String message, Throwable cause) {
        super(systemCode, message, cause, writeLog);
    }

    public ConnectionException(Throwable cause) {
        super(systemCode, cause, writeLog);
    }

    protected ConnectionException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(systemCode, message, cause, enableSuppression, writableStackTrace, writeLog);
    }
}
