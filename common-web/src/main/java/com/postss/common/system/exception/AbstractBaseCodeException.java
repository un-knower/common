package com.postss.common.system.exception;

import com.postss.common.system.code.CodeMessageManager;

/**
 * 基本异常类
 * @className BaseException
 * @author jwSun
 * @date 2017年7月5日 上午9:48:10
 * @version 1.0.0
 */
public abstract class AbstractBaseCodeException extends BaseException {

    private static final long serialVersionUID = 6503301294340165095L;

    private int businessCode;
    private Object[] promptMessage;//提示信息,可在配置文件中以{index}保存

    public AbstractBaseCodeException(int businessCode, String message) {
        super(message);
        this.businessCode = businessCode;
    }

    public AbstractBaseCodeException(int businessCode, String message, Throwable cause) {
        super(message, cause);
        this.businessCode = businessCode;
    }

    public AbstractBaseCodeException(int businessCode, Throwable cause) {
        super(cause);
        this.businessCode = businessCode;
    }

    protected AbstractBaseCodeException(int businessCode, String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.businessCode = businessCode;
    }

    public Object[] getPromptMessage() {
        return promptMessage;
    }

    public AbstractBaseCodeException setPromptMessage(Object... promptMessage) {
        this.promptMessage = promptMessage;
        return this;
    }

    public AbstractBaseCodeException(int businessCode) {
        this(businessCode, "NO_MESSAGE");
    }

    public int getBusinessCode() {
        return this.businessCode;
    }

    public String getMessage() {
        return CodeMessageManager.getDetailMessageByCodeOrDefault(businessCode, super.getMessage(), getPromptMessage());
    }

}
