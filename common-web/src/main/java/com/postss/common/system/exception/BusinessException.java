package com.postss.common.system.exception;

/**
 * 业务抛出异常
 * @author jwSun
 * @date 2017年6月23日 下午5:29:32
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = -4487569574659015744L;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    protected BusinessException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public boolean isWriteLog() {
        return false;
    }

}
