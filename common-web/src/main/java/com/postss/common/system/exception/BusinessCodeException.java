package com.postss.common.system.exception;

/**
 * 业务抛出异常
 * @author jwSun
 * @date 2017年6月23日 下午5:29:32
 */
public class BusinessCodeException extends AbstractBaseCodeException {

    private static final long serialVersionUID = -4487569574659015744L;

    public BusinessCodeException(int businessCode) {
        super(businessCode);
    }

    public BusinessCodeException(int businessCode, String message) {
        super(businessCode, message);
    }

    public BusinessCodeException(int businessCode, String message, Throwable cause) {
        super(businessCode, message, cause);
    }

    public BusinessCodeException(int businessCode, Throwable cause) {
        super(businessCode, cause);
    }

    protected BusinessCodeException(int businessCode, String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(businessCode, message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public boolean isWriteLog() {
        return false;
    }

}
