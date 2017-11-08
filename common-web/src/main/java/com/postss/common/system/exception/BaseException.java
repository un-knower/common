package com.postss.common.system.exception;

/**
 * 基本异常类
 * @className BaseException
 * @author jwSun
 * @date 2017年7月5日 上午9:48:10
 * @version 1.0.0
 */
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = 6503301294340165095L;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * 是否写入日志
     */
    public abstract boolean isWriteLog();

}
