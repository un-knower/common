package com.postss.common.system.exception;

import com.postss.common.system.code.SystemCode;

/**
 * 系统返回码异常
 * @author jwSun
 * @date 2017年6月23日 下午5:29:32
 */
public class SystemCodeException extends AbstractBaseCodeException {

    private static final long serialVersionUID = -4487569574659015744L;

    private boolean writeLog = true;

    private SystemCode systemCode;

    public SystemCodeException(SystemCode systemCode) {
        super(systemCode.getCode());
        this.systemCode = systemCode;
    }

    public SystemCodeException(SystemCode systemCode, String message) {
        super(systemCode.getCode(), message);
        this.systemCode = systemCode;
    }

    public SystemCodeException(SystemCode systemCode, String message, Throwable cause) {
        super(systemCode.getCode(), message, cause);
        this.systemCode = systemCode;
    }

    public SystemCodeException(SystemCode systemCode, Throwable cause) {
        super(systemCode.getCode(), cause);
        this.systemCode = systemCode;
    }

    protected SystemCodeException(SystemCode systemCode, String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(systemCode.getCode(), message, cause, enableSuppression, writableStackTrace);
        this.systemCode = systemCode;
    }

    public SystemCodeException(SystemCode systemCode, boolean writeLog) {
        super(systemCode.getCode());
        this.systemCode = systemCode;
        this.writeLog = writeLog;
    }

    public SystemCodeException(SystemCode systemCode, String message, boolean writeLog) {
        super(systemCode.getCode(), message);
        this.systemCode = systemCode;
        this.writeLog = writeLog;
    }

    public SystemCodeException(SystemCode systemCode, String message, Throwable cause, boolean writeLog) {
        super(systemCode.getCode(), message, cause);
        this.systemCode = systemCode;
        this.writeLog = writeLog;
    }

    public SystemCodeException(SystemCode systemCode, Throwable cause, boolean writeLog) {
        super(systemCode.getCode(), cause);
        this.systemCode = systemCode;
        this.writeLog = writeLog;
    }

    protected SystemCodeException(SystemCode systemCode, String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace, boolean writeLog) {
        super(systemCode.getCode(), message, cause, enableSuppression, writableStackTrace);
        this.systemCode = systemCode;
        this.writeLog = writeLog;
    }

    /**
     * 获得抛出的系统异常码
     * @return SystemCode
     */
    public SystemCode getSystemCode() {
        return this.systemCode;
    }

    @Override
    public boolean isWriteLog() {
        return this.writeLog;
    }

}
