package com.postss.common.kylin.exception;

import com.postss.common.system.code.CodeMessageManager;
import com.postss.common.system.exception.BusinessCodeException;

/**
 * kylin参数验证失败异常
 * @className KylinAuthenticationException
 * @author jwSun
 * @date 2017年7月27日 下午3:52:14
 * @version 1.0.0
 */
public class KylinParamsException extends BusinessCodeException implements KylinException {

    private final static int businessCode = CodeMessageManager.NO_MESSAGE_CODE;
    private final static String PROMPT = "参数错误";

    private static final long serialVersionUID = 4080810070315357155L;

    public KylinParamsException() {
        super(businessCode, PROMPT);
    }

    public KylinParamsException(String message) {
        super(businessCode, PROMPT + ":" + message);
    }
}
