package com.postss.common.kylin.exception;

import com.postss.common.system.code.CodeMessageManager;
import com.postss.common.system.exception.BusinessCodeException;

/**
 * kylin - jdbc 失败异常
 * @className KylinAuthenticationException
 * @author jwSun
 * @date 2017年7月27日 下午3:52:14
 * @version 1.0.0
 */
public class KylinJdbcException extends BusinessCodeException implements KylinException {

    private final static int businessCode = CodeMessageManager.NO_MESSAGE_CODE;
    private final static String PROMPT = "kylin jdbc异常";

    private static final long serialVersionUID = 4080810070315357155L;

    public KylinJdbcException() {
        super(businessCode, PROMPT);
    }

    public KylinJdbcException(String message) {
        super(businessCode, PROMPT + ":" + message);
    }
}
