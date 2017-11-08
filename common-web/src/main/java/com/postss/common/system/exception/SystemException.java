package com.postss.common.system.exception;

/**
 * 系统异常
 * @author jwSun
 * @date 2017年6月23日 下午5:29:32
 */
public class SystemException extends BaseException {

    private static final long serialVersionUID = -4487569574659015744L;

    public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    protected SystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public boolean isWriteLog() {
        return true;
    }
}
